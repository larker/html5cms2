var myScroll,
	pullDownEl, pullDownOffset,
	generatedCount = 0;
var pageIndex = "1";
var myAppName= "larkersos";

var hashMapForScroll = new HashMap();
/*!
 * 切换显示对应的nav页
 */
function swithDiv (divIndex) {
	if(divIndex == null){
		divIndex = 1;
	}
	if(curDiv == null){
		curDiv = $("#div-1");
		curNavbar =  $("#navbar-1");
	}
	curDiv.removeClass("cur");
	curDiv = $("#div-"+divIndex);
	curDiv.addClass("cur" );
	
	curNavbar.removeClass("ui-btn-active" );
	curNavbar = $("#navbar-" + divIndex);
	curNavbar.addClass("ui-btn-active" );
	
	//
	swithPageDiv ();

}
/*!
 * 刷新
 */
function swithPageDiv () {
	setTimeout(function () 
	{
		//var myScroll1 = hashMapForScroll.get(pageIndex);
		myScroll.refresh();
 
	}, 300);
}

var localStorageData = null;

// 加载处理
function loaded() { 
	if(window.localStorage){
		localStorageData = window.localStorage;}
	else{
		alert("localStorage not support");
	};
	initPullDownEl();
	
	swithDiv(pageIndex);
	setTimeout(function () 
	{
		$('#div-1_bannerList').orbit();
	}, 500); 
}

function initPullDownEl() 
 {
	pullDownEl = document.getElementById('pullDown');
	if(pullDownEl){
		pullDownOffset = pullDownEl.offsetHeight;
		myScroll =  new iScroll('wrapper' ,
		{
			//useTransition: true,
			topOffset: pullDownOffset,
			
			onRefresh: function () 
			{
				if (pullDownEl.className.match('loading')) 
				{
					pullDownEl.className = '';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';
				}
			},
			
			onScrollMove: function () 
			{
				if (this.y > 5 && !pullDownEl.className.match('flip')) 
				{
					pullDownEl.className = 'flip';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Release to refresh...';
					this.minScrollY = 0;
				} 
				else if (this.y < 5 && pullDownEl.className.match('flip')) 
				{
					pullDownEl.className = '';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';
					this.minScrollY = -pullDownOffset;
				}
			},
			onScrollEnd: function () 
			{
				if (pullDownEl.className.match('flip')) 
				{
					pullDownEl.className = 'loading';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Loading...';				
					pullDownAction();	// Execute custom function (ajax call?)
				}
			}
		});

		hashMapForScroll.put(pageIndex,myScroll);
	}
 }

/*!
 * 下拉刷新
 */
 function pullDownAction() 
 {		
	//  更新本地缓存数据
	refreshData ();
	// 更新页面数据
	refreshcontent();
	// 更新页面
	swithPageDiv();
}
 function refreshcontent() {
		// 首页轮播图片处理
		banner_list();
		prodInfo_list();
		organiseInfo_update();
		organiseAboutUs_update();
 }

/*!
 * 导航标题
 */
function gettitle() { 
	if(localStorageData == null){
		return;
	}
	// 导航标题数据
	var carrentPage = pageIndex;
	var jsonStr = localStorageData.getItem("sysTitleJsonStr");
	if(jsonStr){
		// 转化存储数据
		var jsonData = jQuery.parseJSON(jsonStr);
		var dataList = jsonData.sysTitleList;
		
		$("#navHeader ul li a").each(function () {  
			var index = $(this).attr("id").split("-")[1];
			
			//alert("navHeader:"+$(this).attr("id"));
			$(this).removeClass("ui-btn-active");
			for(var i in dataList){
			 	if(dataList[i].index == index){
			 		// 判断导航 
			 		var hrefNew = dataList[i].href;
					//$(this).html('<span class="ui-btn-inner"><span class="ui-btn-text">'+dataList[i].title+'</span></span>');
					$(this).find('span').find('span:first-child').html(dataList[i].title);
					$(this).attr("href","javascript:void(0)");

					// 当前导航高亮显示
					if(carrentPage == dataList[i].index){
						// head标题		
						$(this).addClass("ui-btn-active");
						sysTitle = dataList[i].title;
						$("#sysTitle").html( myAppName + "-" + sysTitle);
					}
					$(this).click(function(){
						document.location = hrefNew;
		      		});					
			    }
	  		} 

	    });
	    
		$("#navFooter ul li a").each(function () {  
			var index = $(this).attr("id").split("-")[1];
			$(this).removeClass("ui-btn-active");
			for(var i in dataList){
			 	if(dataList[i].index == index){
			 		// 判断导航 
			 		var hrefNew = dataList[i].href;
					$(this).find('span').find('span:first-child').html(dataList[i].title);
					$(this).attr("href","javascript:void(0)");
					// 当前导航高亮显示
					if(carrentPage == dataList[i].index){
						$(this).addClass("ui-btn-active");
						// head标题		
						sysTitle = dataList[i].title;
						$("#sysTitle").html( myAppName + "-" + sysTitle);
					}
					//alert(hrefNew);
					$(this).click(function(){
						document.location = hrefNew;
		      		});	
			    }
	  		} 
	 
	    });
    }
}

