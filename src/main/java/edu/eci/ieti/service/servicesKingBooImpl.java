package edu.eci.ieti.service;

import java.util.List;
import java.util.Optional;

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

	public void saveUser(User user){
		userRepository.save(user);
	}
	public void saveReserva(Reserva reserva) {
		reservaRepository.save(reserva);
	}

}
