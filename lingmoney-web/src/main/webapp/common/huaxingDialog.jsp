<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
	<style type="text/css">
	    .TipHuaXing{
	        z-index:9999;
	        width: 470px;
	        height: 644px;
	        position: fixed;
	        top: 50%;
	        left: 50%;
	        right:50%;
	        bottom:50%;
	        margin:-322px -235px -322px -235px;
	        background: url("/resource/images/hxAlert_bg.png") no-repeat;
	        border-radius:10px;
	        -ms-border-radius:10px;
	        -moz-border-radius:10px;
	        -webkit-border-radius:10px;
	        overflow: hidden;
	    }
	    .TipHuaXing .th_box{
	        padding-top: 342px;
	        position: relative;
	    }
	    .TipHuaXing .th_box .hx_close{
	        display:block;
	        width:50px;
	        height:50px;
	        position: absolute;
	        top: 80px;
	        right: 0px;
	    }
	    .TipHuaXing .th_box p{
	        font-size: 22px;
	        color: #ea5513;
	        text-align: center;
	        margin-bottom: 212px;
	    }
	    .TipHuaXing .th_box a.ckbtn{
	        display: block;
	        width: 470px;
	        height: 60px;
	        text-align: center;
	        line-height: 60px;
	        color: #ffffff;
	        background: #ea5513;
	        font-size: 22px;
	    }
	    
	    #shadowBox{
		    display: none;
		    position: fixed;
		    z-index: 1000;
		    left: 0;
		    top: 0;
		    width: 100%;
		    height: 100%;
		    background:#000; 
		    opacity:0.6;
		    filter:alpha(opacity=60);
		}
	</style>
	<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
</head>

<body>
	<div id="hxAccountDialog" class="TipHuaXing" style="display: none;">
	    <div class="th_box">
	        <a href="javascript:void(0);" class="hx_close"></a>
	        <p></p>
	        <a class="ckbtn" href="/myAccount/bindBankCard"></a>
	    </div>
	</div>

    <div id="shadowBox"></div>
	<script type="text/javascript">
	    $(function(){
	    	$('.hx_close').click(function() {
	    		$('#hxAccountDialog').hide();
	    		$('#shadowBox').hide();
	    	});
	    });
	    
	    //定位div居中显示
	    function offsetDiv(s) {
	        height = $(s).height() + 30;
	        width = $(s).width();
	        $(s).css("margin-top", -height / 2);
	        $(s).css("margin-left", -width / 2);
	    }
	    
	    //type 0 未开通账户  1 未绑卡
	    function showHXDialog(type) {
	    	if (type == 0) {
	            $('#hxAccountDialog p:first').text('请先开通华兴银行存管账户');
	            $('#hxAccountDialog a:last').text('立即开通');
	            $('#hxAccountDialog').show();
	            $('#shadowBox').show();
	            offsetDiv('#hxAccountDialog');
	        } else if (type == 1) {
	            $('#hxAccountDialog p:first').text('请绑定银行卡激活E账户');
	            $('#hxAccountDialog a:last').text('激活E账户');
	            $('#hxAccountDialog').show();
	            $('#shadowBox').show();
	            offsetDiv('#hxAccountDialog');
	        }
	    }
	</script>
</body>
</html>