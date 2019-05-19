package com.tgw.basic.system.user.filter;

import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.common.utils.spring.SpringContextUtils;
import com.tgw.basic.system.user.model.SysEnUser;
import com.tgw.basic.system.user.model.UserSessionInfo;
import com.tgw.basic.system.user.service.SysEnUserService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String contextPath = request.getContextPath();//项目发布名称。例：/framework
		String url = request.getRequestURI();//访问地址：例：/framework/exampleBeanFormVal/searchData.do
		url = url.replace(contextPath, "");

		List<String> whiteUrlList = new ArrayList<String>();
		whiteUrlList.add( "/sysUser/register.do" );
		whiteUrlList.add( "/login/toLogin.do" );
		whiteUrlList.add( "/login/login.do" );

		boolean loginFilter = false;
		if ( url.matches(".*\\.(jpg|swf|png|gif|js|css|ico|less|html|htm|xls|xlsx|apk)$") ) {
			loginFilter = true;
		}else if (  whiteUrlList.contains( url )  ) {
			loginFilter = true;
		}else if (url.startsWith("/m/")){// 移动端请求统一以"/m/"开头
			String token = request.getParameter("token");
			String loginName = request.getParameter("loginName");

			if (StringUtils.isBlank(token) || StringUtils.isBlank(loginName)){
				loginFilter = false;
			}else {
				SysEnUserService sysEnUserService = (SysEnUserService)SpringContextUtils.getBeanById("sysEnUserService");
				SysEnUser user = sysEnUserService.queryUserByLoginName(loginName);
				if (user==null || StringUtils.isBlank(user.getToken()) || !user.getToken().equals(token)){
					loginFilter = false;
				}else{
					UserSessionInfo userSessionInfo = new UserSessionInfo();
					userSessionInfo.setSysEnUser( user );
					request.getSession().setAttribute( PlatformSysConstant.USER_SESSION_INFO,userSessionInfo);

					loginFilter = true;
				}
			}
		}else{
			UserSessionInfo userSessionInfo = (UserSessionInfo)request.getSession().getAttribute( PlatformSysConstant.USER_SESSION_INFO );
			if( userSessionInfo!=null ){
				loginFilter = true;
			}else{
				loginFilter = false;
			}
		}

		if( loginFilter ){
			filterChain.doFilter(request, response);
		}else{
			response.sendRedirect(contextPath + "/login/toLogin.do");
			return;
		}

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
