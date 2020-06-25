package com.tgw.basic.system.function.dao;


import com.tgw.basic.framework.baseMapper.BaseModelMapper;
import com.tgw.basic.system.function.model.SysEnFunction;

import java.util.List;
import java.util.Map;


public interface SysEnFunctionMapper extends BaseModelMapper<SysEnFunction> {

	/**
	 * 查询表单中树控件的树节点数据
	 * @return
	 */
	public abstract List<Map<String,Object>> queryFunctionTreeMap();

	/**
	 * 查询表单中树控件的树节点数据
	 * @return
	 */
	public abstract List<Map<String,Object>> queryMenuTreeMap();

}


