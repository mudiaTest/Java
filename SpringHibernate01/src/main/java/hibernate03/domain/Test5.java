package hibernate03.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;

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
   * Mapowanie do wielu obiekt�w @Entity
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
          /* Mapowanie do wi�cej niz jednego obiektu jednocze�nie
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
   * Poni�sze spowoduje, ze wynikiem b�dzie lista integer�w z kolumy "intVal1"
   */
  @SqlResultSetMapping(
          name = "Test5Mapping_2",
          columns={
              @ColumnResult(name="intVal1")
              }
          )
  ,
  /*
   * Mapowanie z u�yciem konstruktora
   */
  @SqlResultSetMapping(
      name = "Test5Mapping_3",
      classes={
            @ConstructorResult(
              targetClass=Test5_1.class,
              columns={
              //Kolejno�� p�l jest BARDZO WA�NA - musi odpowiada� kolejno�ci z konstruktora
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
