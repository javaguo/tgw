package com.tgw.basic.system.user.service.impl;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.impl.BaseServiceImpl;
import com.tgw.basic.system.menu.model.SysEnMenu;
import com.tgw.basic.system.role.model.SysEnRole;
import com.tgw.basic.system.user.dao.SysEnUserMapper;
import com.tgw.basic.system.user.dao.SysReUserRoleMapper;
import com.tgw.basic.system.user.model.SysEnUser;
import com.tgw.basic.system.user.model.SysReUserRole;
import com.tgw.basic.system.user.service.SysEnUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("sysEnUserService")
public class SysEnUserServiceImpl extends BaseServiceImpl implements SysEnUserService {

	@Resource
	private SysEnUserMapper sysEnUserMapper;
	@Resource
	private SysReUserRoleMapper sysReUserRoleMapper;

	@Override
	public void initMapper() {
		if( null!=sysEnUserMapper ){
			super.setBaseModelMapper( this.getSysEnUserMapper() );
		}
	}

	public SysEnUser queryUserByLoginName(String loginName) throws PlatformException {
		if( StringUtils.isBlank( loginName ) ){
			throw new PlatformException("用户名不能为空！");
		}
		SysEnUser sysEnUser = this.getSysEnUserMapper().queryUserByLoginName( loginName.trim() );
		return sysEnUser;
	}


	public void saveSysUser(SysEnUser sysEnUser) throws PlatformException {
		this.getSysEnUserMapper().insert( sysEnUser );
		this.saveSysReUserRole( sysEnUser.getId(),sysEnUser.getReUserRoleId() );
	}

	public void updateSysUserAndRoleInfo(SysEnUser sysEnUser) throws PlatformException {
		this.getSysEnUserMapper().updateByPrimaryKey( sysEnUser );

		this.deleteSysReUserRole( sysEnUser.getId() );
		this.saveSysReUserRole( sysEnUser.getId(),sysEnUser.getReUserRoleId() );
	}

	public void updateSysUserBaseInfo(SysEnUser sysEnUser){
		this.getSysEnUserMapper().updateByPrimaryKey( sysEnUser );
	}

	public void deleteSysUser(List<Object> beanList) throws PlatformException {
		if( beanList!=null  ){
			for( Object obj :beanList ){
				SysEnUser sysEnUser = (SysEnUser)obj;
				this.deleteSysReUserRole( sysEnUser.getId() );
				this.getSysEnUserMapper().delete( sysEnUser );
			}
		}
	}

	public void saveSysReUserRole(Integer userId, String roleIds) throws PlatformException {
		if(StringUtils.isNotBlank( roleIds )){
			String[] roleIdArray = roleIds.split(",");
			for( int i=0;i<roleIdArray.length;i++ ){
				if( StringUtils.isNotBlank( roleIdArray[i] ) ){
					SysReUserRole sysReUserRole = new SysReUserRole();

					int roleId = Integer.parseInt( roleIdArray[i].trim() );
					sysReUserRole.setFkRoleId( roleId );
					sysReUserRole.setFkUserId( userId );

					this.getSysReUserRoleMapper().insert( sysReUserRole );
				}
			}
		}
	}

	public void deleteSysReUserRole(Integer userId) throws PlatformException {
		this.getSysReUserRoleMapper().deleteUserRoleByUserId( userId );
	}

	public List<Map<String, Object>> loadUserRoleByUserId(Integer userId) throws PlatformException {
		return this.getSysReUserRoleMapper().loadUserRoleByUserId( userId );
	}

	public List<SysEnRole> loadSysEnRolesByUserId(Integer userId) throws PlatformException {
		return this.getSysReUserRoleMapper().loadSysEnRolesByUserId( userId );
	}

	public List<Map<String, Object>> queryRoleTreeMap() throws PlatformException {
		return this.getSysEnUserMapper().queryRoleTreeMap();
	}

	public List<SysEnMenu> loadMenuByUser(Integer userId) {
		List result = this.getSysEnUserMapper().loadMenuByUser( userId );
		return result;
	}

	public SysEnUserMapper getSysEnUserMapper() {
		return sysEnUserMapper;
	}

	public void setSysEnUserMapper(SysEnUserMapper sysEnUserMapper) {
		this.sysEnUserMapper = sysEnUserMapper;
	}

	public SysReUserRoleMapper getSysReUserRoleMapper() {
		return sysReUserRoleMapper;
	}

	public void setSysReUserRoleMapper(SysReUserRoleMapper sysReUserRoleMapper) {
		this.sysReUserRoleMapper = sysReUserRoleMapper;
	}
}
