<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="/resource/js/user/setting.js"></script>
<script type="text/javascript" src="/resource/js/DatePattern.js"></script>

</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 175px">
			<form id="searchForm" method="post">
				<table>
					<tr>
						<td>用户id：</td>
						<td><input class="easyui-textbox" name="id" id="id"
							style="width: 200px"
							data-options="prompt:'请输入用户id',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#id').textbox('setValue', '');
											}
										}]" /></td>
						<td>用户姓名：</td>
						<td><input class="easyui-textbox" name="name" id="name"
							style="width: 200px;"
							data-options="prompt:'请输入用户姓名',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#name').textbox('setValue', '');
											}
										}]" /></td>
						<td>注册手机号：</td>
						<td><input class="easyui-textbox" name="telephone"
							id="telephone" style="width: 200px;"
							data-options="prompt:'请输入注册手机号',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#telephone').textbox('setValue', '');
											}
										}]" /></td>
					   <td>注册时间：</td>
                       <td>
                          <input class="easyui-datebox" name="regDateStr" style="width: 95px;" />
                          -
                          <input class="easyui-datebox" name="regDateStrEnd" style="width: 95px;" />
                       </td>
					</tr>
					<tr>
						<td>注册推荐码：</td>
						<td><input class="easyui-textbox" name="referralCode"
							id="referralCode" style="width: 200px"
							data-options="prompt:'请输入注册推荐码',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#referralCode').textbox('setValue', '');
											}
										}]" /></td>
						<td>是否绑卡激活：</td>
						<td><select class="easyui-combobox" name="isBindCard"
							id="isBindCard" style="width: 200px;"
							data-options="value:'',prompt:'请选择是否绑卡激活',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#isBindCard').combobox('setValue', '');
											}
										}]">
								<option value="0">否</option>
								<option value="1">是</option>
						</select></td>
						<td>是否买过产品：</td>
						<td><select class="easyui-combobox" name="isFinancialProduct"
							id="isFinancialProduct" style="width: 200px;"
							data-options="value:'',prompt:'请选择是否买过产品',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#isFinancialProduct').combobox('setValue', '');
											}
										}]">
								<option value="0">否</option>
								<option value="1">是</option>
						</select></td>
						<td>注册渠道：</td>
						<td><input class="easyui-combobox" name="registChannel"
							id="registChannel" style="width: 200px;"
							data-options="prompt:'请输入注册渠道',
										method:'get',  
								        mode:'remote',  
								        url:'/rest/user/users/queryRegistChannel',
								        valueField: 'str',
										textField: 'str',
										onShowPanel:function(){
							            	$(this).combobox('reload');
							            },
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#registChannel').combobox('setValue', '');
											}
										}]" /></td>
					</tr>
					<tr>
						<td>是否持有产品：</td>
						<td><select class="easyui-combobox" name="isHoldProduct"
							id="isHoldProduct" style="width: 200px;"
							data-options="value:'',prompt:'请选择是否持有产品',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#isHoldProduct').combobox('setValue', '');
											}
										}]">
								<option value="0">否</option>
								<option value="1">是</option>
						</select></td>
						<td>理财金额区间：</td>
						<td><input class="easyui-numberbox" name="financialMin"
							id="financialMin" style="width: 100px;"
							data-options="prompt:'最低理财金额',min:0,precision:2,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#financialMin').textbox('setValue', '');
											}
										}]" /><input
							class="easyui-numberbox" name="financialMax" id="financialMax"
							style="width: 100px;"
							data-options="prompt:'最高理财金额',min:0,precision:2,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#financialMax').textbox('setValue', '');
											}
										}]" /></td>
						<td>持有金额区间：</td>
						<td><input class="easyui-numberbox" name="holdMin"
							id="holdMin" style="width: 100px;"
							data-options="prompt:'最低持有金额',min:0,precision:2,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#holdMin').textbox('setValue', '');
											}
										}]" /><input
							class="easyui-numberbox" name="holdMax" id="holdMax"
							style="width: 100px;"
							data-options="prompt:'最高持有金额',min:0,precision:2,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#holdMax').textbox('setValue', '');
											}
										}]" /></td>
						<td>平台类型：</td>
                        <td><select class="easyui-combobox" name="platformType"
                            id="platformType" style="width: 200px;"
                            data-options="value:'',prompt:'请选择平台类型',panelHeight:'auto',editable:false,icons: [{
                                            iconCls:'icon-remove',
                                            handler: function(e){
                                                $('#platformType').combobox('setValue', '');
                                            }
                                        }]">
                                <option value="0">注册用户</option>
                                <option value="1">导入用户</option>
                        </select></td>
						<td colspan="2" align="right"><input type="hidden" name="showMgr" id="showMgr"
							value="false"><a href="javascript:swithMgr()"
							class="easyui-linkbutton" id="btnAddOK" iconcls="icon-undo">理财经理</a>
							
							<a
							href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
					<tr>
						<td class="showMgrTd" style="display: none;">是否有理财经理：</td>
						<td class="showMgrTd" style="display: none;"><select
							class="easyui-combobox" name="isHaveManager" id="isHaveManager"
							style="width: 200px;"
							data-options="value:'',prompt:'请选择是否有理财经理',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#isHaveManager').combobox('setValue', '');
											}
										}]">
								<option value="0">否</option>
								<option value="1">是</option>
						</select></td>
						<td class="showMgrTd" style="display: none;">理财经理编号：</td>
						<td class="showMgrTd" style="display: none;"><input
							class="easyui-textbox" name="managerId" id="managerId"
							style="width: 200px;"
							data-options="prompt:'请输入理财经理编号',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#managerId').textbox('setValue', '');
											}
										}]" /></td>
						<td class="showMgrTd" style="display: none;">理财经理姓名：</td>
						<td class="showMgrTd" style="display: none;"><input
							class="easyui-textbox" name="managerName" id="managerName"
							style="width: 200px"
							data-options="prompt:'请输入理财经理姓名',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#managerName').textbox('setValue', '');
											}
										}]" /></td>

					</tr>
					<tr>
						<td class="showMgrTd" style="display: none;">理财经理公司：</td>
						<td class="showMgrTd" style="display: none;"><input
							class="easyui-combobox" name="managerCom" id="managerCom"
							style="width: 200px;"
							data-options="prompt:'请输入理财经理公司',
										method:'get',  
								        mode:'remote',  
								        url:'/rest/user/users/queryManagerCom',
								        valueField: 'str',
										textField: 'str',
										onShowPanel:function(){
							            	$(this).combobox('reload');
							            },
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#managerCom').combobox('setValue', '');
											}
										}]" /></td>
						<td class="showMgrTd" style="display: none;">理财经理部门：</td>
						<td class="showMgrTd" style="display: none;"><input
							class="easyui-combobox" name="managerDept" id="managerDept"
							style="width: 200px;"
							data-options="prompt:'请输入理财经理部门',
										method:'get',  
								        mode:'remote',  
								        url:'/rest/user/users/queryManagerDept',
								        valueField: 'str',
										textField: 'str',
										onShowPanel:function(){
							            	$(this).combobox('reload');
							            },
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#managerDept').combobox('setValue', '');
											}
										}]" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table id="adGrid" data-options="fit:true,border:false"></table>
		</div>
	</div>

	<!-- 查看购买记录-->
	<div id="DivSetting" class="easyui-dialog"
		style="width: 1080px; height: 400px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'查看购买记录',iconCls: 'icon-setting',buttons: '#dlg-buttons'">
		<table id="settingGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolb" style="padding: 5px; height: auto; float: right;">
		<select class="easyui-combobox" name="tStatus" id="tStatus"
			style="width: 150px;"
			data-options="value:'',prompt:'请选择订单状态',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#tStatus').combobox('setValue', '');
											}
										}],onChange:function(newValue, oldValue){
											seeBuyRecords(newValue);
										}">
			<option value="0">待支付</option>
			<option value="1">持有中</option>
			<option value="2">回款中</option>
			<option value="3">已回款</option>
			<option value="5">支付失败</option>
			<option value="12">支付中</option>
			<option value="18">订单失效</option>
		</select>
	</div>
	<!-- 用户银行卡 -->
	<div id="bank" class="easyui-dialog"
		style="width: 400px; height: 340px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'用户银行卡',iconCls: 'icon-organ',buttons: '#dlg-buttons'">
		<form id="Addbank" method="post" novalidate="novalidate">

			<table id="AddbankCard" class="view">
				<tr>
					<th><label for="name">id：</label></th>
					<td><input class="easyui-textbox" name="id" id="aid"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">用户id：</label></th>
					<td><input class="easyui-textbox" name="uId" id="auId"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">银行名称：</label></th>
					<td><input class="easyui-textbox" name="name" id="aname"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">银行卡号：</label></th>
					<td><input class="easyui-textbox" name="number" id="anumber"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">开户行所在省：</label></th>
					<td><input class="easyui-textbox" name="province"
						id="aprovince" style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">开户行所在市：</label></th>
					<td><input class="easyui-textbox" name="city" id="acity"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">开户行所在区(县)：</label></th>
					<td><input class="easyui-textbox" name="town" id="atown"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">开户行：</label></th>
					<td><input class="easyui-textbox" name="bank" id="abank"
						style="width: 200px" readonly></input></td>
				</tr>

			</table>
		</form>
	</div>
	<!-- 用户银行卡 -->

	<!-- 账户表 -->
	<div id="account" class="easyui-dialog"
		style="width: 400px; height: 440px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'账户信息',iconCls: 'icon-view',buttons: '#dlg-buttons'">
		<form id="addAccount" method="post" novalidate="novalidate">

			<table id="Add" class="view">
				<tr>
					<th><label for="name">id：</label></th>
					<td><input class="easyui-textbox" name="id" id="aid"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">用户id：</label></th>
					<td><input class="easyui-textbox" name="uId" id="auId"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">资产总额：</label></th>
					<td><input class="easyui-numberbox" name="totalAsset"
						id="atotalAsset" style="width: 200px"
						data-options="precision:2,groupSeparator:',',decimalSeparator:'.',prefix:'￥'"
						readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">可用余额：</label></th>
					<td><input class="easyui-numberbox" name="balance"
						id="abalance" style="width: 200px" readonly
						data-options="precision:2,groupSeparator:',',decimalSeparator:'.',prefix:'￥'"></input></td>
				</tr>
				<tr>
					<th><label for="name">领宝个数：</label></th>
					<td><input class="easyui-numberbox" name="countLingbao"
						id="acountLingbao" style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">冻结金额：</label></th>
					<td><input class="easyui-numberbox" name="frozenMoney"
						id="afrozenMoney" style="width: 200px" readonly
						data-options="precision:2,groupSeparator:',',decimalSeparator:'.',prefix:'￥'"></input></td>
				</tr>
				<tr>
					<th><label for="name">理财总额：</label></th>
					<td><input class="easyui-numberbox" name="totalFinance"
						id="atotalFinance" style="width: 200px" readonly
						data-options="precision:2,groupSeparator:',',decimalSeparator:'.',prefix:'￥'"></input></td>
				</tr>
				<tr>
					<th><label for="name">冻结金额：</label></th>
					<td><input class="easyui-numberbox" name="frozenMoney"
						id="afrozenMoney" style="width: 200px" readonly
						data-options="precision:2,groupSeparator:',',decimalSeparator:'.',prefix:'￥'"></input></td>
				</tr>
				<tr>
					<th><label for="name">待收本息：</label></th>
					<td><input class="easyui-numberbox" name="principal"
						id="aprincipal" style="width: 200px" readonly
						data-options="precision:2,groupSeparator:',',decimalSeparator:'.',prefix:'￥'"></input></td>
				</tr>
				<tr>
					<th><label for="name">已收获益：</label></th>
					<td><input class="easyui-numberbox" name="income" id="aincome"
						style="width: 200px" readonly
						data-options="precision:2,groupSeparator:',',decimalSeparator:'.',prefix:'￥'"></input></td>
				</tr>
				<tr>
					<th><label for="name">版本号：</label></th>
					<td><input class="easyui-numberbox" name="version"
						id="aversion" style="width: 200px" readonly></input></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 账户表 -->

	<!-- 账户属性表 -->
	<div id="properties" class="easyui-dialog"
		style="width: 800px; height: 470px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'用户属性',iconCls: 'icon-book',buttons: '#dlg-buttons'">
		<form id="propertiesAdd" method="post" novalidate="novalidate">

			<table id="proAdd" class="view">
				<tr>
					<th><label for="name">id：</label></th>
					<td><input class="easyui-textbox" name="id" id="aid"
						style="width: 200px" readonly></input></td>
					<th><label for="name">用户id：</label></th>
					<td><input class="easyui-textbox" name="uId" id="auId"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">昵称：</label></th>
					<td><input class="easyui-textbox" name="nickName"
						id="anickName" style="width: 200px" readonly></input></td>
					<th><label for="name">真实姓名：</label></th>
					<td><input class="easyui-textbox" name="name" id="aname"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">性别：</label></th>
					<td><input class="easyui-textbox" name="sex" id="asex"
						style="width: 200px" readonly></input></td>
					<th><label for="name">头像：</label></th>
					<td><input class="easyui-textbox" name="picture" id="apicture"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">所在省份：</label></th>
					<td><input class="easyui-textbox" name="province"
						id="aprovince" style="width: 200px" readonly></input></td>
					<th><label for="name">所在市：</label></th>
					<td><input class="easyui-textbox" name="city" id="acity"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">所在区(县)：</label></th>
					<td><input class="easyui-textbox" name="town" id="atown"
						style="width: 200px" readonly></input></td>
					<th><label for="name">教育程度：</label></th>
					<td><input class="easyui-textbox" name="education"
						id="aeducation" style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">详细地址：</label></th>

					<td colspan="3"><input class="easyui-textbox" name="address"
						id="address" style="width: 550px" readonly></input></td>
				</tr>

				<tr>
					<th><label for="name">从事职业：</label></th>
					<td><input class="easyui-textbox" name="job" id="ajob"
						style="width: 200px" readonly></input></td>
					<th><label for="name">绑定邮箱：</label></th>
					<td><input class="easyui-textbox" name="email" id="aemail"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">注册日期：</label></th>
					<td><input class="easyui-datebox" name="regDate" id="aregDate"
						style="width: 200px" readonly></input></td>
					<th><label for="name">是否通过实名认证：</label></th>
					<td><input class="easyui-textbox" name="certification"
						id="acertification" style="width: 200px" readonly
						formatter="formatBank"></input></td>
				</tr>
				<tr>
					<th><label for="name">是否绑定银行卡：</label></th>
					<td><input class="easyui-textbox" name="bank" id="abank"
						style="width: 200px" readonly formatter="formatBank"></input></td>
					<th><label for="name">身份证号：</label></th>
					<td><input class="easyui-textbox" name="idCard" id="aidCard"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">推荐码：</label></th>
					<td><input class="easyui-textbox" name="referralCode"
						id="areferralCode" style="width: 200px" readonly></input></td>
					<th><label for="name">推荐人：</label></th>
					<td><input class="easyui-textbox" name="referralId"
						id="areferralId" style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">购买总额：</label></th>
					<td><input class="easyui-numberbox" name="buyTotal"
						id="abuyTotal" style="width: 200px"
						data-options="precision:2,groupSeparator:',',decimalSeparator:'.',prefix:'￥'"
						readonly></input></td>
					<th><label for="name">会员等级：</label></th>
					<td><input class="easyui-textbox" name="level" id="alevel"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">二维码路径：</label></th>
					<td><input class="easyui-textbox" name="codePath"
						id="acodePath" style="width: 200px" readonly></input></td>
					<th><label for="name">是否自动定投：</label></th>
					<td><input class="easyui-textbox" name="autoPay" id="aautoPay"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="name">是否是第一次购买：</label></th>
					<td><input class="easyui-textbox" name="firstBuy"
						id="afirstBuy" style="width: 200px" readonly></input></td>
					<th><label for="name">微信：</label></th>
					<td><input class="easyui-textbox" name="wechat" id="awechat"
						style="width: 200px" readonly></input></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 用户属性表 -->
	<!-- 批量注册 -->
	<div id="DivRegist" class="easyui-dialog"
		style="width: 400px; height: 140px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'批量注册',iconCls: 'icon-batch-add',buttons: '#dlg-buttons'">
		<form id="ffRegist" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">选择txt文件：</label></th>
					<td><input class="easyui-filebox" name="txtFile" id="txtFile"
						style="width: 200px" data-options="prompt:'选择txt文件',required:true"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="upload()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivRegist').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 批量注册 -->

	<!-- 编辑的弹出层 -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 400px; height: 400px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改产品分类',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">分类名称：</label></th>
					<td><input class="easyui-textbox" name="name" id="ename"
						style="width: 200px" readonly></input></td>
				</tr>
				<tr>
					<th><label for="gId">分类组：</label></th>
					<td><select class="easyui-combogrid" name="gId" id="egId"
						style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/product/param/list/product_group',
						            method: 'get',
						            onShowPanel:function(){
						            	$(this).combogrid('grid').datagrid('reload');
						            },
						            columns: [[
						                {field:'id',title:'分类组id',width:30},
						                {field:'name',title:'分类组名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="type">类型：</label></th>
					<td><select class="easyui-combogrid" name="type" id="etype"
						style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'typeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'typeName',
						            url: '/resource/json/category_type.json',
						            method: 'get',
						            onChange:function(newValue,oldValue){
						            	showAndHideFixType('efixTypeTr','efixType',newValue,oldValue,true);
						            },
						            columns: [[
						                {field:'typeId',title:'类型id',width:30},
						                {field:'typeName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="status">状态：</label></th>
					<td><select class="easyui-combogrid" name="status"
						id="estatus" style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'statusId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'statusName',
						            url: '/resource/json/category_status.json',
						            method: 'get',
						            columns: [[
						                {field:'statusId',title:'id',width:30},
						                {field:'statusName',title:'名称',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr id="efixTypeTr" style="display: none;">
					<th><label for="type">固定子类型：</label></th>
					<td><select class="easyui-combobox" name="fixType"
						id="efixType">
							<option value="1">固定不变</option>
							<option value="2">区间</option>
					</select></td>
				</tr>
				<tr>
					<th><label for="description">描述信息：</label></th>
					<td><input class="easyui-textbox" name="description"
						id="edescription" style="width: 200px; height: 100px"
						data-options="required:true,multiline:true"></input></td>
				</tr>
				<tr>
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
	
	<!-- 赠送加息券弹框 S -->
	<div id="giveRedPacketDiv" class="easyui-dialog"
		style="width: 350px; height: 300px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'赠送加息券',iconCls: 'icon-add', buttons: [{text:'确定', handler: confirmGiveRedPacket}, {text:'取消', handler: cancelGiveRedPacket}]">
		
		请选择加息券:<select id="redPacketId" class="easyui-combogrid" name="dept" style="width:200px;"   
				        data-options="    
				            panelWidth:300,    
				            idField:'id',    
				            textField:'hrpNumber',    
				            url:'/rest/hxRedPacket/manualRedPacketList',    
				            columns:[[    
				                {field:'hrpType',title:'名称',width:60},    
				                {field:'hrpNumber',title:'加息率',width:60}, 
				                {field:'hrpRemarks',title:'描述信息'}   
				            ]]    
				        ">
				    </select>  
				    
		<p>赠送人员列表：</p>
		<table id="giveRedPacketList" style="width:250px;overflow-x: scroll;" cellpadding="5px;">
			
		</table>
	</div>
	<!-- 赠送加息券弹框 E -->
	
</body>
</html>