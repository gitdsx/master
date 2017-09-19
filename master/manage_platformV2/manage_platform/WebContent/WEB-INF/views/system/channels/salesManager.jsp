<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
	<%@include file="../common/includeBaseSet.jsp" %>
	<%@include file="../common/includeSystemSet.jsp" %>
	<link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
	<link href="${jypath}/static/js/jquery/jebox/skin/default.css" rel="stylesheet" type="text/css">
	<script src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
</head>
<body>
<div class="page-content">
	<div class="row-fluid">
		<div class="col-xs-12">
			<form id="baseForm" class="form-inline" method="POST" onsubmit="return false;">
				<div class="row">
					<div class="widget-main" id="baseHtml">
						<input type="text" name="mName" placeholder="客户名/手机号"   style="width:150px;" class="input-large">
						&nbsp状态:<select id="isValid" name="isValid" style="width:150px;">
							<option value="1" selected="selected">有效</option>
							<option value="2">无效</option>
							<option value="">全部</option>
					</select>
						&nbsp;&nbsp;<button id='menuBtn' class="btn btn-warning  btn-xs" title="查询" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>
						<ul></ul>
					</div>
				</div>
				<input type='hidden' class='pageNum' name='pageNum' value='1'/>
				<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
			</form>
			<table id="baseTable" class="table table-striped table-bordered table-hover" >
				<thead>
				<tr>
					<th style="width:3%" class="center">
						<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
					</th>
					<th style="width:9%"  class="center hidden-480">姓名</th>
					<th style="width:12%"  class="center ">电话号</th>
					<th style="width:9%" class="center hidden-480">审核进度</th>
					<th style="width:9%"  class="center hidden-480">发展客户数</th>
					<th style="width:9%"  class="center hidden-480">发展渠道数</th>
					<th style="width:15%"  class="center hidden-480">角色</th>
					<th style="width:9%"  class="center hidden-480">状态</th>
					<th style="width:9%" class="center">操作</th>
				</tr>
				</thead>
				<tbody></tbody>
			</table>
			<div class="row">
				<div class="col-sm-4">
					<div class="dataTables_info customBtn" >
						<c:forEach var="pbtn" items="${permitBtn}">
							<a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3" ><i class='${pbtn.icon} bigger-220'></i></a>
						</c:forEach>
					</div>
				</div>
				<%

				%>
				<div class="col-sm-8">
					<!--设置分页位置-->
					<div id="pageing" class="dataTables_paginate paging_bootstrap">
					<ul class="pagination"></ul>
				</div>
				</div>
			</div>
			<%@include file="editSalesManager.jsp" %>
			<%@include file="prepaymentForm.jsp" %>
			<%@include file="../common/dialog.jsp" %>
			<%@include file="addSalesManager.jsp" %>
		</div>
	</div>
</div>
<script type="text/javascript" src="${jypath}/static/js/jquery/jebox/jebox.js?ver=2.2"></script>
<script src="${jypath}/static/js/system/channels/salesManager.js"></script>
</body>
</html>