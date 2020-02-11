package hibernate01.inheritance.mappingStrategy.tablePerClass;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;

@Data
@Entity
  /*
   * InheritanceType.TABLE_PER_CLASS - ka�da klasa ko�cowa ma osobn� tabel� 
   * ze wszystkimi polami, tak�e tymi odziedziczonymi
   * 
   * Bardzo kiepsko odwzorowuje polimorfizm, ale nie potrzebuje join�w. 
   * Niestety w zamian wymaga UNION�w lub wielu osobnych QUERY do pokrycia 
   * ca�ej hierarhii.
   * 
   * Wsparcie dla tej strategii jest opcjonalne, ale HIBERNATE j� wspiera.
   * 
   * SINGLE_TABLE to watro�c domy�lna
   */
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Person6_43Gen {
  @Id
  @GeneratedValue
  public Integer id;
  public String name;
}
