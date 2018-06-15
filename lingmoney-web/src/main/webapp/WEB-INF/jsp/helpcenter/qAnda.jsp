<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>

<link rel="stylesheet" href="/resource/themes/default/default.css" />
<script charset="utf-8" src="/resource/js/kindeditor.js"></script>
<script charset="utf-8" src="/resource/lang/zh_CN.js"></script>
<script type="text/javascript" src="/resource/js/IE89placeholder.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#helpCenterAsk").addClass("hover");
		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");

		/*顶部*/
		$(".leftul .weixinLi").hover(function() {
			$(".popweixin").show();
		}, function() {
			$(".popweixin").hide();
		})
		$(".leftul .qqLi").hover(function() {
			$(".popQQ").show();
		}, function() {
			$(".popQQ").hide();
		})

		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		})
		/*顶部*/

		$(".a-ask").click(function() {
			if($("#session").val()==""){
				$("#rz-box-bg").show();
				$("#deep").show();
				offsetDiv("#deep");
			}else{
				$("#rz-box-bg").show();
				$("#rz-box-mima").show();
				offsetDiv("#rz-box-mima");
			}
		})
		
		$(".rz-close,.reset").click(function() {
			$("#rz-box-bg").hide();
			$("#deep").hide();
			$("#rz-box-mima").hide();
		})
		
		var status = '<%=request.getAttribute("status")%>';
		var hot = '<%=request.getAttribute("hot")%>';
		if (status == '0') {
			$("#quanb0").addClass("selected");
			$("#quanb").removeClass("selected");
			$("#remen").removeClass("selected");
		} else if (status == '4' && hot != '1' || status == 'null') {
			$("#quanb").addClass("selected");
			$("#quanb0").removeClass("selected");
			$("#remen").removeClass("selected");
		}

		if (hot == '1') {
			$("#quanb").removeClass("selected");
			$("#quanb0").removeClass("selected");
			$("#remen").addClass("selected");
		}
	});
</script>

<script language="javascript" type="text/javascript">
	function changeTab4(index) {
		for (var i = 1; i <= 3; i++) {
			document.getElementById("fun_" + i).className = "normal";
			document.getElementById("fun_" + index).className = "selected";
			document.getElementById("dd" + i).style.display = "none";
		}
		document.getElementById("dd" + index).style.display = "block";
	}
</script>
<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			resizeType : 1,
			allowPreviewEmoticons : false,
			allowImageUpload : false,
			items : [ 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
					'bold', 'italic', 'underline', 'removeformat', '|',
					'justifyleft', 'justifycenter', 'justifyright',
					'insertorderedlist', 'insertunorderedlist', '|',
					'emoticons', 'image', 'link' ]
		});
	});
</script>
<script type="text/javascript">
	function copeStatus(val) {
		if (val != null) {
			$("#status").val(val);
			$("#hot").val(4);
		}
		submit();
	}
	function copeHot() {
		$("#hot").val(1);
		$("#status").val(4);
		submit();
	}
	function submit() {
		$("#queryForm").submit();
	}
