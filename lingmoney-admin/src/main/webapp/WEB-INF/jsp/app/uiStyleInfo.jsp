<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UI样式信息</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/app/uiStyle.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>批次号：</td>
						<td><input class="easyui-textbox" style="width: 180px" id="batchNo" /></td>
						<td>描述：</td>
						<td><input class="easyui-textbox" style="width: 180px" id="desc" /></td>
						<td>状态：</td>
						<td>
							<select id="status" class="easyui-combobox" style="width:200px;">   
							    <option value="">全部</option>   
							    <option value="1">可用</option>   
							    <option value="0">禁用</option>   
							</select>
						</td>
						<td><a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:false,toolbar:toolbar,fitColumns:false,pagination:true,pageSize:30,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/uiStyle/listUiStyle',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:150">id</th>
						<th data-options="field:'uiKey',width:150">key</th>
						<th data-options="field:'description',width:150">描述</th>
						<th data-options="field:'groupName',width:150">所属组</th>
						<th data-options="field:'url',width:150">图片地址</th>
						<th data-options="field:'status',width:150, formatter: formatStatus">状态</th>
						<th data-options="field:'batchNumber',width:150">批次号</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<!-- 添加和修改的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 640px; height: 250px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加/修改',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate" enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblAdd" class="view">
				<tr>
					<th>key</th>
					<td><input class="easyui-textbox" name="uiKey"/></td>
					
					<th>描述</th>
					<td><input class="easyui-textbox" name="description"/></td>
				</tr>
				
				<tr>
					<th>组</th>
					<td><input class="easyui-textbox" name="groupName"/></td>
					
					<th>图片地址</th>
					<td><input class="easyui-filebox" name="imageFile"/></td>
				</tr>
				
				<tr>
					<th>状态</th>
					<td>
						<select id="status" class="easyui-combobox" style="width:200px;" name="status">   
						    <option value="0">禁用</option>   
						    <option value="1">可用</option>   
						</select>
					</td>
					
					<th>批次号</th>
					<td><input class="easyui-textbox" name="batchNumber"/></td>
				</tr>
				
				<tr>
					<td colspan="4" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-ok" onclick="saveOrUpdate()">确定</a> 
						<a href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel" onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>