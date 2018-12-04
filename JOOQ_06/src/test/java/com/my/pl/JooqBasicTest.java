/*
 *  CREATE SEQUENCE public."TESTSEQUENCE"
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

	ALTER SEQUENCE public."TESTSEQUENCE"
    OWNER TO postgres;
    
    
    CREATE OR REPLACE FUNCTION public.echo(input integer)
	 RETURNS integer
	 LANGUAGE plpgsql
	AS $function$
	BEGIN
	    RETURN INPUT;
	END;
	$function$
	
	CREATE OR REPLACE FUNCTION public.echo2(src integer)
	 RETURNS TABLE(stval character varying, longval bigint)
	 LANGUAGE plpgsql
	AS $function$
	begin
		stval = cast(src as varchar);
		longVal = src;
	    RETURN NEXT;
	END;
	$function$
	
	CREATE OR REPLACE FUNCTION public.echo3(src integer)
	 RETURNS TABLE(stval character varying, intval integer)
	 LANGUAGE plpgsql
	AS $function$
	begin
		stval = cast(src as varchar);
		intVal = src;
	    RETURN NEXT;
	END;
	$function$
	
	CREATE OR REPLACE FUNCTION public.f1()
	 RETURNS trigger
	 LANGUAGE plpgsql
	AS $function$
	begin
	 NEW.st_val2 := 'x';
	 RETURN NEW;
	END;
	$function$
 */

package com.my.pl;


import static com.my.pl.jooq.db3.Keys.*;
import static com.my.pl.jooq.db3.Routines.*;
import static com.my.pl.jooq.db3.Sequences.*;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.val;

import  org.jooq.Converters;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.assertj.core.util.Arrays;
import org.jooq.CommonTableExpression;
import org.jooq.Cursor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.FieldLike;
import org.jooq.Loader;
import org.jooq.LoaderError;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.WindowDefinition;
import org.jooq.conf.ParamType;
import org.jooq.conf.StatementType;
import org.jooq.exception.DataChangedException;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.impl.DefaultRecordListenerProvider;
import org.jooq.impl.DefaultVisitListenerProvider;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.util.postgres.PostgresDataType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.jooq.RecordValueReader;
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
import com.my.pl.db1.dao.Test11Db1Dao;
import com.my.pl.db1.dao.Test1Db1Dao;
import com.my.pl.db1.dao.Test2Db1Dao;
import com.my.pl.db1.dao.Test4Db1Dao;
import com.my.pl.db1.dao.Test5Db1Dao;
import com.my.pl.db1.domain.Test1;
import com.my.pl.db1.domain.Test11;
import com.my.pl.db1.domain.Test2;
import com.my.pl.db1.domain.Test4;
import com.my.pl.db1.domain.Test41;
import com.my.pl.db1.domain.Test5;
import com.my.pl.db1.domain.Test51;
import com.my.pl.jooq.db3.routines.Lastlpope5;
import com.my.pl.jooq.db3.routines.Lastlpopefnc1;
import com.my.pl.jooq.db3.udt.Tlastlpopebigint;
import com.my.pl.jooq.db3.udt.records.TlastlpopebigintRecord;
import com.my.pl.utils.ExtModelMapper;
import com.my.pl.utils.IntToStringConverter;
import com.my.pl.utils.MapperUtils;
import com.my.pl.utils.NewTransactionWrapper;
import com.my.pl.utils.PrefixValueReader;
import com.my.pl.utils.RecordStream;
import com.my.pl.utils.RecordStreamImpl;

import lombok.Getter;
import lombok.Setter;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class) 
@ContextConfiguration(classes = { PersistenceContextDb1.class, PersistenceContextDb2.class })
public class JooqBasicTest {
	
	@Getter
	@Setter
	private class Test1Res {
		private Long id;
		public Double cst;
	}
	
	@Autowired
	Test1Db1Dao t1d;
	@Autowired
	Test11Db1Dao t11d;
	@Autowired
	Test2Db1Dao t2d;
	@Autowired
	Test4Db1Dao t4d;
	@Autowired
	Test5Db1Dao t5d;
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
	MapperUtils mu;	
	
