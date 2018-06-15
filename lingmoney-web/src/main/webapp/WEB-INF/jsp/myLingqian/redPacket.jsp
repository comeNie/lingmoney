<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="/resource/laydate/laydate.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#redPacket").addClass("hover");
		$("#nav_member02").addClass("nav-hover");
		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");

		/*顶部*/
		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		})
		/*顶部*/
		
		var choosedType = $("#typeValue").val();
		$("#quanb" + choosedType).addClass("hover");
		$("#coupon-active" + $("#hrpTypeValue").val()).addClass("coupon-active");
		
		//==规则切换==
	    var t =  $("#hrpTypeValue").val();
        if (t == 1) {
            $("#ruleboxRedpacket").hide();
            $("#ruleboxRate").show();
            
        } else if (t == 2) {
            $("#ruleboxRedpacket").show();
            $("#ruleboxRate").hide();
        }
        //==规则切换==
	});

	function changeType(type) {
		window.location.href = "/myFinancial/redPacket?type=" + type + "&hrpType=" + $("#hrpTypeValue").val();
		
	}

</script>
</head>
<body>
	<input hidden="true" value="${type}" id="typeValue">
	<input hidden="true"value="${hrp_type}" id="hrpTypeValue">

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<!-- 用户导航开始 -->
	<jsp:include page="/common/navmember.jsp"></jsp:include>
	<!-- 用户导航结束 -->

	<div class="mainbody_member">
		<div class="box clearfix">
			<!-- 我的理财菜单开始 -->
			<jsp:include page="/common/mymanage.jsp"></jsp:include>
			<!-- 我的理财菜单结束 -->

			<form action="/myFinancial/accountFlow" id="queryForm" method="post">
				<input type="hidden" name="type" id="type" value="">
			</form>

			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">优惠券</div>
						<div class="coupon">
					  		<a href="/myFinancial/redPacket?type=0&hrpType=2" class="" id="coupon-active2">红包</a>
					  		<a href="/myFinancial/redPacket?type=0&hrpType=1"  id="coupon-active1">加息券</a>
						</div>
					<div class="zonglan clearfix" style="min-height: 600px;">
					
						<dl class="dl02 clearfix" style="padding-top: 20px;">
							<dd>
								<a href="javascript:void(0)" id="quanb0" onclick="changeType(0)" >未使用</a>
							</dd>
							<dd>
								<a href="javascript:void(0)" id="quanb1" onclick="changeType(1)" >已使用</a>
							</dd>
							<dd> 
								<a href="javascript:void(1)" id="quanb2" onclick="changeType(2)" >已过期</a>
							</dd>
						</dl>
						
						<div class="jiaxi">
							<!-- 加息券列表  开始-->
							<div class="increasingList clearfix">
							
								<c:if test="${pageInfo != null && pageInfo.code == 200 }">
									<c:forEach items="${pageInfo.rows }" var="item">  
										<div class="cardBox <c:if test="${type != 0}">overdue</c:if>">
											<div class="cb_head">
												<c:if test="${hrp_type == 1}">
													<span>${item.amount }<i>%</i></span>&nbsp;${item.hrName }
												</c:if>
												<c:if test="${hrp_type == 2}">
													<span>${item.amount }<i>元</i></span>&nbsp;${item.hrName }
												</c:if>
											</div>
											<div class="cb_middle">
												<c:if test="${item.activityRemark != null && fn:length(item.activityRemark) > 0}">
													<c:forEach items="${item.activityRemark }" var="remark">
														<c:forEach items="${remark }" var="rem">
															<p>${rem.key }：<span>${rem.value }</span></p>
														</c:forEach>
													</c:forEach>
												</c:if>
											</div>
											<div class="cb_bottom clearfix">
												<label>有效期至：${item.overtimeDate }</label>
												<c:if test="${item.dayOfOvertime > 0 && item.dayOfOvertime < 4 && item.useStatus == 0}">
													<span>${item.dayOfOvertime }天后过期</span>
												</c:if>
											</div>
										</div>
									</c:forEach>
								</c:if>
								
								<!-- <div class="cardBox overdue">
									<div class="cb_head">
										<span>1.0</span>%&nbsp;加息券
									</div>
									<div class="cb_middle">
										<p>适用期限：<span>90天以上产品</span></p>
										<p>适用金额：<span>单笔购买金额≥1000元</span></p>
										<p>奖励来源：<span>激活账户奖励</span></p>
									</div>
									<div class="cb_bottom clearfix">
										<label>有效期至：2017-07-23</label>
										<span></span>
									</div>
								</div> -->
							</div>
						</div>
						
						<div class="pages">
							<w:pager pageSize="${pageInfo.pagesize}" pageNo="${pageInfo.nowpage}" url=""
								recordCount="${pageInfo.total}" />
						</div>
						
						<div id="ruleboxRedpacket" class="rulebox">
							<span>规则说明：</span>
							<p>1、红包需根据相应的适用条件使用；</p>
							<p>2、红包不计入投资金额，项目满标后，将发放至账户余额中；</p>
							<p>3、一般情况下，红包每次仅限使用一个，不可累计及拆分使用；（标注可叠加的红包除外）</p>
							<p>4、红包不能提现，不得转赠他人，不得为他人付款；</p>
							<p>5、红包存在有效期，请在有效期内及时投资，过期失效；</p>
							<p>6、已使用的红包，所投资的项目发生流标情况，红包将返还至“我的卡券”中，可继续进行投资；</p>
							<p>7、最终解释权归领钱儿所有。</p>
						</div>
						
						<div id="ruleboxRate" class="rulebox" style="display:none;">
                            <span>规则说明：</span>
                            <p>1、加息券需根据相应的适用条件使用；</p>
                            <p>2、通过加息券获得的额外收益，项目到期后，投标本金和收益同时返还至账户可用余额中；</p>
                            <p>3、加息券不可转赠和叠加使用，一经使用不可撤回，每次购买仅限使用一张；</p>
                            <p>4、请于规定时间内使用加息券，逾期作废，不予补发；</p>
                            <p>5、最终解释权归领钱儿所有。</p>
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