<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>人脉变现管理</title>
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
			url : '/rest/inviting/invitingFriendsList',
			// queryParams:{},
			traditional : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			// fitColumns : true,
			selectOnCheck : false,
			pageSize : 10,
			pageList : [ 10, 20, 30 ],
			onLoadSuccess: function(data) {
				if (data.code != 200) {
					$.messager.alert('错误', data.msg, 'error');
					return false;
				}
			},
			columns : [ [
					{ width : '100', align : 'center', title : 'id', field : 'id' },
					{ width : '100', align : 'center', title : 'uid', field : 'userId' },
					{ width : '100', align : 'center', title : '姓名', field : 'userName' },
					{ width : '80', align : 'center', title : '邀请人数', field : 'count' },
					{ width : '80', align : 'center', title : '首投人数', field : 'firstCount' },
					{ width : '120', align : 'center', title : '30元投资红包', field : 'status1',
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
					{ width : '120', align : 'center', title : '30元投资红包奖励时间', field : 'rewardTime1' },
					{ width : '120', align : 'center', title : '10元现金红包', field : 'status2',
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
					{ width : '120', align : 'center', title : '10元现金红包奖励时间', field : 'rewardTime2' },
					{ width : '120', align : 'center', title : '50元现金红包', field : 'status3',
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
					{ width : '120', align : 'center', title : '50元现金红包奖励时间', field : 'rewardTime3' },
					{ width : '120', align : 'center', title : '2元奖励', field : 'status4',
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
					{ width : '120', align : 'center', title : '6元奖励', field : 'status5',
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
					{ width : '120', align : 'center', title : '12元奖励', field : 'status6',
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
					{ field : 'opt', title : '操作', width : '120', align : 'center',
						formatter : function(value, rec, index) {
							return '<a href="#" onclick="searchResult(\''+rec.id+'\');">查看详情</a>';
						}
					}
			] ]
		});
	}
	
	function searchResult(id) {
		$.ajax({
			url : '/rest/inviting/invitingFriendsInfo',
			type : 'POST',
			data : {
				'id' : id
			},
			success: function(data) {
				if (data.code != 200) {
					$.messager.alert('错误', data.msg, 'error');
					return false;
				} else {
					var result = data.rows;
			    	if (result.length > 0) {
			    		var html = "";
			    		for (var i = 0; i < result.length; i++) {
			    			var obj = result[i];
			    			html += "<tr>";
    						html += "<td>"+obj.uId+"</td>";
    						html += "<td>"+obj.invitingName+"</td>";
    						var cardStatus = "";
   							if (obj.cardStatus == 0){
   								cardStatus =  "未绑卡";
   							} else {
   								cardStatus =  "已绑卡";
   							}
    						html += "<td>"+cardStatus+"</td>";
    						html += "<td>"+obj.regDate+"</td>";
    						html += "<td>"+obj.firstMoney+"</td>";
    						html += "<td>"+obj.rewardMoney+"</td>";
    						html += "<td>"+obj.rewardDate+"</td>";
    						html += "</tr>";

			    		}
			    		$("#tbody").empty();
						$("#tbody").append(html);
			    	}
				}
			}
		});
		
		$("#DivAddInfo").dialog('open');
		$("#DivAddInfo").dialog('setTitle','我的好友');
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
		var money = $("#money").val();
		var a = /^[0-9]*(\.[0-9]{1,2})?$/;
		if(!a.test(money)) {
			$.messager.alert("提示", "请输入正确金额", "error");
			return false;
		}
		$.ajax({
			url : '/rest/inviting/updateInvitingFriends',
			type : 'POST',
			data : {
				'id' : rows[0].id,
				'money' : money,
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
	<div class="queryButton" data-options="region:'north',border:false" style="margin-bottom: 10px; width: 100%; height: 10px; padding: 30px;">
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
	
	<!-- 添加首投详情弹出层 -->
	<div id="DivAddInfo" class="easyui-dialog" style="width: 70%; height: 80%; padding: 10px 20px" closed="true" resizable="true" modal="true"
		data-options="buttons: '#dlg-buttons'">
		<form id="ffAddInfo" method="post" novalidate="novalidate" enctype="multipart/form-data">
			<table id="" class="" border="0" cellspacing="0" cellpadding="0" width="100%" align="center" bordercolor="white"  style="table-layout:fixed;overflow:scroll;">  
                <thead>  
                    <tr class="" bgcolor= "#95B8E7">
                    <td width="40%" align="center">邀请人id</td>  
                    <td width="30%" align="center">邀请人</td>  
                    <td width="30%" align="center">绑卡状态</td>  
                    <td width="30%" align="center">注册时间</td>  
                    <td width="30%" align="center">首投金额</td>  
                    <td width="30%" align="center">奖励金额</td>  
                    <td width="30%" align="center">奖励时间</td>  
                </tr>  
                </thead>
                  
                <tbody align="center" id="tbody">  
                </tbody>  
            </table>  
		</form>
	</div>
	
	<!-- 编辑弹出层 -->
	<div id="DivAddUpdate" class="easyui-dialog" style="width: 700px; height: 385px; padding: 10px 20px" closed="true" resizable="true" modal="true"
		data-options="buttons: '#dlg-buttons'">
		<form id="ffAddUpdate" method="post" novalidate="novalidate" enctype="multipart/form-data">
			<table class="view">
				<tr>
					<td >赠送金额：</td>
					<td>
						<input class="easyui-textbox" name="money" id="money"
						style="width: 152px"
						data-options="prompt:'请输入金额',required:true,
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#giftCode').textbox('setValue', '');
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

