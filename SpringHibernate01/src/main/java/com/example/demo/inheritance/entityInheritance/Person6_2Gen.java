package com.example.demo.inheritance.entityInheritance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;

@Data
@Entity
	/*
	 * Domyœlnie jest SINGLE_TABLE
	 */
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Disc")
public abstract class Person6_2Gen {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
}
