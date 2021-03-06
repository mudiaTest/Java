package my.com.pl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//import lombok.extern.log4j.Log4j;
import my.com.pl.config.TrailForksEnv;
import my.com.pl.srv.ExternalSev;
import my.com.pl.srv.TrailsSrv;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Log4j
public class TrailsTests {

//	@Autowired
//	HttpJsoupSrv hjs;	
	@Autowired
	TrailsSrv ts;
//	@Autowired
//	MapUpdaterSrv mus;
	@Autowired
	TrailForksEnv tfv;
	@Autowired
	ExternalSev es;
	
//	@Test
	public void recreateImgTest() throws Exception {
		ts.recreateImg();
	}
	
//    @Test
	public void installTest() throws Exception {
		ts.install();
	}
		
//	@Test
	public void recreateAndInstallTest() throws Exception {
		ts.recreateAndInstall();
	}
	
	//@Test
	public void mpToImgTest() throws Exception {
		es.createMapsetImg(tfv.getMpFile(), tfv.getImgFile());
		System.out.println("File 'mapset.img created using cgpsmapper.");
	}
		
}
