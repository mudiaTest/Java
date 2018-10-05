package my.com.pl.Hibernate_03;

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

import my.com.pl.Hibernate_03.dao.Test1Dao;
import my.com.pl.Hibernate_03.domain.SubClass1;
import my.com.pl.Hibernate_03.domain.Test1;
import my.com.pl.Hibernate_03.utils.NewTransactionWrapper;

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
//	@Autowired
//	private HibernateTransactionManager htm;
	
	@Autowired
	Test1Dao t1d;
	
	@Autowired
	private ApplicationContext ac;
	
	//@Autowired
	//SessionFactory sfa;
	
	@Transactional
	public void fillTest1UsingRepo() {
//		ntw.inTrans(()->{
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
				
				Session s = em.unwrap(Session.class);
				
				t1d.save(t1);
				s.flush();
				/*Pobieranie klucza z cache i sprawdzanie jego wartoœci*/
					SessionStatistics ss = s.getStatistics();
					Set<EntityKey> sk = ss.getEntityKeys();
					Iterator<EntityKey> ite = sk.iterator();
					EntityKey k = ite.next();
					//String className = k.getEntityName(); //.equals( Test1.class.getName() )
					//k.getIdentifier().toString().equals(Long.valueOf(t0.getId()).toString());
					int t = 0;

				boolean czyCache = s.contains(t1);//true jeœli s.persist(t1) / false dla t1d.save(t1)
				Optional<Test1> t2 = t1d.findById(Long.valueOf(t1.getId()));
				//badanie cache L2				
				//boolean czyCache2 = s3.getSessionFactory().getCache().containsEntity( Test1.class, t1.getId() );
				czyCache = s.contains(t0);
				czyCache = s.contains(t1);
				czyCache = s.contains(t2.get());
				
				t = 0;
			}
//		});
	}
	
	//Testowanie stateless session dla em
	@Test	
	@Transactional
	public void emTest() {
		try {
			SessionFactory sf = emf.unwrap(SessionFactory.class);
//			ntw.inNewTrans(()->{
					fillTest1UsingRepo();
//			});
			
			Session s2 = sf.openSession();
			SessionStatistics ss = s2.getStatistics();
			Set<EntityKey> sk = ss.getEntityKeys();
			EntityKey k = sk.iterator().next();
			
			StatelessSession s= sf.openStatelessSession();
			ScrollableResults r;
			r = s.createQuery("SELECT t1 FROM Test1 t1").scroll(ScrollMode.FORWARD_ONLY);
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
