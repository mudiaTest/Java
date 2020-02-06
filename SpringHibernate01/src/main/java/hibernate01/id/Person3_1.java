package hibernate01.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.NamedQuery;

@Entity
public class Person3_1 {
	
	@Id
	/*
	 * - @GeneratedValue z DOMYŒLNYMI WARTOŒCIAMI (strategy=AUTO) w Hibernate pozwala na pracê z klasami :
	 *   Long, Integer, Short, BigInteger, BigDecimal i dodatkowo z java.util.UUID. 
	 *   Na pewno nie dzia³a ze String, Float
	 * - Wartoœci bêd¹ pobierane z domyœlnej i wpó³dzielonej sekwencji HIBERNATE_SEQUENCE 
	 *   lub (gdy baza nie wspiera sekwencji) z TableGenetator
	 */
	@GeneratedValue
	public Long key1;
	public String name;		
}
