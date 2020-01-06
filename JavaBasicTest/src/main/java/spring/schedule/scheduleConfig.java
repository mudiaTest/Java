package spring.schedule;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class scheduleConfig implements SchedulingConfigurer{

	@Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
      taskRegistrar.setScheduler(taskExecutor());
  }
	
	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(1);
	}
	
	// Rejestracja 2 kolejnych zarządzaczy zadaniami - patrz SecondPool
	// Wszytstkie scedulery domyślny, tpts1 i tpts2 zarządzają własnymi pulami 
	// wątków i zaiwszenie się jednej puli nie powoduje zablokowania akcji w innych pulach
	
	@Bean(name = "tpts1")
	public ThreadPoolTaskScheduler tpts1() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		 threadPoolTaskScheduler.setPoolSize(2);
     threadPoolTaskScheduler.initialize();
     threadPoolTaskScheduler.setThreadNamePrefix("FirstThreadPoolTaskScheduler");
     return threadPoolTaskScheduler;
	}
	@Bean(name = "tpts2")
	public ThreadPoolTaskScheduler tpts2() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(2);
		threadPoolTaskScheduler.initialize();
		threadPoolTaskScheduler.setThreadNamePrefix("SecondThreadPoolTaskScheduler");
		return threadPoolTaskScheduler;
	}
}
