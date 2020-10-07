package edu.eci.ieti.service;

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
	public Provider getProviderByName(String name){
		return providerRepository.findByProviderName(name);
	}
	public void saveUser(User user){
		if(user.getProvider().getProviderName()!=null){
			saveProvider(user.getProvider());
			System.out.println("Im in");
		}
		userRepository.save(user);
	}
	public void saveReserva(Reserva reserva) {
		reservaRepository.save(reserva);
	}

	public void saveProvider(Provider provider){
		providerRepository.save(provider);
	}
	public List<Provider> getAllProviders(){
		return providerRepository.findAll();
	}


}
