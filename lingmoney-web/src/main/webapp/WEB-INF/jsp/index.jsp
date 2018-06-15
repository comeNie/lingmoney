<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2.0, user-scalable=yes" />

<meta http-equiv="X-UA-Compatible"
	content="IE=edge,chrome=1,IE=EmulateIE7" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
<title>领钱儿 安全便捷的综合互联网金融服务平台</title>
<meta name="KEYWords" content="领钱，领钱儿，互联网理财，互联网金融，网上理财">
<meta name="DEscription"
	content="领钱儿(www.lingmoney.cn)是一家专业互联网金融服务平台，由资深金融从业者创建，保证平台安全性并提供方便快捷的理财服务。">
<meta name="Author" content="领钱儿">
<link rel="stylesheet" href="/resource/css/index.css">
<link rel="stylesheet" type="text/css" href="/resource/css/style.css"
	media="screen" />
<link rel="stylesheet" href="/resource/css/others.css">
<link rel="icon" type="image/x-icon" href="/resource/ico.ico">
<link rel="shortcut icon" type="image/x-icon" href="/resource/ico.ico">
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/resource/js/ajax.js"></script>
<script type="text/javascript" src="/resource/js/kxbdSuperMarquee.js"></script>
<script type="text/javascript" src="/resource/js/index.js"></script>
<script type="text/javascript" src="/resource/js/yxMobileSlider.js"></script>
<script type="text/javascript" src="/resource/js/calculator.js"></script>
<script type="text/javascript" src="/resource/js/jquery.SuperSlide.2.1.2.js"></script>
<script type="text/javascript" src="/resource/js/users/login.js"></script>
<style>
.register_box  li.form {
	position: relative;
	z-index: 1;
}

.register_box #p_login_t {
	position: absolute;
	z-index: 1;
	left: 35px;
	top: 6px;
	color: red;
	font-size: 12px;
	font-weight: normal;
}

/**********首页弹窗样式***********/
.activityForm {
	width: 100%;
	background: rgba(0, 0, 0, 0.9);
	position: fixed;
	z-index: 9999;
	top: 0;
	left: 0;
	text-align: center;
	display: none;
}

.activityForm img {
	height: 100%;
}
.activityForm a{
	position: absolute;
	right: 50px;
	top: 50px;
	font-size: 16px;
	color: #ffffff;
}
/*520弹窗样式*/
.index-Popup-bg {
	width: 100%;
	height: 100%;
	position: fixed;
	opacity: 0.5;
	background: #000;
	z-index: 9999;
	left: 0;
	top: 0;
	display: none;
}
			
.register_Popup {
	position: fixed;
	display: block;
	width: 350px;
	height: 470px;
	top: 30%;
	left: 50%;
	z-index: 10000;
	margin-left: -175px;
	text-align: center;
	display: none;
			}
			
.register_redcard {
	 position: relative;
	display: block;
	width: 350px;
	height: 350px;
	margin: 0 auto;
	margin-bottom: 20px;
	}
			
.register_close {
	width: 40px;
	height: 40px;				
	}
.my_cardStock a{
   position: absolute;
	display: block;
	width: 80px;
	height: 30px;
	top:300px;
	left: 155px;
	
}



