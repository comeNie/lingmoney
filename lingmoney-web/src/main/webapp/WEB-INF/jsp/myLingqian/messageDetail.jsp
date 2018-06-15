<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>


<script type="text/javascript">
	$(document).ready(function(){
		$("#message").addClass("hover");
		$("#membersUserNews").addClass("hover");
		$("#nav_member03").addClass("nav-hover");
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
		
		/*消息中心*/
		$(".button_news").toggle(function(){
		   $(this).text('收起');
			 $(this).css("background","url('/resource/images/bg_showHide.gif') no-repeat right bottom");
		     $(this).parent().parent().next().show();
		   },function(){
		     $(this).text('查看');
			 $(this).css("background","url('/resource/images/bg_showHide.gif') no-repeat right top");
		     $(this).parent().parent().next().hide();
		   
		   })	
		})  
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
			<!-- 我的账户菜单开始 -->
			<jsp:include page="/common/myaccount.jsp"></jsp:include>
			<!-- 我的账户菜单结束 -->

			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">消息中心</div>
					<div class="zonglan clearfix ncenter">
						<div style="width: 100%; height: 20px;">
							<a href="/usersMessage/list.html" style="color: red; font-size: 12px; float: right; ">《 返回列表</a>
						</div>
						<h1 style="padding-bottom:15px;text-align:center">${message.topic }</h1>
						<p style="margin-bottom:16px;color:#acacac;text-align:center">
							<span style="margin-right:10px;">发件人：<em style="font-style:normal;color:#248526">系统管理员</em></span>
							<span>时间：<fmt:formatDate value="${message.time }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
						</p>
						<p>${message.content }</p>
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