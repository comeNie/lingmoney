<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>

<style>
body {
	margin: 0px;
	padding: 0px;
	font-family: "微软雅黑";
	font-size: 14px;
	background: #fff5dd;
}
ul, ol, li {
	list-style: none;
	margin: 0px;
	padding: 0px;
}

.warp {
	width: 100%;
	overflow: hidden;
}

.banner {
	width: 100%;
	height: 641px;
	background: url(resource/images/bank_option/banner.jpg) no-repeat center 0
}

.mian {
	width: 1200px;
	margin: 0 auto;
	background: #fff;
}

.mian-top {
	height: 142px;
	background: url(resource/images/bank_option/cz1.jpg) no-repeat center 0
}

.mian-tip {
	text-align: center;
	padding-top: 58px;
}

.flexslider {
	margin: 0px auto 20px;
	position: relative;
	width: 1200px;
	overflow: hidden;
	zoom: 1;
}

.flexslider .slides {
	width: 1064px;
	height: 714px;
	margin-left: 68px;
	margin-top: 106px;
}

.slides li {
	width: 1064px;
	overflow: hidden;
	margin-top: 46px;
	margin-left: 120px;
}

.flex-direction-nav a {
	width: 70px;
	height: 70px;
	line-height: 99em;
	overflow: hidden;
	margin: -35px 0 0;
	display: block;
	background: url(resource/images/bank_option/ad_ctr.png) no-repeat;
	position: absolute;
	top: 50%;
	z-index: 10;
	cursor: pointer;
	/* opacity: 0; */
	filter: alpha(opacity = 0);
	-webkit-transition: all .3s ease; <!--
	border-radius: 35px;
	-->
}

.flex-direction-nav .flex-next {
	background-position: 0 -70px;
	right: 20px;
}
.len-ar a:link{ color:#fff;
}
.len-ar a:visited{ color:#fff;
}
.len-ar a:hover{ color:#fff;
}

.flex-direction-nav .flex-prev {
	left: 20px;
}

.flexslider:hover .flex-next {
	opacity: 0.8;
	filter: alpha(opacity = 25);
}

.flexslider:hover .flex-prev {
	opacity: 0.8;
	filter: alpha(opacity = 25);
}

.flexslider:hover .flex-next:hover, .flexslider:hover .flex-prev:hover {
	opacity: 1;
	filter: alpha(opacity = 50);
}

.flex-control-nav {

	text-align: center;
	height: 38px;
	top: 5px;
	color: #fff;
	margin: 0 auto;
}

.flex-control-nav li {
	display: inline-block;
	zoom: 1;
	*display: inline;

}

.flex-control-paging li a {
	background: url(resource/images/bank_option/dot.png) no-repeat 0 -38px;
	display: block;
	height: 38px;
	overflow: hidden;
	text-align: center;
	line-height: 38px;
	width: 38px;
	cursor: pointer;
}

.flex-control-paging li a.flex-active, .flex-control-paging li.active a
	{
	background-position: 0 0;
	text-decoration: none;
}

.flexslider .slides li a {
	padding-top: 132px;
	display: block;
	text-decoration: none;
}

.flexslider .slides a img {
	width: 965px;
	height: 534px;
	display: block;
	border: 0px;
}

.lunbo {
	overflow: hidden;
	clear: both;
}

.mian-tips {
	font-size: 14px;
	color: #5d5d5d;
	width: 986px;
	margin: 0 auto;
	line-height: 24px;
}

.lfet-nav {
	width: 78px;
	height: 300px;
	background: url(resource/images/bank_option/cz7.jpg) no-repeat 0 0;
	padding-top: 60px;
	position: absolute;
	/* right: 0%; */
	top: 20%;
	/*  margin-right: -675px; */
	z-index:1001;
}
.lfet-nav ul{display:block;padding-top:8px;}

.lfet-nav li {
	color: #fff;
	font-size: 14px;
	text-align: center;
	line-height: 28px;
	width: 72px;
    margin: 4px auto;
}

.mian-but {
	width: 1200px;
	margin: 0 auto;
	background: url(resource/images/bank_option/cz6.jpg) no-repeat 0 0;
	height: 485px;
	text-align: center;
}

.mian-but img {
	margin-top: 221px;
}

.mian-top li {
	float: left;
	width: 251px;
	height: 81px;
	margin-top: 55px;
}

.mian-top-left {
	margin-left: 310px;
	margin-right: 86px;
}

.u-tip {
	color: #717171;
	font-size: 18px;
	height: 40px;
	
}

.lfet-nav a:link {
	 color: #fff; 
	text-decoration: none;
}

.lfet-nav a:visited {
	color: #fff; 
	text-decoration: none;
}

.lfet-nav a:hover {
	 color: #fff; 
	text-decoration: none;
}

