package com.my.pl;

import static com.my.pl.jooq.db1.tables.Test1.TEST1;
import static com.my.pl.jooq.db1.tables.Test2.TEST2;
import static com.my.pl.jooq.db1.Routines.*;
import static com.my.pl.jooq.db1.Sequences.*;
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
import com.my.pl.jooq.db1.routines.Echo;
import com.my.pl.jooq.db1.tables.Echo2;
import com.my.pl.jooq.db1.tables.Echo3;
import com.my.pl.jooq.db1.tables.records.Test1Record;
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
	//@Test
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

	//@Test
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
	
	//Poniższe nie zadział bez ustawionego FK, a to doddaję ręcznie,więc nie skompiluje się bez zmian w DB i ponownego generowania zródłe
	//@Test
	//@Transactional
//	@Commit
//	public void implicitJoinTest() {	
//		ntw.inTrans(()->fillTest1Test2());	
//		try (
//				DSLContext create = dsl1;
//				) {	
//			String s1 = create.select(
//					TEST2.test1().INT_VAL1.as("t1Val") 
//					,TEST2.INT_VAL1.as("t2Val") 
//					//Poniższe nie jest potrzebne
//					,TEST2.ID
//					)
//				.from(TEST2)
//				//Poniższe nie jest potrzebne				
//				.join(TEST1).on(TEST2.test1().ID.eq(TEST1.ID))
//
//			.getSQL();									
//			int t = 0;
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
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
	
	//@Test
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
	
	//@Test
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
					.union(create.selectFrom(t1))//LIMIT i ORDER BY są implementowane przez J przez odpowiednie obudowywanie podzapytań
					.except(create.selectFrom(t1).where(t1.ID.gt((long)3)) )
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
	
	//@Test
	//@Transactional
	@Commit
	public void InsertTest() {	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			/*
			 * Insert z użyciem VALUES
			 */
			String s1 = create
					.insertInto(t1, t1.ID, t1.INT_VAL1)
					.values((long)1, 11)
					.values((long)2, 22)				
					.getSQL();
			
			/*
			 * Insert z użyciem SET
			 */
			String s2 = create
					.insertInto(t1)
					.set(t1.ID, (long)3)
					.set(t1.INT_VAL1, 33)
					.newRecord()
					.set(t1.ID, (long)4)
					.set(t1.ST_VAL2, "44")
					.getSQL();	
			
			/*
			 * Insert z użyciem .onDuplicateKeyUpdate()
			 */
			String s3 = create
					.insertInto(t1)
					.set(t1.ID, (long)1)
					.set(t1.INT_VAL1, 11)
					.getSQL();
			String s4 = create
					.insertInto(t1, t1.ID, t1.INT_VAL1)
					.values((long)1, 12)
					/*
					 * nie działa dla Postgres 9.1. Chce robić za pomocą "ON CONFLICT"
					 */
					.onDuplicateKeyUpdate()
					.set(t1.ID, (long)2)
					/*
					 * DZiała dla 9.1, ale uzywa obejścia "where not exists" wpp "ON CONFLICT"
					 */
					//.onDuplicateKeyIgnore()
					.getSQL();
			
			/*
			 * Insert returning
			 */
			String s5 = create
					.insertInto(TEST1, TEST1.ID, TEST1.INT_VAL1, TEST1.RV)
					.values((long)1, 11, 0)
					.values((long)2, 22, 0)
					.returning(TEST1.ID, TEST1.INT_VAL1, TEST1.ST_VAL2)
					.getSQL();
			
			String s6 = create
			.insertInto(TEST1, TEST1.ID, TEST1.INT_VAL1, TEST1.RV)
			.select(       create.select(DSL.inline((long)1), DSL.inline(111), DSL.inline(0))
					.union(create.select(TEST1.ID.add(10), DSL.inline(111), TEST1.RV).from(TEST1))//też zadziała
					.union(create.select(TEST1.ID.add(20), TEST1.INT_VAL1, TEST1.RV).from(TEST1))//też zadziała
					)
			.getSQL();
			
			/*
			 * Poniższe może dać PK violation w SQL, bo przepisujemy z tej samej tabeli, ale to tyko przykład
			 */
			String s7 = create
			.insertInto(TEST1)
			.select(
					create
					.selectFrom(t1)
					.where(t1.ID.gt((long)1)))					
			.getSQL();
			
			
			/*
			 * Pobieranie danych RAW - jeszcze nie ma żadnego rzutowania
			 */
			Result<?> result = create.fetch(s5);
			
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	//@Commit
	public void updateTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			/*
			 * Basic
			 */
			String s1 = create.update(t1)
					.set(t1.INT_VAL1, 111)					
					.set(t1.ST_VAL2, "111")					
					.getSQL();	
			
			/*
			 * UPDATE FROM SELECT pojedynczej wartości
			 */
			String s2 = create.update(t1)
					.set(t1.INT_VAL1, select(DSL.inline(112))	)				
					.getSQL();	
			
			/*
			 * Wstawianie listy wartości - ROW
			 */
			String s3 = create.update(t1)
					.set(DSL.row(t1.INT_VAL1, t1.ST_VAL2), DSL.row(113, "113"))					
					.getSQL();	
			
			/*
			 * UPDATE FROM SELECT wielu wartości
			 * Zadziała, ale dopiero POSTGRES 9.5 ma to zaimplementowane
			 */
			String s4 = create.update(t1)
					.set(DSL.row(t1.INT_VAL1, t1.ST_VAL2), 
						 select(t1.INT_VAL1, DSL.inline("114")).from(t1) 
					)					
					.getSQL();		
			
			/*
			 * 
			 */
			String s5 = create.update(t1)
					.set(t1.INT_VAL1, 111)
					.returning()
					.getSQL();		
			String s6 = create.update(t1)
					.set(t1.INT_VAL1, 111)
					.returning(t1.INT_VAL1)
					.getSQL();		
			String s7 = create.update(t1)
					.set(t1.INT_VAL1, 111)
					.returning(t1.ID, t1.INT_VAL1)
					.getSQL();	
			
			/*
			 * Da błąd, ponieważ zwracane będzie wiele wyników, a fetchOne pozwala obsłużyć tylko jeden
			 */
			//Record r1 = create.fetchOne(s5);
			Result<Record> r2 = create.fetch(s5);
			//r2.get(0).valuesRow().field(0).getName(); //pobranie wartości, ale nazwa dziwna
			r2.get(1).getValue(0).toString(); //pobieranie wartości jest możliwe tylko jako string
			((Long)r2.get(1).getValue(0)).longValue(); //pobieranie wartości po rzutowaniu jest możliwe do poprawnej wartości
			((Long)r2.get(1).get(0)).longValue(); //jw, String lub rzutowanie
			r2.getValue(2, 0).getClass();//jw, String lub rzutowanie
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void deleteTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			String s1 = create.delete(t1).getSQL();
			String s2 = create.delete(t1).where(t1.ID.eq((long)1)).getSQL();
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void mergeTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			String s1 = create
					.mergeInto(t1)
					.using(DSL.selectOne())//.usingDual()
					.on(t1.ID.eq((long)1))
					.getSQL();
			
			String s2 = create
					.mergeInto(t1)
					.using(DSL.selectOne())//.usingDual()
					.on(t1.ID.eq((long)1))
					.getSQL();
			
			String s3 = create
					.mergeInto(t1)
					.using(DSL.selectOne())//.usingDual()
					.on(t1.ID.eq((long)1))
					.whenMatchedThenUpdate()
					.set(t1.INT_VAL1, 111)
					.whenNotMatchedThenInsert(t1.ID, t1.INT_VAL1, t1.RV)
					.values((long)8, 88, 0)
					//.whenNotMatchedThenInsert()
					//.set()
					.getSQL();
			
			String s4 = create
					.mergeInto(t1)
					.using(DSL.selectOne())//.usingDual()
					.on(t1.ID.eq((long)1))
					.whenMatchedThenUpdate()
					.set(t1.INT_VAL1, 111)
					.whenNotMatchedThenInsert()
					.set(t1.ID, (long)9)
					.set(t1.RV, 0)
					.set(t1.INT_VAL1, 99)
					.getSQL();			
			
			/*
			 * POSTGRES nie obsługuje MERGE. Nalezy używać INSERT ... ON CONFLICT
			 */
			String s5 = create
					.insertInto(TEST1)
					.values((long)7, 77, 0, "7")//.usingDual()
					.onConflict(TEST1.ID).doUpdate().set(TEST1.INT_VAL1, 77)
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
	public void aliasIJoinTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			/*
			 * Alias tabeli
			 */
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			com.my.pl.jooq.db1.tables.Test1 t2 = TEST1.as("t2");
			com.my.pl.jooq.db1.tables.Test1 t11 = TEST1.as("t11");
			/*
			 * Aliasy kolumn
			 */
			Field<Long> f1 = t1.field(t1.ID);
			Field<?> f2 = t1.field("int_val1");
			
			String s1 = create.select(f1, f2)
					.from(t1)					
					.getSQL();
			
			String s21 = create.select(t1.INT_VAL1)
					.from(select(t1.ID).from(t1).asTable())					
					.getSQL();
			
			/*
			 * select() pobierze wszystkie kolumny z podzapytania i nada im oryginalne nazwy
			 * 
			 * Jeśli nazwy kolumn podamy z palca i pomylimy się, to kolumny zostaną pominięte bez rzycenia błędem
			 * 
			 * JOOQ nie sprawdzi też, czy nazwy podajemy poprawne. Dlatego warto posługiwać się 
			 * aliasami, zamiast wpisywać z palca - mniejsze prawdopodobieństwo wystąpienia błędu.
			 * Można też zamiast "asTable(t2)" napisać "asTable()" i automatycznie zostanie nadana nazwa oryginalnej tabeli
			 * 
			 */
			String s22 = create.select(t1.ID)
				.from(select(t1.ID, t1.INT_VAL1).from(t1).asTable("t2"))					
				.getSQL();
			
			/*
			 * BŁąD - Alias tabeli nadaniy automatycznie, więc jest inny niż alias w SELECT nadany ręcznie
			 */
			String s23 = create.select(t1.ID)
				.from(
					select(t1.ID, t1.INT_VAL1)
					.from(t1).join(t2)
					.on(t1.ID.eq(t2.ID))
					.asTable() 
					)					
				.getSQL();
			
			/*
			 * BŁąD - Alias tabeli inny niż alias w SELECT
			 */
			String s25 = create.select(t1.ID)
					.from(
							select(t1.ID, t1.INT_VAL1)
							.from(t1).join(t2)
							.on(t1.ID.eq(t2.ID))
							.asTable("tmp") 
							)
					.getSQL();
			
			/*
			 * OK - Brak Aliasu w subSelect - Aliasy w SELECT zostaną wypełnione automatycznie
			 */
			String s26 = create.select()
					.from(
							select(t1.ID, t1.INT_VAL1)
							.from(t1).join(t2)
							.on(t1.ID.eq(t2.ID))
							.asTable("tmp") 
							)
					.getSQL();
			
			/*
			 * OK - Brak Aliasu w subSelect i SELECT - zostanie wypełnione automatycznie
			 */
			String s27 = create.select()
					.from(
							select(t1.ID, t1.INT_VAL1)
							.from(t1).join(t2)
							.on(t1.ID.eq(t2.ID))
							)
					.getSQL();
			
			/*
			 * OK - Używamy Aliasu, aby nie pomylić się w nazwach kolumn
			 */
			String s28 = create.select(t11.ID)
				.from(
					select(t1.ID, t1.INT_VAL1)
					.from(t1)
					.join(t2).on(t1.ID.eq(t2.ID))
					.asTable(t11.getName())
						)
				.getSQL();
			
			/*
			 * OK - Używamy Aliasu, aby nie pomylić się w nazwach kolumn
			 */
			String s29 = create.select(t11.ID)
					.from(
							select(t1.ID, t2.INT_VAL1)
							.from(t1)
							.join(t2).on(t1.ID.eq(t2.ID))
							.asTable(t11.getName())
							)
					.join(t2).on(t2.ID.eq(t11.ID))
					.getSQL();
			
			/*
			 * SELEFT FROM subselect zwracający customowe kolumny (niebędący kalką istniejącej tabeli)
			 * 1 - Definiujemy zapytanie
			 * 2 - Definiujemy obiekt reprezentujący pierwszą zwracaną kolumnę
			 * 3 - Definiujemy obiekt reprezentujący kolumnę o wybranej nazwie.
			 * 		Dla t2.col piszemy tylko col
			 * 		Jeśli jest więcej kolumn o tej samej nazwie, dodajemy do kolumn aliasy 
			 * 4 - Definiujemy obiekt reprezentujący kolumnę o wybranej nazwie - zwracającą stałą.
			 */
				//1
			Table<Record3<Long, Integer, String>> t12 = create
					.select(t1.ID, t2.INT_VAL1.as("t2Int"), DSL.inline("fff").as("cst"))
					.from(t1)
					.join(t2).on(t1.ID.eq(t2.ID))
					.asTable("tmp");
				//2
			//Field<Long> t12Id = t12.field(0, Long.class);
			//getType odda Integer.class
			//getRecordType
			Field<Long> t12Id = t12.field(t2.ID.getName(), t2.ID.getType());
				//3
			//Field t12Int = t12.field("INT_VAL1");
			Field t12Int = t12.field("t2Int");
				//4
			Field t12Str = t12.field("cst");
			
			String s291 = create.select(t2.ID, t12Id, t12.field("t2Int"), t12Str)
					.from(t12)
					.join(t2).on(t2.ID.eq(t12Id))					
					.getSQL();
			
			/*
			 * Poniższe jest przykładem próby bez użycia dodatkowych obiektów. 
			 * Nie ma jak dodwołać się w zewnętrznym SQL do t2.INT_VAL1
			 */
			/*String s292 = create.select(
						t11.ID, 
						// Oba poniższę zadziałają zle
						//DSL.inline("t11.INT_VAL1"),
						//t11.getName()+"."+t2.INT_VAL1.getName()
						)
					.from(
							select(t1.ID, t2.INT_VAL1)
							.from(t1)
							.join(t2).on(t1.ID.eq(t2.ID))
							.asTable(t11.getName())
							)
					.join(t2).on(t2.ID.eq(t11.ID))
					.getSQL();*/
			
			/*
			 * OK - Nadajemy Alias t dla tabeli i "a" oraz "b" jako nazwy kolumn
			 */
			String s299 = create
				.select()
				.from(DSL.values(
					DSL.row(1, 2),
					DSL.row(3, 4)
						).as("t", "a", "b"))
				.getSQL();
			
			
			Table<Record1<Long>> s31 = create
					.select(t1.ID)
					.from(t1)
					.asTable();
			Table<Record2<Long, Integer>> s32 = create
					.select(t1.ID, t1.INT_VAL1)
					.from(t1)
					.asTable();
			Table<Record> s33 = create
					.select()
					.from(t1)
					.asTable();
			
			/*
			 * Pytamy o wszystkie pola z podzapytania
			 */
			String s41 = create.select().from(s32).getSQL();
			/*
			 * Pytamy o konkretne pole z podzapytania. Rzutowanie nie jest konieczne, w przykładzie rozpoznał drugie pole jako Integer.
			 * Jeśli zapytamy po pole, które nie jest zwracane ("int_val2" to bład - "powinno być int_val1"), to... pominie podczas budowy bez rzucania błędem
			 */
			String s43 = create
					.select(s32.field(0, Long.class), s32.field("int_val2"))					
					.from(s32)					
					.getSQL();
			Record r44 = create
					.select(s32.field(0), s32.field("int_val1"))
					.from(s32)
					.limit(1)
					.fetchOne();
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void joinTest() {	
		ntw.inTrans(()->fillDivideTest());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			com.my.pl.jooq.db1.tables.Test2 t2 = TEST2.as("t2");
			
			String s1 = create.select()
					.from(t1.divideBy(t2).on(t1.ST_VAL2.eq(t2.ST_VAL2)).returning(t1.INT_VAL1))					
					.getSQL();	
			
			/*
			 * Iloczyn karteziański bez warunków
			 */
			String s2 = create.select()					
					.from(t1.crossJoin(t2))					
					.getSQL();	
			
			/*
			 * Iloczyn karteziański porównijący wszystkie kolumny i tych samych nazwach
			 */
			String s3 = create.select()					
					.from(t1.naturalJoin(t2))					
					.getSQL();	
			
			/*
			 * Iloczyn karteziański ze zdefiniowanym warunkiem (lub wieloma)
			 * INNER JOIN = JOIN, więc J używa tylko JOIN w obu przypadkach
			 */
			String s4 = create.select()					
					.from( t1.innerJoin(t2).on(t1.ID.eq(t2.ID)) )					
					.getSQL();				
			String s41 = create.select()					
					.from( t1.join(t2).on(t1.ID.eq(t2.ID)) )					
					.getSQL();		
			
			/* Iloczyn karteziański ze zdefiniowanym warunkiem (lub wieloma)
			 * , uzupełniający brakujące wiersze nullami po Lewej/Prawej stronie
			 * LEFT JOIN = LEFT OUTER JOIN, więc J używa tylko LEFT OUTER JOIN w obu przypadkach
			 */
			String s5 = create.select()					
					.from( t1.leftJoin(t2).on(t1.ID.eq(t2.ID)) )					
					.getSQL();	
			String s51 = create.select()					
					.from( t1.leftOuterJoin(t2).on(t1.ID.eq(t2.ID)) )					
					.getSQL();	
			String s52 = create.select()					
					.from( t1.rightJoin(t2).on(t1.ID.eq(t2.ID)) )					
					.getSQL();
			
			/* Iloczyn karteziański ze zdefiniowanym warunkiem (lub wieloma)
			 * , uzupełniający brakujące wiersze nullami po obu stronach
			 * FULL JOIN = FULL OUTER JOIN, więc J używa tylko FULL OUTER JOIN w obu przypadkach
			 */
			String s6 = create.select()					
					.from( t1.fullOuterJoin(t2).on(t1.ID.eq(t2.ID)) )					
					.getSQL();	
			String s61 = create.select()					
					.from( t1.fullJoin(t2).on(t1.ID.eq(t2.ID)) )					
					.getSQL();	
			
			/* leftSemiJoin - Oddaje t1, o ile jest odpowiedni (wg klauzuli ON) wpis w t2
			 * leftAntiJoin - Oddaje t1, o ile brak odpowiedniego wpisu (wg klauzuli ON) w t2
			 * Obie konstrukcje emulują za pomocą odpowiednich warunków WHERE
			 */
			String s7 = create.select()					
					.from( t1.leftSemiJoin(t2).on(t1.ID.eq(t2.ID)) )					
					.getSQL();	
			
			String s71 = create.select()					
					.from( t1.leftAntiJoin(t2).on(t1.ID.eq(t2.ID)) )					
					.getSQL();	
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	//@Test
	//@Transactional
	@Commit
	public void valuesTest() {	
		try (
				DSLContext create = dsl1;
				) {	
				
			
			/*
			 * Bezpośrednie odwołanie się do pola
			 */
			String s1 = create.select(DSL.field("t.a"))
					.from( DSL.values(DSL.row(1, "a"), DSL.row(2, "b")).as("t", "a", "b"))
					.getSQL();	
			/*
			 * Wyciągnięcie fragmentu zapytania do osobnego obiektu pozwala na 
			 */
			Table<?> tmp = DSL.values(DSL.row(1, "a"), DSL.row(2, "b")).as("t", "a", "b");
			
			String s2 = create.select(tmp.field("a"))
				.from(tmp)
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
	public void SubqueryDerivedTableTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			Table<?> t2 = DSL.values(DSL.row(1, "a"), DSL.row(2, "b")).as("t", "a", "b");
			//Alias a = new Alias();
			
			String s1 = create.select( DSL.field("tt.*") )
					.from( t1.asTable("tt").join(t2).on( DSL.field("tt.id").eq( DSL.field("t.a")) ) )
					.orderBy( DSL.field("tt.id") )
					.getSQL();	
			
			String s2 = create.select( DSL.field("tab.col") ).from( DSL.table("tab2") ).getSQL();
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void kolumnyTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			//Alias a = new Alias();
			
			/*
			 * BŁAD !
			 * withinGroupOrderBy - w Postgresie nie działa ORDER BY, więc SQL będzie błędny
			 */
			Field<String> field1 = DSL.listAgg(TEST1.INT_VAL1)
                    .withinGroupOrderBy(TEST1.ID.asc())
                    .over().partitionBy(TEST1.ST_VAL2);
			
			/*
			 * zrobi concat w losowej kolejności po wszystkich wierszach
			 */
			Field<String> field2 = DSL.groupConcat(TEST1.INT_VAL1).as("agg");
			//może spróbować skonstruować to zapytanie samemu używając window?
			/*
			 * OVER() not supported on GROUP_CONCAT
			 */
			//Field<String> field3 = DSL.groupConcat(TEST1.INT_VAL1)
			//		.over().partitionBy(TEST1.ST_VAL2);
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void castCoerceTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
		
			/*
			 * Typowy CAST SQL
			 * Nie ma możliwości ustalenia ilości znaków dla VARCHAR
			 */
			String s2 = create.select(TEST1.ID, TEST1.ID.cast(SQLDataType.VARCHAR))
					.from(TEST1)
					.getSQL();	
			/*
			 * Castowanie na typ zadanej kolumny
			 */
			String s3 = create.select(TEST1.ID, TEST1.ID.cast(TEST1.ST_VAL2))
					.from(TEST1)
					.getSQL();
			
			/*
			 * DSL.castNull castuje null na odpowiedni typ
			 */
			String s4 = create.select(DSL.castNull(TEST1.ID))
					.from(TEST1)
					.getSQL();	
			
			/*
			 * coerce(java.class...) pozwala na castowanie wewnątrz J - wymuszenie jakiegoś 
			 * typu, dzięki czemu nie będzie CAST w SQL.
			 * 
			 * Można tego uzywać przy porównywaniu kolumn o różnych typach (SQL pozwala a J nie pozwala).
			 * 
			 * Zalecana jest jednak ostrzożność, bo kontrola typów to dość ważna rzecz.
			 * 
			 * Przykład traktowania stringa jak waretości liczbowej
			 */
			String s5 = create.select()
					.from(TEST1)
					.where( TEST1.ID.eq(DSL.val("1").coerce(Long.class)) )
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
	public void sequenceTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {				
			/*
			 * Pomocne jest dołączenie jako STATIC IMPORT poniższego
			 * com.my.pl.jooq.db1.Sequences
			 * 
			 * currval w POSTGRES zadział tylko jeśli wartość została już ustalona, wpp da błąd.
			 * W testach ustalałem wartość używając "nextval"
			 */
			String s1 = create.select( TESTSEQUENCE.currval(), TESTSEQUENCE.nextval() ).getSQL();
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void functionTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			//Alias
			com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			Echo2 e2 = Echo2.ECHO2.as("e2");
			Echo3 e3 = Echo3.ECHO3.as("e3");

			//Alias a = new Alias();
			
			/*
			 * Pomocne jest dołączenie jako STATIC IMPORT poniższego
			 * com.my.pl.jooq.db1.Routines
			 * 
			 * echo1 zwraca INTEGER
			 * echo2 zwraca TABLE z polami stVal i longVal
			 * echo3 zwraca TABLE z polami stVal i intVal
			 * 
			 * SQL wszystkie pozwala łączyć joinem, ale J WYMAGA aby funckcja zwracała tabelę (row chyba też łyknie) np:
			 * from echo(2) as e join test1 as t1 on t1.id = e
			 * Minus dla J
			 */
			
			/*
			 * Proste pobranie wyniku funkcji w klauzuli SELECT
			 */
			String s1 = create.select(/*com.my.pl.jooq.db1.Routines.*/echo(7)).getSQL();
			
			/*
			 * Join funkcji zwracającej tabeleę z czymś innym
			 */
			String s2 = create.select(/*com.my.pl.jooq.db1.Routines.*/)
					.from(
							echo2(2).as(e2.getName())
							.join(t1).on( t1.ID.eq(e2.LONGVAL) )
					)
					.getSQL();	
					
			/*
			 * Przykład restrykcji przy porównywaniu Long i Integer. Konieczne jest castowanie
			 */
			String s3 = create.select(/*com.my.pl.jooq.db1.Routines.*/)
					.from(
							echo3(2).as(e3.getName())
							.join(t1)
								/* Poniższe castowanie jest konieczne bo Integer (e2.INTVAL) <> Long (t1.ID)
								 * To jest restrykcja J, bo SQL nie miał by problemu z taką konstrukcją
								 * Minus dla J :(
								 */
//								.on( t1.ID.equal(e3.INTVAL.cast(SQLDataType.BIGINT)) )
								.on( t1.ID.equal(e3.INTVAL.coerce(Long.class)) )
					)
					.getSQL();
					
			/*
			 * Poniższe nie zadziała w J, bo Integer (e2.INTVAL) <> Long (t1.ID)
			 * To jest restrykcja J, bo SQL nie ma z taką konstrukcją problemu
			 */
			/*
			String s4 = create.select()
					.from( 
							echo2(2).as(e2.getName())
							.join(t1)
								.on( e2.INTVAL.eq(t1.ID) ) 
					)
					.getSQL();
			*/
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void plainTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			
			/*
			 * Konieczne, aby toSQL() oddawało SQL z paramterami wypelnionymi wartościami.
			 * Jeśli używanmy fetch() nie jest to konieczne.
			 */
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			
			/*
			 * Plain w SELECT
			 */
			String s1 = create.select(DSL.field("t1.id")).from(TEST1.as("t1")).getSQL();
			

			/*
			 * Plain w SELECT
			 * 
			 * Wielokrotne uzycie tej samej wartości
			 * 
			 * Nie rozumiem co daje SQLDataType.VARCHAR
			 * 
			 * coerce(String.class) działa jedynie na sprawdzenia J i nie wpływan na typ zwracany przez zapytanie SQL. 
			 * Tu nalezy użyc cast 
			 */
			Field<Integer> f21 = val(3);
			Field<String> f22 = val(3).coerce(String.class);
			String s2 = create.select(DSL.field("{0} as str, {1}, {2}, {1}", SQLDataType.VARCHAR, 22, f21, f22)).from(TEST1.as("t1")).getSQL();

			/*
			 * Plain w FROM
			 */
			String s3 = create.select().from("test1 as t1").getSQL();
			
			
			/*
			 * Plain w parametrach
			 */
			
			/*
			 * Parametry podawane w kolejności
			 * KONIECZNIE ustawić StatementType.STATIC_STATEMENT, bo zamiast wartości dostaniemy "?"
			 */
			String s4 = create.select().from(TEST1.as("t1")).where("t1.id > ? and t1.id < ?", 1, 4).getSQL();
			
			/*
			 * Parametry numerowane
			 * KONIECZNIE ustawić StatementType.STATIC_STATEMENT, bo zamiast wartości dostaniemy "?"
			 */
			String s5 = create.select().from(TEST1.as("t1")).where("t1.id > {1} and t1.id < {0}", 4, 1).getSQL();
			
			/*
			 * Mieszanie parametrów
			 * KONIECZNIE ustawić StatementType.STATIC_STATEMENT, bo zamiast wartości dostaniemy "?"
			 */
			String s51 = create.select().from(TEST1.as("t1")).where("t1.id > {1} and t1.id < ? and {0}=? and {0}=?", 4, 1).getSQL();
			
			/*
			 * Podgląd o pobieranie parametrów
			 * Typ zwracany przez where() dziedziczy po Select<?>
			 */
			Select<?> sel5 = create.select().from(TEST1.as("t1")).where("t1.id > {1} and t1.id < {0} and {1}={1}", 4, 1);
			
			//s6 będzie miało wypelnione wartości parametrów, ale dostęp będzie też możliwy przez poniższe kontenery
			String s6 = sel5.getSQL();
			
			//Na liście będą 4 obiekty Integer o wartościach 1,4,1,1 - 
			//Kolejność zgodna z kolejnością parametrów a nie ich numerami, bo JDBC obsługuje 
			//tylko model z "?". "{}" to mechanizm J tłumaczony potem na "?"
			List<Object> p1 = sel5.getBindValues();
			
			//Mapa analigoczna do powyższej listy, ale jako key ma numery parametrów zaczynające się od 1
			Map<String, Param<?>> m1 = sel5.getParams();
			
			//Pobieranie konkretnego parametru
			Param<?> param = sel5.getParam("3");
			//Podmiana wartości setValue() / setConverted() została deprecated 

			/*
			 * Wartości hardcoded też są parametrami.
			 * Parametry bez nazw
			 * 
			 * Typ zwracany przez where() dziedziczy po Query
			 */
			Query q7 = create.select().from(TEST1).where(TEST1.ID.eq((long)2));
			//będzie WHERE id = 2
			String s71 = q7.getSQL();
			q7.bind(1, 3);
			//będzie WHERE id = 3
			String s72 = q7.getSQL();
			//BŁąD - brak parametru o numerze 2
			//q7.bind(2, 3);
			
			/*
			 * Parametry z nazwami
			 */
			Query q8 = create.select().from(TEST1).where( TEST1.ID.eq(DSL.param("idp", Long.class)) );
			//Jeśli nie ustawimy wartości to będzie
			//będzie WHERE id = null //Nie "IS NULL"
			String s8 = q8.getSQL();
			
			create.settings().setStatementType(StatementType.PREPARED_STATEMENT);
			
			Query q9 = create.select().from(TEST1).where( TEST1.ID.eq(DSL.param("idp", (long)2)).and( TEST1.ID.eq((long)2) ) );
			//będzie WHERE id = 2
			String s91 = q9.getSQL();
			q9.bind("idp", 3);
			//będzie WHERE id = 3
			String s92 = q9.getSQL();
			
			/*
			 * Generowanie stringa z parametrami
			 */
			//Param type not supported ???
			//String s93 = q9.getSQL(ParamType.FORCE_INDEXED);
			String s94 = q9.getSQL(ParamType.INDEXED);//"public"."test1"."id" = ? and "public"."test1"."id" = ?
			String s95 = q9.getSQL(ParamType.INLINED);//"public"."test1"."id" = 3 and "public"."test1"."id" = 22
			String s96 = q9.getSQL(ParamType.NAMED);//"public"."test1"."id" = :idp and "public"."test1"."id" = :2
			String s97 = q9.getSQL(ParamType.NAMED_OR_INLINED);//"public"."test1"."id" = :idp and "public"."test1"."id" = 22
			
			/*
			 * Wstawianie parametru wyrażonego obiektem J
			 */
			Field<Long> idl = DSL.val((long)4);
			String s10 = create.select().from(TEST1).where("test1.id = {0}", idl).getSQL(ParamType.INLINED);
			
			/*
			 * Odłączenie obiektu od kontekstu 
			 * - pozbawia kontrolu nad sposobm formatowania wynikowego SQL
			 * - powoduje BŁąD na fetch()/execute()
			 */
			q9.detach();
			String s11 = q9.getSQL();
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//@Test
	//@Transactional
	@Commit
	public void fetchBasicTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			Select<?> sel1 = create.select().from(TEST1);
			Select<?> sel11 = create.select(TEST1.asterisk()).from(TEST1);
			Select<?> sel12 = create.select(TEST1.asterisk(), DSL.val(12.5).as("cst")).from(TEST1);
			Select<?> sel13 = create.select().from(TEST1.leftJoin(TEST2).on(TEST1.ID.eq(TEST2.ID)));
			Select<?> sel2 = create.select().from(TEST1).limit(1);
			//Odda obiekty Test1Record
			Result<?> r11 = sel1.fetch();
			//Odda listę typu z kolumny
			List<Long> r12 = sel1.fetch(TEST1.ID);
			//Odda listę nieznanego typu
			List<?> r13 = sel1.fetch(0);
			//Odda listę wybranego typu
			List<Long> r131 = sel1.fetch(0, Long.class);
			//Zadziała konwersja, ale ODRADZAM
			List<Double> r132 = sel1.fetch(0, Double.class);
			//Odda BŁąD konwercji
			//List<HashMap> r133 = sel1.fetch(0, HashMap.class);
			//Odda listę nieznanego typu
			List<?> r14 = sel1.fetch("id");
			//Odda listę wybranego typu
			List<Long> r141 = sel1.fetch("id", Long.class);
			
			//Jw., ale odda Array zamiast list  
			Object[] r15 = sel1.fetchArray();
			Long[] r16 = sel1.fetchArray(TEST1.ID);
			Object[] r17 = sel1.fetchArray(0);
			Long[] r171 = sel1.fetchArray(0, Long.class);
			Object[] r18 = sel1.fetchArray("id");
			Long[] r181 = sel1.fetchArray("id", Long.class);
			
			//Jw., ale odda Tylko jeden obiekt zamiast wielu  
			Object r19 = sel2.fetchOne();
			Long r20 = sel2.fetchOne(TEST1.ID);
			Object r21 = sel2.fetchOne(0);
			Long r211 = sel2.fetchOne(0, Long.class);
			Object r22 = sel2.fetchOne("id");
			Long r221 = sel2.fetchOne("id", Long.class);
						
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Domyślne mapowanie
	 * J porównuje nazy kolumn z danymi z obiektu i wypełnia obiekt jeśli nazwy się zgadzają
	 * - jeśli jest choć jedna adnotacja JPA, to szyka adnotacji "name" (czyli także "Id" ect.)
	 * - jeśli brak adnotacji to szuka domyślnego, atrybutu lub settera
	 * - jeśli brak powyższych, to J szuka "najlepszego" konstruktora	 
	 * */
	
	//@Test
	//@Transactional
	@Commit
	public void fetchMapsTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			Select<?> sel1 = create.select().from(TEST1);
			Select<?> sel11 = create.select(TEST1.asterisk()).from(TEST1);
			Select<?> sel12 = create.select(TEST1.asterisk(), DSL.val(12.5).as("cst")).from(TEST1);
			Select<?> sel13 = create.select().from(TEST1.leftJoin(TEST2).on(TEST1.ID.eq(TEST2.ID)));
			Select<?> sel2 = create.select().from(TEST1).limit(1);
					
			/* 
			 * key - kolumna TEST1.ID, typ znany.
			 * val - wartość kolumny TEST1.ST_VAL2, typ znany.
			 */
			List<Map<String, Object>> r1 = sel1.fetchMaps();
			List<Map<String, Object>> r11 = sel11.fetchMaps();
			List<Map<String, Object>> r12 = sel12.fetchMaps();
			List<Map<String, Object>> r13 = sel13.fetchMaps();
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	//@Transactional
	@Commit
	public void fetchMapTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			Select<?> sel1 = create.select().from(TEST1);
			Select<?> sel11 = create.select(TEST1.asterisk()).from(TEST1);
			Select<?> sel12 = create.select(TEST1.asterisk(), DSL.val(12.5).as("cst")).from(TEST1);
			Select<?> sel13 = create.select().from(TEST1.leftJoin(TEST2).on(TEST1.ID.eq(TEST2.ID)));
			Select<?> sel2 = create.select().from(TEST1).limit(1);
						
			/*
			 * key - hibernetowy Test1. J mapuje
			 * val - Dla select() z 1 tabeli pola Test1Record, wpp RecordImpl z array "values"
			 */
			Map<Test1, ?> r23 = sel1.fetchMap(Test1.class);
			Map<Test1, ?> r231 = sel11.fetchMap(Test1.class);
			Map<Test1, ?> r232 = sel12.fetchMap(Test1.class);
			Map<Test1, ?> r233 = sel13.fetchMap(Test1.class);
			/* 
			 * key - kolumna 1, typ nieznany. Tutaj będzie to Integer
			 * val - RecordImpl z array "values"
			 */
			Map<?, ?> r24 = sel1.fetchMap(1);
			Map<?, ?> r241 = sel11.fetchMap(1);
			Map<?, ?> r242 = sel12.fetchMap(1);
			Map<?, ?> r243 = sel13.fetchMap(1);
			/* 
			 * key - kolumna TEST1.ST_VAL2, typ znany.
			 * val - RecordImpl z array "values"
			 * 
			 * Wystąpi BŁąD - pole wybrane na klucz ma wartości powtarzające się
			 */
			//Map<String, ?> r244 = sel1.fetchMap(DSL.field(TEST1.ST_VAL2));
			//Map<String, ?> r245 = sel11.fetchMap(DSL.field(TEST1.ST_VAL2));
			//Map<String, ?> r246 = sel12.fetchMap(DSL.field(TEST1.ST_VAL2));
			//Map<String, ?> r247 = sel13.fetchMap(DSL.field(TEST1.ST_VAL2));
			
			Map<Long, ?> r244 = sel1.fetchMap(TEST1.ID);
			
			/* 
			 * key - kolumna TEST1.ID, typ znany.
			 * val - RecordImpl z array "values", bo taka jest budowa obiektu. J mapuje
			 *      Jeśli jest wiele kolumn o tej samej nazwie, to weżmie ostatnią z klauzuli SELECT
			 */
			Map<Long, Test1Record> r25 = sel1.fetchMap(TEST1.ID, Test1Record.class);
			Map<Long, Test1Record> r251 = sel11.fetchMap(TEST1.ID, Test1Record.class);
			Map<Long, Test1Record> r252 = sel12.fetchMap(TEST1.ID, Test1Record.class);
			Map<Long, Test1Record> r253 = sel13.fetchMap(TEST1.ID, Test1Record.class);
			
			/* 
			 * key - kolumna TEST1.ID, typ znany.
			 * val - RecordImpl z array "values", bo taka jest budowa obiektu. J mapuje do obiektu JPA
			 *       Jeśli jest wiele kolumn o tej samej nazwie, to weżmie ostatnią z klauzuli SELECT
			 */
			Map<Long, Test1> r26 = sel1.fetchMap(TEST1.ID, Test1.class);
			Map<Long, Test1> r261 = sel11.fetchMap(TEST1.ID, Test1.class);
			Map<Long, Test1> r262 = sel12.fetchMap(TEST1.ID, Test1.class);
			Map<Long, Test1> r263 = sel13.fetchMap(TEST1.ID, Test1.class);
			
			/* 
			 * key - kolumna TEST1.ID, typ znany.
			 * val - RecordImpl z array "values", bo taka jest budowa obiektu. J mapuje do POJO bez adnotacji
			 *       Jeśli jest wiele kolumn o tej samej nazwie, to weżmie ostatnią z klauzuli SELECT
			 */
			Map<Long, Test1Res> r29 = sel1.fetchMap(TEST1.ID, Test1Res.class);
			Map<Long, Test1Res> r291 = sel11.fetchMap(TEST1.ID, Test1Res.class);
			Map<Long, Test1Res> r292 = sel12.fetchMap(TEST1.ID, Test1Res.class);
			Map<Long, Test1Res> r293 = sel13.fetchMap(TEST1.ID, Test1Res.class);
			
			/* 
			 * key - kolumna TEST1.ID, typ znany.
			 * val - wartość kolumny TEST1.ST_VAL2, typ znany.
			 */
			Map<Long, String> r27 = sel1.fetchMap(TEST1.ID, TEST1.ST_VAL2);
			Map<Long, String> r271 = sel11.fetchMap(TEST1.ID, TEST1.ST_VAL2);
			Map<Long, String> r272 = sel12.fetchMap(TEST1.ID, TEST1.ST_VAL2);
			Map<Long, String> r273 = sel13.fetchMap(TEST1.ID, TEST1.ST_VAL2);
			
			/* 
			 * key - kolumna TEST1.ID, typ znany.
			 * val - wartość kolumny TEST1.ST_VAL2 (wpisanej z palca), typ nieznany.
			 * UWAGA! nie budowac nazw ręcznie, tylko używć name(...) wpp powie, ze nie zna 
			 * pola, nawet jeśli ręcznie wybudowane stringi wydają się ok.
			 */
			Map<Long, ?> r28 = sel1.fetchMap(TEST1.ID, DSL.field(name("test1","st_val2")));
			//Spowoduje BŁąD, bo nie ma kolumny o nazwie "ggg"
			//Map<Long, ?> r281 = sel11.fetchMap(TEST1.ID, DSL.field("ggg"));
			/* 
			 * key - kolumna TEST1.ID, typ znany.
			 * val - wartość kolumny TEST1.ST_VAL2 (wpisanej z palca), typ znany, bo podany przy definiowaniu pola.
			 */
			Map<Long, String> r281 = sel11.fetchMap(TEST1.ID, DSL.field(name("test1","st_val2"), String.class));
			Map<Long, ?> r282 = sel12.fetchMap(TEST1.ID, DSL.field(name("cst")));
			/*
			 * W tym przypadku podanie nazwy pola z palca zadziała, ale tylko 
			 * dlatego, że tak samo (zxpalca) została podana w klauzuli SELECT
			 */
			Map<Long, ?> r2821 = sel12.fetchMap(TEST1.ID, DSL.field("cst"));
			
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	//@Transactional
	@Commit
	public void fetchIntoPojoTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			Select<?> sel1 = create.select().from(TEST1);
			Select<?> sel11 = create.select(TEST1.asterisk()).from(TEST1);
			Select<?> sel12 = create.select(TEST1.ID, DSL.val(12).as(name("rv"))).from(TEST1);
			Select<?> sel13 = create.select().from(TEST1.leftJoin(TEST2).on(TEST1.ID.eq(TEST2.ID)));
			Select<?> sel2 = create.select().from(TEST1).limit(1);
			
			/*
			 * J mapuje
			 * Jeśli jest wiele kolumn o tej samej nazwie, to wezmie ostatnią z klauzuli SELECT
			 */
			List<Test1> r1 = sel1.fetchInto(Test1.class);
			List<Test1> r11 = sel11.fetchInto(Test1.class);
			List<Test1> r12 = sel12.fetchInto(Test1.class);
			//Wezmie wartości z test2, bo ona jest w SELECT po test1
			List<Test1> r13 = sel13.fetchInto(Test1.class);
						
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	//@Transactional
	@Commit
	public void defaultTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			
			
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
