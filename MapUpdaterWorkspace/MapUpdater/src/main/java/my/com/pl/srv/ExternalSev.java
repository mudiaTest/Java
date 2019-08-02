package my.com.pl.srv;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service
public class ExternalSev {
	/*
	 * Z podanego pliku mp tworzy plik img
	 */
	public void createMapsetImg(String mpFilePath, String imgFileName) throws Exception {
		if (!(new File(mpFilePath)).exists())
			throw new Exception("" + mpFilePath + " does not exist.");
		Runtime rt = Runtime.getRuntime();
		String command = "cgpsmapper " + mpFilePath + " -o " + imgFileName;
		System.out.println("-> " + command);
		Process pr1 = rt.exec(command);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr1.getInputStream()));
		
		BufferedReader stdError = new BufferedReader(new InputStreamReader(pr1.getErrorStream()));
		String line;
		System.out.print("cgpsmapper ");
		while(pr1.isAlive())
		{
			System.out.print(".");
			//Opróżnianie input, aby nie zablokować działania procesu
			while ((line = stdInput.readLine()) != null) {
//				System.out.println(" input -> " + line);
			}
			while ((line = stdError.readLine()) != null) {
				System.out.println(" error -> " + line);
			}
			Thread.sleep(1000);		
		}
		System.out.println("");
		String imgFilePath = imgFileName;
		if (!(new File(imgFilePath)).exists())
			throw new Exception("" + imgFilePath + " does not exist.");
	}
}
