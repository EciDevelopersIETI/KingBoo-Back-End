package edu.eci.ieti.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.servlet.ServletException;

import com.fasterxml.jackson.databind.util.JSONPObject;
import edu.eci.ieti.entities.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

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

	@RequestMapping (path="/actives", method = RequestMethod.GET )
	public ResponseEntity<?>  getAllActiveReservas(){
		try{
			Date fecha = new Date();
			List<Reserva> reservas = Service.getAllReservas();
			List<Reserva> actives = new ArrayList<Reserva>();
			for(Reserva r : reservas){
				if((new Date(r.getFecha().getTime() + (1000 * 60 * 60 * 24))).after(fecha)){
					actives.add(r);
				}
			}
			return new ResponseEntity<>(actives, HttpStatus.ACCEPTED);
		}catch (Exception e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/newreserva")
	public ResponseEntity<?> newReserva(@RequestBody Reserva reserva) throws ServletException, ParseException {
		if(Service.verificarCuposReserva(reserva)) {
			Service.saveReserva(reserva);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			throw new ServletException("No se pudo realizar la reserva debido a que no hay suficientes cupos en la fecha que eligio para todos los servicios que desea reservar ,Por favor revise los horarios disponibles y realize la reserva en esos horarios");
		}

	}
	@RequestMapping(path ="/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getUsuarioByNick(@PathVariable ("id") String id){
            return new ResponseEntity<>(Service.getReservaById(id),HttpStatus.ACCEPTED);
    }


	@RequestMapping(path ="/user/{user}",method = RequestMethod.GET)
	public ResponseEntity<?> getReservaByUser(@PathVariable ("user") String user){
		User usuario = Service.getUserByEmail(user);
		return new ResponseEntity<>(Service.getReservaByUser(usuario),HttpStatus.ACCEPTED);
	}

	@RequestMapping(path ="/activeuser/{user}",method = RequestMethod.GET)
	public ResponseEntity<?> getActiveReservaByUser(@PathVariable ("user") String user){
		User usuario = Service.getUserByEmail(user);
		Date fecha = new Date();
		List<Reserva> reservas = Service.getReservaByUser(usuario);
		List<Reserva> actives = new ArrayList<Reserva>();
		for(Reserva r : reservas){
			if((new Date(r.getFecha().getTime() + (1000 * 60 * 60 * 24))).after(fecha)){
				actives.add(r);
			}
		}
		return new ResponseEntity<>(actives,HttpStatus.ACCEPTED);
	}

	
	@RequestMapping(path ="/provider/{provider}/date/{date}",method = RequestMethod.GET)
	public ResponseEntity<?> getReservaByProviderandDate(@PathVariable ("provider") String provider,@PathVariable ("date") String fecha){
		try {
			return new ResponseEntity<>(Service.getReservasByFecha(provider,fecha),HttpStatus.ACCEPTED);
		} catch (ParseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(path ="/provider/{provider}/date/{date}/getcupos",method = RequestMethod.GET)
	public ResponseEntity<?> getReservaByProviderandDateCupos(@PathVariable ("provider") String provider,@PathVariable ("date") String fecha){
		try {
			return new ResponseEntity<>(Service.getCuposDisponibles(provider, fecha),HttpStatus.ACCEPTED);
		} catch (ParseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}


	
	@RequestMapping(path ="/provider/{provider}",method = RequestMethod.GET)
	public ResponseEntity<?> getReservaByProvider(@PathVariable ("provider") String provider){
		return new ResponseEntity<>(Service.getReservaByProvider(provider),HttpStatus.ACCEPTED);
	}
	@RequestMapping(path ="/getEstadisticasByProvider/{provider}",method = RequestMethod.GET)
	public ResponseEntity<?> getEstadisticasByProvider(@PathVariable ("provider") String provider){
		return new ResponseEntity<>(Service.getEstadisticasByProvider(provider),HttpStatus.ACCEPTED);
	}

	@RequestMapping(path ="/getEstadisticasHora/{provider}",method = RequestMethod.GET)
	public ResponseEntity<?> getEstadisticasHora(@PathVariable ("provider") String provider){
		return new ResponseEntity<>(Service.getEstadisticasByHora(provider),HttpStatus.ACCEPTED);
	}

	@RequestMapping(path ="/activeprovider/{provider}",method = RequestMethod.GET)
	public ResponseEntity<?> getActiveReservaByProvider(@PathVariable ("provider") String provider){
		Date fecha = new Date();
		List<Reserva> reservas = Service.getReservaByProvider(provider);
		List<Reserva> actives = new ArrayList<Reserva>();
		for(Reserva r : reservas){
			if((new Date(r.getFecha().getTime() + (1000 * 60 * 60 * 24))).after(fecha)){
				actives.add(r);
			}
		}
		return new ResponseEntity<>(actives,HttpStatus.ACCEPTED);
	}

    @PostMapping("/updatereserva")
	@ResponseBody
	public ResponseEntity<?> updateReserva(@RequestBody String update) throws ServletException {
		JSONObject encargado = new JSONObject(update);
		Service.updateEncargadoReserva(encargado.get("encargado").toString(),encargado.get("reservaId").toString());
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@PostMapping("/deletereserva")
	@ResponseBody
	public ResponseEntity<?> deleteReserva(@RequestBody String delete) throws ServletException {
		JSONObject encargado = new JSONObject(delete);
		Service.deleteReserva(encargado.get("reservaId").toString());
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
