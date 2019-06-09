package com.tgw.basic.system.constant.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import com.tgw.basic.system.constant.model.SysEnConstant;
import com.tgw.basic.system.constant.service.SysEnConstantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by zhaojg on 2016/10/16.
 */
@Controller
@RequestMapping("/sysEnConstant")
public class SysEnConstantController extends BaseController<SysEnConstant> {

    @Resource
    private SysEnConstantService sysEnConstantService;

    @PostConstruct
    public void initExpendMobile(){
        if( null!=this.getSysEnConstantService() ){
            super.initService(  this.getSysEnConstantService()  );
        }
    }

    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "SysEnConstantList" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "sysEnConstant/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "sysEnConstant/" );//控制器的请求地址

        controller.setSearchConditionColNum( 4 );
    }

    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, SysEnConstant bean)  throws PlatformException{

    }

    @Override
    public void initField( SysEnController controller )  throws PlatformException{
        String nameExtConfigs = "maxLength:50";
        String codeExtConfigs = "maxLength:50,vtype:'letterNumUnderline'";
        String namespaceExtConfigs = "maxLength:50,vtype:'letterNumUnderline'";
        String validJson = "[{name:'是',value:'true',eleId:'validYes',checked:true},{name:'否',value:'false',eleId:'validNo'}]";
        String remarkExtConfigs = "maxLength:100";

        //构造字段
        controller.addFieldId("id","ID",null);
        controller.addFieldText("name","常量名称",true,true,true,true,false,nameExtConfigs);
        controller.addFieldText("code","code",true,true,true,true,false,codeExtConfigs);
        controller.addFieldText("namespace","命名空间",true,true,true,true,false,namespaceExtConfigs);
        controller.addFieldRadioInitDataByJson("valid","是否有效",true,true,true,false,true,validJson,null,null);
        controller.addFieldTextArea("remark","备注",true,true,true,false,true,remarkExtConfigs);
        controller.addFieldDatetime("addTime","添加时间",true,false,false,false,false,null);
        controller.addFieldDatetime("updateTime","更新时间",true,false,false,false,false,null);
    }

    @Override
    public void beforeSaveBean(HttpServletRequest request, HttpServletResponse response, SysEnConstant bean) throws PlatformException{
        Date date = new Date();
        bean.setAddTime( date );
        bean.setUpdateTime( date );
    }

    @Override
    public void beforeUpdateBean(HttpServletRequest request, HttpServletResponse response,Object bean  ) throws PlatformException{
        SysEnConstant tempBean = (SysEnConstant)bean;
        tempBean.setUpdateTime( new Date() );
    }

    public SysEnConstantService getSysEnConstantService() {
        return sysEnConstantService;
    }

    public void setSysEnConstantService(SysEnConstantService sysEnConstantService) {
        this.sysEnConstantService = sysEnConstantService;
    }
}
