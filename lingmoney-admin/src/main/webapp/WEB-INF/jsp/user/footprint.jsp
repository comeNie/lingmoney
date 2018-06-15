<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户足迹</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/customQuery/footprint.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true" >
		
		<div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding: 5px; height: 80px">
			<form>
				<table>
					<tr>
						<td>用户账号：</td>
						<td><input class="easyui-textbox" name="account" id="account" /></td>
						<td>用户手机号：</td>
						<td><input class="easyui-textbox" name="tel" id="tel" /></td>
						<td>日期：</td>
						<td><input class="easyui-datebox" name="date" id="date" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" style="padding: 5px;">
			<table class="easyui-datagrid" title="查询结果" id="footPrintDatagrid" 
				data-options="singleSelect:true,fit:true,fitColumns:true,pagination:true,pageSize:30,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',url:'/rest/user/viewFootprint',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'uid',width:40">用户id</th>
						<th data-options="field:'account',width:100">用户账号</th>
						<th data-options="field:'tel',width:100">手机号</th>
						<th data-options="field:'times',width:100">操作时间</th>
						<th data-options="field:'step',width:100">所到节点</th>
					</tr>
				</thead>
			</table>
		</div>
		
	</div>
	
</body>
</html>