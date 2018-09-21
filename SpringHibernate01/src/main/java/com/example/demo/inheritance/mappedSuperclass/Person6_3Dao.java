package com.example.demo.inheritance.mappedSuperclass;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.inheritance.entityInheritance.Person6_2Gen;

@Repository
public interface Person6_3Dao extends CrudRepository<Person6_2Gen, Integer>{

}
