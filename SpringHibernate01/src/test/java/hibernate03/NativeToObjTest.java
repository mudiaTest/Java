package hibernate03;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hibernate03.domain.SubClass1;
import hibernate03.domain.SubClass2;
import hibernate03.domain.SubObj;
import hibernate03.domain.Test1;
import hibernate03.domain.Test2;
import hibernate03.domain.Test3;
import hibernate03.domain.Test5;
import hibernate03.domain.Test5_1;
import hibernate03.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NativeToObjTest {

  //@Test
  public void contextLoads() {
  }
  
  @Autowired
  EntityManager em;
  @Autowired
  NewTransactionWrapper ntw;
    
  private void fillTest1() {
    Test1 t1 = new Test1();
    t1.setId(1);
    t1.setIntVal1(11);
    SubClass1 so1 = new SubClass1();
    t1.setSo1(so1);
    so1.setSubIntVal1(11);
    so1.setSubStVal1("a");
    em.persist(t1);
  }
  
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
  
  private void fillTest5() {
    Test5 t51 = new Test5();
    t51.setId(1);
    t51.setIntVal1(11);
    List<SubClass2> t21 = t51.getT2();
    t21.add(new SubClass2(1, "a"));
    t21.add(new SubClass2(2, "b"));
    em.persist(t51);
    
    Test5 t52 = new Test5();
    t52.setId(3);
    t52.setIntVal1(12);
    List<SubClass2> t22 = t52.getT2();
    t22.add(new SubClass2(3, "c"));
    t22.add(new SubClass2(4, "d"));
    em.persist(t52);
    
    Test5 t53 = new Test5();
    t53.setId(5);
    t53.setIntVal1(13);
    List<SubClass2> t23 = t53.getT2();
    t23.add(new SubClass2(5, "e"));
    t23.add(new SubClass2(6, "f"));
    em.persist(t53);
  }
  
  //Zwrot pojedynczej warto�ci prostej 
  //@Test
  public void nativeToObj1() {
    try {
      /* Jako drugi parametr mo�e by� podana tylko nazwa klasy oznaczonej @Entity
       * W przypadku pojedynczej warto�ci integer, string... mozna upomin�� ten parameter
       * i castowa� bez problemu 
       */
      Query q = em.createNativeQuery("SELECT 1 FROM test2"/*, Integer*/);
      Integer res = (Integer) q.getSingleResult();
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }
    
    int t = 0;
  }  
  
  //Zwrot obiektu p�askiego dla pojo i sql
  //@Test
  public void nativeToObj2() {
    try {
      ntw.inNewTrans(()->fillTest1());
      /* W przypadku, gdy zwracamy obiekt inny ni� typy prostego, to nale�y poda� klas�, 
       * bo istnienie tego parametru rzutuje na budow� obiektu res i mo�e uniemo�liwi�
       * p�zniejsze castowanie.  
       */
      Query q = em.createNativeQuery("SELECT * FROM test2 b WHERE b.id = 1", Test2.class);
      Object o = q.getSingleResult();
      Test2 res = (Test2) o;
      /* Mo�na te� od razu rzutowa�
       * Test2 res = (Test2) q.getSingleResult();
       */
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }
    
    int t = 0;
  }
  
  //Zwot obiektu wg��b dla pojo i p�askiego dla sql
  @Test
  public void nativeToObj3() {
    try {
      ntw.inNewTrans(()->fillTest3());      
      Query q = em.createNativeQuery("SELECT * FROM test3 a", Test3.class);
      /* Skutkiem ubocznym parametru "Test3.class" i tego �e OO jet EAGER (default) jest to, �e
       * doczytane zostaj� podobiekty Test2. Dla ka�dego Test3 osobny SQL pobieraj�cy Test2.
       * Doczytanie b�dzie mia�o miejsce podczas wykonywania getResultList.
       */
      List<Object> o = q.getResultList();      
      Test3 res = (Test3) o.get(0);
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }    
    int t = 0;
  }
  
  //Zwot obiektu wg��b dla pojo i join dla sql
  //@Test
  public void nativeToObj4() {
    try {
      ntw.inNewTrans(()->fillTest3());      
      Query q = em.createNativeQuery("SELECT * FROM test3 a LEFT JOIN test2 b ON a.t2_id = b.id", Test3.class);
      /* Skutkiem ubocznym parametru "Test3.class" i tego �e OO jest EAGER (default) jest to, �e
       * doczytane zostaj� podobiekty Test2. Dla ka�dego Test3 osobny SQL pobieraj�cy Test2.
       * Doczytanie b�dzie mia�o miejsce podczas wykonywania getResultList.
       */
      List<Object> o = q.getResultList();      
      Test3 res = (Test3) o.get(0);
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }    
    int t = 0;
  }
  
  //Zwrot obiektu z podlist� embedded i join dla SQL
  @Test
  public void nativeToObj5() {
    try {
      ntw.inNewTrans(()->fillTest5());      
      Query q = em.createNativeQuery("SELECT * FROM test5 a LEFT JOIN test5_t2 b ON a.id = test5_id", Test5.class);
      /* Skutkiem ubocznym parametru "Test3.class" i tego �e OO jest EAGER (default) jest to, �e
       * doczytane zostaj� podobiekty Test2. Dla ka�dego Test3 osobny SQL pobieraj�cy Test2.
       * Doczytanie b�dzie mia�o miejsce podczas wykonywania getResultList.
       */
      List<Object> o = q.getResultList();      
      Test5 res = (Test5) o.get(0);
      /*
       * Poni�ej bedzie b�ad. Wygl�da na to, �e wczytywanie list 
       */
      String s = res.getT2().get(0).getSubStVal1();
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }    
    int t = 0;
  }
  
  // --- Customowe mapowanie --- 
  
  //Customowe mapowanie ...
  //Mapowanie wskazuje na typ zwracanych obiekt�w, wi�c nie trzeba rzutowania
  //@Test
  public void nativeToCustomMapping1() {
    try {
      ntw.inNewTrans(()->fillTest5());      
      Query q = em.createNativeQuery(
          "SELECT a.id, a.int_val1 as val2, b.test5_id as id2, b.sub_int_val1 as s1, b.sub_st_val1 as s2 FROM test5 a LEFT JOIN test5_t2 b ON a.id = test5_id", "Test5Mapping_1");
      List<Test5> o = q.getResultList();      
      Test5 res = o.get(0);
      /*
       * Gdy jest mapowianie do wi�cej ni� jedneo obiektu jednocze�nie
       * Test5 res = o.get(0)[0];
       * Test6 res = o.get(0)[1];
       * ...
       */
      /*
       * Poni�ej bedzie b�ad. Wczytywanie tego ElementCollection jest Lazy i poza sesj�
       */
      String s = res.getT2().get(0).getSubStVal1();
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
      throw e;
    }    
    int t = 0;
  }
  
  //Customowe mapowanie gdy klumny odpowiadaj� polom nazwami.
  //Mapowanie wskazuje na typ zwracanych obiekt�w, wi�c nie trzeba rzutowania
  //@Test
  public void nativeToCustomMapping2() {
    try {
      ntw.inNewTrans(()->fillTest5());      
      Query q = em.createNativeQuery(
          "SELECT a.id, a.int_val1 as val2 FROM test5 a LEFT JOIN test5_t2 b ON a.id = test5_id", "Test5Mapping_2");
      List<Integer> o = q.getResultList();      
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
      throw e;
    }    
    int t = 0;
  }
  
  //Customowe mapowanie z uzyciem konstruktora - do POJO.
  //Mapowanie wskazuje na typ zwracanych obiekt�w, wi�c nie trzeba rzutowania
  @Test
  public void nativeToCustomMapping3() {
    try {
      ntw.inNewTrans(()->fillTest5());      
      Query q = em.createNativeQuery(
          "SELECT a.id, a.int_val1 as val2, b.sub_int_val1 as s1, b.sub_st_val1 as s2 FROM test5 a LEFT JOIN test5_t2 b ON a.id = test5_id", 
          "Test5Mapping_3");
      List<Test5_1> o = q.getResultList();      
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
      throw e;
    }    
    int t = 0;
  }
}
