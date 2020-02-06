package com.example.demo.inheritance.doa;

import org.springframework.stereotype.Repository;

import com.example.demo.inheritance.dto.Person;
import com.example.demo.inheritance.dto.Worker;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface WorkerDoa extends CrudRepository<Worker, Integer>{

}
