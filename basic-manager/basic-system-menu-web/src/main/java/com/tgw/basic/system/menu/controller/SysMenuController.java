package com.tgw.basic.system.menu.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import com.tgw.basic.system.menu.model.SysEnMenu;
import com.tgw.basic.system.menu.service.SysEnMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseController<SysEnMenu> {
	
	@Resource
	public SysEnMenuService sysEnMenuService;

    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "SysMenuList" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "sysMenu/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "sysMenu/" );//控制器的请求地址

       //controller.setPageSize( 10 );
    }

    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, SysEnMenu bean ) throws PlatformException {

        if( null!=this.getSysEnMenuService() ){
            super.initService(  this.getSysEnMenuService()  );
        }else{

        }

    }

    @Override
    public void initField( SysEnController controller ) throws PlatformException {
        String menuIdentifyExtCon = "emptyText:'必须与Controller中定义的Identifier一致',maxLength:50,vtype:'letterNumUnderline'";
        String textExtCon = "maxLength:25";
        String linkExtCon = "maxLength:200";//,vtype:'url'
        String qtipExtCon = "maxLength:50";
        String parentIdExtCon = "multiSelect:false,emptyText:'请选择父菜单'";
        String parentIdUrl = "sysMenu/loadTreeData.do?fieldMap=id:id,text:text,parentId:fk_parent_id&treeRootVal=-1&treeFlag=menuTree&resType=map&multiSelect=false";
        String orderNumExtCon = "minValue:0,maxValue:99999999";
        String leafJson = "[{name:'是',value:'true',eleId:'leafShi'},{name:'否',value:'false',eleId:'leafFou',checked:true}]";
        String expandedJson = "[{name:'是',value:'true',eleId:' expandedShi'},{name:'否',value:'false',eleId:'expandedFou',checked:true}]";
        String selfUrlJson = "[{name:'是',value:'true',eleId:'selfUrlShi',checked:true},{name:'否',value:'false',eleId:'selfUrlFou',checked:false}]";

        //构造字段
        controller.addFieldId("id","ID",null);
        controller.addFieldComboBoxTree( "fkParentId","父菜单",true,true,true,true,true,parentIdExtCon,parentIdUrl );
        controller.addFieldText("text","菜单名称",true,true,true,true,false,textExtCon);
        controller.addFieldText("menuIdentify","菜单标识",true,true,true,true,false,menuIdentifyExtCon);
        controller.addFieldText("link","URL地址",true,true,true,true,true,linkExtCon);
        controller.addFieldText("qtip","菜单提示",true,true,true,false,true,qtipExtCon);
        controller.addFieldRadioInitDataByJson("leaf","叶子节点",true,true,true,false,true,leafJson,null,null);
        controller.addFieldRadioInitDataByJson("expanded","展开",true,true,true,false,true,expandedJson,null,null);
        controller.addFieldRadioInitDataByJson("selfUrl","本项目地址",true,true,true,false,true,selfUrlJson,null,null);

        controller.addFieldNumber("orderNum","排序编码",true,true,true,false,false,orderNumExtCon);
        controller.addFieldDatetime("addTime","添加时间",true,false,false,false,false,null);
        controller.addFieldDatetime("updateTime","更新时间",true,false,false,false,false,null);
    }

    @Override
    public void beforeSaveBean(HttpServletRequest request, HttpServletResponse response, SysEnMenu bean) throws PlatformException{
        Date date = new Date();
        bean.setAddTime( date );
        bean.setUpdateTime( date );

        if( bean.getFkParentId()==null  ){
            bean.setFkParentId(-1);
        }
    }

    @Override
    public void beforeUpdateBean(HttpServletRequest request, HttpServletResponse response,Object bean  ) throws PlatformException{
        SysEnMenu tempBean = (SysEnMenu)bean;
        tempBean.setUpdateTime( new Date() );

        if( tempBean.getFkParentId()==null  ){
            tempBean.setFkParentId(-1);
        }
    }

    @Override
    public List<Map<String,Object>> loadTreeNodeDataMap(HttpServletRequest request, HttpServletResponse response, SysEnMenu bean) throws PlatformException{
        /**
         * 加载树结点需要的数据
         *
         */
        String treeFlag = request.getParameter("treeFlag");
        List<Map<String,Object>> res = null;

        if( "menuTree".equals( treeFlag ) ){//行政区划树
            res = getSysEnMenuService().queryMenuTreeMap();
        }

        return res;
    }



	public SysEnMenuService getSysEnMenuService() {
		return sysEnMenuService;
	}

	public void setSysEnMenuService(SysEnMenuService sysEnMenuService) {
		this.sysEnMenuService = sysEnMenuService;
	}
	
}
