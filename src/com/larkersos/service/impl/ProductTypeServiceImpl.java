package com.larkersos.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.larkersos.dao.ProductTypeDao;
import com.larkersos.entity.ProductType;
import com.larkersos.service.ProductTypeService;

/**
 * Service实现类 - 商品类型
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, String> implements
		ProductTypeService {

	@Resource
	public void setBaseDao(ProductTypeDao productTypeDao) {
		super.setBaseDao(productTypeDao);
	}

}
