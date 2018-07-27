package com.tgw.basic.system.menu.service;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.BaseService;
import com.tgw.basic.system.menu.model.SysEnMenu;

import java.util.List;
import java.util.Map;


public interface SysEnMenuService  extends BaseService {

	/**
	 * 查询表单中树控件的树节点数据
	 * @return
	 * @throws PlatformException
	 */
	public abstract List<Map<String,Object>> queryMenuTreeMap()  throws PlatformException;
	/**
	 * 根据角色查询对应的功能菜单
	 * @param roleId
	 * @return
	 */
	List<SysEnMenu> loadMenuByRole(String roleId);


}
