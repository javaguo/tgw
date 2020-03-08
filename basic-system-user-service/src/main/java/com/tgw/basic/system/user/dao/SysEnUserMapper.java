package com.tgw.basic.system.user.dao;


import com.tgw.basic.framework.dao.BaseModelMapper;
import com.tgw.basic.system.menu.model.SysEnMenu;
import com.tgw.basic.system.user.model.SysEnUser;

import java.util.List;
import java.util.Map;


public interface SysEnUserMapper extends BaseModelMapper<SysEnUser> {
	/**
	 * 查询表单中角色树控件的树节点数据
	 * @return
     */
	public abstract List<Map<String,Object>> queryRoleTreeMap();

	/**
	 * 根据用户登录名查找用户
	 * @return
     */
	public abstract SysEnUser queryUserByLoginName(String loginName);

	/**
	 * 根据用户查询对应的功能菜单
	 * @param userId
	 * @return
	 */
	List<SysEnMenu> loadMenuByUser(Integer userId);
}


