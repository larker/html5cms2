package com.larkersos.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.larkersos.util.CommonUtil;
import com.larkersos.util.SystemConfigUtil;


/**
 * Bean类 - 静态生成模板配置
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

public class HtmlConfig {
	
	public static final String REPLACE_UUID = "{uuid}";// 随机UUID字符串替换
	public static final String REPLACE_DATE_YY = "{date_yyyy}";// 当前日期字符串替换(年)
	public static final String REPLACE_DATE_MM = "{date_MM}";// 当前日期字符串替换(月)
	public static final String REPLACE_DATE_DD = "{date_dd}";// 当前日期字符串替换(日)
	public static final String REPLACE_DATE_HH = "{date_HH}";// 当前日期字符串替换(时)
	
	public static final String BASE_JAVASCRIPT = "baseJavascript";// baseJavascript
	public static final String COMMON_FILE = "commonFile";// commonFile
	public static final String INDEX = "index";// 首页
	public static final String LOGIN = "login";// 登录
	public static final String ARTICLE_CONTENT = "articleContent";// 文章内容
	public static final String PRODUCT_CONTENT = "productContent";// 商品内容
	
	// html5app页面
	public static final String PRODUCT = "product";// 商品页
	public static final String ARTICLE = "article";// 资讯页
	
	public static final String JSON_ORGANISE = "jsonOrganise";// 企业
	
	
	
	public static final String ORGANISE_ABOUT = "organiseAbout";// 企业关于我们
	public static final String ORGANISE_INFO = "organiseInfo";// 企业简介
	public static final String ORGANISE_MAP = "organiseMap";// 企业地图
	public static final String ORGANISE_SHARE = "organiseShare";// 企业分享
	public static final String ORGANISE_FEEDBACK = "organiseFeedback";// 企业反馈
	
	public static final String ERROR_PAGE = "errorPage";// 错误页
	public static final String ERROR_PAGE_ACCESS_DENIED = "errorPageAccessDenied";// 权限错误页
	public static final String ERROR_PAGE_500 = "errorPage500";// 错误页500
	public static final String ERROR_PAGE_404 = "errorPage404";// 错误页404
	public static final String ERROR_PAGE_403 = "errorPage403";// 错误页403
	
	private String name;// 配置名称
	private String description;// 描述
	private String templateFilePath;// Freemarker模板文件路径
	private String htmlFilePath;// 生成HTML静态文件存放路径

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplateFilePath() {
		return templateFilePath;
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}

	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}
	
	// 获取生成HTML静态文件存放路径
	public String getHtmlFilePath(String departmentId) {
		htmlFilePath = htmlFilePath.replace(REPLACE_UUID, CommonUtil.getUUID());
		SimpleDateFormat yyDateFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat mmDateFormat = new SimpleDateFormat("MM");
		SimpleDateFormat ddDateFormat = new SimpleDateFormat("dd");
		SimpleDateFormat hhDateFormat = new SimpleDateFormat("HH");
		htmlFilePath = htmlFilePath.replace(REPLACE_DATE_YY, yyDateFormat.format(new Date()));
		htmlFilePath = htmlFilePath.replace(REPLACE_DATE_MM, mmDateFormat.format(new Date()));
		htmlFilePath = htmlFilePath.replace(REPLACE_DATE_DD, ddDateFormat.format(new Date()));
		htmlFilePath = htmlFilePath.replace(REPLACE_DATE_HH, hhDateFormat.format(new Date()));
		
		// 当前登录用户信息 larkersos-departmentId
		if(StringUtils.isBlank(departmentId)){
			departmentId = SystemConfigUtil.getDepartmentId();
		}
		htmlFilePath = htmlFilePath.replace("{admin.departmentId}", SystemConfigUtil.getDepartmentId());
		
		return htmlFilePath;
	}
	// 获取生成HTML静态文件存放路径
	public String getHtmlFilePath() {
		// 当前登录用户信息 larkersos-departmentId
		return getHtmlFilePath(SystemConfigUtil.getDepartmentId());
	}

}