/* 早点八弹窗 */
.eight_Popup{
	position: fixed;
	top: 20%;
	left: 50%;
	z-index: 11000;
	margin-left: -300px;
	text-align: center;
	display: none;

}
.eight_Popup p{
	font-size: 35px;
	
}
.eight_two .card_one_red{
	margin-bottom: 20px;
}
.eight_one{
	width: 580px;
	height:161px ;
	background: url(http://static.lingmoney.cn/lingmoneywap/images/9cafc79f-ca5d-4884-84d1-9804710babe4.png);
	position: relative;
}
.eight_text{
    width: 386px;
	height: 52px;
	position: absolute;
	top:40px;
	left: 75px;
}
.eight_close{
    width: 25px;
	height: 25px;
	position: absolute;
	top: 25px;
	left: 520px;
	cursor: pointer;
}
.eight_two{
	width: 580px;
	background: url(http://static.lingmoney.cn/lingmoneywap/images/a68587a7-a2f4-4255-9e28-615803982015.png);
	position: relative;
}
.eight_two p{
	width:400px;
	background:rgba(255,255,255,1);
	border-radius:10px;
	margin: 0 auto;	
	padding: 10px 20px;
	font-size:25px;
	color:rgba(102,102,102,1);
	display: none;
}

.yield_red,.yield_one{
	color: #FE4400;
	font-size:30px ;
}
.eight_three{
	width: 580px;
	height: 99px;
	background: url(http://static.lingmoney.cn/lingmoneywap/images/1e53f754-5c68-4ef8-8926-accead370e9a.png);
	position: relative;
}
.eight_four{
	width: 580px;
	height: 107px;	
	background: url(http://static.lingmoney.cn/lingmoneywap/images/8c2b63b7-5318-4e93-a88e-1f8e84bed3a9.png);
	position: relative;
}
.img-know{
    width: 248px;
	height: 72px;
	position: absolute;
	top: 0;
	left: 20px;
	cursor: pointer;
}
.img-see{
    width: 240px;
	height: 64px;
	position: absolute;
	top:2px;
	left: 300px;
	cursor: pointer;
}
</style>
<!--网页标题滚动效果-->

<script type="text/javascript">
	$(function() {
		$(".media-link li:first").css("cursor", "pointer")
		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		});
	});

	function offsetDiv(s) {
		height = $(s).height() + 30;
		width = $(s).width();
		$(s).css("margin-top", -height / 2);
		$(s).css("margin-left", -width / 2);
	}
</script>
<style type="text/css">
.a_color {
	color: #ea5513;
}

.a_color:hover {
	text-decoration: underline;
}
</style>
<script type="text/javascript">
		var comp = function(end, start) {
		var now = new Date;//当前时间
		var start = new Date(start);//开始时间
		var end = new Date(end);//结束时间
		var images = [];
		var url = images[Math.floor(Math.random() * images.length)];
		$('.activityForm').find('img').attr('src', url);

		if (now < start) {
			$('.activityForm').hide();//活动未开始
		} else if (now > start && now < end) {
			$('.activityForm').slideDown(1000);//活动开始
			setTimeout(function() {
				$(".activityForm").hide()//20秒后隐藏
			}, 20000);

		} else if (now > end) {
			$('.activityForm').hide();//活动结束
		}

	}

	$(document)
			.ready(
					function() {
						var winHeight=$(window).height();
						$('.activityForm').css({
							'height':winHeight
						});
						comp("2017-09-15 23:00:00", "2017-09-15 00:00:00");
						$('.activityForm').on('click',function(){
							window.location.href="http://www.lingmoney.cn/product/list/0_0_0_0_0_1.html";
						});
						$('.activityForm a').on('click',function(event){
							  event.stopPropagation();
							$('.activityForm').hide();
						});

						/*hotimg*/
						$('.hotimgBox').slide({
							titCell : ".hdd ul",
							mainCell : "#hotimg ul",
							effect : "leftLoop",
							trigger : "click",
							vis : "auto",
							autoPage : true,
							autoPlay : true,
							delayTime : 500
						});

						$(".main-head li:last").css("border", "none");
						$(".main3 li:last").css("margin-right", "0");
						/* $(".licai li:last").css("margin-right", "0"); */
						/*产品三大类*/
						$(".licai1").find(".title_licai").css(
								"background-color", "#611987");
						$(".licai2").find(".title_licai").css(
								"background-color", "#29a7e1");
						$(".licai3").find(".title_licai").css(
								"background-color", "#8fc320");

						$(".licai1").hover(function() {
							$(this).css({
								"border" : "1px solid #611987"
							})
						}, function() {
							$(this).css("border", "1px solid #d8d8d8")
						})

						$(".licai2").hover(function() {
							$(this).css({
								"border" : "1px solid #29a7e1"
							})
						}, function() {
							$(this).css("border", "1px solid #d8d8d8")
						})

						$(".licai3").hover(function() {
							$(this).css({
								"border" : "1px solid #8fc320"
							})
						}, function() {
							$(this).css("border", "1px solid #d8d8d8")
						})

						/*产品三大类*/

						/*新闻滚动*/
						$('#marqueen2').kxbdSuperMarquee({
							distance : 40,
							time : 5,
							btnGo : {
								up : "#goU",
								down : "#goD"
							},
							direction : "up"
						});
						/*新闻滚动*/

						/*热点产品*/
						$(".tt ul:eq(0)").find(".income_strong").css("color",
								"#ea560e");
						$(".tt ul:eq(1)").find(".income_strong").css("color",
								"#f6b920");
						$(".tt ul:eq(2)").find(".income_strong").css("color",
								"#25a8e0");
						$(".tt ul:eq(3)").find(".income_strong").css("color",
								"#5F1885");
						$(".tt ul:eq(4)").find(".income_strong").css("color",
								"#8fc320");

						/*产品系列*/
						$(".tt ul:eq(0)").find("i").css("background-color",
								"#ea560e");
						$(".tt ul:eq(1)").find("i").css("background-color",
								"#f6b920");
						$(".tt ul:eq(2)").find("i").css("background-color",
								"#25a8e0");
						$(".tt ul:eq(3)").find("i").css("background-color",
								"#5F1885");
						$(".tt ul:eq(4)").find("i").css("background-color",
								"#8fc320");

						$(".tt ul:eq(0)").hover(function() {
							$(this).css("border", "1px solid #ea560e")
						}, function() {
							$(this).css("border", "1px solid #dcdcdc")
						})
						$(".tt ul:eq(1)").hover(function() {
							$(this).css("border", "1px solid #f6b920");
						}, function() {
							$(this).css("border", "1px solid #dcdcdc");
						})
						$(".tt ul:eq(2)").hover(function() {
							$(this).css("border", "1px solid #25a8e0");
						}, function() {
							$(this).css("border", "1px solid #dcdcdc");
						})
						$(".tt ul:eq(3)").hover(function() {
							$(this).css("border", "1px solid #5F1885");
						}, function() {
							$(this).css("border", "1px solid #dcdcdc");
						})
						$(".tt ul:eq(4)").hover(function() {
							$(this).css("border", "1px solid #8fc320");
						}, function() {
							$(this).css("border", "1px solid #dcdcdc");
						})
						$(".menu0 li:last").css("border-right", "none");
						/*热点产品*/

						/*会员登陆*/
						//alert($("#session").val()=="");
						if ($("#session").val() == "") {
							//alert("未登录");
							$(".register ul li a:eq(0)")
									.css("color", "#ed6e31");
							$(".register ul li a:eq(0)")
									.hover(
											function() {
												$(".register ul li a:eq(0)")
														.css("color", "#ed6e31")
												$(".register ul li a:eq(1)")
														.css("color", "#fff")
												$(".register")
														.css("background",
																"url(/resource/images/bg_top.png) no-repeat left top");
												$(".register ol li").hide();
												$(".register ol li:eq(0)")
														.show();

											}, function() {
											});

							$(".register ul li a:eq(1)")
									.hover(
											function() {
												$(".register ul li a:eq(1)")
														.css("color", "#ed6e31");
												$(".register ul li a:eq(0)")
														.css("color", "#fff");
												$(".register")
														.css("background",
																"url(/resource/images/bg_top2.png) no-repeat left top");
												$(".register ol li").hide();
												$(".register ol li:eq(1)")
														.show();
												$("li.form input").css(
														"background", "#fff")

											}, function() {
											});
						} else {
							//alert("已登陆");
							$(".register ul li a:eq(1)")
									.css("color", "#ed6e31");
							$(".register")
									.css("background",
											"url(/resource/images/bg_top2.png) no-repeat left top");
							$(".register ol li:eq(0)").hide();
							$(".register ol li:eq(1)").show();
							$(".register ul li a:eq(0)")
									.hover(
											function() {
												$(".register ul li a:eq(0)")
														.css("color", "#ed6e31")
												$(".register ul li a:eq(1)")
														.css("color", "#fff")
												$(".register")
														.css("background",
																"url(/resource/images/bg_top.png) no-repeat left top");
												$(".register ol li").hide();
												$(".register ol li:eq(0)")
														.show();

											}, function() {
											});

							$(".register ul li a:eq(1)")
									.hover(
											function() {
												$(".register ul li a:eq(1)")
														.css("color", "#ed6e31");
												$(".register ul li a:eq(0)")
														.css("color", "#fff");
												$(".register")
														.css("background",
																"url(/resource/images/bg_top2.png) no-repeat left top");
												$(".register ol li").hide();
												$(".register ol li:eq(1)")
														.show();

											}, function() {
											});
						}
						/*会员登陆*/

						/*计算器*/
						$("a.close").click(function() {
							$(".calculator").slideUp(500);
							$("#rz-box-bg").hide();
							resets();
						})
						$(".jsq").click(function() {
							offsetDivC(".calculator");
							$(".calculator").slideDown(1000);
							$("#rz-box-bg").show();
							resets();
						})
						/*计算器*/

						$("#nav01").addClass("nav-hover-main");
						$('.son_ul').hide(); //初始ul隐藏
						$('.select_box span').hover(function() { //鼠标移动函数
							$(this).parent().find('ul.son_ul').slideDown(); //找到ul.son_ul显示
							$(this).parent().find('li').hover(function() {
								$(this).addClass('hover')
							}, function()

							{
								$(this).removeClass('hover')
							}); //li的hover效果
							$(this).parent().hover(function() {
							}, function() {
								$(this).parent().find("ul.son_ul").slideUp();
							});
						}, function() {
						});
						$('ul.son_ul li').click(
								function() {
									$(this).parents('li').find('span').html(
											$(this).html());
									$(this).parents('li').find('ul').slideUp();
								});

						$("#loginPsw_pre").focus(function() {
							$(this).hide();
							$("#loginPsw").show();
						});
						
						/*520弹窗关闭*/
						$('.register_close').click(function(){	
						$(".register_Popup,.index-Popup-bg").hide();
						})
						
						/*520弹窗判断*/	
						$.ajax({
							url: '/activityReward/alertControl',
							type: 'post',
							dataType: 'json',
							success: function(d) {
								  if(d.count==1){
								  	$(".register_Popup,.index-Popup-bg").show()
								  }
								},
								error: function(d) {}
						});	
					});
</script>
</head>
<body>
	<div class="activityForm">
		<a href="javascript:void(0);">关闭</a>
		<img alt="" src="">
	</div>
	<div id="rz-box-bg"></div>
	<input type="hidden" id="session" value="${CURRENT_USER }" />
	<jsp:include page="/common/calculator.jsp"></jsp:include>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<!--banner -->
	<div class="fullSlide">
		<div class="bd">
			<ul>
				<c:forEach items="${listInfoBigBanner }" var="item">
					<li _src="url(${item.path })"
						style="background: #81d5c6 center 0 no-repeat;"><a
						href="${item.url }"></a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="hd">
			<ul>
			</ul>
		</div>
	</div>
	<script type="text/javascript">
		jQuery(".fullSlide").hover(
				function() {
					jQuery(this).find(".prev,.next").stop(true, true).fadeTo(
							"show", 0.5);
				}, function() {
					jQuery(this).find(".prev,.next").fadeOut();
				});
		jQuery(".fullSlide").slide(
				{
					titCell : ".hd ul",
					mainCell : ".bd ul",
					effect : "fold",
					autoPlay : true,
					autoPage : true,
					trigger : "click",
					startFun : function(i) {
						var curLi = jQuery(".fullSlide .bd li").eq(i);
						if (!!curLi.attr("_src")) {
							curLi.css("background-image", curLi.attr("_src"))
									.removeAttr("_src");
						}
					}
				});
	</script>

	<!--快速登录-->
	<div class="register_box">
		<div class="register">
			<ul class="clearfix">
				<li><a href="javascript:void(0)">平台收益率</a></li>
				<li><a href="javascript:void(0)">用户登录</a></li>
			</ul>
			<ol>
				<li
					style="padding-top: 25px; *padding-top: 35px; padding-top: 35px; padding-left: 18px;">
					<img src="/resource/images/imgtop.gif" />
				</li>
				<li class="form">
					<p id="p_login_t" style="display: none">账户或密码错误！</p>
					<form onsubmit="return login()">
						<c:if test="${CURRENT_USER==null }">
							<table width="234" style="margin: 0 auto; display: block">
								<tr>
									<td colspan="2">
										<div class="form-div">
											<i
												style="background: url('/resource/images/bg_input.gif') no-repeat left top"></i>
											<input type="text" name="account" id="account" required
												placeholder="手机号码/用户名" onfocus="this.style.color='#434343'" />
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="form-div">
											<i
												style="background: url('/resource/images/bg_input.gif') no-repeat left bottom"></i>
											<input type="password" name="password" id="password"
												placeholder="登录密码" onfocus="this.style.color='#434343'" />
										</div>
									</td>
								</tr>

								<tr>
									<td align="right"><a href="/users/forgetpassword">忘记密码？</a></td>
								</tr>
								<tr>
									<td colspan="2" height="45px" align="center"><input
										type="image" src="/resource/images/button_zhuce.png"
										style="padding-left: 0; background: none; border: none" /></td>
								</tr>
								<tr>
									<td align="center" height="25px;" colspan="2">还没有领钱儿帐号？ <a
										href="/users/regist" style="color: #ed6e31">注册</a>
									</td>
								</tr>
								<tr>
									<td colspan="2"
										style="padding-left: 35px; background: url('/resource/images/bao.png') no-repeat 20px center; font-size: 12px">领钱儿保障您的账户资金流转安全</td>
								</tr>
							</table>
						</c:if>
						<c:if test="${CURRENT_USER!=null }">
							<div class="denglu2" style="display: block">
								<div class="denglu2_div1">
									<p class="Cname_p">您当前登录的账户是：</p>
									<p class="Cname">${CURRENT_USER.nickName==null?CURRENT_USER.loginAccount:CURRENT_USER.nickName }</p>
								</div>
								<div style="padding-top: 10px; text-align: center">
									<a href="/myLingqian/show"
										style="display: block; margin: 0 auto; width: 214px; height: 34px;">
										<img src="/resource/images/enter.png" width="214px"
										height="34px">
									</a>
								</div>
							</div>
						</c:if>
					</form>
				</li>
			</ol>
		</div>
	</div>
	<!--快速登录-->

	<!--活动-->
	<div class="main2">
		<div id="marqueen2">
			<ul>
				<c:forEach items="${ntlist}" var="alist" varStatus="ActivityProduct">
					<li><a href="javascript:void(0)"> <span
							title="${alist.content }">${fn:substring(alist.content, 0, 50)}${fn:length(alist.content)>50?'...':''}</span>
					</a></li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<div class="main1">
		<ol class="box clearfix">
			<li style="width: 740px;" class="hotimgBox">
				<div id="hotimg">
					<ul>
						<c:forEach items="${listInfoBanner }" var="item">
							<li><a href="${item.url }"><img src="${item.path }"
									width="740px" height="151" alt="领钱产品介绍" /></a></li>
						</c:forEach>
					</ul>
				</div>
				<div class="hdd">
					<ul></ul>
				</div>
			</li>

			<li style="float: right"><img
				src="/resource/images/img_xinshou.jpg" usemap="#planetmap"
				width="270" alt="领钱新手指导" /> <map name="planetmap" id="planetmap">
					<area shape="rect" coords="0,0,265,74" href="/freshman" />
					<area shape="rect" coords="0,75,265,151" href="/direction" />
				</map></li>
		</ol>
	</div>
	<div class="main-body">
		<div class="theme clearfix">
			<div class="left">
				<div class="tian">
					<span class="tian1" onclick="location='/product/list/0_0_0_0_0_1'"
						style="cursor: pointer">明星产品 </span> <span>&nbsp;&nbsp;白领理财利器</span>
				</div>
				<div class="licai">
					<ul class="clearfix">
						<c:forEach items="${listx}" var="plist" varStatus="product">
							<c:if test="${product.index<=2}">
								<c:if test="${product.index==0 }">
									<li class="licai1">
								</c:if>
								<c:if test="${product.index==1 }">
									<li class="licai2">
								</c:if>
								<c:if test="${product.index==2 }">
									<li class="licai3">
								</c:if>
								<div class="title_licai">
									<span> ${plist.pcName }</span>
								</div>
								<p class="coin">
									<img src="/resource/images/coin.gif" /><span>储蓄罐</span>|<span>积少成多</span>
								</p>
								<dl class="clearfix">
									<dt style="border-right: 1px solid #d8d8d8">
										<p style="margin-bottom: 5px;">
											<span
												style="color: #ea5514; font-weight: bold; font-size: 20px;">￥</span><strong>${plist.minMoney }</strong>
										</p>
										<p>
											<!-- 期限的判断 -->
											<c:choose>
												<c:when
													test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
													<c:if
														test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">
														<span style="color: #7b7b7b; font-weight: bold">非固定(0)</span>&nbsp;
									             </c:if>
													<c:if test="${plist.fTime==0}"></c:if>
													<c:if
														test="${fn:substring(plist.code,plist.code.length()-5, plist.code.length()-4)==1}">
														<c:if test="${plist.fTime!=0}">
															<span style="color: #7b7b7b; font-weight: bold">${plist.fTime}</span>
															<c:if test="${plist.unitTime==0}">天</c:if>
															<c:if test="${plist.unitTime==1}">周</c:if>
															<c:if test="${plist.unitTime==2}">个月</c:if>
															<c:if test="${plist.unitTime==3}">年</c:if>
														</c:if>
														<c:if test="${plist.fTime==0}">
															<span style="color: #7b7b7b;">无固定期限</span>&nbsp;
									                    </c:if>
													</c:if>

													<c:if
														test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
														<span
															style="position: relative; z-index: 1; padding-right: 10px; color: #7b7b7b; font-weight: bold">${plist.lTime }-${plist.hTime }
															<img src="/resource/images/jia_small.png"
															style="position: absolute; z-index: 2; right: 1px; top: 0" />
														</span>&nbsp;
														<c:if test="${plist.unitTime==0}">天</c:if>
														<c:if test="${plist.unitTime==1}">周</c:if>
														<c:if test="${plist.unitTime==2}">个月</c:if>
														<c:if test="${plist.unitTime==3}">年</c:if>
													</c:if>
												</c:when>
												<c:otherwise>
													<span style="color: #7b7b7b; font-weight: bold">浮动收益类</span>&nbsp;
									           </c:otherwise>
											</c:choose>
											<!-- 期限的判断 -->
										</p>
									</dt>
									<dd style="padding-top: 5px;">
										<p style="margin-bottom: 5px;">
											<!-- 预期年化收益率的判断 -->
											<c:choose>
												<c:when
													test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
													<c:if
														test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">
														<strong>无</strong>
													</c:if>
													<c:if
														test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==1}">
														<c:if
														test="${plist.addYield>0}">	
														<strong> <fmt:formatNumber type="percent"
																value="${plist.fYield-plist.addYield}" maxFractionDigits="1"
																minFractionDigits="1" /></strong> <strong style="font-size: 18px;">+ <fmt:formatNumber
																type="percent" value="${plist.addYield}"
																minFractionDigits="1" maxFractionDigits="1" /></strong>
														
													</c:if>	
													
													<c:if
														test="${ plist.addYield==0 || plist.addYield==null}">	
														<strong class="income_strong"
															style="padding-left: 5px; font-family: 'myImpact'; font-size: 30px;">
															<fmt:formatNumber type="percent" value="${plist.fYield}"
																minFractionDigits="1" maxFractionDigits="1" />
														</strong>
													</c:if>	
													</c:if>

													<c:if
														test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
														<strong> <fmt:formatNumber type="percent"
																value="${plist.lYield}" minFractionDigits="1"
																maxFractionDigits="1" />- <fmt:formatNumber
																type="percent" value="${plist.hYield}"
																minFractionDigits="1" maxFractionDigits="1" />
														</strong>
													</c:if>
												</c:when>
												<c:otherwise>
													<strong>净值</strong>
												</c:otherwise>
											</c:choose>
											<!-- 预期年化收益率的判断 -->
										</p>
										<p style="padding-top: 2px; text-align: center">预期年化收益率</p>
									</dd>
								</dl>
								<div>
									<c:if test="${plist.tags!=null&&plist.tags!='' }">
										<ol class="clearfix" style="font-size: 12px;">
											<c:forEach items="${fn:split(plist.tags, ',')}" var="key"
												varStatus="status">
												<li
													<c:if test="${status.index==0 }">style="border: 1px solid #ee8241; color: #ee8241;"</c:if>
													<c:if test="${status.index==1 }">style="border: 1px solid #7dbbe4; color: #7dbbe4;"</c:if>
													<c:if test="${status.index==2 }">style="border: 1px solid #79c064; color: #79c064;"</c:if>
													<c:if test="${status.index==3 }">style="border: 1px solid #845299; color: #845299;"</c:if>>${key }</li>
											</c:forEach>
										</ol>
									</c:if>
								</div>
								<a href="javascript:void(0)" onclick="detail('${plist.code }')"
									class="a_tou">立即购买</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				<div class="tian clearfix" style="clear: both;">
					<div style="float: left;">
						<span class="tian1" onclick="location='/product/list/0_0_0_0_0_1'"
							style="cursor: pointer">产品系列（<span style="color: #FC6933">${fn:length(listy) }</span>）
						</span> <span>&nbsp;&nbsp;存取灵活，收益稳健 <a
							href="/product/list/0_0_0_0_0_1" class="moress"
							style="float: none; display: inline-block; clear: both; margin: 10px 0 0 10px;">更多</a>
						</span>
					</div>
					<div style="float: right">
						<span style="font-size: 16px; color: #eb3f01"> <a
							href="javascript:void(0)" class="jsq">理财收益计算器</a>
						</span> <a href="javascript:void(0)" class="jsq"> <img
							src="/resource/images/jsj.png"
							style="position: relative; top: 2px;">
						</a>
					</div>
				</div>
				<div class="tt">
					<c:forEach items="${listy}" var="plist" varStatus="product">
						<c:if test="${product.index<5}">
							<ul class="clearfix">
								<i></i>
								<li
									style="margin-right: 25px; width: 555px; padding: 10px 0 0 20px; margin-right:0px;">
									<div class="clearfix"
										style="margin-bottom: 5px; padding-bottom: 8px; border-bottom: 1px solid #e3e3e3; height: 37px;">
										<p class="title_product">
										<c:if test="${plist.pType == 2}">
											<img class="hxbank_logo" src="/resource/images/hxbank_btn_logo.png">
										</c:if>
										<a href="javascript:void(0)" onclick="detail('${plist.code }')" style="color: #000">${plist.name}</a>
										<sup><img src="/resource/images/new.gif" /></sup>
										</p>
										
										<c:if test="${plist.insuranceTrust == 1}">
    										<span class="style-insurance" style="margin-top:13px">履约险承保</span>
										</c:if> 
										
										
										
										
										
										<span style="display: black; float: right; font-size: 14px;">预期年化收益率
											<!-- 预期年化收益率的判断 --> <c:choose>
												<c:when
													test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
													<c:if
														test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">
														<strong class="income_strong"
															style="padding-left: 5px; font-family: 'myImpact'; font-size: 30px; color: #eb5f22">
															无</strong>
													</c:if>
													<c:if
														test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==1}">
														<c:if
														test="${plist.addYield>0}">	
														<strong class="income_strong"
															style="padding-left: 5px; font-family: 'myImpact'; font-size: 30px;">
															<fmt:formatNumber type="percent" value="${plist.fYield-plist.addYield}"
																minFractionDigits="1" maxFractionDigits="1" /></strong><strong class="income_strong" style="font-family: myImpact; font-size: 20px;">+ <fmt:formatNumber
																type="percent" value="${plist.addYield}"
																minFractionDigits="1" maxFractionDigits="1" /></strong>
														
													</c:if>
													<c:if
														test="${ plist.addYield==0 || plist.addYield==null }">	
														<strong class="income_strong"
															style="padding-left: 5px; font-family: 'myImpact'; font-size: 30px;">
															<fmt:formatNumber type="percent" value="${plist.fYield}"
																minFractionDigits="1" maxFractionDigits="1" />
														</strong>
													</c:if>	
													</c:if>
													<c:if
														test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
														<strong class="income_strong"
															style="padding-left: 5px; font-family: 'myImpact'; font-size: 30px;">
															<fmt:formatNumber type="percent" value="${plist.lYield}"
																minFractionDigits="1" maxFractionDigits="1" />- <fmt:formatNumber
																type="percent" value="${plist.hYield}"
																minFractionDigits="1" maxFractionDigits="1" />
														</strong>
													</c:if>
												</c:when>
												<c:otherwise>
													<strong class="income_strong"
														style="padding-left: 5px; font-family: 'myImpact'; font-size: 30px; color: #eb5f22; font-weight: bold">净值</strong>
												</c:otherwise>
											</c:choose> <!-- 预期年化收益率的判断 -->
										</span>
									</div>

									<div class="scenter clearfix">
										<!-- 起投金额 -->
										<span
											style="background: url('/resource/images/img_center1.gif') no-repeat left center;">${plist.minMoney }元+</span>
										<!-- 起投金额 -->
										<!-- 期限的判断 -->
										<span
											style="background: url('/resource/images/img_center2.gif') no-repeat left center;"><c:choose>
												<c:when
													test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
													<c:if
														test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">非固定收益类</c:if>
													<c:if
														test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==1}">
														<c:if test="${plist.fTime==0}">
														无固定期限
														</c:if>
														<c:if test="${plist.fTime!=0}">期限${plist.fTime }
															<c:if test="${plist.unitTime==0}">天</c:if>
															<c:if test="${plist.unitTime==1}">周</c:if>
															<c:if test="${plist.unitTime==2}">个月</c:if>
															<c:if test="${plist.unitTime==3}">年</c:if>
														</c:if>
													</c:if>
													<c:if
														test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
														<span
															style="position: relative; z-index: 1; padding: 0; padding-right: 8px; margin-right: 5px">期限${plist.lTime }-${plist.hTime }
															<img src="/resource/images/jia_small.png"
															style="position: absolute; z-index: 2; right: -2px; top: 0" />
														</span>
														<c:if test="${plist.unitTime==0}">天</c:if>
														<c:if test="${plist.unitTime==1}">周</c:if>
														<c:if test="${plist.unitTime==2}">个月</c:if>
														<c:if test="${plist.unitTime==3}">年</c:if>
													</c:if>
												</c:when>
												<c:otherwise>浮动收益类</c:otherwise>
											</c:choose> </span>
										<!-- 期限的判断 -->


										<!-- 规模的判断 -->
										<span
											style="background: url('/resource/images/img_center4.gif') no-repeat left center;">
											<c:if test="${plist.rule==0 || plist.rule==2 }">
												<fmt:formatNumber pattern="#,##0.00#"
													value="${plist.priorMoney }" />元
											</c:if> <c:if test="${plist.rule==1 || plist.rule==3 }">无限制</c:if>
										</span>
										<!-- 规模的判断 -->
									</div>

									<div class="sbottom clearfix" style="font-size: 12px;">
										<c:if test="${plist.tags!=null&&plist.tags!='' }">
											<c:forEach items="${fn:split(plist.tags, ',')}" var="key"
												varStatus="status">
												<span
													<c:if test="${status.index==0 }">style="border: 1px solid #ee8241; color: #ee8241"</c:if>
													<c:if test="${status.index==1 }">style="border: 1px solid #7dbbe4; color: #7dbbe4"</c:if>
													<c:if test="${status.index==2 }">style="border: 1px solid #79c064; color: #79c064"</c:if>
													<c:if test="${status.index==3 }">style="border: 1px solid #845299; color: #845299"</c:if>>${key }</span>
											</c:forEach>
										</c:if>
									</div>
								</li>
								<li style="padding-top: 11px;">
									<a href="javascript:void(0)" onclick="detail('${plist.code }')" class="a_tou a_tou2">立即购买</a>
								</li>
							</ul>
						</c:if>

					</c:forEach>
				</div>
			</div>
			<div class="right">
				<div class="income">
					<div class="titles clearfix">
						<strong class="rightTitle">收益对比</strong>
					</div>
					<div style="padding-top: 10px; text-align: center">
						<img src="/resource/images/data.jpg" alt="领钱收益对比" />
					</div>

				</div>
				<div class="faq">
					<div class="titles clearfix">
						<strong class="rightTitle">媒体资讯</strong> <a href="/infoMedia/list"
							target="_blank" class="moress">更多</a>
					</div>
					<ul style="clear: both">
						<c:forEach items="${listInfoMedia }" var="item" varStatus="status">
							<c:if test="${status.index<=8 }">
								<li class="clearfix"><a target="_blank"
									href="/infoMedia/details?id=${item.id}">${item.title }</a><span><fmt:formatDate
											value="${item.pDt }" pattern="yyyy-MM-dd" /> </span></li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				<div class="faq">
					<div class="titles clearfix">
						<strong class="rightTitle">站内公告</strong> <a
							href="/infoNotice/list" target="_blank" class="moress">更多</a>
					</div>
					<ul style="clear: both">
						<c:forEach var="item" items="${listInfoNotice}" varStatus="status">
							<c:if test="${status.index<=8 }">
								<li class="clearfix"><a target="_blank"
									href="/infoNotice/details?id=${item.id}">${item.title }</a><span><fmt:formatDate
											value="${item.pDt }" pattern="yyyy-MM-dd" /> </span></li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				<div class="faq">
					<div class="titles clearfix">
						<strong class="rightTitle">您问我答</strong> <a href="/usersAsk/list"
							target="_blank" class="moress">更多</a>
					</div>
					<ul style="clear: both">
						<c:forEach items="${listUsersAsk }" var="item" varStatus="status">
							<c:if test="${status.index<=8 }">
								<li class="clearfix"><a target="_blank"
									href="/usersAsk/details?id=${item.id }">${item.title }</a> <span><fmt:formatDate
											value="${item.time }" pattern="yyyy-MM-dd" /> </span></li>
							</c:if>
						</c:forEach>
					</ul>
				</div>

				<div>
					<a href="/financialStory/homeList"> <img
						src="/resource/images/story.jpg" alt="领钱理财小故事" />
					</a>
				</div>

			</div>
		</div>
	</div>
	<form action="/product/showProduct" id="the_form">
		<input type="hidden" name="changTab" id="changTab">
		<input type="hidden" name="code" id="the_code">
	</form>
	
	<!--520弹窗-->
  <div class="register_Popup">
  	<img class="register_redcard" src="/resource/images/8806b43a-dc54-46f8-953e-fc34429f173a.png"/>
  	<img class="register_close" src="/resource/images/6d4f78cf-e859-4dc8-8a4b-b3681845a42d.png"/>
  	 <div class="my_cardStock"><a href="/myFinancial/redPacket?type=0&hrpType=2"></a></div>
  </div>
  <div class="index-Popup-bg"></div>
  
  <!--早点八弹窗  -->
  <div class="eight_Popup">
				<div  class="eight_one"><img src="http://static.lingmoney.cn/lingmoneywap/images/73909e7f-65f4-4d1f-82d6-87a302ee1943.png" class="eight_text"/><img src="http://static.lingmoney.cn/lingmoneywap/images/85180f9a-eb08-4d26-a41e-8d6aee88722e.png" class="eight_close"/></div>
				<div  class="eight_two"><p class="card_one_red">您有<span class="yield_red"></span>个红包待使用</p><p class="card_one">您有<span class="yield_one"></span>张加息劵待使用</p></div>
				<div  class="eight_three"></div>
				<div  class="eight_four"><img src="http://static.lingmoney.cn/lingmoneywap/images/b160aed7-afe7-48ab-aec0-0f342a72bfed.png"/ class="img-know"><a href="/myFinancial/redPacket?type=0&hrpType=1"><img src="http://static.lingmoney.cn/lingmoneywap/images/33c46323-95c5-4e6d-a936-d7696a73b98a.png" class="img-see"/></a></div>		
			</div>
   
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	<script>
		window.onload = function() {
			setTimeout(fun, 100);
			
			$.ajax({
				url:  '/activityReward/showCouponReminding',
				type: 'post',
				
				dataType: 'json',
				success: function(d) {
					if(d.code == 200) {
						$(".eight_Popup,.index-Popup-bg").show()

						if(d.obj.rateCoup > 0) {
							$(".card_one_red").show()
						}else{
							$(".card_one_red").css('margin-bottom','0')
						}
						if(d.obj.redEnve > 0) {
							$(".card_one").show()
						}else{
							$(".card_one_red").css('margin-bottom','0')
						}
						$(".yield_one").html(d.obj.redEnve)
						$(".yield_red").html(d.obj.rateCoup)

					} else {
						$(".eight_Popup,.index-Popup-bg").hide()
					}
				},
				error: function(d) {}
			});
			$(".img-know,.eight_close").click(function() {
				$(".eight_Popup,.index-Popup-bg").hide();
			})
			
		};

		function fun() {
			/*input*/
			if ($("#account").val() != "") {
				$(".ssss:eq(0)").find("span").hide();
				$("#account").css("color", "#434343");
			}
			if ($("#loginPsw").val() != "") {
				$(".ssss:eq(1)").find("span").hide();
				$("#loginPsw").css("color", "#434343");
			}
		}

		function detail(val) {
			$("#the_code").val(val);
			$("#the_form").submit();
		}
	</script>

</body>
</html>