<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<title>WEB APP系统</title>

<link rel="stylesheet"  href="css/themes/default/jquery.mobile-1.3.1.min.css">
<link rel="stylesheet"  href="js/orbit/css/orbit-1.2.3.css">

<link rel="stylesheet"  href="css/larkersos.css" />

<script type="text/javascript" src="js/jquery-1.10.0.min.js"></script>
<script type="text/javascript" src="js/jquery.tmpl.js"></script>
<script type="text/javascript" src="js/jquery.mobile-1.3.1.min.js"></script>
<script type="text/javascript" src="js/orbit/jquery.orbit-1.2.3.js"></script>
<script type="text/javascript" src="js/iscroll.js"></script>

<script type="text/javascript" src="js/hashMap.js"></script>
<script type="text/javascript" src="js/larkersos.js"></script>

<script type="text/javascript">
var pageIndex = "1";
window.addEventListener('load', loaded, false); 
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

var curDiv,curNavbar;
var _MY_DOMAIN = "${appDomain}";
</script>

</head>
<body>
<div id="header"  data-role="header" data-theme="a" style="display:block" > 
<#include "/WEB-INF/template/html5app/header.ftl">
</div>
<div id="navHeader"  data-role="navbar" data-theme="b" style="display:block" >
<#include "/WEB-INF/template/html5app/header_nav.ftl"> 
</div>

<div id="content" data-role="content" >
    <div id=wrapper class="app-wrapper" style="top:80px;">
    	<div id="content-scroller" class="app-scroller">
			<div id="pullDown">
				<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
			</div>
			<#include "/WEB-INF/template/html5app/content_index.ftl">
			<#include "/WEB-INF/template/html5app/content_organiseInfo.ftl">
			<#include "/WEB-INF/template/html5app/content_organiseAboutUs.ftl">
			<#include "/WEB-INF/template/html5app/content_prodList.ftl">
			<#include "/WEB-INF/template/html5app/content_newsList.ftl">
     	</div><!-- scroller end -->
    </div><!-- wrapper end -->
</div><!-- content end -->

<div id="footer" class="app-footer" data-theme="a" data-role="footer" style="display:block" >
<#include "/WEB-INF/template/html5app/footer.ftl">
</div>

<#include "/WEB-INF/template/html5app/footer_script.ftl">

</body>
</html>