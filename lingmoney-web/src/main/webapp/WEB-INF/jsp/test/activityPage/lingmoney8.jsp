<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>领钱儿“8”点见</title>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	width: 100%;
	background:#81dfbd url('http://static.lingmoney.cn/lingqian/2018/activityimages/5437e85b-28aa-452b-acd5-be954b11c745.png')
		center 123px no-repeat;
}
.springFestival{
	width:920px;
	margin:0px auto;
	margin-top: 660px;
}
.hdsj{
	height:24px; 
	font-size:24px;
	font-family:PingFangSC-Regular;
	color:rgba(25,104,75,1);
	line-height:24px;
	text-align: center;
}
.info{
	height:161px; 
	font-size:18px;
	font-family:PingFangSC-Regular;
	color:rgba(25,104,75,1);
	line-height:36px;
	text-align: center;
	margin-top: 44px;
}
.rule{
	height: 124px;
	width:400px;
	margin:0px auto;
	text-align: center;
	margin-top: 53px;
	font-size:18px;
	font-family:PingFangSC-Regular;
	color:rgba(25,104,75,1);
	line-height:36px;
}
.rule img{
	display: block;
	margin: 0px auto;
	margin-bottom: 36px;
}
.prdbox{
	margin:0px auto;
	width:674px;
	height:238px; 
	background:rgba(200,241,226,1);
	border-radius: 20px;
	margin-top: 50px;
	padding-top: 40px;
}
.pTop{
	height:23px; 
	font-size:24px;
	font-family:PingFangSC-Regular;
	color:rgba(25,104,75,1);
	line-height:36px;
	text-align: center;
}
.pCont table{
	margin:0px auto;
	margin-top:40px;
	width: 90%;
	text-align: center;
	font-size:18px;
	font-family:PingFangSC-Regular;
	color:rgba(25,104,75,1);
}
.pCont table span{
	font-size: 30px;
}
.pBot  a{
	display: block;
	width: 240px;
	height: 60px;
	margin: 0px auto;
	margin-top:40px;
	background: url("http://static.lingmoney.cn/lingqian/2018/activityimages/28f4fa0f-7868-47fa-8fc1-5bf5f4f37ec0.png");
	line-height: 60px;
	text-align: center;
	font-size:26px;
	font-family:PingFangSC-Regular;
	color:rgba(211,84,0,1);
	
}
.shuoming{
	width:920px;
	font-size:18px;
	font-family:'微软雅黑';
	color:rgba(25,104,75,1);
	line-height:36px;
	margin: 40px auto;
}
.shuoming img{
	display: block;
	margin: 40px auto;
}
</style>
<div class="springFestival">
	<div class="hdsj">2月10日早8点至2月11日晚8点开抢，超高收益不容错过!</div>
	<div class="info">早起有福利，赚钱好过年！<br>领钱儿“8点”见活动惊喜而至，<br>为您送上丰厚“年终奖”！<br>拼手速、赚收益，<br>一起为拼搏了一年的自己打call！	</div>
	<div class="rule">
		<img src="http://static.lingmoney.cn/lingqian/2018/activityimages/46dacfe7-d9b6-4983-9a6d-f0f3323d0678.png">
		100元起投，上不封顶。限时秒杀，先到先得
	</div>
	<div class="prdbox">
		<div class="pTop">周末8点特供A</div>
		<div class="pCont clearfix">
			<table>
				<tr>
					<td>项目期限：<span>3</span>个月</td>
					<td>预期年化收益率：<span>8</span>%</td>
				</tr>
			</table>
		</div>
		<div class="pBot p_A"><a href="javascript:void(0);">立即购买</a></div>
	</div>
	<div class="prdbox">
		<div class="pTop">周末8点特供B</div>
		<div class="pCont clearfix">
			<table>
				<tr>
					<td>项目期限：<span>12</span>个月</td>
					<td>预期年化收益率：<span>9.5</span>%</td>
					<td style="color:#D35400">赠：<span>1</span>%加息券</td>
				</tr>
			</table>
		</div>
		<div class="pBot p_B"><a href="javascript:void(0);">立即购买</a></div>
	</div>
	<div class="shuoming">
		<img src="http://static.lingmoney.cn/lingqian/2018/activityimages/aefe6c70-4d7a-4606-b577-931598332e23.png">
		1、本活动理财产品均支持使用加息券；<br>
		2、认购一年期产品可获赠1%加息券，该加息券仅限一年期产品使用，可长期使用，额度不限，每人仅可获得一次。<br>
		3、加息券将在活动结束后的一个工作日内发放至“我的卡券”中。

	</div>
</div>
<script type="text/javascript">
var pcode_A;
var pcode_B;
function getPrd(prdno){
	var pCode="";
	$.ajax({
		url: '/product/queryProductByBatch',
		type: 'post',
		async:false,
		data:{
			batch:prdno,
			size:1
		},
		dataType: 'json',
		success: function(d) {
			pCode=d.obj[0].code;
			

		},
		error: function(d) {}
	});
	return pCode;
}

var comp = function(end, start) {
	
	var now = new Date;//当前时间
	var start = new Date(start);//开始时间
	var end = new Date(end);//结束时间

	if (now < start) {
		$('.activityForm').hide();//活动未开始
		$('.pBot a').css({
			'background':'url(http://static.lingmoney.cn/lingqian/2018/activityimages/b272afa9-62ab-432c-aa42-8ded2756e7e4.png)',
			'background-size':'100%',
			'color':'rgba(102,102,102,1)'
		});
		$('.pBot a').text('活动未开始');
		$('.pBot a').attr('href','javascript:void(0);');
	} else if (now > start && now < end) {
		pcode_A=getPrd('2018021000090A'); //三个月
		pcode_B=getPrd('2018021000365B');//十二个月
		$('.pBot a').css({
			'background':'url(http://static.lingmoney.cn/lingqian/2018/activityimages/28f4fa0f-7868-47fa-8fc1-5bf5f4f37ec0.png)',
			'background-size':'100%',
			'color':'rgba(211,84,0,1)'
		});
		$('.pBot a').text('立即购买');
		$('.pBot.p_A a').attr('href','/product/showProduct.html?changTab=&code='+pcode_A);
		$('.pBot.p_B a').attr('href','/product/showProduct.html?changTab=&code='+pcode_B);
	} else if (now > end) {
		$('.pBot a').css({
			'background':'url(http://static.lingmoney.cn/lingqian/2018/activityimages/b272afa9-62ab-432c-aa42-8ded2756e7e4.png)',
			'background-size':'100%',
			'color':'rgba(102,102,102,1)'
		});
		$('.pBot a').text('活动已结束');
		$('.pBot a').attr('href','javascript:void(0);');
	}

}

$(function(){
	
	comp("2018-02-11 20:00:00", "2018-02-10 08:00:00");
});
</script>
