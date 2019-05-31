package my.com.pl.srv;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrailsXMLObject {
	List<TrailXMLObject> trails = new ArrayList<TrailXMLObject>(); 
	
	public List<TrailXMLObject> getTrails() {
		return this.trails;
	}
	
	@XmlElement
	public void setTrails(List<TrailXMLObject> trails) {
		this.trails = trails;
	}
}
