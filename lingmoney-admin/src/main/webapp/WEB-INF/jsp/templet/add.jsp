<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>添加模板</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/productImg.js"></script>
</head>
<body>
<!-- http://localhost:8081/rest/forward/templet_add -->
	<!-- <div class="easyui-layout" style="width: 700px;" fit="true">
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/image/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100">id</th>
						<th data-options="field:'name',width:100">产品名称</th>
						<th data-options="field:'url',width:100">图片路径</th>
						<th data-options="field:'status',width:100"
							formatter="formatStatus">状态</th>
						<th data-options="field:'type',width:100" formatter="formatType">类型</th>
					</tr>
				</thead>
			</table>
		</div>
	</div> -->
	
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data" action="/rest/templet/saveFile">
			<input type="hidden" name="id">
			
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">上传模板：</label></th>
					<td><input type="file" id="file" name="file"></input></td>
				</tr>
				
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<input type="submit" value="上传"/>
					</td>
				</tr>
			</table>
		</form>
	</div> 
	
	<%--添加的弹出层--%>
	<!-- <div id="DivAdd" class="easyui-dialog"
		style="width: 400px; height: 210px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'产品图片设置',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">产品名称：</label></th>
					<td><input class="easyui-textbox" name="name"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">上传图片：</label></th>
					<td><input class="easyui-filebox" name="aimg"
						style="width: 200px" data-options="prompt:'请选择图片'"></input></td>
				</tr>
				<tr>
					<th><label for="status">类型：</label></th>
					<td><select class="easyui-combogrid" name="type"
						style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/resource/json/productImgType.json',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'id',width:30},
						                {field:'name',title:'名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="saveAndUpdate()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div> -->
</body>
</html>