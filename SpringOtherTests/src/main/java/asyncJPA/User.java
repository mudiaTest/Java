package asyncJPA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.extern.slf4j.Slf4j;


@Entity
@Table(name = "`user`")
public class User {

  @Id
  Integer id;
  
  @Column
  String name;
  
  @Column
  String login;
  
  @Column
  Integer age;
  
  @Transient
  String info()  {
    return String.format("%d: %s; %s; %d", id, name, login, age);
  }
}
