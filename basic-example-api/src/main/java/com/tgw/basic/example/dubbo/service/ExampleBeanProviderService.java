package com.tgw.basic.example.dubbo.service;

import com.tgw.basic.example.dubbo.model.ExampleBeanDubbo;

import java.util.List;

/**
 * Created by zjg on 2020/5/4.
 */
public interface ExampleBeanProviderService {

    public List<ExampleBeanDubbo> queryAll();

    public ExampleBeanDubbo queryById(int i);
}
