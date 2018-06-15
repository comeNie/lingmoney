<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>


<script type="text/javascript">
	$(document).ready(function() {
		$("#membersUserActiveli").find("dl").show();
		$("#lingbao").addClass("hover");
		$("#membersUserActive").addClass("hover");
		$("#nav_member03").addClass("nav-hover");
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

		$(".a_ling").click(function() {
			$(".ling_more").toggle();
		});
		
		$(".rz-button,.rz-close").click(function(){
			$("#alert").hide();
			$("#rz-box-bg").hide();
		});

	})
</script>
<script type="text/javascript">
	function exchange() {
		var code = document.getElementById("code").value;
		if (code == '') {
			selfAlert("请输入兑换码");
			return false;
		} else {
			var con = sendReq("/myAccount/checkGift.html", "code=" + code);
			selfAlert(con);
			if (con == "该兑换码可用") {
				window.location.href = "/myAccount/exchangeGift.html?code="
						+ code;
				return true;
			} else {
				return false;
			}
		}

	}
	
	/**
	 *自定义弹出框
	 *title 标题
	 *message 内容 
	 *type 不同位置的alert确认事件
	 */
	function selfAlert(message,title){
		if(title){
			$("#alert h2").html(title);
		}
		$("#alert div:eq(1)").html(message);
		$("#alert").show();
		$("#rz-box-bg").show();
		offsetDiv("#alert");
	}
</script>

</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<!-- 用户导航开始 -->
	<jsp:include page="/common/navmember.jsp"></jsp:include>
	<!-- 用户导航结束 -->
	
	<!-- 自定义ALERT弹框 -->
	<div id="rz-box-bg"></div>
	<div class="rz-box-con" id="alert" style="width:500px;height:200px">
	    <div class="rz-title">
	       <h2>提示</h2>
	       <a class="rz-close" href="javascript:;"><img src="/resource/images/img_rz-close.png"/></a>
	    </div>
	    <div style="padding:20px 0;margin:0 auto;width:400px;text-align:center;font-size:1.5em;"></div>
	    <p style="margin: 40px auto; width: 460px;">
	       <a href="javascript:void(0);" class="rz-button ok" style="width:122px;cursor:pointer">确定</a>
	    </p>
	</div>

	<div class="mainbody_member">
		<div class="box clearfix">
			<!-- 我的账户菜单开始 -->
			<jsp:include page="/common/myaccount.jsp"></jsp:include>
			<!-- 我的账户菜单结束 -->

			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">我的领宝</div>
					<div class="zonglan clearfix">
						<div class="quxian">
							<div id="dd2" class="divContent">
								<div class="changeCode clearfix">
									<p>活动兑换码：</p>
									<p>
										<input type="text" id="code" name="code" required
											placeholder="请输入获得的兑换码" onfocus="this.style.color='#434343'" />
									</p>
									<p>
										<a href="javascript:void(0)" onclick="exchange()">领取</a>
									</p>
								</div>
								<table class="action_list" cellpadding="0" cellspacing="0">
									<tr>
										<th width="25%">活动名称</th>
										<th width="30%">奖励内容</th>
										<th width="15%" align="right">数量(个)</th>
										<th width="30%">使用日期</th>
									</tr>

									<c:forEach var="item" items="${gridPage.rows}">
										<tr>
											<td height="48"><p class="p_unused">${item.name }</p></td>
											<td><p class="p_unused">${item.content }</p></td>
											<td style="padding-right:10px;text-align: right;"><p class="p_unused">${item.amount }</p></td>
											<td><p class="p_unused">
													<fmt:formatDate value="${item.useDate }"
														pattern="yyyy-MM-dd" />
												</p></td>
										</tr>
									</c:forEach>

								</table>
								<div class="pages">
									<w:pager pageSize="${pageSize}" pageNo="${pageNo}"
										fromName="fromName" url="" recordCount="${gridPage.total}" />
								</div>

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