package my.com.pl.srv;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import my.com.pl.srv.common.HttpJsoupSrv;
import my.com.pl.srv.common.StringSrv;

import static my.com.pl.srv.common.StringSrv.StrToFile;

@Service
@Slf4j
public class TrailsSrv {
	
	@Autowired
	HttpJsoupSrv hjs;
	@Autowired
	StringSrv ss;
	
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
	
	private Element getLoginForm() throws IOException {
		
		String html= "<form class=\"formCustom \" name=\"loginform\" action=\"/wosFormCheck.php\" method=\"post\"> \r\n" + 
				"         <div>\r\n" + 
				"          <input type=\"hidden\" name=\"ripformname\" value=\"loginform\">\r\n" + 
				"         </div>\r\n" + 
				"         <input type=\"hidden\" name=\"formpage\" value=\"/login/#loginform\"> \r\n" + 
				"         <input type=\"hidden\" name=\"fieldstack[0]\" value=\"source\">\r\n" + 
				"         <input type=\"hidden\" name=\"source-alpha\" value=\"trailforks\"> \r\n" + 
				"         <div class=\"center grey margin-top-10\"> \r\n" + 
				"          <img src=\"https://es.pinkbike.org/246/sprt/i/trailforks/pb_menu_logo.png\" width=\"100\" height=\"24\" alt=\"Pinkbike logo\" title=\"\"> \r\n" + 
				"          <p class=\"margin-top-10\"> You can login to Trailforks with your <a href=\"https://www.pinkbike.com/\" title=\"\">Pinkbike</a> account. <br> Don't have a login? <a href=\"https://www.trailforks.com/signup/\" class=\"bold\" rel=\"nofollow\" title=\"\">Join now!</a> </p> \r\n" + 
				"         </div> \r\n" + 
				"         <div class=\"center block\"> \r\n" + 
				"          <table class=\"block padded5\" style=\"width: auto; margin: 0 auto;\"> \r\n" + 
				"           <tbody>\r\n" + 
				"            <tr> \r\n" + 
				"             <td colspan=\"2\"> <label for=\"username\" class=\"grey\">Email or Username</label> <br> <input type=\"hidden\" name=\"fieldstack[1]\" value=\"username\"><input type=\"text\" name=\"username-login-loginlen\" size=\"30\" id=\"username\" autofocus=\"\" placeholder=\"username\" value=\"\"> </td> \r\n" + 
				"            </tr> \r\n" + 
				"            <tr> \r\n" + 
				"             <td colspan=\"2\"> <label for=\"password\" class=\"grey\">Password:</label> <br> <input type=\"hidden\" name=\"fieldstack[2]\" value=\"password\"><input type=\"password\" name=\"password-password-lt200\" size=\"30\" value=\"\" id=\"password\" placeholder=\"password\"> </td> \r\n" + 
				"            </tr> \r\n" + 
				"            <tr> \r\n" + 
				"             <td colspan=\"2\"></td> \r\n" + 
				"            </tr> \r\n" + 
				"            <tr> \r\n" + 
				"             <td><label for=\"rememberme\">Remember me:</label></td> \r\n" + 
				"             <td><input type=\"hidden\" name=\"fieldstack[3]\" value=\"rememberme\"><input type=\"checkbox\" name=\"rememberme\" checked=\"\" id=\"rememberme\" class=\"remembermeCheckbox\" value=\"\"></td> \r\n" + 
				"            </tr> \r\n" + 
				"            <tr> \r\n" + 
				"             <td><label for=\"logoutother\">Logout all other devices:</label></td> \r\n" + 
				"             <td><input type=\"hidden\" name=\"fieldstack[4]\" value=\"logoutother\"><input type=\"checkbox\" name=\"logoutother\" id=\"logoutother\" value=\"\"></td> \r\n" + 
				"            </tr> \r\n" + 
				"            <tr> \r\n" + 
				"             <td colspan=\"2\"></td> \r\n" + 
				"            </tr> \r\n" + 
				"            <tr> \r\n" + 
				"             <td colspan=\"2\"> <input type=\"submit\" name=\"submitbutton['Login']\" style=\"width:100%;\" value=\"Login\" onclick=\"if(typeof(wO) !== 'undefined') { return false; } else { wO=1;return true;}\"><input type=\"hidden\" name=\"buttondest['Login']\" value=\"https://www.trailforks.com/x_login_form/\"> </td> \r\n" + 
				"            </tr> \r\n" + 
				"           </tbody>\r\n" + 
				"          </table> \r\n" + 
				"         </div> \r\n" + 
				"         <div class=\"center block grey\"> \r\n" + 
				"          <a href=\"https://www.pinkbike.com/user/forgotpassword/\" class=\"underline\" rel=\"nofollow\">Forgot login/password info?</a> \r\n" + 
				"         </div> \r\n" + 
				"         <input type=\"text\" name=\"iebug\" value=\"1\" style=\"display:none\">\r\n" + 
				"         <input type=\"hidden\" name=\"formhash\" value=\"+t9UzozSkiKVu3yg/RbklxuY+ZxOu/O06IA7qGsfV8DFGMCS/+voEzp3aKc0wpcFcHJsbJZrdzkZFVh9v2KiyZoyuVQkg6uwS0PixYW69sH71n6+FfE90vsTv1N7qJOTs3RahKdC7W0KDL+CVvwZdMDc3QQ1An/VzWfdTaimlUgck2BeHPxDEGoD93c2s6oTf9QEucSQqtW2sh69uvRz4S9b2zRsUNMXUzsABzBUhzFqH6g=\" autocomplete=\"off\">\r\n" + 
				"        </form>";
		
		Document form = Jsoup.parse(html);
		Elements inputs = hjs.clear().tags("input").parse(form);
		boolean jestSubmit = false;
		for(Element input : inputs) {
			if (input.attr("type").equals("submit")) {
				jestSubmit = true;
				break;
			}
		}
		if (jestSubmit) {
			log.info("Jest SUBMIT w testowym.");
		}
		else {
			log.info("Brak SUBMITu w testowym.");
		}
		Document loginForm = hjs.getPageDom("https://www.trailforks.com/login/");
		String tmpFilePathBefore = "e:\\loginFormBefore.html";
		String tmpFilePathAfter = "e:\\loginFormAfter.html";
		StrToFile(loginForm.toString(), tmpFilePathBefore);
//
//		System.setProperty("phantomjs.binary.path", "libs/phantomjs") ;
//		WebDriver ghostDriver = new PhantomJSDriver();
//        try {
//            ghostDriver.get(tmpFilePathBefore);
//            Document after = Jsoup.parse(ghostDriver.getPageSource());
//
//            Elements inputs = hjs.clear().clazz("formCustom ").tags("input").parse(after);
//            int t = 8;
//        } finally {
//            ghostDriver.quit();
//        }
		Element el = hjs.clear().tag("form").tag("input", new HashMap<String, String>(){{put("name", "formhash");}}).parseOne(loginForm);
		int t = 8;
		return el;
	}
	
