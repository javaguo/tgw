package com.tgw.basic.common.beans;

import com.tgw.basic.common.utils.config.PlatformSysConstant;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class PlatformCustomDateEditor extends PropertyEditorSupport {

	//注意SimpleDateFormat顺序，会影响解析结果
	public static final DateFormat[] ACCEPT_DATE_FORMATS = {new SimpleDateFormat(PlatformSysConstant.DATE_FORMAT_JAVA_YMDHMS)
		,new SimpleDateFormat(PlatformSysConstant.DATE_FORMAT_JAVA_YMDHM)
		,new SimpleDateFormat(PlatformSysConstant.DATE_FORMAT_JAVA_YMDH)
		,new SimpleDateFormat(PlatformSysConstant.DATE_FORMAT_JAVA_YMD)};

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.trim().equals(""))
			setValue(null);
		for (DateFormat format : ACCEPT_DATE_FORMATS) {
			try {
				setValue(format.parse(text));
				return;
			} catch (ParseException e) {
				continue;
			} catch (RuntimeException e) {
				continue;
			}
		}
	}

	@Override
	public String getAsText() {
		return (String) getValue();
	}

}
