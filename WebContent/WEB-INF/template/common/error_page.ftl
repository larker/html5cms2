<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提示信息 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="larkersos Team" />
<meta name="Copyright" content="larkersos" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
</head>
<body class="error">
	<div class="body">
		<div class="errorBox">
			<div class="errorDetail">
				<div class="errorContent">
					${errorContent!"您的操作出现错误!"}
				</div>
				<div class="errorUrl">点击此处，回到<a href="${base}/admin">后台管理</a></div>
			</div>
		</div>
	</div>
</body>
</html>