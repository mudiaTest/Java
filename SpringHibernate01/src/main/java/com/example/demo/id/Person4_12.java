package com.example.demo.id;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
	/*
	 * Definiuje jakby logiczne opakowanie, które pozwala na obs³ugê.
	 * Oznacza to, ¿e klucz bêdzie przechowywany w tabeli Person4_11
	 * , a nie w jakiejœ osobnej strukturze
	 */
public class Person4_12 {
	
	/*
	 * @EmbeddedId wymaga @Embeddable po stronie Klasy klucza
	 * 
	 * Kluczy nie mo¿na generowaæ @GeneratedValue.
	 * 
	 * Do klucza odwo³ujemy siê poprzez Person4_11.key.key1
	 */
	@EmbeddedId
	public Person4Id key;
	public String name;		
}
