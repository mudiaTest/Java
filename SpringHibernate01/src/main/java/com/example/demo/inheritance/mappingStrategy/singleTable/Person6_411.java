package com.example.demo.inheritance.mappingStrategy.singleTable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.demo.inheritance.Address6_2;

import lombok.Data;

@Data
@Entity
	/*
	 * Dyscrminator column zadzia³a tylko dla najbardziej generycznej klasy
	 */
//@DiscriminatorColumn(name="discCol11", discriminatorType=DiscriminatorType.STRING, length=20)
	/*
	 * Wartoœæ wpisywana do discriminatorColumn
	 * Domyœlnie dla STRING jest to nazwa tabeli a dla INTEGER jakieœ liczby
	 * 
	 * Wartoœæ musi byæ zgodna z typem discriminatorType inaczej da exception
	 */
@DiscriminatorValue(value="1")
public class Person6_411 extends Person6_41Gen{
	public Address6_2 addr;
}
