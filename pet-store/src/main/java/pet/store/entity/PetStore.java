package pet.store.entity;

import java.util.HashSet; 
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//JPA and Hibernate create the pet_store table and this entity's relationships with the other entities

@Entity //Tells JPA that this class is an entity that maps to a table
@Data  //Generates getters, setters, toString, equals and hashCode methods
public class PetStore {
	@Id //Tells JPA that this is the primary key.
	//Tells JPA that MySQL will automatically generate the primary key values using the database's primary key column
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip;
	private String petStorePhone;
	
	@EqualsAndHashCode.Exclude 
	@ToString.Exclude 
	//Creates the many-to-many relationship between pet stores and customers
	@ManyToMany(cascade = CascadeType.PERSIST)
	//Specifies the join table name and join table column names
	@JoinTable(name = "pet_store_customer", joinColumns = @JoinColumn(name = "pet_store_id"),
	inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//Creates the one-to-many relationship between pet store and employees. It is mapped using the petStore field in the Employee class.
	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>();
}
