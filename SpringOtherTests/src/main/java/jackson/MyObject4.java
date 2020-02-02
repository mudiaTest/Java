package jackson;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

//Ustala kolejnoœæ serializacji. Niewymienione bêd¹ serializowane na koñcu w domyœlnej kolejnoœæi
@JsonPropertyOrder({ "txt3", "txt4" })
public class MyObject4 {
	
	public String txt1 = "test1";
	public String txt3 = "test3";
	public String txt4 = "test4";
	
	private String txt2 = "test2";	 
	// getTheTxt2 dzia³a jak getTxt2
  @JsonGetter("txt2") 
  public String getTheTxt2() {
      return txt2;
  }
  // setTheTxt2 dzia³a jak getTxt2
  @JsonSetter("txt2") 
  public void setTheTxt2(String val) {
  	txt2 = val;
  }  
}
