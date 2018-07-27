package com.tgw.basic.system.menu.model;


import com.tgw.basic.core.model.AbstractBaseBean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Table(name="sys_en_menu")
public class SysEnMenu extends AbstractBaseBean {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
    private String menuIdentify;//菜单唯一字符串标识符
	private String text;//菜单名称
	private String link;//菜单链接地址
	private String qtip;//菜单提示
	private Boolean isLeaf;//是否叶子节点
	private Boolean isExpanded;//是否展开子节点
	private Integer fkParentId;
	private Boolean isSelfUrl;//是否为平台自身内部url地址。1：是，0：否
	private Integer orderNum;//排序编码
	private Date addTime;
	private Date updateTime;
	@Transient
	private List<SysEnMenu> children;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuIdentify() {
		return menuIdentify;
	}

	public void setMenuIdentify(String menuIdentify) {
		this.menuIdentify = menuIdentify;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
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

	public Integer getFkParentId() {
		return fkParentId;
	}

	public void setFkParentId(Integer fkParentId) {
		this.fkParentId = fkParentId;
	}

	public Boolean getSelfUrl() {
		return isSelfUrl;
	}

	public void setSelfUrl(Boolean selfUrl) {
		isSelfUrl = selfUrl;
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

	public List<SysEnMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysEnMenu> children) {
		this.children = children;
	}


}
