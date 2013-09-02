package com.larkersos.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.larkersos.dao.BrandDao;
import com.larkersos.entity.Brand;
import com.larkersos.service.BrandService;

/**
 * Service实现类 - 品牌
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Service
public class BrandServiceImpl extends BaseServiceImpl<Brand, String> implements BrandService {

	@Resource
	public void setBaseDao(BrandDao brandDao) {
		super.setBaseDao(brandDao);
	}

}