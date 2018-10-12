package com.my.pl;

import static com.my.pl.jooq.db1.tables.Test1.TEST1;
import static com.my.pl.jooq.db2.tables.Test2.TEST2;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
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

import com.my.pl.config.PersistenceContextDb1;
import com.my.pl.config.PersistenceContextDb2;
import com.my.pl.db1.dao.Test1Dao;
import com.my.pl.db1.domain.Test1;
import com.my.pl.db2.dao.Test2Dao;
import com.my.pl.db2.domain.Test2;
import com.my.pl.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class) 
@ContextConfiguration(classes = { PersistenceContextDb1.class, PersistenceContextDb2.class })
public class JBasicTest {
	
	@Autowired
	Test1Dao t1d;
	@Autowired
	Environment env;	
	@Autowired
	NewTransactionWrapper ntw;
	@Autowired
	@Qualifier("entityManagerDb1")
	EntityManager em;
		
	private void fillTest1() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);
		
		t1d.save(t1);
	}

	@Test
	@Transactional
	@Commit
	public void contextLoads() {
		//przenieść do jakiegoś inicjalizatora
		System.setProperty("org.jooq.no-logo", "true");
		
		ntw.inTrans(()->fillTest1());
		
		/*
		 * Tworzenie podłączenia do DB
		 */
		String userName = "system";
        String password = "system";
        String url = "jdbc:postgresql://Ursus/ll/hibernate05_db1";
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
        	DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
        	
        	/*
        	 * Zapisanie i wysłanie zapytania
        	 */
        	Result<Record> result1 = create
    			.select()
    			.from(TEST1)
    			.fetch();
        	        	
        	/*
        	 * Opracowywanie wyników
        	 */
        	for (Record r : result1) {
        	    Long id = r.getValue(TEST1.ID);
        	    Integer intval1 = r.getValue(TEST1.INT_VAL1);
        	    System.out.println("ID: " + id + " intval1: " + intval1);
        	}        	

        	int t = 0;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
		
	}

}
