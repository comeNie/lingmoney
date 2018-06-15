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
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/wallet/autoPay.js"></script>
</head>
<body> 
	<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
				<form id="searchFrm">
					<table>
						<tr>
							<td>手机号：</td>
							<td><input class="easyui-textbox" name="phone"
								id="phone" /></td>
							<td><a href="javascript:search()" class="easyui-linkbutton"
								id="btnAddOK" iconcls="icon-search">查询</a></td>
						</tr>
					</table>
				</form>
			</div>
	        <div id="tb" data-options="region:'center'"
				style="padding: 5px; height: auto">
				<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
					data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/wallet/autoPay/list',method:'post'">
					<thead>
						<tr>
							<th data-options="field:'id',width:50">id</th>
							<th data-options="field:'uId',width:100">用户id</th>
							<th data-options="field:'telephone',width:100">手机号</th>
							<th data-options="field:'upId',width:100">用户属性id</th>
							<th data-options="field:'walletAutoPay',width:100,
											formatter:function(value){
										        if(value==0)
										                return '未开通';
										        else if(value==1)
										                return '已开通';
										        }">自动支付</th>
							<th data-options="field:'operateName',width:100">操作人</th>
							<th data-options="field:'operateTime',width:100"
								formatter="formatTimer">操作时间</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		
</body>
</html>