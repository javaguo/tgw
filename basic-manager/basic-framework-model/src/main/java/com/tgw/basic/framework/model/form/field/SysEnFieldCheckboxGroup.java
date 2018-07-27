package com.tgw.basic.framework.model.form.field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjg on 2017/5/7.
 */
public class SysEnFieldCheckboxGroup extends SysEnFieldBase {
    private List<SysEnFieldCheckbox> checkboxList = new ArrayList<SysEnFieldCheckbox>();

    public List<SysEnFieldCheckbox> getCheckboxList() {
        return checkboxList;
    }

    public void setCheckboxList(List<SysEnFieldCheckbox> checkboxList) {
        this.checkboxList = checkboxList;
    }
}
