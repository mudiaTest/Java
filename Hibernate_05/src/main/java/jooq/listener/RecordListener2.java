package jooq.listener;

import org.jooq.RecordContext;
import org.jooq.impl.DefaultRecordListener;

import jooq.jooq.db1.tables.Test1;
import jooq.jooq.db1.tables.records.Test1Record;

public class RecordListener2 extends DefaultRecordListener {

  @Override
  public void storeStart(RecordContext ctx) {
    if (ctx.record() instanceof Test1Record)
    {
      Test1Record r = (Test1Record)ctx.record();
      int t = 0;
    }
    super.storeStart(ctx);
  }
  
  /*
   * Poza STORE są jeszcze możliwe implementacje dla:
   *     insert, load, refresh, delete, update
   */
}
