package com.larkersos.service;

import java.util.List;

import com.larkersos.entity.Organise;


/**
 * Service接口 - 商品
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

public interface OrganiseService extends BaseService<Organise, String> {
	
	/**
	 * 根据起始结果数、最大结果数，获取所有单位信息
	 * 
	 * @param firstResult
	 *            起始结果数
	 * 
	 * @param maxResults
	 *            最大结果数
	 * 
	 * @return 此分类下的所有集合
	 */
	public List<Organise> getOrganiseList(int firstResult, int maxResults);

}