package com.tgw.basic.framework.model.form.field;

/**
 * Created by zjg on 2017/5/21.
 */
public class SysEnFieldText extends SysEnFieldBase {
    private String editable;
    private String emptyText;//字段提示信息
    private String maxLength;
    private String maxLengthText;
    private String minLength;
    private String minLengthText;
    private String regex;
    private String regexText;
    private String vtype;//字段正则验证类型
    private String vtypeText;
    private String validatorFunName;//ext的validator函数要调用的函数名称。validatorFunName非ext控件的config属性。
    private String validatorFunField;//与validatorFunName对应，作为validatorFunName的参数。表示要验证表单中的哪几个字段

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getEmptyText() {
        return emptyText;
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getMaxLengthText() {
        return maxLengthText;
    }

    public void setMaxLengthText(String maxLengthText) {
        this.maxLengthText = maxLengthText;
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    public String getMinLengthText() {
        return minLengthText;
    }

    public void setMinLengthText(String minLengthText) {
        this.minLengthText = minLengthText;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegexText() {
        return regexText;
    }

    public void setRegexText(String regexText) {
        this.regexText = regexText;
    }

    public String getVtype() {
        return vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    public String getVtypeText() {
        return vtypeText;
    }

    public void setVtypeText(String vtypeText) {
        this.vtypeText = vtypeText;
    }

    public String getValidatorFunName() {
        return validatorFunName;
    }

    public void setValidatorFunName(String validatorFunName) {
        this.validatorFunName = validatorFunName;
    }

    public String getValidatorFunField() {
        return validatorFunField;
    }

    public void setValidatorFunField(String validatorFunField) {
        this.validatorFunField = validatorFunField;
    }
}
