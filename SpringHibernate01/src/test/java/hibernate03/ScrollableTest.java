package hibernate03;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.stat.SessionStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import hibernate03.dao.Test1Dao;
import hibernate03.domain.SubClass1;
import hibernate03.domain.Test1;
import hibernate03.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScrollableTest {

  //@Test
  public void contextLoads() {
  }
  
  @Autowired
  EntityManager em;
  @Autowired
  NewTransactionWrapper ntw;
  @Autowired
  private EntityManagerFactory emf;
//  @Autowired
//  private HibernateTransactionManager htm;
  
  @Autowired
  Test1Dao t1d;
  
  @Autowired
  private ApplicationContext ac;

  private void fillTest1UsingRepo() {
      for(int i = 1; i<10; i++) {
        Test1 t1;
        t1 = new Test1();
        t1.setId(i);
        t1.setIntVal1(11);
        SubClass1 so1 = new SubClass1();
        t1.setSo1(so1);
        so1.setSubIntVal1(10+i);
        so1.setSubStVal1("a");
        
        Test1 t0;
        t0 = new Test1();
        t0.setId(i);
        t0.setIntVal1(11);
        SubClass1 so0 = new SubClass1();
        t0.setSo1(so0);
        so0.setSubIntVal1(10+i);
        so0.setSubStVal1("a");
        
        //Pobiera Session od repozytori�w
        Session s = em.unwrap(Session.class);
        /*
         * R�nica pomi�dzy "s.persist" a "t1d.save".
         * Save zrobi merge a nie persist. Jest to prawdopodobnie 
         * spowodowane tym, �e r�cznie ustalili�my warto�� id.
         * Z tego powodu (prawdopodobnie) s.contains(t1) da false i dopiero
         */
        //s.persist(t1);
        t1d.save(t1);
        s.flush();
        /*Pobieranie klucza z cache i sprawdzanie jego warto�ci*/
          SessionStatistics ss = s.getStatistics();
          Set<EntityKey> sk = ss.getEntityKeys();
          Iterator<EntityKey> ite = sk.iterator();
          EntityKey k = ite.next();
          //String className = k.getEntityName(); //.equals( Test1.class.getName() )
          //k.getIdentifier().toString().equals(Long.valueOf(t0.getId()).toString());
          int t = 0;

        boolean czyCache = s.contains(t1);//true je�li s.persist(t1) / false dla t1d.save(t1)
        Optional<Test1> t2 = t1d.findById(Long.valueOf(t1.getId()));
        //badanie cache L2        
        //boolean czyCache2 = s3.getSessionFactory().getCache().containsEntity( Test1.class, t1.getId() );
        czyCache = s.contains(t0);//false
        czyCache = s.contains(t1);//false
        czyCache = s.contains(t2.get());//true/false w zale�no�ci od powy�szej uwagi odno�nie zapisu        
        t = 0;
      }
  }
  
  //Testowanie stateless session dla em
  @Test  
  public void emTest() {
    try {
      SessionFactory sf = emf.unwrap(SessionFactory.class);
      ntw.inNewTrans(()->{
          fillTest1UsingRepo();
      });
      /*
       * Sesja pobrana w tym miejscu b�dzie mia�a obiekty w cache, o ile 
       * jest to ta sama transakcja, kt�ra je pobra�a/wstawi�a do DB.
       */
//      Session s = em.unwrap(Session.class);
//      SessionStatistics ss = s.getStatistics();
//      Set<EntityKey> sk = ss.getEntityKeys();
//      EntityKey k = sk.iterator().next();
      
      StatelessSession s2= sf.openStatelessSession();
      ScrollableResults r;
      //To query zostanie odpalone tylko 1 raz, ale wyniki b�da pobierane pojedynczo
      r = s2.createQuery("SELECT t1 FROM Test1 t1").scroll(ScrollMode.FORWARD_ONLY);
        while (r.next()) {          
          Test1 t1 = (Test1) r.get()[0];
          int t = 0;
        }
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }
  }

}
