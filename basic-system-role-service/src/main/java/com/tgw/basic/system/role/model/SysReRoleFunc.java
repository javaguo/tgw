package com.tgw.basic.system.role.model;


import com.tgw.basic.core.model.AbstractBaseBean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sys_re_role_func")
public class SysReRoleFunc extends AbstractBaseBean {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	private Integer fkRoleId;
	private Integer fkFuncId;

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

	public Integer getFkFuncId() {
		return fkFuncId;
	}

	public void setFkFuncId(Integer fkFuncId) {
		this.fkFuncId = fkFuncId;
	}
}
