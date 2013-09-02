package com.larkersos.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.larkersos.entity.Admin;
import com.larkersos.util.SystemConfigUtil;

/**
 * @author larkersos
 * 
 */
public class OperateFilter implements Filter {
	private ApplicationContext ctx;

	private ServletContext application;
	public OperateFilter() {
	}

	public void destroy() {
	}

	/**
	 * @description 【请添加描述】
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 * @author larkersos
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		application = filterConfig.getServletContext();
		ctx = WebApplicationContextUtils.getWebApplicationContext(application);
	}

	/**
	 * @description 【请添加描述】
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 * @author larkersos
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		// 判断是否判断登录
		String requestURI =  httpRequest.getRequestURI().toLowerCase();
		if(requestURI.indexOf("/admin") >0 
				&& requestURI.endsWith("action")
			 	&& !requestURI.endsWith("admin!login.action")){
				// 判断是否是已经登录，如果没有登录，跳转到登录页
				Admin admin = getLoginAdmin();
				if(admin == null){
					String contextPath = httpRequest.getContextPath();
					try {
						httpResponse.sendRedirect(contextPath+"/admin/admin!login.action");
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		}
		
		chain.doFilter(request, response);
	}
	public Admin getLoginAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null || !(principal instanceof Admin)) {
			return null;
		} else {
			return (Admin) principal;
		}
	}
	
}