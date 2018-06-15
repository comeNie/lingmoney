<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>企业用户设置</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/pay/paymentConfirmation.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/easyUI/datagrid-groupview.js"></script>
	 <style type="text/css">
        .subtotal { font-weight: bold; }/*合计单元格样式*/
    </style>
</head>
<body>
	<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm" style="float: left;">
	            	<table>
	            		<tr>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	           <table  class="easyui-datagrid" title="查询结果(只支持搜索查询)" fit='true' id="adGrid"
		            data-options="
		            singleSelect:false, 
		            showFooter:true,  
		            toolbar:toolbar,
		            fitColumns:true,
		            iconCls:'icon-view',
		            loadMsg:'请稍后，正在加载数据...',
		            idField:'id',
		            collapsible:true,
		            url:'/rest/paymentConf/paymentList',
		            method:'post',
		           	groupField:'userTelphone',
		           	view:groupview,
		           	groupFormatter:function(value,rows){
	                    return rows[0].userName+'['+rows[0].userTelphone+']';
	                },onLoadSuccess:function(data){
		           		compute(data);
		           	}
		            "> 
		            
		            <thead>
			            <tr>
			            	<th data-options="field:'ck',checkbox:true"></th>
			            	<th data-options="field:'id',width:100">id</th>
			            	<th data-options="field:'outBizCode',width:100">订单号</th>
			            	<th data-options="field:'partitionIndex',width:100">index位置</th>
			            	<th data-options="field:'outBizCodePartition',width:100">子订单号</th>
			            	<th data-options="field:'bizType',width:100" formatter="formatBizType">订单类型</th>
			            	<th data-options="field:'bizId',width:100">业务id</th>
			            	<th data-options="field:'userTelphone',width:100">用户手机号</th>
			            	<th data-options="field:'userName',width:100">用户姓名</th>
			            	<th data-options="field:'userCardNo',width:100">用户卡号</th>
			            	<th data-options="field:'userCardShort',width:100">用户卡的短称</th>
			            	<th data-options="field:'userAmount',width:150" align="right">兑付金额</th>
			            	<th data-options="field:'operTime',width:100"  formatter="formatTimer">操作时间</th>
			            </tr>
			        </thead>
		    	</table> 
	        </div>
		</div>
		<form id="ffAdd" method="post"></form>
</body>
</html>