<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>母亲节特供</title>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	width: 100%;
	height: 1642px;
	background:
		url('/resource/images/ad4cb561-ec05-498d-9bc2-1b088accd34f.png')
		center 123px no-repeat;
}

.springFestival {
	position: relative;
	width: 1200px;
	margin: 0px auto;
	height: 1642px;
}

.activity-btn {
	position: absolute;
	width: 380px;
	height: 80px;
	line-height: 70px;
	background:
		url('/resource/images/5c89eaee-b90c-46b6-b325-e2517f324c91.png')
		no-repeat center center;
	background-size: 100%;
	top: 1487px;
	left: 416px;
	color: #FFFFFF;
	font-size: 20px;
	text-align: center;
}

.buy-btn a {
	position: absolute;
	display: inline-block;
	left: 455px;
	top: 1103px;
	width: 290px;
	height: 60px;
	/*background-color:red;*/
}

.in {
	-webkit-animation-timing-function: ease-out;
	-webkit-animation-duration: 350ms;
	animation-timing-function: ease-out;
	animation-duration: 350ms;
}

.out {
	-webkit-animation-timing-function: ease-in;
	-webkit-animation-duration: 225ms;
	animation-timing-function: ease-in;
	animation-duration: 225ms;
}

.viewport-flip {
	-webkit-perspective: 1000px;
	perspective: 1000px;
	position: absolute;
}

.flip {
	-webkit-backface-visibility: hidden;
	-webkit-transform: translateX(0);
	/* Needed to work around an iOS 3.1 bug that causes listview thumbs to disappear when -webkit-visibility:hidden is used. */
	backface-visibility: hidden;
	transform: translateX(0);
}

.flip.out {
	-webkit-transform: rotateY(-90deg) scale(.9);
	-webkit-animation-name: flipouttoleft;
	-webkit-animation-duration: 175ms;
	transform: rotateY(-90deg) scale(.9);
	animation-name: flipouttoleft;
	animation-duration: 175ms;
}

.flip.in {
	-webkit-animation-name: flipintoright;
	-webkit-animation-duration: 225ms;
	animation-name: flipintoright;
	animation-duration: 225ms;
}

@
-webkit-keyframes flipouttoleft {from { -webkit-transform:rotateY(0);
	
}

to {
	-webkit-transform: rotateY(-90deg) scale(.9);
}

}
@
keyframes flipouttoleft {from { transform:rotateY(0);
	
}

to {
	transform: rotateY(-90deg) scale(.9);
}

}
@
-webkit-keyframes flipintoright {from { -webkit-transform:rotateY(90deg)scale(.9);
	
}

to {
	-webkit-transform: rotateY(0);
}

}
@
keyframes flipintoright {from { transform:rotateY(90deg)scale(.9);
	
}

to {
	transform: rotateY(0);
}

}
.list {
	position: absolute;
}

<!--
-webkit-perspective: 1000px ;
-moz-perspective: 1000px ; -->.redcard-box {
	width: 530px;
	height: 670px;
	position: relative;
	border: solid #000 1px;
	overflow: hidden;
}

.redcard-warp {
	z-index: 1000000;
	width: 530px;
	position: fixed;
	top: 15%;
	overflow: hidden;
	height: 735px;
	left: 50%;
	margin-left: -265px;
	display: none
}

.redcard {
	width: 530px;
}

.redcard img {
	width: 530px;
}

.redcard-click {
	width: 320px;
	height: 60px;
	background: #f8c282;
	color: #e93f44;
	line-height: 60px;
	border-radius: 10px;
	font-size: 32px;
	text-align: center;
	position: absolute;
	z-index: 20000000000;
	top: 596px;
	left: 113px;
}

.redmoney {
	width: 435px;
	height: 148px;
	position: relative;
	top: 314px;
	left: 47px;
	text-align: center;
	font-size: 38px;
	color: #fff;
}

.error_tip {
	position: fixed;
	top: 50%;
	/* height: 0.68rem; */
	width: 5rem;
	font-size: .24rem;
	/*  line-height: .68rem; */
	border-radius: 3px;
	background: rgba(0, 0, 0, 0.5);
	text-align: center;
	color: #fff;
	left: 50%;
	/*  transform: translate(-50%,-50%); */
	z-index: 100;
	opacity: 0;
	display: none;
	margin-left: -2.5rem;
	padding: 0.2rem 0.1rem;
}

.index-Popup-bg {
	width: 100%;
	height: 100%;
	position: fixed;
	opacity: 0.5;
	background: #000;
	z-index: 99;
	left: 0;
	top: 0;
	display: none;
}

.redcard-closed {
	text-align: right;
	line-height: 0rem;
	font-size: 0rem;
	padding: 10px;
}

