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
		$("#accountFlowTab").addClass("hover");
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
		
		var type = '<%=request.getAttribute("type")%>';
		if (type == '-1' || type=='null') {
			$("#quanb").addClass("hover");
		} else if (type == '0') {
			$("#quanb0").addClass("hover");
		} else if (type == '1') {
			$("#quanb1").addClass("hover");
		} else if (type == '2') {
			$("#quanb2").addClass("hover");
		} else if (type == '3') {
            $("#quanb3").addClass("hover");
        } else if (type == '4') {
            $("#quanb4").addClass("hover");
        } 
        

		var timeType = '<%=request.getAttribute("timeType")%>';
		if (timeType == '7') {
			$("#timeType7").addClass("hover");
		} else if (timeType == '30') {
			$("#timeType30").addClass("hover");
		} else if (timeType == '90') {
			$("#timeType90").addClass("hover");
		} else if (timeType == '365') {
			$("#timeType365").addClass("hover");
		} else {
			$("#timeType7").removeClass("hover");
			$("#timeType30").removeClass("hover");
			$("#timeType90").removeClass("hover");
			$("#timeType365").removeClass("hover");
		}
	});

	function copeType(val) {
		if (val != null) {
			$("#type").val(val);
		}
		sub();
	}

	function copeTimeType(val) {
		if (val != null) {
			$("#timeType").val(val);
		}
		sub();
	}

	function sub() {
		$("#queryForm").submit();
	}
</script>
<script>
		var changTab = '<%=request.getAttribute("changTab")%>';
		if (changTab == 1 || changTab == "null") {
			changeTab4(1);
		} else if (changTab == 2) {
			changeTab4(2);
		}
		function changeTab4(index) {
			$("#changTab").val(index);
			/* 未知作用，先注释，待确认 
			for (var i = 1; i <= 2; i++) {
				document.getElementById("fun_" + i).className = "normal";
				document.getElementById("fun_" + index).className = "selected";
				document.getElementById("dd" + i).style.display = "none";
			}
			document.getElementById("dd" + index).style.display = "block"; */
		}
		$(".rz-button,.rz-close").click(function() {
			$("#rz-box-bg").hide();
			$("#div_cz").hide();
		});
	</script>
