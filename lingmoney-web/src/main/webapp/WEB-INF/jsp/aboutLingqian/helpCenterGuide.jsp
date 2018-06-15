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
		$("#helpCenterGuide").addClass("hover");

		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");

		/*新手指南效果*/
		$(".ul-guide li span").hover(function() {
			$(this).css("cursor", "pointer");
		})
		$(".ul-guide-dl").hide();
		$(".ul-guide li span").click(function() {
			$(".ul-guide-dl").stop().slideUp();
			$(this).next("dl").stop().slideDown();
		})

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
				<a href="/index.html">领钱儿</a>&nbsp;>&nbsp;<span>帮助中心</span>
			</div>
			<!-- 帮助中心开始 -->
			<jsp:include page="/common/helpcenter.jsp"></jsp:include>
			<!-- 帮助中心结束 -->

			<div class="mRight">
				<div class="mRight01">
					<div class="htitle">新手指南</div>
					<div class="zonglan hcenter">
						<ul class="ul-guide">
							<p  style="font-size:16px; color: #434343">注册与登录</p>
							<li><span class="color">注册时，进行手机验证，收不到短信怎么办？</span>
								<dl class="ul-guide-dl">
									<dd>1.请确认手机是否安装短信拦截或过滤软件;</dd>
									<dd>2.请确认手机是否能够正常接收短信（信号问题、欠费、停机等）；</dd>
									<dd>3.短信收发过程中可能会存在延迟，请耐心等待；</dd>
									<dd>4.您还可以联系客服寻求帮助 <i class="color">（400-0051-655）</i>。 </dd>
									<dd></dd>
								</dl></li>
							<li><span class="color">注册需要提供什么资料？</span>
								<dl class="ul-guide-dl">
									<dd>只需提供您常用的手机号码，并通过短信验证即可注册。</dd>
								</dl></li>
						</ul>

						<ul class="ul-guide">
							<p style="font-size: 16px;color: #434343">常见问题</p>
							<li><span class="color">我能在领钱儿理财么？</span>
								<dl class="ul-guide-dl">
									<dd>只要您是年满18周岁，具有完全民事行为能力的中国籍大陆居民，便可在线申请理财。</dd>

								</dl></li>
							<li><span class="color">用户注册信息有安全保护吗？</span>
								<dl class="ul-guide-dl">
									<dd>领钱儿采用先进的系统加密及互联网保护技术，加上完善的运维监测体系，有效保障会员银行账号、交易密码等机密信息在网络传输过程中不被查看、修改或窃取。</dd>
								</dl></li>
						</ul>

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