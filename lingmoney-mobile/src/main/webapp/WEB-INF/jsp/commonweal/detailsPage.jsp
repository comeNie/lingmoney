<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name = "format-detection" content = "telephone=no">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
<title>公益项目详情</title>
<link rel="stylesheet" href="/resource/css/welfare.css">
<script src="/resource/js/jquery-1.8.3.min.js"></script>
<script src="/resource/js/tabbedContent.js"></script>
<script src="/resource/js/jquery.mobile-1.0a4.1.min.js"></script>
<script src="/resource/js/welfare.js"></script>
</head>
<body style="background:#f0f0f0">
<input id="token" type="hidden" value="${token}">
<input id="commonwealId" type="hidden" value="${commonwealId}">
<p class="welfare-box-tc" style="bottom:0.8rem;top:0%;position:fixed;"></p>
<div class="main">

<div class="welfare-details">
    <!-- 公益标题内容礼赞-->
    <div class="welfare-detailsimg1"><img alt="" src="${pbaPicture }"></div>
    <div class="welfare-details-box">
        <p class="welfare-details-box-tip">${pbaName }</p>
        <p class="welfare-details-box-content welfare-suojin">${pbaDesc }</p>
        <div class="welfare-details-box-jd">
            <ul>
                <li class="welfare-details-box-jd1">公益礼赞</li>
                <li class="welfare-details-box-jd2"><div class="welfare-jd"><div class="welfare-zhi"></div></div></li>
                <li class="welfare-details-box-jd3"><span class="fpoints">${sumPraise }</span>/<span class="points">${maxPraise }</span></li>
            </ul>
        </div>
    </div>
     <!-- 公益标题内容礼赞 end-->
    <!-- 公益介绍 -->
    <div class="welfare-details-box">
        <p class="welfare-details-box-tip">介绍</p>
        <p class="welfare-details-box-content"> ${pbaHtml}</p>
       
    </div>
</div>
</div>


<div class="welfare-love">
   <!-- 浮层 -->
   <div class="welfare-details-fc">
    <p class="welfare-close"><img src="/resource/images/welfare2.png"/></p>
    <div class="welfare-details-ja">
        <ul>
            <li class="welfare-add"><img src="/resource/images/welfare3.png"/></li>
            <li style="width:5.44rem;font-size:0.48rem;color:#FC724C;" class="welfare-input">1</li>
            <li class="welfare-reduce"><img src="/resource/images/welfare4.png"/></li>
        </ul>
    </div>
   </div>
   <!-- 浮层 end-->
   
    <ul class="welfare-loveul">
<!--         <li>我的爱心<b class="lovezhi">5</b></li> -->
<!--         <li>已捐赠<b>2</b></li> -->
<!--         <li>登录查看我的爱心</li> -->
<!--         <li><p class="welfare-botton">捐赠爱心</p> <p class="welfare-botton-ok">确定</p></li> -->
        
    </ul>
</div>
<div class="index-Popup-bg"></div>      



<script>
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
    
    queryUsersLoveNum();
    
})(document, window);   
</script>
</body>
</html>