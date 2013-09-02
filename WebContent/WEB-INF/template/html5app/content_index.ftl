<div id="div-1" class="app-div">
		    <div id="div-1_bannerList" style="width:90%">
		    	<a href="index.html"><img src="http://images.cnblogs.com/cnblogs_com/babyzone2004/256463/o_p1.jpg" title="1212" alt="1212"/></a>
		   <a href="index.html"><img src="http://images.cnblogs.com/cnblogs_com/babyzone2004/256463/o_p1.jpg" title="1212" alt="1212"/></a>
		   <a href="index.html"><img src="http://images.cnblogs.com/cnblogs_com/babyzone2004/256463/o_p1.jpg" title="1212" alt="1212"/></a>
		   
		    </div>
		    <div>
				<#if recommendArticleList?size != 0>
					<ul data-role="listview" data-inset="true" data-filter="false">
			            <li data-role="list-divider" id="div-1_companyInfoTitle0">最新资讯</li>
			            <li>
			            <div>
			            <ul data-role="listview" data-filter="false" id="div-1_companyInfoList0" >
			             <#list recommendArticleList as list>
			                <li>
			                   <a  href="${base}${list.htmlFilePath}">
			                    <span class="list-content">${list.title}</span>
			                    </a>
			                </li>
			             </#list>
			            </ul>
			            </div>
			            </li>
			       </ul>
			      </#if>
			</div>
		    <div style="margin-top:1px">
<#if bestProductList?size != 0>
        <ul data-role="listview" data-inset="true" data-filter="false" >
            <li data-role="list-divider" id="div-1_prodInfoTitleBest">推荐商品展示</li>
            <li>
                <div>
                <ul data-role="listview" id="div-1_prodInfoListBest" class="ui-grid-a">
				<#list bestProductList as list>
                	<#if list_index % 2 == 0>
                    <li class="ui-block-a">
                	<#else>
                	<li class="ui-block-b">
                	</#if>
                       	<a class="ui-btn" href="${base}${list.htmlFilePath}" data-theme="c">
                        <span class="ui-btn-text">
                        <img width="100%" src="${base}${list.productImageList[0].bigProductImagePath}">
                        <h3>${list.name}</h3>
                        </span>
                        </a>
                    </li>
                 </#list>
                </ul>
                </div>
            </li>
       </ul>
</#if>
            </div>

</div>