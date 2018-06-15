<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "No-cache");
	response.setDateHeader("Expires", -1);
	response.setHeader("Cache-Control", "No-store");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>我的领地</title>
<link rel="stylesheet" href="/resource/css/index.css">
<link rel="stylesheet" type="text/css" href="/resource/css/style.css" media="screen" />
<link rel="icon" type="image/x-icon" href="/resource/ico.ico">
<link rel="shortcut icon" type="image/x-icon" href="/resource/ico.ico">
<link rel="stylesheet" type="text/css" href="/resource/css/lingbaoMarketCss/market.css" />
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/resource/js/jsAddress.js"></script>
<script type="text/javascript" src="/resource/js/index.js"></script>
<script type="text/javascript" src="/resource/js/jquery.SuperSlide.2.1.2.js"></script>
<script type="text/javascript" src="/resource/js/lingbaoMarketJs/index.js"></script>
<script type="text/javascript" src="/resource/js/lingbaoMarketJs/manageAddress.js"></script>
<script type="text/javascript" src="/resource/js/lingbaoMarketJs/receiptAddress.js"></script>
<script type="text/javascript" src="/resource/js/lingbaoMarketJs/public.js"></script>
<script type="text/javascript" src="/resource/js/ajax.js"></script>
</head>
<body>
	<jsp:include page="/common/kefu.jsp"></jsp:include>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<div class="market-wrap">
		<!-- 领宝商城头部 -->
		<jsp:include page="/common/lingbaoheard.jsp"></jsp:include>
		
		<div class="content-middle clearfix">
			<div class="left">
				<div class="list-title">兑换目录</div>
				<ul class="duihuan-list">
					<c:forEach var="i" begin="0" end="4">
						<c:choose>
							<c:when test="${giftlsit[i]!=null }">
								<li><a href="/lbmarket/queryMore?typeId=${giftlsit[i].typeId }">${giftlsit[i].typeName }</a></li>
							</c:when>
							<c:otherwise>
								<li style="border:none;"></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
				<div class="list-title list-title-ranking">兑换排行榜</div>
				<ul class="duihuan-ranking">
					<c:if test="${topSellList!=null }">
						<c:forEach items="${topSellList }" var="list">
							<li class="clearfix">
								<a title="${list.name }" href="/lingbaoExchange/exchange?id=${list.id }" class="clearfix"> 
								<img src="${list.pictureSmall }" width="69" height="57" /> 
								<span> 
									<em style="padding-top: 17px;">
									<c:choose>
										<c:when test="${fn:length(list.name)>8 }">
											${fn:substring(list.name, 0, 7)}...
										</c:when>
										<c:otherwise>
											${list.name}
										</c:otherwise>
									</c:choose> 
									</em>
									<c:choose>
										<c:when test="${null!=list.preferentialIntegral && list.preferentialIntegral>0 }">
											<em title="${list.preferentialIntegral }领宝 " style="padding-top: 5px; color: #ea5513;width:104px; height:20px;overflow: hidden;">
												<i style="text-decoration: line-through;font-size: xx-small;display: inline;margin-left:5px;">${list.integral }</i>
												${list.preferentialIntegral }领宝 
											</em>
										</c:when>
										<c:otherwise>
											<em style="padding-top: 5px; color: #ea5513;">${list.integral }领宝</em>
										</c:otherwise>
									</c:choose>
								</span>
								</a>
							</li>
						</c:forEach>
					</c:if>
				</ul>
			</div>
			<div class="right">
				<div class="title">热门兑换区</div>
				<div class="box-item clearfix">
					<c:if test="${recomList!=null }">
						<c:forEach items="${recomList }" var="list">
							<div class="item">
								<img title="${list.name }" src="${list.pictureMiddle }" width="242" height="242" />
								<p title="${list.name }">${list.name }</p>
								<div class="clearfix">
									<c:choose>
										<c:when test="${null!=list.preferentialIntegral && list.preferentialIntegral>0 }">
											<span class="left" >
												<em style="text-decoration: line-through;font-size: xx-small;">${list.integral }</em>
												${list.preferentialIntegral }领宝 
											</span>
										</c:when>
										<c:otherwise>
											<span class="left">${list.integral }领宝 </span>
										</c:otherwise>
									</c:choose>
									<a href="/lingbaoExchange/exchange?id=${list.id }"
										class="right">立即兑换</a>
								</div>
							</div>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</div>

		<div class="content-bottom">
			<c:if test="${giftlsit!=null }">
				<c:forEach items="${giftlsit }" var="list">
					<div class="title">${list.typeName }
						<a href="/lbmarket/queryMore?typeId=${list.typeId }">更多>></a>
					</div>
					<div class="clearfix">
						<c:if test="${list.value!=null }">
							<c:forEach items="${list.value }" var="giftlist"
								varStatus="status">
								<c:if test="${status.index<4 }">
									<c:choose>
										<c:when test="${status.index==3 }">
											<div class="item-classification last">
										</c:when>
										<c:otherwise>
											<div class="item-classification">
										</c:otherwise>
									</c:choose>
									<img title="${giftlist.name }" src="${giftlist.pictureMiddle }" width="220" height="228" />
									<p title="${giftlist.name }">${giftlist.name }</p>
									<div class="clearfix">
										<c:choose>
											<c:when test="${null!=giftlist.preferentialIntegral && giftlist.preferentialIntegral>0 }">
												<span class="left" >
													<em style="text-decoration: line-through;font-size: xx-small;">${giftlist.integral }</em>
													${giftlist.preferentialIntegral }领宝 
												</span>
											</c:when>
											<c:otherwise>
												<span class="left">${giftlist.integral }领宝 </span>
											</c:otherwise>
										</c:choose>
										<a href="/lingbaoExchange/exchange?id=${giftlist.id }"
											class="right">立即兑换</a>
									</div>
									</div>
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->

	<!-- 协议弹框 -->
	<div id="rz-box-bg"></div>
	<input id="saveType" type="hidden" value="add" />  
	<!-- 用于判断是否初次添加地址 -->
	<input id="isFirstAddress" type="hidden" value="0">
	<div class="rz-box-con changeAddr" id="changeAddr">
		<div class="rz-title">
			<h2>我的地址</h2>
			<a href="javascript:void(0)" class="rz-close"> <img
				src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<div class="changeAddr_con">
			<div id="receiptAddressDiv" class="has_addr"></div>

			<div class="addAddress">
				<p id="addTitle">新增收货地址</p>
				<!-- <p id="editorTitle" style="display: none">
					<span style="float: left" >新增收货地址</span>
					<a href="javascript:void(0)" class="editor-addr_btn">编辑完成</a>
				</p> -->
				<div class="form-addAddress">
					<div>
						<input id="addressId" type="hidden"> 
						<input id="addressDefault" type="hidden"> 
						<input id="addressStatus" type="hidden"> <span class="label">*收货人姓名：</span>
						<input type="text" onblur="testName(this.value)" id="name" style="margin-right: 30px;" /> 
						<span id="nameTip" class="tip"></span>
						<span class="label">*收货人手机号：</span> 
						<input type="text" id="tel" onblur="testTel(this.value)" /> 
						<span id="telTip" class="tip"></span>
					</div>
					<div>
						<span class="label">*选择地区：</span> <select class="select-box"
							id="province" name="province"></select> <select
							class="select-box" id="city" name="city"></select> <select
							class="select-box" id="town" name="town"></select>
					</div>
					<div>
						<span class="label">*详细地址：</span> <input type="text"
							id="addressDetail" onblur="testAddress(this.value)"
							style="width: 666px;" /> <span id="addressDetailTip" class="tip"
							style="margin-left: 67px;"></span>
					</div>
				</div>
			</div>

			<div class="btns-addAddress">
				<a id="save" href="javascript:void(0)" style="width: 146px; background: #ea560e">保存</a> 
				<a href="javascript:void(0)" style="width: 81px; background: #ccc">取消</a>
			</div>
		</div>
	</div>
	<div class="rz-box-con successQian" id="successQian">
		<div class="rz-title">
			<h2></h2>
			<a href="javascript:void(0)" class="rz-close"> <img
				src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<div class="successQian_con">
			<div></div>
			<p id="successContent"
				style="margin-bottom: 0; line-height: 50px; height: 50px; font-size: 18px;">恭喜您今日签到成功！</p>
			<p id="successQianP" style="font-size: 24px; margin-bottom: 20px;">+5领宝</p>
			<a href="javascript:void(0)" class="rz-button">确定</a>
		</div>
	</div>
	<div class="rz-box-con " id="zhc">
		<div class="rz-title">
			<h2>登录</h2>
		</div>
		<p class="color"
			style="padding-top: 30px; font-size: 16px; color: #c54107">您还未登录，请先登录。</p>
		<p style="padding-top: 10px;">
			<a href="/login" class="rz-button">去登录</a> <a
				href="javascript:void(0)" class="rz-button reset">取消</a>
		</p>
	</div>
</body>
</html>