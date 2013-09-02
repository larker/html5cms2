<div id="div-4" class="app-div">
<#if bestProductList?size != 0>
        <ul data-role="listview" data-inset="true" data-filter="false" >
            <li data-role="list-divider" id="div-4_prodInfoTitleBest">推荐商品展示</li>
            <li>
                <div>
                <ul data-role="listview" id="div-4_prodInfoListBest" class="ui-grid-a">
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
<#if productList?size != 0>
        <ul data-role="listview" data-inset="true" data-filter="false" >
            <li data-role="list-divider" id="div-4_prodInfoTitle">一般商品展示</li>
            <li>
                <div>
                <ul data-role="listview" id="div-4_prodInfoList" class="ui-grid-a">
				<#list productList as list>
                	<#if list_index % 2 == 0>
                    <li class="ui-block-a">
                	<#else>
                	<li class="ui-block-b">
                	</#if>
                       	<a class="ui-btn" href="${base}${list.htmlFilePath}" data-theme="c">
                        <span class="ui-btn-text">
                        
                        <#if list.productImageList??>
                        <img width="100%" src="${base}${list.productImageList[0].bigProductImagePath}">
                        </#if>
                        
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