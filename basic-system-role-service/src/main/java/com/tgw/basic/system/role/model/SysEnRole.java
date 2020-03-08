package com.tgw.basic.system.role.model;


import com.tgw.basic.core.model.AbstractBaseBean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Table(name="sys_en_role")
public class SysEnRole extends AbstractBaseBean {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	private Integer fkParentId;
	private String roleCode;
	private String roleName;
	private String roleStatus;
	@Transient
	private String reRoleMenuId;
	@Transient
	private String reRoleFuncId;
	private String remark;
	private Integer orderNum;//排序编码
	private Date addTime;
	private Date updateTime;
	@Transient
	private List<SysEnRole> children;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFkParentId() {
		return fkParentId;
	}

	public void setFkParentId(Integer fkParentId) {
		this.fkParentId = fkParentId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}

	public String getReRoleMenuId() {
		return reRoleMenuId;
	}

	public void setReRoleMenuId(String reRoleMenuId) {
		this.reRoleMenuId = reRoleMenuId;
	}

	public String getReRoleFuncId() {
		return reRoleFuncId;
	}

	public void setReRoleFuncId(String reRoleFuncId) {
		this.reRoleFuncId = reRoleFuncId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<SysEnRole> getChildren() {
		return children;
	}

	public void setChildren(List<SysEnRole> children) {
		this.children = children;
	}
}
