package com.my.pl.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Test71 {
	@Id
	Long id;
	
	int val;
	
	@Version
	int ver;

	@OneToMany(mappedBy = "t71"
			, cascade = CascadeType.ALL
			, orphanRemoval = true
//			, fetch=FetchType.EAGER
			)
	Set<SubClass7> set = new HashSet<>();
}
