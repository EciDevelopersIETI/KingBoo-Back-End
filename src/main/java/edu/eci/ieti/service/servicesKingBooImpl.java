package edu.eci.ieti.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import edu.eci.ieti.entities.Provider;
import edu.eci.ieti.persistence.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.ieti.entities.Reserva;
import edu.eci.ieti.entities.User;
import edu.eci.ieti.persistence.ReservaRepository;
import edu.eci.ieti.persistence.UserRepository;

@Service
public class servicesKingBooImpl {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReservaRepository reservaRepository;
  	@Autowired
	private ProviderRepository providerRepository;

	public servicesKingBooImpl() {
		// TODO Auto-generated constructor stub
	}
	public User getUsuarioByUserId(String codigo){
	        User usuario = null;
	        try{
	            usuario = userRepository.findByEmail(codigo);
	            return usuario;
	        }catch(Exception e){
	        	e.printStackTrace(); 
	        }
			return usuario;
	}
	public List<Reserva> getReservasByFecha(String provider,String date) throws ParseException{
		List<Reserva> reservas = new ArrayList<Reserva>();
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		Date fechaFiltro = dateFormat.parse(date);
		for(Reserva res:getAllReservas()) {
			 if(fechaFiltro.equals(dateFormat.parse(res.getFecha().toInstant().toString())) && provider.equals(res.getProvider().getProviderName())) {
				 reservas.add(res);
				 System.out.println(res.getReservaId());
			 }
		}
		return reservas;
	
		
	}
	public List<String> getCuposDisponibles(String provider,String date) throws ParseException{
		List<String> horarios=new ArrayList<String>(Arrays.asList("07:00-07:30","07:30-08:00","08:00-08:30","08:30-09:00","09:00-09:30","09:30-10:00","10:00-10:30","10:30-11:00","11:00-11:30","11:30-12:00","12:00-12:30","12:30-13:00","13:00-13:30","13:30-14:00","14:00-14:30","14:30-15:00","15:00-15:30","15:30-16:00","16:00-16:30","16:30-17:00","17:00-17:30","17:30-18:00","18:00-18:30","18:30-19:00"));
		List<String> horariosfin=new ArrayList<String>();
		Provider pro = getProviderByName(provider);
		List<Reserva> reservas = getReservasByFecha(provider,date);
		System.out.println("cap "+pro.getCapacity());
		if(reservas.size()==0) {
			return horarios;
		}
		else {
			for(Reserva res:reservas) {
				for(String hora:horarios) {
					String horas[]= hora.split("-"); 
					if(countReservasByHora(reservas,horas[0]) < pro.getCapacity()) {
						horariosfin.add(hora);
					}
				}
			}
			return  horariosfin;
		}
		
	}
	public int countReservasByHora(List<Reserva> reservas,String hora) {
		int cont = 0;
		for(Reserva res:reservas) {
			String hora1[] = hora.split(":");
			String hora2[] = res.getHora().split(":");
			if(hora.equals(res.getHora())) {
				cont++;
			}			
			else if(Integer.parseInt(hora2[0])<Integer.parseInt(hora1[0])) {
				if(diferenciaEnMinutosPorHoras(res.getHora(),hora,res.getServicios().length*30)) {
					cont++;
				}
			}
		}		
		return cont;
	}
	public boolean diferenciaEnMinutosPorHoras(String ini,String finalxd,int intervalo){
		int contador = 0;
		String inicio = ini;
		String fin = finalxd;
		
		while(!inicio.equals(fin)){
			String parts[] = inicio.split(":");
			if(parts[1].equals("00")) {
				inicio = parts[0]+":"+"30";
				contador+=30;
			}
			else {
				int nuevo = Integer.parseInt(parts[0]);
				nuevo++;
				if(nuevo<10) {
					inicio = "0"+String.valueOf(nuevo)+":"+"00";
					contador+=30;
				}
				else {
					inicio = String.valueOf(nuevo)+":"+"00";
					contador+=30;
				}
				
			}
		}
		if(contador<intervalo) {
			return true;
		}
		else {
			return false;
		}
	}
	public List<User> getAllUsuarios(){
		return userRepository.findAll();
	}
	public List<Reserva> getAllReservas(){
		return reservaRepository.findAll();
	}
	public User getUserByEmail(String mail) {
		return userRepository.findByEmail(mail);
	}

	public Reserva getReservaById(String id) {
		return reservaRepository.findByReservaId(id);
	}

	public List<Reserva> getReservaByProvider(String provider){
		List<Reserva> reservas = new ArrayList<>();
		for(Reserva res:reservaRepository.findAll()){
			System.out.println("res: "+res.getProvider().getProviderName());
			if(res.getProvider().getProviderName().equals(provider)){
				reservas.add(res);
			}
		}
		return reservas;
	}

	public List<Reserva> getReservaByUser(User user){
		List<Reserva> reservas = new ArrayList<>();
		for(Reserva res:reservaRepository.findAll()){
			System.out.println("res: "+res.getUser().getEmail());
			System.out.println("user: "+user.getEmail());
			if(res.getUser().getEmail().equals(user.getEmail())){
				reservas.add(res);
			}
		}
		return reservas;
	}

	public String[] getServicesProviderByName(String name){
		return this.getProviderByName(name).getServices();
	}
	public Provider getProviderByName(String name){
		return providerRepository.findByProviderName(name);
	}
	public void saveUser(User user)  {
		if (user.getProvider().getProviderName() != null) {
        saveProvider(user.getProvider());
        System.out.println("Im in");
		}
		userRepository.save(user);
	}

	public void saveReserva(Reserva reserva) {
		reservaRepository.save(reserva);
	}

	public void deleteReserva(String id) {
		reservaRepository.deleteByReservaId(id);;
	}

	public void deleteUser(String email){
	    userRepository.deleteUserByEmail(email);
    }

    public void deleteProvider(String name){
		providerRepository.deleteUserByProviderName(name);
	}

	public void updateEncargadoReserva(String name, String id){
		Reserva reserva = this.getReservaById(id);
		reserva.setEncargado(name);
		this.deleteReserva(id);
		this.saveReserva(reserva);
	}

	public void updateProvider(User user){
		// User usr = this.getUserByEmail(user.getEmail());
		User userTemp = user;
		this.deleteUser(user.getEmail());
		this.deleteProvider(user.getProvider().getProviderName());
		this.saveUser(userTemp);
    }

	public void saveProvider(Provider provider){
		providerRepository.save(provider);
	}
	public List<Provider> getAllProviders(){
		return providerRepository.findAll();
	}

}
