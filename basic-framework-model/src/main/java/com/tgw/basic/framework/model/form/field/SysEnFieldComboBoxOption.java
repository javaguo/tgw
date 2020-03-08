package com.tgw.basic.framework.model.form.field;

/**
 * Created by zjg on 2017/5/14.
 */
public class SysEnFieldComboBoxOption extends SysEnFieldBase {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
