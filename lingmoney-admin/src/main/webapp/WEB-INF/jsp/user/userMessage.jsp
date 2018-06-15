<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户消息</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/user/userMessage.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>


</head>
<body>

	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/user/usermessage/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100">id</th>
						<th data-options="field:'uId',width:100">用户id</th>
						<th data-options="field:'topic',width:100">消息主题</th>
						<th data-options="field:'sender',width:100">发送人</th>
						<th data-options="field:'time',width:100" formatter="formatTimer">发送时间</th>
						<th data-options="field:'status',width:100"
							formatter="formatStatus">阅读状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>


	<!-- 用户信息表 -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 800px; height: 610px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'查看消息',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">

			<table id="adGrid" class="view">
				<tr>
					<th><label for="name">id：</label></th>
					<td><input class="easyui-textbox" name="id" id="aid"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">用户id：</label></th>
					<td><input class="easyui-textbox" name="uId" id="auId"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">消息主题：</label></th>
					<td><input class="easyui-textbox" name="topic" id="atopic"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">发送人：</label></th>
					<td><input class="easyui-textbox" name="sender" id="asender"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">发送时间：</label></th>
					<td><input class="easyui-datetimebox" name="time" id="atime"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">阅读状态：</label></th>
					<td><input class="easyui-textbox" name="status" id="astatus"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">消息内容：</label></th>
					<td><textarea name="content" id="acontent" readonly
							style="width: 300px; height: 300px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 用户信息表 -->


</body>
</html>