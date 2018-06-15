function oldPsws() {
	var oldPsw = document.getElementById("oldPsw").value;
	var con = sendReq("/myAccount/oldPsw.html", "oldPsw=" + oldPsw);

	if(oldPsw!=""){
		if (con == "overtime") {
			sAlert("登录超时");
			return false;
		}
		if (con == "旧密码有误") {
			document.getElementById("tip_oldPsw").style.display = "block";
			document.getElementById("tip_oldPsw").innerHTML = con;
			document.getElementById("span_oldPsw").style.display = "none";
			return false;
		} else {
			document.getElementById("tip_oldPsw").style.display = "none";
			document.getElementById("span_oldPsw").style.display = "inline-block";
			return true;
		}
	}else{
		document.getElementById("tip_oldPsw").style.display = "block";
		document.getElementById("tip_oldPsw").innerHTML = "请输入旧密码";
		document.getElementById("span_oldPsw").style.display = "none";
		return false;
	}
	
}
function newPsws() {
	var par = /^[a-zA-Z0-9_!@#$%^&]{6,16}$/;
	var newPsw = document.getElementById("newPsw").value;
	var ret = par.test(newPsw);
	if(newPsw != ""){
		if (!ret) {
			document.getElementById("tip_newPsw").style.display = "block";
			document.getElementById("tip_newPsw").innerHTML = "6-16位，可由字母、数字、下划线或特殊符号组成";
			document.getElementById("span_newPsw").style.display = "none";
			return ret;
		}
		document.getElementById("tip_newPsw").style.display = "none";
		document.getElementById("span_newPsw").style.display = "inline-block";
		return ret;
	}else{
		document.getElementById("tip_newPsw").style.display = "block";
		document.getElementById("tip_newPsw").innerHTML = "请输入新密码";
		document.getElementById("span_newPsw").style.display = "none";
		return false;
	}
	
}
function configPsws() {
	var par = /^[a-zA-Z0-9_!@#$%^&]{6,16}$/;
	var newPsw = document.getElementById("newPsw").value;
	var configPsw = document.getElementById("configPsw").value;
	var ret = par.test(configPsw);
	if (configPsw != "") {
		if (!ret) {
			document.getElementById("tip_configPsw").style.display = "block";
			document.getElementById("tip_configPsw").innerHTML = "6-16位，可由字母、数字、下划线或特殊符号组成";
			document.getElementById("span_configPsw").style.display = "none";
			return ret;
		} else {
			if (configPsw != newPsw) {
				document.getElementById("tip_configPsw").style.display = "block";
				document.getElementById("tip_configPsw").innerHTML = "两次密码输入不一致";
				document.getElementById("span_configPsw").style.display = "none";
				return false;
			}
			document.getElementById("tip_configPsw").style.display = "none";
			document.getElementById("span_configPsw").style.display = "inline-block";
			return true;
		}

	} else {
		document.getElementById("tip_configPsw").style.display = "block";
		document.getElementById("tip_configPsw").innerHTML = "请输入确认密码";
		document.getElementById("span_configPsw").style.display = "none";
		return false;
	}
}

function modifyTradingPassWord() {
	$.ajax({
		url : '/bank/modifyTradingPassword',
		type : 'post',
		success : function(d) {
			if (d.code != 200) {
				$("#ask p:first").text(d.msg);
				$("#ask .button_ok").click(function() {
					askClose()
				});
				$("#rz-box-bg").show();
				$("#ask").show();
				offsetDiv("#ask");
				return false;
			}
			// 组织表单，提交数据
			$("#requestData").val(d.obj.requestData);
			$("#transCode").val(d.obj.transCode);
			$("#modifyTradingPassWord").attr("action", d.obj.bankUrl);
			$("#modifyTradingPassWord").submit();
		}
	});
}

//自定义alert
function cusAlert(msg) {
	$("#rz-box-bg").show();
	$("#div_customer_tip").show();
	offsetDiv("#div_customer_tip");
	$("#div_customer_tip h2").text("提示");
	$("#div_customer_tip p:eq(0)").html(msg);
}

function askClose(){
	$("#rz-box-bg").hide();
	$("#ask").hide();
}
/*
 * function updatePsw(){ if(!oldPsws()||!newPsws()||!configPsws()){ return
 * false; }else{ return true; }
 */

