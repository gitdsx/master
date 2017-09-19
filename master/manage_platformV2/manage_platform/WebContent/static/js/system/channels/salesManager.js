$(function () {
	//下拉框
	JY.Dict.setSelect("selectisValid","isValid",2,"全部");
	getbaseList();

    //增加回车事件
	$("#baseForm").keydown(function(e){
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 search();
		 } 
	});

    $(".icon-plus-sign").on("click", function() {
         cleanForm();
		 JY.Model.edit("addSalesM","新增",function(){
			if(JY.Validate.form("form1")){
				var that =$(this);
				JY.Ajax.doRequest("form1",jypath +'/channels/salesManager/add',null,function(data) {
                    if(data.resMsg=="1"){
                        that.dialog("close");
                        getbaseList();
                        JY.Model.info("添加成功!");
                    }else if(data.resMsg=="2"){
                        JY.Model.info("电话号码不合法!");
                    }else if(data.resMsg=="3"){
                        JY.Model.info("电话号已存在!");
                    }else{
                        JY.Model.info("添加失败!");
                    }
				});
			};
		});
    });

	//批量删除
	$('#delSalesManager').on('click', function(e) {
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
				JY.Ajax.doRequest(null,jypath +'/channels/salesManager/delSalesManager',{chks:chks.toString()},function(data){
					getbaseList();
					JY.Model.info(data.resMsg,function(){search();});
				});
			});		
		}		
	});
});

function search(){
	$("#searchBtn").trigger("click");
}

function emptyRole(){
	$("#roleName").prop("value","");
	$("#auForm input[name$='roleId']").prop("value","0");
}

var preisShow=false;//窗口是否显示

function showRole() {
	if(preisShow){
		hideRole();
	}else{
		var obj = $("#roleName");
		var offpos = $("#roleName").position();
		$("#roleContent").css({left:offpos.left+"px",top:offpos.top+obj.heith+"px"}).slideDown("fast");	
		preisShow=true;
	}
}

function hideRole(){
	$("#roleContent").fadeOut("fast");
	preisShow=false;
}

