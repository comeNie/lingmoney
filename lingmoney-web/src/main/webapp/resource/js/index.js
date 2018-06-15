var banner_index = 2,prv_banner_index=1;
var banner_num ;
var s ;
var delayTime ;
var obj ;
$(document).ready(function(){
	
	$(".banner_ctrl a").hover(function(){
		$(this).fadeTo(100,.5);
		},function(){
		$(this).fadeTo(100,.1);
	});
			
	var tNum=$(".m_banner .banner").length-1;
	var nNum=0;		
	$(".banner_ctrl .prev").click(function(){
		(nNum-1)<0?n2=tNum:n2=nNum-1;
		bSwitch(nNum,n2);
		nNum=n2;
	});	
	$(".banner_ctrl .next").click(function(){
		(nNum+1)>tNum?n2=0:n2=nNum+1;
		bSwitch(nNum,n2);
		nNum=n2;
	});
	function bSwitch(nNum,n2){
		$(".m_banner .banner:eq("+nNum+")").fadeOut();
		$(".m_banner .banner:eq("+n2+")").fadeIn();
	};
							
	$(function(){
		var switchTime;
	 	$(".m_banner").hover(function(){
			clearInterval(switchTime);
		},function(){
		switchTime = setInterval(function(){
			(nNum+1)>tNum?n2=0:n2=nNum+1;
			bSwitch(nNum,n2);
			nNum=n2;
		},3000);
		}).trigger("mouseleave");
	});
	
	$(".notice_box").animate({bottom:0});
	$(".notice_box_t .close").click(function(){
		$(".notice_box").fadeOut();
	});
	
	
	/*city toggle*/
	$(".adress-box").click(function(){
	 $(".dropdown").toggle();
	});
	$(".dropdown li a").click(function(){
		$(".dropdown").css("display","none");
	});

});

function delayHide(){
	$(obj).children(".s_nav").removeClass("fade_in");
	if(delayTime)
		clearTimeout(delayTime);
	delayTime = null;
	obj = null;
}

function slide() {
	if (banner_index > banner_num) {banner_index = 1;} 
	$('#c'+banner_index).addClass("curr");
	if(prv_banner_index > 0){
		$('#c'+prv_banner_index).removeClass("curr");
	}
	$('#banner'+banner_index).fadeIn(500);
	if(prv_banner_index > 0){
		$('#banner'+prv_banner_index).hide(); 
	}
	prv_banner_index=banner_index;
	banner_index++;
}
function convert(index){
	if($('#c'+index).hasClass("curr"))
		return ;
	
	if(s != null){
		clearInterval(s);
		s = null ;
	}
	banner_index = index ;
	prv_banner_index = ( prv_banner_index == 0 ? 1 : prv_banner_index );
	$('#c'+banner_index).addClass("curr");
	$('#c'+prv_banner_index).removeClass("curr");
	$('#banner'+banner_index).fadeIn(500);
	$('#banner'+prv_banner_index).hide();
	prv_banner_index=banner_index;banner_index++;
	if(s == null )
		s=setInterval(slide, 3000); 
}
function go(url){
	$("#gform").attr("action",url);
	$("#gform").submit();
}

/* 开通华兴E账户，开通绑卡提示js开始 */
function checkHxAccount() {
	var accountOpen = $("#accountOpen").val();
	var bindBank = $("#bindBank").val();
	if (accountOpen == 0) {
		$("#hxAccountDialog p:first").text("请先开通华兴银行存管账户");
		$("#hxAccountDialog a:last").text("立即开通");
		$("#hxAccountDialog").show();
		$("#rz-box-bg").show();
		offsetDiv("#hxAccountDialog");
	} else if (bindBank == 0) {
		$("#hxAccountDialog p:first").text("请绑定银行卡激活E账户");
		$("#hxAccountDialog a:last").text("激活E账户");
		$("#hxAccountDialog").show();
		$("#rz-box-bg").show();
		offsetDiv("#hxAccountDialog");
	}
}
/* 开通华兴E账户，开通绑卡提示js开始 */
