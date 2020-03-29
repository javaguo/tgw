package com.tgw.basic.example.exampleEchart.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.example.exampleBean.model.ExampleBean;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.constant.FrameworkConstant;
import com.tgw.basic.framework.model.controller.SysEnController;
import com.tgw.basic.framework.model.controller.SysEnControllerField;
import com.tgw.basic.framework.model.form.field.SysEnFieldDate;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.tgw.basic.example.exampleBean.controller.ExampleBeanController.BASE_PATH_JS;

/**
 * Created by zhaojg on 2017/03/25
 */
@Controller
@RequestMapping("/exampleSearchModel")
public class ExampleSearchModelController extends BaseController<ExampleBean> {
    private static final Log LOG = LogFactory.getLog(ExampleSearchModelController.class);

    @Override
    public void initControllerBaseInfoSearchModel(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "ExampleSearchModel" );// 每一个列表页面的唯一身份id
        controller.setControllerBaseUrl( "exampleSearchModel/" );//控制器的请求地址
        //设置搜索区域字段列数。不设置时使用系统默认值。
        controller.setSearchConditionColNum( 4 );
    }

    @Override
    public void initSearchModel(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, ExampleBean bean) throws PlatformException{
        controller.addJsFileNameUserDefinePath(BASE_PATH_JS+"exampleEcharts/exampleSearchModel.js" );

        modelAndView.addObject(FrameworkConstant.FW_SEARCH_MODEL_SEARCH_CALLBACK_FUNCTION_NAME,"exampleSearchModelCallback");
        modelAndView.addObject(FrameworkConstant.FW_SEARCH_MODEL_FREE_AREA_ELEMENT_ID,"exampleSearchModelShowResEle");
        modelAndView.addObject(FrameworkConstant.FW_SEARCH_MODEL_SEARCH_URL,"exampleSearchModel/ajaxReq.do");
    }

    @Override
    public void initFieldSearchModel( SysEnController controller ) throws PlatformException {
        /**--------------------- 隐藏域、文本框、密码框、文本域  示例开始 -----------------------------------------------------------------*/
        /**
         *
         * 添加、编辑窗口中的显示样式相同，在各controller中分别配置，搜索区域样式已在前端页面中统一配置好。
         */
        String formTextConfigs = "labelWidth:100,width:400,emptyText:'文本提示信息',value:'初始值'";
        controller.addFieldText("formText","文本框",true,true,true,true,false,formTextConfigs);
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


        /**--------------------- 日期、时间控件 示例开始 -----------------------------------------------------------------*/
        /**
         * 添加、编辑窗口中的显示样式相同，在各controller中分别配置，搜索区域样式已在前端页面中统一配置好。
         */
        String formDateConfigs = "labelWidth:100,width:300,emptyText:'录入日期',editable:false,maxValue:'2089-12-31',maxText:'不能超过2089-12-31',minValue:'2015-03-21',minText:'最小为2015-03-21',value:'2017-01-01'";
        String formDateTimeConfigs = "labelWidth:100,width:300,emptyText:'精确时间',value:'2017-01-01 09:01:01'";
        String formDateTimeYmdhmConfigs = "labelWidth:100,width:300,emptyText:'精确到时分'";

        SysEnControllerField formDateStringField = controller.addFieldDate("formDateString","日期(String)",true,true,true,true,false,formDateConfigs);
        formDateStringField.setSearByRange( true );
        SysEnFieldDate fieldDate = (SysEnFieldDate)formDateStringField.getSysEnFieldAttr();
        fieldDate.setStartTimeForRange( "2016-03-15" );
        fieldDate.setEndTimeForRange( "2017-12-05" );

        controller.addFieldDate("formDateDate","日期(Date)",true,true,true,true,true,formDateConfigs);

        controller.addFieldDate_yMdHm("formDatetimeYmdhm","时间(时分)",true,true,true,true,true,formDateTimeYmdhmConfigs);

        controller.addFieldDatetime("formDatetimeString","时间(String)",true,true,true,true,false,formDateTimeConfigs);
        SysEnControllerField formDatetimeDateField = controller.addFieldDatetime("formDatetimeDate","时间(Date)",true,true,true,true,true,formDateTimeConfigs);
        formDatetimeDateField.setSearByRange( true );
        /**--------------------- 日期、时间控件 示例结束 -----------------------------------------------------------------*/

    }

    @RequestMapping("/ajaxReq.do")
    public ModelAndView ajaxReq(ExampleBean bean){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        jo.put("success",true);
        jo.put("msg","请求成功！");

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );

        return  modelAndView;
    }

}
