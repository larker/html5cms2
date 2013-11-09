<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑单位 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script src="${base}/ckeditor4/ckeditor.js"></script>
<script type="text/javascript">
$().ready(function() {
 var editor = CKEDITOR.replace('organiseDescription'); 
})
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
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加<#else>编辑</#if></h1>
		</div>
		<div class="blank"></div>
		<form id="inputForm" class="validate" action="<#if isAdd??>organise!save.action<#else>organise!update.action</#if>" method="post" enctype="multipart/form-data" >
			<input type="hidden" name="id" value="${id}" />
			<ul class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus="true" />
				</li>
				<li>
					<input type="button" value="企业介绍" hidefocus="true" />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						单位名称:
					</th>
					<td>
						<input type="text" name="organise.name" class="formText {required: false}" value="${(organise.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						单位编码:
					</th>
					<td>
						<input type="text" class="formText" name="organise.code" value="${(organise.code)!}" title="若留空则由系统随机生成" />
					</td>
				</tr>
				<tr>
					<th>
						单位地址:
					</th>
					<td>
						<input type="text" class="formText" name="organise.addr" value="${(organise.addr)!}" title="所在地址" />
					</td>
				</tr>
				<tr>
					<th>
						经度（百度）:
					</th>
					<td>
						<input type="text" class="formText" name="organise.longitude" value="${(organise.longitude)!}" title="精度" />
					</td>
				</tr>
				<tr>
					<th>
						纬度（百度）:
					</th>
					<td>
						<input type="text" class="formText" name="organise.latitude" value="${(organise.latitude)!}" title="纬度" />
					</td>
				</tr>
				
				
				<tr>
					<th>
						单位电话:
					</th>
					<td>
						<input type="text" class="formText" name="organise.phone" value="${(organise.phone)!}" />
					</td>
				</tr>
				<tr>
					<th>
						联系人:
					</th>
					<td>
						<input type="text" class="formText" name="organise.contact" value="${(organise.contact)!}" />
					</td>
				</tr>
				<tr>
					<th>
						appTitle:
					</th>
					<td>
						<input type="text" class="formText" name="organise.appTitle" value="${(organise.appTitle)!}" />
					</td>
				</tr>
				<tr>
					<th>
						appDomain:
					</th>
					<td>
						<input type="text" class="formText" name="organise.appDomain" value="${(organise.appDomain)!}" />
					</td>
				</tr>
				<tr>
					<th>
						页面关键词:
					</th>
					<td>
						<input type="text" name="orgainise.metaKeywords" class="formText" value="${(orgainise.metaKeywords)!}" />
					</td>
				</tr>
				<tr>
					<th>
						页面描述:
					</th>
					<td>
						<textarea name="orgainise.metaDescription" class="formTextarea">${(orgainise.metaDescription)!}</textarea>
					</td>
				</tr>
			</table>
			<table >
				<tr>
					<td colspan="2">
						<textarea name="organise.description" id="organiseDescription"   style="width: 480px; height: 600px;">${(organise.description)!}</textarea>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<td colspan="2">
						<textarea name="organise.aboutUs" id="organiseAboutUs"  class="wysiwyg" style="width: 100%; height: 450px;">${(organise.aboutUs)!}</textarea>
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