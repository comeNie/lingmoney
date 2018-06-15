// 修改邮箱窗口
function showWin(){
	$("#vailCodeBtn").removeAttr("disabled");
	$("#vailCodeBtn").html("获取验证码")
	$("#emailInput").val('');
	$("#rz-box-bg").show();
	$(".updateEmail").show();
}
function cencleEmail(){
	$(".updateEmail input").val("");
	$(".updateEmail span").html("");
	$(".updateEmail").hide();
	$("#rz-box-bg").hide();
}
var emailFlag=false;
// 发送邮箱验证码
function sendVerifyEmail(val){
	var email = $("#emailInput").val();
	//验证邮箱
	if(!emails()){
		return;
	}
	$.post("/users/sendVailEmail",{email:email},function(d){
		if(d.code!==200){
			emailFlag = false;
			$("#spanSendEmail").html(d.msg)
			return false;
		}else{
			emailFlag = true;
			$("#spanSendEmail").html("");
			// 开始倒计时
			settimeEmail(val);
			return true;
		}
		
	})
}

//获取验证码倒计时 
var emailCountdown = 60;
var emailTime;//计时对象
// val 为button对象
function settimeEmail(obj) {
	var val=$(obj);
	var id = val.attr("id");
	var count; 
	count = emailCountdown;
	// 提交
	if (count == 0) {
		val.removeAttr("disabled");
		val.html("重新获取验证码")
		emailCountdown = 60;
	} else {
		val.attr("disabled", true);
		val.html("重新发送(" + count + ")");
		val.val("重新发送(" + count + ")");
		count--;
		emailCountdown = count;
		if (count > -1) {
			emailTime=setTimeout(function() {
				settimeEmail(val)
			}, 1000)

		}
	}
}
//点击关闭提示框按钮时取消计时
$('.rz-close,.reset').on('click',function(){
	clearTimeout(emailTime);//清除计时对象
	emailCountdown=60;//恢复倒计时原始值
})

function emails() {
	var par = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
	var email = $("#emailInput").val();
	var ret = par.test(email);
	if (!ret) {
		$("#spanSendEmail").html("邮箱格式错误")
		return false;
	}
	$("#spanSendEmail").html("")
	return true;
}
function vailCode(){
	if(!emailFlag){
		return true;
	}
	var par=/^([0-9|A-Za-z]{6})$/;
	var vailCode = $("#emailVailCode").val();
	var ret = par.test(vailCode);
	if (!ret) {
		$("#spanEmailCode").html("验证码格式错误")
		return false;
	}
	$("#spanEmailCode").html("")
	return true;
}

function saveEmail(){
	if(!emailFlag){
		$("#spanEmailCode").html("请获取验证码")
		return;
	}
	if(!emails() ||!vailCode()){
		return;
	}
	var emailVailCode = $("#emailVailCode").val();
	var email = $("#emailInput").val();
	$.post("/users/setEmail",{email:email,vailCode:emailVailCode},function(d){
		if(d.code!==200){
			$("#spanEmailCode").html(d.msg)
			return false;
		}
		$("#spanSendEmail").html("");
		sAlert1("修改成功",d.code);
	})
}



