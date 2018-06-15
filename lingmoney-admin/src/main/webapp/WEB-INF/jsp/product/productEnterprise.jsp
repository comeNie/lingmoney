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
<style type="text/css">
<script
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
	<div id="tb" data-options="region:'center'" style="padding: 5px; height: auto">
		<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
			data-options="
				singleSelect:true,
				toolbar:toolbar,
				fitColumns:true,
				iconCls:'icon-view',
				loadMsg:'请稍后，正在加载数据...',
				collapsible:true,
				url:'/rest/product/product/productEnterprise',
				columns: [[  
	                {field:'enterpriseName',title:'公司名称',width:100},
					{field:'money',title:'募集金额',width:100}
				]]">
			
		</table>
	</div>
	
	<!-- 底部结束 -->
	<script type="text/javascript">
		var toolbar = [{
			text : '产品募集列表',
			iconCls : 'icon-view',
			handler : function() {
			}
		}]
	</script>
</body>
</html>

