<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>


<script type="text/javascript">
	$(document).ready(function() {
		$("#helpCenterRule").addClass("hover");

		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");

		/*顶部*/
		$(".leftul .weixinLi").hover(function() {
			$(".popweixin").show();
		}, function() {
			$(".popweixin").hide();
		})
		$(".leftul .qqLi").hover(function() {
			$(".popQQ").show();
		}, function() {
			$(".popQQ").hide();
		})

		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		})
		/*顶部*/

	})
</script>




</head>
<body>





	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<div class="mainbody">
		<div class="box clearfix">
			<div class="sitemap">
				<a href="/index.html">领钱儿</a>&nbsp;>&nbsp;<span>帮助中心</span>
			</div>
			<!-- 帮助中心开始 -->
			<jsp:include page="/common/helpcenter.jsp"></jsp:include>
			<!-- 帮助中心结束 -->

			<div class="mRight">
				<div class="mRight01">
					<div class="htitle">平台规则</div>
					<div class="zonglan hcenter">
						<div class="section">
							<strong class="color">领钱儿交易规则</strong>
							<h6>1.平台会员</h6>
							<p>只要您是年满18周岁，具有完全民事行为能力的中国籍大陆居民，便可在线申请理财。</p>
							<h6>2.开通E账户</h6>
							<p>进入会员首页，点击【我的账户】图标，进入银行卡信息，输入【姓名】、【身份证号】点击立即开通。</p>
							<p style="color:#ea5513;font-size:12px;text-indent:0;">★相关认证细节可能会视市场环境及平台需求不定期做更改，请以具体认证页面的提示信息为准。</p>
							<h6>3.绑卡激活</h6>
							<p>开通E帐号后，点击【我的账户】图标，进入银行卡信息，点击【请绑卡激活华兴E账户】。</p>
							<h6>4.充值</h6>
							<p>进入会员首页，点击【我的理财】图标，输入充值金额，点击充值。</p>
							<h6>5.如何购买理财</h6>
							<p>进入领钱儿首页，点击【我要理财】，选择【开放中】的产品，进入产品详情页，输入购买金额，点击【我要理财】；选择支付方式。页面跳转至银行支付页面进行操作，根据操作直至购买成功。</p>
							<h6>6.如何赎回/兑付</h6>
							<p>产品到期自动还款到E账户虚拟户中。</p>
							<h6>7.如何提现</h6>
							<p>下载华兴投资融平台进行实名验证后，入会员首页，点击【我的理财】图标，输入充值金额，点击提现。</p>
						</div>

					</div>

				</div>

			</div>
			<div style="clear: both"></div>
		</div>
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>