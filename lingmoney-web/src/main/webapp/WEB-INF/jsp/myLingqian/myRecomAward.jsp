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
$(document).ready(function(){
	$("#membersRecTicket").addClass("hover");
	$("#nav_member04").addClass("nav-hover");
	$(".main-head li:last").css("border","none");
	$(".main3 li:last").css("margin-right","0");
	$(".licai li:last").css("margin-right","0"); 
	/*顶部*/		 
	$(".media-link li:first").hover(function(){
	  $(this).find("div").show();
	},function(){
	  $(this).find("div").hide();
	})   
	/*顶部*/
}); 
</script>
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<!-- 用户导航开始 -->
	<jsp:include page="/common/navmember.jsp"></jsp:include>
	<!-- 用户导航结束 -->

	<div class="mainbody_member">
		<div class="box clearfix">
			<!-- 我的推荐菜单开始 -->
			<jsp:include page="/common/myrecommend.jsp"></jsp:include>
			<!-- 我的推荐菜单结束 -->

			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">推荐兑奖</div>
					<div class="zonglan clearfix recommend">
						<p style="padding-top: 20px; font-size: 16px; font-weight: normal;">目前可参与兑奖活动</p>
						<div class="intro">
							您暂没有可兑换的奖品！<a href="/index.html" class="color">去首页看看更多活动</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>