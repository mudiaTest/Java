package jackson;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class ChildLoopObject {
  public String txt = "child";
  // Zapobiega loop gdy parent ma child, a child parent
  // Powoduje, �e nie b�dzie pasowane. Dzia�a pryz serializacji dziecka, ale przy 
  // serializacji parenta dziecko w og�le jest pomijane
  @JsonManagedReference
  public ParentLoopObject parent;
}
