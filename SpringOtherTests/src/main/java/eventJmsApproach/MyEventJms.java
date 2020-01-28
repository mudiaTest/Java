package eventJmsApproach;

import lombok.Getter;
import lombok.Setter;

public class MyEventJms {
	// JEST serializacjia jackson - pole publiczne
	public String stMessage;
	
	// JEST serializacjia jackson - get i set to prefixy pola
	@Getter
	@Setter
	private String hiddenTxt;
	
	// BRAK serializacjia jackson 
	private String hiddenMessage;
	public String getStMess()
	{
		return hiddenMessage;
	}
	public void setStMesss(String txt)
	{
		hiddenMessage = txt;
	}
	
	// JEST serializacjia jackson - get i set to prefixy pola
	private String stTxt;
	public String getStTxt()
	{
		return stTxt;
	}
	public void setStTxt(String txt)
	{
		stTxt = txt;
	}
}
