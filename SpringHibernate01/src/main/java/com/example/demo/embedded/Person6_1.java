package com.example.demo.embedded;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Person6_1 {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
	@Embedded
	public HomeData6_1 homeData;
}
