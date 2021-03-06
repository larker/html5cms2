<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理中心 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="html5cms Team" />
<meta name="Copyright" content="html5cms" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/header.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 6]>
<script type="text/javascript">
	DD_belatedPNG.fix(".headerLogo");
</script>
<![endif]-->  
</head>
<body class="header">
	<div class="body">
		<div class="headerLogo"></div>
		<div class="headerTop">
			<div class="headerLink">
				<span class="welcome">
					<strong><@sec.authentication property="name" /></strong>&nbsp;您好!&nbsp;
				</span>
				<a href="admin!index.action" target="mainFrame">首页</a>|
                <a href="http://about.larkersos.com" target="_blank">关于我们</a>|
                <a href="http://contact.larkersos.com" target="_blank">联系我们</a>
			</div>
		</div>
		<div class="headerBottom">
			<div class="headerMenu">
				<div class="menuLeft"></div>
				<ul>
	            	<li><a href="menu!product.action" target="menuFrame" hidefocus="true">商品维护</a></li>
	                <li><a href="menu!content.action" target="menuFrame" hidefocus="true">文章资讯维护</a></li>
	                <li><a href="menu!organise.action" target="menuFrame" hidefocus="true">企业信息维护</a></li>
	                <#if (adminDepartment == true)!>
	                <li><a href="menu!push.action" target="menuFrame" hidefocus="true">推送维护</a></li>
	                <li><a href="menu!analysis.action" target="menuFrame" hidefocus="true">统计分析</a></li>
	                <li><a href="menu!admin.action" target="menuFrame" hidefocus="true">系统管理</a></li>
	                </#if>
	                <li class="home"><a href="${base}/html/${departmentId}/www/" target="_blank" hidefocus="true"></a></li>
	            </ul>
	            <div class="menuRight"></div>
			</div>
			<div class="userInfo">
				<a href="admin_profile!edit.action" target="mainFrame">
					<span class="profileIcon">&nbsp;</span>个人资料
				</a>
				<a href="${base}/admin/logout" target="_top">
					<span class="logoutIcon">&nbsp;</span>退出&nbsp
				</a>
			</div>
		</div>
	</div>
</body>
</html>