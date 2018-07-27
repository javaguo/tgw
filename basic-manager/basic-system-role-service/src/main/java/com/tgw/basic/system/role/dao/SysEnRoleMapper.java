package com.tgw.basic.system.role.dao;


import com.tgw.basic.framework.dao.BaseModelMapper;
import com.tgw.basic.system.role.model.SysEnRole;

import java.util.List;
import java.util.Map;


public interface SysEnRoleMapper extends BaseModelMapper<SysEnRole> {

	/**
	 * 查询表单中菜单树控件的树节点数据
	 * @return
	 */
	public abstract List<Map<String,Object>> queryMenuTreeMap();

	/**
	 * 查询表单中功能树控件的树节点数据
	 * @return
	 */
	public abstract List<Map<String,Object>> queryFunctionTreeMap();

	/**
	 * 查询表单中角色树控件的树节点数据
	 * @return
     */
	public abstract List<Map<String,Object>> queryRoleTreeMap();


}


