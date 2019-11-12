package my.com.pl.srv.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	static public List<String> bufferedReader2List(BufferedReader br)
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
	
	/*
	 * Zapisywanie w różnym kodowaniu
	 * Dla polski używać UTF-8 lub CP1250 
	 * https://stackoverflow.com/questions/18258355/how-to-encode-to-windows-1252-in-java-while-writing-to-the-file
	 */
	static public void StrLstToFile(List<String> lst, String filePath) throws UnsupportedEncodingException {
		StrLstToFile(lst, filePath, "UTF-8");
	}
	static public void StrLstToFile(List<String> lst, String filePath, String coding) throws UnsupportedEncodingException {		
		try (PrintWriter outPut = new PrintWriter(filePath, coding)) {
			lst.stream().forEach(line -> outPut.println(line));			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	static public void StrToFile(String txt, String filePath) throws UnsupportedEncodingException {
		StrToFile(txt, filePath, "UTF-8");
	}
	static public void StrToFile(String text, String filePath, String coding) throws UnsupportedEncodingException {
		try (PrintWriter outPut = new PrintWriter(filePath, coding)) {
			outPut.println(text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	static public String Concat(String prefix, String dst, String text) {
		if (!dst.equals(""))
			return dst + prefix + text;
		else
			return text;
		
	}
}
