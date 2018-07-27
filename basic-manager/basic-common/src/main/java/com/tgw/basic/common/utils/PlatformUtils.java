package com.tgw.basic.common.utils;

import com.tgw.basic.common.exception.PlatformException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class PlatformUtils {
	
	/**
	 * 页面勾选传递的ids转为list
	 * @param request
	 * @return
	 * @throws PlatformException
	 */
	public static List<String>  idsToList(HttpServletRequest request) throws PlatformException {
		String ids = request.getParameter("ids");
		if( StringUtils.isBlank(ids) ){
			throw new PlatformException("id参数值不能为空！");
		}

		return PlatformUtils.stringToList(ids,",");
	}

	/**
	 * 字符串转为list
	 * @param str
	 * @param separator 分隔符
	 * @return
	 * @throws PlatformException
	 */
	public static List<String> stringToList(String str,String separator) throws PlatformException {
		if( StringUtils.isBlank(str) || null==separator  ){
			throw new PlatformException("参数不能为空！");
		}

		List<String> list= new ArrayList<String>();
		String[] arrays = str.split(separator);
		for (int i = 0; i < arrays.length; i++) {
			list.add( arrays[i] );
		}

		return list;
	}

}
