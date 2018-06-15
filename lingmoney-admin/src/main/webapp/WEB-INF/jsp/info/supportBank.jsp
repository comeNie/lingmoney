<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>支持银行设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/info/supportBank.js"></script>
<script type="text/javascript">
	$(function() {
		$("#DivAdd input[name=bankName]").change(function() {
			checkBankName();
		});

		$("#DivAdd input[name=bankCode]").change(function() {
			checkBankCode();
		});
		$("#DivAdd input[name=bankShort]").change(function() {
			checkBankShort();
		});

		$("#DivEdit input[name=bankName]").change(function() {
			checkBankNameEdit();
		});

		$("#DivEdit input[name=bankCode]").change(function() {
			checkBankCodeEdit();
		});
		$("#DivEdit input[name=bankShort]").change(function() {
			checkBankShortEdit();
		});
	});
</script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 100px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>银行名称：</td>
						<td><input class="easyui-textbox" name="bankName"
							id="bankName" style="width: 200px"
							data-options="prompt:'请输入银行名称',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#bankName').textbox('setValue', '');
											}
										}]" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
						<td><a href="javascript:addbankMaintain()"
							class="easyui-linkbutton" id="addbankMaintain"
							iconcls="icon-edit">添加银行支付修改</a></td>
						<td><a href="javascript:delbankMaintain()"
							class="easyui-linkbutton" id="delbankMaintain" iconcls="icon-del">删除银行支付修改</a></td>
					</tr>
					<tr>
						<td>禁止购买提示信息：</td>
						<td><input class="easyui-textbox" name="takeHeartMsg"
							id="takeHeartMsg" /></td>
						<td><a href="javascript:takeHeartAllowBuy(1)"
							class="easyui-linkbutton" id="addbankMaintain"
							iconcls="icon-edit">禁止购买随心取</a></td>
						<td><a href="javascript:takeHeartAllowBuy(0)"
							class="easyui-linkbutton" id="addbankMaintain"
							iconcls="icon-edit">开通购买随心取</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/info/supportBank/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'bankCode',width:50">代码</th>
						<th data-options="field:'bankName',width:100">银行名称</th>
						<th data-options="field:'bankShort',width:80">银行简称</th>
						<th
							data-options="field:'bankType',width:150,formatter:
						function(value, row, rowIndex) {
						if(row.bankType == 0) {return '快捷';}
						else if(row.bankType == 1) {return '网银';}
						else {return '全部';}}">支付方式</th>
						<th data-options="field:'upperLimit',width:150">限额(笔/天/月)</th>
						<th data-options="field:'bankNo',width:50">编码</th>
						<th data-options="field:'bankOrder',width:50">排序</th>
						<th data-options="field:'bankLogo',width:300">银行Logo</th>
						<th data-options="field:'colorValue',width:100">手机端色值</th>
						<th data-options="field:'background',width:300">手机端背景图</th>
						<th data-options="field:'ebankUpperlimit',width:300">网银支付限额</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 500px; height: 450px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加支付银行',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" />
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">银行名称:</label></th>
					<td><input class="easyui-text" name="bankName"
						data-options="prompt:'请输入银行名称',required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行代码:</label></th>
					<td><input class="easyui-text" name="bankCode"
						data-options="prompt:'请输入银行代码',required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行简称:</label></th>
					<td><input class="easyui-text" name="bankShort"
						data-options="min:0,prompt:'请填写银行简称',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">支付类型:</label></th>
					<td><select class="easyui-combogrid" id="supportBankTypeId"
						name="supportBankTypeId" style="width: 200px"
						data-options="
						            panelWidth:'auto',
						            panelHeight:'auto',
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'bankType',
						            url:'<%=request.getContextPath()%>/resource/json/supportBankType.json',
						            columns: [[
						                {field:'bankType',title:'选择下面的支付类型',width:100,align:'center',
      									styler:function(value,row,index){return 'font-weitght:bold;color:green;';}}
						            ]],
						            fitColumns: true
						        ">
					</select> <input type="hidden" id="bankType" name="bankType" /></td>
				</tr>
				<tr>
					<th><label for="name">银行排序:</label></th>
					<td><input class="easyui-text" name="bankOrder"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行上限:</label></th>
					<td><input class="easyui-text" name="upperLimit"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行Logo:</label></th>
					<td><input class="easyui-filebox" name="bankImg" id="bankImg"
						style="width: 200px"
						data-options="prompt:'请选择图片',required:true,buttonText:'上传Logo'"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行编号:</label></th>
					<td><input class="easyui-text" name="bankNo"
						data-options="min:0,prompt:'请填写银行编号',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">手机端银行卡色值:</label></th>
					<td><input class="easyui-text" name="colorValue"
						data-options="min:0,prompt:'请填写色值',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">手机端银行卡背景图:</label></th>
					<td><input class="easyui-text" name="background"
						data-options="min:0,prompt:'请填写背景图',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">网银支付限额:</label></th>
					<td><input class="easyui-text" name="ebankUpperlimit"
						data-options="min:0,prompt:'请填写网银支付限额',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<td><span class="bankCodeSpan"></span></td>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close');">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 编辑的弹出层 -->

	<div id="DivEdit" class="easyui-dialog"
		style="width: 500px; height: 450px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改银行快捷支付充值限额',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id"> <input type="hidden"
				name="bankOrder">
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">银行名称:</label></th>
					<td><input class="easyui-text" name="bankName"
						data-options="prompt:'请输入银行名称',required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行代码:</label></th>
					<td><input class="easyui-text" name="bankCode"
						data-options="min:0,prompt:'请填写银行代码',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行简称:</label></th>
					<td><input class="easyui-text" name="bankShort"
						data-options="min:0,prompt:'请填写银行简称',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">支付类型:</label></th>
					<td><select class="easyui-combogrid"
						id="supportBankTypeUpdateId" name="supportBankTypeUpdateId"
						style="width: 200px"
						data-options="
						            panelWidth: 300,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'bankType',
						            url:'<%=request.getContextPath()%>/resource/json/supportBankType.json',
						            columns: [[
						                {field:'bankType',title:'类型',width:100}
						            ]],
						            fitColumns: true
						        ">
					</select> <input type="hidden" id="bankType" name="bankType" value="1000" />
					</td>
				</tr>
				<tr>
					<th><label for="name">银行上限:</label></th>
					<td><input class="easyui-text" name="upperLimit"
						data-options="min:0,prompt:'请填写银行上限',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行logo:</label></th>
					<td><input class="easyui-text" name="bankLogo"
						data-options="min:0,prompt:'请填写Logo地址',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行编号:</label></th>
					<td><input class="easyui-text" name="bankNo"
						data-options="min:0,prompt:'请填写银行编号',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">手机端银行卡色值:</label></th>
					<td><input class="easyui-text" name="colorValue"
						data-options="min:0,prompt:'请填写色值',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">手机端银行卡背景图:</label></th>
					<td><input class="easyui-text" name="background"
						data-options="min:0,prompt:'请填写背景图',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">网银支付限额:</label></th>
					<td><input class="easyui-text" name="ebankUpperlimit"
						data-options="min:0,prompt:'请填写网银支付限额',required:true"
						style="width: 200px"></input></td>
				</tr>
				<!-- <tr>
					<th><label for="name">银行logo:</label></th>
					<td><img id="bankLogo" width="70px" height="70" /><br /> <input
						class="easyui-filebox" name="bankImg" id="bankImg"
						style="width: 200px" data-options="prompt:'请选择图片'"></input></td>
				</tr> -->
				<tr>
					<td><span class="bankCodeSpanEdit"></span></td>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!--设置排序对话框 -->
	<div id="DivOrder" class="easyui-dialog"
		style="width: 600px; height: 400px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'设置排序',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffOrder" method="post">
			<input type="hidden" name="id">
			<table id="tblOrder" class="view">
				<tr>
					<th><label for="name">银行名称:</label></th>
					<td><input class="easyui-text" name="bankName" disabled="true"
						style="width: 400px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行代码:</label></th>
					<td><input class="easyui-text" name="bankCode" disabled="true"
						style="width: 400px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行简称:</label></th>
					<td><input class="easyui-text" name="bankShort"
						disabled="true" style="width: 400px"></input></td>
				</tr>
				<tr>
					<th><label for="name">支付类型:</label></th>
					<td><input class="easyui-text" name="bankType" disabled="true"
						style="width: 400px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行排序:</label></th>
					<td><input class="easyui-text" id="bankOrderShow"
						disabled="true" style="width: 400px"></input></td>
				</tr>
				<tr>
					<th><label for="name">更新银行排序:</label></th>
					<td><input class="easyui-text" name="bankOrder"
						style="width: 400px"></input></td>
				</tr>
				<tr>
					<th><label for="name">银行logo:</label></th>
					<td><input class="easyui-text" name="bankLogo" id="bankLogo"
						disabled="true" style="width: 400px"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnOrderOK" iconcls="icon-ok" onclick="updateOrder()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivOrder').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>