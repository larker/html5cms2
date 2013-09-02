package com.larkersos.action.admin;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;
import org.springframework.security.AccountExpiredException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.DisabledException;
import org.springframework.security.LockedException;

import com.larkersos.entity.Admin;
import com.larkersos.entity.Organise;
import com.larkersos.entity.Role;
import com.larkersos.service.AdminService;
import com.larkersos.service.ArticleService;
import com.larkersos.service.OrganiseService;
import com.larkersos.service.ProductService;
import com.larkersos.service.RoleService;
import com.larkersos.util.SystemConfigUtil;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;

/**
 * 后台Action类 - 后台管理、管理员
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@ParentPackage("admin")
public class AdminAction extends BaseAdminAction {

	private static final long serialVersionUID = -5383463207248344967L;
	
	public static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";// Spring security 最后登录异常Session名称

	private String loginUsername;
	
	private Admin admin;
	private List<Role> allRole;
	private List<Role> roleList;

	@Resource
	private AdminService adminService;
	@Resource
	private RoleService roleService;
	@Resource
	private OrganiseService organiseService;
	@Resource
	private ProductService productService;
	@Resource
	private ArticleService articleService;
	@Resource
	private ServletContext servletContext;
	
	// 登录页面
	public String login() {
		String error = getParameter("error");
		if (StringUtils.endsWithIgnoreCase(error, "captcha")) {
			addActionError("验证码错误,请重新输入!");
			return "login";
		}
		Exception springSecurityLastException = (Exception) getSession(SPRING_SECURITY_LAST_EXCEPTION);
		if (springSecurityLastException != null) {
			if (springSecurityLastException instanceof BadCredentialsException) {
				loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
				Admin admin = adminService.get("username", loginUsername);
				if (admin != null) {
					int loginFailureLockCount = getSystemConfig().getLoginFailureLockCount();
					int loginFailureCount = admin.getLoginFailureCount();
					if (getSystemConfig().getIsLoginFailureLock() && loginFailureLockCount - loginFailureCount <= 3) {
						addActionError("若连续" + loginFailureLockCount + "次密码输入错误,您的账号将被锁定!");
					} else {
						addActionError("您的用户名或密码错误!");
					}
				} else {
					addActionError("您的用户名或密码错误!");
				}
			} else if (springSecurityLastException instanceof DisabledException) {
				addActionError("您的账号已被禁用,无法登录!");
			} else if (springSecurityLastException instanceof LockedException) {
				addActionError("您的账号已被锁定,无法登录!");
			} else if (springSecurityLastException instanceof AccountExpiredException) {
				addActionError("您的账号已过期,无法登录!");
			} else {
				addActionError("出现未知错误,无法登录!");
			}
			getSession().remove(SPRING_SECURITY_LAST_EXCEPTION);
		}
//		String k = (String) servletContext.getAttribute("LARKERSOS" + "_" + "KEY");
//		if (!StringUtils.containsIgnoreCase(k, "larkersos")) {
//			throw new ExceptionInInitializerError();
//		}
		
		return "login";
	}
	
	// 后台主页面
	public String main() {
		String returnPage = "main";
		// 判断是否是已经登录，如果没有登录，跳转到登录页
		admin = SystemConfigUtil.getLoginAdmin();
		if (admin == null) {
			// 是否有管理员数据
			if(adminService.isExistByUsername("admin")){
				// 有用户未登录，跳转到登录
				returnPage = "login";
			}else{
				//初始化
				// 组织单位
				Organise organise = (Organise)organiseService.get("id", "0");
				if(organise == null){
					organise = new Organise();
					organise.setId("0");
					organise.setCode("0");
					organise.setName("总部");
					// 默认上级单位
					organise.setParentId("0");
					organise.setAddr("ADDR");
					organiseService.save(organise);
				}
				
				// 角色权限
				Role role = (Role)roleService.get("name", "ROLE-0");
				if(role == null){
					role = new Role();
					role.setName("ROLE-0");
					role.setValue("0");
					role.setIsSystem(true);
					roleService.save(role);
				}

				// admin
				admin = new Admin();
				admin.setName("admin");
				admin.setUsername("admin");
				admin.setDepartment(organise.getName());
				admin.setDepartmentId(organise.getId());
				admin.setPassword("admin");
				admin.setEmail("admin@larkersos.com");
				// 数据处理
				admin.setUsername(admin.getUsername().toLowerCase());
				admin.setLoginFailureCount(0);
				admin.setIsAccountLocked(false);
				admin.setIsAccountExpired(false);
				admin.setIsCredentialsExpired(false);
				admin.setIsAccountEnabled(true);
				HashSet roleSet = new HashSet<Role>();
				roleSet.add(role);
				admin.setRoleSet(roleSet);
				String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
				admin.setPassword(passwordMd5);
				adminService.save(admin);
				
				// 刷新缓存
				flushCache();
			}
		}
		return returnPage;
	}

	// 后台Header
	public String header() {
		return "header";
	}
	
	// 后台菜单
	public String menu() {
		return "menu";
	}
	
	// 后台中间(显示/隐藏菜单)
	public String middle() {
		return "middle";
	}
	
	// 后台首页
	public String index() {
		return "index";
	}

	// 是否已存在 ajax验证
	public String checkUsername() {
		String username = admin.getUsername();
		if (adminService.isExistByUsername(username)) {
			return ajaxText("false");
		} else {
			return ajaxText("true");
		}
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		admin = adminService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = adminService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		adminService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "admin.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "admin.password", message = "密码不允许为空!"),
			@RequiredStringValidator(fieldName = "admin.department", message = "组织单位部门不允许为空!"),
			@RequiredStringValidator(fieldName = "admin.email", message = "E-mail不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "admin.isAccountEnabled", message = "是否启用不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "admin.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "admin.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!")
		},
		emails = {
			@EmailValidator(fieldName = "admin.email", message = "E-mail格式错误!")
		},
		regexFields = {
			@RegexFieldValidator(fieldName = "admin.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (roleList == null || roleList.size() == 0) {
			return ERROR;
		}
		admin.setUsername(admin.getUsername().toLowerCase());
		admin.setLoginFailureCount(0);
		admin.setIsAccountLocked(false);
		admin.setIsAccountExpired(false);
		admin.setIsCredentialsExpired(false);
		admin.setRoleSet(new HashSet<Role>(roleList));
		String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
		admin.setPassword(passwordMd5);
		adminService.save(admin);
		redirectionUrl = "admin!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "admin.username", message = "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "admin.department", message = "组织单位部门不允许为空!"),
			@RequiredStringValidator(fieldName = "admin.email", message = "E-mail不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "admin.isAccountEnabled", message = "是否启用不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "admin.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
			@StringLengthFieldValidator(fieldName = "admin.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") },
		emails = {
			@EmailValidator(fieldName = "admin.email", message = "E-mail格式错误!")
		},
		regexFields = {
			@RegexFieldValidator(fieldName = "admin.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Admin persistent = adminService.load(id);
		if (roleList == null && roleList.size() == 0) {
			addActionError("请至少选择一个角色!");
			return ERROR;
		}
		admin.setRoleSet(new HashSet<Role>(roleList));
		if (StringUtils.isNotEmpty(admin.getPassword())) {
			String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
			persistent.setPassword(passwordMd5);
		}
		BeanUtils.copyProperties(admin, persistent, new String[] {"id", "createDate", "modifyDate", "username", "password", "isAccountLocked", "isAccountExpired", "isCredentialsExpired", "loginFailureCount", "lockedDate", "loginDate", "loginIp", "authorities"});
		adminService.update(persistent);
		redirectionUrl = "admin!list.action";
		return SUCCESS;
	}
	
	// 获取商品库存报警数
	public Long getStoreAlertCount() {
		return productService.getStoreAlertCount();
	}
	
	// 获取已上架商品数
	public Long getMarketableProductCount() {
		return productService.getMarketableProductCount();
	}
	
	// 获取已下架商品数
	public Long getUnMarketableProductCount() {
		return productService.getUnMarketableProductCount();
	}
	
	// 获取文章总数
	public Long getArticleTotalCount() {
		return articleService.getTotalCount();
	}
	
	// freemarker静态方法调用
	public TemplateHashModel getStatics() {
		return BeansWrapper.getDefaultInstance().getStaticModels();
	}
	
	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Role> getAllRole() {
		allRole = roleService.getAll();
		return allRole;
	}

	public void setAllRole(List<Role> allRole) {
		this.allRole = allRole;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}