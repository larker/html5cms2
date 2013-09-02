package com.larkersos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.larkersos.dao.AdminDao;
import com.larkersos.entity.Admin;
import com.larkersos.service.AdminService;
import com.larkersos.util.SystemConfigUtil;

/**
 * Service实现类 - 管理员
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, String> implements AdminService {

	@Resource
	private AdminDao adminDao;

	@Resource
	public void setBaseDao(AdminDao adminDao) {
		super.setBaseDao(adminDao);
	}

	public Admin getLoginAdmin() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication == null) {
//			return null;
//		}
//		Object principal = authentication.getPrincipal();
//		if (principal == null || !(principal instanceof Admin)) {
//			return null;
//		} else {
//			return (Admin) principal;
//		}
		
		return SystemConfigUtil.getLoginAdmin();
	}

	public Admin loadLoginAdmin() {
		Admin admin = getLoginAdmin();
		if (admin == null) {
			return null;
		} else {
			return adminDao.load(admin.getId());
		}
	}
	
	public boolean isExistByUsername(String username) {
		return adminDao.isExistByUsername(username);
	}
	
	public Admin getAdminByUsername(String username) {
		return adminDao.getAdminByUsername(username);
	}

}