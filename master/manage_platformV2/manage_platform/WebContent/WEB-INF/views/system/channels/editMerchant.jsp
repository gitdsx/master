<%@ page contentType="text/html;charset=UTF-8" %>
<head>
	<link rel="stylesheet" href="${jypath}/static/plugins/webuploader/css/webuploader.css" />
	<link rel="stylesheet" href="${jypath}/static/apidoc/jquery/css/colorbox.css" />
	<script src="${jypath}/static/js/ace/ace-extra.min.js"></script>
	<script src="${jypath}/static/plugins/webuploader/js/webuploader.nolog.min.js"></script>
</head>
<div id="avDiv" class="hide">
	<form id="avForm" method="POST" onsubmit="return false;" >
		<table  class="customTable">
			<tr>
			<tr style="display:none">
				<td colspan="2" class="ui-state-error">
					<input type="hidden" name="mId" >
					<input type="hidden" name="mParentMerchant" id="mParentMerchant">
					<input type="hidden" name="mStatus" >
					<input type="hidden" name="bcId" id="bcId">
					<input type="hidden" name="bcIdLine" id="bcIdLine">
					<input type="hidden" name="mLevel" id="mLevels">
					<input type="hidden" name="line" id="line">
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD"><font color="red">*</font>渠道名称：</td>
				<td class="DataTD">&nbsp;
					<input type="text"   jyValidate="required"  maxlength="16" name="mName" class="FormElement ui-widget-content ui-corner-all"></td>
				<td class="CaptionTD">渠道类型：</td>
				<td class="DataTD">&nbsp;
					<select name="mType" id="mType" style="width:163px;"></select>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD"><font color="red">*</font>渠道电话号：</td>
				<td class="DataTD">&nbsp;
					<input type="text" jyValidate="required"  maxlength="32" name="mMobile" class="FormElement ui-widget-content ui-corner-all">
				</td>
				<td class="CaptionTD">联系人：</td>
				<td class="DataTD">&nbsp;
					<input type="text"   maxlength="32" name="mContactUser" class="FormElement ui-widget-content ui-corner-all">
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">身份证：</td>
				<td class="DataTD">&nbsp;
					<input type="text"  maxlength="32" name="mIdCard" class="FormElement ui-widget-content ui-corner-all"></td>
				<td class="CaptionTD">渠道地址：</td>
				<td class="DataTD">&nbsp;
					<textarea style="width:163px;" rows="2"  cols="10" maxlength="100" name="mAddress" multiline="true" class="FormElement ui-widget-content ui-corner-all isSelect147"></textarea>
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">彩票账户：</td>
				<td class="DataTD">&nbsp;
					<input type="text"  readonly="readonly"   maxlength="16" name="mCpUserId" class="FormElement ui-widget-content ui-corner-all"></td>
				<td class="CaptionTD">系统用户登录名：</td>
				<td class="DataTD">&nbsp;
					<input type="text"   readonly="readonly"  maxlength="32" name="mAccountId" class="FormElement ui-widget-content ui-corner-all"></td>
			</tr>
			<tr class="FormData">
				<td id="edOnlineCommission" class="CaptionTD">线上佣金：</td>
				<td id="edOnlineCommission1" class="DataTD">&nbsp;
					<select name="name" id="edCommissionName" style="width:163px;"></select>
				</td>
				<td class="CaptionTD">渠道状态：</td>
				<td class="DataTD">&nbsp;
					<select name="merIsValid" id="merIsValid" style="width:163px;"></select>
				</td>
			</tr>
			<tr class="FormData">
				<td id="edOfflineCommission" class="CaptionTD">线下佣金：</td>
				<td id="edOfflineCommission1" class="DataTD">&nbsp;
					<select name="nameLine" id="edCommissionNameLine" style="width:163px;"></select>
				</td>
				<td class="CaptionTD">渠道反佣类型：</td>
				<td>&nbsp;
					<select id="mCommionType" name="mCommionType" style="width:163px;">
						<option value="1">日返</option>
						<option value="2">周返</option>
						<option value="3">月返</option>
						<option value="4">季度反</option>
					</select>
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">渠道等级:</td>
				<td>&nbsp;
					<select id="editMerchantLevel" name="editMerchantLevel" style="width:163px;">
						<option value="1">一级渠道</option>
						<option value="2">二级渠道</option>
					</select>
				</td>
				<td id="edParentMerchantTd" class="CaptionTD">上级渠道：</td>
				<td id="edParentMerchantTd1" class="DataTD">&nbsp;
					<select name="editParentMerchant" id="editParentMerchant" style="width:163px;"></select>
				</td>
			</tr>
			<tr class="FormData">
				<td class="CaptionTD">审核进度：</td>
				<td class="DataTD">&nbsp;
					<select name="mStatus" id="mStatus" style="width:163px;"></select>
				</td>
				<td class="CaptionTD">渠道简介：</td>
				<td class="DataTD">&nbsp;
					<textarea style="width:163px;" rows="2" cols="10" maxlength="100" name="mIntroduce" multiline="true" class="FormElement ui-widget-content ui-corner-all isSelect147"></textarea>
				</td>
			</tr>

			<tr class="FormData">
				<td class="CaptionTD">渠道二维码：</td>
				<td class="DataTD">&nbsp;
					<div id="qrCodeDiv">
						<img class="mBarcode" id="mBarcode" name="mBarcode" src="" onload="if(this.width > 150 && this.height >150) this.width = 150 ,this.height =150 " >
					</div>
				</td>
				<td class="CaptionTD" width=100>三证：</td>
				<td id="savePaths" hidden="true">&nbsp;
					<input  type="text"  id="savePath" readonly="readonly"  maxlength="32" name="mLicense">
				</td>
				<td class='center hidden-480'>
					<div class="row-fluid">
						<ul class="ace-thumbnails" id="pic">

						</ul>
					</div>
				</td>
			</tr>
			</tbody>
		</table>
	</form>
</div>

<script src="${jypath}/static/js/system/channels/editMLicense.js"></script>
<script src="${jypath}/static/apidoc/JYUI/assets/js/jquery.colorbox-min.js"></script>
<script type="text/javascript">
    jQuery(function($) {
        var colorbox_params = {
            reposition:true,
            scalePhotos:true,
            scrolling:false,
            previous:'<i class="icon-arrow-left"></i>',
            next:'<i class="icon-arrow-right"></i>',
            close:'&times;',
            current:'{current} of {total}',
            maxWidth:'100%',
            maxHeight:'100%',
            onOpen:function(){
                document.body.style.overflow = 'hidden';
            },
            onClosed:function(){
                document.body.style.overflow = 'auto';
            },
            onComplete:function(){
                $.colorbox.resize();
            }
        };

        $('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
        $("#cboxLoadingGraphic").append("<i class='icon-spinner orange'></i>");//let's add a custom loading icon
    })
</script>