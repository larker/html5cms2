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
				<span>组织机构维护&nbsp;</span>
			</dt>
			<dd>
				<a href="organise!list.action" target="mainFrame">单位列表</a>
			</dd>
			<dd>
				<a href="organise!add.action" target="mainFrame">单位开户</a>
			</dd><dd>
				<a href="organise!select.action" target="mainFrame">单位选择</a>
			</dd>
			
		</dl>
		<dl>
			<dt>
				<span>管理员&nbsp;</span>
			</dt>
			<dd>
				<a href="admin!list.action" target="mainFrame">管理员列表</a>
			</dd>
			<dd>
				<a href="role!list.action" target="mainFrame">角色管理</a>
			</dd>
			<dd>
				<a href="resource!list.action" target="mainFrame">资源管理</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>模板管理</span>
			</dt>
			<dd>
				<a href="template_dynamic!list.action" target="mainFrame">动态模板管理</a>
			</dd>
			<dd>
				<a href="template_html!list.action" target="mainFrame">静态模板管理</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>操作日志管理&nbsp;</span>
			</dt>
			<dd>
				<a href="log!list.action" target="mainFrame">查看日志</a>
			</dd>
			<dd>
				<a href="log_config!list.action" target="mainFrame">日志配置</a>
			</dd>
		</dl>
	</div>
</body>
</html>