<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>平台汇款</title>
<jsp:include page="/common/head.jsp"></jsp:include>
    																			  
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/sell/platformTrnsfer.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>

</head>
<body>


	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div id="tbx" data-options="region:'center'"                                                               
			style="padding: 5px; height: auto">
			<table id="dgx" class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/sell/platformTransfer/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100">交易id</th>
						<th data-options="field:'aId',width:100">对应账户id</th>
						<th data-options="field:'name',width:100">姓名</th>
						<th data-options="field:'idCard',width:100">身份证号码</th>
						<th data-options="field:'money',width:100">操作金额</th>
				
						<th
							data-options="field:'status',width:100,
			            					formatter:function(value){
										        if(value==1)
										                return '成功';
										        else if(value==2)
										                return '失败';
										        else if(value==0)
										                return '处理中';
								               
										        }">处理状态</th>
						<th data-options="field:'operateTime',width:100"
							formatter="formatTimer">发送时间</th>
						
					</tr>
				</thead>
			</table>
		</div>

		<!-- 弹出编辑消息 -->
	
	</div>
	<!-- 发送消息-->
	<div id="autoPay" class="easyui-dialog"
		style="width: 860px; height: 400px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'平台汇款',iconCls: 'icon-add',buttons: '#dlg-buttons'">

		<form id="autoPayForm" method="post" novalidate="novalidate">
			<table>
				<tr>
					<!-- <td>用户名称：</td>
					<td><input class="easyui-textbox" id="userName" /></td> -->
					<td>手机号码：</td>
					<td><input class="easyui-textbox" id="telphone"  /></td>
					<td><a href="javascript:search()" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-search">查询</a></td>
				</tr>
			</table>
			
            <input type="hidden" id="uId" name="uId"/> 
			<table id="dg" class="easyui-datagrid" title="请选择用户"
				style="width: 806px; height: 200px"
				data-options="rownumbers:true,singleSelect:false,method:'post'">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'id',width:150,align:'center'">ID</th>
						<th data-options="field:'loginAccount',width:150,align:'center'">用户登录名称</th>
						<th data-options="field:'telephone',width:150,align:'center'">手机号码</th>
						<th data-options="field:'name',width:150,align:'center'">名称</th>
						<th data-options="field:'idCard',width:150,align:'center'">身份证号码</th>
					</tr>
				</thead>
			</table>

			<table class="view">
				
				<tr>
					<td>汇款金额(元)：</td>
					<td>
					<input class="easyui-numberbox" name="money"
							id="money" style="width: 200px"
							data-options="min:0.01,precision:2,required:true"></input>
				 </td>
				</tr>
			</table>
			<a href="javascript:autoPaySubmit()" class="easyui-linkbutton"
				id="btnAddOK" iconcls="icon-search">汇款</a>
		</form>

	</div>

</body>
</html>