package hibernate03;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hibernate03.dao.Test6Dao;
import hibernate03.domain.Test6;
import hibernate03.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityGraphTest {

	//@Test
	public void contextLoads() {
	}
	
	@Autowired
	EntityManager em;
	@Autowired
	NewTransactionWrapper ntw;
	
	@Autowired
	Test6Dao t6d;
	
	private void fillTest1UsingRepo() {
		try {
		Test6 t6 = new Test6(1, 11, 2, 3, 4, 5, 6, 7);
		t6d.save(t6);
		}
		catch(Exception e) {
			int t = 0;
		}
	}	
	
	private void getUsingRepo() {
		try {
		Test6 t6 = t6d.findById((long)1).get();
		int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	private void getUsingGraphEm() {
		try {
			EntityGraph graph = this.em.getEntityGraph("graph.TestSix.lista");
			Map hints = new HashMap();
			hints.put("javax.persistence.fetchgraph", graph);			
			Test6 t6 = em.find(Test6.class, Long.valueOf(1), hints);
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	private void getUsingGraphDefaultRepo() {
		try {			
			Test6 t6 = t6d.getById(Long.valueOf(1)).get();
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	private void getUsingGraphNoneLoadRepo() {
		try {
			Test6 t6 = t6d.getByIdNoneLoad(Long.valueOf(1)).get();
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	private void getUsingGraphNoneFetchRepo() {
		try {
			Test6 t6 = t6d.getByIdNoneFetch(Long.valueOf(1)).get();
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	private void getUsingGraphListaLoadRepo() {
		try {
			Test6 t6 = t6d.getByIdListaLoad(Long.valueOf(1)).get();
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	private void getUsingGraphListaFetchRepo() {
		try {			
			Optional<Test6> t6opt = t6d.getByIdListaFetch(Long.valueOf(1));
			Test6 t6 = t6opt.get();
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	private void getUsingGraphListyLoadRepo() {
		try {
			Test6 t6 = t6d.getByIdListyLoad(Long.valueOf(1)).get();
            int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	private void getUsingGraphListyFetchRepo() {
		try {			
			Test6 t6 = t6d.getByIdListyFetch(Long.valueOf(1)).get();
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	//Testowanie 
//	@Test
	public void entityGraphEmTest() {
		try {
			ntw.inNewTrans(()->fillTest1UsingRepo());	
			ntw.inNewTrans(()->getUsingGraphEm());	
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	//Testowanie 
	@Test
	public void entityGraphRepoTest() {
		try {
			ntw.inNewTrans(()->fillTest1UsingRepo());	
			ntw.inNewTrans(()->getUsingRepo());	
			ntw.inNewTrans(()->getUsingGraphDefaultRepo());	
			ntw.inNewTrans(()->getUsingGraphNoneLoadRepo());	
			ntw.inNewTrans(()->getUsingGraphNoneFetchRepo());	
			ntw.inNewTrans(()->getUsingGraphListaLoadRepo());	
			ntw.inNewTrans(()->getUsingGraphListaFetchRepo());	
			ntw.inNewTrans(()->getUsingGraphListyLoadRepo());	
			ntw.inNewTrans(()->getUsingGraphListyFetchRepo());	
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
}