</script>
</head>
<body>
	<input type="hidden" id="session" value="${CURRENT_USER }" />
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<div class="mainbody">
		<div class="box clearfix">
			<div class="sitemap">
				<a href="/index.html">领钱儿</a>&nbsp;>&nbsp;<span>帮助中心</span>
			</div>
			<!-- 帮助中心开始 -->
			<jsp:include page="/common/helpcenter.jsp"></jsp:include>
			<!-- 帮助中心结束 -->
			<div class="mRight">
				<div class="mRight01">
					<div class="htitle">您问我答</div>
					<div class="form-ask clearfix">
						<div class="form-ask-search">
							<form class="clearfix" method="post" action="/usersAsk/list.html" style="background:#fff">
								<input type="text" id="title" name="title" value="${title }"
									onfocus="if(this.value=='请输入搜索关键词'){this.style.color='#434343';this.value=''}"
									onblur="if(this.value==''){this.value='请输入搜索关键词';this.style.color='#aaa'}" />
								<button type="submit" id="bot">搜索</button>
							</form>
						</div>

						<div class="form-ask-go clearfix">
							<div class="a-ask">
								<a href="javascript:void(0)">我要提问</a>
							</div>
							<div class="player">
								<p>
									<span class="color">${totalSize }</span>个问题
								</p>
							</div>
						</div>

						<div id="rz-box-bg"></div>
						<div id="rz-box-mima" class="rz-box-con ask-form-div">
							<div class="rz-title">
								<h2>我要提问</h2>
								<a href="javascript:void(0)" class="rz-close">
									<img src="/resource/images/img_rz-close.png" />
								</a>
							</div>
							<form action="/usersAsk/newAsk.html" method="post" id="form01"
								style="width: 80%" onsubmit="return contentCheck()">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="text" id="newTitle" name="newTitle" required="required" placeholder='请输入提问的主题...' maxlength="30" minlength="1"
												onfocus="this.style.color='#434343'" style="width: 97%" />
										</td>
									</tr>
									<tr>
										<td>
											<textarea name="newContent" id="newContent" placeholder="请输入内容" maxlength="300" minlength="1"
												required="required"></textarea>
										</td>
									</tr>
									<tr>
										<td>
											<input type="submit" style="cursor: pointer;" value="发表提问" class="rz-button" />
										</td>
									</tr>
								</table>
							</form>
						</div>

						<div class="rz-box-con " id="deep">
							<div class="rz-title">
								<h2>未登录</h2>
								<a href="javascript:void(0)" class="rz-close">
									<img src="/resource/images/img_rz-close.png" />
								</a>
							</div>
							<p class="color" style="padding-top: 30px; font-size: 16px; color: #c54107">您还没登录，请先登录！</p>
							<p style="padding-top: 20px;">
								<a href="/login.html" class="rz-button">去登录</a>
								<a href="javascript:void(0)" class="rz-button reset">取消</a>
							</p>
						</div>
					</div>
					<form id="queryForm" action="/usersAsk/list.html" method="post">
						<input type="hidden" name="status" id="status" value="${status }" />
						<input type="hidden" name="hot" id="hot" value="${hot }" />
					</form>
					<div class="zonglan askcenter">
						<div class="quxian" style="min-height:500px">
							<div id="divMainTab4">
								<ul style="list-style: none; margin: 0px; padding: 0px; border-collapse: collapse;" class="clearfix">
									<li>
										<a href="javascript:void(0)" id="quanb" onclick="copeStatus(4)">所有问题</a>
									</li>
									<li>
										<a href="javascript:void(0)" id="quanb0" onclick="copeStatus(0)">待回答问题</a>
									</li>
									<li style="border: none">
										<a href="javascript:void(0)" id="remen" onclick="copeHot()">热门问题</a>
									</li>
								</ul>
							</div>

							<div class="contents">
								<div class="divContent">
									<c:forEach var="item" items="${gridPage.rows}"
										varStatus="status">
										<div class="ask-questions">
											<div class="title-questions clearfix">
												<strong>
													<a href="/usersAsk/details.html?id=${item.id }">${item.title }</a>
												</strong>
												<p>
													<span class="asker">提问者</span>
													<span class="color">${item.nickName }</span>[${item.degreeName }]
												</p>
											</div>
											<p class="content-question" style="width: 100%; overflow: hidden; white-space: nowrap; text-overflow: ellipsis">
												<span title="${fn:substring(item.content, 0, 50)}">${fn:substring(item.content, 0, 50)}${fn:length(item.content)>50?'...':''}</span>
											</p>
											<c:if test="${item.keyWord!=null&&item.keyWord!='' }">
												<div class="bottom-qestions">
													<c:forEach items="${fn:split(item.keyWord, ',')}" var="key">
														<span>${key }</span>
													</c:forEach>
												</div>
											</c:if>
										</div>
									</c:forEach>
								</div>
								<w:pager pageSize="${pageSize}" pageNo="${pageNo}" url=""
									recordCount="${gridPage.total}" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div style="clear: both"></div>
		</div>
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	<script type="text/javascript">
	function contentCheck(){
		var newTitle=$("#newTitle").val();
		var newContent=$("#newContent").val();
		if(newTitle.indexOf('<')>=0||newTitle.indexOf('>')>=0||newContent.indexOf('<')>=0||newContent.indexOf('>')>=0){
			return false;
		}
	}
	</script>
</body>
</html>