package com.tgw.basic.example.dubbo.service.impl;

import com.tgw.basic.example.dubbo.model.ExampleBeanDubbo;
import com.tgw.basic.example.dubbo.service.ExampleBeanProviderService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zjg on 2020/5/4.
 */
@Service
public class ExampleBeanProviderServiceImpl implements ExampleBeanProviderService {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleBeanProviderServiceImpl.class);

    public List<ExampleBeanDubbo> queryAll() {
        LOG.info("ProviderAllBean!");
        List result = new ArrayList();
        for(int i=0;i<5;i++){
            Date d = new Date();
            ExampleBeanDubbo bean= new ExampleBeanDubbo(i,"ExampleBeanDubbo"+i,d,d);
            result.add(bean);
        }
        return result;
    }

    public ExampleBeanDubbo queryById(int i) {
        LOG.info("ProviderById,id: "+i);
        Date d = new Date();
        return new ExampleBeanDubbo(i,"ExampleBeanDubbo"+i,d,d);
    }
}
