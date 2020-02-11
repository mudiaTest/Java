package jooq;

import static com.my.pl.jooq.db2.tables.Test2.TEST2;
import static jooq.jooq.db1.tables.Test1.TEST1;

import javax.persistence.EntityManager;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DefaultDSLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import jooq.config.PersistenceContextDb1;
import jooq.config.PersistenceContextDb2;
import jooq.db1.dao.Test1Db1Dao;
import jooq.db1.domain.Test1;
import jooq.db2.dao.Test2Dao;
import jooq.db2.domain.Test2;
import jooq.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class) 
@ContextConfiguration(classes = { PersistenceContextDb1.class, PersistenceContextDb2.class })
public class Spring_Jooq_Hibernate_InOneTransactionTest {
  
  @Autowired
  Test1Db1Dao t1d;
  @Autowired
  Test2Dao t2d;
  @Autowired
  Environment env;  
  @Autowired
  NewTransactionWrapper ntw;
  
  @Autowired
  @Qualifier("entityManagerDb1")
  EntityManager emDb1;  
  @Autowired
  @Qualifier("dslDb1")
  DefaultDSLContext dslDb1;
  
  @Autowired
  @Qualifier("entityManagerDb2")
  EntityManager emDb2;
  @Autowired
  @Qualifier("dslDb2")
  DefaultDSLContext dslDb2;
  
  private void fillTest1() {
    Test1 t1 = new Test1();
    t1.setId(1);
    t1.setIntVal1(11);
    
    Test2 t2 = new Test2();
    t2.setId(2);
    t2.setStVal1("a");
    
    t1d.save(t1);
    t2d.save(t2);
  }

  @Test
  /*
   * @Transactional i @Transactional("transactionManagerDb1") są równznaczne, bo transactionManagerDb1 jest @Primary
   */
  @Transactional("transactionManagerDb1")
  @Commit
  public void contextLoads() {
    ntw.inTrans(()->fillTest1());
    /*
     * Wymuszamy zapis do bazy, bo inaczej obekt może zostać w PC i Jooq go nie odnajdzie
     * Może to oczywiście spowodować spadek wydajności, bo próba pobrania przez H tego obiektu do 
     * dalszych zmian, będzie pociągala za soba konieczność sunchronizacji (Selecta) z DB.
     */
    emDb1.flush();
    /*
     * Poniższe wybychnie, bo brak akrywnej transakcji.
     * Jest to logiczne, bo @Transactional odnosi się do db1, więc t2d.save 
     * nie przejmie tej transakcji, tylko otworzy własną i potem ją zamknie.
     */
    //emDb2.flush();
        try {
          /*
           * dslDb1 będzie współdzielić transakcję z emDb1 i t1d
           */
          //Zapytanie do DB1
          Result<Record> result1 = dslDb1
          .select()
          .from(TEST1)
          .fetch();
          
          //Zapytanie do DB1
          Result<Record> result2 = dslDb2
              .select()
              .from(TEST2)
              .fetch();
          
          for (Record r : result1) {
              Long id = r.getValue(TEST1.ID);
              Integer intval1 = r.getValue(TEST1.INT_VAL1);
              System.out.println("ID: " + id + " intval1: " + intval1);
          }
          for (Record r : result2) {
              Long id = r.getValue(TEST2.ID);
              String stval1 = r.getValue(TEST2.ST_VAL1);
              System.out.println("ID: " + id + " stval1: " + stval1);
          }
          
          /*
           * W tym miejscu result1 i result2 odnajdą wpisy, ale zewnętrzny manager znajdzie tylko 
           * wpisy w tabeli ..._db2, bo transakcja dla db1 nie miała jeszcze commit. 
           */
          int t = 0;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    
  }

}
