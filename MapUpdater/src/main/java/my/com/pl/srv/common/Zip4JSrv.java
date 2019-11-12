/*
 * Zip jest obsługiwany przez moduł net.lingala.zip4j.core zamiast java.util.zip
 */
package my.com.pl.srv.common;

import static my.com.pl.srv.common.AssertionSrv.Assert;

import org.springframework.stereotype.Service;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

@Service
public class Zip4JSrv {
	public void unzipZip4J(String fileNamePath, String dstDirPath, String password) {	
		try {
		    ZipFile zipFile = new ZipFile(fileNamePath);
		    if (zipFile.isEncrypted()) {
		    	Assert(password.equals(""), "Puste hasło.");
		        zipFile.setPassword(password);
		    }
		    zipFile.extractAll(dstDirPath);
		} catch (ZipException e) {
		    e.printStackTrace();
		}
	}
}
