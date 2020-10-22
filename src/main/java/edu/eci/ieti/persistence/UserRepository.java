package edu.eci.ieti.persistence;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.eci.ieti.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
	User findByEmail(String email);
	void deleteUserByEmail(String email);
}