	private void fillTest1() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);		
		t1d.save(t1);
	}
	
	private void fillTest1_11() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);		
		t1d.save(t1);
		
		Test11 t11 = new Test11();
		t11.setId(1);
		t11.setIntVal1(11);		
		t11d.save(t11);
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
	
	private void fillTest4_5() {
		Test4 t4 = new Test4();
		t4.setId(1);
		t4.setIntVal1(11);	
		t4d.save(t4);
			Test41 t41 = new Test41();
			t41.setId(11);
			t41.setIntVal1(111);
			t4.getSubObjSet().add(t41);
			//t41.setTest4(t4);
			t41 = new Test41();
			t41.setId(12);
			t41.setIntVal1(121);
			t4.getSubObjSet().add(t41);
			//t41.setTest4(t4);
		t4d.save(t4);
		
		t4 = new Test4();
		t4.setId(2);
		t4.setIntVal1(12);	
		t4d.save(t4);
			t41 = new Test41();
			t41.setId(21);
			t41.setIntVal1(211);
			t4.getSubObjSet().add(t41);
			//t41.setTest4(t4);
			t41 = new Test41();
			t41.setId(22);
			t41.setIntVal1(221);
			t4.getSubObjSet().add(t41);
			//t41.setTest4(t4);
		t4d.save(t4);

		
		Test5 t5 = new Test5();
		t5.setId(1);
		t5.setIntVal1(11);		
			Test51 t51 = new Test51();
			t51.setId(11);
			t51.setIntVal1(111);
			t5.getSubObjSet().add(t51);
			t51.setTest5(t5);
			t51 = new Test51();
			t51.setId(12);
			t51.setIntVal1(121);
			t5.getSubObjSet().add(t51);
			t51.setTest5(t5);
		t5d.save(t5);
		
		t5 = new Test5();
		t5.setId(2);
		t5.setIntVal1(12);	
			t51 = new Test51();
			t51.setId(21);
			t51.setIntVal1(211);
			t5.getSubObjSet().add(t51);
			t51.setTest5(t5);
			t51 = new Test51();
			t51.setId(22);
			t51.setIntVal1(221);
			t5.getSubObjSet().add(t51);
			t51.setTest5(t5);
		t5d.save(t5);
		
	}
	
	private void fillTest1Test2() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);
		t1.setStVal2("a");
		t1d.save(t1);
		
		t1 = new Test1();
		t1.setId(2);
		t1.setIntVal1(12);		
		t1.setStVal2("b");
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
			//LastLpOpe5 l = Lastlpope5.RETURN_VALUE
			//com.my.pl.jooq.db1.tables.Test1 t1 = TEST1.as("t1");
			
			
			
			/*
			 * Proste wywołanie funkcji
			 */
			
			Table<Record2<Integer, Integer>> tmp = create
				.select(DSL.val(1).as("a"),DSL.val(1).as("b"))
				.union(
						create.select(DSL.val(2).as("a"),DSL.val(1).as("b"))
						)
				.union(
						create.select(DSL.val(1).as("a"),DSL.val(2).as("b"))
						)
				.union(
						create.select(DSL.val(2).as("a"),DSL.val(3).as("b"))
						)
				.union(
						create.select(DSL.val(2).as("a"),DSL.val(2).as("b"))
						).asTable();
			
			
			
			Field<Long> fa = tmp.field(0, Long.class).as("a");
			Field<Integer> fb = tmp.field(1, Integer.class).as("b");
			
			com.my.pl.jooq.db3.udt.Tlastlpopebigint r0= com.my.pl.jooq.db3.udt.Tlastlpopebigint.TLASTLPOPEBIGINT;
		
			TlastlpopebigintRecord r1 = new TlastlpopebigintRecord();

			
			
			String s01 = create.select(lastlpope5( 
					create.newRecord(r0).
				)).from(tmp).getSQL();

			

			
			/*
			 * Proste pobranie wyniku funkcji w klauzuli SELECT
			 */
//			String s1 = create.select(/*com.my.pl.jooq.db1.Routines.*/echo(7)).getSQL();
			
			/*
			 * Join funkcji zwracającej tabeleę z czymś innym
			 */
//			String s2 = create.select(/*com.my.pl.jooq.db1.Routines.*/)
//					.from(
//							echo2(2).as(e2.getName())
//							.join(t1).on( t1.ID.eq(e2.LONGVAL) )
//					)
//					.getSQL();	
					
			/*
			 * Przykład restrykcji przy porównywaniu Long i Integer. Konieczne jest castowanie
			 */
//			String s3 = create.select(/*com.my.pl.jooq.db1.Routines.*/)
//					.from(
//							echo3(2).as(e3.getName())
//							.join(t1)
//								/* Poniższe castowanie jest konieczne bo Integer (e2.INTVAL) <> Long (t1.ID)
//								 * To jest restrykcja J, bo SQL nie miał by problemu z taką konstrukcją
//								 * Minus dla J ???
//								 */
////								.on( t1.ID.equal(e3.INTVAL.cast(SQLDataType.BIGINT)) )
//								.on( t1.ID.equal(e3.INTVAL.coerce(Long.class)) )
//					)
//					.getSQL();
					
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
