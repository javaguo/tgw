package com.tgw.basic.example.dubbo.service.impl;

import com.tgw.basic.example.dubbo.model.ExampleBeanDubbo;
import com.tgw.basic.example.dubbo.service.ExampleBeanConsumerService;
import com.tgw.basic.example.dubbo.service.ExampleBeanProviderService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zjg on 2020/5/5.
 */
@Service
public class ExampleBeanConsumerServiceImpl implements ExampleBeanConsumerService {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleBeanConsumerServiceImpl.class);

    @Reference(check = false)
    ExampleBeanProviderService providerService;

    public List<ExampleBeanDubbo> consumerAllBean() {
        LOG.info("consumerAllBean!");
        return  this.getProviderService().queryAll();
    }

    public ExampleBeanDubbo consumerBeanById(int i) {
        LOG.info("consumerBeanById,id: "+i);
        return  this.getProviderService().queryById(i);
    }

    public ExampleBeanProviderService getProviderService() {
        return providerService;
    }

    public void setProviderService(ExampleBeanProviderService providerService) {
        this.providerService = providerService;
    }
}
