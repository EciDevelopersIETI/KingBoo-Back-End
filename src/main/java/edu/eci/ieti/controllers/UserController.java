package edu.eci.ieti.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.eci.ieti.config.PasswordEncryptorConfiguration;
import edu.eci.ieti.config.Token;
import edu.eci.ieti.entities.User;
import edu.eci.ieti.service.servicesKingBooImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin
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

	 @PostMapping("/login")
	 public ResponseEntity<?> login(@RequestBody User userLogin) throws ServletException {
		String jwtToken = "";
		System.out.println("llegoooooooooooooo a controlleeeeeeeeeeeeeeeer");
		if (userLogin.getEmail() == null || userLogin.getPassword() == null) {
			throw new ServletException("Please fill in username and password");
		}
		String email = userLogin.getEmail();
		String password = userLogin.getPassword();
		User user = Service.getUserByEmail(email);
		String pwd = user.getPassword();
		if (!pwd.equals(password)) {
			throw new ServletException("Invalid login. Please check your name and password.");
		}
		jwtToken = Jwts.builder().setSubject(email).claim("roles", "user").setIssuedAt(new Date(System.currentTimeMillis() + 600000)).signWith(
				SignatureAlgorithm.HS256, PasswordEncryptorConfiguration.passwordEncryptor().encryptPassword(pwd)).compact();
		return new ResponseEntity<>(new Token(jwtToken, user), HttpStatus.OK);
	}

	@PostMapping("/newuser")
	public ResponseEntity<?> newUser(@RequestBody User user) throws ServletException {
		List<User> users = Service.getAllUsuarios();
		Boolean res = false;
		for (User u:users){
			System.out.println(u.getEmail().equals(user.getEmail()));
			if(u.getEmail().equals(user.getEmail())){
				System.out.println("Hola, el user esta repetido xdddd");
				res = true;
			}
		}
		if(!res) {
			Service.saveUser(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else{
			throw new ServletException("Ususarios existentes");
		}
	}

	@RequestMapping(path ="/{correo}",method = RequestMethod.GET)
    public ResponseEntity<?> getUsuarioByNick(@PathVariable ("correo") String correo){
            return new ResponseEntity<>(Service.getUserByEmail(correo),HttpStatus.ACCEPTED);
    }

	@PostMapping("/updateprovider")
	public ResponseEntity<?> updateProvider(@RequestBody User user) throws ServletException {
		Service.updateProvider(user);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}


}
