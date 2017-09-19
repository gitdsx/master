$(function () {
	//下拉框
	getbaseList();

	//批量删除
	$('#delBatchBtn').on('click', function(e) {
		//通知浏览器不要执行与事件关联的默认动作
		e.preventDefault();
		var chks =[];
		$('#baseTable input[name="ids"]:checked').each(function(){
			chks.push($(this).val());
		});
		if(chks.length==0) {
			JY.Model.info("您没有选择任何内容!");
		}else{
			JY.Model.confirm("确认要删除选中的数据吗?",function(){
				JY.Ajax.doRequest(null,jypath +'/backstage/cash/delBatch',{chks:chks.toString()},function(data){
					JY.Model.info(data.resMsg,function(){search();});
				});
			});
		}
	});
});

/** 佣金配置展示页*/
function getbaseList(init){
	if(init==1)$("#baseForm.pageNum").val(1);	
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/backstage/commission/findByPage',null,function(data){
			 $("#baseTable tbody").empty();
			 $("#baseHtml ul").empty();
        	 var obj=data.obj;
        	 var list=obj.list;
        	 var results=list.results;
        	 var permitBtn=obj.permitBtn;
         	 var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
        	 var html="";
    		 if(results!=null&&results.length>0){
        		 for(var i = 0;i<results.length;i++){
            		 var l=results[i];
					 html+="<tr>";
					 html+="<td class='center '>"+JY.Object.notEmpty(l.name)+"</td>";
					 html+="<td class='center ' >"+JY.Object.notEmpty(l.typeName)+"</td>";
					 html+="<td class='center '>"+JY.Object.notEmpty(l.oneRankMin)+" - "+JY.Object.notEmpty(l.oneRankMax)+"</td>";
					 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.onePercent)+"%"+"</td>";
					 html+="<td class='center '>"+JY.Object.notEmpty(l.twoRankMin)+" - "+JY.Object.notEmpty(l.twoRankMax)+"</td>";
					 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.twoPercent)+"%"+"</td>";
					 html+="<td class='center '>"+JY.Object.notEmpty(l.threeRankMin)+" - "+JY.Object.notEmpty(l.threeRankMax)+"</td>";
					 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.threePercent)+"%"+"</td>";
					 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.createUser)+"</td>";
					 html+="<td class='center '>"+JY.Date.Default(l.changeDate)+"</td>";
					 html+="<td class=\"center\">"+
						 "<div class=\"visible-md visible-lg hidden-sm hidden-xs btn-group\">"+
						 "<a href=\"#\" title=\"修改\" onclick=\"editCommission(\'"+l.id+"\',\'"+l.name+"\',\'"+l.typeName+"\',\'"+l.oneRankMin+"\',\'"+l.oneRankMax+"\',\'"+l.onePercent+"\',\'"+l.twoRankMin+"\',\'"+l.twoRankMax+"\',\'"+l.twoPercent+"\',\'"+l.threeRankMin+"\',\'"+l.threeRankMax+"\',\'"+l.threePercent+"\',\'"+l.createUser+"\',\'"+l.changeDate+"\')\"class=\"aBtnNoTD\"><i class=\"icon-edit-sign  bigger-140\"></i></a>"+
						 "<a href=\"#\" title=\"删除\" onclick=\"deleteCommission(\'"+l.id+"\')\"class=\"aBtnNoTD\"><i class=\"icon-remove-sign  bigger-140\"></i></a>"+
						 "</div></td>";	
					 html+="</tr>";
				 }
        		 	
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='11' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }	
 	 
    	 JY.Model.loadingClose();
	 });
}


/** 清除想关佣金记录*/
function deleteCommission(id,merchantId){
	JY.Model.confirm("确认删除吗？",function(){
		JY.Ajax.doRequest(null,jypath +'/backstage/commission/deleteCommission',{id:id},function(data){
			if(data.obj=='1'){
				JY.Model.info("该佣金以关联渠道，不可删除");
			}
			getbaseList(1);
		});
	});
}
/** 修改佣金记录*/
function editCommission(id,name,typeName,oneRankMin,oneRankMax,onePercent,twoRankMin,twoRankMax,twoPercent,threeRankMin,threeRankMax,threePercent,createUser,changeDate){
	setBaseCommissionForm(id,name,typeName,oneRankMin,oneRankMax,onePercent,twoRankMin,twoRankMax,twoPercent,threeRankMin,threeRankMax,threePercent,createUser,changeDate);
	JY.Model.edit("baseCommissionId","修改佣金",function(){
		if(JY.Validate.form("baseCommissionForm")){
			var that =$(this);
			JY.Ajax.doRequest("baseCommissionForm",jypath +'/backstage/commission/createBaseCommission',null,function(data){
				if(data.obj=='1'){
					JY.Model.info("该佣金以关联渠道，不可修改");
				}else{
					that.dialog("close");
					JY.Model.info(data.resMsg,function(){search();});
					getbaseList(1);
				}
			});
		}
	});
}


$(".icon-plus-sign").on("click", function() {
	setBaseCommissionForm(null,null,null,null,null,null,null,null,null,null,null,null,null,null);
	JY.Model.edit("baseCommissionId","新增佣金",function(){
		 if(JY.Validate.form("baseCommissionForm")){
			 var that =$(this);
			 JY.Ajax.doRequest("baseCommissionForm",jypath +'/backstage/commission/insertBaseCommission',null,function(data){
			     that.dialog("close");      
			     JY.Model.info(data.resMsg,function(){search();});
			     getbaseList(1);
			 });
		 }	
	});
});

function setBaseCommissionForm(id,name,typeName,oneRankMin,oneRankMax,onePercent,twoRankMin,twoRankMax,twoPercent,threeRankMin,threeRankMax,threePercent,createUser,changeDate){
	$("#baseCommissionForm input[name$='id']").val(id);
	$("#baseCommissionForm input[name$='name']").val(name);
	$("#baseCommissionForm input[name$='typeName']").val(typeName);
	$("#baseCommissionForm input[name$='oneRankMin']").val(oneRankMin);
	$("#baseCommissionForm input[name$='oneRankMax']").val(oneRankMax);
	$("#baseCommissionForm input[name$='onePercent']").val(onePercent);
	$("#baseCommissionForm input[name$='twoRankMin']").val(twoRankMin);
	$("#baseCommissionForm input[name$='twoRankMax']").val(twoRankMax);
	$("#baseCommissionForm input[name$='twoPercent']").val(twoPercent);
	$("#baseCommissionForm input[name$='threeRankMin']").val(threeRankMin);
	$("#baseCommissionForm input[name$='threeRankMax']").val(threeRankMax);
	$("#baseCommissionForm input[name$='threePercent']").val(threePercent);
	$("#baseCommissionForm input[name$='createUser']").val(createUser);
	$("#baseCommissionForm input[name$='changeDate']").val(changeDate);
}

