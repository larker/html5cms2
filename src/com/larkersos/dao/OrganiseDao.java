package com.larkersos.dao;

import java.util.List;

import com.larkersos.entity.Organise;


/**
 * Dao接口 - 组织单位
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

public interface OrganiseDao extends BaseDao<Organise, String> {
	
	/**
	 * 根据起始结果数、最大结果数，获取所有商品（只包含isMarketable=true的对象）
	 *            
	 * @param firstResult
	 *            起始结果数
	 *            
	 * @param maxResults
	 *            最大结果数
	 * 
	 * @return 此分类下的所有商品集合
	 */
	public List<Organise> getOrganiseList(int firstResult, int maxResults);
	
}