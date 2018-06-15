$(function(){ 

$('.Invitation-tabs li').click(function(){
  $(this).addClass('Invitation-active').siblings().removeClass('Invitation-active');
  $('.Invitation-uptop-box >.Invitation-none').eq($(this).index()).show().siblings('.Invitation-uptop-box >.Invitation-none').hide();

});

//活动说明

$('.Invitation-btn-left').click(function(){
	$('.index-Popup-bg').show();
	$('.Invitation-tc').show();
})

$('.Invitation-tc >i').click(function(){
	$('.index-Popup-bg').hide();
	$('.Invitation-tc').hide();
})
//获取RUL参数值
	var urlTools = {
		getUrlParam: function(name) { /*?videoId=identification  */
			var params = decodeURI(window.location.search); /* 截取？号后面的部分    index.html?act=doctor,截取后的字符串就是?act=doctor  */
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = params.substr(1).match(reg);
			if (r != null) return unescape(r[2]);
			return null;
		}
	};

var needLogin = urlTools.getUrlParam("needLogin");
var isLogin = urlTools.getUrlParam("isLogin");
//ios Android判断
 var u = navigator.userAgent;
    var ua = navigator.userAgent.toLowerCase();
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if(isiOS){
    	if(needLogin=='N'){
    		 $('#ios-Android').html('立即登录')
    		 $('#ios-Android').attr('href','/gotoLogin')
    		
    		/*window.location.href="/gotoLogin"*/
    	}else{
    		 $('#ios-Android').html('邀请好友')
    		 $('#ios-Android').attr('href','javascript:volid(0)').closest('.Invitation-btnt').addClass('iosclick')
    	}
		}else if(isAndroid){
			
			if(isLogin==1){
				 $('#ios-Android').html('立即登录')
	    		 $('#ios-Android').attr('href','/gotoLogin')
	    		/*window.location.href="/gotoLogin"*/
	    	}else{
	    		 $('#ios-Android').html('邀请好友')
	    		$('#ios-Android').attr('href','/recNow').closest('.Invitation-btnt').removeClass('iosclick')
	    	}
			
			
			
		    
		}

    
    
  //推荐码
    $.ajax({
    	url:'/users/getMyRefferCode?token='+$('#token').val(),
        type:'get',
        data:{
        	/*token:token*/
        },
        success: function(data) {
        	if(data.code == 200) {
                $('.Invitation-code >img').attr('src',data.obj.codePath)
        	} 
        }
    })	   



    $('.iosclick').click(function(){
    	$('.index-Popup-bg').show();
    	$('.Invitation-ios').show();
    })
    $('.Invitation-ios >i').click(function(){
    	$('.index-Popup-bg').hide();
    	$('.Invitation-ios').hide();
    })    
    
}); 


//滚动
function autoScroll(obj){  
	$(obj).find(".Invitationul").animate({  
		marginTop : "-0.38rem"  
	},500,function(){  
		$(this).css({marginTop : "0rem"}).find("li:first").appendTo(this);  
	})  
   }  
$(function(){  
	setInterval('autoScroll(".maquee")',3000);
	  
}) 






























