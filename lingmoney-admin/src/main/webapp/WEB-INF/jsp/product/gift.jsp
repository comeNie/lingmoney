<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>礼品表</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/gift.js"></script>
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
						<td>礼品名称：</td>
						<td><input class="easyui-textbox" name="gName" id="giftName" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/gift/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100">id</th>
						<th data-options="field:'gName',width:100">礼品名称</th>
						<th data-options="field:'gMoney',width:100">礼品金额</th>
						<th data-options="field:'gCount',width:100">礼品库存</th>
						<th data-options="field:'type',width:100" formatter="formatType">礼品类型</th>
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
					<th><label for="name">礼品名称：</label></th>
					<td><input class="easyui-textbox" name="gName" id="gname"
						style="width: 200px" data-options="required:true"></input></td>

					<th><label for="gMoney">礼品金额：</label></th>
					<td><input type="text" class="easyui-textbox" name="gMoney"
						id="gmoney" style="width: 200px" data-options="required:true" /></td>
				</tr>
				<tr>
					<th><label for="gCount">礼品库存：</label></th>
					<td><input type="text" class="easyui-textbox" name="gCount"
						name="gcount" style="width: 200px" data-options="required:true" />
					</td>
					<th><label for="type">礼品类型：</label></th>
					<td><select class="easyui-combogrid" name="type" id="gtype"
						style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'typeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'typeName',
						            url: '/resource/json/gift.json',
						            method: 'get',
						            columns: [[
						                {field:'typeId',title:'id',width:30},
						                {field:'typeName',title:'名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="content">礼品描述：</label></th>
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
					<th><label for="name">礼品名称：</label></th>
					<td><input class="easyui-textbox" name="gName" id="ename"
						style="width: 200px" data-options="required:true"></input></td>
					<th><label for="gMoney">礼品金额：</label></th>
					<td><input type="text" class="easyui-textbox" name="gMoney"
						id="emoney" style="width: 200px" data-options="required:true" /></td>
				</tr>
				<tr>
					<th><label for="gCount">礼品库存：</label></th>
					<td><input type="text" class="easyui-textbox" name="gCount"
						name="ecount" style="width: 200px" data-options="required:true" />
					</td>
					<th><label for="type">礼品类型：</label></th>
					<td><select class="easyui-combogrid" name="type" id="etype"
						style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'typeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'typeName',
						            url: '/resource/json/gift.json',
						            method: 'get',
						            columns: [[
						                {field:'typeId',title:'id',width:30},
						                {field:'typeName',title:'名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
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