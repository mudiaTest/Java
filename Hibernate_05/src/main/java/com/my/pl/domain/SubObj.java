package com.my.pl.domain;

/*
 * Zamiast klasy tworzymy interfejs, czyli rodzaj View.
 * Można używac w połączeniu z HQL lub SQL (native)
 * Ważne jest aby nazewnictwo geeterów odpowiadało "as V3" z kodzie SQL/HQL 
 */
public interface SubObj {
	public Integer getV3();
	public Integer getint_val();
}
