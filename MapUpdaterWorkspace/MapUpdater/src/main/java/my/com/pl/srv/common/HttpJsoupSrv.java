package my.com.pl.srv.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aspectj.bridge.context.PinpointingMessageHandler;
import org.javatuples.Pair;
//import org.javatuples.Pair;
//import org.javatuples.Triplet;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Service
public class HttpJsoupSrv {
	//type, name, position, multiple
	// position = -1 <=> multiple = true
	// position >= 0 <=> multiple = false
	private List<Quintet<String, String, Integer, Boolean, Map<String, String>>> params = new ArrayList(); 
	
	/**
	 * W wersji "get" parametry podajemy w adresie url
	 * 
	 * @param url
	 * "https://www.youtube.com/watch?v=6f88Fp_r88A&feature=youtu.be&t=321" :
	 * https://www.youtube.com/watch
	 * ?
	 * v=6f88Fp_r88A
	 * &
	 * feature=youtu.be
	 * &
	 * t=321
	 * @return
	 * @throws IOException
	 */
	public Document getPageDom(String url) throws IOException
	{
		return Jsoup.connect(url).get();
	}
	
	public Document postPageDom(String url) throws IOException
	{
		return postPageDom(url, null);
	}	
	
	public Document postPageDom(String url, Map<String, String> attributes) throws IOException
	{		
		Connection conn = Jsoup.connect(url);
		if (attributes != null)
			for(Map.Entry<String, String> attr: attributes.entrySet()) {
				conn = conn.data(attr.getKey(), attr.getValue());
			}
		return conn.followRedirects(true).post();
	}		
	
	/**
	 * Resetuje listę parametrów.
	 * @return this;
	 */
	public HttpJsoupSrv clear() {
		params.clear();
		return this;
	}
	
