
$(function () {
	//下拉框
	JY.Dict.setSelect("selectType", "searchType", "", "--渠道用户--");
	getbaseList();
	//增加回车事件
	$("#baseForm").keydown(function (e) {
		keycode = e.which || e.keyCode;
		if (keycode == 13) {
			search();
		}
	});

	$(".icon-plus-sign").on("click", function() {
		var index = jeBox.open({
			cell:"jbx",
			padding:"0",
			title:"新增终端机",
			maxBtn:true,
			area:["25%","25%"],
			type:2,
			maskClose:true,
			content: 'addTerminalInit',
			success:function(data){
			}
		})
	});
});



function getbaseList(init) {
	if (init == 1)$("#baseForm .pageNum").val(1);
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm", jypath + '/channels/terminal/findTerminal', null, function (data) {
		$("#baseTable tbody").empty();
		var obj = data.obj;
		var list = obj.list;
		var results = list.results;
		var permitBtn = obj.permitBtn;
		var pageNum = list.pageNum, pageSize = list.pageSize, totalRecord = list.totalRecord;
		var html = "";
		if (results != null && results.length > 0) {
			var leng = (pageNum - 1) * pageSize;//计算序号
			for (var i = 0; i < results.length; i++) {
				var l = results[i];
				html += "<tr>";
				html+="<td class='center hidden-480'>"+JY.Object.notEmpty(i+1)+"</td>";
				html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.machineNum)+"</td>";
				html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.merchantName)+"</td>";
				html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.phone)+"</td>";
				html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createTime)+"</td>";
				 html+=JY.Tags.setFunction(l.id,permitBtn);
				html += "</tr>";
			}
			$("#baseTable tbody").append(html);
			JY.Page.setPage("baseForm", "pageing", pageSize, pageNum, totalRecord, "getbaseList");
		} else {
			html += "<tr><td colspan='13' class='center'>没有相关数据</td></tr>";
			$("#baseTable tbody").append(html);
			$("#pageing ul").empty();//清空分页
		}

		JY.Model.loadingClose();
	});
}
function addTerminal(){
	var index = jeBox.open({
		cell:"jbx",
		padding:"0",
		title:"新增终端机",
		maxBtn:true,
		area:["80%","100%"],
		type:2,
		maskClose:true,
		content:"addTerminalInit",
		button: [ {name: '取消'}]
	});
}
function updateTerminal(id){
	var index = jeBox.open({
		cell:"jbx",
		padding:"0",
		title:"修改",
		maxBtn:true,
		area:["30%","30%"],
		type:2,
		maskClose:true,
		content:"updateTerminalInit?id="+id,
		button: [ {name: '取消'}]
	});
}
function delTerminal(id){

	JY.Model.confirm("确认删除吗？",function(){
		JY.Ajax.doRequest("",jypath +'/channels/terminal/delTerminal?id='+id,null,function(data){
		if(data.resMsg=="success"){
			JY.Model.info("删除成功!",function(){getbaseList(1);});
		}else{
			JY.Model.info("删除失败!");
		}
	});
	});

}