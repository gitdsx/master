$(function () {
	getbaseList();
});
function getbaseList(init){
	if(init==1)$("#baseForm .pageNum").val(1);
	JY.Model.loading();

	JY.Ajax.doRequest("baseForm",jypath +'/backstage/salesVolume/countCustomer',null,function(data){
		$("#baseTable tbody").empty();
		var obj=data.obj;
		var list=obj.list;
		var results=list.results;
		var permitBtn=obj.permitBtn;
		var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
		var html="";

		if(results!=null&&results.length>0){
			var leng=(pageNum-1)*pageSize;//计算序号
			for(var i = 0;i<results.length;i++){
				var l=results[i];
				html+="<tr>";
				// html+="<td class='center hidden-480''>"+JY.Date.Format(l.date,'yyyy-MM-dd')+"</td>";
				html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.userId)+"</td>";
				html+="<td class='center hidden-480' >"+JY.Object.notEmpty(l.userName)+"</td>";
				html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.phone)+"</td>";
				html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.marketTime)+"</td>";
				html+="</tr>";
			}
			$("#baseTable tbody").append(html);
			JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
		}else{
			html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
			$("#baseTable tbody").append(html);
			$("#pageing ul").empty();//清空分页
		}671309
		JY.Model.loadingClose();
	});
}

function findsaleList(userNAME,userId){
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	var index = jeBox.open({
		cell:"jbx",
		padding:"0",
		title:"渠道销售额明细信息",
		maxBtn:true,
		area:["80%","80%"],
		type:2,
		maskClose:true,
		content:"findSaleList?userId="+userId+"&bTime="+beginTime+"&eTime="+endTime+"&userName="+userNAME,
		button: [ {name: '取消'},
		]
	});
}
function salesUserList(merchantId){
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	var index = jeBox.open({
		cell:"jbx",
		padding:"0",
		title:"客户明细表",
		maxBtn:true,
		area:["80%","80%"],
		type:2,
		maskClose:true,
		content:"salesUserList?merchantId="+merchantId+"&bTime="+beginTime+"&eTime="+endTime,
		button: [ {name: '取消'},
		]
	});
}

function findchildMerchant(merchantId){
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	var index = jeBox.open({
		cell:"jbx",
		padding:"0",
		title:"客户明细表",
		maxBtn:true,
		area:["80%","50%"],
		type:2,
		maskClose:true,
		content:"findchildMerchantInit?preId="+merchantId+"&bTime="+beginTime+"&eTime="+endTime,
		button: [ {name: '取消'}]
	});
}


// $(function () {
// 	//下拉框
// 	JY.Dict.setSelect("selectisValid","isValid",2,"全部");
// 	getbaseList();
// 	//增加回车事件
// 	$("#baseForm").keydown(function(e){
// 		 keycode = e.which || e.keyCode;
// 		 if (keycode==13) {
// 			 search();
// 		 }
// 	});
// 	//批量删除
// 	$('#delBatchBtn').on('click', function(e) {
// 		//通知浏览器不要执行与事件关联的默认动作
// 		e.preventDefault();
// 		var chks =[];
// 		$('#baseTable input[name="ids"]:checked').each(function(){
// 			chks.push($(this).val());
// 		});
// 		if(chks.length==0) {
// 			JY.Model.info("您没有选择任何内容!");
// 		}else{
// 			JY.Model.confirm("确认要删除选中的数据吗?",function(){
// 				JY.Ajax.doRequest(null,jypath +'/backstage/cash/delBatch',{chks:chks.toString()},function(data){
// 					JY.Model.info(data.resMsg,function(){search();});
// 				});
// 			});
// 		}
// 	});
// });
// function search(){
// 	$("#searchBtn").trigger("click");
// }
// function emptyRole(){
// 	$("#roleName").prop("value","");
// 	$("#auForm input[name$='roleId']").prop("value","0");
// }
// var preisShow=false;//窗口是否显示
// function showRole() {
// 	if(preisShow){
// 		hideRole();
// 	}else{
// 		var obj = $("#roleName");
// 		var offpos = $("#roleName").position();
// 		$("#roleContent").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");
// 		preisShow=true;
// 	}
// }
// function hideRole(){
// 	$("#roleContent").fadeOut("fast");
// 	preisShow=false;
// }
// /** 商户销量信息展示页*/
// function getbaseList(init){
// 	if(init==1)$("#baseForm .pageNum").val(1);
// 	JY.Model.loading();
// 	JY.Ajax.doRequest("baseForm",jypath +'/backstage/salesVolume/findByPage?date='+new Date().getTime()
// 		,null,function(data){
// 		 $("#baseTable tbody").empty();
//         	 var obj=data.obj;
//         	 var list=obj.list;
// 			 var totalSalesVolume=obj.totalSalesVolume;
// 			var totalUsers=obj.totalUsers;
// 			var allCommssion=obj.allCommssion;
// 			  $("#totalSalesVolumeId").text(totalSalesVolume);
// 			$("#totalUsersId").text(totalUsers);
// 			$("#allCommssion").text(allCommssion);
//
//         	 var results=list.results;
//          	 var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
//         	 var html="";
//     		 if(results!=null&&results.length>0){
//         		 for(var i = 0;i<results.length;i++){
//             		 var l=results[i];
// 					 html+="<tr>";
// 					 html+="<td class='center '>"+JY.Object.notEmpty(l.userName)+"</td>";
// 					 html+="<td class='center ' >"+JY.Object.notEmpty(l.userTypeName)+"</td>";
// 					 html+="<td class='center '>"+JY.Object.notEmpty(l.phone)+"</td>";
// 					 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.name)+"</td>";
// 					 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.records)+"</td>";
// 					 //html+="<td class='center hidden-480' onclick='detailList("+l.userId+")' style='color:#428bca' >"+JY.Object.notEmpty(l.salesVolume)+"元</td>";
// 					 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.salesVolume)+"元</td>";
// 					 html+="<td class='center '>"+JY.Date.Default(l.orderTime)+"</td>";
// 					 html+="<td class='center '>"+JY.Date.Default(l.totalCommission)+"</td>";
// 					 //html+="<td class=\"center\">"+
// 						// "<div class=\"visible-md visible-lg hidden-sm hidden-xs btn-group\">"+
// 						// "<a href=\"#\" title=\"删除\" onclick=\"detail(\'"+l.id+"\',\'"+l.merchantId+"\')\"class=\"aBtnNoTD\"><i class=\"icon-remove-sign  bigger-140\"></i></a>"+
// 						// "</div></td>";
// 				 }
//         		 $("#baseTable tbody").append(html);
//         		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
//         	 }else{
//         		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
//         		$("#baseTable tbody").append(html);
//         		$("#pageing ul").empty();//清空分页
//         	 }
//
//     	 JY.Model.loadingClose();
// 	 });
// }
//
// /** 查看明细*/
// function detailList(userId) {
// 	var index = jeBox.open({
// 		cell:"jbx",
// 		padding:"0",
// 		title:"销量明细",
// 		maxBtn:true,
// 		area:["600px","80%"],
// 		type:2,
// 		maskClose:true,
// 		content:"findDetailByPage?userId="+userId,
// 	})
// }