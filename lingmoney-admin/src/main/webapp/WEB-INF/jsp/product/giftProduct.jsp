<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>活动产品关联礼品表</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/giftProduct.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
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
						<td><input class="easyui-textbox" name="pName" id="productName" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/giftProduct/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100">id</th>
						<th data-options="field:'pId',width:100">产品id</th>
						<th data-options="field:'pName',width:100">产品名称</th>
						<th data-options="field:'gId',width:100">礼品id</th>
						<th data-options="field:'gName',width:100">礼品名称</th>
						<th data-options="field:'gDesc',width:200">描述信息</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 850px; height: 480px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加礼品',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="pName">活动产品:</label></th>
						<td><select class="easyui-combogrid"
							id="apId" name="pId" style="width: 200px"
							data-options="
							            panelWidth: 200,
							            idField: 'id',
							            required:true,
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/product/activityProduct/listProduct', 
							            method: 'get',
							            remote:'remote',
							            columns: [[
							                {field:'id',title:'产品id',width:30},
							                {field:'name',title:'产品名称',width:50}
							            ]],
							            fitColumns: true
							        ">
						</select>
						<input type="hidden" name="pName" id="apName"/>
						</td>
					<th><label for="gName">礼品名称：</label></th>
						<td><select class="easyui-combogrid" name="gId" id="agId"
							style="width: 200px"
							data-options="
							            panelWidth: 200,
							            idField: 'id',
							            required:true,
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'gName',
							            url: '/rest/product/gift/listGift',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'gName',title:'名称',width:50}
							            ]],
							            fitColumns: true
							        ">
						</select>
						<input type="hidden" name="gName" id="agName"/> 
						</td>
				</tr>
				<tr>
					<th><label for="content">描述：</label></th>
					<td colspan="3"><textarea name="discription" id="acontent"
							name="gDesc"
							style="width: 150px; height: 290px; visibility: hidden;"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 编辑的弹出层 -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 850px; height: 480px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改礼品',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
			<input type="hidden" name="id" id="id">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="gName">礼品名称：</label></th>
						<td><select class="easyui-combogrid" name="gId" id="egId"
							style="width: 200px"
							data-options="
							            panelWidth: 200,
							            idField: 'id',
							            required:true,
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'gName',
							            url: '/rest/product/gift/listGift',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'gName',title:'名称',width:50}
							            ]],
							            fitColumns: true
							        ">
						</select>
						<input type="hidden" name="gName" id="egName"/> 
						</td>
				</tr>
				<tr>
					<th><label for="econtent">礼品描述：</label></th>
					<td colspan="3"><textarea name="discription" id="econtent" name="gDesc"
							style="width: 200px; height: 290px; visibility: hidden;"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>