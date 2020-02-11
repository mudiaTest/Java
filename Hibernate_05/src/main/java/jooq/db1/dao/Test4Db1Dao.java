package jooq.db1.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jooq.db1.domain.Test1;
import jooq.db1.domain.Test4;

public interface Test4Db1Dao extends CrudRepository<Test4, Long>{

  @Query(
      "SELECT t4 \n" +
      "FROM Test4 t4 LEFT JOIN FETCH t4.subObjSet \n" + 
      "WHERE t4.id = :id"
      )
  public Test4 getTest4Test41ById(@Param("id") Long id);
}
