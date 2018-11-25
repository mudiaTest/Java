/*
 * Ważne jest odpowiednie nazewnictwo, bo używając @Qualifier("...") bedziemy 
 * wskazywali na konkretny entityManager, transakcje etc 
 */

package com.my.pl.config;

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
/*
 * basePackages wskazuje - na package gdzie będą dao. Wtedy odnosząc się do tego dao będziemy odpytywać konkretną bazę
 * 	w tym samym pakiecie będą entity
 * entityManagerFactoryRef wskazuje na to jakiego entityManagera mają używac DAO
 * transactionManagerRef - wskazuje manager transakcji, którego mają używać DAO
 */
@EnableJpaRepositories(
	basePackages = "com.my.pl.db1",
	entityManagerFactoryRef = "entityManagerDb1",
	transactionManagerRef = "transactionManagerDb1"
)

public class PersistenceContextDb1 {

	@Autowired
	private Environment environment;
	 
	//Spring
	//Customowe podłaczeni do DataSource - pozwala na podłaczenie do wielu DS
	
	@Bean(name="dataSourceDb1")
	public DataSource dataSourceDb1() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		//dataSource.setUrl("jdbc:postgresql://Ursus/ll/hibernate05_db1");
		dataSource.setUrl(environment.getProperty("spring.datasource.ds1"));		
		dataSource.setUsername(environment.getProperty("spring.datasource.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.password"));
		return dataSource;
	}
	
	@Bean(name="entityManagerDb1")
	@Primary
    public LocalContainerEntityManagerFactoryBean entityManagerDb1() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		
		em.setDataSource(dataSourceDb1());
		em.setPackagesToScan("com.my.pl.db1");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();		 
        em.setJpaVendorAdapter(vendorAdapter);
		
		//Pobieranie ustawień Hibernate z z application.properties
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
		properties.put("hibernate.temp.use_jdbc_metadata_defaults",  false);
		properties.put("hibernate.show_sql", true);
		properties.put("hibernate.format_sql", true);
		properties.put("hibernate.type", "TRACE");
		properties.put("hibernate.use_sql_comments", true);
		//Ustawia strategię generowania nazw zgodnie z tym co domyślnie ustawia spring.
		//Bez tego intVal zostanie przerobiony na kolumnę intval, podczas gdy spring robi int_val
		//Mogą być problemy co podłączeniu gotowej bazy danych - konieczność ręczego ustalania nazw tabel i kolumn
		properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
		properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
		//Podłączenie mapy KONIECZNIE po jej wypełnieniu, bo inicjalizuje ono wartości w em.
		em.setJpaPropertyMap(properties);
		return em;
	}
	
	@Bean(name="transactionManagerDb1")
	@Primary
	public PlatformTransactionManager transactionManagerDb1() {
		JpaTransactionManager result = new JpaTransactionManager();
		result.setEntityManagerFactory(entityManagerDb1().getObject());
		return result;
	}
	
	// JOOQ
	
	@Bean(name="transactionAwareDataSourceDb1")
	@Primary
	public TransactionAwareDataSourceProxy transactionAwareDataSourceDb1() {
	    return new TransactionAwareDataSourceProxy(dataSourceDb1());
	}
	 
/*
 * Nie budujemy transactionManagera (jak to pokazano w tutorialu), bo jest on 
 * już wybudowany powyżej dla JPA i będzie współuzytkowany.	
 */
//	@Bean
//	public DataSourceTransactionManager transactionManager() {
//	    return new DataSourceTransactionManager(dataSourceDb1());
//	}
	 
	@Bean
	@Primary
	public DataSourceConnectionProvider connectionProviderDb1() {
	    return new DataSourceConnectionProvider(transactionAwareDataSourceDb1());
	}
	 
	/*@Bean
	@Primary
	public ExceptionTranslator exceptionTransformerDb1() {
	    return new ExceptionTranslator();
	}*/
	
	@Bean
	@Primary
	public DefaultConfiguration configurationDb1() {
	    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
	    jooqConfiguration.set(connectionProviderDb1());
	    /*
	     * Poniższe ustawienie powodowało, że JOOQ gubił się w typach błędów i nie działało .onErrorIgnore()
	     */
	    //jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformerDb1()));
	 
	    String sqlDialectName = "POSTGRES_9_3"; //environment.getProperty("..."); //inne, np POSTGRES_9_3
	    //SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
	    SQLDialect dialect = SQLDialect.POSTGRES_9_3;
	    jooqConfiguration.set(dialect);
	 
	    return jooqConfiguration;
	}
	
	@Bean(name="dslDb1")
	@Primary
	public DefaultDSLContext dslDb1() {
		DefaultDSLContext result = new DefaultDSLContext(configurationDb1());
		/*
		 * Właczy opcję "ładnego" generowania SQL - nowe linia i wcięcia
		 */
		result.settings().withRenderFormatted(true);
		return result;
	}
	
}
