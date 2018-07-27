package com.tgw.basic.framework.model.form.field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjg on 2017/5/14.
 */
public class SysEnFieldComboBoxGroup extends SysEnFieldCheckboxGroup {

    private String comboBoxGroupName;//级联下拉框分组名称
    private boolean isCascade;//是否是级联下拉框
    private List<SysEnFieldComboBox> comboBoxList = new ArrayList<SysEnFieldComboBox>();

    public String getComboBoxGroupName() {
        return comboBoxGroupName;
    }

    public void setComboBoxGroupName(String comboBoxGroupName) {
        this.comboBoxGroupName = comboBoxGroupName;
    }

    public boolean getIsCascade(){
        return isCascade;
    }

    public boolean isCascade() {
        return isCascade;
    }

    public void setCascade(boolean cascade) {
        isCascade = cascade;
    }

    public List<SysEnFieldComboBox> getComboBoxList() {
        return comboBoxList;
    }

    public void setComboBoxList(List<SysEnFieldComboBox> comboBoxList) {
        this.comboBoxList = comboBoxList;
    }
}
