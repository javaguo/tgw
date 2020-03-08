package com.tgw.basic.system.role.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.collections.PlatformCollectionsUtils;
import com.tgw.basic.common.utils.string.PlatformStringUtils;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import com.tgw.basic.system.role.model.SysEnRole;
import com.tgw.basic.system.role.service.SysEnRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController<SysEnRole> {
	
	@Resource
	public SysEnRoleService sysEnRoleService;

    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "SysRoleList" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "sysRole/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "sysRole/" );//控制器的请求地址

        //controller.setPageSize( 10 );
    }

    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, SysEnRole bean ) throws PlatformException {

        if( null!=this.getSysEnRoleService() ){
            super.initService(  this.getSysEnRoleService()  );
        }else{

        }

    }

    @Override
    public void initField( SysEnController controller ) throws PlatformException {
        String roleCodeExtCon = "maxLength:50,vtype:'letterNumUnderline'";
        String roleNameExtCon = "maxLength:25";
        String fkParentIdExtCon = "emptyText:'请选择父角色',multiSelect:false";
        String fkParentIdUrl = "sysRole/loadTreeData.do?fieldMap=id:id,text:role_name,parentId:fk_parent_id&treeRootVal=-1&treeFlag=roleTree&resType=map&multiSelect=false";
        String reRoleMenuIdExtCon = "emptyText:'请选择角色的菜单权限',multiSelect:true,multiCascade:true";
        String reRoleMenuIdUrl = "sysRole/loadTreeData.do?fieldMap=id:id,text:text,parentId:fk_parent_id&treeRootVal=-1&treeFlag=menuTree&resType=map&multiSelect=true";
        String reRoleFuncIdExtCon = "emptyText:'请选择角色的功能权限',multiSelect:true,multiCascade:true";
        String reRoleFuncIdUrl = "sysRole/loadTreeData.do?fieldMap=id:id,text:func_name,parentId:fk_parent_id&treeRootVal=-1&treeFlag=functionTree&resType=map&multiSelect=true";
        String roleStatusExtCon = "emptyText:'角色状态',value:'1'";
        String roleStatusJson = "[{name:'正常',value:'1'},{name:'注销',value:'2'}]";
        String remarkExtCon = "maxLength:200";
        String orderNumExtCon = "minValue:0,maxValue:99999999";

        //构造字段
        controller.addFieldId("id","ID",null);
        controller.addFieldText("pp_fkParentId","父父角色",true,false,false,false,true,null);
        controller.addFieldComboBoxTree( "fkParentId","父角色",true,true,true,true,true,fkParentIdExtCon,fkParentIdUrl );
        controller.addFieldText("roleCode","角色编码",true,true,true,true,false,roleCodeExtCon);
        controller.addFieldText("roleName","角色名称",true,true,true,true,false,roleNameExtCon);
        controller.addFieldComboBoxTree( "reRoleMenuId","菜单权限",true,true,true,false,true,reRoleMenuIdExtCon,reRoleMenuIdUrl ).setShowList(false);
        controller.addFieldComboBoxTree( "reRoleFuncId","功能权限",true,true,true,false,true,reRoleFuncIdExtCon,reRoleFuncIdUrl ).setShowList(false);
        controller.addFieldComboBoxByJSON("roleStatus","角色状态",true,true,true,true,false,roleStatusJson,roleStatusExtCon);
        controller.addFieldTextArea("remark","备注",true,true,true,false,true,remarkExtCon);

        controller.addFieldNumber("orderNum","排序编码",true,true,true,false,false,orderNumExtCon);
        controller.addFieldDatetime("addTime","添加时间",true,false,false,false,false,null);
        controller.addFieldDatetime("updateTime","更新时间",true,false,false,false,false,null);
    }

    /**
     * 覆写baseController方法
     * @param request
     * @param response
     * @param bean
     */
    @Override
    public void saveCore(HttpServletRequest request, HttpServletResponse response, SysEnRole bean ) throws PlatformException{
        Date date = new Date();
        bean.setAddTime( date );
        bean.setUpdateTime( date );

        if( bean.getFkParentId()==null  ){
            bean.setFkParentId(-1);
        }

        this.getSysEnRoleService().saveSysRole( bean );
    }

    @Override
    public void initBenaVal(HttpServletRequest request, HttpServletResponse response, Object bean) throws PlatformException{
        SysEnRole sysEnRole = (SysEnRole)bean;

        //查询角色的菜单
        List<Map<String,Object>> roleMenuList = this.getSysEnRoleService().loadRoleMenuByRoleId( sysEnRole.getId() );
        List<Object> menuList = PlatformCollectionsUtils.extractMapValToList( roleMenuList,"fk_menu_id" );
        String fk_menu_ids =  PlatformCollectionsUtils.listToStr( menuList );
        sysEnRole.setReRoleMenuId( fk_menu_ids );

        //查询角色的功能权限
        List<Map<String,Object>> roleFuncList = this.getSysEnRoleService().loadRoleFuncByRoleId( sysEnRole.getId() );
        List<Object> funcList = PlatformCollectionsUtils.extractMapValToList( roleFuncList,"fk_func_id" );
        String fk_func_ids =  PlatformCollectionsUtils.listToStr( funcList );
        sysEnRole.setReRoleFuncId( fk_func_ids );

    }

    @Override
    public void updateCore(HttpServletRequest request, HttpServletResponse response, SysEnRole bean ) throws PlatformException{
        SysEnRole oldSysEnRole = new SysEnRole();
        oldSysEnRole.setId( bean.getId() );
        oldSysEnRole = (SysEnRole)this.getSysEnRoleService().selectUniqueBeanByPrimaryKey( oldSysEnRole );

        bean.setAddTime( oldSysEnRole.getAddTime() );
        bean.setUpdateTime( new Date() );

        if( bean.getFkParentId()==null  ){
            bean.setFkParentId(-1);
        }

        this.getSysEnRoleService().updateSysRole( bean );
    }

    @Override
    public void deleteBatchBean( List<Object>  beanList ) throws PlatformException {
        this.getSysEnRoleService().deleteSysRoles( beanList );
    }

    @Override
    public List dealListQueryResult(HttpServletRequest request, HttpServletResponse response, SysEnRole bean, List dataList) throws PlatformException{
        Map<String,Object> roleStatusDataMap =getRoleStatusMap();

        for( int i=0;i<dataList.size();i++ ){
            HashMap<String,Object> map = (HashMap<String,Object>)dataList.get(i);

            if( map.get("roleStatus")!=null ){
                map.put("roleStatus", PlatformStringUtils.strKeyToName( map.get("roleStatus").toString(),roleStatusDataMap ));
            }

            dataList.set(i,map);
        }
        return dataList;
    }

    private Map<String,Object> getRoleStatusMap(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1","正常");
        map.put("2","注销");

        return map;
    }

    @Override
    public List<Map<String,Object>> loadTreeNodeDataMap(HttpServletRequest request, HttpServletResponse response, SysEnRole bean) throws PlatformException{
        /**
         * 加载树结点需要的数据
         *
         */
        String treeFlag = request.getParameter("treeFlag");
        List<Map<String,Object>> res = null;

        if( "roleTree".equals( treeFlag ) ){//角色树
            res = getSysEnRoleService().queryRoleTreeMap();
        }else if( "menuTree".equals( treeFlag ) ){//菜单树
           res = getSysEnRoleService().queryMenuTreeMap();
        }else if( "functionTree".equals( treeFlag ) ){//功能权限
            res = getSysEnRoleService().queryFunctionTreeMap();
        }

        return res;
    }

    public SysEnRoleService getSysEnRoleService() {
        return sysEnRoleService;
    }

    public void setSysEnRoleService(SysEnRoleService sysEnRoleService) {
        this.sysEnRoleService = sysEnRoleService;
    }
}