.mian-top img {
	cursor: pointer;
}
.u-tip span{
 	    width: 274px;
    float: left;
    text-align: left;
    margin-right: 72px;
}
.mian-tippp{color:666; width:987px;margin:0 auto; text-align: left;font-size:18px; line-height:32px; height:300px;background: url(resource/images/bank_option/bj-k.png) no-repeat 0 0;padding:30px 50px; margin-top:30px;}
.mian-tippp span{font-size:24px;color:#ff6f03;}
.nan-hover{background:#fff;}
.nan-hover a:link{color:#ff6f03}
.nan-hover a:visited{color:#ff6f03}
.nan-hover a:hover{color:#ff6f03}

.bj1{background: url(resource/images/bank_option/cz3.jpg) no-repeat center 0}
.bj2{background: url(resource/images/bank_option/cz03.png) no-repeat center 0}
</style>
<script src="/resource/js/slider.js"></script>

</head>
<body>

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<div class="warp" style="position: relative;">
		<div
			style="width: 140px; height: 229px; top: 942px; left: 216px; position: absolute;">
			<img src="resource/images/bank_option/cz8.png" />
		</div>
		<div
			style="width: 183px; height: 166px; top: 2007px; left: 60px; position: absolute;">
			<img src="resource/images/bank_option/cz9.png" />
		</div>
		<div
			style="width: 114px; height: 64px; top: 2517px; left: 16px; position: absolute;">
			<img src="resource/images/bank_option/cz10.png" />
		</div>
		<div
			style="width: 179px; height: 124px; top: 2787px; left: 216px; position: absolute;">
			<img src="resource/images/bank_option/cz11.png" />
		</div>

		<div
			style="width: 168px; height: 127px; top: 3051px; left: 16px; position: absolute;">
			<img src="resource/images/bank_option/cz12.png" />
		</div>
		<div
			style="width: 119px; height: 89px; top: 3477px; left: 6px; position: absolute;">
			<img src="resource/images/bank_option/cz13.png" />
		</div>
		<div
			style="width: 127px; height: 133px; top: 4017px; left: 234px; position: absolute;">
			<img src="resource/images/bank_option/cz14.png" />
		</div>
		<div
			style="width: 106px; height: 56px; top: 906px; right: 3px; position: absolute;">
			<img src="resource/images/bank_option/cz15.png" />
		</div>
		<div
			style="width: 191px; height: 137px; top: 1167px; right: 220px; position: absolute;">
			<img src="resource/images/bank_option/cz16.png" />
		</div>
		<div
			style="width: 191x; height: 164px; top: 1728px; right: 12px; position: absolute;">
			<img src="resource/images/bank_option/cz17.png" />
		</div>
		<div
			style="width: 113px; height: 102px; top: 2124px; right: 3px; position: absolute;">
			<img src="resource/images/bank_option/cz18.png" />
		</div>
		<div
			style="width: 180px; height: 279px; top: 2667px; right: 237px; position: absolute;">
			<img src="resource/images/bank_option/cz19.png" />
		</div>
		<div
			style="width: 183px; height: 162px; top: 3339px; right: 3px; position: absolute;">
			<img src="resource/images/bank_option/cz20.png" />
		</div>
		<div
			style="width: 106px; height: 56px; top: 3867px; right: 3px; position: absolute;">
			<img src="resource/images/bank_option/cz21.png" />
		</div>
		<div class="banner"></div>
		<div class="mian" style="position: relative;z-index:100; ">
		
		 	<div class="lfet-nav" id="nav">
		<ul class="u-pc" >
			<li ><a href="#pc1">开通e账户</a></li>
			<li class="pc2"><a href="#pc2">激活绑卡</a></li>
			<li><a href="#pc7">充值</a></li>
			<!-- <li><a href="#pc3">充值</a></li> -->
			<li><a href="#pc4">购买</a></li>
			 <li><a href="#pc5">实名认证</a></li> 
			<li><a href="#pc6">提现</a></li>
			<li id="returnTop" style=" cursor: pointer">返回顶部</li>
			<p class="left-guanbi" style="text-align: center; padding-top:8px;cursor: pointer"><img src="resource/images/guanbi.png" style="width:30px;"/></p>
		</ul>
		<ul style="display: none;" class="u-app">
			<li ><a href="#ap1">开通e账户</a></li>
			<li><a href="#ap2">激活绑卡</a></li>
			<!-- <li><a href="#ap7">转账</a></li> -->
			<li><a href="#ap3">充值</a></li>
			<li><a href="#ap4">购买</a></li>
			<li><a href="#ap5">实名认证</a></li> 
			<li><a href="#ap6">提现</a></li>
			<li id="returnTop1" style=" cursor: pointer">返回顶部</li>
			<p class="left-guanbi" style="text-align: center; padding-top:8px;cursor: pointer"><img src="resource/images/guanbi.png" style="width:30px;"/></p>
		</ul>
	</div>

			<div class="mian-top">
				<ul>
					<li class="mian-top-left"><img src="resource/images/bank_option/pc01.png" /></li>
					<li class="mian-top-right"><img src="resource/images/bank_option/app.png" /></li>
				</ul>
			</div>
         
			<!-- pc端 -->
			<div class="warp-box">
			
				<div class="pc">
				   <div class="mian-tippp"><span>温馨提示：</span><br><br>
						1、如您在操作过程中遇到浏览器拦截弹窗窗口，可根据我们列举的一些常用浏览器的拦截弹出页面的解决方法进行解决。<br/><a href="/helpproblem.html" style="color:#1b1f84;text-decoration:underline">点击查看解决方法</a><br><br>
						2、浏览器建议使用：IE（含IE8、9、10）及IE内核浏览器、Firefox火狐浏览器;<br/>
						操作系统支持：WindowsXP、Windows Vista、Windows7、Windows8。</div>
					<div class="mian-tip height-body" style="margin-bottom: 36px;" id="pc1">
						<img src="resource/images/bank_option/kt1.png" />
					</div>
					<div class="lunbo">
						<!-- 轮播广告 -->
						<ol id="bannerCtrl"
							class="flex-control-nav flex-control-paging resText-t">
						</ol>
						<div id="banner_tabs" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz3.jpg) no-repeat center 0;"
								id="resText">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>
						</div>
					</div>
					
					<!-- 2 -->
					<div class="mian-tip height-body" style="margin-bottom: 36px;" id="pc2">
						<img src="resource/images/bank_option/jh1.png" />
					</div>
					<div class="lunbo">
						<!-- 轮播广告 -->
						<ol id="bannerCtrl2"
							class="flex-control-nav flex-control-paging resText-t2">
						</ol>
						<div id="banner_tabs2" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz3.jpg) no-repeat center 0;"
								id="resText2">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>

						</div>
					</div>
					
					
					
					<div class="lunbo">
                     <div class="mian-tip height-body" style="margin-bottom: 36px;" id="pc7">
						<img src="resource/images/bank_option/cz1.png" />
					</div>
						<!-- 轮播广告 -->
						<div style="width:970px;margin:0 auto;color:#ff7403;font-size:20px;padding-bottom:20px;">
				温馨提示：使用您已绑定的银行卡，通过手机银行、网银方式将资金转账至您的华兴银行存管账户中即可完成充值。
			</div>
						
						<div id="banner_tabs7" class="flexslider">
							<ul class="slides bj-ka "style="clear: both; margin-top: 150px;"id="resText7" >
							</ul>
							<ul class="flex-direction-nav">
								<li ><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li class="idright"><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>
						</div>
					</div>
					
					

					<!-- 3 -->
					<!-- <div class="mian-tip " style="margin-bottom: 36px;" id="pc3">
						<img src="resource/images/bank_option/cz1.png" />
					</div>
					<div class="lunbo">
						轮播广告
						<ol id="bannerCtrl3"
							class="flex-control-nav flex-control-paging resText-t3">
						</ol>
						<div id="banner_tabs3" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz3.jpg) no-repeat center 0;"
								id="resText3">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>

						</div>
					</div> -->

					<!-- 4 -->
					<div class="mian-tip height-body" style="margin-bottom: 36px;" id="pc4">
						<img src="resource/images/bank_option/gm1.png" />
					</div>
					<div class="lunbo">
						<!-- 轮播广告 -->
						<ol id="bannerCtrl4"
							class="flex-control-nav flex-control-paging resText-t4">
						</ol>
						<div id="banner_tabs4" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz3.jpg) no-repeat center 0;"
								id="resText4">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>

						</div>
					</div>
					<!-- 5 -->
					<!-- 5 -->
					 <div class="mian-tip height-body" style="margin-bottom: 36px;" id="pc5">
						<img src="resource/images/bank_option/rz1.png" />
					</div>
					<div class="lunbo">
						<!-- 轮播广告 -->
						<ol id="bannerCtrl5"
							class="flex-control-nav flex-control-paging resText-t5">
						</ol>
						<div id="banner_tabs5" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz03.png) no-repeat center 0;"
								id="resText5">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>

						</div>
					</div>
						<div class="mian-tips">
				提示：<br>工作日周一到周五8:30-17:30，当天审核完17:30以前的申请；<br/>周六周日、法定节假日10:00-18:00，当天审核完18:00以前的申请。长假期审批时间另行通知。
			</div> 
					<!-- 6 -->
					<div class="mian-tip height-body" style="margin-bottom: 36px;" id="pc6">
						<img src="resource/images/bank_option/tx1.png" />
					</div>
					<!-- <div style="text-align: center;color:#ff7403;font-size:20px;padding-bottom:20px;">温馨提示：首次提现，需要到华兴银行“投融资平台”进行实名认证，若已完成实名认认证，可直接提现操作。

					</div> -->
					<div class="lunbo">
						<!-- 轮播广告 -->
						
						<ol id="bannerCtrl6"
							class="flex-control-nav flex-control-paging resText-t6">
						</ol>
						<div style="width:970px;margin:20px auto;color:#ff7403;font-size:20px; text-align: center;">
				温馨提示：首次提现，需先进行实名认证。
			</div>
						<div id="banner_tabs6" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz3.jpg) no-repeat center 0;"
								id="resText6">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>

						</div>
					</div>
				</div>
				<!-- app端 -->
				<div class="app" style="display: none">
					<div class="mian-tip height-body1" style="margin-bottom: 36px;" id="ap1">
						<img src="resource/images/bank_option/kt1.png" />
					</div>
					<div class="lunbo">
						<!-- 轮播广告 -->
						
						<div id="banner_tabsapp" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz03.png) no-repeat center 0;"
								id="resTextapp">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>
						</div>
					</div>
                     

					<!-- 2 -->
					<div class="mian-tip height-body1" style="margin-bottom: 36px;" id="ap2">
						<img src="resource/images/bank_option/jh1.png" />
					</div>
					<div class="lunbo">
						<!-- 轮播广告 -->
						
						<div id="banner_tabsapp2" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz03.png) no-repeat center 0;"
								id="resTextapp2">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>

						</div>
					</div>
					
					
					 <!-- 轮播广告 7-->
                     <!-- <div class="lunbo">
                     <div class="mian-tip " style="margin-bottom: 36px;" id="ap7">
						<img src="resource/images/bank_option/zz1.png" />
					</div>
						轮播广告
						<div style="width:970px;margin:0 auto;color:#ff7403;font-size:20px;padding-bottom:20px;">
				温馨提示：<br>收款银行开户行时找不到“广东华兴银行”时，可选择“城市商业银行—广东华兴银行股份有限公司”。
			</div>
						
						<div id="banner_tabsapp7" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz03.png) no-repeat center 0;"
								id="resTextapp7">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next " href="javascript:;">Next</a></li>
							</ul>
						</div>
					</div> -->

					<!-- 3 -->
					<div class="mian-tip height-body1" style="margin-bottom: 36px;" id="ap3">
						<img src="resource/images/bank_option/cz1.png" />
					</div>
					<div style="text-align: center;color:#ff7403;font-size:20px;padding-bottom:20px;">温馨提示：使用您已绑定的银行卡，通过手机银行、网银方式将资金转账至您的华兴银行存管账户中即可完成充值。
