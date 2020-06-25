package com.tgw.basic.system.menu.dao;


import com.tgw.basic.framework.baseMapper.BaseModelMapper;
import com.tgw.basic.system.menu.model.SysEnMenu;

import java.util.List;
import java.util.Map;


public interface SysEnMenuMapper extends BaseModelMapper<SysEnMenu> {

	/**
	 * 查询表单中树控件的树节点数据
	 * @return
	 */
	public abstract List<Map<String,Object>> queryMenuTreeMap();


	/**
	 * 根据角色查询对应的功能菜单
	 * @param roleId
	 * @return
	 */
	List<SysEnMenu> loadMenuByRole(Long roleId);



}


