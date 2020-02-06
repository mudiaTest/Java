package jooq;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.val;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.jooq.CommonTableExpression;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.FieldLike;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.WindowDefinition;
import org.jooq.conf.ParamType;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.SQLDataType;
import org.jooq.util.postgres.PostgresDataType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import jooq.config.PersistenceContextDb1;
import jooq.config.PersistenceContextDb2;
import jooq.db1.dao.Test1Db1Dao;
import jooq.db1.dao.Test2Db1Dao;
import jooq.db1.domain.Test1;
import jooq.db1.domain.Test2;
import jooq.utils.NewTransactionWrapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Test1Res {
	private Long id;
	public Double cst;
}

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class) 
@ContextConfiguration(classes = { PersistenceContextDb1.class, PersistenceContextDb2.class })
public class BuildTest {
	
	@Test
	@Transactional
	@Commit
	public void staticTest()
	{
		
	}	
}
