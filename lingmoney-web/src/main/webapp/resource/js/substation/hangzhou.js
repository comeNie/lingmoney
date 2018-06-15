$(function() {
	startPrd();
	/* 会员登陆 */
	if ($("#session").val() == "") {
		$(".register ul li a:eq(0)").css("color", "#ed6e31");
		$(".register ul li a:eq(0)")
				.hover(
						function() {
							$(".register ul li a:eq(0)")
									.css("color", "#ed6e31")
							$(".register ul li a:eq(1)").css("color", "#fff")
							$(".register")
									.css("background",
											"url(/resource/images/bg_top.png) no-repeat left top");
							$(".register ol li").hide();
							$(".register ol li:eq(0)").show();

						}, function() {
						});

		$(".register ul li a:eq(1)")
				.hover(
						function() {
							$(".register ul li a:eq(1)")
									.css("color", "#ed6e31");
							$(".register ul li a:eq(0)").css("color", "#fff");
							$(".register")
									.css("background",
											"url(/resource/images/bg_top2.png) no-repeat left top");
							$(".register ol li").hide();
							$(".register ol li:eq(1)").show();
							$("li.form input").css("background", "#fff")

						}, function() {
						});
	} else {
		$(".register ul li a:eq(1)").css("color", "#ed6e31");
		$(".register").css("background",
				"url(/resource/images/bg_top2.png) no-repeat left top");
		$(".register ol li:eq(0)").hide();
		$(".register ol li:eq(1)").show();
		$(".register ul li a:eq(0)")
				.hover(
						function() {
							$(".register ul li a:eq(0)")
									.css("color", "#ed6e31")
							$(".register ul li a:eq(1)").css("color", "#fff")
							$(".register")
									.css("background",
											"url(/resource/images/bg_top.png) no-repeat left top");
							$(".register ol li").hide();
							$(".register ol li:eq(0)").show();

						}, function() {
						});

		$(".register ul li a:eq(1)")
				.hover(
						function() {
							$(".register ul li a:eq(1)")
									.css("color", "#ed6e31");
							$(".register ul li a:eq(0)").css("color", "#fff");
							$(".register")
									.css("background",
											"url(/resource/images/bg_top2.png) no-repeat left top");
							$(".register ol li").hide();
							$(".register ol li:eq(1)").show();

						}, function() {
						});
	}
	/* 会员登陆 */

	/* 会员登录 */
	$(".register ul li a:eq(0)").css("color", "#ed6e31")
	$(".register ul li a:eq(0)")
			.hover(
					function() {
						$(".register ul li a:eq(0)").css("color", "#ed6e31");
						$(".register ul li a:eq(1)").css("color", "#fff");
						$(".register")
								.css("background",
										"url(/resource/images/qdimages/bg_top_qd.png) no-repeat left top");
						$(".register ol li").hide();
						$(".register ol li:eq(0)").show();

					}, function() {
					})

	$(".register ul li a:eq(1)")
			.hover(
					function() {
						$(".register ul li a:eq(1)").css("color", "#ed6e31");
						$(".register ul li a:eq(0)").css("color", "#fff");
						$(".register")
								.css("background",
										"url(/resource/images/qdimages/bg_top_qd2.png) no-repeat left top");
						$(".register ol li").hide();
						$(".register ol li:eq(1)").show();

					}, function() {
					})
	/* 会员登录 */

	/* banner */
	$(".fullSlide").hover(function() {
		$(this).find(".prev,.next").stop(true, true).fadeTo("show", 0.5)
	}, function() {
		$(this).find(".prev,.next").fadeOut()
	});
	$(".fullSlide").slide(
			{
				titCell : ".hd ul",
				mainCell : ".bd ul",
				effect : "fold",
				autoPlay : true,
				autoPage : true,
				trigger : "click",
				startFun : function(i) {
					var curLi = $(".fullSlide .bd li").eq(i);
					if (!!curLi.attr("_src")) {
						curLi.css("background-image", curLi.attr("_src"))
								.removeAttr("_src")
					}
				}
			});
	/* banner */

	/* city toggle */
	$(".adress-box").click(function() {
		$(".dropdown").toggle();
	});
	$(".dropdown li a").click(function() {
		$(".dropdown").css("display", "none");
	});

	createProgress();

	//媒体资讯，站内公告切换点击
	$(".ml_box").click(function(){
		
	});
	
	//查询新手标or最新产品
	checkOutNewProduct();
	$('.bi_1').click();
	
});
function createProgress() {
	var bottonProgress = $(".demo [class=div_water]");
	if (bottonProgress.length > 0) {
		for (var i = 0; i < bottonProgress.length; i++) {
			var heigh = $("#progress" + i).val() * 1.6;
			// 项目进度
			$("#waterBProgress" + i).stop().animate({
				bottom : heigh + "px"
			}, 2000);
			$("#waterHProgress" + i).stop().animate({
				height : heigh + "px"
			}, 2000);

		}
	}
}

