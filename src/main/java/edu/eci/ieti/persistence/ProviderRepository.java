package edu.eci.ieti.persistence;

import edu.eci.ieti.entities.Provider;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProviderRepository extends MongoRepository<Provider, String>{
    Provider findByNit(long nit);
    Provider findByProviderName(String providerName);
   
}
