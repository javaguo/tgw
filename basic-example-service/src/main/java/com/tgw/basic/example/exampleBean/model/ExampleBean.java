package com.tgw.basic.example.exampleBean.model;


import com.tgw.basic.core.model.AbstractBaseBean;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by zhaojg on 2017/03/24.
 */
@Table(name="example_bean")
public class ExampleBean extends AbstractBaseBean {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    //org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.builder.BuilderException: Error resolving class. Cause: org.apache.ibatis.type.TypeException: Could not resolve type alias 'char'.  Cause: java.lang.ClassNotFoundException: Cannot find class: char
    //private char baseChar;
    private String formHidden;
    private String formText;
    private String formPassword;
    private String formTextArea;
    private String formHtmlEditor;
    private Short formNumberShort;
    @Transient
    private Short formNumberShortStart;
    @Transient
    private Short formNumberShortEnd;
    private short formNumberShortBase;
    private Integer formNumberInteger;
    private int formNumberIntBase;
    private Long formNumberLong;
    private long formNumberLongBase;
    private Float  formNumberFloat;
    private float formNumberFloatBase;
    private Double formNumberDouble;
    private double formNumberDoubleBase;
    private Boolean formBoolean;
    private Boolean isFormBooleanFlag;
    private boolean formBooleanBase;
    private String formDateString;
    @Transient
    private String formDateStringStart;
    @Transient
    private String formDateStringEnd;
    private Date formDateDate;
    private Date formDatetimeYmdhm;
    private String formDatetimeString;
    private Date formDatetimeDate;
    @Transient
    private Date formDatetimeDateStart;
    @Transient
    private Date formDatetimeDateEnd;
    private String formComboBoxJson;
    private String formComboBoxSql;
    private String formTagJson;
    private String formTagSql;
    private String formComboBoxCascadeA;
    private String formComboBoxCascadeB;
    private String formComboBoxCascade1;
    private String formComboBoxCascade2;
    private String formComboBoxCascade3;
    private String formComboBoxTree1;
    private String formComboBoxTree2;
    private String formComboBoxTree3;
    private String formComboBoxTree4;
    private String formComboBoxTree5;
    private String formComboBoxTree6;
    private String formRadio;
    private String formCheckbox;
    private String formDisplay;
    /**
     * 使用框架配置上传文件时，MultipartFile变量不是必须字段。
     * 只需要定义附件Url及OrigFileName即可。
     * 自己实现文件上传时，需要定义MultipartFile变量接收上传的文件。
     *
     * url：相对地址   格式：附件名+Url
     * OrigFileName：原始文件名   格式：附件名+OrigFileName
     * 变量的附件名自己起，Url及OrigFileName为固定写法
     *
     * 附件变量定义请参考formFile3Url及formFile3OrigFileName
     */
    @Transient
    private MultipartFile formFile1;
    private String formFile1Url;
    private String formFile1OrigFileName;
    @Transient
    private MultipartFile formFile2;
    private String formFile2Url;
    private String formFile2OrigFileName;
    private String formFile3Url;
    private String formFile3OrigFileName;

    private Date addTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormHidden() {
        return formHidden;
    }

    public void setFormHidden(String formHidden) {
        this.formHidden = formHidden;
    }

    public String getFormText() {
        return formText;
    }

    public void setFormText(String formText) {
        this.formText = formText;
    }

    public String getFormPassword() {
        return formPassword;
    }

    public void setFormPassword(String formPassword) {
        this.formPassword = formPassword;
    }

    public String getFormTextArea() {
        return formTextArea;
    }

    public void setFormTextArea(String formTextArea) {
        this.formTextArea = formTextArea;
    }

    public String getFormHtmlEditor() {
        return formHtmlEditor;
    }

    public void setFormHtmlEditor(String formHtmlEditor) {
        this.formHtmlEditor = formHtmlEditor;
    }

    public Short getFormNumberShort() {
        return formNumberShort;
    }

    public void setFormNumberShort(Short formNumberShort) {
        this.formNumberShort = formNumberShort;
    }

    public Short getFormNumberShortStart() {
        return formNumberShortStart;
    }

    public void setFormNumberShortStart(Short formNumberShortStart) {
        this.formNumberShortStart = formNumberShortStart;
    }

    public Short getFormNumberShortEnd() {
        return formNumberShortEnd;
    }

    public void setFormNumberShortEnd(Short formNumberShortEnd) {
        this.formNumberShortEnd = formNumberShortEnd;
    }

    public short getFormNumberShortBase() {
        return formNumberShortBase;
    }

    public void setFormNumberShortBase(short formNumberShortBase) {
        this.formNumberShortBase = formNumberShortBase;
    }

    public Integer getFormNumberInteger() {
        return formNumberInteger;
    }

