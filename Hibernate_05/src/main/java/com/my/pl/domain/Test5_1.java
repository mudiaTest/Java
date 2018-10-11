package com.my.pl.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Test5_1 {
	private long id;
	private int intVal1;	
	private List<SubClass2> t2 = new ArrayList<>();
	
	public Test5_1() {}
	
	public Test5_1(long id, int intVal1) {
		super();
		this.id = id;
		this.intVal1 = intVal1;
	}
	
	public Test5_1(long id, int intVal1, List<SubClass2> t2) {
		super();
		this.id = id;
		this.intVal1 = intVal1;
		this.t2 = t2;
	}
	
	public Test5_1(long id, int intVal1, int s1, String s2) {
		super();
		this.id = id;
		this.intVal1 = intVal1;
		this.t2.add(new SubClass2(s1, s2));
	}
}
