<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>银行快捷支付充值限额设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/info/rechargeLimit.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>银行名称：</td>
						<td><input class="easyui-textbox" name="bankName"
							id="bankName" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/info/rechargeLimit/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'bankName',width:100">银行名称</th>
						<th data-options="field:'everytimeLimit',width:100">单笔限额（元）</th>
						<th data-options="field:'everydayLimit',width:100">单日限额（元）</th>
						<th data-options="field:'everymonthLimit',width:100">每月限额（元）</th>
						<th data-options="field:'status',width:100" formatter="formatStatus">状态</th>
						<th data-options="field:'bankLogo',width:500">银行logo</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加银行快捷支付充值限额',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" />
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">银行名称:</label></th>
					<td><input class="easyui-textbox" name="bankName"
						data-options="prompt:'请输入银行名称',required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">单笔限额（元）:</label></th>
					<td><input class="easyui-numberbox" name="everytimeLimit"
						data-options="min:0,prompt:'请填写整数',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">单日限额（元）:</label></th>
					<td><input class="easyui-numberbox" name="everydayLimit"
						data-options="min:0,prompt:'请填写整数',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">每月限额（元）:</label></th>
					<td><input class="easyui-numberbox" name="everymonthLimit"
						data-options="min:0,prompt:'请填写整数',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行logo:</label></th>
					<td><input class="easyui-filebox" name="bankImg"
						style="width: 200px" data-options="prompt:'请选择图片',required:true"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close');">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 添加的弹出层 -->
	<!-- 编辑的弹出层 -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 400px; height: 350px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改银行快捷支付充值限额',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">银行名称:</label></th>
					<td><input class="easyui-textbox" name="bankName"
						data-options="prompt:'请输入银行名称',required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">单笔限额（元）:</label></th>
					<td><input class="easyui-numberbox" name="everytimeLimit"
						data-options="min:0,prompt:'请填写整数',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">单日限额（元）:</label></th>
					<td><input class="easyui-numberbox" name="everydayLimit"
						data-options="min:0,prompt:'请填写整数',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">每月限额（元）:</label></th>
					<td><input class="easyui-numberbox" name="everymonthLimit"
						data-options="min:0,prompt:'请填写整数',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行logo:</label></th>
					<td><img id="bankLogo" width="70px" height="70" /><br />
					<input class="easyui-filebox" name="bankImg" style="width: 200px"
						data-options="prompt:'请选择图片'"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>