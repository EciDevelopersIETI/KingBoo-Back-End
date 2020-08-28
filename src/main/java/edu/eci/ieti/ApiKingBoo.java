package edu.eci.ieti;


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
    

    
    /**
    @Bean
    public CommandLineRunner demo(UserRepository service) {
      return (args) -> {
    	
    	  	User user =service.findByEmail("f9830@hotmail.com");
    	  	System.out.println(user.getUserName());
    	  	
    	  	
        
        
  
      };   
      
    }
    */
}
