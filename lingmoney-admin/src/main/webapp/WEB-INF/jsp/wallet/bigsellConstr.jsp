<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>企业用户设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/wallet/bigsellConstr.js"></script>
</head>
<body> 
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>触发时间：</td>
						<td><input class="easyui-datebox" name="walletDate" id="walletDate" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/wallet/constraint/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'maxMoney',width:100">上限金额(元)</th>
						<th data-options="field:'minMoney',width:100">下限金额(元)</th>
						<th data-options="field:'walletDt',width:100"
							formatter="formatTimer">触发时间</th>
						<th data-options="field:'operDt',width:100"
							formatter="formatTimers">操作时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 400px; height: 210px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate">
			<table id="tblAdd" class="view">
				<tr>
					<th><label>上限金额(元)：</label></th>
					<td><input class="easyui-numberbox" name="maxMoney"
						id="amaxMoney" required style="width: 200px"></td>
				</tr>
				<tr>
					<th><label>下限金额(元)：</label></th>
					<td><input class="easyui-numberbox" name="minMoney" min="1000"
						id="aminMoney" required style="width: 200px"></td>
				</tr>
				<tr>
					<th><label>触发时间：</label></th>
					<td><input class="easyui-datebox" name="walletDt"
						id="awalletDt" required style="width: 200px"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 修改的弹出层 -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 400px; height: 210px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
			<input type="hidden" name="id" id="id">
			<table id="tblAdd" class="view">
				<tr>
					<th><label>上限金额(元)：</label></th>
					<td><input class="easyui-numberbox" name="maxMoney"
						id="emaxMoney" required style="width: 200px"></td>
				</tr>
				<tr>
					<th><label>下限金额(元)：</label></th>
					<td><input class="easyui-numberbox" name="minMoney" min="1000"
						id="eminMoney" required style="width: 200px"></td>
				</tr>
				<tr>
					<th><label>触发时间：</label></th>
					<td><input class="easyui-datebox" name="walletDt"
						id="ewalletDt" required style="width: 200px"></td>
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