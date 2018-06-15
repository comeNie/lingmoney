<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>中奖统计/限制表</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/festival/winningCount.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>手机号：</td>
						<td><input class="easyui-textbox" style="width: 180px"
							name="telephone" id="telephone"
							data-options="prompt:'请输入手机号',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#telephone').textbox('setValue', '');
											}
										}]" /></td>
						<td>数据生成状态：</td>
						<td><select class="easyui-combobox" name="status" id="status"
							style="width: 200px;"
							data-options="value:'',prompt:'请选择数据生成状态',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#status').combobox('setValue', '');
											}
										}]">
								<option value="0">导入</option>
								<option value="1">抽奖生成</option>
						</select></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/festival/winningCount/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'telephone',width:50">手机号</th>
						<th
							data-options="field:'status',width:50,
			            					formatter:function(value){
										        if(value==0)
										                return '导入';
										        else if(value==1)
										                return '抽奖生成';
										        }">数据生成状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 导入限制手机号 -->
	<div id="DivRegist" class="easyui-dialog"
		style="width: 400px; height: 140px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'导入限制手机号',iconCls: 'icon-batch-add',buttons: '#dlg-buttons'">
		<form id="ffRegist" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">选择txt文件：</label></th>
					<td><input class="easyui-filebox" name="txtFile" id="txtFile"
						style="width: 200px" data-options="prompt:'选择txt文件',required:true"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="upload()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivRegist').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 导入限制手机号 -->
</body>
</html>