    public void setFormNumberInteger(Integer formNumberInteger) {
        this.formNumberInteger = formNumberInteger;
    }

    public int getFormNumberIntBase() {
        return formNumberIntBase;
    }

    public void setFormNumberIntBase(int formNumberIntBase) {
        this.formNumberIntBase = formNumberIntBase;
    }

    public Long getFormNumberLong() {
        return formNumberLong;
    }

    public void setFormNumberLong(Long formNumberLong) {
        this.formNumberLong = formNumberLong;
    }

    public long getFormNumberLongBase() {
        return formNumberLongBase;
    }

    public void setFormNumberLongBase(long formNumberLongBase) {
        this.formNumberLongBase = formNumberLongBase;
    }

    public Float getFormNumberFloat() {
        return formNumberFloat;
    }

    public void setFormNumberFloat(Float formNumberFloat) {
        this.formNumberFloat = formNumberFloat;
    }

    public float getFormNumberFloatBase() {
        return formNumberFloatBase;
    }

    public void setFormNumberFloatBase(float formNumberFloatBase) {
        this.formNumberFloatBase = formNumberFloatBase;
    }

    public Double getFormNumberDouble() {
        return formNumberDouble;
    }

    public void setFormNumberDouble(Double formNumberDouble) {
        this.formNumberDouble = formNumberDouble;
    }

    public double getFormNumberDoubleBase() {
        return formNumberDoubleBase;
    }

    public void setFormNumberDoubleBase(double formNumberDoubleBase) {
        this.formNumberDoubleBase = formNumberDoubleBase;
    }

    public Boolean getFormBoolean() {
        return formBoolean;
    }

    public void setFormBoolean(Boolean formBoolean) {
        this.formBoolean = formBoolean;
    }

    public Boolean getFormBooleanFlag() {
        return isFormBooleanFlag;
    }

    public void setFormBooleanFlag(Boolean formBooleanFlag) {
        isFormBooleanFlag = formBooleanFlag;
    }

    public boolean isFormBooleanBase() {
        return formBooleanBase;
    }

    /*public boolean getFormBooleanBase(){ return formBooleanBase; }*//*public boolean getFormBooleanBase(){ return formBooleanBase; }*/

    public void setFormBooleanBase(boolean formBooleanBase) {
        this.formBooleanBase = formBooleanBase;
    }

    public String getFormDateString() {
        return formDateString;
    }

    public void setFormDateString(String formDateString) {
        this.formDateString = formDateString;
    }

    public String getFormDateStringStart() {
        return formDateStringStart;
    }

    public void setFormDateStringStart(String formDateStringStart) {
        this.formDateStringStart = formDateStringStart;
    }

    public String getFormDateStringEnd() {
        return formDateStringEnd;
    }

    public void setFormDateStringEnd(String formDateStringEnd) {
        this.formDateStringEnd = formDateStringEnd;
    }

    public Date getFormDateDate() {
        return formDateDate;
    }

    public void setFormDateDate(Date formDateDate) {
        this.formDateDate = formDateDate;
    }

    public Date getFormDatetimeYmdhm() {
        return formDatetimeYmdhm;
    }

    public void setFormDatetimeYmdhm(Date formDatetimeYmdhm) {
        this.formDatetimeYmdhm = formDatetimeYmdhm;
    }

    public String getFormDatetimeString() {
        return formDatetimeString;
    }

    public void setFormDatetimeString(String formDatetimeString) {
        this.formDatetimeString = formDatetimeString;
    }

    public Date getFormDatetimeDate() {
        return formDatetimeDate;
    }

    public void setFormDatetimeDate(Date formDatetimeDate) {
        this.formDatetimeDate = formDatetimeDate;
    }

    public Date getFormDatetimeDateStart() {
        return formDatetimeDateStart;
    }

    public void setFormDatetimeDateStart(Date formDatetimeDateStart) {
        this.formDatetimeDateStart = formDatetimeDateStart;
    }

    public Date getFormDatetimeDateEnd() {
        return formDatetimeDateEnd;
    }

    public void setFormDatetimeDateEnd(Date formDatetimeDateEnd) {
        this.formDatetimeDateEnd = formDatetimeDateEnd;
    }

    public String getFormComboBoxJson() {
        return formComboBoxJson;
    }

    public void setFormComboBoxJson(String formComboBoxJson) {
        this.formComboBoxJson = formComboBoxJson;
    }

    public String getFormComboBoxSql() {
        return formComboBoxSql;
    }

    public void setFormComboBoxSql(String formComboBoxSql) {
        this.formComboBoxSql = formComboBoxSql;
    }

    public String getFormTagJson() {
        return formTagJson;
    }

    public void setFormTagJson(String formTagJson) {
        this.formTagJson = formTagJson;
    }

