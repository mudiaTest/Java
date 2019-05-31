package my.com.pl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import my.com.pl.config.FilesEnv;
import my.com.pl.srv.HttpReaderService;
import my.com.pl.srv.TrailXMLObject;
import my.com.pl.srv.TrailsXMLObject;
import my.com.pl.srv.GMTService;
import my.com.pl.srv.HttpParserHelper;
import my.com.pl.srv.UnzipService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapUpdaterApplicationTests {

	//@Test
	public void contextLoads() {
	}
	
	@Autowired
	HttpReaderService ds;
	@Autowired
	UnzipService us;
	@Autowired
	GMTService gs;	
	@Autowired
	FilesEnv fenv;
	
//	@Test	@Test                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           gggggggg@Test
	public void dummy() {
		
	}
	
	@Autowired
	HttpReaderService hs;
	
	//@Test
	public void httpGetLines() {
		try {
			List<String> pageLines = hs.getPage("http://192.168.1.1/");
			int i = 0;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Elemnts = List<Element>
	 * Element.parentNode -> Element
	 * Element.children -> List<Element>
	 *   - nie pokazuje nodów, które zostały doczytane potem
	 */
	//@Test
	public void httpGetDocument() {
		try {
			Document doc = hs.getPageDom("http://namzalezy.pl/");
			Elements body = doc.select("body");
			Elements divs = doc.select("div");
			Element div = doc.selectFirst("div");
			Elements el1 = doc.getElementsByAttribute("cookies-message");
			Elements el2 = doc.getElementsByAttribute("id");
			Elements el3 = doc.getElementsByAttributeValue("id", "cookies-message-container");
			Elements el4 = doc.getElementsByAttributeValueContaining("id", "cookies-mes");
//			Elements el5 = doc.getElements
//			Elements el6 = doc.getElements
//			Elements el7 = doc.getElements
//			Elements el8 = doc.getElements
//			Elements el9 = doc.getElements
//			Elements el10 = doc.getElements
//			Elements el11 = doc.getElements
			int i = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void CheckIfOne(Collection col) throws Exception
	{
		if (col.size() != 1)
			throw new Exception("Znaleziono " + col.size());
	}
	
	private Map<String, TrailXMLObject> getTrails(Document doc) throws Exception{
		Map<String, TrailXMLObject> result = new HashMap<String, TrailXMLObject>();
		
		Elements trs = new HttpParserHelper().id("trails_table").tag("tbody").tags("tr").parse(doc);
		if (trs.size() > 1)
			for (Element tr : trs) {
				
		//		Elements rows = trs.getElementsByTag("tr");
		//		Element table = doc.getElementById("trails_table");
		//		Elements tbody = table.getElementsByTag("tbody");
		//		CheckIfOne(tbody);
		//		Elements rows = tbody.get(0).getElementsByTag("tr");		
				
				//for (int i = 0; i < tr.size(); i++)
				//{
					//Element row = rows.get(i);
					Element td = tr.children().get(1);
					
					
					Element a = new HttpParserHelper().tag("a").parseOne(td);
	//				Elements a = col.getElementsByTag("a");
	//				CheckIfOne(a);
					if (a != null) {						
						String trailMainUrl = a.attr("href");					
						TrailXMLObject trailObj = new TrailXMLObject();
						trailObj.setHtml(trailMainUrl+"download/");
						trailObj.setName(a.text());
						result.put(trailMainUrl, trailObj);
					}
				//}
			}
		return result;
	}
	
	
	
	@Test
	public void trailForks1() throws Exception {
		try {
			Map<String, TrailXMLObject> trailPagesUrl = new HashMap<String, TrailXMLObject>();
			//Pobieranie informacji o ilości podstron
			Document doc1 = hs.getPageDom("https://www.trailforks.com/region/poland/trails/?difficulty=2,3,4,5,6,8,1,7");
			Elements numbersList = doc1.getElementsByClass("paging-middle centertext");
			//Może istnieć tylko jeden element z którego czytamy ilość stron
			CheckIfOne(numbersList);
			Element numbers = numbersList.get(0);
			Integer firstPage = Integer.parseInt(numbers.child(0).text());
			Integer lastPage = Integer.parseInt(numbers.child(numbers.childNodeSize()-1).text());
			
			
			//Przeglądamy każdą podstronę
			for (int i = firstPage; i <= lastPage; i++)
			{
				Document doc2 = hs.getPageDom("https://www.trailforks.com/region/poland/trails/?difficulty=2,3,4,5,6,8,1,7&page=" + i);
				trailPagesUrl.putAll(getTrails(doc2));											
			}
			
			TrailsXMLObject trailsObj = new TrailsXMLObject();
			for (int i = 0; i < trailPagesUrl.size(); i++) {
				TrailXMLObject trailObj = trailPagesUrl.get(i);
				Document doc3 = hs.getPageDom(trailObj.getHtml());	
				
				Element a = new HttpParserHelper().id("file").clazz("inline padded10").tag("li").tag("a").parseOne(doc3);			
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
	
//	@Test
	public void chainTest() {
		
		boolean goNext = true;
		if(! fenv.getUsePreparedTmpLocation()){
			/*
			 * Sprawdzanie istnienia pliku ze zródłową mapą
			 */
			File f = new File(fenv.getDownloadedFile());
			goNext = f.exists();	
			if (!goNext){
				System.out.println("Brak pliku '"+fenv.getDownloadedFile()+"'");
				return;
			}
			/*
			 * unzip
			 */
			goNext = unzip(Paths.get(fenv.getDownloadedFile()), Paths.get(fenv.getTmpLocation()));
			if (!goNext){
				System.out.println("Nie powiodła się akcja rozpakowywania.");
				return;	
			}
		}
		
		/*
		 * Tworzenie mapy
		 */
		if (fenv.getKdDst() > 3) {
			System.out.println("Niezdefiniowana akcja o kodzie '" + fenv.getKdDst() + "'");
			return;
		}		
//		if (fenv.getKdDst() == 1 || fenv.getKdDst() == 3) {
//			goNext =  addMapToMapSource();
//			if (!goNext){
//				System.out.println("Nie powiodła się akcja instalacji mapy");
//				return;	
//			}
//		}
		if (fenv.getKdDst() == 2 || fenv.getKdDst() == 3) {
			goNext =  addMapToCard();
			if (!goNext){
				System.out.println("Nie powiodła się akcja utworzenia mapy");
				return;	
			}
		}
	}	
	
	private boolean download(Path tmpFilePath) {
		boolean exists;
		try {
			String fileUrl = "https://www.nuug.no/pub/openstreetmap/frikart/garmin/poland/roadmap/windows/OSM_Roadmap_Poland.exe";
			
			if (!Files.exists(tmpFilePath, java.nio.file.LinkOption.NOFOLLOW_LINKS)) {			
				exists = ds.fileDownload(
						fileUrl, 
						tmpFilePath.toString()
						);
			}
			else {
				exists = true;
			}
			return exists;
		}
		catch (Exception e) {
			int t = 0;
			return false;
		}
	}
	
	private boolean unzip(Path tmpFilePath, Path tmpTmpDirPath) {
		try {			
			us.unzip7Zip(tmpFilePath.toString(), tmpTmpDirPath);
//			ds.unzipArchive(tmpFilePath.toString(), tmpTmpDirPath.toString());
//			ds.unzipZip4J(tmpFilePath.toString(), tmpTmpDirPath.toString());
		}
		catch (Exception e) {
			System.out.println("Unzip error: "+e.getMessage());
			return false;
		}
		System.out.println(" -- 'unzip' done --");
		return true;
	}
	
	private boolean addMapToMapSource() {
		try {			
			gs.addToMapSource(
					fenv.getTypFid(), 
					fenv.getDstMapSourceDir(), 
					fenv.getDstMapName(), 
					fenv.getScrImgs(), 
					fenv.getSrcTypFilePath(),
					fenv.getPriority(),
					fenv.getTransparency()
					);
			return true;
		}
		catch (Exception e) {
			System.out.println("GMTSrv error: "+e.getMessage());
			return false;
		}
	}
	
	private boolean addMapToCard() {
		String t = fenv.getDstMapNumber();
		System.out.println(t);
		t = t;
		try {			
			gs.addToCard(
					fenv.getTypFid(), 
					fenv.getDstCardPath(), 
					fenv.getDstMapName(), 
					fenv.getScrImgs(), 
					fenv.getSrcTypFilePath(),
					fenv.getPriority(),
					fenv.getTransparency()
					);
			return true;
		}
		catch (Exception e) {
			System.out.println("GMTSrv error: "+e.getMessage());
			return false;
		}
	}

}
