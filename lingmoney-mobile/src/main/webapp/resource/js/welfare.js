$(function() {
	$('body').on('click','.welfare-botton',function(){
		var lovezhi = $('.lovezhi').html();
		if (lovezhi > 0) {
			$('.welfare-details-fc').show()
			$('.index-Popup-bg').show()
			$(this).hide();
			$('.welfare-botton-ok').show();
		} else {
			$('.welfare-box-tc').show(100, function() {
				$('.welfare-box-tc').html('每天登录送爱心值哦～');
				$('.welfare-box-tc').animate({
					opacity : 1,
					top : '50%'
				}, 1000, function() {
					window.setTimeout(function() {
						$('.welfare-box-tc').animate({
							opacity : 0,

						}, 1000, function() {
							$('.welfare-box-tc').hide();
						});
					}, 2000)
				});
			});
		}
	})
	num = 1;
	$('.welfare-add').click(function() {
		var lovezhi = Number($('.lovezhi').html());
		var fpoints = Number($('.fpoints').html());
		num++;
		if (lovezhi == Number($('.welfare-input').html())) {
			num = lovezhi
		} else if (fpoints == Number($('.welfare-input').html())) {
			num = fpoints
		}
		$('.welfare-input').html(num)

	})

	$('.welfare-reduce').click(function() {
		var lovezhi = n = Number($('.lovezhi').html());

		if (Number($('.welfare-input').html()) > 1) {
			num--;
		}

		$('.welfare-input').html(num)

	})
	// 关闭
	$('.welfare-close').click(function() {
		$('.welfare-details-fc').hide()
		$('.index-Popup-bg').hide()
		$('.welfare-botton').show();
		$('.welfare-botton-ok').hide()
		$('.welfare-input').html(1);
		num = 1;
	})
	$('body').on('click','.welfare-botton-ok',function(){
		sponsorProject();
	})

	// 进度条计算
	var points = Number($('.points').html());
	var fpoints = Number($('.fpoints').html());

	$('.welfare-zhi').css('width', (4.35 / points) * fpoints + 'rem')
	
});

function sponsorProject(){
	$.ajax({
		async:false,
		cache: false,
		url:"/commonweal/sponsorProject?r=" + new Date().getTime(),
		data:{
			token: $("#token").val(),
			loveNumber: $(".welfare-input").text(),
			commonwealId:$("#commonwealId").val()
		},
		success:function(data) {
			if(data.code == 200){
				$('.fpoints').html(data.obj);
				
				$('.welfare-details-fc').hide()
				$('.index-Popup-bg').hide()
				$(this).hide();
				$('.welfare-botton').show();
				$('.welfare-input').html(1);
				num = 1;
				$('.welfare-box-tc').show(100, function() {
					$('.welfare-box-tc').html('已收到您的爱心哦～');
					$('.welfare-box-tc').animate({
						opacity : 1,
						top : '50%'
					}, 1000, function() {
						window.setTimeout(function() {
							$('.welfare-box-tc').animate({
								opacity : 0,

							}, 1000, function() {
								$('.welfare-box-tc').hide();
							});
						}, 2000)
					});
				});
				
			} else{
				$('.welfare-details-fc').hide()
				$('.index-Popup-bg').hide()
				$(this).hide();
				$('.welfare-botton').show();
				$('.welfare-input').html(1);
				num = 1;
				$('.welfare-box-tc').show(100, function() {
					$('.welfare-box-tc').html(data.msg);
					$('.welfare-box-tc').animate({
						opacity : 1,
						top : '50%'
					}, 1000, function() {
						window.setTimeout(function() {
							$('.welfare-box-tc').animate({
								opacity : 0,

							}, 1000, function() {
								$('.welfare-box-tc').hide();
							});
						}, 2000)
					});
				});
			}
			queryUsersLoveNum();
		}
	});
}

