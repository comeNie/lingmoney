<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>手机端引导图片</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript" src="/resource/js/DatePattern.js"></script>
<script type="text/javascript" src="/resource/js/app/appBootImage.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto;">
			<table class="easyui-datagrid" title="查询结果" id="bootImageGrid" height="99%"
				data-options="toolbar:toolbar,rownumbers:true,singleSelect:true,fitColumns:true,pagination:true,pageSize:20,iconCls:'icon-view',
		            loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/appBootImage/queryAll',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'title',width:60">标题</th>
						<th data-options="field:'imgUrl',width:350">图片地址</th>
						<th data-options="field:'createTime',width:90,formatter:formatTime">显示开始时间</th>
						<th data-options="field:'status',width:140,formatter:formatStatus">状态</th>
						<th data-options="field:'sizeCode',width:40">尺寸</th>
						<th data-options="field:'showEndTime',width:120,formatter:formatTime">显示结束时间</th>
						<th data-options="field:'cityCode',width:100,formatter:formatCityCode">城市编码</th>
						<th data-options="field:'type',width:100,formatter:formatType">所属类型</th>
						<th data-options="field:'data',width:100">类型对应数据</th>
						<th data-options="field:'colorInfo',width:100">色值</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 编辑的弹出层 -->
	<div id="DivEdit" class="easyui-dialog" closed="true" modal="true" title="编辑" style="width:700px;height:350px;padding:10px"
			data-options="
				iconCls: 'icon-edit',
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						save();
					}
				},{
					text:'取消',
					handler:function(){
						$('#DivEdit').dialog('close');
					}
				}]
			">
		<form id="bootImageForm" method="post" novalidate="novalidate" enctype="multipart/form-data">
			<input type="hidden" name="id" />
			<table class="view">
				<tr>
					<td>
						标题
					</td>
					<td>
						<input name="title" class="easyui-textbox easyui-validbox" data-options="required:true">
					</td>
					<td>
						图片
					</td>
					<td>
						<input name="imageFile" class="easyui-filebox" >
					</td>
				</tr>
				
				<tr>
					<td>
						尺寸
					</td>
					<td>
						<input name="sizeCode" class="easyui-textbox">
					</td>
					
					<td>
						城市
					</td>
					<td>
						<input name="cityCode" class="easyui-combobox easyui-validbox" data-options="
							required:true,
							valueField: 'label',
							textField: 'value',
							editable:false,
							data: [{
								label: '000',
								value: '全国'
							},{
								label: '131',
								value: '北京'
							},
							{
								label: '236',
								value: '青岛'
							},
							{
								label: '179',
								value: '杭州'
							}]" >
					</td>
				</tr>
				
				<tr>
					<td>
						显示开始时间
					</td>
					<td>
						<input name="createTime" class="easyui-datetimebox">
					</td>
					<td>
						显示结束时间
					</td>
					<td>
						<input name="showEndTime" class="easyui-datetimebox">
					</td>
				</tr>
				
				<tr>
					<td>
						类型
					</td>
					<td>
						<input name="type" class="easyui-combobox" data-options="
							required:true,
							valueField: 'label',
							textField: 'value',
							editable:false,
							data: [{
								label: '0',
								value: '默认类型'
							},{
								label: '1',
								value: '产品'
							},{
								label: '2',
								value: '活动'
							}]" >
					</td>
					<td>
						对应类型数据
					</td>
					<td>
						<input name="data" class="easyui-textbox">
					</td>
				</tr>
				<tr>
					<td>
						状态
					</td>
					<td>
						<input name="status" class="easyui-combobox" data-options="
							required:true,
							valueField: 'label',
							textField: 'value',
							editable:false,
							data: [{
								label: '1',
								value: '启用'
							},{
								label: '0',
								value: '禁用'
							}]" >
					</td>
					<td>色值</td>
					<td>
						<input class="easyui-textbox" name="colorInfo">
					</td>
				</tr>
				<tr>
					<td>原图片地址</td>
					<td colspan="3">
						<input class="easyui-textbox" readonly="readonly" name="imgUrl" style="width:450px;">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>