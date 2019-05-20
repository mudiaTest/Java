/*
 * Adres do pobrania strony
 * http://alternativaslibres.org/en/download.php?file=OpenStreetMap_Poland.exe
 */

package my.com.pl.srv;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.pl.config.ConnectionEnv;

@Service
public class DownloadSrv {
	@Autowired
	ConnectionEnv cenv;
	
	public boolean fileDownload(String url, String fileName) throws MalformedURLException, IOException {
		FileUtils.copyURLToFile(
				  new URL(url), 
				  new File(fileName)//, 
				 // Integer.decode(cenv.getConnectTimeout()), 
				 // Integer.decode(cenv.getReadTimeout())
		);
		return true;
	}
	
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
//	
//	private List<String> BufferedReader2List(BufferedReader br)
//	{
//		List<String> lines = new ArrayList<>();
//	    br.lines().forEach(s->lines.add(s));
//	    //br.lines().limit(30).forEach(s->lines.add(s));
//	    return lines;
//	}
//	
//	public List<String> getPage(String phpUrl) throws ClientProtocolException, IOException
//	{
//		HttpClient client = HttpClientBuilder.create().build();
//		HttpGet request = new HttpGet(phpUrl);		
//		HttpResponse response = client.execute(request);
//		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());	
//		
//		//Pobranie odpowiedzi jako jednego stringa
//		//String bodyAsString = EntityUtils.toString(response.getEntity(), "UTF-8");	
//		
//		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
//	    return BufferedReader2List(rd);
//	}		
	
//	public void unzipZip4J(String fileNamePath, String dstDirPath) {
//		String password = "password";
//
//		try {
//		    ZipFile zipFile = new ZipFile(fileNamePath);
//		    //if (zipFile.isEncrypted()) {
//		    //    zipFile.setPassword(password);
//		   // }
//		    zipFile.extractAll(dstDirPath);
//		    int t = 0;
//		} catch (ZipException e) {
//		    e.printStackTrace();
//		}
//	}
}
