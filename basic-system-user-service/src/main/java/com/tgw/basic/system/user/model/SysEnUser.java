package com.tgw.basic.system.user.model;


import com.tgw.basic.core.model.AbstractBaseBean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Table(name="sys_en_user")
public class SysEnUser extends AbstractBaseBean {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	private String userName;
	private String loginName;
	private String password;
	private String token;
	private String userStatus;
	private String theme;
	private String remark;
	private Date addTime;
	private Date updateTime;

	@Transient
	private String reUserRoleId;
	@Transient
	private List<String> reUserRoleIdList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getReUserRoleId() {
		return reUserRoleId;
	}

	public void setReUserRoleId(String reUserRoleId) {
		this.reUserRoleId = reUserRoleId;
	}

	public List<String> getReUserRoleIdList() {
		return reUserRoleIdList;
	}

	public void setReUserRoleIdList(List<String> reUserRoleIdList) {
		this.reUserRoleIdList = reUserRoleIdList;
	}
}
