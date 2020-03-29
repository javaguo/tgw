package com.tgw.basic.example.exampleBean.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.collections.PlatformCollectionsUtils;
import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.common.utils.string.PlatformStringUtils;
import com.tgw.basic.example.exampleBean.model.ExampleBean;
import com.tgw.basic.example.exampleBean.service.ExampleBeanService;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import com.tgw.basic.framework.model.controller.SysEnControllerField;
import com.tgw.basic.framework.model.controller.SysEnControllerFunction;
import com.tgw.basic.framework.model.form.field.SysEnFieldDate;
import com.tgw.basic.redis.utils.template.PlatformRedisTempUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaojg on 2017/03/25
 */
@Controller
@RequestMapping("/exampleBean")
public class ExampleBeanController extends BaseController<ExampleBean> {
    private static final Log LOG = LogFactory.getLog(ExampleBeanController.class);

    @Resource
    private ExampleBeanService exampleBeanService;
    @Autowired
    private PlatformRedisTempUtil platformRedisTempUtil;

    public static final String VIEW_EXAMPLE = VIEW_PLATFORM+"example/";
    public static final String BASE_PATH_JS = "resource/platform/js/example/";

    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "ExampleBeanList" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "exampleBean/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "exampleBean/" );//控制器的请求地址

        //设置每页条数，不设置时使用系统默认值
        controller.setPageSize( 10 );
        //设置搜索区域字段列数。不设置时使用系统默认值。
        controller.setSearchConditionColNum( 4 );
    }

    @Override
    public void beforeSearch(HttpServletRequest request, HttpServletResponse response, ExampleBean bean) throws PlatformException{
        //覆写了beforeSearch方法，则只调用覆写的beforeSearch方法

        //可手动调用父类的方法
        super.beforeSearch(request,response,bean);
    }

    @Override
    public void afterSearch(HttpServletRequest request, HttpServletResponse response, ExampleBean bean) throws PlatformException{
        //覆写了afterSearch方法，则只调用覆写的afterSearch方法

        //可手动调用父类的方法
        super.afterSearch(request,response,bean);
    }


    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, ExampleBean bean ) throws PlatformException {
        //覆写了initSearch方法，则只调用覆写的initSearch方法
        /**
         * 将具体的业务的service对象赋值给baseservice，必须的操作。
         * service层需要将具体业务的mapper赋值给BaseModelMapper
         *
         * 此操作主要解决的问题是BaseModelMapper无法注入到BaseServiceImpl中的问题。手动赋值。
         *
         * 要点：
         * 1.BaseController会调用统一的searchData()接口查询具体的业务数据。
         * 2.具体业务的mapper文件中实现searchData查询语句
         *
         *
         */
        if( null!=this.getExampleBeanService() ){
            super.initService(  this.getExampleBeanService()  );
        }else{

        }

        /**
         * initSearch方法中还可以初始化bean对象的值，供在页面上使用。
         * 暂时还没有实现
         */

        /**
         *此处的配置会覆盖jsp页面中默认的配置。
         * 此处配置也可以写在   initControllerBaseInfo()或initField()或initFunction()方法中
         */
        String addWindowConfigs = "title: '添加窗口-示例',width:800";
        String editWindowConfigs = "title: '编辑窗口-示例',width:800";
        String viewWindowConfigs = "title: '查看详情窗口-示例',width:600";
        controller.addWindowConfig( null,null,viewWindowConfigs );
    }

    @Override
    public void initField( SysEnController controller ) throws PlatformException {
        /**
         * 初始化字段说明：
         *
         * 注意事项：
         * 1.定义的变量中不要包含SavePathHidden。SavePathHidden被框架使用。用来存储上传附件的路径。
         *
         */

        //构造字段。框架基于mysql数据库，model对象的主键请使用Integer类型。前台页面请求时，id的值一定要为整数。
        controller.addFieldId("id","ID",null);

        /**--------------------- 隐藏域、文本框、密码框、文本域  示例开始 -----------------------------------------------------------------*/
        /**
         *
         * 添加、编辑窗口中的显示样式相同，在各controller中分别配置，搜索区域样式已在前端页面中统一配置好。
         */
        String formHiddenConfigs = "value:'hidden隐藏域值'";
        String formTextConfigs = "labelWidth:100,width:400,emptyText:'文本提示信息',value:'初始值'";
        String formPasswordConfigs = "labelWidth:100,width:400,emptyText:'密码提示信息',value:'123456'";
        String formTextAreaConfigs = "labelWidth:100,width:400,height:80,emptyText:'文本域内容......',maxLength:50,maxLengthText:'最长为50个字',minLength:5,minLengthText:'最小为5个字'";
        /*String formHiddenConfigs = ""; //配置为空时，则使用框架默认配置。
        String formTextConfigs = null;
        String formPasswordConfigs = null;
        String formTextAreaConfigs = null;*/

        controller.addFieldHidden( "formHidden","form隐藏域",true,true,true,formHiddenConfigs );
        controller.addFieldText("formText","文本框",true,true,true,true,false,formTextConfigs);
        controller.addFieldPassword("formPassword","密码框",true,true,true,false,formPasswordConfigs);
        controller.addFieldTextArea("formTextArea","文本域",true,true,true,false,false,formTextAreaConfigs);
        /**--------------------- 隐藏域、文本框、密码框、文本域  示例结束 -----------------------------------------------------------------*/



        /**--------------------- 树控件 示例开始 -----------------------------------------------------------------*/
        /**
         *
         * 添加、编辑窗口中的显示样式相同，在各controller中分别配置，搜索区域样式已在前端页面中统一配置好。
         */
        String formComboBoxTreeConfigs1 = "labelWidth:100,width:400,multiSelect:true,multiCascade:true,selectedIds:'A,A1,A2,A13'";
        String formComboBoxTreeConfigs2 = "labelWidth:100,width:400,multiSelect:true,multiCascade:false";
        String formComboBoxTreeConfigs3 = "labelWidth:100,width:400,multiSelect:false,selectedIds:'15'";
        String formComboBoxTreeConfigs4 = "labelWidth:100,width:400,multiSelect:true,multiCascade:true,emptyText:'请选择菜单...'";
        String formComboBoxTreeConfigs5 = "labelWidth:130,width:400,multiSelect:true,multiCascade:false";
        String formComboBoxTreeConfigs6 = "labelWidth:100,width:400,multiSelect:false,emptyText:'请选择地区'";

        /*String formComboBoxTreeConfigs1 = null;//配置为空时，则使用框架默认配置。
        String formComboBoxTreeConfigs2 = null;
        String formComboBoxTreeConfigs3 = null;
        String formComboBoxTreeConfigs4 = null;
        String formComboBoxTreeConfigs5 = null;
        String formComboBoxTreeConfigs6 = null;*/

        String treeUrl1=  BASE_PATH_JS+"exampleBean/tree/tree2.json";
        String treeUrl2 = BASE_PATH_JS+"exampleBean/tree/tree2.json";
        String treeUrl3 = "exampleBean/loadTreeData.do?fieldMap=id:id,text:name,parentId:parent_id&treeRootVal=-1&treeFlag=district&resType=map&multiSelect=false";
        String treeUrl4 = "exampleBean/loadTreeData.do?fieldMap=id:id,text:name,parentId:parent_id&treeRootVal=-1&treeFlag=district&resType=map&multiSelect=true";
        String treeUrl5 = "exampleBean/loadTreeData.do?fieldMap=id:id,text:name,parentId:parent_id&treeRootVal=-1&treeFlag=district&resType=map&multiSelect=true";
        String treeUrl6 = "exampleBean/loadTreeData.do?fieldMap=id:id,text:name,parentId:parent_id&treeRootVal=-1&treeFlag=district&resType=map&multiSelect=false";

        /**
         * 目前遇到一个问题，formComboBoxTree1与formComboBoxTree2这两个字段会引起searchData请求的返回值乱码。尚未发现原因。此问题不太确定。
         */
        controller.addFieldComboBoxTree( "formComboBoxTree1","树(多选级联)",true,true,true,false,false,formComboBoxTreeConfigs1,treeUrl1 );
        controller.addFieldComboBoxTree( "formComboBoxTree2","树(多选不级联)",true,true,true,false,true,formComboBoxTreeConfigs2,treeUrl2 );
        controller.addFieldComboBoxTree( "formComboBoxTree3","树(单选)",true,true,true,false,true,formComboBoxTreeConfigs3,treeUrl3 );
        controller.addFieldComboBoxTree( "formComboBoxTree4","行政区划(多选级联)",true,true,true,false,true,formComboBoxTreeConfigs4,treeUrl4 );
        /**
         * 在某些情况下，可以单独设置一个字段，只用来作条件查询，以字段formComboBoxTree5为例。
         * formComboBoxTree5与formComboBoxTree5Search其实对应数据库中的同一个字段，
         * formComboBoxTree5只用来作添加、编辑、列表展示。formComboBoxTree5Search只用来作条件查询，可以对此条件进行某些特殊处理。
         * 需要在实体类中定义变量formComboBoxTree5Search。
         */
        controller.addFieldComboBoxTree( "formComboBoxTree5","行政区划(多选不级联)",true,true,true,false,true,formComboBoxTreeConfigs5,treeUrl5 );
        controller.addFieldComboBoxTree( "formComboBoxTree5Search","行政区划(多选不级联)",true,false,false,true,true,formComboBoxTreeConfigs5,treeUrl5 ).setShowList(false);
        controller.addFieldComboBoxTree( "formComboBoxTree6","行政区划(单选)",true,true,true,true,true,formComboBoxTreeConfigs6,treeUrl6 );
        /**--------------------- 树控件 示例结束 -----------------------------------------------------------------*/



        /**--------------------- 下拉框控件 示例开始 -----------------------------------------------------------------*/
        /**
         * 添加、编辑窗口、搜索区域中的显示样式相同，在各controller中分别配置。
         */
        String formComBoxConfigs = "labelWidth:100,emptyText:'选择类型',width:250,value:'90'";
        String formComBoxSqlConfigs = "labelWidth:100,width:250,listConfig:{emptyText:'默认提示'}";
        String formComboboxGroup1Configs = "labelWidth:100,width:450";
        String formComboboxGroup1ComConfigs = "emptyText:'请选择',width:150";
        String formComboboxGroup2Configs = "labelWidth:100,width:520";
        String formComboboxGroup2ComConfigs = "emptyText:'请选择...',width:120";
        /*String formComBoxConfigs = null;//配置为空时，则使用框架默认配置。
        String formComBoxSqlConfigs = null;
        String formComboboxGroup1Configs = null;
        String formComboboxGroup1ComConfigs = null;
        String formComboboxGroup2Configs = null;
        String formComboboxGroup2ComConfigs = null;*/

        String comboBoxJson = "[{name:'优',value:'90'},{name:'良',value:'80'},{name:'中',value:'70'},{name:'及格',value:'60'},{name:'差',value:'50'}]";
        String[] formComboboxGroup1FieldLabel = new String[] {"省","市"};
        String[] formComboboxGroup1Name = new String[] {"formComboBoxCascadeA","formComboBoxCascadeB"};
        String[] formComboboxGroup1Flag = new String[] {"loadDistrict","loadDistrict"};
        String[] formComboboxGroup1Val =  new String[] {"",""};//级联框初始化值
        String[] formComboboxGroup2FieldLabel = new String[] {"省（直辖市）","市（区）","县"};
        String[] formComboboxGroup2Name = new String[] {"formComboBoxCascade1","formComboBoxCascade2","formComboBoxCascade3"};
        String[] formComboboxGroup2Flag = new String[] {"loadDistrict","loadDistrict","loadDistrict"};
        String[] formComboboxGroup2Val = new String[] {"140000","140200","140222"};

        controller.addFieldComboBoxByJSON("formComboBoxJson","下拉框(json)",true,true,true,true,false,comboBoxJson,formComBoxConfigs);
        controller.addFieldComboBoxBySQL("formComboBoxSql","下拉框(sql)",true,true,true,true,true,"loadDistrict","-1",formComBoxSqlConfigs);
        //级联框组名与每个下拉框的名称不能相同。级联框的组名自己定义，没有特殊要求，同一个controller中级联框组名不要重复就可以。
        controller.addFieldComboBoxCascadeBySQL("二级级联",true,true,true,true,true,"formComboboxGroup1","140000",formComboboxGroup1FieldLabel,formComboboxGroup1Name,formComboboxGroup1Flag,formComboboxGroup1Configs,formComboboxGroup1ComConfigs);
        controller.addFieldComboBoxCascadeBySQL("三级级联",true,true,true,true,false,"formComboboxGroup2","-1",formComboboxGroup2FieldLabel,formComboboxGroup2Name,formComboboxGroup2Flag,formComboboxGroup2Configs,formComboboxGroup2ComConfigs,formComboboxGroup2Val);
        /**--------------------- 下拉框控件 示例结束 -----------------------------------------------------------------*/



        /**--------------------- 数字控件 示例开始 -----------------------------------------------------------------*/
        /**
         * 对应java各数据类型
         *
         * 基本类型的数据目前提交表单时还存在问题。当值为空的时候，表单提交报400错误。
         * 所以项目中定义数据变量时使用包装类型
         *
         * 添加、编辑窗口中的显示样式相同，在各controller中分别配置，搜索区域样式已在前端页面中统一配置好。
         */
        String formNumberIntConfigs = "labelWidth:100,width:300,emptyText:'整数',allowDecimals:false,maxValue:20,maxText:'最大为20',minValue:6,minText:'最小为6'";
        String formNumberDoubleConfigs = "labelWidth:100,width:300,emptyText:'小数',maxValue:999.9,maxText:'最大为999.9',minValue:0.1,minText:'最小为0.1',step:100,value:'109.58'";
        String formDecimalConfigs = "labelWidth:100,width:300,emptyText:'得分',maxValue:99999.9,maxText:'最大为99999.9',minValue:0.1,minText:'最小为0.1',step:100";
        String formRadioGroupConfigsBoolean = "labelWidth:100,width:400";
        String formRadioConfigsBoolean = "width:80";

        /*String formNumberIntConfigs = null;//配置为空时，则使用框架默认配置。
        String formNumberDoubleConfigs = null;
        String formDecimalConfigs = null;
        String formRadioGroupConfigsBoolean = null;
        String formRadioConfigsBoolean = null;*/

        /*controller.addFieldNumber("formNumberShortBase","short",true,true,true,false,true,formNumberIntConfigs);
        controller.addFieldNumber("formNumberIntBase","int",true,true,true,false,false,formNumberIntConfigs);
        controller.addFieldNumber("formNumberLongBase","long",true,true,true,false,true,formNumberIntConfigs);
        controller.addFieldNumber("formNumberFloatBase","float",true,true,true,false,true,formNumberDoubleConfigs);
        controller.addFieldNumber("formNumberDoubleBase","double",true,true,true,false,true,formNumberDoubleConfigs);
        controller.addFieldRadioInitDataByJson("formBooleanBase","boolean",true,true,false,false,false,booleanJson,formRadioGroupConfigs,formRadioConfigs);*/

        SysEnControllerField fieldFormNumberShort = controller.addFieldNumber("formNumberShort","Short",true,true,true,true,true,formNumberIntConfigs);
        fieldFormNumberShort.setSearByRange( true );
        controller.addFieldNumber("formNumberInteger","Integer",true,true,true,true,true,formNumberIntConfigs);
        SysEnControllerField fieldFormNumberLong = controller.addFieldNumber("formNumberLong","Long",true,true,true,false,true,formNumberIntConfigs);
        fieldFormNumberLong.setSearByRange( true );
        controller.addFieldNumber("formNumberFloat","Float",true,true,true,false,true,formNumberDoubleConfigs);
        controller.addFieldNumber("formNumberDouble","Double",true,true,true,true,true,formNumberDoubleConfigs);

        //类中的变量名为formBoolean
        String booleanJson = "[{name:'是',value:'true',eleId:'formBooleanShi',checked:true},{name:'否',value:'false',eleId:'formBooleanFou'}]";
        controller.addFieldRadioInitDataByJson("formBoolean","Boolean",true,true,true,false,true,booleanJson,formRadioGroupConfigsBoolean,formRadioConfigsBoolean);
        //类中的变量名为isFormBooleanFlag。Boolean类型的变量自动生成的get、set方法为getFormBooleanFlag()、setFormBooleanFlag()。去掉了is。
        //在此处名称使用formBooleanFlag，去掉开头的is，这样添加、编辑、查看详情、条件搜索才可以正常使用。
        //查询sql语句使用方法参考示例对应的sql语句。
        //基本类型的boolean自动生成get、set的方法与Boolean自动生成get、set方法规则不同。在框架中的使用规则也相同。目前框架不支持使用基本类型的boolean。
        String isFormBooleanFlagJson = "[{name:'是',value:'true',eleId:'isFormBooleanShi',checked:true},{name:'否',value:'false',eleId:'isFormBooleanFou'}]";
        controller.addFieldRadioInitDataByJson("formBooleanFlag","formBooleanFlag",true,true,true,true,true,isFormBooleanFlagJson,formRadioGroupConfigsBoolean,formRadioConfigsBoolean);
        /**--------------------- 数字控件 示例结束 -----------------------------------------------------------------*/


        /**--------------------- Tag控件 示例开始 -----------------------------------------------------------------*/
        /**
         * Tag控件，ext的Tag控件继承自combobox
         *
         *目前存在的bug，点击tag控件后，总是要回跳到formPanel顶部。radioGroup与checkGroup也存在相同的问题。
         *
         * 添加、编辑窗口中的显示样式相同，在各controller中分别配置，搜索区域样式已在前端页面中统一配置好。
         */
        String formTagJsonConfigs = "";
        String formTagSqlConfigs = "value:'130000,140000'";
        /*String formTagJsonConfigs = null;//配置为空时，则使用框架默认配置。
        String formTagSqlConfigs = null;*/
        String tagJson = "[{name:'优',value:'90'},{name:'良',value:'80'},{name:'中',value:'70'},{name:'及格',value:'60'},{name:'差',value:'50'}]";

        controller.addFieldTagByJSON( "formTagJson","tag控件(json)",true,true,true,false,false,tagJson,formTagJsonConfigs );
        //controller.addFieldTagBySQL( "formTagSql","tag控件(sql)",true,true,true,true,true,"loadTag",null,formTagSqlConfigs );//禁用
        /**--------------------- Tag控件 示例结束 -----------------------------------------------------------------*/



        /**--------------------- 日期、时间控件 示例开始 -----------------------------------------------------------------*/
        /**
         * 添加、编辑窗口中的显示样式相同，在各controller中分别配置，搜索区域样式已在前端页面中统一配置好。
         */
        String formDateConfigs = "labelWidth:100,width:300,emptyText:'录入日期',editable:false,maxValue:'2089-12-31',maxText:'不能超过2089-12-31',minValue:'2015-03-21',minText:'最小为2015-03-21',value:'2017-01-01'";
        String formDateTimeConfigs = "labelWidth:100,width:300,emptyText:'精确时间',value:'2018-01-01 09:01:01'";
        String formDateTimeYmdhmConfigs = "labelWidth:100,width:300,emptyText:'精确到时分'";
        /*String formDateConfigs = null;//配置为空时，则使用框架默认配置。
        String formDateTimeConfigs = null;*/

        SysEnControllerField formDateStringField = controller.addFieldDate("formDateString","日期(String)",true,true,true,true,false,formDateConfigs);
        formDateStringField.setSearByRange( true );
        SysEnFieldDate fieldDate = (SysEnFieldDate)formDateStringField.getSysEnFieldAttr();
        fieldDate.setStartTimeForRange( "2016-03-15" );
        fieldDate.setEndTimeForRange( "2099-12-05" );

        controller.addFieldDate("formDateDate","日期(Date)",true,true,true,true,true,formDateConfigs);

        controller.addFieldDate_yMdHm("formDatetimeYmdhm","时间(时分)",true,true,true,true,true,formDateTimeYmdhmConfigs);

        controller.addFieldDatetime("formDatetimeString","时间(String)",true,true,true,true,false,formDateTimeConfigs);
        SysEnControllerField formDatetimeDateField = controller.addFieldDatetime("formDatetimeDate","时间(Date)",true,true,true,true,true,formDateTimeConfigs);
        formDatetimeDateField.setSearByRange( true );
        /**--------------------- 日期、时间控件 示例结束 -----------------------------------------------------------------*/



        /**--------------------- 单选及多选控件 示例开始 -----------------------------------------------------------------*/
        /**
         * 添加、编辑窗口、搜索区域中的显示样式相同，在各controller中分别配置。
        */
        String formRadioGroupConfigs = "labelWidth:100,width:300";
        String formRadioConfigs = "width:60";//是否初始选中checked，不要在此处配置中设置，此处设置控制所有的Radio；checkBox同理。
        String formCheckboxGroupConfigs = "labelWidth:100,width:300";
        String formCheckboxConfigs = "width:60";
        /*String formRadioGroupConfigs = null;//配置为空时，则使用框架默认配置。
        String formRadioConfigs = null;//是否初始选中checked，不要在此处配置中设置，此处设置控制所有的Radio；checkBox同理。
        String formCheckboxGroupConfigs = "";
        String formCheckboxConfigs = "";*/

        //eleId为自定义的属性，非extjs的属性.框架使用生成页面需要使用。
        String radioJson = "[{name:'优秀',value:'90',eleId:'formRadioYX',checked:true},{name:'良好',value:'80',eleId:'formRadioLH'},{name:'中等',value:'70',eleId:'formRadioZD'}]";
        controller.addFieldRadioInitDataByJson("formRadio","单选",true,true,true,true,true,radioJson,formRadioGroupConfigs,formRadioConfigs);

        String checkboxJson = "[{name:'读书',value:'readbook',eleId:'DS',checked:true},{name:'跑步',value:'running',eleId:'PB',checked:true},{name:'游泳',value:'swimming',eleId:'YY'}]";
        controller.addFieldCheckboxInitDataByJson("formCheckbox","多选",true,true,true,true,false,checkboxJson,formCheckboxGroupConfigs,formCheckboxConfigs);
        /**--------------------- 单选及多选控件 示例结束 -----------------------------------------------------------------*/



        /**--------------------- 富文本编辑器控件 示例开始 -----------------------------------------------------------------*/
        /**
         * 富文本编辑哭目前不知道如何设置emptyText提示信息。
         * htmlEditor没有继承Ext.form.field.Base
         */
        String formHtmlEditorConfigs = "labelWidth:100,width:400,height:200,value:'富文本编辑器很强大！'";
        //String formHtmlEditorConfigs = null;//配置为空时，则使用框架默认配置。

        controller.addFieldHtmlEditor("formHtmlEditor","编辑器",true,true,true,false,formHtmlEditorConfigs);
        /**--------------------- 富文本编辑器控件 示例结束 -----------------------------------------------------------------*/



        /**--------------------- 附件控件 示例开始 -----------------------------------------------------------------*/
        /**
         * extjs会自动判断提交的表单是否包含有附件（判断表单中是否有inputType="file"类型的表单元素，与是否选择了文件无关）
         * ，以此决定是否使用enctype="multipart/form-data"提交表单
         */
        String formFileConfigs = "labelWidth:100,width:400,emptyText:'请选择附件...'";
//        String formFileConfigs = null;//配置为空时，则使用框架默认配置。
        /**
         * 上传附件的serviceConfigs为必须项，savePath和allowFileType必须配置。
         * 以下格式的路径都可以，框架会自动处理修正。例：/upload/pic/,/upload/pic,upload/pic/。
         */
        String formFileServiceConfigs1 = "savePath:'/upload/doc/',allowFileType:'doc,docx'";
        String formFileServiceConfigs2 = "savePath:'/upload/txt',allowFileType:'txt'";
        String formFileServiceConfigs3 = "savePath:'upload/pdf',allowFileType:'pdf'";

        controller.addFieldFile("formFile1","附件1",true,true,true,true,formFileConfigs,formFileServiceConfigs1);
        controller.addFieldFile("formFile2","附件2",true,true,true,true,formFileConfigs,formFileServiceConfigs2);
        controller.addFieldFile("formFile3","附件3",true,true,true,true,formFileConfigs,formFileServiceConfigs3);
        /**--------------------- 附件控件 示例结束 -----------------------------------------------------------------*/



        /**--------------------- 面板控件 示例开始 -----------------------------------------------------------------*/
        String formDisplayConfigs = "labelWidth:100,width:400,value:'管理员',submitValue:true";
//        String formDisplayConfigs = null;//配置为空时，则使用框架默认配置。
        StringBuffer ins = new StringBuffer();//功能说明
        ins.append("此字段对Display控件进行了扩展</br>");
        ins.append("此字段为白板类型字段，只用于在添加、编辑页面中显示提示信息。</br>");
        ins.append("提示信息可以是简单的文字，也可以是html。</br>");
        ins.append("可以设置display控件属性修改此字段样式。</br>");
        ins.append("白板类型字段不需要在model中设置相应字段。");
        String instructionsExtConfigs = "value:'"+ins.toString()+"'";

        controller.addFieldDisplay("formDisplay","form面板",true,true,false,false,formDisplayConfigs);
        controller.addFieldWhiteBoard("instructions","白板字段",true,true,true,instructionsExtConfigs);
        /**--------------------- 面板控件 示例结束 -----------------------------------------------------------------*/


        /**--------------------- 添加时间、更新时间固定字段 示例开始 -----------------------------------------------------------------*/
        controller.addFieldDatetime("addTime","添加时间",true,false,false,false,false,null);
        controller.addFieldDatetime("updateTime","更新时间",true,false,false,false,false,null);
        /**--------------------- 添加时间、更新时间固定字段 示例结束 -----------------------------------------------------------------*/



        /**--------------------- 可操作的列表字段 示例开始 -----------------------------------------------------------------*/
        //设置自定义js函数所在的js文件；可以添加多个js文件。
        controller.addJsFileNameUserDefinePath(BASE_PATH_JS+"exampleBean/fieldUserDefineOpe.js");

        controller.addFieldViewDetail("id",null);
        controller.addFieldSingleBaseAjaxReq( "exampleBeanOpeSingDataAjaxReq","基本ajax异步请求","exampleBean/exampleBeanOpeSingDataAjaxReq.do","id",null );
        controller.addFieldSingleDelete("id",null);
        /**
         * reqUrl及param可为空
         *
         * 自定义的js函数。
         * js函数的参数顺序参考fieldUserDefineOpe.js
         */
        controller.addFieldUserDefineOperate( "exampleBeanUserDefineOpe1","自定义操作(带参)","exampleBeanUserDefineOpe1","exampleBean/exampleBeanUserDefineOpe.do","id,formText,formNumberInteger",null );
        controller.addFieldUserDefineOperate( "exampleBeanUserDefineOpe2","自定义操作(不带参)","exampleBeanUserDefineOpe2",null,null,null );

        //idFieldName可为空
        controller.addFieldOpenNewTab( "openNewTab1","打开新tab(带参，自定义页面)","exampleBean/openNewTab.do","打开新tab(带参，自定义页面)","id",null );
        controller.addFieldOpenNewTab( "openNewTab2","打开新tab(不带参，自定义页面)","exampleBean/openNewTab.do","打开新tab(不带参，自定义页面)","",null );
        controller.addFieldOpenNewTabList( "openNewTab3","打开新tab(框架列表页面)","exampleBeanFormVal/search.do","打开新tab(框架列表页面)","id","ExampleBeanFormVal",null );
        //idFieldName可为空
        controller.addFieldOpenNewBrowserWindow( "openNewBrowserWindow1","打开浏览器窗口(带参)","http://www.baidu.com","id",null );
        controller.addFieldOpenNewBrowserWindow( "openNewBrowserWindow2","打开浏览器窗口(不带参)","http://www.baidu.com",null,null );
        /**--------------------- 可操作的列表字段 示例结束 -----------------------------------------------------------------*/
    }

    @Override
    public void initFunction(SysEnController controller) throws PlatformException {
        controller.addFunctionBaseAjax("menu1","基本ajax请求(需要勾选数据)","exampleBean/ajaxReq.do",true,"Application",1);
        controller.addFunctionBaseAjaxIndepe("menu1_a","基本ajax请求(不用勾选数据)","exampleBean/ajaxReq.do",true,"Application",1);

        String updateFields = "formText,formPassword,formTextArea,formNumberInteger,formDateString,formDatetimeDate";
        SysEnControllerFunction ajaxUpdate = controller.addFunctionAjaxUpdateFields("menu2","修改多个字段的值","exampleBean/menuAjaxUpdate.do",false,"Applicationedit",2,controller,updateFields);
        ajaxUpdate.setAjaxUpdateWindowConfigs( "title: '修改字段窗口-示例'" );

        controller.addFunctionAjaxUpdateFields("menu3","修改Double值","exampleBean/menuAjaxUpdate.do",true,"Applicationedit",3,controller,"formNumberDouble");
        controller.addFunctionAjaxUpdateFields("menu4","修改TextArea值","exampleBean/menuAjaxUpdate.do",false,"Applicationgo",4,controller,"formTextArea");

        controller.addJsFileNameUserDefinePath( BASE_PATH_JS+"exampleBean/menuUserDefineOpe.js" );
        controller.addFunctionUserDefineOperate("menu5","自定义操作","menuUserDefineOpe","Application",5);

        controller.addFunctionBaseAjaxIndepe("exam_redis1","redis测试","exampleBean//ajaxRedisTest.do",true,"Application",6);

        StringBuilder strIns = new StringBuilder();
        strIns.append("    此列表页面是一个表单控件示例。");
        strIns.append("此列表页面是一个表单控件示例。");
        strIns.append("此列表页面是一个表单控件示例。");
        strIns.append("此列表页面是一个表单控件示例。");
        controller.addFunctionInstructions("instructions1","功能说明","Zoom",10,strIns.toString());
    }

    /**
     *具体的业务controller可以覆写BaseController的方法，
     * 以此来自己实现save或update等操作。
     */
   /* @RequestMapping("/save.do")
    public ModelAndView save( HttpServletRequest request, HttpServletResponse response, ExampleBean bean ){
        this.beforeSave();

        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        try{

            jo.put("success",true);
            jo.put("msg","保存成功（子类覆写的save方法）！");
        }catch( PlatformException e){
            jo.put("success",false);
            jo.put("msg","保存失败（子类覆写的save方法）！"+e.getMsg() );
        }catch (Exception e){
            e.printStackTrace();
            jo.put("success",false);
            jo.put("msg","保存失败（子类覆写的save方法），发生异常！");
        }

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );

        this.afterSave();
        return  modelAndView;
    }*/

    @Override
    public void beforeSaveBean(HttpServletRequest request, HttpServletResponse response, ExampleBean bean) throws PlatformException{
        Date date = new Date();
        bean.setAddTime( date );
        bean.setUpdateTime( date );
    }

    @Override
    public void beforeUpdateBean(HttpServletRequest request, HttpServletResponse response,Object bean  ) throws PlatformException{
        ExampleBean tempBean = (ExampleBean)bean;
        tempBean.setUpdateTime( new Date() );
    }

    @Override
    public void beforeMenuAjaxUpdateBean(HttpServletRequest request, HttpServletResponse response, Object bean  ) throws PlatformException{
        ExampleBean tempBean = (ExampleBean)bean;
        tempBean.setUpdateTime( new Date() );
    }

    @Override
    public List dealListQueryResult(HttpServletRequest request, HttpServletResponse response, ExampleBean bean, List dataList) throws PlatformException{
        LOG.info("可以在具体业务的controller中对数据库的查询结果进行处理。");
        /**
         *具体业务controller中可以覆写dealSearchData方法，可以对数据库的查询结果进行加工处理。
         *
         * 本示例中，将查询结果的某些列的值进行了处理。将ids转为names。查询列表中最后展示names。
         */

        Map<String,Object> formBooleanDataMap =getFormBooleanDataMap();
        Map<String,Object> formTagJson =getFormTagDataMap();
        Map<String,Object> formRadioDataMap =getFormRadioDataMap();
        Map<String,Object> formCheckboxDataMap =getFormCheckboxDataMap();

        List<Map<String, Object>> allDisRes = this.getExampleBeanService().queryAllDistrict();
        Map<String,Object> disMap = PlatformCollectionsUtils.convertListMapToMap( allDisRes,"id","name" );

        for( int i=0;i<dataList.size();i++ ){
            HashMap<String,Object> map = (HashMap<String,Object>)dataList.get(i);

           if( map.get("formBoolean")!=null ){
                map.put("formBoolean", PlatformStringUtils.strKeyToName( map.get("formBoolean").toString(),formBooleanDataMap ));
            }

            if( map.get("formBooleanFlag")!=null ){
                map.put("formBooleanFlag", PlatformStringUtils.strKeyToName( map.get("formBooleanFlag").toString(),formBooleanDataMap ));
            }

            if( map.get("formTagSql")!=null ){
                map.put("formTagSql", PlatformStringUtils.strKeyToName( map.get("formTagSql").toString(),disMap ));
            }

            if( map.get("formRadio")!=null ){
                map.put("formRadio", PlatformStringUtils.strKeyToName( map.get("formRadio").toString(),formRadioDataMap ));
            }

            if( map.get("formCheckbox")!=null ){
                map.put("formCheckbox", PlatformStringUtils.strKeyToName( map.get("formCheckbox").toString(),formCheckboxDataMap ));
            }

            if( map.get("formComboBoxTree4")!=null ){
                map.put("formComboBoxTree4", PlatformStringUtils.strKeyToName( map.get("formComboBoxTree4").toString(),disMap ));
            }

            if( map.get("formComboBoxTree5")!=null ){
                map.put("formComboBoxTree5", PlatformStringUtils.strKeyToName( map.get("formComboBoxTree5").toString(),disMap ));
            }

            if( map.get("formComboBoxTree6")!=null ){
                map.put("formComboBoxTree6", PlatformStringUtils.strKeyToName( map.get("formComboBoxTree6").toString(),disMap ));
            }

            /**
             * 把结果集中的某个字段值移除掉
             */
            /*map.remove("formHidden");
            map.remove("formText");
            map.remove("formTextArea");
            map.remove("formNumberShort");
            map.remove("formNumberInteger");
            map.remove("formNumberLong");
            map.remove("formNumberFloat");
            map.remove("formNumberDouble");
            map.remove("formBoolean");
            map.remove("formTagJson");
            map.remove("formTagSql");
            map.remove("formDateString");
            map.remove("formDateDate");
            map.remove("formDatetimeString");
            map.remove("formDatetimeDate");
            map.remove("formRadio");
            map.remove("formCheckbox");
            map.remove("formComboBoxJson");
            map.remove("formComboBoxSql");
            map.remove("formComboBoxCascadeA");
            map.remove("formComboBoxCascadeB");
            map.remove("formComboBoxCascade1");
            map.remove("formComboBoxCascade2");
            map.remove("formComboBoxCascade3");*/

            /*map.remove("formComboBoxTree1");
            map.remove("formComboBoxTree2");
            map.remove("formComboBoxTree3");*/
            /*map.remove("formComboBoxTree4");
            map.remove("formComboBoxTree5");
            map.remove("formComboBoxTree6");*/

           /* map.remove("formHtmlEditor");
            map.remove("formFile1OrigFileName");
            map.remove("formFile2OrigFileName");
            map.remove("formFile3OrigFileName");
            map.remove("formDisplay");
            map.remove("insertTime");
            map.remove("updateTime");*/

            dataList.set(i,map);
        }
        return dataList;
    }

    @RequestMapping("/ajaxReq.do")
    public ModelAndView ajaxReq(){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        jo.put("success",true);
        jo.put("msg","菜单按钮ajax异步操作成功！");

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );

        return  modelAndView;
    }

    private void testLog(){
       /* Logger.debug ( Object message ) ;
        Logger.info ( Object message ) ;
        Logger.warn ( Object message ) ;
        Logger.error ( Object message ) ;*/

        LOG.info("测试info级别记录信息");
        try {
            int a=10,b=0;
            int c = a/b;
        }catch (Exception e){
            LOG.info("测试info级别记录异常信息",e);
        }

        try {
            throw new PlatformException("自定义异常");
        }catch (Exception e){
            LOG.info("测试info级别记录PlatformException异常信息",e);
        }


        LOG.warn("测试info级别记录信息");
        try {
            int a=10,b=0;
            int c = a/b;
        }catch (Exception e){
            LOG.warn("测试info级别记录异常信息",e);
        }

        try {
            throw new PlatformException("自定义异常");
        }catch (Exception e){
            LOG.warn("测试info级别记录PlatformException异常信息",e);
        }
    }

    @RequestMapping("/exampleBeanOpeSingDataAjaxReq.do")
    public ModelAndView exampleBeanOpeSingDataAjaxReq(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        String id = request.getParameter("id");

        LOG.info("请求参数：id-->"+id);

        jo.put("success",true);
        jo.put("msg","单条数据ajax异步操作成功！");

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );

        return  modelAndView;
    }


    @RequestMapping("/exampleBeanUserDefineOpe.do")
    public ModelAndView exampleBeanUserDefineOpe(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        String id = request.getParameter("id");
        String formText = request.getParameter("formText");
        String formNumberInteger = request.getParameter("formNumberInteger");

        LOG.info("请求参数：id-->"+id+"  formText-->"+formText+"  formNumberInteger-->"+formNumberInteger);

        jo.put("success",true);
        jo.put("msg","单条数据自定义方法操作成功！");

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );

        return  modelAndView;
    }

    @RequestMapping("/openNewTab.do")
    public ModelAndView openNewTab(HttpServletRequest request, HttpServletResponse responsen){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(VIEW_EXAMPLE +"exampleBean/openNewTab");
        return  modelAndView;
    }

    @RequestMapping("/ajaxRedisTest.do")
    public ModelAndView ajaxRedisTest(){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        Date d = new Date();
        String t = sdf.format(d);
//        platformRedisTempUtil.delete("string");
        Date startDate = new Date();
        int total = 50*10000;
        for(int i=1;i<total;i++){
            platformRedisTempUtil.set("string_"+t+"_"+i, RandomStringUtils.randomAlphanumeric(10));
        }
        Date endDate = new Date();
        jo.put("success",true);
        String time = "用时"+ (endDate.getTime()-startDate.getTime())/1000+"秒"+((endDate.getTime()-startDate.getTime())%1000)+"毫秒！";
        LOG.info("");
        LOG.info("redis："+time);
        jo.put("msg","执行redis测试完毕！"+time);

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );

        return  modelAndView;
    }

    private Map<String,Object> getFormBooleanDataMap(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1","是");
        map.put("0","否");

        return map;
    }

    private Map<String,Object> getFormTagDataMap(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("90","优");
        map.put("80","良");
        map.put("70","中");
        map.put("60","及格");
        map.put("50","差");

        return map;
    }

    private Map<String,Object> getFormRadioDataMap(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("90","优秀");
        map.put("80","良好");
        map.put("70","中等");

        return map;
    }

    private Map<String,Object> getFormCheckboxDataMap(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("readbook","读书");
        map.put("running","跑步");
        map.put("swimming","游泳");

        return map;
    }

    @Override
    public List<Map<String,Object>> loadComboBoxDataMap(HttpServletRequest request, HttpServletResponse response, ExampleBean bean, String parentId) throws PlatformException{
        /**
         * 查询下拉框数据
         *
         * Controller中所有获取下拉框数据的方法都在此方法中实现
         */
        String comboBoxFlag = request.getParameter("comboBoxFlag");
        List<Map<String,Object>> res = null;

        /*if( "loadMenu".equals( comboBoxFlag ) ){
            res = this.getExampleBeanService().queryMenuComboBoxMap( parentId );
        }else */if( "loadDistrict".equals( comboBoxFlag ) ){
            res = this.getExampleBeanService().queryDistrictComboBoxMap( parentId );
        }else if("loadTag".equals( comboBoxFlag )){
            res = this.getExampleBeanService().queryDistrictComboBoxMap( parentId );
        }

        return res;
    }

    @Override
    public List<Map<String,Object>> loadTreeNodeDataMap(HttpServletRequest request, HttpServletResponse response, ExampleBean bean) throws PlatformException{
        /**
         * 加载树结点需要的数据
         *
         * Controller中所有获取树节点数据的方法都在此方法中实现。
         * 除非自定义了url
         */
        String treeFlag = request.getParameter("treeFlag");
        List<Map<String,Object>> res = null;

        if( "district".equals( treeFlag ) ){//行政区划树
            res = getExampleBeanService().queryDistrictTreeMap();
        }/*else if( "menu".equals( treeFlag ) ){//菜单树
            res = getExampleBeanService().queryMenuTreeMap();
        }*/
        else{
            throw new PlatformException("获取树节点信息时没有找到匹配的查询方法！");
        }

        return res;
    }


    public ExampleBeanService getExampleBeanService() {
        return exampleBeanService;
    }

    public void setExampleBeanService(ExampleBeanService exampleBeanService) {
        this.exampleBeanService = exampleBeanService;
    }

    public PlatformRedisTempUtil getPlatformRedisTempUtil() {
        return platformRedisTempUtil;
    }

    public void setPlatformRedisTempUtil(PlatformRedisTempUtil platformRedisTempUtil) {
        this.platformRedisTempUtil = platformRedisTempUtil;
    }
}
