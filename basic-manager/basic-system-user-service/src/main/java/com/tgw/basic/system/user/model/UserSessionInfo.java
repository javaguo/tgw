package com.tgw.basic.system.user.model;


import com.tgw.basic.core.model.AbstractBaseBean;
import com.tgw.basic.system.role.model.SysEnRole;

import java.util.List;

public class UserSessionInfo extends AbstractBaseBean {

	private SysEnUser sysEnUser;
	private List<SysEnRole> sysEnRoleList;

	public SysEnUser getSysEnUser() {
		return sysEnUser;
	}

	public void setSysEnUser(SysEnUser sysEnUser) {
		this.sysEnUser = sysEnUser;
	}

	public List<SysEnRole> getSysEnRoleList() {
		return sysEnRoleList;
	}

	public void setSysEnRoleList(List<SysEnRole> sysEnRoleList) {
		this.sysEnRoleList = sysEnRoleList;
	}
}
