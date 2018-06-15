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
		$("#mygift").addClass("hover");
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
	});
</script>

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
			<!-- 我的账户菜单开始 -->
			<jsp:include page="/common/myaccount.jsp"></jsp:include>
			<!-- 我的账户菜单结束 -->
			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">我的礼品</div>
					<div class="zonglan clearfix">
						<div class="quxian">
							<div id="dd2" class="divContent">
								<table class="action_list" cellpadding="0" cellspacing="0">
									<tr>
										<th>礼品名称</th>
										<th>礼品数量</th>
										<th>使用日期</th>
										<th>产品名称</th>
										<th>状态</th>
									</tr>

									<c:forEach var="item" items="${gridPage.rows}">
										<tr>
											<td height="48"><p class="p_unused">${item.gName}</p></td>
											<td><p class="p_unused">${item.share}</p></td>

											<td><p class="p_unused">
													<fmt:formatDate value="${item.createTime }"
														pattern="yyyy-MM-dd HH:mm:ss" />
												</p></td>
											<td><p class="p_unused">${item.pName }</p></td>
											<td>
											
											<c:if test="${item.state==0}">
												<p class="p_unused">未生效</p>
											</c:if>
											
											<c:if test="${item.state==1}">
												<p class="p_unused" style="color:red">礼品冻结</p>
											</c:if>
											
											<c:if test="${item.state==2}">
												<p class="p_unused" style="color:green">已发货</p>
											</c:if>
											
											<c:if test="${item.state==3}">
												<p class="p_unused" style="color:orange">未发货</p>
											</c:if>
											
											<c:if test="${item.state==4}">
												<p class="p_unused" style="color:gray">已收货</p>
											</c:if>
											</td>
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