    public String getFormTagSql() {
        return formTagSql;
    }

    public void setFormTagSql(String formTagSql) {
        this.formTagSql = formTagSql;
    }

    public String getFormComboBoxCascadeA() {
        return formComboBoxCascadeA;
    }

    public void setFormComboBoxCascadeA(String formComboBoxCascadeA) {
        this.formComboBoxCascadeA = formComboBoxCascadeA;
    }

    public String getFormComboBoxCascadeB() {
        return formComboBoxCascadeB;
    }

    public void setFormComboBoxCascadeB(String formComboBoxCascadeB) {
        this.formComboBoxCascadeB = formComboBoxCascadeB;
    }

    public String getFormComboBoxCascade1() {
        return formComboBoxCascade1;
    }

    public void setFormComboBoxCascade1(String formComboBoxCascade1) {
        this.formComboBoxCascade1 = formComboBoxCascade1;
    }

    public String getFormComboBoxCascade2() {
        return formComboBoxCascade2;
    }

    public void setFormComboBoxCascade2(String formComboBoxCascade2) {
        this.formComboBoxCascade2 = formComboBoxCascade2;
    }

    public String getFormComboBoxCascade3() {
        return formComboBoxCascade3;
    }

    public void setFormComboBoxCascade3(String formComboBoxCascade3) {
        this.formComboBoxCascade3 = formComboBoxCascade3;
    }

    public String getFormComboBoxTree1() {
        return formComboBoxTree1;
    }

    public void setFormComboBoxTree1(String formComboBoxTree1) {
        this.formComboBoxTree1 = formComboBoxTree1;
    }

    public String getFormComboBoxTree2() {
        return formComboBoxTree2;
    }

    public void setFormComboBoxTree2(String formComboBoxTree2) {
        this.formComboBoxTree2 = formComboBoxTree2;
    }

    public String getFormComboBoxTree3() {
        return formComboBoxTree3;
    }

    public void setFormComboBoxTree3(String formComboBoxTree3) {
        this.formComboBoxTree3 = formComboBoxTree3;
    }

    public String getFormComboBoxTree4() {
        return formComboBoxTree4;
    }

    public void setFormComboBoxTree4(String formComboBoxTree4) {
        this.formComboBoxTree4 = formComboBoxTree4;
    }

    public String getFormComboBoxTree5() {
        return formComboBoxTree5;
    }

    public void setFormComboBoxTree5(String formComboBoxTree5) {
        this.formComboBoxTree5 = formComboBoxTree5;
    }

    public String getFormComboBoxTree6() {
        return formComboBoxTree6;
    }

    public void setFormComboBoxTree6(String formComboBoxTree6) {
        this.formComboBoxTree6 = formComboBoxTree6;
    }

    public String getFormRadio() {
        return formRadio;
    }

    public void setFormRadio(String formRadio) {
        this.formRadio = formRadio;
    }

    public String getFormCheckbox() {
        return formCheckbox;
    }

    public void setFormCheckbox(String formCheckbox) {
        this.formCheckbox = formCheckbox;
    }


    public String getFormDisplay() {
        return formDisplay;
    }

    public void setFormDisplay(String formDisplay) {
        this.formDisplay = formDisplay;
    }

    public MultipartFile getFormFile1() {
        return formFile1;
    }

    public void setFormFile1(MultipartFile formFile1) {
        this.formFile1 = formFile1;
    }

    public MultipartFile getFormFile2() {
        return formFile2;
    }

    public void setFormFile2(MultipartFile formFile2) {
        this.formFile2 = formFile2;
    }

    public String getFormFile1Url() {
        return formFile1Url;
    }

    public void setFormFile1Url(String formFile1Url) {
        this.formFile1Url = formFile1Url;
    }

    public String getFormFile1OrigFileName() {
        return formFile1OrigFileName;
    }

    public void setFormFile1OrigFileName(String formFile1OrigFileName) {
        this.formFile1OrigFileName = formFile1OrigFileName;
    }

    public String getFormFile2Url() {
        return formFile2Url;
    }

    public void setFormFile2Url(String formFile2Url) {
        this.formFile2Url = formFile2Url;
    }

    public String getFormFile2OrigFileName() {
        return formFile2OrigFileName;
    }

    public void setFormFile2OrigFileName(String formFile2OrigFileName) {
        this.formFile2OrigFileName = formFile2OrigFileName;
    }

    public String getFormFile3Url() {
        return formFile3Url;
    }

    public void setFormFile3Url(String formFile3Url) {
        this.formFile3Url = formFile3Url;
    }

    public String getFormFile3OrigFileName() {
        return formFile3OrigFileName;
    }

    public void setFormFile3OrigFileName(String formFile3OrigFileName) {
        this.formFile3OrigFileName = formFile3OrigFileName;
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