function queryUsersLoveNum(){
	$.ajax({
		async:false,
		cache: false,
		url:"/commonweal/getUserLoveNum?r=" + new Date().getTime(),
		data:{
			token: $("#token").val()
		},
		success:function(data) {
			var html = '';
			if(data.code == 200){
				html += '<li>我的爱心<b class="lovezhi">' + data.obj + '</b></li>';
				html += '<li>已捐赠<b>' + data.obj2 + '</b></li>';
				html += '<li><p class="welfare-botton">捐赠爱心</p> <p class="welfare-botton-ok">确定</p></li>';
			} else if(data.code == 1006){
				html += '<li>我的爱心<b class="lovezhi">0</b></li>';
				html += '<li>已捐赠<b>0</b></li>';
				html += '<li><p class="welfare-botton">捐赠爱心</p> <p class="welfare-botton-ok">确定</p></li>';
			}else{
				html += '<li>登录查看我的爱心</li>';
			}
			$('.welfare-loveul').html(html);
		}
	});
}

function listPageQueryUsersLoveNum(){
	$.ajax({
		async:false,
		cache: false,
		url:"/commonweal/getUserLoveNum?r=" + new Date().getTime(),
		data:{
			token: $("#token").val()
		},
		success:function(data) {
			var html = '';
			if(data.code == 200){
				$('.welfare-yuan').html('<span>'+data.obj+'</span><br>我的爱心');
			} else if(data.code == 1006){
				$('.welfare-yuan').html('<span>'+data.obj+'</span><br>我的爱心');
			}else{
				$('.welfare-yuan').html('<br><a href="/gotoLogin">登录</a>');
			}
		}
	});
}

/**
 * 获取公益项目列表
 * @returns
 */
function queryCommonwealProject(){
	$.ajax({
		async:false,
		cache: false,
		url:"/commonweal/list?r=" + new Date().getTime(),
		data:{
			token: $("#token").val(),
			type:1,
			page:1,
			rows:5
		},
		success:function(data) {
			if(data.code == 200){
				var array = data.data;
				var html = '';
				for (var i = 0; i < array.length; i++) {
					var obj = array[i];
					html += '<a href="/commonweal/openDetailsPage?token=' + $("#token").val() +'&commonwealId=' + obj.id +'">'
					html += '<div class="welfare-box-list">';
					html += '<div class="welfare-box-list-img"><img alt="" src="'+obj.pbaPicture+'"></div><div class="welfare-box-wb">';
					html += '<p class="welfare-box-wbl">' + obj.pbaName + '</p>';
					if (obj.status == 2) {
						html += '<p class="welfare-box-wbr">已完成</p>';
					} else{
						html += '<p class="welfare-box-wbr"><img src="/resource/images/welfare1.png" />' + obj.sumPraise + '/'+obj.maxPraise+'</p>';
					}
					html += '</div></div></a>';
				}
				$('#commonwealProject .welfare-box').html(html);
			} 
		}
	});
}

function querySponsorProject(){
	$.ajax({
		async:false,
		cache: false,
		url:"/commonweal/list?r=" + new Date().getTime(),
		data:{
			token: $("#token").val(),
			type:2,
			page:1,
			rows:5
		},
		success:function(data) {
			if(data.code == 200){
				var array = data.data;
				var html = '';
				for (var i = 0; i < array.length; i++) {
					var obj = array[i];
					html += '<a href="/commonweal/openDetailsPage?token=' + $("#token").val() +'&commonwealId=' + obj.id +'">'
					html += '<div class="welfare-box-list">';
					html += '<div class="welfare-box-list-img"><img alt="" src="'+obj.pbaPicture+'"></div><div class="welfare-box-wb">';
					html += '<p class="welfare-box-wbl">' + obj.pbaName + '</p>';
					if (obj.status == 2) {
						html += '<p class="welfare-box-wbr">已完成</p>';
					} else{
						html += '<p class="welfare-box-wbr"><img src="/resource/images/welfare01.png" />' + obj.sumPraise + '/'+obj.maxPraise+'</p>';
					}
					html += '</div></div></a>';
				}
				$('#sponsorProject .welfare-box').html(html);
			} else if(data.code== 1006){
				$('#sponsorProject .welfare-box').html('<span style="font-size:0.24rem;text-align: center;display:block;color:#999;padding-top:25%;">暂未赞助公益项目！</span>');
				
			} else{
				$('#sponsorProject .welfare-box').html('<span style="font-size:0.24rem;text-align: center;display:block;color:#999;padding-top:25%;">暂未登录，请先登录！</span>');
			}
		}
	});
}




