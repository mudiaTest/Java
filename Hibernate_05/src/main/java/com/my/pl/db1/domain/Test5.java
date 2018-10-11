package com.my.pl.db1.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

import lombok.Data;

@Data
@Entity

@SqlResultSetMappings( {
	/*
	 * Mapowanie do pojedynczego obiektu @Entity
	 */
	@SqlResultSetMapping(
	        name = "Test5Mapping_1",
	        entities = {
	        		@EntityResult(
	                entityClass = Test5.class,
	                fields = {
	                    @FieldResult(name = "id", column = "id"),
	                    @FieldResult(name = "intVal1", column = "val2")
	                    })
	        }
			)
	,
	/*
	 * Mapowanie do wielu obiektów @Entity
	 */
	@SqlResultSetMapping(
			name = "Test5Mapping_4",
			entities = {
					@EntityResult(
							entityClass = Test5.class,
							fields = {
									@FieldResult(name = "id", column = "id"),
									@FieldResult(name = "intVal1", column = "val2")
							})
					/* Mapowanie do więcej niz jednego obiektu jednocześnie
					 ,
					@EntityResult(
							entityClass = Test5.class,
							fields = {
									@FieldResult(name = "id", column = "id"),
									@FieldResult(name = "intVal1", column = "val2")
							})*/
			}
			)
	,
	/*
	 * "SELECT a.id, a.int_val1 as intVal1 FROM test5 a LEFT JOIN test5_t2 b ON a.id = test5_id"
	 * Poniższe spowoduje, ze wynikiem będzie lista integer�w z kolumy "intVal1"
	 */
	@SqlResultSetMapping(
	        name = "Test5Mapping_2",
	        columns={
	        		@ColumnResult(name="intVal1")
	        		}
	        )
	,
	/*
	 * Mapowanie z użyciem konstruktora
	 */
	@SqlResultSetMapping(
			name = "Test5Mapping_3",
			classes={
			      @ConstructorResult(
			        targetClass=Test5_1.class,
			        columns={
			        //Kolejno�� p�l jest BARDZO WA�NA - musi odpowiadać kolejności z konstruktora
			          @ColumnResult(name="id", type=Long.class),
			          @ColumnResult(name="val2", type=Integer.class),
			          @ColumnResult(name="s1", type=Integer.class),
			          @ColumnResult(name="s2", type=String.class)
			          }
			        )
			      }
			)
})
public class Test5 {
	@Id
	private long id;
	private int intVal1;	
	@ElementCollection
	private List<SubClass2> t2 = new ArrayList<>();
	
	public Test5() {}
	
	public Test5(long id, int intVal1) {
		super();
		this.id = id;
		this.intVal1 = intVal1;
	}
	
	public Test5(long id, int intVal1, List<SubClass2> t2) {
		super();
		this.id = id;
		this.intVal1 = intVal1;
		this.t2 = t2;
	}	

	public Test5(long id, int intVal1, int s1, String s2) {
		super();
		this.id = id;
		this.intVal1 = intVal1;
		this.t2.add(new SubClass2(s1, s2));
	}
	
	
}
