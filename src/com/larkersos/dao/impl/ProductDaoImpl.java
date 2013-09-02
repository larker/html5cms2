package com.larkersos.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.larkersos.bean.Pager;
import com.larkersos.dao.ProductDao;
import com.larkersos.entity.Product;
import com.larkersos.entity.ProductCategory;
import com.larkersos.util.SystemConfigUtil;

/**
 * Dao实现类 - 商品
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product, String> implements ProductDao {
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductList(ProductCategory productCategory) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "from Product as product where product.isMarketable = ? and product.productCategory.path like ?" +
					" and product.departmentId  = ?  order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, productCategory.getPath() + "%")
					.setParameter(2, departmentId)
					.list();		
		}else{
			String hql = "from Product as product where product.isMarketable = ? and product.productCategory.path like ? order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, productCategory.getPath() + "%").list();
	
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductList(int firstResult, int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "from Product as product where product.isMarketable = ?" +
					" and product.departmentId  = ? order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, departmentId)
					.setFirstResult(firstResult).setMaxResults(maxResults).list();
		}else{
			String hql = "from Product as product where product.isMarketable = ? order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setFirstResult(firstResult).setMaxResults(maxResults).list();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductList(ProductCategory productCategory, int firstResult, int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "from Product as product where product.isMarketable = ? and product.productCategory.path like ?" +
					" and product.departmentId  = ?  order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, productCategory.getPath() + "%")
					.setParameter(2, departmentId)
					.setFirstResult(firstResult).setMaxResults(maxResults).list();
	
		}else{
			String hql = "from Product as product where product.isMarketable = ? and product.productCategory.path like ? order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, productCategory.getPath() + "%").setFirstResult(firstResult).setMaxResults(maxResults).list();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductList(Date beginDate, Date endDate, int firstResult, int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			if (beginDate != null && endDate == null) {
				String hql = "from Product as product where product.isMarketable = ? and product.createDate > ?" +
						" and product.departmentId  = ? order by product.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true)
						.setParameter(1, beginDate)
						.setParameter(2, departmentId)
						.setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else if (endDate != null && beginDate == null) {
				String hql = "from Product as product where product.isMarketable = ? and product.createDate < ?" +
						" and product.departmentId  = ? order by product.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true)
						.setParameter(1, endDate)
						.setParameter(2, departmentId)
						.setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else if (endDate != null && beginDate != null) {
				String hql = "from Product as product where product.isMarketable = ? and product.createDate > ? and product.createDate < ?" +
						" and product.departmentId  = ? order by product.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true)
						.setParameter(1, beginDate)
						.setParameter(2, endDate)
						.setParameter(3, departmentId)
						.setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else {
				String hql = "from Product as product where product.isMarketable = ?" +
						" and product.departmentId  = ? order by product.createDate desc";
				return getSession().createQuery(hql)
						.setParameter(0, true)
						.setParameter(1, departmentId)
						.setFirstResult(firstResult).setMaxResults(maxResults).list();
			}
		}else{
			if (beginDate != null && endDate == null) {
				String hql = "from Product as product where product.isMarketable = ? and product.createDate > ? order by product.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else if (endDate != null && beginDate == null) {
				String hql = "from Product as product where product.isMarketable = ? and product.createDate < ? order by product.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true).setParameter(1, endDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else if (endDate != null && beginDate != null) {
				String hql = "from Product as product where product.isMarketable = ? and product.createDate > ? and product.createDate < ? order by product.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setParameter(2, endDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else {
				String hql = "from Product as product where product.isMarketable = ? order by product.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true).setFirstResult(firstResult).setMaxResults(maxResults).list();
			}
		}
	}
	
	public Pager getProductPager(ProductCategory productCategory, Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
		detachedCriteria.createAlias("productCategory", "productCategory");
		detachedCriteria.add(Restrictions.or(Restrictions.eq("productCategory", productCategory), Restrictions.like("productCategory.path", productCategory.getPath() + "%")));
		detachedCriteria.add(Restrictions.eq("isMarketable", true));
		return super.findByPager(pager, detachedCriteria);
	}

	
	@SuppressWarnings("unchecked")
	public List<Product> getBestProductList(int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		return getBestProductList(maxResults,true);
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getBestProductList(int maxResults, boolean isBest) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "from Product as product where product.isMarketable = ? and product.isBest = ?" +
					" and product.departmentId  = ?  order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, isBest)
					.setParameter(2, departmentId)
					.list();
		}else{
			String hql = "from Product as product where product.isMarketable = ? and product.isBest = ? order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, isBest).list();
		}
	}


	@SuppressWarnings("unchecked")
	public List<Product> getBestProductList(ProductCategory productCategory, int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "from Product as product where product.isMarketable = ? and product.isBest = ? and (productCategory = ? or product.productCategory.path like ?)" +
					" and product.departmentId  = ?  order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, true)
					.setParameter(2, productCategory)
					.setParameter(3, productCategory.getPath() + "%")
					.setParameter(4, departmentId)
					.list();
		}else{
			String hql = "from Product as product where product.isMarketable = ? and product.isBest = ? and (productCategory = ? or product.productCategory.path like ?) order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, productCategory).setParameter(3, productCategory.getPath() + "%").list();

		}
	}

	@SuppressWarnings("unchecked")
	public List<Product> getHotProductList(int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "from Product as product where product.isMarketable = ? and product.isHot = ?" +
					" and product.departmentId  = ?  order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, true)
					.setParameter(2, departmentId)
					.list();
		}else{
			String hql = "from Product as product where product.isMarketable = ? and product.isHot = ? order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Product> getHotProductList(ProductCategory productCategory, int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "from Product as product where product.isMarketable = ? and product.isHot = ? and (productCategory = ? or product.productCategory.path like ?)" +
					" and product.departmentId  = ?  order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, true)
					.setParameter(2, productCategory)
					.setParameter(3, productCategory.getPath() + "%")
					.setParameter(4, departmentId)
					.list();
		}else{
			String hql = "from Product as product where product.isMarketable = ? and product.isHot = ? and (productCategory = ? or product.productCategory.path like ?) order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, productCategory).setParameter(3, productCategory.getPath() + "%").list();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getNewProductList(int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "from Product as product where product.isMarketable = ? and product.isNew = ?" +
					" and product.departmentId  = ?  order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, true)
					.setParameter(2, departmentId)
					.list();
		}else{
			String hql = "from Product as product where product.isMarketable = ? and product.isNew = ? order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Product> getNewProductList(ProductCategory productCategory, int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "from Product as product where product.isMarketable = ? and product.isNew = ? and (productCategory = ? or product.productCategory.path like ?)" +
					" and product.departmentId  = ?  order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, true)
					.setParameter(2, productCategory)
					.setParameter(3, productCategory.getPath() + "%")
					.setParameter(4, departmentId)
					.list();
		}else{
			String hql = "from Product as product where product.isMarketable = ? and product.isNew = ? and (productCategory = ? or product.productCategory.path like ?) order by product.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, productCategory).setParameter(3, productCategory.getPath() + "%").list();
		}
	}
	
	
	public Long getStoreAlertCount() {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "select count(*) from Product as product where product.isMarketable = ? and product.store - product.freezeStore <= ?" +
					" and product.departmentId  = ?  ";
			return (Long) getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, SystemConfigUtil.getSystemConfig().getStoreAlertCount())
					.setParameter(2, departmentId)
					.uniqueResult();
		}else{
			String hql = "select count(*) from Product as product where product.isMarketable = ? and product.store - product.freezeStore <= ?";
			return (Long) getSession().createQuery(hql).setParameter(0, true).setParameter(1, SystemConfigUtil.getSystemConfig().getStoreAlertCount()).uniqueResult();
		}
	}
	
	public Long getMarketableProductCount() {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "select count(*) from Product as product where product.isMarketable = ?" +
					" and product.departmentId  = ?  ";
			return (Long) getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, departmentId)
					.uniqueResult();
		}else{
			String hql = "select count(*) from Product as product where product.isMarketable = ?";
			return (Long) getSession().createQuery(hql).setParameter(0, true).uniqueResult();
		}
	}
	
	public Long getUnMarketableProductCount() {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Product",getDepartmentId())){
			String hql = "select count(*) from Product as product where product.isMarketable = ?" +
					"  and product.departmentId  = ? ";
			return (Long) getSession().createQuery(hql).setParameter(0, false)
					.setParameter(1, departmentId)
					.uniqueResult();
		}else{
			String hql = "select count(*) from Product as product where product.isMarketable = ?";
			return (Long) getSession().createQuery(hql).setParameter(0, false).uniqueResult();
		}
	}
	
	// 关联处理
	@Override
	public void delete(Product product) {
		super.delete(product);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Product product = load(id);
		this.delete(product);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Product product = load(id);
			this.delete(product);
		}
	}

}