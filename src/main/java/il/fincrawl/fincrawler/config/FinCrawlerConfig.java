package il.fincrawl.fincrawler.config;

import il.fincrawl.fincrawler.services.CrawlDataFromWebJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.*;

@Configuration
public class FinCrawlerConfig {


    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(CrawlDataFromWebJob.class);
        jobDetailFactory.setDescription("Invoke Sample Job service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public SimpleTriggerFactoryBean trigger(JobDetail job) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);
//        trigger.setRepeatInterval(10000); // 10 sec 10 * 1000
        trigger.setRepeatInterval(43200000);//12 hours 12 *60 *60 *1000
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        return trigger;
    }



    @Bean
    public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobDetails(job);
        schedulerFactory.setTriggers(trigger);
        return schedulerFactory;
    }


}
