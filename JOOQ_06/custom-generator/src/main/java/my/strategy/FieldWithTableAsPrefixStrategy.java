package my.strategy;

import java.util.List;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

public class FieldWithTableAsPrefixStrategy extends DefaultGeneratorStrategy  {

  @Override
  public void setInstanceFields(boolean instanceFields) {
    // TODO Auto-generated method stub
    super.setInstanceFields(instanceFields);
  }

  @Override
  public boolean getInstanceFields() {
    // TODO Auto-generated method stub
    return super.getInstanceFields();
  }

  @Override
  public void setJavaBeansGettersAndSetters(boolean javaBeansGettersAndSetters) {
    // TODO Auto-generated method stub
    super.setJavaBeansGettersAndSetters(javaBeansGettersAndSetters);
  }

  @Override
  public boolean getJavaBeansGettersAndSetters() {
    // TODO Auto-generated method stub
    return super.getJavaBeansGettersAndSetters();
  }

  @Override
  public String getTargetDirectory() {
    // TODO Auto-generated method stub
    return super.getTargetDirectory();
  }

  @Override
  public void setTargetDirectory(String directory) {
    // TODO Auto-generated method stub
    super.setTargetDirectory(directory);
  }

  @Override
  public String getTargetPackage() {
    // TODO Auto-generated method stub
    return super.getTargetPackage();
  }

  @Override
  public void setTargetPackage(String packageName) {
    // TODO Auto-generated method stub
    super.setTargetPackage(packageName);
  }

  @Override
  public String getFileHeader(Definition definition, Mode mode) {
    // TODO Auto-generated method stub
    //??
    return super.getFileHeader(definition, mode);
  }

  @Override
  public String getJavaIdentifier(Definition definition) {
    // TODO Auto-generated method stub
//    String NL = "_";
//    String t = "";
//    t=t
//        +definition.getInputName()+NL// rv
//        +definition.getName()+NL// rv
//        +definition.getDefinitionPath()+NL // 5bpublic_2c_public_test1_2c_public_test1_rv_5d
//        +definition.getOutputName()+NL // rv
//        +definition.getOverload()+NL // null
//        +definition.getQualifiedInputName()+NL //public_test1_rv
//        +definition.getQualifiedName()+NL // public_test1_rv
//        +definition.getQualifiedOutputName()+NL // public_test1_rv
//        +definition.getQualifiedNamePart()+NL // _22public_22__22test1_22__22rv_22
//        +definition.getQualifiedOutputNamePart() // _22public_22__22test1_22__22rv_22
//        ;
        
        
//    return super.getJavaIdentifier(definition)+"__"+t;
//    return super.getJavaIdentifier(definition) + " as " + super.getJavaIdentifier(definition);
    return super.getJavaIdentifier(definition);
  }

  @Override
  public String getJavaSetterName(Definition definition, Mode mode) {
    // TODO Auto-generated method stub
    //if (mode==Mode.RECORD) {
//      return "5_"+super.getJavaSetterName(definition, mode);
    //}
    //else
      return super.getJavaSetterName(definition, mode);
  }

  @Override
  public String getJavaGetterName(Definition definition, Mode mode) {
    // TODO Auto-generated method stub
    //gettery w record
    return super.getJavaGetterName(definition, mode);
  }

  @Override
  public String getJavaMethodName(Definition definition, Mode mode) {
    // TODO Auto-generated method stub
    return super.getJavaMethodName(definition, mode);
  }

  @Override
  public String getJavaClassExtends(Definition definition, Mode mode) {
    // TODO Auto-generated method stub
    return super.getJavaClassExtends(definition, mode);
  }

  @Override
  public List<String> getJavaClassImplements(Definition definition, Mode mode) {
    // TODO Auto-generated method stub
    return super.getJavaClassImplements(definition, mode);
  }

  @Override
  public String getJavaClassName(Definition definition, Mode mode) {
    // TODO Auto-generated method stub
    //Nazwy klas dla tabel rekordów etc
    return super.getJavaClassName(definition, mode);
  }

  @Override
  public String getJavaPackageName(Definition definition, Mode mode) {
    // TODO Auto-generated method stub
    return super.getJavaPackageName(definition, mode);
  }

  @Override
  public String getJavaMemberName(Definition definition, Mode mode) {
    // TODO Auto-generated method stub
    //nazwy pól dla pojo 
    return super.getJavaMemberName(definition, mode);
  }

  @Override
  public String getOverloadSuffix(Definition definition, Mode mode, String overloadIndex) {
    // TODO Auto-generated method stub
    //???
    return super.getOverloadSuffix(definition, mode, overloadIndex);
  }

}
