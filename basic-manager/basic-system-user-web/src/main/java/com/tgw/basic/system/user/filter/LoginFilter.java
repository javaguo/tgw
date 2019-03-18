package com.tgw.basic.system.user.filter;

import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.system.user.model.UserSessionInfo;

import javax.servlet.*;
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
		whiteUrlList.add( "/login/toLogin.do" );
		whiteUrlList.add( "/login/login.do" );

		boolean loginFilter = false;
		if ( url.matches(".*\\.(jpg|swf|png|gif|js|css|ico|less|html|htm|xls|xlsx)$") ) {
			loginFilter = true;
		}else if (  whiteUrlList.contains( url )  ) {
			loginFilter = true;
		}else if (url.startsWith("/m/")){// 移动端请求统一以"/m/"开头
			String token = request.getParameter("token");
			System.out.println("token："+token);
			loginFilter = true;
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
