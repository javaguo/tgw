package com.tgw.basic.framework.model.controller;


import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.collections.PlatformCollectionsUtils;
import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.common.utils.string.PlatformStringUtils;
import com.tgw.basic.core.model.AbstractBaseBean;
import com.tgw.basic.framework.model.form.field.SysEnFieldBase;
import com.tgw.basic.framework.model.form.field.SysEnFieldCheckbox;
import com.tgw.basic.framework.model.form.field.SysEnFieldCheckboxGroup;
import com.tgw.basic.framework.model.form.field.SysEnFieldComboBox;
import com.tgw.basic.framework.model.form.field.SysEnFieldComboBoxGroup;
import com.tgw.basic.framework.model.form.field.SysEnFieldComboBoxOption;
import com.tgw.basic.framework.model.form.field.SysEnFieldComboBoxTree;
import com.tgw.basic.framework.model.form.field.SysEnFieldDate;
import com.tgw.basic.framework.model.form.field.SysEnFieldDisplay;
import com.tgw.basic.framework.model.form.field.SysEnFieldFile;
import com.tgw.basic.framework.model.form.field.SysEnFieldHidden;
import com.tgw.basic.framework.model.form.field.SysEnFieldHtmlEditor;
import com.tgw.basic.framework.model.form.field.SysEnFieldListExtend;
import com.tgw.basic.framework.model.form.field.SysEnFieldNumber;
import com.tgw.basic.framework.model.form.field.SysEnFieldRadio;
import com.tgw.basic.framework.model.form.field.SysEnFieldRadioGroup;
import com.tgw.basic.framework.model.form.field.SysEnFieldTag;
import com.tgw.basic.framework.model.form.field.SysEnFieldText;
import com.tgw.basic.framework.model.form.field.SysEnFieldTextArea;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhaojg on 2016/10/20.
 */
public class SysEnController extends AbstractBaseBean {
    private Integer id;
    private String name;
    private String identifier;
    private String loadDataUrl;//加载列表页面数据的方法
    private String controllerBaseUrl;//控制器的请求地址
    private int searchConditionColNum;//搜索区域布局列数
    private String addWindowConfigs;//添加窗口(Ext.Window)的配置
    private String editWindowConfigs;//编辑窗口(Ext.Window)的配置
    private String viewWindowConfigs;//详情窗口(Ext.Window)的配置
    private Integer pageSize;//列表页面大小
    /**
     * 自定义函数js文件名，此js文件中写自己的表单验证函数。
     * formValJsFileName已弃用。
     */
    private String formValJsFileName;
    private Set<String> jsFileNameSet = new HashSet<String>();
    private Set<String> jsFileNameUserDefinePathSet = new HashSet<String>();

    private List<SysEnControllerField> sysEnControllerFieldList = new ArrayList<SysEnControllerField>();
    private List<SysEnControllerFunction> sysEnControllerFunctionList = new ArrayList<SysEnControllerFunction>();

    public void addWindowConfig(String addWindowConfigs,String editWindowConfigs,String viewWindowConfigs){
        if( StringUtils.isNotBlank( addWindowConfigs ) ){
            this.setAddWindowConfigs( addWindowConfigs );
        }

        if( StringUtils.isNotBlank( editWindowConfigs ) ){
            this.setEditWindowConfigs( editWindowConfigs );
        }

        if( StringUtils.isNotBlank( viewWindowConfigs ) ){
            this.setViewWindowConfigs( viewWindowConfigs );
        }
    }

    /**
     * 处理ext的特殊配置。
     * 将一些自定义的配置转为ext的配置。
     * @param formField
     * @param extConfigsTransform
     */
    private void dealExtConfigsTransform(SysEnFieldText formField, String extConfigsTransform){
        if( StringUtils.isNotBlank( extConfigsTransform ) ){
            JSONObject jo = JSONObject.fromObject( "{"+extConfigsTransform+"}" );
            if( jo.containsKey("validatorFunName") ){//方法名称
                String validator = (String)jo.get("validatorFunName");
                formField.setValidatorFunName( validator );
            }

            if( jo.containsKey("validatorFunField") ){//方法参数
                String validatorField = (String)jo.get("validatorFunField");
                String[] filedArra = validatorField.split(",");

                StringBuffer fieldRes = new StringBuffer();
                for( int i=0;i<filedArra.length;i++ ){
                    fieldRes.append( ",field_"+filedArra[i]+"_"+this.getIdentifier() );
                }
                formField.setValidatorFunField( fieldRes.toString() );
            }

        }
    }

    /**
     * 添加列表中用到的js文件名。
     * 已无可用意义，废弃掉
     *
     * 自定义函数js文件名。
     * js文件必须放在resource/platform/js/platform/目录下。
     * 文件名示例：exampleBeanFormVal.js或path1/path2/exampleBeanFormVal.js
     *
     * @param jsFileName  js文件名，可包含路径。resource/platform/js/platform/的相对路径
     */
    @Deprecated
    public void addJsFileName( String jsFileName ) throws PlatformException {
        if( StringUtils.isBlank( jsFileName ) ){
            throw new PlatformException("配置的js文件名为空 ！");
        }
        if( !jsFileName.endsWith( ".js" ) ){
            throw new PlatformException("配置的js文件名错误！");
        }

        this.getJsFileNameSet().add( jsFileName.trim() );
    }

    public void addJsFileNameUserDefinePath( String jsFileName ) throws PlatformException {
        if( StringUtils.isBlank( jsFileName ) ){
            throw new PlatformException("配置的js文件名为空 ！");
        }
        if( !jsFileName.endsWith( ".js" ) ){
            throw new PlatformException("配置的js文件名错误！");
        }

        this.getJsFileNameUserDefinePathSet().add( jsFileName.trim() );
    }

    /**
     * 添加配置ID字段
     * @param name
     * @param fieldLabel
     * @param configs
     */
    public SysEnControllerField addFieldId( String name, String fieldLabel,String configs ){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel, PlatformSysConstant.FORM_XTYPE_HIDDEN,true,false,false,false,false,false);
        sysEnControllerField.setPrimaryKey(true);

        SysEnFieldBase formField = new SysEnFieldBase();
        if( StringUtils.isNotBlank( configs ) ){
            formField.setConfigs( configs );
        }

		/*if( StringUtils.isNotBlank( configs ) ){
			JSONObject jo = JSONObject.fromObject( "{"+configs+"}" );
			this.dealFormFieldAttr( formField,jo );
		}*/

