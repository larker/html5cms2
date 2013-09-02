package com.larkersos.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.compass.core.CompassTemplate;
import org.springframework.stereotype.Service;

import com.larkersos.bean.HtmlConfig;
import com.larkersos.dao.OrganiseDao;
import com.larkersos.entity.Organise;
import com.larkersos.service.HtmlService;
import com.larkersos.service.OrganiseService;
import com.larkersos.util.TemplateConfigUtil;

/**
 * Service实现类 - 商品
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Service
public class OrganiseServiceImpl extends BaseServiceImpl<Organise, String> implements OrganiseService {

	@Resource
	private OrganiseDao organiseDao;
	@Resource
	private CompassTemplate compassTemplate;
	@Resource
	private HtmlService htmlService;

	@Resource
	public void setBaseDao(OrganiseDao organiseDao) {
		super.setBaseDao(organiseDao);
	}
	
	public List<Organise> getOrganiseList(int firstResult, int maxResults) {
		return organiseDao.getOrganiseList(firstResult, maxResults);
	}
	
	// 重写方法，保存对象的同时处理价格精度并生成HTML静态文件
	@Override
	public String save(Organise organise) {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ORGANISE_INFO);
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		organise.setHtmlFilePath(htmlFilePath);
		String id = organiseDao.save(organise);
		organiseDao.flush();
		organiseDao.evict(organise);
		organise = organiseDao.load(id);
		// 生成关于我们和企业简介等关于企业的的静态页面
		htmlService.organiseBuildHtml(organise);
		
		return id;
	}

	// 重写方法，更新对象的同时重新生成HTML静态文件
	@Override
	public void update(Organise organise) {
		File htmlFile = new File(ServletActionContext.getServletContext().getRealPath(organise.getHtmlFilePath()));
		if (htmlFile.exists()) {
			htmlFile.delete();
		}
		String id = organise.getId();
		organiseDao.update(organise);
		organiseDao.flush();
		organiseDao.evict(organise);
		organise = organiseDao.load(id);

		// 生成关于我们和企业简介等关于企业的的静态页面
		htmlService.organiseBuildHtml(organise);
	}

}