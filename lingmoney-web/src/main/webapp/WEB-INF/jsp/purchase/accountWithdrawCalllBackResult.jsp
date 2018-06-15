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
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<div class="mainbody " style="padding: 30px 0">
		<div class="box">
			<div class="changepas">
				<div style="margin: 0 auto; width: 875px;">
					<c:if test="${status=='S'}">
						<div
							style="padding: 87px 0 16px 0; border-bottom: 1px solid #e5e5e5; text-align: center">
							<img src="/resource/images/img_returnSuccess.gif">
						</div>
					</c:if>
					<c:if test="${status=='F'}">
						<div
							style="padding: 87px 0 16px 0; border-bottom: 1px solid #e5e5e5; text-align: center">
							<img src="/resource/images/img_returnFail.gif">
						</div>
					</c:if>

					<c:if test="${status=='R'}">
						<div
							style="padding: 60px 0 16px 0; border-bottom: 1px solid #e5e5e5; text-align: center">
							<img src="/resource/images/img_returnManaging.gif">
						</div>
					</c:if>
					<div style="padding-bottom: 200px;">
						<p
							style="padding: 20px 0 73px 0; font-size: 18px; color: #362e2b; text-align: center">
							<span id="spanSecond">5</span>秒种后将自动返回我的领钱儿。
						</p>
						<p style="text-align: center">
							<a href="/myFinancial/finanCialManage">我的理财</a> <a
								href="/myLingqian/show">我的领钱儿</a>
						</p>
					</div>
				</div>

			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->

	<script type="text/javascript">
		window.onload = function() {
			countDown(6);
		}
	</script>
	<script type="text/javascript">
		function countDown(secs) {
			surl = "/myLingqian/show";
			if (--secs > 0) {
				document.getElementById("spanSecond").innerHTML = secs;
				setTimeout("countDown(" + secs + ")", 1000);
			} else {
				location.href = surl;
			}
		}
	</script>
</body>
</html>