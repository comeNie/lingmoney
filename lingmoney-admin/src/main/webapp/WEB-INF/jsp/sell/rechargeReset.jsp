<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>充值重置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/sell/rechargeReset.js"></script>
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
			data-options="region:'north',title:'操作窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="resetFrom">
				<table>
					<tr>
						<td>手机号码：</td>
						<td><input class="easyui-textbox" name="telephone"
							id="telephone" /></td>
						<td><a href="javascript:reset()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">重置</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>