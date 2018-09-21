package com.example.demo.select;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Person7_1Dao extends CrudRepository<Person7_1, Integer>{

	@Query(
			  "SELECT p.name,\n"
			+ "			CASE p.name\n" // <-----
			+ "				WHEN :name THEN 'T'\n" // <-----
			+ "				ELSE 'N'\n"
			+ "			END\n"
			+ "FROM Person7_1 p \n"				  
			)
	public List<List<String>> caseTest_Proper1(@Param("name") String name);
	
	@Query(
			  "SELECT p.name,\n"
			+ "			CASE\n" // <-----
			+ "				WHEN p.name= :name THEN 'T'\n" /// <-----
			+ "				ELSE 'N'\n"
			+ "			END\n"
			+ "FROM Person7_1 p \n"				  
			)
	public List<List<String>> caseTest_Proper2(@Param("name") String name);
}
