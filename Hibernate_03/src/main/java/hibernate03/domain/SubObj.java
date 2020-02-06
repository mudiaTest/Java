package my.com.pl.Hibernate_03.domain;

/*
 * Zamiast klasy tworzymy interfejs, czyli rodzaj View.
 * Mo¿na u¿ywac w po³¹czeniu z HQL lub SQL (native)
 * Wa¿ne jest aby nazewnictwo geeterów odpowiada³o "as V3" z kodzie SQL/HQL 
 */
public interface SubObj {
	public Integer getV3();
	public Integer getint_val();
}
