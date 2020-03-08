package com.tgw.basic.framework.model.form.field;

/**
 * Created by zjg on 2017/5/7.
 */
public class SysEnFieldCheckbox extends SysEnFieldBase {
    private String boxLabel;
    private String boxLabelAlign;
    private String checked;
    private String inputValue;

    public String getBoxLabel() {
        return boxLabel;
    }

    public void setBoxLabel(String boxLabel) {
        this.boxLabel = boxLabel;
    }

    public String getBoxLabelAlign() {
        return boxLabelAlign;
    }

    public void setBoxLabelAlign(String boxLabelAlign) {
        this.boxLabelAlign = boxLabelAlign;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }
}
