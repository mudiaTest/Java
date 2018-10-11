package com.my.pl.db1.dao;

import org.springframework.stereotype.Component;

//@Component("CustomDaoyImpl")
public class CustomDaoImpl implements CustomDao {

	@Override
	public int getCustomImplRef() {
		return 3;
	}

}
