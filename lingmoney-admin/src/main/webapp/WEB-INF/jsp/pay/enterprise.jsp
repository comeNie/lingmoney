<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>企业用户设置</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/pay/enterprise.js"></script>
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>企业名称：</td>
	            			<td><input class="easyui-textbox"  id="senterpriseName" /></td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/pay/listEnterprise',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'platformuserno',width:50">企业用户的id</th>
			            	<th data-options="field:'enterprisename',width:100">企业名称</th>
			            	<th data-options="field:'banklicense',width:100">开户银行许可证</th>
			            	<th data-options="field:'legal',width:100">法人姓名</th>
			            	<th data-options="field:'memberclasstype',width:100,formatter:function(value){
				            		if(value=='ENTERPRISE'){
					            		return '企业借款人';
					            	}else{
					            		return '担保公司';
					            	}
					            }" 
			            		>借款人类型</th>
			            	<th data-options="field:'status',width:100,formatter:function(value){
				            		if(value=='0'){
					            		return '添加成功';
					            	}
					            	if(value=='1'){
					            		return '提交成功';
					            	}
					            	if(value=='2'){
					            		return '注册成功';
					            	}
					            	if(value=='3'){
					            		return '注册失败';
					            	}
					            }" 
			            		>借款人类型</th>
			            	<th data-options="field:'bankNo',width:100">绑定的银行卡</th>
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		<!-- 添加的弹出层 -->
		<div id="DivAddView" class="easyui-dialog" style="width:760px;height:350px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'添加企业账户',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 	<form action="/rest/pay/eRegister" id="addForm" method="post" target="_blank">
				 	<table id="tblAdd" class="view">
				 		<tr>
							<th><label>商户编号:</label></th>
							<td><input class="easyui-textbox" data-options="required:true,readonly:true" name="platformNo" value="21140011137"></td>
							<th><label>企业名称:</label></th>
							<td><input class="easyui-textbox" data-options="required:true" name="enterpriseName"></td>
						</tr>
						<tr>
							<th><label>开户银行许可证:</label></th>
							<td><input class="easyui-textbox" data-options="required:true" name="bankLicense" ></td>
							<th><label>组织机构代码:</label></th>
							<td><input class="easyui-textbox" data-options="required:true" name="orgNo"></td>
						</tr>
						<tr>
							<th><label>营业执照编号:</label></th>
							<td><input class="easyui-textbox" data-options="required:true" name="businessLicense" ></td>
							<th><label>税务登记号:</label></th>
							<td><input class="easyui-textbox" data-options="required:true" name="taxNo"></td>
						</tr>
						<tr>
							<th><label>法人姓名:</label></th>
							<td><input class="easyui-textbox" data-options="required:true" name="legal" ></td>
							<th><label>法人身份证号:</label></th>
							<td><input class="easyui-textbox" data-options="required:true" name="legalIdNo"></td>
						</tr>
						<tr>
							<th><label>企业联系人:</label></th>
							<td><input class="easyui-textbox" data-options="required:true" name="contact" ></td>
							<th><label>联系人手机号:</label></th>
							<td><input class="easyui-textbox" data-options="required:true" name="contactPhone"></td>
						</tr>
						<tr>
							<th><label>联系人邮箱:</label></th>
							<td><input class="easyui-textbox" data-options="required:true" name="email" ></td>
							<th><label>借款人类型:</label></th>
							<td>
								<select class="easyui-combobox"  data-options="required:true,editable:false" name="memberClassType">
									<option value="ENTERPRISE" selected="selected">企业借款人</option>
									<option value="GUARANTEE_CORP">担保公司</option>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="text-align:right; padding-top:10px">
	                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a>
	                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivAddView').dialog('close')">关闭</a>
	                        </td>
						</tr>
					</table>
				</form>
		</div>
		<!-- 详细信息的弹出层 -->
		 <div id="DivInfoView" class="easyui-dialog" style="width:560px;height:230px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'查看企业账户信息',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 	<table id="tblAdd" class="view">
			 		<tr>
			 			<th>
                           <label >账户余额：</label>
                        </th>
                        <td>
                        	<label id="vbalance"></label>
                        </td>
                        <th>
                           <label >可用余额：</label>
                        </th>
                        <td>
                        	<label id="vavailableAmount"></label>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label >冻结金额：</label>
                        </th>
                        <td>
                        	<label id="vfreezeAmount"></label>
                        </td>
                        <th>
                           <label >银行卡号：</label>
                        </th>
                        <td>
                        	<label id="vcardNo"></label>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>激活状态</th>
			 			<td colspan="3"><label id="vactiveStatus"></label></td>
			 		</tr>
			 		<tr>
                        <td colspan="4" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivInfoView').dialog('close');">关闭</a>
                        </td>
                    </tr>
			 	</table>
			</div>
			<!-- 提现的form -->
			<div id="DivTixianView" class="easyui-dialog" style="width:350px;height:180px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'企业用户提现',iconCls: 'icon-add',buttons: '#dlg-buttons'">
				<form id="tixianForm" method="post" action="/rest/pay/toWithdraw" target="_blank">
					<input type="hidden" id="tplatformUserNo" name="platformUserNo" >
				 	<table id="tblAdd" class="view">
					 	<tr>
				 			<th>
	                           <label for="name">提现金额：</label>
	                        </th>
	                        <td>
	                        	 <input class="easyui-numberbox" name="money" id="tmoney" data-options="precision:2,required:true">
	                        </td>
				 		</tr>
				 		<tr>
	                        <td colspan="2" style="text-align:right; padding-top:10px">
	                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="doTixian()">确定</a>
	                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivTixianView').dialog('close')">关闭</a>
	                        </td>
	                    </tr>
				 	</table>
			 	</form>
			</div>
			<!-- 充值的form -->
			<div id="DivChongzhiView" class="easyui-dialog" style="width:350px;height:180px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'企业用户充值',iconCls: 'icon-add',buttons: '#dlg-buttons'">
				<form id="chongzhiForm" method="post" action="/rest/pay/toRecharge" target="_blank">
					<input type="hidden" id="cplatformUserNo" name="platformUserNo" >
				 	<table id="tblAdd" class="view">
					 	<tr>
				 			<th>
	                           <label for="name">充值金额：</label>
	                        </th>
	                        <td>
	                        	 <input class="easyui-numberbox" name="money" id="cmoney" data-options="precision:2,required:true">
	                        </td>
				 		</tr>
				 		<tr>
	                        <td colspan="2" style="text-align:right; padding-top:10px">
	                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="doChongzhi()">确定</a>
	                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivChongzhiView').dialog('close')">关闭</a>
	                        </td>
	                    </tr>
				 	</table>
			 	</form>
			</div>
			
			<!-- 绑卡的form -->
			<div style="display:none">
				<form id="bindBank" target="_blank" action="/rest/pay/bindUserCard" method="post" >
					<input type="hidden" name="platformUserNo" id="bplatformUserNo">
				</form>
			</div>
</body>
</html>