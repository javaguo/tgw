package com.tgw.basic.common.utils.config;

/**
 * Created by zhaojg on 2017/2/12.
 * 平台
 */
public class PlatformSysConstant {
    public static String USER_SESSION_INFO = "userSessionInfo";

    public static int LAYOUT_NUM_HORI_SEARCH_CONDITION=4;

    public static int PAGE_SIZE=20;// 默认分页大小

    public static String JSONSTR = "jsonStr";

    public static String DATE_FORMAT_JAVA_YMD = "yyyy-MM-dd";
    public static String DATE_FORMAT_JAVA_YMDH = "yyyy-MM-dd HH";
    public static String DATE_FORMAT_JAVA_YMDHM = "yyyy-MM-dd HH:mm";
    public static String DATE_FORMAT_JAVA_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static String DATE_FORMAT_EXT_YMD = "Y-m-d";
    public static String DATE_FORMAT_EXT_YMDH = "Y-m-d H";
    public static String DATE_FORMAT_EXT_YMDHM = "Y-m-d H:i";
    public static String DATE_FORMAT_EXT_YMDHMS = "Y-m-d H:i:s";
    public static String DATE_FORMAT_EXT_HMS = "H:i:s";

    public static String FIELD_TYPE_GENERAL="general";//普通类型的字段，可进行增、删、查、改操作
    public static String FIELD_TYPE_OPERATE="operate";//操作类型的字段，此类型的字段只在列表中出现，例：列表中每一行数据后的删除、查看详情。

    public static String FORM_XTYPE_HIDDEN = "hiddenfield";
    public static String FORM_XTYPE_TEXT = "textfield";
    public static String FORM_XTYPE_TEXTAREA = "textareafield";
    public static String FORM_XTYPE_NUMBER = "numberfield";
    public static String FORM_XTYPE_DATE = "datefield";
    public static String FORM_XTYPE_TIME = "timefield";
    public static String FORM_XTYPE_DATE_TIME = "datetimefield";//extjs未提供datetimefield组件，此组件是自定义扩展的组件
    public static String FORM_XTYPE_HTML_EDITOR = "htmleditor";
    public static String FORM_XTYPE_RADIO = "radiofield";
    public static String FORM_XTYPE_RADIOGROUP = "radiogroup";
    public static String FORM_XTYPE_CHECKBOX = "checkboxfield";
    public static String FORM_XTYPE_CHECKBOXGROUP = "checkboxgroup";
    public static String FORM_XTYPE_COMBOBOX = "combobox";
    public static String FORM_XTYPE_COMBOBOXTREE = "comboboxtree";//extjs未提供comboboxtree组件，此组件是自定义扩展的组件
    public static String FORM_XTYPE_FILE = "filefield";
    public static String FORM_XTYPE_DISPLAY = "displayfield";
    public static String FORM_XTYPE_SPINNER = "spinnerfield";
    public static String FORM_XTYPE_PICKER = "pickerfield";
    public static String FORM_XTYPE_TAG = "tagfield";

    public static String FORM_INPUTTYPE_TEXT = "text";
    public static String FORM_INPUTTYPE_PASSWORD = "password";

    public static String MENU_TYPE_ADD = "a1";
    public static String MENU_TYPE_BASE_AJAX = "a2";
    public static String MENU_TYPE_BASE_AJAX_INDEPENDENT = "baseAjaxIndependent";
    public static String MENU_TYPE_AJAX_UPDATE_FIELDS = "a3";
    public static String MENU_TYPE_USER_DEFINE_OPERATE = "a4";
    public static String MENU_TYPE_INSTRUCTIONS = "b1";



    public static String LIST_FIELD_VIEW_DETAIL = "1";
    public static String LIST_FIELD_SINGLE_BASE_AJAX_REQ = "2";
    public static String LIST_FIELD_SINGLE_DELETE = "3";
    public static String LIST_FIELD_USER_DEFINE_OPERATE = "4";
    public static String LIST_FIELD_OPEN_NEW_TAB = "5";
    public static String LIST_FIELD_OPEN_NEW_TAB_LIST = "6";
    public static String LIST_FIELD_OPEN_NEW_BROWSER_WINDOW = "7";

    //系统固定角色code
    public static String SYS_ROLE_CODE_SUPER_ADMIN = "superAdmin";//超级管理员
}
