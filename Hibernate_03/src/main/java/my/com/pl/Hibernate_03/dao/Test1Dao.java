package my.com.pl.Hibernate_03.dao;

import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import my.com.pl.Hibernate_03.domain.SubObj;
import my.com.pl.Hibernate_03.domain.Test1;
import my.com.pl.Hibernate_03.domain.Test4;
import my.com.pl.Hibernate_03.domain.Test5_1;

@Repository
public interface Test1Dao extends CrudRepository<Test1, Long>, CustomDao {

	/* "as v3" jest istotne, bo bez tego H oddaje nazwy, których nie moge rozpoznaæ 
	 * i zbudowanie  metody get w interface jest niemo¿liwe. "getInt_val1" nie dzia³a. 
	 */

	@Query(value="SELECT t3.int_val1 as v3, t2.int_val as v2 FROM Test3 t3 LEFT JOIN Test2 t2 ON t3.t2_id = t2.id", nativeQuery = true)
	public List<SubObj> getSubObj(); 
	
	@Query(value="SELECT t3.intVal1 FROM Test3 t3 JOIN t3.t2 t2 ")
	public Page<Integer> getPaged(Pageable pageable); 
	
	@Query(value="SELECT t4 FROM Test4 t4 LEFT JOIN FETCH t4.t2",
			countQuery="SELECT count(t4) FROM Test4 t4 LEFT JOIN t4.t2")
	public Page<Test4> getPagedFetch(Pageable pageable);
	
	@Query("SELECT t3.intVal1 FROM Test3 t3 JOIN t3.t2 t2 ")
	public List<Integer> getSorted(Sort sort); 
	
	@Query("SELECT t3.intVal1 FROM Test3 t3 JOIN t3.t2 t2 ")
	public Slice<Integer> getSliced(Pageable pageable); 
	
	@Modifying
	@Query("DELETE FROM Test1 WHERE id=?1")
	public void bulkTest1Delete(Long id);
}