</div>
					<div class="lunbo">
						<!-- 轮播广告 -->
						
						<div id="banner_tabsapp3" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz03.png) no-repeat center 0;"
			 					id="resTextapp3">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>

						</div>
					</div>

					<!-- 4 -->
					<div class="mian-tip height-body1" style="margin-bottom: 36px;" id="ap4">
						<img src="resource/images/bank_option/gm1.png" />
					</div>
					<div class="lunbo">
						<!-- 轮播广告 -->
						
						<div id="banner_tabsapp4" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz03.png) no-repeat center 0;"
								id="resTextapp4">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>

						</div>
					</div>
					<!-- 5 -->
					 <div class="mian-tip height-body1" style="margin-bottom: 36px;" id="ap5">
						<img src="resource/images/bank_option/rz1.png" />
					</div>
					<div class="lunbo">
						<!-- 轮播广告 -->
						
						<div id="banner_tabsapp5" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz03.png) no-repeat center 0;"
								id="resTextapp5">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>

						</div>
					</div>
					<div class="mian-tips">
				提示：<br>工作日周一到周五8:30-17:30，当天审核完17:30以前的申请；<br/>周六周日、法定节假日10:00-18:00，当天审核完18:00以前的申请。长假期审批时间另行通知。
			</div> 
					<!-- 6 -->
					<div class="mian-tip height-body1" style="margin-bottom: 36px;" id="ap6">
						<img src="resource/images/bank_option/tx1.png" />
					</div>
					
					<!-- <div style="width:970px;margin:0 auto;color:#ff7403;font-size:20px;padding-bottom:20px;">温馨提示：<br/>
					
					
					选择提现至“E账户”，实时到账。选择提现至“银行卡”，根据提现金额大小，到账时间有所不同。<br/>


					
					</div> -->
					
					<div class="lunbo">
						<!-- 轮播广告 -->
						<div style="width:970px;margin:20px auto;color:#ff7403;font-size:20px; text-align: center;">
				温馨提示：首次提现，需先进行实名认证。
			</div>
						<div id="banner_tabsapp6" class="flexslider">
							<ul class="slides"
								style="clear: both; background: url(resource/images/bank_option/cz03.png) no-repeat center 0;"
								id="resTextapp6">
							</ul>
							<ul class="flex-direction-nav">
								<li><a class="flex-prev" href="javascript:;">Previous</a></li>
								<li><a class="flex-next" href="javascript:;">Next</a></li>
							</ul>

						</div>
					</div>
				</div>

			</div>
		
		</div>
		<div class="mian-but">
			<a href="/helpproblem.html"><img src="resource/images/bank_option/xx.png" /></a>
		</div>
	</div>


	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
