<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>礼品详情表</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/giftDetail.js"></script>
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
				data-options="rownumbers:true,singleSelect:false,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/giftDetail/list',method:'post'">
				<thead>
					<tr>
					    <th data-options="field:'ck',checkbox:true" name="check"></th>
						<th data-options="field:'id',width:100">id</th>
						<th data-options="field:'tId',width:100">交易Id</th>
						<th data-options="field:'pId',width:100">产品Id</th>
						<th data-options="field:'uId',width:100">用户Id</th>
						<th data-options="field:'gId',width:100">礼品Id</th>
						<th data-options="field:'gName',width:100">礼品名称</th>
						<th data-options="field:'share',width:100">礼品库存</th>
						<th data-options="field:'state',width:100"  formatter="formatStatus">状态</th>
						<th data-options="field:'gDesc',width:100">礼品描述</th>
						<th data-options="field:'createTime',width:100" formatter="formatTimer">创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!--编辑弹出层  -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 530px; height: 360px; padding: 10px 20px" closed="true" resizable="true" modal="true"
		data-options="title:'修改礼品',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">状态：</label></th>
					<td><select class="easyui-combogrid" name="state" id="gstate"
						style="width: 306px"
						data-options="
						            panelWidth: 200,
						            idField: 'typeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'typeName',
						            url: '/resource/json/giftDetail.json',
						            method: 'get',
						            columns: [[
						                {field:'typeId',title:'id',width:30},
						                {field:'typeName',title:'状态',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				
				<tr>
					<th><label for="name">礼品描述：</label></th>
					<td colspan="2">
					<input class="easyui-textbox" name="gDesc" id="egDesc" 
							style="width:300px; height: 200px;" data-options="multiline:true"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="sendMessage()">确定</a> <a
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