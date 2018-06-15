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
		$("#aboutusZixun").addClass("hover");
		$("#aboutusZixunli").find("dl").show();
		$("#aboutusIdea").addClass("hover");
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
				<a href="/index.html">领钱儿</a>&nbsp;>&nbsp;<span>关于我们</span>
			</div>
			<!-- 关于我们开始 -->
			<jsp:include page="/common/aboutus.jsp"></jsp:include>
			<!-- 关于我们结束 -->
			<div class="mRight">
				<div class="mRight01">
					<div class="htitle">资讯中心</div>
					<div class="zonglan ncenter" style="padding-bottom: 150px;">
						<div class="section">

							<h2>理财理念</h2>
							<h1 title="领钱儿理念">领钱儿理念</h1>
							<p>
								在互联网金融日益盛行的当下，领钱儿始终坚持<b style="font-size: 15px;">“大类资产配置+互联网金融=资产稳健增值”</b>的理财理念。
							</p>
							<strong class="color">什么是大类资产配置？</strong>
							<p>大类资产广义上包括现金、房产、大宗商品、黄金、债券、理财产品以及权益类资产等；狭义上指现金、债券和股票。</p>
							<p>大类资产配置就是根据用户本身对收益的要求、风险承受能力以及市场形势的分析判断而进行有效的大类资产比例划分。</p>
							<p>坚持大类资产配置至上的理念是不以追求单边市场中利润最大化为目标，而是追求避免在任何一个时点上出现大的损失，尽量减少管理资产净值缩水幅度，从而达到在一个完整经济波动周期中取得收益最大化的目的。</p>
							<strong class="color">什么是互联网金融？</strong>
							<p>以互联网的精神为指引的金融业，可以统称为互联网金融业。它包括第三方支付、互联网金融产品销售、信用评价审计、金融中介和金融电子商务插件。</p>
							<p>互联网金融业务使所有国家的金融部门都站在同一条起跑线上，所以即使中国金融业的水平和金融业发达国家的差距较大，互联网金融业务也可以提供机会使中国整体金融水平与世界先进水平国家的差距缩小。</p>

						</div>

					</div>

				</div>

			</div>
			<div style="clear:both"></div>
		</div>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>