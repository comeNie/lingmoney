<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<title>领钱儿 分享生活中的理财小故事</title>
<meta name="KEYWords" content="理财故事，理财漫画，理财安全">
<meta name="DEscription" content="领钱儿(www.lingmoney.cn)理财小漫画，理财小故事。">
<meta name="Author" content="领钱儿">
<link rel="stylesheet" href="/resource/css/index.css">
<link rel="stylesheet" type="text/css" href="/resource/css/style.css"
	media="screen" />
<link rel="icon" type="image/x-icon" href="/resource/ico.ico">
<link rel="shortcut icon" type="image/x-icon" href="/resource/ico.ico">
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/resource/js/ajax.js"></script>
<script type="text/javascript" src="/resource/js/kxbdSuperMarquee.js"></script>
<jsp:include page="/common/kefu.jsp"></jsp:include>
<script type="text/javascript" src="/resource/js/index.js"></script>
<script type="text/javascript" src="/resource/js/yxMobileSlider.js"></script>
<script type="text/javascript" src="/resource/js/calculator.js"></script>
<script type="text/javascript" src="/resource/js/superslide.2.1.js"></script>
<script type="text/javascript">
	$(function() {
		$(".media-link li:first").css("cursor", "pointer")
		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		});
		document.getElementById("demo2").innerHTML = document.getElementById("demo1").innerHTML;
	});
	
	function offsetDiv(s) {
		height = $(s).height() + 30;
		width = $(s).width();
		$(s).css("margin-top", -height / 2);
		$(s).css("margin-left", -width / 2);
	}
	
	var dir = 1; //每步移动像素，数值越大则滚动得越快
	var speed = 40; //循环周期（毫秒）,数值越大滚动越快.
	function Marquee() {//移动
		//alert(demo2.offsetWidth+"\n"+demo.scrollLeft)
		if (dir > 0 && (demo2.offsetWidth - demo.scrollLeft) <= 0)
			demo.scrollLeft = 0
		if (dir < 0 && (demo.scrollLeft <= 0))
			demo.scrollLeft = demo2.offsetWidth
		demo.scrollLeft += dir

		demo.onmouseover = function() {
			clearInterval(MyMar)
		}//暂停移动
		demo.onmouseout = function() {
			MyMar = setInterval(Marquee, speed)
		}//继续移动
	}
	function r_left() {
		if (dir = -3)
			dir = 3
	}//向左滚动
	function r_right() {
		if (dir = 3)
			dir = -3
	}//向右滚动
	var MyMar = setInterval(Marquee, speed)
</script>
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<div
		style="width: 100%; min-width:1024px;overflow: hidden; height: 353px; border-top: 1px solid #ddd; background: url('/resource/images/banner_story.jpg') no-repeat center top #fff1e8"></div>
	<div class="mainbody mainbody_safe" style="min-height: 300px;">
		<div class="box">
			<div class="new_story">
				<span class="storySpan01">消息</span> <span
					style="padding-left: 20px; color: #525252">各色逗趣漫画人物，即将与大家见面，敬请期待！</span>
				<span> </span>
			</div>

			<div class="div_series">
				<div class="story_title">
					<h2>漫画贴</h2>
				</div>
				<div class="story_con">
					<c:if test="${listCartoonCategorySize>0 }">
						<a href="###" class="left_01" onClick="r_left()" title="点击向左滚动">&lt;&lt;</a>
						<div id="demo">
							<div id="indemo">
								<div id="demo1">
									<c:forEach var="item" items="${listCartoonCategory }">
										<a href="/financialStory/cartoonCategory.html?id=${item.id }">
											<img src="${item.indexPic }" width="148px" height="142px" />
										</a>
									</c:forEach>
								</div>
								<div id="demo2"></div>
							</div>
						</div>
						<a href="###" class="right_01" onClick="r_right()" title="点击向右滚动">&gt;&gt;</a>
					</c:if>
					<c:if test="${listCartoonCategorySize<=0 }">
						<p style="text-align: center; line-height: 50px; height: 50px;">暂无此内容</p>
					</c:if>
				</div>
			</div>
			<div class="story_bottom clearfix">
				<div class="story_left">
					<div class="story_title">
						<h2>理财经</h2>
					</div>
					<div class="story_left_con clearfix">
						<c:if test="${listFinancialManagementSize>0 }">
							<c:forEach var="item" items="${listFinancialManagement }" varStatus="status">
								<c:if test="${status.index<9 }">
									<a href="/financialStory/financialManagementDetail.html?id=${item.id }">
										<img src="${item.indexPic }" width="177" height="234" />
										<p>${item.title }</p>
									</a>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${listFinancialManagementSize<=0 }">
							<p style="text-align: center; line-height: 50px; height: 50px;">暂无此内容</p>
						</c:if>
					</div>
				</div>
				<div class="story_right">
					<div class="story_rightBox">
						<img src="/resource/images/img_storyRight_title.jpg" />
						<c:if test="${listFinancialManagementSize>0 }">
							<ul>
								<c:forEach var="item" items="${listFinancialManagement }"
									varStatus="status">
									<c:if test="${status.index < 10 }">
										<li>
											<a href="/financialStory/financialManagementDetail.html?id=${item.id }">${status.index+1 }.&nbsp;${item.title }</a>
											<span>
												<fmt:formatDate value="${item.pubDate }" pattern="yyyy/MM/dd" />
											</span>
										</li>
									</c:if>
								</c:forEach>
							</ul>
						</c:if>
						<c:if test="${listFinancialManagementSize<=0 }">
							<p style="text-align: center; line-height: 50px; height: 50px;">暂无此内容</p>
						</c:if>
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