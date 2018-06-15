<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品购买情况</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/customQuery/productSellInfo.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />

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
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>产品名称：</td>
						<td>
							<input class="easyui-combogrid" id="productId" style="width: 300px" data-options="
							    panelHeight: 400,
								mode: 'remote',    
							    url: '/rest/product/product/list',    
							    idField: 'id',    
							    textField: 'name',
							    pagination: true,  
							    columns: [[    
							        {field:'id',title:'产品id',width:50,sortable:true},    
							        {field:'name',title:'产品名',width:250,sortable:true}    
							    ]]"/>
						</td>
						<td>支付状态：</td>
						<td>
							<input class="easyui-combobox" id="statusCombobox" data-options="
								valueField: 'label',
								textField: 'value',
								data: [{
									label: '-1',
									value: '全部'
								},{
									label: '0',
									value: '待支付'
								},{
									label: '1',
									value: '支付成功'
							 },{
                                    label: '4',
                                    value: '待成立'
                             },{
                                    label: '12',
                                    value: '华兴-支付中'
                             },{
                                    label: '5',
                                    value: '华兴-交易失败'
                             },{
									label: '18',
									value: '支付失败(没有去支付)'
							 }]" />
						</td>
						<td>
							<a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">

			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="fitColumns:true,pagination:true,pageSize:30,iconCls:'icon-view',showFooter:true,idField:'id',collapsible:true,
					method:'post',onLoadSuccess:function(data){
		           		statistics(data);
		           	}">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'name',width:200">客户姓名</th>
						<th data-options="field:'tel',width:200">客户手机号</th>
						<th data-options="field:'money',width:200">购买金额</th>
						<th data-options="field:'employeeName',width:200">理财经理</th>
						<th data-options="field:'buyStatus',width:100,styler: function(value,row,index){
								if (value == '支付成功'){
									return 'color:green;';
								} else if (value == '支付失败') {
									return 'color:red;';
								} else {
									return 'color:gray;';
								}
							}
						">购买状态</th>
					</tr>

				</thead>
			</table>
		</div>
	</div>

</body>
</html>