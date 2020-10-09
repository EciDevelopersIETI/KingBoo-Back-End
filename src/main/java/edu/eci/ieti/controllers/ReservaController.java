package edu.eci.ieti.controllers;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.ieti.entities.Reserva;
import edu.eci.ieti.entities.User;
import edu.eci.ieti.service.servicesKingBooImpl;

@RestController
@CrossOrigin
@RequestMapping("reservas")

public class ReservaController {
	@Autowired
    private servicesKingBooImpl Service;
	public ReservaController() {
		// TODO Auto-generated constructor stub
	}
	@RequestMapping (method = RequestMethod.GET )
	 public ResponseEntity<?>  getAllReservas(){
	        try{
	            List<Reserva> reservas = Service.getAllReservas();
	            return new ResponseEntity<>(reservas, HttpStatus.ACCEPTED);
	        }catch (Exception e){
	            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	       }
	 }
	@PostMapping("/newreserva")
	public ResponseEntity<?> newUser(@RequestBody Reserva reserva) throws ServletException {
		Service.saveReserva(reserva);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	@RequestMapping(path ="/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getUsuarioByNick(@PathVariable ("id") String id){
            return new ResponseEntity<>(Service.getReservaById(id),HttpStatus.ACCEPTED);

    }

    @PostMapping("/updatereserva")
	public ResponseEntity<?> updateReserva(@RequestBody String idReserva,String encargado) throws ServletException {
		Service.updateEncargadoReserva(idReserva,encargado);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
