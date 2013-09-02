<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>单位选择 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="html5cms Team" />
<meta name="Copyright" content="html5cms" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>

<script type="text/javascript">
//设置返回到父窗口的值 
function retrunValue() { 
	var returnData= new Array("","");	 
	var ids = document.getElementsByName("ids"); 
	for(i=0;i<ids.length;i++) 
	{ 
	    if(ids[i].checked) {
	       returnData[0]=ids[i].value; 
	       returnData[1]=document.getElementsByName("names")[i].value;
	    }
	}
    window.returnValue=returnData; 
    window.close(); 
} 
</script>



</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="organise!select.action" method="post">
			<div class="operateBar">
				<label>查找:</label>
				<select name="pager.property">
					<option value="name" <#if pager.property == "name">selected="selected" </#if>>
						单位名称
					</option>
					<option value="code" <#if pager.property == "code">selected="selected" </#if>>
						单位编码
					</option>
				</select>
				<label class="searchText"><input type="text" name="pager.keyword" value="${pager.keyword!}" /></label><input type="button" id="searchButton" class="searchButton" value="" />
				<label>每页显示:</label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10" <#if pager.pageSize == 10>selected="selected" </#if>>
						10
					</option>
					<option value="20" <#if pager.pageSize == 20>selected="selected" </#if>>
						20
					</option>
					<option value="50" <#if pager.pageSize == 50>selected="selected" </#if>>
						50
					</option>
					<option value="100" <#if pager.pageSize == 100>selected="selected" </#if>>
						100
					</option>
				</select>
			</div>
			<table class="listTable">
				<tr>
					<th class="check">
						<span >选择</span>
					</th>
					<th>
						<span class="sort" name="name">单位名称</span>
					</th>
					<th>
						<span class="sort" name="code">单位编码</span>
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
						<td>
							<input type="radio" name="ids" value="${list.id}" onclick="javascript:s=${list.id}" />
							<input type="hidden" name="names" value="${list.name}" />
						</td>
						<td>
							<#if (list.name?length <= 20)!>
								<span title="${list.name}">${list.name}</span>
							<#else>
								<span title="${list.name}">${list.name[0..20]}...</span>
							</#if>
						</td>
						<td>
							${list.code}
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<#include "/WEB-INF/template/admin/pager.ftl" />
				</div>
			<#else>
				<div class="noRecord">
					没有找到任何记录!
				</div>
			</#if>
			<div class="buttonArea">
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="formButton" onclick="retrunValue();" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
		</form>
	</div>
</body>
</html>