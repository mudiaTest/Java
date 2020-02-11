package hibernate03.dao;

import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.StoredProcedureParameter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hibernate03.domain.SubObj;
import hibernate03.domain.Test1;
import hibernate03.domain.Test3;
import hibernate03.domain.Test4;
import hibernate03.domain.Test5_1;

@Repository
public interface Test1Dao extends CrudRepository<Test1, Long>, CustomDao {

  /* "as v3" jest dla uproszczenia
   * "getint_val1" DZIA£A. 
   * "getInt_val1" NIE DZIA£A. 
   */

  @Query(value="SELECT t3.int_val1 as v3, t2.int_val FROM Test3 t3 LEFT JOIN Test2 t2 ON t3.t2_id = t2.id", nativeQuery = true)
  public List<SubObj> getSubObj(); 
  
  @Query(value="SELECT t3.intVal1 FROM Test3 t3 JOIN t3.t2 t2 ")
  public Page<Integer> getPaged(Pageable pageable); 
  
  //Brak countQuery powoduje automatyczne utworzenie odpowiedniega zapytania, ale 
  //nazwa metody musi byæ odpowiednia, a to nie wiem jak uzyskaæ
//  @Query(value="SELECT t3 FROM Test3 t3 LEFT JOIN FETCH t3.t2")
//  public Page<Test3> findTest3(Pageable pageable); 
  
  @Query(  
      value="SELECT t4 FROM Test4 t4 LEFT JOIN FETCH t4.t2"
      //countQuery jest niezbêdne, jeœli w zapytaniu wystêpuje FETCH inaczej wystêpuje b³¹d
      //query specified join fetching, but the owner of the fetched association was not present in the select list
      ,countQuery="SELECT count(t4) FROM Test4 t4 LEFT JOIN t4.t2"
      )
  public Page<Test4> getPagedFetch(Pageable pageable);
  
  @Query("SELECT t3.intVal1 FROM Test3 t3 JOIN t3.t2 t2 ")
  public List<Integer> getSorted(Sort sort); 
  
  @Query("SELECT t3.intVal1 FROM Test3 t3")
  public List<Integer> getSorted2(Sort sort); 
  
  @Query("SELECT t3.intVal1 FROM Test3 t3 JOIN t3.t2 t2 ")
  public Slice<Integer> getSliced(Pageable pageable); 
  
  @Query("SELECT t2.intVal FROM Test2 t2")
  public Slice<Integer> getSlicedTest2(Pageable pageable); 
  
  @Modifying
  @Query("DELETE FROM Test1 WHERE id=?1")
  public void bulkTest1Delete(Long id);
  
  @Procedure
  public List<Object> inctest71(int i);
  
  @Procedure
  public List<Object> inctest72(int i);
  
  @Procedure
  public List<Object> inctest73(int i);
  
  @Procedure
  public Integer p61(@Param("i") Integer i);

  @Procedure
  public Integer p62(@Param("i") Integer i);
  
  @Procedure
  public List<Object[]> p72(@Param("i") Integer i);
  
  @Procedure(value = "public.p61", outputParameterName = "r")
  Integer p61inout(@Param("i") Integer i);
}
