package my.com.pl.srv;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrailXMLObject {
	String name;
	String fileName;
	String html;
	
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
	
	@XmlElement
	public void setHtml(String html) {
		this.html = html;
	}
}
