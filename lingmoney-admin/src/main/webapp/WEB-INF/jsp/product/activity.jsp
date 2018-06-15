<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>活动表</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/activity.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>

<style type="text/css">
fieldset {
	margin-bottom: 10px;
	color: #333;
	border: #06c dashed 1px;
}

legend {
	color: #06c;
	font-weight: 800;
	background: #fff;
}
</style>

</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 110px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>活动名称：</td>
						<td><input class="easyui-textbox" name="pactName"
							id="pactName"
							data-options="prompt:'请输入活动名称',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#pactName').textbox('setValue', '');
											}
										}]" /></td>
						<td>关键字：</td>
						<td><input class="easyui-textbox" name="actUrl" id="actUrl"
							data-options="prompt:'请输入关键字',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#actUrl').textbox('setValue', '');
											}
										}]" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
						<td style="width:100px;"></td>	
						<td rowspan="2">
						  <div style="width:800px; height:66px;overflow:auto;color:red;">
						      说明：<br>
						      1.如果《优先级》不为普通活动，则会在对应页面显示新手活动信息。PC无首页展示。<br>
						      2.如果想要活动在手机端展示需要满足以下条件：  1、在活动中心显示 2、是活动产品 3、是普通活动 4、状态可用<br>
						      3.设置活动可预热可以让用户在活动开始前进入活动页面。<br>
						      4.PC/APP活动描述内容为H5页面，分别对应各自链接进入的H5展示页面，所有页面相关内容都放在这里包括CSS JS等。<br>
						      5.如果是产品活动需要选择对应的活动产品。<br>
						      6.活动关键字用于控制banner跳转链接，对应首页大banner中跳转链接里的activityKey参数值。<br>
						      7.活动可以添加活动图片，在活动属性菜单中选择需要添加活动图片的活动中选择标题图片上传。
						  </div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/activityProduct/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'actName',width:150">活动名称</th>
						<th data-options="field:'actTitle',width:200">活动标题</th>
						<th data-options="field:'startDate',width:150"
							formatter="formatTimer">起始时间</th>
						<th data-options="field:'endDate',width:150"
							formatter="formatTimer">结束时间</th>
						<th data-options="field:'status',width:100"
							formatter="formatStatus">状态</th>
						<th data-options="field:'actUrl',width:100">关键字</th>
						<th data-options="field:'priority',width:150"
							formatter="formatPriority">优先级</th>
						<th data-options="field:'actCenter',width:150"
							formatter="formatCenter">是否在活动中心展示</th>
						<th data-options="field:'preheat',width:150"
							formatter="formatPreheat">是否可预热</th>
						<th data-options="field:'actType',width:150"
							formatter="formatType">活动类型</th>
						<th data-options="field:'apId',width:200">新手关联弹框提示</th>
						<th data-options="field:'linkUrlPc',width:150">新手pc端活动链接地址</th>
						<th data-options="field:'linkUrlApp',width:150">新手app端活动链接地址</th>
						<th data-options="field:'activityPic',width:150">新手活动首页宣传（图）</th>
						<th data-options="field:'activityGift',width:150">新手活动赠品（图）</th>
						<th data-options="field:'activityWord',width:150">新手活动文字（图）</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<!--添加的弹出层  -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 880px; height: 500px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'维护活动',iconCls: 'icon-add',
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
			<input type="hidden" name="id">
			<fieldset id="aProductBase">
				<legend>活动的基本信息</legend>
				<table id="tblAdd" class="view">
					<tr>
						<th><label for="name">活动名称：</label></th>
						<td><input class="easyui-textbox" name="actName"
							style="width: 200px" data-options="required:true"></input></td>
						<th><label for="name">活动标题：</label></th>
						<td><input class="easyui-textbox" name="actTitle"
							style="width: 200px" data-options="required:true"></input></td>
					</tr>
					<tr>
						<th><label for="startDate">起始时间：</label></th>
						<td><input class="easyui-datetimebox" name="startDate"
							style="width: 200px"
							data-options="required:true,prompt:'请选择开始时间',editable:false"></input>
						</td>
						<th><label for="endDate">结束时间：</label></th>
						<td><input class="easyui-datetimebox" name="endDate"
							style="width: 200px"
							data-options="required:true,prompt:'请选择结束时间',editable:false"></input>
						</td>
					</tr>
					<tr>
						<th>关键字：</th>
						<td><input class="easyui-textbox" name="actUrl"
							style="width: 200px" /></td>
						<th>活动状态：</th>
						<td><select class="easyui-combobox" name="status"
							style="width: 200px" data-options="editable:false,required:true">
								<option value="1">可用</option>
								<option value="0">不可用</option>
						</select></td>
					</tr>
					<tr>
						<th>设置优先级</th>
						<td><select class="easyui-combobox" name="priority"
							style="width: 200px;"
							data-options="editable:false,required:true,
										onChange:function(newValue,oldValue){
							            	showAndHidePriority(newValue,oldValue);
							            }">
								<option value="0">普通活动</option>
								<option value="1">新手特供活动（首页）</option>
								<option value="2">新手特供活动（理财列表页）</option>
								<option value="3">新手特供活动（购买页）</option>
						</select></td>
						<th>是否可预热：</th>
						<td><select class="easyui-combobox" name="preheat"
							style="width: 200px;" data-options="editable:false,required:true">
								<option value="1">是</option>
								<option value="0">否</option>
						</select></td>
					</tr>

					<tr>
						<th>活动类型：</th>
						<td><select class="easyui-combobox" name="actType"
							style="width: 200px;"
							data-options="editable:false,required:true,
										onChange:function(newValue,oldValue){
							            	showAndHideType(newValue,oldValue);
							            }">
								<option value="0">产品活动</option>
								<option value="1">非产品活动</option>
						</select></td>
						<th>是否在活动中心展示：</th>
						<td><select class="easyui-combobox" name="actCenter"
							style="width: 200px;" data-options="editable:false,required:true">
								<option value="0">是</option>
								<option value="1">否</option>
						</select></td>
					</tr>
				</table>
			</fieldset>
			<fieldset id="actPriorityProp" style="display: block">
				<legend>新手特供活动属性项</legend>
				<table class="view">
					<tr>
						<th>是否关联弹框提示（打开app指定页面）:</th>
						<td><select class="easyui-combogrid" id="apId" name="apId"
							style="width: 200px"
							data-options="
							            panelWidth: 400,
							            panelHeight: 220,
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            prompt: '不选则默认跳h5页面',
							            pagination:true,
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/info/alertPrompt/list?status=1')
									    },
							            columns: [[
							                {field:'id',title:'编号',width:30},
							                {field:'name',title:'名称',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#apId').combogrid('setValue', '');
											}
										}]
							        ">
						</select><span style="color:red;">  如果新手活动关联弹框提示表，则取关联提示信息，否则跳转H5</span></td>
					</tr>
					<tr>
						<th><label for="name">pc端活动链接地址：</label></th>
						<td><input class="easyui-textbox" name="linkUrlPc"
							id="linkUrlPc" style="width: 582px" data-options="required:false"></input></td>
					</tr>
					<tr>
						<th><label for="reWay">app端活动链接地址：</label></th>
						<td><input class="easyui-textbox" name="linkUrlApp"
							id="linkUrlApp" style="width: 582px"
							data-options="required:false"></input></td>
					</tr>
					<tr>
						<th><label for="reWay">新手活动首页宣传（图）：</label></th>
						<td><input class="easyui-filebox" name="path1" id="path1"
							style="width: 200px"></input></td>
					</tr>
					<tr>
						<th><label for="reWay">新手活动赠品（图）：</label></th>
						<td><input class="easyui-filebox" name="path2" id="path2"
							style="width: 200px"></input></td>
					</tr>
					<tr>
						<th><label for="path1">新手活动文字（图）：</label></th>
						<td><input class="easyui-filebox" name="path3" id="path3"
							style="width: 200px"></input></td>
					</tr>
				</table>
			</fieldset>
			<fieldset id="actTypeProp" style="display: block">
				<legend>产品活动属性项</legend>
				<table class="view">
					<tr>
						<th>活动产品:</th>
						<td><select multiple="multiple" class="easyui-combogrid"
							id="pId" name="pId" style="width: 200px"
							data-options="
							            panelWidth: 200,
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/product/activityProduct/listProduct', 
							            method: 'get',
							            columns: [[
							                {field:'id',title:'产品id',width:30},
							                {field:'name',title:'产品名称',width:50}
							            ]],
							            fitColumns: true
							        ">
						</select></td>
					</tr>
				</table>
			</fieldset>

			<table class="view">
				<tr>
					<th><label for="content">pc活动描述：</label></th>
					<td colspan="3"><input class="easyui-textbox" name="actDesc"
						id="actDesc" style="width: 630px; height: 300px"
						data-options="multiline:true" /></td>
				</tr>
			</table>
			<table class="view">
				<tr>
					<th><label for="content">app活动描述：</label></th>
					<td colspan="3"><input class="easyui-textbox"
						name="actDescMobile" id="actDescMobile"
						style="width: 630px; height: 300px" data-options="multiline:true" /></td>
				</tr>
			</table>
		</form>
	</div>


</body>
</html>