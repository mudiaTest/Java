package com.my.pl.db1.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.my.pl.db1.domain.Test6;

public interface Test6Dao extends CrudRepository<Test6, Long> {
	
	@Query("SELECT t FROM Test6 t WHERE t.id=?1")
	public Optional<Test6> getById(Long id);

	@EntityGraph(value="graph.TestSix.none", type = EntityGraphType.LOAD)
	@Query("SELECT t FROM Test6 t WHERE t.id=?1")
	public Optional<Test6> getByIdNoneLoad(Long id);
	
	@EntityGraph(value="graph.TestSix.none", type = EntityGraphType.FETCH)
	@Query("SELECT t FROM Test6 t WHERE t.id=?1")
	public Optional<Test6> getByIdNoneFetch(Long id);
	
	@EntityGraph(value="graph.TestSix.lista", type = EntityGraphType.LOAD)
	@Query("SELECT t FROM Test6 t WHERE t.id=?1")
	public Optional<Test6> getByIdListaLoad(Long id);
	
	@EntityGraph(value="graph.TestSix.lista", type = EntityGraphType.FETCH)
	@Query("SELECT t FROM Test6 t WHERE t.id=?1")
	public Optional<Test6> getByIdListaFetch(Long id);
	
	@EntityGraph(value="graph.TestSix.listy", type = EntityGraphType.LOAD)
	@Query("SELECT t FROM Test6 t WHERE t.id=?1")
	public Optional<Test6> getByIdListyLoad(Long id);
	
	@EntityGraph(value="graph.TestSix.listy", type = EntityGraphType.FETCH)
	@Query("SELECT t FROM Test6 t WHERE t.id=?1")
	public Optional<Test6> getByIdListyFetch(Long id);
}
