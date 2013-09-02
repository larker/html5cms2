package com.larkersos.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.larkersos.bean.Pager;
import com.larkersos.dao.ArticleDao;
import com.larkersos.entity.Article;
import com.larkersos.entity.ArticleCategory;
import com.larkersos.util.SystemConfigUtil;

/**
 * Dao实现类 - 文章
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Repository
public class ArticleDaoImpl extends BaseDaoImpl<Article, String> implements ArticleDao {

	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(ArticleCategory articleCategory) {
		String hql = "from Article as article where article.isPublication = ? and article.articleCategory.path like ? order by article.isTop desc, article.createDate desc";
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Article",getDepartmentId())){
			hql = "from Article as article where article.isPublication = ? and article.articleCategory.path like ?" +
					" and article.departmentId  = ? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory.getPath() + "%").setParameter(2, departmentId).list();
		}else{
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory.getPath() + "%").list();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(int firstResult, int maxResults) {
		String hql = "from Article as article where article.isPublication = ? order by article.isTop desc, article.createDate desc";
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Article",getDepartmentId())){
			hql = "from Article as article where article.isPublication = ?" +
					" and article.departmentId  = ? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, departmentId).setFirstResult(firstResult).setMaxResults(maxResults).list();
		}else{
			return getSession().createQuery(hql).setParameter(0, true).setFirstResult(firstResult).setMaxResults(maxResults).list();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(ArticleCategory articleCategory, int firstResult, int maxResults) {
		String hql = "from Article as article where article.isPublication = ? and article.articleCategory.path like ? order by article.isTop desc, article.createDate desc";
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Article",getDepartmentId())){
			hql = "from Article as article where article.isPublication = ? and article.articleCategory.path like ?" +
					" and article.departmentId  = ? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, articleCategory.getPath() + "%")
					.setParameter(2, departmentId)
					.setFirstResult(firstResult).setMaxResults(maxResults).list();
		}else{
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory.getPath() + "%").setFirstResult(firstResult).setMaxResults(maxResults).list();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(Date beginDate, Date endDate, int firstResult, int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Article",getDepartmentId())){
			if (beginDate != null && endDate == null) {
				String hql = "from Article as article where article.isPublication = ? and article.createDate >= ?" +
						" and article.departmentId  = ?  order by article.isTop desc, article.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true)
						.setParameter(1, beginDate)
						.setParameter(2, departmentId)
						.setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else if (endDate != null && beginDate == null) {
				String hql = "from Article as article where article.isPublication = ? and article.createDate <= ?" +
						" and article.departmentId  = ?  order by article.isTop desc, article.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true)
						.setParameter(1, endDate)
						.setParameter(2, departmentId)
						.setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else if (endDate != null && beginDate != null) {
				String hql = "from Article as article where article.isPublication = ? and article.createDate >= ? and article.createDate <= ?" +
						" and article.departmentId  = ?  order by article.isTop desc, article.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true)
						.setParameter(1, beginDate)
						.setParameter(2, endDate)
						.setParameter(3, departmentId)
						.setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else {
				String hql = "from Article as article where article.isPublication = ?" +
						" and article.departmentId  = ?  order by article.isTop desc, article.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true)
						.setParameter(1, departmentId)
						.setFirstResult(firstResult).setMaxResults(maxResults).list();
			}
		}else{
			if (beginDate != null && endDate == null) {
				String hql = "from Article as article where article.isPublication = ? and article.createDate >= ? order by article.isTop desc, article.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else if (endDate != null && beginDate == null) {
				String hql = "from Article as article where article.isPublication = ? and article.createDate <= ? order by article.isTop desc, article.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true).setParameter(1, endDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else if (endDate != null && beginDate != null) {
				String hql = "from Article as article where article.isPublication = ? and article.createDate >= ? and article.createDate <= ? order by article.isTop desc, article.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setParameter(2, endDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else {
				String hql = "from Article as article where article.isPublication = ? order by article.isTop desc, article.createDate desc";
				return getSession().createQuery(hql).setParameter(0, true).setFirstResult(firstResult).setMaxResults(maxResults).list();
			}
		}
	}
	
	public Pager getArticlePager(ArticleCategory articleCategory, Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
		detachedCriteria.createAlias("articleCategory", "articleCategory");
		detachedCriteria.add(Restrictions.or(Restrictions.eq("articleCategory", articleCategory), Restrictions.like("articleCategory.path", articleCategory.getPath() + "%")));
		detachedCriteria.add(Restrictions.eq("isPublication", true));
		return super.findByPager(pager, detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<Article> getRecommendArticleList(int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		return getRecommendArticleList(maxResults,true);
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getRecommendArticleList(int maxResults,boolean isRecommend) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Article",getDepartmentId())){
			String hql = "from Article as article where article.isPublication = ? and article.isRecommend = ?" +
					" and article.departmentId  = ?   order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, isRecommend)
					.setParameter(2, departmentId)
					.list();
		}else{
			String hql = "from Article as article where article.isPublication = ? and article.isRecommend = ? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, isRecommend).list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Article> getRecommendArticleList(ArticleCategory articleCategory, int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Article",getDepartmentId())){
			String hql = "from Article as article where article.isPublication = ? and article.isRecommend = ? and (articleCategory = ? or article.articleCategory.path like ?)" +
					" and article.departmentId  = ?  order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, true)
					.setParameter(2, articleCategory)
					.setParameter(3, articleCategory.getPath() + "%")
					.setParameter(4, departmentId)
					.list();
		}else{
			String hql = "from Article as article where article.isPublication = ? and article.isRecommend = ? and (articleCategory = ? or article.articleCategory.path like ?) order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, articleCategory).setParameter(3, articleCategory.getPath() + "%").list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Article> getHotArticleList(int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Article",getDepartmentId())){
			String hql = "from Article as article where article.isPublication = ?" +
					" and article.departmentId  = ?  order by article.hits desc, article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, departmentId)
					.list();
		}else{
			String hql = "from Article as article where article.isPublication = ? order by article.hits desc, article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Article> getHotArticleList(ArticleCategory articleCategory, int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Article",getDepartmentId())){
			String hql = "from Article as article where article.isPublication = ? and (articleCategory = ? or article.articleCategory.path like ?)" +
					" and article.departmentId  = ?  order by article.hits desc, article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, articleCategory)
					.setParameter(2, articleCategory.getPath() + "%")
					.setParameter(3, departmentId)
					.list();
		}else{
			String hql = "from Article as article where article.isPublication = ? and (articleCategory = ? or article.articleCategory.path like ?) order by article.hits desc, article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory).setParameter(2, articleCategory.getPath() + "%").list();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getNewArticleList(int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Article",getDepartmentId())){
			String hql = "from Article as article where article.isPublication = ?" +
					" and article.departmentId  = ?  order by article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, departmentId)
					.list();
		}else{
			String hql = "from Article as article where article.isPublication = ? order by article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Article> getNewArticleList(ArticleCategory articleCategory, int maxResults) {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Article",getDepartmentId())){
			String hql = "from Article as article where article.isPublication = ? and (articleCategory = ? or article.articleCategory.path like ?)" +
					" and article.departmentId  = ?  order by article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true)
					.setParameter(1, articleCategory)
					.setParameter(2, articleCategory.getPath() + "%")
					.setParameter(3, departmentId)
					.list();
		}else{
			String hql = "from Article as article where article.isPublication = ? and (articleCategory = ? or article.articleCategory.path like ?) order by article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory).setParameter(2, articleCategory.getPath() + "%").list();
		}
	}
	
	// 根据isTop、createDate进行排序
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getAll() {
		// 当前登录用户信息 larkersos-departmentId
		// 如果当前实体表需要使用departmentId并且当前登录非管理员
		if(SystemConfigUtil.isFilterDepartment("Article",getDepartmentId())){
			String hql = "from Article as article" +
					" and article.departmentId  = ?  order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql)
					.setParameter(0, departmentId)
					.list();
		}else{
			String hql = "from Article as article order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).list();
		}
	}

}