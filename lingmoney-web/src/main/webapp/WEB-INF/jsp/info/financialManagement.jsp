<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>

<link rel="stylesheet" type="text/css"
	href="/resource/css/index_pubu.css">
<!--这个插件是瀑布流主插件函数必须-->
<script type="text/javascript" src="/resource/js/jquery.masonry.min.js"></script>
<!--这个插件只是为了扩展jquery的animate函数动态效果可有可无-->
<script type="text/javascript" src="/resource/js/jQeasing.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#financialmanagement").addClass("hover");
	})
</script>
<script type="text/javascript">
	$(function() {
		/*瀑布流开始*/
		var container = $('.waterfull ul');
		var loading = $('#imloading');
		// 初始化loading状态
		loading.data("on", true);
		/*判断瀑布流最大布局宽度，最大为1280*/
		function tores() {
			var tmpWid = $(window).width();
			if (tmpWid > 1000) {
				tmpWid = 716;
			} else {
				var column = Math.floor(tmpWid / 320);
				tmpWid = column * 320;
			}
			$('.waterfull').width(tmpWid);
		}
		tores();
		$(window).resize(function() {
			tores();
		});
		container.imagesLoaded(function() {
			container.masonry({
				columnWidth : 320,
				itemSelector : '.item',
				isFitWidth : true,//是否根据浏览器窗口大小自动适应默认false
				isAnimated : true,//是否采用jquery动画进行重拍版
				isRTL : false,//设置布局的排列方式，即：定位砖块时，是从左向右排列还是从右向左排列。默认值为false，即从左向右
				isResizable : true,//是否自动布局默认true
				animationOptions : {
					duration : 800,
					easing : 'easeInOutBack',//如果你引用了jQeasing这里就可以添加对应的动态动画效果，如果没引用删除这行，默认是匀速变化
					queue : false
				//是否队列，从一点填充瀑布流
				}
			});
		});
		/*模拟从后台获取到的数据*/
		
		var pageNo = 2;
		// 同步执行  
		var sqlJson = [];
		$.ajaxSettings.async = true;
		var noCache = Date();
		//alert("dssddd");
		$.getJSON('/financialStory/financialManagementJson.html?pageNo='+pageNo,{"noCache":noCache},
							function(json) {
								sqlJson = json.data;
							});
		/* var sqlJson = [ {
			'title' : '瀑布流其实就是几个函数的事！',
			'src' : '/resource/images/pubu1.jpg'
		}, {
			'title' : '瀑布流其实就是几个函数的事！',
			'src' : '/resource/images/pubu2.jpg'
		}, {
			'title' : '瀑布流其实就是几个函数的事！',
			'src' : '/resource/images/pubu3.jpg'
		}, {
			'title' : '瀑布流其实就是几个函数的事！',
			'src' : '/resource/images/pubu4.jpg'
		}, {
			'title' : '瀑布流其实就是几个函数的事！',
			'src' : '/resource/images/pubu5.jpg'
		},

		]; */
		
		/*本应该通过ajax从后台请求过来类似sqljson的数据然后，便利，进行填充，这里我们用sqlJson来模拟一下数据*/
		$(window).scroll(
			function() {
				if (!loading.data("on"))
					return;
				// 计算所有瀑布流块中距离顶部最大，进而在滚动条滚动时，来进行ajax请求，方法很多这里只列举最简单一种，最易理解一种
				var itemNum = $('#waterfull').find('.item').length;
				/* 
				var num =itemNum +4 
				alert(num);
				$.getJSON('/financialStory/financialManagementJson.html?num=4',{"noCache":noCache},
						function(json) {
					
							sqlJson = json.data;
						});
				
				//alert(itemNum) */
				var itemArr = [];
				itemArr[0] = $('#waterfull').find('.item').eq(
						itemNum - 1).offset().top
						+ $('#waterfull').find('.item').eq(
								itemNum - 1)[0].offsetHeight;
				itemArr[1] = $('#waterfull').find('.item').eq(
						itemNum - 2).offset().top
						+ $('#waterfull').find('.item').eq(
								itemNum - 1)[0].offsetHeight;
				itemArr[2] = $('#waterfull').find('.item').eq(
						itemNum - 3).offset().top
						+ $('#waterfull').find('.item').eq(
								itemNum - 1)[0].offsetHeight;
				var maxTop = Math.max.apply(null, itemArr);
				if (maxTop < $(window).height()
						+ $(document).scrollTop()) {
					//加载更多数据
					loading.data("on", false).fadeIn(800);
					pageNo++;
					$.getJSON('/financialStory/financialManagementJson.html?pageNo='+pageNo,{"noCache":noCache},
							function(json) {
								sqlJson = json.data;
							});
					(function(sqlJson) {
						/*这里会根据后台返回的数据来判断是否你进行分页或者数据加载完毕这里假设大于30就不在加载数据*/
						if (sqlJson==null) {
							loading.text('小编正在绞尽脑汁努力更新中...');
						} else {
							var html = "";
							for ( var i in sqlJson) {
								html += "<li class='item'><a href='/financialStory/financialManagementDetail.html?id="+sqlJson[i].id+"' class='a-img'><img src='"+sqlJson[i].indexPic+"'></a>";
								html += "<h2 class='li-title'>"
										+ sqlJson[i].title
										+ "</h2></li>";

							}
							/*模拟ajax请求数据时延时800毫秒*/
							var time = setTimeout(function() {
								$(html).find('img').each(
										function(index) {
											loadImage($(this).attr(
													'indexPic'));
										})
								var $newElems = $(html).css({
									opacity : 0
								}).appendTo(container);
								$newElems.imagesLoaded(function() {
									$newElems.animate({
										opacity : 1
									}, 800);
									container.masonry('appended',
											$newElems, true);
									loading.data("on", true)
											.fadeOut();
									clearTimeout(time);
								});
							}, 800)
						}
					})(sqlJson);
				}
						});
		function loadImage(url) {
			var img = new Image();
			//创建一个Image对象，实现图片的预下载
			img.indexPic = url;
			if (img.complete) {
				return img.indexPic;
			}
			img.onload = function() {
				return img.indexPic;
			};
		}
		;
		loadImage('/resource/images/one.jpeg');
	})
</script>
</head>
<body>

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<div class="mainbody_member">
		<div class="box clearfix">
			<!-- 理财小故事菜单开始 -->
			<jsp:include page="/common/financialstory.jsp"></jsp:include>
			<!-- 理财小故事菜单结束 -->

			<div class="mRight">
				<div class="mRight01">
					<div class="htitle">理财经</div>
					<div class="zonglan hcenter">
						
						<!-- 瀑布流样式开始 -->
						<div class="content">
							<div class="waterfull clearfloat" id="waterfull">
								<ul>
									
									<c:forEach var="item" items="${listFinancialManagement}"
										varStatus="status">
										<c:if test="${status.index<4}">
											<li class="item"><a href="/financialStory/financialManagementDetail.html?id=${item.id }" class="a-img"> <img
													src="${item.indexPic }" />
											</a>
												<h2 class="li-title" title="手把手教你用css3来创建loading动画(二)">${item.title }</h2>
											</li>
										</c:if>
									</c:forEach>
								</ul>
								<!-- loading按钮自己通过样式调整 -->
								<div id="imloading"
									style="width: 250px; height: 30px; line-height: 30px; font-size: 16px; text-align: center; border-radius: 3px; opacity: 0.7; background: #000; margin: 10px auto 30px; color: #fff; display: none">
									加载中.....</div>
							</div>

						</div>

					</div>

				</div>

			</div>
		</div>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>