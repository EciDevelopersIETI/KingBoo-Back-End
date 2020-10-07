package edu.eci.ieti.persistence;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.eci.ieti.entities.Reserva;
import edu.eci.ieti.entities.User;

public interface ReservaRepository extends MongoRepository<Reserva, String> {
	Reserva findByUser(User user);
	Reserva findByReservaId(String id);
	void deleteByReservaId(String id);


}
