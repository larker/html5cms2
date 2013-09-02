<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="larkersos Team" />
<meta name="Copyright" content="larkersos" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/menu.css" rel="stylesheet" type="text/css" />
</head>
<body class="menu">
	<div class="menuContent">
		<dl>
			<dt>
				<span>内容管理</span>
			</dt>
			<dd>
				<a href="article!list.action" target="mainFrame">文章管理</a>
			</dd>
			<dd>
				<a href="article!add.action" target="mainFrame">添加文章</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>网站静态管理</span>
			</dt>
			<dd>
				<a href="build_html!articleInput.action" target="mainFrame">文章更新</a>
			</dd>
		</dl>
		<#if (adminDepartment == true)!>
		<dl>
			<dt>
				<span>文章分类管理</span>
			</dt>
			<dd>
				<a href="article_category!list.action" target="mainFrame">文章分类</a>
			</dd>
		</dl>
		</#if>

	</div>
</body>
</html>