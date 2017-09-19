<%@ page contentType="text/html;charset=UTF-8" %>
<%--新增方法--%>
<div id="prepaymentId" class="hide">
	<form id="prepaymentForm" method="POST" onsubmit="return false;" >
		<table cellspacing="0" cellpadding="0" border="0" class="customTable">
			<tbody>
			<tr class="FormData">
				<td class="CaptionTD">商户名称：</td>
				<td class="DataTD">&nbsp;
					<input type="text" size="10" readonly="true"   maxlength="20" name="merchantName" class="FormElement ui-widget-content ui-corner-all isSelect147">
					<input type="text" hidden="true" size="10" readonly="true"   maxlength="20" name="merchantId" class="FormElement ui-widget-content ui-corner-all isSelect147">
					<input type="text" hidden="true" size="10" readonly="true"   maxlength="20" name="id" class="FormElement ui-widget-content ui-corner-all isSelect147">
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">预存款类型：</td>
				<td class="DataTD">&nbsp;
					<select name="payType" class="FormElement ui-widget-content ui-corner-all isSelect147">
						<option value = 1>终端机额度</option>
						<option value = 2>其他</option>
					</select>
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">添加金额：</td>
				<td class="DataTD">&nbsp;
					<input maxlength="9" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" name="payMoney" type="text"  size="10"  class="FormElement ui-widget-content ui-corner-all isSelect147">元</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">剩余金额：</td>
				<td class="DataTD">&nbsp;
					<input name="balance" readonly="readonly" type="text" size="10"   class="FormElement ui-widget-content ui-corner-all isSelect147">元
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">预警金额：</td>
				<td class="DataTD">&nbsp;
					<input maxlength="9" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" name="warningMoney" type="text" size="10" class="FormElement ui-widget-content ui-corner-all isSelect147">元</td>
			</tr>
			</tbody>
		</table>
	</form>
</div>