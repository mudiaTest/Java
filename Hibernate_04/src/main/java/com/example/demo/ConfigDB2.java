package com.example.demo;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

//basePackages wskazuje na package gdzie bêd¹ dao. Wtedy odnosz¹c siê do tego dao bêdziemy odpytywaæ konkretn¹ bazê
@Configuration
@EnableJpaRepositories(
		basePackages = "com.example.demo.db2",
		entityManagerFactoryRef = "db2EntityManager",
		transactionManagerRef = "db2TransactionManager")
public class ConfigDB2 {
	@Autowired
    private Environment env;
	
	@Bean
	
	public DataSource db2DataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://Ursus/ll/hibernate04_db2");
		dataSource.setUsername("system");
		dataSource.setPassword("system");
		return dataSource;
	}
	
	@Bean
	
    public LocalContainerEntityManagerFactoryBean db2EntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		
		em.setDataSource(db2DataSource());
		em.setPackagesToScan("com.example.demo.db2");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		//Pobieranie ustawieñ Hibernate z z application.properties
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
		properties.put("hibernate.temp.use_jdbc_metadata_defaults",  false);
		properties.put("hibernate.show-sql", true);
		//Ustawia strategiê generowania nazw zgodnie z tym co domyœlnie ustawia spring.
		//Bez tego intVal zostanie przerobiony na kolumnê intval, podczas gdy spring robi int_val
		//Mog¹ byæ problemy co pod³¹czeniu gotowej bazy danych - koniecznoœæ rêczego ustalania nazw tabel i kolumn
		properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
		properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
		em.setJpaPropertyMap(properties);
		return em;
	}
	
	@Bean
	
	public PlatformTransactionManager db2TransactionManager() {
		JpaTransactionManager result = new JpaTransactionManager();
		result.setEntityManagerFactory(db2EntityManager().getObject());
		return result;
	}
}
