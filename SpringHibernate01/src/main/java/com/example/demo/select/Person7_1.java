package com.example.demo.select;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Person7_1 {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
	
	public Address7_1 address;
	
	@OneToMany(mappedBy="person")
	private Set<JobApplication7_1> jobApplications = new HashSet<>();	
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<Contact7_1> contacts = new HashSet<>();	
	
	public void addJobApplication(String company) {
		JobApplication7_1 ja = new JobApplication7_1();
		ja.setCompany(company);
		ja.setPerson(this);
		jobApplications.add(ja);
	}
	
	
	
	public void addContact(String envelope) {
		Contact7_1 c = new Contact7_1();
		c.envelope = envelope;	
		contacts.add(c);
	}
}
