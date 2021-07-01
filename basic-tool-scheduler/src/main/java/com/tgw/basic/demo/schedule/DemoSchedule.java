package com.tgw.basic.demo.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(DemoSchedule.class);

    @Scheduled(cron="0 0 12 * * ?" )
    public void scheduledOne(){
        LOG.info("scheduledOne");
    }

//    @Scheduled(initialDelay = 3000,fixedRate = 50000)
    public void scheduledTwo(){
        LOG.info("scheduledTwo");
    }
}