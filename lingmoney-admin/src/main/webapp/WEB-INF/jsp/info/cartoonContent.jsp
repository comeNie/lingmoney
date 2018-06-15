<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>漫画贴</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/info/cartoonContent.js"></script>
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
						<td>分类名称： <input type="hidden" id="cId" name="cId" />
						</td>
						<td><select class="easyui-combogrid" name="name"
							id="cartoonName" style="width: 200px;"
							data-options="
						            panelWidth: 200,
						            idField: 'id',
						            editable:false,
						            prompt:'请选择分类',
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/info/cartooncategory/list',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'分类Id',width:30},
						                {field:'name',title:'分类名称',width:50}
						            ]],
						            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#cartoonName').combogrid('setValue', '');
											}
										}]
						        ">
						</select></td>
						<td>状态 ：</td>
						<td><select class="easyui-combogrid" id="status"
							name="status" style="width: 200px"
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/info/content/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'cId',width:100">分类id</th>
						<th data-options="field:'title',width:200">标题</th>
						<th data-options="field:'contentPic',width:200">内容图片</th>
						<th data-options="field:'themePic',width:200">主题图片</th>
						<%-- <th data-options="field:'uId',width:200">用户Id</th>
						<th data-options="field:'uName',width:200">用户名称</th>--%>
						<th data-options="field:'status',width:200"
							formatter="formatStatus">状态</th>
						<th data-options="field:'pubDate',width:200"
							formatter="formatTimer">发布时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 440px; height: 250px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加漫画',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table id="tblAdd" class="view">
				<tr>
					<th><input type="hidden" name="pubDate" id="apubDate"></input>
						<label for="name">分类名称:</label></th>
					<td><select class="easyui-combogrid" name="cId" id="acId"
						style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/info/cartooncategory/list',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'分类Id',width:30},
						                {field:'name',title:'分类名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="name">标题:</label></th>
					<td><input class="easyui-textbox" data-options="required:true"
						name="title" id="atitle" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">内容图片:</label></th>
					<td><input class="easyui-filebox" name="acontent"
						id="acontentPic" style="width: 200px"
						data-options="prompt:'请选择图片',required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">主题图片:</label></th>
					<td><input class="easyui-filebox" name="atheme" id="athemePic"
						style="width: 200px" data-options="prompt:'请选择图片',required:true"></input></td>
				</tr>
				<%-- <tr>
					<th><label for="name">用户Id:</label></th>
					<td><input class="easyui-textbox" name="uId" id="auId"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">用户名称:</label></th>
					<td><input class="easyui-textbox" name="uName" id="auName"
						style="width: 200px"></input></td>
				</tr>--%>

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
		style="width: 650px; height: 300px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'编辑漫画',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" />
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">分类名称:</label></th>
					<td><select class="easyui-combogrid" name="cId" id="ecId"
						style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/info/cartooncategory/list',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'分类Id',width:30},
						                {field:'name',title:'分类名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
					<th><label for="name">标题:</label></th>
					<td><input class="easyui-textbox" data-options="required:true"
						name="title" id="etitle" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">内容图片:</label></th>
					<td><img id="contentPic" style="width: 200px; height: 150px" />
						<input class="easyui-filebox" name="econtent" style="width: 200px"
						data-options="prompt:'请选择图片'"></input></td>
					<th><label for="name">主题图片:</label></th>
					<td><img id="themePic" style="width: 200px; height: 150px" />
						<input class="easyui-filebox" name="etheme" style="width: 200px"
						data-options="prompt:'请选择图片'"></input></td>
				</tr>
				<%--<tr>
					<th><label for="name">用户Id:</label></th>
					<td><input class="easyui-textbox" name="uId" id="euId"
						style="width: 200px"></input></td>
					<th><label for="name">用户名称:</label></th>
					<td><input class="easyui-textbox" name="uName" id="euName"
						style="width: 200px"></input></td>
				</tr>--%>


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