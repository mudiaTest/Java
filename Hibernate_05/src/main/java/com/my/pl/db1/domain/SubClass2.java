package com.my.pl.db1.domain;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class SubClass2 {
	private int subIntVal1;
	
	public SubClass2() {}
	
	public SubClass2(int subIntVal1, String subStVal1) {
		super();
		this.subIntVal1 = subIntVal1;
		this.subStVal1 = subStVal1;
	}
	private String subStVal1;
}
