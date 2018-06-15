/**
 * 用户登录
 * @returns {Boolean}
 */
function login() {
	$("#submit_btn").attr("src","/resource/images/button_login_disabled.png");
	$("#submit_btn").attr("disabled","disabled");
	var account = $("#account").val();
	var password = $("#password").val();
	
	$.post("/users/login",{account:account,password:password},function(res){
		if(res.code==200){
			$("#p_login_t").html("登录成功!");
			window.location.href=res.obj;
		} else {
			$("#password").focus();
			$("#password").val("");
			$("#p_login_t").show();
			$("#p_login_t").html(res.msg);
		}
		$("#submit_btn").removeAttr("disabled");
		$("#submit_btn").attr("src","/resource/images/button_login.png")
	  });
	return false;
}


 