<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>手机端产品买卖情况</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width:700px;" fit="true">
	        
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="rownumbers:true,singleSelect:true,fitColumns:true,pagination:false,iconCls:'icon-view',
		            loadMsg:'请稍后，正在加载数据...',collapsible:true,url:'/rest/trade/trading/phoneFlow'">
			        <thead>
			            <tr>
			            	<th data-options="field:'name',width:200">产品名称</th>
			            	<th data-options="field:'buy',width:200">买入金额</th>
			            	<th data-options="field:'sell',width:200">卖出金额</th>
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		
</body>
</html>