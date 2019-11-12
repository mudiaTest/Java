package my.com.pl.component;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrailsXML {
	List<TrailXML> trails = new ArrayList<TrailXML>(); 
	
	public List<TrailXML> getTrails() {
		return this.trails;
	}
	
	@XmlElement
	public void setTrails(List<TrailXML> trails) {
		this.trails = trails;
	}
	
	public void addTrail(TrailXML trail) {
		trails.add(trail);
	}
}
