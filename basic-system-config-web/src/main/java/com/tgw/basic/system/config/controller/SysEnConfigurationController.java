package com.tgw.basic.system.config.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import com.tgw.basic.system.config.model.SysEnConfiguration;
import com.tgw.basic.system.config.service.SysEnConfigurationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaojg on 2016/10/16.
 */
@Controller
@RequestMapping("/sysEnConf")
public class SysEnConfigurationController extends BaseController<SysEnConfiguration> {

    @Resource
    private SysEnConfigurationService sysEnConfigurationService;

    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "SysEnConfList" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "sysEnConf/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "sysEnConf/" );//控制器的请求地址

    }

    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, SysEnConfiguration bean) throws PlatformException {
        if( null!=this.getSysEnConfigurationService() ){
            super.initService(  this.getSysEnConfigurationService()  );
        }
    }

    @Override
    public void initField( SysEnController controller ) throws PlatformException {
        String fkParentIdExtCon = "multiSelect:false,emptyText:'请选择父节点'";
        String fkParentIdUrl = "sysEnConf/loadTreeData.do?fieldMap=id:id,text:conf_name,parentId:fk_parent_id&treeRootVal=-1&treeFlag=sysConfTree&resType=map&multiSelect=false";
        String confNameExtConfigs = "maxLength:50";
        String confKeyExtConfigs = "maxLength:50,vtype:'letterNumUnderline'";
        String confValueExtConfigs = "maxLength:250";
        String remarkExtConfigs = "maxLength:200";

        //构造字段
        controller.addFieldId("id","ID",null);
        controller.addFieldComboBoxTree( "fkParentId","父节点",true,true,true,true,true,fkParentIdExtCon,fkParentIdUrl );
        controller.addFieldText("confName","配置名称",true,true,true,true,false,confNameExtConfigs);
        controller.addFieldText("confKey","key值",true,true,true,true,false,confKeyExtConfigs);
        controller.addFieldText("confValue","value值",true,true,true,true,false,confValueExtConfigs);
        controller.addFieldTextArea("remark","备注",true,true,true,false,true,remarkExtConfigs);
        controller.addFieldDatetime("addTime","添加时间",true,false,false,false,false,null);
        controller.addFieldDatetime("updateTime","更新时间",true,false,false,false,false,null);

        controller.addFieldViewDetail("id",null);

    }

    @Override
    public void beforeSaveBean(HttpServletRequest request, HttpServletResponse response, SysEnConfiguration bean) throws PlatformException{
        Date date = new Date();
        bean.setAddTime( date );
        bean.setUpdateTime( date );

        if( bean.getFkParentId()==null  ){
            bean.setFkParentId(-1);
        }
    }

    @Override
    public void beforeUpdateBean(HttpServletRequest request, HttpServletResponse response,Object bean  ) throws PlatformException{
        SysEnConfiguration tempBean = (SysEnConfiguration)bean;
        tempBean.setUpdateTime( new Date() );

        if( tempBean.getFkParentId()==null  ){
            tempBean.setFkParentId(-1);
        }
    }

    @Override
    public List<Map<String,Object>> loadTreeNodeDataMap(HttpServletRequest request, HttpServletResponse response, SysEnConfiguration bean) throws PlatformException{
        /**
         * 加载树结点需要的数据
         *
         */
        String treeFlag = request.getParameter("treeFlag");
        List<Map<String,Object>> res = null;

        if( "sysConfTree".equals( treeFlag ) ){
            res = getSysEnConfigurationService().queryConfTreeMap();
        }

        return res;
    }

    public SysEnConfigurationService getSysEnConfigurationService() {
        return sysEnConfigurationService;
    }

    public void setSysEnConfigurationService(SysEnConfigurationService sysEnConfigurationService) {
        this.sysEnConfigurationService = sysEnConfigurationService;
    }
}
