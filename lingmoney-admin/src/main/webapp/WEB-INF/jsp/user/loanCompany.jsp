<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公司贷款表</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/user/loanCompany.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
	
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>借款人姓名：</td>
	            			<td><input class="easyui-textbox" name="nameCom" id="nameCom" /></td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/user/loancompany/list',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:100">id</th>
			            	<th data-options="field:'nameCom',width:100">借款人姓名</th>
			            	<th data-options="field:'phoneCom',width:100">联系电话</th>
			            	<th data-options="field:'moneyCom',width:100" >借款金额</th>
			            	<th data-options="field:'deadlineCom',width:100" >借款期限</th>
			            	<th data-options="field:'useCom',width:100">借款用途</th>
			            	<th data-options="field:'companyCom',width:100">公司名称</th>
			            	<th data-options="field:'jobCom',width:100">所属行业</th>
			            	<th data-options="field:'areaCom',width:100" >所在地区</th>
			            	<th data-options="field:'establishCom',width:100" formatter="formatTimer">成立时间</th>
			            	<th data-options="field:'registCom',width:100">注册资金</th>
			            	<th data-options="field:'bankCom',width:100" formatter="formatbank">银行贷款情况</th>
			            	<th data-options="field:'statusCom',width:100" formatter="formatStatus">申请状态</th>
			            	
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		
</body>
</html>