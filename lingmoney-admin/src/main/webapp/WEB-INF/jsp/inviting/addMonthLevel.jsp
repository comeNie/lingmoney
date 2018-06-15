<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>添加人脉风云排行榜</title>
<jsp:include page="/common/head.jsp"></jsp:include>
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
<script type="text/javascript">
	var dataGrid;
	$(function() {
		searchList();		
	});
	
	function searchList(){	   
		dataGrid = $('#dataGrid').datagrid({
			url : '/rest/inviting/findInvitingLevelList',
			queryParams:{},
			traditional : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			// fitColumns : true,
			selectOnCheck:false,
			pageSize : 10,
			pageList : [ 10, 20, 30 ],
			onLoadSuccess: function(data) {
				if (data.code != 200) {
					$.messager.alert('错误', data.msg, 'error');
					return false;
				}
			},
			columns : [ [
					{ width : '120', align : 'center', title : 'id', field : 'id' },
					{ width : '100', align : 'center', title : '姓名', field : 'userName' },
					{ width : '120', align : 'center', title : '手机号', field : 'telephone' },
					{ width : '100', align : 'center', title : '月份', field : 'month' },
					{ width : '100', align : 'center', title : '邀请人数', field : 'count' },
					{ width : '100', align : 'center', title : '本月邀请等级', field : 'level' },
					{ width : '152', align : 'center', title : '奖励内容', field : 'rewardContent' },
					{ width : '100', align : 'center', title : '奖励状态', field : 'status',
						formatter:function(value, row, index){
							if(value == 0	){
								return "未获得";
							} else if (value == 1) {
								return "未赠送";
							} else if (value == 2) {
								return "已赠送";
							}
						}
					},
					{ width : '130', align : 'center', title : '奖励时间', field : 'rewardDate' }
			] ]
		});
	}
	
	function addContent() {
		$('#ffAddUpdate').form('clear');
		$("#DivAddUpdate").dialog('setTitle','添加');
		$("#DivAddUpdate").dialog('open');
	}
	
	function save() {
		var telephone = $("#telephone").val();
		var userName = $("#userName").val();
		var count = $("#count").val();
		var level = $("#level").val();
		var rewardContent = $("#rewardContent").val();
		var rewardDate = $("#rewardDate").datebox('getValue');
		if (null == telephone || telephone.trim() == "") {
			$.messager.alert('错误', '手机号不能为空', 'error');
			return false;
		}
		if (null == userName || userName.trim() == "") {
			$.messager.alert('错误', '姓名不能为空', 'error');
			return false;
		}
		if (null == count || count.trim() == "") {
			$.messager.alert('错误', '邀请人数不能为空', 'error');
			return false;
		}
		if (null == level || level.trim() == "") {
			$.messager.alert('错误', '排名不能为空', 'error');
			return false;
		}
		if (null == rewardContent || rewardContent.trim() == "") {
			$.messager.alert('错误', '奖励不能为空', 'error');
			return false;
		}
		if (null == rewardDate || rewardDate.trim() == "") {
			$.messager.alert('错误', '奖励时间不能为空', 'error');
			return false;
		}
		$('#ffAddUpdate').form('submit',{
			url: '/rest/inviting/addInvitingFriendsLevel',
			ajax:true,
			dataType:'json',
			success:function(data){
				 window.location.reload();
			}
		});
	}
	
	function deleteData() {
		var rows = $("#dataGrid").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert("提示", "请选择一条记录", "error");
			return;
		}
		$.ajax({
			url : '/rest/inviting/deleteInvitingLevelData',
			type : 'POST',
			data : {
				'id' : rows[0].id
			},
			success : function(data) {
				window.location.reload();
			}
		});
	}
</script>

 
</head>
<body class="easyui-layout" style="font-size:16px;" data-options="fit:true,border:false">
	<div class="queryButton" data-options="region:'north',border:false" style="margin-bottom: 10px; width: 100%; height: 50px; padding: 20px;">
		<form id="searchForm">
			<div>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
					data-options="iconCls:'icon-search'" onclick="searchList();">查询列表</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
					data-options="iconCls:'icon-search'" onclick="addContent();">添加</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
					data-options="iconCls:'icon-search'" onclick="deleteData();">删除</a>
			</div>
			
		</form>
	</div>
	<div data-options="region:'center',border:false"
		style="width: 550px; height: 350px; overflow: hidden;">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	
	<!-- 添加弹出层 -->
	<div id="DivAddUpdate" class="easyui-dialog" style="width: 700px; height: 385px; padding: 10px 20px" closed="true" resizable="true" modal="true"
		data-options="buttons: '#dlg-buttons'">
		<form id="ffAddUpdate" method="post" novalidate="novalidate" enctype="multipart/form-data">
			<table class="view">
				<tr>
					<td >手机号：</td>
					<td>
						<input class="easyui-textbox" name="telephone" id="telephone"
						style="width: 120px"
						data-options="prompt:'请输入手机号',required:true,
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#telephone').textbox('setValue', '');
									}
								}]" />
					</td>
					<td >姓名：</td>
					<td>
						<input class="easyui-textbox" name="userName" id="userName"
						style="width: 120px"
						data-options="prompt:'请输入姓名',required:true,
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#userName').textbox('setValue', '');
									}
								}]" />
					</td>
				</tr>
				<tr>
					<td >邀请人数：</td>
					<td>
						<input class="easyui-textbox" name="count" id="count"
						style="width: 120px"
						data-options="prompt:'请输入邀请人数',required:true,
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#count').textbox('setValue', '');
									}
								}]" />
					</td>
					<td >邀请人数：</td>
					<td>
						<input class="easyui-textbox" name="level" id="level"
						style="width: 120px"
						data-options="prompt:'请输入排名',required:true,
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#level').textbox('setValue', '');
									}
								}]" />
					</td>
				</tr>
				<tr>
					<td >赠送内容：</td>
					<td>
						<input class="easyui-textbox" name="rewardContent" id="rewardContent"
						style="width: 152px"
						data-options="prompt:'年卡需线下赠送，然后编辑赠送时间',required:true,
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#rewardContent').textbox('setValue', '');
									}
								}]" />
					</td>
					<td >电子卷：</td>
					<td>
						<input class="easyui-textbox" name="singNumber" id="singNumber"
						style="width: 152px"
						data-options="prompt:'如果有电子券，需填写电子码',required:false,
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#singNumber').textbox('setValue', '');
									}
								}]" />
					</td>
				</tr>
				<tr>
					<td>赠送时间：</td>
					<td>
						<input id="rewardDate" class="easyui-datetimebox" name="rewardDate" 
						data-options="editable:false,required:true," style="width:152px"/>
					</td>
				</tr>
				
				<tr>
					<td colspan="4" style="text-align: right;">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-ok" onclick="save()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAddUpdate').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>

</html>