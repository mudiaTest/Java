package my.com.pl;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import my.com.pl.srv.TrailsSrv;
import my.com.pl.srv.common.HttpJsoupSrv;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrailsTests {

	@Autowired
	HttpJsoupSrv hjs;
	
	@Autowired
	TrailsSrv ts;
	
	/*
	 * Elemnts = List<Element>
	 * Element.parentNode -> Element
	 * Element.children -> List<Element>
	 *   - nie pokazuje nodów, które zostały doczytane potem
	 */
	//@Test
	public void httpGetDocument() {
		try {
			Document doc = hjs.getPageDom("http://namzalezy.pl/");
			Elements body = doc.select("body");
			Elements divs = doc.select("div");
			Element div = doc.selectFirst("div");
			Elements el1 = doc.getElementsByAttribute("cookies-message");
			Elements el2 = doc.getElementsByAttribute("id");
			Elements el3 = doc.getElementsByAttributeValue("id", "cookies-message-container");
			Elements el4 = doc.getElementsByAttributeValueContaining("id", "cookies-mes");
			int i = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void trailForks1() throws Exception {
		ts.trailsUpdate();
	}
}
