package com.larkersos.dao.impl;

import java.util.Set;


import org.springframework.stereotype.Repository;

import com.larkersos.dao.BrandDao;
import com.larkersos.entity.Brand;
import com.larkersos.entity.Product;

/**
 * Dao实现类 - 品牌
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Repository
public class BrandDaoImpl extends BaseDaoImpl<Brand, String> implements BrandDao {
	
	// 关联处理
	@Override
	public void delete(Brand brand) {
		Set<Product> productSet = brand.getProductSet();
		if (productSet != null) {
			for (Product product : productSet) {
				product.setBrand(null);
			}
		}
		super.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Brand brand = load(id);
		this.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Brand brand = load(id);
			this.delete(brand);
		}
	}
	
}