/*!
 * 幻灯片展示图片
 */
function banner_list () {
	if(localStorageData == null){
		return;
	}
	// 导航标题数据
	var jsonStr = localStorageData.getItem("indexBannerJsonStr");
	if(jsonStr){
		// 转化存储数据
		//alert("jsonStr:"+jsonStr);
		var jsonData = jQuery.parseJSON(jsonStr);
		var dataList = jsonData.bannerList;
		$("#div-1_bannerList").empty();
		$('#tmpl-banner').tmpl(dataList).appendTo("#div-1_bannerList" );
		//alert($('#div-1_bannerList').html());
	}

}

/*!
 * 公司资讯
 */
function organiseInfo_update () {
	if(localStorageData == null){
		return;
	}
	// 导航标题数据
	var jsonStr = localStorageData.getItem("organiseJsonStr");
	if(jsonStr){
		// 转化存储数据
		var jsonData = jQuery.parseJSON(jsonStr);
		if(jsonData.info_title){
			$("#div-2_organiseInfoTitle").html(jsonData.info_title);
		}
		if(jsonData.info_content){
			$("#div-2_organiseInfoContent").html(jsonData.info_content);
		}
	}
}
/*!
 * 关于我们
 */
function organiseAboutUs_update () {
	if(localStorageData == null){
		return;
	}
	// 导航标题数据
	var jsonStr = localStorageData.getItem("organiseJsonStr");
	//alert(jsonStr);
	if(jsonStr){
		// 转化存储数据
		var jsonData = jQuery.parseJSON(jsonStr);
		//alert(jsonData.about_content);
		
		if(jsonData.about_title){
			$("#div-5_organiseAboutUsTitle").html(jsonData.about_title);
		}
		if(jsonData.about_content){
			$("#div-5_organiseAboutUsContent").html(jsonData.about_content);
		}
	}
}
/*!
 * 产品推荐
 */
function prodInfo_list () {
	if(localStorageData == null){
		return;
	}
	// 导航标题数据
	var jsonStr = localStorageData.getItem("prodInfoJsonStr");
	if(jsonStr){
		// 转化存储数据
		var jsonData = jQuery.parseJSON(jsonStr);
		var dataList = jsonData.prodInfoList;
		
		$("#div-4_prodInfoTitle").html(jsonData.prodInfoTitle);
		$("#div-4_prodInfoList").empty();
		$('#tmpl-prodInfo').tmpl(dataList).appendTo("#div-4_prodInfoList");
	
		$("#div-4_prodInfoList").listview('refresh');
	}
}

/*!
 * 更新数据
 */
function refreshData () {
	if(localStorageData == null){
		return;
	}
	// nav更新
	refreshData_nav();

	// nav更新
	refreshData_indexBanner();

	// 企业信息更新
	refreshData_organise();
	// 商品信息更新
	refreshData_prod();
	
	window.localStorage.setItem("map_label", '<strong>北京larkersos</strong>' );
	var new_html = "<div class='infoBoxContent'><div class='title'><strong>企业信息</strong></div>"
+"<div class='list'><ul>"
+"<li> <div class='left'>公司名称 ： 北京larkersos</div></li>"
+"<li> <div class='left'>公司地址 ： 北京朝阳区酒仙桥</div></li>"
+"<li> <div class='left'>联络方式 ： 王天方</div></li>"
+"<li> <div class='left'>联络电话 ：  <a class='rmb' href='tel:13522150275' >13522150275</a></div></li>"
+"</ul></div>"
+"</div>";
	window.localStorage.setItem("map_html", new_html);
}

