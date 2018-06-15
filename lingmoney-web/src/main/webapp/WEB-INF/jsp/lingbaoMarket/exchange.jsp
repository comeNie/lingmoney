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
    <link rel="stylesheet" type="text/css" href="/resource/css/lingbaoMarketCss/market.css"/>
    <script type="text/javascript" src="/resource/js/lingbaoMarketJs/public.js"></script>
    <script type="text/javascript" src="/resource/js/lingbaoMarketJs/manageAddress.js"></script>
	<script type="text/javascript" src="/resource/js/lingbaoMarketJs/exchange.js"></script>
	<script type="text/javascript" src="/resource/js/jsAddress.js"></script>
</head>
<body>
<jsp:include page="/common/kefu.jsp"></jsp:include>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
   <div class="market-wrap" style="background:none">
      <input type="hidden" id="exchangeNumber" value="${gift.exchangeNumber }">
      <div class="sitemap">
        <a href="/index">领钱儿</a> > <a href="/lbmarket/index">我的领地</a> > <span>${gift.name }</span>
      </div>

   	  <div class="content-top-detail clearfix">
   	  	<div class="left">
   	  		<img src="${gift.pictureBig }" width="299" height="348"/>
   	  	</div>
   	  	<div class="right">
   	  	  <form>
   	  	  	   <input id="giftId" type="hidden" value="${gift.id }">
   	  	  	   <input id="store" type="hidden" value="${gift.store }">
   	  	  	   <input id="typeId" type="hidden" value="${gift.typeId }">
   	  	  	   <input id="exchangeNumber" type="hidden" value="${gift.exchangeNumber }">
   	  	       <h3>${gift.name }</h3>
	           <p style="color:#ea5513;line-height:25px;height:25px;"></p>
	           <div class="price">
	              <div style="width:60px;color:#ea5513">领宝</div>
	              <c:choose>
	              	<c:when test="${null!=gift.preferentialIntegral && gift.preferentialIntegral>0 }">
	              		<div id="lingbaoSpan" style="width:102px;font-size:24px;color:#fff;font-family:Arial">
	              			${gift.preferentialIntegral }
	              		</div>
	              		<input id="integral" type="hidden" value="${gift.preferentialIntegral }">
	              	</c:when>
	              	<c:otherwise>

	              	 	<div id="lingbaoSpan" style="width:102px;font-size:24px;color:#fff;font-family:Arial">
	              			${gift.integral }
	              		</div>
	              		<input id="integral" type="hidden" value="${gift.integral }">
	              	</c:otherwise>
	              </c:choose>
	           </div>
	           <div class="count-song-box clearfix">
	             <span class="tb-name">选择数量：</span>
	             <c:choose>
	             	<c:when test="${gift.exchangeNumber==1 }">
	             		<span class="tb-count">
			                 <a onclick="subtractNum($(this))" href="javascript:void(0)" title="减1" class="limit">-</a>
			                 <input id="num" value="1" title="请输入购买量" readonly="readonly"/>
			                 <a onclick="addNum($(this))" href="javascript:void(0)" title="加1" class="limit">+</a>
			             </span>
	             	</c:when>
	             	<c:when test="${gift.store==0 }">
	             		<span class="tb-count">
			                 <a href="javascript:void(0)" title="减1" class="limit">-</a>
			                 <input id="num" value="0" disabled="disabled"/>
			                 <a href="javascript:void(0)" title="加1" class="limit">+</a>
			             </span>
	             	</c:when>
	             	<c:otherwise>
	             		<span class="tb-count">
			                 <a onclick="subtractNum($(this))" href="javascript:void(0)" title="减1" class="limit">-</a>
			                 <input id="num" onblur="numBlur()" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))"  type="text" value="1" maxlength="8" title="请输入购买量"/>
			                 <a onclick="addNum($(this))" href="javascript:void(0)" title="加1">+</a>
			             </span>
	             	</c:otherwise>
	             </c:choose>
	           </div>
	           <div class="color-song-box clearfix">
	             <span class="tb-name">规格描述：</span>
	             <span>${gift.propertyDesc }</span>
	           </div>
	           <p style="font-size:12px;color:#989898;line-height:25px;">图片仅供参考，实际兑换礼品请以实物或您获取的服务为准</p>
	           <a href="javascript:void(0)" onclick="addToShoppingCart()" class="detial-btn">加入购物车</a> 
	           <c:choose>
	             	<c:when test="${gift.store==0 }">
	             		<a href="javascript:void(0)" class="detial-btn exchange btn_unable">立即兑换</a>
	             	</c:when>
	             	<c:otherwise>
	             		<a href="javascript:void(0)" onclick="validateExchange()" class="detial-btn exchange">立即兑换</a>
	             	</c:otherwise>
	             </c:choose>
	             <a href="/lingbaoExchange/exchangeRecord?type=1" style="text-decoration:underline; margin-left:5px; vertical-align:bottom;color:#e95412; padding-bottom:5px;">去<span style="vertical-align:bottom;">购物车</span>结算&gt;&gt;</a>
   	  	  </form>
   	  	</div>
   	  </div>

   	  <%-- <div class="content-middle-detail clearfix">
   	  	 <ul class="tab clearfix">
   	  	    <li class="clicked">礼品介绍</li>
   	  	    <li>规格参数</li>
   	  	 </ul>
   	  	 <div class="cons">
   	  	    <div class="show">
   	  	       ${gift.introduction }
   	  	    </div>
   	  	    <div>
   	  	       ${gift.property }
   	  	      <!-- <ul>
   	  	         <li class="clearfix">
   	  	            <span class="label">礼品概述</span>
   	  	            <span>产品特点：中长方形保鲜盒,适合存放各种小菜类,水果类,蔬菜类,三明治等</span>
   	  	         </li>
   	  	         <li class="clearfix">
   	  	            <span class="label">礼品保修</span>
   	  	            <span>如有质量问题，可接受7天退换货（保证商品包装完好，不影响二次销售）客服热线：400 888 0866</span>
   	  	         </li>
   	  	      </ul>  -->
   	  	    </div>
   	  	 </div>
   	  </div> --%>
   </div>
   
    <!-- 底部开始 -->
	  <jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	