<script type="text/javascript">
 $('.mian-top-right >img').click(function(){
	 $(this).attr('src','resource/images/bank_option/app01.png');
	 $('.mian-top-left >img').attr('src','resource/images/bank_option/pc.png')
	 $('.app').show();
	 $('.pc').hide();
	 $('.u-pc').hide();
	  $('.u-app').show();
	  var atRlens=$('.height-body1');
	   atRlens.each(function(index,domEle){
		   var heightts=$(this).offset().top;
		  
		   var _index=index;
		
		   $(window).scroll(function ()
			{
				var sts = $(this).scrollTop();
				 console.log('qw'+heightts);
				 console.log('as'+sts);
				if(heightts-60<sts){
			    	 $('.u-app >li').eq(_index).addClass('nan-hover').siblings().removeClass('nan-hover') 
			     }
				
			});

	   })
	 
	})
	 $('.mian-top-left >img').click(function(){
		 $(this).attr('src','resource/images/bank_option/pc01.png');
		 $('.mian-top-right >img').attr('src','resource/images/bank_option/app.png')
	 $('.app').hide();
	 $('.pc').show();
	  $('.u-pc').show();
	  $('.u-app').hide();
	  
	})
	
<!-- 开户 -->
	setTimeout(funcName,500);
   function funcName() {
   $(function() {
        var bannerSlider = new Slider($('#banner_tabs'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrl'),
            activeControllerCls: 'active'

        });
        $('#banner_tabs .flex-prev').click(function() {
            bannerSlider.prev()
        });
        $('#banner_tabs .flex-next').click(function() {
            bannerSlider.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		 
		})
		
    
   }
   
   setTimeout(funcNameapp,500);
   function funcNameapp() {
   $(function() {
        var bannerSliderapp = new Slider($('#banner_tabsapp'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrlapp'),
            activeControllerCls: 'active'

        });
        $('#banner_tabsapp .flex-prev').click(function() {
            bannerSliderapp.prev()
        });
        $('#banner_tabsapp .flex-next').click(function() {
            bannerSliderapp.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
   }
<!-- 绑卡 --> 
 setTimeout(funcName2,500);
  function funcName2() {
   $(function() {
        var bannerSlider2 = new Slider($('#banner_tabs2'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrl2'),
            activeControllerCls: 'active'
        });
        $('#banner_tabs2 .flex-prev').click(function() {
            bannerSlider2.prev()
        });
        $('#banner_tabs2 .flex-next').click(function() {
            bannerSlider2.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
  }
 setTimeout(funcNameapp2,500);
  function funcNameapp2() {
   $(function() {
        var bannerSliderapp2 = new Slider($('#banner_tabsapp2'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrlapp2'),
            activeControllerCls: 'active'
        });
        $('#banner_tabsapp2 .flex-prev').click(function() {
            bannerSliderapp2.prev()
        });
        $('#banner_tabsapp2 .flex-next').click(function() {
            bannerSliderapp2.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
  }
    
<!-- 充值 -->	
	
	
	setTimeout(funcName3,500);
  function funcName3() {
   $(function() {
        var bannerSlider3 = new Slider($('#banner_tabs3'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrl3'),
            activeControllerCls: 'active'
        });
        $('#banner_tabs3 .flex-prev').click(function() {
            bannerSlider3.prev()
        });
        $('#banner_tabs3 .flex-next').click(function() {
            bannerSlider3.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
  }
  
  
	setTimeout(funcNameapp3,500);
  function funcNameapp3() {
   $(function() {
        var bannerSliderapp3 = new Slider($('#banner_tabsapp3'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrlapp3'),
            activeControllerCls: 'active'
        });
        $('#banner_tabsapp3 .flex-prev').click(function() {
            bannerSliderapp3.prev()
        });
        $('#banner_tabsapp3 .flex-next').click(function() {
            bannerSliderapp3.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
  }
	
<!-- 投资 -->
setTimeout(funcName4,500);
  function funcName4() {
   $(function() {
        var bannerSlider4 = new Slider($('#banner_tabs4'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrl4'),
            activeControllerCls: 'active'
        });
        $('#banner_tabs4 .flex-prev').click(function() {
            bannerSlider4.prev()
        });
        $('#banner_tabs4 .flex-next').click(function() {
            bannerSlider4.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
  }	
setTimeout(funcNameapp4,500);
  function funcNameapp4() {
   $(function() {
        var bannerSliderapp4 = new Slider($('#banner_tabsapp4'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrlapp4'),
            activeControllerCls: 'active'
        });
        $('#banner_tabsapp4 .flex-prev').click(function() {
            bannerSliderapp4.prev()
        });
        $('#banner_tabsapp4 .flex-next').click(function() {
            bannerSliderapp4.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
  }		  
<!-- 认证 -->	
setTimeout(funcName5,500);
  function funcName5() {
   $(function() {
        var bannerSlider5 = new Slider($('#banner_tabs5'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrl5'),
            activeControllerCls: 'active'
        });
        $('#banner_tabs5 .flex-prev').click(function() {
            bannerSlider5.prev()
        });
        $('#banner_tabs5 .flex-next').click(function() {
            bannerSlider5.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
  }	
setTimeout(funcNameapp5,500);
  function funcNameapp5() {
   $(function() {
        var bannerSliderapp5 = new Slider($('#banner_tabsapp5'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrlapp5'),
            activeControllerCls: 'active'
        });
        $('#banner_tabsapp5 .flex-prev').click(function() {
            bannerSliderapp5.prev()
        });
        $('#banner_tabsapp5 .flex-next').click(function() {
            bannerSliderapp5.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
  }		  
<!-- 提现 -->
setTimeout(funcName6,500);
  function funcName6() {
   $(function() {
        var bannerSlider6 = new Slider($('#banner_tabs6'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrl6'),
            activeControllerCls: 'active'
        });
        $('#banner_tabs6 .flex-prev').click(function() {
            bannerSlider6.prev()
        });
        $('#banner_tabs6 .flex-next').click(function() {
            bannerSlider6.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
  }	
  <!-- 转账 -->
  setTimeout(funcName7,500);
    function funcName7() {
     $(function() {
          var bannerSlider7 = new Slider($('#banner_tabs7'), {
              time: 5000,
              delay: 400,
              event: 'hover',
              auto: true,
              mode: 'fade',
              controller: $('#bannerCtrl7'),
              activeControllerCls: 'active'
          });
          $('#banner_tabs7 .flex-prev').click(function() {
              bannerSlider7.prev()
          });
          $('#banner_tabs7 .flex-next').click(function() {
              bannerSlider7.next()
          });
      })
  	var Each=$(".flex-control-nav>li");
  		Each.each(function(){
  		var index=$(this).index();
  		  if(index==0){
  		  $(this).find('div').hide();
  		  $(this).addClass('active');
  		  }
  		
  		})
    }	
setTimeout(funcNameapp6,500);
  function funcNameapp6() {
   $(function() {
        var bannerSliderapp6 = new Slider($('#banner_tabsapp6'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrlapp6'),
            activeControllerCls: 'active'
        });
        $('#banner_tabsapp6 .flex-prev').click(function() {
            bannerSliderapp6.prev()
        });
        $('#banner_tabsapp6 .flex-next').click(function() {
            bannerSliderapp6.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
  }	
  
  setTimeout(funcNameapp7,500);
  function funcNameapp7() {
   $(function() {
        var bannerSliderapp7 = new Slider($('#banner_tabsapp7'), {
            time: 5000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrlapp7'),
            activeControllerCls: 'active'
        });
        $('#banner_tabsapp7 .flex-prev').click(function() {
            bannerSliderapp7.prev()
        });
        $('#banner_tabsapp7 .flex-next').click(function() {
            bannerSliderapp7.next()
        });
    })
	var Each=$(".flex-control-nav>li");
		Each.each(function(){
		var index=$(this).index();
		  if(index==0){
		  $(this).find('div').hide();
		  $(this).addClass('active');
		  }
		
		})
  }				  
  
	var ad = document.getElementById("nav")
	 window.onscroll = function(){
	  var _top = document.body.scrollTop || document.documentElement.scrollTop;
	  if(_top>=800){
	   ad.style.position = "fixed";
	   ad.style.top = 0 +"px";
	  }else{
	   ad.style.position = "absolute";
	   ad.style.top = 800+"px";
	  }
	 }
	 
 $("#returnTop").click(function () {
        var speed=200;
        $('body,html').animate({ scrollTop: 0 }, speed);
        return false;
 });
$("#returnTop1").click(function () {
        var speed=200;
        $('body,html').animate({ scrollTop: 0 }, speed);
        return false;
 });

   $(function() {
      $.ajax({
                url:"/resource/data/data.js",
                type:"GET",
				 data:{},
                dataType:"json", 
                success:function(data) {
                 $('#resText').empty();  
                         var html = ''; 
                         var htmllen='';						 
                         $.each(data.data.zhinan1, function(i, item){  
						     
                                 html += ' <li> ';  
								 html += '  <div style="font-size:18px;color:#717171">'+item.title+'</div> ';
								 html+='<a title=""  href="javascript:void(0);" style=" padding-top: 87px;">'
							     html+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;" src="resource/images/bank_option/alpha.png">'
								 html+='</a>'
							     html += ' </li> ';  
								 htmllen+='<li class="len-ar">'
								 htmllen+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllen+='</li>';
                         });  
						 

						  $('#resText').html(html);
						  $('.resText-t').html(htmllen);
						  
                           var html2 = ''; 
                           var htmllen2='';	
							 $.each(data.data.zhinan2, function(i, item){  
						     
                                 html2 += ' <li> ';  
								 html2 += '  <div style="font-size:18px;color:#717171">'+item.title+'</div> ';
								 html2+='<a title=""  href="javascript:void(0);"style=" padding-top: 87px;">'
							     html2+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 html2+='</a>'
							     html2 += ' </li> ';  
								 htmllen2+='<li>'
								 htmllen2+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllen2+='</li>';
                         });  
						  $('#resText2').html(html2);
						  $('.resText-t2').html(htmllen2);
                        
                         var html3 = ''; 
                           var htmllen3='';	
							 $.each(data.data.zhinan3, function(i, item){  
						     
                                 html3 += ' <li> ';  
								 html3 += '  <div style="font-size:18px;color:#717171">'+item.title+'</div> ';
								 html3+='<a title=""  href="javascript:void(0);"style=" padding-top: 87px;">'
							     html3+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 html3+='</a>'
							     html3 += ' </li> ';  
								 htmllen3+='<li>'
								 htmllen3+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllen3+='</li>';
                         });  
						  $('#resText3').html(html3);
						  $('.resText-t3').html(htmllen3);	
                        
                           var html4 = ''; 
                           var htmllen4='';	
							 $.each(data.data.zhinan4, function(i, item){  
						     
                                 html4 += ' <li> ';  
								 html4 += '  <div style="font-size:18px;color:#717171">'+item.title+'</div> ';
								 html4+='<a title=""  href="javascript:void(0);"style=" padding-top: 87px;">'
							     html4+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 html4+='</a>'
							     html4 += ' </li> ';  
								 htmllen4+='<li>'
								 htmllen4+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllen4+='</li>';
                         });  
						  $('#resText4').html(html4);
						  $('.resText-t4').html(htmllen4);	
                          
						  var html5 = ''; 
                           var htmllen5='';	
							 $.each(data.data.zhinan2, function(i, item){  
						     
                                 html5 += ' <li> ';  
								 html5 += '  <div style="font-size:18px;color:#717171">'+item.title+'</div> ';
								 html5+='<a title=""  href="javascript:void(0);"style=" padding-top: 87px;">'
							     html5+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 html5+='</a>'
							     html5 += ' </li> ';  
								 htmllen5+='<li>'
								 htmllen5+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllen5+='</li>';
                         });  
						  $('#resText5').html(html5);
						  $('.resText-t5').html(htmllen5);	
						  
						  var html6 = ''; 
                           var htmllen6='';	
							 $.each(data.data.zhinan6, function(i, item){  
						     
                                 html6 += ' <li> ';  
								 html6 += '  <div style="font-size:18px;color:#717171">'+item.title+'</div> ';
								 html6+='<a title=""  href="javascript:void(0);"style=" padding-top: 87px;">'
							     html6+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 html6+='</a>'
							     html6 += ' </li> ';  
								 htmllen6+='<li>'
								 htmllen6+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllen6+='</li>';
                         });  
						  $('#resText6').html(html6);
						  $('.resText-t6').html(htmllen6);	
						  
						
						 var htmlapp = ''; 
                         var htmllenapp='';						 
                         $.each(data.dataapp.zhinan1, function(i, item){  
						     
                                 htmlapp += ' <li> ';  
								 htmlapp += '  <div style="font-size:18px;color:#717171"> <p class="u-tip">'+item.alt+'</p></div> ';
								 htmlapp+='<a title=""  href="javascript:void(0);">'
							     htmlapp+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 htmlapp+='</a>'
							     htmlapp += ' </li> ';  
								 htmllenapp+='<li class="len-ar">'
								 //htmllenapp+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllenapp+='</li>';
                         });  
						 

						  $('#resTextapp').html(htmlapp);
						  $('.resText-tapp').html(htmllenapp);
						  
                           var htmlapp2 = ''; 
                           var htmllenapp2='';	
							 $.each(data.dataapp.zhinan2, function(i, item){  
						     
                                 htmlapp2 += ' <li> ';  
								 htmlapp2 += '  <div style="font-size:18px;color:#717171"><p class="u-tip">'+item.alt+'</p></div> ';
								 htmlapp2+='<a title=""  href="javascript:void(0);">'
							     htmlapp2+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 htmlapp2+='</a>'
							     htmlapp2+= ' </li> ';  
								 htmllenapp2+='<li>'
								// htmllenapp2+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllenapp2+='</li>';
                         });  
						  $('#resTextapp2').html(htmlapp2);
						  $('.resText-tapp2').html(htmllenapp2);
                        
                         var htmlapp3 = ''; 
                           var htmllenapp3='';	
							 $.each(data.dataapp.zhinan3, function(i, item){  
						     
                                 htmlapp3 += ' <li> ';  
								 htmlapp3 += '  <div style="font-size:18px;color:#717171"><p class="u-tip">'+item.alt+'</p></div> ';
								 htmlapp3+='<a title=""  href="javascript:void(0);">'
							     htmlapp3+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 htmlapp3+='</a>'
							     htmlapp3 += ' </li> ';  
								 htmllenapp3+='<li>'
								// htmllenapp3+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllenapp3+='</li>';
                         });  
						  $('#resTextapp3').html(htmlapp3);
						  $('.resText-tapp3').html(htmllenapp3);	
                        
                         var htmlapp4 = ''; 
                           var htmllenapp4='';	
							 $.each(data.dataapp.zhinan4, function(i, item){  
						     
                                 htmlapp4 += ' <li> ';  
								 htmlapp4 += '  <div style="font-size:18px;color:#717171"><p class="u-tip">'+item.alt+'</p></div> ';
								 htmlapp4+='<a title=""  href="javascript:void(0);">'
							     htmlapp4+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 htmlapp4+='</a>'
							     htmlapp4 += ' </li> ';  
								 htmllenapp4+='<li>'
								// htmllenapp4+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllenapp4+='</li>';
                         });  
						  $('#resTextapp4').html(htmlapp4);
						  $('.resText-tapp4').html(htmllenapp4);	
                          
						  var htmlapp5 = ''; 
                           var htmllenapp5='';	
							 $.each(data.dataapp.zhinan5, function(i, item){  
						     
                                 htmlapp5 += ' <li> ';  
								 htmlapp5 += '  <div style="font-size:18px;color:#717171"><p class="u-tip">'+item.alt+'</p></div> ';
								 htmlapp5+='<a title=""  href="javascript:void(0);">'
							     htmlapp5+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 htmlapp5+='</a>'
							     htmlapp5 += ' </li> ';  
								 htmllenapp5+='<li>'
								//htmllenapp5+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllenapp5+='</li>';
                         });  
						  $('#resTextapp5').html(htmlapp5);
						  $('.resText-tapp5').html(htmllenapp5);	
						  $('#resText5').html(htmlapp5);
						  $('.resText-t5').html(htmllenapp5);	
						  
						   var htmlapp6 = ''; 
                           var htmllenapp6='';	
							 $.each(data.dataapp.zhinan6, function(i, item){  
                                 htmlapp6 += ' <li> ';  
								 htmlapp6 += '  <div style="font-size:18px;color:#717171"><p class="u-tip">'+item.alt+'</p></div> ';
								 htmlapp6+='<a title=""  href="javascript:void(0);">'
							     htmlapp6+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 htmlapp6+='</a>'
							     htmlapp6 += ' </li> ';  
								 htmllenapp6+='<li>'
								// htmllenapp6+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllenapp6+='</li>';
                         });  
						  $('#resTextapp6').html(htmlapp6);
						  $('.resText-tapp6').html(htmllenapp6);

						  var htmlapp7 = ''; 
                          var htmllenapp7='';	
							 $.each(data.dataapp.zhinan7, function(i, item){  
								 
                                htmlapp7 += ' <li data-i="'+i+'">' 
								 htmlapp7 += '  <div style="font-size:18px;color:#717171"><p class="u-tip">'+item.alt+'</p></div> ';
								 htmlapp7+='<a title=""  href="javascript:void(0);">'
							     htmlapp7+=' <img width="965" height="534" alt="" style="background: url(resource/images/bank_option/'+item.img+') no-repeat center;"src="resource/images/bank_option/alpha.png">'
								 htmlapp7+='</a>'
							     htmlapp7 += ' </li>'  
								 htmllenapp7+='<li>'
								// htmllenapp6+='<div style="width:48px;height:4px;background:#ffe9dd;margin-top:17px;float:left;" class="xian">q</div><a style="float:left;color:#fff">'+item.len+'</a>'
								 
								 htmllenapp7+='</li>';
                        });  
						  $('#resTextapp7').html(htmlapp7);
						  $('.resText-tapp7').html(htmllenapp7);
						  $('#resText7').html(htmlapp7);
						  $('.resText-t7').html(htmllenapp7);
                },
                error:function() {
                    alert("错误");
                }
            });

    })
    
   var atRlen=$('.height-body');
   atRlen.each(function(index,domEle){
	   var heightt=$(this).offset().top;
	   
	   var _index=index;

	   $(window).scroll(function ()
		{
			var st = $(this).scrollTop();
			if(heightt-60<st){
				
		    	 $('.u-pc >li').eq(_index).addClass('nan-hover').siblings().removeClass('nan-hover') 
		     }
			
		});

   })
   setTimeout(function(){

	   $('.bj-ka >li').each(function(){
		  
		   if($(this).attr('data-i')==2){
	    		$(this).closest('ul').css('background',' url(resource/images/bank_option/cz3.jpg) no-repeat center 0')
	    		
	    	}
		   })
  
	     
   },800);
   /* 浏览器宽度 */
   var _width=$(window).width() ;
   if(_width<1278){ 
	   $('.lfet-nav').css('right','0px');
	   $('.left-guanbi').show();
	  }else{
		  $('.lfet-nav').css({'right':'50%','margin-right':'-675px'});
		  $('.left-guanbi').hide();
	  }
   $('.left-guanbi').click(function(){
	   $(this).closest('ul').closest('.lfet-nav').hide();
   })
    
     
    </script>
</html>