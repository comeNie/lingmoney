<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户设置</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/user/reconciliation.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
	<script charset="utf-8" src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
	<script charset="utf-8" src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>


</head>
<body>

		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>账户姓名：</td>
	            			<td><input class="easyui-textbox" name="userName" id="userName" /></td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/user/reconciliation/list',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:100">id</th>
			            	<th data-options="field:'userName',width:100">用户名称</th>
			            	<th data-options="field:'pFrozenMoney',width:100">平台账号</th>
			            	<th data-options="field:'yFrozenMoney',width:100" >易宝账户</th>
			            	<th data-options="field:'pBalance',width:100">平台账户余额</th>
			            	<th data-options="field:'yBalance',width:100" >易宝账户余额</th>
			            	<th data-options="field:'reconDate',width:100" formatter="formatTimer">处理时间</th>
			            	<th data-options="field:'status',width:100" formatter="formatStatus">阅读状态</th>
			            	
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		
		
		<!-- 用户信息表 -->
		 <div id="DivEdit" class="easyui-dialog" style="width:760px;height:650px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'添加产品分类',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 <form id="ffEdit" method="post" novalidate="novalidate">
			
			 	<table id="adGrid" class="view">
			 		<tr>
			 			<th>
                           <label for="name">id：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="id" id="aid" style="width:200px" readonly></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="name">用户id：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="uId" id="auId" style="width:200px" readonly></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="name">消息主题：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="topic" id="atopic" style="width:200px" readonly></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="name">发送人：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="sender" id="asender" style="width:200px" readonly></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="name">发送时间：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="time" id="atime" style="width:200px" readonly></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="name">阅读状态：</label>
                        </th>
                        <td>
                         <select name="status" id="astatus"  class="easyui-combogrid" style="width:200px" readonly>
                        <option value="0">未读</option>
                        <option value="1">已读</option>
                        </select>
                        
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="name">消息内容：</label>
                        </th>
                        <td>
                        <textarea name="content" id="acontent" style="width:300px;height:200px;visibility:hidden;"></textarea>
                        </td>
			 		</tr>
			 	
			 		<tr> 
                        <td colspan="2" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
                        </td>
                    </tr>
			 	</table>
			 </form>
		</div>
		<!-- 用户信息表 -->
		
		<!-- 编辑的弹出层 -->
		<!--  <div id="DivEdit" class="easyui-dialog" style="width:400px;height:300px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'修改产品分类',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
			<form id="ffEdit" method="post" novalidate="novalidate">
				<table id="tblEdit" class="view">
			 		<tr>
			 			<th>
                           <label for="name">id：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="id" id="eid" style="width:200px" readonly></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="name">登录账户：</label>
                        </th>
                        <td>
                        	<input  class="easyui-textbox" name="loginAccount" id="eloginAccount" style="width:200px" readonly></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="name">登录密码：</label>
                        </th>
                        <td>
                        	<input  class="easyui-textbox" name="loginPsw" id="eloginPsw" style="width:200px" readonly></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="name">联系电话：</label>
                        </th>
                        <td>
                        <input  class="easyui-textbox" name="telephone" id="etelephone" style="width:200px" readonly></input>	
                        </td>
			 		</tr>
			 	
			 		<tr>
			 			<th>
                           <label for="name">最后一次登录时间：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="lastLogin" id="elastLogin" style="width:200px" data-options="required:true"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="name">终端类型：</label>
                        </th>
                        <td>
                        <select class="easyui-combobox" name="type" id="etype" style="width:200px" data-options="required:true">
                        <option value="0">0</option>
                        <option value="1">1</option>
                           </select>
                        	
                        </td>
			 		</tr>
			 		<tr>
                        <td colspan="2" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
                        </td>
                    </tr>
                 </table>
            </form>
        </div>  -->
</body>
</html>