<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
$(function() {
	//网页头部昵称鼠标悬浮事件
	$(".media-link li:first").css("cursor", "pointer")
	$(".media-link li:first").hover(function() {
		$(this).find("div").show();
	}, function() {
		$(this).find("div").hide();
	});
	//禁用双击事件
	document.body.ondblclick=function(){
		return false;
	}
});

//定位div居中显示
function offsetDiv(s) {
	height = $(s).height() + 30;
	width = $(s).width();
	$(s).css("margin-top", -height / 2);
	$(s).css("margin-left", -width / 2);
}
</script>

<style type="text/css">
.dropdown img{
    width: 21px;
    float: left;
    margin: 4px 10px;
}
</style>
<!-- 头部开始 -->
<div class="header">
	<div class="top-wrap">
		<div class="top-body clearfix">
			<ul class="leftul clearfix">
				<li 
					<c:choose>
						<c:when test="${CURRENT_USER==null }">class="welcome-nologin"</c:when>
						<c:otherwise>class="welcome"</c:otherwise>
					</c:choose>
					 style="margin-left: 0;">您好，欢迎
					 <span style="color: #e43c0f; font-weight: bold">${CURRENT_USER.nickName==null?CURRENT_USER.loginAccount:CURRENT_USER.nickName }</span>来到
					 <a href="/index" class="sep">领钱儿！</a>
				</li>
			</ul>
			<ul class="media-link clearfix">
				<c:choose>
					<c:when test="${CURRENT_USER==null }">
						<li><a href="/users/regist" id="zhuce">注册</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
							href="/users/login" id="denglu">登录</a></li>
					</c:when>
					<c:otherwise>
						<li style="position: relative; z-index: 1; padding-left: 32px;">
							<p class="head2">
								<c:choose>
									<c:when
										test="${CURRENT_USER.picture==null||CURRENT_USER.picture=='' }">
										<img src="/resource/images/bg_membersTouxiang.gif" width="24"
											height="24" />
									</c:when>
									<c:otherwise>
										<img id="srcManager" src="${CURRENT_USER.picture}" width="24"
											height="24" />
									</c:otherwise>
								</c:choose>
							</p>
							<p style="float: left" onclick="window.location='/myLingqian/show'">${CURRENT_USER.nickName==null?CURRENT_USER.loginAccount:CURRENT_USER.nickName }</p>
							<div>
								<a href="/myLingqian/show.html">我的领钱儿</a> 
								<a href="/myFinancial/finanCialManage">我的理财</a> 
								<a href="/myAccount/bindBankCard">我的账户</a> 
								<a href="/users/logout?domainCityCode=${AREADOMAIN.bdCityCode }" style="border: none">退出</a>
							</div>
						</li>
						<li style="padding-left: 20px; background: url('/resource/images/bg_memberNews.gif') no-repeat left center">
							<a href="/usersMessage/list.html" id="message">站内信(<span style="color: #ea5513">${CURRENT_USER.unRead }</span>)</a>
						</li>
					</c:otherwise>
				</c:choose>
				<li><a href="/phone.html" id="phone">手机客户端</a></li>
				<li><a href="/helpproblem.html" id="help">帮助中心</a></li>
				<li><a href="/contactUs.html" id="contact">联系我们</a></li>
			</ul>
		</div>
	</div>
	<div class="header-wrap">
		<div class="head-body clearfix">
			<div class="qingdaoLogo">
				<!-- <img src="/resource/images/logo-dong.gif" class="logo-dong"/> -->
				<a href="/index.html">
					<img src="/resource/images/logo.png" alt="领钱儿标志"/>
				</a>
				<!-- <img src="/resource/images/qdimages/beijing.png" style="margin-left:5px;margin-top:4px"/>
				  <span class="adress-box beijing">
					  <i class="adress adress-beijing"></i>
					  <i class="font">北京</i>
					  <i class="icon-drop"></i>
				  </span> -->
				   <!-- <ul class="dropdown">
					  <li><img src="/resource/images/qdimages/beijing.png" /><a href="/index.html?domainCityCode=131">北京站</a></li>
					  <li style="border:none"><img src="/resource/images/qdimages/qingdao.png"/><a href="/index?domainCityCode=236">青岛站</a></li>
					  <li style="border:none"><img src="/resource/images/hangzhouImages/hangzhou.png"/><a href="/index?domainCityCode=179">杭州站</a></li>
				   </ul> -->
			</div>
			<div>
				<ul class="nav clearfix">
					<li><a href="/index" id="nav01">首页</a></li>
					<li><a href="/product/list/0_0_0_0_0_1" id="nav02">我要理财</a></li>
					<li><a href="/lbmarket/index" id="nav03">领宝商城</a></li>
					<li><a href="/aboutus" id="nav04">关于我们</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<!-- 头部结束 -->
