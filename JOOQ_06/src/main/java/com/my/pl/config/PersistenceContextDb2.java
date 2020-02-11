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
 *   w tym samym pakiecie będą entity
 * entityManagerFactoryRef wskazuje na to jakiego entityManagera mają używac DAO
 * transactionManagerRef - wskazuje manager transakcji, którego mają używać DAO
 */
@EnableJpaRepositories(
  basePackages = "com.my.pl.db2",
  entityManagerFactoryRef = "entityManagerDb2",
  transactionManagerRef = "transactionManagerDb2"
)

public class PersistenceContextDb2 {

  @Autowired
  private Environment environment;
   
  @Bean(name="dataSourceDb2")
  public DataSource dataSourceDb2() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
    //dataSource.setUrl("jdbc:postgresql://Ursus/ll/hibernate05_db2");
    dataSource.setUrl(environment.getProperty("spring.datasource.ds2"));
    dataSource.setUsername(environment.getProperty("spring.datasource.username"));
    dataSource.setPassword(environment.getProperty("spring.datasource.password"));
    return dataSource;
  }
  
  @Bean(name="entityManagerDb2")
    public LocalContainerEntityManagerFactoryBean entityManagerDb2() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    
    em.setDataSource(dataSourceDb2());
    em.setPackagesToScan("com.my.pl.db2");
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
  
  
@Bean(name="transactionManagerDb2")
  public PlatformTransactionManager transactionManagerDb2() {
    JpaTransactionManager result = new JpaTransactionManager();
    result.setEntityManagerFactory(entityManagerDb2().getObject());
    return result;
  }
  
  // JOOQ
  
    @Bean(name="transactionAwareDataSourceDb2")
    public TransactionAwareDataSourceProxy transactionAwareDataSourceDb2() {
        return new TransactionAwareDataSourceProxy(dataSourceDb2());
    }
     
//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSourceDb1());
//    }
     
    @Bean
    public DataSourceConnectionProvider connectionProviderDb2() {
        return new DataSourceConnectionProvider(transactionAwareDataSourceDb2());
    }
     
    /*@Bean
    public ExceptionTranslator exceptionTransformerDb2() {
        return new ExceptionTranslator();
    }*/
    
    @Bean
    public DefaultConfiguration configurationDb2() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(connectionProviderDb2());
        /*
         * Poniższe ustawienie powodowało, że JOOQ gubił się w typach błędów i nie działało .onErrorIgnore()
         */
        //jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformerDb2()));
     
        String sqlDialectName = "POSTGRES"; //environment.getProperty("..."); //inne, np POSTGRES_9_3
        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfiguration.set(dialect);
     
        return jooqConfiguration;
    }
    
    @Bean(name="dslDb2")
    public DefaultDSLContext dslDb2() {
      return new DefaultDSLContext(configurationDb2());
    }
    
  }
