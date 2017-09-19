<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <%@include file="../../../common/includeBaseSet.jsp" %>
    <%@include file="../../../common/includeSystemSet.jsp" %>
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
                    商户Id:<input type="text" name="merchantId" placeholder="这里输入商户Id"   class="input-large">
                    等级:<input type="text" name="level"  placeholder="这里输入等级"   class="input-large">
                        日期:<input id="beginTime" name="beginTime" value="" class="date-picker" type="text" placeholder="开始日期" >
                        <%--<input name="endTime" value="" class="date-picker" type="text" placeholder="结束日期" >--%>
                  <!--   <input type="checkbox"  class="agreement" name="depId" value="1" checked="checked"> 个人渠道 -->
                    <%--<%@include file="../../../org/orgTrees.jsp" %>--%>
                    &nbsp;&nbsp;
                    <button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>
                        <button id='resetData' class="btn btn-warning  btn-xs" title="过滤" type="button" >数据重置</button>
                        <a id='excelReport' class="btn btn-warning  btn-xs" href="#" onclick="javascript:exportReport('${jypath}/statistics/salesCommissionReport/exportReport')">导出报表</a>
                    </div>
                </div>
                <input type='hidden' class='pageNum' name='pageNum' value='1'/>
                <input type='hidden' class='pageSize'  name='pageSize' value='10'/>
            </form>
            <table id="baseTable" class="table table-striped table-bordered table-hover" >
                <thead>
                <tr>
                    <th style="width:10%"  class="center hidden-480">日期</th>
                    <th style="width:10%" class="center">渠道商户名</th>
                    <th style="width:5%" class="center hidden-480">发展渠道数</th>
                    <th style="width:8%"  class="center ">销售总额</th>
                    <th style="width:5%" class="center hidden-480">销售等级</th>
                    <th style="width:5%"  class="center hidden-480">提成比例</th>
                    <th style="width:15%" class="center hidden-480">佣金总额</th>
                    <%--<th style="width:15%" class="center">操作</th>--%>
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
            <%--<%@include file="form.jsp" %>--%>
            <%--<%@include file="../common/dialog.jsp" %>--%>
        </div>
    </div>
</div>
<script type="text/javascript" src="${jypath}/static/js/jquery/jebox/jebox.js?ver=2.2"></script>
<script src="${jypath}/static/js/system/finance/statistics/salesCommission.js"></script>
<script type="text/javascript">
function exportReport(url){
	window.location.href=url+"?date="+$('#beginTime').val();
}
</script>
</body>
</html>