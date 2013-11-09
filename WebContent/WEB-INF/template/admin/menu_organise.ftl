<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="html5cms Team" />
<meta name="Copyright" content="html5cms" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/menu.css" rel="stylesheet" type="text/css" />
</head>
<body class="menu">
	<div class="menuContent">
		<dl>
			<dt>
				<span>公司信息管理</span>
			</dt>
			<dd>
				<a href="organise!edit.action" target="mainFrame">企业基本信息</a>
			</dd>
			<dd>
				<a href="organise!aboutUs.action" target="mainFrame">关于我们</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>缓存管理</span>
			</dt>
			<#if (adminDepartment == true)!>
			<dd>
				<a href="cache!flush.action" target="mainFrame">更新缓存</a>
			</dd>
			</#if>
			<dd>
				<a href="build_html!allInput.action" target="mainFrame">一键网站更新</a>
			</dd>
		</dl>
	</div>
</body>
</html>