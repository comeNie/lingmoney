﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>领钱儿 提供安全便捷理财项目平台</title>
<meta name="KEYWords" content="理财项目，安全理财平台，理财产品，个人理财产品，白领理财产品">
<meta name="DEscription"
	content="领钱儿(www.lingmoney.cn)-提供2015年最新理财项目，最安全理财产品，打造安全便捷的理财服务平台。">
<meta name="Author" content="领钱儿">
<link rel="stylesheet" href="/resource/css/index.css">
<link rel="stylesheet" type="text/css" href="/resource/css/style.css" media="screen" />
<link rel="icon" type="image/x-icon" href="/resource/ico.ico">
<link rel="shortcut icon" type="image/x-icon" href="/resource/ico.ico">
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/resource/js/ajax.js"></script>
<script type="text/javascript" src="/resource/js/kxbdSuperMarquee.js"></script>
<script type="text/javascript" src="/resource/js/index.js"></script>
<script type="text/javascript" src="/resource/js/yxMobileSlider.js"></script>
<script type="text/javascript" src="/resource/js/calculator.js"></script>
<script type="text/javascript" src="/resource/js/superslide.2.1.js"></script>

<script type="text/javascript" src="/resource/js/product/productList.js"></script>

</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<div class="mainbody clearfix">
		<div class="box">
			<c:if test="${userBuyState == 0 }">
				<c:if test="${!empty novice}">
					<div class="pageTip clearfix">
						<span>${novice.actTitle }</span>
						<a href="${novice.linkUrlPc }">查看详情</a>
					</div>
				</c:if>
			</c:if>
			<div class="sitemap">
				<a href="/index.html">领钱儿</a>&nbsp;>&nbsp;<span>产品列表</span>
			</div>

			<form action="/product/list.html" method="post" id="queryForm">
				<input type="hidden" name="isRecommend" id="isRecommend" value="${isRecommend}"> 
				<input type="hidden" name="cType" id="cType" value="${cType}"> 
				<input type="hidden" name="cRate" id="cRate" value="${cRate}"> 
				<input type="hidden" name="cCycle" id="cCycle" value="${cCycle}">
				<input type="hidden" name="minMenoy" id="minMenoy" value="${minMenoy}"> 
				<input type="hidden" name="pcStatus" id="pcStatus" value="${pcStatus}"> 
				<input type="hidden" name="key" id="key">
			</form>

			<form action="/product/showProduct.html" id="showForm">
				<input type="hidden" name="changTab" id="changTab"> <input
					type="hidden" name="code" id="code">
			</form>

			<div class="rightdiv1">
				<div class="search clearfix">
					<input type="text" id="keyword" placeholder="输入产品名称进行搜索"
						onfocus="this.style.color='#434343'" >
					<button id="bot" onclick="keyWordSub();"></button>
				</div>
				<div class="contents" style="top: 5px; width: 97%">
					<div id="dd1" style="display: block; padding-top: 15px"
						class="divContent">
						<ol>
							<li><span class="proleft">筛选条件：</span><a
								href="javascript:void(0)" id="isRecommend0"
								onclick="isRecommendSub(0);">全部</a><a href="javascript:void(0)"
								id="isRecommend1" onclick="isRecommendSub(1);">推荐产品</a></li>
							<li><span class="proleft">产品类型：</span><a
								href="javascript:void(0)" id="cType0" onclick="cTypeSub('0');">不限</a>
								<c:forEach var="item" items="${productCatgoryList}" varStatus="status">
								    <c:if test="${item.id != 22}">
									    <a href="javascript:void(0)" id="cType${item.id}" onclick="cTypeSub('${item.id}')">${item.name}</a>
								    </c:if>
								</c:forEach>
							<li><span class="proleft">预期收益：</span><a
								href="javascript:void(0)" id="cRate0" onclick="cRateSub(0)">不限</a><a
								href="javascript:void(0)" id="cRate1" onclick="cRateSub(1)">5%及以下</a><a
								id="cRate2" onclick="cRateSub(2)" href="javascript:void(0)">5%-7%</a><a
								href="javascript:void(0)" id="cRate3" onclick="cRateSub(3)">7%-9%</a><a
								href="javascript:void(0)" id="cRate4" onclick="cRateSub(4)">9%及以上</a></li>
							<li><span class="proleft">项目周期：</span><a
								href="javascript:void(0)" id="cCycle0" onclick="cCycleSub(0);">不限</a><a
								href="javascript:void(0)" id="cCycle1" onclick="cCycleSub(1);">小于30天</a><a
								href="javascript:void(0)" onclick="cCycleSub(2);" id="cCycle2">30-90天</a><a
								href="javascript:void(0)" id="cCycle3" onclick="cCycleSub(3);">91-180天</a><a
								href="javascript:void(0)" id="cCycle4" onclick="cCycleSub(4);">180天以上</a></li>
							<li><span class="proleft">起投金额：</span><a
								href="javascript:void(0)" id="minMenoy0"
								onclick="minMenoySub(0);">不限</a><a href="javascript:void(0)"
								id="minMenoy1" onclick="minMenoySub(1);">￥10~￥99</a><a
								href="javascript:void(0)" id="minMenoy2"
								onclick="minMenoySub(2);">￥100~￥999</a><a
								href="javascript:void(0)" id="minMenoy3"
								onclick="minMenoySub(3);">￥1000~￥9999</a><a
								href="javascript:void(0)" id="minMenoy4"
								onclick="minMenoySub(4);">￥10000及以上</a></li>
							<li><span class="proleft">项目状态：</span><a
								href="javascript:void(0)" id="pcStatus0"
								onclick="pcStatusSub(0);">不限</a><a href="javascript:void(0)"
								onclick="pcStatusSub(1);" id="pcStatus1">募集/开放中</a><a
								href="javascript:void(0)" id="pcStatus2"
								onclick="pcStatusSub(2);">运行中</a><a
								href="javascript:void(0)" onclick="pcStatusSub(4);"
								id="pcStatus4">已结束</a></li>
						</ol>
					</div>
				</div>
			</div>
			<div class="rightdiv22">
				<div id="div25" class="divContent">
					<c:forEach var="item" items="${gridPage.rows}" varStatus="status">
						<div class="pro-item clearfix">
							<div class="tou-left">
								<span class="name-prod">${item.pcName}</span>
								<c:if test="${item.productRecommend.recommend==1}">
									<img src="/resource/images/img_tuijian.png" class="img_tui" />
								</c:if>
								<h2>
								    ${item.name}
								    <!-- 如果产品为华兴的产品，显示角标 -->
								    <c:if test="${item.pType == 2}">
									    <img class="hxbank_logo" src="/resource/images/hxbank_btn_logo.png">
								    </c:if>
								 </h2>
								 <c:if test="${item.insuranceTrust == 1}">
									  <span class="style-insurance" style="position: absolute;left: 450px;top: 4px;">履约险承保</span>
								 </c:if>
								 
								 
								<!-- 固定收益 -->
								<dl class="clearfix">
									<c:if
										test="${fn:substring(item.code, item.code.length()-6, item.code.length()-5)==0}">

										<!--固定收益 -->
										<c:if
											test="${fn:substring(item.code, item.code.length()-5, item.code.length()-4)==1}">
											<dt>
											<c:if
														test="${item.addYield>0}">	
												<p class="fixed">
					                            						     
													<c:set var="arr" value="${item.fYield*100-item.addYield*100}" />
													<c:forEach items="${fn:split(arr,'.')}" var="addr" varStatus="stat">
														 <c:if test="${stat.index==0 }"><strong style="font-size: 38px">${addr}</strong>.</c:if>
														<c:if test="${stat.index==1 }"> ${fn:substring(addr,0,1)}</c:if>
													</c:forEach>%+ <c:set var="arr1" value="${item.addYield*100}" />
													<c:forEach items="${fn:split(arr1,'.')}" var="addr1" varStatus="stat">
														 <c:if test="${stat.index==0 }">${addr1}.</c:if>
														<c:if test="${stat.index==1 }"> ${fn:substring(addr1,0,1)}</c:if>
													</c:forEach>%
													
												</p>
												</c:if>
												<c:if
														test="${item.addYield=='0.0000'||item.addYield==null}">	
												<p class="fixed">
					                            						     
													<c:set var="arr" value="${item.fYield*100}" />
													<c:forEach items="${fn:split(arr,'.')}" var="addr" varStatus="stat">
														 <c:if test="${stat.index==0 }"><strong style="font-size: 38px">${addr}</strong>.</c:if>
														<c:if test="${stat.index==1 }"> ${fn:substring(addr,0,1)}</c:if>
													</c:forEach>%
													
												</p>
												</c:if>
												<p style="color: #666;">预期年化收益率</p>
											</dt>

										</c:if>
										<!--区间收益-->
										<c:if
											test="${fn:substring(item.code, item.code.length()-5, item.code.length()-4)==2}">
											<dt>
												<p class="fixed">
													<c:set var="arr2" value="${item.lYield*100}" />
													<c:forEach items="${fn:split(arr2,'.')}" var="addr2" varStatus="stat">
														 <c:if test="${stat.index==0 }"><strong style="font-size: 38px">${addr2}</strong>.</c:if>
														<c:if test="${stat.index==1 }"> ${fn:substring(addr2,0,1)}</c:if>
													</c:forEach>%-<c:set var="arr3" value="${item.hYield*100}" />
													<c:forEach items="${fn:split(arr3,'.')}" var="addr3" varStatus="stat">
														 <c:if test="${stat.index==0 }"><strong style="font-size: 38px">${addr3}</strong>.</c:if>
														<c:if test="${stat.index==1 }"> ${fn:substring(addr3,0,1)}</c:if>
													</c:forEach>%
												</p>
												<p style="color: #666;">预期年化收益率</p>
											</dt>

										</c:if>
										<dd>
											<!-- 理财规则，0:金额限制，1:时间限制，2:金额时间限制，3:无限制 -->

											<ul class="ul-top clearfix" style="padding-top:20px;">
												<c:if test="${item.rule==2||item.rule==0}">
													<li class="li01">项目规模：<span class="color"><fmt:formatNumber  pattern="#,##0" value="${item.priorMoney}" /></span>元
													</li>
												</c:if>
												<c:if test="${item.rule==1||item.rule==3}">
													<li class="li01">项目规模：<span class="color" style="font-family:'Arial';font-weight:bold;font-size:30px">--</span>
													</li>
												</c:if>

												<c:if test="${fn:substring(item.code, item.code.length()-5, item.code.length()-4)==1}">
													<li class="li02">项目期限： 
														<c:if test="${item.fTime==0}">
															<span class="color" style="font-family:'Arial';font-weight:bold;font-size:30px">--</span>
														</c:if> 
														<c:if test="${item.fTime!=0}">
															<span class="color">${item.fTime}</span>
															<c:if test="${item.unitTime==0}"> 天</c:if>
															<c:if test="${item.unitTime==1}"> 周</c:if>
															<c:if test="${item.unitTime==2}"> 个月</c:if>
														</c:if>
													</li>
												</c:if>

												<c:if test="${fn:substring(item.code, item.code.length()-5, item.code.length()-4)==2}">
													<li class="li02">项目期限： <span class="color" style="position:relative;z-index:1;padding-right:20px">
															${item.lTime} - ${item.hTime}
															<img src="/resource/images/jia.png" style="position:absolute;z-index:2;right:5px;top:0"/> </span> 
															<c:if test="${item.unitTime==0}"> 天</c:if> 
													  		<c:if test="${item.unitTime==1}"> 周</c:if> 
													  		<c:if test="${item.unitTime==2}"> 个月</c:if>
													</li>
												</c:if>
											</ul>
									
											<c:if test="${item.rule==0||item.rule==3}">
												<ul class="ul-top clearfix">
													<li class="li03">截止时间：不限日期，随时可购买</li>
													<li class="li04">起投金额：<strong><fmt:formatNumber  pattern="#,##0" value="${item.minMoney}"/></strong>元</li>
												</ul>
											</c:if>
		
											<c:if test="${item.rule==1||item.rule==2}">
												<ul class="ul-top clearfix">
													<li class="li03">截止时间： <fmt:formatDate
															value="${item.edDt}" pattern="yyyy-MM-dd HH:mm" />
													</li>
													<li class="li04">起投金额：<strong><fmt:formatNumber  pattern="#,##0" value="${item.minMoney}"/></strong>元</li>
												</ul>
											</c:if>
										</dd>
									</c:if>
									<!-- 浮动收益-->
									<c:if test="${fn:substring(item.code, item.code.length()-6, item.code.length()-5)==1}">

										<dl class="clearfix">
											<dt>
												<p class="jingzhi">
													<strong>净值</strong>
													<a href="javascript:void(0)">
														<img src="/resource/images/img_jingzhi.png" />
													</a>
												</p>
												<p style="color: #666;">预期年化收益率</p>
											</dt>
											<dd>
												<!-- 理财规则，0:金额限制，1:时间限制，2:金额时间限制，3:无限制 -->
												<ul class="ul-top clearfix">
													<c:if test="${item.rule==2||item.rule==0}">
														<li class="li01">项目规模：<span class="color" ><fmt:formatNumber  pattern="#,##0" value="${item.priorMoney}" /></span>元
														</li>
													</c:if>
													<c:if test="${item.rule==1||item.rule==3}">
														<li class="li01">项目规模：<span class="color" style="font-family:'Arial';font-weight:bold;font-size:30px">--</span>
														</li>
													</c:if>
													<li class="li02">项目期限： <span class="color wuxian" style="font-family:'Arial';font-weight:bold;font-size:30px">--</span>
													</li>
												</ul>
												<c:if test="${item.rule==0||item.rule==3}">
													<ul class="ul-top clearfix">
														<li class="li03">截止时间：不限日期，随时可购买</li>
														<li class="li04">起投金额：<strong><fmt:formatNumber  pattern="#,##0.00#" value="${item.minMoney}"/></strong>元</li>
													</ul>
												</c:if>

												<c:if test="${item.rule==1||item.rule==2}">
													<ul class="ul-top clearfix">
														<li class="li03">截止时间： <fmt:formatDate value="${item.edDt}" pattern="yyyy-MM-dd hh:mm" />
														</li>
														<li class="li04">起投金额：<strong><fmt:formatNumber  pattern="#,##0.00#" value="${item.minMoney}"/></strong>元</li>
													</ul>
												</c:if>
											</dd>
										</dl>
									</c:if>
								</dl>

							</div>

							<div class="tou-right"   
							  <c:if test="${item.rule!=2}">style="padding-top:41px;"</c:if>>
							   <c:if test="${item.rule==2}">
								   <p>
								      <c:choose>
									      <c:when test="${item.status==1}">
									      		剩余可购：<strong><fmt:formatNumber  pattern="#,##0" value="${item.priorMoney-item.reachMoney}"/></strong>元
									      </c:when>  
									      <c:otherwise>
									     		<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>
									      </c:otherwise>
								      </c:choose>
									</p>
							   </c:if>
								
								<!-- 	1:项目筹集期，2项目运行中,3项目汇款中,4项目结束，5项目作废 -->
								<c:if test="${item.status==1}">
									  <c:if test="${item.fTime==0|| empty item.fTime}">
									  	<a href="javascript:void(0)" onclick="showProduct('${item.code}');" class="buy">开放中</a>
									  </c:if>
									  <c:if test="${ not empty item.fTime && item.fTime!=0 }">
									  	<a href="javascript:void(0)" onclick="showProduct('${item.code}');" class="buy">募集中</a>
									  </c:if>
										
								</c:if>
								<c:if test="${item.status==2}">
										<a href="javascript:void(0)" class="repay">运行中</a>
								</c:if>
								<c:if test="${item.status==3}">
									<a href="javascript:void(0)" class="repay">还款中</a>
								</c:if>
								<c:if test="${item.status==4}">
									<a href="javascript:void(0)"  class="over">项目结束</a>
								</c:if>
								<c:set var="string2" value="${fn:split(item.tags, ',')}" />
								<ol class="clearfix">
									<c:forEach items="${string2}" var="s" varStatus="status">
										<li>${s}</li>
									</c:forEach>
								</ol>
							</div>
						</div>
					</c:forEach>

					<div class="pages">
						<w:pager pageSize="${pageSize}" pageNo="${pageNo}" url="" recordCount="${gridPage.total}" falg='0' />
					</div>
				</div>
			</div>
		</div>
		<div style="clear: both"></div>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	<c:if test="${pageNo!=1}">

	<script  type="text/javascript">	
		$(function(){
			var h = $(document).height()- $("#div25").height()-$("#footer").height(); //350应该是页面底部的高度，可以适当在多一点
		 	$(document).scrollTop(h);
			
		});
		
	</script>
	
</c:if>

</body>
</html>