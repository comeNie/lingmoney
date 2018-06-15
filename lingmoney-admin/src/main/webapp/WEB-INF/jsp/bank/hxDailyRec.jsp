<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>华兴银行日终对账</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/formatter.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/bank/hxDailyRec.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<style type="text/css">
.address {
	Font-size: 16px;
	Font-family: 微软雅黑;
}
</style>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>开始日期：</td>
						<td>
							<input class="easyui-datebox" style="width: 120px" id="startDate" data-options="" />
						</td>
						<td>结束日期：</td>
						<td>
							<input class="easyui-datebox" style="width: 120px" id="endDate" data-options="" />
						</td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btn_search" iconcls="icon-search">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'" style="padding: 5px;width:100%; height: auto">
			<table class="easyui-datagrid" title="日终对账列表" fit='true' id="dailyGrid"
				data-options="toolbar:toolbar,rownumbers:false,singleSelect:true,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/bank/dailyReconciliate/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:200,hidden:false">id</th>
						<th data-options="field:'checkDate',width:100,formatter:formatter.DateTimeFormatter">对账时间</th>
						<th data-options="field:'fileName',width:100">文件名</th>
						<th data-options="field:'fileContent',width:100">文件内容</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>
