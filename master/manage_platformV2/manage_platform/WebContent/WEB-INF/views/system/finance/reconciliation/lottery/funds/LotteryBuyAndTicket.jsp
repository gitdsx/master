<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <%@include file="../../../../common/includeBaseSet.jsp" %>
    <%@include file="../../../../common/includeSystemSet.jsp" %>
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
                    <div class="widget-main">
                        <input name ="beginTime" id = "beginTime" value="" class="date-picker" type="text" placeholder="开始日期">
                        <input name="endTime" value="" class="date-picker" type="text" placeholder="结束日期">
                    &nbsp;&nbsp;<button id='searchBtn' class="btn btn-warning  btn-xs" title="查询" type="button" onclick="getDataList()"><i class="icon-search bigger-110 icon-only"></i></button>
                        <button id='resetData' class="btn btn-warning  btn-xs" title="过滤" type="button" >数据重置</button>
                        <a id='excelReport' class="btn btn-warning  btn-xs" href="#" onclick="javascript:exportReport('${jypath}/backstage/reconciliation/lottery/lotteryBuyAndTicket/download')">导出报表</a>
                        <%--<a id='downReport' class="btn btn-warning  btn-xs" href="#" onclick="javascript:exportReport('${jypath}/backstage/reconciliation/lottery/lotteryBuyAndTicket/downloadDiff')">导出差异报表</a>--%>
                    </div>
                </div>
                <input type='hidden' class='pageNum' name='pageNum' value='1'/>
                <input type='hidden' class='pageSize'  name='pageSize' value='10'/>
            </form>
            <table id="baseTable" class="table table-striped table-bordered table-hover" >
                <thead>
                <tr>
							<th style="width: 10%" class="center hidden-480" rowspan="2">日期</th>
							<th style="width: 7%" class="center hidden-480" colspan="3">非追期购彩金额</th>
							<th style="width: 7%" class="center hidden-480" colspan="3">非追期出票金额</th>
                    <th style="width: 7%" class="center hidden-480" colspan="3">非追期退款金额</th>
                    <th style="width: 7%" class="center hidden-480" colspan="3">非追期未出票金额</th>
							<th style="width: 10%" class="center hidden-480" rowspan="2">差异</th>
                </tr>
                <tr>
                    <th style="width: 7%" class="center hidden-480">普通</th>
                    <th style="width: 7%" class="center hidden-480">商户</th>
                    <th style="width: 7%" class="center hidden-480">合计</th>
                    <th style="width: 7%" class="center hidden-480">普通</th>
                    <th style="width: 7%" class="center hidden-480">商户</th>
                    <th style="width: 7%" class="center hidden-480">合计</th>
                    <th style="width: 7%" class="center hidden-480">普通</th>
                    <th style="width: 7%" class="center hidden-480">商户</th>
                    <th style="width: 7%" class="center hidden-480">合计</th>
                    <th style="width: 7%" class="center hidden-480">普通</th>
                    <th style="width: 7%" class="center hidden-480">商户</th>
                    <th style="width: 7%" class="center hidden-480">合计</th>
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
    </div>
    <%@include file="../../../../account/form.jsp" %>
    <!-- #dialog-confirm -->
    <%@include file="../../../../common/dialog.jsp" %>
</div>
<script type="text/javascript" src="${jypath}/static/js/jquery/jebox/jebox.js?ver=2.2"></script>
<script src="${jypath}/static/js/system/finance/reconciliation/lottery/funds/LotteryBuyAndTicket.js"></script>
<script type="text/javascript">
function exportReport(url){
	window.location.href=url+"?date="+$('#beginTime').val();
}
</script>


</body>
</html>