function testLogin() {
	var account = document.getElementById("account").value;
	var loginPsw = document.getElementById("loginPsw").value;
	var con = sendReq("/users/testLogin", "account=" + account
			+ "&loginPsw=" + loginPsw);
	if (con == "success") {
		return true;
	} else {
		document.getElementById("register-tip").style.display = "block";
		document.getElementById("loginPsw").focus();
		document.getElementById("loginPsw").value = "";
		return false;
	}

}

/*****************明星产品切换****************/
function startPrd(){
	$('.main-body-box .baoImg').each(function(){
		$(this).on('click',function(){
			var imgtag=$(this).attr('imgtag');
			$('.baoBox').stop().animate({
				width:'0px',
				padding:'0px'
			},0);
			$('#baoBox_'+imgtag).stop().animate({
				'width':'530px'
			},500);
		});
	});
}
/*************站内公告和媒体咨询**************/
function announce(){
	$('.ml_tab a').each(function(){
		$()
	});
}

/**
 * 查询新手标or最新产品
 */
function checkOutNewProduct(){
	$.ajax({
		url:'/product/queryNewproduct',
		type:'get',
		success:function(data) {
			data = JSON.parse(data);
			if(data.code==200) {
				var item = data.latestProduct;
				$("#hzTopProductDiv .tipBox").remove();
				$("#hzTopProductDiv span:first").text(item.name);
				$("#hzTopProductDiv span:first").attr('title',item.name);
				$("#hzTopProTag").css("margin-right","0px");
				var tags = item.tags.split(",");
				$("#hzTopProTag span:eq(0)").text(tags[0]);
				$("#hzTopProTag span:eq(1)").text(tags[1]);
				$("#hzTopProTag span:eq(2)").text(tags[2]);
				$("#hzTopProTag span:eq(3)").text(tags[3]);
				if(item.fYield!=null && item.fYield!=0) {
					$("#hzExpectRate").text((Number(item.fYield)*10000/100).toFixed(1)+"%");
				} else {
					if(item.lYield!=item.hYield) {
						$("#hzExpectRate").text((Number(item.lYield)*10000/100).toFixed(1)+"-"+(Number(item.hYield)*10000/100).toFixed(1)+"%");
					} else {
						$("#hzExpectRate").text((Number(item.lYield)*10000/100).toFixed(1)+"%");
					}
				}
				
				if(item.fTime!=null && item.fTime!=0) {
					$("#hzTopProFtime").text(item.fTime);
				} else {
					$("#hzTopProFtime").text(item.lTime);
				}
				
				$("#hzTopProMinMoney").text(item.minMoney);
				$("#hzTopProTips").text("*"+item.minMoney+"起购,"+item.increaMoney+"元递增");
				$("#hzTopProCode").val(item.code);
			}
		}
	});
}

function infoNoticeClick(elem){
	$(elem).next().removeClass("active");
	$(elem).addClass("active");
	$("#hzInfoMediaDiv").hide();
	$("#hzInfoNoticeDiv").show();
	$("#mediaMoreDiv a").attr("href","/infoNotice/list.html");
}

function infoMediaClick(elem){
	$(elem).prev().removeClass("active");
	$(elem).addClass("active");
	$("#hzInfoNoticeDiv").hide();
	$("#hzInfoMediaDiv").show();
	$("#mediaMoreDiv a").attr("href","/infoMedia/list.html");
}