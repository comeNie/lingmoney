<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>稳赢宝提前兑付</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/trading/aheadOfWenYingCash.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto;">
			<div style="height:50px;">
			姓名：	<input id="username" name="username" class="easyui-textbox" data-options="prompt:'请输入用户名'"/>
			手机号：	<input id="tel" name="tel" class="easyui-textbox" data-options="prompt:'请输入手机号'"/>
			产品名称：	<input id="productName" name="productName" class="easyui-textbox" data-options="prompt:'请输入产品名称'"/>
			购买日期：	<input id="buyDt" name="buyDt" class="easyui-datebox" data-options="prompt:'请选择购买日期',editable:false,icons: [{
				iconCls:'icon-remove',
				handler: function(e){
					$('#buyDt').datebox('setValue', '');
				}
			}]"/>
			<a href="javascript:void(0);" onclick="subQuery()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			<a href="javascript:void(0);" onclick="refresh()" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
			<a href="javascript:void(0);" onclick="duifu()" class="easyui-linkbutton">兑付</a>
			</div>
			<table class="easyui-datagrid" title="查询结果" id="adGrid" height="95%"
				data-options="rownumbers:true,singleSelect:true,fitColumns:false,pagination:true,pageSize:20,iconCls:'icon-view',
		            loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/trade/trading/wenYingList',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'uname',width:60">姓名</th>
						<th data-options="field:'loginAccount',width:100">账号</th>
						<th data-options="field:'tel',width:90">手机号</th>
						<th data-options="field:'idCard',width:140">身份证号</th>
						<th data-options="field:'pname',width:240">产品名称</th>
						<th data-options="field:'financialMoney',width:80">理财金额</th>
						<th data-options="field:'fYieldView',width:90">固定收益率</th>
						<th data-options="field:'tfiInterest',width:60">利息</th>
						<th data-options="field:'buyDt',width:150">购买日期</th>
						<th data-options="field:'valueDt',width:75">计息日期</th>
						<th data-options="field:'minSellDt',width:80">可赎回的日期</th>
						<th data-options="field:'bizCode',width:220">订单号</th>
						<th data-options="field:'stDt',width:150">发行时间</th>
						<th data-options="field:'edDt',width:150">结束时间</th>
						<th data-options="field:'fTime',width:60">产品期限</th>
						<th data-options="field:'pCode',width:120">产品代码</th>
						<th data-options="field:'email',width:240">邮箱</th>
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