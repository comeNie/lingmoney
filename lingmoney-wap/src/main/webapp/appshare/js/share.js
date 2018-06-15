_windowUrl = window.location.href;
_appId = "";
_timestamp = "";
_nonceStr = "";
_signature = "";
_url = "";


window._appId = '';
window._timestamp = '';
window._nonceStr = '';
window._signature = '';
window._url = '';

$(function(){ 
	$.ajax({
		url : locationUrl + '/wechat/wechatShareUrl',
		data : {
			shareUrl :  locationUrl + '/appshare/product/product_details.html?id=1825&pType=2',
			from : lyw.urlTools.getUrlParam('from'),
			isappinstalled : lyw.urlTools.getUrlParam('isappinstalled')
		},
		async : false,
		type : 'post',
		dataType : 'json',
		success : function(d) {
			if (d.code == 200) {
				window._appId = d.data.appId;
				window._timestamp = d.data.timestamp;
				window._nonceStr = d.data.nonceStr;
				window._signature = d.data.signature;
				window._url = d.data.url;
			}
		},
		error : function(d) {
		}
	});

	wx.config({
		debug : true,
		appId : window._appId,
		timestamp : window._timestamp,
		nonceStr : window._nonceStr,
		signature : window._signature,
		jsApiList : [ 'onMenuShareTimeline', 'onMenuShareAppMessage',
				'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone' ]
	// 功能列表，我们要使用JS-SDK的什么功能
	});

	wx.ready(function() {
		// 获取"分享到朋友圈"按钮点击状态及自定义分享内容接口
		wx.onMenuShareTimeline({
			title : window.wxtitle, // 分享标题
			desc : window.wxdesc, // 分享描述
			link : window._url,
			imgUrl : 'http://www.ruibowealth.com/parentChild/images/share.png' // 分享图标
		});

		wx.onMenuShareAppMessage({
			title : window.wxtitle, // 分享标题
			desc : window.wxdesc, // 分享描述
			link : window._url, // 分享链接
			imgUrl : 'http://www.ruibowealth.com/parentChild/images/share.png', // 分享图标
			type : 'link' // 分享类型,music、video或link，不填默认为link
		});

		wx.onMenuShareQQ({
			title : window.wxtitle, // 分享标题
			desc : window.wxdesc, // 分享描述
			link : window._url, // 分享链接
			imgUrl : 'http://www.ruibowealth.com/parentChild/images/share.png' // 分享图标
		});

		wx.onMenuShareWeibo({
			title : window.wxtitle, // 分享标题
			desc : window.wxdesc, // 分享描述
			link : window._url, // 分享链接
			imgUrl : 'http://www.ruibowealth.com/parentChild/images/share.png' // 分享图标
		});

		wx.onMenuShareQZone({
			title : window.wxtitle, // 分享标题
			desc : window.wxdesc, // 分享描述
			link : window._url, // 分享链接
			imgUrl : 'http://www.ruibowealth.com/parentChild/images/share.png' // 分享图标
		});
	});
});
