package my.com.pl.Hibernate_03.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import my.com.pl.Hibernate_03.domain.Test2;

public interface Test2Dao extends CrudRepository<Test2, Long> {
	
}
