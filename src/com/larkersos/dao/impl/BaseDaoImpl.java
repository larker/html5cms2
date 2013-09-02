package com.larkersos.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.larkersos.bean.Pager;
import com.larkersos.bean.Pager.OrderType;
import com.larkersos.dao.BaseDao;
import com.larkersos.util.SystemConfigUtil;

/**
 * Dao实现类 - Dao实现类基类
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Repository
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

	private Class<T> entityClass;
	protected SessionFactory sessionFactory;
	// larkersos-departmentId
	protected String departmentId;
	
	/**
	 * 获取当前登录用户的组织单位
	 */
	protected String getDepartmentId() {
		// 当前登录用户信息 larkersos-departmentId
		departmentId = SystemConfigUtil.getDepartmentId();
		return departmentId;
	}

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.entityClass = null;
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public T load(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> get(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		String hql = "from " + entityClass.getName() + " as model where model.id in(:ids)";
		return getSession().createQuery(hql).setParameterList("ids", ids).list();
	}

	@SuppressWarnings("unchecked")
	public T get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment(entityClass.getName(),getDepartmentId())){
			hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ? and model.departmentId  = ? ";
			return (T) getSession().createQuery(hql).setParameter(0, value).setParameter(1, departmentId).uniqueResult();
		}else{
			return (T) getSession().createQuery(hql).setParameter(0, value).uniqueResult();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment(entityClass.getName(),getDepartmentId())){
			hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ? and model.departmentId  = ?";
			return getSession().createQuery(hql).setParameter(0, value).setParameter(1, departmentId).list();
		}else{
			return getSession().createQuery(hql).setParameter(0, value).list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		String hql = "from " + entityClass.getName();
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment(entityClass.getName(),getDepartmentId())){
			hql = "from " + entityClass.getName() + " as model where model.departmentId  = ?";
			return getSession().createQuery(hql).setParameter(0, departmentId).list();
		}else{
			return getSession().createQuery(hql).list();
		}
		
		
	}
	
	public Long getTotalCount() {
		String hql = "select count(*) from " + entityClass.getName();
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

	public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null && StringUtils.equalsIgnoreCase((String) oldValue, (String) newValue)) {
				return true;
			}
		}
		T object = get(propertyName, newValue);
		return (object == null);
	}
	
	public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		T object = get(propertyName, value);
		return (object != null);
	}

	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		return (PK) getSession().save(entity);
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().update(entity);
	}

	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().delete(entity);
	}

	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		T entity = load(id);
		getSession().delete(entity);
	}

	public void delete(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = load(id);
			getSession().delete(entity);
		}
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	public void evict(Object object) {
		Assert.notNull(object, "object is required");
		getSession().evict(object);
	}
	
	public Pager findByPager(Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		return findByPager(pager, detachedCriteria);
	}

	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();
		
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			String propertyString = "";
			if (property.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property, ".");
				String propertySuffix = StringUtils.substringAfter(property, ".");
				criteria.createAlias(propertyPrefix, "model");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			criteria.add(Restrictions.like(propertyString, "%" + keyword + "%"));
		}
		
		// 判断是否需要按组织单位部门查询 larkersos
		if (StringUtils.isEmpty(pager.getDepartmentId())) {
			pager.setDepartmentId(SystemConfigUtil.getDepartmentId());
		}
		if (!SystemConfigUtil.DEPARTMENTID_ADMIN.equals(pager.getDepartmentId())) {
			criteria.add(Restrictions.eq("departmentId", pager.getDepartmentId()));
		}

		Integer totalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
	}

}