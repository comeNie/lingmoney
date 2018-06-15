<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>年货节</title>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	width: 100%;
	background:
		url('http://static.lingmoney.cn/lingqian/2018/activityimages/ac41490d-e6ee-4db6-b72f-820630ee3553.jpg')
		center 123px no-repeat;
}

.springFestival {
	width: 1200px;
	margin: 0px auto;
}

.sf_datetime {
	height: 30px;
	line-height: 30px; text-align : center;
	font-size: 22px;
	color: #ef4633;
	margin-top: 1017px;
	text-align: center;
}

.sf_introduction {
	width: 1065px;
	margin: 0px auto;
	padding: 0px 50px;
	height: 150px;
	margin-top: 186px;
	font-size: 26px;
	color: #fdedd6;
	line-height: 50px;
}

.product_box {
	padding-top: 50px; width : 1105px;
	margin: 0px auto;
	background: #fff4e5;
	border-radius: 50px;
	width: 1105px;
}

.product_box .product_limit {
	height: 110px;
	line-height: 110px;
	background: #f8e2c3;
	font-size: 30px;
	color: #242424;
}

.product_box .product_limit .product_limit_left {
	width: 490px;
	padding-left: 60px;
	float: left;
}

.product_box .product_limit .product_limit_right {
	width: 490px;
	padding-left: 60px;
	float: left;
}
.product_box .product_limit .product_limit_right img{
	width: 30px;
	margin: 0 10px;
}

.product_box .product_limit span {
	color: #ee4634;
}

.product_box  .product_describe {
	margin-top: 30px;
	font-size: 20px;
	text-align: center;
	color: #242424;
	height: 30px;
	line-height: 30px;
	margin-bottom: 70px;
}

.product_box  .product_describe span {
	color: #ee4634;
}

.product_box .product_details {
	width: 1053px;
	height: 117px;
	margin: 0px auto;
	line-height: 117px;
}

.product_box .product_details .product_name {
	height: 117px;
	width: 385px;
	float: left;
	padding-left: 20px;
	font-size: 35px;
	color: #ffd19f;
}

.product_box .product_details .product_remaining {
	height: 117px;
	float: left;
	padding-left: 20px;
	color: #ffffff;
}

.product_box .product_details .product_remaining span.s_1 {
	display: table-cell;
	vertical-align: middle;
	font-size: 20px;
	font-weight: bold;
}

.product_box .product_details .product_remaining span.s_2 {
	display: table-cell;
	vertical-align: middle;
	font-size: 35px;
	font-weight: bold;
	padding-left: 10px;
}

.product_box .product_details a {
	display: block;
	float: right;
	margin: 19px 17px 0 0;
}

#nhj_A {
	margin-top: 630px;
	height: 436px;
}

#nhj_B {
	margin-top: 420px;
	height: 436px;
	margin-bottom: 100px;
}
#nhj_B .product_limit,#nhj_A .product_limit,#nhj_C .product_limit{
	background:#f8e2c3 url("http://static.lingmoney.cn/lingqian/2018/activityimages/d0621cf5-28ed-4073-9a69-da32361bdfa6.png")  1018px top no-repeat;
	
}
#nhj_C {
	margin-top: 341px;
	height: 434px;
}

#nhj_A .product_details {
	background:
		url('http://static.lingmoney.cn/lingqian/2018/activityimages/d611147c-eefe-4157-b519-3a3af6a6733c.png')
		no-repeat;
}

#nhj_B .product_details {
	background:
		url('http://static.lingmoney.cn/lingqian/2018/activityimages/350a9457-7b32-41af-a1b0-f94ac3b60075.png')
		no-repeat;
}

#nhj_C .product_details {
	background:
		url('http://static.lingmoney.cn/lingqian/2018/activityimages/3d7e2a61-b390-4b46-be0f-5a87431546db.png')
		no-repeat;
}

