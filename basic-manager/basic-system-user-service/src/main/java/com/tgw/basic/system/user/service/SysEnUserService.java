package com.tgw.basic.system.user.service;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.BaseService;
import com.tgw.basic.system.menu.model.SysEnMenu;
import com.tgw.basic.system.role.model.SysEnRole;
import com.tgw.basic.system.user.model.SysEnUser;

import java.util.List;
import java.util.Map;


public interface SysEnUserService extends BaseService {

	/**
	 * 根据用户登录名查找用户
	 * @param loginName
	 * @return
	 * @throws PlatformException
     */
	public abstract SysEnUser queryUserByLoginName(String loginName)  throws PlatformException;

	/**
	 * 保存用户
	 * @param sysEnUser
	 * @throws PlatformException
     */
	public void saveSysUser(SysEnUser sysEnUser)  throws PlatformException;

	/**
	 * 更新用户基本信息及角色信息
	 * @param sysEnUser
	 * @throws PlatformException
     */
	public void updateSysUserAndRoleInfo(SysEnUser sysEnUser)  throws PlatformException;

	/**
	 * 更新用户基本信息
	 * @param sysEnUser
     */
	public void updateSysUserBaseInfo(SysEnUser sysEnUser);

	/**
	 * 批量删除用户
	 * @param beanList
	 * @throws PlatformException
     */
	public void deleteSysUser(List<Object> beanList)  throws PlatformException;

	/**
	 * 保存用户拥有的角色
	 * @param userId
	 * @param roleIds
	 * @throws PlatformException
     */
	public void saveSysReUserRole(Integer userId, String roleIds) throws PlatformException;

	/**
	 * 删除用户拥有的角色
	 * @param userId
	 * @throws PlatformException
     */
	public void deleteSysReUserRole(Integer userId) throws PlatformException;

	/**
	 * 加载用户拥有的角色
	 * @param userId
     */
	public List<Map<String,Object>> loadUserRoleByUserId(Integer userId) throws PlatformException;

	/**
	 * 加载用户角色
	 * @param userId
	 */
	List<SysEnRole> loadSysEnRolesByUserId(Integer userId) throws PlatformException;

	/**
	 * 查询表单中角色树控件的树节点数据
	 * @return
	 * @throws PlatformException
	 */
	public abstract List<Map<String,Object>> queryRoleTreeMap()  throws PlatformException;

	/**
	 * 根据用户查询对应的功能菜单
	 * @param userId
	 * @return
	 */
	List<SysEnMenu> loadMenuByUser(Integer userId);

}
