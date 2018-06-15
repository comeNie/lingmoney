<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户推荐等级查询</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/user/userLevel.js"></script>
<style type="text/css">
</style>
</head>
<body>



	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>用户推荐码：</td>
						<td><input class="easyui-textbox" name="referralCode"
							id="areferralCode" style="width: 200px"
							data-options="prompt:'请输入用户推荐码',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#areferralCode').textbox('setValue', '');
											}
										}]" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 用户自己的信息 -->
		<div id="tz" data-options="region:'center'" style="padding: 5px;">
			<table class="easyui-datagrid" title="当前用户" id="azGrid"
				style="height: 20%"
				data-options="singleSelect:true,fitColumns:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',url:'/rest/user/transfer/searchUserByReferralCode',collapsible:true,method:'post'">
				<thead>
					<tr>
						<th data-options="field:'uId',width:200">用户Id</th>
						<th data-options="field:'name',width:200">用户姓名</th>
						<th data-options="field:'nickName',width:200">用户昵称</th>
						<th data-options="field:'userLevel',width:200">用户推荐等级</th>
						<th data-options="field:'referralCode',width:200">用户推荐码</th>
					</tr>
				</thead>
			</table>
			<table class="easyui-datagrid" title="推荐用户" id="adGrid"
				style="height: 80%"
				data-options="singleSelect:true,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',url:'/rest/user/users/userLevel',collapsible:true,method:'post'">
				<thead>
					<tr>
						<th data-options="field:'uId',width:200">用户Id</th>
						<th data-options="field:'name',width:200">用户姓名</th>
						<th data-options="field:'nickName',width:200">用户昵称</th>
						<th data-options="field:'referralCode',width:200">用户推荐码</th>
						<th data-options="field:'userLevel',width:200">用户推荐等级</th>
						<th data-options="field:'referralId',width:200">上级推荐用户id</th>
						<th data-options="field:'referralName',width:200">上级推荐用户名称</th>
					</tr>
				</thead>
			</table>
		</div>
		<!-- 用户自己的信息 -->
	</div>
</body>
</html>