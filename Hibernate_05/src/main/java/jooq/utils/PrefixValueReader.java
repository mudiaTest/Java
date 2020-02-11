package jooq.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Field;
import org.jooq.Record;
import org.modelmapper.jooq.RecordValueReader;
import org.modelmapper.spi.ValueReader;
import org.modelmapper.spi.ValueReader.Member;

public class PrefixValueReader implements ValueReader<Record> {
  
  private String prefix;
  
  public PrefixValueReader(String prefix) {
    this.prefix = prefix;
  }
  
  public Object get(Record source, String memberName) {
      Field<?> field = matchField(source, memberName);
      if (field != null) {
        return source.getValue(field);
      }
      return null;
  }

  public Member<Record> getMember(Record source, String memberName) {
      return new Member<Record>(Record.class) {
        @Override
        public Object get(Record source, String memberName) {
          return PrefixValueReader.this.get(source, memberName);
        }
      };
  }

  private Field<?> matchField(Record source, String memberName) {
      for (Field<?> field : source.fields())
        if (memberName.equalsIgnoreCase( field.getName() ))
          return field;
      return null;
  }

  public Collection<String> memberNames(Record source) {
      Field<?>[] fields =  Arrays.asList(source.fields()).stream().filter(f->f.getName().startsWith(prefix)).toArray(Field<?>[]::new);
//      Field<?>[] fields =  source.fields();
      if (fields != null) {
        List<String> memberNames = new ArrayList<String>(fields.length);
        for (Field<?> field : fields)
          memberNames.add(field.getName());
        return memberNames;
      }

      return null;
  }

  @Override
  public String toString() {
      return "jOOQ";
  }
}
