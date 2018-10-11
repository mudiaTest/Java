package com.my.pl.db1.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SubClass63 {
	@Id
	private Long id;
	private int subIntVal1;
	
	public SubClass63() {}
	
	public SubClass63(long id, int subIntVal1) {
		super();
		this.id = id;
		this.subIntVal1 = subIntVal1;
	}
	private String subStVal1;
}
