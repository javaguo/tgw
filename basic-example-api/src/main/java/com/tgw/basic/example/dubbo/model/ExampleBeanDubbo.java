package com.tgw.basic.example.dubbo.model;


import com.tgw.basic.core.model.AbstractBaseBean;

import java.util.Date;

/**
 * Created by zhaojg on 2020/05/04.
 */
public class ExampleBeanDubbo extends AbstractBaseBean {

    private Integer id;
    private String name;
    private Date addTime;
    private Date updateTime;

    public ExampleBeanDubbo() {
    }

    public ExampleBeanDubbo(Integer id) {
        this.id = id;
    }

    public ExampleBeanDubbo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ExampleBeanDubbo(Integer id, String name, Date addTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.addTime = addTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
