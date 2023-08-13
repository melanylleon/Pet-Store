package pet.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Tells JPA that this is a Spring Boot application. It enables auto-configuration and component scan.
@SpringBootApplication
public class PetStoreApplication {
	public static void main(String[] args) {
		//Starts Spring Boot
		SpringApplication.run(PetStoreApplication.class, args); 

	}

}
