<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品参数表</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/param.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'west',split:false,title:'产品分类组信息',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<table class="easyui-datagrid" fit=true id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/param/list/product_group',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'name',width:200">名称</th>
						<th data-options="field:'description',width:200">描述</th>
						<th
							data-options="field:'status',width:200,formatter:function(value){
										        if(value==0)
										                return '不可用';
										        else if(value==1)
										                return '可用';
										        }">状态</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="easyui-panel"
			data-options="region:'center',title:'节假日信息',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<table class="easyui-datagrid" fit=true id="adHolidayGrid"
				data-options="singleSelect:true,toolbar:toolbarHoliday,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/param/list/holiday',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'name',width:200">名称</th>
						<th data-options="field:'holiday',width:200">日期</th>
						<th
							data-options="field:'status',width:200,formatter:function(value){
										        if(value==0)
										                return '不可用';
										        else if(value==1)
										                return '可用';
										        }">状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>


	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 350px; height: 245px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加产品参数',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate">
			<input type="hidden" name="type" id="atype" />
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">名称：</label></th>
					<td><input class="easyui-textbox" name="name" id="aname"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="description">描述信息：</label></th>
					<td><input class="easyui-textbox" name="description"
						id="adescription" style="width: 200px"
						data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="status">状态：</label></th>
					<td><select class="easyui-combobox" name="status"
							id="astatus" style="width: 200px;"
							data-options="value:'',prompt:'请选择状态',panelHeight:'auto',required:true,editable:false">
								<option value="0">不可用</option>
								<option value="1">可用</option>
						</select></td>
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
		style="width: 350px; height: 245px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改产品参数',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
			<input type="hidden" name="id">
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">名称：</label></th>
					<td><input class="easyui-textbox" name="name" id="ename"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="description">描述信息：</label></th>
					<td><input class="easyui-textbox" name="description"
						id="edescription" style="width: 200px"
						data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="status">状态：</label></th>
					<td><select class="easyui-combobox" name="status"
							id="astatus" style="width: 200px;"
							data-options="value:'',prompt:'请选择状态',panelHeight:'auto',required:true,editable:false">
								<option value="0">不可用</option>
								<option value="1">可用</option>
						</select></td>
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


	<!-- 添加holiday的弹出层 -->
	<div id="DivAddHoliday" class="easyui-dialog"
		style="width: 350px; height: 245px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加产品参数',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAddHoliday" method="post" novalidate="novalidate">
			<input type="hidden" name="type" id="hatype" />
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">名称：</label></th>
					<td><input class="easyui-textbox" name="name" id="haname"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="holiday">日期：</label></th>
					<td><input class="easyui-datebox" name="holiday" id="aholiday"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="status">状态：</label></th>
					<td><select class="easyui-combobox" name="status"
							id="astatus" style="width: 200px;"
							data-options="value:'',prompt:'请选择状态',panelHeight:'auto',required:true,editable:false">
								<option value="0">不可用</option>
								<option value="1">可用</option>
						</select></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="saveHoliday()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAddHoliday').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 编辑Holiday的弹出层 -->
	<div id="DivEditHoliday" class="easyui-dialog"
		style="width: 350px; height: 245px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改产品参数',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEditHoliday" method="post" novalidate="novalidate">
			<input type="hidden" name="id">
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">名称：</label></th>
					<td><input class="easyui-textbox" name="name" id="hename"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="holiday">日期：</label></th>
					<td><input class="easyui-datebox" name="holiday"
						id="heholiday" style="width: 200px" data-options="required:true"></input>
					</td>
				</tr>
				<tr>
					<th><label for="status">状态：</label></th>
					<td><select class="easyui-combobox" name="status"
							id="astatus" style="width: 200px;"
							data-options="value:'',prompt:'请选择状态',panelHeight:'auto',required:true,editable:false">
								<option value="0">不可用</option>
								<option value="1">可用</option>
						</select></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="updateHoliday()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivEditHoliday').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>