package com.larkersos.common;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;
import org.springframework.stereotype.Component;

import com.larkersos.entity.Resource;
import com.larkersos.service.ResourceService;

/**
 * 后台权限、资源对应关系
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Component
public class AdminSecurityDefinitionSource implements FactoryBean {

	@javax.annotation.Resource
	private ResourceService resourceService;

	public boolean isSingleton() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return FilterInvocationDefinitionSource.class;
	}

	protected UrlMatcher getUrlMatcher() {
		return new AntUrlPathMatcher();
	}

	public Object getObject() throws Exception {
		return new DefaultFilterInvocationDefinitionSource(this.getUrlMatcher(), this.buildRequestMap());
	}

	protected LinkedHashMap<RequestKey, ConfigAttributeDefinition> buildRequestMap() throws Exception {
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> resultMap = new LinkedHashMap<RequestKey, ConfigAttributeDefinition>();
		ConfigAttributeEditor configAttributeEditor = new ConfigAttributeEditor();
		Map<String, String> resourceMap = this.getResourceMap();
		for (Map.Entry<String, String> entry : resourceMap.entrySet()) {
			RequestKey key = new RequestKey(entry.getKey(), null);
			configAttributeEditor.setAsText(entry.getValue());
			resultMap.put(key, (ConfigAttributeDefinition) configAttributeEditor.getValue());
		}
		return resultMap;
	}

	protected Map<String, String> getResourceMap() {
		Map<String, String> resourceMap = new LinkedHashMap<String, String>();
		for (Resource resource : resourceService.getAll()) {
			String resourceValue = resource.getValue();
			if (StringUtils.isNotEmpty(resource.getRoleSetString())) {
				resourceMap.put(resourceValue, resource.getRoleSetString());
			}
		}
		return resourceMap;
	}

}