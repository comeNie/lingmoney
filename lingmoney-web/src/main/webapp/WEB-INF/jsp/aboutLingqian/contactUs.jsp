<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>


<style type="text/css">
  .mapcenter p{text-indent:0;}
</style>


<script type="text/javascript">
	$(document).ready(function() {
		$("#aboutusMap").addClass("hover");
		$("#contact").addClass("hover");
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
					<div class="htitle">关于我们</div>
					<div class="zonglan hcenter ncenter mapcenter">
					   <h2>联系我们</h2>
					   <script type="text/javascript" src="/resource/js/bdMap.js"></script>
						<div id="map"
							style="margin-bottom: 35px; height: 345px; width: 100%; border: 1px solid #bcbcbc;"></div>
						 <script type="text/javascript">
							ShowMap("map", {
								city : '北京',
								addr : '北京市朝阳区广渠路3号竞园艺术中心21A',
								title : '北京睿博盈通网络科技有限公司',
								lawfirm : '北京睿博盈通网络科技有限公司',
								tel : '400-0051-655',
								ismove : '0',
								piobj : 'nxp'
							});
						</script>
						<strong
							style="display: block; padding: 20px 0; font-size: 20px;">北京睿博盈通网络科技有限公司</strong>

						<p>地址：北京市朝阳区广渠路3号竞园艺术中心21A</p>
						 <p>
							交通：[地铁]七号线百子湾站A3口出， 步行约10分钟</p> 
						<p style="padding-left:42px;">
						         [公交]11、23、455、541路小海子站下车步行200米
						</p>
						<p>电话：400-0051-655</p>
						<p>邮件：Official@lingmoney.cn</p>
						<strong
							style="display: block; padding: 20px 0; font-size: 20px;">客户服务、投诉与建议</strong>
						<p>在这里我们会为您解决使用过程中遇到的所有疑问。</p>
						<p>客服热线：400-0051-655</p>
						<p>工作时间：09:00-18:00（工作日）</p>
						<p>领钱儿客服 QQ1：3232833073</p>
						<p>领钱儿客服 QQ2：3163127228</p>
						<p>领钱儿客服 QQ3：1549025970</p>
						<p>邮箱：Service@lingmoney.cn</p>
						<strong
							style="display: block; padding: 20px 0; font-size: 20px;">媒体采访与业务合作</strong>
						<p>Email：Official@lingmoney.cn</p>
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