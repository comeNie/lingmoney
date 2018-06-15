<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>赎回失败流水</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/trading/redeemFailFlow.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<!-- <div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>状态：</td>
						<td><select class="easyui-combobox" name="status" id="status"
							style="width: 200px;">
								<option value="-1">全部</option>
								<option value="0">待处理</option>
								<option value="1">处理成功</option>
								<option value="2">处理失败</option>
						</select></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div> -->
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="rownumbers:true,singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',
		            loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/trading/redeemFailFlow/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'uId',width:150">用户id</th>
						<th data-options="field:'uName',width:150">姓名</th>
						<th data-options="field:'uTelephone',width:200">手机</th>
						<th data-options="field:'tId',width:150">交易id</th>
						<th data-options="field:'pCode',width:150">产品code</th>
						<th data-options="field:'dizNumber',width:200">订单号</th>
						<th data-options="field:'buyMoney',width:200">兑付金额</th>
						<th data-options="field:'redeemFailTime',width:200"
							formatter="formatTimer">失败时间</th>
						<th data-options="field:'failReason',width:200">失败原因</th>
						<th data-options="field:'bankName',width:200">银行名称</th>
						<th data-options="field:'bankCard',width:200">银行卡号</th>
						<th data-options="field:'bankShort',width:200">银行简称</th>
						<th data-options="field:'bankTel',width:200">预留手机号</th>
						<th data-options="field:'status',width:200"
							formatter="formatStatus">状态</th>
						<th data-options="field:'operateName',width:200">操作人员</th>
						<th data-options="field:'operateTime',width:200"
							formatter="formatTimer">操作时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 编辑的弹出层 -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 350px; height: 150px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'卖出产品',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
			<input type="hidden" name="id" /> <input type="hidden" name="sellDt"
				id="esellDt" />

			<table id="tblEdit" class="view">
				<tr>
					<th><label>卖出日期：</label></th>
					<td><input class="easyui-datetimebox" id="viewASellDt"
						required style="width: 200px"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="sell()">确定</a> <a
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