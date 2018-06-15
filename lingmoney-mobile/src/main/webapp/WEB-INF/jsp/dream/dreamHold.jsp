<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
    <title>梦想</title>
    <link rel="stylesheet" href="/resource/css/dream.css">
   

</head>
<body style="background:#f0f0f0">

<div class="main">
   <input id="token" type="hidden" value="${token}">
   <input id="dreamid" type="hidden" value="${dreamid}">
   
   <input id="requiredAmount" type="hidden">
   <input id="accumulatedIncome" type="hidden">
   
   <div class="Dreamadd"><img alt="" src=""><div class="Dreamadd-jindu"></div></div>
   <p class="Dreamadd-tip"><span class="descOne" style="color:#4A4B5D;font-size:0.3rem;">价值20万汽车</span><span class="descTwo">行走的工具</span></p>

  
</div>
 <div class="error_tip"></div>
<p class="opendrem-money">已为梦想攒入<span class="accumulatedIncome">8</span><span>万元</span></p>
<p class="opendrem-hou">距离目标还差<span class="dreamDistance">2</span><span>万元</span>（收益），赶快加快梦想～</p>
<div class="opendrem">加快梦想</div>
<p class="opendrem-tips">温馨提示：以<span class="requiredAmount"></span>万元价格计算<br>
计算公式：投资本金*预期年化收益率／365*项目期限</p>
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/resource/js/deram.js"></script>
<script>
 $(function(){
      queryBaseDreamInfo();
      queryUsersDreamInfo();
      
      //计算滚动
      var heiGht =	(3.2/$('#requiredAmount').val())*$('#accumulatedIncome').val()
      $(".Dreamadd-jindu").animate({height:heiGht+'rem'}); 
       
      $('.opendrem').click(function(){
    	  window.location.href = "/gotoNewProductList?token="+$("#token").val()+'&dreamid='+$("#dreamid").val();
      });
      
    });
(function (doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            if(clientWidth>=750){
                docEl.style.fontSize = '100px';
            }else{
                docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
            }
        };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);   
</script>
</body>
</html>