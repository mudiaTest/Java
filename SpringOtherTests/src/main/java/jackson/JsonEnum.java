package jackson;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Adnotacja wskazuje jedn¹ metodê do serializacji ca³ej klasy.
 * Tutaj u¿yte do Enum (dobry pomys³), ale mo¿na tak u¿yæ do ka¿dej
 * klasy i zwracaæ np: {\"txt32\":\"dummy\"}
 */
public enum JsonEnum {
    TYPE1(1, "Type A"), 
    TYPE2(2, "Type 2");
 
    private Integer id;
    private String name;
    
    JsonEnum(Integer id, String name) {
    	this.id = id;
    	this.name = name;
    }
 
    @JsonValue
    public String getName() {
        return name;
    }
}
