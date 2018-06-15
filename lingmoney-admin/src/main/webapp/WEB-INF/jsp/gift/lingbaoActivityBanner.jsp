<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的领地活动banner</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/gift/lingbaoActivityBanner.js"></script>
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
							name="name" id="name"
							data-options="prompt:'请输入名称',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#name').textbox('setValue', '');
											}
										}]" /></td>
						<td>状态：</td>
						<td><select class="easyui-combogrid" id="status"
							name="status" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择状态',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/gift/lingbaoActivityBanner/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'name',width:100">名称</th>
						<th data-options="field:'ltTypeId',width:100">领宝活动类型id</th>
						<th data-options="field:'status',width:100"
							formatter="formatStatus">状态</th>
						<th data-options="field:'webUrl',width:150">网站端跳转链接</th>
						<th data-options="field:'appUrl',width:150">手机端跳转链接</th>
						<th data-options="field:'level',width:150">优先级（由高到低）</th>
						<th data-options="field:'createTime',width:150"
							formatter="formatTimer">创建时间</th>
						<th data-options="field:'webBannerPath',width:150">网站端图片地址</th>
						<th data-options="field:'codePath1',width:150">地址1【Andriod】400x800</th>
						<th data-options="field:'codePath2',width:150">地址2【Andriod】720x1280</th>
						<th data-options="field:'codePath3',width:150">地址3【Andriod】1080x1800</th>
						<th data-options="field:'codePath4',width:150">地址4【IOS】640x1136</th>
						<th data-options="field:'codePath5',width:150">地址5【IOS】750x1334</th>
						<th data-options="field:'codePath6',width:150">地址6【IOS】1080x1920</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加和编辑的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 460px; height: 480px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'我的领地活动banner设置',iconCls: 'icon-add',
			buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        saveAndUpdate();
                    }
                },{
                    text:'关闭',
                     iconCls:'icon-cancel',
                    handler:function(){
                        $('#DivAdd').dialog('close');
                    }
                }]">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" />
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">名称：</label></th>
					<td><input class="easyui-textbox" name="name"
						style="width: 220px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">领宝活动类型id：</label></th>
					<td><select class="easyui-combogrid" id="ltTypeId" name="ltTypeId"
						style="width: 220px"
						data-options="
							            panelWidth: 400,
							            idField: 'id',
							            editable:false,
							            required:true,
							            loadMsg:'正在加载,请稍后...',
							            pagination:true,
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/gift/lingbaoLotteryType/list')
									    },
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="name">优先级(由高到低)：</label></th>
					<td><input class="easyui-numberbox" name="level"
						style="width: 220px" data-options="min:1,max:9999,required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">网站端跳转链接：</label></th>
					<td><input class="easyui-textbox" name="webUrl"
						style="width: 220px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">手机端跳转链接：</label></th>
					<td><input class="easyui-textbox" name="appUrl"
						style="width: 220px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">网站端图片：</label></th>
					<td><input class="easyui-filebox" name="webPath"
						style="width: 220px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="name">【Andriod】400x800</label></th>
					<td><input class="easyui-filebox" name="path1"
						style="width: 220px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="name">【Andriod】720x1280</label></th>
					<td><input class="easyui-filebox" name="path2"
						style="width: 220px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="name">【Andriod】1080x1800</label></th>
					<td><input class="easyui-filebox" name="path3"
						style="width: 220px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="name">【IOS】640x1136</label></th>
					<td><input class="easyui-filebox" name="path4"
						style="width: 220px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="name">【IOS】750x1334</label></th>
					<td><input class="easyui-filebox" name="path5"
						style="width: 220px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="name">【IOS】1080x1920</label></th>
					<td><input class="easyui-filebox" name="path6"
						style="width: 220px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>