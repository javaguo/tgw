package com.tgw.basic.system.user.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.PlatformInfo;
import com.tgw.basic.common.utils.PlatformUtils;
import com.tgw.basic.common.utils.collections.PlatformCollectionsUtils;
import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.common.utils.json.PlatformJsonUtils;
import com.tgw.basic.common.utils.string.PlatformStringUtils;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import com.tgw.basic.system.config.service.SysEnConfigurationService;
import com.tgw.basic.system.menu.model.SysEnMenu;
import com.tgw.basic.system.user.model.SysEnUser;
import com.tgw.basic.system.user.model.UserSessionInfo;
import com.tgw.basic.system.user.service.SysEnUserService;
import com.tgw.basic.system.user.utils.PlatformUserUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sysUser")
public class SysUserController extends BaseController<SysEnUser> {

    @Resource
	public SysEnUserService sysEnUserService;
    @Resource
    private SysEnConfigurationService sysEnConfigurationService;

    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "SysUserList" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "sysUser/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "sysUser/" );//控制器的请求地址

        //controller.setPageSize( 10 );
        controller.setSearchConditionColNum( 3 );
    }

    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, SysEnUser bean ) throws PlatformException {

        if( null!=this.getSysEnUserService() ){
            super.initService(  this.getSysEnUserService()  );
        }else{

        }

    }

    @Override
    public void initField( SysEnController controller ) throws PlatformException {
        String userNameExtCon = "maxLength:25";
        String loginNameExtCon = "maxLength:25,vtype:'accountNumber'";
        String passwordExtCon = "maxLength:25,vtype:'generalPassword'";
        String reUserRoleIdExtCon = "emptyText:'请选择角色',multiSelect:true,multiCascade:false";
        String reUserRoleIdUrl = "sysUser/loadTreeData.do?fieldMap=id:id,text:role_name,parentId:fk_parent_id&treeRootVal=-1&treeFlag=roleTree&resType=map&multiSelect=true";
        String userStatusExtCon = "emptyText:'用户状态',value:'1'";
        String userStatusJson = "[{name:'正常',value:'1'},{name:'注销',value:'2'}]";
        String remarkExtCon = "maxLength:200";

        //构造字段
        controller.addFieldId("id","ID",null);
        controller.addFieldText("userName","用户名",true,true,true,true,false,userNameExtCon);
        controller.addFieldText("loginName","登录名",true,true,true,true,false,loginNameExtCon);
        controller.addFieldPassword("password","密码",true,true,true,false,passwordExtCon);
        controller.addFieldComboBoxTree( "reUserRoleId","所属角色",true,true,true,true,false,reUserRoleIdExtCon,reUserRoleIdUrl );
        controller.addFieldComboBoxByJSON("userStatus","用户状态",true,true,true,true,false,userStatusJson,userStatusExtCon);
        controller.addFieldTextArea("remark","备注",true,true,true,false,true,remarkExtCon);

        controller.addFieldDatetime("addTime","添加时间",true,false,false,false,false,null);
        controller.addFieldDatetime("updateTime","更新时间",true,false,false,false,false,null);
    }

    @Override
    public void beforeSearchData(HttpServletRequest request, HttpServletResponse response, SysEnUser bean) throws PlatformException{
        if(StringUtils.isNotBlank( bean.getReUserRoleId() )){
            bean.setReUserRoleIdList( PlatformUtils.stringToList( bean.getReUserRoleId(),"," ) );
        }
    }

    /**
     * 覆写baseController方法
     * @param request
     * @param response
     * @param bean
     */
    @Override
    public void saveCore(HttpServletRequest request, HttpServletResponse response, SysEnUser bean ) throws PlatformException{
        Date date = new Date();
        bean.setAddTime( date );
        bean.setUpdateTime( date );

        this.getSysEnUserService().saveSysUser( bean );
    }

    @Override
    public void initBenaVal(HttpServletRequest request, HttpServletResponse response, Object bean) throws PlatformException{
        SysEnUser sysEnUser = (SysEnUser)bean;

        sysEnUser.setReUserRoleId( this.queryUserRolesIdsByUserId( sysEnUser.getId() ) );
    }

    @Override
    public void updateCore(HttpServletRequest request, HttpServletResponse response, SysEnUser bean ) throws PlatformException{
        SysEnUser oldSysEnUser = new SysEnUser();
        oldSysEnUser.setId( bean.getId() );
        oldSysEnUser = (SysEnUser)this.getSysEnUserService().selectUniqueBeanByPrimaryKey( oldSysEnUser );

        bean.setTheme( oldSysEnUser.getTheme() );
        if( StringUtils.isBlank( bean.getPassword() ) ){//修改时，编辑页面表单提交的密码为空，则把原来的密码赋值给bean对象。
            bean.setPassword( oldSysEnUser.getPassword() );
        }
        bean.setAddTime( oldSysEnUser.getAddTime() );
        bean.setUpdateTime( new Date() );

        this.getSysEnUserService().updateSysUserAndRoleInfo( bean );
    }

    @Override
    public void deleteBatchBean( List<Object>  beanList ) throws PlatformException {
        this.getSysEnUserService().deleteSysUser( beanList );
    }

    /**
     * 用户自己注册用户。app与pc端注册都使用此方法。
     * @param request
     * @param response
     * @param bean
     * @throws PlatformException
     */
    @RequestMapping("/register.do")
    public ModelAndView register(HttpServletRequest request, HttpServletResponse response, SysEnUser bean ){
        ModelAndView modelAndView = new ModelAndView();
        Map map = PlatformJsonUtils.stringToMap("{}");

        /**
         * extjs的form表单提交后根据返回值中的success值判断走success回调函数或failure函数
         */
        try{
            modelAndView.setViewName( this.getJsonView() );

            if ("off".equals( sysEnConfigurationService.querySysEnConfigValByKey("registerSwitch") )){
                throw new PlatformException("暂时关闭对外注册通道！");
            }

            // 初始化用户信息
            if (StringUtils.isBlank(bean.getUserName())){
                bean.setUserName( "u_"+RandomStringUtils.randomAlphanumeric(10) );// u_ 代表用户自定义注册
            }
            if (StringUtils.isBlank(bean.getLoginName()) || StringUtils.isBlank(bean.getPassword())){
                throw new PlatformException("用户名/密码不能为空！");
            }
            if (StringUtils.isBlank(bean.getReUserRoleId())){
                // 记账项目中5代表普通用户角色。PC端登录需要有角色，默认自定义注册的都是普通用户。
                bean.setReUserRoleId("5");
            }
            bean.setUserStatus("1");// 默认代表正常
            // 至此，初始化用户信息成功
        }catch( PlatformException e){
            map.put("success",false);
            map.put("msg",""+e.getMsg() );

            modelAndView.addObject( PlatformSysConstant.JSONSTR, PlatformJsonUtils.toJsonString(map) );
            return  modelAndView;
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","发生异常！");

            modelAndView.addObject( PlatformSysConstant.JSONSTR, PlatformJsonUtils.toJsonString(map) );
            return  modelAndView;
        }

        return this.save(request,response,bean);// 保存用户使用与管理端相同的方法
    }

    @Override
    public List dealListQueryResult(HttpServletRequest request, HttpServletResponse response, SysEnUser bean, List dataList) throws PlatformException{
        Map<String,Object> roleStatusDataMap =getRoleStatusMap();

        for( int i=0;i<dataList.size();i++ ){
            try{
                HashMap<String,Object> map = (HashMap<String,Object>)dataList.get(i);

                if( map.get("userStatus")!=null ){
                    map.put("userStatus", PlatformStringUtils.strKeyToName( map.get("userStatus").toString(),roleStatusDataMap ));
                }

                if( map.get("reUserRoleId")!=null ){
                    int tempUserId = Integer.parseInt( map.get("id").toString() );
                    String roleNames = this.queryUserRolesNamesByUserId( tempUserId );
                    map.put("reUserRoleId", roleNames );
                }

                dataList.set(i,map);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return dataList;
    }

    /**
     * 根据用户id查询用户所拥有的角色ids
     * @param userId
     * @return
     */
    public String queryUserRolesIdsByUserId(Integer userId ){
        //查询用户所拥有的角色
        List<Map<String,Object>> userRoleList = this.getSysEnUserService().loadUserRoleByUserId( userId );
        List<Object> roleList = PlatformCollectionsUtils.extractMapValToList( userRoleList,"fk_role_id" );
        String userRolesIds =  PlatformCollectionsUtils.listToStr( roleList );
        return userRolesIds;
    }

    /**
     * 根据用户id查询用户所拥有的角色名称
     * @param userId
     * @return
     */
    public String queryUserRolesNamesByUserId(Integer userId ){
        //查询角色的菜单
        List<Map<String,Object>> userRoleList = this.getSysEnUserService().loadUserRoleByUserId( userId );
        List<Object> roleList = PlatformCollectionsUtils.extractMapValToList( userRoleList,"role_name" );
        String userRolesNames =  PlatformCollectionsUtils.listToStr( roleList );
        return userRolesNames;
    }

    private Map<String,Object> getRoleStatusMap(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1","正常");
        map.put("2","注销");

        return map;
    }

    @Override
    public List<Map<String,Object>> loadTreeNodeDataMap(HttpServletRequest request, HttpServletResponse response, SysEnUser bean) throws PlatformException{
        /**
         * 加载树结点需要的数据
         *
         */
        String treeFlag = request.getParameter("treeFlag");
        List<Map<String,Object>> res = null;

        if( "roleTree".equals( treeFlag ) ){//角色树
            res = getSysEnUserService().queryRoleTreeMap();
        }

        return res;
    }

    @RequestMapping("/queryMenuByUser.do")
    public ModelAndView queryMenuByUser(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();

        List<SysEnMenu> e2tList = null;
        try{
            UserSessionInfo userSessionInfo = PlatformUserUtils.getUserSessionInfo( );
            if( userSessionInfo == null ){
                throw new PlatformException("请先登录系统！");
            }

            int userId = userSessionInfo.getSysEnUser().getId();
            e2tList = this.getSysEnUserService().loadMenuByUser( userId );
            if( e2tList ==null || e2tList.size()==0 ){
                throw new PlatformException("没有查询到菜单权限！");
            }
        }catch (PlatformException e){
            modelAndView.setViewName( super.getJsonView() );
            modelAndView.addObject("info", e.getMsg() );
            return modelAndView;
        }

        /**
         * 定义“数组-链表”，该数组链表的每一项相当于一深度为2的小树
         * Map的key相当于“数组”的某一项，Map的value相当于该key所拥有的“链表”
         * 这里，key为父节点ID，list为具有相同父节点ID的所有同级子节点实体list（属于该父节点的所有子节点）
         */
        Map<String, List<SysEnMenu>> arrayListMap = new HashMap<String, List<SysEnMenu>>();

        for (SysEnMenu e : e2tList) {
            // 变量定义务必在循环内，对象是引用，不能重复使用同一个对象变量
            SysEnMenu e2t = new SysEnMenu();
            e2t.setId( e.getId() );

            e2t.setMenuIdentify( e.getMenuIdentify() );
            e2t.setText( e.getText() );
            e2t.setQtip( e.getQtip() );
            if( e2t.getSelfUrl()!=null && e2t.getSelfUrl() ){
                e2t.setLink( PlatformInfo.baseUrl(request)+e.getLink() );
            }else{
                e2t.setLink( e.getLink() );
            }
            e2t.setLeaf( e.getLeaf() );
            e2t.setExpanded( e.getExpanded() );
            e2t.setFkParentId( e.getFkParentId() );

            String fatherId = String.valueOf(  e.getFkParentId()  );
            if( "-1".equals( fatherId ) ){
                /**
                 * SysEnMenu类中id用Integer,parentId无值时，e.getParentId()为null
                 * SysEnMenu类中id用int,parentId无值时.e.getParentId()默认为0。
                 */
                fatherId = "root";
            }
            // 获取当前遍历结点的父ID，并判断该父节点的数组链表项是否存在，如果该“数组项-链表项”不存在，则新建一个，并放入“数组-链表”
            if (arrayListMap.get(fatherId) == null) {
                List<SysEnMenu> list = new ArrayList<SysEnMenu>();
                list.add(e2t);
                arrayListMap.put(fatherId, list);
            } else {
                List<SysEnMenu> valueList = arrayListMap.get(fatherId);
                valueList.add(e2t);
                arrayListMap.put(fatherId, valueList);
            }
        }
        // 以上，至此，第一遍遍历完毕，非叶子节点都拥有一个“数组-链表项”，也即“最小的树”已创建完毕

        // 以下，对“数组链表”Map进行遍历，更改“最小的树”的从属关系（更改指针指向），也即把所有小树组装成大树
        for (Map.Entry<String, List<SysEnMenu>> entry : arrayListMap.entrySet()) {
            // 获取当前遍历“数组项-链表项”的链表项，并对链表项进行遍历，从“数组-链表”小树中找到它的子节点，并将该子节点加到该小树的children中
            List<SysEnMenu> smallTreeList = new ArrayList<SysEnMenu>();
            smallTreeList = entry.getValue();
            int nodeListSize = smallTreeList.size();
            for (int i = 0; i < nodeListSize; i++) {
                String findID = String.valueOf( smallTreeList.get(i).getId() );
                List<SysEnMenu> findList = arrayListMap.get(findID);
                // 以下操作不能取出对象存放在变量中，否则将破坏树的完整性
                smallTreeList.get(i).setChildren(findList);
            }
        }
        // 获取以root为父Id的链表项，该链表项是根节点实体，里面已封装好各子节点，可以由于多个根节点，即这些根结点的父Id都为root
        //使用root的原因上面注释有相关解释
        List<SysEnMenu> rootNodeList = arrayListMap.get("root");
        if(  arrayListMap.get("root")==null ){
            rootNodeList = arrayListMap.get("0");
        }

        /**
         * 也可以直接向response输出内容
         */
        /*try {
			response.getWriter().print( PlatformJsonUtils.toJsonString(rootNodeList) );
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/

        modelAndView.setViewName( super.getJsonView() );
        //取第一个，不显示查询出的根结点，根结点由前端js代码设置
        modelAndView.addObject("jsonStr", PlatformJsonUtils.toJsonString(rootNodeList.get(0))  );
        return modelAndView;
    }

    public SysEnUserService getSysEnUserService() {
        return sysEnUserService;
    }

    public void setSysEnUserService(SysEnUserService sysEnUserService) {
        this.sysEnUserService = sysEnUserService;
    }
}
