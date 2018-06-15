<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<% 
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", -10);
%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1,IE=EmulateIE7" />
<title>领钱儿 专业的互联网金融理财平台</title>

<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0, user-scalable=yes" />

<meta name="KEYWords" content="领钱，领钱儿，互联网理财，互联网金融，网上理财">
<meta name="DEscription"
	content="领钱儿致力于打造大众值得信赖的理财平台，依托“全民理财”的发展理念，致力于改变大众固有理财观念，帮助大众建立理财习惯，为大众提供专业化、全方位、个性化的资产配置方案，满足大众对于理财的不同需求。同时通过互联网技术让广大的用户享受更便捷、更透明、更智能化的一站式理财服务，使普惠金融，真正惠及大众日常生活。">
<meta name="Author" content="领钱儿">

<link rel="stylesheet" href="/resource/css/index.css">
<link rel="stylesheet" type="text/css" href="/resource/css/style.css"
	media="screen" />
<link rel="icon" type="image/x-icon" href="/resource/ico.ico">
<link rel="shortcut icon" type="image/x-icon" href="/resource/ico.ico">
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/resource/js/ajax.js"></script>
<script type="text/javascript" src="/resource/js/index.js"></script>
<script type="text/javascript" src="/resource/js/kxbdSuperMarquee.js"></script>

<script type="text/javascript">
	$(function() {
		$(".media-link li:first").css("cursor", "pointer")
		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		});
	});
	
	function offsetDiv(s) {
		height = $(s).height() + 30;
		width = $(s).width();
		$(s).css("margin-top", -height / 2);
		$(s).css("margin-left", -width / 2);
	}
</script>