<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/common/kefu.jsp"></jsp:include>
<!-- 底部开始 -->
<div class="footer" id="footer">

	<c:if test="${AREADOMAIN.footImage!=null && AREADOMAIN.footImage!=''}">
		<div class="top-body">
			<img src="${AREADOMAIN.footImage }" />
		</div>
	</c:if>

	<div class="footertop clearfix">
		<div class="box clearfix">
			<div class="fleft clearfix">
				<ul>
					<li><a href="/aboutus.html">关于我们</a> <a href="/safe.html">安全保障</a>
						<a href="/infoNotice/list.html">资讯中心</a> <a
						href="/aboutusJoin.html">加入我们</a> <a href="/aboutusMap.html">联系我们</a></li>
					<li>
						<a href="/helpCenterGuide.html">新手指南</a> 
						<a href="/usersAsk/list.html">您问我答</a> 
						<a href="/memberShip">会员制度</a> 
						<a href="/helpCenterRule.html">平台规则</a>
					</li>

				</ul>
				<p style="padding-top: 20px; padding-bottom:12px;">北京市朝阳区广渠路3号竞园艺术中心21A</p>
				
			</div>

			<div class="fcenter clearfix">
				<div>
					<img src="/resource/images/footer_img1.jpg" alt="领钱微信">
					<p
						style="padding-left: 17px; background: url('/resource/images/footer_img11.png') no-repeat left center">官方微信</p>
				</div>
				<div>
					<img src="/resource/images/footer_img2.jpg" alt="领钱微博">
					<p
						style="padding-left: 17px; background: url('/resource/images/footer_img22.png') no-repeat left center">官方微博</p>
				</div>
			</div>
			<div class="fright">
				<p>工作日时间：09:00 - 18:00</p>
				<p style="padding-left: 0;">
					<img src="/resource/images/tel_num.png">
					<span>为了节省您的时间，请先<a href="https://www.ghbibank.com.cn/eAccountF/download/ocx/USBKey_Assistant.exe">安装华兴银行控件</a>,重启浏览器。</span>
				</p>
			</div>
		</div>
	</div>
	
	<div class="footerbottom">
	 <div style="padding-top:24px; padding-bottom:12px;text-align: center;">
		<a id="_pingansec_bottomimagelarge_p2p" href="http://si.trustutn.org/info?sn=652180504000644039556&certType=4"><img src="http://v.trustutn.org/images/cert/p2p_official_large.jpg" style="width:104px;"/></a>
		<a id='___szfw_logo___' href='https://credit.szfw.org/CX20180423080060011671.html' target='_blank'><img src='/resource/images/cert.png' border='0' /></a>
		 <script type='text/javascript'>(function(){document.getElementById('___szfw_logo___').oncontextmenu = function(){return false;}})();</script>
		<a target="_blank" href="https://v.pinpaibao.com.cn/cert/site/?site=www.lingmoney.cn&at=business" ><img src="https://static.anquan.org/static/outer/image/hy_124x47.png" style="width:104px;"></img></a>
	  <!--可信网站图片LOGO安装开始-->	
<script src="https://kxlogo.knet.cn/seallogo.dll?sn=e18042411010573484psue000000&size=0"></script>
<!--可信网站图片LOGO安装结束-->
	</div>
		<div class="box">
			<p>
				Copyright<span style="font-family: Arial">&nbsp;©&nbsp;</span>2015-2017领钱儿www.lingmoney.cn
				All Rights Reserved 版权所有 京ICP证150899号 京ICP备15012687号-2 <a
					href="/sitemap.html">站点地图</a>
			</p>
		</div>
	</div>
	
</div>

<!-- 底部结束 -->