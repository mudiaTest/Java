package jackson;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Adnotacja wskazuje jedn� metod� do serializacji ca�ej klasy.
 * Tutaj u�yte do Enum (dobry pomys�), ale mo�na tak u�y� do ka�dej
 * klasy i zwraca� np: {\"txt32\":\"dummy\"}
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
