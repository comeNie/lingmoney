$(function(){ 
var height=document.documentElement.clientHeight;	
	$('.tabslider ul').css('height',height-45)
$('body').on('click','.problem-tip',function(){
	if($(this).attr('data-tip')==0){
		$(this).find('img').attr('src','/resource/images/top.png')
	    $(this).closest('.problem-box').find('.problem-content').show();
		$(this).attr('data-tip','1')
	}else if($(this).attr('data-tip')==1){
		$(this).find('img').attr('src','/resource/images/uparrow.png')
	    $(this).closest('.problem-box').find('.problem-content').hide();
		$(this).attr('data-tip','0')
	}
})

$('body').on('click','.project-jilv',function(){
		 window.location.href="/product/gotoInvesterListPage?pId="+$('#pid').val() 
})

/* if(!$('.error_tip').is(":animated")){
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
}  */














}); 


































