package com.my.pl.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Test70 {
	@Id
	Long id;
	
	int val;
	
	//@Version
	int ver;

}
