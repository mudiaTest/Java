package my.com.pl.Hibernate_03.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.NamedAttributeNode;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@NamedEntityGraphs ({
	@NamedEntityGraph(	name = "graph.TestSix.none",
			attributeNodes = {})
	,
	@NamedEntityGraph(	name = "graph.TestSix.lista",
						attributeNodes = @NamedAttributeNode(value = "lazyList", subgraph = "subLista"),
						subgraphs = @NamedSubgraph(name = "subLista", attributeNodes = @NamedAttributeNode("lista"))
	)
	,
	@NamedEntityGraph(	name = "graph.TestSix.listy",
						attributeNodes = {@NamedAttributeNode(value = "lazyList", subgraph = "subLista2")
						, @NamedAttributeNode(value = "eagerList", subgraph = "inLista")
						},
						subgraphs = {@NamedSubgraph(name = "subLista2", attributeNodes = @NamedAttributeNode("lista"))
	}
	
	)
})
public class Test6 {
	@Id
	private long id;
	private int intVal1;
	
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "parentLst1")
	private Set<SubClass6> lazyList = new HashSet<>();
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "parentLst2")
	private Set<SubClass6> eagerList = new HashSet<>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "parent1")
	private SubClass62 sc;
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "parent2")
	private SubClass63 sc2;
	
	public Test6() {}
	
	public Test6(long id, int intVal1) {
		super();
		this.id = id;
		this.intVal1 = intVal1;
	}

	public Test6(long id, int intVal1, int i1, int i2, int i11, int i21, int i111, int i2111) {
		super();
		this.id = id;
		this.intVal1 = intVal1;
		
		SubClass6 s6 = new SubClass6(1, i1);
		s6.getLista().add(new SubClass61(3, i11));
		this.lazyList.add(s6);
		
		s6 = new SubClass6( 4, i2);
		s6.getLista().add(new SubClass61(2, i21));
		this.eagerList.add(s6);
		
		int t = 0;
		sc = new SubClass62(5, i111, this);
		sc2 = new SubClass63(5, i2111);
	}
	
	
}