function getbaseList(init){
	JY.Model.loading();
	JY.Ajax.doRequest("baseForm",jypath +'/channels/salesManager/findByPage',null,function(data){
		$("#baseTable tbody").empty();
		$("#baseHtml ul").empty();
		var obj=data.obj;
		var list=obj.list;
		if(list==null){
			var lists=obj.lists;
			var results=lists.results;
			var pageNum=lists.pageNum,pageSize=lists.pageSize,totalRecord=lists.totalRecord;
		}else{
			var results=list.results;
			var pageNum=list.pageNum,pageSize=list.pageSize,totalRecord=list.totalRecord;
		}
		var permitBtn=obj.permitBtn;
        var mName = obj.mName;
		var html="";
		if(results!=null&&results.length>0){
			var leng=(pageNum-1)*pageSize;//计算序号
			for(var i = 0;i<results.length;i++){
				var l=results[i];
				html+="<tr>";
                if('1'==l.isValid) {
                    html+="<td class='center'><label> <input type='checkbox' name='ids' value='"+l.mId+"' class='ace' /> <span class='lbl'></span></label></td>";
                }else{
                    html += "<td class='center'> </td>";
                }
				html+="<td class='center'>"+JY.Object.notEmpty(l.mName)+"</td>";
                html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.mMobile)+"</td>";

                if(l.mStatus==0)  html+="<td class='center '>未审核</td>";
				else if (l.mStatus==1) html+="<td class='center '>一审通过</td>";
				else if(l.mStatus==2) html+="<td class='center '>有效</td>";
				else if(l.mStatus==3) html+="<td class='center '>拒绝</td>";
				else  html+="<td class='center '>未知</td>";
                html+="<td class='center hidden-480'><a style='cursor:pointer' onclick='findChildCustomer(\""+l.mId+"\")'>"+JY.Object.notEmpty(l.childCustomerNum)+"</a></td>";
                html+="<td class='center hidden-480'><a style='cursor:pointer' onclick='findChildMerchant(\""+l.mId+"\")'>"+JY.Object.notEmpty(l.childMerchantNum)+"</a></td>";
                html+="<td class='center hidden-480'>"+JY.Object.notEmpty(l.roleName)+"</td>";
                var isValid="";
                if(l.isValid==1){
                    isValid="有效";
                }else if(l.isValid==2){
                    isValid="无效";
                }
                html+="<td class='center hidden-480'>"+JY.Object.notEmpty(isValid)+"</td>";
                html+=JY.Tags.setFunction(l.mId,permitBtn);
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

function findChildCustomer(id){
    var index = jeBox.open({
        cell:"jbx",
        padding:"0",
        title:"查看发展客户",
        maxBtn:true,
        area:["700px","90%"],
        type:2,
        maskClose:true,
        content: 'findChildCustomer?mId='+id,
        button: [ {name: '取消'},
        ],
        success:function(data){
        }
    })
}

function findChildMerchant(id){
    var index = jeBox.open({
        cell:"jbx",
        padding:"0",
        title:"查看发展渠道",
        maxBtn:true,
        area:["700px","90%"],
        type:2,
        maskClose:true,
        content: 'findChildMerchant?mId='+id,
        button: [ {name: '取消'},
        ],
        success:function(data){
        }
    })
}

//修改
function updateSalesManager(id){
	cleanForm();
	$('#fileList').html('');
		JY.Ajax.doRequest(null,jypath +'/channels/salesManager/findParticulars',{mId:id},function(data){
			setForm(data,"2");
		    JY.Model.edit("avDiv","修改",function(){
		    	if(JY.Validate.form("avForm")){
					var that =$(this);
					JY.Ajax.doRequest("avForm",jypath +'/channels/salesManager/updateSalesManager',null,function(data){
                        if(data.resMsg=="1"){
                            that.dialog("close");
                            getbaseList();
                            JY.Model.info("修改成功!");
                        }else if(data.resMsg=="2"){
                            JY.Model.info("修改失败，请确认该商户是否可修改!");
                        }else if(data.resMsg=="3"){
                            JY.Model.info("电话号码不合法!");
                        }else if(data.resMsg=="4"){
                            JY.Model.info("电话号已存在!");
                        }else {
                            JY.Model.info("修改失败!");
                        }
					});
				}
		    });
		});
}

//查看详情
function findParticulars(id){
	cleanForm();
		JY.Ajax.doRequest(null,jypath +'/channels/salesManager/findParticulars',{mId:id},function(data){
			setForm(data,"1");
			JY.Model.check("avDiv");
		});
}

function cleanForm(){
	JY.Tags.isValid("avForm","1");
	JY.Tags.cleanForm("avForm");
    JY.Tags.cleanForm("form1");
	$("#avForm input[name$='roleId']").val('0');//上级资源
	$("#avForm input[name$='loginName']").prop("disabled",false); 
	hideRole();
}

function setForm(data,type){
    if(type=="1"){
        $("#avDiv").ready(function(event) {
            $(this).find('input').attr("readonly","true");
            $(this).find('textarea').attr("readonly","true");
            $(this).find('select').attr("disabled","true");

        });
    }else{
        $("#avDiv").ready(function(event) {
            $(this).find('input').removeAttr("readonly");
            $(this).find('textarea').removeAttr("readonly");
            $(this).find('select').removeAttr("disabled");
        });
    }
	var l=data.obj;
	$("#avForm input[name$='mId']").val(JY.Object.notEmpty(l.mId));							//商户ID
	$("#avForm input[name$='mCpUserId']").val(JY.Object.notEmpty(l.mCpUserId));				//彩票系统账户ID
	$("#avForm input[name$='mName']").val(JY.Object.notEmpty(l.mName));						//商户名称
    var status ="";
    if(l.mStatus==0){
        status='<option value="0">未审核</option>';
	}else if(l.mStatus==1){
        status='<option value="1">一审通过</option>';
	}else if(l.mStatus==2){
        status='<option value="2">有效</option>';
    }else if(l.mStatus==3){
		status='<option value="3">拒绝</option><option value="0">未审核</option>';
    }
    var mStatus=document.getElementById("mStatus");
    mStatus.innerHTML=status;

    var isValid="";
    if(l.isValid=='1'){
        isValid='<option value="1">有效</option>';
    }else{
        isValid='<option value="2">无效</option>';
    }
    var merIsValid=document.getElementById("merIsValid");
    merIsValid.innerHTML=isValid;

//	$("#avForm input[name$='mStatus']").val(JY.Object.notEmpty(status));
	$("#avForm input[name$='mParentMerchant']").val(JY.Object.notEmpty(l.mParentMerchant));	//上级渠道ID
	$("#avForm input[name$='mMobile']").val(JY.Object.notEmpty(l.mMobile));					//商户手机号
	$("#avForm textarea[name$='mAddress']").val(JY.Object.notEmpty(l.mAddress));			//商户地址
	$("#avForm input[name$='mContactUser']").val(JY.Object.notEmpty(l.mContactUser));		//联系人
	$("#avForm input[name$='mContactMobile']").val(JY.Object.notEmpty(l.mContactMobile));	//联系人手机号
	$("#avForm input[name$='mIdCard']").val(JY.Object.notEmpty(l.mIdCard));					//身份证
	$("#avForm textarea[name$='mIntroduce']").val(JY.Object.notEmpty(l.mIntroduce));		//商户简介
	$("#avForm input[name$='mAccountId']").val(JY.Object.notEmpty(l.mAccountId));			//平台用户绑定ID
	$("#avForm").find(".mBarcode").attr("src",JY.Object.notEmpty(l.mBarcode));
	$("#avForm").find(".mLicense").attr("src",JY.Object.notEmpty(l.mLicense));
}