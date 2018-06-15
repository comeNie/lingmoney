<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","No-cache");
response.setDateHeader("Expires", -1);
response.setHeader("Cache-Control","No-store");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
	<title>我的领地</title>
	<link rel="stylesheet" href="/resource/css/index.css">
	<link rel="stylesheet" type="text/css" href="/resource/css/style.css" media="screen" />
	<link rel="icon" type="image/x-icon" href="/resource/ico.ico">
	<link rel="shortcut icon" type="image/x-icon" href="/resource/ico.ico">
	<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="/resource/js/lingbaoMarketJs/public.js"></script>
	<script type="text/javascript" src="/resource/js/index.js"></script>
	<script type="text/javascript" src="/resource/js/jquery.SuperSlide.2.1.2.js"></script>
    <link rel="stylesheet" type="text/css" href="/resource/css/lingbaoMarketCss/market.css"/>
    <script type="text/javascript" src="/resource/js/jsAddress.js"></script>
    <script type="text/javascript" src="/resource/js/lingbaoMarketJs/indexMore.js"></script>
    <script type="text/javascript" src="/resource/js/lingbaoMarketJs/manageAddress.js"></script>
	<script type="text/javascript" src="/resource/js/lingbaoMarketJs/receiptAddress.js"></script>
	<script type="text/javascript" src="/resource/js/lingbaoMarketJs/public.js"></script>
	<script type="text/javascript" src="/resource/js/ajax.js"></script>
	<style type="text/css">
		.pagination {padding: 5px;font-size:12px;text-align:right}
		.pagination a, .pagination a:link, .pagination a:visited {padding:3px 8px;margin:0 5px;border:1px solid #ddd;text-decoration:none;color:#434343;}
		.pagination a:hover, .pagination a:active {background:#204c6f;color: #fff;text-decoration: none;}
		.pagination span.current {padding:3px 8px;margin:0 5px;background:#204c6f;font-weight: bold;color: #fff;}
		.pagination span.disabled {padding: 3px 8px;margin:0 5px;border: 1px solid #eee; color: #ddd;}
	</style>
</head>
<body>
<jsp:include page="/common/kefu.jsp"></jsp:include>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
    <div class="market-wrap">
    <!-- 领宝商城头部 -->
	<jsp:include page="/common/lingbaoheard.jsp"></jsp:include>

   	  <div class="content-middle-select clearfix">
   	  	 <input type="hidden" id="typeFilter" value="${checkedType }">
   	  	 <input type="hidden" id="countFilter">
   	  	 <dl class="clearfix" id="typePick" style="margin-bottom:23px;">
           <dt>分类</dt>
           <dd onclick="setTypeFileter('-1',$(this))" >全部</dd>
           <c:if test="${typeList!=null }">
           	<c:forEach items="${typeList }" var="list">
           		<c:choose>
           			<c:when test="${list.id==checkedType }">
           				 <dd class="clicked" onclick="setTypeFileter('${list.id}',$(this))">${list.name }</dd>
           			</c:when>
           			<c:otherwise>
           				<dd onclick="setTypeFileter('${list.id}',$(this))" >${list.name }</dd>
           			</c:otherwise>
           		</c:choose>
           	</c:forEach>
           </c:if>
         </dl>
         <dl class="clearfix" id="rangePcik">
           <dt>按领宝浏览</dt>
           <dd onclick="setCountFilter('0-9999999',$(this))">全部</dd>
           <dd onclick="setCountFilter('0-5000',$(this))">0-5000</dd>
           <dd onclick="setCountFilter('5001-10000',$(this))">5001-10000</dd>
           <dd onclick="setCountFilter('10001-20000',$(this))">10001-20000</dd>
           <dd onclick="setCountFilter('20001-30000',$(this))">20001-30000</dd> 
           <dd onclick="setCountFilter('30001-40000',$(this))">30001-40000</dd>
           <dd onclick="setCountFilter('40001-50000',$(this))">40001-50000</dd>
           <dd onclick="setCountFilter('50001-9999999',$(this))">50000以上</dd>
         </dl>
   	  </div>
      
      <div class="content-bottom-more clearfix" id="giftList">
      		<c:if test="${giftList!=null }">
      			<c:forEach items="${giftList }" var="list" varStatus="status">
      				<c:choose>
      					<c:when test="${(status.index+1)%4==0 }">
      						<div class="item-classification last">
			    				<img title="${list.name }" src="${list.pictureMiddle }" width="220" height="228"/>
			    				<p title="${list.name }">${list.name }</p>
			    				<div class="clearfix">
			    					<span class="left">${list.integral }领宝</span>
			    					<a href="/lingbaoExchange/exchange?id=${list.id }" class="right">立即兑换</a>
			    				</div>
			   	  			</div>
      					</c:when>
      					<c:otherwise>
      						<div class="item-classification">
			    				<img title="${list.name }" src="${list.pictureMiddle }" width="220" height="228"/>
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
			    					<a href="/lingbaoExchange/exchange?id=${list.id }" class="right">立即兑换</a>
			    				</div>
			   	  			</div>
      					</c:otherwise>
      				</c:choose>
      			</c:forEach>
      		</c:if>
      		<c:if test="${giftList==null || empty giftList }">
      			<h3>暂无数据</h3>
      		</c:if>
      </div>
      <div class="page">
			<div id="pages" class="pagination">
				<c:choose>
					<c:when test="${total==0 }">
					</c:when>
					<c:otherwise>
						&nbsp;共<strong>${total }</strong>项,<strong><fmt:formatNumber value="${pages }" pattern="#"/></strong>页:&nbsp;
						<c:if test="${pages!=null && pages>1 }">
							<c:choose>
								<c:when test="${currentPage==1 }">
									<span class="disabled">«&nbsp;上一页</span>
								</c:when>
								<c:otherwise>
									<a href="javascript:filterSearch(${currentPage+1 })">«&nbsp;上一页</a>
								</c:otherwise>
							</c:choose>
							<c:forEach var="page" begin="1" end="${pages }">
								<c:choose>
									<c:when test="${page==currentPage }">
										<span class="current">${currentPage }</span>
									</c:when>
									<c:otherwise>
										<a href="javascript:filterSearch(${page })">${page }</a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:choose>
								<c:when test="${currentPage==pages }">
									<span class="disabled">下一页&nbsp;»</span>
								</c:when>
								<c:otherwise>
									<a href="javascript:filterSearch(${currentPage+1 })">下一页&nbsp;»</a>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${pages==1 }">
							<span class="disabled">«&nbsp;上一页</span>
							<span class="current">1</span>
							<span class="disabled">下一页&nbsp;»</span>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
      </div>
   </div>
   
    <!-- 底部开始 -->
	  <jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	
<!-- 地址弹框START -->	
<div id="rz-box-bg"></div>
<div class="rz-box-con changeAddr" id="changeAddr">
	<div class="rz-title">
		<h2>我的地址</h2>
		<a href="javascript:void(0)" class="rz-close">
		   <img src="/resource/images/img_rz-close.png" />
		</a>
	</div>
	<div class="changeAddr_con">
	   <div id="receiptAddressDiv" class="has_addr">
	   </div>
	   
	   <div class="addAddress">
	      <p>新增收货地址</p>
	      <div class="form-addAddress">
	        <div>
	        	 <input id="addressId" type="hidden">
	        	 <input id="addressDefault" type="hidden"> 
				 <input id="addressStatus" type="hidden">
	             <span class="label">*收货人姓名：</span>
		         <input type="text" id="name" style="margin-right:30px;"/>
		         <span id="nameTip" class="tip"></span>
		         <span class="label">*收货人手机号：</span>
		         <input type="text" id="tel" />
		         <span id="telTip" class="tip"></span>
	        </div>
	        <div>
	             <span class="label">*选择地区：</span>
		         <select class="select-box" id="province" name="province"></select> 
		         <select class="select-box" id="city" name="city"></select> 
		         <select class="select-box" id="town" name="town"></select>
	        </div> 
	        <div>
	             <span class="label">*详细地址：</span>
		         <input type="text" id="addressDetail"  style="width:666px;"/>
		         <span id="addressDetailTip" class="tip" style="margin-left:67px;"></span>
	        </div>
	      </div>
	   </div>
	   
	   <div class="btns-addAddress">
	      <a href="javascript:void(0)" onclick="updateAddress()" style="width:146px;background:#ea560e">保存收货人地址</a>
	      <a href="javascript:void(0)" style="width:81px;background:#ccc">取消</a>
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
			<p id="successContent" style="margin-bottom: 0; line-height: 50px; height: 50px; font-size: 18px;">恭喜您今日签到成功！</p>
			<p id="successQianP" style="font-size: 24px; margin-bottom: 20px;">+5领宝</p>
			<a href="javascript:void(0)" class="rz-button">确定</a>
		</div>
	</div>

<div class="rz-box-con " id="zhc">
	<div class="rz-title">
		<h2>登录</h2>
	</div>
	<p class="color" style="padding-top: 30px; font-size: 16px; color: #c54107">您还未登录，请先登录。</p>
	<p style="padding-top: 10px;">
		<a href="/login" class="rz-button">去登录</a>
		<a href="javascript:void(0)" class="rz-button reset">取消</a>
	</p>
</div>
</body>
</html>