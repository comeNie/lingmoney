<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品分类表</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/category.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>产品分类名称：</td>
						<td><input class="easyui-textbox" name="name"
							id="categoryName" style="width: 200px"
							data-options="prompt:'请输入产品分类名称',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#categoryName').textbox('setValue', '');
											}
										}]" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/category/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100">id</th>
						<th data-options="field:'code',width:100">code码</th>
						<th data-options="field:'name',width:100">分类名称</th>
						<th data-options="field:'gId',width:100">分类组id</th>
						<th data-options="field:'gName',width:100">分类组名称</th>
						<th
							data-options="field:'type',width:100,
			            					formatter:function(value){
										        if(value==0)
										                return '固定收益类';
										        else if(value==1)
										                return '浮动';
										        }">类型</th>
						<th
							data-options="field:'fixType',width:100,
			            					formatter:function(value){
										        if(value==0)
										                return '无';
										        else if(value==1)
										                return '固定不变';
										        else if(value==2)
										                return '区间';
										        }">固定子类型</th>
						<th
							data-options="field:'status',width:100,
											formatter:function(value){
										        if(value==0)
										                return '未生效';
										        else if(value==1)
										                return '已生效';
										        }">状态</th>
						<th data-options="field:'description',width:100">描述信息</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 400px; height: 400px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加产品分类',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate">
			<input type="hidden" name="code" id="acode"> <input
				type="hidden" name="gName" id="agName">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">分类名称：</label></th>
					<td><input class="easyui-textbox" name="name" id="aname"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="gId">分类组：</label></th>
					<td><select class="easyui-combogrid" name="gId" id="agId"
						style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/product/param/list/product_group',
						            method: 'get',
						            mode:'remote',
						            columns: [[
						                {field:'id',title:'分类组id',width:30},
						                {field:'name',title:'分类组名称',width:50}
						            ]],
						            onShowPanel:function(){
						            	$(this).combogrid('grid').datagrid('reload');
						            },
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="type">类型：</label></th>
					<td><select class="easyui-combogrid" name="type" id="atype"
						style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'typeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'typeName',
						            url: '/resource/json/category_type.json',
						            method: 'get',
						            onChange:function(newValue,oldValue){
						            	showAndHideFixType('afixTypeTr','afixType',newValue,oldValue);
						            },
						            columns: [[
						                {field:'typeId',title:'类型id',width:30},
						                {field:'typeName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr id="afixTypeTr" style="display: none;">
					<th><label for="type">固定子类型：</label></th>
					<td><select class="easyui-combobox" name="fixType"
						id="afixType" data-options="required:true">
							<option value="1">固定不变</option>
							<option value="2">区间</option>
					</select></td>
				</tr>
				<tr>
					<th><label for="status">状态：</label></th>
					<td><select class="easyui-combogrid" name="status"
						id="astatus" style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'statusId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'statusName',
						            url: '/resource/json/category_status.json',
						            method: 'get',
						            columns: [[
						                {field:'statusId',title:'id',width:30},
						                {field:'statusName',title:'名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="description">描述信息：</label></th>
					<td><input class="easyui-textbox" name="description"
						id="adescription" style="width: 200px; height: 100px"
						data-options="required:true,multiline:true"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
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
		style="width: 400px; height: 400px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改产品分类',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
			<input type="hidden" name="id"> <input type="hidden"
				name="code" id="ecode"> <input type="hidden" name="gName"
				id="egName">
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">分类名称：</label></th>
					<td><input class="easyui-textbox" name="name" id="ename"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="gId">分类组：</label></th>
					<td><select class="easyui-combogrid" name="gId" id="egId"
						style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/product/param/list/product_group',
						            method: 'get',
						            onShowPanel:function(){
						            	$(this).combogrid('grid').datagrid('reload');
						            },
						            columns: [[
						                {field:'id',title:'分类组id',width:30},
						                {field:'name',title:'分类组名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="type">类型：</label></th>
					<td><select class="easyui-combogrid" name="type" id="etype"
						style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'typeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'typeName',
						            url: '/resource/json/category_type.json',
						            method: 'get',
						            onChange:function(newValue,oldValue){
						            	showAndHideFixType('efixTypeTr','efixType',newValue,oldValue,true);
						            },
						            columns: [[
						                {field:'typeId',title:'类型id',width:30},
						                {field:'typeName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="status">状态：</label></th>
					<td><select class="easyui-combogrid" name="status"
						id="estatus" style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'statusId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'statusName',
						            url: '/resource/json/category_status.json',
						            method: 'get',
						            columns: [[
						                {field:'statusId',title:'id',width:30},
						                {field:'statusName',title:'名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr id="efixTypeTr" style="display: none;">
					<th><label for="type">固定子类型：</label></th>
					<td><select class="easyui-combobox" name="fixType"
						id="efixType" data-options="required:true">
							<option value="1">固定不变</option>
							<option value="2">区间</option>
					</select></td>
				</tr>
				<tr>
					<th><label for="description">描述信息：</label></th>
					<td><input class="easyui-textbox" name="description"
						id="edescription" style="width: 200px; height: 100px"
						data-options="required:true,multiline:true"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
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