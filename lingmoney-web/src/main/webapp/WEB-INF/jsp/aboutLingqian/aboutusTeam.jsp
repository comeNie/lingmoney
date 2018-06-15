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
		$("#aboutusTeam").addClass("hover");
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
					<div class="htitle">关于我们</div>
					<div class="zonglan hcenter ncenter">
						<div class="section">
							<h2>团队精英</h2>
							<div >
								<img src="/resource/images/banner_aboutTeam.jpg"
									 width="100%" alt="领钱团队精英"/>
							</div>
							<p style="padding-top:20px;margin-left:0">我们是一支高效专业的团队，我们是一群有想法、敢创新、勇于实践的年轻人，追随着互联网发展的脚步与专业金融的创新而凝聚在一起。</p>
							<p style="margin-left:0">我们胸怀豪情、不骄不躁、底蕴深厚、拥有远大理想。</p>
							<p style="margin-left:0">我们创想无限、活力无限，坚信着一个理念——“只有耐得住寂寞，才能等得到繁华！”</p>
							<p style="margin-left:0">我们希望通过不断创新，向大众传递着“健康理财、快乐理财、诚信理财”的理念，建立良好的理财习惯，让大众在“开源”之余学会“节流”。</p>
							<p style="margin-left:0">这就是我们，一群梦想与实践结合的“领钱儿“人，一群不断向前奔跑着的”领钱儿“人。</p>
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