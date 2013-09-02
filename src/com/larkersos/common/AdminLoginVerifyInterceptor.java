package com.larkersos.common;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 拦截器 - 判断cms操作员是否登录
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

public class AdminLoginVerifyInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -86246303854807787L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		return invocation.invoke();
	}

}