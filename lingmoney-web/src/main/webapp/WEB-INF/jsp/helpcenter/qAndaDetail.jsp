<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript" src="/resource/js/jquery.qqFace.js"></script>
<style type="text/css">
	table,table tr,table td{
		border:1px solid #ccc;
		text-align: left;
	}
</style>
<script type="text/javascript">
	$(document).ready(
		function() {
			$("#helpCenterAsk").addClass("hover");
			$(".main-head li:last").css("border", "none");
			$(".main3 li:last").css("margin-right", "0");
			$(".licai li:last").css("margin-right", "0");
			$(".a-ask").click(function() {
				$("#rz-box-bg").css("display", "block");
				$("#rz-box-mima").css("display", "block");
			});
			$(".rz-close").click(function() {
				$("#rz-box-bg").hide();
				$("#rz-box-mima").hide();
			});
			$(".answer").click(
				function() {
					$(this).parent(".bottom-qestions").next(".answer-content")
						   .find(".replay_con").show();
				});
			$(".answer2").click(function() {
				$(this).parent(".bottom-answer-contents").next(".answer-contents-detail")
					   .find(".replay_con").show();
			});
			$(".say").click(function() {
				$(this).parent("div").next(".replay_con").show();
		});
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
				<a href="/index.html">领钱儿</a>&nbsp;>&nbsp;<span>帮助中心</span>
			</div>
			<!-- 帮助中心开始 -->
			<jsp:include page="/common/helpcenter.jsp"></jsp:include>
			<!-- 帮助中心结束 -->
			<div class="mRight">
				<div class="mRight01">
					<div class="htitle">
						您问我答 <a href="/usersAsk/list.html">返回</a>
					</div>
					<div class="zonglan askcenter">
						<div class="question-askdetail" style="padding-top: 40px">
							<div class="question-askdetail-title">
								<p>
									<strong title="${usersAskVo.title }">${usersAskVo.title }</strong>
								</p>
								<p>
									<span class="asker">提问者</span><span class="color">${usersAskVo.nickName }</span>
									[${usersAskVo.degreeName }]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;提问时间：
									<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${usersAskVo.time }" />
								</p>
							</div>
							<p class="content-question">${usersAskVo.content }</p>
							<c:if test="${usersAskVo.keyWord!=null&&usersAskVo.keyWord!='' }">
								<div class="bottom-qestions">
									<c:forEach items="${fn:split(usersAskVo.keyWord, ',')}" var="key">
										<span>${key }</span>
									</c:forEach>
								</div>
							</c:if>
						</div>

						<c:if test="${usersAskAnwser!=null }">
							<div class="answer-contents">
								<div class="answer-contents-item">
									<div class="time-answer clearfix">
										<div class="time-answer-day">
											<fmt:formatDate pattern="dd" value="${usersAskAnwser.time }" />
										</div>
										<div class="time-answer-month">
											<p>
												<fmt:formatDate pattern="MMMM"
													value="${usersAskAnwser.time }" />
											</p>
											<p style="font-size: 18px;">
												<fmt:formatDate pattern="yyyy"
													value="${usersAskAnwser.time }" />
											</p>
										</div>
									</div>
									<p class="content-question">${usersAskAnwser.content }</p>
									<div class="bottom-answer-contents">
										<span class="asker" style="margin-right: 5px;"><font
											class="color">${usersAskAnwser.anwserMan }</font></span><span><fmt:formatDate
												pattern="HH:mm:ss" value="${usersAskAnwser.time }" /></span>
									</div>
								</div>
							</div>
						</c:if>
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