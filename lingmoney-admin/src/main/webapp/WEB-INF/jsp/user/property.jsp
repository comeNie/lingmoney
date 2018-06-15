<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/user/property.js"></script>
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
						<td>注册日期：</td>
						<td><input class="easyui-datebox" name="regDate" id="regDate" /></td>
						<td>时间符号：</td>
						<td>
		            			<select class="easyui-combogrid" name="symbol" id="symbol" style="width:200px" data-options="
							            panelWidth: 200,
							            idField: 'typeId',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'typeName',
							            url: '/resource/json/symbol.json',
							            method: 'get',
							            onShowPanel:function(){
							            	$(this).combogrid('grid').datagrid('reload');
							            },
							            columns: [[
							                {field:'typeId',title:'id',width:30},
							                {field:'typeName',title:'符号',width:50}
							            ]],
							            fitColumns: true
							        ">
							    </select>
						    </td>
						<td>平台类型：</td>
						<td>
		            			<select class="easyui-combogrid" name="platformType" id="platformType" style="width:200px" data-options="
							            panelWidth: 200,
							            idField: 'typeId',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'typeName',
							            url: '/resource/json/platform_type.json',
							            method: 'get',
							            onShowPanel:function(){
							            	$(this).combogrid('grid').datagrid('reload');
							            },
							            columns: [[
							                {field:'typeId',title:'平台id',width:30},
							                {field:'typeName',title:'平台名称',width:50}
							            ]],
							            fitColumns: true
							        ">
							    </select>
						    </td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/user/users/listProperty',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'uId',width:100">用户id</th>
						<th data-options="field:'nickName',width:100">昵称</th>
						<th data-options="field:'regDate',width:100" formatter="formatTimer">注册日期</th>
						<th data-options="field:'referralCode',width:100">推荐码</th>
						<th data-options="field:'platformType',width:100,
			            					formatter:function(value){
										        if(value==1)
										                return '<font color=green>导入用户</font>';
										        else if(value==0)
										                return '注册用户';
										        }">平台类型</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>


	
	
</body>
</html>