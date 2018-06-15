<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>手机端banner</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="/resource/js/info/clientBanner.js"></script>
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
						<td><input class="easyui-textbox" name="name" id="bannername"
							style="width: 200px"
							data-options="prompt:'请输入名称',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#bannername').textbox('setValue', '');
											}
										}]" /></td>
						<td>所属地区 ：</td>
						<td><select class="easyui-combogrid" id="sCityCode"
							style="width: 200px"
							data-options="
							            panelWidth: 200,
							            idField: 'code',
							            editable:false,
							            prompt:'请选择所属地区',
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/areaDomain/queryCodeName',
							            method: 'get',
							            columns: [[
							                {field:'code',title:'城市编码',width:30},
							                {field:'name',title:'所属地区',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#sCityCode').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td>状态 ：</td>
						<td><select class="easyui-combogrid" id="status" name="status"
							style="width: 200px"
							data-options="
							            panelWidth: 200,
							            idField: 'id',
							            editable:false,
							            prompt:'请选择状态',
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/resource/json/useStatus.json',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'名称',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#status').combogrid('setValue', '');
											}
										}]
							        ">
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/info/clientBanner/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'name',width:200">名称</th>
						<th data-options="field:'url',width:400">访问路径（关键字）</th>
						<th data-options="field:'level',width:100">优先级</th>
						<th data-options="field:'status',width:100"
							formatter="formatStatus">状态</th>
						<th data-options="field:'codePath1',width:100">地址1【Andriod】400x800</th>
						<th data-options="field:'codePath2',width:100">地址2【Andriod】720x1280</th>
						<th data-options="field:'codePath3',width:100">地址3【Andriod】1080x1800</th>
						<th data-options="field:'codePath4',width:100">地址4【ISO】640x1136</th>
						<th data-options="field:'codePath5',width:100">地址5【ISO】750 x
							1334</th>
						<th data-options="field:'codePath6',width:100">地址6【ISO】1080x1920</th>
						<th data-options="field:'cityCode',width:100"
							formatter="formatCityCode">所属地区</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 600px; height: 480px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加手机banner',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">banner名称：</label></th>
					<td><input class="easyui-textbox" name="name"
						style="width: 350px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="url">访问路径（关键字）</label></th>
					<td><input class="easyui-textbox" name="url"
						style="width: 350px" data-options="required:true" prompt="activity&产品活动id；webView&H5页面地址"></input></td>
				</tr>
				<tr>
					<th><label for="level">优先级：</label></th>
					<td><select class="easyui-combobox" name="level"
						style="width: 200px" data-options="required:true">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
					</select></td>
				</tr>
				<tr>
					<th><label for="path1">图片地址1【Andriod】400x800：</label></th>
					<td><input class="easyui-filebox" name="path1"
						style="width: 350px"></input></td>

				</tr>
				<tr>
					<th><label for="path2">图片地址2【Andriod】720x1280：</label></th>
					<td><input class="easyui-filebox" name="path2"
						style="width: 350px"></input></td>
				</tr>
				<tr>
					<th><label for="path3">图片地址3【Andriod】1080x1800：</label></th>
					<td><input class="easyui-filebox" name="path3"
						style="width: 350px"></input></td>

				</tr>
				<tr>
					<th><label for="path4">图片地址4【IOS】640x1136：</label></th>
					<td><input class="easyui-filebox" name="path4"
						style="width: 350px"></input></td>

				</tr>
				<tr>
					<th><label for="path5">图片地址5【IOS】750 x 1334：</label></th>
					<td><input class="easyui-filebox" name="path5"
						style="width: 350px"></input></td>

				</tr>
				<tr>
					<th><label for="path6">图片地址6【IOS】1080x1920：</label></th>
					<td><input class="easyui-filebox" name="path6"
						style="width: 350px"></input></td>

				</tr>
				<tr>
					<th><label for="cityCode">所属地区 ：</label></th>
					<td><select class="easyui-combogrid" name="cityCode"
						style="width: 200px"
						data-options="
							            panelWidth: 200,
							            idField: 'code',
							            required:true,
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/areaDomain/queryCodeName',
							            method: 'get',
							            columns: [[
							                {field:'code',title:'城市编码',width:30},
							                {field:'name',title:'所属地区',width:50}
							            ]],
							            fitColumns: true
							        ">
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
		style="width: 580px; height: 480px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改应用信息',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" />
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">banner名称：</label></th>
					<td><input class="easyui-textbox" name="name"
						style="width: 350px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="url">访问路径（关键字）</label></th>
					<td><input class="easyui-textbox" name="url"
						style="width: 350px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="level">优先级：</label></th>
					<td><select class="easyui-combobox" name="level"
						style="width: 200px" data-options="required:true">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
					</select></td>
				</tr>
				<tr>
					<th><label for="path1">图片地址1【Andriod】400x800：</label></th>
					<td><input class="easyui-filebox" name="path1"
						style="width: 350px"></input></td>

				</tr>
				<tr>
					<th><label for="path2">图片地址2【Andriod】720x1280：</label></th>
					<td><input class="easyui-filebox" name="path2"
						style="width: 350px"></input></td>
				</tr>
				<tr>
					<th><label for="path3">图片地址3【Andriod】1080x1800：</label></th>
					<td><input class="easyui-filebox" name="path3"
						style="width: 350px"></input></td>

				</tr>
				<tr>
					<th><label for="path4">图片地址4【IOS】640x1136：</label></th>
					<td><input class="easyui-filebox" name="path4"
						style="width: 350px"></input></td>

				</tr>
				<tr>
					<th><label for="path5">图片地址5【IOS】750 x 1334：</label></th>
					<td><input class="easyui-filebox" name="path5"
						style="width: 350px"></input></td>

				</tr>
				<tr>
					<th><label for="path6">图片地址6【IOS】1080x1920：</label></th>
					<td><input class="easyui-filebox" name="path6"
						style="width: 350px"></input></td>

				</tr>
				<tr>
					<th><label for="cityCode">所属地区 ：</label></th>
					<td><select class="easyui-combogrid" name="cityCode"
						style="width: 200px"
						data-options="
							            panelWidth: 200,
							            idField: 'code',
							            required:true,
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/areaDomain/queryCodeName',
							            method: 'get',
							            columns: [[
							                {field:'code',title:'城市编码',width:30},
							                {field:'name',title:'所属地区',width:50}
							            ]],
							            fitColumns: true
							        ">
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

</body>
</html>