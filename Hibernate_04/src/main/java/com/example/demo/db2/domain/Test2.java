package com.example.demo.db2.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import lombok.Data;

@Data
@Entity
public class Test2 {
	@Id
	private long id;
	private String stVal1;

}
