package com.example.demo.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person3_2 {
	
	@Id
	/*
	 * IDENTITY - bazuje na wartoœciach autoincrement w kolimnie id w bazie
	 * 
	 * Tworzone jest pole BIGSERIAL (niezale¿nie od klasy pola @Id) z wartoœæi¹ domyœln¹ - generuj¹c¹ kolejne klucze
	 * Bigserial - large autoincrementing integer (1 to 9223372036854775807)
	 * Zak³ada te¿ sekwencjê person3_2_key1_seq pracuj¹c¹ na rzecz autoincrement
	 * 
	 * Jeœli pole bedzie String ,to odda string z liczby, ale co jeœli pole bêdzie Integer a tam bedzie BIGSERIAL?
	 * IDENTITY - uniemo¿liwa batch uploades
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String key1;
	public String name;		
}
