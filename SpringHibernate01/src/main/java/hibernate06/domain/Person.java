package hibernate06.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Entity
@Getter
@Setter
@Table(name="Person")
public class Person {
  @Id
  public Integer id;
  
  public String name;  
  public Integer age;

  //public Address home;

  //public Address office;
}
