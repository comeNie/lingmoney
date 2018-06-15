<%@page import="java.util.ResourceBundle"%>﻿
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<!DOCTYPE html>
<html lang="zh"> 
<head>
<meta name="KEYWords" content="<%=request.getAttribute("pname")%>">
<meta name="DEscription"
	content="${fn:escapeXml(fn:substring(description, 0, fn:length(description)>100 ? 100:fn:length(description)))}" />
<meta name="Author" content="领钱儿">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<title>领钱儿 提供安全便捷理财项目平台-<%=request.getAttribute("pname")%></title>
<link rel="stylesheet" href="/resource/css/index.css">
<link rel="stylesheet" type="text/css" href="/resource/css/style.css" media="screen" />
<link rel="icon" type="image/x-icon" href="/resource/ico.ico">
<link rel="shortcut icon" type="image/x-icon" href="/resource/ico.ico">
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/resource/js/ajax.js"></script>
<script type="text/javascript" src="/resource/js/kxbdSuperMarquee.js"></script>
<jsp:include page="/common/kefu.jsp"></jsp:include>
<script type="text/javascript" src="/resource/js/index.js"></script>
<script type="text/javascript" src="/resource/js/yxMobileSlider.js"></script>
<script type="text/javascript" src="/resource/js/calculator.js"></script>
<script type="text/javascript" src="/resource/js/superslide.2.1.js"></script>
<script type="text/javascript" src="/resource/laydate/laydate.js"></script>
<script type="text/javascript" src="/resource/js/lingbaoMarketJs/public.js"></script>

