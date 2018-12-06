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


import static com.my.pl.jooq.db3.Routines.lastlpope1;
import static com.my.pl.jooq.db3.Routines.lastlpope2;
import static com.my.pl.jooq.db3.Routines.lastlpope3;
import static com.my.pl.jooq.db3.Routines.lastlpope4;
import static com.my.pl.jooq.db3.Routines.lastlpope5;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.persistence.EntityManager;

import org.jooq.AggregateFunction;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Record17;
import org.jooq.Record2;
import org.jooq.ResultQuery;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.SQLDataType;
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
import com.my.pl.jooq.db3.tables.AkwPodzial;
import com.my.pl.jooq.db3.tables.DbMetadane;
import com.my.pl.jooq.db3.tables.UmubezpPolisaoper;
import com.my.pl.jooq.db3.tables.UmubezpProduktoper;
import com.my.pl.jooq.db3.tables.UmubezpSkladowaoper;
import com.my.pl.jooq.db3.tables.UmubezpSkladowaoperanul;
import com.my.pl.jooq.db3.udt.records.TlastlpopebigintRecord;
import com.my.pl.jooq.db3.udt.records.TlastlpopedateRecord;
import com.my.pl.jooq.db3.udt.records.TlastlpopedblRecord;
import com.my.pl.jooq.db3.udt.records.TlastlpopedtstatdateRecord;
import com.my.pl.jooq.db3.udt.records.TlastlpopedtstatintegerRecord;
import com.my.pl.jooq.db3.udt.records.TlastlpopedtstattextRecord;
import com.my.pl.jooq.db3.udt.records.TlastlpopedtstattunitRecord;
import com.my.pl.jooq.db3.udt.records.TlastlpopesmallintRecord;
import com.my.pl.jooq.db3.udt.records.TlastlpopetextRecord;
import com.my.pl.utils.MapperUtils;
import com.my.pl.utils.NewTransactionWrapper;

import lombok.Getter;
import lombok.Setter;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class) 
@ContextConfiguration(classes = { PersistenceContextDb1.class, PersistenceContextDb2.class })
public class JooqAnalitTest {
	
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
	
		private static final String separator = "_";
		
		public String getTableAlias(Table<?> table, String nr) {
			return table.getName() + separator +nr;
		}
		
		public Function<Field<?>, String> getPrefixFunction(String p){
			Function<Field<?>, String> pr = f -> {
				return p + separator + f.getName();
			};
			return pr;
		}
		
		public Table<?> getAliasedTable(Table<?> t, String nr) {
			return t.as(getTableAlias(t, nr), getPrefixFunction(getTableAlias(t, nr)));
		}
		
