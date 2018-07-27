package com.tgw.basic.system.user.utils;

import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.system.role.model.SysEnRole;
import com.tgw.basic.system.runtimeenvir.context.PlatformContextUtils;
import com.tgw.basic.system.user.model.SysEnUser;
import com.tgw.basic.system.user.model.UserSessionInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zjg on 2017/10/8.
 */
public class PlatformUserUtils {

    public static void setUserSessionInfo(UserSessionInfo userSessionInfo){
        HttpServletRequest request = PlatformContextUtils.getRequest();
        request.getSession().setAttribute( PlatformSysConstant.USER_SESSION_INFO ,userSessionInfo);
    }

    /**
     * 获取登录用户的session信息
     * @return
     */
    public static UserSessionInfo getUserSessionInfo(  ){
        HttpServletRequest request = PlatformContextUtils.getRequest();
        UserSessionInfo userSessionInfo = (UserSessionInfo)request.getSession().getAttribute( PlatformSysConstant.USER_SESSION_INFO );
        return  userSessionInfo;
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    public static SysEnUser getLoginUserInfo(){
        UserSessionInfo userSessionInfo = getUserSessionInfo();
        if( userSessionInfo!=null ){
            return userSessionInfo.getSysEnUser();
        }else{
            return null;
        }
    }

    /**
     * 获取当前登录用户的角色list信息
     * @return
     */
    public static List<SysEnRole> getLoginUserRoleList(){
        UserSessionInfo userSessionInfo = getUserSessionInfo();
        List<SysEnRole> list = new ArrayList<SysEnRole>();
        if( userSessionInfo!=null ){
            list = userSessionInfo.getSysEnRoleList();
        }
        return  list;
    }

    /**
     * 获取当前登录用户的角色map信息，map的key为角色编码
     * @return
     */
    public static Map<String,SysEnRole> getLoginUserRoleCodeMap(){
        List<SysEnRole> list = getLoginUserRoleList();
        Map<String,SysEnRole> map = new HashMap<String, SysEnRole>();
        if( null!=list && !list.isEmpty() ){
            for( SysEnRole sysEnRole:list ){
                map.put( sysEnRole.getRoleCode(),sysEnRole);
            }
        }

        return map;
    }

    /**
     * 判断当前用户是否为某角色
     * @param roleCode
     * @return
     */
    public static boolean isContainRoleByCode(String roleCode){
        if( getLoginUserRoleCodeMap().containsKey( roleCode ) ){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 将角色对象集合转成角色名称。
     * @param roleList
     * @return
     */
    public static String joinRoleName( List<SysEnRole> roleList ){
        StringBuffer str = new StringBuffer();
        if( roleList!=null && roleList.size()>0 ){
            for( SysEnRole sysEnRole : roleList  ){
                str.append( sysEnRole.getRoleName() +"、");
            }
            str.deleteCharAt( str.length()-1 );
        }
        return  str.toString();
    }
}