.product_hx_tip {
	width: 1053px;
	height: 43px;
	line-height: 43px;
	font-size: 18px;
	color: #ffffff;
	background: #e93827;
	margin: 24px auto;
	text-align: center;
}

#exchange {
	margin-top: 824px;
}

.exchange_top {
	width: 1065px;
	margin: 0px auto;
	padding: 0px 50px;
	height: 150px;
	margin-top: 186px;
	font-size: 26px;
	color: #fdedd6;
	line-height: 50px;
}

.exchange_table {
	width: 1165px;
	background: #ea4434;
	margin: 0px auto;
	margin-top: 60px;
	text-align: center;
	padding-bottom: 38px;
}

.exchange_table table {
	width: 1165px;
	border-collapse: collapse;
}

.exchange_table table tr {
	height: 90px;
}

.exchange_table table thead tr td {
	font-size: 30px;
	color: #f7bb7f;
}

.exchange_table table tbody {
	background: #ffffff;
	font-size: 20px;
	color: #242424;
}
.exchange_table table tbody span{
	color: #ea4434;
}

.exchange_table table tbody tr:nth-child(odd) {
	background: #ffffff;
}

.exchange_table table tbody tr:nth-child(even) {
	background: #fdf6ed;
}
.bz{
	height:123px;
	line-height:123px;
	font-size: 20px;
	text-align: center;
	color: #f58f4c;
	margin-bottom: 716px;
}
#wxts{
	margin-bottom: 585px;
}
#wxts .about_box{
	width: 1083px;
	margin: 0px auto;
	margin-bottom: 28px;
}
#wxts .about_top{
	height: 60px;
	line-height:60px;
	padding: 0 25px;
	background: #f34f3d;
	color: #fdedd6;
	font-size: 30px;
}
#wxts .about_content{
	padding: 40px 25px;
	background: #fdedd6;
	font-size: 20px;
	color: #242424;
	line-height: 36px;
}
#nav{
	position: fixed;
	top: 120px;
	right: 50px;
	width: 203px;
	height: 613px;
	background: url("http://static.lingmoney.cn/lingqian/2018/activityimages/79171aa9-bf07-4cf0-a174-7ce0eb1c6de3.png") no-repeat;
	padding-top: 103px;
}
#nav .navBox a{
	display: block;
	width: 127px;
	height: 27px;
	line-height: 27px;
	text-align: center;
	margin: 0px auto;
	margin-bottom: 40px;
	font-size: 16px;
	color: #ffffff;
}
.gotop{
	display:block;
	text-align: center;
	color: #ffffff;
	font-size: 18px;
	padding-top: 20px;
	margin-top:50px;
	background: url("http://static.lingmoney.cn/lingqian/2018/activityimages/db061a95-12ea-4c85-848a-49b7025345b3.png") center top no-repeat;
	
}
</style>
	<div id="nav">
		<div class="navBox">
			<a href="#nhj_A">特供A计划</a>
			<a href="#nhj_B">特供B计划</a>
			<a href="#nhj_C">特供C计划</a>
			<a href="#exchange">津贴兑换明细</a>
			<a href="#wxts">温馨提示</a>
		</div>
		<a href="javascript:void(0);" class="gotop">返回顶部</a>
	</div>
	<div class="springFestival">
		<div class="sf_datetime">活动时间：2018年1月17日-2018年2月5日</div>
		<div class="sf_introduction">活动期间，领钱儿上架“年货节特供”产品，可使用加息券，享超高预期年化收益，累计认购一定金额的理财产品，即可获得相应的年货津贴，年货津贴可兑换等值购物卡。
		</div>
		<div id="nhj_A" class="product_box">
			<div class="product_limit clearfix">
				<div class="product_limit_left">
					产品期限：<span>一年期</span>
				</div>
				<div class="product_limit_right">
					预期年化收益：<span>9%<img alt="" src="http://static.lingmoney.cn/lingqian/2018/activityimages/1a5dceab-248f-4a25-83fb-51f940e3c0f3.png"></span>
				</div>
			</div>
			<div class="product_describe">
				活动期间，累计认购“年货节特供A”产品<span>每满20000元</span>，即可获得<span>100元年货津贴</span>，上不封顶。
			</div>
			<div class="product_details clearfix">
				<div class="product_name"><!-- 年货节特供A001期 --></div>
				<div class="product_remaining">
					<span class="s_1">剩余额度</span><span class="s_2"><!-- 888888 --></span>
				</div>
				<a href="javascript:void(0);"><img
					src="http://static.lingmoney.cn/lingqian/2018/activityimages/b57ecc36-940c-40b7-a481-0a8e35c25a50.png"></a>
			</div>
		</div>
		<div id="nhj_B" class="product_box">
			<div class="product_limit clearfix">
				<div class="product_limit_left">
					产品期限：<span>半年期</span>
				</div>
				<div class="product_limit_right">
					预期年化收益：<span>7%<img alt="" src="http://static.lingmoney.cn/lingqian/2018/activityimages/1a5dceab-248f-4a25-83fb-51f940e3c0f3.png"></span>
				</div>
			</div>
			<div class="product_describe">
				活动期间，累计认购“年货节特供B”产品<span>每满40000元</span>，即可获得<span>100元年货津贴</span>，上不封顶。
			</div>
			<div class="product_details clearfix">
				<div class="product_name"><!-- 年货节特供A001期 --></div>
				<div class="product_remaining">
					<span class="s_1">剩余额度</span><span class="s_2"><!-- 888888 --></span>
				</div>
				<a href="javascript:void(0);"><img
					src="http://static.lingmoney.cn/lingqian/2018/activityimages/b57ecc36-940c-40b7-a481-0a8e35c25a50.png"></a>
			</div>
		</div>
		<div id="nhj_C" class="product_box">
			<div class="product_limit clearfix">
				<div class="product_limit_left">
					产品期限：<span>三月期</span>
				</div>
				<div class="product_limit_right">
					预期年化收益：<span>6.5%<img alt="" src="http://static.lingmoney.cn/lingqian/2018/activityimages/1a5dceab-248f-4a25-83fb-51f940e3c0f3.png"></span>
				</div>
			</div>
			<div class="product_describe">
				活动期间，累计认购“年货节特供C”产品<span>每满60000元</span>，即可获得<span>100元年货津贴</span>，上不封顶。
			</div>
			<div class="product_details clearfix">
				<div class="product_name"><!-- 年货节特供A001期 --></div>
				<div class="product_remaining">
					<span class="s_1">剩余额度</span><span class="s_2"><!-- 888888 --></span>
				</div>
				<a href="javascript:void(0);"><img
					src="http://static.lingmoney.cn/lingqian/2018/activityimages/b57ecc36-940c-40b7-a481-0a8e35c25a50.png"></a>
			</div>
		</div>
		<div id="exchange">
			<div class="exchange_top">活动期间，用户累计认购活动产品均可获得年货津贴，并可兑换同等价值的购物卡，购物卡卡种可由用户自主选择。</div>
			<div class="exchange_table">
				<table>
					<thead>
						<tr>
							<td>年货津贴</td>
							<td>可兑换购物卡价值</td>
							<td>可选卡种</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>100元</td>
							<td>价值<span>100</span>元</td>
							<td>京东E卡（电子卡）</td>
						</tr>
						<tr>
							<td>200元</td>
							<td>价值<span>200</span>元</td>
							<td>京东E卡、沃尔玛、家乐福</td>
						</tr>
						<tr>
							<td>300元</td>
							<td>价值<span>300</span>元</td>
							<td>京东E卡、沃尔玛、家乐福</td>
						</tr>
						<tr>
							<td>500元</td>
							<td>价值<span>500</span>元</td>
							<td>京东E卡、沃尔玛、家乐福、华润万家</td>
						</tr>
						<tr>
							<td>1000元</td>
							<td>价值<span>1000</span>元</td>
							<td>京东E卡、沃尔玛、家乐福、华润万家</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="bz">备注：仅100元京东E卡为电子卡，其他面额京东E卡以及其他品牌卡种均为实体卡。</div>
		</div>
		<div id="wxts">
			<div class="about_box">
				<div class="about_top">关于年货津贴</div>
				<div class="about_content">
					1、仅在活动期间，认购本活动产品达一定额度后，方可获得年货津贴；<br>2、活动期间，本活动产品认购金额可累计，年货津贴可累计；<br>3、年货津贴兑换完购物卡后，消耗相应津贴数量；活动结束后，所有未兑换津贴失效。
				</div>
			</div>
			<div class="about_box">
				<div class="about_top">关于津贴兑换</div>
				<div class="about_content">
					1、活动期间所累积的年货津贴可兑换同等价值的商超购物卡，即100元年货津贴可兑换价值100元的购物卡；<br>2、单张购物卡仅可选择活动指定面额，用户可根据自身累计津贴数量自由组合；例如：A用户有800元年货津贴，可选择300元面额与500元面额购物卡相组合；B用户有2000元年货津贴，可选择2张1000元面额或者4张500元面额购物卡相组合；<br>3、本次活动年货津贴只能兑换100的整数倍购物卡，不足或超出部分无法兑换；例如：C用户有550元年货津贴，仅可兑换价值500元购物卡。
				</div>
			</div>
			<div class="about_box">
				<div class="about_top">关于购物卡</div>
				<div class="about_content">
					1、本次活动年货津贴所兑换的购物卡卡种，用户可在京东E卡、沃尔玛、家乐福、华润万家中自由选择；其中100元价值的购物卡只可选择京东E卡电子卡，华润万家购物卡只支持500元的整数倍面额兑换；<br>2、本次活动所有购物卡均全国通用，京东E卡可在京东商城购买任意京东自营商品，沃尔玛、家乐福、华润万家购物卡可在全国相应门店使用，使用时请注意在门店激活；<br>3、本次活动所有购物卡均不记名、不挂失、不补办、不计息、不兑换现金，请妥善保管；<br>4、所有购物卡激活后，该购物卡售后服务由卡片服务商提供。
				</div>
			</div>
			<div class="about_box">
				<div class="about_top">关于卡片发放</div>
				<div class="about_content">
					1、客服人员将在用户认购成功后一个工作日内进行回访，确认兑换信息后7个工作日内将购物卡片寄出；<br>2、电子卡将通过短信方式发放至用户所提供的手机号上，实物卡片将通过邮寄形式寄送至客户所提供的收货地址；<br>3、卡片发放过程中，超市购物卡发货时间可能会受供应商制卡影响，推荐优先选择京东E卡。
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
	$(".gotop").click(function () {
        var speed=500;//滑动的速度
        $('body,html').animate({ scrollTop: 0 }, speed);
        return false;
 	});
	
	$(function(){
		getPrd('20180116000365A','#nhj_A');
		getPrd('20180116000180A','#nhj_B');
		getPrd('20180116000090A','#nhj_C');
	});

	function getPrd(prdno,idname){
		$.ajax({
			url: '/product/queryProductByBatch',
			type: 'post',
			data:{
				batch:prdno,
				size:1
			},
			dataType: 'json',
			success: function(d) {
				var prd_name="";//产品名称
			     if (d.code==200) {			    		
			     	prd_name=d.obj[0].name;
			     	prd_available=d.obj[0].available;
			     	$(idname).find('.product_name').text(prd_name);
			     	$(idname).find('.s_2').text(prd_available);
			     	$(idname).find('.product_details a').attr('href','/product/showProduct.html?changTab=&code='+d.obj[0].code);

			     }

			},
			error: function(d) {}
		});
	}
	</script>
