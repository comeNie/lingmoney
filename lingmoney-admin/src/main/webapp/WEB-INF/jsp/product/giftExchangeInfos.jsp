<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新吉粉活动礼品兑换</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/giftExchangeInfos.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<style type="text/css">
.address {
	Font-size: 16px;
	Font-family: 微软雅黑;
}
</style>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 130px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>推荐人id：</td>
						<td><input class="easyui-textbox" name="referralId"
							id="referralId" style="width: 180px"
							data-options="prompt:'请输入推荐人id',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#referralId').textbox('setValue', '');
											}
										}]" /></td>
						<td>推荐人手机号：</td>
						<td><input class="easyui-textbox" name="telephone"
							id="telephone" style="width: 180px"
							data-options="prompt:'请输入推荐人手机号',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#telephone').textbox('setValue', '');
											}
										}]" /></td>
						<td>推荐人姓名：</td>
						<td><input class="easyui-textbox" name="name" id="name"
							style="width: 180px"
							data-options="prompt:'请输入推荐人姓名',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#name').textbox('setValue', '');
											}
										}]" /></td>
					</tr>
					<tr>
						<td>推荐人推荐码：</td>
						<td><input class="easyui-textbox" name="referralCode"
							id="referralCode" style="width: 180px"
							data-options="prompt:'请输入推荐人推荐码',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#referralCode').textbox('setValue', '');
											}
										}]" /></td>
						<td>礼品状态：</td>
						<td><select class="easyui-combogrid" id="status"
							name="status" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择礼品状态',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#status').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td>活动名称：</td>
						<td><input class="easyui-textbox" name="activityName"
							id="activityName" style="width: 180px"
							data-options="prompt:'请输入活动名称',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#activityName').textbox('setValue', '');
											}
										}]" /></td>
					</tr>
					<tr>
						
						<td>快递单号：</td>
						<td><input class="easyui-textbox" id="expressNumber"
							name="expressNumber" style="width: 180px"
							data-options="prompt:'请输入快递单号',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#expressNumber').textbox('setValue', '');
											}
										}]" /></td>
						<td>快递公司：</td>
						<td><select class="easyui-combogrid" id="expressCompany"
							name="expressCompany" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择快递公司',
							            idField: 'id',
							            editable:true,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#expressCompany').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="pageList:[10,100,1000],rownumbers:true,singleSelect:true,toolbar:toolbar,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/giftExchangeInfo/list?category=1',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100,hidden:true">id</th>
						<!-- <th field="ck" checkbox="true"></th> -->
						<th data-options="field:'referralId',width:100">推荐人id</th>
						<th data-options="field:'telephone',width:100">推荐人手机号</th>
						<th data-options="field:'name',width:100">推荐人姓名</th>
						<th data-options="field:'referralCode',width:100">推荐人推荐码</th>
						<th data-options="field:'employeeName',width:100">上级推荐人姓名</th>
						<th data-options="field:'employeeID',width:100">上级推荐人员工号</th>
						<th data-options="field:'department',width:100">上级推荐人部门</th>
						<th data-options="field:'employeeNameUp',width:100">上上级推荐人姓名</th>
						<th data-options="field:'employeeIDUp',width:100">上上级推荐人员工号</th>
						<th data-options="field:'departmentUp',width:100">上上级推荐人部门</th>
						<th data-options="field:'rechargeAccount',width:100">充值卡号</th>
						<th data-options="field:'rechargeCode',width:100" formatter="formatRechargeCode">充值卡密码</th>
						<th data-options="field:'mobile',width:100">充值手机号码</th>
						<th data-options="field:'province',width:100">推荐人所在省</th>
						<th data-options="field:'city',width:100">推荐人所在市</th>
						<th data-options="field:'town',width:100">推荐人所在区/县</th>
						<th data-options="field:'address',width:100">推荐人详细地址</th>
						<th data-options="field:'uId',width:100">新客户id</th>
						<th data-options="field:'newName',width:100">新客户姓名</th>
						<th data-options="field:'newTelephone',width:100">新客户手机号</th>
						<th data-options="field:'newRegDate',width:100" formatter="formatTimers">新客户注册日期</th>
						<th data-options="field:'newIdCard',width:150">新客户身份证号</th>
						<th data-options="field:'newAge',width:100">新客户年龄</th>
						<th data-options="field:'buyMoney',width:100">新客户首投金额</th>
						<th data-options="field:'threeBuyMoney',width:200">新客户首投金额(3个月及以上产品)</th>
						<th data-options="field:'productName',width:150">新客户首投产品名称</th>
						<th data-options="field:'buyDt',width:100" formatter="formatTimers">新客户首投时间</th>
						<th data-options="field:'activityName',width:100">活动名称</th>
						<th data-options="field:'giftName',width:100">礼品名称</th>
						<th data-options="field:'num',width:100">礼品数量</th>
						<th data-options="field:'status',width:100"
							formatter="formatStatus">礼品状态</th>
						<th data-options="field:'type',width:100" formatter="formatType">礼品类型</th>
						<th data-options="field:'expressNumber',width:150">快递单号</th>
						<th data-options="field:'expressCompany',width:150">快递公司</th>
						<th data-options="field:'exchangeTime',width:150"
							formatter="formatTimer">兑换时间</th>
						<th data-options="field:'sendTime',width:150"
							formatter="formatTimer">发货时间</th>
						<th data-options="field:'receiveTime',width:150"
							formatter="formatTimer">收货时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>


	<!-- 发货 -->
	<div id="DivDelivery" class="easyui-dialog"
		style="width: 440px; height: 250px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'发货',iconCls: 'icon-ok',buttons: '#dlg-buttons'">
		<form id="ffDelivery" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" /><input type="hidden" name="status"
				id="deliveryStatus" />
			<table id="tblOrder" class="view">
				<tr>
					<th><label for="expressCompany">快递公司：</label></th>
					<td><select class="easyui-combogrid" name="expressCompany"
						style="width: 200px"
						data-options="
							            panelWidth: 200,
							            idField: 'name',
							            required:false,
							            editable:true,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/resource/json/express_company.json',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'名称',width:50}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="expressNumber">快递单号：</label></th>
					<td><input class="easyui-textbox" name="expressNumber"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="expressNumber">发货时间：</label></th>
					<td><input class="easyui-datetimebox" name="sendTime"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label id="label"></label></th>
					<td><input class="easyui-combobox" name="giftName"
						id="giftName" style="width: 200px"></td>
				</tr>
				<tr class="recharge">
					<th><label>充值卡号</label></th>
					<td><input class="easyui-textbox" name="rechargeAccount"
						id="rechargeAccount" style="width: 200px"></td>
				</tr>
				<tr class="recharge">
					<th><label>充值卡密码</label></th>
					<td><input class="easyui-textbox" name="rechargeCode"
						id="rechargeCode" style="width: 200px"></td>
				</tr>
				<tr class="recharge">
					<th><label>充值手机号码</label></th>
					<td><input class="easyui-textbox" name="mobile" id="mobile"
						style="width: 200px"></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="processingDelivery()">确定</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivDelivery').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>