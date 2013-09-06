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
var pageIndex = 'share';
window.addEventListener('load', loaded, false);
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
</script>
</head>
<body>
<div id="header"  data-role="header" data-theme="a" style="display:block" > 
<#include "/WEB-INF/template/html5app/header.ftl">
</div>


<div id="content" data-role="content" >
    <div id=wrapper class="app-wrapper" style="top:40px;">
		    <div>
				<ul data-role="listview" data-inset="true" data-filter="false">
                    <li data-role="list-divider" id="companyInfo_title">分享页</li>
                    <li>
                    <div>
                    <ul data-role="listview" data-filter="false" id="companyInfo_list" >
	                    <li>
	                       <a  href="http://dyd.tapp.daoyoudao.com:8253/wappro/wapIndex_getArticleInfo.do?tenantid=10010&id=12263">
	                        <span class="list-content">分享到新浪微博</span>
	                        </a>
	                    </li>
	                    <li>
	                    <!-- JiaThis Button BEGIN -->
<div id="ckepop">
<span class="jiathis_txt">分享到：</span>
<a class="jiathis_button_tsina">新浪微博</a>
<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jiathis_separator jtico jtico_jiathis" target="_blank">更多</a>
<a class="jiathis_counter_style"></a>
</div>
<script type="text/javascript" src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>
<!-- JiaThis Button END -->
	                    </li>
	                </ul>
	                </div>
                    </li>
               </ul>
			</div>
    </div><!-- wrapper_lmc1 end -->

</div><!-- content end -->

<div id="footer" class="app-footer" data-theme="a" data-role="footer" style="display:block" >
<#include "/WEB-INF/template/html5app/footer.ftl">
</div>

</body>
</html>
