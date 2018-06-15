window.wxtitle = "";
window.wxdesc = "";

$(function(){ 
	

/* 产品详情 */
var productId = lyw.urlTools.getUrlParam("id");
var productpType = lyw.urlTools.getUrlParam("pType");/* 产品类型 */

var pTypebank=function(){
	setTimeout(function(){
		if(productpType!=2){
		$('.pType0-project').css('display','none');
		$('.pType0-money').css('float','right');
		$('.product-details-jd').css('display','none');
		$('.product-looksy').css('display','block');
//		$('.product-details-yue').css('display','none')
		$('.main').css('bottom','0.88rem')
		
	}
	},100)
	
}
$.ajax({
		url: locationUrl+'/product/productList',
		type: 'post',
		data:{
			proId:productId,	
		},
		async : false,
		dataType: 'json',
		success: function(d) {
			 var html="";
			 if(d.code==200){
				 $.each(d.rows, function(i, item){  
					var sy=item.priorMoney-item.reachMoney,expireDt=new Date(item.expireDt),dt=lyw.formatDate(expireDt),edDt=new Date(item.edDt),jz=lyw.formatDate(edDt),countime= new Date(jz);
					lyw.countDown(countime);
					
					window.wxtitle = item.proType + "-" + item.name;
					window.wxdesc = wxtitle + ",预期年化收益率" + item.fYield + '项目期限' + item.fTime + '天，起投金额' + item.minMoney + ',到期时间' + dt;
					
					html+='<div class="product-details">'
					html+='<div class="product-details-1">'
					html+='<p class="product-details-tip">预期年化收益率（％）</p>'
					html+='<p  class="product-details-mun">'+ (item.fYield).toFixed(1) +'</p>'
					html+='<div class="product-details-box">'
					html+='<div class="product-details-box-list">'
					html+='<ul>'
					html+='<li><div>项目期限(天)</div><p>'+item.fTime+'</p></li>'
					html+='<li class="pType0-money"><div>起投金额(元)</div><p>'+item.minMoney+'</p></li>'
					html+='<li class="pType0-project"><div>项目规模(元)</div><p>'+item.priorMoney+'</p></li>'
					html+='</ul>'
					html+='</div>'
					html+='<div class="product-details-jd">'
					html+='<div class="product-details-shuxing"><span class="product-details-baifenbi"></span>%</div>'
					html+='<div class="product-details-jindu">'
					html+='<div class="product-details-zhi"></div>'
					html+='<div class="product-details-zhi-right"></div>'
					html+='<input type="hidden" value="100" class="jinduzhi"/>'
					html+='<input type="hidden" value="8000" class="xiangmu"/>'
					html+='<input type="hidden" value="6000" class="shengyu"/>'
					html+='<input type="hidden" value="" class="yimuji"/>'
					html+='<input type="hidden" value="2000" class="zhanyong"/>'
					html+='</div>'
					html+='<div class="product-details-kt">剩余可购'+sy+' 该产品被占用'+item.occupyMoney+'元,待释放</div>'
					html+='</div>'
					html+='</div>'
					html+='<p class="product-looksy">查看期限收益率</p>'
					html+='<div  class="product-details-2">'
					html+='<div class="product-touzi">'
					html+='<ul>'
					html+='<li class="product-touzi-btt"><img src="../images/product-5.png" alt="">投资金额(元)：</li>'
					html+='<li class="product-touzi-put"><input type="text" class="product-goumai-input" id="product-blur" placeholder="'+item.minMoney+'元起投，以'+item.minMoney+'元整数倍递增" data-Investment="'+item.minMoney+'" data-surplus="'+sy+'" data-status="0" data-income="'+item.fYield+'" data-days="'+item.fTime+'" data-code="'+item.code+'" data-ptype="'+item.pType+'" data-name="'+item.name+'" data-dqtime="'+item.edDt+'"><i><img src="../images/erro.png" alt="" class="img-empty"></i></li>'
					html+='</ul>'
					html+='</div>'
					html+='</div>'
					html+='</div>'
					html+='<div class="product-details-juan">'
					html+='<ul>'
					html+='<li class="product-details-juan-left">预期收益：<span class="year-Income">0</span><span class="add-shouyi"></span>元</li>'
					html+='<li class="product-details-juan-right"><img src="../images/product-5.png" alt="" style="width:0.35rem;vertical-align:middle;margin-bottom:0.03rem;margin-right:0.09rem;"><span class="have-class"></span><span class="have-ka">暂无可用</span><img src="../images/index-arrow.png" alt="" style="width:0.13rem;vertical-align:middle;margin-bottom:0.04rem;margin-left:0.17rem;"></li>'
					html+='</ul>'
					html+='</div>'
					html+='<div class="product-details-brief">'
					html+='<ul>'
					html+='<li class="pType0-project"><div>项目规模：</div><p>'+item.priorMoney+'元</p></li>'
					html+='<li><div>到期时间：</div><p>'+dt+'</p></li>'
					html+='<li><div>赎回方式：</div><p>'+item.reWay+'</p></li>'
					html+='<li><div>截止时间：</div><p>' + lyw.formatDate(edDt) + '</p></li>'
					html+='</ul>'
					html+='</div>'
					html+='</div>'
				 })
				 $('#product_details').html(html) 
				 pTypebank();
			 }
			 
			
           
		},
		error: function(d) {}
	});
	
	
/* 默认请求余额 */
$.ajax({
	url: locationUrl+'/userfinance/getUserBalance',
	type: 'post',
	data:{

	},
	 xhrFields: {  
	  withCredentials: true  
	},  
	dataType: 'json',
	success: function(d) {
		if(d.code==200){
//			$('.product-details-yue').css('display','block')
			 $('#balance').html(d.obj.balance) 
			 $('.main').css('bottom','2.08rem')
		}
		
	},
	error: function(d) {}
});	
	

//年化收益计算
$('body').on('blur','#product-blur',function(){
	var val=$('#product-blur').val();
	var datacode=$('#product-blur').attr('data-code')
	var income=$('#product-blur').attr('data-income')/* 年化收益 */
	var days=$('#product-blur').attr('data-days')/* 项目期限 */
	/* 预期收益计算 */	
	var yearIncome=(((val*income)/365)*days).toFixed(2);
	$('.year-Income').html(yearIncome);
	
	/* 判断有没有可用劵 */
	
	 $.ajax({
		url: locationUrl+'/userfinance/queryFinancialAvailableRedPacket',
		type: 'post',
		data:{
		 pCode:datacode,
		 buyMoney:val,
		},
		 xhrFields: {  
		  withCredentials: true  
		},  
		dataType: 'json',
		success: function(d) {
			var html="",Nohtml="",redhtml="";
			if(d.code==200){
	       $('.have-ka').html(d.obj.availableCount+'张可用').addClass('go-coupons')	
             var map = {},  
                 dest = [];  
			for(var i = 0; i <d.obj.availableList.length; i++){  
			var ai = d.obj.availableList[i];  
			if(!map[ai.hrpType]){  
				dest.push({  
					hrpType: ai.hrpType,  
					data: [ai]  
				});  
				map[ai.hrpType] = ai;  
			}else{  
				for(var j = 0; j < dest.length; j++){  
					var dj = dest[j];  
					if(dj.hrpType == ai.hrpType){  
						dj.data.push(ai);  
						break;  
					}  
				}  
			}  
			}  
          
			 hrpType1=dest[1].hrpType;
             hrpType0=dest[0].hrpType;			
            if(hrpType0==1){
				 $.each(dest[0].data, function(i, item){  
					html+='<div class="ct_cards clearfix">'
					html+='<div class="ct_cardsleft">'
					html+='<div class="ct_cdl_top">'+item.amount+'<em>%</em></div>'
					html+='<div class="ct_cdl_bottom"><span>'+item.hrName+'</span></div>'
					html+='</div>'
					html+='<div class="ct_cardsright ct_img" data-amount="'+item.amount+'" data-hrName="'+item.hrName+'" data-id="'+item.id+'">'
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
			}
			
			if(hrpType1==0){
				 $.each(dest[1].data, function(i, item){  
					redhtml+='<div class="ct_cards clearfix">'
					redhtml+='<div class="ct_cardsleft">'
					redhtml+='<div class="ct_cdl_top">'+item.amount+'<em>%</em></div>'
					redhtml+='<div class="ct_cdl_bottom"><span>'+item.hrName+'</span></div>'
					redhtml+='</div>'
					redhtml+='<div class="ct_cardsright ct_img" data-amount="'+item.amount+'" data-hrName="'+item.hrName+'" data-id="'+item.id+'">'
					redhtml+='<div class="ct_cr_top">'
					redhtml+='<p>使用产品：<span>'+item.activityRemark[0].适用产品+'</span></p>'
					redhtml+='<p>适用金额：<span>'+item.activityRemark[1].适用金额+'</span></p>'
					redhtml+='<p>有效期：<span>'+item.overtimeDate+'</span></p>'
					redhtml+='</div>'
					redhtml+='<div class="ct_cr_bottom clearfix">'
					redhtml+='<span>有效期截止：'+item.overtimeDate+'</span>'
					redhtml+='<em>3天后过期</em>'
					redhtml+='</div>'
					redhtml+='</div>'
					redhtml+='</div>'
		      })
			}
       /* 红包加息劵切换 */  
	    $('#ct_cardbox').html(html) 
		   $('#envelopes').click(function(){
	       $('#ct_cardbox').html(redhtml);
           $('#ct_rule-url').attr('href','/cardTicket/red_rule.html')
           $(this).addClass('active').siblings().removeClass('active');		   
        })
		$('#add-xi').click(function(){
			 $('#ct_cardbox').html(html);
             $('#ct_rule-url').attr('href','/cardTicket/rule.html');
             $(this).addClass('active').siblings().removeClass('active');		   
		})
	
			
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

/* 选择卡劵 */
$('body').on('click','.ct_img',function(){
	$(this).addClass('ct_img-active');
	$(this).closest('.clearfix').siblings().find('.ct_img').removeClass('ct_img-active');
	$('#ct_box').hide();
	$('.have-ka').html($(this).attr('data-hrName'));
	$('.have-class').html($(this).attr('data-amount'));
	$('.add-shouyi').html('+'+$(this).attr('data-amount'))
	
})


/* 弹出弹窗 */
$('body').on('click','.go-coupons',function(){
	$('.ct-box').css('display','block')
})

	
/* 清空表单 */
$('body').on('click','.img-empty',function(){
	$('#product-blur').val('')
	$(this).css('display','none');
})	
$('body').on('keyup','#product-blur',function(){
	$('.img-empty').css('display','block');
})
/*$('#product-goumai').click(function(){*/
	window.location.href = "http://static.lingmoney.cn/wap/product/product_details.html?id="+productId+'&pType='+productpType ;
/*});	*/
	
/* 余额不足判断绑卡或者跳转充值 */	
$('#insufficient').click(function(){
	var pType=$('#product-blur').attr('data-ptype');/* 产品类型 */	
	$.ajax({
		url: locationUrl+'/users/queryUserAccountSetInfo',
		type: 'post',
		data:{
		  
		},
		 xhrFields: {  
		  withCredentials: true  
		},  
		dataType: 'json',
		success: function(d) {
			var hxcallbank=function(){	
				if(d.obj.certification==2){
					window.location.href="/recharge/recharge.html"
				  }else if(d.obj.certification==3){
					window.location.href="/recharge/recharge.html"
				  }else{
					  window.location.href="/hxbank/oncard.html"
					
				}
			}	
			if(d.code==200){
				/*  var jdcallbank=function(){
						if(d.obj.certification!==1 || d.obj.certification!==3){
						  window.location.href="/hxbank/jdoncard.html"
						}
						}	
 */                    

						
				 if(pType==2){
					  hxcallbank()
			      }
				 
				
				
			}else{
				window.location.href="../login.html"
			}
			
		},
		error: function(d) {}
	});
})	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}); 




	






























