<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>转移推荐用户</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/info/transfer.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>


</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'推荐码绑定修改',iconCls:'icon-book'"
			style="padding: 5px; height: 300px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>用户注册手机号：</td>
						<td><input class="easyui-textbox" name="telephone"
							id="telephone" /></td>
						<td>用户推荐码：</td>
						<td><input class="easyui-textbox" name="referralCode"
							id="referralCode" /></td>
						<td><a href="javascript:searchUser()"
							class="easyui-linkbutton" id="btnAddOKs" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
			<table class="easyui-datagrid" title="要修改的用户" id="adGrid"
				data-options="onClickRow: onClickRow,toolbar:toolbar,singleSelect:true,fitColumns:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,method:'post',onAfterEdit:saveChange">
				<thead>
					<tr>
						<th data-options="field:'id',width:150,align:'center',hidden:true">id</th>
						<th data-options="field:'uId',width:150,align:'center'">用户ID</th>
						<th data-options="field:'name',width:150,align:'center'">姓名</th>
						<th data-options="field:'referralCode',width:150,align:'center'">推荐码</th>
						<th data-options="field:'referralId',width:150,align:'center'">推荐人ID</th>
						<th data-options="field:'userLevel',width:150,align:'center'">用户推荐等级</th>
						<th
							data-options="field:'recommendedLevel',width:150,align:'center'">推荐层级</th>
						<th
							data-options="field:'isTransfer',width:150,align:'center',editor:'numberbox'"
							formatter="formatType">用户标识</th>
						<th data-options="field:'commissionRate',width:150,align:'center'">佣金费率</th>
					</tr>
				</thead>
			</table>

			<table class="easyui-datagrid" title="推荐的用户" id="adGridRec"
				data-options="onClickRow: onClickRows,toolbar:toolbars,rownumbers:true,singleSelect:true,fitColumns:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,method:'post',onAfterEdit:saveChange">
				<thead>
					<tr>
						<th data-options="field:'id',width:150,align:'center',hidden:true">id</th>
						<th data-options="field:'uId',width:150,align:'center'">用户ID</th>
						<th data-options="field:'name',width:150,align:'center'">姓名</th>
						<th data-options="field:'referralCode',width:150,align:'center'">推荐码</th>
						<th data-options="field:'referralId',width:150,align:'center'">推荐人ID</th>
						<th data-options="field:'userLevel',width:150,align:'center'">用户推荐等级</th>
						<th
							data-options="field:'recommendedLevel',width:150,align:'center'">推荐层级</th>
						<th
							data-options="field:'isTransfer',width:150,align:'center',editor:'numberbox'"
							formatter="formatType">用户标识</th>
						<th data-options="field:'commissionRate',width:150,align:'center'">佣金费率</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="easyui-panel"
			data-options="region:'center',title:'公司内部员工',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrms">
				<table>
					<tr>
						<td>员工推荐码(0+员工号)：</td>
						<td><input class="easyui-textbox" name="codeTo" id="codeTo" /></td>
						<td><a href="javascript:searchTo()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
			<table class="easyui-datagrid" title="员工信息" id="adGridTo"
				data-options="singleSelect:true,fitColumns:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,method:'post'">
				<thead>
					<tr>
						<th data-options="field:'uId',width:150,align:'center'">用户ID</th>
						<th data-options="field:'name',width:150,align:'center'">员工姓名</th>
						<th data-options="field:'referralCode',width:150,align:'center'">推荐码</th>
					</tr>
				</thead>
			</table>
			<table>
				<tr>
					<td>当前用户的佣金费率：</td>
					<td><input class="easyui-numberbox" name="commissionRateNow"
						id="commissionRateNow" data-options="prompt:'无需输入',editable:false,precision:2" /></td>
					<td><span id="stateNow"></span></td>
				</tr>
				<tr>
					<td>修改佣金费率：</td>
					<td><input class="easyui-numberbox" name="commissionRate"
						id="commissionRate" data-options="prompt:'请输入佣金费率，例如0.50',precision:2,min:0.01,max:1" /></td>
					<td><a href="javascript:transfer()" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-search">修改</a></td>
				</tr>
			</table>
		</div>
	</div>



</body>
</html>