package hibernate03;

import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.sql.DataSource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.jdbc.PgResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hibernate03.dao.Test1Dao;
import hibernate03.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcedureTest {

  //@Test
  public void contextLoads() {
  }
  
  @Autowired
  EntityManager em;
  @Autowired
  NewTransactionWrapper ntw;
  
  @Autowired
  Test1Dao t1d;
  
  @Autowired
  DataSource ds;
    
  /*
   * RZUCI EXCEPTION !!!
   * Hibernate nie lubi jak funkcja zwraca void. Mo�na 
   * - zmiani� zwracany typ na int i zwraca� warto�� dummy
   * - zmiani� spos�b wywo�ania tej funkcji - zamiast execute() zrobi� getFirstResult();
   */
//  @Test
  public void emReturnVoidTest() {
    try {
      StoredProcedureQuery q = em.createStoredProcedureQuery("inctest2");
      q.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
      q.setParameter(0, Integer.valueOf(2));
//      q.execute();
      q.getFirstResult();
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
  /*
   * Wywo�ujemy funkcj� przez native SQL zwracaj�c warto�� dummy - DZIA�A !
   */
//  @Test
  public void emReturnVoidNativeSQLTest() {
    try {
      Query q = em.createNativeQuery("Select 1 FROM inctest2(:val);");
      q.setParameter("val", 3);      
      int res = (Integer)q.getSingleResult();
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
  /*
   * Wywo�ujemy funkcj� przez native SQL zwracaj�c warto�� int - DZIA�A !
   */
//  @Test
  public void emReturnIntNativeSQLTest() {
    try {
      Query q = em.createNativeQuery("Select * FROM inctest3(:val);");
      q.setParameter("val", 3);      
      int res = (Integer)q.getSingleResult();
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
  /*
   * Wywo�ujemy funkcj� przez native SQL zwracaj�c warto�� int - DZIA�A !
   */
//  @Test
  public void emReturnIntStoredProcedureQueryTest() {
    try {
      StoredProcedureQuery q = em.createStoredProcedureQuery("inctest5");
      q.registerStoredProcedureParameter("x", Integer.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("y", Integer.class, ParameterMode.OUT);
      q.setParameter("x", Integer.valueOf(4));
      boolean gotRes = q.execute();
      //Wyglada na to, �e Hibernate ma b��d
      /*Poni�sze oddaje null*/
      List<Object[]> postComments = q.getResultList();
      /*Poni�sze rzuca Exception, ale to jest chyba b��d H, bo wszystkie tutoriale podaj� taki kod*/
      int res = (Integer)q.getOutputParameterValue("y");
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
  /*
   * Wywo�ujemy funkcj� przez native SQL zwracaj�c warto�� int - DZIA�A !
   */
//  @Test
  public void emReturnIntStoredProcedureSQLTest() {
    try {
      Query q = em.createNativeQuery("Select * FROM inctest5(:val);");
      q.setParameter("val", 4);      
      List<Integer> res = q.getResultList();
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
  /*
   * Wywo�ujemy funkcj� przez native SQL zwracaj�c warto�� int - DZIA�A !
   */
//  @Test
  public void emInctest61() {
    try {
      Query q = em.createNativeQuery("Select * FROM inctest61(:val);");
      q.setParameter("val", 4);      
      List<List<Integer>> res = q.getResultList();
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
//  @Test
  public void emInctest62() {
    try {
      StoredProcedureQuery q = em.createStoredProcedureQuery("inctest61");
      q.registerStoredProcedureParameter("i", Integer.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("o1", Integer.class, ParameterMode.OUT);
      q.registerStoredProcedureParameter("o2", Integer.class, ParameterMode.OUT);
      q.setParameter("i", Integer.valueOf(4));
      boolean gotRes = q.execute();
      /*Poni�sze oddaje null*/
      List<Object[]> postComments = q.getResultList();
      /*Poni�sze rzuca Exception, ale to jest chyba b��d H, bo wszystkie tutoriale podaj� taki kod*/
      int res = (Integer)q.getOutputParameterValue("o2");
      int t = 0;
    }
    catch (Exception e) {
      int t = 0;
    }
  }
  
// ------> Pojedyncza warto�� przez RETURN: NamedStoredProcedureQuery vs @StoredProcedureQuery
  
  /*
   * @NamedStoredProcedureQuery
   * "t61" to nazwa nazwanejProcedury zdefiniowanej nad jednej z Entity 
   */
//  @Test
  public void domP61() {
    try {
      StoredProcedureQuery q = em.createNamedStoredProcedureQuery("t61");
      q.setParameter("i", 5);
      Integer res = (Integer) q.getSingleResult();
      int t = 0;
    }
    catch(Exception e) {
      int t = 0;
    }
  }
  
  /*
   * @StoredProcedureQuery
   * "p61" to nazwa procedury w SQL. Nie wymaga dodatkowych adnotacji, ale wymaga okre�lenia parametr�w
   */
//  @Test
  public void emP61() {
    try {
      StoredProcedureQuery q = em.createStoredProcedureQuery("p61");
      q.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN); 
      q.setParameter(1, 6);
      Integer res = (Integer) q.getSingleResult();
      int t = 0;
    }
    catch(Exception e) {
      int t = 0;
    }
  }
  
// ------> Pojedyncza warto�� przez OUT: Repo
  
  /* OK !!!
   * Wymaga TRANSAKCJI !!! 
   */
//  @Test
  public void daoP61_trans() {
    ntw.inNewTrans(()->{
      try {
        Integer res = t1d.p61(5);
        int t = 0;
      }
      catch(Exception e) {
        int t = 0;
      }
    });
  }
  
  /* EXCEPTION !!!
   * Brak TRANSAKCJI !!!
   */
//  @Test
  public void daoP61_noTrans() {
    try {
      Integer res = t1d.p61(5);
      int t = 0;
    }
    catch(Exception e) {
      int t = 0;
    }
  }
  
// ------> Pojedyncza warto�� przez OUT: EM i createNamedStoredProcedureQuery

  /* OK !!!
   * Zwracanie pojedynczej warto�ci z uzyciem @StoredProcedureQuery i output parameter
   * KONIECZNIE wewn�trz transakcji !!!*/
//  @Test
  public void domP61_2() {
    ntw.inTrans(()->{
      try {
        StoredProcedureQuery q = em.createNamedStoredProcedureQuery("t62");
        q.setParameter("i", 5);
        q.execute();
        Object r = q.getOutputParameterValue("o");
      }
      catch(Exception e) {
        int t = 0;
      }
    });
  }
  
  /*
   * EXCEPTION !!!
   * Tak jak powy�sze, ale bez transakcji i zamyka zapytanie nie pozwalaj�c pobra� wyniku
   */
//  @Test
  public void domP61_2_NoTrans() {
    try {
      StoredProcedureQuery q = em.createNamedStoredProcedureQuery("t62");
      q.setParameter("i", 5);
      q.execute();
      Object r = q.getOutputParameterValue("o");
    }
    catch(Exception e) {
      int t = 0;
    }
  }

// ------> Zwrot wielu wierszy "refCourse". Wymaga transakcji, tak jak ka�de uzycie OUT
  
  /* 
   * Zwracanie refcoursor pozwala na pobieranie wynik�w przez Hibernate
   * Zwracanie TABLE(o1 integer, o2 integer) pozwala na ogl�danie wynik�w w SQL managerze 
   */
  
  
  /* OK!
   * Jeden parametr IN i Return: refCourse
   * Konieczne jest dodanie jako PIERWSZEGO parametru ukrytego OUT
   * UWAGA! Kolumny zwracane przez procedur� musz� by� nazwane. 
   * Wpp mo�e by� wyj�tek wskazuj�cy na wiele kolumn o takiej samej nazwie
   */
//  @Test
  public void emP71() {
    ntw.inTrans(()->{
      try {
        StoredProcedureQuery q = em.createStoredProcedureQuery("p71");
        q.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
        q.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN); 
        q.setParameter(2, 6);
        List<Object[]> res = q.getResultList();
        int t = 0;
      }
      catch(Exception e) {
        int t = 0;
      }
    });
  }

  /*
   * Jeden parametr IN i Return: refCourse
   * Konieczne jest dodanie jako PIERWSZEGO parametru ukrytego OUT
   */
//  @Test
  public void emP72() {
    ntw.inTrans(()->{
      try {
        StoredProcedureQuery q = em.createStoredProcedureQuery("p72");
        q.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
        q.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN); 
        q.setParameter(2, 6);
        Object r = q.getOutputParameterValue(1);
        List<Integer> res = q.getResultList();
        int t = 0;
      }
      catch(Exception e) {
        int t = 0;
      }
    });
  }
  
// ------> Zwrot wielu wierszy "TABLE(o1 integer, o2 integer)"   

  /*
   * NIE DZIA�A do ko�ca poprawnie !!!
   */
//  @Test
  public void emP73() {
    ntw.inTrans(()->{
      try {
        StoredProcedureQuery q = em.createStoredProcedureQuery("p73");
        //Liczba paramert�w out musi by� taka, jak w "RETURNS TABLE(o1 integer, o2 integer)" 
        q.registerStoredProcedureParameter(1, Integer.class, ParameterMode.OUT);
        q.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);
        
        q.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN); 
        q.setParameter(3, 6);
        
        //Spowowduje obranie tylko pierwszej warto�ci a nie ca�ej listy
        q.execute();
        Object rCol1 = q.getOutputParameterValue(1);
        //Object rCol2 = q.getOutputParameterValue(2);
        
        //Zwraca NULL
        List<Object[]> res = (List<Object[]>)q.getResultList();
        
        int t = 0;
      }
      catch(Exception e) {
        int t = 0;
      }
    });
  }
  
  /*
   * OK !!!
   */
  
  @Autowired
  private EntityManagerFactory emf;
  
//  @Test
  public void emP73_2() {
    ntw.inTrans(()->{
      try {
        //Pobieranie sesji
        SessionFactory sf = emf.unwrap(SessionFactory.class);
        Session s = sf.openSession();
        
        //Tworzenie wywo�ania procedury
        ProcedureCall q = s.createStoredProcedureCall("p73");
        q.registerParameter(1, Integer.class, ParameterMode.IN);
        q.setParameter(1, 7);
        
        //Pobieranie listy wynik�w
        ProcedureOutputs po = q.getOutputs();
        ResultSetOutput resultSetOutput = (ResultSetOutput) po.getCurrent();
        //Zwraca List. Mo�na rzutowan� List<Object[]> lub List<List<Integer>>
        List<List<Integer>> results = (List<List<Integer>>) resultSetOutput.getResultList();
                  
        int t = 0;
      }
      catch(Exception e) {
        int t = 0;
      }
    });
  }
  
  /*
   * NIE ZNALAZ�EM sposobu aby zwraca� list� wynik�w za pomoc� named query.
   * Jedynie gdy zwracamy Entity to MO�NA SPOBOWA� doda� resultClasses.
   * Mo�e zadzia�a, ale nie pr�bowa�em.
   * Mo�na pr�bowa� te� zwraca� ResultSet i jako� z niego wyci�ga� warto�ci, ale 
   * nic konkretnego nie mam.
   */
//  @Test
  public void domP71() {
    ntw.inTrans(()->{
      try {
        StoredProcedureQuery q = em.createNamedStoredProcedureQuery("t71");
        q.setParameter(2, 6);        
        q.execute();
        Object rCol1 = q.getOutputParameterValue(1);
        //rCol1 -> PgResultSet -> ResultSet 
        ResultSet r = (ResultSet) rCol1; 
        
        int t = 0;
      }
      catch(Exception e) {
        int t = 0;
      }
    });
  }
  
  /*
   * Nie jestem w stanie wykonac zapytania w �aden spos�b
   */
//  @Test
  public void domP71_() {
    ntw.inTrans(()->{
      try {
        Object r = t1d.p72(6);        
        int t = 0;
      }
      catch(Exception e) {
        int t = 0;
      }
    });
  }
}
