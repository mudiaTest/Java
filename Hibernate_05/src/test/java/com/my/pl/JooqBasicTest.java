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
import org.jooq.WindowDefinition;
import org.jooq.conf.StatementType;
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
	public Test1Record getTest1Record(DSLContext create, long id) {
		/*
		 * fetchOne() nie zapewnia jednego wyniku, tylko oddaje jeden wynik.
		 * Jeśli zapytanie SQL zwróci więcej niż 1 rekord to dostaniemy 
		 * exception, więc warto zadbać o WHERE
		 */
		return create
				.selectFrom(TEST1)
				.where(TEST1.ID.eq(id))
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
			Test1Record result1 = getTest1Record(create, 1);
			Test1Record result2 = getTest1Record(create, 1);
			
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
	
	//@Test
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
			Test1Record result1 = getTest1Record(create, 1);
			
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
	
	//@Test
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
			Test1Record result1 = getTest1Record(create, 1);
			
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
	
	//@Test
	//@Transactional
	@Commit
	public void multopleJoinTheSameTableTest() {	
		ntw.inTrans(()->fillTest1Test2());	
		try (
				DSLContext create = dsl1;
				) {				
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			com.my.pl.jooq.db1.tables.Test1 t2 = TEST1.as("t2");
			
			String s1 = create.select(t1.ID, t2.INT_VAL1)
					.from(t1)
					.join(t2).on(t1.ID.eq(t2.ID))
					.getSQL();			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void groupByTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			/*
			 * Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			 */
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			/*
			 * proste
			 */
			String s1 = create.select(t1.ID, DSL.count())
					.from(t1)
					.groupBy(t1.ID)
					.getSQL();	
			
			/*
			 * rózne funkce agregujące. Mediana i niżej dostępne od 9.4
			 */
			String s2 = create.select(
						t1.ST_VAL2, DSL.count()
						, DSL.sum  (t1.ID)
						, DSL.max  (t1.ID)
						, DSL.min(t1.INT_VAL1)
						, DSL.avg(t1.INT_VAL1)					
					    , DSL.countDistinct(t1.INT_VAL1)
					    , DSL.maxDistinct(t1.INT_VAL1)
					    , DSL.minDistinct(t1.INT_VAL1)
					    , DSL.sumDistinct  (t1.INT_VAL1)
					    , DSL.avgDistinct  (t1.INT_VAL1)
					    , DSL.groupConcat        (t1.INT_VAL1)
					    , DSL.groupConcatDistinct(t1.INT_VAL1)
					    
					    , DSL.median(t1.INT_VAL1)
					    , DSL.stddevPop (t1.INT_VAL1)
					    , DSL.stddevSamp(t1.INT_VAL1)
					    , DSL.varPop    (t1.INT_VAL1)
					    , DSL.varSamp   (t1.INT_VAL1)
				    )
					.from(t1)
					.groupBy(t1.ST_VAL2)
					.getSQL();	
			
			/*
			 * filtrowanie dostępne od 9.4, albo 9.6
			 */
			String s3 = create.select(
						t1.ID, 
						DSL.sum(t1.INT_VAL1)
							.filterWhere(t1.ID.gt((long)3))
						)
					.from(t1)
					.groupBy(t1.ID)
					.getSQL();	
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void windowTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Aliasowanie
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			/*
			 * select 
				  "t1"."id", 
				  "t1"."st_val2", 
				  sum("t1"."int_val1") over "w", 
				   sum("t1"."int_val1") over "w1", 
				    sum("t1"."int_val1") over "w2", 
				     sum("t1"."int_val1") over "w3", 
				  sum("t1"."int_val1") over (partition by "t1"."st_val2" order by "t1"."id" desc)
				from "public"."test1" as "t1" 
					-- Dla każdej wartości id pokazuje sume dotychczasowych i aktualnej wartosci. 
					-- Są to kolejne liczby bo id jest PK i nie powtarza sie.
					-- Jako partition brana jest cała tabela t1, wic wynik nie jest resetowany
				window "w" as (order by "t1"."id") 
					-- Dla każdej wartości id pokazuje sume dotychczasowych i aktualnej wartosci. 
					-- Są to kolejne liczby bo id jest PK i nie powtarza sie.
					-- Jako partition brana jest cała tabela t1, wic wynik nie jest resetowany.
				, "w1" as (order by "t1"."st_val2") 
					-- Dla każdej wartości st_val2 oblicza sume od nowa. 
					-- Wartosci powtarzają sie, bo dla jednej wartości st_val2 wyliczane jest tylko raz a wartości w st_val2 nie jest UNIQUE
					-- Brak order by sprawia, ze liczona jest suma wszystkich na raz i wpisywana do wszystkich wierszy jednocześnie
				, "w2" as (partition by "t1"."st_val2")
				-- Dla każdej wartości st_val2 oblicza sume od nowa. 
				-- Wartosci powtarzają sie, bo dla jednej wartości st_val2 wyliczane jest tylko raz a wartości w st_val2 nie jest UNIQUE
				-- Order by sprawia, ze suma liczona jest po kolei dla kolejnych wierszy w danej grupie (wedle kolejności order by)
				, "w3" as (partition by "t1"."st_val2" order by "t1"."id")
				order by "t1"."id"
			 */
			//Definiowanie okna		
			WindowDefinition w = DSL.name("w").as(DSL.partitionBy(t1.ST_VAL2));
			
			/*
			 * proste
			 */
			String s1 = create.select(
						t1.ID
						, t1.ST_VAL2
						, DSL.sum(t1.INT_VAL1).over(w)
						, DSL.sum(t1.INT_VAL1).over().partitionBy(t1.ST_VAL2).orderBy(t1.ID.desc())
						)
					.from(t1)
					.window(w)
					.getSQL();	
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void OrderByTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			String s1 = create.select(t1.ID)
					.from(t1)
					.orderBy(
							t1.ID, 
							t1.INT_VAL1.asc(), 
							DSL.inline(1).desc(),
							DSL.choose(t1.INT_VAL1).when(13, 1).otherwise(2),
							DSL.choose().when(t1.INT_VAL1.mod(2).eq(0), 1).otherwise(2),
							t1.INT_VAL1.sortAsc(14,15).nullsFirst()
							)
					.getSQL();	
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void limitOffsetTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			String s1 = create.select(t1.ID)
					.from(t1)
					//po czym sortujemy
					.orderBy(t1.ID)
						//ile wierszy
						.limit(1)
						//czy pobierze "ogon"
						.withTies()
						//od kótrego wiersza rozpocząć pobieranie - liczenie od 0
						.offset(1)
					.getSQL();
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	//@Transactional
	@Commit
	public void seekTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			String s1 = create.select(t1.ID)
					.from(t1)
					.orderBy(t1.ID)
					//jako argument wstawiamy ostatni pobrany wiersz
					.seek((long)2)
					//ile kolejnych wierszy należy pobrać
					.limit(1)
					.getSQL();	
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	//@Transactional
	@Commit
	public void unionTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			String s1 = create.selectFrom(t1)
					.union(create.selectFrom(t1))
					.except(create.selectFrom(t1).where(t1.ID.gt((long)3)))
					.getSQL();	
			
			s1 = create.selectFrom(t1)
					.where(t1.ID.gt((long)2))
					.getSQL();	
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	//@Transactional
	@Commit
	public void defaultTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			String s1 = create.select(t1.ID)
					.from(t1)
					.orderBy(
							t1.ID, 
							t1.INT_VAL1.asc(), 
							DSL.inline(1).desc(),
							DSL.choose(t1.INT_VAL1).when(13, 1).otherwise(2),
							DSL.choose().when(t1.INT_VAL1.mod(2).eq(0), 1).otherwise(2),
							t1.INT_VAL1.sortAsc(14,15).nullsFirst()
							)
					.getSQL();	
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
