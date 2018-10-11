package com.my.pl.db1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.my.pl.db1.domain.Test71;

public interface Test71Dao extends CrudRepository<Test71, Long>{

	@Query("Select t71 From Test71 t71 LEFT JOIN FETCH t71.set")
	public List<Test71> getFullById(Long id);
}
