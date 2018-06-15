
$(function(){
	toggleCardType();
	touseCardCheck();

/* 获取加息劵 */
function interest(){
$.ajax({
	url: locationUrl+'/userfinance/redPacket',
	type: 'post',
	data:{
        pageNo:1,
		pageSize:100, 
		hrpType:1,
		type:0
	},
	 xhrFields: {  
	  withCredentials: true  
	},  
	dataType: 'json',
	success: function(d) {
		var html="",Nohtml=""
		if(d.code==200){	
			$.each(d.rows, function(i, item){  
			html+='<div class="ct_cards clearfix">'
			html+='<div class="ct_cardsleft">'
			html+='<div class="ct_cdl_top">'+item.amount+'<em>%</em></div>'
			html+='<div class="ct_cdl_bottom"><span>'+item.hrName+'</span></div>'
			html+='</div>'
			html+='<div class="ct_cardsright">'
			html+='<div class="ct_cr_top">'
			html+='<p>使用产品：<span>'+item.activityRemark[0].适用产品+'</span></p>'
			html+='<p>适用金额：<span>'+item.activityRemark[1].适用金额+'</span></p>'
			html+='<p>有效期：<span>'+item.overtimeDate+'</span></p>'
			html+='</div>'
			html+='<div class="ct_cr_bottom clearfix">'
			html+='<span>有效期截止：'+item.overtimeDate+'</span>'
			html+='<em>3天后过期</em>'
			html+='</div>'
			html+='</div>'
			html+='</div>'
			
		 })
			 $('#ct_cardbox').html(html)
		}else{
		 Nohtml+='<div class="nodata">'
		 Nohtml+='<div><img src="../images/no-data.png" alt=""></div>'
		 Nohtml+='<p>没有数据哦～</p>'
		 Nohtml+='</div>'
			$('#ct_cardbox').html(Nohtml)
		}
		
		
	},
	error: function(d) {}
});	
$('#ct_viewCard-url').attr('href','beenused.html?hrpType=1')
	
}		

interest();
$('#interest').click(function(){
	interest();
	$('#ct_rule-url').attr('href','/cardTicket/rule.html')	
})	
/* 红包列表	 */
$('#envelopes').click(function(){
$('#ct_viewCard-url').attr('href','beenused.html?hrpType=2')
$('#ct_rule-url').attr('href','/cardTicket/red_rule.html')	
	$.ajax({
	url: locationUrl+'/userfinance/redPacket',
	type: 'post',
	data:{
        pageNo:1,
		pageSize:100, 
		hrpType:0,
		type:0
	},
	 xhrFields: {  
	  withCredentials: true  
	},  
	dataType: 'json',
	success: function(d) {
		var html="",Nohtml=""
		if(d.code==200){	
			$.each(d.rows, function(i, item){  
			html+='<div class="ct_cards clearfix">'
			html+='<div class="ct_cardsleft">'
			html+='<div class="ct_cdl_top">'+item.amount+'<em>%</em></div>'
			html+='<div class="ct_cdl_bottom"><span>'+item.hrName+'</span></div>'
			html+='</div>'
			html+='<div class="ct_cardsright">'
			html+='<div class="ct_cr_top">'
			html+='<p>使用产品：<span>'+item.activityRemark[0].适用产品+'</span></p>'
			html+='<p>适用金额：<span>'+item.activityRemark[1].适用金额+'</span></p>'
			html+='<p>有效期：<span>'+item.overtimeDate+'</span></p>'
			html+='</div>'
			html+='<div class="ct_cr_bottom clearfix">'
			html+='<span>有效期截止：'+item.overtimeDate+'</span>'
			html+='<em>3天后过期</em>'
			html+='</div>'
			html+='</div>'
			html+='</div>'
			
		 })
			 $('#ct_cardbox').html(html)
		}else{
		 Nohtml+='<div class="nodata">'
		 Nohtml+='<div><img src="../images/no-data.png" alt=""></div>'
		 Nohtml+='<p>没有数据哦～</p>'
		 Nohtml+='</div>'
			$('#ct_cardbox').html(Nohtml)
		}
		
		
	},
	error: function(d) {}
});
	
	
})	
	
	
});

//卡券红包切换
function toggleCardType(){
	$('#ct_tab a').click(function(){
		$(this).addClass('active').siblings().removeClass('active');
	});
}

//使用优惠券
function touseCardCheck(){
$('body').on('click','.ct_touse .ct_cards',function(){
	$(this).addClass('active').siblings().removeClass('active');
})	
	/* $('.ct_touse .ct_cards').click(function(){
		$(this).addClass('active').siblings().removeClass('active');
	}); */
}

















