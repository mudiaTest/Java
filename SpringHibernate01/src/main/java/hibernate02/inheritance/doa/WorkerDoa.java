package hibernate02.inheritance.doa;

import org.springframework.stereotype.Repository;

import hibernate02.inheritance.dto.Person;
import hibernate02.inheritance.dto.Worker;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface WorkerDoa extends CrudRepository<Worker, Integer>{

}
