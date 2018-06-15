<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
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
		$("#aboutusNews").addClass("hover");
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
					<div class="zonglan ncenter">
						<h2>公司动态</h2>
						<ul>
							<c:forEach var="item" items="${gridPage.rows}" varStatus="status">
								<li><a href="/infoNews/details.html?id=${item.id}">${item.title}</a><span>
										<fmt:formatDate value="${item.pDt }" pattern="yyyy-MM-dd" />
								</span></li>
							</c:forEach>
						</ul>
						<div class="pages">
							<w:pager pageSize="${pageSize}" pageNo="${pageNo}" url=""
								recordCount="${gridPage.total}" />
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