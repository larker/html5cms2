<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<title>WEB APP系统</title>

<link rel="stylesheet"  href="css/themes/default/jquery.mobile-1.3.1.min.css">

<link rel="stylesheet"  href="css/larkersos.css" />

<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=F23df9cb0bb565fc2c2504dc8f9b428f"></script>

<script type="text/javascript" src="js/jquery-1.10.0.min.js"></script>
<script type="text/javascript" src="js/jquery.tmpl.js"></script>
<script type="text/javascript" src="js/jquery.mobile-1.3.1.min.js"></script>
<script type="text/javascript" src="js/orbit/jquery.orbit-1.2.3.js"></script>
<script type="text/javascript" src="js/iscroll.js"></script>

<script type="text/javascript" src="js/hashMap.js"></script>
<script type="text/javascript" src="js/larkersos.js"></script>


<style type="text/css">
	.infoBoxContent{font-size:12px;}
	.infoBoxContent .title{height:42px;width:240px;}
	.infoBoxContent .title strong{font-size:14px;line-height:42px;padding:0 10px 0 5px;}
	.infoBoxContent .title .price{color:#FFFF00;}
	.infoBoxContent .list{width:235px;border:solid 1px #4FA5FC;border-top:none;background:#fff;height:100px;}
	.infoBoxContent .list ul{margin:0;padding:5px;list-style:none;}
	.infoBoxContent .list ul li {float:left;width:255px;border-bottom:solid 1px #4FA5FC;padding:2px 0;}
	.infoBoxContent .list ul .last{border:none;}
	.infoBoxContent .list ul img{width:53px;height:42px;margin-right:5px;}
	.infoBoxContent .list ul p{padding:0;margin:0;}
	.infoBoxContent .left{float:left;}
	.infoBoxContent .rmb{float:right;color:#EB6100;font-size:14px;font-weight:bold;}
	.infoBoxContent a{color:#0041D9;text-decoration:none;}
</style>


<script type="text/javascript">
var pageIndex = "map";
window.addEventListener('load', loaded, false); 
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);





var longitude = ${organise.longitude};
var latitude = ${organise.latitude};
//var longitude = 116.48;
//var latitude = 39.97;
var _label = "${organise.name}";
var _html = "<div class='infoBoxContent'><div class='title'><strong>${organise.name}</strong></div>"
+"<div class='list'><ul>"
+"<li><div class='left'>企业名称 ：${organise.name}</div></li>"
+"<li><div class='left'>企业地址 ：${organise.addr}</div></li>"
+"<li><div class='left'>联系人   ：${organise.contact}</div></li>"
+"<li><div class='left'>联系电话 ：${organise.phone}</div></li>"
+"</ul></div>"
+"</div>";

function showmap(){
	  var map = new BMap.Map("map-container");
	  var point = new BMap.Point(longitude, latitude);// 创建点坐标
	  map.centerAndZoom(point, 15);
	  
	 	map.addControl(new BMap.NavigationControl());     
		map.addControl(new BMap.ScaleControl());     
		map.addControl(new BMap.OverviewMapControl());     
		map.addControl(new BMap.MapTypeControl()); 
		
	  //var icon = new /BMap.Icon('pin.png', new BMap.Size(20, 32), {  anchor: new BMap.Size(10, 30)  });
	  //var marker = new BMap.Marker(new BMap.Point(longitude, latitude), {  icon: icon  });  // 创建标注
	  var marker = new BMap.Marker(new BMap.Point(longitude, latitude));
	  marker.setLabel(new BMap.Label(_label));
	  		marker.addEventListener("click", function(e){
                    //this.openInfoWindow(new BMap.InfoWindow(_html));
            });
	  map.addOverlay(marker);// 将标注添加到地图中 
}
</script>

</head>
<body>
<div id="header"  data-role="header" data-theme="a" style="display:block" > 
<#include "/WEB-INF/template/html5app/header.ftl">
</div>
<div id="content" data-role="content" >
    <div id=wrapper class="app-wrapper" style="top:40px;" align="center">
            <div id="content-scroller" class="app-scroller">
                <div id="pullDown" style="display:none" >
					<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
				</div>
 				<div id="map-container" style="width:320px;height:600px;margin-top:2px"></div>
         	</div><!-- scroller_lmc1 end -->
    </div><!-- wrapper_lmc1 end -->
</div><!-- content end -->

<div id="footer" class="app-footer" data-theme="a" data-role="footer" style="display:block" >
<#include "/WEB-INF/template/html5app/footer.ftl">
</div>

</body>
</html>
