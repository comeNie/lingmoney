<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>活动表</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/sell/autoReturn.js"></script>
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
<body>

			<!-- <input type="button" name="close" value="关闭" onclick="window.close();" /> -->
		
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/sell/sellAutoReturn/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'name',width:50">名称</th>
						<th data-options="field:'code',width:100">标的</th>
						<th data-options="field:'platformUserNo',width:50">借款账户</th>
						<th data-options="field:'status',width:100,
			            					formatter:function(value){
										        if(value==1)
										                return '<font color=green>已开通</font>';
										        else if(value==0)
										                return '未开通';
										       else return '未开通';
										        }">状态</th>
						<th data-options="field:'bought',width:100,
			            					formatter:function(value){
										        if(value>0)
										                return '<font color=green>是</font>';
										       else return '否';
										        }">是否可开通</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>