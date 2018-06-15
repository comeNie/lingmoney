<%@page import="java.util.ResourceBundle"%>﻿
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<%
    Date nowDate = new Date();
    request.setAttribute("nowDate", nowDate);
%>
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
<style type="text/css">
.t-box{display:none;}
</style>
</head>
<body>
	<input type="hidden" id="buyLimit" value="${product.buyLimit}" />
	<input type="hidden" id="isLogin" value="${isLogin }">
	<input type="hidden" id="userCertification" value="${CURRENT_USER.certification }">
	<input type="hidden" id="userBank" value="${CURRENT_USER.bank }">
	
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
							<h1>${product.name}</h1>
							<div class="cleardiv"></div>
						</div>

						<ul class="top clearfix">
							<li class="clearfix">
								<img src="/resource/images/img_guimo.png" />
								<div>
									<p>项目规模</p>
									<strong>--</strong>
								</div>
							</li>
							<li class="clearfix">
								<img src="/resource/images/img_lilv.png" />
								<div>
									<p>预期年化收益率</p>
									<span id="yield" style="padding: 0; float: none"> 
									<strong>
										<fmt:formatNumber type="percent" value="${product.lYield}" minFractionDigits="1"
											maxFractionDigits="3" />-<fmt:formatNumber type="percent"
											value="${product.hYield}" minFractionDigits="1" maxFractionDigits="3" />
									</strong>
									</span>
								</div>
							</li>
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
						</ul>
						<div class="dcenter">
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

							<c:if test="${product.status==1}">
								<c:if test="${product.fTime==0 || empty product.fTime}">
									<img src="/resource/images/img_open.png" />
								</c:if>
								<c:if test="${product.fTime!=0&&not empty product.fTime}">
									<img src="/resource/images/img_going.png" />
								</c:if>
							</c:if>
							<c:if test="${product.status==2}">
								<img src="/resource/images/img_open.png" />
							</c:if>
							<c:if test="${product.status==3}">
								<img src="/resource/images/img_repay.png" />
							</c:if>
							<c:if test="${product.status==4}">
								<img src="/resource/images/end-15.png" />
							</c:if>

							<div class="div_statement">
								<a href="javascript:void(0)" id="a_protocal_2">风险提示</a> 
								<a href="javascript:void(0)" id="a_protocal_3">免责声明</a>
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
							<p style="color: #000; margin-bottom: 10px;">
								剩余可购：<span id="toumoney">不限</span>
							</p>
							<p>起投金额：<span id="startMoney">￥${product.minMoney}</span></p>
							<ul class="counts clearfix">
								<p>购买金额：</p>
								<li style="border: 1px solid #727171">
									<input type="number" id="buyMoney" name="buyMoney" required="required"  placeholder="￥${product.increaMoney} 输入金额"
									min="${product.increaMoney}" step="${product.increaMoney}"
									onfocus="this.style.color='#434343'" style="width: 160px; border: none;" />
								</li>
								<li style="margin-left: 5px">
									
								</li>
							</ul>
							<span id="sp" style="display: none"></span>
							<!-- 后台直接预览，未审核通过的和活动产品置灰 -->
							<div class="clearfix" style="height:50px;">
								<a href="javascript:void(0)" class="detail_a detail_buy">立即购买</a>
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
						<li>
						  <a href="javascript:void(0)" id="f_1" class="selected" onmouseover="changeTab3('1')">产品详情</a>
						</li>
						<li>
						  <a href="javascript:void(0)" id="f_2" class="normal" onmouseover="changeTab3('2')">理财记录</a>
						</li>
					</ul>
				</div>
				<div class="contents">
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
									<p>${fn:substring(item.nickName==null?item.name:item.nickName, 0, 1)}<span style="display:inline-block;vertical-align:middle">**</span></p></td>
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
				</div>

			</div>
		</div>
	</div>
	<input type="hidden" id="rule" value="${product.rule }">
	<input type="hidden" id="code" value="${product.code }">
	<input type="hidden" id="unitTime" value="${product.unitTime }">
	<input type="hidden" id="fTime" value="${product.fTime }">
	<input type="hidden" id="fYield" value="${product.fYield }">
	
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	
	<!-- ===========================================以下下为弹框================================== -->
	<!-- 确认购买DIALOG Start -->
	<form action="/product/jdBuyProductHtml" id="sbidForm" target="_blank">
		<input type="hidden" id="tradingId" name="takeheartTid">
		<div class="rz-box-con" id="tou_xqd" style="width: 750px">
			<div class="rz-title">
				<h2>支付确认</h2>
				<a href="javascript:void(0)" class="rz-close">
					<img src="/resource/images/img_rz-close.png" />
				</a>
			</div>
			<h1>领钱儿理财计划</h1>
			<div class="xqd_top clearfix">
				<div class="xqd_left">
					<p>项目名称：<span class="color">${product.name}</span></p>
					<p>项目期限：
						<span class="color" style="position: relative; z-index: 1; padding-right: 10px; font-weight: bold">${product.lTime}-${product.hTime}
							<img src="/resource/images/jia_small.png" style="position: absolute; z-index: 2; right: 1px; top: 0" />
						</span>天
					</p>
					<% Date date = new Date(); %>
					<p>购买时间：<span class="color"><fmt:formatDate value="<%=date%>" pattern="yyyy年MM月dd日" /></span>
					</p>
				</div>
				<div class="xqd_right">
					<p>理财额度：<span class="color" id="licmoney"></span>元</p>
					<p>预期年化收益率：
					   <span class="color" id="yield1" style="padding: 0; float: none">
					       <fmt:formatNumber type="percent" value="${product.lYield}" minFractionDigits="1"
                                maxFractionDigits="3" />-<fmt:formatNumber type="percent"
                                value="${product.hYield}" minFractionDigits="1" maxFractionDigits="3" />
					   </span>
					</p>
					<p>到期时间：<span class="color">${product.lTime}天后,随用随取</span></p>
				</div>
			</div>

			<div class="xqd_bottom">
				<p>支付金额：<span class="color" style="font-size: 28px;" id="zhimoney"></span>元</p>
			</div>
			<p class="agree">
				<input type="checkbox" class="input_agree" checked="checked"/>阅读并同意领钱儿
				<a href="javascript:void(0)" id="a_protocal_1" style="color: #ea5513;">《借款协议》</a>
			</p>
			<p style="margin: 10px auto; width: 460px;">
                <button type="button" onclick="subbuy()" class="rz-button ok" style="float: left; color: rgb(255, 255, 255); cursor: pointer;">确定</button>
                <a href="javascript:void(0)" class="rz-button reset">取消</a>
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
	
	<div class="rz-box-con protocol" id="protocol1" style="z-index: 9999;">
		<div class="rz-title">
			<h2>借款协议</h2>
			<a href="javascript:void(0)" class="rz-close1"
				style="padding-top: 15px;"><img
				src="/resource/images/img_rz-close1.png" /></a>
		</div>
		<div class="pro_txt">
			<p class="MsoNormal" align="center"
				style="text-align: center; ">
			<p class="MsoNormal" style="text-indent: 2em;">&nbsp;</p>
			<p class="MsoNormal" align="right"
				style="text-align: right; ">
				“领钱儿”合同编号：<span>XXXX</span>
			</p>
			<p class="MsoNormal" align="center"
				style="text-align: center; ">
				领钱儿借款协议<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">&nbsp;</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				（“本协议”）由以下各方于【<span>&nbsp;&nbsp;&nbsp; </span>】年【 】月【<span>&nbsp;
				</span>】日签订：<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				甲方：（债权转让人）：<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				真实姓名：<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				身份证号：<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				平台用户名：<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				乙方：（债权受让人）：<span></span>
			</p>
			<table class="MsoNormalTable" border="1" cellspacing="0"
				cellpadding="0" style="padding-left:42px;border: none;">
				<tbody>
					<tr>
						<td width="120" align="center" valign="middle" 
							style="border: solid windowtext 1.0pt;height:30px;">
							领钱儿用户
							
						</td>
						<td width="120" align="center" 
							style="border: solid windowtext 1.0pt;height:30px;">
							受让金额
							
						</td>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							期限
							
						</td>
					</tr>
					<tr>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							用户001
							
						</td>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							
						</td>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							
						</td>
					</tr>
					<tr>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							用户002
							
						</td>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							
						</td>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							
						</td>
					</tr>
					<tr>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							用户003
							
						</td>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							
						</td>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							
						</td>
					</tr>
					<tr>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							用户004
						</td>
						<td width="120" align="center" valign="middle"
							style="border: solid windowtext 1.0pt;height:30px;">
							
							
						</td>
						<td width="120" align="center"
							style="border: solid windowtext 1.0pt;height:30px;">
							
						</td>
					</tr>
				</tbody>
			</table>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">&nbsp;</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				丙方：北京睿博盈通网络科技有限公司（具有提供信息服务资质并拥有领钱儿合法经营权，网址：www.lingmoney.cn）<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">&nbsp;</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				除非本协议另有规定，以下词语在本协议中定义如下：<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				a、债权转让人（甲方）：指与线下借款人存在合法借贷关系并将该等合法真实债权转让给债权受让人，且具有完全民事权利<span>/</span>行为能力的自然人。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				b、债权受让人（乙方）：指通过丙方领钱儿成功注册账户的会员，可参考丙方的推荐自主选择受让债权转让人或同为领钱儿注册用户的第三方合法拥有的真实债权，且具有完全民事权利<span>/</span>行为能力的自然人或法人。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				c、线下借款人：与债权转让人形成真实合法借贷关系的，知悉债权转让，对债权受让人负有债务，具有一定的资金需求且具有完全民事权利<span>/</span>行为能力的自然人。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				d、债权：指债权转让人通过向线下借款人提供借贷资金而在《借款协议》项下享有的所有权利。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				e、借款：指债权转让人向线下借款人提供借贷资金。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				f、《借款协议》：指就任何一笔借款而言，由线下借款人、债权转让人在公证机关签署的、作为本协议之法律基础的书面纸质合同。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				g、“领钱儿”：指丙方运营管理的平台，域名为：<span>www.lingmoney.cn</span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				h、第三方监管账户：以丙方名义委托京东支付提供资金账户管理服务，账户内资金独立于丙方自有资金的监管<span>/</span>保管账户。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				i、领钱儿账户：指债权受让人或债权转让人以自身名义在领钱儿注册后系统自动产生的虚拟账户，可通过第三方支付平台及<span>/</span>或其他通道进行充值或提现。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">&nbsp;</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				就甲方通过由丙方运营管理的领钱儿平台向乙方转让债权事宜，各方根据平等、自愿的原则，达成本协议如下：<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				一、债权转让金额及还款期限<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				1.1在通知线下借款人并获得其授权后，甲方同意根据本协议约定的条件将其通过发放借款形成的以下债权按照如下方式转让给乙方，乙方同意按如下方式进行受让：<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				还款日自 <span>&nbsp;&nbsp;</span>年<span>&nbsp; </span>月<span>&nbsp;
				</span>日起，每月 <span>&nbsp;&nbsp;</span>日（若当月没有该日，则还款日为当月最后一天，节假日不顺延）<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				二、债权转让流程<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				2.1 本协议成立：乙方按照领钱儿的规则，通过在领钱儿上对甲方发布的债权转让需求点击“投资”按钮确认时，本协议立即成立。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				2.2 债权转让资金冻结：乙方点击“投资”按钮即视为其已经向丙方发出不可撤销的授权指令，授权丙方委托相应的第三方支付机构及<span>/</span>或丙方银行监管账户所属的监管银行（“监管银行”）等合作机构，在银行监管账户中乙方领钱儿用户名项下的虚拟账户（“乙方领钱儿账户”）中，冻结金额等同于本协议第一条所列的“需支付对价”的资金。上述冻结在本协议生效时或本协议确定失效时解除。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				2.3 本协议生效：本协议在甲方发布的债权转让需求全部得到满足，且甲方债权转让需求所对应的资金已经全部冻结时立即生效。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				2.4
				债权转让资金划转：本协议生效时，甲方即不可撤销地授权丙方委托相应的第三方支付机构及监管银行等合作机构，将金额等同于本协议第一条所列的“借款金额”的资金，由银行监管账户中乙方领钱儿账户下划转至甲方的个人银行账户，划转完毕即视为债权转让成功。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				2.5
				自债权转让成功之日起，甲方不再对债权享有任何权利。乙方自债权转让成功之日起，成为债权的受让人，承继甲方与线下借款人签订的《借款协议》等所有协议项下出借人的全部权利并承担出借人的全部义务。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				2.6自债权转让成功之日起，乙方授权甲方作为代表，行使和管理与线下借款人之间的抵押权。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">&nbsp;</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				三、受让债权资金的来源保证<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				3.1乙方保证其所用于受让债权的资金来源合法，乙方是该资金的合法所有人，如果第三方对资金归属、合法性问题提出异议，由乙方自行解决。如乙方未能解决，则放弃享有其所受让债权资金所带来的利息等收益。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				四、收费及税费<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				4.1 丙方作为信息中介，有权就为本协议项下的债权转让所提供的服务向甲方收取居间服务费，费用为&nbsp;&nbsp; <span>%</span>。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				4.2 乙方应自行负担并主动缴纳因利息所得带来的可能的税费。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				五、债权转让的约定<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				5.1 债权转让完成后，丙方有义务通知线下借款人按时、足额偿还对乙方的借款本金和利息。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				5.2丙方将代为收取的本息划转至丙方的银行监管账户后，即视为线下借款人已经履行本协议项下对乙方的相应还款义务。收到上述本息后，丙方根据与乙方之间的约定通过银行或第三方支付机构向乙方支付该等资金。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				六、还款保障条款<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				6.1 在线下借款人未在《借款协议》规定的借款期限内偿还本金的，甲方应承担回购义务，确保在本协议第一条所列的“借款期限”到期<span>5</span>个工作日之后承担回购义务。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				6.2
				甲方依据上述规定承担回购义务后，乙方、丙方在本协议项下的所有权利视为已经得到满足和实现，乙方、丙方不得再对线下借款人提出任何请求或主张。乙方、丙方在本协议下所享的全部权利和主张均由甲方享有。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				6.3 乙方在此不可撤销的授权丙方作为其代表，在甲方承担回购义务后，由丙方为甲方向线下借款人进行追偿出具相应的借款清偿证明文件。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				七、提前还款<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				7.1
				在《借款协议》约定的借款期限内，线下借款人提出提前偿还全部剩余本金时，乙方授权“领钱儿”决定是否同意线下借款人提出的提前还款申请。线下借款人提前还款得到允许的，“领钱儿”应保证线下借款人向乙方支付剩余全部本金、当期利息及补偿金。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				7.2 本协议及《借款协议》项下的借款不允许提前部分还款。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				八、逾期还款<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				8.1
				如在《借款协议》约定还款日丙方银行监管账户中未收到或未足额收到线下借款人当月应还款的，视为逾期还款。线下借款人逾期还款的，甲方须按规定在本协议第一条所列的“借款期限”到期<span>5</span>个工作日之后对已转让的债权进行无条件回购，履行回购义务。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				8.2 逾期款项中的利息不计利息。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				九、违约责任<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				甲方在《借款协议》项下期限届满<span>5</span>日后仍未履行本协议约定的回购义务的，乙方授权丙方有权以书面通知形式，要求甲方支付等同于债权转让金额<span>10%</span>的金额作为违约金。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				十、争议解决方式<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				10.1 如果各方在本协议履行过程中发生任何争议，应友好协商解决；如协商不成，则须提交丙方所在地人民法院进行诉讼。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				10.2若甲方未按时足额履行还款义务，且甲方也未按时足额履行其回购义务，则在此种情况下，乙方处置的方式包括但不限于：（<span>1</span>）通过法律途径向甲方进行追偿（包括但不限于代为委托律师提起诉讼）；（<span>2</span>）委托第三方专业机构进行催收；（<span>3</span>）将该笔债权出售予第三方资产管理公司等机构。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				十一、其他<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				11.1 本协议以电子文本形式生成。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				11.2
				丙方须在一定的时间（甲方与丙双方协商而定）内完成甲方所需资金的募集，并向甲方成功放款。在丙方向甲方放款之前发现并核实甲方提供的债权有虚假及严重安全风险因素，丙方有权单方面终止本协议下的一切权利和义务，因此所造成的一切法律风险及费用都由甲方无条件承担。在一定的时间之内（甲方与丙方双方协商而定），丙方没能及时且足额的满足甲方的融资需求，则丙方退还甲方所缴纳的居间服务费总金额的<span>1/2</span>。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				11.3 本协议的任何修改、补充均须以领钱儿平台电子文本形式作出。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				11.4
				各方均确认，本协议的签订、生效和履行以不违反法律为前提，本协议与《借款协议》存在不一致的，以本协议的约定为准。如果本协议中的任何一条或多条违反适用的法律，则该条将被视为无效，但该无效条款并不影响本协议其他条款的效力。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				11.5 各方委托领钱儿保管所有与本协议有关的书面文件或电子信息。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				11.6 本协议中所使用的相关用语，除非在上下文中另有定义外，应具有领钱儿公布的交易规则中定义的含义。<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">&nbsp;</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">&nbsp;</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				甲方：<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				乙方：<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				用户<span>001</span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				用户<span>002</span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				用户<span>003</span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				用户<span>004</span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				……
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">
				丙方：北京睿博盈通网络科技有限公司（具有提供信息服务资质并拥有领钱儿合法经营权，网址：www.lingmoney.cn）<span></span>
			</p>
			<p class="MsoNormal" style="text-align: justify; text-indent: 2em;">&nbsp;</p>
			<p class="MsoNormal">
				<span></span>
			</p>
		</div>
		<div style="margin: 0 auto; width: 640px; padding-top: 20px; text-align: center">
			<a href="javascript:void(0)" class="a_go1">同意并继续</a>
		</div>
	</div>
	
	<!--确认提現弹框 -->
    <div class="rz-box-con" id="div_cz">
        <div class="rz-title">
            <h2>请在新页面完成支付</h2>
            <a href="javascript:void(0)" class="rz-close">
            <img src="/resource/images/img_rz-close.png" /></a>
        </div>
        <p style="padding-top: 30px; font-size: 16px; text-align: center">
            完成支付前请<span style="margin: 0 5px; color: #ea5513">不要关闭此窗口</span>，完成提现后根据您的情况点击下面按钮
        </p>
        <p style="padding-top: 20px;">
            <a href="/product/showPayResult" class="rz-button ">已完成支付</a>
            <a href="/product/showPayResult" class="rz-button reset">支付遇到问题</a>
        </p>
    </div>

	<!-- 优惠券循环模板 E -->
	<script type="text/javascript">
	// 金额合法标识，请求后台是需先判断此值
	var moneyValid = false;
	
	$(function() {
		$("#buyMoney").keyup(function() {
	        buyMoneyAvailable();
	    });
	    
	    $("#buyMoney").change(function() {
	        buyMoneyAvailable();
	    });
	    
	    $(".rz-close, #ok_button").click(function(){
	        $("#rz-box-bg").hide();
	        $("#div_customer").hide();
	        $("#div_customer_tip").hide();
	        $("#increasedRates").hide();
	    });
	    
	    $(".media-link li:first").css("cursor", "pointer")
	    $(".media-link li:first").hover(function() {
	        $(this).find("div").show();
	    }, function() {
	        $(this).find("div").hide();
	    });
	    
	    var changTab = $("#changTab").val();
	    if (changTab == 1 || changTab == "null") {
	        changeTab3(1);
	    } else if (changTab == 2) {
	        changeTab3(2);
	    }
	    
	    // 金额输入框事件 START ======================================
	    var isSpecialKeyCode = false;
	    var isNumbersKeyCode = false;
	    $("#buyMoney").keydown(function(event) {
	        isSpecialKeyCode = (event.keyCode == 190 || event.keyCode == 110 || event.keyCode == 46
	                || event.keyCode == 8 || event.keyCode == 37 || event.keyCode == 39);
	        
	        isNumbersKeyCode = ((48 <= event.keyCode && event.keyCode <= 57) || (96 <= event.keyCode && event.keyCode <= 105));
	        
	        if (!isSpecialKeyCode && !isNumbersKeyCode) {
	            event.returnValue = false;
	        }
	        
	    });
	    
	    $(".main-head li:last").css("border","none");
	    $(".main3 li:last").css("margin-right","0");
	    $(".licai li:last").css("margin-right","0"); 
	    //债券转让协议 
	    $("#a_protocal_1").click(function(){
	        $("#rz-box-bg3").show();
	        $("#protocol1").show();
	        offsetDiv("#protocol1");
	        //a_protocal_1();
	    });
	    //借款协议 
	    function a_protocal_1(){
	        var tId=$("#tradingId").val();
	        $.ajax({
	                type : "post",
	                url:'/pdf/getPdfPath',
	                data:'tId='+tId,
	                dataType:'json',
	                success : function(data) {
	                 if(data.code==200){
	                     window.location.href="/pdf/PdfPath?tId="+tId;
	                 }else{
	                     cusAlert(data.msg);
	                 }
	            },
	            error : function(data) {
	                alert(data);
	            }
	        });
	    }
	    
	    $(".a_go1").click(function(){
	        $("#rz-box-bg3").hide();
	        $("#protocol1").hide();
	        $(".input_agree").attr("checked",true);
	        $(".ok").attr("disabled", false);
	        $(".ok").css({
	            "cursor" : "pointer",
	            "background" : "#ea5513",
	            "color" : "#fff"
	        });
	    });
	    
	    //风险提示 
	    $("#a_protocal_2").click(function(){
	        $("#rz-box-bg").show();
	        $("#protocol2").show();
	        offsetDiv("#protocol2");
	    });
	    
	    //免责声明 
	    $("#a_protocal_3").click(function(){
	        $("#rz-box-bg").show();
	        $("#protocol3").show();
	        offsetDiv("#protocol3");
	    });
	    
	   //计算器 
	    $(".jsq_nei").click(function(){
	        offsetDivC(".calculator");
	        $(".calculator").slideDown(1000);
	        $("#rz-box-bg").show();
	        resets();
	    });
	    
	 // 弹框关闭事件 START =========================================
	    $(".jtClose").click(function(){
	        $("#buyerInfoValidDialog").hide();
	        $("#rz-box-bg").hide();
	    });
	    
	    $(".hx_close").click(function(){
	        $("#hxAccountDialog").hide();
	        $("#rz-box-bg").hide();
	    });
	    
	    $(".rz-close1").click(function(){
	        $("#rz-box-bg3").hide();
	        $("#protocol1").hide();
	    });
	    
	    $(".rz-close,.a_go").click(function(){
	        $("#rz-box-bg").hide();
	        $("#protocol2").hide();
	        $("#protocol3").hide();
	        $("#protocol4").hide();
	        $("#takeHeart_div_cz").hide();
	    });
	    
	    $(".rz-close, .reset, .ensure").click(function() {
	        $("#cart").hide();
	        $("#rz-box-bg").hide();
	        $("#div_cz").hide();
	        $("#deep").hide();
	        $("#tou_xqd").hide();
	        $("#zhc").hide();
	        $("#butOverLimit").hide();
	        $("#increasedRates").hide();
	    });
	    
	    $(".rz-close, #ok_button").click(function(){
	        $("#rz-box-bg").hide();
	        $("#div_customer").hide();
	        $("#div_customer_tip").hide();
	        $("#increasedRates").hide();
	    });
	    // 弹框关闭事件 END ==========================================
	    
	   // 《同意阅读协议》勾选框
	    $(".input_agree").click(function() {
	        if ($(this).attr("checked")) {
	            $(".ok").attr("disabled", false);
	            $(".ok").css({
	                "cursor" : "pointer",
	                "background" : "#ea5513",
	                "color" : "#fff"
	            });
	        } else {
	            $(".ok").attr("disabled", true);
	            $(".ok").css({
	                "cursor" : "default",
	                "background" : "#eee",
	                "color" : "#535353"
	            });
	        }
	    });
	});
	
	//自定义alert
	function cusAlert(msg) {
	    $("#tou_xqd").hide();
	    $("#rz-box-bg").show();
	    $("#div_customer_tip").show();
	    offsetDiv("#div_customer_tip");
	    $("#div_customer_tip h2").text("提示");
	    $("#div_customer_tip p:eq(0)").html(msg);
	}
	
	// 购买金额是否合法
	function buyMoneyAvailable() {
	    $('#sp').html("");
	    var minMoney = $('#minMoney').val(); //起投金额 
	    var toumoney = $('#toumoney').val(); //剩余可购金额 
	    var buyMoney = $('#buyMoney').val(); //购买金额 
	    var increaMoney = $('#increaMoney').val();
	    var buyLimit = $("#buyLimit").val(); //购买限额 
	    var regex = /^([1-9]\d*|0)(\.\d{1,2})?$/;
	    var med = null;
	    var p_rule = $('#rule').val();//投资规则，0:金额限制，1:时间限制，2:金额时间限制，3:无限制
	    
	    if (regex.test(buyMoney)) {
	        if (!isNaN(toumoney) && (parseFloat(toumoney) - parseFloat(buyMoney) < 0)) {
	            $('#sp').css("display", "block");
	            $('#sp').html("<font color='red'>输入金额不能大于产品剩余可购金额!</font>");
	            moneyValid = false;
	            return false;
	        }
	        
	        if (!isNaN(buyMoney) && buyLimit > 0 && (parseFloat(buyMoney) > parseFloat(buyLimit))) {
	            $('#sp').css("display", "block");
	            $('#sp').html("<font color='red'>该产品每人限购"+ buyLimit +"元</font>");
	            moneyValid = false;
	            return false;
	        }
	        
	        if (parseFloat(buyMoney) < parseFloat(increaMoney)) {
	            $('#sp').css("display", "block");
	            $('#sp').html("<font color='red'>起投金额不能小于" + increaMoney + "元!</font>");
	            moneyValid = false;
	            return false;
	        }
	        
	        if (increaMoney != 0) {
	            if ((parseFloat(buyMoney) % increaMoney) != 0) {
	                $('#sp').css("display", "block");
	                $('#sp').html("<font color='red'>输入的数据不符合要求!</font>");
	                moneyValid = false;
	                return false;
	            }
	        }
	        
	        $('#sp').css("display", "none");
	        moneyValid = true;
	        return true;    
	    } else {
	        
	        $('#sp').css("display", "block");
	        $('#sp').html("<font color='red'>输入的数据不符合要求!</font>");
	        moneyValid = false;
	        return false;
	    }
	}
	
	//立即购买 
    $(".detail_buy").click(function() {
        var pCode = $('#code').val();
        var buyMoney = $("#buyMoney").val();
        
        var reg = /\d+/g;
        if (!moneyValid) {
            $('#sp').css("display", "block");
            $('#sp').html("<font color='red'>输入的数据不符合要求!</font>");
            
        } else {
            $.ajax({
                url : '/product/buyProduct',
                type : 'post',
                data : {lastbuyMoney : buyMoney, prCode : pCode},
                success : function (data) {
                    if (data.code == 3009) {//未登录 
                        $("#zhc").show();
                        $("#rz-box-bg").show();
                        offsetDiv("#zhc");
                        return false;
                    }
                    
                    if (data.code == 3030) {//用户验证提示 
                        cusAlert("您目前还有未支付的产品，请您到【我的理财-待支付】进行处理");
                        $("#ok_button").attr("href","/myFinancial/finanCialManage?status=0").text("确定");
                        return false;
                    }
                    
                    // 剩余错误提示 
                    if (data.code != 200) {
                        cusAlert(data.msg);
                        return false;
                    } else {
                        var tid = data.obj.tId;
                        // 确认支付表单展示数据
                        $("#tradingId").val(tid);
                        // 实际理财金额，扣除手续费 
                        var code_back = sendReq("/product/financialMoney",
                                "money=" + buyMoney + "&code=" + pCode);
                        if(code_back=="code:500") {
                            cusAlert("系统错误");
                            return false;
                        }
                        
                        var redPacketNumber = $("#redPacketNumber").text();
                        var reg = /\d+/g;
                        var num = redPacketNumber.match(reg);
                        if (null != num && redPacketNumber.indexOf("返现红包") > 0){
                            $("#redpacket").text(num +"元（产品成立发放至您的账户余额）");
                        } else {
                            $("#redpack").hide();
                        }
                        
                        $("#licmoney").text(fmoney(code_back, 2));
                        $("#zhimoney").text(fmoney(buyMoney, 2));
                        $("#lastbuyMoney").val(buyMoney);
                        
                        $("#rz-box-bg").show();
                        $("#tou_xqd").show();
                        offsetDiv("#tou_xqd");
                    }
                }
                
            });
        }
    });
	
    // 滑动门
    function changeTab3(index) {
        $("#changTab").val(index);
        for (var i = 1; i <= 2; i++) {
            document.getElementById("f_" + i).className = "normal";
            document.getElementById("f_" + index).className = "selected";
            document.getElementById("f" + i).style.display = "none";
        }
        document.getElementById("f" + index).style.display = "block";
    }
    
    function subbuy() {
//      $("#buyTime").val($("#hello").val());
        $("#sbidForm").submit();
        $("#tou_xqd").hide();
        $("#div_cz").show();
        offsetDiv("#div_cz");
        $("#rz-box-bg").show();
        return false;
    }
	</script>
</body>
</html>