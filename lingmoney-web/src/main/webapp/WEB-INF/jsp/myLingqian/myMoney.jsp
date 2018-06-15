<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="/resource/js/echarts.js"></script>
<script type="text/javascript" src="/resource/laydate/laydate.js"></script>
<link rel="stylesheet" type="text/css" href="/resource/css/ymcalendar.css">
<script type="text/javascript" src="/resource/js/jquery.ymcalendar.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#myMoneyTab").addClass("hover");
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
		ok(0);
	});

	// 配置路径  
	require.config({
		paths : {
			echarts : '/resource/js'
		}
	});

	function ok(type) {
		var date_time = "";
		if(type==1){
			date_time = $("#hello").val();
		}
		require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line' ],
				function(ec) {
					var chart = document.getElementById('chart');
					var echart = ec.init(chart);

					echart.showLoading({
						text : '正在努力加载中...'
					});

					var categories = [];
					var values = [];

					// 同步执行  
					$.ajaxSettings.async = false;
					var noCache = Date();
					// 加载数据  
					$.getJSON('/myFinancial/getMyMoneyData.html?date_time=' + date_time,{"noCache":noCache},
							function(json) {
								categories = json.categories;
								values = json.values;
							});

					var option = {
						tooltip : {
							show : true 
						},
						legend : {
							data : [ '收益' ]
						},
						xAxis : [ {
							type : 'category',
							data : categories,
							axisLabel:{
								//X轴刻度配置
								interval:0,
			                    rotate:45,
			                    margin:2
								}
						} ],
						yAxis : [ {
							type : 'value'
						} ],
						series : [ {
							'name' : '收益',
							'type' : 'line',
							'data' : values
						} ]
					};

					echart.setOption(option);
					echart.hideLoading();
				});
	}
	
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

			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">我的资产</div>
					<div class="zonglan clearfix">
						<div class="quxian">
							<!-- <h3 style="font-weight: normal">我的收益</h3> -->
							<div class="list-money">
								<ul class="clearfix">
									<li>理财总金额：<span class="color "><fmt:formatNumber type="currency" value="${totalFinance}" /></span>
									</li>
									<li>累计收益（已清算）：<span class="color "><fmt:formatNumber type="currency" value="${income}" /></span>
									</li>
								</ul>
							</div>

							<div class="div-time">
								请选择年月： <input id="hello" class="ymcalendar" readonly="readonly"/><span></span> <a
									href="javascript:void(0)" onclick="ok(1)" class="laydate-ok">确定</a>
							</div>
							<div id="chart"
								style="margin: 0 auto; width: 810px; height: 400px;"></div>

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