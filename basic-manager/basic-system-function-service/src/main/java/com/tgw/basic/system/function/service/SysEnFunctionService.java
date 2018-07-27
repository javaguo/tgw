package com.tgw.basic.system.function.service;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.BaseService;

import java.util.List;
import java.util.Map;


public interface SysEnFunctionService extends BaseService {

	/**
	 * 查询表单中树控件的树节点数据
	 * @return
	 * @throws PlatformException
	 */
	public abstract List<Map<String,Object>> queryFunctionTreeMap()  throws PlatformException;

	/**
	 * 查询表单中树控件的树节点数据
	 * @return
	 * @throws PlatformException
	 */
	public abstract List<Map<String,Object>> queryMenuTreeMap()  throws PlatformException;

}
