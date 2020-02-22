package hibernate02.inheritance.doa;

import org.springframework.stereotype.Repository;

import hibernate02.inheritance.dto.Person;
import hibernate02.inheritance.dto.PersonInWork;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface Person_01Doa extends CrudRepository<PersonInWork, Integer>{

}
