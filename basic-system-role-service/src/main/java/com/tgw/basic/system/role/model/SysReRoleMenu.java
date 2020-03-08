package com.tgw.basic.system.role.model;


import com.tgw.basic.core.model.AbstractBaseBean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sys_re_role_menu")
public class SysReRoleMenu extends AbstractBaseBean {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	private Integer fkRoleId;
	private Integer fkMenuId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFkRoleId() {
		return fkRoleId;
	}

	public void setFkRoleId(Integer fkRoleId) {
		this.fkRoleId = fkRoleId;
	}

	public Integer getFkMenuId() {
		return fkMenuId;
	}

	public void setFkMenuId(Integer fkMenuId) {
		this.fkMenuId = fkMenuId;
	}
}
