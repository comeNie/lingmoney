<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		$("#aboutusQualification").addClass("hover");

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
							<h2>资质证明</h2> 
							<img src="/resource/images/img_zhizhao1.jpg" alt="睿博盈通网络科技有限公司执照"/>
							<img src="/resource/images/yinyezhizhao.png" alt="睿博盈通网络科技有限公司执照"/>
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