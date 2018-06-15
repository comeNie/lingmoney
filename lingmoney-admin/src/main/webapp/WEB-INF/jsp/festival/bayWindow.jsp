<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>飘窗设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/festival/bayWindow.js"></script>
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
						<td>名称：</td>
						<td><input class="easyui-textbox" style="width: 180px"
							name="title" id="title"
							data-options="prompt:'请输入标题',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#title').textbox('setValue', '');
											}
										}]" /></td>
						<td>状态：</td>
						<td><select class="easyui-combobox" name="status" id="status"
							style="width: 200px;"
							data-options="value:'',prompt:'请选择状态',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#status').combobox('setValue', '');
											}
										}]">
								<option value="0">不显示</option>
								<option value="1">显示</option>
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/festival/bayWindow/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'title',width:50">标题</th>
						<th data-options="field:'appImgUrl',width:50"
							formatter="formatPicture">app飘窗图片url</th>
						<th data-options="field:'pcImgUrl',width:50"
							formatter="formatPicture">网站飘窗图片url</th>
						<th data-options="field:'appOpenUrl',width:50">app打开链接</th>
						<th data-options="field:'pcOpenUrl',width:50">网站打开链接</th>
						<th data-options="field:'startTime',width:50" formatter="formatTimer">显示开始时间</th>
						<th data-options="field:'endTime',width:50" formatter="formatTimer">显示结束时间</th>
						<th data-options="field:'createTime',width:50" formatter="formatTimer">创建时间</th>
						<th
							data-options="field:'disPos',width:50,
			            					formatter:function(value){
										        if(value==0)
										                return '全站显示';
										        }">显示位置</th>
						<th
							data-options="field:'status',width:50,
			            					formatter:function(value){
										        if(value==0)
										                return '不显示';
										        else if(value==1)
										                return '显示';
										        }">状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加和修改的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 830px; height: 350px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'设置飘窗',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">标题：</label></th>
					<td><input class="easyui-textbox" name="title"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">app飘窗图片：</label></th>
					<td><input class="easyui-filebox" name="appImg"
						style="width: 200px" data-options="prompt:'默认不修改'"></input></td>
					<th><label for="name">网站飘窗图片：</label></th>
					<td><input class="easyui-filebox" name="pcImg"
						style="width: 200px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="name">app打开链接：</label></th>
					<td colspan="3"><input class="easyui-textbox" name="appOpenUrl"
						style="width: 600px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">网站打开链接：</label></th>
					<td colspan="3"><input class="easyui-textbox" name="pcOpenUrl"
						style="width: 600px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="startDate">起始时间：</label></th>
					<td><input class="easyui-datetimebox" name="startTime"
						style="width: 200px"
						data-options="required:false,prompt:'请选择开始时间',editable:false"></input>
					</td>
					<th><label for="endDate">结束时间：</label></th>
					<td><input class="easyui-datetimebox" name="endTime"
						style="width: 200px"
						data-options="required:false,prompt:'请选择结束时间',editable:false"></input>
					</td>
				</tr>
				<tr>
					<th><label for="startDate">显示位置：</label></th>
					<td><input class="easyui-numberbox" name="disPos"
						style="width: 200px" data-options="required:false,prompt:'默认0'"></input>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="saveAndUpdate()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>