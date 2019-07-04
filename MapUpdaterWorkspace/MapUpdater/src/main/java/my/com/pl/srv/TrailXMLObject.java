package my.com.pl.srv;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrailXMLObject {
	/**
	 * Nazwa traila
	 */
	String name;
	
	/**
	 * Nazwa pliku ".gpx" z trailem
	 */
	String fileName;
	
	/**
	 * Adres strony danego traila
	 */
	String html;
	
	/**
	 * Id trasy
	 */
	String id;
	
	public String getName() {
		return this.name;
	}
	
	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	@XmlElement
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getHtml() {
		return this.html;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	@XmlElement
	public void setHtml(String html) {
		this.html = html;
	}
}
