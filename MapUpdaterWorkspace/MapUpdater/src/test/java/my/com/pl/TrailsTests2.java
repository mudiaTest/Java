package my.com.pl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.log4j.Log4j;
import my.com.pl.config.TrailForksEnv;
import my.com.pl.srv.MapUpdaterSrv;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j
public class TrailsTests2 {

//	@Autowired
//	HttpJsoupSrv hjs;	
//	@Autowired
//	TrailsSrv ts;
//	@Autowired
//	MapUpdaterSrv mus;
//	@Autowired
//	TrailForksEnv tfv;
	
//	@Test
//	public void recreateImgTest() throws Exception {
////		ts.recreateImg();
//	}
//		
//	//@Test
//	public void recreateAndInstallTest() throws Exception {
////		ts.recreateAndInstall();
//	}
	
	@Autowired
	MapUpdaterSrv mus;
	@Autowired
	TrailForksEnv tfv;
	
	@Test
	public void fullTest() {
		mus.run();
	}
	
	/*
	 * Elemnts = List<Element>
	 * Element.parentNode -> Element
	 * Element.children -> List<Element>
	 *   - nie pokazuje nodów, które zostały doczytane potem
	 */
	//@Test
//	public void httpGetDocument() {
//		try {
//			Document doc = hjs.getPageDom("http://namzalezy.pl/");
//			Elements body = doc.select("body");
//			Elements divs = doc.select("div");
//			Element div = doc.selectFirst("div");
//			Elements el1 = doc.getElementsByAttribute("cookies-message");
//			Elements el2 = doc.getElementsByAttribute("id");
//			Elements el3 = doc.getElementsByAttributeValue("id", "cookies-message-container");
//			Elements el4 = doc.getElementsByAttributeValueContaining("id", "cookies-mes");
//			int i = 0;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}	
}
