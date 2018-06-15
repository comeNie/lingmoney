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
		$("#aboutusWe").addClass("hover");
		$("#aboutusWeli").find("dl").show();
		$("#aboutus").addClass("hover");
		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");
	});
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
					<div class="htitle">关于我们</div>
					<div class="zonglan hcenter ncenter">
						<div class="section">
							<h2>公司简介</h2>
							<div style="text-align: center">
								<img src="/resource/images/banner_aboutus.jpg" width="100%" alt="睿博盈通网络科技有限公司"/>
							</div>
							<p style="padding-top: 20px; text-indent: 2em;margin-left:0">
								领钱儿是北京睿博盈通网络科技有限公司旗下的互联网金融理财平台，隶属于万德众归集团互联网金融事业部。依托“互联网+”及“全民理财”的发展理念，面向社会各阶层人士，打造新型的互联网金融理财平台，帮助用户建立良好的理财习惯。</p>
								<p style="margin-left:0">领钱儿拥有先进的技术团队及严谨专业的风控团队，通过引入先进的互联网技术，成功接入广东华兴银行存管系统，建立大分散的风控体系，致力于为大众提供便捷、透明、智能化的一站式理财服务。</p>
								<p style="margin-left:0">为不断提升服务品质，深化用户体验，2017年，领钱儿迎来全新品牌升级。借助银行存管系统的引入，网站及APP2.0版本的升级，平台将在不断完善资质、丰富产品的基础上，打造有温度、有情怀、高格调的“领钱儿范”，为用户提供个性化、多福利、一站式的综合理财金融服务，帮助客户实现财务增值，惠及大众及社会。</p>
							<p style=" text-indent: 0; padding: 10px 0 0 0;margin-left:0">
								<strong style="display: inline; margin: 0; padding: 0; font-size: 15px; color: #434343">领钱儿slogan：</strong>自由梦想，源自领钱儿
							</p>
							<p style=" text-indent: 0; padding: 10px 0 0 0;margin-left:0">
								<strong style="display: inline; margin: 0; padding: 0; font-size: 15px; color: #434343">领钱儿愿景：</strong>致力于打造大众值得信赖的理财平台
							</p>
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