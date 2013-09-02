package com.larkersos.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.larkersos.dao.LogDao;
import com.larkersos.entity.Log;
import com.larkersos.service.LogService;

/**
 * Service实现类 - 日志
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Service
public class LogServiceImpl extends BaseServiceImpl<Log, String> implements LogService {

	@Resource
	public void setBaseDao(LogDao logDao) {
		super.setBaseDao(logDao);
	}

}
