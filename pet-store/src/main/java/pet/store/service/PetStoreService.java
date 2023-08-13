package pet.store.service;

import java.util.LinkedList; 
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

//This is the service class. It is used to manage the transactions that are performed in the PetStoreDao interface.

//Tells Spring that this is a service class and creates the PetStoreService bean 
@Service
public class PetStoreService {
	
	//Injects the PetStoreDao bean into this field
	@Autowired
	private PetStoreDao petStoreDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired CustomerDao customerDao;

	//Saves the created or updated pet store data in the pet_store table by calling the save() method in the PetStoreDao interface
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		
		copyPetStoreFields(petStore, petStoreData);
		
		PetStore dbPetStore = petStoreDao.save(petStore);
		
		return new PetStoreData(dbPetStore);
	}

	//Sets the values of the PetStore fields to the values of the PetStoreData fields
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}

	//If the pet store ID is null, it creates a pet store. If the ID is not null, it calls
	//the findPetStoreById() method to retrieve the pet store data from the database. 
	private PetStore findOrCreatePetStore(Long petStoreId) {
		PetStore petStore;
		
		if (Objects.isNull(petStoreId)) {
			petStore = new PetStore();
		} else {
			petStore = findPetStoreById(petStoreId);
		}
		
		return petStore;
	}

	//Retrieves pet store data from the database. It calls the findById() method that is provided by the JpaRepository interface  
	//used in the PetStoreDao interface. If the pet store ID that is passed to the method does not exist in the pet_store table, it throws an exception.
	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"PetStore with ID=" + petStoreId + " does not exist."));
	}

	//Saves the employee data in the employee table by calling the save() method in the EmployeeDao interface. 
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Employee employee = findOrCreateEmployee(petStoreId, petStoreEmployee.getEmployeeId());
		copyEmployeeFields(employee, petStoreEmployee);
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		return new PetStoreEmployee(dbEmployee);
	}
	
	//Sets the values of the Employee fields to the values of the PetStoreEmployee fields
	public void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
	}
	
	//If the employee ID is null, it creates an employee. If the ID is not null, it calls
	//the findEmployeeById() method to retrieve the employee data from the employee table. 
	public Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		Employee employee;
		
		if (Objects.isNull(employeeId)) {
			employee = new Employee();
		} else {
			employee = findEmployeeById(petStoreId, employeeId);
		}
		
		return employee;
	}
	
	//Retrieves employee data from the database. It calls the findById() method that is provided by the JpaRepository interface  
	//used in the EmployeeDao interface. If the employee ID that is passed to the method does not exist in the employee table, it throws an exception.
	//If the pet store ID associated with the employee does not equal the pet store ID passed to the method, it throws an exception.
	public Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
			.orElseThrow(() -> new NoSuchElementException(
					"Employee with ID=" + employeeId + " does not exist."));
		
		if(employee.getPetStore().getPetStoreId() == petStoreId) {
			return employee;
		} else {
			throw new IllegalArgumentException("Pet store with ID=" + petStoreId + " does not have an employee with ID=" + employeeId);
		}
	}
	
	//Saves the customer data in the customer table by calling the save() method in the CustomerDao interface. 
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Customer customer = findOrCreateCustomer(petStoreId, petStoreCustomer.getCustomerId()); 
		copyCustomerFields(customer, petStoreCustomer);
		customer.getPetStores().add(petStore);
		petStore.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new PetStoreCustomer(dbCustomer);
	}
	
	//Sets the values of the Customer fields to the values of the PetStoreCustomer fields
	public void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}
	
	//If the customer ID is null, it creates a customer. If the ID is not null, it calls
	//the findCustomerById() method to retrieve the customer data from the customer table. 
	public Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		Customer customer;
		
		if (Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(petStoreId, customerId);
		}
		
		return customer;
	}
	
	//Retrieves customer data from the database. It calls the findById() method that is provided by the JpaRepository interface  
	//used in the CustomerDao interface. If the customer ID that is passed to the method does not exist in the customer table, it throws an exception.
	//If one of the pet store IDs associated with the customer does not equal the pet store ID passed to the method, it throws an exception.
	public Customer findCustomerById(Long petStoreId, Long customerId) {
		boolean petStoreIdsMatch = false;
		
		Customer customer = customerDao.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(
					"Customer with ID=" + customerId + " does not exist."));
		
		Set<PetStore> petStores = customer.getPetStores();
		for (PetStore petStore : petStores) {
			if (petStore.getPetStoreId() == petStoreId) {
				petStoreIdsMatch = true;
			}
		}
		
		if(petStoreIdsMatch) {
			return customer;
		} else {
			throw new IllegalArgumentException("Pet store with ID=" + petStoreId + " does not have a customer with ID=" + customerId);
		}
	}

	//Retrieves all of the pet store data in the pet_store table by calling the findAll() method in the PetStoreDao interface. 
	//It does not return the employee and customer data associated with the pet stores.
	@Transactional
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> results = new LinkedList<>();
		
		for (PetStore petStore : petStores) {
			PetStoreData petStoreData = new PetStoreData(petStore);
			
			petStoreData.getEmployees().clear();
			petStoreData.getCustomers().clear();
			
			results.add(petStoreData);
		}
		
		return results;
	}
	
	//Retrieves a pet store by calling the findPetStoreById() method in the PetStoreDao interface
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		return new PetStoreData(petStore);
	}

	//Deletes a pet store by calling the delete() method in the PetStoreDao interface
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}
}
