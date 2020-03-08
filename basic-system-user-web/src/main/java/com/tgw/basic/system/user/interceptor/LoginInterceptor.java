package com.tgw.basic.system.user.interceptor;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	

	public static final String CONTEXT_PATH = "/framework";

	static final String HTML_401 = "<center><div style=\"margin-top:15%;\" ><h2>401</h2><br /><h2 style=\"font-weight:normal;\" >content</h2></div></center>";

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		return super.preHandle(request, response, handler);

		/*String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		StringBuffer requestURL = request.getRequestURL();

		System.out.println("requestURI---->"+requestURI);
		System.out.println("contextPath---->"+contextPath);
		System.out.println("requestURL---->"+requestURL);

		if (contextPath.matches(".*\\.(jpg|swf|png|gif|js|css|ico|less|html|htm|xls|xlsx)$")) {
			return super.preHandle(request, response, handler);
		}

		List<String> whiteUrlList = new ArrayList<String>();
		whiteUrlList.add( contextPath + "/login/toLogin.do" );

		if (  whiteUrlList.contains( requestURI )  ) {
			return super.preHandle(request, response, handler);
		}*/

		/*if (  whiteUrlList.contains(  )  ) {
			return super.preHandle(request, response, handler);
		}

		if ( request.getRequestURI().startsWith(contextPath + "/page/manage/index/index.jsp") ) {
			return super.preHandle(request, response, handler);
		}

		response.sendRedirect(contextPath + "/page/manage/index/index.jsp");*/

		/*if ( request.getRequestURI().startsWith(contextPath + "/page/manage/index/index.jsp") ) {
			System.out.println("/login/toLogin.do");
			response.sendRedirect(contextPath + "/login/toLogin.do");

		}*/
		/*String userSessionInfo = (String)request.getSession().getAttribute("userSessionInfo");*/
		/*if ( userSessionInfo == null ) {
			System.out.println("/login/toLogin.do");
			response.sendRedirect(contextPath + "/login/toLogin.do");
		}
		return false;*/
	}

}
