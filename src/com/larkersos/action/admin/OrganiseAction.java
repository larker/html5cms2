package com.larkersos.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.larkersos.entity.Admin;
import com.larkersos.entity.Organise;
import com.larkersos.service.AdminService;
import com.larkersos.service.OrganiseService;
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
public class OrganiseAction extends BaseAdminAction {

	private static final long serialVersionUID = -4433964283757192335L;
	
	private Organise organise;

	@Resource
	private OrganiseService organiseService;
	public Organise getOrganise() {
		return organise;
	}

	public void setOrganise(Organise organise) {
		this.organise = organise;
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

		organise = organiseService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = organiseService.findByPager(pager);
		return LIST;
	}
	// 组织机构选择
	public String select() {
		pager = organiseService.findByPager(pager);
		return "select";
	}

	// 删除
	public String delete() throws Exception {
		for (String id : ids) {
			Organise organise = organiseService.load(id);
			List<Admin> adminList = adminService.getList("departmentId",organise.getId());
			if (adminList != null && adminList.size() > 0) {
				return ajaxJsonErrorMessage("[" + organise.getName() + "]下还有关联用户，删除失败！");
			}
		}
		organiseService.delete(ids);
		flushCache();
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "organise.name", message = "名称不允许为空哦!") 
		}, 
		requiredFields = {
//			@RequiredFieldValidator(fieldName = "organise.isMarketable", message = "是否上架不允许为空!"),
		},
		intRangeFields = {
//			@IntRangeFieldValidator(fieldName = "organise.point", min = "0", message = "积分必须为零或正整数!"),
//			@IntRangeFieldValidator(fieldName = "organise.store", min = "0", message = "库存必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (StringUtils.isNotEmpty(organise.getCode())) {
			if (organiseService.isExist("code", organise.getCode())) {
				addActionError("编码重复,请重新输入!");
				return ERROR;
			}
		} else {
			String organiseCode = SerialNumberUtil.buildOrganiseCode();
			organise.setCode(organiseCode);
		}
		// 默认上级单位
		if (StringUtils.isEmpty(organise.getParentId())) {
			organise.setParentId("0");
		}

		organiseService.save(organise);
		flushCache();
		redirectionUrl = "organise!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "organise.name", message = "名称不允许为空!") 
		}, 
		requiredFields = {
		},
		intRangeFields = {
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		// 判断组织机构，默认编辑当前所属
		if(StringUtils.isEmpty(id)){
			id = SystemConfigUtil.getDepartmentId();
		}
		Organise persistent = organiseService.load(id);
		if (StringUtils.isNotEmpty(organise.getCode())) {
			if (!organiseService.isUnique("code", persistent.getCode(), organise.getCode())) {
				addActionError("编码重复,请重新输入!");
				return ERROR;
			}
		} else {
			String organiseSn = SerialNumberUtil.buildOrganiseCode();
			organise.setCode(organiseSn);
		}

		BeanUtils.copyProperties(organise, persistent, new String[] {"id", "createDate", "modifyDate", "parentId", "htmlFilePath"});

		organiseService.update(persistent);
		flushCache();
		redirectionUrl = "organise!list.action";
		return SUCCESS;
	}

}