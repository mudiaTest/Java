package hibernate06;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.Tuple;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import hibernate06.dao.Person0601Dao;
import hibernate06.domain.Address;
import hibernate06.domain.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test01 {
  
  @Autowired
  private Person0601Dao r;
  
//  @Test
  @Transactional
  @Rollback(false)
  public void person1Init() {
    r.deleteAll();
    Person p = new Person();
    p.id = 1; p.name = "os1"; p.age = 11;//*, new Address(11, "homestreet"), new Address(12, "homestreet")*/);    
    Person p2 = r.save(p);
    p.id = 2; p.name = "os2"; p.age = 12;
    p2 = r.save(p);
    p.id = 3; p.name = "os3"; p.age = 11;
    p2 = r.save(p);    
  }
  
//  @Test
  @Transactional
  @Rollback(false)
  public void personSimpleTest() {
    Person res ;
    res = r.getById2(1);
    
    Tuple t = r.getTupleById(1);
    Integer age = t.get(0, Integer.class);
    String name = t.get(1, String.class);
    System.out.println("");
    
    // Lista z JPA
    List<Person> lst = r.getJpaList();
    System.out.println("");
  }
  
//  @Test
  @Transactional
  @Rollback(false)
  public void personJPATest() {  
    // Stream z JPA
    Stream<Person> str = r.getJpaStream();
    Iterator<Person> it = str.iterator();
    while(it.hasNext())
    {
      Person p = it.next();
      System.out.println("");
    }
    System.out.println("");
  }
  
//  @Test
  @Transactional
  @Rollback(false)
  public void personCustomListTest() {
    // Lista custom
    List<String> names = r.getNameList();
    System.out.println("");
    // Lista custom
    List<Person> people = r.getPersonList();
    System.out.println("");
  }
  
  @Test
  @Transactional
  @Rollback(false)
  public void personCustomStreamTest() {
    // Lista custom
    Stream<String> names = r.getNameStream();
    Iterator<String> itS = names.iterator();
    while(itS.hasNext())
    {
      String p = itS.next();
      System.out.println("");
    }
    System.out.println("");
    // Lista custom
    Stream<Person> people = r.getPersonStream();
    Iterator<Person> itP = people.iterator();
    while(itP.hasNext())
    {
      Person p = itP.next();
      System.out.println("");
    }
    System.out.println("");
  }
}