	/**
	 * 
	 * @param id
	 * @return this
	 */
	// Jeśli w dokumencie znajduje się więcej elementów o takim samym "id" to zostanie 
	// zwrócony pierwszy znaleziony. Tak budowa jednak jest błędna i należy to rozpatrywać jako przypadek szczególny
	public HttpJsoupSrv id(String id) {
		params.add(new Quintet<String, String, Integer, Boolean, Map<String, String>>("id", id, 0, false, null));
		return this;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return this
	 */
	public HttpJsoupSrv clazz(String clazz) {
		params.add(new Quintet<String, String, Integer, Boolean, Map<String, String>>("clazz", clazz, 0, false, null));
		return this;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return this
	 */
	public HttpJsoupSrv clazz(String clazz, Map<String, String> attributes) {
		params.add(new Quintet<String, String, Integer, Boolean, Map<String, String>>("clazz", clazz, 0, false, attributes));
		return this;
	}
	
	/**
	 * 
	 * @param clazz
	 * @param poz
	 * @return this
	 */
	public HttpJsoupSrv clazz(String clazz, Map<String, String> attributes, int poz) {
		params.add(new Quintet<String, String, Integer, Boolean, Map<String, String>>("clazz", clazz, poz, false, attributes));
		return this;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return this
	 */
	public HttpJsoupSrv clazzes(String clazz) {
		params.add(new Quintet<String, String, Integer, Boolean, Map<String, String>>("clazz", clazz, -1, true, null));
		return this;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return this
	 */
	public HttpJsoupSrv clazzes(String clazz, Map<String, String> attributes) {
		params.add(new Quintet<String, String, Integer, Boolean, Map<String, String>>("clazz", clazz, -1, true, attributes));
		return this;
	}
	
	/**
	 * 
	 * @param tag
	 * @return this
	 */
	public HttpJsoupSrv tag(String tag) {
		params.add(new Quintet<String, String, Integer, Boolean, Map<String, String>>("tag", tag, 0, false, null));
		return this;
	}
	
	/**
	 * 
	 * @param tag
	 * @return this
	 */
	public HttpJsoupSrv tag(String tag, Map<String, String> attributes) {
		params.add(new Quintet<String, String, Integer, Boolean, Map<String, String>>("tag", tag, 0, false, attributes));
		return this;
	}
	
	/**
	 * 
	 * @param tag
	 * @param poz
	 * @return this
	 */
	public HttpJsoupSrv tag(String tag, Map<String, String> attributes, int poz) {
		params.add(new Quintet<String, String, Integer, Boolean, Map<String, String>>("tag", tag, poz, false, attributes));
		return this;
	}

	/**
	 * 
	 * @param tag
	 * @return this
	 */
	public HttpJsoupSrv tags(String tag) {
		params.add(new Quintet<String, String, Integer, Boolean, Map<String, String>>("tag", tag, -1, true, null));
		return this;
	}	
	
	/**
	 * 
	 * @param tag
	 * @return this
	 */
	public HttpJsoupSrv tags(String tag, Map<String, String> attributes) {
		params.add(new Quintet<String, String, Integer, Boolean, Map<String, String>>("tag", tag, -1, true, attributes));
		return this;
	}
	
	private Boolean elementHastAttributeValue(Element el, Map.Entry<String, String> attr){
		Boolean result = false;
		if (el.hasAttr(attr.getKey())){
			if (el.attributes().get(attr.getKey()).equals(attr.getValue()))
				result = true;
		}
		return result;
	}
	
	private void filterByAttribute(Elements elems, Map<String, String> attributes) {
		Iterator<Element> it = elems.iterator();
		while (it.hasNext()) {
			Element el = it.next();
			if (attributes != null) {
				Boolean del = false;
				for (Map.Entry<String, String> attr: attributes.entrySet()) { //Map.Entry<String, String> attr: attributes.entrySet()
					if (!elementHastAttributeValue(el, attr)) {
						del = true;
						break;
					}
					
				}
				if (del)
					it.remove();
			}
		}
	}
	
	public Element parseOne(Element root) {			
		Element result = root;
		for (int i = 0; i < params.size(); i++) {
			Quintet<String, String, Integer, Boolean, Map<String, String>> step = params.get(i);
			if (result != null)
				switch (step.getValue0()) {
					case "id" : {							
						result = result.getElementById(step.getValue1());
						continue;
					}
					case "clazz" : {						
						Elements tmp = result.getElementsByClass(step.getValue1());
						filterByAttribute(tmp, step.getValue4());
						result = tmp.size() >= step.getValue2() ? tmp.get(step.getValue2()) : null;
						continue;
					}
					case "tag" : {
						Elements tmp = result.getElementsByTag(step.getValue1());
						filterByAttribute(tmp, step.getValue4());
						result = tmp.size() >= step.getValue2() ? tmp.get(step.getValue2()) : null;
						continue;
					}
				}		
		}
		return result;
	}
	
	private Elements getStepResult(Elements localRoots, Quintet<String, String, Integer, Boolean, Map<String, String>> step) {
		Elements prvLocalRoots = localRoots;
		Elements result = new Elements();
		//Dla każdego elementu oddanego w poprzednim kroku
		for(Element localRoot: prvLocalRoots) {
			
			//Pobieramy listę znalezionych
			Elements localResults = new Elements();
			switch (step.getValue0()) {
				case "clazz": localResults.addAll(localRoot.getElementsByClass(step.getValue1()));
				case "tag": localResults.addAll(localRoot.getElementsByTag(step.getValue1()));	
			}
			filterByAttribute(localResults, step.getValue4());
			
			//Jeśli oddajemy wiele
			if (step.getValue3())
				result = localResults;
			//Jeśli oddajemy pojedynczy
			else {
				//Wyłuskujemy odpowiedni, a w przypadku braku odajemy null
				Element localResult = localResults.size() >= step.getValue2() ? localResults.get(step.getValue2()) : null;
				//Jeśli znaleismy odpowiedni, to dodajemy do do lokalnej listy, na podstawie której będzie wykoanan kolejna iteracja
				if (localResult != null)
					result.add(localResult);
			}
		}
		return result;
	}
	
	/**
	 * 
	 */
	public Elements parse(Element root) {
		Elements localRoots = new Elements() {{ if (root != null) add(root);}};
		Elements result = null;
		for (int i = 0; i < params.size(); i++) {
			Quintet<String, String, Integer, Boolean, Map<String, String>> step = params.get(i);
			if (localRoots.size() > 0)
				switch (step.getValue0()) {
					case "id" : {
						Elements prvLocalRoots = localRoots;
						localRoots = new Elements();
						for(Element localRoot: prvLocalRoots) {
							//getElementById jako jedyny oddaje tylko jeden element. Możliwe więc, że wewnątrz 
							//badanego kodu jest więcej elementów, ale ich nie znajdziemy
							Element localResult = localRoot.getElementById(step.getValue1());						
							localRoots.add(localResult);
						}
						continue;
					}
					default: {
						Elements prvLocalRoots = localRoots;
						localRoots = getStepResult(prvLocalRoots, step);
						continue;
					}
				}		
		}
		result = localRoots;
		return result;
	}
}
