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
				<span>推送管理</span>
			</dt>
			<dd>
				<a href="push!list.action" target="mainFrame">推送列表</a>
			</dd>
			<dd>
				<a href="push!add.action" target="mainFrame">添加推送</a>
			</dd>
		</dl>
	</div>
</body>
</html>