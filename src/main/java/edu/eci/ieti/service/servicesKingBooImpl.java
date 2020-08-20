package edu.eci.ieti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.ieti.entities.User;
import edu.eci.ieti.persistence.UserRepository;

@Service
public class servicesKingBooImpl {
	@Autowired
	private UserRepository userRepository;
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

}
