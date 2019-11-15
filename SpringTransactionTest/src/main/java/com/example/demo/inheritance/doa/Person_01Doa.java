package com.example.demo.inheritance.doa;

import org.springframework.stereotype.Repository;

import com.example.demo.inheritance.dto.Person;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface Person_01Doa extends CrudRepository<Person, Integer> {

}
