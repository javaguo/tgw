package com.tgw.basic.system.role.service;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.BaseService;
import com.tgw.basic.system.role.model.SysEnRole;

import java.util.List;
import java.util.Map;


public interface SysEnRoleService extends BaseService {

	/**
	 * 保存角色
	 * @param sysEnRole
	 * @throws PlatformException
     */
	public void saveSysRole(SysEnRole sysEnRole)  throws PlatformException;

	/**
	 * 更新角色
	 * @param sysEnRole
	 * @throws PlatformException
     */
	public void updateSysRole(SysEnRole sysEnRole)  throws PlatformException;

	/**
	 * 批量删除角色
	 * @param beanList
	 * @throws PlatformException
     */
	public void deleteSysRoles(List<Object>  beanList)  throws PlatformException;

	/**
	 * 保存角色拥有的菜单
	 * @param roleId
	 * @param menuIds
	 * @throws PlatformException
     */
	public void saveSysRoleMenu( Integer roleId,String menuIds ) throws PlatformException;

	/**
	 * 删除角色拥有的菜单
	 * @param roleId
	 * @throws PlatformException
     */
	public void deleteSysRoleMenu( Integer roleId ) throws PlatformException;

	/**
	 * 加载角色拥有的菜单
	 * @param roleId
     */
	public List<Map<String,Object>> loadRoleMenuByRoleId( Integer roleId ) throws PlatformException;

	/**
	 * 保存角色拥有的功能
	 * @param roleId
	 * @param funcIds
	 * @throws PlatformException
     */
	public void saveSysRoleFunc( Integer roleId,String funcIds ) throws PlatformException;

	/**
	 * 删除角色拥有的功能
	 * @param roleId
	 * @throws PlatformException
     */
	public void deleteSysRoleFunc( Integer roleId ) throws PlatformException;

	/**
	 * 加载角色拥有的功能权限
	 * @param roleId
	 */
	public List<Map<String,Object>> loadRoleFuncByRoleId(Integer roleId ) throws PlatformException;

	/**
	 * 查询表单中角色树控件的树节点数据
	 * @return
	 * @throws PlatformException
	 */
	public abstract List<Map<String,Object>> queryRoleTreeMap()  throws PlatformException;

	/**
	 * 查询表单中菜单树控件的树节点数据
	 * @return
	 * @throws PlatformException
	 */
	public abstract List<Map<String,Object>> queryMenuTreeMap()  throws PlatformException;

	/**
	 * 查询表单中功能树控件的树节点数据
	 * @return
	 * @throws PlatformException
	 */
	public abstract List<Map<String,Object>> queryFunctionTreeMap()  throws PlatformException;

}
