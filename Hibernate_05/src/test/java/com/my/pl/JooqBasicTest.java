package com.my.pl;

import static com.my.pl.jooq.db1.tables.Test1.TEST1;
import static com.my.pl.jooq.db1.tables.Test2.TEST2;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.val;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;

import org.jooq.CommonTableExpression;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
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
import com.my.pl.db1.dao.Test1Db1Dao;
import com.my.pl.db1.dao.Test2Db1Dao;
import com.my.pl.db1.domain.Test1;
import com.my.pl.db1.domain.Test2;
import com.my.pl.jooq.db1.tables.records.Test1Record;
import com.my.pl.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class) 
@ContextConfiguration(classes = { PersistenceContextDb1.class, PersistenceContextDb2.class })
public class JooqBasicTest {
	
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
	
		
	private void fillTest1() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);		
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
        	System.out.println("Nie można utworzyć połączenia: " + dbName);
        }
        return result;
	}
	
	/*
	 * Użycie class function - pozwala na stworzenie SQL, ale nie pobranie wyników
	 */
//	@Test
	@Transactional
	@Commit
	public void staticTest() {	
		ntw.inTrans(()->fillTest1());
		try {
			/*
			 * Tworzenie SQL - można go potem wykorzystać np w Hibernate
			 */
			String sql = DSL.select()
					.from(TEST1)
					.getSQL();
			/*
			 * Poniższe da bład bo statyczny DSL nie ma podłączonego połączenia
			 */
//			Result<Record> result1 = DSL.select()
//					.from(TEST1)
//					.fetch();
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Pobieranie rekordu - wynik bez ustalonego typu
	 */
//	@Test
	@Transactional
	@Commit
	public void selectTest() {	
		ntw.inTrans(()->fillTest1());
		/*
		 * Tworzenie podłączenia do DB
		 */
        try (DSLContext create = getDLSCtx("hibernate05_db1")) {
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
	
	/*
	 * Pobieranie ustalonego typu
	 */
//	@Test
	@Transactional
	@Commit
	public void insertTest() {	
		ntw.inTrans(()->fillTest1());
		/*
		 * Tworzenie podłączenia do DB
		 */
		em.flush();
		
		try (
				//DSLContext create = getDLSCtx("hibernate05_db1")
				DSLContext create = dsl1;
				) {
			/*
			 * Zapobiega jakiemukolwiek logowaniu
			 */
			create.configuration().settings().withExecuteLogging(false);
			Result<Record> result1 = create
	    			.select()
	    			.from(TEST1)
	    			.fetch();
			
			/*for (Record r : result1) {
				Long id = r.getValue(TEST1.ID);
				Integer intval1 = r.getValue(TEST1.INT_VAL1);
				System.out.println("ID: " + id + " intval1: " + intval1);
			}        	*/
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public Test1Record getTest1Record(DSLContext create, int id) {
		return create
				.selectFrom(TEST1)
				.fetchOne();
	}

//	@Test
	//@Transactional
	@Commit
	public void selectUpdateWithOptimistocLockingTest() {	
		ntw.inTrans(()->fillTest1());	
		try (
				DSLContext create = dsl1;
				) {	
			/*
			 * Włącza optymistyczne blokowanie
			 */
			//create.settings().withExecuteWithOptimisticLocking(true);
			Test1Record result1 = getTest1Record(create, 0);
			Test1Record result2 = getTest1Record(create, 0);
			
			ntw.inTrans(()->{
				result1.setIntVal1(12);
				result1.store();
				
				/*
				 * To nie zadziała - zostanie zablokowane przez optymistic locking 
				 * Jest robiony dodatkowy SELECT * FOR UPDATE.
				 */
				result2.setIntVal1(13);
				result2.store();
				
				/*
				 * To zadziała, bo jest to działanie na tym samym obiekcie
				 * Jest robiony dodatkowy SELECT * FOR UPDATE.
				 */
				//result1.setIntVal1(13);
				//result1.store();
			});
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	//@Transactional
	@Commit
	public void updatePKTest() {	
		ntw.inTrans(()->fillTest1());	
		try (
				DSLContext create = dsl1;
				) {	
			/*
			 * Włącza opcję podmiany PK. Bez tej opcji zamiast UPDATE został by wykonany INSERT z nowym PK
			 */
			create.settings().withUpdatablePrimaryKeys(true);
			Test1Record result1 = getTest1Record(create, 0);
			
			ntw.inTrans(()->{
				result1.setId((long)2);
				result1.store();
				result1.setIntVal1(12);
				result1.store();
			});
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Hibernate domyślnie przebudowyje DB i usuwa triggera, więc wykonać wpierw
	 * CREATE OR REPLACE FUNCTION f1()
	 *  RETURNS trigger AS
	 * $$
	 * begin
	 *  NEW.st_val2 := 'x';
	 *  RETURN NEW;
	 * END;
	 * $$
	 * LANGUAGE 'plpgsql';
	 * 
	 * a następnie na breake na ntw.inTrans(()->{ wykonać
	 * 
	 * create trigger t1 before update on test1 for each row execute PROCEDURE f1();
	 */
	
//	@Test
	//@Transactional
	@Commit
	public void getTriggerResultTest() {	
		ntw.inTrans(()->fillTest1());	
		try (
				DSLContext create = dsl1;
				) {	
			/*
			 * Włącza opcję pobierania wszystkich wartości. Obiekt zostanie uaktualniony także o wartości zmienione triggerem, czy w jakikolwiek inny sposób.
			 */
			create.settings().withReturnAllOnUpdatableRecord(true);
			Test1Record result1 = getTest1Record(create, 0);
			
			ntw.inTrans(()->{
				result1.setIntVal1(12);
				//poniższe store spowoduje uaktualnienie wartości w result1 w kolumnie stringa do wartości 'x'
				result1.store();
				result1.setIntVal1(13);
				result1.store();
			});
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void implicitJoinTest() {	
		ntw.inTrans(()->fillTest1Test2());	
		try (
				DSLContext create = dsl1;
				) {	
			Test1Record result1 = getTest1Record(create, 0);
			
			CommonTableExpression<Record1<Integer>> t1 = name("t1").fields("f1").as(select(val(1)));
			
			String s1 = create.select(
					TEST2.test1().INT_VAL1.as("t1Val") 
					,TEST2.INT_VAL1.as("t2Val") 
					//Poniższe nie jest potrzebne
					,TEST2.ID
					)
				.from(TEST2)
				//Poniższe nie jest potrzebne
				.join(TEST1).on(TEST2.test1().ID.eq(TEST1.ID))

			.getSQL();									
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	//@Transactional
	@Commit
	public void defaultTest() {	
		ntw.inTrans(()->fillTest1());	
		try (
				DSLContext create = dsl1;
				) {	
			Test1Record result1 = getTest1Record(create, 0);
			
			CommonTableExpression<Record1<Integer>> t1 = name("t1").fields("f1").as(select(val(1)));
			
			String s1 = create.select()
				.from(TEST1)
				.join(TEST2).onKey()
			.getSQL();
							
			/*create
				//.with("a").as(select(val(1).as("x"))
				.select()
		      .from(TEST1,later		      
		      .where(a.YEAR_OF_BIRTH.gt(1920)
		      .and(a.FIRST_NAME.eq("Paulo")))
		      .orderBy(b.TITLE)
		      .fetch();*/
			
			ntw.inTrans(()->{
				result1.setStVal2("k'kk");
				//poniższe store spowoduje uaktualnienie wartości w result1 w kolumnie stringa do wartości 'x'
				//create.insertInto(TEST1, result1);
			});
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
