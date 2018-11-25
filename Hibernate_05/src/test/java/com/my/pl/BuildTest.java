package com.my.pl;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.val;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.jooq.CommonTableExpression;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.FieldLike;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.WindowDefinition;
import org.jooq.conf.ParamType;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.SQLDataType;
import org.jooq.util.postgres.PostgresDataType;
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
import com.my.pl.db1.dao.Test1Db1Dao;
import com.my.pl.db1.dao.Test2Db1Dao;
import com.my.pl.db1.domain.Test1;
import com.my.pl.db1.domain.Test2;
import com.my.pl.utils.NewTransactionWrapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Test1Res {
	private Long id;
	public Double cst;
}

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class) 
@ContextConfiguration(classes = { PersistenceContextDb1.class, PersistenceContextDb2.class })
public class BuildTest {
	/*
	@Autowired
	Test1Db1Dao t1d;
	@Autowired
	Test2Db1Dao t2d;
	@Autowired
	Environment env;	
	@Autowired
	NewTransactionWrapper ntw;
	@Autowired
	@Qualifier("entityManagerDb1")
	EntityManager em;
	@Autowired
	@Qualifier("dslDb1")
	DefaultDSLContext dsl1;
	@Autowired
	@Qualifier("dslDb2")
	DefaultDSLContext dsl2;	
		*/
	/*
	private void fillTest1() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);		
		t1d.save(t1);
	}
	
	private void fillTest1_5() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);	
		t1.setStVal2("a");
		t1d.save(t1);
		
		t1 = new Test1();
		t1.setId(2);
		t1.setIntVal1(12);		
		t1.setStVal2("a");	
		t1d.save(t1);
		
		t1 = new Test1();
		t1.setId(3);
		t1.setIntVal1(13);	
		t1.setStVal2("a");		
		t1d.save(t1);
		
		t1 = new Test1();
		t1.setId(4);
		t1.setIntVal1(14);	
		t1.setStVal2("b");		
		t1d.save(t1);
		
		t1 = new Test1();
		t1.setId(5);
		t1.setIntVal1(15);		
		t1.setStVal2("b");	
		t1d.save(t1);
	}
	
	private void fillTest1Test2() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);		
		t1d.save(t1);
		
		Test2 t2 = new Test2();
		t2.setId(1);
		t2.setIntVal1(22);
		t2d.save(t2);
	}
	
	private void fillDivideTest() {
		Test1 t1;
		t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);	
		t1.setStVal2("a");	
		t1d.save(t1);
		
		t1 = new Test1();
		t1.setId(2);
		t1.setIntVal1(11);		
		t1.setStVal2("b");	
		t1d.save(t1);

		t1 = new Test1();
		t1.setId(3);
		t1.setIntVal1(11);	
		t1.setStVal2("c");		
		t1d.save(t1);
		
		t1 = new Test1();
		t1.setId(4);
		t1.setIntVal1(12);	
		t1.setStVal2("a");		
		t1d.save(t1);
		
		t1 = new Test1();
		t1.setId(5);
		t1.setIntVal1(12);		
		t1.setStVal2("c");	
		t1d.save(t1);
		
		t1 = new Test1();
		t1.setId(6);
		t1.setIntVal1(12);		
		t1.setStVal2("d");	
		t1d.save(t1);
		
		
		Test2 t2;
		t2 = new Test2();
		t2.setId(1);
		t2.setStVal2("a");
		t2d.save(t2);
		
		t2 = new Test2();
		t2.setId(1);
		t2.setStVal2("b");
		t2d.save(t2);
	}
	
	private DSLContext getDLSCtx(String dbName) {
		String userName = "system";
        String password = "system";
        String url = "jdbc:postgresql://Ursus/ll/" + dbName;
        DSLContext result = null;
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
        	result = DSL.using(conn, SQLDialect.POSTGRES);
        }
        catch (Exception e)
        {
        	System.out.println("Nie moĹĽna utworzyÄ‡ poĹ‚Ä…czenia: " + dbName);
        }
        return result;
	}*/
	
	@Test
	@Transactional
	@Commit
	public void staticTest()
	{
		
	}	
}
