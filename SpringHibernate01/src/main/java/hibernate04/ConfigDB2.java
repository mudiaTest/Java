package hibernate04;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

/*
 * basePackages wskazuje - na package gdzie b�d� dao. Wtedy odnosz�c si� do tego dao b�dziemy odpytywa� konkretn� baz�
 * 	w tym samym pakiecie b�d� entity
 * entityManagerFactoryRef wskazuje na to jakiego entityManagera maj� u�ywac DAO
 * transactionManagerRef - wskazuje manager transakcji, kt�rego maj� u�ywa� DAO
 */
@Configuration
@EnableJpaRepositories(
		basePackages = "com.example.demo.db2",
		entityManagerFactoryRef = "db2EntityManager",
		transactionManagerRef = "db2TransactionManager")
public class ConfigDB2 {
	@Autowired
    private Environment env;
	
	/*
	 * Konfiguracja jest pobierana z odpowiednich wpis�w z application.properties
	 */
	@Bean
	@ConfigurationProperties(prefix="second.ds")
	public DataSource db2DataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		return dataSource;
	}
	
	@Bean	
    public LocalContainerEntityManagerFactoryBean db2EntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		
		em.setDataSource(db2DataSource());
		em.setPackagesToScan("com.example.demo.db2");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
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
