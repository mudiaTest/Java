package hibernate04;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/*
 * basePackages wskazuje - na package gdzie b�d� dao. Wtedy odnosz�c si� do tego dao b�dziemy odpytywa� konkretn� baz�
 * 	w tym samym pakiecie b�d� entity
 * entityManagerFactoryRef wskazuje na to jakiego entityManagera maj� u�ywac DAO
 * transactionManagerRef - wskazuje manager transakcji, kt�rego maj� u�ywa� DAO
 */
@Configuration
@EnableJpaRepositories(
		basePackages = "com.example.demo.db1",
		entityManagerFactoryRef = "db1EntityManager",
		transactionManagerRef = "db1TransactionManager")
public class ConfigDB1 {
	@Autowired
    private Environment env;
	
	/*
	 * Konfiguracja jest hardcoded
	 */
	@Bean
	@Primary
	public DataSource db1DataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://Ursus/ll/hibernate04_db1");
		dataSource.setUsername("system");
		dataSource.setPassword("system");
		return dataSource;
	}
	
	@Bean
	@Primary
    public LocalContainerEntityManagerFactoryBean db1EntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		
		em.setDataSource(db1DataSource());
		em.setPackagesToScan("com.example.demo.db1");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();		 
        em.setJpaVendorAdapter(vendorAdapter);
		
		//Pobieranie ustawie� Hibernate z z application.properties lub hardcoded
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
		properties.put("hibernate.temp.use_jdbc_metadata_defaults",  false);
		properties.put("hibernate.show_sql", true);
		properties.put("hibernate.format_sql", true);
		properties.put("hibernate.type", "TRACE");
		properties.put("hibernate.use_sql_comments", true);
		//Ustawia strategi� generowania nazw zgodnie z tym co domy�lnie ustawia spring.
		//Bez tego intVal zostanie przerobiony na kolumn� intval, podczas gdy spring robi int_val
		//Mog� by� problemy co pod��czeniu gotowej bazy danych - konieczno�� r�czego ustalania nazw tabel i kolumn
		properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
		properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
		//Pod��czenie mapy KONIECZNIE po jej wype�nieniu, bo inicjalizuje ono warto�ci w em.
		em.setJpaPropertyMap(properties);
		return em;
	}
	
	@Bean
	@Primary
	public PlatformTransactionManager db1TransactionManager() {
		JpaTransactionManager result = new JpaTransactionManager();
		result.setEntityManagerFactory(db1EntityManager().getObject());
		return result;
	}
}
