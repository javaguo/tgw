package com.tgw.basic.demo.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoQuartzJob2 extends QuartzJobBean {
    private static final Logger LOG = LoggerFactory.getLogger(DemoQuartzJob2.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info(" DemoQuartzJob 2 executeInternal ,"+sdf.format(new Date()));
    }

}