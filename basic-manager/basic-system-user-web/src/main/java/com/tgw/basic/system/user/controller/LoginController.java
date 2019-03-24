package com.tgw.basic.system.user.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.system.config.service.SysEnConfigurationService;
import com.tgw.basic.system.role.model.SysEnRole;
import com.tgw.basic.system.user.enums.LoginSource;
import com.tgw.basic.system.user.model.SysEnUser;
import com.tgw.basic.system.user.model.UserSessionInfo;
import com.tgw.basic.system.user.service.SysEnUserService;
import com.tgw.basic.system.user.utils.PlatformUserUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController<SysEnUser> {
	
	@Resource
	public SysEnUserService sysEnUserService;
    @Resource
    private SysEnConfigurationService sysEnConfigurationService;

    @RequestMapping("/toLogin.do")
    public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/manage/login");
        return  modelAndView;
    }

    @RequestMapping("/login.do")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        String loginName = request.getParameter("loginName");
        loginName = StringUtils.isNotBlank(loginName)?loginName:request.getParameter("userName");// 后续逐步改为loginName。
        String password = request.getParameter("password");

        try {
            if( StringUtils.isBlank( loginName ) ){
                throw new PlatformException("用户名不能为空！");
            }
            if( StringUtils.isBlank( password ) ){
                throw new PlatformException("密码不能为空！");
            }

            SysEnUser sysEnUser = this.getSysEnUserService().queryUserByLoginName( loginName.trim() );
            if( sysEnUser==null ){
                throw new PlatformException("用户名不存在！");
            }
            if( sysEnUser.getUserStatus()==null || !"1".equals( sysEnUser.getUserStatus() ) ){
                throw new PlatformException("无效用户 ！");
            }
            List<SysEnRole> sysEnRoleList = this.getSysEnUserService().loadSysEnRolesByUserId( sysEnUser.getId() );
            if( null==sysEnRoleList || sysEnRoleList.size()<1 ){
                throw new PlatformException("无效用户！");
            }
            if( !password.trim().equals( sysEnUser.getPassword() ) ){
                throw new PlatformException("密码错误！");
            }

            String loginSource = request.getParameter("loginSource");
            if (LoginSource.M.name().equals(loginSource)){// 移动端登录
                String token = UUID.randomUUID().toString().replaceAll("-","");// 目前使用一个简单的字符串，后续可以考虑使用JWT等。
                sysEnUser.setToken(token);
                this.getSysEnUserService().updateSysUserBaseInfo(sysEnUser);
                jo.put("token",token);
            }

            UserSessionInfo userSessionInfo = new UserSessionInfo();
            userSessionInfo.setSysEnUser( sysEnUser );
            userSessionInfo.setSysEnRoleList( sysEnRoleList );

            request.getSession().setAttribute( PlatformSysConstant.USER_SESSION_INFO,userSessionInfo);


            jo.put("success",true);
            jo.put("info","登录成功！");
            jo.put("theme", StringUtils.isBlank( sysEnUser.getTheme() )?"":sysEnUser.getTheme().trim() );
        }catch (PlatformException e){
            jo.put("success",false);
            jo.put("info",e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            jo.put("success",false);
            jo.put("info","发生异常！");
        }

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );
        return  modelAndView;
    }

    @RequestMapping("/toManageIndex.do")
    public ModelAndView manageIndex(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        String theme = request.getParameter("theme");
        if( StringUtils.isNotBlank( theme ) ){
            modelAndView.addObject("theme",theme);
        }else{
            modelAndView.addObject("theme","classic");
            theme = "classic";
        }

        UserSessionInfo userSessionInfo = PlatformUserUtils.getUserSessionInfo( );
        SysEnUser sysEnUser = userSessionInfo.getSysEnUser();//session中的用户信息
        if( !theme.equals( sysEnUser.getTheme() ) ){
            sysEnUser.setTheme( theme );
            //主题变更，将主题更新到数据库
            this.getSysEnUserService().updateSysUserBaseInfo( sysEnUser );
        }

        modelAndView.setViewName("page/manage/index/index");
        return  modelAndView;
    }

    @RequestMapping("/loadUserInfo.do")
    public ModelAndView loadUserInfo(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        try {
            UserSessionInfo userSessionInfo = PlatformUserUtils.getUserSessionInfo( );
            SysEnUser sysEnUser = userSessionInfo.getSysEnUser();

            jo.put("userName", sysEnUser.getUserName() );
            jo.put("loginName", sysEnUser.getLoginName() );
            jo.put("role", PlatformUserUtils.joinRoleName( userSessionInfo.getSysEnRoleList() ) );

            jo.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            jo.put("success",false);
            jo.put("info","获取用户登录信息失败！");
        }

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );
        return  modelAndView;
    }

    @RequestMapping("/modifyUserPassword.do")
    public ModelAndView modifyUserPassword(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        try {
            String oldPassword = request.getParameter( "oldPassword" );
            String newPassword = request.getParameter( "newPassword" );
            if( StringUtils.isBlank( oldPassword ) || StringUtils.isBlank( newPassword ) ){
                throw new PlatformException("请求参数错误！");
            }

            UserSessionInfo userSessionInfo = PlatformUserUtils.getUserSessionInfo( );
            SysEnUser sysEnUser = this.getSysEnUserService().queryUserByLoginName( userSessionInfo.getSysEnUser().getLoginName() );

            if( !oldPassword.equals( sysEnUser.getPassword() ) ){
                throw new PlatformException("旧密码不正确！");
            }

            userSessionInfo.getSysEnUser().setPassword( newPassword );
            sysEnUser.setPassword( newPassword );
            this.getSysEnUserService().updateBean( sysEnUser );

            jo.put("success",true);
            jo.put("msg","密码修改成功！" );
        }catch (PlatformException e){
            jo.put("success",false);
            jo.put("msg",e.getMsg() );
        }catch (Exception e){
            e.printStackTrace();
            jo.put("success",false);
            jo.put("msg","发生异常！" );
        }

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );
        return  modelAndView;
    }

    @RequestMapping("/loginOut.do")
    public ModelAndView loginOut(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        try {
            request.getSession().removeAttribute( PlatformSysConstant.USER_SESSION_INFO );
            String contextPath = request.getContextPath();
            jo.put("url",contextPath + "/login/toLogin.do" );
            jo.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            jo.put("success",false);
            jo.put("info","发生异常！");
        }

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );
        return  modelAndView;
    }

    @RequestMapping("/loadPageInfo.do")
    public ModelAndView loadPageInfo(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        try {
            String manageIndexTopTitle = this.getSysEnConfigurationService().querySysEnConfigValByKey( "manageIndexTopTitle" );
            if( StringUtils.isNotBlank( manageIndexTopTitle ) ){
                jo.put("manageIndexTopTitle",manageIndexTopTitle);
            }else{
                jo.put("manageIndexTopTitle","欢迎使用");
            }

            String manageIndexBottomCopyright = this.getSysEnConfigurationService().querySysEnConfigValByKey( "manageIndexBottomCopyright" );
            if( StringUtils.isNotBlank( manageIndexBottomCopyright ) ){
                jo.put("manageIndexBottomCopyright",manageIndexBottomCopyright);
            }else{
                jo.put("manageIndexBottomCopyright","Copyright 2018 ZhaoJianGuo. AllRightsReserved.");
            }

            jo.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            jo.put("success",false);
            jo.put("info","发生异常！");
        }

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );
        return  modelAndView;
    }

    public SysEnUserService getSysEnUserService() {
        return sysEnUserService;
    }

    public void setSysEnUserService(SysEnUserService sysEnUserService) {
        this.sysEnUserService = sysEnUserService;
    }

    public SysEnConfigurationService getSysEnConfigurationService() {
        return sysEnConfigurationService;
    }

    public void setSysEnConfigurationService(SysEnConfigurationService sysEnConfigurationService) {
        this.sysEnConfigurationService = sysEnConfigurationService;
    }
}
