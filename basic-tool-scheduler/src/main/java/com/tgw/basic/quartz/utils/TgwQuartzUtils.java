package com.tgw.basic.quartz.utils;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.spring.PlatformSpringContextUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by zjg on 2020/12/21.
 */
public class TgwQuartzUtils {
    private static final Logger LOG = LoggerFactory.getLogger(TgwQuartzUtils.class);

    public static void createSimpleQuartzJob(Class jobClass,String groupName, String jobName,
                                                SimpleScheduleBuilder simpleScheduleBuilder, Date beginTime, Date endTime) {
        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(jobName, groupName)
                .forJob(jobName, groupName)
                .withSchedule(simpleScheduleBuilder);
        if(endTime != null){
            triggerBuilder.endAt(endTime);
        }
        if(beginTime!=null) {
            Date now = new Date();
            triggerBuilder.startAt(now.after(beginTime)?now:beginTime);
        }

        Trigger trigger = triggerBuilder.build();

        createQuartzJob(jobClass,jobName,groupName, trigger);
    }

    public static void createCronQuartzJob(Class jobClass,String groupName, String jobName,
                                           String cronExpression, Date beginTime, Date endTime) {
        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(jobName, groupName)
                .forJob(jobName, groupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));
        if(endTime != null){
            triggerBuilder.endAt(endTime);
        }
        if(beginTime!=null) {
            Date now = new Date();
            triggerBuilder.startAt(now.after(beginTime)?now:beginTime);
        }

        Trigger trigger = triggerBuilder.build();

        createQuartzJob(jobClass,jobName,groupName, trigger);
    }

    private static void createQuartzJob(Class jobClass,String jobName, String groupName, Trigger trigger){
        JobDetail job = JobBuilder.newJob(jobClass).withIdentity(jobName, groupName).build();

        try {
            getScheduler().scheduleJob(job, trigger);
        }catch (Exception e) {
            LOG.error("", e);
            throw new PlatformException("创建定时任务异常！");
        }
    }

    public static Scheduler getScheduler(){
        return (Scheduler)PlatformSpringContextUtils.getBeanByClass(Scheduler.class);
    }

}
