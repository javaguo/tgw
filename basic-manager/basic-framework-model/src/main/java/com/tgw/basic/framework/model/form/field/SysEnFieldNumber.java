package com.tgw.basic.framework.model.form.field;

/**
 * Created by zjg on 2017/5/21.
 */
public class SysEnFieldNumber extends SysEnFieldSpinner {
    private String allowDecimals;
    private String decimalPrecision;
    private String maxText;
    private String maxValue;
    private String minText;
    private String minValue;
    private String setp;

    public String getAllowDecimals() {
        return allowDecimals;
    }

    public void setAllowDecimals(String allowDecimals) {
        this.allowDecimals = allowDecimals;
    }

    public String getDecimalPrecision() {
        return decimalPrecision;
    }

    public void setDecimalPrecision(String decimalPrecision) {
        this.decimalPrecision = decimalPrecision;
    }

    public String getMaxText() {
        return maxText;
    }

    public void setMaxText(String maxText) {
        this.maxText = maxText;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinText() {
        return minText;
    }

    public void setMinText(String minText) {
        this.minText = minText;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getSetp() {
        return setp;
    }

    public void setSetp(String setp) {
        this.setp = setp;
    }
}
