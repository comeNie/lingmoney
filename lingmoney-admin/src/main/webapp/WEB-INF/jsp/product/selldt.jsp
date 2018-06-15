<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品推荐表</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/product/selldt.js"></script>
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/trade/trading/list',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:100">id</th>
			            	<th data-options="field:'userName',width:100">用户id</th>
			            	<th data-options="field:'pName',width:100">产品id</th>
			            	<th data-options="field:'pCode',width:100">产品code码</th>
			            	<th data-options="field:'pcType',width:100" formatter="formatTypes">产品分类类型</th>
			            	<th data-options="field:'pCname',width:100">产品分类id</th>
			            	<th data-options="field:'buyMoney',width:100">购买金额</th>
			            	<th data-options="field:'financialMoney',width:100">理财金额</th>
			            	<th data-options="field:'buyDt',width:100" formatter="formatTimer">购买日期</th>
			            	<th data-options="field:'valueDt',width:100" formatter="formatTimer">计息日</th>
			            	<th data-options="field:'wValueDt',width:100" formatter="formatTimer">筹备期计息日</th>
			            	<th data-options="field:'minSellDt',width:100" formatter="formatTimer">可赎回的日期</th>
			            	<th data-options="field:'status',width:100">状态</th>
			            	<th data-options="field:'bizCode',width:100">交易码</th>
			            	<th data-options="field:'sellDt',width:100" formatter="formatTimer">卖出日期</th>
			            	<th data-options="field:'sellMoney',width:100">卖出金额</th>
			            	<th data-options="field:'outBizCode',width:100">卖出交易码</th>
			            	<th data-options="field:'fixInvest',width:100" formatter="formatInvest">定期投资的标志</th>
			            	<th data-options="field:'autoPay',width:100" formatter="formatAutoPay">是否自动付款</th>
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		
</body>
</html>