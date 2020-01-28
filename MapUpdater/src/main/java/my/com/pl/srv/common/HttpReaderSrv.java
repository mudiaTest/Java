/*
 * Adres do pobrania strony
 * http://alternativaslibres.org/en/download.php?file=OpenStreetMap_Poland.exe
 */

package my.com.pl.srv.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.pl.config.ConnectionEnv;

@Service
public class HttpReaderSrv {
	@Autowired
	ConnectionEnv cenv;
	@Autowired
	StringSrv comms; 
	
	/**
	 * Pobieranie pliku
	 * @param url
	 * @param fileName
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public boolean fileDownload(String url, String fileName) throws MalformedURLException, IOException {
		FileUtils.copyURLToFile(
				  new URL(url), 
				  new File(fileName)//, 
				 // Integer.decode(cenv.getConnectTimeout()), 
				 // Integer.decode(cenv.getReadTimeout())
		);
		return true;
	}
	
	/**
	 * Próba wyspecjalizowanej metody do pobierania url pliku 
	 */
//	public String getFileUrl(String phpUrl) throws IOException {
//		String location = phpUrl;
//		HttpURLConnection connection = null;
//		for (;;) {
//		    URL url = new URL(location);
//		    connection = (HttpURLConnection) url.openConnection();
//		    connection.setInstanceFollowRedirects(true);
//		    String redirectLocation = connection.getHeaderField("Location");
//		    if (redirectLocation == null) break;
//		    location = redirectLocation;
//		}
//		String fileName = location.substring(location.lastIndexOf('/') + 1, location.length());
//		return fileName;
//	}
	
	@Autowired
	StringSrv ss;
	
	/**
	 * Oddaje kod strony w postaci listy linii (stringów); 	 
	 * @param url = np: 'www.rp.pl', 'http://192.168.1.1/'
	 * @return Kod strony
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<String> getPage(String url) throws ClientProtocolException, IOException
	{
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);				
		HttpResponse response = client.execute(request);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());	
		
		//Pobranie odpowiedzi jako jednego stringa
		//String bodyAsString = EntityUtils.toString(response.getEntity(), "UTF-8");	
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
	    return ss.bufferedReader2List(rd);
	}	
	
	/*
	 * Poza prostym Get i Post można wywołać stronę z róznymi parametrami
	 * 
	 * Jsoup.connect("http://www.example.com/postpage")
     * .userAgent("Mozilla/5.0")
     * .timeout(10 * 1000)
     * .method(Method.POST)
     * .data("txtloginid", "YOUR_LOGINID")
     * .data("txtloginpassword", "YOUR_PASSWORD")
     * .data("random", "123342343")
     * .data("task", "login")
     * .data("destination", "/welcome")
     * .followRedirects(true)
     * .execute();
     * 
     * Przykład przekazywania danych do post
     *   .data("selected", "Option2") -> .data( name="selected" , value="Option2" )
	 * Jest odpowiednikiem
	 *   <select name="selected">
	 *     <option value="Option1">Option 1</option>
	 *     <option selected="selected" value="Option2">Option 2</option>
	 *     <option value="Option3">Option 3</option>
	 *   </select>
     */
	/**
	 * Pobieranie 
	 * @param url = np: 'www.rp.pl', 'http://192.168.1.1/'
	 * @param pattern Wzór zgodny z zasadami RegExp
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<String> getLines(String url, String pattern) throws ClientProtocolException, IOException
	{
		return comms.filterLines(getPage(url), pattern);	
	}
}
