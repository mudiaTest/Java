package com.my.pl.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Test4 {
	@Id
	private long id;
	private int intVal1;	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Test2> t2;
}
