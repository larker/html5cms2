package com.larkersos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.larkersos.dao.OrganiseDao;
import com.larkersos.entity.Organise;

/**
 * Dao实现类 - 商品
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Repository
public class OrganiseDaoImpl extends BaseDaoImpl<Organise, String> implements OrganiseDao {
	
	@SuppressWarnings("unchecked")
	public List<Organise> getOrganiseList(int firstResult, int maxResults) {
		String hql = "from Organise as organise order by product.createDate desc";
		return getSession().createQuery(hql).setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

}