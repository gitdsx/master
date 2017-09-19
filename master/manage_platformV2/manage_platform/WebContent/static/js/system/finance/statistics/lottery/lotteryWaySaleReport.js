$(function () {
	getbaseList();
});

function download1() {
	var date = $("#beginTime").val();
	JY.Ajax.doRequest("baseForm",jypath +'/statistics/lotteryWaySale/download',{fileName:date});
	
}

function getbaseList(init){
	if(init==1)$("#baseForm .pageNum").val(1);
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/statistics/lotteryWaySale/findByPage',null,function(data){
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
            		 html+="<td class='center hidden-550''>"+JY.Date.Format(l.date,'yyyy-MM-dd')+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.lottery10066)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.lottery10059)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.lottery10058)+"</td>";
					 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.lottery10042)+"</td>";
					 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.lottery10041)+"</td>";
					 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.lottery10040)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.lottery10039)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.lottery10030)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.lottery10026)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.lottery100234)+"</td>";
            		 html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.lotteryAll)+"</td>";
            		 html+="</tr>";
            	 }
        		 $("#baseTable tbody").append(html);
        		 JY.Page.setPage("baseForm","pageing",pageSize,pageNum,totalRecord,"getbaseList");
        	 }else{
        		html+="<tr><td colspan='10' class='center'>没有相关数据</td></tr>";
        		$("#baseTable tbody").append(html);
        		$("#pageing ul").empty();//清空分页
        	 }

    	 JY.Model.loadingClose();
	 });
}



$('#resetData').bind('click', function() {
	var beginTime=$("input[name=beginTime]").val().substring(0,7).trim();
	var endTime=$("input[name=endTime]").val().substring(0,7).trim();
	if(beginTime==""||endTime==""){
		jeBox.msg("请输入重置时间，不可跨月重置");
		return;
	}
	if(beginTime!=endTime){
		jeBox.msg("不可跨月重置");
		return;
	}
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/statistics/lotteryWaySale/manual',null,function(data){
		JY.Model.loadingClose();
		var obj=data.obj;
		JY.Model.info(obj.msg,function(){ getbaseList();});
	});
})