		public <T> Field<T> getAliasedField(Field<T> f, String nr) {
			return f.as(getPrefixFunction(nr));
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
	
		public <R> Field<R> getTlastlpopeRecord2(Field<?> fld1, Field<?> fld2, Class<R> clazz){
			return DSL.cast(DSL.rowField(DSL.row(fld1, fld2)), clazz);
		}
		public <R> Field<R> getTlastlpopeRecord3(Field<?> fld1, Field<?> fld2, Field<?> fld3,Class<R> clazz){
			return DSL.cast(DSL.rowField(DSL.row(fld1, fld2, fld3)), clazz);
		}
		public <R> Field<R> getTlastlpopeRecord4(Field<?> fld1, Field<?> fld2, Field<?> fld3,Field<?> fld4,Class<R> clazz){
			return DSL.cast(DSL.rowField(DSL.row(fld1, fld2, fld3, fld4)), clazz);
		}
		public <R> Field<R> getTlastlpopeRecord5(Field<?> fld1, Field<?> fld2, Field<?> fld3,Field<?> fld4,Field<?> fld5,Class<R> clazz){
			return DSL.cast(DSL.rowField(DSL.row(fld1, fld2, fld3, fld4, fld5)), clazz);
		}
		public <R> Field<R> getTlastlpopeRecord6(Field<?> fld1, Field<?> fld2, Field<?> fld3,Field<?> fld4,Field<?> fld5,Field<?> fld6,Class<R> clazz){
			return DSL.cast(DSL.rowField(DSL.row(fld1, fld2, fld3, fld4, fld5, fld6)), clazz);
		}

		
		/*
		 * oryginalnie lpOperacja to Integer, ale przekazywane sa tam także BigInt (Long), więc 
		 * my przyjmujemy Long i najwyżej padnie SQL podczas wykonywania, ale i tak by padł.
		 * To pokazuje, ze kroki podnoszenia są złe, bo przekazyjemy coś, co może przekraczać zakres.
		 */
		public <T1 extends Field<String>, T2 extends Field<Long>> Field<TlastlpopetextRecord> getTlastlpopetextRecord(T1 stVal, T2 lpOperacja){
			return DSL.cast(DSL.rowField(DSL.row(stVal, lpOperacja)), TlastlpopetextRecord.class);
		}
		public <T1 extends Field<Date>, T2 extends Field<Long>> Field<TlastlpopedateRecord> getTlastlpopedateRecord(T1 val, T2 lpOperacja){
			return DSL.cast(DSL.rowField(DSL.row(val, lpOperacja)), TlastlpopedateRecord.class);
		}
		public <T1 extends Field<Short>, T2 extends Field<Long>> Field<TlastlpopesmallintRecord> getTlastlpopesmallintRecord(T1 val, T2 lpOperacja){
			return DSL.cast(DSL.rowField(DSL.row(val, lpOperacja)), TlastlpopesmallintRecord.class);
		}
		public <T1 extends Field<Double>, T2 extends Field<Long>> Field<TlastlpopedblRecord> getTlastlpopedblRecord(T1 val, T2 lpOperacja){
			return DSL.cast(DSL.rowField(DSL.row(val, lpOperacja)), TlastlpopedblRecord.class);
		}
		public <T1 extends Field<Long>, T2 extends Field<Long>> Field<TlastlpopebigintRecord> getTlastlpopebigintRecord(T1 val, T2 lpOperacja){
			return DSL.cast(DSL.rowField(DSL.row(val, lpOperacja)), TlastlpopebigintRecord.class);
		}
//		public Field<TlastlpopedtstattextRecord> getTlastlpopedtstattextRecord(Field<String> pierwsza, Field<String> ostatnia, Field<Integer> lpOperacja, Field<Integer> lpOperacjaPo, Field<Date> dtStatWarunek, Field<Date> dtStat){
//			return DSL.cast(DSL.rowField(DSL.row(pierwsza, ostatnia, lpOperacja, lpOperacjaPo, dtStatWarunek, dtStat)), TlastlpopedtstattextRecord.class);
//		}
//		public Field<TlastlpopedtstatdateRecord> getTlastlpopedtstatdateRecord(Field<String> pierwsza, Field<String> ostatnia, Field<Integer> lpOperacja, Field<Integer> lpOperacjaPo, Field<Date> dtStatWarunek, Field<Date> dtStat){
//			return DSL.cast(DSL.rowField(DSL.row(pierwsza, ostatnia, lpOperacja, lpOperacjaPo, dtStatWarunek, dtStat)), TlastlpopedtstatdateRecord.class);
//		}
//		public Field<TlastlpopedtstatintegerRecord> getTlastlpopedtstatintegerRecord(Field<Integer> pierwsza, Field<Integer> ostatnia, Field<Integer> lpOperacja, Field<Integer> lpOperacjaPo, Field<Date> dtStatWarunek, Field<Date> dtStat){
//			return DSL.cast(DSL.rowField(DSL.row(pierwsza, ostatnia, lpOperacja, lpOperacjaPo, dtStatWarunek, dtStat)), TlastlpopedtstatintegerRecord.class);
//		}
//		public Field<TlastlpopedtstattunitRecord> getTlastlpopedtstattunitRecord(Field<Double> pierwsza, Field<Double> ostatnia, Field<Integer> lpOperacja, Field<Integer> lpOperacjaPo, Field<Date> dtStatWarunek, Field<Date> dtStat){
//			return DSL.cast(DSL.rowField(DSL.row(pierwsza, ostatnia, lpOperacja, lpOperacjaPo, dtStatWarunek, dtStat)), TlastlpopedtstattunitRecord.class);
//		}
		
//		public Field<TlastlpopebigintRecord> getTlastlpopebigintRecord(Field<Long> val, Field<Integer> lpOperacja){
//			return DSL.cast(DSL.rowField(DSL.row(val, lpOperacja)), TlastlpopebigintRecord.class);
//		}
	
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
			
			Table<Record2<Long, Integer>> tmpA = create
				.select(DSL.val(1).cast(Long.class).as("a"),DSL.val(1).cast(Integer.class).as("b"))
				.union(
						create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(1).cast(Integer.class).as("b"))
						)
				.union(
						create.select(DSL.val(1).cast(Long.class).as("a"),DSL.val(2).cast(Integer.class).as("b"))
						)
				.union(
						create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(3).cast(Integer.class).as("b"))
						)
				.union(
						create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(2).cast(Integer.class).as("b"))
						).asTable("tmp","aa","bb")
				;
			Table<Record2<Long, Integer>> tmpB = create
					.select(DSL.val(1).cast(Long.class).as("a"),DSL.val(1).cast(Integer.class).as("b"))
					.union(
							create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(1).cast(Integer.class).as("b"))
							)
					.union(
							create.select(DSL.val(1).cast(Long.class).as("a"),DSL.val(2).cast(Integer.class).as("b"))
							)
					.union(
							create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(3).cast(Integer.class).as("b"))
							)
					.union(
							create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(2).cast(Integer.class).as("b"))
							).asTable("tmp","aa","bb")
					;
			
			//CustomQueryPart a = new CustomQueryPart();
			
			Field<Long> fa1 = tmpA.field(0, Long.class);//.as("aaa");
			Field<Integer> fb1 = tmpA.field(1, Integer.class);//.as("bbb");		
			Table<?> tmp2 = getAliasedTable(tmpA, "");
			Field<Long> fa21 = tmp2.field(0, Long.class);//.as("aaa");
			Field<Integer> fb21 = tmp2.field(1, Integer.class);//.as("bbb");

			DbMetadane d = DbMetadane.DB_METADANE;
			
			String s03 = create.select(
					lastlpope5( DSL.cast(DSL.rowField(DSL.row(fa1,fb1)), TlastlpopebigintRecord.class) )
					)
					.from(tmpA)
					.getSQL();
			
			String s04 = create.select(
					lastlpope5( getTlastlpopebigintRecord(fa21, fb21.cast(Long.class)) )					
					)
					.from(tmp2)
					.getSQL();
			
			String s08 = create.select(
					d.IDKLUCZ, 
					d.STWARTOSC,
					lastlpope5( DSL.cast(DSL.rowField(DSL.row(fa1,fb1)), TlastlpopebigintRecord.class) ))
				.from(tmpA.crossJoin(d))
				.groupBy(d.IDKLUCZ, d.STWARTOSC)
				.getSQL();
								
			String s09 = create.select(
						d.IDKLUCZ, 
						d.STWARTOSC, 
						lastlpope5( DSL.cast(DSL.rowField(DSL.row(fa21,fb21)), TlastlpopebigintRecord.class) ))
					.from(tmp2.crossJoin(d))
					.groupBy(d.IDKLUCZ, d.STWARTOSC)
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
	public void function2Test() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			// Pozwala na sworzenie SQL z wpisanymi na stałe wartościami - użyte TYLKO w celu debugowania
			create.settings().setStatementType(StatementType.STATIC_STATEMENT);
			
			Table<Record2<Long, Integer>> tmpA = create
					.select(DSL.val(1).cast(Long.class).as("a"),DSL.val(1).cast(Integer.class).as("b"))
					.union(
							create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(1).cast(Integer.class).as("b"))
							)
					.union(
							create.select(DSL.val(1).cast(Long.class).as("a"),DSL.val(2).cast(Integer.class).as("b"))
							)
					.union(
							create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(3).cast(Integer.class).as("b"))
							)
					.union(
							create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(2).cast(Integer.class).as("b"))
							).asTable("tmp","aa","bb")
					;
			Table<Record2<Long, Integer>> tmpB = create
					.select(DSL.val(1).cast(Long.class).as("a"),DSL.val(1).cast(Integer.class).as("b"))
					.union(
							create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(1).cast(Integer.class).as("b"))
							)
					.union(
							create.select(DSL.val(1).cast(Long.class).as("a"),DSL.val(2).cast(Integer.class).as("b"))
							)
					.union(
							create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(3).cast(Integer.class).as("b"))
							)
					.union(
							create.select(DSL.val(2).cast(Long.class).as("a"),DSL.val(2).cast(Integer.class).as("b"))
							).asTable("tmp","aa","bb")
					;
			
			//CustomQueryPart a = new CustomQueryPart();
			
			Field<Long> fa1 = tmpA.field(0, Long.class);//.as("aaa");
			Field<Integer> fb1 = tmpA.field(1, Integer.class);//.as("bbb");		
			Table<?> tmp2 = getAliasedTable(tmpA, "");
			Field<Long> fa21 = tmp2.field(0, Long.class);//.as("aaa");
			Field<Integer> fb21 = tmp2.field(1, Integer.class);//.as("bbb");
			
			DbMetadane d = DbMetadane.DB_METADANE;
			
			String s03 = create.select(
					lastlpope5( DSL.cast(DSL.rowField(DSL.row(fa1,fb1)), TlastlpopebigintRecord.class) )
					)
					.from(tmpA)
					.getSQL();
			
			String s04 = create.select(
					lastlpope5( getTlastlpopebigintRecord(fa21, fb21.cast(Long.class)) )					
					)
					.from(tmp2)
					.getSQL();
			
			String s08 = create.select(
					d.IDKLUCZ, 
					d.STWARTOSC,
					lastlpope5( DSL.coerce(DSL.rowField(DSL.row(fa1,fb1)), TlastlpopebigintRecord.class) )
					)
					.from(tmpA.crossJoin(d))
					.groupBy(d.IDKLUCZ, d.STWARTOSC)
					.getSQL();
			
			String s09 = create.select(
					d.IDKLUCZ, 
					d.STWARTOSC, 
					lastlpope5( DSL.cast(DSL.rowField(DSL.row(fa21,fb21)), TlastlpopebigintRecord.class) ))
					.from(tmp2.crossJoin(d))
					.groupBy(d.IDKLUCZ, d.STWARTOSC)
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
	public void strConcatTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			create.configuration().settings().setStatementType(StatementType.STATIC_STATEMENT);
			AkwPodzial ap = AkwPodzial.AKW_PODZIAL.as("ap");
			String s1 = create.select(ap.IDKATEGORIA.concat(ap.IDPODZIAL).concat(ap.IDSYMBOL).concat("coś").as("jakasKolumna")).from(ap).getSQL();
			String s2 = create.select(ap.IDKATEGORIA.concat(ap.IDPODZIAL.concat(ap.IDSYMBOL.concat("coś"))).as("jakasKolumna")).from(ap).getSQL();
			int t = 0;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	//@Transactional
	@Commit
	public void situQueryTest() {	
		ntw.inTrans(()->fillTest1_5());	
		try (
				DSLContext create = dsl1;
				) {	
			
			create.configuration().settings().setStatementType(StatementType.STATIC_STATEMENT);
			
			UmubezpSkladowaoper skl = UmubezpSkladowaoper.UMUBEZP_SKLADOWAOPER.as("skl");
			UmubezpSkladowaoperanul skla = UmubezpSkladowaoperanul.UMUBEZP_SKLADOWAOPERANUL.as("skla");
			UmubezpProduktoper x = UmubezpProduktoper.UMUBEZP_PRODUKTOPER.as("x");
			Param<Date> fdtStat = DSL.param("fdtStat", Date.class);
			Param<Date> fdtWDniu = DSL.param("fdtWDniu", Date.class);
			List<String> kdFirmaIn = Arrays.asList(new String[] {"kdPZM()","kdIR()"});
			AggregateFunction<BigDecimal> sum1 = DSL.sum(skl.DPDCENA);
			AggregateFunction<Double> sum2 = DSL.max(skl.DPDSUMA);
			Field<BigDecimal> sum1as = sum1.as("dpSkladka");
			Field<Double> sum2as = sum2.as("dpDSuma");
			
			Table<Record17<String, Double, Short, Double, Long, String, Date, Date, BigDecimal, Double, Double, Long, String, Double, Double, Double, Short>> t1 = 
			create
				.select(x.IDSYMBOL, x.UNPRODUKT, x.KDWALUTA, skl.UNPOLISA, x.LPPOZYCJA, x.IDTARPOZ
						,DSL.min(x.DTOD).as("dtOd"), DSL.max(x.DTDO).as("dtDo")
						,sum1as//.as("dpSkladka")
						,sum2as//.as("dpDSuma")
						,DSL.max(skl.DPDSUMAMAX).as("dpDSumaMax")
						,DSL.max(skl.LPDLICZBA).as("lpLiczba")
						,lastlpope1(getTlastlpopetextRecord(x.IDKLUCZSTAT, x.LPOPERACJA))
						,lastlpope4(getTlastlpopedblRecord(x.UNUBNYSPR, x.LPOPERACJA))
						,lastlpope4(getTlastlpopedblRecord(x.UNUBNYOPE, x.LPOPERACJA))
						,lastlpope4(getTlastlpopedblRecord(x.DPFRAN, x.LPOPERACJA))
						,lastlpope3(getTlastlpopesmallintRecord(x.KDFRANWAL, x.LPOPERACJA))
						)
				.from(skl
					.leftJoin(skla).on(skla.UNSKLADOWA.eq(skl.UNSKLADOWA).and(skla.DTSTAT.le(fdtStat)))
					.join(x).on(skl.UNPRODUKT.eq(x.UNPRODUKT))
					)
				.where(
						fdtWDniu.between(skl.DTOD, skl.DTDO)
						.and(skl.DTSTAT.le(fdtStat))
						.and(fdtWDniu.between(x.DTOD, x.DTDO))
						.and(x.DTSTAT.le(fdtStat))
						.and(skl.KDOPERACJA.eq((short) 1))
						.and(skl.KDFIRMA.in(kdFirmaIn))
						.and(x.KDFIRMA.in(kdFirmaIn))
						.and(skla.UNSKLADOWA.isNull())						
					)
				.groupBy(DSL.inline(1),DSL.inline(2),DSL.inline(3),DSL.inline(4),DSL.inline(5),DSL.inline(6))
				.having(sum1.ne(BigDecimal.ZERO).and(sum2.ne((double)0)))
				.asTable().as("x","IDSYMBOL")				
				;
			
			UmubezpPolisaoper pol = UmubezpPolisaoper.UMUBEZP_POLISAOPER;
			//dajminuskurs(t1.field(x.KDFRANWAL.getName() ))
			
			create
				.select(
						pol.UNPOLISA
						,pol.KDFIRMA
						,DSL.cast( lastlpope1(getTlastlpopetextRecord(pol.IDNUMER, pol.LPOPERACJA)), SQLDataType.VARCHAR.length(40) )
						,x.KDWALUTA
					    ,x.IDSYMBOL
					    ,x.UNPRODUKT
					    ,x.field(sum1as.getName())
					    ,t1.field("dtOd")
					    ,t1.field("dtDo")
					    ,t1.field("lpPozycja")
					    ,t1.field("idTarPoz")    
					    ,t1.field("idKluczStat")    
					    ,t1.field("unUbnySpr")
					    ,t1.field("unUbnyOpe")
					    ,t1.field("kdFranWal")
					    ,t1.field("lpLiczba")
					    //,DSL.round(x.DPFRAN.mul(dajminuskurs( DSL )))
					    		
					    )
				.from(t1);
			
			String s1 = create
				.select()
				.from(t1)
				.bind("fdtStat", Date.valueOf(LocalDate.now()))
				.bind("fdtWDniu", Date.valueOf(LocalDate.now()))
				.getSQL();
				
			int t = 0;
			;
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
