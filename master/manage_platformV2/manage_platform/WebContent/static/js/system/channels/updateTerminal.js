function closeJeBox(){

	var machineNum = $("#machineNum").val();
	if(machineNum == ""){
		JY.Model.info("机器号不能为空");
		return false;
	}

	JY.Ajax.doRequest("form1",jypath +'/channels/terminal/updateTerminal',null,function(data){
		if(data.resMsg=="success"){
			JY.Model.info("保存成功!");
			window.parent.location.reload();
			window.parent.jeBox.close("jbx");
		}else{
			JY.Model.info("保存失败!");return false;
		}
	});

}

function findMerchant() {
	var $selectmercha=$("#merchantName");
	var niw='<option>请选择</option>';
	JY.Ajax.doRequest("baseForm", jypath + '/channels/onlinedata/findMerchant', null, function (data) {
		var obj = data.obj;
		var list = obj.list;
		var results = list.results;
		if (results != null && results.length > 0) {
			var leng = obj.size;//计算序号
			for (var i = 0; i < results.length; i++) {
				var l = results[i];
				niw+="<option value='"+l.mId+";"+l.mName+"'>"+l.mName+"</option>";
			}
		}
		//selectmerchantId.innerHTML=niw;
		$selectmercha.append(niw);
		$("#merchantName").chosen();
	});

}
function findMerchantForUpdate() {
	var merchantId = $("#merchantId").val();
	var $selectmercha=$("#merchantName");
	var niw='<option>请选择</option>';
	JY.Ajax.doRequest("baseForm", jypath + '/channels/onlinedata/findMerchant', null, function (data) {
		var obj = data.obj;
		var list = obj.list;
		var results = list.results;
		if (results != null && results.length > 0) {
			var leng = obj.size;//计算序号
			for (var i = 0; i < results.length; i++) {
				var l = results[i];
				if(merchantId== l.mId){
					niw+="<option value='"+l.mId+";"+l.mName+"' selected='selected'>"+l.mName+"</option>";
				}else{
					niw+="<option value='"+l.mId+";"+l.mName+"'>"+l.mName+"</option>";
				}

			}
		}
		//selectmerchantId.innerHTML=niw;
		$selectmercha.append(niw);
		$("#merchantName").chosen();
	});

}



