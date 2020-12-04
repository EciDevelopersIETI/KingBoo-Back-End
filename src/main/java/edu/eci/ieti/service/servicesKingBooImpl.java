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
	public List<Object> getClientesFrecuentes(String nameProvider){
		List<Object> usuariosfrecuentes = new ArrayList<Object>();
		List<User> usuarios = this.getAllUsuarios();
		List<Reserva> reservas = this.getReservaByProvider(nameProvider);
		for(User u:usuarios) {
			if((u.getRol()).equals("cli")) {
				System.out.println(getReservasUserByProvider(u,reservas));
				if(3<getReservasUserByProvider(u,reservas)) {
					Object[] datos = {getReservasUserByProvider(u,reservas),u};
					usuariosfrecuentes.add(datos);
				}
			}
		}
		return usuariosfrecuentes;

	}
	public int getReservasUserByProvider(User user,List<Reserva> reservas) {
		int cont = 0;
		for(Reserva re:reservas) {
			if((user.getEmail()).equals(re.getUser().getEmail())) {
				cont++;
			}
		}
		return cont;
	}
	public int[] getEstadisticasByProvider(String nameProvider) {
		int res[]=new int[4];
		List<Reserva> reservas = this.getReservaByProvider(nameProvider);
		String servicesreferTempo[] = {"Corte de Cabello","Barba","Manicura","Depilacion"};
		String servicesrefer[] =new String[servicesreferTempo.length];
		List<String> services = Arrays.asList(this.getServicesProviderByName(nameProvider));
		for(int i=0;i<servicesreferTempo.length;i++) {
			for(String ser2:services) {
				if((servicesreferTempo[i].split(" ")[0]).equals(ser2.split(" ")[0])) {
					servicesrefer[i] = ser2;
				}
			}
		}
		for(int i=0;i<servicesrefer.length;i++) {
			if(!services.contains(servicesrefer[i])) {
				res[i]=0;
			}
			else {
				res[i]=this.getNumeroReservasServicio(servicesrefer[i],reservas);
			}
		}
		return res;
	}

	public int[] getEstadisticasByHora(String nameProvider){
		int res[]=new int[24];
		List<Reserva> reservas = this.getReservaByProvider(nameProvider);
		String horaTemp[] = {"07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30"};
		for(int i=0;i<horaTemp.length;i++) {
			int count = countReservasByHora(reservas,horaTemp[i]);
			res[i]=count;
		}
		List<String> services = Arrays.asList(this.getServicesProviderByName(nameProvider));
		return res;
	}

	public int getNumeroReservasServicio(String service,List<Reserva> reservas) {
		String[] serviceOnly = service.split(" ");
		int cont = 0;
		for(Reserva res:reservas) {
			List<String> servicesReserva = Arrays.asList(res.getServicios());
			for(String i:servicesReserva){
				String[] serviceResOnly = i.split(" ");
				if(serviceResOnly[0].equals(serviceOnly[0])){
					cont++;
				}
			}
		}
		return cont;
	}
	public int getNumeroTotalReservas(List<Reserva> reservas) {
		int cont = 0;
		for(Reserva res:reservas) {
			cont+=res.getServicios().length;
		}
		return cont;
	}
	public boolean verificarCuposReserva(Reserva reserva) throws ParseException {
		boolean res = true;
		String fecha = new SimpleDateFormat("yyyy-MM-dd").format((new Date(reserva.getFecha().getTime() + (1000 * 60 * 60 * 24))));
		List<String> horarios = getCuposDisponibles(reserva.getProvider().getProviderName(),fecha);
		int cont = 0;
		String tempo = reserva.getHora();
		for(int i=0;i<reserva.getServicios().length-1;i++) {
			String hora[] = tempo.split(":");
			if(hora[1].equals("00")) {
				tempo= hora[0]+":"+"30";
				cont+=30;
			}
			else {
				int nuevo = Integer.parseInt(hora[0]);
				nuevo++;
				if(nuevo<10) {
					tempo = "0"+String.valueOf(nuevo)+":"+"00";
					cont+=30;
				}
				else {
					tempo = String.valueOf(nuevo)+":"+"00";
					cont+=30;
				}
			}
			if(!verificarCupo(horarios,tempo)) {
				res = false;
			}
		}
		
		return res;
	}
	public boolean verificarCupo(List<String> horarios,String hora) {
		boolean res = false;
		for(String hor:horarios) {
			String part[] = hor.split("-");
			if(part[0].equals(hora)) {
				res = true;
			}
		}
		return res;
		
	}
	public List<String> getCuposDisponibles(String provider,String date) throws ParseException{
		List<String> horarios=new ArrayList<String>(Arrays.asList("07:00-07:30","07:30-08:00","08:00-08:30","08:30-09:00","09:00-09:30","09:30-10:00","10:00-10:30","10:30-11:00","11:00-11:30","11:30-12:00","12:00-12:30","12:30-13:00","13:00-13:30","13:30-14:00","14:00-14:30","14:30-15:00","15:00-15:30","15:30-16:00","16:00-16:30","16:30-17:00","17:00-17:30","17:30-18:00","18:00-18:30","18:30-19:00"));
		List<String> horariosfin=new ArrayList<String>();
		Provider pro = getProviderByName(provider);
		List<Reserva> reservas = getReservasByFecha(provider,date);
		if(reservas.size()==0) {
			return horarios;
		}
		else {
			for(Reserva res:reservas) {
				for(String hora:horarios) {
					String horas[]= hora.split("-"); 
					if(countReservasByHora(reservas,horas[0]) < pro.getCapacity() && !horariosfin.contains(hora)) {
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
			else if(Integer.parseInt(hora2[0])<Integer.parseInt(hora1[0]) || (Integer.parseInt(hora2[0])==Integer.parseInt(hora1[0]) && Integer.parseInt(hora2[1])<Integer.parseInt(hora1[1])) ) {
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
			if(res.getProvider().getProviderName().equals(provider)){
				reservas.add(res);
			}
		}
		return reservas;
	}

	public List<Reserva> getReservaByUser(User user){
		List<Reserva> reservas = new ArrayList<>();
		for(Reserva res:reservaRepository.findAll()){
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
