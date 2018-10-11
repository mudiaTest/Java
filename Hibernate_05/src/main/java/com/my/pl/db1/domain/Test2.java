package com.my.pl.db1.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Test2 {
	@Id
	private long id;
	private int intVal;
	private String strVal;
}
