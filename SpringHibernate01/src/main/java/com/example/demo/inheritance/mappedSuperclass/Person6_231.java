package com.example.demo.inheritance.mappedSuperclass;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.demo.inheritance.Address6_2;

import lombok.Data;

@Data
@Entity
public class Person6_231 extends Person6_3Gen{
	public Address6_2 addr;
}
