package hibernate01.select;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Person7_2Dao extends CrudRepository<Person7_2, Integer>{
  
  @Query(
        "SELECT p \n"
      + "FROM Person7_2 p \n"
      + "WHERE p.age1 = :age")
  public List<Person7_2> getByAge1(@Param("age") int age);
  
  @Query(
      "SELECT p \n"
      + "FROM Person7_2 p \n"
      + "WHERE p.age2 = :age")
  public Person7_2 getByAge2(@Param("age") Integer age);
  
  @Query(
      "SELECT p \n"
      + "FROM Person7_2 p \n"
      + "WHERE p.age3 = :age")
  public Person7_2 getByAge3(@Param("age") Integer age);
  
  @Query(
      "SELECT p, p.name \n"
      + "FROM Person7_2 p \n")
  public List<Object[]> selectObjAndField();
  
  @Query(
      "SELECT MAX(p.age2) \n"
      + "FROM Person7_2 p \n")
  public Integer selectMax();
  
  @Query(
      "SELECT p \n"
      + "FROM Person7_2 p \n"
      + "WHERE MAX(p.age2)=p.age2")
  public Person7_2 selectMaxWhereErr();
  
  @Query(
      "SELECT p \n"
          + "FROM Person7_2 p \n"
          + "WHERE (Select MAX(p.age2) FROM Person7_2 p) = p.age2")
  public Person7_2 selectMaxWhereOk();
  
//  @Query(
//      "SELECT MAX(p.age2) \n"
//      + "FROM Person7_2 p \n"
//      + "HAVING(MAX(p.age2)>2)")
//  public Integer selectMaxHavingTrue();
//  
//  @Query(
//      "SELECT MAX(p.age2) \n"
//      + "FROM Person7_2 p \n"
//      + "HAVING(MAX(p.age2)>99)")
//  public Integer selectMaxHavingFalse();
//  
  
}
