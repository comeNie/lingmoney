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
 .section p{
   text-indent:0;
   padding-left:15px;
}
  
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$("#helpCenterLingbao").addClass("hover");

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
				<a href="/index.html">领钱儿</a>&nbsp;>&nbsp;<span>帮助中心</span>
			</div>
			<!-- 帮助中心开始 -->
			<jsp:include page="/common/helpcenter.jsp"></jsp:include>
			<!-- 帮助中心结束 -->

			<div class="mRight">
				<div class="mRight01">
					<div class="htitle">领宝详情</div>
					<div class="zonglan hcenter">
						<div class="section">

							<div>
								<strong class="color" style="margin-bottom:20px;">积领宝规则</strong>
								<table cellpadding="0" cellspacing="0" border="0"
									class="action_list action_list1"
									style="border: 1px solid #e4e4e4">
									<tr>
										<th width="6%">序号</th>
										<th width="22%">积领宝方式</th>
										<th width="13%" style="text-align:right">领宝数额（个）</th>
										<th width="40%">备注</th>
									</tr>
									<tr>
										<td height="48" width="6%" align="center">1</td>
										<td width="22%">注册激活E账户</td>
										<td width="13%" style="text-align:right;padding-right:10px;">100</td>
										<td width="40%" >常规活动</td>
									</tr>
									<tr>
										<td height="48" align="right">2</td>
										<td>推荐的好友注册并激活E账户</td>
										<td style="text-align:right;padding-right:10px;">50</td>
										<td >常规活动</td>
									</tr>
									<tr>
										<td height="48" align="right">3</td>
										<td>月理财奖励</td>
										<td style="text-align:right;padding-right:10px;">50</td>
										<td >&nbsp;每月首位、最后一位成功理财会员按理<br/>&nbsp;财额全月理财排名前三名的会员</td>
									</tr>
									<tr>
										<td height="48" align="right">4</td>
										<td>年理财奖励</td>
										<td style="text-align:right;padding-right:10px;">100</td>
										<td >按自然年理财排名前10名送领宝</td>
									</tr>
									<tr>
										<td height="48" align="right">5</td>
										<td>活动产品</td>
										<td style="text-align:right;padding-right:10px;"></td>
										<td >平台活动产品的活动期限内双倍送领宝</td>
									</tr>

								</table>
								<p style="text-indent: 0;padding:0">
									<strong class="color">1.如何获取领宝</strong>
								</p>
								<p>★在领钱儿购买理财成功可获得领宝：每理财100元/年可送2领宝。</p>
								<p>&nbsp;&nbsp;&nbsp;领宝赠送的计算公式：理财额÷365天×理财天数÷50＝所赠送领宝。</p>
								<p>★100元以下理财额不赠送领宝；领宝采用四舍五入，保留两位小数方法赠送。</p>
								<p>★购买的理财计划赎回时即赠送相应领宝。</p>
								<p>★领钱儿的活动赠送领宝，按【积领宝规则】来赠送。</p>
								<p style="text-indent: 0;padding:0">
									<strong class="color">2.如何使用领宝</strong>
								</p>
								<p>★领宝可以兑换领宝商城中的相应物品。 </p>
								<p>★领宝兑换相应礼品后系统将会自动扣减相应领宝。</p>
								<p>★会员有自行使用在各种奖励规则中或购买产品所获得的领宝的使用权力。</p>
								<p style="text-indent: 0;padding:0">
									<strong class="color">3.领宝的使用范围</strong>
								</p>
								<p>★领宝可兑换领宝商城中的相应物品。</p>
								<p style="text-indent: 0;padding:0">
									<strong class="color">4.领宝的获取、使用规则、修改权及最终解释权归北京睿博盈通网络科技有限公司所有。</strong>
								</p>
								<p style="text-indent: 0;padding:0">
									<strong class="color">5.平台对领宝规则及其他服务条款进行修改时，以通告形式公布于本公司平台公告栏，一经公布即视<br/>&nbsp;&nbsp;&nbsp;为已通知会员。</strong>
								</p>
								
							</div>
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