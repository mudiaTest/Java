package jackson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonSetter;

public class MyObject3 {
	public String txt;
	public MyObject31 o31 = new MyObject31();
	public List<MyObject32> l32 = new ArrayList<>();
	public Map<Long, MyObject32> m32 = new HashMap<>();
	
	public MyObject32 o32 = new MyObject32(); 
	
	/**
	 * Obiekt p_o32 nie bedzie serializowany, ale mo¿e zostaæ deserializowany
	 * Dane do obiektu p_o32 zostan¹ sztucznie wstrzykniête metod¹ getO33
	 */
  private MyObject32 p_o32 = new MyObject32();
  // getTheTxt5 dzia³a jak getTxt2
  @JsonSetter("o33") 
  public void setTheObj32(MyObject32 obj) {
  	p_o32 = obj;
  }
  // Wy³¹cza serializacjê i wk³ada do wynikowego stringa wartoœæ tak¹, jaka jest
  // Jest to tylko getter, wiêc nie bêdzie deserializowany.
  @JsonRawValue
  public String getO33() {
  	return "{\"txt32\":\"dummy\"}";
  }
  // Inne urzycie JsonRawValue - bezpoœrednie u¿ycie pola, ale to pole tak¿e bêdzie serializowane/deserializowane
  //  @JsonRawValue
  //  public String o33 = "{\"txt32\":null}";
}
