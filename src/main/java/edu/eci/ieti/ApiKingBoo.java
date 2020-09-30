package edu.eci.ieti;





import java.util.Date;

import edu.eci.ieti.entities.Provider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import edu.eci.ieti.entities.Reserva;
import edu.eci.ieti.entities.User;
import edu.eci.ieti.persistence.UserRepository;
import edu.eci.ieti.service.servicesKingBooImpl;

@SpringBootApplication
@ComponentScan("edu.eci.ieti")
@EntityScan("edu.eci.ieti.entities")
public class ApiKingBoo
{

    public static void main( String[] args )
    {
    	SpringApplication.run(ApiKingBoo.class, args);
    }
    

    
    @Bean
    public CommandLineRunner demo(servicesKingBooImpl service) {
      return (args) -> {
    	
    	  	  String servicios[] = {"corte","unas"};
            Provider provider = new Provider("Luis Shop",123,"Calle 14#5c-19",servicios,"Carlos es mi amigo");
            User user1 = new User("Luis","luis@mail.com","ieti123","3107704065",provider);
            service.saveUser(user1);

            User user2 = new User("Charlis","carlitos@mail.com","ieti123","3125465454",new Provider());
            service.saveUser(user2);

            User user3 = new User("Fernando","fernando@mail.com","ieti123","3152211212",new Provider());
            service.saveUser(user3);
    	  	
    	  	
        
        
  
      };   
      
    }

    
}
