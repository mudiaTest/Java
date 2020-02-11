package hibernate03;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hibernate03.dao.Test1Dao;
import hibernate03.domain.SubObj;
import hibernate03.domain.Test2;
import hibernate03.domain.Test3;
import hibernate03.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjekcjeTest {

  //@Test
  public void contextLoads() {
  }
  
  @Autowired
  EntityManager em;
  @Autowired
  NewTransactionWrapper ntw;  
  
  private void fillTest3() {
    Test3 t31 = new Test3();
    t31.setId(1);
    t31.setIntVal1(11);
    Test2 t21 = new Test2();
    t31.setT2(t21);
    t21.setId(2);
    t21.setIntVal(21);
    t21.setStrVal("b");
    em.persist(t31);
    
    Test3 t32 = new Test3();
    t32.setId(3);
    t32.setIntVal1(12);
    Test2 t22 = new Test2();
    t32.setT2(t22);
    t22.setId(4);
    t22.setIntVal(22);
    t22.setStrVal("c");
    em.persist(t32);
    
    Test3 t33 = new Test3();
    t33.setId(5);
    t33.setIntVal1(13);
    Test2 t23 = new Test2();
    t33.setT2(t23);
    t23.setId(6);
    t23.setIntVal(23);
    t23.setStrVal("d");
    em.persist(t33);
  }
    
  @Autowired
  Test1Dao t1d;
  
  //Testowanie projekcji native
  @Test
  public void projectionTest2() {
    ntw.inNewTrans(()->fillTest3());  
    List<SubObj> lst = t1d.getSubObj();    
    SubObj so = lst.get(0);
    int r = so.getint_val();
    int t = 0;
  }
  

}
