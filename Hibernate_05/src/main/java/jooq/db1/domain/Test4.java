package jooq.db1.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Test4 {
  @Id
  private long id;
  private int intVal1;
  @OneToMany(cascade=CascadeType.ALL)
  private Set<Test41> subObjSet = new HashSet<>();
  @Transient
  private Set<Test41> subObjSet2 = new HashSet<>();
}
