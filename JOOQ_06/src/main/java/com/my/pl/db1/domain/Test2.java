package com.my.pl.db1.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Test2 {
  @Id
  private long id;
  private int intVal1;  
  private String stVal2;
  private int rv;
}
