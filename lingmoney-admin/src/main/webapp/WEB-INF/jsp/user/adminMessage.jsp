<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>发送消息</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/user/adminMessage.js"></script>
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/user/adminmessage/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100">id</th>
						<th data-options="field:'theme',width:100">消息主题</th>
						<th data-options="field:'sendname',width:100">发送人</th>
						<th data-options="field:'uId',width:100">接收人id</th>
						<th data-options="field:'sendtime',width:100"
							formatter="formatTimer">发送时间</th>
						<th data-options="field:'count',width:100">发送总人数</th>
					</tr>
				</thead>
			</table>
		</div>

		<!-- 弹出编辑消息 -->
		<div id="DivEdit" class="easyui-dialog"
			style="width: 800px; height: 530px; padding: 10px 20px" closed="true"
			resizable="true" modal="true"
			data-options="title:'编辑消息',iconCls: 'icon-add',buttons: '#dlg-buttons'">
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
						<td><input class="easyui-textbox" name="theme" id="atheme"
							style="width: 200px" readonly></input></td>
					</tr>
					<tr>
						<th><label for="name">发送人：</label></th>
						<td><input class="easyui-textbox" name="sendname"
							id="asendname" style="width: 200px" readonly></input></td>
					</tr>
					<tr>
						<th><label for="name">发送时间：</label></th>
						<td><input class="easyui-textbox" name="sendtime"
							id="asendtime" style="width: 200px" readonly></input></td>
					</tr>

					<tr>
						<th><label for="name">消息内容：</label></th>
						<td><textarea name="content" id="acontent" readonly
								style="width: 200px; height: 265px; visibility: hidden;"></textarea>
						</td>
					</tr>
					<!-- <tr>
						<td colspan="2" style="text-align: right; padding-top: 10px">
							<a href="javascript:void(0)" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a> <a
							href="javascript:void(0)" class="easyui-linkbutton"
							iconcls="icon-cancel"
							onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
						</td>
					</tr> -->
				</table>
			</form>
		</div>
	</div>
	<!-- 发送消息-->
	<div id="toSend" class="easyui-dialog"
		style="width: 860px; height: 500px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'发送消息',iconCls: 'icon-add',buttons: '#dlg-buttons'">

		<form id="sendForm" method="post" novalidate="novalidate">
			<table>
				<tr>
					<td>发送给：</td>
					<td><select class="easyui-combogrid" id="userSend"
						name="userSend" style="width: 154px"
						data-options="
							            panelWidth: 154,
							            panelHeight: 80,
							            idField: 'typeId',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'typeName',
							            url: '/resource/json/user_send.json',
							            method: 'get',
							            onChange:function(){
							            	change();
							            },
							            columns: [[
							                {field:'typeId',title:'id',width:54},
							                {field:'typeName',title:'名称',width:100}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
				</tr>
			</table>
			<table  id="the" style="display:block">
				<tr>
					<td>用户手机号：</td>
					<td><input class="easyui-textbox" id="telephone" width="30px" /></td>
					<td>最低理财总金额：</td>
					<td><input class="easyui-textbox" id="finaclMoney"
						width="30px" /></td>
					<td>最低账户余额：</td>
					<td><input class="easyui-textbox" id="balance" width="30px" /></td>
					<td><a href="javascript:search()" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-search">查询</a></td>
				</tr>
			</table>

			<table id="dg" class="easyui-datagrid" title="请选择用户"
				style="width: 806px; height: 200px"
				data-options="rownumbers:true,singleSelect:false,method:'post'">

				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'id',width:150,align:'center'">ID</th>
						<th data-options="field:'uId',width:150,align:'center'">用户ID</th>
						<th data-options="field:'userName',width:150,align:'center'">用户昵称</th>
						<th data-options="field:'totalFinance',width:150,align:'center'">理财总金额</th>
						<th data-options="field:'balance',width:150,align:'center'">账户余额</th>
					</tr>
				</thead>
			</table>

			<table class="view">
				<tr>
					<td>主题：</td>
					<td>
						<input type="hidden" name="uId" id="uId"> 
						<input type="hidden" name="sendtime" id="sendtime"> 
						<input type="hidden" name="count" id="count"> 
						<input type="hidden" name="sendname" id="sendname"> 
						<input class="easyui-textbox" id="theme" name="theme" />
					</td>
				</tr>
				<tr>
					<td>内容：</td>
					<td>
						<textarea name="content" id="econtent"
							style="width: 200px; height: 250px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
			<a href="javascript:sendMessage()" class="easyui-linkbutton"
				id="btnAddOK" iconcls="icon-search">发送</a>
		</form>

	</div>

</body>
</html>