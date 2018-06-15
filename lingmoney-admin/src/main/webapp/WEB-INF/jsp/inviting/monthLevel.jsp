<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>人脉风云排行榜（每月）</title>
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
			url : '/rest/inviting/monthInvitingRewardList',
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
	
	function updateContent() {
		var rows = $("#dataGrid").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert("提示", "请选择一条记录", "error");
			return;
		}
		$('#ffAddUpdate').form('clear');
		$("#DivAddUpdate").dialog('setTitle','编辑');
		$("#DivAddUpdate").dialog('open');
	}
	
	function save() {
		var rows = $("#dataGrid").datagrid("getSelections");
		$.ajax({
			url : '/rest/inviting/monthUpdateInvitingReward',
			type : 'POST',
			data : {
				'id' : rows[0].id,
				'rewardCode' : $("#rewardCode").val(),
				'rewardContent' : $("#rewardContent").val(),
				'rewardTime' : $("#rewardTime").datebox('getValue')
			},
			success : function(data) {
				if (data.code == 200) {
					$.messager.alert("提示", "编辑成功", "error");
					window.location.reload();
				} else {
					$.messager.alert("提示", data.msg, "error");
				}
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
					data-options="iconCls:'icon-search'" onclick="updateContent();">编辑</a>
			</div>
			
		</form>
	</div>
	<div data-options="region:'center',border:false"
		style="width: 550px; height: 350px; overflow: hidden;">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	
	<!-- 编辑弹出层 -->
	<div id="DivAddUpdate" class="easyui-dialog" style="width: 700px; height: 385px; padding: 10px 20px" closed="true" resizable="true" modal="true"
		data-options="buttons: '#dlg-buttons'">
		<form id="ffAddUpdate" method="post" novalidate="novalidate" enctype="multipart/form-data">
			<table class="view">
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
						<input class="easyui-textbox" name="rewardCode" id="rewardCode"
						style="width: 152px"
						data-options="prompt:'如果有电子券，需填写电子码',required:false,
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#rewardCode').textbox('setValue', '');
									}
								}]" />
					</td>
					<td>赠送时间：</td>
					<td>
						<input id="rewardTime" class="easyui-datetimebox" name="rewardTime" 
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