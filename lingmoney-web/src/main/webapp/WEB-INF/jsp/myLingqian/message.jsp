<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>


<script type="text/javascript">
	$(document).ready(
		function() {
			$("#message").addClass("hover");
			$("#membersUserNews").addClass("hover");
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

			/*消息中心*/
			$(".button_news").toggle(
				function() {
					$(this).text('收起');
					$(this).css("background","url('/resource/images/bg_showHide.gif') no-repeat right bottom");
					$(this).parent().parent().next().show();
				},
				function() {
					$(this).text('查看');
					$(this).css("background","url('/resource/images/bg_showHide.gif') no-repeat right top");
					$(this).parent().parent().next().hide();
			});
			$("#closeImg, #agree, #cancel").click(function() {
				$("#rz-box-bg").hide();
				$("#bombBox").hide();
				$("#ensure").hide();
				$("#ensureCancel").hide();
			});
		});

		function docheckall() {
			if ($("#SelectAll").attr("checked")) {
				$(":checkbox").attr("checked", true);
			} else {
				$(":checkbox").attr("checked", false);
			}
		}

		function setSelectAll() {
			//当没有选中某个子复选框时，SelectAll取消选中  
			if (!$("#entityId").checked) {
				$("#SelectAll").attr("checked", false);
			}
			var chsub = $("input[type='checkbox'][id='entityId']").length; //获取subcheck的个数  
			var checkedsub = $("input[type='checkbox'][id='entityId']:checked").length; //获取选中的subcheck的个数  
			if (checkedsub == chsub) {
				$("#SelectAll").attr("checked", true);
			}
		}

		function dell() {
			var arrChk = $("input[name='entityId']:checked");
			var id_str = ""
			if (arrChk.length > 0) {
				$("#rz-box-bg").show();
				$("#bombBox").show();
				$("#ensureCancel").show();
				$("#ensure").hide();
				offsetDiv("#bombBox");
				$("#prompt").text("提示");
				$("#bombBoxContent").text("确认删除消息？");
			} else {
				$("#rz-box-bg").show();
				$("#bombBox").show();
				$("#ensure").show();
				$("#ensureCancel").hide();
				offsetDiv("#bombBox");
				$("#prompt").text("提示");
				$("#bombBoxContent").text("请选择至少一条数据!");
			}
		}

		function del() {
			var arrChk = $("input[name='entityId']:checked");
			var id_str = ""
			if (arrChk.length > 0) {
				$(arrChk).each(function() {
					id_str += this.value + "_"
				});
				window.location.href = "/usersMessage/delete.html?id=" + id_str;
			} else {
				$("#rz-box-bg").show();
				$("#bombBox").show();
				$("#ensure").show();
				$("#ensureCancel").hide();
				offsetDiv("#bombBox");
				$("#prompt").text("提示");
				$("#bombBoxContent").text("请选择至少一条数据!");
			}
		}
		
		function setRead() {
			var arrChk = $("input[name='entityId']:checked");
			var id_str = ""
			if (arrChk.length > 0) {
				$(arrChk).each(function() {
					id_str += this.value + "_"
				});
				window.location.href = "/usersMessage/setRead.html?id=" + id_str;
			} else {
				$("#rz-box-bg").show();
				$("#bombBox").show();
				$("#ensure").show();
				$("#ensureCancel").hide();
				offsetDiv("#bombBox");
				$("#prompt").text("提示");
				$("#bombBoxContent").text("请选择至少一条数据!");
			}
		}

		function setUnread() {
			var arrChk = $("input[name='entityId']:checked");
			var id_str = ""
			if (arrChk.length > 0) {
				$(arrChk).each(function() {
					id_str += this.value + "_"
				});
				window.location.href = "/usersMessage/setUnread.html?id=" + id_str;
			} else {
				$("#rz-box-bg").show();
				$("#bombBox").show();
				$("#ensure").show();
				$("#ensureCancel").hide();
				offsetDiv("#bombBox");
				$("#prompt").text("提示");
				$("#bombBoxContent").text("请选择至少一条数据!");
			}
		}
</script>



</head>
<body>
	<!-- 弹框背景 -->
	<div id="rz-box-bg"></div>

	<!-- 弹框 -->
	<div class="rz-box-con" style="width: 500px" id="bombBox">
		<div class="rz-title">
			<h2 id="prompt">提示</h2>
			<a href="javascript:void(0)" class="rz-close" id="closeImg"
				style="padding-top: 15px;">
				<img src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<div style="padding: 20px 0; margin: 0 auto; width: 400px; text-align: center; font-size: 1.5em;"
			id="bombBoxContent"></div>
		<p style="margin: 0 auto; width: 460px;" id="ensure">
			<a href="javascript:void(0);" class="rz-button ok"
				style="width: 122px; cursor: pointer" id="agree">确定</a>
		</p>
		<p style="padding-top: 10px;" id="ensureCancel">
			<a href="javascript:void(0)" class="rz-button" onclick="del()">确定</a><a
				href="javascript:void(0)" class="rz-button reset" id="cancel">取消</a>
		</p>
	</div>


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
					<div class="mtitle">消息中心</div>
					<div class="zonglan clearfix">
						<div class="quxian">
							<div class="newsbox">
								共<span class="color">${totalSize }</span>条消息，未读消息<span
									class="color">${unreadSize }</span>条
							</div>
							<div class="contents">
								<div id="dd1" style="display: block" class="divContent">
									<div class="biaozhu">
										<input type="checkbox" name="checkall" id="SelectAll" onclick="docheckall();" />
										<label for="SelectAll"><a href="javascript:void(0)" >全选</a>|</label>
										<a href="javascript:void(0)" onclick="setRead();">标为已读</a>|
										<a href="javascript:void(0)" onclick="setUnread();">标为未读</a>|
										<a href="javascript:void(0)" onclick="dell();">删除</a>
									</div>
									<div class="newsTitle">
										<span style="width: 25%; padding-left: 33px; text-align: left">消息主题</span><span>发送人</span><span>发送时间</span>
									</div>
									<!-- 循环 -->


									<c:forEach var="item" items="${gridPage.rows}"
										varStatus="status">

										<c:if test="${item.status==0}">

											<div class="show-box">
												<div class="show-box-1 clearfix unread">
													<p class="checkbox">
														<input type="checkbox" onclick="setSelectAll();"
															id="entityId" name="entityId" value=${item.id } />
													</p>
													<p class="textcon">
														<a href="/usersMessage/details.html?id=${item.id }">${item.topic}</a>
													</p>
													<p class="sender">${item.sender}</p>
													<p class="sendtime">
														<fmt:formatDate value="${item.time}"
															pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
													</p>
												</div>

											</div>

										</c:if>


										<c:if test="${item.status==1}">

											<div class="show-box">
												<div class="show-box-1 clearfix ">
													<p class="checkbox">
														<input type="checkbox" onclick="setSelectAll();"
															id="entityId" name="entityId" value=${item.id } />
													</p>
													<p class="textcon">
														<a href="/usersMessage/details.html?id=${item.id }">${item.topic}</a>
													</p>
													<p class="sender">${item.sender}</p>
													<p class="sendtime">
														<fmt:formatDate value="${item.time}"
															pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
													</p>
												</div>

											</div>

										</c:if>

									</c:forEach>

								</div>


								<w:pager pageSize="${pageSize}" pageNo="${pageNo}"
									url="/usersMessage/list.html?name=eeerr&&ccc=ddd"
									recordCount="${gridPage.total}" />

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