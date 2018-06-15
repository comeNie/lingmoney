<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的领地礼品兑换</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/gift/lingbaoExchangeInfo.js"></script>
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
			style="padding: 5px; height: 150px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>用户姓名：</td>
						<td><input class="easyui-textbox" name="uname" id="uname"
							style="width: 180px"
							data-options="prompt:'请输入用户姓名',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#uname').textbox('setValue', '');
											}
										}]" /></td>
						<td>用户手机号：</td>
						<td><input class="easyui-textbox" name="uPhoneNumber"
							id="uPhoneNumber" style="width: 180px"
							data-options="prompt:'请输入用户手机号',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#uPhoneNumber').textbox('setValue', '');
											}
										}]" /></td>
						<td>礼品名称：</td>
						<td><input class="easyui-textbox" name="giftName"
							id="giftName" style="width: 180px"
							data-options="prompt:'请输入礼品 名称',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#giftName').textbox('setValue', '');
											}
										}]" /></td>
					</tr>
					<tr>
						<td>礼品类型：</td>
						<td><select class="easyui-combogrid" id="gifttype"
							name="gifttype" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择礼品类型',
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
												$('#gifttype').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td>活动名称：</td>
						<td><select class="easyui-combogrid" id="activityId"
							name="activityId" style="width: 180px"
							data-options="
							            panelWidth: 400,
							            prompt:'请选择领宝活动',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            pagination:true,
							            textField: 'name',
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/gift/lingbaoLotteryType/list')
									    },
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#activityId').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td>兑换类型：</td>
						<td><select class="easyui-combogrid" id="type" name="type"
							style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择兑换类型',
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
												$('#type').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
					</tr>
					<tr>
						<td>流水号：</td>
						<td><input class="easyui-textbox" name="serialNumber"
							id="serialNumbers" style="width: 180px"
							data-options="prompt:'请输入流水号',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#serialNumbers').textbox('setValue', '');
											}
										}]" /></td>
						<td>快递单号：</td>
						<td><input class="easyui-textbox" name="expressNumber"
							id="expressNumbers" style="width: 180px"
							data-options="prompt:'请输入快递单号',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#expressNumbers').textbox('setValue', '');
											}
										}]" /></td>
						<td>快递公司：</td>
						<td><select class="easyui-combogrid" id="expressCompanys"
							name="expressCompany" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择快递公司',
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
												$('#expressCompanys').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
					</tr>
					<tr>
						<td>兑换状态：</td>
						<td><select class="easyui-combogrid" id="status"
							name="status" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择兑换状态',
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
						<td>礼品领取方式：</td>
						<td><select class="easyui-combogrid" id="receiveWay"
							name="receiveWay" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择礼品领取方式',
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
												$('#receiveWay').combogrid('setValue', '');
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
				data-options="rownumbers:false,singleSelect:true,toolbar:toolbar,fitColumns:false,pagination:true,pageSize:20,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/gift/lingbaoExchangeInfo/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50,hidden:false">id</th>
						<th data-options="field:'giftId',width:30,hidden:true">giftId</th>
						<th data-options="field:'uId',width:30,hidden:true">uId</th>
						<th data-options="field:'uname',width:70">用户姓名</th>
						<th data-options="field:'uPhoneNumber',width:90">用户手机号</th>
						<th data-options="field:'giftName',width:90,formatter:giftNameFormatter">礼品名称</th>
						<th data-options="field:'num',width:50">数量</th>
						<th data-options="field:'integral',width:80">消耗积分</th>
						<th data-options="field:'gifttype',width:80"
							formatter="formatGifttype">礼品类型</th>
						<th data-options="field:'activityName',width:70">活动名称</th>
						<th data-options="field:'type',width:80" formatter="formatType">兑换类型</th>
						<th data-options="field:'status',width:80"
							formatter="formatStatus">兑换状态</th>
						<th data-options="field:'receiveWay',width:80"
							formatter="formatReceiveWay">礼品领取方式</th>
						<th data-options="field:'addressId',width:80"
							formatter="formatAddress">地址详情</th>
						<th data-options="field:'serialNumber',width:130">流水号</th>
						<th data-options="field:'expressNumber',width:130">快递单号</th>
						<th data-options="field:'expressCompany',width:130">快递公司</th>
						<th data-options="field:'exchangeTime',width:130"
							formatter="formatTimer">兑换时间</th>
						<th data-options="field:'sendTime',width:130"
							formatter="formatTimer">发货时间</th>
						<th data-options="field:'receiveTime',width:130"
							formatter="formatTimer">收货时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<!-- 用户地址 -->
	<div id="addressWin" class="easyui-window" title="地址详情"
		data-options="closed:true"
		style="width: 450px; height: 120px; padding: 5px;">
		<div class="address">
			收货人姓名:&nbsp;&nbsp;<span id="name"></span>
		</div>
		<div class="address">
			手机号码:&nbsp;&nbsp;<span id="phone"></span>
		</div>
		<div class="address">
			收货地址:&nbsp;&nbsp;<span id="address"></span>
		</div>
	</div>

	<!-- 发货 -->
	<div id="DivDelivery" class="easyui-dialog"
		style="width: 440px; height: 250px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'发货',iconCls: 'icon-ok',buttons: '#dlg-buttons'">
		<form id="ffDelivery" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" /> <input type="hidden" name="giftId" />
			<input type="hidden" name="uId" /> <input type="hidden"
				name="status" id="deliveryStatus" />
			<table id="tblOrder" class="view">
				<tr id="expressCompanyTr">
					<th><label for="expressCompany">快递公司：</label></th>
					<td><select class="easyui-combogrid" name="expressCompany"
						id="expressCompany" style="width: 200px"
						data-options="
							            panelWidth: 200,
							            idField: 'name',
							            required:true,
							            editable:false,
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
				<tr id="expressNumberTr">
					<th><label for="expressNumber">快递单号：</label></th>
					<td><input class="easyui-textbox" name="expressNumber"
						id="expressNumber" style="width: 200px"
						data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="expressNumber">发货时间：</label></th>
					<td><input class="easyui-datetimebox" name="sendTime"
						id="sendTime" style="width: 200px" data-options="required:true"></input></td>
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