<link rel="stylesheet" href="/resource/css/product/productDetail.css">
<script type="text/javascript" src="/resource/js/product/productDetail.js"></script>
<style type="text/css">
.t-box{display:none;}
</style>
</head>
<body>
	<input type="hidden" id="buyLimit" value="${product.buyLimit}" />
	<input type="hidden" id="isLogin" value="${isLogin }">
	<input type="hidden" id="userCertification" value="${CURRENT_USER.certification }">
	<input type="hidden" id="userBank" value="${CURRENT_USER.bank }">
	<input type="hidden" id="pType" value="${product.pType}">
	
	<!-- 计算器弹框 -->
	<jsp:include page="/common/calculator.jsp"></jsp:include>

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<form action="/accountFlow/listQX" id="queryForm" method="post">
		<input type="hidden" name="changTab" id="changTab" value="${changTab}">
	</form>

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
				<a href="/index">领钱儿</a>&nbsp;>&nbsp;<a
					href="/product/list/0_0_0_0_0_1">产品列表</a>&nbsp;>&nbsp;${product.name}&nbsp;>&nbsp;<span>项目详情</span>
			</div>
			<div class="detailtop">
				<div class="name">
					<strong>${product.pcName}</strong>
				</div>
				<div class="clearfix">
					<div class="proDetialLeft">

						<div class="detail_proname clearfix">
							<c:if test="${product.productRecommend.recommend==1}">
								<img src="/resource/images/img_tuijian2.png" />
							</c:if>
							<h1>${product.name}
							     <c:if test="${product.pType == 2}">
								     <img class="hxbank_logo" src="/resource/images/hxbank_btn_logo.png">
							     </c:if>
							</h1>
							
							    <c:if test="${product.insuranceTrust == 1}">
									 <span class="style-insurance" style="margin-top:34px;">履约险承保</span>
								 </c:if>
							
							
							
							<div class="cleardiv"></div>
						</div>

						<ul class="top clearfix">
							<li class="clearfix">
								<img src="/resource/images/img_guimo.png" />

								<div>
									<p>项目规模</p>

									<c:if test="${product.rule==2||product.rule==0}">
										<strong><fmt:formatNumber pattern="#,##0"
												value="${product.priorMoney}" /></strong>

									</c:if>
									<c:if test="${product.rule==1||product.rule==3}">
										<strong>--</strong>
									</c:if>


								</div></li>
							<li class="clearfix">
								<img src="/resource/images/img_lilv.png" />
								<div>								
									<p>预期年化收益率</p>
									
									<span id="yield" style="padding: 0; float: none">
									<c:if test="${fn:substring(product.code, product.code.length()-5, product.code.length()-4)==1}">			
									      <span id="product-addYield" style=" font-family: 'myImpact'; font-size:18px; padding-top:6px;"></span>
									     <span  id="product-fYield"  style="font-family: 'myImpact'; font-size:26px; padding-top: 0px; padding-left: 9px;"></span> 
									     
									       					
										
									</c:if> 
									<c:if test="${fn:substring(product.code, product.code.length()-5, product.code.length()-4)==2}">
										<strong>
											<fmt:formatNumber type="percent" value="${product.lYield}" minFractionDigits="1"
												maxFractionDigits="3" />-<fmt:formatNumber type="percent"
												value="${product.hYield}" minFractionDigits="1" maxFractionDigits="3" />
										</strong>
									</c:if>
									</span>
								</div></li>
							<c:if test="${fn:substring(product.code, product.code.length()-5, product.code.length()-4)==1}">
								<li class="clearfix">
									<img src="/resource/images/img_qixian.png" />
									<div>
										<p>项目期限</p>
										<c:if test="${product.fTime==0}">
											<strong>--</strong>
										</c:if>
										<c:if test="${product.fTime!=0}">
											<span id="ftime" style="padding: 0; float: none"> 
												<strong>${product.fTime}
													<c:if test="${product.unitTime==0}">天</c:if> 
													<c:if test="${product.unitTime==1}">周</c:if> 
													<c:if test="${product.unitTime==2}">个月</c:if>
												</strong>
											</span>
										</c:if>
									</div>
								</li>
							</c:if>

							<c:if test="${fn:substring(product.code, product.code.length()-5, product.code.length()-4)==2}">
								<li class="clearfix">
									<img src="/resource/images/img_qixian.png" />
									<div>
										<p>项目期限</p>
										<span id="ftime" style="padding: 0; float: none"> 
											<strong style="position: relative; z-index: 1; padding-right: 20px">
													${product.lTime}-${product.hTime}
												<img src="/resource/images/jia.png" style="position: absolute; z-index: 2; right: 5px; top: 0; width: 12px; height: 12px;" />
											</strong>
											<strong style="font-size: 22px">
												<c:if test="${product.unitTime==0}">天</c:if> 
												<c:if test="${product.unitTime==1}">周</c:if> 
												<c:if test="${product.unitTime==2}">个月</c:if>
											</strong>
										</span>
									</div>
								</li>
							</c:if>
						</ul>
						<div class="dcenter">
							<c:if test="${product.fixInvest!=0}">
								<P>
									<c:if test="${product.fixInvest==1}">
			                			定投方式：<span> 周周投</span>
									</c:if>
									<c:if test="${product.fixInvest==2}">
			                			 定投方式：<span> 半月投</span>
									</c:if>
									<c:if test="${product.fixInvest==3}">
			                 			 定投方式：<span> 月月投</span>
									</c:if>
									定投期限：<span>${product.fTime }
												<c:if test="${product.unitTime==0}">天</c:if> 
												<c:if test="${product.unitTime==1}">周</c:if> 
												<c:if test="${product.unitTime==2}">个月</c:if> 
												<c:if test="${product.unitTime==3}">年</c:if>
											</span>
								</P>
							</c:if>
							<input type="hidden" id="minMoney" value="${product.minMoney}" />
							<input type="hidden" id="increaMoney" value="${product.increaMoney}" />
							<p>
								<c:if test="${product.increaMoney==0}">
										起投金额：<span>${product.minMoney}元起投</span>
								</c:if>
								<c:if test="${product.increaMoney!=0}">
										起投金额：<span>${product.minMoney}元起投，以${product.increaMoney}元整数倍递增</span>
								</c:if>							
							</p>
							<%
								Date nowDate = new Date();
								request.setAttribute("nowDate", nowDate);
							%>
							<c:choose>
								<c:when test="${(product.rule==1||product.rule==2)&&(product.edDt>=nowDate)&&(product.stDt<=nowDate)}">
								<p >
									<span style="color: #000">到期时间：</span><span id="timerss"></span>
								</p>
								</c:when >
								<c:when test="${(product.rule==1||product.rule==2)&&(product.stDt>nowDate)}">
								<p >
									<span style="color: #000">到期时间：</span><span id="timerss"></span>
								</p>
								</c:when>
							</c:choose>
							<c:if test="${product.rule==2}">
								<div class="div-progress">
									<span class="name-pro">项目进度</span>
									<dl class="barbox">
										<dd class="barline">
											<input type="hidden" id="priorMoney" value="${product.priorMoney}" /> 
											<input type="hidden" id="reachMoney" value="${product.reachMoney}" />
											<div w="" style="width: 0px;" class="charts"></div>
										</dd>
									</dl>
									<span class="percent" id="perSp"></span>
								</div>
							</c:if>
							<c:choose>
								<c:when test="${(product.rule==1||product.rule==2)&&(product.edDt>=nowDate)&&(product.stDt<=nowDate)}">
									<p >
										<span style="color: #000">截止时间：</span><span id="timer"></span>
									</p>
								</c:when >
								<c:when test="${(product.rule==1||product.rule==2)&&(product.stDt>nowDate)}">
									<p >
										<span style="color: #000">距离开始时间：</span><span id="timers"></span>
									</p>
								</c:when>
								
								<c:when test="${(product.rule==1||product.rule==2)&&(product.edDt<nowDate)}">
									<p >
										<span style="color: #000">截止时间：</span><span><fmt:formatDate value="${product.edDt }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
									</p>
								</c:when>
							</c:choose>
							
							<input type="hidden" value="${product.edDt.getTime()}" id="edDt">
							<input type="hidden" value="${product.stDt.getTime()}" id="stDt">
							
							<div class="sbottom sbottoms clearfix"
								style="font-size: 12px; margin-top: 10px;">

								<c:set var="string2" value="${fn:split(product.tags, ',')}" />

								<c:forEach items="${string2}" var="s" varStatus="status">
									<c:if test="${status.count==1}">
										<span style="border: 1px solid #ee8241; color: #ee8241">${s}</span>
									</c:if>
									<c:if test="${status.count==2}">
										<span style="border: 1px solid #7dbbe4; color: #7dbbe4">${s}</span>
									</c:if>
									<c:if test="${status.count==3}">
										<span style="border: 1px solid #79c064; color: #79c064">${s}</span>
									</c:if>
									<c:if test="${status.count==4}">
										<span style="border: 1px solid #845299; color: #845299">${s}</span>
									</c:if>
									<c:if test="${status.count>4}">
										<span style="border: 1px solid #845299; color: #845299">${s}</span>
									</c:if>
								</c:forEach>
							</div>
							<c:if test="${product.insuranceTrust == 1}">
							 <p><a href="/huanongInsAgeIndex?pId=${product.id}" style="color:#69AFFF;" target="_blank">保险协议详情></a></p>
							 </c:if>
							
                           
							<c:if test="${product.status==1}">
								<c:if test="${product.fTime==0 || empty product.fTime}">
									<img src="/resource/images/img_open.png" />
								</c:if>
								<c:if test="${product.fTime!=0&&not empty product.fTime}">
									<img src="/resource/images/img_going.png" />
								</c:if>
							</c:if>
							<c:if test="${product.status==2}">
								<img src="/resource/images/img_over.png" />
							</c:if>
							<c:if test="${product.status==3}">
								<img src="/resource/images/img_repay.png" />
							</c:if>
							<c:if test="${product.status==4}">
								<img src="/resource/images/end-15.png" />
							</c:if>

							<div class="div_statement">
								<a href="javascript:void(0)" id="a_protocal_2">风险提示</a> <a
									href="javascript:void(0)" id="a_protocal_3">免责声明</a>
									<c:if test="${not empty product.introduction && product.introduction!='' }">
										<a href="javascript:void(0)" id="a_protocal_4">抵押物信息</a>
									</c:if>
							</div>

						</div>

					</div>
					<div class="proDetialRight">
						<ul class="top clearfix">
							<li class="clearfix" style="padding: 36px 0 0 0">
								<div>
									<p style="margin: 0; font-size: 18px; color: #fff">已参与理财</p>
									<p style="padding-top: 10px; font-size: 20px;">
										<strong style="color: #ea5513; font-size: 46px;">${totalSize}</strong>人次
									</p>
								</div>
							</li>
						</ul>
						<div style="clear: both; margin-top: 10px;color:#000">
							<c:if test="${product.rule==2||product.rule==0}">
								<p style="color: #000; margin-bottom: 10px;">
								<input type="hidden" value="${product.priorMoney-product.reachMoney}" id="toumoney" /> 
									剩余可购：<span id="toumoney1">￥<fmt:formatNumber pattern="#,##0" value="${product.priorMoney-product.reachMoney}" /></span>
								</p>
							</c:if>
							<c:if test="${product.rule==1||product.rule==3}">
								<p style="color: #000; margin-bottom: 10px;">
									剩余可购：<span id="toumoney">不限</span>
								</p>
							</c:if>
							<p>起投金额：<span id="startMoney">￥${product.minMoney}</span></p>
							<c:if test="${product.pType == 2}">
								<p>可用余额：
									<c:choose>
										<c:when test="${isLogin }">
											<span>￥${balance }</span><a style="color:#0483ef; margin: 0 5px;" onclick="goToRecharge();" href="javascript:void(0);">充值</a>
										</c:when>
										<c:otherwise>
											<a style="color:#0483ef;" href="/login.html">登录</a>后可见
										</c:otherwise>
									</c:choose>
								</p>
							</c:if>
							<ul class="counts clearfix">
								<p>购买金额：</p>
								<li style="border: 1px solid #727171">
									<input type="number" id="buyMoney" name="buyMoney" required="required"  placeholder="￥${product.minMoney} 输入金额"
									min="${product.minMoney}" step="${product.increaMoney}"
									onfocus="this.style.color='#434343'" style="width: 160px; border: none;" />
								</li>
								<li style="margin-left: 5px">
									
								</li>
								<c:if test="${isLogin && product.pType == 2}">
									<li style="margin-left: 5px" id="redPacketNumber">
										我的卡券：暂无可用
									</li>
								</c:if>
							</ul>
							<span id="sp" style="display: none"></span>
							<p>预期收益：<span id="expectIncome">0.00</span><span id="redPacketIncome"></span></p>
							<p style="font-size:14px;color:#ccc;">(预期收益不代表实际收益)</p>
							<!-- 后台直接预览，未审核通过的和活动产品置灰 -->
							<div class="clearfix" style="height:50px;">
								<c:choose>								
									<c:when test="${product.approval==0||product.approval==1||product.activity==1}">
										<a href="javascript:void(0)" class="detail_a detail_disable">立即购买</a>
									</c:when>
									<c:otherwise>
										<!-- 非定投 -->
										<c:if test="${product.fixInvest==0}">
											
												<!-- 判断规则 -->
												<c:if test="${product.rule==0}">
													<c:choose>
														<c:when test="${product.status==1&&(product.priorMoney-product.reachMoney>0)&& (product.stDt.time <=nowDate)}">
															<a href="javascript:void(0)" class="detail_a detail_buy">立即购买</a>
														</c:when>
			
														<c:otherwise>
															<a href="javascript:void(0)" class="detail_a detail_disable">立即购买</a>
														</c:otherwise>
													</c:choose>
												</c:if>
			
												<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
												<c:if test="${product.rule==1}">
													<c:choose>
														<c:when test="${product.status==1&&(product.edDt.time >nowDate)&& (product.stDt.time <=nowDate)}">
															<a href="javascript:void(0)" class="detail_a detail_buy">立即购买</a>
														</c:when>
														<c:otherwise>
															<a href="javascript:void(0)" class="detail_a detail_disable">立即购买</a>
														</c:otherwise>
													</c:choose>
			
												</c:if>
			
												<c:if test="${product.rule==2}">
													<c:choose>
														<c:when test="${product.status==1 && (product.priorMoney-product.reachMoney) > 0 && (product.stDt.time <=nowDate) && (product.edDt.time >nowDate)}">
															<a href="javascript:void(0)" class="detail_a detail_buy">立即购买</a>
														</c:when>
														<c:otherwise>
															<a href="javascript:void(0)" class="detail_a detail_disable">立即购买</a>
														</c:otherwise>
													</c:choose>
												</c:if>
			
												<c:if test="${product.rule==3&&product.status==1}">
													<a href="javascript:void(0)" class="detail_a detail_buy">立即购买</a>
												</c:if>
												
												<c:if test="${product.rule==3&&product.status!=1}">
													<a href="javascript:void(0)" class="detail_a detail_disable">立即购买</a>
												</c:if>
											
										</c:if>
										<!-- 定投 -->
										<c:if test="${product.fixInvest!=0}">
											
												<!-- 不在1到25号之间 -->
												<c:if test="${dingTouCanBuy==1}">
													<a href="javascript:void(0)" class="detail_a detail_disable">立即购买</a>
												</c:if>
												<!-- 在1到25号之间 -->
												<c:if test="${dingTouCanBuy==0}">
													<!-- 判断规则 -->
													<c:if test="${product.rule==0}">
														<c:choose>
															<c:when test="${product.status==1&&(product.priorMoney-product.reachMoney>0)}">
																<a href="javascript:void(0)" class="detail_a detail_buy">立即购买</a>
															</c:when>
														</c:choose>
														<c:otherwise>
															<a href="javascript:void(0)" class="detail_a detail_disable">立即购买</a>
														</c:otherwise>
													</c:if>
			
													<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
													<c:if test="${product.rule==1}">
														<c:choose>
															<c:when test="${product.status==1&&(product.edDt.time >nowDate)&& (product.stDt.time <=nowDate) }">
																<a href="javascript:void(0)" class="detail_a detail_buy">立即购买</a>
															</c:when>
															<c:otherwise>
																<a href="javascript:void(0)"
																	class="detail_a detail_disable">立即购买</a>
															</c:otherwise>
														</c:choose>
			
													</c:if>
			
													<c:if test="${product.rule==2}">
														<c:choose>
															<c:when
																test="${product.status==1&&(product.priorMoney-product.reachMoney)>0 && (product.edDt.time >nowDate)&& (product.stDt.time <=nowDate)}">
																<a href="javascript:void(0)" class="detail_a detail_buy">立即购买</a>
															</c:when>
															<c:otherwise>
																<a href="javascript:void(0)" class="detail_a detail_disable">立即购买</a>
															</c:otherwise>
														</c:choose>
													</c:if>
			
													<c:if test="${product.rule==3}">
														<a href="javascript:void(0)" class="detail_a detail_buy">立即购买</a>
													</c:if>
												</c:if>
											
										</c:if>
									</c:otherwise>
								</c:choose>
								<img src="/resource/images/img_nei_jsq.png" class="jsq_nei" style="float:left; margin-left:10px; width: 50px;cursor: pointer;" />
							</div>
						</div>

					</div>
					<div class="cleardiv"></div>
				</div>
			</div>

			<div class="detailbottom">
				<div id="divMainTab3">
					<ul style="list-style: none; margin: 0px; padding: 0px; border-collapse: collapse;"
						class="clearfix">
						<c:if test="${not empty descriptionList && not empty projectInfo}">						
							<li>
							  <a href="javascript:void(0)" id="f_1" class="selected" onmouseover="changeTab3('1')">产品详情</a>
							</li>
							<li>
							  <a href="javascript:void(0)" id="f_3" class="normal" onmouseover="changeTab3('3')">项目详情</a>
							</li>
							<li>
							  <a href="javascript:void(0)" id="f_2" class="normal" onmouseover="changeTab3('2')">理财记录</a>
							</li>
						</c:if>
						
						<c:if test="${not empty descriptionList && empty projectInfo}">
							<li>
							  <a href="javascript:void(0)" id="f_1" class="selected" onmouseover="changeTab3('1')">产品详情</a>
							</li>
							<li>
							  <a href="javascript:void(0)" id="f_2" class="normal" onmouseover="changeTab3('2')">理财记录</a>
							</li>
						</c:if>
						
						<c:if test="${empty descriptionList && not empty projectInfo}">
							<li>
							  <a href="javascript:void(0)" id="f_3" class="selected" onmouseover="changeTab3('3')">项目详情</a>
							</li>
							<li>
							  <a href="javascript:void(0)" id="f_2" class="normal" onmouseover="changeTab3('2')">理财记录</a>
							</li>
						</c:if>
						
						<c:if test="${empty descriptionList && empty projectInfo}">
							<li>
							  <a href="javascript:void(0)" id="f_2" class="selected" onmouseover="changeTab3('2')">理财记录</a>
							</li>
						</c:if>
												
					</ul>
				</div>
				<div class="contents">
					<c:if test="${not empty descriptionList && not empty projectInfo}">						
						<div id="f1" style="display: block;" class="divContent">
							<c:choose>  
	  							<c:when test="${not empty descriptionList}">
									<c:forEach items="${descriptionList }" var="item">
										<div
											style="border-left: 6px solid #ff5a00; width: 100%; margin: 20px auto; height: 16px; line-height: 16px; font-size: 16px; color: #4a4a4a; padding-bottom: 0; padding-left: 15px;">
											${item.title }</div>
										<div style="display: block;padding-bottom:0; " >${item.content }</div>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<div style="display: block;padding-bottom:0; " >${descriptionString }</div>
		   						</c:otherwise>	  
							</c:choose>
						</div>
					
						<div id="f2" style="display: none" class="divContent">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<th>用户</th>
									<th>理财金额（元）</th>
									<th>理财时间</th>
									<th>状态</th>
								</tr>
								<c:forEach var="item" items="${gridPage.rows}" varStatus="status">
									<tr>
										<td style="width:111px">
										<p>${fn:substring(item.name, 0, 1)}<span style="display:inline-block;vertical-align:middle">**</span></p></td>
										<td style="width:236px;padding-right:154px;text-align:right"><fmt:formatNumber pattern="#,##0.00#"
												value="${item.financialMoney}" /></td>
										<td style="width:303px"><fmt:formatDate value="${item.buyDt}"
												pattern="yyyy-MM-dd" /></td>
										<td style="width:168px">已中标</td>
									</tr>
								</c:forEach>
							</table>
	
							<div class="pages">
								<w:pager pageSize="${pageSize}" pageNo="${pageNo}"
									url="/product/showProduct.html?changTab=2&code=${product.code}"
									recordCount="${gridPage.total}" falg='0' />
							</div>
						</div>
						<div id="f3" style="display: none;  text-align: left;" class="divContent">
							${projectInfo }
						</div>
					</c:if>
					
					<c:if test="${not empty descriptionList && empty projectInfo}">
						<div id="f1" style="display: block;" class="divContent">
							<c:choose>  
	  							<c:when test="${not empty descriptionList}">
									<c:forEach items="${descriptionList }" var="item">
										<div
											style="border-left: 6px solid #ff5a00; width: 100%; margin: 20px auto; height: 16px; line-height: 16px; font-size: 16px; color: #4a4a4a; padding-bottom: 0; padding-left: 15px;">
											${item.title }</div>
										<div style="display: block;padding-bottom:0; " >${item.content }</div>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<div style="display: block;padding-bottom:0; " >${descriptionString }</div>
		   						</c:otherwise>	  
							</c:choose>
						</div>
						
						<div id="f2" style="display: none" class="divContent">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<th>用户</th>
									<th>理财金额（元）</th>
									<th>理财时间</th>
									<th>状态</th>
								</tr>
								<c:forEach var="item" items="${gridPage.rows}" varStatus="status">
									<tr>
										<td style="width:111px">
										<p>${fn:substring(item.name, 0, 1)}<span style="display:inline-block;vertical-align:middle">**</span></p></td>
										<td style="width:236px;padding-right:154px;text-align:right"><fmt:formatNumber pattern="#,##0.00#"
												value="${item.financialMoney}" /></td>
										<td style="width:303px"><fmt:formatDate value="${item.buyDt}"
												pattern="yyyy-MM-dd" /></td>
										<td style="width:168px">已中标</td>
									</tr>
								</c:forEach>
							</table>
	
							<div class="pages">
								<w:pager pageSize="${pageSize}" pageNo="${pageNo}"
									url="/product/showProduct.html?changTab=2&code=${product.code}"
									recordCount="${gridPage.total}" falg='0' />
							</div>
						</div>
					</c:if>
					
					<c:if test="${empty descriptionList && not empty projectInfo}">
						<div id="f2" style="display: none" class="divContent">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<th>用户</th>
									<th>理财金额（元）</th>
									<th>理财时间</th>
									<th>状态</th>
								</tr>
								<c:forEach var="item" items="${gridPage.rows}" varStatus="status">
									<tr>
										<td style="width:111px">
										<p>${fn:substring(item.name, 0, 1)}<span style="display:inline-block;vertical-align:middle">**</span></p></td>
										<td style="width:236px;padding-right:154px;text-align:right"><fmt:formatNumber pattern="#,##0.00#"
												value="${item.financialMoney}" /></td>
										<td style="width:303px"><fmt:formatDate value="${item.buyDt}"
												pattern="yyyy-MM-dd" /></td>
										<td style="width:168px">已中标</td>
									</tr>
								</c:forEach>
							</table>
	
							<div class="pages">
								<w:pager pageSize="${pageSize}" pageNo="${pageNo}"
									url="/product/showProduct.html?changTab=2&code=${product.code}"
									recordCount="${gridPage.total}" falg='0' />
							</div>
						</div>
						
						<div id="f3" style="display: block;  text-align: left;" class="divContent">
							${projectInfo }
						</div>
					</c:if>
					
					<c:if test="${empty descriptionList && empty projectInfo}">
						<div id="f2" style="display: block" class="divContent">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<th>用户</th>
									<th>理财金额（元）</th>
									<th>理财时间</th>
									<th>状态</th>
								</tr>
								<c:forEach var="item" items="${gridPage.rows}" varStatus="status">
									<tr>
										<td style="width:111px">
										<p>${fn:substring(item.name, 0, 1)}<span style="display:inline-block;vertical-align:middle">**</span></p></td>
										<td style="width:236px;padding-right:154px;text-align:right"><fmt:formatNumber pattern="#,##0.00#"
												value="${item.financialMoney}" /></td>
										<td style="width:303px"><fmt:formatDate value="${item.buyDt}"
												pattern="yyyy-MM-dd" /></td>
										<td style="width:168px">已中标</td>
									</tr>
								</c:forEach>
							</table>
	
							<div class="pages">
								<w:pager pageSize="${pageSize}" pageNo="${pageNo}"
									url="/product/showProduct.html?changTab=2&code=${product.code}"
									recordCount="${gridPage.total}" falg='0' />
							</div>
						</div>
					</c:if>
				</div>

			</div>
		</div>
	</div>
	<input type="hidden" id="rule" value="${product.rule }">
	<input type="hidden" id="code" value="${product.code }">
	<input type="hidden" id="unitTime" value="${product.unitTime }">
	<input type="hidden" id="fTime" value="${product.fTime }">
	<input type="hidden" id="fYield" value="${product.fYield }">
	<input type="hidden" id="addYield" value="${product.addYield }">
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	
	<!-- ===========================================以下下为弹框================================== -->
	
	<!-- 表单提交华兴银行 Start -->
	<form action="" target="_blank" method="post" id="hxBankForm">
		<input type="hidden" id="requestData" name="RequestData" /> 
		<input type="hidden" id="transCode" name="transCode" />
	</form>
	<!-- 表单提交华兴银行 End -->
	
	<!-- 表单提交京东 Start -->
    <form action="" target="_blank" method="post" id="jdActiveForm">
        <input type="hidden" id="jdTradingId" name="jdTradingId" value = ''>
    </form>
    <!-- 表单提交京东银行 End -->
	
	<!-- 确认购买DIALOG Start -->
	<form action="" id="sbidForm" target="_blank">
		<input type="hidden" id="tradingId" name="tId">
		<div class="rz-box-con" id="tou_xqd" style="width: 750px">
			<div class="rz-title">
				<h2>支付确认</h2>
				<div id="paymentCountDownDiv" class="shengyuTime clearfix">
					<span>支付剩余时间：</span>
					<a href="javascript:void(0);">00:15:00</a>
				</div>
				<a href="javascript:void(0)" class="rz-close">
					<img src="/resource/images/img_rz-close.png" />
				</a>
			</div>
			<h1>领钱儿理财计划</h1>
			<div class="xqd_top clearfix">
				<div class="xqd_left">
					<p>项目名称：<span class="color">${product.name}</span></p>

					<c:if test="${fn:substring(product.code, product.code.length()-5, product.code.length()-4)==1}">
						<c:if test="${product.fTime==0}">
							<p>项目期限：<span class="color">无固定期限</span></p>
						</c:if>
						<c:if test="${product.fTime!=0}">
							<p>项目期限：<span class="color">${product.fTime}<c:if test="${product.unitTime==0}">天</c:if><c:if test="${product.unitTime==1}">周</c:if><c:if test="${product.unitTime==2}">个月</c:if></span>
							</p>
						</c:if>
					</c:if>

					<c:if test="${fn:substring(product.code, product.code.length()-5, product.code.length()-4)==2}">
						<p>项目期限：
							<span class="color" style="position: relative; z-index: 1; padding-right: 10px; font-weight: bold">${product.lTime}-${product.hTime}
								<img src="/resource/images/jia_small.png" style="position: absolute; z-index: 2; right: 1px; top: 0" />
							</span>天
						</p>
					</c:if>
					<% Date date = new Date(); %>
					<p>购买时间：<span class="color"><fmt:formatDate value="<%=date%>" pattern="yyyy年MM月dd日" /></span>
					</p>
					
					<p id="redpack">返现红包：<span class="color" id="redpacket"></span></p>
				</div>
				<div class="xqd_right">
					<p>理财额度：<span class="color" id="licmoney"></span>元</p>
					<p>预期年化收益率：<span class="color" id="yield1" style="padding: 0; float: none"></span>
					</p>
					<c:if test="${fn:substring(product.code, product.code.length()-5, product.code.length()-4)==1}">
						<c:if test="${product.fTime==0}">
							<p>到期时间：<span class="color">随存随取</span></p>
						</c:if>
						<c:if test="${product.fTime!=0}"><p>到期时间：<span class="color">到期日以产品成立日计算后${product.fTime}<c:if test="${product.unitTime==0}">天</c:if><c:if test="${product.unitTime==1}">周</c:if><c:if test="${product.unitTime==2}">个月</c:if></span>
							</p>
						</c:if>
					</c:if>

					<c:if test="${fn:substring(product.code, product.code.length()-5, product.code.length()-4)==2}">
						<p>到期时间：<span class="color">${product.lTime}天后,随用随取</span></p>
					</c:if>

					<c:if test="${product.fixInvest!=0}">
						<p>定投状态：<span class="color" id="DingStyle"></span></p>
					</c:if>
				</div>
			</div>

			<div class="xqd_bottom">
				<p>支付金额：<span class="color" style="font-size: 28px;" id="zhimoney"></span>元</p>
			</div>
			<p class="agree">
				<input type="checkbox" class="input_agree" checked="checked"/>阅读并同意领钱儿
				<a href="javascript:void(0)" id="a_protocal_1" style="color: #ea5513;">《借款协议》</a>
			</p>
			<p style="margin: 10px auto; width: 460px;padding-left:0;">
				<button type="button" onclick="subbuy()" class="rz-button ok"
					style="width: 308px;margin-right:0;font-size:24px;font-weight: bold;" >立即支付</button><br>
				<a href="javascript:void(0)" style="margin: 0 10px 0;background: none;color: #535353;" class="rz-button" onclick="cancelPayment()">取消支付</a>
			</p>
		</div>
	</form>
	<!-- 确认购买DIALOG End -->
	
	<!-- 遮罩层 -->
	<div id="rz-box-bg"></div>
	<div id="rz-box-bg3" style="z-index: 1100"></div>

	<!-- 深度注册弹框 -->
	<div class="rz-box-con " id="deep">
		<div class="rz-title">
			<h2>深度注册</h2>
			<a href="javascript:void(0)" class="rz-close">
				<img src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<p class="color"
			style="padding-top: 30px; font-size: 16px; color: #c54107">您只有在进行了身份认证及银行卡绑定后，才能购买本产品</p>
		<p style="padding-top: 10px;">
			<a href="/membersUserRealname.html" class="rz-button">去认证</a>
			<a href="javascript:void(0)" class="rz-button reset">取消</a>
		</p>
	</div>
	
	<!-- 购买超额弹框 -->
	<div class="rz-box-con " id="butOverLimit">
		<div class="rz-title">
			<h2>提示</h2>
			<a href="javascript:void(0)" class="rz-close">
				<img src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<p class="color" style="padding-top: 30px; font-size: 16px; color: #c54107"></p>
		<p style="padding-top: 10px;">
			<a href="javascript:void(0)" class="rz-button ensure">我知道了</a>
		</p>
	</div>

	<!-- 自定义弹框 start -->
	<div class="rz-box-con" id="div_customer">
		<div class="rz-title">
			<h2></h2>
			<a href="javascript:void(0)" class="rz-close"> <img
				src="/resource/images/img_rz-close.png" /></a>
		</div>
		<p style="padding-top: 30px; font-size: 16px; text-align: center">

		</p>
		<p style="padding-top: 20px;">
			<a href="javascript:void(0)" class="rz-button"></a> <a
				href="javascript:void(0)" class="rz-button reset"></a>
		</p>
	</div>
	<!-- 自定义弹框 end -->
	
	<!--确认提現弹框 -->
	<div class="rz-box-con" id="div_cz">
		<div class="rz-title">
			<h2>请在新页面完成支付</h2>
			<a href="javascript:void(0)" class="rz-close"><img
				src="/resource/images/img_rz-close.png" /></a>
		</div>
		<p style="padding-top: 30px; font-size: 16px; text-align: center">
			完成支付前请<span style="margin: 0 5px; color: #ea5513">不要关闭此窗口</span>，完成提现后根据您的情况点击下面按钮
		</p>
		<p style="padding-top: 20px;">
			<a href="/product/showPayResult.html" class="rz-button ">已完成支付</a><a
				href="/product/showPayResult.html" class="rz-button reset">支付遇到问题</a>
		</p>
	</div>

	<div class="rz-box-con " id="zhc">
		<div class="rz-title">
			<h2>登录</h2>
			<a href="javascript:void(0)" class="rz-close">
				<img src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<p class="color" style="padding-top: 30px; font-size: 16px; color: #c54107">登录超时,请重新登录。</p>
		<p style="padding-top: 10px;">
			<a href="/login" class="rz-button">去登录</a>
			<a href="javascript:void(0)" class="rz-button reset">取消</a>
		</p>
	</div>
	
	<!-- 卡券提示 -->
	<div class="rz-box-con " id="cart" style="right:50%;bottom:50%;margin: -110px -350px -110px -350px;">
		<div class="rz-title">
			<h2>提示</h2>
			<a href="javascript:void(0)" class="rz-close">
				<img src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<p id="carnum" class="color" style="padding-top: 30px; font-size: 16px; color: #c54107"></p>
		<p style="padding-top: 10px;">
			<a href="javascript:void(0);" onclick="cartick(1);" class="rz-button">确认</a>
			<a href="javascript:void(0);"  onclick="cartick(0);" class="rz-button reset">取消</a>
		</p>
	</div>
	
	
	<!-- 确认取消支付弹框 -->
	<div id="cancelPaymentAlert" class="cancelPaymentAlert" style="display:none;z-index:9999">
		<div class="contentBox">
			您确定取消支付吗？
		</div>
		<div class="buttonBox clearfix">
			<a class="determine" href="javascript:void(0);">确定</a>
			<a class="cancel" href="javascript:void(0);">取消</a>
		</div>
	</div>
	
	<!-- 提示开通华兴E账户弹框 -->
	<div id="hxAccountDialog" class="TipHuaXing" style="display: none;">
		<div class="th_box">
			<a href="javascript:void(0);" class="hx_close"></a>
			<p></p>
			<a class="ckbtn" href="/myAccount/bindBankCard"></a>
		</div>
	</div>
	
	<!-- 判断用户是否有待支付产品、判断购买金额  弹框 -->
	<div id="buyerInfoValidDialog" class="judgeTip" style="display: none;">
		<div class="jtClose clearfix">
			<a href="javascript:void(0);">关闭</a>
		</div>
		<div class="jtContent">
			<p></p>
		</div>
		<a class="jt_ckbtn" href="javascript:void(0);"></a>
	</div>	
	
	<!-- 自定义弹框提示信息 start -->
	<div class="rz-box-con" id="div_customer_tip" style="width: 400px;">
		<div class="rz-title">
			<h2></h2>
			<a href="javascript:void(0)" class="rz-close"> <img
				src="/resource/images/img_rz-close.png" /></a>
		</div>
		<p
			style="padding-top: 30px; font-size: 20px; text-align: center; color: #ea5513">
		</p>
		<p style="padding-top: 20px;">
			<a href="javascript:void(0)" class="rz-button" id="ok_button">确定</a>
		</p>
	</div>
	<!-- 自定义弹框提示信息 end -->
	
	<!-- 含有确定取消按钮的小弹框 start -->
	<div class="rz-box-con" id="bombBox" style="width: 500px">
		<div class="rz-title">
			<h2>提示</h2>
		</div>
		<div style="padding: 20px 0; margin: 0 auto; width: 400px; text-align: center; font-size: 1.5em;"
			id="bombBoxContent"></div>
		<p style="padding-top: 10px;" id="ensureCancel">
			<a href="javascript:void(0)" class="rz-button">确定</a><a
				href="javascript:void(0)" class="rz-button" style="background: #eee;color: #535353;">取消</a>
		</p>
	</div>
	<!-- 含有确定取消按钮的小弹框 end -->
	
	
	<!-- 协议弹框 -->
	<div class="rz-box-con protocol" id="protocol3">
		<div class="rz-title">
			<h2>免责声明</h2>
			<a href="javascript:void(0)" class="rz-close"
				style="padding-top: 15px;"><img
				src="/resource/images/img_rz-close1.png" /></a>
		</div>
		<div class="pro_txt">
			<p class="MsoNormal" align="center"
				style="text-align: center; ">
				免责声明<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				1、 您将对您提供或发布的信息及其他在本平台上发生的任何行为承担责任，并应使本平台免于对此承担任何责任。<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				2、
				若因客户、银行、支付机构或其他合作机构原因（包括但不限于客户、银行、支付机构、合作机构系统故障、操作失误等），造成操作或交易失败，本平台对此不负任何责任。<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				3、
				您确认知悉并同意，基于互联网的特殊性，本平台不担保服务不会中断，也不担保服务的及时性、安全性。系统因相关状况无法正常运作，使个人会员无法使用任何本平台服务或使用任何领钱儿网服务时受到任何影响时，本平台对个人会员或第三方不负任何责任，前述状况包括但不限于：
				<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				3.1、本平台进行升级或维护的。<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				3.2、电信设备及网络出现不能进行、不能正确进行或不能完整进行数据传输等问题的。<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				3.3、由于黑客攻击、网络供应商技术调整或故障、银行方面的问题等原因而造成的本平台服务中断或延迟。<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				3.4、因包括但不限于台风、地震、海啸、洪水、停电、战争、恐怖袭击或其他根据互联网惯例所认定之不可抗力之因素，造成本平台不能提供服务的。<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				3.5、其他影响本平台提供服务的情形。<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				4、
				本平台仅提供投资信息发布及相关增值服务，具体交易信息均以产品发布方所发布内容为准，本平台不对该等产品信息的真实性、充分性、完整性、及时性、可靠性或有效性作出任何明示或暗示的保证，亦不对投资产品收益做出任何明示的或暗示的承诺或担保。您应根据自身的投资偏好和风险承受能力，自行衡量交易相对方、产品信息以及交易的真实性和安全性。<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				5、 您在选择本平台服务时应直接登录本平台，不应通过邮件或其他平台提供的链接登录，否则由此造成的风险和损失将由您自行承担。<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				6、
				如您发现有他人冒用或盗用您的账户及密码或进行任何其他未经合法授权行为之情形，应立即以书面方式通知本平台并要求本平台暂停服务，否则由此产生一切责任由您本人承担。本平台将积极响应您的要求；但您应理解，对您的要求采取行动需要合理期限，在此之前，本平台对已执行的指令及<span>(</span>或<span>)</span>所导致的您的损失不承担任何责任。<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				7、本平台内容可能涉及或链接到由第三方所有、控制或者运营的其他平台（“第三方平台”）。本平台不能保证也没有义务保证第三方平台上的信息的真实性和有效性。您确认按照第三方平台的注册协议而非本注册协议使用第三方平台，第三方平台的内容、产品、广告和其他任何信息均由您自行判断并承担风险。<span></span>
			</p>
		</div>
		<div
			style="margin: 0 auto; width: 640px; padding-top: 20px; text-align: center">
			<a href="javascript:void(0)" class="a_go">同意并继续</a>
		</div>
	</div>
	
	<!-- 协议弹框 -->
	<div class="rz-box-con protocol" id="protocol2">
		<div class="rz-title">
			<h2>风险提示</h2>
			<a href="javascript:void(0)" class="rz-close"
				style="padding-top: 15px;"><img
				src="/resource/images/img_rz-close1.png" /></a>
		</div>
		<div class="pro_txt">
			<p class="MsoNormal" align="center"
				style="text-align: center; ">
				风险提示<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				您明确并认可，任何通过本平台进行的交易并不能避免以下风险的产生，本平台不能也没有义务为如下风险负责：<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				（<span>1</span>）宏观经济风险：因宏观经济形势变化，可能引起价格等方面的异常波动，用户有可能遭受损失；<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				（<span>2</span>）政策风险：有关法律、法规及相关政策、规则发生变化，可能引起价格等方面异常波动，用户有可能遭受损失；<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				（<span>3</span>）违约风险：因其他交易方无力或无意愿按时足额履约，用户有可能遭受损失；<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				（<span>4</span>）利率风险：市场利率变化可能对购买或持有产品的实际收益产生影响；<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				（<span>5</span>）不可抗力因素导致的风险；<span></span>
			</p>
			<p class="MsoNormal" style="text-indent: 2em;">
				（<span>6</span>）因用户的过错导致的任何损失
				，该过错包括但不限于：决策失误、操作不当、遗忘或泄露密码、密码被他人破解、用户使用的计算机系统被第三方侵入、用户委托他人代理交易时他人恶意或不当操作而造成的损失。<span></span>
			</p>
			<p class="MsoNormal">
				<span>&nbsp;</span>
			</p>
		</div>
		<div
			style="margin: 0 auto; width: 640px; padding-top: 20px; text-align: center">
			<a href="javascript:void(0)" class="a_go">同意并继续</a>
		</div>
	</div>
	
	<!-- 抵押物信息弹框 -->
	<div class="rz-box-con protocol" id="protocol4">
		<div class="rz-title">
			<h2>抵押物信息</h2>
			<a href="javascript:void(0)" class="rz-close"
				style="padding-top: 15px;">
				<img src="/resource/images/img_rz-close1.png" />
			</a>
		</div>
		<div class="pro_txt" id="introduction4">
		</div>
		<div
			style="margin: 0 auto; width: 640px; padding-top: 20px; text-align: center">
			<a href="javascript:void(0)" class="a_go">关闭</a>
		</div>
	</div>
	
	<div class="rz-box-con protocol" id="protocol1" style="z-index: 9999;">
		<jsp:include page="/common/loanAgreement.jsp"></jsp:include>
		<div style="margin: 0 auto; width: 640px; padding-top: 20px; text-align: center">
			<a href="javascript:void(0)" class="a_go1">同意并继续</a>
		</div>
	</div>

	<!-- 优惠券================= -->
	<div class="rz-box-con" id="increasedRates" style="display: none;border:solid #000 1px;height:560px;">
		<div class="rz-title">
			<h2>我的卡券</h2>
			<a href="javascript:void(0)" class="rz-close" onclick="cancelUseRedPacket()"> 
				<img src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		
		<div class="coupon kq">
	  		<a  href="javascript:void(0)" id="coupon-active2" onclick="couponActive2();">红包</a>
	  		<a class="active" href="javascript:void(0)" id="coupon-active1" onclick="couponActive1();">加息券</a> 
		</div>
		<div class="divss">
		
			<div class="t-box increasingList clearfix"  id="redPacketParentDiv2">
			
			</div>	
			<div class="t-box increasingList clearfix" style="display:block" id="redPacketParentDiv1">
			
			</div>
		</div>
		
		<p style="padding-top: 20px; text-align: center;">
			<a href="javascript:void(0)" class="rz-button" onclick="confirmUseRedPacket()">确定</a> 
			<a href="javascript:void(0)" class="rz-button reset" onclick="cancelUseRedPacket()">取消</a>
		</p>
	</div>
	
	<!-- 优惠券循环模板 S -->
	<div id="redPacketDemoDiv" class="cardBox" style="display:none;">
		<div class="cb_head">
			<span>0<i>%</i></span>&nbsp;加息券
		</div>
		<div class="cb_middle">
			
		</div>
		<div class="cb_bottom clearfix">
			<label></label>
			<span></span>
		</div>
	</div>
	<!-- 优惠券循环模板 E -->
	<script type="text/javascript">
			$('.coupon > a').click(function(){
				$('.divss >div').eq($(this).index()).show().siblings().hide();
			})
			/* 判断加息率 */
			
		if(addYield.value>0){
			$("#product-fYield").html((fYield.value*100-addYield.value*100).toFixed(1)+"%"); 
			$("#product-addYield").html("+"+(addYield.value*100).toFixed(1)+"%");
			}else{
				$("#product-fYield").html((fYield.value*100).toFixed(1)+"%");
			}
			

			
		</script>
</body>
</html>