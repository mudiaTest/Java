package com.example.demo.oneManyEmbdedded;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Person1Dao extends CrudRepository<Person1, Integer>{

}
