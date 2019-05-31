package my.com.pl.srv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpParserHelper {
	//type, name, position, multiple
	// position = -1 <=> multiple = true
	// position >= 0 <=> multiple = false
	private List<Quartet<String, String, Integer, Boolean>> params = new ArrayList(); 
	
	// Jeśli w dokumencie znajduje się więcej elementów o takim samym "id" to zostanie 
	// zwrócony pierwszy znaleziony. Tak budowa jednak jest błędna i należy to rozpatrywać jako przypadek szczególny
	public HttpParserHelper id(String id) {
		params.add(new Quartet<String, String, Integer, Boolean>("id", id, 0, false));
		return this;
	}
	
//	public HttpParserHelper id(String id, int poz) {
//		params.add(new Triplet<String, String, Integer>("id", id, poz));
//		return this;
//	}	
	
	public HttpParserHelper clazz(String clazz) {
		params.add(new Quartet<String, String, Integer, Boolean>("clazz", clazz, 0, false));
		return this;
	}
	
	public HttpParserHelper clazz(String clazz, int poz) {
		params.add(new Quartet<String, String, Integer, Boolean>("clazz", clazz, poz, false));
		return this;
	}
	
	public HttpParserHelper clazzes(String clazz) {
		params.add(new Quartet<String, String, Integer, Boolean>("clazz", clazz, -1, true));
		return this;
	}
	
	public HttpParserHelper tag(String tag) {
		params.add(new Quartet<String, String, Integer, Boolean>("tag", tag, 0, false));
		return this;
	}
	
	public HttpParserHelper tag(String tag, int poz) {
		params.add(new Quartet<String, String, Integer, Boolean>("tag", tag, poz, false));
		return this;
	}
	
	public HttpParserHelper tags(String tag) {
		params.add(new Quartet<String, String, Integer, Boolean>("tag", tag, -1, true));
		return this;
	}	
	
	
	public Element parseOne(Element root) {
		Element result = root;
		for (int i = 0; i < params.size(); i++) {
			Quartet<String, String, Integer, Boolean> step = params.get(i);
			if (result != null)
				switch (step.getValue0()) {
					case "id" : {							
						result = result.getElementById(step.getValue1());
						continue;
					}
					case "clazz" : {						
						Elements tmp = result.getElementsByClass(step.getValue1());
						result = tmp.size() >= step.getValue2() ? tmp.get(step.getValue2()) : null;
						continue;
					}
					case "tag" : {
						Elements tmp = result.getElementsByTag(step.getValue1());
						result = tmp.size() >= step.getValue2() ? tmp.get(step.getValue2()) : null;
						continue;
					}
				}		
		}
		return result;
	}
	
	private Elements getStepResult(Elements localRoots, Quartet<String, String, Integer, Boolean> step) {
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
	
	public Elements parse(Element root) {
		Elements localRoots = new Elements() {{ if (root != null) add(root);}};
//		Element localRoot = null;
		Elements result = null;
		for (int i = 0; i < params.size(); i++) {
			Quartet<String, String, Integer, Boolean> step = params.get(i);
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
//					case "clazz" : {			
//						Elements prvLocalRoots = localRoots;
//						localRoots = new Elements();
//						//Dla każdego elementu oddanego w poprzednim kroku
//						for(Element localRoot: prvLocalRoots) {
//							//Jeśli oddajemy wiele
//							if (step.getValue3())
//								localRoots.addAll(localRoot.getElementsByClass(step.getValue1()));
//							//Jeśli oddajemy pojedynczy
//							else {
//								//Pobieramy listę znalezionych
//								Elements localResults = localRoot.getElementsByClass(step.getValue1());
//								//Wyłuskujemy odpowiedni, a w przypadku braku odajemy null
//								Element localResult = localResults.size() >= step.getValue2() ? localResults.get(step.getValue2()) : null;
//								//Jeśli znaleismy odpowiedni, to dodajemy do do lokalnej listy, na podstawie której będzie wykoanan kolejna iteracja
//								if (localResult != null)
//									localRoots.add(localResult);
//							}
//								
//						}
//						continue;
//						Elements prvLocalRoots = localRoots;
//						localRoots = new Elements();
//						getStepResult(prvLocalRoots, step);
//						continue;
//					}
//					case "tag" : {
//						Elements prvLocalRoots = localRoots;
//						localRoots = new Elements();
//						getStepResult(prvLocalRoots, step);
//						continue;
//					}
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
