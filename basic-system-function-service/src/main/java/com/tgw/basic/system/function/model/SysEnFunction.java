package com.tgw.basic.system.function.model;


import com.tgw.basic.core.model.AbstractBaseBean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="sys_en_function")
public class SysEnFunction extends AbstractBaseBean {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	private Integer fkParentId;
	private Integer fkSysEnMenuId;
	private String funcIdentify;
	private String funcName;
	private String funcCode;
	private Boolean isLeaf;//是否叶子节点
	private Boolean isExpanded;//是否展开子节点
	private Integer orderNum;//排序编码
	private Date addTime;
	private Date updateTime;


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

	public Integer getFkSysEnMenuId() {
		return fkSysEnMenuId;
	}

	public void setFkSysEnMenuId(Integer fkSysEnMenuId) {
		this.fkSysEnMenuId = fkSysEnMenuId;
	}

	public String getFuncIdentify() {
		return funcIdentify;
	}

	public void setFuncIdentify(String funcIdentify) {
		this.funcIdentify = funcIdentify;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public Boolean getLeaf() {
		return isLeaf;
	}

	public void setLeaf(Boolean leaf) {
		isLeaf = leaf;
	}

	public Boolean getExpanded() {
		return isExpanded;
	}

	public void setExpanded(Boolean expanded) {
		isExpanded = expanded;
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
}
