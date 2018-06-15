$(function(){ 

$('.assessment-warp').each(function(index,item){
	_index=$(this).index()+1
	$(this).attr('data-index',_index);
	_that=$(this);
	$('.assessment-warp li').click(function(){
		if(Number($(this).closest('.assessment-warp').attr('data-index'))<_index){

			$('.assessment-page').html(Number($(this).closest('.assessment-warp').attr('data-index'))+1)
			$('.assessment-block').animate({left:-(Number($(this).closest('.assessment-warp').attr('data-index'))*7.02)+'rem'},150) 
		}else{
				$('.result-button').show()
		}
		
		$(this).find('img').attr('src','/resource/images/assessment01.png');
		$(this).css('color','#FC724C ').siblings().css('color','#868798 ');
		$(this).siblings().find('img').attr('src','/resource/images/assessment1.png');
		
		$(this).closest('ul').closest('.assessment-warp').attr('data-zfz',$(this).attr('data-fen'))
		
	})

})



$('.assessment-test').click(function(){
	$('.assessment-page').html((Number($('.assessment-page').html()))-1)
	 num=Number($('.assessment-page').html())-1
      if(num>=0){
		  $('.assessment-block').animate({left:-(num*7.02)+'rem'}) 
	  }
	  if(num<0){
		  $('.assessment-page').html(1)
	  }	
})


//提交计算


var sum=0;
$('.result-button').click(function(){
	$('.assessment-warp').each(function(){
		sum+=Number($(this).attr('data-zfz'));
		
		$.ajax({
			url:'/users/riskAssessmentResult?token='+ $('#token').val(),
			type:"get",
		    dataType:"json",
		    data:{
		    	score:sum,
		    	
		    },
		    success:function(data){
		    },
		    error:function(data){
		    }
		})
		
		window.location.href="/users/riskAssessmentResult?token="+ $('#token').val()+'&score='+sum
		
	})
	
})







if(20<=$('#score').val() && $('#score').val()<=40 ){
	$('.result-img').addClass('assessment-bj3').html('谨慎型')
	$('.result-chengdu').html('四平八稳，您有比较有限的风险承担能力，对投资收益比较敏感，希望通过短期、持续、渐进地投资跑赢通胀，适合投资中短期的固定收益类产品。')
}else if(40<=$('#score').val() && $('#score').val()<=60){
	$('.result-img').addClass('assessment-bj4').html('稳健型')
	$('.result-chengdu').html('平衡进取，你当前处于财富积累期，财务状况良好，在理财进阶中，对投资流动性要求中等，适合投资平衡长期收益和短期风险的资产。')
}else if(61<=$('#score').val() && $('#score').val()<=80){
	$('.result-img').addClass('assessment-bj5').html('积极型')
	$('.result-chengdu').html('茁壮成长，您有中高风险承担能力，愿意承担可预见的风险去获得较高的收益，一般倾向于中短期投资，中高风险的投资产品比较适合您。')
}else if(81<=$('#score').val() && $('#score').val()<=100){
	$('.result-img').addClass('assessment-bj6').html('激进型')
	$('.result-chengdu').html('乘风波浪，您具有较高的风险承受能力，是富有冒险精神的积极型选手。在投资收益波动的情况下，依然保持乐观进取的投资心态。您适合进行风险、灵活性、收益都比较高的投资，不过请务必做好风险管理和资金分配，以获得源源不断的高收益。')
}


var urlTools = {
	getUrlParam: function(name) { /*?videoId=identification  */
		var params = decodeURI(window.location.search); /* 截取？号后面的部分    index.html?act=doctor,截取后的字符串就是?act=doctor  */
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = params.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
};
	
	var token = urlTools.getUrlParam("token");	   




$('.resultbut').click(function(){
	window.location.href="/users/riskAssessmentQA?type=1&token="+ $('#token').val()

})
























}); 

































