<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>中奖礼品兑换详情</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/festival/giftDetail.js"></script>
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
			style="padding: 5px; height: 120px">
			<form id="searchForm">
				<table>
					<tr>
						<td>用户姓名：</td>
						<td><input class="easyui-textbox" name="uName" id="uName"
							style="width: 180px"
							data-options="prompt:'请输入用户姓名',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#uName').textbox('setValue', '');
											}
										}]" /></td>
						<td>用户手机号：</td>
						<td><input class="easyui-textbox" name="uTel" id="uTel"
							style="width: 180px"
							data-options="prompt:'请输入用户手机号',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#uTel').textbox('setValue', '');
											}
										}]" /></td>
						<td>礼品名称：</td>
						<td><input class="easyui-textbox" name="gName" id="gName"
							style="width: 180px"
							data-options="prompt:'请输入礼品名称',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#gName').textbox('setValue', '');
											}
										}]" /></td>
					</tr>
					<tr>
						<td>状态：</td>
						<td><select class="easyui-combobox" name="state" id="state"
							style="width: 180px;"
							data-options="value:'',prompt:'请选择状态',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#state').combobox('setValue', '');
											}
										}]">
								<option value="0">未生效</option>
								<option value="1">冻结中</option>
								<option value="2">已发货</option>
								<option value="3">未发货</option>
								<option value="4">已收货</option>
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
				data-options="rownumbers:true,singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/festival/giftDetail/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100">id</th>
						<th data-options="field:'uId',width:100">用户Id</th>
						<th data-options="field:'uName',width:100">用户姓名</th>
						<th data-options="field:'uTel',width:100">用户手机号</th>
						<th data-options="field:'gName',width:100">礼品名称</th>
						<th data-options="field:'lingbaoGiftId',width:100">领宝礼品id</th>
						<th
							data-options="field:'state',width:100,
			            					formatter:function(value){
										        if(value==0)
										                return '<font color=#3B3B3B>未生效</font>';
										        if(value==1)
										                return '<font color=#66CD00>冻结中</font>';
										        if(value==2)
										                return '<font color=#141414>已发货</font>';
										        if(value==3)
										                return '<font color=#0000FF>未发货</font>';
										        if(value==4)
										                return '<font color=#EE0000>已收货</font>';
										        }">状态</th>
						<th data-options="field:'createTime',width:100"
							formatter="formatTimer">创建时间</th>
						<th data-options="field:'sendTime',width:100"
							formatter="formatTimer">发货时间</th>
						<th data-options="field:'receiveTime',width:100"
							formatter="formatTimer">收货时间</th>
						<th data-options="field:'expressNumber',width:100">快递单号</th>
						<th data-options="field:'expressCompany',width:100">快递公司</th>
						<th data-options="field:'rechargeAccount',width:100">充值卡号</th>
						<th
							data-options="field:'rechargeCode',width:100,formatter:function(value){
																			if(value)
														               		 return '******';
														      			  }">充值卡密码</th>
						<th data-options="field:'mobile',width:100">充值手机号码</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>


	<!-- 发货 -->
	<div id="DivDelivery" class="easyui-dialog"
		style="width: 440px; height: 320px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'发货',iconCls: 'icon-ok',buttons: '#dlg-buttons'">
		<form id="ffDelivery" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" />
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
					<th><label>充值卡号</label></th>
					<td><input class="easyui-textbox" name="rechargeAccount"
						style="width: 200px"></td>
				</tr>
				<tr>
					<th><label>充值卡密码</label></th>
					<td><input class="easyui-textbox" name="rechargeCode"
						style="width: 200px"></td>
				</tr>
				<tr>
					<th><label>充值手机号码</label></th>
					<td><input class="easyui-textbox" name="mobile"
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