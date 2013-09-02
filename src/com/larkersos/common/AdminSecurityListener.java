package com.larkersos.common;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.Authentication;
import org.springframework.security.event.authentication.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.event.authentication.AuthenticationSuccessEvent;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.larkersos.bean.SystemConfig;
import com.larkersos.entity.Admin;
import com.larkersos.service.AdminService;
import com.larkersos.util.SystemConfigUtil;

/**
 * 监听器 - 后台登录成功、登录失败处理
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Component
@Transactional
public class AdminSecurityListener implements ApplicationListener {

	@Resource
	private AdminService adminService;
	@Resource
	private ServletContext servletContext;

	public void onApplicationEvent(ApplicationEvent event) {
		
		String k = (String) servletContext.getAttribute("CONFIG_FILE_NAME_KEY");
		if (!StringUtils.containsIgnoreCase(k, SystemConfigUtil.CONFIG_FILE_NAME)) {
			throw new RuntimeException();
		}
		
		// 登录成功：记录登录IP、清除登录失败次数
		if (event instanceof AuthenticationSuccessEvent) {
			AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent) event;
			Authentication authentication = (Authentication) authEvent.getSource();
			String loginIp = ((WebAuthenticationDetails)authentication.getDetails()).getRemoteAddress();
			Admin admin = (Admin) authentication.getPrincipal();
			admin.setLoginIp(loginIp);
			admin.setLoginDate(new Date());
			SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
			if (systemConfig.getIsLoginFailureLock() == false) {
				return;
			}
			admin.setLoginFailureCount(0);
			adminService.update(admin);
		}

		// 登录失败：增加登录失败次数
		if (event instanceof AuthenticationFailureBadCredentialsEvent) {
			AuthenticationFailureBadCredentialsEvent authEvent = (AuthenticationFailureBadCredentialsEvent) event;
			Authentication authentication = (Authentication) authEvent.getSource();
			String loginUsername = authentication.getName();
			SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
			if (systemConfig.getIsLoginFailureLock() == false) {
				return;
			}
			Admin admin = adminService.get("username", loginUsername);
			if (admin != null) {
				int loginFailureCount = admin.getLoginFailureCount() + 1;
				if (loginFailureCount >= systemConfig.getLoginFailureLockCount()) {
					admin.setIsAccountLocked(true);
					admin.setLockedDate(new Date());
				}
				admin.setLoginFailureCount(loginFailureCount);
				adminService.update(admin);
			}
		}

	}

}