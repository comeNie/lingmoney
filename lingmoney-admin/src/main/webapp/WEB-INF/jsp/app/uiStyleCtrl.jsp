<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>UI当前样式</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	$(function (){
		getUsedUiStyle();
	});
	
	function getUsedUiStyle() {
		$.ajax({
			url : "/rest/uiStyle/getUsedUiStyle",
			type : "post",
			success : function (data) {
				console.info(data);
				$("#num").textbox("setValue", data);
			},
		});
	}
	
	function setUsedUiStyle() {
		var num = $("#num").textbox("getValue");
		if (num == null || num == '') {
			return false;
		}
		
		$.ajax({
			url : "/rest/uiStyle/setUsedUiStyle",
			type : "post",
			data : {num : num},
			dataType : "text",
			success : function (data) {
				data = JSON.parse(data);
				$.messager.alert("提示", data.msg);
			}
		});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: auto; overflow: hidden; background-color: #fff">
		选择样式：<input type="text" class="easyui-textbox" name="num" id="num" placeholder="请填写数值"> 
			   <a href="javascript:void(0)" class="easyui-linkbutton" onclick="setUsedUiStyle()">提交</a>
	</div>
</body>
</html>

