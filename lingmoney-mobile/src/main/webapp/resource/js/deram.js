/**
 * 梦想列表
 * @returns
 */
function queryDreamInfo(){
	$.ajax({
		async:false,
		cache: false,
		url:"/dream/queryDreamInfo?r=" + new Date().getTime(),
		data:{
			token: $("#token").val()
		},
		success:function(data) {
			if(data.code == 200){
				var array = data.data;
				var infoHtml = '';
				for (var i = 0; i < array.length; i++) {
					obj = array[i];
					
					infoHtml += '<p class="Dream-tip">' + obj.mainTitle + '<span>-' + obj.subTitle + '</span></p>';
					infoHtml += '<div class="wrapper wrapper01" id="retr'+i+'"><div class="scroller"> <ul class="clearfix">';
					/*infoHtml += '<ul class="clearfix">';*/
					
					//主标题
					
					var array2 = obj.dreamBaseInfo;
					for (var j = 0; j < array2.length; j++) {
						var obj2 = array2[j];
						var token = $("#token").val();
						//判断用户是否有该梦想，如果有跳转到指定页面，0为领取，1，已有，2，完成
						infoHtml += '<li>';
						if(obj2.haveDrive == 0){
							infoHtml += '<a href="/dream/dreamHold?token=' + token + '&dreamid=' + obj2.id +'">';
//							$('#idhreff').attr("href","/dream/dreamHold?token=" + token + '&dreamid=' + obj2.id )
						}else if(obj2.haveDrive == 1) {
							infoHtml += '<a href="/dream/dreamComplete?token=' + token + '&dreamid=' + obj2.id +'">';
//							$('#idhreff').attr("href","/dream/dreamComplete?token="+ token + '&dreamid=' + obj2.id )
						} else{
							infoHtml += '<a href="/dream/dreamAdd?token=' + token + '&dreamid=' + obj2.id +'">';
//							$('#idhreff').attr("href","/dream/dreamAdd?token=" + token + '&dreamid=' + obj2.id)
						}
						infoHtml += '<div><img src="' + obj2.icon + '"></div>';
						infoHtml += '<p>' + obj2.title + '<br>';
						
						
						if(obj2.haveDrive == 0){
							infoHtml += '<span>进行中</span></p>';
						}else if(obj2.haveDrive == 1) {
							infoHtml += '<span>已完成</span></p>';
						} else{
							infoHtml += '<span>加入梦想</span></p>';
						}
						
						infoHtml += '</a></li>';
					}
					
					infoHtml += '</ul></div></div>';
					
				}
				$('.Dream').html(infoHtml);
			}
		}
	});
}

/**
 * 通过基础信息ID获取详情
 */
function queryBaseDreamInfo(){
	$.ajax({
		async:false,
		cache: false,
		url:"/dream/queryBaseDreamInfo",
		data:{
			baseId: $("#dreamid").val()
		},
		success:function(data) {
			if(data.code == 200){
				var obj = data.data2;
				console.log(obj);
				$(".descOne").html(obj.descOne);
				$(".descTwo").html(obj.descTwo);
				$(".Dreamadd img").attr("src", obj.stereogram);
				$('#requiredAmount').val(obj.requiredAmount);
				$('.requiredAmount').html((obj.requiredAmount/10000).toFixed(2));
			} 
		}
	});
}

/**
 * 通过基础信息ID获取详情
 */
function queryBaseDreamInfo2(){
	$.ajax({
		async:false,
		cache: false,
		url:"/dream/queryBaseDreamInfo",
		data:{
			baseId: $("#dreamid").val()
		},
		success:function(data) {
			if(data.code == 200){
				var obj = data.data2;
				$(".Dreamadd-over img").attr("src", obj.hollowedDiagram);
			} 
		}
	});
}

/**
 * 添加用户梦想
 * @returns
 */
function appendUsersDream(){
	$.ajax({
		async:false,
		cache: false,
		url:"/dream/selectDream?r=" + new Date().getTime(),
		data:{
			token: $("#token").val(),
			baseId: $("#dreamid").val()
		},
		success:function(data) {
			if(data.code == 200){
				//添加提示，添加梦想成功，跳转到持有梦想页面
				
				if(!$('.error_tip').is(":animated")){
					$('.error_tip').show(100, function() {
						$('.error_tip').html(data.msg);
						$('.error_tip').animate({
							opacity: 1,
							},1000, function() {
							window.setTimeout(function(){
								$('.error_tip').animate({
									opacity: 0,
									},
									1000, function() {
									$('.error_tip').hide();
									window.location.href = "/gotoNewProductList?token="+$("#token").val()+'&dreamid='+$("#dreamid").val(); 
								});
							}, 2000)
						});
					});		
				}
				
				
				
				
				
//				window.location.href = "/dream/dreamHold?token="+$("#token").val()+'&dreamid='+$("#dreamid").val(); 
				//拦截跳转到APP产品列表
				/*alert("拦截跳转到APP产品列表");*/
			} else if(data.code == 1006){
				//添加提示,去登录
				window.location.href = "/gotoLogin?token="+$("#token").val()+'&dreamid='+$("#dreamid").val(); 
				/*if(!$('.error_tip').is(":animated")){
					$('.error_tip').show(100, function() {
						$('.error_tip').html(data.msg);
						$('.error_tip').animate({
							opacity: 1,
							},1000, function() {
							window.setTimeout(function(){
								$('.error_tip').animate({
									opacity: 0,
									},
									1000, function() {
									$('.error_tip').hide();
									window.location.href = "/gotoLogin?token="+$("#token").val()+'&dreamid='+$("#dreamid").val(); 
								});
							}, 2000)
						});
					});		
				}*/
				
				
			} else if(data.code == 1007){
				
				//添加提示，已有梦想，跳转到持有的梦想页面
				if(!$('.error_tip').is(":animated")){
					$('.error_tip').show(100, function() {
						$('.error_tip').html(data.msg);
						$('.error_tip').animate({
							opacity: 1,
							},1000, function() {
							window.setTimeout(function(){
								$('.error_tip').animate({
									opacity: 0,
									},
									1000, function() {
									$('.error_tip').hide();
									window.location.href = "/dream/dreamHold?token="+$("#token").val()+'&dreamid='+$("#dreamid").val(); 
								});
							}, 2000)
						});
					});		
				}
				
				
				
			} else {
				//错误提示

				if(!$('.error_tip').is(":animated")){
					$('.error_tip').show(100, function() {
						$('.error_tip').html(data.msg);
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
		}
	});
}

/**
 * 通过token和数据ID查询用户梦想信息
 */
function queryUsersDreamInfo(){
	$.ajax({
		async:false,
		cache: false,
		url:"/dream/queryUsersDreamInfo",
		data:{
			token: $("#token").val(),
			baseId: $("#dreamid").val()
		},
		success:function(data) {
			if(data.code == 200){
				var obj = data.data;
				$(".accumulatedIncome").html((obj.accumulatedIncome/10000).toFixed(2));
				$("#accumulatedIncome").val(obj.accumulatedIncome);
				$(".dreamDistance").html(((obj.accumulatedIncome/$('#requiredAmount').val())/10000).toFixed(2));
			} 
		}
	});
}