        sysEnControllerField.setSysEnFieldAttr( formField );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }

    /**
     * 添加一个隐藏域
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param configs
     */
    public SysEnControllerField addFieldHidden( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,String configs){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_HIDDEN,isValid,isAllowAdd,isAllowUpdate,false,false,true);
        sysEnControllerField.setShowOnView(false);

        SysEnFieldHidden formField = new SysEnFieldHidden();
        if( StringUtils.isNotBlank( configs ) ){
            formField.setConfigs( configs );
        }
		/*if( StringUtils.isNotBlank(configs) ){
			JSONObject jo = JSONObject.fromObject( "{"+ configs +"}" );
			this.dealFormFieldAttr( formField,jo );
		}*/

        sysEnControllerField.setSysEnFieldAttr( formField );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }

    /**
     *  添加一个text字段。
     *
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param extConfigs
     */
    public SysEnControllerField addFieldText( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String extConfigs){
        String tempConfigs = "inputType:'"+PlatformSysConstant.FORM_INPUTTYPE_TEXT+"'";
        if( StringUtils.isNotBlank(extConfigs) ){
            tempConfigs += ","+ extConfigs;
        }
        return this.addFieldText(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,tempConfigs,null);
    }

    public SysEnControllerField addFieldText( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String extConfigs,String extConfigsTransform){
        String tempConfigs = "inputType:'"+PlatformSysConstant.FORM_INPUTTYPE_TEXT+"'";
        if( StringUtils.isNotBlank(extConfigs) ){
            tempConfigs += ","+ extConfigs;
        }
        return this.addFieldText(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,tempConfigs,extConfigsTransform);
    }

    /**
     * 添加一个Password字段
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowBlank
     * @param configs
     */
    public SysEnControllerField addFieldPassword( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowBlank,String configs ){
        String tempConfigs = "inputType:'"+PlatformSysConstant.FORM_INPUTTYPE_PASSWORD+"'";
        if( StringUtils.isNotBlank( configs ) ){
            tempConfigs += ","+configs;
        }
        SysEnControllerField sysEnControllerField =this.addFieldText(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,false,false,isAllowBlank,tempConfigs,null);
        sysEnControllerField.setShowOnView(false);
        sysEnControllerField.setEncryption( true );//需要加密
        return sysEnControllerField;
    }

    public SysEnControllerField addFieldPassword( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowBlank,String configs,String extConfigsTransform ){
        String tempConfigs = "inputType:'"+PlatformSysConstant.FORM_INPUTTYPE_PASSWORD+"'";
        if( StringUtils.isNotBlank( configs ) ){
            tempConfigs += ","+configs;
        }
        SysEnControllerField sysEnControllerField =this.addFieldText(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,false,false,isAllowBlank,tempConfigs,extConfigsTransform);
        sysEnControllerField.setShowOnView(false);
        sysEnControllerField.setEncryption( true );//需要加密
        return sysEnControllerField;
    }

    /**
     * 添加一个Text字段
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param extConfigs  ext控件原生配置，json串
     * @param extConfigsTransform  需要经过转换后传再配置ext属性。
     */
    public SysEnControllerField addFieldText( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String extConfigs,String extConfigsTransform){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_TEXT,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldText formField = new SysEnFieldText();
        if( StringUtils.isNotBlank(extConfigs) ){
            extConfigs = "allowBlank:"+isAllowBlank+","+ extConfigs;
        }else{
            extConfigs = "allowBlank:"+isAllowBlank;
        }

        if( StringUtils.isNotBlank(extConfigs) ){
            formField.setConfigs(extConfigs);
        }
		/*if( StringUtils.isNotBlank( configs ) ){
			JSONObject jo = JSONObject.fromObject( "{"+configs+"}" );
			this.dealFormFieldAttr( formField,jo );
		}*/

        dealExtConfigsTransform(formField,extConfigsTransform);

        sysEnControllerField.setSysEnFieldAttr( formField );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }

    /**
     * 添加一个TextArea字段，isShowList：true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     */
    public SysEnControllerField addFieldTextArea( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs ){
        return this.addFieldTextArea(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,null);
    }

    public SysEnControllerField addFieldTextArea( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs,String extConfigsTransform ){
        return this.addFieldTextArea(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,extConfigsTransform);
    }

    /**
     * 添加一个TextArea字段
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     */
    public SysEnControllerField addFieldTextArea( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String configs,String extConfigsTransform ){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_TEXTAREA,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldTextArea formField = new SysEnFieldTextArea();
        if( StringUtils.isNotBlank( configs ) ){
            configs = "allowBlank:"+isAllowBlank+","+configs;
        }else{
            configs = "allowBlank:"+isAllowBlank;
        }

        if( StringUtils.isNotBlank( configs ) ){
            formField.setConfigs( configs );
        }
		/*if( StringUtils.isNotBlank( configs ) ){
			JSONObject jo = JSONObject.fromObject( "{"+configs+"}" );
			this.dealFormFieldAttr( formField,jo );
		}*/
        dealExtConfigsTransform(formField,extConfigsTransform);

        sysEnControllerField.setSysEnFieldAttr( formField );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }

    /**
     * 添加一个Number字段    isShowList：true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     */
    public SysEnControllerField addFieldNumber(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs){
        return this.addFieldNumber(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,null);
    }

    public SysEnControllerField addFieldNumber(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs,String extConfigsTransform){
        return this.addFieldNumber(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,extConfigsTransform);
    }

    /**
     * 添加一个Number字段
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     */
    public SysEnControllerField addFieldNumber(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String configs,String extConfigsTransform){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_NUMBER,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldNumber formField = new SysEnFieldNumber();
        if( StringUtils.isNotBlank( configs ) ){
            configs = "width:250,allowBlank:"+isAllowBlank+","+configs;
        }else{
            configs = "width:250,allowBlank:"+isAllowBlank;
        }

        if( StringUtils.isNotBlank( configs ) ){
            formField.setConfigs( configs );
        }
		/*if( StringUtils.isNotBlank( configs ) ){
			JSONObject jo = JSONObject.fromObject( "{"+configs+"}" );
			this.dealFormFieldAttr( formField,jo );
		}*/
        dealExtConfigsTransform(formField,extConfigsTransform);

        sysEnControllerField.setSysEnFieldAttr( formField );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }


    /**
     * 添加一个Date字段   isShowList：true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     */
    public SysEnControllerField addFieldDate(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs){
        return this.addFieldDate(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,null);
    }

    public SysEnControllerField addFieldDate(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs,String extConfigsTransform){
        return this.addFieldDate(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,extConfigsTransform);
    }

    /**
     * 添加一个Date字段
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     */
    public SysEnControllerField addFieldDate(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String configs,String extConfigsTransform){
        if( StringUtils.isNotBlank( configs ) ){
            configs = "format:'"+PlatformSysConstant.DATE_FORMAT_EXT_YMD+"',formatJava:'"+PlatformSysConstant.DATE_FORMAT_JAVA_YMD+"',"+configs;
        }else{
            configs = "format:'"+PlatformSysConstant.DATE_FORMAT_EXT_YMD+"',formatJava:'"+PlatformSysConstant.DATE_FORMAT_JAVA_YMD+"'";
        }

        return this.addFieldDatetime(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank,configs,extConfigsTransform);
        /*SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_DATE_TIME,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldDate fieldDate = new SysEnFieldDate();
        if( StringUtils.isNotBlank( configs ) ){
            configs = "width:250,allowBlank:"+isAllowBlank+","+configs;
        }else{
            configs = "width:250,allowBlank:"+isAllowBlank;
        }

        JSONObject jo = null;
        if( StringUtils.isNotBlank( configs ) ){
             jo = JSONObject.fromObject( "{"+configs+"}" );

            //处理日期格式
            if( jo.containsKey( "format" ) ){
                if( !jo.containsKey( "formatJava" ) ){
                    throw new PlatformException( name+"日期字段配置错误，缺少formatJava配置！");
                }

                fieldDate.setFormat( jo.get("format").toString() );
                fieldDate.setFormatJava( jo.get("formatJava").toString() );
            }else{
                configs = "format:'"+PlatformSysConstant.DATE_FORMAT_EXT_YMD+"',"+configs;
                configs = "formatJava:'"+PlatformSysConstant.DATE_FORMAT_JAVA_YMD+"',"+configs;
                jo.put("format",PlatformSysConstant.DATE_FORMAT_EXT_YMD);
                jo.put("formatJava",PlatformSysConstant.DATE_FORMAT_JAVA_YMD);

                fieldDate.setFormat( PlatformSysConstant.DATE_FORMAT_EXT_YMD );
                fieldDate.setFormatJava( PlatformSysConstant.DATE_FORMAT_JAVA_YMD );
            }
        }

        if( StringUtils.isNotBlank( configs ) ){
            fieldDate.setConfigs( configs );
        }

        dealExtConfigsTransform(fieldDate,extConfigsTransform);

        sysEnControllerField.setSysEnFieldAttr( fieldDate );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;*/
    }

    /**
     * 添加一个格式为 yyyy-MM-dd HH:mm 的Date字段。     isShowList：true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     */
    public SysEnControllerField addFieldDate_yMdHm(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs){
        return this.addFieldDate_yMdHm(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,null);
    }

    /**
     * 添加一个格式为 yyyy-MM-dd HH:mm 的Date字段。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     * @param extConfigsTransform
     * @return
     */
    public SysEnControllerField addFieldDate_yMdHm(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs,String extConfigsTransform){
        return this.addFieldDate_yMdHm(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,extConfigsTransform);
    }

    /**
     * 添加一个格式为 yyyy-MM-dd HH:mm 的Date字段。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isShowList
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     * @param extConfigsTransform
     * @return
     */
    public SysEnControllerField addFieldDate_yMdHm(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String configs,String extConfigsTransform){
        if( StringUtils.isNotBlank( configs ) ){
            configs = "format:'"+PlatformSysConstant.DATE_FORMAT_EXT_YMDHM+"',formatJava:'"+PlatformSysConstant.DATE_FORMAT_JAVA_YMDHM+"',"+configs;
        }else{
            configs = "format:'"+PlatformSysConstant.DATE_FORMAT_EXT_YMDHM+"',formatJava:'"+PlatformSysConstant.DATE_FORMAT_JAVA_YMDHM+"'";
        }

        return this.addFieldDatetime(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank,configs,extConfigsTransform);
    }

    /**
     *  添加一个格式为 yyyy-MM-dd  HH:mm:ss 的DateTime字段    isShowList：true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     */
    public SysEnControllerField addFieldDatetime(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs){
        return this.addFieldDatetime(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,null);
    }

    /**
     * 添加一个格式为 yyyy-MM-dd  HH:mm:ss 的DateTime字段
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     * @param extConfigsTransform
     * @return
     */
    public SysEnControllerField addFieldDatetime(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs,String extConfigsTransform){
        return this.addFieldDatetime(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,extConfigsTransform);
    }

    /**
     * 添加一个格式为 yyyy-MM-dd  HH:mm:ss 的DateTime字段
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     */
    public SysEnControllerField addFieldDatetime(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String configs,String extConfigsTransform){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_DATE_TIME,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldDate fieldDate = new SysEnFieldDate();
        /**
         * 执行以下代码，确保configs有值
         */
        if( StringUtils.isNotBlank( configs ) ){
            configs = "width:250,allowBlank:"+isAllowBlank+","+configs;
        }else{
            configs = "width:250,allowBlank:"+isAllowBlank;
        }

        JSONObject jo = null;
        if( StringUtils.isNotBlank( configs ) ){
            jo = JSONObject.fromObject( "{"+configs+"}" );

            //处理日期格式
            if( jo.containsKey( "format" ) ){
                if( !jo.containsKey( "formatJava" ) ){
                    throw new PlatformException( name+"日期字段配置错误，缺少formatJava配置！");
                }

                fieldDate.setFormat( jo.get("format").toString() );
                fieldDate.setFormatJava( jo.get("formatJava").toString() );
            }else{
                configs = "format:'"+PlatformSysConstant.DATE_FORMAT_EXT_YMDHMS+"',"+configs;
                configs = "formatJava:'"+PlatformSysConstant.DATE_FORMAT_JAVA_YMDHMS+"',"+configs;
                jo.put("format",PlatformSysConstant.DATE_FORMAT_EXT_YMDHMS);
                jo.put("formatJava",PlatformSysConstant.DATE_FORMAT_JAVA_YMDHMS);

                fieldDate.setFormat( PlatformSysConstant.DATE_FORMAT_EXT_YMDHMS );
                fieldDate.setFormatJava( PlatformSysConstant.DATE_FORMAT_JAVA_YMDHMS );
            }

        }

        /*if( StringUtils.isNotBlank( configs ) ){
            configs = "format:'"+PlatformSysConstant.DATE_FORMAT_EXT_YMDHMS+"',"+configs;
        }else{
            configs = "format:'"+PlatformSysConstant.DATE_FORMAT_EXT_YMDHMS;
        }*/

        if( StringUtils.isNotBlank( configs ) ){
            fieldDate.setConfigs( configs );
        }
		/*if( StringUtils.isNotBlank( configs ) ){
			JSONObject jo = JSONObject.fromObject( "{"+configs+"}" );
			this.dealFormFieldAttr( fieldDate,jo );
		}
		fieldDate.setFormat( PlatformSysConstant.DATE_FORMAT_EXT_YMD );
*/
        dealExtConfigsTransform(fieldDate,extConfigsTransform);

        sysEnControllerField.setSysEnFieldAttr( fieldDate );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }

    /**
     * 添加一个富文本编辑器字段   isShowList：true   isAllowSearch:false
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowBlank
     * @param extConfigs
     */
    public SysEnControllerField addFieldHtmlEditor( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowBlank,String extConfigs){
        return this.addFieldHtmlEditor( name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,false,isAllowBlank, extConfigs);
    }

    /**
     * 添加一个富文本编辑器字段
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isShowList
     * @param isAllowSearch
     * @param isAllowBlank
     * @param extConfigs
     */
    public SysEnControllerField addFieldHtmlEditor( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String extConfigs){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_HTML_EDITOR,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldHtmlEditor formField = new SysEnFieldHtmlEditor();
        if( StringUtils.isNotBlank(extConfigs) ){
            extConfigs = "width:400,allowBlank:"+isAllowBlank+","+ extConfigs;
        }else{
            extConfigs = "width:400,allowBlank:"+isAllowBlank;
        }


        if( StringUtils.isNotBlank( extConfigs ) ){
            formField.setConfigs(extConfigs);
        }

        sysEnControllerField.setSysEnFieldAttr( formField );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }

    /**
     * 添加一组复选框。  isShowList：true
     * 通过json初始化数据
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param jsonData
     * @param checkboxGroupConfigs
     * @param checkboxConfigs
     */
    public SysEnControllerField addFieldCheckboxInitDataByJson(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String jsonData,String checkboxGroupConfigs,String checkboxConfigs){
        return this.addFieldCheckbox(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,jsonData,checkboxGroupConfigs,checkboxConfigs);
    }

    /**
     * 添加一组复选框。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param jsonData
     * @param checkboxGroupConfigs
     * @param checkboxConfigs
     */
    public SysEnControllerField addFieldCheckbox(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String jsonData,String checkboxGroupConfigs,String checkboxConfigs){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_CHECKBOXGROUP,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldCheckboxGroup checkboxGroup = new SysEnFieldCheckboxGroup();

        if( StringUtils.isNotBlank( checkboxGroupConfigs ) ){
            checkboxGroup.setConfigs( checkboxGroupConfigs );
        }

        JSONArray ja = JSONArray.fromObject(jsonData);
        for( int i=0 ;i<ja.size();i++ ){
            JSONObject tempJo = ja.getJSONObject(i);
            if( !tempJo.containsKey("name") ){
                throw new PlatformException("缺少name属性。");
            }
            if( !tempJo.containsKey("value") ){
                throw new PlatformException("缺少value属性。");
            }
            if( !tempJo.containsKey("eleId") ){
                throw new PlatformException("缺少eleId属性。");
            }

            SysEnFieldCheckbox checkbox = new SysEnFieldCheckbox();
            checkbox.setBoxLabel( tempJo.getString("name") );
            checkbox.setInputValue( tempJo.getString("value") );
            checkbox.setEleId( tempJo.getString("eleId") );
            if( StringUtils.isNotBlank( checkboxConfigs ) ){
                checkbox.setConfigs( checkboxConfigs );
            }
            if( tempJo.containsKey("checked") && "true".equals( tempJo.get("checked") ) ){
                checkbox.setChecked( "true" );
            }else{
                checkbox.setChecked( "false" );
            }

            checkboxGroup.getCheckboxList().add(checkbox);
        }

        sysEnControllerField.setSysEnFieldAttr( checkboxGroup );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }

    /**
     *暂时没有实现
     */
    public SysEnControllerField addFieldRadioInitDataByMethod(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String loadDataMethodName,String radioGroupConfigs,String radioConfigs){
        String jsonData = null;
        return this.addFieldRadio(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,jsonData,radioGroupConfigs,radioConfigs);
    }


    /**
     * 添加一组单选按钮。
     * 通过json初始化数据
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param jsonData
     * @param radioGroupConfigs
     * @param radioConfigs
     */
    public SysEnControllerField addFieldRadioInitDataByJson(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String jsonData,String radioGroupConfigs,String radioConfigs){
        return this.addFieldRadio(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,jsonData,radioGroupConfigs,radioConfigs);
    }

    /**
     * 添加一组单选按钮
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param jsonData
     * @param radioGroupConfigs
     * @param radioConfigs
     */
    public SysEnControllerField addFieldRadio(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String jsonData,String radioGroupConfigs,String radioConfigs){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_RADIOGROUP,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldRadioGroup radioGroup = new SysEnFieldRadioGroup();

        if( StringUtils.isNotBlank( radioGroupConfigs ) ){
            radioGroup.setConfigs( radioGroupConfigs );
        }

        JSONArray ja = JSONArray.fromObject(jsonData);
        for( int i=0 ;i<ja.size();i++ ){
            JSONObject tempJo = ja.getJSONObject(i);
            if( !tempJo.containsKey("name") ){
                throw new PlatformException("缺少name属性。");
            }
            if( !tempJo.containsKey("value") ){
                throw new PlatformException("缺少value属性。");
            }

            if( !tempJo.containsKey("eleId") ){
                throw new PlatformException("缺少eleId属性。");
            }

            SysEnFieldRadio radio = new SysEnFieldRadio();
            radio.setBoxLabel( tempJo.getString("name") );
            radio.setInputValue( tempJo.getString("value") );
            radio.setEleId( tempJo.getString("eleId") );
            if( StringUtils.isNotBlank( radioConfigs ) ){
                radio.setConfigs( radioConfigs );
            }
            if( tempJo.containsKey("checked") &&
                ("true".equals( tempJo.get("checked") )  || true==(Boolean) tempJo.get("checked") )  ){
                radio.setChecked( "true" );
            }else{
                radio.setChecked( "false" );
            }

            radioGroup.getRadioList().add(radio);
        }

        sysEnControllerField.setSysEnFieldAttr( radioGroup );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }

    /**
     * 添加一个tag控件     isShowList为true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param jsonData
     * @param tagConfigs
     */
    public SysEnControllerField addFieldTagByJSON( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String jsonData,String tagConfigs ){
        return this.addFieldTagByJSON(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,jsonData,tagConfigs,null);
    }

    public SysEnControllerField addFieldTagByJSON( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String jsonData,String tagConfigs,String extConfigsTransform ){
        return this.addFieldTagByJSON(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,jsonData,tagConfigs,extConfigsTransform);
    }

    /**
     * 添加一个tag控件
     * 使用json初始化数据。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isShowList
     * @param isAllowSearch
     * @param isAllowBlank
     * @param jsonData
     * @param tagConfigs
     */
    public SysEnControllerField addFieldTagByJSON( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String jsonData,String tagConfigs,String extConfigsTransform ){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_TAG,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        //tag继承combox，使用combobox属性即可
        SysEnFieldTag tag = new SysEnFieldTag();
        tag.setComboBoxName( name );
        tag.setLoadDataImplModel("json");
        tag.setCascade(false);

        if( StringUtils.isNotBlank( tagConfigs ) ){
            tagConfigs = "allowBlank:"+isAllowBlank+","+tagConfigs;
        }else{
            tagConfigs = "allowBlank:"+isAllowBlank;
        }

        if( StringUtils.isNotBlank( tagConfigs ) ){
            tag.setConfigs( tagConfigs );
        }

        JSONArray ja = JSONArray.fromObject(jsonData);
        for( int i=0 ;i<ja.size();i++ ){
            JSONObject tempJo = ja.getJSONObject(i);
            if( !tempJo.containsKey("name") ){
                throw new PlatformException("缺少name属性。 ");
            }
            if( !tempJo.containsKey("value") ){
                throw new PlatformException("缺少value属性。");
            }

            SysEnFieldComboBoxOption comboBoxOption = new SysEnFieldComboBoxOption();
            comboBoxOption.setName( tempJo.getString("name") );
            comboBoxOption.setValue( tempJo.getString("value") );

            tag.getComboBoxOptionList().add( comboBoxOption );
        }

        dealExtConfigsTransform(tag,extConfigsTransform);

        sysEnControllerField.setSysEnFieldAttr( tag );
        this.getSysEnControllerFieldList().add(sysEnControllerField);

        return sysEnControllerField;
    }

    /**
     * 此方法目前有bug，使用tag控件不要使用sql加载数据的方式。
     * 前台异步请求加载tag数据时有问题。
     * 1.ext配置的value值在请求时会传给id，而目前框架id只能接收整数，下例中传了多个值，框架无法接收参数。
     *   http://localhost:8080/framework/exampleBean/loadComboxData.do?id=130000%2C140000&comboBoxFlag=loadTag&value=&page=1&start=0&limit=25
     * 2.tag控件发起加载数据请求后进入无限循环，不断请求后台。
     * 添加一个tag控件。
     * isShowList   true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param comboBoxFlag
     * @param firstComboBoxParamValue
     * @param tagConfigs
     */
    @Deprecated
    public SysEnControllerField addFieldTagBySQL( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String comboBoxFlag,String firstComboBoxParamValue,String tagConfigs){
        return this.addFieldTagBySQL(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,comboBoxFlag,firstComboBoxParamValue,tagConfigs,null);
    }

    @Deprecated
    public SysEnControllerField addFieldTagBySQL( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String comboBoxFlag,String firstComboBoxParamValue,String tagConfigs,String extConfigsTransform){
        return this.addFieldTagBySQL(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,comboBoxFlag,firstComboBoxParamValue,tagConfigs,extConfigsTransform);
    }

    /**
     * 添加一个tag控件。
     * 通过页面请求url地址加载下拉数据。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isShowList
     * @param isAllowSearch
     * @param isAllowBlank
     * @param comboBoxFlag
     * @param firstComboBoxParamValue
     * @param tagConfigs
     */
    @Deprecated
    public SysEnControllerField addFieldTagBySQL( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String comboBoxFlag,String firstComboBoxParamValue,String tagConfigs,String extConfigsTransform){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_TAG,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldTag tag = new SysEnFieldTag();
        tag.setComboBoxName( name );
        tag.setLoadDataImplModel("sql");
        tag.setComboBoxFlag( comboBoxFlag );
        tag.setFirstComboBoxParamValue( firstComboBoxParamValue );
        tag.setCascade(false);

        if( StringUtils.isNotBlank(tagConfigs) ){
            tagConfigs = "allowBlank:"+isAllowBlank+","+ tagConfigs;
        }else{
            tagConfigs = "allowBlank:"+isAllowBlank;
        }

        if( StringUtils.isNotBlank( tagConfigs ) ){
            tag.setConfigs( tagConfigs );
        }

        dealExtConfigsTransform(tag,extConfigsTransform);

        sysEnControllerField.setSysEnFieldAttr( tag );
        this.getSysEnControllerFieldList().add(sysEnControllerField);

        return sysEnControllerField;
    }

    /**
     * 添加一个下拉框。 isShowList：true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param comboBoxFlag
     * @param firstComboBoxParamValue
     * @param comboBoxConfigs
     */
    public SysEnControllerField addFieldComboBoxBySQL( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String comboBoxFlag,String firstComboBoxParamValue,String comboBoxConfigs){
        return this.addFieldComboBoxBySQL(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,comboBoxFlag,firstComboBoxParamValue,comboBoxConfigs,null);
    }

    /**
     * 添加一个常量下拉框，框架自动实现数据的加载，不需要自己实现数据加载方法。 isShowList：true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param constantNamespace
     * @param firstComboBoxParamValue
     * @param comboBoxConfigs
     * @return
     */
    public SysEnControllerField addFieldComboBoxBySQLConstant( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String constantNamespace,String firstComboBoxParamValue,String comboBoxConfigs){
        return this.addFieldComboBoxBySQL(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,"constant_"+constantNamespace,firstComboBoxParamValue,comboBoxConfigs,null);
    }

    public SysEnControllerField addFieldComboBoxBySQL( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String comboBoxFlag,String firstComboBoxParamValue,String comboBoxConfigs,String extConfigsTransform){
        return this.addFieldComboBoxBySQL(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,comboBoxFlag,firstComboBoxParamValue,comboBoxConfigs,extConfigsTransform);
    }

    /**
     * 添加一个常量下拉框，框架自动实现数据的加载，不需要自己实现数据加载方法。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param constantNamespace
     * @param firstComboBoxParamValue
     * @param comboBoxConfigs
     * @param extConfigsTransform
     * @return
     */
    public SysEnControllerField addFieldComboBoxBySQLConstant( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String constantNamespace,String firstComboBoxParamValue,String comboBoxConfigs,String extConfigsTransform){
        return this.addFieldComboBoxBySQL(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,"constant_"+constantNamespace,firstComboBoxParamValue,comboBoxConfigs,extConfigsTransform);
    }

    /**
     * 添加一个下拉框。
     * 通过请求后台查询数据库初始化下拉框数据。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param comboBoxFlag
     * @param firstComboBoxParamValue
     * @param comboBoxConfigs
     */
    public SysEnControllerField addFieldComboBoxBySQL( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String comboBoxFlag,String firstComboBoxParamValue,String comboBoxConfigs,String extConfigsTransform){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_COMBOBOX,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldComboBox comboBox = new SysEnFieldComboBox();
        comboBox.setComboBoxName( name );
        comboBox.setLoadDataImplModel("sql");
        comboBox.setComboBoxFlag( comboBoxFlag );
        comboBox.setFirstComboBoxParamValue( firstComboBoxParamValue );
        comboBox.setCascade(false);

        if( StringUtils.isNotBlank( comboBoxConfigs ) ){
            comboBoxConfigs = "width:250,allowBlank:"+isAllowBlank+","+comboBoxConfigs;
        }else{
            comboBoxConfigs = "width:250,allowBlank:"+isAllowBlank;
        }

        if( StringUtils.isNotBlank( comboBoxConfigs ) ){
            comboBox.setConfigs( comboBoxConfigs );
        }
        dealExtConfigsTransform(comboBox,extConfigsTransform);

        SysEnFieldComboBoxGroup comboBoxGroup = new SysEnFieldComboBoxGroup();
        comboBoxGroup.setCascade( false );
        comboBoxGroup.getComboBoxList().add( comboBox );
       // comboBoxGroup.setConfigs( comboBoxGroupConfigs );


        sysEnControllerField.setSysEnFieldAttr( comboBoxGroup );
        this.getSysEnControllerFieldList().add(sysEnControllerField);

        return sysEnControllerField;
    }

    /**
     * 添加一个下拉框。  isShowList ：true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param jsonData
     * @param comboBoxConfigs
     */
    public SysEnControllerField addFieldComboBoxByJSON( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String jsonData,String comboBoxConfigs ){
        return this.addFieldComboBoxByJSON(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,jsonData,comboBoxConfigs,null);
    }

    public SysEnControllerField addFieldComboBoxByJSON( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String jsonData,String comboBoxConfigs,String extConfigsTransform ){
        return this.addFieldComboBoxByJSON(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,jsonData,comboBoxConfigs,extConfigsTransform);
    }

    /**
     * 添加一个下拉框。
     * 通过给定的json初始化下拉数据。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param jsonData
     * @param comboBoxConfigs
     */
    public SysEnControllerField addFieldComboBoxByJSON( String name, String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String jsonData,String comboBoxConfigs,String extConfigsTransform ){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_COMBOBOX,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldComboBox comboBox = new SysEnFieldComboBox();
        comboBox.setComboBoxName( name );
        comboBox.setLoadDataImplModel("json");
        comboBox.setCascade(false);

        if( StringUtils.isNotBlank( comboBoxConfigs ) ){
            comboBoxConfigs = "width:250,allowBlank:"+isAllowBlank+","+comboBoxConfigs;
        }else{
            comboBoxConfigs = "width:250,allowBlank:"+isAllowBlank;
        }

        if( StringUtils.isNotBlank( comboBoxConfigs ) ){
            comboBox.setConfigs( comboBoxConfigs );
        }

        JSONArray ja = JSONArray.fromObject(jsonData);
        for( int i=0 ;i<ja.size();i++ ){
            JSONObject tempJo = ja.getJSONObject(i);
            if( !tempJo.containsKey("name") ){
                throw new PlatformException("缺少name属性。");
            }
            if( !tempJo.containsKey("value") ){
                throw new PlatformException("缺少value属性。");
            }

            SysEnFieldComboBoxOption comboBoxOption = new SysEnFieldComboBoxOption();
            comboBoxOption.setName( tempJo.getString("name") );
            comboBoxOption.setValue( tempJo.getString("value") );

            comboBox.getComboBoxOptionList().add( comboBoxOption );
        }

        dealExtConfigsTransform(comboBox,extConfigsTransform);

        SysEnFieldComboBoxGroup comboBoxGroup = new SysEnFieldComboBoxGroup();
        comboBoxGroup.setCascade( false );
        comboBoxGroup.getComboBoxList().add( comboBox );
//        comboBoxGroup.setConfigs( comboBoxGroupConfigs );


        sysEnControllerField.setSysEnFieldAttr( comboBoxGroup );
        this.getSysEnControllerFieldList().add(sysEnControllerField);

        return sysEnControllerField;
    }

    /**
     * 添加一组级联下拉框（级联层数不限）     isShowList：true
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param comboBoxGroupName
     * @param firstComboBoxParamValue
     * @param comboBoxNames
     * @param comboBoxFlag
     * @param comboBoxGroupConfigs
     * @param comboBoxConfigs
     */
    public SysEnControllerField addFieldComboBoxCascadeBySQL(  String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,
                                               String comboBoxGroupName,String firstComboBoxParamValue,
                                               String[] comboBoxFieldLabel,String[] comboBoxNames,String[] comboBoxFlag,
                                               String comboBoxGroupConfigs,String comboBoxConfigs,String[] comboBoxInitVal ){
        return this.addFieldComboBoxCascadeBySQL(fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,
                                            comboBoxGroupName,firstComboBoxParamValue,
                                            comboBoxFieldLabel,comboBoxNames,comboBoxFlag,comboBoxGroupConfigs,comboBoxConfigs,comboBoxInitVal);
    }

    /**
     * 添加一组级联下拉框（级联层数不限）     isShowList：true
     * 不用传初始化的值。
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param comboBoxGroupName
     * @param firstComboBoxParamValue
     * @param comboBoxNames
     * @param comboBoxFlag
     * @param comboBoxGroupConfigs
     * @param comboBoxConfigs
     */
    public SysEnControllerField addFieldComboBoxCascadeBySQL(  String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,
                                               String comboBoxGroupName,String firstComboBoxParamValue,
                                               String[] comboBoxFieldLabel,String[] comboBoxNames,String[] comboBoxFlag,
                                               String comboBoxGroupConfigs,String comboBoxConfigs ){
        return this.addFieldComboBoxCascadeBySQL(fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,
                                            comboBoxGroupName,firstComboBoxParamValue,
                                            comboBoxFieldLabel,comboBoxNames,comboBoxFlag,comboBoxGroupConfigs,comboBoxConfigs,null);
    }

    /**
     * 添加一组级联下拉框（级联层数不限）
     * 通过请求后台查询数据库初始化下拉框数据。
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param comboBoxGroupName
     * @param firstComboBoxParamValue
     * @param comboBoxNames
     * @param comboBoxFlag
     * @param comboBoxGroupConfigs
     * @param comboBoxConfigs
     */
    public SysEnControllerField addFieldComboBoxCascadeBySQL(  String fieldLabel,  boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,
                                               String comboBoxGroupName,String firstComboBoxParamValue,
                                               String[] comboBoxFieldLabel,String[] comboBoxNames,String[] comboBoxFlag,
                                               String comboBoxGroupConfigs,String comboBoxConfigs,String[] comboBoxInitVal ){
        if( comboBoxFieldLabel==null || comboBoxNames==null || comboBoxFlag ==null || ( comboBoxNames.length!=comboBoxFlag.length )  || ( comboBoxNames.length!=comboBoxFieldLabel.length ) ){
            throw new PlatformException("构造下拉框出错！参数错误！");
        }

        if( comboBoxInitVal!=null && comboBoxNames.length!=comboBoxInitVal.length  ){
            throw new PlatformException("构造下拉框出错！初始化下拉框值参数错误！");
        }

        if( comboBoxNames.length<1 ){
            throw new PlatformException("构造下拉框出错！至少需要配置一个下来框！");
        }

        SysEnControllerField  sysEnControllerField = new SysEnControllerField(comboBoxGroupName,fieldLabel,PlatformSysConstant.FORM_XTYPE_COMBOBOX,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldComboBoxGroup comboBoxGroup = new SysEnFieldComboBoxGroup();
        comboBoxGroup.setCascade( true );

        if( StringUtils.isNotBlank( comboBoxGroupConfigs ) ){
            comboBoxGroup.setConfigs( comboBoxGroupConfigs );
        }

        int comboBoxNum = comboBoxNames.length;
        for( int i=0;i<comboBoxNum;i++ ){
            if( StringUtils.isBlank( comboBoxNames[i] )  || StringUtils.isBlank( comboBoxFlag[i] ) ){
                throw new PlatformException("构造下拉框出错！参数为空！");
            }

            SysEnFieldComboBox tempComboBox = new SysEnFieldComboBox();
            tempComboBox.setComboBoxName( comboBoxNames[i] );
            tempComboBox.setComboBoxFieldLabel( comboBoxFieldLabel[i] );
            tempComboBox.setLoadDataImplModel("sql");
            if( i==0 ){
                tempComboBox.setFirstComboBoxParamValue( firstComboBoxParamValue );
            }
            tempComboBox.setComboBoxFlag( comboBoxFlag[i] );
            tempComboBox.setCascade(true);
            tempComboBox.setComboBoxOrder( i+1 );
            if( i>0 ){
                tempComboBox.setParentComboBox( comboBoxNames[i-1] );
            }
            if( i+1<comboBoxNum ){
                tempComboBox.setChildComboBox( comboBoxNames[i+1] );
            }

            if( i==0 && i+1==comboBoxNum ){
                tempComboBox.setFirst( true );
                tempComboBox.setLast( true );
            }else if(i==0){
                tempComboBox.setFirst( true );
                tempComboBox.setLast( false );
            }else if( i+1==comboBoxNum ){
                tempComboBox.setFirst( false );
                tempComboBox.setLast( true );
            }else{
                tempComboBox.setFirst( false );
                tempComboBox.setLast( false );
            }

            if( StringUtils.isNotBlank( comboBoxConfigs ) ){
                comboBoxConfigs = "labelWidth:0,width:88,allowBlank:"+isAllowBlank+","+comboBoxConfigs;
            }else{
                comboBoxConfigs = "labelWidth:0,width:88,allowBlank:"+isAllowBlank;
            }


            if( StringUtils.isNotBlank( comboBoxConfigs ) ){
                tempComboBox.setConfigs( comboBoxConfigs );
            }

            if( comboBoxInitVal!=null && StringUtils.isNotBlank( comboBoxInitVal[i] ) ){
                tempComboBox.setValue( comboBoxInitVal[i] );
            }

            comboBoxGroup.getComboBoxList().add( tempComboBox );
        }

        /**
         * 设置每个下拉框的父子关系及所有后续级联下拉框
         */
        List<SysEnFieldComboBox> comboboxList = comboBoxGroup.getComboBoxList();
        for( int i=0;i<comboBoxGroup.getComboBoxList().size();i++ ){
            if( i>0 ){
                comboboxList.get(i-1).setChildSysEnFieldComboBox( comboboxList.get(i) );
                comboboxList.get(i).setParentSysEnFieldComboBox( comboboxList.get(i-1) );
            }

            SysEnFieldComboBox tempComboBox = comboboxList.get(i);
            if( !tempComboBox.isLast() ){
                List<SysEnFieldComboBox> cascadeList = new ArrayList<SysEnFieldComboBox>();
                for( int j=i+1;j<comboboxList.size();j++ ){
                    cascadeList.add( comboboxList.get(j) );
                }
                tempComboBox.setCascadeList( cascadeList );
            }
        }

        sysEnControllerField.setSysEnFieldAttr( comboBoxGroup );
        this.getSysEnControllerFieldList().add(sysEnControllerField);

        return sysEnControllerField;
    }

    /**
     * 添加一个下拉树。   isShowList：true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     * @param loadDataUrl
     */
    public SysEnControllerField addFieldComboBoxTree(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs,String loadDataUrl){
        return this.addFieldComboBoxTree(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,loadDataUrl,null);
    }

    public SysEnControllerField addFieldComboBoxTree(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank,String configs,String loadDataUrl,String extConfigsTransform){
        return this.addFieldComboBoxTree(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,configs,loadDataUrl,extConfigsTransform);
    }

    /**
     * 添加一个下拉树。
     * 通过请求后台查询数据库初始化下拉树节点数据。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param configs
     * @param loadDataUrl
     */
    public SysEnControllerField addFieldComboBoxTree(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch, boolean isAllowBlank,String configs,String loadDataUrl,String extConfigsTransform){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_COMBOBOXTREE,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,isAllowBlank);

        SysEnFieldComboBoxTree fieldComboBoxTree = new SysEnFieldComboBoxTree();
        fieldComboBoxTree.setUrl( loadDataUrl );

        if( StringUtils.isNotBlank( configs ) ){
            configs = "allowBlank:"+isAllowBlank+","+configs;
        }else{
            configs = "allowBlank:"+isAllowBlank;
        }

        /*if( StringUtils.isNotBlank( configs ) ){
            JSONObject jo = JSONObject.fromObject( "{"+configs+"}" );
            Object multiSelect = jo.get("multiSelect");
            if( null!=multiSelect ){
                loadDataUrl = loadDataUrl+"&multiSelect="+multiSelect.toString();
                fieldComboBoxTree.setUrl( loadDataUrl );
            }
        }*/


        if( StringUtils.isNotBlank( configs ) ){
            fieldComboBoxTree.setConfigs( configs );
        }
        dealExtConfigsTransform(fieldComboBoxTree,extConfigsTransform);

        sysEnControllerField.setSysEnFieldAttr( fieldComboBoxTree );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }



    public SysEnControllerField addFieldFile(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isAllowBlank, String extConfigs, String serviceConfigs ){
        return this.addFieldFile(name,fieldLabel,isValid, isAllowAdd, isAllowUpdate,true,isAllowBlank, extConfigs, serviceConfigs,null);
    }

    public SysEnControllerField addFieldFile(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isAllowBlank, String extConfigs, String serviceConfigs,String extConfigsTransform ){
        return this.addFieldFile(name,fieldLabel,isValid, isAllowAdd, isAllowUpdate,true,isAllowBlank, extConfigs, serviceConfigs,extConfigsTransform );
    }

    /**
     * 添加一个附件
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowBlank
     * @param extConfigs
     * @param serviceConfigs  json格式
     */
    public SysEnControllerField addFieldFile(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isShowList,boolean isAllowBlank, String extConfigs, String serviceConfigs,String extConfigsTransform ){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_FILE,isValid,isAllowAdd,isAllowUpdate,false,false,isAllowBlank);

        SysEnFieldFile formField = new SysEnFieldFile();
        if( StringUtils.isNotBlank(extConfigs) ){
            extConfigs = "allowBlank:"+isAllowBlank+","+ extConfigs;
        }else{
            extConfigs = "allowBlank:"+isAllowBlank;
        }

        if( StringUtils.isNotBlank( extConfigs ) ){
            formField.setConfigs(extConfigs);
        }
		/*if( StringUtils.isNotBlank( configs ) ){
			JSONObject jo = JSONObject.fromObject( "{"+configs+"}" );
			this.dealFormFieldAttr( formField,jo );
		}*/

        /**
         * 处理上传附件的服务端配置信息
         */
        if( StringUtils.isNotBlank(serviceConfigs) ){
            JSONObject jo = JSONObject.fromObject( "{"+ serviceConfigs +"}" );

            Object savePath = jo.get("savePath");
            if( savePath!=null && StringUtils.isNotBlank( savePath.toString() ) ){
                //文件的保存路径存到隐藏域中
                String fileHiddenConfigs = "value:'"+savePath.toString()+"'";
                this.addFieldHidden( name+"SavePathHidden","",isValid,isAllowAdd,isAllowUpdate,fileHiddenConfigs);
            }else{
                throw new PlatformException("上传文件缺少保存路径配置！");
            }

            Object allowFileType = jo.get("allowFileType");
            if( allowFileType!=null && StringUtils.isNotBlank( allowFileType.toString() ) ){
                formField.setAllowFileType( allowFileType.toString() );
            }else{
                throw new PlatformException("上传文件缺少文件类型配置！");
            }

        }else{
            throw new PlatformException("上传文件缺少配置！");
        }

        dealExtConfigsTransform(formField,extConfigsTransform);

        sysEnControllerField.setSysEnFieldAttr( formField );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        if( isShowList ){
            //设置只在列表中显示附件名称
            SysEnControllerField orgFileNameField = this.addFieldText(name+"OrigFileName",fieldLabel,isValid,false,false,false,true,null);
            //设置不在详情页面显示
            orgFileNameField.setShowOnView( false );
        }

        return sysEnControllerField;
    }

    /**
     * 添加一个Display表单控件   isShowList:true
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param configs
     */
    public SysEnControllerField addFieldDisplay(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch,String configs){
        return this.addFieldDisplay(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,configs);
    }

    /**
     * 添加一个Display表单控件
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param configs
     */
    public SysEnControllerField addFieldDisplay(String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate,boolean isShowList, boolean isAllowSearch,String configs){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_DISPLAY,isValid,isAllowAdd,isAllowUpdate,isShowList,isAllowSearch,true);

        SysEnFieldDisplay formField = new SysEnFieldDisplay();

        if( StringUtils.isNotBlank( configs ) ){
            formField.setConfigs( configs );
        }
		/*if( StringUtils.isNotBlank(configs) ){
			JSONObject jo = JSONObject.fromObject( "{"+ configs +"}" );
			this.dealFormFieldAttr( formField,jo );
		}*/

        sysEnControllerField.setSysEnFieldAttr( formField );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }


    public SysEnControllerField addFieldWhiteBoard(String name, String fieldLabel, boolean isValid, boolean showOnAddPage, boolean showOnEditPage,String extConfigs){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,PlatformSysConstant.FORM_XTYPE_DISPLAY,isValid,showOnAddPage,showOnEditPage,false,false,true);

        SysEnFieldDisplay formField = new SysEnFieldDisplay();
        formField.setWhiteBoard(true);

        if( StringUtils.isNotBlank( extConfigs ) ){
            extConfigs = "submitValue:false,"+extConfigs;
            formField.setConfigs( extConfigs );
        }else{
            formField.setConfigs( "submitValue:false" );
        }

        sysEnControllerField.setSysEnFieldAttr( formField );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }

    /**
     * 列表中添加操作类型字段的基本方法。
     * 不支持在controller调用此方法添加列表字段。
     * @param name
     * @param fieldLabel
     * @param extConfigs
     * @param serviceConfigs
     * @return
     */
    public SysEnControllerField addFieldOperate(String name, String fieldLabel, String extConfigs, String serviceConfigs){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,null,true,false,false,true,false,true);
        sysEnControllerField.setShowOnView(false);
        sysEnControllerField.setFieldType( PlatformSysConstant.FIELD_TYPE_OPERATE );

        SysEnFieldListExtend fieldAttr = new SysEnFieldListExtend();

        if( StringUtils.isNotBlank(extConfigs) ){
            fieldAttr.setConfigs(extConfigs);
        }

        if( StringUtils.isNotBlank(serviceConfigs) ){
            JSONObject jo = JSONObject.fromObject( "{"+ serviceConfigs +"}" );

            if( !jo.containsKey("functionName") ){
                throw new PlatformException("列表操作字段("+name+")配置错误！缺少脚本函数名配置！");
            }else{
                fieldAttr.setFunctionName( jo.getString( "functionName" ).trim() );
            }

            //url不是必须参数
            if( jo.containsKey("url") ){
                fieldAttr.setUrl( jo.getString( "url" ).trim() );
            }

            if( jo.containsKey("param") ){
                String tempParam = jo.getString("param");
                if( StringUtils.isNotBlank( tempParam ) && tempParam.split(",").length>0 ){
                    fieldAttr.setParam( jo.getString( "param" ) );
                }
            }

            //tabTitle不是必须参数，打开新tab的操作才需要
            if( jo.containsKey("tabTitle") ){
                fieldAttr.setTabTitle( jo.getString( "tabTitle" ).trim() );
            }

            if( jo.containsKey( "tabFlag" ) ){
                fieldAttr.setTabFlag( jo.getString("tabFlag").trim() );
            }

        }

        sysEnControllerField.setSysEnFieldAttr( fieldAttr );
        this.getSysEnControllerFieldList().add( sysEnControllerField );

        return sysEnControllerField;
    }

    /**
     * 列表中添加查看详情字段。
     * @param idFieldName  不能为空
     * @param extConfigs
     * @return
     */
    public SysEnControllerField addFieldViewDetail(String idFieldName,String extConfigs){
        if( StringUtils.isBlank( idFieldName ) ){
            throw new PlatformException("列表操作字段'查看详情'列配置错误！缺少参数配置！");
        }
        String viewServiceConfig = "functionName:'viewDetail_"+this.getIdentifier()+"',param:'"+idFieldName+"'";
        SysEnControllerField sysEnControllerField =this.addFieldOperate( "viewDetail","查看详情",extConfigs,viewServiceConfig );

        SysEnFieldListExtend sysEnFieldAttr =(SysEnFieldListExtend)sysEnControllerField.getSysEnFieldAttr();
        sysEnFieldAttr.setListFieldOperateTypeCode( PlatformSysConstant.LIST_FIELD_VIEW_DETAIL );

        return sysEnControllerField;
    }


    /**
     * 列表中添加一个ajax异步请求字段。
     * @param name
     * @param fieldLabel
     * @param reqUrl   不能为空
     * @param idFieldName  不能为空
     * @param extConfigs
     * @return
     */
    public SysEnControllerField addFieldSingleBaseAjaxReq(String name, String fieldLabel,String reqUrl, String idFieldName,String extConfigs){
        if( StringUtils.isBlank( reqUrl ) || StringUtils.isBlank( idFieldName ) ){
            throw new PlatformException("列表操作字段（"+name+"）列配置错误！缺少参数配置！");
        }
        String serviceConfigs = "functionName:'singleBaseAjaxReq_"+this.getIdentifier()+"',url:'"+reqUrl+"',param:'"+idFieldName+"'";
        SysEnControllerField sysEnControllerField =this.addFieldOperate( name,fieldLabel,extConfigs,serviceConfigs );

        SysEnFieldListExtend sysEnFieldAttr =(SysEnFieldListExtend)sysEnControllerField.getSysEnFieldAttr();
        sysEnFieldAttr.setListFieldOperateTypeCode( PlatformSysConstant.LIST_FIELD_SINGLE_BASE_AJAX_REQ );

        return sysEnControllerField;
    }

    /**
     * 列表中添加一个删除操作字段
     * @param idFieldName  不能为空
     * @param extConfigs
     * @return
     */
    public SysEnControllerField addFieldSingleDelete(String idFieldName,String extConfigs){
        if( StringUtils.isBlank( idFieldName ) ){
            throw new PlatformException("列表操作字段'删除'列配置错误！缺少参数配置！");
        }
        String deleServiceConfig = "functionName:'deleteSingleData_"+this.getIdentifier()+"',url:'delete.do',param:'"+idFieldName+"'";
        SysEnControllerField sysEnControllerField =this.addFieldOperate( "deleteSingleData","删除",extConfigs,deleServiceConfig );

        SysEnFieldListExtend sysEnFieldAttr =(SysEnFieldListExtend)sysEnControllerField.getSysEnFieldAttr();
        sysEnFieldAttr.setListFieldOperateTypeCode( PlatformSysConstant.LIST_FIELD_SINGLE_DELETE );

        return sysEnControllerField;
    }

    /**
     * 列表中添加一个自定义函数字段。
     * @param name
     * @param fieldLabel
     * @param functionName  不能为空
     * @param reqUrl  可为空
     * @param param  可为空
     * @param extConfigs
     * @return
     */
    public SysEnControllerField addFieldUserDefineOperate(String name, String fieldLabel,String functionName,String reqUrl,String param, String extConfigs){
        if( StringUtils.isBlank( functionName ) ){
            throw new PlatformException("列表操作字段（"+name+"）列配置错误！缺少参数配置！");
        }
        StringBuffer serviceConfigs = new StringBuffer( "functionName:'"+functionName+"'" );
        if( StringUtils.isNotBlank( reqUrl ) ){
            serviceConfigs.append( ",url:'"+reqUrl+"'" );
        }
        if( StringUtils.isNotBlank( param ) ){
            serviceConfigs.append( ",param:'"+param+"'" );
        }

        SysEnControllerField sysEnControllerField =this.addFieldOperate( name,fieldLabel,extConfigs,serviceConfigs.toString() );

        SysEnFieldListExtend sysEnFieldAttr =(SysEnFieldListExtend)sysEnControllerField.getSysEnFieldAttr();
        sysEnFieldAttr.setListFieldOperateTypeCode( PlatformSysConstant.LIST_FIELD_USER_DEFINE_OPERATE );

        return sysEnControllerField;
    }

    /**
     * 列表中添加一个打开新tab窗口字段。
     * 适用情况：新窗口的页面是自定义的。
     * @param name
     * @param fieldLabel
     * @param reqUrl  不能为空
     * @param tabTitle  不能为空
     * @param idFieldName  可为空
     * @param extConfigs
     * @return
     */
    public SysEnControllerField addFieldOpenNewTab(String name, String fieldLabel,String reqUrl,String tabTitle, String idFieldName,String extConfigs){
        if( StringUtils.isBlank( reqUrl ) || StringUtils.isBlank( tabTitle ) ){
            throw new PlatformException("列表操作字段（"+name+"）列配置错误！缺少参数配置！");
        }
        StringBuffer serviceConfigs = new StringBuffer( "functionName:'openNewTab',url:'"+reqUrl+"',tabTitle:'"+tabTitle+"'" );
        if( StringUtils.isNotBlank( idFieldName ) ){
            serviceConfigs.append( ",param:'"+idFieldName+"'" );
        }
        SysEnControllerField sysEnControllerField =this.addFieldOperate( name,fieldLabel,extConfigs,serviceConfigs.toString() );

        SysEnFieldListExtend sysEnFieldAttr =(SysEnFieldListExtend)sysEnControllerField.getSysEnFieldAttr();
        sysEnFieldAttr.setListFieldOperateTypeCode( PlatformSysConstant.LIST_FIELD_OPEN_NEW_TAB );

        return sysEnControllerField;
    }

    /**
     * 列表中添加一个打开新tab窗口字段。
     * 适用情况：新窗口的页面是通过框架配置的列表页面。
     *
     * 此方法中的controllerIdentifier参数，是为了解决在新tab窗口中打开框架的列表页面报错问题。
     * 与列表页面中的某一行代码有关，代码如下：
     * Ext.getCmp('right_tab_${identifier}').add( pagePanel${identifier} );
     *
     * @param name
     * @param fieldLabel
     * @param reqUrl  不能为空
     * @param tabTitle  不能为空
     * @param idFieldName  可为空
     * @param controllerIdentifier controller的id
     * @param extConfigs
     * @return
     */
    public SysEnControllerField addFieldOpenNewTabList(String name, String fieldLabel,String reqUrl,String tabTitle, String idFieldName,String controllerIdentifier, String extConfigs){
        if( StringUtils.isBlank( reqUrl ) || StringUtils.isBlank( tabTitle ) || StringUtils.isBlank( controllerIdentifier ) ){
            throw new PlatformException("列表操作字段（"+name+"）列配置错误！缺少参数配置！");
        }
        StringBuffer serviceConfigs = new StringBuffer( "functionName:'openNewTab',url:'"+reqUrl+"',tabTitle:'"+tabTitle+"',tabFlag:'"+controllerIdentifier+"'" );
        if( StringUtils.isNotBlank( idFieldName ) ){
            serviceConfigs.append( ",param:'"+idFieldName+"'" );
        }
        SysEnControllerField sysEnControllerField =this.addFieldOperate( name,fieldLabel,extConfigs,serviceConfigs.toString() );

        SysEnFieldListExtend sysEnFieldAttr =(SysEnFieldListExtend)sysEnControllerField.getSysEnFieldAttr();
        sysEnFieldAttr.setListFieldOperateTypeCode( PlatformSysConstant.LIST_FIELD_OPEN_NEW_TAB_LIST );

        return sysEnControllerField;
    }

    /**
     * 列表中添加一个打开浏览器新窗口字段。
     * @param name
     * @param fieldLabel
     * @param reqUrl  不能为空
     * @param idFieldName  可为空
     * @param extConfigs
     * @return
     */
    public SysEnControllerField addFieldOpenNewBrowserWindow(String name, String fieldLabel,String reqUrl, String idFieldName,String extConfigs){
        if( StringUtils.isBlank( reqUrl )  ){
            throw new PlatformException("列表操作字段（"+name+"）列配置错误！缺少参数配置！");
        }
        StringBuffer serviceConfigs = new StringBuffer( "functionName:'openNewBrowserWindow',url:'"+reqUrl+"'" );
        if( StringUtils.isNotBlank( idFieldName ) ){
            serviceConfigs.append( ",param:'"+idFieldName+"'" );
        }
        SysEnControllerField sysEnControllerField =this.addFieldOperate( name,fieldLabel,extConfigs,serviceConfigs.toString() );

        SysEnFieldListExtend sysEnFieldAttr =(SysEnFieldListExtend)sysEnControllerField.getSysEnFieldAttr();
        sysEnFieldAttr.setListFieldOperateTypeCode( PlatformSysConstant.LIST_FIELD_OPEN_NEW_BROWSER_WINDOW );

        return sysEnControllerField;
    }

    /**
     * 添加一个基本的ajax请求菜单。可以对选中的记录进行相关业务处理
     * @param identify
     * @param name
     * @param url
     * @param isSingle
     * @param iconCls
     * @param orderNum
     */
    public SysEnControllerFunction addFunctionBaseAjax( String identify,String name,String url,boolean isSingle,String iconCls,int orderNum ){
        SysEnControllerFunction sysEnControllerFunction = new SysEnControllerFunction(identify,name,url,PlatformSysConstant.MENU_TYPE_BASE_AJAX,isSingle,iconCls,orderNum);
        this.getSysEnControllerFunctionList().add( sysEnControllerFunction );

        return sysEnControllerFunction;
    }

    /**
     * 添加一个基本的ajax请求菜单，不需要选中任何记录。
     * @param identify
     * @param name
     * @param url
     * @param refreshList
     * @param iconCls
     * @param orderNum
     * @return
     */
    public SysEnControllerFunction addFunctionBaseAjaxIndepe( String identify,String name,String url,boolean refreshList,String iconCls,int orderNum ){
        //此构造方法中的isSingle值没有意义，默认为false，也可以是true。
        SysEnControllerFunction sysEnControllerFunction = new SysEnControllerFunction(identify,name,url,PlatformSysConstant.MENU_TYPE_BASE_AJAX_INDEPENDENT,false,iconCls,orderNum);
        sysEnControllerFunction.setRefreshList( refreshList );
        this.getSysEnControllerFunctionList().add( sysEnControllerFunction );

        return sysEnControllerFunction;
    }

    /**
     * 添加一个ajax请求菜单，可以统一修改某些字段的值。
     * @param identify
     * @param name
     * @param url
     * @param isSingle
     * @param iconCls
     * @param orderNum
     * @param fields
     * @return
     */
    public SysEnControllerFunction addFunctionAjaxUpdateFields( String identify,String name,String url,boolean isSingle,String iconCls,int orderNum,SysEnController controller,String fields ){
        if( StringUtils.isBlank( fields ) ){
            throw new PlatformException("菜单类型编码："+PlatformSysConstant.MENU_TYPE_AJAX_UPDATE_FIELDS+"。缺少字段配置！");
        }
        String[] fieldArray = fields.split(",");
        if( fieldArray==null || fieldArray.length==0 ){
            throw new PlatformException("菜单类型编码："+PlatformSysConstant.MENU_TYPE_AJAX_UPDATE_FIELDS+"。缺少字段配置！");
        }

        SysEnControllerFunction sysEnControllerFunction = new SysEnControllerFunction(identify,name,url,PlatformSysConstant.MENU_TYPE_AJAX_UPDATE_FIELDS,isSingle,iconCls,orderNum);
        sysEnControllerFunction.setUpdateFieldList( new ArrayList<SysEnControllerField>() );

        Map fieldMap = PlatformCollectionsUtils.convertObjectToMap(controller.getSysEnControllerFieldList(),"name");
        for( int i=0;i<fieldArray.length;i++ ){
            SysEnControllerField tempField = (SysEnControllerField)fieldMap.get( fieldArray[i] );
            if( null!=tempField ){
                String tempXtype = tempField.getXtype();
                if( PlatformSysConstant.FORM_XTYPE_TEXT.equals( tempXtype ) ||
                    PlatformSysConstant.FORM_XTYPE_TEXTAREA.equals( tempXtype ) ||
                    PlatformSysConstant.FORM_XTYPE_NUMBER.equals( tempXtype ) ||
                    PlatformSysConstant.FORM_XTYPE_DATE.equals( tempXtype ) ||
                    PlatformSysConstant.FORM_XTYPE_DATE_TIME.equals( tempXtype ) ){

                    sysEnControllerFunction.getUpdateFieldList().add( tempField );
                }else{
                    throw new PlatformException("菜单类型编码："+PlatformSysConstant.MENU_TYPE_AJAX_UPDATE_FIELDS+"。不支持的字段，只能配置以下控件类型的字段：文本框、文本域、数字、日期！");
                }
            }else{
                throw new PlatformException("菜单类型编码："+PlatformSysConstant.MENU_TYPE_AJAX_UPDATE_FIELDS+"。字段配置错误！");
            }
        }
        sysEnControllerFunction.setAjaxUpdateFields( fields );

        this.getSysEnControllerFunctionList().add( sysEnControllerFunction );

        return sysEnControllerFunction;
    }

    /**
     * 添加一个自定义菜单
     * @param identify
     * @param name
     * @param functionName
     * @param iconCls
     * @param orderNum
     * @return
     */
    public SysEnControllerFunction addFunctionUserDefineOperate( String identify,String name,String functionName,String iconCls,int orderNum ){
        SysEnControllerFunction sysEnControllerFunction = new SysEnControllerFunction(identify,name,functionName,PlatformSysConstant.MENU_TYPE_USER_DEFINE_OPERATE,iconCls,orderNum);
        this.getSysEnControllerFunctionList().add( sysEnControllerFunction );

        return sysEnControllerFunction;
    }

    /**
     * 添加一个功能说明性的菜单。
     * @param identify
     * @param name
     * @param iconCls
     * @param orderNum
     * @param instructions
     * @return
     */
    public SysEnControllerFunction addFunctionInstructions( String identify,String name,String iconCls,int orderNum,String instructions ){
        SysEnControllerFunction sysEnControllerFunction = new SysEnControllerFunction(identify,name,null,PlatformSysConstant.MENU_TYPE_INSTRUCTIONS,false,iconCls,orderNum);
        if( StringUtils.isNotBlank( instructions ) ){
            sysEnControllerFunction.setInstructions( instructions );
        }else{
            throw new PlatformException("菜单类型编码："+PlatformSysConstant.MENU_TYPE_INSTRUCTIONS+"。缺少说明内容配置！");
        }
        this.getSysEnControllerFunctionList().add( sysEnControllerFunction );

        return sysEnControllerFunction;
    }

    /**
     * 添加一个text字段，不需要传ext配置
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     */
/*    public void addFieldText( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank ){
        String tempConfigs = "inputType:'"+PlatformSysConstant.FORM_INPUTTYPE_TEXT+"'";
        this.addFieldText(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,tempConfigs,null);
    }*/

    /**
     * 添加一个text字段，可以传emptyText,vtype参数，不需要传ext配置
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param emptyText
     * @param vtype
     */
