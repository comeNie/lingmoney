<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
<title>公益</title>
<link rel="stylesheet" href="/resource/css/welfare.css">
<script src="/resource/js/jquery-1.8.3.min.js"></script>
<script src="/resource/js/tabbedContent.js"></script>
<script src="/resource/js/jquery.mobile-1.0a4.1.min.js"></script>
<script src="/resource/js/welfare.js"></script>

</head>
<body style="background: #f0f0f0">
    <input id="token" type="hidden" value="${token}">
	<div class="main">
		<section class="clear">
			<!-- 电子新闻&设计应用&牛人夜话&暴力拆解 begin -->
			<div class="tabbed_content">
				<div class="tabs">
					<div class="moving_bg">&nbsp;</div>
					<span class="tab_item">公益项目</span> 
					<span class="tab_item">已资助</span>
				</div>

				<div class="slide_content">
					<div class="tabslider">
						<div style="font-size: 0rem; line-height: 0rem;">&nbsp;</div>
						<ul id="commonwealProject">
							<!-- 公益项目 -->
							<div class="welfare-box">
<!-- 								<div class="welfare-box-list"> -->
<!-- 									<div class="welfare-box-list-img"><img alt="" src=""></div> -->
<!-- 									<div class="welfare-box-wb"> -->
<!-- 										<p class="welfare-box-wbl">彩虹桥·为半心宝贝加油</p> -->
<!-- 										<p class="welfare-box-wbr"><img src="/resource/images/welfare1.png" />1500/8500</p> -->
<!-- 									</div> -->
<!-- 								</div> -->

<!-- 								<div class="welfare-box-list"> -->
<!-- 									<div class="welfare-box-list-img"></div> -->
<!-- 									<div class="welfare-box-wb"> -->
<!-- 										<p class="welfare-box-wbl">彩虹桥·为半心宝贝加油</p> -->
<!-- 										<p class="welfare-box-wbr">已完成</p> -->
<!-- 									</div> -->
<!-- 								</div> -->

								<p class="welfare-box-tc"></p>
							</div>
							<!-- 公益项目 end-->
						</ul>
						
						<ul id="sponsorProject">
							<!-- 公益项目 -->
							<div class="welfare-box">
<!-- 								<div class="welfare-box-list"> -->
<!-- 									<div class="welfare-box-list-img"></div> -->
<!-- 									<div class="welfare-box-wb"> -->
<!-- 										<p class="welfare-box-wbl">彩虹桥·为半心宝贝加油</p> -->
<!-- 										<p class="welfare-box-wbr"> -->
<!-- 											<img src="/resource/images/welfare01.png" />1500/8500 -->
<!-- 										</p> -->
<!-- 									</div> -->
<!-- 								</div> -->

<!-- 								<div class="welfare-box-list"> -->
<!-- 									<div class="welfare-box-list-img"></div> -->
<!-- 									<div class="welfare-box-wb"> -->
<!-- 										<p class="welfare-box-wbl">彩虹桥·为半心宝贝加油</p> -->
<!-- 										<p class="welfare-box-wbr">已完成</p> -->
<!-- 									</div> -->
<!-- 								</div> -->

							</div>
							<!-- 公益项目 end-->
						</ul>
					</div>
					<br style="clear: both" />
				</div>
			</div>
		</section>
	</div>
	<!-- <a href=""></a> -->
	<div class="welfare-yuan">
<!-- 		<span>3</span><br>我的爱心 -->
	</div>
	<script>
		(function(doc, win) {
			var docEl = doc.documentElement, resizeEvt = 'orientationchange' in window ? 'orientationchange'
					: 'resize', recalc = function() {
				var clientWidth = docEl.clientWidth;
				if (!clientWidth)
					return;
				if (clientWidth >= 750) {
					docEl.style.fontSize = '100px';
				} else {
					docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
				}
			};
			if (!doc.addEventListener)
				return;
			win.addEventListener(resizeEvt, recalc, false);
			doc.addEventListener('DOMContentLoaded', recalc, false);
		})(document, window);

		$(function() {
			queryCommonwealProject();
		    
		    querySponsorProject();

		    listPageQueryUsersLoveNum();
			
			//浮动弹窗显示隐藏
			function headAdd() {
				$('.welfare-box-tc').show(100, function() {
					$('.welfare-box-tc').html('每天登录送爱心值哦～');
					$('.welfare-box-tc').animate({
						opacity : 1,
						top : '0rem'
					}, 1000, function() {
						window.setTimeout(function() {
							$('.welfare-box-tc').animate({
								opacity : 0,

							}, 1000, function() {
								$('.welfare-box-tc').hide();
							});
						}, 3000)
					});
				});
			}
			headAdd();
		})
	</script>
</body>
</html>