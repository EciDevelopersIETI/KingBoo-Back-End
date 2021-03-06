package edu.eci.ieti.entities;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "reservas")
public class Reserva {
	private String reservaId;
	private Date fecha;
	private String hora;
	private String[] servicios;
	private String comentario;
	private String encargado;
	private User user;
	private Provider  provider;

	

	public Reserva(Date fecha, String[] servicios, String comentario, String encargado,User user, Provider provider) {
		this.fecha=fecha;
		SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
		this.hora = formateador.format(fecha);
		this.servicios = servicios;
		this.comentario = comentario;
		this.encargado = encargado;
		this.user = user;
		this.provider = provider;
	}



	public String getReservaId() {
		return reservaId;
	}



	public void setReservaId(String reservaId) {
		this.reservaId = reservaId;
	}



	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String[] getServicios() {
		return servicios;
	}
	public void setServicios(String[] servicios) {
		this.servicios = servicios;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getEncargado() {
		return encargado;
	}
	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	



	
}
