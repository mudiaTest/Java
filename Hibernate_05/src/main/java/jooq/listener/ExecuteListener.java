package jooq.listener;

import org.jooq.ExecuteContext;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DefaultExecuteListener;

public class ExecuteListener extends DefaultExecuteListener {

  /*
   * Przykład syntetyczny
   */
  @Override
  public void executeStart(ExecuteContext ctx) {
    // TODO Auto-generated method stub
    Query q = ctx.query();
    Result<?> r = ctx.result();
    /*
     * Odczytujemy customową zmienną 
     * Sami ją ustawiliśmy kodem: create.configuration().data("xxx", true);
     */
    Boolean b = Boolean.TRUE.equals(ctx.configuration().data("xxx"));
    super.executeStart(ctx);
  }

  /*
   * Przykład syntetyczny
   */
  @Override
  public void resultStart(ExecuteContext ctx) {
    // TODO Auto-generated method stub
    Query q = ctx.query();
    Result<?> r = ctx.result();
    super.resultStart(ctx);
  }
  
  /*
   * Przykład syntetyczny
   */
  @Override
  public void resultEnd(ExecuteContext ctx) {
    // TODO Auto-generated method stub
    Query q = ctx.query();
    Result<?> r = ctx.result();
    //Ostatni pobrany record
    Record rc = ctx.record();
    super.resultEnd(ctx);
  }
  
  /*
   * Przykład z życia
   */
  @Override
  public void renderEnd(ExecuteContext ctx) {
    /*
     * Sprawdzamy, czy DELETE ma WHERE. Jeśli nie, to rzucamy wyjątkiem
     */
    if (ctx.sql().matches("^(?i:(UPDATE|DELETE)(?!.* WHERE ).*)$")) {
            //throw new ...();
        }
  }

  @Override
  public void renderStart(ExecuteContext ctx) {
    // TODO Auto-generated method stub
    super.renderStart(ctx);
  }

  @Override
  public void prepareStart(ExecuteContext ctx) {
    // TODO Auto-generated method stub
    super.prepareStart(ctx);
  }

  @Override
  public void prepareEnd(ExecuteContext ctx) {
    // TODO Auto-generated method stub
    super.prepareEnd(ctx);
  }
  
  
  
}
