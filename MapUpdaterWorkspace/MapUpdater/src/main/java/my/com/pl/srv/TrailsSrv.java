package my.com.pl.srv;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;	
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Arrays;
import org.assertj.core.util.Files;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import my.com.pl.component.RawTrackData;
import my.com.pl.component.TrailData;
import my.com.pl.component.TrailXML;
import my.com.pl.component.TrailsXML;
import my.com.pl.config.TrailForksEnv;
import my.com.pl.srv.common.HttpJsoupSrv;
import my.com.pl.srv.common.ParameterStringBuilder;
import my.com.pl.srv.common.StringSrv;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

import static my.com.pl.srv.common.AssertionSrv.*;
import static my.com.pl.srv.common.StringSrv.*;

@Service
@Slf4j
public class TrailsSrv {
	
	@Autowired
	HttpJsoupSrv hjs;
	@Autowired
	MPSrv mps;
	@Autowired
	TrailForksEnv tfv;
	
	private void CheckIfOne(Collection col) throws Exception
	{
		if (col.size() != 1)
			throw new RuntimeException("Znaleziono " + col.size());
	}
	
	/**
	 * Pobieramy informacje o dostępnych trailach
	 * @param doc Kod całej strony z listą ścieżek. 
	 * @return Mapa: klucz - url strony traila, val - TrailXMLObject
	 * @throws Exception
	 */
	private Map<String, TrailXML> getTrails(Document doc) throws Exception{
		Map<String, TrailXML> result = new HashMap<String, TrailXML>();
		
		//Pobieramy informacje o stronach z trailami
		Elements trs = hjs.clear().id("trails_table").tag("tbody").tags("tr").parse(doc);
		if (trs.size() > 1)
			//Pobieramy informacje o każdym trailu
			for (Element tr : trs) {
				Element ulId = hjs.clear().clazz("star-rating allowvoting").tag("ul").parseOne(tr);
				Element aName = hjs.clear().tag("td", null, 1).tag("a").parseOne(tr);
				Assert(ulId != null, "Brak komórki tdId");
				Assert(aName != null, "Brak komórki aName'");				
				TrailXML trailObj = new TrailXML();
				String trailUrl = aName.attr("href");
				trailObj.setHtml(trailUrl);
				trailObj.setName(aName.text());
				String id = Arrays.asList(ulId.attr("id").split("_")).stream().reduce((first, second) -> second).orElse(null).toString();
				trailObj.setId(id);
				result.put(id, trailObj);
			}
		return result;
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

https://www.trailforks.com/api/1/trail?id=48667&scope=track&api_key=docs - nie można sortowac wybranych pól bo przestaje zwracAĆ TRACK
	 */
	
	/*private void GetTrack(String id) throws Exception {
		//Przygotowywanie zapytania API
		URL url = new URL("https://www.trailforks.com/api/1/trail?id=178177&scope=track&api_key=docs");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("scope", "track");
        parameters.put("api_key", "docs");
        con.setDoOutput(true);
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        //Przygotowywanie obiektu przechowującego odpowiedz
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();
        //Odpalenie zapytania
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        //Zbieranie wyników  
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
	}*/
	
	/**
	 * Pobiera dane dla traila za pomocą API serwisu
	 * @param id - cyfrowy indentyfikator traila
	 * @return 
	 * @throws Exception
	 */
	private TrailData GetRawTrailData(String id) throws Exception {
		
		//Przygotowywanie zapytania API		
		URL url = new URL("https://www.trailforks.com/api/1/trail?id="+id+"&scope=track&api_key=docs");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);        
        //Wykomentowany kod nie działa - problemy z pabijaniem parametrów
	        /*URL url = new URL("https://www.trailforks.com/api/1/trail");
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        con.setRequestMethod("POST");
	        Map<String, String> parameters = new HashMap<>();
	        parameters.put("id", id);
	        parameters.put("scope", "track");
	        parameters.put("api_key", "docs");
	        con.setDoOutput(true);
	        con.setConnectTimeout(5000);
	        con.setReadTimeout(5000);
	        //Przygotowywanie obiektu przechowującego odpowiedz
	        DataOutputStream out = new DataOutputStream(con.getOutputStream());
	        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
	        out.flush();
	        out.close();*/ 
        
        //Odpalenie zapytania
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        //Zbieranie wyników  
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }        
        in.close();
        
