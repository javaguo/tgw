package com.tgw.basic.common.utils;

import javax.servlet.http.HttpServletRequest;

public class PlatformInfo {
	/**
	 * 获取访问平台的url地址：例：http://localhost:8080。
	 * 结尾没有斜杠。
	 * @param request
	 * @return
	 */
	public static String fullServerUrlNotEndWidthSlash(HttpServletRequest request ){
		String basePath = request.getScheme()+
				"://"+request.getServerName()+
				":"+request.getServerPort();
		return basePath;
	}

	/**
	 * 获取访问平台的url地址：例：http://localhost:8080/。
	 * 以斜杠结尾。
	 * @param request
	 * @return
	 */
	public static String fullServerUrlEndWidthSlash(HttpServletRequest request ){
		return fullServerUrlNotEndWidthSlash(request)+"/";
	}

	/**
	 * 获取访问平台的url地址：例：http://localhost:8080/项目名称。
	 * 结尾没有斜杠。
	 * @param request
	 * @return
	 */
	public static String fullContextUrlNotEndWidthSlash(HttpServletRequest request ){
		String basePath = fullServerUrlNotEndWidthSlash(request)+
				request.getContextPath();
		return basePath;
	}

	/**
	 * 获取访问平台的url地址：例：http://localhost:8080/项目名称/。
	 * 以斜杠结尾。
	 * @param request
	 * @return
	 */
	public static String fullContextUrlEndWidthSlash(HttpServletRequest request ){
		return fullContextUrlNotEndWidthSlash(request)+"/";
	}

	/**
	 * 获取访问平台的url地址：例：http://localhost:8080/项目访问名称/
	 * @param request
	 * @return
	 */
	@Deprecated
	public static String baseUrl( HttpServletRequest request ){
		return fullContextUrlEndWidthSlash(request);
	}

	/**
	 * 返回绝对路径
	 * @param request
	 * @param savePath
     * @return
     */
	public static String getAbsolutePath(HttpServletRequest request,String savePath){
		return request.getSession().getServletContext().getRealPath( savePath );
	}
}
