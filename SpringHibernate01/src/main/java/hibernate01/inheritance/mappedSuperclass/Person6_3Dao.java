package hibernate01.inheritance.mappedSuperclass;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hibernate01.inheritance.entityInheritance.Person6_2Gen;

@Repository
public interface Person6_3Dao extends CrudRepository<Person6_2Gen, Integer>{

}