        //Parsowanie pobranego JSona
        ObjectMapper mapper = new ObjectMapper();		
		JsonNode root = mapper.readTree(content.toString()); 
		JsonNode dataNode =root.get("data");  
		TrailData result = mapper.treeToValue(dataNode, TrailData.class);
        return result;
	}
	
	@Autowired
	GMTService gs;
	/**
	 * Tworzy od nowa pliki zródłowe mapy: img i mp 
	 * @throws Exception
	 */
	public void recreateImg() throws Exception{
		try {
			Map<String, TrailXML> trailPagesUrl = new HashMap<String, TrailXML>();
			
			//Pobieranie informacji o ilości podstron
			Document doc1 = hjs.getPageDom("https://www.trailforks.com/region/" + tfv.getRegion() + "/trails/?difficulty=" + tfv.getDifficulty());
			Elements numbersList = doc1.getElementsByClass("paging-middle centertext");
			
			//Może istnieć tylko jeden element z którego czytamy ilość stron
			CheckIfOne(numbersList);
			Element numbers = numbersList.get(0);
			Integer firstPage = Integer.parseInt(numbers.child(0).text());
			Integer lastPage = Integer.parseInt(numbers.child(numbers.childNodeSize()-1).text());
			
			//Przeglądamy każdą podstronę zbierając z niej informacje o trailach
			for (int i = firstPage; i <= /*lastPage*/firstPage; i++)
			{
				System.out.println("Zbieranie listy traili ze strony " + i + "/" + lastPage);
				Document doc2 = hjs.getPageDom("https://www.trailforks.com/region/" + tfv.getRegion() + "/trails/?difficulty=" + tfv.getDifficulty() + "&page=" + i);
				trailPagesUrl.putAll(getTrails(doc2));		
				System.out.println("Lącznie znaleziono " + trailPagesUrl.size() + " traili.");
			}
			
			//Pobieranie do trails danych o trailach (za pośrednictwem API) 
			List<TrailData> trails = new ArrayList<TrailData>();		
			int loop = 0;
			for(Map.Entry<String, TrailXML> pair : trailPagesUrl.entrySet()) {							
				TrailData trailData = GetRawTrailData(pair.getValue().getId());
				if (trailData == null) { 
					System.out.println("Trail nie został pobrany - może być hidden; " + pair.getValue().getInfo());
					continue;
				}
				trailData.TrackFromRaw();
				trails.add(trailData);	
				loop++;
				if (loop > 0 && loop % tfv.getLoopstep() == 0)
					System.out.println("Zbierano " + loop + "/" + trailPagesUrl.size() + " traili");
			}
			System.out.println("Zbierano " + loop + "/" + trailPagesUrl.size() + " traili");
			
			//Utworzenie linii pliku MP z traili
			List<String> lines = mps.getMpLines(trails);
			
			System.out.println("Tworzenie pliku: '" + tfv.getMpFile() + "'");
			//Zapis do pliku mp
			StrLstToFile(lines, tfv.getMpFile());
			
//			try {
//				//tu dodać tworzenie img files.scrImg z mp
//				//gs.createMapsetImg(outputDir, "mapset.mp", "mapset.img");
//				//System.out.println("File 'mapset.img created using cgpsmapper.");
//			}
//			finally {
//				if (tfv.isDeleteMpFile()) {
//					System.out.println("Usuwanie pliku: '" + tfv.getMpFile() + "'");
//					log.info("Usuwanie pliku: '" + tfv.getMpFile() + "'");
//					Files.delete(new File(tfv.getMpFile()));
//				}
//			}
			
			/*
			File file = new File("E:\\trails.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(TrailsXML.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(trailsObj, file);
			*/
			
			int i = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Autowired
	MapUpdaterSrv mus;
	public void recreateAndInstall() throws Exception {
		recreateImg();
		try {
			mus.run();
		}
		finally {
			if (tfv.isDeleteMpFile()) {
				System.out.println("Usuwanie pliku: '" + tfv.getMpFile() + "'");
				log.info("Usuwanie pliku: '" + tfv.getMpFile() + "'");
				Files.delete(new File(tfv.getMpFile()));
			}
		}
	}
}