var _MY_DOMAIN = 'http://192.168.1.103:8080/html5cms/html/0/www';
_MY_DOMAIN="";
//nav信息更新
var nav_url = '/json/nav.html';
function refreshData_nav () {
	// 调用预订,返回结果为json
	var url = _MY_DOMAIN + nav_url;
	$.getJSON( url , function( data )
	{
		// 转化存储数据
		var dataStr = JSON.stringify(data);
		var dataJson = jQuery.parseJSON( dataStr );
		if(dataJson.result.resultCode == ""){
			// 转化存储数据
			localStorageData.removeItem("sysTitleJsonStr");
			localStorageData.setItem("sysTitleJsonStr", dataStr );
		}else{
			if(jsonData.result.message){
				alert(jsonData.result.message);
			}else{
				alert("失败，请检查对应参数");
			}
		}
	});	
}

//nav信息更新
var banner_url = '/json/json_indexBanner.html';
function refreshData_indexBanner () {
	// 调用预订,返回结果为json
	var url = _MY_DOMAIN + banner_url;
	//alert("url===="+url);
	$.getJSON( url , function( data )
	{
		// 转化存储数据
		//alert("url===="+1);
		var indexBannerJson = {"bannerList":[
		                                 	{"href":"index.html","imgSrc":"http://images.cnblogs.com/cnblogs_com/babyzone2004/256463/o_p1.jpg","imgTitle":"11"}
		                                 	,{"href":"index.html","imgSrc":"http://images.cnblogs.com/cnblogs_com/babyzone2004/256463/o_p3.jpg","imgTitle":"32"}
		                                 	,{"href":"index.html","imgSrc":"http://images.cnblogs.com/cnblogs_com/babyzone2004/256463/o_p4.jpg","imgTitle":"23"}
		                                 	]};
		
		var dataStr = JSON.stringify(data);
		//alert("url===="+2);
		var dataJson = jQuery.parseJSON( dataStr );
		
		//alert("dataStr===="+dataStr);
		if(dataJson.result.resultCode == ""){
			// 转化存储数据
			dataStr = JSON.stringify(indexBannerJson);
			localStorageData.removeItem("indexBannerJsonStr");
			localStorageData.setItem("indexBannerJsonStr", dataStr );
		}else{
			if(jsonData.result.message){
				alert(jsonData.result.message);
			}else{
				alert("失败，请检查对应参数");
			}
		}
	});	
}

// 企业信息更新
var jsonOrganise_url = '/json/json_organise.html';
function refreshData_organise () {
	// 调用预订,返回结果为订单详情
	var url = _MY_DOMAIN + jsonOrganise_url;
	//alert("url=="+url);
	$.getJSON( url , function( data )
	{
		// 转化存储数据
		var dataStr = JSON.stringify(data);
		//alert(dataStr);
		var dataJson = jQuery.parseJSON( dataStr );
		if(dataJson.result.resultCode == ""){
			// 转化存储数据
			localStorageData.removeItem("organiseJsonStr");
			localStorageData.setItem("organiseJsonStr", dataStr );
		}else{
			if(jsonData.result.message){
				alert(jsonData.result.message);
			}else{
				alert("失败，请检查对应参数");
			}
		}
	});
}

// 商品信息信息更新
var prodInfo_url = '/json/prodInfo.html';
function refreshData_prod () {
	// 调用预订,返回结果为订单详情
	var url = _MY_DOMAIN + prodInfo_url;
	$.getJSON( url , function( data )
	{
		// 转化存储数据
		var dataStr = JSON.stringify(data);
		var dataJson = jQuery.parseJSON( dataStr );
		if(dataJson.result.resultCode == ""){
			// 转化存储数据
			localStorageData.removeItem("prodInfoJsonStr");
			localStorageData.setItem("prodInfoJsonStr", dataStr );
		}else{
			if(jsonData.result.message){
				alert(jsonData.result.message);
			}else{
				alert("失败，请检查对应参数");
			}
		}
	});
	
}

 function getLocation()
 { 
  if(navigator.geolocation){
   navigator.geolocation.getCurrentPosition(showmap, handleError, {enableHighAccuracy:true, maximumAge:1000});
  }else{
   alert("您的浏览器不支持使用HTML 5来获取地理位置服务");
  }
 }
 
 function handleError(value)
 {
  switch(value.code){
   case 1:
    alert("位置服务被拒绝");
    break;
   case 2:
    alert("暂时获取不到位置信息");
    break;
   case 3:
    alert("获取信息超时");
    break;
   case 4:
    alert("未知错误");
    break;
  }
 }