<!--确认兑换  -->
<div id="rz-box-bg"></div>
<div class="rz-box-con exchangeDialog" id="exchangeDialog">
	<input id="giftType" type="hidden">
	<div class="rz-title">
	    <h2>立即兑换</h2>
		<a href="javascript:void(0)" class="rz-close">
			<img src="/resource/images/img_rz-close.png" />
		</a>
	</div>
	<div class="exchangeDialog_con">
	   <div class="exchangeDialog_lingbao">
	      <span style="margin-left:245px;line-height: 70px;width:122px;font-size:18px;color:#ea5513">所需支付领宝</span>
	      <span id="spanLingbaoCost" style="width:149px;font-size:36px;font-family: arial">3500</span>
	   </div>
	   <div id="exchangeDialogAddress" class="exchangeDialog_address">
	     
	   </div>
	   
	   <div id="exchangeEditorAddr" class="editor-addr">
	   	  <!-- 用于判断是否初次添加地址 -->
	   	  <input id="isFirstAddress" type="hidden" value="0">
	      <p>
	      	 <input id="saveType" type="hidden" value="update" />  
	         <span style="float:left">修改收货地址</span>   
	         <a href="javascript:void(0)" id="save" class="editor-addr_btn">编辑完成</a>
	      </p>
	      <div class="form-addAddress">
	        <div>
	        	 <input id="addressId" type="hidden">
	        	 <input id="addressDefault" type="hidden"> 
				 <input id="addressStatus" type="hidden"> 
	             <span class="label">*收货人姓名：</span>
		         <input type="text" id="name" onblur="testName(this.value)"/>
		         <span id="nameTip" class="tip"></span>
		         <span class="label">*收货人电话：</span>
		         <input type="text" id="tel" onblur="testTel(this.value)"/>
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
		         <input type="text" id="addressDetail" onblur="testAddress(this.value)" style="width:666px;"/>
		         <span id="addressDetailTip" class="tip" style="margin-left:67px;"></span>
	        </div>
	      </div>
	   </div>
	</div>
	
	<div class="btns">
	  <a href="javascript:void(0)"  onclick="exchangeGift()">确认兑换</a>
	</div>
</div>

<div class="rz-box-con exchangeSuccessDialog" id="exchangeSuccessDialog" style="width:400px; padding-bottom:0px; height:230px; margin:-125px -200px;border:1px solid #ea5413;">
	<div class="rz-title" style="height: 40px; line-height: 40px;">
	    <h2 style="line-height: 40px;">兑换成功</h2>
		<a href="javascript:void(0)" class="rz-close"  style="padding-top: 4px;margin-right: 13px;">
			<img src="/resource/images/img_rz-close.png" />
		</a>
	</div>
	<div class="exchangeSuccess_con" style="width:400px;"></div>  <!--.exchangeFail_con 兑换失败  -->
</div>
<div class="rz-box-con exchangeSuccessDialog" id="exchangeFailDialog" style=" width:400px; padding-bottom:0px; height:250px; margin:-125px -200px;border:1px solid #ea5413;">
	<div class="rz-title" style="height: 40px; line-height: 40px;">
	    <h2 style="line-height: 40px;">兑换失败</h2>
		<a href="javascript:void(0)" class="rz-close"  style="padding-top: 4px;margin-right: 13px;">
			<img src="/resource/images/img_rz-close.png" />
		</a>
	</div>
	<div class="exchangeSuccess_con exchangeFail_con"></div>  <!--.exchangeFail_con 兑换失败  -->
</div>
<div class="rz-box-con exchangeSuccessDialog" id="addToGiftCartAlertDiv" style=" width:400px; padding-bottom:0px; height:200px; margin:-100px -200px;">
	<div class="rz-title">
	    <h2>提示</h2>
		<a href="javascript:void(0)" class="rz-close">
			<img src="/resource/images/img_rz-close.png" />
		</a>
	</div>
	<div class="exchangeSuccess_con exchangeFail_con">
		<img alt="" src="/resource/images/lingbaoMarketImages/tjgwccg.jpg">
	</div>  <!--.exchangeFail_con 兑换失败  -->
</div>

<div class="rz-box-con addBuyCar" id="addBuyCar">
	<div class="rz-title">
	    <h2>提示</h2>
		<a href="javascript:void(0)" class="rz-close">
			<img src="/resource/images/img_rz-close.png" />
		</a>
	</div>
	<div class="addBuyCar_con">
       <h1></h1>
       <a href="javascript:void(0)" class="rz-button">确认</a>	   
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