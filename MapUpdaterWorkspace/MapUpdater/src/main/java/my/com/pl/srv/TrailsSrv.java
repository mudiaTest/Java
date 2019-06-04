package my.com.pl.srv;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.pl.srv.common.HttpJsoupSrv;

@Service
public class TrailsSrv {
	
	@Autowired
	HttpJsoupSrv hjs;
	
	private void CheckIfOne(Collection col) throws Exception
	{
		if (col.size() != 1)
			throw new RuntimeException("Znaleziono " + col.size());
	}
	
	/**
	 * Pobieramy informacje o dostępnych trailach
	 * @param doc
	 * @return Mapa: klucz - url strony traila, val - TrailXMLObject
	 * @throws Exception
	 */
	private Map<String, TrailXMLObject> getTrails(Document doc) throws Exception{
		Map<String, TrailXMLObject> result = new HashMap<String, TrailXMLObject>();
		
		//Pobieramy informacje o stronach z trailami
		Elements trs = new HttpJsoupSrv().id("trails_table").tag("tbody").tags("tr").parse(doc);
		if (trs.size() > 1)
			//Pobieramy informacje o każdym trailu
			for (Element tr : trs) {
				Element td = tr.children().get(1);
				Element a = new HttpJsoupSrv().tag("a").parseOne(td);
				if (a != null) {						
					String trailMainUrl = a.attr("href");					
					TrailXMLObject trailObj = new TrailXMLObject();
					//Adres strony trailem do ściągnięcia, ale to jeszcze nie jest adres samego pliku
					trailObj.setHtml(trailMainUrl+"download/");
					//Nazwa traila
					trailObj.setName(a.text());
					result.put(trailMainUrl, trailObj);
				}
			}
		return result;
	}
	
	public void trailsUpdate() throws Exception{
		try {
			Map<String, TrailXMLObject> trailPagesUrl = new HashMap<String, TrailXMLObject>();
			
			//Pobieranie informacji o ilości podstron
			Document doc1 = hjs.getPageDom("https://www.trailforks.com/region/poland/trails/?difficulty=2,3,4,5,6,8,1,7");
			Elements numbersList = doc1.getElementsByClass("paging-middle centertext");
			
			//Może istnieć tylko jeden element z którego czytamy ilość stron
			CheckIfOne(numbersList);
			Element numbers = numbersList.get(0);
			Integer firstPage = Integer.parseInt(numbers.child(0).text());
			Integer lastPage = Integer.parseInt(numbers.child(numbers.childNodeSize()-1).text());
			
			//Przeglądamy każdą podstronę 
			for (int i = firstPage; i <= lastPage; i++)
			{
				Document doc2 = hjs.getPageDom("https://www.trailforks.com/region/poland/trails/?difficulty=2,3,4,5,6,8,1,7&page=" + i);
				trailPagesUrl.putAll(getTrails(doc2));											
			}
			
			TrailsXMLObject trailsObj = new TrailsXMLObject();
			for(Map.Entry<String, TrailXMLObject> pair : trailPagesUrl.entrySet()) {
				Document doc3 = hjs.getPageDom(pair.getValue().getHtml());					
				Element a = new HttpJsoupSrv().id("file").clazz("inline padded10").tag("li").tag("a").parseOne(doc3);	
				trailsObj.addTrail(pair.getValue());
			}
			
			File file = new File("E:\\trails.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(TrailsXMLObject.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(trailsObj, file);
			
			int t = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
