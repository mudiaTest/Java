package my.com.pl.Hibernate_03.dao;

import org.springframework.stereotype.Component;

//@Component("CustomDaoyImpl")
public class CustomDaoImpl implements CustomDao {

	@Override
	public int getCustomImplRef() {
		return 3;
	}

}