.redcard-closed img {
	width: 44px;
}

.lookredcaed {
	cursor: pointer;
}
</style>

<div class="springFestival">
	<div class="buy-btn">
		<a href="/product/list/0_0_0_0_0_1"></a>
	</div>
	<a href="/users/login" id="href-link"><div class="activity-btn">请先登录</div></a>
</div>



<div class="index-Popup-bg"></div>

<!-- 红包 -->
<div class="redcard-warp">
	<div class="redcard-closed">
		<img src="/resource/images/closed.png" alt="" />
	</div>
	<div class="redcard-box">
		<div id="box" class="box viewport-flip redcard-click" title="" data-class="0">马上拆红包</div>
		<div href="javascript:void(0)" class="list flip out redcard">
			<img src="/resource/images/redcard01.png" alt="红包正面" />
		</div>
		<div href="javascript:void(0)" class="list flip redcard">
			<img src="/resource/images/redcard1.png" alt="红包背面" />
		</div>
		<div class="redmoney"></div>
	</div>
</div>
<!-- 红包 end-->

<script>
	$.ajax({
		url : '/activityReward/findUserRedPacketCount',
		type : 'post',
		data : {

		},
		dataType : 'json',
		success : function(d) {
			if (d.code == 3009) {
				$('.activity-btn').html('请先登录');
				$('.activity-btn').click(function() {
					window.location.href = "/users/login";
				})
			} else {
				$('#href-link').attr('href','javascript:void(0)');
				if (d.count == 0) {
					$('.activity-btn').html('暂无红包').removeClass('lookredcaed');
				} else {
					$('.activity-btn').html('您有' + d.count + '个红包可领取').addClass('lookredcaed');
					
				}
			}
		},
		error : function(d) {
		}
	});
	//点击领取
	$('body').on('click', '.lookredcaed', function() {
		$('.index-Popup-bg').show();
		$('.redcard-warp').show();

	})

	//点击关闭
	$('.redcard-closed >img').click(function() {
		$('.index-Popup-bg').hide();
		$('.redcard-warp').hide();
		window.location.reload()
	})

	// 在前面显示的元素，隐藏在后面的元素
	var eleBack = null, eleFront = null,
	// 纸牌元素们 
	eleList = $(".list");

	// 确定前面与后面元素
	var funBackOrFront = function() {
		eleList.each(function() {
			if ($(this).hasClass("out")) {
				eleBack = $(this);
			} else {
				eleFront = $(this);
			}
		});
	};
	funBackOrFront();
	$("#box").bind("click", function() {
		// 切换的顺序如下
		// 1. 当前在前显示的元素翻转90度隐藏, 动画时间225毫秒
		// 2. 结束后，之前显示在后面的元素逆向90度翻转显示在前
		// 3. 完成翻面效果

		$.ajax({
			url : '/activityReward/findUserRedPacketCount',
			type : 'post',
			data : {

			},
			dataType : 'json',
			success : function(d) {
				count = d.count
				if (d.count == 0) {
					$('.redcard-click').html('红包已领取完');
				} else {
					callbank();
				}

			},
			error : function(d) {
			}
		});
		var callbank = function() {
			if ($("#box").attr('data-class') == 0) {
				$.ajax({
					url : '/activityReward/clickRedPacketDo',
					type : 'post',
					dataType : 'json',
					success : function(d) {
						if (d.code == 200) {
							eleFront.addClass("out").removeClass("in");
							$("#box").attr('data-class', '1');
							setTimeout(function() {
								eleBack.addClass("in").removeClass("out");
								$('.redcard-click').html('收下它');
								$('.redmoney').html('恭喜你获得' + d.money + '元红包');
								// 重新确定正反元素
								funBackOrFront();
							}, 225);
							return false;
						} else if (d.code == 502) {
							if (!$('.error_tip').is(":animated")) {
								$('.error_tip').show(100, function() {
									$('.error_tip').html("暂无数据");
									$('.error_tip').animate({
										opacity : 1,
									}, 1000, function() {
										window.setTimeout(function() {
											$('.error_tip').animate({
												opacity : 0,
											}, 1000, function() {
												$('.error_tip').hide();
											});
										}, 2000)
									});
								});
							}
						} else if (d.code == 1006) {
							window.location.href = "/users/login";
						}
					},
					error : function(d) {
					}
				});
			} else if ($("#box").attr('data-class') == 1) {
				eleFront.addClass("out").removeClass("in");
				$("#box").attr('data-class', '0')
				setTimeout(function() {
					eleBack.addClass("in").removeClass("out");
					$('.redcard-click').html('马上拆红包')
					$('.redmoney').html('')
					// 重新确定正反元素
					funBackOrFront();
				}, 225);
				return false;
			}
		}

	});
</script>









