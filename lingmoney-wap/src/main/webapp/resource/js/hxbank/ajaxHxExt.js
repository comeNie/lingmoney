$(function(){
	
	$("#loginSubmit").click(function () {
        var options = {
            url: "/users/login",
            success: function (data) {
            	showMyModal("用户登录", data.msg, "");
            	$('#myModal').modal("show");
            }
        };
        $("#loginForm").ajaxForm(options);
    })
    
    $("#loginOut").click(function () {
        var options = {
            url: "/users/logout",
            success: function (data) {
            	showMyModal("退出登录", data.msg, "");
            	$('#myModal').modal("show");
            }
        };
        $("#loginForm").ajaxForm(options);
    })
	
	$("#accountOpenSubmit").click(function () {
        var options = {
            url: "/bank/accountOpen",
            success: function (data) {
            	if(data.code == 200){
            		fillInBankFormData(data);
            	}else{
            		showMyModal("开通E账户提示", data.msg, "");
            		$('#myModal').modal("show");
            	}
            }
        };
        $("#accountOpenForm").ajaxForm(options);
    });
	
	$("#accountRecharge").click(function () {
        var options = {
            url: "/bank/accountRecharge",
            success: function (data) {
            	if(data.code == 200){
            		fillInBankFormData(data);
            	}else{
            		showMyModal("开通E账户提示", data.msg, "");
            		$('#myModal').modal("show");
            	}
            }
        };
        $("#accountRechargeForm").ajaxForm(options);
    });
})

/**
 * 通用弹窗
 * @param title
 * @param info
 * @returns
 */
function showMyModal(title, info, okbutton){
	if(okbutton == ""){
		$('#modal_ok_button').hide();
	}else{
		$('#modal_ok_button').show();
	}
	$('#myModalLabel').html(title);
	$('#myModalInfo').html(info);
	$('#myModal').modal("show");
}

/**
 * 填写银行表单数据提交
 * @param data
 * @returns
 */
function fillInBankFormData(data){
	$("#bankForm").attr("action", data.obj.bankUrl);
	$("#bankFormRequestData").val(data.obj.requestData);
	$("#bankFormTransCode").val(data.obj.transCode);
	$("#bankForm").submit();
}
