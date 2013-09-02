package com.larkersos.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.larkersos.bean.HtmlConfig;
import com.larkersos.bean.SystemConfig;
import com.larkersos.dao.ArticleDao;
import com.larkersos.dao.OrganiseDao;
import com.larkersos.dao.ProductDao;
import com.larkersos.entity.Article;
import com.larkersos.entity.ArticleCategory;
import com.larkersos.entity.Organise;
import com.larkersos.entity.Product;
import com.larkersos.entity.ProductCategory;
import com.larkersos.service.ArticleCategoryService;
import com.larkersos.service.HtmlService;
import com.larkersos.service.NavigationService;
import com.larkersos.service.ProductCategoryService;
import com.larkersos.util.FilesUtil;
import com.larkersos.util.SystemConfigUtil;
import com.larkersos.util.TemplateConfigUtil;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Service实现类 - 生成静态
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Service
public class HtmlServiceImpl implements HtmlService {

	@Resource
	private FreemarkerManager freemarkerManager;
	@Resource
	private NavigationService navigationService;
	@Resource
	private ArticleCategoryService articleCategoryService;
	@Resource
	private ArticleDao articleDao;
	@Resource
	ProductCategoryService productCategoryService;
	@Resource
	private ProductDao productDao;
	@Resource
	private OrganiseDao organiseDao;

	public void buildHtml(String templateFilePath, String htmlFilePath,
			Map<String, Object> data) {
		try {
			ServletContext servletContext = ServletActionContext
					.getServletContext();
			Configuration configuration = freemarkerManager
					.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templateFilePath);
			File htmlFile = new File(servletContext.getRealPath(htmlFilePath));
			File htmlDirectory = htmlFile.getParentFile();
			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(htmlFile), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取公共数据
	public Map<String, Object> getCommonData() {
		Map<String, Object> commonData = new HashMap<String, Object>();
		ServletContext servletContext = ServletActionContext
				.getServletContext();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
		ResourceBundleModel resourceBundleModel = new ResourceBundleModel(
				resourceBundle, new BeansWrapper());
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();

		String priceCurrencyFormat = SystemConfigUtil.getPriceCurrencyFormat();
		String priceUnitCurrencyFormat = SystemConfigUtil
				.getPriceUnitCurrencyFormat();

		String orderCurrencyFormat = SystemConfigUtil.getOrderCurrencyFormat();
		String orderUnitCurrencyFormat = SystemConfigUtil
				.getOrderUnitCurrencyFormat();
		commonData.put("bundle", resourceBundleModel);
		commonData.put("base", servletContext.getContextPath());
		commonData.put("systemConfig", systemConfig);
		commonData.put("priceCurrencyFormat", priceCurrencyFormat);
		commonData.put("priceUnitCurrencyFormat", priceUnitCurrencyFormat);
		commonData.put("orderCurrencyFormat", orderCurrencyFormat);
		commonData.put("orderUnitCurrencyFormat", orderUnitCurrencyFormat);
		commonData.put("topNavigationList",
				navigationService.getTopNavigationList());
		commonData.put("middleNavigationList",
				navigationService.getMiddleNavigationList());
		commonData.put("bottomNavigationList",
				navigationService.getBottomNavigationList());

		return commonData;
	}

	public void baseJavascriptBuildHtml() {
		// 按工程项目copy公共js以及样式
		commonBuildHtml();

		// 公共js
		Map<String, Object> data = getCommonData();
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.BASE_JAVASCRIPT);
		if (htmlConfig != null) {
			String htmlFilePath = htmlConfig.getHtmlFilePath();
			String templateFilePath = htmlConfig.getTemplateFilePath();
			buildHtml(templateFilePath, htmlFilePath, data);
		}
	}

