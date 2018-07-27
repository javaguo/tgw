package com.tgw.basic.system.user.model;


import com.tgw.basic.core.model.AbstractBaseBean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sys_re_user_role")
public class SysReUserRole extends AbstractBaseBean {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	private Integer fkRoleId;
	private Integer fkUserId;

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

	public Integer getFkUserId() {
		return fkUserId;
	}

	public void setFkUserId(Integer fkUserId) {
		this.fkUserId = fkUserId;
	}
}
