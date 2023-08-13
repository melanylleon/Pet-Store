package pet.store.controller;

import java.util.List; 
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

//This is the pet store controller class. The HTTP requests are mapped to this class and responses 
//are sent back to the client.

//Tells Spring that this a controller and that it takes and returns JSON
@RestController 
//Maps the URIs starting with /pet_store onto the PetStoreController. 
//All of the URIs mapped to the methods below will start with /pet_store.
@RequestMapping("/pet_store") 
@Slf4j
public class PetStoreController {
	//Injects the PetStoreService bean into this field
	@Autowired
	private PetStoreService petStoreService;
	
	//Maps POST requests to the method. The requests are sent to /pet_store. 
	//The method creates/inserts a pet store's data into the database by calling the savePetStore() method in the PetStoreService class
	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Creating the pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	//Maps PUT requests to the method. The requests are sent to /pet_store/{petStoreId}.
	//The method updates a pet store's data in the database by calling the savePetStore() method in the PetStoreService class
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId);
		log.info("Updating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	//Maps a POST request to the method. The requests are sent to /pet_store/{petStoreId}/employee.
	//The method inserts an employee's data into the database by calling the saveEmployee() method in the PetStoreService class
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee insertEmployee(@PathVariable Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Creating employee {} for pet store with ID={}", petStoreEmployee, petStoreId);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	//Maps a POST request to the method. The requests are sent to /pet_store/{petStoreId}/customer.
	//The method inserts a customer's data into the database by calling the saveCustomer() method in the PetStoreService class
	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer insertCustomer(@PathVariable Long petStoreId, @RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Creating customer {} for pet store with ID={}", petStoreCustomer, petStoreId);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	//Maps a GET request to the method. The requests are sent to /pet_store.
	//The method retrieves all pet store data from the database by calling the retrieveAllPetStores() method in the PetStoreService class
	@GetMapping()
	public List<PetStoreData> retrieveAllPetStores() {
		log.info("Retrieve all pet stores.");
		return petStoreService.retrieveAllPetStores();
	}
	
	//Maps a GET request to the method. The requests are sent to /pet_store/{petStoreId}. 
	//The method retrieves a pet store's data from the database by calling the retrievePetStoreById() method in the PetStoreService class
	@GetMapping("/{petStoreId}")
	public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
		log.info("Retrieving pet store by ID={}", petStoreId);
		return petStoreService.retrievePetStoreById(petStoreId);
	}
	
	//Maps a DELETE request to the method. The requests are sent to /pet_store/{petStoreId}.
	//The method deletes a pet store's data from the database by calling the deletePetStoreById() method in the PetStoreService class
	@DeleteMapping("/{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
		log.info("Deleting pet store with ID={}", petStoreId);
		petStoreService.deletePetStoreById(petStoreId);
		
		return Map.of("message", "Deletion of pet store with ID=" + petStoreId 
				+ " was successful.");
	}
}
