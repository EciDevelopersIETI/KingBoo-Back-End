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

import javax.xml.ws.Service;

@SpringBootApplication
@ComponentScan("edu.eci.ieti")
@EntityScan("edu.eci.ieti.entities")
public class ApiKingBoo {

    public static void main(String[] args) {
        SpringApplication.run(ApiKingBoo.class, args);
    }



     @Bean public CommandLineRunner demo(servicesKingBooImpl service) {
     return (args) -> {
 /**
     String servicios[] = {"Corte de Cabello","Manicura","Barba","Depilacion"};
     Provider provider = new Provider("Luis Shop",123,"Calle 14#5c-19",servicios,"Carlos es mi amigo",5);
     User user1 = new User("Luis","luis@mail.com","ieti123","3107704065",provider);
     service.saveUser(user1);

     String servicios2[] = {"Manicura", "Depilacion"};
     Provider provider2 = new Provider("Charlis Styles",123,"Calle 69#5c-19",servicios2,"Te depilamos completico",15);
     User user2 = new User("Charlis","carlitos@mail.com","ieti123","3125465454", provider2);
     service.saveUser(user2);

     User user3 = new User("Fernando","fernando@mail.com","ieti123","3152211212",new Provider());
     service.saveUser(user3);

     User user4 = new User("Zeus","zeus@mail.com","ieti123","64521215",new Provider());
     service.saveUser(user4);
*/
      //service.updateEncargadoReserva("Andres","Luis Shop2020-09-3011:35");
         String serviciosx[] = {"Manicura", "Depilacion"};
         Provider providerx = new Provider("Hugos cute haircuts",45678946,"Calle 69#5c-19",serviciosx,"Hola, ven a cortarte el pelo y la barba con nostros xd",15);
         User userprov = new User("hugo","hugo@mail.com","ieti123","123456789",providerx);

         service.updateProvider(userprov);


     };
     }


}
