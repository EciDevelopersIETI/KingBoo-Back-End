package edu.eci.ieti.controllers;


import edu.eci.ieti.entities.Provider;
import edu.eci.ieti.entities.User;
import edu.eci.ieti.service.servicesKingBooImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("provider")
public class ProviderController {
    @Autowired
    private servicesKingBooImpl Service;
    public ProviderController(){
    }
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllProviders(){
        try{
            List<Provider> providers = Service.getAllProviders();
            return new ResponseEntity<>(providers, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path ="/{name}",method = RequestMethod.GET)
    public ResponseEntity<?> getUsuarioByNick(@PathVariable ("name") String name){
            return new ResponseEntity<>(Service.getProviderByName(name),HttpStatus.ACCEPTED);

    }


}
