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
		$("#helpCenterRegime").addClass("hover");
		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");
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
					<div class="htitle">会员制度</div>
					<div class="zonglan hcenter">
						<div class="section">
							<strong class="color">会员的生成及目的</strong>
							<p style="margin-left:0;">用户通过购买领钱儿的理财产品即可成为领钱儿会员。领钱儿会员以购买理财产品的累计总额，来设定会员的等级。</p>
							<strong class="color">会员级别设定</strong>
							<p style="margin-left:0;">领钱儿会员共分为10个级别，分别为：<font style="font-weight: bold;">零级、一级、二级、三级、四级、五级、六级、七级、八级、九级</font>。以“皇冠”为会员头像。</p>
							<strong class="color">会员级别升级条件</strong>
							<img src="/resource/images/level.jpg">
						</div>

						<div class="section">
							<strong class="color">会员的权益与义务</strong>
							<h6>1.会员资格界定及享受的权益</h6>
							<p style="text-indent:0;">凡在领钱儿购买理财产品的用户即可成为领钱儿会员。</p>
							<p style="text-indent:0;">在领钱儿有新的活动及新产品得到短信通知。</p>
							<p style="text-indent:0;">领钱儿会员享受平台活动、生日祝福、节假日天气提醒等。</p>
							<h6>2.会员服务流程</h6>
							<p style="text-indent:0;">会员提醒服务是短信或邮件的形式达成。</p>
						</div>

						<div class="section"
							style="margin-bottom: 30px; padding: 10px; border: 1px dashed #ccc">
							<strong class="color">附 则</strong>
							<p style="text-indent:0">1.
								本公司严格保密会员资料信息，除国家机关需要等原因外，公司不在未经会员授权的情况下公开或向第三方透露其注册资料信息及保存在公司中的非公开内容的个人信息；</p>
							<p style="text-indent:0">2. 对会员作出损害本公司及本公司合作商家、联名商家利益的行为，将被列入本平台黑名单，本公司保留追究法律责任的权利；</p>
							<p style="text-indent:0">
								3. 会员规则的修改权及最终解释权归
								<b class="color" onclick="location.href='/index.html'" style="cursor: pointer">北京睿博盈通网络科技有限公司</b>所有；
							</p>
							<p style="text-indent:0">4.  平台对会员规则及其他服务条款进行修改时，以通告形式公布于本公司平台页面上，一经公布即视为已通知会员。</p>
						</div>
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