<div id="div-3" class="app-div">
	<div style="margin-top:1px">
	<#if recommendArticleList?size != 0>
		<ul data-role="listview" data-inset="true" data-filter="false">
            <li data-role="list-divider" id="div-3_companyInfoTitle0">最新资讯</li>
            <li>
            <div>
            <ul data-role="listview" data-filter="false" id="div-3_companyInfoList0" >
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
     <#if articleList?size != 0>
		<ul data-role="listview" data-inset="true" data-filter="false">
            <li data-role="list-divider" id="div-3_companyInfoTitle">其他资讯</li>
            <li>
            <div>
            <ul data-role="listview" data-filter="false" id="div-3_companyInfoList" >
             <#list articleList as list>
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
</div>
         	