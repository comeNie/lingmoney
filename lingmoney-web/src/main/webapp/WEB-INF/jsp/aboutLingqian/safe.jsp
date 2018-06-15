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

		$(".mLeft li a").click(function() {
			$(".mLeft").find("dl").stop().slideUp();
			$(this).parent("li").next("dl").stop().slideDown();

		})

	})
</script>




</head>
<body>



	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<div
		style="width: 100%; overflow: hidden; height: 446px; border-top: 1px solid #ddd; background: url('/resource/images/banner_safe.jpg') no-repeat center top #ebb27d"></div>
	<div class="mainbody mainbody_safe">
		<div class="box" style="background: #fff">
			<img width="1024px" alt="" src="/resource/images/aqbz_2.jpg">
		</div>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>