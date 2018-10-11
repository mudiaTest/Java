package com.my.pl;

import java.util.HashMap;

import javax.sql.DataSource;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"com.my.pl"})
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "com.my.pl.db1",
		entityManagerFactoryRef = "db1EntityManager",
		transactionManagerRef = "db1TransactionManager")
//@PropertySource("classpath:intro_config.properties")

public class PersistenceContext {

	@Autowired
	private Environment environment;
	 
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(environment.getProperty("spring.datasource.url"));
		dataSource.setUsername(environment.getProperty("spring.datasource.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.password"));
		return dataSource;
	}
	
	@Bean
	@Primary
    public LocalContainerEntityManagerFactoryBean db1EntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		
		em.setDataSource(dataSource());
		em.setPackagesToScan("com.my.pl.db1");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();		 
        em.setJpaVendorAdapter(vendorAdapter);
		
		//Pobieranie ustawień Hibernate z z application.properties
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
		properties.put("hibernate.temp.use_jdbc_metadata_defaults",  false);
		properties.put("hibernate.show-sql", true);
		properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
		properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
		//Podłączenie mapy KONIECZNIE po jej wypełnieniu, bo inicjalizuje ono wartości w em.
		em.setJpaPropertyMap(properties);
		return em;
	}
	/*
	@Bean
	@Primary
	public PlatformTransactionManager db1TransactionManager() {
		JpaTransactionManager result = new JpaTransactionManager();
		result.setEntityManagerFactory(db1EntityManager().getObject());
		return result;
	}
	
	@Bean
	public TransactionAwareDataSourceProxy transactionAwareDataSource() {
	    return new TransactionAwareDataSourceProxy(dataSource());
	}
	 
	@Bean
	public DataSourceTransactionManager transactionManager() {
	    return new DataSourceTransactionManager(dataSource());
	}
	 
	@Bean
	public DataSourceConnectionProvider connectionProvider() {
	    return new DataSourceConnectionProvider(transactionAwareDataSource());
	}
	 
	@Bean
	public ExceptionTranslator exceptionTransformer() {
	    return new ExceptionTranslator();
	}
	     
	@Bean
	public DefaultDSLContext dsl() {
	    return new DefaultDSLContext(configuration());
	}
	
	@Bean
	public DefaultConfiguration configuration() {
	    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
	    jooqConfiguration.set(connectionProvider());
	    jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));
	 
	    String sqlDialectName = "POSTGRES";//environment.getProperty("...");//inne, np POSTGRES_9_3
	    SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
	    jooqConfiguration.set(dialect);
	 
	    return jooqConfiguration;
	}
	*/
}
