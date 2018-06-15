$(function(){
	document.onkeydown = function(e) {
	 //禁用回车事件 
	 var ev = (typeof event!= 'undefined') ? window.event : e;
	 if(ev.keyCode == 13) {
	  return false;
	 }
	}
});

function showPop(id){
  $("#rz-box-bg").show();
  $(id).show();
  offsetDiv(id);
  
  $(id).find(".rz-close,.rz-button").unbind();
  $(id).find(".rz-close,.rz-button").on("click",function(){
	  $("#rz-box-bg").hide();
		  $(id).hide();
	  })
  }
	
//自定义alert
function sAlert(msg){
	if(undefined==msg) {
		msg = "系统错误,请联系管理员";
	}
	var html = '<div id="sShadow" style="position: fixed;z-index: 9999;left: 0;top: 0;width: 100%;height: 100%;background:#000;opacity:0.6;filter:alpha(opacity=60);"></div>'
	html += '<div style="position: fixed;z-index: 9999;left: 50%;top: 50%;padding-bottom: 10px;width: 380px;background: #fff;border:1px solid #ea5413;" id="sAlert">';
	html += '<div style="height: 40px;line-height:40px;padding-left:22px;border: 0;border-bottom: 1px solid #ea5413;border-left: 8px solid #ea5413;"><h2>提示</h2></div>';
	html += '<p style="padding-bottom: 20px; font-size: 18px;border-bottom:1px solid #cccccc; color: #c54107; text-align:center;padding-left:20px;padding-right:20px;margin-top:22px;">'+msg+'</p>';
	html += '<p style="padding-top: 10px;text-align:center;padding-left:14px;">';
	html += '<a href="javascript:void(0)" onclick="sAlertClose()"  style="text-align:center;padding: 0;display: inline-block;margin-right: 20px;width: 140px;height: 30px;line-height: 30px;font-size: 16px;color: #fff;-webkit-border-radius: 5px;-ms-border-radius: 5px;-o-border-radius: 5px;-moz-border-radius: 5px;border-radius: 5px;background: #ea5413;">确定</a></p></div>';
	$('body').append(html);
	$('#sAlert').show();
	height = $('#sAlert').height() + 30;
	width = $('#sAlert').width();
	$('#sAlert').css("margin-top", -height / 2);
	$('#sAlert').css("margin-left", -width / 2);
}

function sAlertClose() {
	$('#sShadow').remove();
	$('#sAlert').remove();
}

//自定义confirm
function sConfirm(msg,ok,cancel,title) {
	if(undefined==title) {
		title = "提示";
	}
	var html = '<div id="sShadow" style="position: fixed;z-index: 9999;left: 0;top: 0;width: 100%;height: 100%;background:#000;opacity:0.6;filter:alpha(opacity=60);"></div>';
	html += '<div id="sConfirm" style="position: fixed;z-index: 9999;left: 50%;top: 50%; right:50%;bottom:50%; margin:-100px -190px;padding-bottom: 30px;width:380px; height:200px;background: #fff; border:1px solid #ea5413;">';
	html += '<div style="height: 40px;line-height:40px;padding-left:22px; border: 0;border-bottom: 1px solid #ea5413;border-left: 8px solid #ea5413;"><h2>'+title+'</h2></div>';
	html +='<div class="clearfix" style="height:111px;border-bottom:1px solid #cccccc;"><div style="float:left;width:60px;height:60px; margin: 25px 0 0 75px;"><img src="/resource/images/lingbaoMarketImages/shanchutishi.jpg"/></div>';
	if(msg.indexOf("_")>0) {
		var msgs = msg.split("_");
		html += '<div style="float:left;width:220px;height:60px;margin: 20px 0 0 20px;"><h3 style="font-weight: normal;color:#ea5513;font-size:20px;margin-bottom:11px;">'+msgs[0]+'</h3><p>'+msgs[1]+'</p>';
	} else {
		html += '<div style="float:left;width:220px;height:60px;margin: 40px 0 0 20px;"><h3 style="font-weight: normal;color:#ea5513;font-size:20px;margin-bottom:11px;">'+msg+'</h3><p></p>';
	}
	html += '</div></div>';
	/*html += '<p style="padding-top: 0px; font-size: 30px; color: #c54107; text-align:center;padding-left:14px;margin-top:15px;">'+msg+'</p>';*/
	html += '<p style="padding-top: 10px;text-align:center;padding-left:14px;margin-top:11px;"><a style="text-align:center;padding: 0;display: inline-block;margin-right: 20px;width: 118px;height: 28px;line-height: 28px;border:1px solid #ea5413; font-size: 14px;color: #ea5413;-webkit-border-radius: 3px;-ms-border-radius: 3px;-o-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px;background: #ffffff;" href="javascript:void(0)">确定</a>';
	html += '<a href="javascript:void(0)" style="text-align:center;padding: 0;display: inline-block;margin-right: 20px;width: 120px;height: 30px;line-height: 30px;font-size: 14px;-webkit-border-radius: 3px;-ms-border-radius: 3px;-o-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px;background: #ea5413;color: #ffffff;">取消</a></p></div>';
	$("body").append(html);
	$("#sShadow").focus();
	$("#sConfirm a:eq(0)").click(function(){
		$("#sShadow").remove();
		$("#sConfirm").remove();
		ok();
	});
	$("#sConfirm a:eq(1)").click(function(){
		$("#sShadow").remove();
		$("#sConfirm").remove();
		cancel();
	});
}
