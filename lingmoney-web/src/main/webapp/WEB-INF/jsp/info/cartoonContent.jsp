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
		$("#financialstoryli").find("dl").show();
		$("#cartoon${cid}").addClass("hover");
		$("#cartooncategory").addClass("hover");
	});
</script>

</head>
<body>


	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<div class="mainbody_member">
		<div class="box clearfix">
			<!-- 理财小故事菜单开始 -->
			<jsp:include page="/common/financialstory.jsp"></jsp:include>
			<!-- 理财小故事菜单结束 -->

			<div class="mRight">
				<div class="mRight01">
					<div class="htitle">
						漫画贴
						<!--  <a href="storySeries.html">返回</a> -->
					</div>
					<div class="zonglan ncenter news-con"
						style="position: relative; z-index: 1; height: 50px;">
						<h2>${cartoonCategory.name }</h2>
						<h1>${cartoonContent.title }</h1>
						<div class="clearfix"
							style="position: relative; z-index: 1; height: 50px;">
							<span class="display-time">发表时间：<fmt:formatDate
									value="${cartoonContent.pubDate  }"
									pattern="yyyy-MM-dd HH:mm:ss" /></span>
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

						<p style="text-align: center">
							<img src="${cartoonContent.contentPic  }" />
						</p>

						<div class="prenext">
							<p class="pre">
								<c:choose>
									<c:when test="${cartoonContent_pre==null }">
									</c:when>
									<c:otherwise>
										上一篇：<a
											href="/financialStory/cartoonContent.html?id=${cartoonContent_pre.id}"><span
											title="${cartoonContent_pre.title }">${cartoonContent_pre.title}</span></a>
									</c:otherwise>
								</c:choose>
							</p>
							<p class="next">
								<c:choose>
									<c:when test="${cartoonContent_next==null }">
									</c:when>
									<c:otherwise>
										下一篇：<a
											href="/financialStory/cartoonContent.html?id=${cartoonContent_next.id}"><span
											title="${cartoonContent_next.title }">${cartoonContent_next.title}</span></a>
									</c:otherwise>
								</c:choose>
							</p>
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