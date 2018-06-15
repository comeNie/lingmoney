<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>风险测评</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1" />
	<link rel="stylesheet" href="/resource/css/assessment.css">
    <script src="/resource/js/jquery-1.8.3.min.js"></script>
	<script src="/resource/js/assessment.js"></script>
</head>
<body style="background:#f9f9f9">
	<input id="token" value="${token}" type="hidden">
	
<div class="main">

<div class="assessment-tip">所有题目均为单选，点击选定答案 <span><b class="assessment-page">1</b>/10</span></div>
<div class="assessment-box">
   <div class="assessment-block">
		 <div class="assessment-warp" data-index="" data-zfz="">
		<p><b>1、</b><span>您的主要收入来源是？</span></p>
		<ul>
			<li data-fen="2"><img src="/resource/images/assessment1.png"/><b>A.</b></b><span>工资、劳务报酬</span></li>
			<li data-fen="4"><img src="/resource/images/assessment1.png"/><b>B.</b><span>生产经营所得</span></li>
			<li data-fen="6"><img src="/resource/images/assessment1.png"/><b>C.</b><span>利息、股息、转让等金融性资产收入</span></li>
			<li data-fen="8"><img src="/resource/images/assessment1.png"/><b>D.</b><span>出租、出售房地产等非金融性资产收入</span></li>
			<li data-fen="10"><img src="/resource/images/assessment1.png"/><b>E.</b><span>综合收入</span></li>
		</ul>	
		
   </div>
   
   
    <div class="assessment-warp" data-index="" data-zfz="">
		<p><b>2、</b><span>您的家庭可投资性资产为（折合人民币）？</span></p>
		<ul>
			<li  data-fen="2"><img src="/resource/images/assessment1.png"/><b>A.</b><span>50万元以下</span></li>
			<li data-fen="4"><img src="/resource/images/assessment1.png"/><b>B.</b><span>50—100万元</span></li>
			<li data-fen="6"><img src="/resource/images/assessment1.png"/><b>C.</b><span>100—300万元</span></li>
			<li data-fen="8"><img src="/resource/images/assessment1.png"/><b>D.</b><span>300—500万元</span></li>
			<li data-fen="10"><img src="/resource/images/assessment1.png"/><b>E.</b><span>500万元以上</span></li>
		</ul>	
		
   </div>
   
   
    <div class="assessment-warp" data-index="" data-zfz="">
		<p><b>3、</b><span>在您每年的家庭可支配资产中，可用于金融投资（储蓄存款除外）的比例为？</span></p>
		<ul>
			<li  data-fen="2"><img src="/resource/images/assessment1.png"/><b>A.</b><span>小于10%</span></li>
			<li  data-fen="4"><img src="/resource/images/assessment1.png"/><b>B.</b><span>10%至25%</span></li>
			<li data-fen="6"><img src="/resource/images/assessment1.png"/><b>C.</b><span>25%至50%</span></li>
			<li data-fen="8"><img src="/resource/images/assessment1.png"/><b>D.</b><span>大于50%</span></li>
		</ul>	
		
   </div>
   
    <div class="assessment-warp" data-index="" data-zfz="">
		<p><b>4、</b><span>您是否有尚未清偿的数额较大的债务，如有，其性质是：</span></p>
		<ul>
			<li  data-fen="2"><img src="/resource/images/assessment1.png"/><b>A.</b><span>有，亲戚朋友借款</span></li>
			<li data-fen="4"><img src="/resource/images/assessment1.png"/><b>B.</b><span>有，信用卡欠款、消费信贷等短期信用债务逾期未还</span></li>
			<li data-fen="6"><img src="/resource/images/assessment1.png"/><b>C.</b><span>有，住房抵押贷款等长期定额债务</span></li>
			<li data-fen="8"><img src="/resource/images/assessment1.png"/><b>D.</b><span>没有</span></li>
		</ul>	
		
   </div>
   
    <div class="assessment-warp" data-index="" data-zfz="">
		<p><b>5、</b><span>您的投资知识可描述为：</span></p>
		<ul>
			<li  data-fen="2"><img src="/resource/images/assessment1.png"/><b>A.</b><span>没有</span></li>
			<li data-fen="4"><img src="/resource/images/assessment1.png"/><b>B.</b><span>有限：基本没有金融产品方面的知识</span></li>
			<li data-fen="6"><img src="/resource/images/assessment1.png"/><b>C.</b><span>一般：对金融产品及其相关风险具有基本的知识和理解</span></li>
			<li data-fen="8"><img src="/resource/images/assessment1.png"/><b>D.</b><span>丰富：对金融产品及其相关风险具有丰富的知识和理解</span></li>
		</ul>	
		
   </div>
    <div class="assessment-warp" data-index="" data-zfz="">
		<p><b>6、</b><span>您的投资经验可描述为：</span></p>
		<ul>
			<li  data-fen="2"><img src="/resource/images/assessment1.png"/><b>A.</b><span>除银行储蓄外，基本没有其他投资经验</span></li>
			<li data-fen="4"><img src="/resource/images/assessment1.png"/><b>B.</b><span>购买过债券、保险等理财产品</span></li>
			<li data-fen="6"><img src="/resource/images/assessment1.png"/><b>C.</b><span>参与过股票、基金等产品的交易</span></li>
			<li data-fen="8"><img src="/resource/images/assessment1.png"/><b>D.</b><span>参与过股权、权证、期货、期权等产品的交易</span></li>
		</ul>	
		
   </div>
    <div class="assessment-warp" data-index="" data-zfz="">
		<p><b>7、</b><span>您有多少年投资基金、股票、信托、私募证券或金融衍生产品等风险投资品的经验？</span></p>
		<ul>
			<li  data-fen="2"><img src="/resource/images/assessment1.png"/><b>A.</b><span>没有经验</span></li>
			<li data-fen="4"><img src="/resource/images/assessment1.png"/><b>B.</b><span>少于1年</span></li>
			<li data-fen="6"><img src="/resource/images/assessment1.png"/><b>C.</b><span>1至3年</span></li>
			<li data-fen="8"><img src="/resource/images/assessment1.png"/><b>D.</b><span>3年以上</span></li>
		</ul>	
		
   </div>
    <div class="assessment-warp" data-index="" data-zfz="">
		<p><b>8、</b><span>您计划的投资期限是多久？</span></p>
		<ul>
			<li  data-fen="2"><img src="/resource/images/assessment1.png"/><b>A.</b><span>半年以下</span></li>
			<li data-fen="4"><img src="/resource/images/assessment1.png"/><b>B.</b><span>半年至1年</span></li>
			<li data-fen="6"><img src="/resource/images/assessment1.png"/><b>C.</b><span>1年至3年</span></li>
			<li data-fen="8"><img src="/resource/images/assessment1.png"/><b>D.</b><span>3年以上</span></li>
		</ul>	
		
   </div>
   
    <div class="assessment-warp" data-index="" data-zfz="">
		<p><b>9、</b><span>以下哪项描述最符合您的投资态度？</span></p>
		<ul>
			<li  data-fen="2"><img src="/resource/images/assessment1.png"/><b>A.</b><span>厌恶风险，不能有本金损失，可以没有回报</span></li>
			<li data-fen="4"><img src="/resource/images/assessment1.png"/><b>B.</b><span>保守投资，不希望本金损失，希望获得稳定回报</span></li>
			<li data-fen="6"><img src="/resource/images/assessment1.png"/><b>C.</b><span>寻求资金的较高收益和成长性，愿意为此承担有限本金损失</span></li>
			<li data-fen="8"><img src="/resource/images/assessment1.png"/><b>D.</b><span>希望赚取高回报，愿意为此承担较大本金损失</span></li>
		</ul>	
		
   </div>
   <div class="assessment-warp" data-index="" data-zfz="">
		<p><b>10、</b><span>您认为自己能承受的最大投资损失是多少？</span></p>
		<ul>
			<li  data-fen="2"><img src="/resource/images/assessment1.png"/><b>A.</b><span>5%以内</span></li>
			<li data-fen="4"><img src="/resource/images/assessment1.png"/><b>B.</b><span>5%-10%</span></li>
			<li data-fen="6"><img src="/resource/images/assessment1.png"/><b>C.</b><span>10%-20%</span></li>
			<li data-fen="8"><img src="/resource/images/assessment1.png"/><b>D.</b><span>超过20%</span></li>
		</ul>	
		
   </div>

   

   
   
   </div>
  
	
    
</div>
  <div class="assessment-test">上一题</div>
  <div class="result-btn result-button">提交测评</div>
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