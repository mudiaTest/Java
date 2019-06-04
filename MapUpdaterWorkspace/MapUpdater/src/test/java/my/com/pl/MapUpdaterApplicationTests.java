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
import java.util.Map.Entry;

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
import my.com.pl.srv.TrailXMLObject;
import my.com.pl.srv.TrailsSrv;
import my.com.pl.srv.TrailsXMLObject;
import my.com.pl.srv.GMTService;
import my.com.pl.srv.common.HttpJsoupSrv;
import my.com.pl.srv.common.HttpReaderSrv;
import my.com.pl.srv.common.Zip7Srv;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapUpdaterApplicationTests {

	//@Test
	public void contextLoads() {
	}
	
	@Autowired
	HttpReaderSrv ds;
	@Autowired
	Zip7Srv us;
	@Autowired
	GMTService gs;	
	@Autowired
	FilesEnv fenv;
	
	@Autowired
	HttpReaderSrv hrs;	
	
	//@Test
	public void httpGetLines() {
		try {
			List<String> pageLines = hrs.getPage("http://192.168.1.1/");
			int i = 0;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	@Autowired
	TrailsSrv ts;
	
	//Nieużywane
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
	
	//Nieużywane
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

}
