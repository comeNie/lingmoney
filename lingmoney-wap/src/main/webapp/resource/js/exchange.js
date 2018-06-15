$(function(){ 

$('.dj-button').click(function(){
  var val=$('.dj-input').val();
   if(val!=''){
	   $.ajax({
			url: '/redeem2017/userRedeemOper',
			type: 'post',
			data:{
				redeemCode:$('.dj-input').val()
			},
			 xhrFields: {  
			  withCredentials: true  
			},  
			dataType: 'json',
			success: function(d) {
				if(d.code==200){
					if(!$('.error_tip').is(":animated")){
						$('.error_tip').show(100, function() {
							$('.error_tip').html(d.msg);
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
				}else{
					 if(!$('.error_tip').is(":animated")){
							$('.error_tip').show(100, function() {
								$('.error_tip').html(d.msg);
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
				}
				
			},
			error: function(d) {}
		});
	   
	   
	   
	   
   }else{
	   if(!$('.error_tip').is(":animated")){
			$('.error_tip').show(100, function() {
				$('.error_tip').html('请输入兑奖码');
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
   }
 });  
  
  
  
	

















}); 


































