<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
<%@include file="../../common/includeBaseSet.jsp" %>
<%@include file="../../common/includeSystemSet.jsp" %>
<link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
	<link rel="stylesheet" href="${jypath}/static/js/jquery/jebox/skin/default.css" />
	<link rel="stylesheet" href="${jypath}/static/css/system/system/basic.css" />
<script src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
</head>
<body>
	<div class="page-content">
		<div class="row-fluid">	
			<div class="col-xs-12">
				<form id="fundsForm" class="form-inline" method="POST" onsubmit="return false;">
					<div class="row">
						<div class="widget-main">
							<input name="beginTime" id="beginTime" value="" class="date-picker" type="text" placeholder="开始日期" >
							<input name="endTime" value="" class="date-picker" type="text" placeholder="结束日期" >
							<%--<input type="text" name="keyWord" placeholder="输入关键词"   class="input-large">--%>&nbsp;&nbsp;
							<%--<span id="selectDealStatus">--%>
								<%--<label></label>：--%>
								<%--<select  data-placeholder="处理状态" name="dealResultStatus" class="chosen-select isSelect95"></select>--%>
							<%--</span>--%>
							<button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getList(1)">
								<i class="icon-search bigger-110 icon-only"></i>
							</button>
							<button id='resetData' class="btn btn-warning  btn-xs" title="过滤" type="button" >数据重置</button>
							<a id='excelsReport' class="btn btn-warning  btn-xs" href="#" onclick="javascript:exportReport('${jypath}/backstage/reconciliation/download')">导出报表</a>
						</div>
					</div>
					<input type='hidden' class='pageNum' name='pageNum' value='1'/>
					<input type='hidden' class='pageSize'  name='pageSize' value='10'/>
				</form>
				<table id="fundsTable" class="table table-striped table-bordered table-hover" >
					<thead>
						<tr>
							<th style="width:3%" class="center" rowspan="2">
									<label><input type="checkbox" class="ace" ><span class="lbl"></span></label>
							</th>
							<th style="width:8%" class="center" rowspan="2">日期</th>
							<th style="width:5%" class="center" rowspan="2">内部存入</th>
							<th style="width:7%" class="center"colspan="4">投注系统</th>
							<th style="width:7%" class="center"colspan="4">第三方流水</th>
							<th style="width:5%" class="center" rowspan="2">总差异额</th>
							<th style="width:7%" class="center hidden-480"rowspan="2">对账差异</th>
							<%--<th style="width:5%" class="center hidden-480" rowspan="2">调整差异</th>--%>
							<%--<th style="width:5%" class="center hidden-480" rowspan="2">累计差异额</th>--%>
							<%--<th style="width:7%" class="center" rowspan="2">处理状态</th>--%>
						</tr>
						<tr>
							<th style="width:7%" class="center hidden-480">银联</th>
							<th style="width:7%" class="center hidden-480">微信</th>
							<th style="width:7%" class="center hidden-480">得仕通</th>
							<th style="width:7%" class="center">小计</th>
							<th style="width:7%" class="center hidden-480">银联</th>
							<th style="width:7%" class="center hidden-480">微信</th>
							<th style="width:7%" class="center hidden-480">得仕通</th>
							<th style="width:7%" class="center">小计</th>
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
					<div class="col-sm-8">
						<!--设置分页位置-->
						<div id="pageing" class="dataTables_paginate paging_bootstrap">
							<ul class="pagination"></ul>
						</div>
					</div>
				</div>
			</div>
			<div>
				<span style="color: red">说明：</span><br>
				<span style="color: red">总差异额：投注系统总额-第三方流水总额</span><br>
				<span style="color: red">对账差异=程序对账查出的差异额</span><br>
				<%--<span style="color: red">调整差异=总差异额-对账差异</span><br>--%>
				<%--<span style="color: red">T日累计总差异额=截止T日所有差异和</span>--%>
			</div>
			<!-- #addorUpdateFrom -->
			<%@include file="../../account/form.jsp" %>
			<%@include file="../../common/dialog.jsp" %>
		</div>
	</div>
	<script type="text/javascript" src="${jypath}/static/js/jquery/jebox/jebox.js?ver=2.2"></script>
	<script src="${jypath}/static/js/system/finance/reconciliation/reconciliation_dealStatus.js"></script>
	<script src="${jypath}/static/js/system/finance/reconciliation/platformFundsRunning.js"></script>
</body>
</html>