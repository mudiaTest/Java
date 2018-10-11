package com.my.pl;

import static com.my.pl.jooq.db1.tables.Test5.TEST5;
import static com.my.pl.jooq.db1.tables.Test5T2.TEST5_T2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.my.pl.db1.dao.Test1Dao;
import com.my.pl.db1.domain.SubClass2;
import com.my.pl.db1.domain.Test5;
import com.my.pl.utils.NewTransactionWrapper;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
//@EnableAutoConfiguration//(exclude = {JmxAutoConfiguration.class}) 
@ContextConfiguration(classes = { PersistenceContext.class })
public class Hibernate05ApplicationTests {
	
	@Autowired
	Test1Dao t1d;
	@Autowired
	Environment env;
	@Autowired
	EntityManager em;
	@Autowired
	NewTransactionWrapper ntw;
	
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
		em.flush();
	}

	@Test
	@Transactional
	@Commit
	public void contextLoads() {
		ntw.inTrans(()->fillTest5());
		
		String userName = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        String url = env.getProperty("spring.datasource.url");

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
        	DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
        	String sql = create
				.select()
				.from(TEST5
						.leftJoin(TEST5_T2)
							.on(TEST5.ID.eq(TEST5_T2.TEST5_ID))
				)
				.getSQL();
        	Result<Record> result = create
    			.select()
    			.from(TEST5
    					.leftJoin(TEST5_T2)
    						.on(TEST5.ID.eq(TEST5_T2.TEST5_ID))
    			)
    			.fetch();
        	
        	for (Record r : result) {
        	    Long id = r.getValue(TEST5.ID);
        	    Integer intval1 = r.getValue(TEST5.INTVAL1);
        	    Integer subintval1 = r.getValue(TEST5_T2.SUBINTVAL1);

        	    System.out.println("ID: " + id + " intval1: " + intval1 + " subintval1: " + subintval1);
        	}
        	int t = 0;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
		
	}

}
