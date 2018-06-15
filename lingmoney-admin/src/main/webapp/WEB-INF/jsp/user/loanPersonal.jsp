<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>个人贷款表</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/user/loanPersonal.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
	
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>借款人姓名：</td>
	            			<td><input class="easyui-textbox" name="namePer" id="namePers" /></td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/user/loanpersonal/list',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:100">id</th>
			            	<th data-options="field:'namePer',width:100">借款人姓名</th>
			            	<th data-options="field:'phonePer',width:100">联系电话</th>
			            	<th data-options="field:'moneyPer',width:100" >借款金额</th>
			            	<th data-options="field:'deadlinePer',width:100" >借款期限</th>
			            	<th data-options="field:'jobPer',width:100">职业类型</th>
			            	<th data-options="field:'areaPer',width:100">所在地区</th>
			            	<th data-options="field:'housePer',width:100" formatter="formatHouse">房贷情况</th>
			            	<th data-options="field:'carPer',width:100" formatter="formatCar">车贷情况</th>
			            	<th data-options="field:'creditPer',width:100" formatter="formatCredit">信用卡使用情况</th>
			            	<th data-options="field:'statusPer',width:100" formatter="formatStatus">申请状态</th>
			            	
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		
</body>
</html>