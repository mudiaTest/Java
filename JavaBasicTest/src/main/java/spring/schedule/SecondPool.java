package spring.schedule;

import java.util.concurrent.Executor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class SecondPool implements ApplicationContextAware{

	ApplicationContext ac;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ac = applicationContext;		
	}
	
	//Wywołanie tej metody spowoduje, że scheduler1 i scheduler2 otzrymają swoje zadania 
	public void doIt() {
		ThreadPoolTaskScheduler tpts1 = ac.getBean("tpts1", ThreadPoolTaskScheduler.class); 
		ThreadPoolTaskScheduler tpts2 = ac.getBean("tpts2", ThreadPoolTaskScheduler.class); 
		tpts1.scheduleWithFixedDelay(ScheduleTest2.r, 500);
		tpts2.scheduleWithFixedDelay(ScheduleTest3.r, 500);
		int i=0;
	}
}
