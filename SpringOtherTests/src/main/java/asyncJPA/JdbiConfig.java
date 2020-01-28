package asyncJPA;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.spi.JdbiPlugin;
import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
public class JdbiConfig
{
  @Bean
  public Jdbi jdbi(DataSource ds, List<JdbiPlugin> jdbiPlugins, List<RowMapper<?>> rowMappers)
  {
    TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(ds);
    Jdbi jdbi = Jdbi.create(proxy);
    jdbiPlugins.forEach(plugin -> jdbi.installPlugin(plugin));
    rowMappers.forEach(mapper -> jdbi.registerRowMapper(mapper));
    jdbi.setSqlLogger(sqlLogger());
    return jdbi;
  }

  private SqlLogger sqlLogger()
  {
    final Logger log = Logger.getLogger("pl.biu.rofk.jdbi");
    return new SqlLogger()
    {
      @Override
      public void logBeforeExecution(StatementContext context)
      {
        if (log.isDebugEnabled())
        {
          log.debug(context.getRawSql());
        }
      }
    };
  }
}