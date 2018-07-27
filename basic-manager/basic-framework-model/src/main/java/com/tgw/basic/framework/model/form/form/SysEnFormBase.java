package com.tgw.basic.framework.model.form.form;


import com.tgw.basic.core.model.AbstractBaseBean;

/**
 * 页面表单属性
 * 此类中的所有字段都用String类型
 *
 * Created by zhaojg on 2017/5/21.
 */
public class SysEnFormBase extends AbstractBaseBean {
    private String title;//表单标题
    private String height;//表单窗口高度
    private String labelWidth;//字段标签宽度，整个表单中所有字段的标签宽度
    private String maxHeight;
    private String maxWidth;
    private String minHeight;
    private String minWidth;
    private String width;//表单窗口宽度

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
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

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
