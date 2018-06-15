<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品领宝活动</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/productActiveRate.js"></script>
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
				data-options="rownumbers:true,singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/productActiveRate/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100">id</th>
						<th data-options="field:'pId',width:100">产品Id</th>
						<th data-options="field:'pCode',width:100">产品code</th>
						<th data-options="field:'userId',width:100">用户Id</th>
						<th data-options="field:'pName',width:100">用户名称</th>
						<th data-options="field:'rate',width:100">倍率</th>
						<th data-options="field:'startDate',width:100"  formatter="formatTimer">开始时间</th>
						<th data-options="field:'endDate',width:100" formatter="formatTimer">结束时间</th>
						<th data-options="field:'createTime',width:100" formatter="formatTimer">创建时间</th>
					    <th data-options="field:'status',width:100" formatter="formatStatus">状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 440px; height: 280px; padding: 10px 20px" closed="true" resizable="true" modal="true"
		data-options="title:'添加',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">产品名称：</label></th>
					<td>
					<select class="easyui-combogrid" name="pId" id="apId"
							style="width: 200px"
							data-options="
						            panelWidth: 200,
						            idField: 'id',
						            editable:false,
						            required:true,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/product/product/list',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'产品id',width:30},
						                {field:'name',title:'产品名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						</select>
					</td>
				</tr>
				
				 <tr>
					<th><label for="name">用户名称：</label></th>
					<td>
					<select class="easyui-combogrid" name="userId" id="auserId"
							style="width: 200px"
							data-options="
						            panelWidth: 200,
						            idField: 'id',
						            editable:false,
						            required:true,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'loginAccount',
						            url: '/rest/user/users/list',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'用户id',width:30},
						                {field:'loginAccount',title:'用户名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						</select>
					</td>
				</tr>
				
				<tr>
					<th><label for="name">倍率：</label></th>
					<td>
					<input  class="easyui-numberbox" name="rate" id="arate" style="width:200px" data-options="required:true"></input>
					</td>
				</tr>
				<tr>
					<th><label for="name">开始时间：</label></th>
					<td>
					<input  class="easyui-datetimebox" name="startDate" id="astartDate" style="width:200px" data-options="required:true"></input>
					</td>
				</tr>
				<tr>
					<th><label for="name">结束时间：</label></th>
					<td>
					<input  class="easyui-datetimebox" name="endDate" id="aendDate" style="width:200px" data-options="required:true"></input>
					</td>
				</tr>
				
				<tr>
					<td colspan="4" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close')">取消</a>
					</td>
				</tr>
			</table>
				</form>
	    </div>
	<!--编辑弹出层  -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 440px; height: 280px; padding: 10px 20px" closed="true" resizable="true" modal="true"
		data-options="title:'修改',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
		<input type="hidden" name="id" >
			<table id="tblAdd" class="view">
				
				
				<tr>
					<th><label for="name">产品名称：</label></th>
					<td>
					<select class="easyui-combogrid" name="pId" id="pId"
							style="width: 200px"
							data-options="
						            panelWidth: 200,
						            idField: 'id',
						            editable:false,
						            required:true,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/product/product/list',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'产品id',width:30},
						                {field:'name',title:'产品名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						</select>
					</td>
				</tr>
				
				<tr>
					<th><label for="name">用户名称：</label></th>
					<td>
					<select class="easyui-combogrid" name="userId" id="euserId"
							style="width: 200px"
							data-options="
						            panelWidth: 200,
						            idField: 'id',
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'loginAccount',
						            url: '/rest/user/users/list',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'用户id',width:30},
						                {field:'loginAccount',title:'用户名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						</select>
					</td>
				</tr>
				
				<tr>
					<th><label for="name">倍率：</label></th>
					<td>
					<input  class="easyui-numberbox" name="rate" id="erate" style="width:200px" data-options="required:true"></input>
					</td>
				</tr>
				<tr>
					<th><label for="name">开始时间：</label></th>
					<td>
					<input  class="easyui-datetimebox" name="startDate" id="estartDate" style="width:200px" data-options="required:true"></input>
					</td>
				</tr>
				 <tr>
					<th><label for="name">结束时间：</label></th>
					<td>
					<input  class="easyui-datetimebox" name="endDate" id="eendDate" style="width:200px" data-options="required:true"></input>
					</td>
				</tr> 
				<!--<tr>
					<th><label for="name">创建时间：</label></th>
					<td>
					<input  class="easyui-datetimebox" name="createTime" id="ecreateTime" style="width:200px" data-options="required:true"></input>
					</td>
				</tr>-->
				
				<tr>
					<td colspan="4" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivEdit').dialog('close')">取消</a>
					</td>
				</tr>
			</table>
				</form>
	    </div>
	
</body>
</html>