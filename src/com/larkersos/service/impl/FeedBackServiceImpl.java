package com.larkersos.service.impl;

import javax.annotation.Resource;

import org.compass.core.CompassTemplate;
import org.springframework.stereotype.Service;

import com.larkersos.dao.FeedBackDao;
import com.larkersos.entity.FeedBack;
import com.larkersos.service.FeedBackService;
import com.larkersos.service.HtmlService;

/**
 * Service实现类 - 商品
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Service
public class FeedBackServiceImpl extends BaseServiceImpl<FeedBack, String> implements FeedBackService {

	@Resource
	private FeedBackDao feedBackDao;
	@Resource
	private CompassTemplate compassTemplate;
	@Resource
	private HtmlService htmlService;

	@Resource
	public void setBaseDao(FeedBackDao feedBackDao) {
		super.setBaseDao(feedBackDao);
	}
}