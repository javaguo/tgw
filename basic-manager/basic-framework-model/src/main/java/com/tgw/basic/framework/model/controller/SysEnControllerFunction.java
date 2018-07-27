package com.tgw.basic.framework.model.controller;


import com.tgw.basic.core.model.AbstractBaseBean;

import java.util.List;

/**
 * Created by zhaojg on 2016/10/20.
 */
public class SysEnControllerFunction extends AbstractBaseBean {
    private Integer id;
    private String  identify;//菜单英文标识
    private String  name;//菜单名称
    private String  url;//菜单请求地址
    private String  type;//菜单类型
    /**
     * PlatformSysConstant中定义了所有的相关常量。
     * a1：基本的添加方法
     * a2：基本的ajax请求方法，处理选中的记录
     * a2_extend：基本的ajax请求方法，不需要选中数据
     * a3: ajax请求方法，可以统一修改指定字段的值
     * a4: 自定义按钮的处理方法
     *
     * b1:介绍说明
     */
    private String  menuTypeCode;///菜单类型编码
    private boolean isSingle;//是否一次只能操作列表中的一条数据
    private boolean isRefreshList;//是否刷新列表
    private String  iconCls;//菜单图标样式，参考\resource\js\extjs\extjs5\resources\css\icon.css
    private int  orderNum;//排序标识

    private List<SysEnControllerField> updateFieldList;//要修改的字段  menuTypeCode为a3使用此字段
    private String ajaxUpdateFields;//要修改的字段  menuTypeCode为a3使用此字段
    private String ajaxUpdateWindowConfigs;//修改字段值窗口配置 menuTypeCode为a3使用此字段

    private String functionName;//自定义js函数名称，menuTypeCode为a4使用此字段

    private String instructions;//功能介绍说明   menuTypeCode为b1使用此字段

    /*public SysEnControllerFunction(String identify,String name, String url, String type,boolean isSingle,String iconCls, int orderNum) {
        this.identify = identify;
        this.name = name;
        this.url = url;
        this.type = type;
        this.isSingle = isSingle;
        this.iconCls = iconCls;
        this.orderNum = orderNum;
    }*/

    public SysEnControllerFunction(String identify, String name, String url, String menuTypeCode,boolean isSingle, String iconCls, int orderNum) {
        this.identify = identify;
        this.name = name;
        this.url = url;
        this.menuTypeCode = menuTypeCode;
        this.isSingle = isSingle;
        this.iconCls = iconCls;
        this.orderNum = orderNum;
    }

    public SysEnControllerFunction(String identify, String name,String functionName, String menuTypeCode,String iconCls, int orderNum) {
        this.identify = identify;
        this.name = name;
        this.functionName = functionName;
        this.menuTypeCode = menuTypeCode;
        this.iconCls = iconCls;
        this.orderNum = orderNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMenuTypeCode() {
        return menuTypeCode;
    }

    public void setMenuTypeCode(String menuTypeCode) {
        this.menuTypeCode = menuTypeCode;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public boolean getIsSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public boolean isRefreshList() {
        return isRefreshList;
    }

    public boolean getIsRefreshList() {
        return isRefreshList;
    }

    public void setRefreshList(boolean refreshList) {
        isRefreshList = refreshList;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public List<SysEnControllerField> getUpdateFieldList() {
        return updateFieldList;
    }

    public void setUpdateFieldList(List<SysEnControllerField> updateFieldList) {
        this.updateFieldList = updateFieldList;
    }

    public String getAjaxUpdateFields() {
        return ajaxUpdateFields;
    }

    public void setAjaxUpdateFields(String ajaxUpdateFields) {
        this.ajaxUpdateFields = ajaxUpdateFields;
    }

    public String getAjaxUpdateWindowConfigs() {
        return ajaxUpdateWindowConfigs;
    }

    public void setAjaxUpdateWindowConfigs(String ajaxUpdateWindowConfigs) {
        this.ajaxUpdateWindowConfigs = ajaxUpdateWindowConfigs;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
