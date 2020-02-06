package hibernate03;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class WebAppCfg {
	
	//@Autowired
	//DataSource ds;
	
	//@Value("${spring.jpa.hibernate.dialect}")
   // private String dialect;

    //@Value("${spring.jpa.hibernate.hbm2ddl.auto}")
    //private String hbmddl;
	
//	private Properties getHibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.show_sql", true);
//        //properties.put("hibernate.dialect", dialect);
//        //properties.put("hibernate.hbm2ddl.auto", hbmddl);
//        return properties;
//    }
//	
//	@Bean
//	public SessionFactory sessionFactory(DataSource dataSource) {
//		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
//        sessionBuilder.addProperties(getHibernateProperties());
//        //sessionBuilder.addAnnotatedClass(my.com.pl.class);
//        return sessionBuilder.buildSessionFactory();
//	}
	

//	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory());
	}
//	@Bean
	public SessionFactory sessionFactory() {
		return new LocalSessionFactoryBuilder(getDataSource())
		   .scanPackages("my.com.pl")
		   .buildSessionFactory();
	}
//	@Bean
	public DataSource getDataSource() {
	    /*BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/concretepage");
	    dataSource.setUsername("root");
	    dataSource.setPassword("");*/
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://Ursus/ll/hibernate03");
		dataSource.setUsername("system");
		dataSource.setPassword("system");
	 
	    return dataSource;
	}
//	@Bean
	public HibernateTransactionManager hibTransMan(){
		return new HibernateTransactionManager(sessionFactory());
	}

}
