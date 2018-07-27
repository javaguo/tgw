package com.tgw.basic.framework.model.form.field;


import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.core.model.AbstractBaseBean;

/**
 * 每个字段在页面上的属性
 * 此类中的所有字段都用String类型
 *
 * 表单元素继承结构参考extjs的继承机构
 * 表单元素类的属性也参考extjs
 *
 * SysEnFieldBase类参考Ext.form.field.Base
 * Created by zhaojg on 2017/4/23.
 */
public class SysEnFieldBase extends AbstractBaseBean {
    private String eleId;//表单元素id
    private String configs;

    private String anchor;
    private String disabled;
    private String height;
    private String inputType;
    private String labelWidth;//字段标签宽度
    private String maxHeight;
    private String maxWidth;
    private String minHeight;
    private String minWidth;
    private String readOnly;
    private String tabIndex;
    private String value;
    private String width;//字段宽度(包括字段标签与字段表单元素的宽度)

    /**
     * 判断参数fieldBase的值是否为xtype对应类或子类的对象
     * @param fieldBase SysEnFieldBase或其子类的对象
     * @param xtype
     * @return
     */
    public boolean judgeXtype( SysEnFieldBase fieldBase,String xtype){
        System.out.println( "fieldBase--->"+fieldBase.toString() + "   xtype--->"+xtype );
        boolean checkRes = false;

        Class objClass = null;
        if( PlatformSysConstant.FORM_XTYPE_TEXT.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldText.class;
        }else if( PlatformSysConstant.FORM_XTYPE_TEXTAREA.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldTextArea.class;
        }else if( PlatformSysConstant.FORM_XTYPE_SPINNER.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldSpinner.class;
        }else if( PlatformSysConstant.FORM_XTYPE_NUMBER.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldNumber.class;
        }else if( PlatformSysConstant.FORM_XTYPE_PICKER.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldPicker.class;
        }else if( PlatformSysConstant.FORM_XTYPE_COMBOBOX.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldComboBox.class;
        }else if( PlatformSysConstant.FORM_XTYPE_DATE.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldDate.class;
        }else if( PlatformSysConstant.FORM_XTYPE_FILE.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldFile.class;
        }else if( PlatformSysConstant.FORM_XTYPE_HIDDEN.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldHidden.class;
        }else if( PlatformSysConstant.FORM_XTYPE_DISPLAY.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldDisplay.class;
        }else if( PlatformSysConstant.FORM_XTYPE_CHECKBOX.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldCheckbox.class;
        }else if( PlatformSysConstant.FORM_XTYPE_RADIO.equals( xtype ) ){
            objClass = com.tgw.basic.framework.model.form.field.SysEnFieldRadio.class;
        }else{
            return checkRes;
        }
        String objClassInfo = objClass.toString();
        String fieldBaseClass = SysEnFieldBase.class.toString();

        Class fieldClass = fieldBase.getClass();

        boolean endFlag = false;
        while( !endFlag ){
            String fieldClassInfo = fieldClass.toString();

            if( fieldBaseClass.equals( fieldClassInfo ) ){//找到了根类
                endFlag = true;
                checkRes = false;
            }else if( objClassInfo.equals( fieldClassInfo ) ){
                endFlag = true;
                checkRes = true;
            }else{
                fieldClass = fieldClass.getSuperclass();
            }
        }

        return checkRes;
    }

    public String getEleId() {
        return eleId;
    }

    public void setEleId(String eleId) {
        this.eleId = eleId;
    }

    public String getConfigs() {
        return configs;
    }

    public void setConfigs(String configs) {
        this.configs = configs;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getLabelWidth() {
        return labelWidth;
    }

    public void setLabelWidth(String labelWidth) {
        this.labelWidth = labelWidth;
    }

    public String getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(String maxHeight) {
        this.maxHeight = maxHeight;
    }

    public String getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(String maxWidth) {
        this.maxWidth = maxWidth;
    }

    public String getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(String minHeight) {
        this.minHeight = minHeight;
    }

    public String getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(String minWidth) {
        this.minWidth = minWidth;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public String getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(String tabIndex) {
        this.tabIndex = tabIndex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
