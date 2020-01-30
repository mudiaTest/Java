package jackson;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class MyObject4 {
	public String txt1 = "test1";
	
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
