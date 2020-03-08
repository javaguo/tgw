package com.tgw.basic.framework.model.form.field;

/**
 * 列表中的操作字段。配置的字段只在列表中展示。
 * 例：列表中的查看详情、删除等操作。
 *
 * Created by zjg on 2017/8/21.
 */
public class SysEnFieldListExtend extends SysEnFieldBase {
    private String listFieldOperateTypeCode;
    private String functionName;
    private String param;//参数。param为列表中的字段名，多个字段用英文逗号隔开。
    private String url;//操作请求地址。
    private String tabTitle;//tab窗口标题
    private String tabFlag;//新tab窗口标记

    public String getListFieldOperateTypeCode() {
        return listFieldOperateTypeCode;
    }

    public void setListFieldOperateTypeCode(String listFieldOperateTypeCode) {
        this.listFieldOperateTypeCode = listFieldOperateTypeCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public String getTabFlag() {
        return tabFlag;
    }

    public void setTabFlag(String tabFlag) {
        this.tabFlag = tabFlag;
    }
}
