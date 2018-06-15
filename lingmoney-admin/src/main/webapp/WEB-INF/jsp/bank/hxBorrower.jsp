<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>华兴银行借款人列表</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="/resource/js/bank/hxBorrower.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>借款人姓名：</td>
						<td><input class="easyui-textbox" style="width: 180px"
							id="bwAcname"
							data-options="prompt:'请输入借款人姓名',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#bwAcname').textbox('setValue', '');
											}
										}]" /></td>
						<td>借款人手机号：</td>
						<td><input class="easyui-textbox" style="width: 180px"
							id="bwMobile"
							data-options="prompt:'请输入借款人手机号',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#bwMobile').textbox('setValue', '');
											}
										}]" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/bank/hxBorrower/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:150">id</th>
						<th data-options="field:'bwAcname',width:150">借款人姓名</th>
						<th data-options="field:'bwIdtype',width:150"
							formatter="formatIdtype">借款人证件类型</th>
						<th data-options="field:'bwIdno',width:150">借款人证件号码</th>
						<th data-options="field:'mobile',width:150">借款人手机号</th>
						<th data-options="field:'bwAcno',width:150">借款人账号</th>
						<th data-options="field:'bwAcbankid',width:150">借款人账号所属行号</th>
						<th data-options="field:'bwAcbankname',width:150">借款人账号所属行名</th>
						<th data-options="field:'status',width:150"
							formatter="formatStatus">状态</th>
						<!-- <th data-options="field:'bwAge',width:150">借款人年龄</th>
						<th data-options="field:'bwMarriage',width:150">借款人婚姻状态</th>
						<th data-options="field:'bwSex',width:150">借款人性别</th>
						<th data-options="field:'bwCreditRecord',width:150">借款人信用记录</th>
						<th data-options="field:'bwOrginPlace',width:150">借款人籍贯</th> -->
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加和修改的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 440px; height: 250px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'维护借款人',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">借款人证件类型(身份证为1010)：</label></th>
					<td>
						<select id="bwIdType" style="width: 200px" class="easyui-combobox easyui-validatebox" data-options="required:true" name="bwIdtype">
							<option value="1010">身份证</option>
							<option value="2020">组织机构代码证</option>
						</select>	
					</td>
				</tr>
				<tr>
					<th><label for="name">选择借款人：</label></th>
					<td>
						<select id="accId" name="accId" style="width: 200px"> </select>
					</td>
				</tr>
				<tr>
					<th><label for="name">借款人账号所属行号(12位联行号)：</label></th>
					<td><input class="easyui-textbox" name="bwAcbankid"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">借款人账号所属行名：</label></th>
					<td><input class="easyui-textbox" name="bwAcbankname"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<!-- <tr>
					<th><label for="name">借款人年龄：</label></th>
					<td><input class="easyui-textbox" name="bwAge"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">借款人婚姻状态：</label></th>
					<td><input class="easyui-textbox" name="bwMarriage"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">借款人性别：</label></th>
					<td><input class="easyui-textbox" name="bwSex"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">借款人信用记录：</label></th>
					<td><input class="easyui-textbox" name="bwCreditRecord"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">借款人籍贯：</label></th>
					<td><input class="easyui-textbox" name="bwOrginPlace"
						style="width: 200px" data-options="required:false"></input></td>
				</tr> -->
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="saveAndUpdate()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 普通添加查询 -->
	<div id="tbs" style="padding: 2px 5px;">
		E账户姓名：<input class="easyui-textbox" style="width: 80px"
			id="acName"
			data-options="prompt:'E账户姓名',
		            icons: [{
						iconCls:'icon-remove',
						handler: function(e){
							$('#acName').textbox('setValue', '');
						}
					}]">
		手机号： <input class="easyui-textbox" style="width: 80px"
			id="mobile"
			data-options="prompt:'手机号',
		            icons: [{
						iconCls:'icon-remove',
						handler: function(e){
							$('#mobile').textbox('setValue', '');
						}
					}]">
		<a href="javascript:searchName()" class="easyui-linkbutton"
			iconCls="icon-search">查询</a>
	</div> 
	
	<!-- 企业账户查询 -->
	<div id="enterpriseSerachTab" style="padding: 2px 5px;">
		企业名：<input class="easyui-textbox" style="width: 80px" id="companyName">
		银行账号： <input class="easyui-textbox" style="width: 80px" id="companyBankNo">
		<a href="javascript:enterpriseSearch()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	</div> 
	
	<form id="accountManageForm" name="accountManageSubBankForm" style="display: none"
        target="_blank" method="post" action=''>
        <input id="accountManageRequestData" name="RequestData" value='' /> 
        <input id="accountManageTransCode" name="transCode" value='' />
    </form>
	
</body>
</html>