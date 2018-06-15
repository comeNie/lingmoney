<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>小功能设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript">
	$(function() {
		$.getJSON("/rest/info/setting/showCrabCount?r=" + Math.random(),
				function(info) {
					if (info.code == 200) {
						$("#crabCountNow").text(info.obj);
					}
				});
	});

	// 设置赠送螃蟹个数
	function setCrabCount() {
		var crabCount = $("#crabCount").val();
		$.getJSON("/rest/info/setting/setCrabCount?r=" + Math.random()
				+ "&crabCount=" + crabCount, function(info) {
			if (info.code == 200) {
				$("#crabCountNow").text(crabCount);
				$.messager.alert('提示', '设置成功', 'info');
			} else {
				$.messager.alert('错误提示', info.msg, 'error');
			}
		});
	}
</script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<table>
			<tr>
				<td>赠送螃蟹个数(当前<span id="crabCountNow"></span>个)：
				</td>
				<td><input class="easyui-numberbox" style="width: 180px"
					id="crabCount"
					data-options="min:0,max:10000,prompt:'请输入赠送螃蟹个数',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#crabCount').numberbox('setValue', '');
											}
										}]" /></td>
				<td><a href="javascript:setCrabCount()"
					class="easyui-linkbutton" id="btnAddOK" iconcls="icon-setting">设置</a></td>
			</tr>
		</table>
	</div>

</body>
</html>