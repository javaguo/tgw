package com.tgw.basic.framework.model.form.field;

/**
 * Created by zjg on 2017/5/21.
 */
public class SysEnFieldDisplay extends SysEnFieldBase {
    /**
     * 是否为白板类型字段。白板类型的字段用于在页面上显示一些文字信息，也可以是html信息。
     * 白板类型的字段只在添加、编辑页面表单中使用。
     * 添加、编辑功能中提交表单时不提交白板类型的字段。
     * model中不需要定义白板类型的字段。但非白板类型的Display字段需要在model中字义对应的字段。
     */
    private Boolean isWhiteBoard;//

    public Boolean getIsWhiteBoard() {
        return isWhiteBoard;
    }

    public Boolean getWhiteBoard() {
        return isWhiteBoard;
    }

    public void setWhiteBoard(Boolean whiteBoard) {
        isWhiteBoard = whiteBoard;
    }
}
