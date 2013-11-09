package com.larkersos.action.admin;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.larkersos.entity.FeedBack;
import com.larkersos.entity.Organise;
import com.larkersos.service.AdminService;
import com.larkersos.service.FeedBackService;
import com.larkersos.util.SerialNumberUtil;
import com.larkersos.util.SystemConfigUtil;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 组织机构管理
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@ParentPackage("admin")
public class FeedBackAction extends BaseAdminAction {

	private static final long serialVersionUID = -4433964283757192335L;
	
	private FeedBack feedBack;

	@Resource
	private FeedBackService feedBackService;
	public FeedBack getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(FeedBack feedBack) {
		this.feedBack = feedBack;
	}

	@Resource
	private AdminService adminService;

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		// 判断组织机构，默认编辑当前所属
		if(StringUtils.isEmpty(id)){
			id = SystemConfigUtil.getDepartmentId();
		}

		feedBack = feedBackService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = feedBackService.findByPager(pager);
		return LIST;
	}
	// 组织机构选择
	public String select() {
		pager = feedBackService.findByPager(pager);
		return "select";
	}

	// 删除
	public String delete() throws Exception {
		feedBackService.delete(ids);
		flushCache();
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "feedBack.contact", message = "联系不允许为空哦!") ,
			@RequiredStringValidator(fieldName = "feedBack.content", message = "反馈意见不允许为空哦!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		feedBackService.save(feedBack);
		flushCache();
		redirectionUrl = "feedBack!list.action";
		return SUCCESS;
	}

	// 更新
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		// 判断组织机构，默认编辑当前所属
		if(StringUtils.isEmpty(id)){
			id = SystemConfigUtil.getDepartmentId();
		}
		FeedBack persistent = feedBackService.load(id);

		BeanUtils.copyProperties(feedBack, persistent, new String[] {"id", "createDate", "modifyDate", "departmentId", "department"});

		feedBackService.update(persistent);
		flushCache();
		redirectionUrl = "feedBack!list.action";
		return SUCCESS;
	}

}