package hibernate03.domain;

/*
 * Zamiast klasy tworzymy interfejs, czyli rodzaj View.
 * Mo�na u�ywac w po��czeniu z HQL lub SQL (native)
 * Wa�ne jest aby nazewnictwo geeter�w odpowiada�o "as V3" z kodzie SQL/HQL 
 */
public interface SubObj {
  public Integer getV3();
  public Integer getint_val();
}
