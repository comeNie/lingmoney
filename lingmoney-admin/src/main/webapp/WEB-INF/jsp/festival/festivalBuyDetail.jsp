<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>活动产品认购管理</title>
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
	src="/resource/js/festival/festivalBuyDetail.js"></script>
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
					<th>认购时间:</th>
					<td>
						<input class="easyui-datetimebox" id="startDate" name="startDate" style="width:152px"/>
						-
						<input class="easyui-datetimebox" id="endDate" name="endDate" style="width:152px"/>
					</td>
					
					<th>产品名称：</th>
					<td><input class="easyui-textbox" name="pactName" style="width:152px"
						id="pactName"
						data-options="prompt:'请输入产品名称关键字',
						            icons: [{
										iconCls:'icon-remove',
										handler: function(e){
											$('#pactName').textbox('setValue', '');
										}
									}]" /></td>
									
					<th>活动时间:</th>
					<td>
						<input class="easyui-datetimebox" id="activityStartDate" name="activityStartDate" style="width:152px"/>
						-
						<input class="easyui-datetimebox" id="activityEndDate" name="activityEndDate" style="width:152px"/>
					</td>
	
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
					iconCls:'icon-view',
					loadMsg:'请稍后，正在加载数据...',
					collapsible:true,
					url:'/rest/festival/festivalBuy/festivalBuyDetailList',
					columns: [[  
		                {field:'u_name',title:'姓名',width:100},
						{field:'telephone',title:'手机号',width:100},
						{field:'o_name',title:'理财经理',width:100},
						{field:'department',title:'理财经理所属',width:100},
						{field:'p_name',title:'产品名称',width:100},
						{field:'f_time',title:'认购期限(天)',width:100},
						{field:'financial_money',title:'认购金额',width:100},
						{field:'buy_dt',title:'认购时间',width:100},
						{field:'moneyForThree',title:'三月期累计持有',width:100},
						{field:'moneyForSix',title:'六月期累计持有',width:100},
						{field:'moneyForTwelve',title:'十二月期累计持有',width:100}
					]]">
				
			</table>
		</div>
	
</body>
</html>

