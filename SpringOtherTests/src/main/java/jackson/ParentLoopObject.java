package jackson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class ParentLoopObject {
  public String txt = "parent";
  // Zapobiega loop gdy parent ma child, a child parent
  // Powoduje, �e nie b�dzie pasowane. Dzia�a pryz serializacji dziecka, ale przy 
  // serializacji parenta dziecko w og�le jest pomijane
  @JsonBackReference
  public List<ChildLoopObject> child;
}
