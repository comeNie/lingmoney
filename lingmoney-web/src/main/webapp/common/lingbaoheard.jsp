<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<div class="content-top clearfix">
	<div class="left">
			<c:choose>
				<c:when test="${CURRENT_USER==null }">
					<p class="loginA">用户登录</p>
					<p id="market-register-tip"></p>
					<form onsubmit="return login()">
							<table>
								<tr>
									<td colspan="2">
										<div class="form-div">
											<i style="background: url('/resource/images/bg_input.gif') no-repeat left top"></i>
											<input type="text" name="account" id="account" required
												placeholder="手机号码/用户名" onfocus="this.style.color='#434343'" />
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="form-div">
											<i style="background: url('/resource/images/bg_input.gif') no-repeat left bottom"></i>
											<input type="password" id="password" name="password"
												placeholder="登录密码" onfocus="this.style.color='#434343'" />
										</div>
									</td>
								</tr>
								<tr>
									<td align="right"><a href="/users/forgetpassword">忘记密码？</a></td>
								</tr>
								<tr>
									<td colspan="2" height="45px">
										<input type="image" src="/resource/images/button_zhuce.png" id='submit_btn'
										style="padding-left: 0; background: none; border: none; width: 200px;" />
									</td>
								</tr>
								<tr>
									<td align="center" height="25px;" colspan="2">还没有领钱儿帐号？
										<a href="/users/regist" style="color: #ed6e31">注册</a>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="padding-left: 22px; background: url('/resource/images/bao.png') no-repeat 5px center;
									 font-size: 12px">领钱儿保障您的账户资金流转安全</td>
								</tr>
							</table>
					</form>
				</c:when>
				<c:otherwise>
					<ul class="ul-photo clearfix">
					<li class="left"><c:choose>
							<c:when test="${userInfo.picture==null  }">
								<img src="/resource/images/img_memberTouxiang.gif" width="50" height="50" />
							</c:when>
							<c:otherwise>
								<img src="${userInfo.picture }" width="50" height="50" />
							</c:otherwise>
						</c:choose></li>
					<li class="left left_right">
						<div class="left_right_top clearfix">
							<strong class="left">${userInfo.nickname }</strong>
							<strong class="right" style="width: auto">
								<a href="/users/logout?domainCityCode=${AREADOMAIN.bdCityCode }&location=lbmarket">[退出]</a>
							</strong>
						</div>
						<div class="left_right_bottom clearfix">
							<span id="lingbaoShow" class="left">${userInfo.lingbao }</span>
							<a href="/helpCenterLingbao" target="_blank" class="right"></a>
						</div>
					</li>
					</ul>
					<div class="border"></div>
					<div class="div-manager">
						<span style="background: url(/resource/images/lingbaoMarketImages/dh.png) no-repeat">
							<a href="/lingbaoExchange/exchangeRecord?type=2">兑换管理</a>
						</span>
						<span style="background: url(/resource/images/lingbaoMarketImages/gwc.png) no-repeat">
							<a href="/lingbaoExchange/exchangeRecord?type=1">购物车<em>(${userInfo.cartGiftNum })</em></a>
						</span>
						<span style="background: url(/resource/images/lingbaoMarketImages/dizxg.png) no-repeat">
							<a href="javascript:void(0)" id="btnChangeAddr">修改收货地址</a>
						</span>
					</div>
					<div class="div-choujiang" style="height: 62px">
						<c:if test="${userInfo.isSignToday!=-1 }">
							<c:choose>
								<c:when test="${userInfo.isSignToday==1 }">
									<button class="btn_unable">今日已签到</button>
								</c:when>
								<c:otherwise>
									<button id="signInBtn" onclick="checkIn()">每日签到</button>
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
				</c:otherwise>
			</c:choose>
	</div>
	<div class="right">
		<div class="fullSlide">
    	  <div class="bd">
    	    <ul>
    	    	<c:if test="${bannerList!=null }">
					<c:forEach items="${bannerList }" var="banner">
						<li style="background: #81d5c6 center 0 no-repeat;">
							<a href="${banner.webUrl }?typeID=${banner.ltTypeId}"><img src="${banner.webBannerPath }" class="right_banner" /></a>
						</li>
					</c:forEach>
				</c:if>
    	    </ul>
    	  </div>
    	  <div class="hd">
    	    <ul>
    	    </ul>
    	  </div>
    	</div> 
	</div>
</div>

<script type="text/javascript">
/*banner*/
$(".fullSlide").hover(function() {
   $(this).find(".prev,.next").stop(true, true).fadeTo("show", 0.5)
},
function() {
   $(this).find(".prev,.next").fadeOut()
});
$(".fullSlide").slide({
   titCell: ".hd ul",
   mainCell: ".bd ul",
   effect: "fold",
   autoPlay: true,
   autoPage: true,
   trigger: "click",
   startFun: function(i) {
       var curLi = $(".fullSlide .bd li").eq(i);
       if ( !! curLi.attr("_src")) {
           curLi.css("background-image", curLi.attr("_src")).removeAttr("_src")
       }
   }
});

/**
 * 签到
 * @param uId
 * @param signid
 */
function checkIn() {
	$.ajax({
		url:'/lbmarket/checkIn',
		type:'post',
		success:function(data) {
			if(data.code==200) {
				var lingbao = $("#lingbaoShow").html();
				lingbao = Number(lingbao)+data.obj;
				$("#successQian").find("h2").text("签到成功");
				$("#successContent").text("恭喜您今日签到成功！");
				$("#lingbaoShow").html(lingbao);
				$("#successQianP").html("+"+data.obj+"领宝");
				showPop("#successQian");
				$(".successQian_con div").css("background","url('/resource/images/lingbaoMarketImages/bg_successQiandao.gif') center top no-repeat");
				$("#signInBtn").after("<button class='btn_unable'>今日已签到</button>");
				$("#signInBtn").remove();
			}
		}
	});
}

/**
 * 登录验证
 * @returns {Boolean}
 */
function marketTestLogin() {
	var account = document.getElementById("account").value;
	var loginPsw = document.getElementById("loginPsw").value;
	var con = sendReq("/users/testLogin.html", "account=" + account
			+ "&loginPsw=" + loginPsw);
	if (con == "success") {
		return true;
	} else {
		document.getElementById("market-register-tip").innerHTML = "用户名或密码错误!";
		document.getElementById("loginPsw").focus();
		document.getElementById("loginPsw").value = "";
		return false;
	}
} 
 
 /**
  * 用户登录
  * @returns {Boolean}
  */
function login() {
	$("#market-register-tip").css('height','18px');
 	$("#submit_btn").attr("src","/resource/images/button_login_disabled.png");
 	$("#submit_btn").attr("disabled","disabled");
 	var account = $("#account").val();
 	var password = $("#password").val();
 	
 	$.post("/users/login?location=lbmarket",{account:account,password:password},function(res){
 		if(res.code==200){
 			$("#p_login_t").html("登录成功!");
 			window.location.href=res.obj;
 		}else{
 			$("#password").focus();
 			$("#password").val("");
 			if(res.msg.length>17){
 				// 提示消息过长遮挡input
 				$("#market-register-tip").css('height','35px');
 			}
 			$("#market-register-tip").html(res.msg);
 		}
 		$("#submit_btn").removeAttr("disabled");
 		$("#submit_btn").attr("src","/resource/images/button_login.png")
 	  });
 	return false;
 }
</script>