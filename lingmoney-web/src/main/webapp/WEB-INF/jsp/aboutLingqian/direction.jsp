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

<script type="text/javascript">
	$(function() {
		$(".dir_box_top span").removeClass("active");
		$(".dir_box_top img").attr("src", "/resource/images/img_dir_top.jpg");
		$(".dir_box i").css("background-position", "left top");
		$(".box_top_title").css("background-position", "right -51px");
		$(".box_top_title h2").css("background-position", "left -51px");

		$(".dir_box_top").hover(
				function() {
					$(this).css("border", "1px solid #f2545a");
					$(this).find("span").addClass("active");
					$(this).find("img").attr("src",
							"/resource/images/img_dir_topH.jpg");
					$(this).prev("i").css("background-position", "left -26px");
					$(this).find(".box_top_title").css("background-position",
							"right top");
					$(this).find(".box_top_title h2").css(
							"background-position", "left top");
				},
				function() {
					$(this).css("border", "1px solid #fff");
					$(this).find("span").removeClass("active");
					$(this).find("img").attr("src",
							"/resource/images/img_dir_top.jpg");
					$(this).prev("i").css("background-position", "left top");
					$(this).find(".box_top_title").css("background-position",
							"right -51px");
					$(this).find(".box_top_title h2").css(
							"background-position", "left -51px");

				})
		$(".dir_box_bottom").hide(500);
		$(".dir_box_top").click(function() {
			$(".dir_box_bottom").stop().hide(500);
			$(this).next(".dir_box_bottom").stop().toggle(500);
			$(this).css("border", "1px solid #fff");

		})
	})
</script>

</head>
<body style="background: #eee">

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<div
		style="width: 100%; min-width: 1024px; overflow: hidden; height: 446px; border-top: 1px solid #ddd; background: url('/resource/images/banner_direction.jpg') no-repeat center top #e02828"></div>
	<div style="width: 100%; min-width: 1024px; background: #f7f8f8;">
		<div class="dir_step box">
			<strong>“领钱儿”网操作步骤分为：</strong>
			<p>
				【<a href="#step01">注册</a>】、【<a href="#step02">绑定银行卡</a>】、【<a
					href="#step03">购买理财</a>】、【<a href="#step04">赎回/兑付</a>】。
			</p>
			<p>以下分别为对每个步骤的具体操作流程图及讲解。</p>
		</div>
	</div>
	<a name="step01"></a>
	<div class="mainbody body_direction">
		<div class="box">
			<div class="dir_box">

				<i></i>
				<div class="dir_box_top">
					<div class="box_top_title">
						<h2>注册</h2>
					</div>
					<p>
						进入领钱儿首页，<img src="/resource/images/img_dir_top.jpg" /> 点击【<span>注册</span>】，<img
							src="/resource/images/img_dir_top.jpg" />填写资料（选填，<span>推荐人代码</span>），查看《用户注册协议》，确认后，<img
							src="/resource/images/img_dir_top.jpg" />点击【<span>同意并注册</span>】，即注册成功。<img
							src="/resource/images/img_dir_top.jpg" />点击【<span>进入我的领钱儿</span>】,<img
							src="/resource/images/img_dir_top.jpg" />在会员首页，点击【<span>我的账户</span>】，可<span>修改</span>会员资料。
					</p>
				</div>
				<div class="dir_box_bottom">
					<img src="/resource/images/dir_regist.gif" />
				</div>
			</div>


			<a name="step02"></a>
			<div class="dir_box">

				<i></i>
				<div class="dir_box_top">
					<div class="box_top_title box_top_title_card">
						<h2>绑定银行卡</h2>
					</div>
					<p>
						进入会员首页，<img src="/resource/images/img_dir_top.jpg" />点击【<span>银行卡</span>】图标，<img
							src="/resource/images/img_dir_top.jpg" />进入银行账户信息，点击【<span>去绑定</span>】，<img
							src="/resource/images/img_dir_top.jpg" />输入<span>与实名相符</span>的银行卡号并<span>输入交易密码</span>，<img
							src="/resource/images/img_dir_top.jpg" />点击【<span>确认添加</span>】即绑定成功。
					</p>
				</div>
				<div class="dir_box_bottom">
					<img src="/resource/images/dir_bindbank.gif" />
				</div>
			</div>

			<a name="step03"></a>
			<div class="dir_box">

				<i></i>
				<div class="dir_box_top">
					<div class="box_top_title box_top_title_buy">
						<h2>购买理财</h2>
					</div>
					<p>
						进入领钱儿首页，<img src="/resource/images/img_dir_top.jpg" />点击【<span>我要理财</span>】，<img
							src="/resource/images/img_dir_top.jpg" /> 选择【<span>开放中</span>】
						的产品，进入产品详情页，<img src="/resource/images/img_dir_top.jpg" />输入购买金额，点击【<span>我要理财</span>】；<img
							src="/resource/images/img_dir_top.jpg" />选择支付方式；<img
							src="/resource/images/img_dir_top.jpg" />跳转至银行支付页面进行操作，根据操作直至购买成功。
					</p>
				</div>
				<div class="dir_box_bottom">
					<img src="/resource/images/dir_purchase.gif" />
				</div>
			</div>
			<a name="step04"></a>
			<div class="dir_box">

				<i></i>
				<div class="dir_box_top">
					<div class="box_top_title box_top_title_shu">
						<h2>赎回/兑付</h2>
					</div>
					<p>
						进入会员首页，<img src="/resource/images/img_dir_top.jpg" />点击【<span>我的理财</span>】，<img
							src="/resource/images/img_dir_top.jpg" />选择可赎回理财产品，点击【<span>申请赎回</span>】查看产品赎回详情，<img
							src="/resource/images/img_dir_top.jpg" />点击【<span>确定</span>】即可赎回并兑付该产品赎回份额。固定期限理财产品到期后自动兑付。兑付金额将退至用户绑定默认银行卡。
					</p>
				</div>
				<div class="dir_box_bottom">
					<img src="/resource/images/dir_redeem.gif" />
				</div>
			</div>
		</div>
	</div>


	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>