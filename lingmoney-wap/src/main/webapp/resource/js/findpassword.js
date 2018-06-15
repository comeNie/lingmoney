$(function(){  
/*  手机号	 */
$('.iphone-pass').on('blur',function(){
	var val=$(this).val();
	var reg =  /^1[0-9]{10}$/;
	if(reg.test(val)){
		 $.ajax({
				url: '/usersVerify/verifyTelephone',
				type: 'post',
				data:{
					'telephone':$('.iphone-pass').val()
				},
				dataType: 'json',
				success: function(d) {
				    if(d.code==3002){
				    	$('.iphone-pass').attr('index-data','1');
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
		$(this).attr('index-data','手机格式不正确');
		 if(!$('.error_tip').is(":animated")){
			$('.error_tip').show(100, function() {
				$('.error_tip').html('手机格式不正确');
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
})

/*生成图形验证码*/
function getCodel(){
    var roundNum= new Date().getTime()+Math.round(100000);
    $('.img-tu-pass').attr('src','/commonsetVerify/pictureCode?picKey='+roundNum);
    
}
getCodel();
$('.img-tu-pass').click(function(){
	getCodel();
})

var str=$('.img-tu-pass').attr('src');
var ret = str.split("=")[1];
/*  图形验证码	 */
$('.imgtuxing-pass').on('blur keyup',function(){
	var val=$(this).val();
	if(val.length==4){
		
		 $.ajax({
				url: '/commonsetVerify/verPicCode',
				type: 'post',
				data:{
					'code':$('.imgtuxing-pass').val(),
					'picKey':ret
				},
				dataType: 'json',
				success: function(d) {
				    if(d.code==200){
				    	$('.imgtuxing-pass').attr('index-data','1');
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
		$(this).attr('index-data','验证码不正确');
		 
	}
})





/* 验证码倒计时 */
    var times = 60;
    function registerTime() {
        if (times == 0) {
            $('.iphone-tip-pass').show().html('重新获取');
            $('.iphone-tips-pass').hide();
            times = 60;
        } else {
            times--;
            $('.iphone-tip-pass').hide();
            $('.iphone-tips-pass').show().html('倒计时'+times);
            setTimeout(function () {
                registerTime()
            }, 1000);
        }
    }
	
	$('.iphone-tip-pass').click(function(){
		registerTime();
		$('.register-but-pass').click();
	})

$('.register-but-pass').click(function(){
	var flag=true;
	$('.index-input-pass').each(function(){
		var indexdata=$(this).attr('index-data');
		if(indexdata!=1){
			if(!$('.error_tip').is(":animated")){
			$('.error_tip').show(100, function() {
				$('.error_tip').html(indexdata);
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
			return false;
			
		}
	});
	
	if(flag){
		$('.register-mask').show();
		$('.register-regiser-open-pass').show();
		
		 $.ajax({
				url: '/smsWap/sendModiPw',
				type: 'post',
				data:{
					'phone':$('.iphone-pass').val(),
					'picKey':ret,
					'code':$('.imgtuxing-pass').val()
				},
				dataType: 'json',
				success: function(d) {
				    if(d.code==200){
				    	registerTime();
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
	}
	
});

/* 关闭弹窗 */
$('.register-close >img').click(function(){
	$('.register-mask').hide();
	$('.register-regiser-open-pass').hide();
	
});
/* 短信验证码 */
$('.iphone-dx-pass').on('blur',function(){
	var val=$(this).val();
	if(val!=''){
		 $.ajax({
				url: '/usersVerify/verifyMsgCode',
				type: 'post',
				data:{
					'telephone':$('.iphone-pass').val(),
					'msgCode':$('.iphone-dx-pass').val()
				},
				dataType: 'json',
				success: function(d) {
			
				    if(d.code==200){
				    	$('.iphone-dx-pass').attr('data-register','1');
				    }else {
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
		$(this).attr('data-register','短信验证码不能为空');
		 if(!$('.error_tip').is(":animated")){
			$('.error_tip').show(100, function() {
				$('.error_tip').html('短信验证码不能为空');
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
})

$('.password-pass').on('blur',function(){
	var val=$(this).val();
	var reg=/^([0-9]|[a-zA-Z]){6,16}$/
	if(reg.test(val)){
		$(this).attr('data-register','1');
	}else{
		$(this).attr('data-register','密码格式不正确');
		 if(!$('.error_tip').is(":animated")){
			$('.error_tip').show(100, function() {
				$('.error_tip').html('密码格式不正确');
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
})




/* 注册提交 */
/*var urlTools = {
	//获取RUL参数值
	getUrlParam: function(name) {?videoId=identification  
		var params = decodeURI(window.location.search); 截取？号后面的部分    index.html?act=doctor,截取后的字符串就是?act=doctor  
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			var r = params.substr(1).match(reg);
			if (r!=null) return unescape(r[2]); return null;
		}
	};
   var categoryId = urlTools.getUrlParam("channel");*/
$('.register-wancheng-pass').click(function(){
	var flag=true;
	$('.register-input-pass').each(function(){
		var dataregister=$(this).attr('data-register');
		if(dataregister!=1){
			if(!$('.error_tip').is(":animated")){
			$('.error_tip').show(100, function() {
				$('.error_tip').html(dataregister);
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
			return false;
			flag=false;
			
		}
	});
	if(flag){
		 $.ajax({
				url: '/users/retrievePassword',
				type: 'post',
				data:{
					'telephone':$('.iphone-pass').val(),
					'password':$('.password-pass').val(),
					'verificationCode':$('.iphone-dx-pass').val()
					/*'channel':categoryId*/
				},
				dataType: 'json',
				success: function(d) {
				    if(d.code==200){
				    	$('.register-mask').hide();
						$('.register-regiser-open-pass').hide();
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
				    	/*setTimeout(function(){
                       
				    		window.location.href="/bankCall.html";
				    	},2000);*/
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
	}
	
});







}); 


































