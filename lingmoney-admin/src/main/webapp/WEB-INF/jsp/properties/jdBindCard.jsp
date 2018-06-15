<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>京东绑卡</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var select = "N";
	$(function (){
		
		queryJDBindCardStatus();
		
		$("#type").combobox({
			value : select,
			onSelect : function(param){
				updateJDBindCardStatus(param);
			}
		});
	});
	
	function queryJDBindCardStatus() {
		$.ajax({
			url : "/rest/properties/queryJDBindCardSwitch",
			type : "get",
			async : false,
			dataType : "text",
			success : function (data) {
				select = data;
			},
		});
	}
	
	function updateJDBindCardStatus(param) {
		$.ajax({
			url : "/rest/properties/updateJDBindCardSwitch",
			type : "get",
			data : {type : param.value},
			dataType : "text",
			success : function (data) {
				$.messager.alert("提示", data);
			}
		});
	}
	function setMinimumAmount(){
		var amount=$("#amount").val();
		if(amount!=""){
			$.ajax({
				url : "/rest/properties/setMinimumAmount",
				type : "post",
				data : {amount : amount},
				dataType : "text",
				success : function (data) {
					$.messager.alert("提示", data);
				}
			});
		}else{
			$.messager.alert("提示", "请输入充值最小金额");
		}
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 175px">
			<form id="searchForm" method="post">
				<table>
					<tr>
						<td>京东绑卡开关：</td>
						<td><input id="type" class="easyui-combobox" name="dept" data-options="valueField:'value',textField:'label',
								data: [{
									label: '开启',
									value: 'Y'
								},{
									label: '关闭',
									value: 'N'
								}]" />  </td>
						
					</tr>
					<tr>
						<td>华兴E账户允许充值最小金额设置：</td>
						<td><input id="amount" class="easyui-numberbox" data-options="min:0,precision:2"/></td>
						<td> <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="margin-left: 10px;" onclick="setMinimumAmount()">确定</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>

