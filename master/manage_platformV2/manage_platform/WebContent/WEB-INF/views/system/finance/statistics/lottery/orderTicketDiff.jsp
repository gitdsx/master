<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <%@include file="../../../common/includeBaseSet.jsp" %>
    <%@include file="../../../common/includeSystemSet.jsp" %>
    <link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
    <script src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
</head>
<body>
<div>

</div>
<div class="page-content">
    <div class="row-fluid">
        <div class="col-xs-12">
            <form id="baseForm" class="form-inline" method="POST" onsubmit="return false;">
                <div class="row">
                    <input type="hidden" name="date" value="${date}">
                    <%--<input type="hidden" name="dResult" value="-1">--%>
                    <%--<input type="hidden" name="dType" value="2">--%>
                    <input type='hidden' class='pageNum' name='pageNum' value='1'/>
                    <input type='hidden' class='pageSize'  name='pageSize' value='10'/>
                </div>
            </form>
            <table id="baseTable" class="table table-striped table-bordered table-hover" >
                <thead>
                <tr>
                    <th style="width:5%"  class="center hidden-480">时间</th>
                    <th style="width:5%" class="center">编号</th>
                    <%--<th style="width:5%" class="center hidden-480">方案金额</th>--%>
                    <%--<th style="width:15%"  class="center ">票号</th>--%>
                    <%--<th style="width:15%" class="center hidden-480">票面金额</th>--%>
                    <th style="width:5%"  class="center hidden-480">差异金额</th>
                    <th style="width:5%" class="center hidden-480">差异类型</th>
                    <th style="width:5%" class="center hidden-480">差异原因</th>
                    <th style="width:5%" class="center hidden-480">处理状态</th>
                    <th style="width:5%" class="center hidden-480">处理人</th>
                    <th style="width:5%" class="center hidden-480">处理时间</th>
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
        <%@include file="../../../account/form.jsp" %>
        <!-- #dialog-confirm -->
        <%@include file="../../../common/dialog.jsp" %>
    </div>
</div>
<script src="${jypath}/static/js/system/finance/statistics/lottery/orderTicketDiff.js"></script>
</body>
</html>