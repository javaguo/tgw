package com.tgw.basic.framework.model.controller;


import com.tgw.basic.core.model.AbstractBaseBean;
import com.tgw.basic.framework.model.form.field.SysEnFieldBase;

/**
 * Created by zhaojg on 2016/10/20.
 */
public class SysEnControllerField extends AbstractBaseBean {
    private Integer id;
    private String fieldType;//字段类型。PlatformSysConstant.FIELD_TYPE_GENERAL或PlatformSysConstant.Field_TYPE_OPERATE两种
    private String name;//字段名称
    private String fieldLabel;//字段标签名称
    private boolean isPrimaryKey;//是否为主键字段
    private String type;//数据类型
    private String xtype;//表单控件类型
    private boolean isValid;//字段是否有效
    private boolean isAllowAdd;//是否可添加
    private boolean isAllowUpdate;//是否可更新
    private boolean isShowList;//是否在列表中显示
    private boolean isShowOnView=true;//是否在详情窗口中显示。大部分字段需要在详情页面中展示，所以默认设置为true
    private boolean isAllowSearch;//是否可被搜索
    private boolean isSearByRange;//是否按区间搜索，如果为true，则搜索条件按照大于等于、小于等于查询搜索
    private boolean isAllowBlank;//是否可为空
    /**
     * 是否加密，默认都不加密。
     * 对于需要加密的字段，编辑页面初始化时表单中不需要设置值，同时在编辑页面中也是非必填字段。
     * 只支持文本、文本域字段进行加密。
     */
    private boolean isEncryption=false;//
    private SysEnFieldBase sysEnFieldAttr;//字段具体属性。此属性对象中的每个具体属性大部分参考ext中控件属性。

    public SysEnControllerField(String name, String fieldLabel, String type, String xtype, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank) {
        this.name = name;
        this.fieldLabel = fieldLabel;
        this.type = type;
        this.xtype = xtype;
        this.isValid = isValid;
        this.isAllowAdd = isAllowAdd;
        this.isAllowUpdate = isAllowUpdate;
        this.isShowList = isShowList;
        this.isAllowSearch = isAllowSearch;
        this.isAllowBlank = isAllowBlank;
    }

    public SysEnControllerField(String name, String fieldLabel, String xtype, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank) {
        this.name = name;
        this.fieldLabel = fieldLabel;
        this.xtype = xtype;
        this.isValid = isValid;
        this.isAllowAdd = isAllowAdd;
        this.isAllowUpdate = isAllowUpdate;
        this.isShowList = isShowList;
        this.isAllowSearch = isAllowSearch;
        this.isAllowBlank = isAllowBlank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getXtype() {
        return xtype;
    }

    public void setXtype(String xtype) {
        this.xtype = xtype;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isAllowAdd() {
        return isAllowAdd;
    }

    public void setAllowAdd(boolean allowAdd) {
        isAllowAdd = allowAdd;
    }

    public boolean isAllowUpdate() {
        return isAllowUpdate;
    }

    public void setAllowUpdate(boolean allowUpdate) {
        isAllowUpdate = allowUpdate;
    }

    public boolean getIsShowList(){
        return isShowList;
    }

    public boolean isShowList() {
        return isShowList;
    }

    public void setShowList(boolean showList) {
        isShowList = showList;
    }

    public boolean getIsShowOnView() {
        return isShowOnView;
    }

    public boolean isShowOnView() {
        return isShowOnView;
    }

    public void setShowOnView(boolean showOnView) {
        isShowOnView = showOnView;
    }

    public boolean isAllowSearch() {
        return isAllowSearch;
    }

    public void setAllowSearch(boolean allowSearch) {
        isAllowSearch = allowSearch;
    }

    public boolean getIsSearByRange() {
        return isSearByRange;
    }

    public boolean isSearByRange() {
        return isSearByRange;
    }

    public void setSearByRange(boolean searByRange) {
        isSearByRange = searByRange;
    }

    public boolean getIsAllowBlank() {
        return isAllowBlank;
    }

    public boolean isAllowBlank() {
        return isAllowBlank;
    }

    public void setAllowBlank(boolean allowBlank) {
        isAllowBlank = allowBlank;
    }

    public boolean getIsEncryption() {
        return isEncryption;
    }

    public boolean isEncryption() {
        return isEncryption;
    }

    public void setEncryption(boolean encryption) {
        isEncryption = encryption;
    }

    public SysEnFieldBase getSysEnFieldAttr() {
        return sysEnFieldAttr;
    }

    public void setSysEnFieldAttr(SysEnFieldBase sysEnFieldAttr) {
        this.sysEnFieldAttr = sysEnFieldAttr;
    }
}
