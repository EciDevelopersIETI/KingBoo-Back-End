package edu.eci.ieti.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.ieti.entities.User;
import edu.eci.ieti.service.servicesKingBooImpl;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
    private servicesKingBooImpl Service;
	public UserController() {
		// TODO Auto-generated constructor stub
	}
	 @RequestMapping (method = RequestMethod.GET )
	 public ResponseEntity<?>  getAllUsers(){
	        try{
	            List<User> usuarios = Service.getAllUsuarios();
	            return new ResponseEntity<>(usuarios, HttpStatus.ACCEPTED);
	        }catch (Exception e){
	            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	       }
	 }      

}
