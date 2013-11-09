<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑文章 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="larkersos Team" />
<meta name="Copyright" content="larkersos" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script src="${base}/ckeditor4/ckeditor.js"></script>
<script type="text/javascript">
$().ready(function() {

var editor = CKEDITOR.replace('articleContent'); 
})
function selectDepartmentPage(){
	var returnData=window.showModalDialog("${base}/admin/organise!select.action",window,"dialogWidth:500px;status:no;dialogHeight:400px");
	if(returnData != null){
	    document.getElementById("DepartmentId").value = returnData[0]; 
	    document.getElementById("Department").value = returnData[1]; 
	} 
}
</script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加文章<#else>编辑文章</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>article!save.action<#else>article!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
			<#if (adminDepartment == true)!>
				<tr>
					<th>
						所属组织部门:
					</th>
					<td>
						<input type="text" name="article.department" id="Department" class="formText{required: true}" value="${(article.department)!}" readonly/>
						<label class="requireField">*</label>
						<input type="button" class="formButton" onclick="selectDepartmentPage();" value="选   择" hidefocus="true" />
						<input type="hidden" name="article.departmentId" id="DepartmentId" value="${(article.departmentId)!}" />
					</td>
				</tr>
			</#if>
				<tr>
					<th>
						文章标题:
					</th>
					<td>
						<input type="text" name="article.title" class="formText {required: true}" value="${(article.title)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						文章分类:
					</th>
					<td>
						<select name="article.articleCategory.id" class="{required: true}">
							<option value="">请选择...</option>
							<#list articleCategoryTreeList as list>
								<option value="${list.id}"<#if (list.id == article.articleCategory.id)!> selected</#if>>
									<#if list.level != 0>
										<#list 1..list.level as i>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</#list>
									</#if>
								${list.name}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						作者:
					</th>
					<td>
						<input type="text" class="formText" name="article.author" value="${(article.author)!}" />
					</td>
				</tr>
				<tr>
					<th>
						是否推荐:
					</th>
					<td>
						<label><input type="radio" name="article.isRecommend" value="true"<#if (article.isRecommend == true)!> checked</#if> />是</label>
						<label><input type="radio" name="article.isRecommend" value="false"<#if (isAdd || article.isRecommend == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						是否置顶:
					</th>
					<td>
						<label><input type="radio" name="article.isTop" value="true"<#if (article.isTop == true)!> checked</#if> />是</label>
						<label><input type="radio" name="article.isTop" value="false"<#if (isAdd || article.isTop == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						是否发布:
					</th>
					<td>
						<label><input type="radio" name="article.isPublication" value="true"<#if (isAdd || article.isPublication == true)!> checked</#if> />是</label>
						<label><input type="radio" name="article.isPublication" value="false"<#if (article.isPublication == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						内容:
					</th>
					<td>
						<textarea name="article.content" id="articleContent" class="wysiwyg {required: true, messagePosition: '#contentMessagePosition'}" rows="20" cols="100">${(article.content)!}</textarea>
						<div class="blank"></div>
						<span id="contentMessagePosition"></span>
					</td>
				</tr>
				<tr>
					<th>
						页面关键词:
					</th>
					<td>
						<input type="text" class="formText" name="article.metaKeywords" value="${(article.metaKeywords)!}" />
					</td>
				</tr>
				<tr>
					<th>
						页面描述:
					</th>
					<td>
						<textarea name="article.metaDescription" class="formTextarea">${(article.metaDescription)!}</textarea>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>