	public void commonBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.COMMON_FILE);
		if (htmlConfig != null) {
			String desFile = htmlConfig.getHtmlFilePath();
			String sourceFile = htmlConfig.getTemplateFilePath();
			try {
				FilesUtil.copyDirectiories(sourceFile, desFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void indexBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.INDEX);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			
			// 企业信息组织 larkersos
			Organise organise = organiseDao.get(SystemConfigUtil.getDepartmentId());
			data.put("organise" ,organise);
			data.put("appDomain" ,organise.getAppDomain());
			data.put("appTitle" ,organise.getAppTitle());
			if(StringUtils.isBlank(organise.getAppTitle())){
				data.put("appTitle" ,organise.getName());
			}

			// 商品信息
			// 热门首页展示
			data.put("hotProductList", productDao
					.getHotProductList(Product.MAX_HOT_PRODUCT_LIST_COUNT));
			// 一般
			data.put("productList", productDao
					.getBestProductList(Product.MAX_BEST_PRODUCT_LIST_COUNT,false));
			// 推荐
			data.put("bestProductList", productDao
					.getBestProductList(Product.MAX_BEST_PRODUCT_LIST_COUNT));
			
			// 文章资讯
			//推荐
			data.put(
					"recommendArticleList",
					articleDao
							.getRecommendArticleList(Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT));
			//一般
			data.put(
					"articleList",
					articleDao
							.getRecommendArticleList(Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT,false));
			
			String htmlFilePath = htmlConfig.getHtmlFilePath();
			String templateFilePath = htmlConfig.getTemplateFilePath();
			buildHtml(templateFilePath, htmlFilePath, data);
		}
	}

	public void loginBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.LOGIN);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			String htmlFilePath = htmlConfig.getHtmlFilePath();
			String templateFilePath = htmlConfig.getTemplateFilePath();
			buildHtml(templateFilePath, htmlFilePath, data);
		}
	}
	/**
	 * 生成文章资讯首页内容HTML静态文件
	 * 
	 * @param article
	 *            文章
	 */
	@Transactional
	public void articlePageBuildHtml(){
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ARTICLE);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("hotArticleList", articleDao.getHotArticleList(Article.MAX_HOT_ARTICLE_LIST_COUNT));
			data.put("newArticleList", articleDao.getNewArticleList(Article.MAX_NEW_ARTICLE_LIST_COUNT));
			
			String htmlFilePath = htmlConfig.getHtmlFilePath();
			String templateFilePath = htmlConfig.getTemplateFilePath();
			buildHtml(templateFilePath, htmlFilePath, data);
		}
	}
	@Transactional
	public void articleContentBuildHtml(Article article) {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ARTICLE_CONTENT);
		if (htmlConfig != null) {
			ArticleCategory articleCategory = article.getArticleCategory();
			Map<String, Object> data = getCommonData();
			data.put("article", article);
			data.put("pathList",
					articleCategoryService.getArticleCategoryPathList(article));
			data.put("rootArticleCategoryList",
					articleCategoryService.getRootArticleCategoryList());
			data.put("recommendArticleList", articleDao
					.getRecommendArticleList(articleCategory,
							Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT));
			data.put("hotArticleList", articleDao.getHotArticleList(
					articleCategory, Article.MAX_HOT_ARTICLE_LIST_COUNT));
			data.put("newArticleList", articleDao.getNewArticleList(
					articleCategory, Article.MAX_NEW_ARTICLE_LIST_COUNT));
			String htmlFilePath = article.getHtmlFilePath();
			String prefix = StringUtils.substringBeforeLast(htmlFilePath, ".");
			String extension = StringUtils
					.substringAfterLast(htmlFilePath, ".");
			List<String> pageContentList = article.getPageContentList();
			article.setPageCount(pageContentList.size());
			articleDao.update(article);
			articleDao.flush();
			for (int i = 0; i < pageContentList.size(); i++) {
				data.put("content", pageContentList.get(i));
				data.put("pageNumber", i + 1);
				data.put("pageCount", pageContentList.size());
				String templateFilePath = htmlConfig.getTemplateFilePath();
				String currentHtmlFilePath = null;
				if (i == 0) {
					currentHtmlFilePath = htmlFilePath;
				} else {
					currentHtmlFilePath = prefix + "_" + (i + 1) + "."
							+ extension;
				}
				buildHtml(templateFilePath, currentHtmlFilePath, data);
			}
		}
	}
	/**
	 * 生成产品首页HTML静态文件
	 * 
	 * @param product
	 *            商品
	 */
	@Transactional
	public void productPageBuildHtml(){
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.PRODUCT);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("hotProductList", productDao.getHotProductList(Product.MAX_HOT_PRODUCT_LIST_COUNT));
			data.put("newProductList", productDao.getNewProductList(Product.MAX_NEW_PRODUCT_LIST_COUNT));

			String htmlFilePath = htmlConfig.getHtmlFilePath();
			String templateFilePath = htmlConfig.getTemplateFilePath();
			buildHtml(templateFilePath, htmlFilePath, data);
		}
	}
	public void productContentBuildHtml(Product product) {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.PRODUCT_CONTENT);
		if (htmlConfig != null) {
			ProductCategory productCategory = product.getProductCategory();
			Map<String, Object> data = getCommonData();
			data.put("product", product);
			data.put("pathList",
					productCategoryService.getProductCategoryPathList(product));
			data.put("rootProductCategoryList",
					productCategoryService.getRootProductCategoryList());
			data.put("bestProductList", productDao.getBestProductList(
					productCategory, Product.MAX_BEST_PRODUCT_LIST_COUNT));
			data.put("hotProductList", productDao.getHotProductList(
					productCategory, Product.MAX_HOT_PRODUCT_LIST_COUNT));
			data.put("newProductList", productDao.getNewProductList(
					productCategory, Product.MAX_NEW_PRODUCT_LIST_COUNT));
			String htmlFilePath = product.getHtmlFilePath();
			String templateFilePath = htmlConfig.getTemplateFilePath();
			buildHtml(templateFilePath, htmlFilePath, data);
		}
	}

	/**
	 * 生成组织关于我们的静态页面
	 * 
	 * @param organise
	 */
	public void organiseBuildJson(Organise organise) {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.JSON_ORGANISE);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("organise", organise);
			// 生成静态页面
			buildHtml(htmlConfig.getTemplateFilePath(),
					htmlConfig.getHtmlFilePath(), data);
		}
	}
	
	/**
	 * 生成组织单位的静态页面
	 * 
	 * @param organise
	 */
	public void organiseBuildHtml() {
		// 生成组织关于我们的静态页面
		String orgId = SystemConfigUtil.getDepartmentId();
		organiseBuildHtml(orgId);
	}
	public void organiseBuildHtml(String orgId) {
		// 生成组织关于我们的静态页面
		organiseBuildHtml(organiseDao.get(orgId));
	}
	public void organiseBuildHtml(Organise organise) {
		
		// 生成json数据
		organiseBuildJson(organise);
		
//		// 生成组织关于我们的静态页面
//		organiseAboutBuildHtml(organise);
//		// 生成组织单位简介的静态页面
//		organiseInfoBuildHtml(organise);
//		// 生成组织地图的静态页面
//		organiseMapBuildHtml(organise);
//		// 生成组织单位分享的静态页面
//		organiseShareBuildHtml(organise);
//		// 生成组织意见反馈的静态页面
//		organiseFeedbackBuildHtml(organise);
	}

	/**
	 * 生成组织关于我们的静态页面
	 * 
	 * @param organise
	 */
	public void organiseAboutBuildHtml(Organise organise) {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ORGANISE_ABOUT);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("organise", organise);
			// 生成静态页面
			buildHtml(htmlConfig.getTemplateFilePath(),
					htmlConfig.getHtmlFilePath(), data);
		}
	}

	/**
	 * 生成组织单位的静态页面
	 * 
	 * @param organise
	 */
	public void organiseInfoBuildHtml(Organise organise) {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ORGANISE_INFO);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("organise", organise);
			// 生成静态页面
			buildHtml(htmlConfig.getTemplateFilePath(),
					htmlConfig.getHtmlFilePath(), data);
		}
	}

	/**
	 * 生成组织地图的静态页面
	 * 
	 * @param organise
	 */
	public void organiseMapBuildHtml(Organise organise) {
		// 生成组织地图的静态页面
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ORGANISE_MAP);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("organise", organise);
			// 生成静态页面
			buildHtml(htmlConfig.getTemplateFilePath(),
					htmlConfig.getHtmlFilePath(), data);
		}
	}

	/**
	 * 生成组织单位分享的静态页面
	 * 
	 * @param organise
	 */
	public void organiseShareBuildHtml(Organise organise) {
		// 生成组织地图的静态页面
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ORGANISE_SHARE);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("organise", organise);
			// 生成静态页面
			buildHtml(htmlConfig.getTemplateFilePath(),
					htmlConfig.getHtmlFilePath(), data);
		}
	}

	/**
	 * 生成企业反馈的静态页面
	 * 
	 * @param organise
	 */
	public void organiseFeedbackBuildHtml(Organise organise) {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ORGANISE_FEEDBACK);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("organise", organise);
			// 生成静态页面
			buildHtml(htmlConfig.getTemplateFilePath(),
					htmlConfig.getHtmlFilePath(), data);
		}
	}

	public void errorPageBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ERROR_PAGE);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("errorContent", "系统出现异常，请与管理员联系！");
			String htmlFilePath = htmlConfig.getHtmlFilePath();
			String templateFilePath = htmlConfig.getTemplateFilePath();
			buildHtml(templateFilePath, htmlFilePath, data);
		}
	}

	public void errorPageAccessDeniedBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ERROR_PAGE);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("errorContent", "您无此访问权限！");
			String htmlFilePath = htmlConfig.getHtmlFilePath();
			String templateFilePath = htmlConfig.getTemplateFilePath();
			buildHtml(templateFilePath, htmlFilePath, data);
		}
	}

	public void errorPage500BuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ERROR_PAGE_500);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("errorContent", "系统出现异常，请与管理员联系！");
			String htmlFilePath = htmlConfig.getHtmlFilePath();
			String templateFilePath = htmlConfig.getTemplateFilePath();
			buildHtml(templateFilePath, htmlFilePath, data);
		}
	}

	public void errorPage404BuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ERROR_PAGE_404);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("errorContent", "您访问的页面不存在！");
			String htmlFilePath = htmlConfig.getHtmlFilePath();
			String templateFilePath = htmlConfig.getTemplateFilePath();
			buildHtml(templateFilePath, htmlFilePath, data);
		}
	}

	public void errorPage403BuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil
				.getHtmlConfig(HtmlConfig.ERROR_PAGE_403);
		if (htmlConfig != null) {
			Map<String, Object> data = getCommonData();
			data.put("errorContent", "系统出现异常，请与管理员联系！");
			String htmlFilePath = htmlConfig.getHtmlFilePath();
			String templateFilePath = htmlConfig.getTemplateFilePath();
			buildHtml(templateFilePath, htmlFilePath, data);
		}
	}

}