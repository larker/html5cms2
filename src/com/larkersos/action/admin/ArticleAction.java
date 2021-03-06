package com.larkersos.action.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.jsp.PageContext;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.larkersos.entity.Article;
import com.larkersos.entity.ArticleCategory;
import com.larkersos.service.ArticleCategoryService;
import com.larkersos.service.ArticleService;
import com.larkersos.util.SystemConfigUtil;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 文章
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@ParentPackage("admin")
public class ArticleAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;

	private Article article;
	private List<ArticleCategory> articleCategoryTreeList;

	@Resource
	private ArticleService articleService;
	@Resource
	private ArticleCategoryService articleCategoryService;

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		article = articleService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		adminDepartment = getAdminDepartment();
		pager = articleService.findByPager(pager);
		
		return LIST;
	}

	// 删除
	public String delete() throws Exception {
		articleService.delete(ids);
		flushCache();
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
			@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!"),
			@RequiredStringValidator(fieldName = "article.content", message = "文章内容不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "article.isPublication", message = "是否发布不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isTop", message = "是否置顶不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isRecommend", message = "是否推荐不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		article.setHits(0);
		// 当前登录用户信息 larkersos-department
		if(article.getDepartment() == null){
			article.setDepartment(SystemConfigUtil.getDepartment());
		}
		if(article.getDepartmentId() == null){
			article.setDepartmentId(SystemConfigUtil.getDepartmentId());
		}
		articleService.save(article);
		flushCache();
		redirectionUrl = "article!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
			@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!"),
			@RequiredStringValidator(fieldName = "article.content", message = "文章内容不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "article.isPublication", message = "是否发布不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isTop", message = "是否置顶不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isRecommend", message = "是否推荐不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Article persistent = articleService.load(id);
		// 当前登录用户信息 larkersos-department
		if(article.getDepartment() == null){
			article.setDepartment(SystemConfigUtil.getDepartment());
		}
		if(article.getDepartmentId() == null){
			article.setDepartmentId(SystemConfigUtil.getDepartmentId());
		}
		BeanUtils.copyProperties(article, persistent, new String[] {"id", "createDate", "modifyDate", "pageCount", "htmlFilePath", "hits"});
		articleService.update(persistent);
		flushCache();
		redirectionUrl = "article!list.action";
		return SUCCESS;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<ArticleCategory> getArticleCategoryTreeList() {
		articleCategoryTreeList = articleCategoryService.getArticleCategoryTreeList();
		return articleCategoryTreeList;
	}

	public void setArticleCategoryTreeList(List<ArticleCategory> articleCategoryTreeList) {
		this.articleCategoryTreeList = articleCategoryTreeList;
	}


}