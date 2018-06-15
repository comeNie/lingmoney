$(function(){ 
	 $(document).ready(function(){                 
		$(".recommendation-btn").click(function(){  
			var Url2 = document.getElementById("copycode");  
			Url2.select();  
			document.execCommand("Copy");  
			headAdd()
		});           
      });  
	   function headAdd(){		
			$('.error_tip').show(100, function() {
				$('.error_tip').html('已复制好，可贴粘!');
				$('.error_tip').animate({
					opacity: 1,
					},1000, function() {
					window.setTimeout(function(){
						$('.error_tip').animate({
							opacity: 0,
							},
							1000, function() {
							$('.error_tip').hide();
						});
					}, 2000)
				});
			});		
		}
	   

	   
 
   
	   
//获取推荐码
   $.ajax({
		url:'/users/getMyRefferCode?token='+$('#token').val(),
	    type:'get',
	    data:{
	    	/*token:token*/
	    },
	    success: function(data) {
	    	if(data.code == 200) {
                $('#copycode').html(data.obj.referralCode)
                $('.recommendation-box-img >img').attr('src',data.obj.codePath)
                $('#shareUrl').val('http://app2.lingmoney.cn/infoInterface/recommend?referralTel='+ data.obj.referralCode) 
	    	} else {
	    		
	    	}
	    }
	})	   
	   
	   
$('.recommendation-rightbor').click(function(){
	window.location.href="/invitingFriends/invitingHtml?token="+$('#token').val()
})	   
$('.recommendation-jilv').click(function(){
	window.location.href="/users/gotoListMyRecommendersPage?token="+$('#token').val()
})	   
//我的推荐人	   
$.ajax({
	url:'/users/listMyRecommenders/'+$('#token').val(),
    type:'get',
    data:{
    	/*token:'9268c3caeb6d451381c4cf8cf692341cacba8fdc1c7448cdba5c907106e1f054'*/
    },
    success: function(data) {
    	var html="",nohtml=""
	    	if(data.code==200) {
	    		  $.each(data.rows, function(i, item){ 
	    			  setTimeout(function(){callbank();},100) 
	    			html+='<li data-binCard="'+item.binCard+'" class="Invitation-each"><span>'+item.loginAccount+'</span><span class="binCard"></span><span>'+item.regDate+'</span></li>'
	    		 }) 
                 $('.Invitation-box').html(html)
               
	    		
	    	} else {
	    		 nohtml+=' <div class="no-data"><p>暂无数据哦~</p><img src="/resource/images/no-data.png" style="width:2.3rem;"/></div>'
	    		$('.Invitation-box').html(nohtml) 
	    		
	    	}
    }
})	   	   
	   
var callbank=function(){
	   $('.Invitation-each').each(function(){
			var binCard=$(this).attr('data-binCard')
			if(binCard==0){
				$(this).find('.binCard').html('否')
			}else if(binCard==1){
				$(this).find('.binCard').html('是')
			}
			
			
		})
}	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
}); 
































