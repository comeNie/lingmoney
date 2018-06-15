<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>礼品兑换</title>
<jsp:include page="/common/head.jsp"></jsp:include>
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
			style="padding: 5px; height: 80px">
			<div id="searchDiv" style="padding:2px 5px;">
			                       用户名：<input class="easyui-textbox" style="width:110px" id="searchName">
			       	  手机号： <input class="easyui-textbox" style="width:110px" id="searchTel">
			        <a href="javascript:searchUserCombo()" class="easyui-linkbutton" iconCls="icon-search" >查询</a>
			    </div>
			<form id="searchFrm">
				<table>
					<tr>
						<td>用户：</td>
						<td>
							<select class="easyui-combogrid" id="approvalStatus"
								name="approvalStatus" style="width: 200px"
								data-options="
											editable:false,
								            panelWidth: 450,
								            idField: 'id',
								            loadMsg:'正在加载,请稍后...',
								            textField: 'telephone',
								            url: '/rest/user/users/list.html',
								            toolbar:'#searchDiv',
								            method: 'get',
								            columns: [[
								                {field:'id',title:'id',width:30},
								                {field:'loginAccount',title:'用户名',width:50},
								                {field:'telephone',title:'手机号',width:50}
								            ]],
								            fitColumns: true,
								            icons: [{
												iconCls:'icon-remove',
												handler: function(e){
													$('#approvalStatus').combogrid('setValue', '');
												}
											}],
											pagination:true,
								        ">
							</select>
						</td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" id="getNewGrid"
				data-options="rownumbers:false,singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/activityProduct/getNewActivityInfo',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'uId',width:70">用户ID</th>
						<th data-options="field:'name',width:70">姓名</th>
						<th data-options="field:'tel',width:70">手机号</th>
						<th data-options="field:'buyMoney',width:70">购买金额</th>
						<th data-options="field:'referralId',width:70">推荐人ID</th>
						<th data-options="field:'province',width:70">省</th>
						<th data-options="field:'city',width:70">市</th>
						<th data-options="field:'town',width:90">区/县</th>
						<th data-options="field:'address',width:90">详细地址</th>
					</tr>
				</thead>
			</table>
			
			<div id="editDiv" class="easyui-dialog" title="My Dialog" style="width:500px;height:200px;"   
			        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
			    <form id="editForm" method="post">
			    	<table>
			    		<tr>
			    			<td>
			    			礼品名称：
			    			</td>
			    			<td>
			    			<input name="giftName" class="easyui-textbox" >
			    			</td>
			    			<td>
			    			快递公司：
			    			</td>
			    			<td>
			    			<input name="expressCompany" class="easyui-textbox" >
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>
			    			快递单号：
			    			</td>
			    			<td>
			    			<input name="expressNumber" class="easyui-textbox" >
			    			</td>
			    			<td>
			    			发货时间：
			    			</td>
			    			<td>
			    			<input name="sendTime" class="easyui-datetimebox" >
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>状态：</td>
			    			<td>
								<select name="status" class="easyui-combobox">
								  <option value ="0">兑换成功</option>
								  <option value ="1">已发货</option>
								  <option value="2">已收货</option>
								</select>
							</td>
			    			<td></td>
			    			<td></td>
			    		</tr>
			    		
			    		<tr>
			    			<td>
			    			活动名称：
			    			</td>
			    			<td>
			    			<input name="giftName" class="easyui-textbox" >
			    			</td>
			    			<td>
			    			数量：
			    			</td>
			    			<td>
			    			<input name="expressCompany" class="easyui-textbox" >
			    			</td>
			    		</tr>
			    		
			    	</table>
			    	<input name="uId" type="hidden">
			    	<a href="#" onclick="saveForm()" class="easyui-linkbutton" iconCls="icon-save">Refresh</a>
			    	<a href="#" onclick="closeForm()" class="easyui-linkbutton" iconCls="icon-cancel">Cancel</a>
			    </form>   
			</div>  
		</div>
	</div>
	
	<script type="text/javascript">
		var toolbar = [ '-', {
			text : '编辑',
			iconCls : 'icon-edit',
			handler : function() {
				edit();
			}
		}, '-'];
	
		function search(){
			$('#getNewGrid').datagrid('reload',{
				page:1,
				uid:$("#approvalStatus").combogrid("getValue")
			});
		}
		
		function searchUserCombo(){
			$('#approvalStatus').combogrid('grid').datagrid('reload',{
				page:1,
				loginAccount:$('#searchName').val(),
				telephone:$('#searchTel').val()
			});
		}
		
		function edit(){
			var row = $('#getNewGrid').datagrid('getSelected');
			$("#editDiv").dialog("open");
			$("#editForm").form('load',row);
		}
		
		function saveForm(){
			$('#editForm').form('submit', {    
				url:'/rest/product/activityProduct/saveGetNewGiftInfo',
			    onSubmit: function(){    
			        // do some check    
			        // return false to prevent submit;    
			    },    
			    success:function(data){    
			        alert(data)    
			    }    
			});  
			/* $.ajax({
				url:'/rest/product/activityProduct/saveGetNewGiftInfo',
				type:'post',
				data:$("#editForm").serialize(), 
				data:{"giftName":"123",
					"expressCompany":"123",
					"expressNumber":"123",
					"sendTime":"2017-04-19",
					"status":0,
					"uId":161},
				success:function(data){
					alert(data);
				}
			})  */
		}
		
		function closeForm(){
			$("#editForm").form('clear');
			$("#editDiv").dialog("close");
		}
	</script>
</body>
</html>