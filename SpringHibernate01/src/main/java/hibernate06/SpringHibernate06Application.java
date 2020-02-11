package hibernate06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "hibernate06" })
public class SpringHibernate06Application {

//  @Bean
//  public HibernateJpaSessionFactoryBean sessionFactory() {
//      return new HibernateJpaSessionFactoryBean();
//  }
  
  public static void main(String[] args) {
    SpringApplication.run(SpringHibernate06Application.class, args);
  }
}
