package com.larkersos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;

/**
 * 实体类 - 组织机构
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Entity
@Searchable
public class Organise extends BaseEntity {
	private static final long serialVersionUID = 4858058186018438871L;
	
	private String parentId;// 上级单位
	private String code;// 单位名称
	private String name;// 单位名称
	private String addr;// 单位地址

	private String phone;// 单位电话
	private String contact;// 单位联系人
	private String logoPath;// 单位logo地址
	
	
	private String aboutUs;// 关于我们描述
	private String description;// 单位描述
	private String metaKeywords;// 页面关键词
	private String metaDescription;// 页面描述
	private String htmlFilePath;// HTML静态文件路径
	
	private String appTitle;// 单位APP名称
	private String appDomain;// 单位domain
	public String getAppTitle() {
		return appTitle;
	}
	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}
	public String getAppDomain() {
		return appDomain;
	}
	public void setAppDomain(String appDomain) {
		this.appDomain = appDomain;
	}
	@SearchableProperty(store = Store.YES)
	@Column(nullable = true)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@SearchableProperty(store = Store.YES)
	@Column(nullable = true)
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@SearchableProperty(store = Store.YES)
	@Column(nullable = true)
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	@SearchableProperty(store = Store.YES)
	@Column(nullable = true)
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentId() {
		return parentId;
	}
	
	@SearchableProperty(store = Store.YES)
	@Column(nullable = false)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@SearchableProperty(store = Store.YES)
	@Column(nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@SearchableProperty(store = Store.YES)
	@Column(nullable = false)
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(length = 1000)
	public String getAboutUs() {
		return aboutUs;
	}
	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}


	@Column(length = 10000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(length = 5000)
	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	@Column(length = 5000)
	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}
	
	@SearchableProperty(index = Index.NO, store = Store.YES)
	@Column(nullable = false, updatable = false)
	public String getHtmlFilePath() {
		return htmlFilePath;
	}

	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}

}