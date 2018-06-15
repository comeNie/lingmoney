window._appId = '';
window._timestamp = '';
window._nonceStr = '';
window._signature = '';
window._url = '';

window.wxtitle = "世界杯扫盲测试，三分钟鉴定伪球迷！ ";
window.wxdesc = "是不是伪球迷，测测就知道！";
window.imgUrl = 'http://zldt.lingmoney.cn/resource/images/8485821007493508.jpg';

$(function(){ 
	$.ajax({
		url : locationUrl + '/wechat/wechatShareUrl',
		data : {
			shareUrl :  locationUrl + '/extension/worldCupIndex.html',
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
			imgUrl : window.imgUrl // 分享图标
		});

		wx.onMenuShareAppMessage({
			title : window.wxtitle, // 分享标题
			desc : window.wxdesc, // 分享描述
			link : window._url, // 分享链接
			imgUrl : window.imgUrl, // 分享图标
			type : 'link' // 分享类型,music、video或link，不填默认为link
		});

		wx.onMenuShareQQ({
			title : window.wxtitle, // 分享标题
			desc : window.wxdesc, // 分享描述
			link : window._url, // 分享链接
			imgUrl : window.imgUrl // 分享图标
		});

		wx.onMenuShareWeibo({
			title : window.wxtitle, // 分享标题
			desc : window.wxdesc, // 分享描述
			link : window._url, // 分享链接
			imgUrl : window.imgUrl // 分享图标
		});

		wx.onMenuShareQZone({
			title : window.wxtitle, // 分享标题
			desc : window.wxdesc, // 分享描述
			link : window._url, // 分享链接
			imgUrl : window.imgUrl // 分享图标
		});
	});
	
	
});
