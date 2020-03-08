package com.tgw.basic.system.role.service.impl;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.impl.BaseServiceImpl;
import com.tgw.basic.system.role.dao.SysEnRoleMapper;
import com.tgw.basic.system.role.dao.SysReRoleFuncMapper;
import com.tgw.basic.system.role.dao.SysReRoleMenuMapper;
import com.tgw.basic.system.role.model.SysEnRole;
import com.tgw.basic.system.role.model.SysReRoleFunc;
import com.tgw.basic.system.role.model.SysReRoleMenu;
import com.tgw.basic.system.role.service.SysEnRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("sysEnRoleService")
public class SysEnRoleServiceImpl extends BaseServiceImpl implements SysEnRoleService {

	@Resource
	private SysEnRoleMapper sysEnRoleMapper;
	@Resource
	private SysReRoleMenuMapper sysReRoleMenuMapper;
	@Resource
	private SysReRoleFuncMapper sysReRoleFuncMapper;

	@Override
	public void initMapper() {
		if( null!=sysEnRoleMapper ){
			super.setBaseModelMapper( this.getSysEnRoleMapper() );
		}
	}

	public void saveSysRole(SysEnRole sysEnRole)  throws PlatformException{
		this.getSysEnRoleMapper().insert( sysEnRole );

		this.saveSysRoleMenu( sysEnRole.getId(),sysEnRole.getReRoleMenuId() );
		this.saveSysRoleFunc( sysEnRole.getId(),sysEnRole.getReRoleFuncId() );

	}

	public void updateSysRole(SysEnRole sysEnRole) throws PlatformException {
		this.getSysEnRoleMapper().updateByPrimaryKey( sysEnRole );

		this.deleteSysRoleMenu( sysEnRole.getId() );
		this.saveSysRoleMenu( sysEnRole.getId(),sysEnRole.getReRoleMenuId() );

		this.deleteSysRoleFunc( sysEnRole.getId() );
		this.saveSysRoleFunc( sysEnRole.getId(),sysEnRole.getReRoleFuncId() );

	}

	public void deleteSysRoles(List<Object>  beanList)  throws PlatformException{
		if( beanList!=null  ){
			for( Object obj :beanList ){
				SysEnRole sysEnRole = (SysEnRole)obj;
				this.deleteSysRoleMenu( sysEnRole.getId() );
				this.deleteSysRoleFunc( sysEnRole.getId() );
				this.getSysEnRoleMapper().delete( sysEnRole );
			}
		}
	}

	public void saveSysRoleMenu(Integer roleId, String menuIds) throws PlatformException {
		if(StringUtils.isNotBlank( menuIds )){
			String[] menuIdArray = menuIds.split(",");
			for( int i=0;i<menuIdArray.length;i++ ){
				if( StringUtils.isNotBlank( menuIdArray[i] ) ){
					SysReRoleMenu sysReRoleMenu = new SysReRoleMenu();

					int menuId = Integer.parseInt( menuIdArray[i].trim() );
					sysReRoleMenu.setFkMenuId( menuId );
					sysReRoleMenu.setFkRoleId( roleId );

					this.getSysReRoleMenuMapper().insert( sysReRoleMenu );

				}
			}
		}
	}

	public void deleteSysRoleMenu(Integer roleId) throws PlatformException {
		this.getSysReRoleMenuMapper().deleteRoleMenuByRoleId( roleId );
	}

	public List<Map<String,Object>> loadRoleMenuByRoleId( Integer roleId ) throws PlatformException{
		return this.getSysReRoleMenuMapper().loadRoleMenuByRoleId( roleId );
	}

	public void saveSysRoleFunc(Integer roleId, String funcIds) throws PlatformException {
		if(StringUtils.isNotBlank( funcIds )){
			String[] funcIdArray = funcIds.split(",");
			for( int i=0;i<funcIdArray.length;i++ ){
				if( StringUtils.isNotBlank( funcIdArray[i] ) ){
					SysReRoleFunc sysReRoleFunc = new SysReRoleFunc();

					int funcId = Integer.parseInt( funcIdArray[i].trim() );
					sysReRoleFunc.setFkFuncId( funcId );
					sysReRoleFunc.setFkRoleId( roleId );

					this.getSysReRoleFuncMapper().insert( sysReRoleFunc );
				}
			}
		}
	}

	public void deleteSysRoleFunc(Integer roleId) throws PlatformException {
		this.getSysReRoleFuncMapper().deleteRoleFuncByRoleId( roleId );
	}

	public List<Map<String,Object>> loadRoleFuncByRoleId(Integer roleId ) throws PlatformException{
		return this.getSysReRoleFuncMapper().loadRoleFuncByRoleId( roleId );
	}

	public List<Map<String, Object>> queryRoleTreeMap() throws PlatformException {
		return this.getSysEnRoleMapper().queryRoleTreeMap();
	}

	public List<Map<String, Object>> queryMenuTreeMap() throws PlatformException {
		return this.getSysEnRoleMapper().queryMenuTreeMap();
	}

	public List<Map<String, Object>> queryFunctionTreeMap() throws PlatformException {
		return this.getSysEnRoleMapper().queryFunctionTreeMap();
	}

	public SysEnRoleMapper getSysEnRoleMapper() {
		return sysEnRoleMapper;
	}

	public void setSysEnRoleMapper(SysEnRoleMapper sysEnRoleMapper) {
		this.sysEnRoleMapper = sysEnRoleMapper;
	}

	public SysReRoleMenuMapper getSysReRoleMenuMapper() {
		return sysReRoleMenuMapper;
	}

	public void setSysReRoleMenuMapper(SysReRoleMenuMapper sysReRoleMenuMapper) {
		this.sysReRoleMenuMapper = sysReRoleMenuMapper;
	}

	public SysReRoleFuncMapper getSysReRoleFuncMapper() {
		return sysReRoleFuncMapper;
	}

	public void setSysReRoleFuncMapper(SysReRoleFuncMapper sysReRoleFuncMapper) {
		this.sysReRoleFuncMapper = sysReRoleFuncMapper;
	}
}