/*    public void addFieldText( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank, String emptyText, String vtype ){
        String tempConfigs = "inputType:'"+PlatformSysConstant.FORM_INPUTTYPE_TEXT+"',emptyText:'"+emptyText+"',"+"vtype:'"+vtype+"'";
        this.addFieldText(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,tempConfigs,null);
    }*/

    /**
     * 添加一个text字段，可以传emptyText,vtype,ext配置参数
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param emptyText
     * @param vtype
     * @param configs
     */
/*    public void addFieldText( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank, String emptyText, String vtype,String configs ){
        String tempConfigs = "inputType:'"+PlatformSysConstant.FORM_INPUTTYPE_TEXT+"',emptyText:'"+emptyText+"',"+"vtype:'"+vtype+"'";
        if( StringUtils.isNotBlank( configs ) ){
            tempConfigs += ","+configs;
        }
        this.addFieldText(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,true,isAllowSearch,isAllowBlank,tempConfigs,null);
    }*/

    /**
     * 添加一个Password字段。
     * 最简化的操作，不需要传extConfig。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     */
/*    public void addFieldPassword( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank ){
        String tempConfigs = "inputType:'"+PlatformSysConstant.FORM_INPUTTYPE_PASSWORD+"'";
        this.addFieldText(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,false,isAllowSearch,isAllowBlank,tempConfigs,null);
    }*/

    /**
     * 添加一个Password字段
     * 可以传emptyText、vtype参数，不需要传extConfig。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param emptyText
     * @param vtype
     */
