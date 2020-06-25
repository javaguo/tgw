package com.tgw.basic.example.exampleBeanFormVal.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.example.exampleBean.controller.ExampleBeanController;
import com.tgw.basic.example.exampleBeanFormVal.model.ExampleBeanFormVal;
import com.tgw.basic.example.exampleBeanFormVal.service.ExampleBeanFormValService;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhaojg on 2017/08/01
 */
@Controller
@RequestMapping("/exampleBeanFormVal")
public class ExampleBeanFormValController extends BaseController<ExampleBeanFormVal> {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleBeanFormValController.class);

    @Resource
    private ExampleBeanFormValService exampleBeanFormValService;

    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "ExampleBeanFormVal" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "exampleBeanFormVal/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "exampleBeanFormVal/" );//控制器的请求地址

        //此处的配置会覆盖jsp页面中默认的配置
        String addWindowConfigs = "title: '添加窗口-表单验证示例'";
        String editWindowConfigs = "title: '编辑窗口-表单验证示例'";
        controller.addWindowConfig( addWindowConfigs,editWindowConfigs,null );

        controller.setSearchConditionColNum( 4 );
    }

    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, ExampleBeanFormVal bean ) throws PlatformException {

        if( null!=this.getExampleBeanFormValService() ){
            super.initService(  this.getExampleBeanFormValService()  );
        }else{

        }


    }

    @Override
    public void initField( SysEnController controller ) throws PlatformException {
        //构造字段
        controller.addFieldId("id","ID",null);

        /***********************************************************************************************************
         * 表单验证示例，包括vtype、validator、regex三种验证。
         */


        /**
         * vtype示例
         */

        //此示例中，增、改、查询表单其它字段验证时引用了alpha字段，所以alpha字段设置为可以增、改、查
        String extConfigsAlpha = "vtype:'alpha'";
        controller.addFieldText("alpha","alpha",true,true,true,true,false,extConfigsAlpha);

        String extConfigsAlphanum = "vtype:'alphanum'";
        controller.addFieldText("alphanum","alphanum",true,true,true,false,true,extConfigsAlphanum);

        String extConfigsEmail = "vtype:'email'";
        controller.addFieldText("email","email",true,true,true,false,true,extConfigsEmail);

        String extConfigsUrl = "vtype:'url'";
        controller.addFieldText("url","url",true,true,true,false,true,extConfigsUrl);

        String extConfigsLetter = "vtype:'letter'";
        controller.addFieldText("letter","字母",true,true,true,false,true,extConfigsLetter);

        String extConfigsUpperCase = "vtype:'upperCase'";
        controller.addFieldText("upperCase","大写字母",true,true,true,false,true,extConfigsUpperCase);

        String extConfigsLowerCase = "vtype:'lowerCase'";
        controller.addFieldText("lowerCase","小写字母",true,true,true,false,true,extConfigsLowerCase);

        String extConfigsLetterNum = "vtype:'letterNum'";
        controller.addFieldText("letterNum","字母数字",true,true,true,false,true,extConfigsLetterNum);

        String extConfigsLetterNumNnderline = "vtype:'letterNumUnderline'";
        controller.addFieldText("letterNumUnderline","字母数字下划线",true,true,true,false,true,extConfigsLetterNumNnderline);

        String extConfigsChineseletterNum = "vtype:'chineseLetterNum'";
        controller.addFieldText("chineseLetterNum","汉字字母数字",true,true,true,false,true,extConfigsChineseletterNum);

        String extConfigsChineseletterNumNnderline = "vtype:'chineseLetterNumUnderline'";
        controller.addFieldText("chineseLetterNumUnderline","汉字字母数字下划线",true,true,true,false,true,extConfigsChineseletterNumNnderline);

        String extConfigsChinese = "vtype:'chinese'";
        controller.addFieldText("chinese","汉字",true,true,true,false,true,extConfigsChinese);

        String extConfigsCharacter50 = "vtype:'character50'";
        controller.addFieldText("character50","50个字符",true,true,true,false,true,extConfigsCharacter50);

        String extConfigsEmailPlatform = "vtype:'emailPlatform'";
        controller.addFieldText("emailPlatform","自定义email",true,true,true,false,true,extConfigsEmailPlatform);

        String extConfigsMobileNo = "vtype:'mobileNo'";
        controller.addFieldText("mobileNo","手机号",true,true,true,false,true,extConfigsMobileNo);

        String extConfigsPhoneNo = "vtype:'fixedPhoneNo'";
        controller.addFieldText("fixedPhoneNo","固定电话号",true,true,true,false,true,extConfigsPhoneNo);

        String extConfigsPhoneNoMobileNo = "vtype:'phoneNo'";
        controller.addFieldText("phoneNo","电话或手机号",true,true,true,false,true,extConfigsPhoneNoMobileNo);

        String extConfigsIDNumber15 = "vtype:'IDNumber15'";
        controller.addFieldText("IdNumber15","15位身份证",true,true,true,false,true,extConfigsIDNumber15);

        String extConfigsIDNumber18 = "vtype:'IDNumber18'";
        controller.addFieldText("IdNumber18","18位身份证",true,true,true,false,true,extConfigsIDNumber18);

        String extConfigsIDNumber = "vtype:'IDNumber'";
        controller.addFieldText("IdNumber","身份证",true,true,true,false,true,extConfigsIDNumber);

        String extConfigsAge = "vtype:'age'";
        controller.addFieldNumber("age","年龄",true,true,true,true,true,extConfigsAge);

        String extConfigsDateYMD = "vtype:'dateYMD'";
        controller.addFieldText("dateYmd","年月日",true,true,true,false,true,extConfigsDateYMD);

        String extConfigsQQ = "vtype:'QQ'";
        controller.addFieldText("qq","QQ",true,true,true,false,true,extConfigsQQ);

        String extConfigsPostCode = "vtype:'postCode'";
        controller.addFieldText("postCode","邮政编码",true,true,true,false,true,extConfigsPostCode);

        String extConfigsIP = "vtype:'IP'";
        controller.addFieldText("ip","IP地址",true,true,true,false,true,extConfigsIP);

        String extConfigsAccountNumber = "vtype:'accountNumber'";
        controller.addFieldText("accountNumber","账号",true,true,true,false,true,extConfigsAccountNumber);

        String extConfigsDefaultPassword = "vtype:'generalPassword'";
        controller.addFieldPassword("generalPassword","普通密码",true,true,true,true,extConfigsDefaultPassword);

        String extConfigsStrongPassword = "vtype:'strongPassword'";
        controller.addFieldPassword("strongPassword","强密码",true,true,true,true,extConfigsStrongPassword);

        /**
         * regex验证示例
         */
        String extConfigsRegex = "regex:/^[12]{1}[0-9]{1}$/,regexText:'输入值非法，正确范围：10-29'";
        controller.addFieldText("regex","自定义regex",true,true,true,true,true,extConfigsRegex);

        /**
         * validator验证示例，自定义validator验证函数。
         *
         * 提供了两种方式（下方有举例说明）：
         * 1.验证方法参数只传验证字段本身的值
         * 2.验证方法参数传字段本身的值外，还可以传其它字段。
         * (第2种方式目前有bug，生成的代码变量先后的顺序有问题，被引用的变量在引用的代码后面出现，则会报js错误。
         *          combobox控件目前就存在此问题，其它控件只要先后顺序没有问题，也可以使用。)
         *
         * 使用validator验证注意事项：
         * regex、vtype进行验证时，如果输入值为空，则不会进行正则验证。
         * 但validator不论值是否为空，都会进行正则验证，所以使用validator验证时要做好判空处理。
         */

        controller.addJsFileName("exampleBeanFormVal/exampleBeanFormVal.js" );
        controller.addJsFileNameUserDefinePath(ExampleBeanController.BASE_PATH_JS+"exampleBeanFormVal/exampleBeanFormVal.js" );
        String extConfigsTranText = "validatorFunName:'valText'";
        controller.addFieldText("valText","valText",true,true,true,true,true,null,extConfigsTranText);

        //String extConfigsTranTextParam = "validatorFunName:'valTextParam',validatorFunField:'alpha,chinese'";//validatorFunField为要验证的字段名
        String extConfigsTranTextParam = "validatorFunName:'valTextParam'";//validatorFunField为要验证的字段名
        controller.addFieldText("valTextParam","valTextParam",true,true,true,true,true,null,extConfigsTranTextParam);

        String extConfigsTranPassword = "validatorFunName:'valPassword'";
        controller.addFieldPassword("valPassword","valPassword",true,true,true,true,null,extConfigsTranPassword);

        String extConfigsTranPasswordParam = "validatorFunName:'valPasswordParam'";
        controller.addFieldPassword("valPasswordParam","valPasswordParam",true,true,true,true,null,extConfigsTranPasswordParam);

        String extConfigsTranTextAreaPassword = "validatorFunName:'valTextArea'";
        controller.addFieldTextArea("valTextArea","valTextArea",true,true,true,false,true,null,extConfigsTranTextAreaPassword);

        String extConfigsTranTextAreaParam = "validatorFunName:'valTextAreaParam'";
        controller.addFieldTextArea("valTextAreaParam","valTextAreaParam",true,true,true,false,true,null,extConfigsTranTextAreaParam);

        String extConfigsTranNumber = "validatorFunName:'valNumber'";
        controller.addFieldNumber("valNumber","valNumber",true,true,true,true,true,null,extConfigsTranNumber);

        String extConfigsTranNumberParam = "validatorFunName:'valNumberParam'";
        controller.addFieldNumber("valNumberParam","valNumberParam",true,true,true,true,true,null,extConfigsTranNumberParam);

        String extConfigsTranTag = "validatorFunName:'valTag'";
        String tagJson = "[{name:'优',value:'90'},{name:'良',value:'80'},{name:'中',value:'70'},{name:'及格',value:'60'},{name:'差',value:'50'}]";
        controller.addFieldTagByJSON( "valTag","valTag",true,true,true,false,false,tagJson,null,extConfigsTranTag );

        String extConfigsTranTagParam = "validatorFunName:'valTagParam'";
        controller.addFieldTagByJSON( "valTagParam","valTagParam",true,true,true,false,false,tagJson,null,extConfigsTranTagParam );

        String extConfigsTranDate = "validatorFunName:'valDate'";
        controller.addFieldDate("valDate","valDate",true,true,true,true,true,null,extConfigsTranDate);

        String extConfigsTranDateParam = "validatorFunName:'valDateParam'";
        controller.addFieldDate("valDateParam","valDateParam",true,true,true,true,true,null,extConfigsTranDateParam);

        String comboBoxJson = "[{name:'优',value:'90'},{name:'良',value:'80'},{name:'中',value:'70'},{name:'及格',value:'60'},{name:'差',value:'50'}]";
        String extConfigsTranComboBox = "validatorFunName:'valComboBox'";
        controller.addFieldComboBoxByJSON("valComboBox","valComboBox",true,true,true,false,false,comboBoxJson,null,extConfigsTranComboBox);

        String extConfigsComboBoxParam = "validatorFunName:'valComboBoxParam'";
        controller.addFieldComboBoxByJSON("valComboBoxParam","valComboBoxParam",true,true,true,false,false,comboBoxJson,null,extConfigsComboBoxParam);

/*        String extConfigsTranComboBoxCascade = "validatorFunName:'valComboBoxCascade'";
        controller.addFieldText("valComboBoxCascade","valComboBoxCascade",true,true,true,true,true,null,extConfigsTranComboBoxCascade);

        String extConfigsTranComboBoxCascadeParam = "validatorFunName:'valComboBoxCascade',validatorFunField:'alpha'";
        controller.addFieldText("valComboBoxCascade","valComboBoxCascade",true,true,true,true,true,null,extConfigsTranComboBoxCascadeParam);*/

        String treeUrl6 = "exampleBean/loadTreeData.do?fieldMap=id:id,text:name,parentId:parent_id&treeRootVal=-1&treeFlag=district&resType=map&multiSelect=false";
        String extConfigsTranTree = "validatorFunName:'valTree'";
        controller.addFieldComboBoxTree( "valTree","valTree",true,true,true,false,true,null,treeUrl6,extConfigsTranTree );

        String extConfigsTranTreeParam = "validatorFunName:'valTreeParam'";
        controller.addFieldComboBoxTree( "valTreeParam","valTreeParam",true,true,true,false,true,null,treeUrl6,extConfigsTranTreeParam );

        String formFileServiceConfigs1 = "savePath:'/upload/doc/',allowFileType:'doc,docx'";
        String extConfigsTranFile = "validatorFunName:'valFile'";
        controller.addFieldFile("valFile","valFile",true,true,true,true,null,formFileServiceConfigs1,extConfigsTranFile);

        String extConfigsTranFileParam = "validatorFunName:'valFileParam'";
        controller.addFieldFile("valFileParam","valFileParam",true,true,true,true,null,formFileServiceConfigs1,extConfigsTranFileParam);

        /**
         *vtype与regex可同时使用，两个都会去进行验证。
         */
        String extConfigsRegexVtype = "vtype:'age',regex:/^\\d{3}$/,regexText:'输入值非法，只能输入三位数的整数'";
        controller.addFieldText("regexVtype","regex+vtype",true,true,true,true,true,extConfigsRegexVtype);

        /**
         * regex、vtype、validator可同时使用。三个都会去进行验证。
         * 三都验证顺序：validator、vtype、regex
         */
        String extConfigsRegexVtypeValidator = "regex:/^\\d{3}$/,regexText:'输入值非法，只能输入三位数的整数',vtype:'age'";
        String extConfigsRegexVtypeValidatorTra = "validatorFunName:'valText'";
        controller.addFieldText("regexVtypeValidator","regex+vtype+validator",true,true,true,true,true,extConfigsRegexVtypeValidator,extConfigsRegexVtypeValidatorTra);



        /***********************************************************************************************************
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addTimeConfigs = "value:'"+sdf.format( new Date() )+"'";
        String updateTimeConfigs = "value:'"+sdf.format( new Date() )+"'";
        controller.addFieldDatetime("addTime","添加时间",true,true,false,false,false,addTimeConfigs);
        controller.addFieldDatetime("updateTime","更新时间",true,true,true,false,false,updateTimeConfigs);
    }

    public ExampleBeanFormValService getExampleBeanFormValService() {
        return exampleBeanFormValService;
    }

    public void setExampleBeanFormValService(ExampleBeanFormValService exampleBeanFormValService) {
        this.exampleBeanFormValService = exampleBeanFormValService;
    }
}
