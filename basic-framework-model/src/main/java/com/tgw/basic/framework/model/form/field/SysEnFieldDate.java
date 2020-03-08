package com.tgw.basic.framework.model.form.field;

/**
 * Created by zjg on 2017/5/18.
 */
public class SysEnFieldDate extends SysEnFieldPicker {
    private String altFormats;
    private String disabledDates;
    private String disabledDatesText;
    private String disabledDays;
    private String disabledDaysText;
    private String format;//extjs的时间格式
    private String formatJava;//java的时间格式，format要与formatJava格式相对应
    private String invalidText;
    private String maxText;
    private String maxValue;//最大时间
    private String minText;
    private String minValue;//最小时间
    private String showToday;
    private String startDay;
    private String submitFormat;
    private String startTimeForRange;//当字段设置为可按范围搜索时使用，设置开始时间
    private String endTimeForRange;//当字段设置为可按范围搜索时使用，设置结束时间

    public String getAltFormats() {
        return altFormats;
    }

    public void setAltFormats(String altFormats) {
        this.altFormats = altFormats;
    }

    public String getDisabledDates() {
        return disabledDates;
    }

    public void setDisabledDates(String disabledDates) {
        this.disabledDates = disabledDates;
    }

    public String getDisabledDatesText() {
        return disabledDatesText;
    }

    public void setDisabledDatesText(String disabledDatesText) {
        this.disabledDatesText = disabledDatesText;
    }

    public String getDisabledDays() {
        return disabledDays;
    }

    public void setDisabledDays(String disabledDays) {
        this.disabledDays = disabledDays;
    }

    public String getDisabledDaysText() {
        return disabledDaysText;
    }

    public void setDisabledDaysText(String disabledDaysText) {
        this.disabledDaysText = disabledDaysText;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormatJava() {
        return formatJava;
    }

    public void setFormatJava(String formatJava) {
        this.formatJava = formatJava;
    }

    public String getInvalidText() {
        return invalidText;
    }

    public void setInvalidText(String invalidText) {
        this.invalidText = invalidText;
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

    public String getShowToday() {
        return showToday;
    }

    public void setShowToday(String showToday) {
        this.showToday = showToday;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getSubmitFormat() {
        return submitFormat;
    }

    public void setSubmitFormat(String submitFormat) {
        this.submitFormat = submitFormat;
    }

    public String getStartTimeForRange() {
        return startTimeForRange;
    }

    public void setStartTimeForRange(String startTimeForRange) {
        this.startTimeForRange = startTimeForRange;
    }

    public String getEndTimeForRange() {
        return endTimeForRange;
    }

    public void setEndTimeForRange(String endTimeForRange) {
        this.endTimeForRange = endTimeForRange;
    }
}
