# Pet Stores
![GitHub contributors](https://img.shields.io/github/contributors/melanylleon/Pet-Store)
![GitHub commit activity (branch)](https://img.shields.io/github/commit-activity/t/melanylleon/Pet-Store)
![GitHub issues](https://img.shields.io/github/issues/melanylleon/Pet-Store)
![GitHub](https://img.shields.io/github/license/melanylleon/Pet-Store)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)

##  :star2: About the Project
This is a program that lists pet store information and includes the employee and customer information. 

### :space_invader: Tech Stack
- Java
- MySQL
- Spring Boot

### :dart: Features
- Lists all of the pet stores along with the employees that work for each store and the customers that shop at each store
- The user can retrieve a city by providing the city id

## :toolbox: Getting Started

### :gear: Installation
- Lombok is a Java library that needs to be installed to run the project. There are different installation instructions depending on the tool 
that you are using to run the project. Here is a [link](https://projectlombok.org/setup/) to the installation instructions.

- Any database management tool, such as [DBeaver](https://github.com/advanced-rest-client/arc-electron/releases), can be used along with this project. 

- Any API testing tool, such as the Advanced REST Client (ARC), can be used along with this project.
Click on this [link](https://github.com/advanced-rest-client/arc-electron/releases) to install an ARC package for your specific operating system.
For MacOS, install the arc-macos.dmg package. For Windows, install the arc-setup.exe package. 

### :running: Run Locally
Clone the project

``` bash
git clone https://github.com/melanylleon/Pet-Store.git
```

Run on the IDE


## :eyes: Usage
This project lists a pet store’s data along with the employee and customer data. In this project, you can create, read, update, and delete a pet store. Employee and customer data can also be inserted into the database and a list of all the pet stores can be retrieved as well. 

**Finds a customer in the database and verifies that the customer shops at the pet store with the pet store id that was provided**
```java
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
```
</br>

**Creates or updates the employee in the database** 
```java
public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
Employee employee = findOrCreateEmployee(petStoreId, petStoreEmployee.getEmployeeId());

		copyEmployeeFields(employee, petStoreEmployee);
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		return new PetStoreEmployee(dbEmployee);
	}

```
</br>

The program also has a global error handler class with a method that prints a custom message when a NoSuchElementException is thrown. 

**Global error handler method** 
```java
public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
		Map<String, String> exception = new HashMap<String, String>();
		String message = ex.toString();
		log.error("Exception: {}", message);
		
		exception.put("message", message);
		
		return exception; 
	}
```

## :compass: Roadmap

* [ ] Add a feature that keeps track of the pet stores’ inventory and keeps track of the most sold items
* [ ] Add a feature to keep a record of the sales and returns of the pet store products 

## :wave: Contributing
Please feel free to contribute to the project!  

Please see the `CONTRIBUTING.md` file for more information.

## :warning: License
Please see the `LICENSE.txt` file for more information.

# :handshake: Contact

Melany Landaverde Leon - melany.leon0199@gmail.com

Project Link: [https://github.com/melanylleon/Pet-Store.git]( https://github.com/melanylleon/Pet-Store.git)




