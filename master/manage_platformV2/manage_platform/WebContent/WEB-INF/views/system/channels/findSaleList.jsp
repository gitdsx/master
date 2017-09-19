<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <%@include file="../common/includeBaseSet.jsp" %>
    <%@include file="../common/includeSystemSet.jsp" %>
    <link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css" />
    <script src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
</head>
<body>
<div>

</div>
<div class="page-content">
    <%--<ul class="pager">--%>
        <%--<li class="previous">--%>
            <%--<a href="javascript:window.history.go(-1)">← </a>--%>
        <%--</li>--%>
    <%--</ul>--%>
    <div class="row-fluid">
        <div class="col-xs-12">
            <form id="baseForm" class="form-inline" method="POST" onsubmit="return false;">
                <%--<div class="row">--%>
                    <%--<div class="widget-main">--%>
                    <%--客户名称:<input type="text" name="userName"     placeholder="请输入客户名称">--%>
                        <%--&lt;%&ndash;电话后四位:<input name="phone"  type="text" placeholder="请输入电话后四位" >&ndash;%&gt;--%>
                        <%--&lt;%&ndash;下单日期:<input name="beginTime" value="" class="date-picker" type="text" placeholder="开始日期" >&ndash;%&gt;--%>

                       <%--&lt;%&ndash; <input name="endTime" value=""  class="date-picker" type="text" placeholder="结束日期" >&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <span id="selectType">--%>
                            <%--<label></label>--%>
                        <%--<select  data-placeholder="类型" name="searchType" class="chosen-select isSelect95"></select></span>&ndash;%&gt;--%>
                    <%--&nbsp;&nbsp;<button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button" onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <input type='hidden' class='pageNum' name='pageNum' value='1'/>
                <input type='hidden' class='pageSize'  name='pageSize' value='10'/>
                <input type='hidden'  name='userId' value='${userId}'/>
                <input type='hidden'  name='beginTime' value='${bTime}'/>
                <input type='hidden'  name='endTime' value='${eTime}'/>
            </form>
            <table id="baseTable" class="table table-striped table-bordered table-hover" >
                <thead>
                <tr>
                    <th style="width:5%"  class="center hidden-480">客户名称</th>
                    <th style="width:6%"  class="center hidden-480">彩票名称</th>
                    <%--<th style="width:10%" class="center">联系电话</th>--%>
                    <th style="width:8%" class="center">方案编号</th>
                    <th style="width:10%" class="center hidden-480">购买金额</th>
                    <th style="width:10%"  class="center ">下单日期</th>
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
            <div id="totalId" class="">
                <table>
                    <tr>

                        <td style='font-weight:bold;color: #707070;'>
                            总销量(单位：元)：
                        </td>
                        <td id="sumSales" style='color:#F00'>

                        </td>
                    </tr>

                </table>
            </div>
        </div>
    </div>
</div>
<script src="${jypath}/static/js/system/channels/findSaleList.js"></script>
</body>
</html>