package pet.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//JPA and Hibernate create the employee table and this entity's relationship with pet store

@Entity //Tells JPA that this class is an entity that maps to a table
@Data //Generates getters, setters, toString, equals and hashCode methods
public class Employee {
	@Id //Tells JPA that this is the primary key.
	//Tells JPA that MySQL will automatically generate the primary key values using the database's primary key column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhone;
	private String employeeJobTitle;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	//Creates the many-to-one relationship between employees and pet store 
	@ManyToOne(cascade = CascadeType.ALL)
	//Specifies the foreign key column in the employee table.
	@JoinColumn(name = "pet_store_id")
	private PetStore petStore;
}
