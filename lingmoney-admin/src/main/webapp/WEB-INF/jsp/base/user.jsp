<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>后台用户管理</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/base/user.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>用户名称：</td>
	            			<td><input class="easyui-textbox" name="name" id="sname" /></td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/base/user/list',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:50">id</th>
			            	<th data-options="field:'name',width:200">用户名称</th>
			            	<th data-options="field:'loginName',width:200">登陆名称</th>
			            	<th data-options="field:'roleName',width:200">所在权限组</th>
			            	<th data-options="field:'lastTime',width:200" formatter="formatTimer">最后登陆时间</th>
			            	<th data-options="field:'status',width:200" formatter="formatStatus">状态</th>
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		<!-- 添加的弹出层 -->
		 <div id="DivAdd" class="easyui-dialog" style="width:650px;height:200px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'添加用户',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 <form id="ffAdd" method="post" novalidate="novalidate">
			 	<input type="hidden" name="roleName" id="aroleName">
			 	<table id="tblAdd" class="view">
			 		<tr>
			 			<th>
                           <label for="aname">用户名称：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="name" id="aname" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="aloginName">登陆名称：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="loginName" id="aloginName" style="width:200px" data-options="required:true"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="aloginPsw">登陆密码：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="loginPsw" id="aloginPsw" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="aloginName">权限组：</label>
                        </th>
                        <td>
                        	  <select class="easyui-combogrid" id="aroleId" name="roleId" style="width:200px" data-options="
						            panelWidth: 300,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'roleName',
						            url: '/rest/base/role/list?limit=100',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'id',width:80},
						                {field:'roleName',title:'权限名称',width:120}
						            ]],
						            fitColumns: true
						        ">
						    </select>
                        </td>
			 		</tr>
			 		<tr>
                        <td colspan="4" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
                        </td>
                    </tr>
			 	</table>
			 </form>
		</div>
		<!-- 添加的弹出层 -->
		<!-- 编辑的弹出层 -->
		<div id="DivEdit" class="easyui-dialog" style="width:650px;height:200px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'修改用户信息',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
			<form id="ffEdit" method="post" novalidate="novalidate">
				<input type="hidden" name="id" />
				<input type="hidden" name="loginPsw"  id="eloginPsw" value="" />
				<input type="hidden" name="roleName" id="eroleName"/>
				<table id="tblEdit" class="view">
			 		<tr>
			 			<th>
                           <label for="ename">用户名称：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="name" id="ename" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="aloginName">登陆名称：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="loginName" id="eloginName" style="width:200px" data-options="required:true"></input>
                        </td>
			 		</tr>
			 		<tr>
                        <th>
                           <label for="aloginName">权限组：</label>
                        </th>
                        <td colspan="3">
                        	  <select class="easyui-combogrid" id="eroleId" name="roleId" style="width:200px" data-options="
						            panelWidth: 300,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'roleName',
						            url: '/rest/base/role/list?limit=100',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'id',width:80},
						                {field:'roleName',title:'权限名称',width:120}
						            ]],
						            fitColumns: true
						        ">
						    </select>
                        </td>
			 		</tr>
			 		<tr>
                        <td colspan="4" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
                        </td>
                    </tr>
                 </table>
            </form>
        </div>
</body>
</html>