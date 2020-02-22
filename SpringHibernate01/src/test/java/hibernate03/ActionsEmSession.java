package hibernate03;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import hibernate03.dao.Test1Dao;
import hibernate03.domain.SubClass1;
import hibernate03.domain.Test1;
import hibernate03.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionsEmSession {

  //@Test
  public void contextLoads() {
  }
  
  @Autowired
  EntityManager em;
  @Autowired
  NewTransactionWrapper ntw;
  
  @Autowired
  Test1Dao t1d;
  
  private Test1 getTest1Obj() {
    Test1 t1 = new Test1();
    t1.setId(1);
    t1.setIntVal1(11);
    SubClass1 so1 = new SubClass1();
    t1.setSo1(so1);
    so1.setSubIntVal1(11);
    so1.setSubStVal1("a");
    return t1;
  }
  
    
  //Testowanie 
  @Test
  //@Transactional
  @Commit
  public void doublePersistTest() {
    try {    
      ntw.inNewTrans(() ->{
        Test1 t1 = getTest1Obj();
        ntw.inTrans(()->
        {
          em.persist(t1); //zapisze obiekt do cache              
          em.flush(); //zrzuci cache do bazy
          em.detach(t1); //odłaczy obiet od em
          t1.setIntVal1(99); //odłączony obiekt nie wpłynie na cache
          em.persist(t1); //PK VIOLATION                    
          int t = 0;
        });
      });
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
  //Testowanie 
  //@Test
  //@Transactional
  @Commit
  public void mergeTest() {
    try {    
      ntw.inNewTrans(() ->{
        Test1 t1 = getTest1Obj();
        ntw.inTrans(()->
        {
          em.persist(t1); //zapisze obiekt do cache              
          em.flush(); //zrzuci cache do bazy
          em.detach(t1); //odłaczy obiet od em
        });
        t1.setIntVal1(99); //odłączony obiekt nie wpłynie na cache
        ntw.inTrans(()->{
          em.merge(t1); //podłączy obiekt do em (wykona SELECT)
        });
        int t = 0;
        em.flush(); //wykona UPDATE obieky w DB
        t = 0;
      });
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
  
  @Test
//  @Transactional
  @Commit
  public void persistTest() {
    try {    
//      ntw.inNewTrans(() ->{
      ntw.inNewTrans(()->
      {
        Test1 t1 = getTest1Obj();
        em.persist(t1);        
        t1.setIntVal1(91);                
        Test1 t2 = em.find(Test1.class, t1.getId());
        t2.setIntVal1(92);                
        int t = 0;
        
        t = 0;
      });
      int t = 0;
//      });
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
  @Test
//  @Transactional
  @Commit
  public void persistErrTest() {
    try {    
//      ntw.inNewTrans(() ->{
        Test1 t1 = getTest1Obj();
        ntw.inNewTrans(()->
        {
          em.persist(t1);        
          em.flush();
          //em.detach(t1);
        });
        ntw.inNewTrans(()->
        {
          t1.setIntVal1(91);
          em.merge(t1);
          em.flush();
        });
        ntw.inNewTrans(()->{
          em.merge(t1);
          Test1 t2 = em.find(Test1.class, t1.getId());
          //em.detach(t2);
          t2.setIntVal1(99);        
          em.persist(t2);
          int t = 0;
        });
        int t = 0;
//      });
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
  @Test
//  @Transactional
  @Commit
  public void saveTest() {
    try {    
//      ntw.inNewTrans(() ->{
      Test1 t1 = getTest1Obj();
      ntw.inNewTrans(()->
      {
        em.persist(t1);        
        em.flush();
        //em.detach(t1);
      });
      t1.setIntVal1(99);
      ntw.inNewTrans(()->{
        Session s = em.unwrap(Session.class);
        Test1 t2 = em.find(Test1.class, t1.getId());
        em.detach(t2);
        t2.setIntVal1(88); 
        //s.save(t2); //PK VIOLATION
        s.saveOrUpdate(t2);
        int t = 0;
      });
      int t = 0;
//      });
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
  @Test
//  @Transactional
  @Commit
  public void updateTest() {
    try {    
//      ntw.inNewTrans(() ->{
      Test1 t1 = getTest1Obj();
      ntw.inNewTrans(()->
      {
        em.persist(t1);        
        em.flush();
        //em.detach(t1);
      });
      t1.setIntVal1(99);
      ntw.inNewTrans(()->{
        Session s = em.unwrap(Session.class);
        Test1 t2 = em.find(Test1.class, t1.getId());
        em.detach(t2);
        //t2.setIntVal1(88);        
        s.update(t2);
        int t = 0;
      });
      int t = 0;
//      });
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  

}
