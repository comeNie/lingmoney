<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>兑付回签管理</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" 
	src="/resource/js/sellincome/sellincomeList.js"></script>
<style type="text/css">
fieldset {
	margin-bottom: 10px;
	color: #333;
	border: #06c dashed 1px;
}

legend {
	color: #06c;
	font-weight: 800;
	background: #fff;
}
</style>

 
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false"
		style="height: auto; overflow: hidden; background-color: #fff">
		<form id="searchForm">
			<table>
				<tr>
					<th>兑付时间:</th>
					<td><input class="easyui-datetimebox" id="sellDate" name="sellDate" style="width:152px"/></td>
					
					<th>回签天数:</th>
					<td><input class="easyui-textbox" name="days" id="days" style="width: 152px"/></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton"
							data-options="iconCls:'icon-search',plain:true"
							onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton"
							data-options="iconCls:'icon-excel',plain:true"
							onclick="exportFun();">导出</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="tb" data-options="region:'center'" style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="
					singleSelect:true,
					toolbar:toolbar,
					fitColumns:true,
					pagination:true,
					iconCls:'icon-view',
					loadMsg:'请稍后，正在加载数据...',
					collapsible:true,
					url:'/rest/sellBatchIncome/findSellBatchIncomeList',
					columns: [[  
		                {field:'sellDate',title:'兑付时间',width:150},
						{field:'sellMoney',title:'兑付金额',width:100},
						{field:'username',title:'兑付人',width:100},
						{field:'phoneNumber',title:'兑付人电话',width:150},
						{field:'orderName',title:'理财经理',width:100},
						{field:'orderPhone',title:'理财经理电话',width:150},
						{field:'orderOrg',title:'所属部门',width:100},
						{field:'orderCompany',title:'所属分公司',width:100},
						{field:'oneMoney',title:'第一天回签金额',width:100},
						{field:'twoMoney',title:'第二天回签金额',width:100},
						{field:'threeMoney',title:'第三天回签金额',width:100},
						{field:'fourMoney',title:'第四天回签金额',width:100},
						{field:'fiveMoney',title:'第五天回签金额',width:100},
						{field:'incomeDate',title:'回签时间',width:100},
						{field:'changeMoney',title:'金额变化',width:100},
						{field:'incomeEfficiency',title:'回签率',width:100}
					]]">
				
			</table>
		</div>
	
</body>
</html>

