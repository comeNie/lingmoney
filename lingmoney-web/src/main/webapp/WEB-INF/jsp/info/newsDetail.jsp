﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
		$("#aboutusZixun").addClass("hover");
		$("#aboutusZixunli").find("dl").show();
		$("#aboutusNews").addClass("hover");

		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");
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
					<div class="htitle">
						资讯中心 <a href="/infoNews/list.html">返回</a>
					</div>
					<div class="zonglan ncenter">
						<h2>公司动态</h2>
						<h1 title="${infoNews_now.title }">${infoNews_now.title }</h1>
						<div class="clearfix" style="position:relative;z-index:1;height:50px;">
							<span class="display-time">发表时间：<fmt:formatDate
									value="${infoNews_now.pDt }" pattern="yyyy/MM/dd HH:mm:ss" /></span>
							<!-- JiaThis Button BEGIN -->
							<div class="jiathis_style jia_div">
								<span class="jiathis_txt">分享到：</span> <a
									class="jiathis_button_qzone"></a> <a
									class="jiathis_button_tsina"></a> <a class="jiathis_button_tqq"></a>
								<a class="jiathis_button_weixin"></a> <a
									class="jiathis_button_renren"></a> 
							</div>
						</div>
						<script type="text/javascript">
							var jiathis_config = {
								url : "http://www.lingmoney.cn",
								title : "领钱儿 安全便捷的综合互联网金融服务平台",
								summary : "领钱儿 安全便捷的综合互联网金融服务平台---为您轻松解决理财问题", //定义要分享页面的摘要，摘要默认为Meta标签中Description部分的内容
							};
						</script>
						<script type="text/javascript"
							src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
						<!-- JiaThis Button END -->
						${infoNews_now.content }
						<div class="prenext">
							<p class="pre">
								<c:choose>
									<c:when test="${infoNews_pre==null }">
									</c:when>
									<c:otherwise>
										上一篇：<a href="/infoNews/details.html?id=${infoNews_pre.id}"><span
											title="${infoNews_pre.title }">${infoNews_pre.title}</span></a>
									</c:otherwise>
								</c:choose>
							</p>
							<p class="next">
								<c:choose>
									<c:when test="${infoNews_next==null }">
									</c:when>
									<c:otherwise>
										下一篇：<a href="/infoNews/details.html?id=${infoNews_next.id}"><span
											title="${infoNews_next.title }">${infoNews_next.title}</span></a>
									</c:otherwise>
								</c:choose>
							</p>
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