package hibernate01.inheritance.mappingStrategy.tablePerClass;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import hibernate01.inheritance.Contact6_2;
import lombok.Data;

@Data
@Entity
  /*
   * Dyscrminator column zadzia�a tylko dla najbardziej generycznej klasy
   */
//@DiscriminatorColumn(name="discCol12", discriminatorType=DiscriminatorType.STRING, length=20)
  /*
   * Warto�� wpisywana do discriminatorColumn
   * Domy�lnie dla STRING jest to nazwa tabeli a dla INTEGER jakie� liczby
   * 
   * Warto�� musi by� zgodna z typem discriminatorType inaczej da exception
   */
@DiscriminatorValue(value="22")
public class Person6_432 extends Person6_43Gen{
  public Contact6_2 con;
}