/*    public void addFieldPassword( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank, String emptyText, String vtype ){
        String tempConfigs = "inputType:'"+PlatformSysConstant.FORM_INPUTTYPE_PASSWORD+"',emptyText:'"+emptyText+"',"+"vtype:'"+vtype+"'";
        this.addFieldText(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,false,isAllowSearch,isAllowBlank,tempConfigs,null);
    }*/

    /**
     * 添加一个Password字段。
     * 可以传emptyText、vtype、extConfig参数。
     * @param name
     * @param fieldLabel
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param emptyText
     * @param vtype
     * @param configs
     */
/*    public void addFieldPassword( String name, String fieldLabel, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank, String emptyText, String vtype,String configs ){
        String tempConfigs = "inputType:'"+PlatformSysConstant.FORM_INPUTTYPE_PASSWORD+"',emptyText:'"+emptyText+"',"+"vtype:'"+vtype+"'";
        if( StringUtils.isNotBlank( configs ) ){
            tempConfigs += ","+configs;
        }
        this.addFieldText(name,fieldLabel,isValid,isAllowAdd,isAllowUpdate,false,isAllowSearch,isAllowBlank,tempConfigs,null);
    }*/

    /**
     * 暂时先保留此方法，框架编写完成后删除
     * 添加列表页面每一个字段的相关属性
     * @param name
     * @param fieldLabel
     * @param type
     * @param xtype
     * @param isValid
     * @param isAllowAdd
     * @param isAllowUpdate
     * @param isAllowSearch
     * @param isAllowBlank
     * @param emptyText
     * @param vtype
     */
    /*public void addField(String name, String fieldLabel, String type, String xtype, boolean isValid, boolean isAllowAdd, boolean isAllowUpdate, boolean isAllowSearch, boolean isAllowBlank, String emptyText, String vtype){
        SysEnControllerField  sysEnControllerField = new SysEnControllerField(name,fieldLabel,type,xtype,isValid,isAllowAdd,isAllowUpdate,isAllowSearch,isAllowBlank);
        this.getSysEnControllerFieldList().add( sysEnControllerField );
    }*/



    /**
     * 暂时先保留此方法，框架编写完成后删除
     * 添加列表页面每一个字段的相关属性
     * @param name
     * @param fieldLabel
     * @param type
     */
    /*public void addField( String name, String fieldLabel, String type ){
        this.addField(name,fieldLabel,type,null,true,true,true,true,true,null,null);
    }*/

    /**
     * 将JSONObject的键值赋值给SysEnFormField对应的字段
     * @param sysEnField
     * @param jo
     */
    public void dealFormFieldAttr(SysEnFieldBase sysEnField, JSONObject jo ){
        Class fieldClass = sysEnField.getClass();

        SysEnFieldBase formFieldBase = new SysEnFieldBase();
        Class baseClass = formFieldBase.getClass();
        String baseClassInfo = baseClass.toString();

        boolean endFlag = false;
        while( !endFlag ){
            /**
             *getDeclaredFields只能获取类本身声明的字段，父类声明的字段获取不到
             * 所以要通过循环设置所有的字段属性值
             */
            Field[] fields = fieldClass.getDeclaredFields();
            for( Field field : fields ){
                try{
                    String fieldName = field.getName();
                    if( jo.containsKey(fieldName) ){
                        Method method = fieldClass.getDeclaredMethod( "set"+ PlatformStringUtils.firstLetterToUpperCase( fieldName ),String.class );
                        method.invoke(sysEnField,jo.get(fieldName).toString() );
                    }
                }catch( Exception e ){
                    e.printStackTrace();
                }
            }

            String fieldClassInfo = fieldClass.toString();
            if( baseClassInfo.equals( fieldClassInfo ) ){
                endFlag = true;
            }else{
                fieldClass = fieldClass.getSuperclass();
            }
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLoadDataUrl() {
        return loadDataUrl;
    }

    public void setLoadDataUrl(String loadDataUrl) {
        this.loadDataUrl = loadDataUrl;
    }

    public String getControllerBaseUrl() {
        return controllerBaseUrl;
    }

    public void setControllerBaseUrl(String controllerBaseUrl) {
        this.controllerBaseUrl = controllerBaseUrl;
    }

    public int getSearchConditionColNum() {
        return searchConditionColNum;
    }

    public void setSearchConditionColNum(int searchConditionColNum) {
        this.searchConditionColNum = searchConditionColNum;
    }

    public String getAddWindowConfigs() {
        return addWindowConfigs;
    }

    public void setAddWindowConfigs(String addWindowConfigs) {
        this.addWindowConfigs = addWindowConfigs;
    }

    public String getEditWindowConfigs() {
        return editWindowConfigs;
    }

    public void setEditWindowConfigs(String editWindowConfigs) {
        this.editWindowConfigs = editWindowConfigs;
    }

    public String getViewWindowConfigs() {
        return viewWindowConfigs;
    }

    public void setViewWindowConfigs(String viewWindowConfigs) {
        this.viewWindowConfigs = viewWindowConfigs;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getFormValJsFileName() {
        return formValJsFileName;
    }

    public void setFormValJsFileName(String formValJsFileName) {
        this.formValJsFileName = formValJsFileName;
    }

    public Set<String> getJsFileNameSet() {
        return jsFileNameSet;
    }

    public void setJsFileNameSet(Set<String> jsFileNameSet) {
        this.jsFileNameSet = jsFileNameSet;
    }

    public Set<String> getJsFileNameUserDefinePathSet() {
        return jsFileNameUserDefinePathSet;
    }

    public void setJsFileNameUserDefinePathSet(Set<String> jsFileNameUserDefinePathSet) {
        this.jsFileNameUserDefinePathSet = jsFileNameUserDefinePathSet;
    }

    public List<SysEnControllerField> getSysEnControllerFieldList() {
        return sysEnControllerFieldList;
    }

    public void setSysEnControllerFieldList(List<SysEnControllerField> sysEnControllerFieldList) {
        this.sysEnControllerFieldList = sysEnControllerFieldList;
    }

    public List<SysEnControllerFunction> getSysEnControllerFunctionList() {
        return sysEnControllerFunctionList;
    }

    public void setSysEnControllerFunctionList(List<SysEnControllerFunction> sysEnControllerFunctionList) {
        this.sysEnControllerFunctionList = sysEnControllerFunctionList;
    }
}
