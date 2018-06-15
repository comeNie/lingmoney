<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>安全保障</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1" />
	<link rel="stylesheet" href="/resource/css/guarantee.css">
    <script src="/resource/js/jquery-1.8.3.min.js"></script>
	<!-- <script src="/resource/js/guarantee.js"></script> -->
</head>
<body style="background:#f9f9f9">
<div class="main">
	<div class="guarantee-box">
		<p class="guarantee-box-tip">· 风险控制保障 ·</p>
		<div class="guarantee-box-img"><img src="/resource/images/guarantee1.png" style="width:2.96rem;"/></div>
		<p  class="guarantee-box-bt guarantee-suojin">
		所有项目都经过严谨的技术风控体系及规范的借贷风控体系，从项目源头开始筛选，并进行层层把控，通过独有的大数据债权匹配系统高效撮合理财资金和优质债权，兼顾收益和流动性。
		</p>
	</div>
	
	
	<div class="guarantee-box">
		<p class="guarantee-box-tip">· 完善的项目管理机制 ·</p>
		<div class="guarantee-box-img"><img src="/resource/images/guarantee2.png" style="width:2.49rem;"/></div>
		<p  class="guarantee-box-bt"><span>三重审查：</span>
1、项目团队实地对拟融资企业进行勘察；<br>
2、风险管理部对拟融资企业和融资标的所有资料、勘察报告进行初审； <br>
3、初审通过后，风控、财务、法务等部门联合成立审查组，着重对拟融资企业和融资项目的财务和经营情况进行深度复审。</p>
<p  class="guarantee-box-bt"><span>三重保障：</span>
1.审核通过后，由审批决策委员会，对拟融资企业和融资项目表决，表决实行一票否决制；<br>2.平台发布的每笔贷款业务均成立1对1贷后管理小组，定期对贷款企业的合同执行情况、借款情况、公司经营状况进行跟踪调查； <br>3.借贷项目到期前，贷后管理小组将严格监督借款人到期还款，为投资人规避资金偿还延期的风险。</p>
	</div>

    <div class="guarantee-box">
		<p class="guarantee-box-tip">· 账户安全保障 ·</p>
		<div class="guarantee-box-img"><img src="/resource/images/guarantee3.png" style="width:2.7rem;"/></div>
		<p  class="guarantee-box-bt "><span>信息一致：</span>
<p class="guarantee-suojin">用户需使用真实的姓名、身份证号和手机号码（手机号码为绑定银行卡预留手机号）进行注册。</p>
</p>
<p  class="guarantee-box-bt"><span>双重密码：</span>
<p class="guarantee-suojin">采取“登录密码+交易密码”双重密码模式，以确保您的账户安全。</p>
</p>

<p  class="guarantee-box-bt"><span>银行存管：</span>
	<div class="guarantee-box-img" style="padding-bottom:0.24rem;"><img src="/resource/images/guarantee4.jpg" style="width:6.56rem;"/></div>
</p>

	</div>
  
</div>
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
})(document, window);	
</script>
</body>
</html>