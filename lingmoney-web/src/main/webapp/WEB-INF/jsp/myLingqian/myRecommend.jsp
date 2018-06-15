<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/common/top.jsp"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript">
	$(document).ready(function() {
		$("#membersRec").addClass("hover");
		$("#nav_member04").addClass("nav-hover");
		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");

		/*顶部*/
		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		})
		/*顶部*/
	});
	
	function showhide(tag){
		if(tag==0){
			$('.mask ,.alertGift').show();
		}else if(tag==1){
			$('.mask ,.alertGift').hide();
		}
	}
</script>
<link rel="stylesheet" href="/resource/css/activity/recommend.css">
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<!-- 用户导航开始 -->
	<jsp:include page="/common/navmember.jsp"></jsp:include>
	<!-- 用户导航结束 -->

	<div class="mainbody_member">
		<div class="box clearfix">
			<!-- 我的推荐菜单开始 -->
			<jsp:include page="/common/myrecommend.jsp"></jsp:include>
			<!-- 我的推荐菜单结束 -->
			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">我要推荐</div>
					<div class="zonglan clearfix recommend">
						<h1>尊敬的
							<span class="color">${CURRENT_USER.nickName==null?CURRENT_USER.loginAccount:CURRENT_USER.nickName}</span>,您在领钱儿的推荐码为
							<span class="color">${CURRENT_USER.referralCode}</span>
						</h1>
						<p class="rec-p">
							1、每位用户拥有专属、唯一的推荐码；<br>
                            2、当您推荐亲朋好友到领钱儿进行理财时，记得让好友在注册领钱儿会员时扫码注册或填写您的推荐码；<br>
                            3、领钱儿将对您的推荐信息进行记录，并不定期推出推荐奖励活动。
						</p>
						<div class="rec-div clearfix">
							<div class="erweima">
								<p>推荐好友扫一扫！</p>
								<img src="${CURRENT_USER.codePath}" width="165" height="165" />
								<p></p>
							</div>
							<div class="me">
								<!-- JiaThis Button BEGIN -->
								<div class="jiathis_style"
									style="padding-bottom:15px; width: 100%">
									<h2 style="float: left">通过转发邀请：</h2>
									<span
										style="padding-left: 10px; display: inline-block; padding-top: 5px;">
										<a class="jiathis_button_qzone"></a> <a
										class="jiathis_button_tsina"></a> <a
										class="jiathis_button_tqq"></a> <a
										class="jiathis_button_weixin"></a>
									</span>
								</div>
								<script type="text/javascript">
									var jiathis_config = {
										url : "http://www.lingmoney.cn",
										title : "领钱儿 安全便捷的综合互联网金融服务平台",
										summary : "领钱儿 安全便捷的综合互联网金融服务平台---为您轻松解决理财问题", //定义要分享页面的摘要，摘要默认为Meta标签中Description部分的内容
									};
								</script>
								<script type="text/javascript"
									src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
								<!-- JiaThis Button END -->
								<p>可以先获取推荐码，再转发给更多您想推荐的朋友！</p>
								<img src="/resource/images/img_reczf.jpg" width="330"/>
								<p>
									我是[<span class="color">${CURRENT_USER.nickName==null?CURRENT_USER.loginAccount:CURRENT_USER.nickName}</span>]，推荐您到<span
										class="color"><a href="http://www.lingmoney.cn">领钱儿（www.lingmoney.cn）</a>来理财，优质的理财产品、100元即可起投！</span>注册时一定要记得填写我的推荐码[<span
										class="color">${CURRENT_USER.referralCode}</span>]喔！
								</p>
							</div>
						</div>

						<c:if test="${ not empty UsersPropertiesList }">
						<div class="title-rec">我推荐的用户</div>
						<table cellpadding="0" cellspacing="0" style="width:100%;margin-top: 20px;background: url('/resource/images/bg_table.gif') no-repeat left top;">
								<tr>
									<th style="line-height: 34px;height: 34px;">用户昵称</th>
									<th>注册时间</th>
									<th>会员级别</th>
								</tr>
						</table>
						<div id="myRecommerTab" style="height:420px;overflow:auto;">
							<table class="table02" cellpadding="0" cellspacing="0" style="width:100%;margin-top: 0px;background: #FFFFFF no-repeat left top;">
								<c:forEach var="item" items="${UsersPropertiesList}"
									varStatus="status">
									<tr>
										<td style="width:34%;">
											<c:choose>
												<c:when test="${not empty item.nickName && item.nickName != null}">
											       ${item.nickName}
											    </c:when>
												<c:otherwise>
													${item.loginAccount}
												</c:otherwise>
											</c:choose>
										</td>
										<td style="width:35%;">${item.regDate}</td>
										<td>${item.lev }</td>
									</tr>
								</c:forEach>


							</table>
						</div>
						</c:if>

						<c:if test="${ not empty UsersProperties}">
							<div class="title-rec">谁推荐我的</div>
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td height="35px">他的推荐码：${UsersProperties.referralCode}</td>
									<td>他的昵称：${UsersProperties.nickName==null?UsersProperties.loginAccount:UsersProperties.nickName}&nbsp;&nbsp;【等级：
									<span class="color"> 
									${UsersProperties.lev}
									</span>】
									</td>
								</tr>
							</table>
						</c:if>
					</div>
					<!-- 推荐好友活动统计 -->
					<input id="recommendNewAvailable" value="${recommendNewAvailable }"
						type="hidden"> <input id="prizeNum" value="${prizeNum }"
						type="hidden"> <input id="heartNum" value="${heartNum }"
						type="hidden"> <input id="newPrize" value="${newPrize }"
						type="hidden"> <input id="personNum" value="${personNum }"
						type="hidden">
					<c:if test="${recommendNewAvailable=='1' }">
						<div class="frdTongji ft1">
							<div class="ft_top">
								<img src="/resource/images/activity/laxinDate2.png">
							</div>
							<div class="xinbox clearfix">
								<div class="xin">
									<div class="xintop">
										<img alt="" src="/resource/images/activity/xinImg_1.png">
									</div>
									<div class="xinbottom"></div>
								</div>
								<div class="xin">
									<div class="xintop">
										<img alt="" src="/resource/images/activity/xinImg_1.png">
									</div>
									<div class="xinbottom"></div>
								</div>
								<div class="xin">
									<div class="xintop">
										<img alt="" src="/resource/images/activity/xinImg_1.png">
									</div>
									<div class="xinbottom"></div>
								</div>
								<div class="xin">
									<div class="xintop">
										<img alt="" src="/resource/images/activity/xinImg_1.png">
									</div>
									<div class="xinbottom"></div>
								</div>
								<div class="xin">
									<div class="xintop">
										<img alt="" src="/resource/images/activity/xinImg_1.png">
									</div>
									<div class="xinbottom"></div>
								</div>
								<div class="xin">
									<div class="xintop">
										<img alt="" src="/resource/images/activity/xinImg_1.png">
									</div>
									<div class="xinbottom"></div>
								</div>
								<div class="xin">
									<div class="xintop">
										<img alt="" src="/resource/images/activity/xinImg_1.png">
									</div>
									<div class="xinbottom"></div>
								</div>
								<div class="xin">
									<div class="xintop">
										<img alt="" src="/resource/images/activity/xinImg_1.png">
									</div>
									<div class="xinbottom"></div>
								</div>
								<div class="xin">
									<div class="xintop">
										<img alt="" src="/resource/images/activity/xinImg_1.png">
									</div>
									<div class="xinbottom"></div>
								</div>
								<div class="xin">
									<div class="xintop">
										<img alt="" src="/resource/images/activity/giftImg_1.png">
									</div>
									<div class="xinbottom">
										<span id="tenPersonPrize">邀请十人领大奖</span>
									</div>
								</div>
							</div>
						</div>

						<!-- 兑换大奖统计 -->
						<div class="frdTongji ft2">
							<div class="ft_top">
								<img src="/resource/images/activity/laxinDate3.png">
							</div>
							<div class="xinbox clearfix">
								<c:set var="exchangeListSize" value="${fn:length(exchangeList)}" />
								<c:if test="${exchangeList==null }">
									<c:forEach begin="1" end="5" varStatus="status">
										<div class="xin">
											<div class="xintop">
												<img alt="" src="/resource/images/activity/gift.png">
											</div>
										</div>
									</c:forEach>
								</c:if>
								<c:choose>
									<c:when test="${exchangeListSize<=5}">
										<c:if test="${exchangeList!=null }">
											<c:forEach items="${exchangeList }" var="list"
												varStatus="status">
												<div class="xin giftInfo" data-id="${list.id}"
													data-status="${list.status}"
													data-giftName="${list.giftName}">
													<div class="xintop">
														<img alt="" src="/resource/images/activity/gift_h.png">
													</div>
												</div>
											</c:forEach>
											<c:forEach begin="1" end="${5-exchangeListSize}"
												varStatus="status">
												<div class="xin">
													<div class="xintop">
														<img alt="" src="/resource/images/activity/gift.png">
													</div>
												</div>
											</c:forEach>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${exchangeList!=null }">
											<c:forEach items="${exchangeList }" var="list"
												varStatus="status">
												<div class="xin giftInfo" data-id="${list.id}"
													data-status="${list.status}"
													data-giftName="${list.giftName}">
													<div class="xintop">
														<img alt="" src="/resource/images/activity/gift_h.png">
													</div>
												</div>
											</c:forEach>
											<c:forEach begin="1" end="${10-exchangeListSize}"
												varStatus="status">
												<div class="xin">
													<div class="xintop">
														<img alt="" src="/resource/images/activity/gift.png">
													</div>
												</div>
											</c:forEach>
										</c:if>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="ft2_tip">
								<span class="active">点击宝箱查看礼品状态</span>
							</div>
						</div>

						<!-- 我的新好友 -->
						<div class="mynewfriend">
							<div class="money_people">
								<p class="clearfix">
									<label>首投总额</label> <span><c:if test="${sumInvest==null }">0</c:if><c:if test="${sumInvest!=null }">${sumInvest }</c:if>元</span>
								</p>
								<p class="clearfix">
									<label>好友人数</label> <span><c:if test="${personNum==null }">0</c:if><c:if test="${personNum!=null }">${personNum }</c:if>人</span>
								</p>
							</div>
							<div class="mnfTitle">
								<img alt="" src="/resource/images/activity/myfriendbg.png">
							</div>
							<div id="friendTable2" class="friendTable"
								style="height: 672px; overflow: auto;">
								<table>
									<tbody>
										<c:if test="${validList!=null }">
											<c:forEach items="${validList }" var="list"
												varStatus="status">
												<tr>
													<td>${status.index+1 }</td>
													<td>${list.nickName }</td>
													<td><fmt:formatNumber value="${list.buyMoney }"
															pattern="#,#00" />元</td>
													<td>${list.referralCode }</td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div class="alertGift" style="display:none;">	
		<div class="close clearfix">
			<a href="javascript:void(0);" onclick="showhide(1)"></a>
		</div>
		<div class="gobackHome">
			<a href="/index.html"></a>
		</div>
	</div>
	<div class="mask" style="display:none;">
	
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	<script type="text/javascript">
		$(function() {
			var recommendNewAvailable = $('#recommendNewAvailable').val();
			// 如果拉新活动第二季可用
			if (recommendNewAvailable == '1') {
				// 拉新好友人数
				var personNum = $("#personNum").val();
				// 当前心个数
				var heartNum = $("#heartNum").val();
				if (personNum > 0) {
					$("#friendTable2").css({
						"margin-top" : "30px",
						"height" : "400px",
						"overflow-y" : "scroll"
					});
					for (var i = 0; i < heartNum; i++) {
						$(".ft1 .xintop:eq(" + i + ") img").attr("src",
								"/resource/images/activity/xinImg_2.png");
					}
				} else {
					$("#friendTable2").css("height", "60px");
				}

				// 是否新获得大奖 -1是，弹框 0不是，不弹框
				var newPrize = $("#newPrize").val();
				if (newPrize == -1) {
					showhide(0);
				}
			}
			//点击礼品事件
			$(".giftInfo").click(function() {
				var id = $(this).attr('data-id');
				var giftName = $(this).attr('data-giftName');
				var status = $(this).attr('data-status');
				var content = '';
				var background = '/resource/images/activity/gift_get.png';
				if (status == 0) {//获得大奖没联系
					background = '/resource/images/activity/gift_get.png';
				} else if (status == 1) {//已发货
					content = giftName;
					background = '/resource/images/activity/gift_text_1.png';
				} else if (status == 2) {//已收货
					content = giftName;
					background = '/resource/images/activity/gift_text_2.png';
				}
				$('.mask ,.alertGift').show();
				$('.alertGift').css({
					'background' : 'url("' + background + '") no-repeat'
				});
				$('.alertGift .content').text(content);
			});
		});
	</script>
</body>
</html>