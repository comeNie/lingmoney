<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />

<!-- 引用分站公共部分 -->
<jsp:include page="/common/substation.jsp"></jsp:include>

<!-- 引用分站特定js -->
<script type="text/javascript" src="/resource/js/substation/hangzhou.js"></script>
<script type="text/javascript" src="/resource/js/easypieChart/html5.js"></script>
<script type="text/javascript" src="/resource/js/easypieChart/excanvas.js"></script>
<script type="text/javascript" src="/resource/js/easypieChart/jquery.easy-pie-chart.js"></script>
<script type="text/javascript" src="/resource/js/users/login.js"></script>
<!-- 引用分站特定css -->
<link rel="stylesheet" href="/resource/css/substation/hangzhou.css">
<link rel="stylesheet" href="/resource/css/easypieChart/style.css">
<link rel="stylesheet" href="/resource/css/easypieChart/jquery.easy-pie-chart.css">
<!-- 导航【首页】样式 -->
<style type="text/css">
#nav01 {
	color: #ea5514;
	background: url(/resource/images/bg_nava.png) no-repeat center bottom;
}
</style>

</head>
<body style="position: relative; z-index: 1; background: #fafafa url('/resource/images/hangzhouImages/body_bg.jpg') center  1350px no-repeat;">
	<!-- 引用计算器-->
	<jsp:include page="/common/calculator.jsp"></jsp:include>

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<!-- 背景遮罩层 -->
	<div id="rz-box-bg"></div>

	<!-- 隐藏域存放用户session -->
	<input type="hidden" id="session" value="${CURRENT_USER }" />

	<!-- 首页大banner -->
	<div class="fullSlide">
		<div class="bd">
			<ul>
				<c:forEach items="${listInfoBigBanner }" var="item">
					<li _src="url(${item.path })"
						style="background: #81d5c6 center 0 no-repeat;"><a
						href="${item.url }"></a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="hd">
			<ul>
			</ul>
		</div>
	</div>

	<!-- 快速登录 -->
	<div class="register_box">
		<div class="register">
			<ul class="clearfix">
				<li><a href="javascript:void(0)">平台收益率</a></li>
				<li><a href="javascript:void(0)">用户登录</a></li>
			</ul>
			<ol>
				<li
					style="padding-top: 25px; *padding-top: 35px; padding-top: 35px; padding-left: 18px;">
					<img src="/resource/images/imgtop.gif" />
				</li>
				<li class="form">
					<p id="p_login_t" style="display: none">账户或密码错误！</p>
					<form onsubmit="return login()">
						<c:if test="${CURRENT_USER==null }">
							<table width="234" style="margin: 0 auto; display: block">
								<tr>
									<td colspan="2">
										<div class="form-div">
											<i style="background: url('/resource/images/bg_input.gif') no-repeat left top"></i>
											<input type="text" name="account" id="account" required
												placeholder="手机号码/用户名" onfocus="this.style.color='#434343'" />
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="form-div">
											<i style="background: url('/resource/images/bg_input.gif') no-repeat left bottom"></i>
											<input type="password" id="password" name="password"
												placeholder="登录密码" onfocus="this.style.color='#434343'" />
										</div>
									</td>
								</tr>

								<tr>
									<td align="right"><a href="/users/forgetpassword">忘记密码？</a></td>
								</tr>
								<tr>
									<td colspan="2" height="45px" align="center"><input
										type="image" src="/resource/images/button_zhuce.png"
										style="padding-left: 0; background: none; border: none" /></td>
								</tr>
								<tr>
									<td align="center" height="25px;" colspan="2">还没有领钱儿帐号？<a
										href="regist.html" style="color: #ed6e31">注册</a></td>
								</tr>
								<tr>
									<td colspan="2"
										style="padding-left: 35px; background: url('/resource/images/bao.png') no-repeat 20px center; font-size: 12px">领钱儿保障您的账户资金流转安全</td>
								</tr>
							</table>
						</c:if>
						<c:if test="${CURRENT_USER!=null }">
							<div class="denglu2" style="display: block">
								<div class="denglu2_div1">
									<p class="Cname_p">您当前登录的账户是：</p>
									<p class="Cname">${CURRENT_USER.nickName==null?CURRENT_USER.loginAccount:CURRENT_USER.nickName }</p>
								</div>
								<div style="padding-top: 10px; text-align: center">
									<a href="/myLingqian/show" style="display: block; margin: 0 auto; width: 214px; height: 34px;">
									<img src="/resource/images/enter.png" width="214px" height="34px"></a>
								</div>
							</div>
						</c:if>
					</form>
				</li>
			</ol>
		</div>
	</div>

	<!-- 活动 -->
	<div class="main2"></div>
	<!-- 特色 -->
	<!-- 新手专享 -->
	<div class="novice-box clearfix">
		<div class="novice_left">
			<a href="/regist.html"><img src="/resource/images/hangzhouImages/regist_img.jpg"></a>
		</div>
		<div class="novice_middle">
			<div class="tt">
				<div id="hzTopProductDiv" class="product-item">
					<div class="tipBox">新手专享</div>
					<div class="top">
						<div class="left">
							<i class="icon icon-elephant  left"></i>
							<span></span>
						</div>
						<div id="hzTopProTag" class="right" style="margin-right:55px;">
							<span style="border: 1px solid #ee8241;color: #ee8241"></span>
							<span style="border: 1px solid #7dbbe4; color: #7dbbe4"></span>
							<span style="border: 1px solid #79c064; color: #79c064"></span>
							<span style="border: 1px solid #845299; color: #845299"></span>
						</div>
					</div>
					<div class="bottom clearfix">
						<ul class="left clearfix">
							<li style="padding-left:10px;width:110px;">
								<p style="color:#ea560e;"><strong id="hzExpectRate" style="font-weight:bold"></strong></p>
								<span>预期年化收益率</span>
							<!-- 预期年化收益率的判断 -->
							</li>
							<li class="jiange"></li>
							<li  style="padding-left:10px;width:130px;">
								<p style="margin-bottom: 1px;">
										<strong id="hzTopProFtime" style="font-size:40px;"></strong>
									<!-- 期限的判断 -->											
								</p>
								<span style="padding-top:10px;">项目期限（天）</span>
							</li>
							<li class="jiange"></li>
							<li  style="padding-left:10px;width:170px;">
								<p style="margin-bottom: 56px;">
									<strong id="hzTopProMinMoney" style="float:left"></strong>
								</p>
								<span>起投金额（元）</span>
							</li>
						</ul>
						<div class="right">
							<div class="progress-box clearfix">
								<span id="hzTopProTips"></span>
							</div>									
							<form action="/product/showProduct.html">
								<input id="hzTopProCode" type="hidden" name="code">
								<button value="submit">立即购买</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="novice_right">
			<img src="/resource/images/hangzhouImages/noviceKnow.jpg" usemap="#planetmap">
			<map name="planetmap" id="planetmap">
				<area shape="rect" coords="0,0,221,74" href="freshman.html">
				<area shape="rect" coords="0,75,221,148" href="direction.html">
			</map>
		</div>
	</div>

	<!-- 主体部分 -->
	<div class="main-body" style="background: none">
		<div class="title-box" style="margin-top:20px">
			<i class="icon icon-products left"></i>
			<strong>明星产品</strong>
			<span>白领理财利器</span>
			<i class="icon icon-more"></i>
			<a href="/product/list/0_0_0_0_0_1.html" class="more">更多</a>
		</div>
		<div class="main-body-box clearfix">
			<!-- 循环X S -->
			<c:if test="${listx!=null }">
			<c:forEach items="${listx}" var="plist" varStatus="product">
				<c:if test="${product.index<=2}">
					<div class="baoImg bi_${product.index+1}" imgtag="${product.index+1}">
						<div class="bName">
							<p>${plist.pcName }</p>
							<span>${fn:substringAfter(plist.name, '-')}</span>
						</div>
					</div>
					<div class="baoBox" id="baoBox_${product.index+1}">
						<div class="box">
							<div class="boxTop">
							<div class="baoName">${plist.name }</div>
							<div class="label clearfix">
								<c:forEach items="${fn:split(plist.tags, ',')}" var="key" varStatus="status">
									<span
										<c:if test="${status.index==0 }">style="border: 1px solid #ee8241; color: #ee8241"</c:if>
										<c:if test="${status.index==1 }">style="border: 1px solid #7dbbe4; color: #7dbbe4"</c:if>
										<c:if test="${status.index==2 }">style="border: 1px solid #79c064; color: #79c064"</c:if>
										<c:if test="${status.index==3 }">style="border: 1px solid #845299; color: #845299"</c:if>>${key }
									</span>
								</c:forEach>
							</div>
						</div>
						<div class="boxBottom">
							<ul class="clearfix">
							 	<li style="width: 220px;">
							 		<!-- 预期年化收益率的判断  S--> 
									<c:choose>
										<c:when test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">
												<p><strong>无</strong></p>
											</c:if>
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==1}">
												<p>
													<strong>
													<fmt:formatNumber type="number" value="${plist.fYield*100 }"
														minFractionDigits="1" maxFractionDigits="1" />%
													</strong>
												</p>
											</c:if>
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
												<p>
													<strong style="font-weight:bold;font-size:27px">
														<fmt:formatNumber type="number" value="${plist.lYield*100}"
															minFractionDigits="1" maxFractionDigits="1" />-<fmt:formatNumber type="number" value="${plist.hYield*100}"
															minFractionDigits="1" maxFractionDigits="1" />%
													</strong>
												</p>
											</c:if>
										</c:when>
										<c:otherwise>
											<p><strong>净值</strong></p>
										</c:otherwise>
									</c:choose>
									<!-- 预期年化收益率的判断  E-->
							 		<span>预期年化收益率</span>
							 	</li>
							 	<li style="width: 160px;">
							 		<!-- 期限的判断 S -->
							 		<p>
									<c:choose>
										<c:when test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">非固定收益类</c:if>
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==1}">
												<c:if test="${plist.fTime==0}">
													无固定期限
												</c:if>
												<c:if test="${plist.fTime!=0}">
													${plist.fTime }
													<c:if test="${plist.unitTime==0}">天</c:if>
													<c:if test="${plist.unitTime==1}">周</c:if>
													<c:if test="${plist.unitTime==2}">个月</c:if>
													<c:if test="${plist.unitTime==3}">年</c:if>
												</c:if>
											</c:if>
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
												${plist.lTime }-${plist.hTime }
												<c:if test="${plist.unitTime==0}">天</c:if>
												<c:if test="${plist.unitTime==1}">周</c:if>
												<c:if test="${plist.unitTime==2}">个月</c:if>
												<c:if test="${plist.unitTime==3}">年</c:if>
											</c:if>
										</c:when>
										<c:otherwise>浮动收益类</c:otherwise>
									</c:choose>
									</p>
									<!-- 期限的判断 E-->				
							 		<span>项目期限</span>
							 	</li>
							 	<li style="width: 130px;">
							 		<!-- 规模的判断 -->
							 		<p>
										<c:if test="${plist.rule==0 || plist.rule==2 }">
											<fmt:formatNumber pattern="#,##0.00#" value="${plist.priorMoney/10000 }" />万
										</c:if> 
										<c:if test="${plist.rule==1 || plist.rule==3 }">无限制</c:if>
									</p>
									<!-- 规模的判断 -->
							 		<span>项目规模</span>
							 	</li>
							</ul>
						</div>
						<div class="prd_progress clearfix">
							<div class="prd_left">
								<p>起投金额：<span>${plist.minMoney }元起投，以${plist.increaMoney }元整数倍递增</span></p>
								<div class="jindu clearfix">
									<span>项目进度：</span>
				               	  	<c:choose>
										<c:when test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
											<div class="progress-box">
												<div class="progress" style="width:50%;"></div>
											</div>
											<span class="progressNum">50%</span>	
										</c:when>
										<c:when test="${empty plist.priorMoney || plist.reachMoney<1  || plist.priorMoney<1 || plist.reachMoney*1.00000001/plist.priorMoney<0.009999999}">
											<div class="progress-box">
												<div class="progress" style="width:0%;"></div>
											</div>
											<span class="progressNum">0%</span>	
										</c:when>
										<c:otherwise>
											<div class="progress-box">
												<div class="progress" style="width:<fmt:formatNumber value="${((plist.reachMoney*1.00000001)/(plist.priorMoney)-0.005)*100 }" maxFractionDigits="0"/>%;"></div>
											</div>
											<span class="progressNum"><fmt:formatNumber value="${((plist.reachMoney*1.00000001)/(plist.priorMoney)-0.005)*100 }" maxFractionDigits="0"/>%</span>	
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="prd_right">
								<a href="/product/showProduct.html?code=${plist.code }">立即购买</a>
							</div>
						</div>
						</div>
					</div>
				</c:if>
			</c:forEach>
			</c:if>
			<!-- 循环X E -->
		</div>
		
		<div class="main-body-table">
			<div class="title-box" style=" margin-bottom:0px; border-bottom:0;">
				<i class="icon icon-products left"></i>
				<strong>产品系列（${fn:length(listy) }）</strong>
				<span>市场有风险，投资需谨慎</span>
				<i class="icon icon-more"></i>
				<a href="/product/list/0_0_0_0_0_1.html" class="more">更多</a>
			</div>
			<table>
				<thead>
					<tr>
						<th>产品名称</th>
						<th>预期年化收益</th>
						<th>项目期限</th>
						<th>剩余可购</th>
						<th>进度</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<!-- 循环Y S -->
					<c:if test="${listy!=null }">
					<c:forEach items="${listy}" var="plist" varStatus="product">
						<c:if test="${product.index<5}">
							<tr>
								<td class="prdName">
									<c:choose>
										<c:when test="${plist.cityCode=='000' }">
											<img src="/resource/images/hangzhouImages/jing.png">
										</c:when>
										<c:otherwise>
											<img src="/resource/images/hangzhouImages/hang_icon.png">
										</c:otherwise>
									</c:choose>
									${plist.name }
								</td>
								<td class="yield">
									<!-- 预期年化收益率的判断  S--> 
									<c:choose>
										<c:when test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">
												无
											</c:if>
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==1}">
												<fmt:formatNumber type="number" value="${plist.fYield*100 }"
													minFractionDigits="1" maxFractionDigits="1" />%
											</c:if>
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
												<fmt:formatNumber type="number" value="${plist.lYield*100}"
													minFractionDigits="1" maxFractionDigits="1" />-<fmt:formatNumber type="number" value="${plist.hYield*100}"
													minFractionDigits="1" maxFractionDigits="1" />%
											</c:if>
										</c:when>
										<c:otherwise>
											净值
										</c:otherwise>
									</c:choose>
									<!-- 预期年化收益率的判断  E-->
								</td>
								<td class="limit">
									<!-- 期限的判断 S -->
									<c:choose>
										<c:when test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">非固定收益类</c:if>
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==1}">
												<c:if test="${plist.fTime==0}">
													无固定期限
												</c:if>
												<c:if test="${plist.fTime!=0}">
													${plist.fTime }
													<c:if test="${plist.unitTime==0}">天</c:if>
													<c:if test="${plist.unitTime==1}">周</c:if>
													<c:if test="${plist.unitTime==2}">个月</c:if>
													<c:if test="${plist.unitTime==3}">年</c:if>
												</c:if>
											</c:if>
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
												${plist.lTime }-${plist.hTime }
												<img src="resource/images/hangzhouImages/jia.png">
												<c:if test="${plist.unitTime==0}">天</c:if>
												<c:if test="${plist.unitTime==1}">周</c:if>
												<c:if test="${plist.unitTime==2}">个月</c:if>
												<c:if test="${plist.unitTime==3}">年</c:if>
											</c:if>
										</c:when>
										<c:otherwise>浮动收益类</c:otherwise>
									</c:choose>
									<!-- 期限的判断 E-->
								</td>
								<td class="remaining">
								<!-- 规模的判断 -->
						 		<p>
									<c:if test="${plist.rule==0 || plist.rule==2 }">
										<fmt:formatNumber pattern="#,##0.00#" value="${plist.priorMoney }" /><span>元</span>
									</c:if> 
									<c:if test="${plist.rule==1 || plist.rule==3 }">无限制</c:if>
								</p>
								<!-- 规模的判断 -->
								</td>
								<td class="prog">
			                   	  	<c:choose>
										<c:when test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
											<div class="percentage-light" data-percent="50"><span>50</span>%</div>
										</c:when>
										<c:when test="${empty plist.priorMoney || plist.reachMoney<1  || plist.priorMoney<1 || plist.reachMoney*1.00000001/plist.priorMoney<0.009999999}">
											<div class="percentage-light" data-percent="0"><span>0</span>%</div>
										</c:when>
										<c:otherwise>
											<div class="percentage-light" data-percent="<fmt:formatNumber value="${((plist.reachMoney*1.00000001)/(plist.priorMoney)-0.005)*100 }" maxFractionDigits="0"/>"><span><fmt:formatNumber value="${((plist.reachMoney*1.00000001)/(plist.priorMoney)-0.005)*100 }" maxFractionDigits="0"/></span>%</div>
										</c:otherwise>
									</c:choose>
								</td>
								<td class="operation"><a href="/product/showProduct.html?code=${plist.code }">立即购买</a></td>
							</tr>
						</c:if>
					</c:forEach>
					</c:if>
					<!-- 循环Y E -->
				</tbody>
			</table>
		</div>
		<div class="main-box-announce clearfix">
			<div class="ma_left">
				<div class="ml_top clearfix">
					<div class="ml_tab clearfix">
						<a onclick="infoNoticeClick(this)" class="active" href="javascript:void(0);"><span style="border-right: 1px solid #666">站内公告</span></a>
						<a onclick="infoMediaClick(this)" href="javascript:void(0);"><span>媒体资讯</span><img src="/resource/images/hangzhouImages/new.png"></a>
					</div>
					<div id="mediaMoreDiv" class="ml_more">
						<i class="icon icon-more"></i><a href="/infoNotice/list.html">更多</a>
					</div>
				</div>
				<div id="hzInfoNoticeDiv" class="ml_box">
					<!-- 站内公告循环 S -->
					 <c:forEach var="item" items="${listInfoNotice}" varStatus="status">
						<c:if test="${status.index<=4 }">
							<div class="mlb clearfix">
								<a target="_blank" href="/infoNotice/details.html?id=${item.id}">${item.title } </a>
								<span><fmt:formatDate value="${item.pDt }" pattern="yyyy/MM/dd" /> </span>
							</div>
						</c:if>
					 </c:forEach>
					 <!-- 站内公告循环 E -->
				</div>
				<div id="hzInfoMediaDiv" class="ml_box" style="display:none;">
					<!-- 媒体资讯循环 S -->
					<c:forEach items="${listInfoMedia }" var="item" varStatus="status">
						<c:if test="${status.index<=4 }">
							<div class="mlb clearfix">
								<a target="_blank" href="/infoMedia/details.html?id=${item.id}">${item.title } </a>
								<span><fmt:formatDate value="${item.pDt }" pattern="yyyy/MM/dd" /> </span>
							</div>
						</c:if>
					</c:forEach>
					<!-- 媒体资讯循环 E -->
				</div>
			</div>
			<div class="ma_right">
				<div class="mr_top clearfix">
					<span>你问我答</span>
					<div class="mr_more">
						<i class="icon icon-more"></i><a href="/usersAsk/list.html">更多</a>
					</div>
				</div>
				<div class="mr_box">
					<c:forEach items="${listUsersAsk }" var="item" varStatus="status">
						<c:if test="${status.index<=5 }">
							<a target="_blank" href="/usersAsk/details.html?id=${item.id }">问：${item.title }</a>
						</c:if>
					</c:forEach>   	
				</div>
			</div>
		</div>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	
	<script type="text/javascript">
		$(function(){
			/*city toggle*/
			$(".adress-box").click(function(){
			 $(".dropdown").toggle();
			});
			$(".dropdown li a").click(function(){
				$(".dropdown").css("display","none");
			});
			
			initPieChart();
		});


	var initPieChart = function() {
		$('.percentage').easyPieChart({
			animate: 1000
		});

		$('.percentage-light').easyPieChart({
			barColor: function(percent) {
				percent /= 100;
				return "rgb(0,157,235)";
			},
			trackColor: '#e6e6e6',
			scaleColor: false,
			lineCap: 'butt',
			lineWidth: 10,
			animate: 1000
		});
	};

</script>
</body>
</html>