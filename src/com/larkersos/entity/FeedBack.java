package com.larkersos.entity;

import javax.persistence.Entity;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 意见反馈
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Entity
@Searchable
public class FeedBack extends BaseEntity {
	private static final long serialVersionUID = 4858058186018438871L;
	
	private String departmentId;	// 组织部门ID
	private String department;		// 部门名称
	private String contact;			// 联系人Email，PHONE
	private String content;			// 反馈信息
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}


}