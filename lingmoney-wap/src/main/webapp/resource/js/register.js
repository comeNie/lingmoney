$(function(){  
/*  手机号	 */
$('.iphone').on('blur',function(){
	var val=$(this).val();
	var reg =  /^1[0-9]{10}$/;
	if(reg.test(val)){
		 $.ajax({
				url: '/usersVerify/verifyTelephone',
				type: 'post',
				data:{
					'telephone':$('.iphone').val()
				},
				dataType: 'json',
				success: function(d) {
				    if(d.code==200){
				    	$('.iphone').attr('index-data','1');
				    }else{
				    	$('.iphone').attr('index-data',d.msg);
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
		$(this).attr('index-data',d.msg);
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
})


function uuid(len, radix) {
		var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
		var uuid = [],
			i;
		radix = radix || chars.length;
		if (len) {
			// 填充
			for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
		} else {

			var r;
			// 分割字符串
			uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
			uuid[14] = '4';

			// 填充随机字符
			for (i = 0; i < 36; i++) {
				if (!uuid[i]) {
					r = 0 | Math.random() * 16;
					uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
				}
			}
		}
		return uuid.join('');
	}





/*生成图形验证码*/
function getCode(){
    /*var roundNum= new Date().getTime()+Math.round(10000000);*/
	 roundNum=uuid(16)
    $('.img-tu').attr('src','/commonsetVerify/pictureCode?picKey='+roundNum);
	 str=$('.img-tu').attr('src');
	 ret = str.split("=")[1];
   
    
}
getCode()
$('.img-tu').click(function(){
	getCode();
})


/*  图形验证码	 */
$('.imgtuxing').on('blur keyup',function(){
	var val=$(this).val();
	if(val.length==4){
		
		 $.ajax({
				url: '/commonsetVerify/verPicCode',
				type: 'post',
				data:{
					'code':$('.imgtuxing').val(),
					'picKey':ret
				},
				dataType: 'json',
				success: function(d) {
				    if(d.code==200){
				    	$('.imgtuxing').attr('index-data','1');
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

/*协议*/
$('.xieyi').on('click',function(){
	if($(this).attr('index-data')==1){
       $(this).attr('src','../resource/images/register05.png');
       $(this).attr('index-data','请勾选协议')
      /* $(this).attr('index-data','请勾选协议')*/
	}else if($(this).attr('index-data')=='请勾选协议'){
		 $(this).attr('src','../resource/images/register5.png');
		 $(this).attr('index-data','1')
	}
})



/* 验证码倒计时 */
    var times = 60;
    function registerTime() {
        if (times == 0) {
            $('.iphone-tip').show().html('重新获取');
            $('.iphone-tips').hide();
            times = 60;
        } else {
            times--;
            $('.iphone-tip').hide();
            $('.iphone-tips').show().html(times+'s');
            setTimeout(function () {
                registerTime()
            }, 1000);
        }
    }
	
	$('.iphone-tip').click(function(){
		registerTime();
		$('.register-but').click();
	})

$('.register-but').click(function(){
	var flag=true;
	$('.index-input').each(function(){
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
		$('.register-regiser-open').show();
		
		 $.ajax({
				url: '/sms/sendreg',
				type: 'post',
				data:{
					'phone':$('.iphone').val(),
					'picKey':ret,
					'code':$('.imgtuxing').val()
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
	$('.register-regiser-open').hide();
	$('.Success').hide()
	
});
/* 短信验证码 */
$('.iphone-dx').on('blur',function(){
	var val=$(this).val();
	if(val!=''){
		 $.ajax({
				url: '/usersVerify/verifyMsgCode',
				type: 'post',
				data:{
					'telephone':$('.iphone').val(),
					'msgCode':$('.iphone-dx').val()
				},
				dataType: 'json',
				success: function(d) {
			
				    if(d.code==200){
				    	$('.iphone-dx').attr('data-register','1');
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

$('.password').on('blur',function(){
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




/*$('.okpassword').on('blur',function(){
	var val=$(this).val();
	
	if(val==$('.password').val()){
		$(this).attr('data-register','1');
	}else{
		$(this).attr('data-register','两次密码不一致');
		 if(!$('.error_tip').is(":animated")){
			$('.error_tip').show(100, function() {
				$('.error_tip').html('两次密码不一致');
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
})*/


/* 注册提交 */
var urlTools = {
	//获取RUL参数值
	getUrlParam: function(name) {/*?videoId=identification  */
		var params = decodeURI(window.location.search);/* 截取？号后面的部分    index.html?act=doctor,截取后的字符串就是?act=doctor  */
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			var r = params.substr(1).match(reg);
			if (r!=null) return unescape(r[2]); return null;
		}
	};
   var categoryId = urlTools.getUrlParam("channel");
$('.register-wancheng').click(function(){
	var flag=true;
	$('.register-input').each(function(){
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
				url: '/users/register',
				type: 'post',
				data:{
					'telephone':$('.iphone').val(),
					'password':$('.password').val(),
					'verificationCode':$('.iphone-dx').val(),
					'channel':categoryId
				},
				dataType: 'json',
				success: function(d) {
				    if(d.code==200){
				    	$('.Success').show()
				    	$('.register-mask').show();
				    	
				    	$('.register-regiser-open').hide();
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


/*登录*/

/*$('.lijilogin').click(function(){
	$('.login').show();
	$('.register-mask').show();
	
	
})*/


/* 关闭登录弹窗 */
$('.register-close >img').click(function(){
	$('.register-mask').hide();
	$('.login').hide();
	$('.huoduoming').hide();
	
});

$('.login-but').click(function(){
	 $.ajax({
			url: '/users/login',
			type: 'post',
			data:{
				'account':$('.login-iphone').val(),
				'password':$('.loginpassword').val(),
			},
			dataType: 'json',
			success: function(d) {
			    if(d.code==200){
			    	$('.Success').show()
			    	$('.register-mask').show();
			    	
			    	$('.login').hide();
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
})


$('.banner-link').click(function(){
	$('.huoduoming').show();
	$('.register-mask').show()
})

$('.login-but-new').click(function(){
	window.location.href="http://static.lingmoney.cn/wap/index.html"
})


}); 


































