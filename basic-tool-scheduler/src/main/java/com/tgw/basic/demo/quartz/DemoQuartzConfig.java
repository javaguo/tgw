package com.tgw.basic.demo.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoQuartzConfig {

    @Bean
    public JobDetail demoJobDetail() {
        JobDetail jobDetail = JobBuilder.newJob(DemoQuartzJob1.class)
                .withIdentity("demo_quartz_job1", "demo_quartz_job1")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger trigger() {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(demoJobDetail())
                .withIdentity("demo_quartz_trigger1", "demo_quartz_trigger1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 * * * ?"))
                .build();
        return trigger;
    }
    
}