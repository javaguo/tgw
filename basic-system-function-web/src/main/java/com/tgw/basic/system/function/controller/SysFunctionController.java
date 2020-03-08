package com.tgw.basic.system.function.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import com.tgw.basic.system.function.model.SysEnFunction;
import com.tgw.basic.system.function.service.SysEnFunctionService;
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
@RequestMapping("/sysFunction")
public class SysFunctionController extends BaseController<SysEnFunction> {
	
	@Resource
	public SysEnFunctionService sysEnFunctionService;

    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "SysFunctionList" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "sysFunction/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "sysFunction/" );//控制器的请求地址

        controller.setSearchConditionColNum( 4 );
    }

    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, SysEnFunction bean )  throws PlatformException{

        if( null!=this.getSysEnFunctionService() ){
            super.initService(  this.getSysEnFunctionService()  );
        }else{

        }

    }

    @Override
    public void initField( SysEnController controller ) throws PlatformException{
        String parentIdExtCon = "multiSelect:false,emptyText:'请选择父节点'";
        String parentIdUrl = "sysFunction/loadTreeData.do?fieldMap=id:id,text:func_name,parentId:fk_parent_id&treeRootVal=-1&treeFlag=functionTree&resType=map&multiSelect=false";
        String fkSysEnMenuIdExtCon = "multiSelect:false,emptyText:'请选择所属菜单'";
        String fkSysEnMenuIdUrl = "sysFunction/loadTreeData.do?fieldMap=id:id,text:text,parentId:fk_parent_id&treeRootVal=-1&treeFlag=menuTree&resType=map&multiSelect=false";
        String funcIdentifyExtCon = "maxLength:50,vtype:'letterNumUnderline'";
        String funcNameExtCon = "maxLength:25";
        String funcCodeExtCon = "maxLength:50,vtype:'letterNumUnderline'";
        String orderNumExtCon = "minValue:0,maxValue:99999999";

        StringBuffer ins = new StringBuffer();//功能说明
        ins.append("配置菜单两种使用方法说明：</br>");
        ins.append("1.为controller配置功能权限</br>");
        ins.append("  &nbsp;&nbsp;&nbsp;所属菜单、名称、编码配合使用</br>");
        ins.append("2.为非controller配置功能权限</br>");
        ins.append("  &nbsp;&nbsp;&nbsp;唯一标识、名称配合使用 ");
        String instructionsExtConfigs = "value:'"+ins.toString()+"'";

        //构造字段
        controller.addFieldId("id","ID",null);
        controller.addFieldComboBoxTree( "fkParentId","父节点",true,true,true,true,true,parentIdExtCon,parentIdUrl );
        controller.addFieldText("funcName","名称",true,true,true,true,false,funcNameExtCon);
        controller.addFieldText("funcCode","编码",true,true,true,true,false,funcCodeExtCon);
        controller.addFieldText("funcIdentify","唯一标识",true,true,true,true,true,funcIdentifyExtCon);
        controller.addFieldComboBoxTree("fkSysEnMenuId","所属菜单",true,true,true,true,true,fkSysEnMenuIdExtCon,fkSysEnMenuIdUrl);

        String leafJson = "[{name:'是',value:'true',eleId:'leafShi'},{name:'否',value:'false',eleId:'leafFou',checked:true}]";
        controller.addFieldRadioInitDataByJson("leaf","叶子节点",true,true,true,false,true,leafJson,null,null);
        String expandedJson = "[{name:'是',value:'true',eleId:' expandedShi'},{name:'否',value:'false',eleId:'expandedFou',checked:true}]";
        controller.addFieldRadioInitDataByJson("expanded","展开",true,true,true,false,true,expandedJson,null,null);

        controller.addFieldNumber("orderNum","排序编码",true,true,true,false,false,orderNumExtCon);

        controller.addFieldWhiteBoard("instructions","功能说明",true,true,true,instructionsExtConfigs);

        controller.addFieldDatetime("addTime","添加时间",true,false,false,false,false,null);
        controller.addFieldDatetime("updateTime","更新时间",true,false,false,false,false,null);
    }

    @Override
    public void initFunction(SysEnController controller) throws PlatformException {
        StringBuffer strIns = new StringBuffer();
        strIns.append("框架controller功能点（功能点：例如添加、删除、编辑、查看详情、审核等称作功能点）权限配置说明如下：");
        strIns.append("1.功能权限管理中没有配置某个功能权限时，默认所有用户都有此功能权限；");
        strIns.append("2.功能权限管理中配置了某个功能权限时，角色勾选了此功能权限，则对应角色用户拥有此权限，否则没有。");
        controller.addFunctionInstructions("instructions1","权限配置说明","sysFunctionZoom",1,strIns.toString());
    }

    @Override
    public void beforeSaveBean(HttpServletRequest request, HttpServletResponse response, SysEnFunction bean) throws PlatformException{
        Date date = new Date();
        bean.setAddTime( date );
        bean.setUpdateTime( date );

        if( bean.getFkParentId()==null  ){
            bean.setFkParentId(-1);
        }
    }

    @Override
    public void beforeUpdateBean(HttpServletRequest request, HttpServletResponse response,Object bean  ) throws PlatformException{
        SysEnFunction tempBean = (SysEnFunction)bean;
        tempBean.setUpdateTime( new Date() );

        if( tempBean.getFkParentId()==null  ){
            tempBean.setFkParentId(-1);
        }
    }

    @Override
    public List<Map<String,Object>> loadTreeNodeDataMap(HttpServletRequest request, HttpServletResponse response, SysEnFunction bean) throws PlatformException{
        /**
         * 加载树结点需要的数据
         *
         */
        String treeFlag = request.getParameter("treeFlag");
        List<Map<String,Object>> res = null;

        if( "functionTree".equals( treeFlag ) ){
            res = getSysEnFunctionService().queryFunctionTreeMap();
        }else if( "menuTree".equals( treeFlag ) ){
            res = getSysEnFunctionService().queryMenuTreeMap();
        }

        return res;
    }

    public SysEnFunctionService getSysEnFunctionService() {
        return sysEnFunctionService;
    }

    public void setSysEnFunctionService(SysEnFunctionService sysEnFunctionService) {
        this.sysEnFunctionService = sysEnFunctionService;
    }
}