	/*
	 * olać wybieranie przez api listy i wybierać przez stronę, bo brak klucza powoduje oddanie tylko 3
	 * 
	 * poszczegolne wybierać przez api
	 */
	
	/*
	 * pobieranie informacji o trailach [54.84,14.05] - [48.97,24.20]
	 * https://www.trailforks.com/api/1/trails?scope=track&fields=trailid%2Ctitle%2Calias&filter=bbox%3A%3A54.84%2C14.05%2C48.97%2C24.20&rows=500&api_key=docs
	 * 
	 * {
  "error": 0,
  "message": "",
  "data": [
    {
      "trailid": "4001",
      "title": "Sjezd Z Kamenného Vrchu",
      "alias": "sjezd-z-kamenneho-vrchu"
    },
    {
      "trailid": "5781",
      "title": "Frantodrom",
      "alias": "frantodrom"
    },
    {
      "trailid": "5823",
      "title": "Oběšenec",
      "alias": "ob-senec"
    }
  ]
}
	 * 
	 * https://www.trailforks.com/api/1/trail?id=48667&scope=track&api_key=docs - nie można sortowac wybranych pól bo przestaje zwracAĆ TRACK
	 * 
	 * {
  "error": 0,
  "message": "",
  "data": {
    "trailid": "48667",
    "vid": "3",
    "title": "OldSchoolowy",
    "rid": "19253",
    "difficulty": "4",
    "activitytype": 1,
    "trailtype": "1",
    "biketype": "2,3",
    "physical_rating": "0",
    "ttfs": "",
    "wet_weather": "0",
    "season": "",
    "unsanctioned": "1",
    "hidden": "0",
    "rating": "100",
    "votes": "1",
    "ridden": "45",
    "faved": "0",
    "status": "3",
    "products": "",
    "total_comments": "0",
    "total_photos": "0",
    "total_videos": "1",
    "total_reports": "10",
    "total_poi": "0",
    "total_supporters": "3",
    "total_osmways": "0",
    "created": "1454520292",
    "changed": "1497993362",
    "last_comment_ts": "0",
    "last_report_ts": "1561307111",
    "last_totals_ts": "1562154310",
    "alias": "oldschoolowy",
    "approved": "0",
    "userid": "1102293",
    "published": "1",
    "dirty": "0",
    "views": "707",
    "trackid": "59627",
    "condition": "5",
    "confirmid": "38747",
    "download_disable": "0",
    "usage": "2",
    "direction": "2",
    "opendate": "1409554800",
    "strava_segment": "0",
    "strava_segment_reverse": "0",
    "legacy_id": "0",
    "closed": "0",
    "climb_difficulty": "0",
    "land_manager": "",
    "land_manager_manual": "0",
    "refnum": "",
    "global_rank": "4778",
    "global_rank_score": "50.888",
    "total_checkins": "119",
    "total_ridelogs": "83",
    "land_manager_url": "",
    "aka": "",
    "search_terms": "",
    "search": "OldSchoolowy",
    "featured_photo": "0",
    "featured_video": "0",
    "skidmap_id": "0",
    "osmway": "0",
    "password": "",
    "funding_goal": "1000",
    "funding_usd": "0",
    "funding_currency": "",
    "watchmen": "0",
    "cleanup": "0",
    "cleanup2": "0",
    "cleanup3": "",
    "leaderboard_disable": "0",
    "popularity_colour": "ff9e00",
    "popularity_score": "70",
    "season_type": "0",
    "alpine": "0",
    "ebike": "0",
    "color": "",
    "archived": "0",
    "disable_sensitive_check": "0",
    "planned": "0",
    "family_friendly": "0",
    "total_socialmedia": "0",
    "hide_association": "0",
    "cover_photo": "0",
    "trail_association": "0",
    "direction_flagged": "0",
    "direction_forward": "96",
    "direction_backward": "4",
    "inventory_exclude": "0",
    "difficulty_user_avg": "0",
    "difficulty_votes": "0",
    "imagemap3": "1",
    "connector": "0",
    "sac_scale": "0",
    "trail_visibility": "0",
    "datasource": "tf",
    "verified": "1",
    "snow_grooming": "0",
    "osm_import_job": "0",
    "dogs_allowed": "1",
    "restricted_access": "0",
    "id": "48667",
    "type": "trail",
    "description": "",
    "access_info": "",
    "source_text": "",
    "source_link": "",
    "disclaimer": "",
    "act_mtb": "1",
    "act_ebike": "0",
    "act_hike": "1",
    "act_moto": "0",
    "act_trailrun": "1",
    "act_atv": "0",
    "act_snowshoe": "0",
    "act_skialpine": "0",
    "act_skixc": "0",
    "act_skibc": "0",
    "act_horse": "0",
    "act_snowmobile": "0",
    "act_mototrials": "0",
    "latitude": "54.396069",
    "longitude": "18.528847",
    "activitytypes": [
      1,
      6,
      5
    ],
    "track": {
      "latitude": "54.396069,54.39621,54.39636,54.39638,54.39649,54.3966,54.39664,54.39665,54.39669,54.39685,54.39697,54.397,54.39727,54.39735,54.3974,54.39745,54.39756,54.39795,54.3981,54.3984,54.39861,54.3987,54.39882,54.39881,54.39889,54.39895,54.39898,54.39904,54.39953,54.39963,54.39975,54.39992,54.39997,54.39993,54.39987,54.39968,54.39958,54.3995,54.39924,54.3991,54.39875,54.39857,54.39829,54.39814,54.39804,54.39778,54.39753,54.39738,54.39728,54.39715,54.39712,54.39707,54.39699,54.3969,54.39683,54.39681,54.39682,54.39689,54.39687,54.39687",
      "longitude": "18.528847,18.52874,18.52851,18.52851,18.52871,18.52884,18.52905,18.52929,18.52943,18.52973,18.53,18.53012,18.53066,18.53099,18.53108,18.53125,18.53135,18.53162,18.5318,18.53221,18.53263,18.53287,18.53304,18.53315,18.53331,18.53347,18.53362,18.53375,18.53431,18.53454,18.53475,18.53492,18.53492,18.53462,18.53445,18.53417,18.53391,18.53381,18.53334,18.53315,18.53244,18.532,18.53144,18.53119,18.5309,18.53041,18.52985,18.52959,18.52936,18.52888,18.5285,18.52827,18.52812,18.528,18.5278,18.52765,18.52743,18.52723,18.52697,18.52656",
      "altitude": "120.43,118.728,116.691,116.301,114.089,112.362,112.083,112.369,112.297,109.579,106.941,106.529,103.395,104.863,105.174,105.996,104.286,98.457,97.45,96.518,99.148,101.701,102.991,105.129,105.145,104.325,104.265,103.731,100.416,100.541,99.482,97.114,96.262,95.649,94.753,95.731,95.515,96.479,97.472,98.554,93.78,90.033,89.626,89.611,88.476,89.607,90.972,92.593,93.981,95.432,94.512,95.658,98.246,101.217,103.038,103.157,101.907,98.705,98.419,96.957",
      "distance": "0,17.13,39.474,41.697,59.497,74.335,88.629,104.195,114.284,140.605,162.581,171.03,217.08,240.206,248.254,260.576,274.406,321.132,341.465,384.066,419.882,438.35,455.635,462.837,476.481,488.793,499.053,509.785,575.186,593.756,612.792,634.651,640.207,660.116,672.976,700.794,720.953,731.948,773.891,793.718,853.903,888.692,936.447,959.672,981.477,1024.368,1070.021,1093.701,1112.272,1146.52,1171.328,1187.211,1200.372,1213.033,1228.13,1238.085,1252.361,1267.458,1284.424,1310.948",
      "encodedPath": "kf_kIg|apB]R]l@C?Ug@UYGi@Ao@G[_@{@Wu@EWu@kBOaAIQIa@USmAu@]c@{@qAi@sAQo@Wa@@UO_@K_@E]KYaBoBSm@Wi@a@a@I?Fz@J`@d@v@Rt@NPr@|AZd@dAlCb@vAv@nB\\r@Rv@r@`Bp@nB\\r@Rl@X~ADjAHl@N\\PXLd@B\\Aj@Mf@Bt@?nA",
      "encodedLevels": "P@?D?B??B??@A??D?B?C?@AA???CA?A@H@B@@??B@@?@@???D@?B?@C?AA?P"
    }
  }
}
	 */
	
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
			
