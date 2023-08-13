package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.PetStore;

//This is the DAO interface for the pet_store table. The interface extends the JpaRepository interface that is
//used to perform CRUD operations on the pet_store table. It generates the SQL statements using its interfaces. 

public interface PetStoreDao extends JpaRepository<PetStore, Long> {


}
