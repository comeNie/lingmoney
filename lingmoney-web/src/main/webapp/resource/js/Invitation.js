$(function(){ 
/*邀请列表*/
	$.ajax({
		url: '/invitingFriends/invitingFriendsLevel',
		type: 'post',
		dataType: 'json',
		success: function(data) {
			  var html="",nohtml=""
		    	if(data.listMap!='') {
		    		  $.each(data.listMap, function(i, item){ 
					  _i=data.listMap.length;
					      callbank();
			    		  if(item.telephone != null){
			    			  html+='<li><span>'+item.level+'</span>  <span>'+item.telephone+'</span>  <span>共邀请'+item.count+'位</li>'
			    		  }
		    		 }) 
	                 $('.Invitationul').html(html)
	                 $('.Invitationulno').hide()
		    		
		    	} else {
		    		nohtml+='<div class="nodata">'
		    			nohtml+='<div><img src="http://static.lingmoney.cn/lingmoneywap/images/no-data.png" alt="" style="width:30%;"></div>'
		    				nohtml+='<p>没有数据哦～</p>'
		    					nohtml+='</div>'
		    		$('.Invitationulno').html(nohtml) 
		    		$('.Invitationul').hide();
		    	}
		},
		error: function(d) {}
	});	
	
	
	
//
	$.ajax({
		url:'/invitingFriends/invitingRewards',
	    type:'get',
	    success: function(data) {
		    console.log(data);
		  
	    	if(data.code == 200){
	    		$.each(data.listMap, function(i, item){ 
		    		console.log(item);
                    if(item.status1 == 0){
                        $('.jiaxi05').find('div').addClass('Invitation-cssbj').removeClass('Invitation-cssbj1');
                        $('.status-P1').html('邀请3位')
                    }else if(item.status1 == 2){
                        $('.jiaxi05').find('div').addClass('Invitation-cssbj1').removeClass('Invitation-cssbj').find('span').addClass('Invitation-visited');
                        $('.status-P1').html('邀请3位(已完成)')
                    }
                    
                    if(item.status2 == 0){
                        $('.jiaxi1').addClass('Invitation-cssbj').removeClass('Invitation-cssbj1');
                    }else if(item.status2 == 2){
                    	  $('.jiaxi05').find('div').addClass('Invitation-cssbj1').removeClass('Invitation-cssbj').find('span').addClass('Invitation-visited');
                        $('.status-P2').html('再邀请2位(已完成)')
                    } 
                    
                    if(item.status3 == 0){
                        $('.jiaxi2').addClass('Invitation-cssbj').removeClass('Invitation-cssbj1');
                    }else if(item.status3 == 2){
                    	  $('.jiaxi05').find('div').addClass('Invitation-cssbj1').removeClass('Invitation-cssbj').find('span').addClass('Invitation-visited');
                        $('.status-P3').html('再邀请8位(已完成)')
                    }
                });
            }else{
            	$('.jiaxi05').addClass('Invitation-haoli-listbjl0');
                $('.jiaxi1').addClass('Invitation-haoli-listbjl0');
                $('.jiaxi2').addClass('Invitation-haoli-listbjl0');
                $('.status-P1').html('邀请3位')
            }
	    }
	});	
	
//我的邀请
	
$('.my-yaoqing').click(function(){
	$('.tc-box').show();
	$('.index-Popup-bg').show();
	/*邀请列表*/
	$.ajax({
		url:'/invitingFriends/invitingFriendsList',
	    type:'get',
	    success: function(data) {
	       var html="",Nohtml=""
	    	if(data.listMap!='') {
	    		  $.each(data.listMap, function(i, item){ 
	    			 setTimeout(function(){callbank();},100)
	    			
	    			html+='<li class="cardStatus" data-cardStatus="'+item.cardStatus+'" data-rewardMoney="'+item.rewardMoney+'"><span>'+item.telephone+'</span> <span>'+item.rewardMoney+'</span> <span class="cardStatusspan"></span> <span>'+item.regDate+'</span></li> ' 

	    		 }) 
                 $('.myInvitation-box-list').html(html)
          
	    		
	    	} else {
	    		 Nohtml+='<div class="nodata">'
				 Nohtml+='<div><img src="http://static.lingmoney.cn/lingmoneywap/images/no-data.png" alt="" style="margin: 0 auto;"></div>'
				 Nohtml+='<p>没有数据哦～</p>'
				 Nohtml+='</div>'
	    		$('.myInvitation-box-list').html(Nohtml) 
	    		
	    	}
	    }
	})
	
	/*var sum=0*/
	var callbank=function(){
			$('.cardStatus').each(function(){
				var cardStatus=$(this).attr('data-cardStatus')
				if(cardStatus==0){
					$(this).find('.cardStatusspan').html('否')
				}else if(cardStatus==1){
					$(this).find('.cardStatusspan').html('是')
				}
				
				/*sum+=Number($(this).attr('data-rewardMoney'))
				$('.myInvitation-money >span').html(sum)*/
			})
		}		
/*奖励排名*/	
	$.ajax({
		url:'/invitingFriends/invitingFriendsLevel',
	    type:'post',
	    data:{
	    	 /* "token":token */
	    },
	    success: function(data) {
	       var html="",Nohtml=""
	    	if(data.listMap!='') {
	    		  $.each(data.listMap, function(i, item){ 
	    			  setTimeout(function(){calbank();},100)
	    			html+='<li data-status="'+item.status+'" class="myInvitation-tops"><span>'+item.level+'</span> <span>累计邀请<b>'+item.count+'</b>人</span> <span>'+item.rewardContent+'</span> <span class="nodate"></span></li>'  
	    			/*html+='<div class="myInvitation-tops" data-status="'+item.status+'">'
	    			html+='<p class="myInvitation-tops1">'+item.level+'</p>'
	    			html+='<p class="myInvitation-tops2"><span>累计邀请<b>'+item.count+'</b>人</span><span style="font-size:0.24rem;color:#868798;padding-top:0.08rem;" class="nodate"></span></p>'
	    			html+='<p class="myInvitation-tops3">'+item.rewardContent+' <span>'+item.rewardDate+'</span></p>'
	    			html+='</div>'*/
	    		 }) 
	             $('.listMap-list').html(html)
	      
	    		
	    	} else {
	    	 Nohtml+='<div class="nodata">'
					 Nohtml+='<div><img src="http://static.lingmoney.cn/lingmoneywap/images/no-data.png" alt="" style="margin: 0 auto;"></div>'
					 Nohtml+='<p>没有数据哦～</p>'
					 Nohtml+='</div>'
	    		$('.listMap-list').html(Nohtml) 
	    		
	    	}
	    }
	})
	var calbank=function(){
		$('.myInvitation-tops').each(function(){
			if($(this).attr('data-status')==2){
				$(this).find('.nodate').html('已发放')
			}else if($(this).attr('data-status')==0){
				$(this).find('.nodate').html('未获得')
			}else if($(this).attr('data-status')==1){
				$(this).find('.nodate').html('未发放')
			}
		})
	}
	
})	
/*关闭*/
$('.closed').click(function(){
	$('.tc-box').hide();
	$('.index-Popup-bg').hide();
})
}); 

//滚动
function autoScroll(obj){  
	$(obj).find(".Invitationul").animate({  
		marginTop : "-0.38rem"  
	},500,function(){  
		$(this).css({marginTop : "0rem"}).find("li:first").appendTo(this);  
	})  
   }  

var callbank=function(){
	$(function(){  
	if(_i>=8){
	  setInterval('autoScroll(".maquee")',3000);
  }
  })
}
































