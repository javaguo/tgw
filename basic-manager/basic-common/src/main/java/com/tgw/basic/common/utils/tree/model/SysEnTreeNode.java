package com.tgw.basic.common.utils.tree.model;


import com.tgw.basic.core.model.AbstractBaseBean;

import java.util.List;


public class SysEnTreeNode extends AbstractBaseBean {

	private String id;
	private String text;
	private String parentId;

	private String qtip;
	private boolean isLeaf;//是否叶子节点
	private boolean isExpanded;//是否展开子节点
	private boolean isChecked;//树节点勾选框

	private List<SysEnTreeNode> children;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean leaf) {
		isLeaf = leaf;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean expanded) {
		isExpanded = expanded;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	public List<SysEnTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<SysEnTreeNode> children) {
		this.children = children;
	}
}
