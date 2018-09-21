package com.example.demo.oneManyEmbdedded;

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
public class Person1 {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
	/*
	 * @Embedded gdy Address1 jest @Embeddable, to:
	 * - Address1 nie posiada id
	 * - pola Address1 postaj¹ w³¹czone do Person1
	 */
	@Embedded
	public Address1 address;
	/*
	 * @ElementCollection gdy PersonFeature1 jest @Embeddable, to
	 * - PersonFeature1 nie posziada id
	 * - Zostanie utworzona tabela "person1_features" i w niej pole "person1_id"	   
	 *   Pole id zostanie utworzone tego samego typu, wiêc mo¿na u¿yæ stringa etc.
	 */
	@ElementCollection()
	/*
	 * Pozwala na nadpisanie domyœlnoœci nazwy tworzonej tabeli i nazwy pola id.
	 * targetClass ma wskazywac na klasê docelow¹, ale gdy uzywamy kolekcji generycznej to jest niepotrzebne
	 * fetch: FetchType.LAZY (default) lub EAGER
	 */
	//@CollectionTable(name = "person_feature2", joinColumns = @JoinColumn(name="person_id2"))
	public List<PersonFeature1> features = new ArrayList<PersonFeature1>();
	
	/*
	 * To tak¿e utworzy w³¹sn¹ tabele z 2 kolumnami "person1_id" i "strings"
	 */
	@ElementCollection
	public List<String> strings = new ArrayList<>();
	
	@ElementCollection(targetClass=String.class)	
	public List annoStrings = new ArrayList();
}
