package my.com.pl.Hibernate_03;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import my.com.pl.Hibernate_03.dao.Test1Dao;
import my.com.pl.Hibernate_03.domain.SubClass1;
import my.com.pl.Hibernate_03.domain.Test1;
import my.com.pl.Hibernate_03.utils.NewTransactionWrapper;

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
		
	/*
	 * RZUCI EXCEPTION !!!
	 * Hibernate nie lubi jak funkcja zwraca void. Mo¿na 
	 * - zmianiæ zwracany typ na int i zwracaæ wartoœæ dummy
	 * - zmianiæ sposób wywo³ania tej funkcji
	 */
//	@Test
	public void emReturnVoidTest() {
		try {
			StoredProcedureQuery q = em.createStoredProcedureQuery("inctest2");
			q.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
			q.setParameter(0, Integer.valueOf(2));
			q.execute();
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	/*
	 * Wywo³ujemy funkcjê przez native SQL zwracaj¹c wartoœæ dummy - DZIA£A !
	 */
//	@Test
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
	 * Wywo³ujemy funkcjê przez native SQL zwracaj¹c wartoœæ int - DZIA£A !
	 */
//	@Test
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
	 * Wywo³ujemy funkcjê przez native SQL zwracaj¹c wartoœæ int - DZIA£A !
	 */
//	@Test
	public void emReturnIntStoredProcedureQueryTest() {
		try {
			StoredProcedureQuery q = em.createStoredProcedureQuery("inctest5");
			q.registerStoredProcedureParameter("x", Integer.class, ParameterMode.IN);
			q.registerStoredProcedureParameter("y", Integer.class, ParameterMode.OUT);
			q.setParameter("x", Integer.valueOf(4));
			boolean gotRes = q.execute();
			//Wyglada na to, ¿e Hibernate ma b³¹d
			/*Poni¿sze oddaje null*/
			List<Object[]> postComments = q.getResultList();
			/*Poni¿sze rzuca Exception, ale to jest chyba b³¹d H, bo wszystkie tutoriale podaj¹ taki kod*/
			int res = (Integer)q.getOutputParameterValue("y");
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	/*
	 * Wywo³ujemy funkcjê przez native SQL zwracaj¹c wartoœæ int - DZIA£A !
	 */
//	@Test
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
	 * Wywo³ujemy funkcjê przez native SQL zwracaj¹c wartoœæ int - DZIA£A !
	 */
//	@Test
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
	
//	@Test
	public void emInctest62() {
		try {
			StoredProcedureQuery q = em.createStoredProcedureQuery("inctest61");
			q.registerStoredProcedureParameter("i", Integer.class, ParameterMode.IN);
			q.registerStoredProcedureParameter("o1", Integer.class, ParameterMode.OUT);
			q.registerStoredProcedureParameter("o2", Integer.class, ParameterMode.OUT);
			q.setParameter("i", Integer.valueOf(4));
			boolean gotRes = q.execute();
			/*Poni¿sze oddaje null*/
			List<Object[]> postComments = q.getResultList();
			/*Poni¿sze rzuca Exception, ale to jest chyba b³¹d H, bo wszystkie tutoriale podaj¹ taki kod*/
			int res = (Integer)q.getOutputParameterValue("o2");
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	private void runEmInctest71() {
		try {
			Query q = em.createNativeQuery("Select * FROM inctest71(:val);");
			q.setParameter("val", 4);			
			Object res = q.getResultList();
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	@Test
	public void emInctest71() {
		ntw.inNewTrans(()->runEmInctest71());
	}
	
//	@Test
	public void emInctest73() {
		try {
			Query q = em.createNativeQuery("Select * FROM inctest73(:val);");
			q.setParameter("val", 4);			
			List<List<Integer>> res = q.getResultList();
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
}