			int i = 0;
			TrailsXMLObject trailsObj = new TrailsXMLObject();			
			for(Map.Entry<String, TrailXMLObject> pair : trailPagesUrl.entrySet()) {	
				//i++;
				//Co 10 stronę przelogowujemy
				if (i % 10 == 0) {
					Element hashEl = getLoginForm();
					
					Document loginResult = hjs.postPageDom("https://www.trailforks.com/login/", new HashMap() {{
						put("formpage", "/login/#loginform");
						put("fieldstack[0]", "source");
						put("source-alpha", "trailforks");
						
						put("fieldstack[1]", "username");					
						put("username-login-loginlen", "mudia80@gmail.com");
						
						put("fieldstack[2]", "password");
						put("password-password-lt200", "pbshinji#");
						
						put("fieldstack[3]", "rememberme");
						put("rememberme", "true");
						
						put("fieldstack[4]", "logoutother");
						put("logoutother", "logoutother");
						
						//put("", "");
						put("buttondest['Login']", "https://www.trailforks.com/x_login_form/");
						
						put("iebug", "1");
						put("formhash", hashEl.val());
						
						}});
					StrToFile(loginResult.toString(), "e:\\loginResult.html");
					Pattern compiledPattern = Pattern.compile("There are ERRORS on this form. Detail in each field");
					Matcher matcher = compiledPattern.matcher(loginResult.getElementById("loginform").html());
					matcher.find();
					//List<String> lines = rege ss.filterLines(loginResult.getElementById("loginform").html(), "There are ERRORS on this form. Detail in each field");
					int t = 0;
				}
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
