$(function(){ 
	$('.Messag-Blessings >p').each(function(index,item){
		var i=0;
		$('.dianji').click(function(){	
		 i++
		if(i<9){
			$('.Message-input-g').val($('.Messag-Blessings >p').eq(i).html());
		}else{
			i=-1
		}
      })
      
     
	
})


$('.input-to').on('blur',function(){
	var val=$(this).val();
	if(val!=''){
		$(this).attr('data-in','1')
	}else{
		$(this).attr('data-in','请输入姓名')
	}
})


$('.input-form').on('blur',function(){
	var val=$(this).val();
	if(val!=''){
		$(this).attr('data-in','1')
	}else{
		$(this).attr('data-in','请输入姓名')
	}
})
$('.Message-input-g').on('keyup , blur',function(){
	var val=$(this).val();
	if(val.length<100){
		$('.Message-cyg').css({'display':'none'})
		$(this).css({'margin-bottom':'0.59rem'})
	}else{
		$('.Message-cyg').css({'display':'block'})
		$(this).css({'margin-bottom':'0rem'})
	}
})

$('.Message-button').click(function(){
	var flag=true;
	$('.text-input').each(function(){
		var tip=$(this).attr('data-in')
		if(tip!=1){
			if(!$('.error_tip').is(":animated")){
				$('.error_tip').show(100, function() {
					$('.error_tip').html(tip);
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
			flag=false;
			return false
			
		}
	})
	if(flag){
		$('.xz123').css('display','block')
		$('.register-mask').css('display','block')
		$.ajax({
			url: '/createImg/getMarkedInputstream',
			type: 'post',
			data:{
				toName:$('.input-to').val(), /*接收人*/
				fromName:$('.input-form').val(),/*发送人*/
				waterMarkContent:$('.Message-input-g').val()
			},
			dataType: 'json',
			success: function(d) {

				if (d.code == 200) {
					$('.xz123').hide()
					$('.register-mask').hide()
					$('.input-to').val('')
					$('.input-form').val('')
					/*$('.Messageimg >img').attr('src',d.url)*/
					window.location.href="/activity/newyear/messageEditimg.html?img="+d.url
				}

			},
			error: function(d) {
				
			}
		});	
	}
	
})


var urlTools = {
		getUrlParam: function(name) {/*?videoId=identification  */
			var params = decodeURI(window.location.search);/* 截取？号后面的部分    index.html?act=doctor,截取后的字符串就是?act=doctor  */
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			var r = params.substr(1).match(reg);
			if (r!=null) return unescape(r[2]); return null;
		}
	};

var img = urlTools.getUrlParam("img");
$('.msg-images').attr('src',img)






}); 


































