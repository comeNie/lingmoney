 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
 <!DOCTYPE html>
 <html lang="zh">
 <head>
 	<meta charset="utf-8">
 	<meta name="viewport" content="width=device-width,initial-scale=1">
 	<link rel="stylesheet" href="/resource/css/newYear.css">
 	<jsp:include page="/common/head.jsp"></jsp:include>
 </head>
 <body>
 	<script type="text/javascript">
		$(document).ready(function() {
			$("#phone").addClass("hover");
		})
	</script>
   <!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
   <div class="main-phone">
    <div class="banner_phones">
   	  <div class="box">
     	 <div class="ma"><img src="/resource/images/img_phoneMa.gif" width="170" height="170" /></div>
     	 <div class="btn_phone">
	      	<a href="https://itunes.apple.com/cn/app/%E9%A2%86%E9%92%B1%E5%84%BF/id1094553300?mt=8"  class="btn_phone_ios"></a>
	      	<a href="http://static.lingmoney.cn/download/LingQian_L.apk" class="btn_phone_android"></a>
         </div>
      </div> 
      
    </div>
    <div class="bottom_phone">
     	 <div class="box">
     	 	<ul class="clearfix">
     	 		<li>
     	 			<strong>理财随手更轻松</strong>
     	 			<p>无论身在何处  服务无处不在<br/>随手掌控资金  收益了然于心</p>
     	 		</li>
     	 		<li>
     	 			<strong class="bottom_phone_strong02">管理同步更便捷</strong>
     	 			<p>APP和PC两端理财数据共享资产<br/>记录一目了然，理财灵活更高效</p>
     	 		</li>
     	 		<li style="width:260px">
     	 			<strong class="bottom_phone_strong03">支付灵活更安全</strong>
     	 			<p>支持各大主流银行，无需网银、U盾<br/>采用第三方支付平台资金托管</p>
     	 		</li>
     	 		<li>
     	 			<strong class="bottom_phone_strong04">账户加密更安全</strong>
     	 			<p>理财资金100%物理隔离<br/>手势密码、信息加密保障账户安全</p>
     	 		</li>
     	 	</ul>
     	 </div>
    </div> 
   </div> 
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
 </body>
 </html>