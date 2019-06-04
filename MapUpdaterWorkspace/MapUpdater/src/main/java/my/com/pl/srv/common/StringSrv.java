package my.com.pl.srv.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StringSrv {
	
	@Autowired
	StringSrv comms; 
	
	/** 
	 * Zczytuje kolejne linie z bufora i zapisuje je na wynikową listę.
	 * @param br
	 * @return Lista linii z bufora	 
	 */
	public List<String> bufferedReader2List(BufferedReader br)
	{
		List<String> lines = new ArrayList<>();
	    br.lines().forEach(s->lines.add(s));
	    //br.lines().limit(30).forEach(s->lines.add(s));
	    return lines;
	}
	
	/**
	 * Wywołanie comms.filterLines 
	 * @param lines Lista Stringów, z których pobieramy potencjalnych potencjalne pozycje do wyniku
	 * @param pattern Wzór zgodny z zasadami RegExp
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<String> filterLines(List<String> lines, String pattern)
	{
		return comms.filterLines(lines, pattern);	
	}
}
