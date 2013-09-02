package com.larkersos.action.admin;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import freemarker.template.TemplateException;

/**
 * 后台Action类 - 缓存
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@ParentPackage("admin")
public class CacheAction extends BaseAdminAction {

	private static final long serialVersionUID = 3290111140770511789L;

	@Resource
	private GeneralCacheAdministrator cacheManager;
	@Resource
	private FreemarkerManager freemarkerManager;
	
	// 刷新所有缓存
	public String flush() {
		cacheManager.flushAll();
		flushCache();
		try {
			ServletContext servletContext = ServletActionContext.getServletContext();
			freemarkerManager.getConfiguration(servletContext).clearTemplateCache();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

}