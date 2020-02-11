package hibernate03;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hibernate03.dao.Test1Dao;
import hibernate03.domain.SubClass1;
import hibernate03.domain.Test1;
import hibernate03.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainEventTest {

  //@Test
  public void contextLoads() {
  }
  
  @Autowired
  EntityManager em;
  @Autowired
  NewTransactionWrapper ntw;
  
  @Autowired
  Test1Dao t1d;
  
  private void fillTest1UsingRepo() {
    Test1 t1 = new Test1();
    t1.setId(1);
    t1.setIntVal1(11);
    SubClass1 so1 = new SubClass1();
    t1.setSo1(so1);
    so1.setSubIntVal1(11);
    so1.setSubStVal1("a");
    
    /*
     * DomainEvents dzia�aja tylko gdy korzystany z Repozytori�w. 
     * Je�li zapis jest wykonywany bezpo�rednio przez EM, to nie beda uruchamiane
     */
    t1d.save(t1);
    //em.persist(t1);
  }
  
  //Testowanie domainEvents
  @Test
  public void joinInterfaceTest() {
    try {
      ntw.inNewTrans(()->fillTest1UsingRepo());  
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }
  }

}
