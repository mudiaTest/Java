package hibernate06.dao;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hibernate06.domain.Person;

@Repository
@Transactional
public interface Person0601Dao extends JpaRepository<Person, Integer>, Person0601CustomDao{

  @Query("Select p"
      + " From Person p"
      + " Where p.id = ?1")
  public Person getById2(Integer id);
  
  @Query("Select p.age, p.name"
      + " From Person p"
      + " Where p.id = ?1")
  public Tuple getTupleById(Integer id);
  
  @Query("Select p"
      + " From Person p")
  public List<Person> getJpaList();
  
  @Query("Select p"
      + " From Person p")
  public Stream<Person> getJpaStream();
}
