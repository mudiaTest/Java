package com.my.pl.dao;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;

import com.my.pl.domain.Test1;

public interface Test1CustomDao extends Test1GenDao<Test1, Integer>{
	@Query("SELECT a FROM Test1 a")
	public List<Test1> getAll();
	
	@Query("SELECT a FROM Test1 a")
	public Stream<Test1> getAllStream();
	
	public List<Test1> findByIntVal1(Integer ntVal1);
}