</head>
<body>
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
				<input type="hidden" name="type" id="type" value="${type}">
				<input type="hidden" name="timeType" id="timeType" value="${timeType}">
				<input type="hidden" name="beginTime" id="beginTime" value="${beginTime}"> 
				<input type="hidden" name="endTime" id="endTime" value="${endTime}"> 
				<input type="hidden" name="changTab" id="changTab" value="${changTab}">
			</form>

			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">账户流水</div>
					<div class="zonglan clearfix" style="min-height: 600px;">
						<dl class="dl02 clearfix" style="padding-top: 20px;">
							<dt>资金类型:</dt>
							<dd>
								<a href="javascript:void(0)" id="quanb" onclick="copeType(-1)">全部</a>
							</dd>
							<dd>
								<a href="javascript:void(0)" id="quanb0" onclick="copeType(0)">充值</a>
							</dd>
							<dd>
								<a href="javascript:void(1)" id="quanb1" onclick="copeType(1)">提现</a>
							</dd>
							<dd>
								<a href="javascript:void(2)" id="quanb2" onclick="copeType(2)">理财</a>
							</dd>
							<dd>
                                <a href="javascript:void(2)" id="quanb3" onclick="copeType(3)">赎回</a>
                            </dd>
                            <dd>
                                <a href="javascript:void(2)" id="quanb4" onclick="copeType(4)">奖励</a>
                            </dd>
						</dl>
						<dl class="dl02 clearfix">
							<dt>时间范围:</dt>
							<dd>
								<input readonly="readonly" class="laydate-icon" value="${beginTime}" id="start"
									style="width: 100px; height: 15px; margin-right: 10px;" />--<input
									readonly="readonly" class="laydate-icon" value="${endTime}"
									id="end" style="width: 100px; height: 15px; margin-right: 10px;" />
							</dd>
							<dd>
								<a href="javascript:void(0)" id="timeType7"
									onclick="copeTimeType(7)">最近7天</a>
							</dd>
							<dd>
								<a href="javascript:void(0)" id="timeType30"
									onclick="copeTimeType(30)">最近30天</a>
							</dd>
							<dd>
								<a href="javascript:void(0)" id="timeType90"
									onclick="copeTimeType(90)">最近90天</a>
							</dd>
							<dd>
								<a href="javascript:void(0)" id="timeType365"
									onclick="copeTimeType(365)">最近一年</a>
							</dd>
						</dl>
						<table class="table02" width="100%" cellpadding="0" cellspacing="0" border="0" style="background: none;">
							<tr style="height: 42px; background: #f0efef;">
								<th>类型</th>
								<th>金额</th>
								<th>时间</th>
								<th>状态</th>
								<c:if test="${type==3}">
									<th>兑付账户</th>
								</c:if>
							</tr>
							<c:forEach items="${gridPage.rows}" var="item">
								<tr>
									<td>
										<c:choose>
											<c:when test="${item.type==0 }">华兴E账户充值</c:when>
											<c:when test="${item.type==1 }">华兴E账户提现</c:when>
											<c:when test="${item.type==2 && item.pType==0}">京东投资</c:when>
											<c:when test="${item.type==2 && item.pType==2}">华兴E账户投资</c:when>
											<c:when test="${item.type==3 && item.pType==0}">京东赎回</c:when>
											<c:when test="${item.type==3 && item.pType==2}">华兴E账户赎回</c:when>
											<c:when test="${item.type==4 }">补费</c:when>
											<c:when test="${item.type==5 }">还款</c:when>
											<c:when test="${item.type==6 }">放款</c:when>
										</c:choose>
									</td>
									<td>
									   ${item.money }
										<!-- 
										<c:choose>
											<c:when test="${item.type==0 }">+${item.money }</c:when>
											<c:when test="${item.type==1 }">-${item.money }</c:when>
											<c:when test="${item.type==2 }">-${item.money }</c:when>
											<c:when test="${item.type==3 }">+${item.money }</c:when>
											<c:when test="${item.type==4 }">+${item.money }</c:when>
											<c:when test="${item.type==5 }">-${item.money }</c:when>
											<c:when test="${item.type==6 }">+${item.money }</c:when>
										</c:choose>
										 -->
									</td>
									<td><fmt:formatDate value="${item.operateTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									
									<c:choose>
										<c:when test="${item.status==0 }"><td style="color:#333333;">处理中</td></c:when>
										<c:when test="${item.status==1 }"><td style="color:#00be2d;">已完成</td></c:when>
										<c:when test="${item.status==2 }"><td style="color:#e60021;">处理失败</td></c:when>
										<c:when test="${item.status==3 }"><td style="color:#999999;">已撤标</td></c:when>
										<c:when test="${item.status==4 }"><td style="color:#666666;">已流标</td></c:when>
										<c:when test="${item.status==5 }"><td style="color:#1979fd;">冻结中</td></c:when>
										<c:when test="${item.status==6 }"><td style="color:#1979fd;">取消支付</td></c:when>
										<c:when test="${item.status==7 }"><td style="color:#1979fd;">支付中</td></c:when>
										<c:when test="${item.status==8 }"><td style="color:#1979fd;">待支付</td></c:when>
										<c:when test="${item.status==15 }"><td style="color:#1979fd;">赎回中</td></c:when>
									</c:choose>
									
									<c:if test="${type==3}">
										<td>
											${item.cardPan }
										</td>
									</c:if>
								</tr>

							</c:forEach>
							
						</table>
						<div class="pages">
							<w:pager pageSize="${pageSize}" pageNo="${pageNo}" url=""
								recordCount="${gridPage.total}" />
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
	<script type="text/javascript">
		
		
		var start = {
				elem : '#start',
				
				format : 'YYYY-MM-DD',
				//min: laydate.now(), //设定最小日期为当前日期
				max : laydate.now(), //最大日期
				//istime: true,
				istoday : false,
				choose : function(datas) {
					end.min = datas; //开始日选好后，重置结束日的最小日期
					end.start = datas //将结束日的初始值设定为开始日
				}
			};
			var end = {
				elem : '#end',
				format : 'YYYY-MM-DD',
				min : start,
				max : laydate.now(),
				//istime: true,
				istoday : false,
				choose : function(datas) {

					var beginTime = $("#start").val();
					var endTime = $("#end").val();

					if (beginTime != null && endTime != null && (endTime >= beginTime)) {
						$("#beginTime").val(beginTime);
						$("#endTime").val(endTime);
						$("#timeType").val("");
						sub();
					}

					start.max = datas; //结束日选好后，重置开始日的最大日期
				}
			};
			laydate(start);
			laydate(end);
	</script>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>