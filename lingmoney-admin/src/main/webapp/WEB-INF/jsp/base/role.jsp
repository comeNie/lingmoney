<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>权限设置</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/base/role.js"></script>
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>权限名称：</td>
	            			<td><input class="easyui-textbox" name="roleName" id="sroleName" /></td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/base/role/list',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:50">id</th>
			            	<th data-options="field:'roleName',width:200">权限名称</th>
			            	<th data-options="field:'status',width:200" formatter="formatStatus">状态</th>
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		<!-- 添加的弹出层 -->
		 <div id="DivAdd" class="easyui-dialog" style="width:350px;height:150px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'添加权限',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 <form id="ffAdd" method="post" novalidate="novalidate">
			 	<table id="tblAdd" class="view">
			 		<tr>
			 			<th>
                           <label for="roleName">权限名称：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="roleName" id="aRoleName" style="width:200px" data-options="required:true"></input>
                        </td>
			 		</tr>
			 		<tr>
                        <td colspan="2" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
                        </td>
                    </tr>
			 	</table>
			 </form>
		</div>
		<!-- 添加的弹出层 -->
		<!-- 编辑的弹出层 -->
		<div id="DivEdit" class="easyui-dialog" style="width:350px;height:150px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'修改权限信息',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
			<form id="ffEdit" method="post" novalidate="novalidate">
				<input type="hidden" name="id" />
				<table id="tblEdit" class="view">
			 		<tr>
			 			<th>
                           <label for="roleName">权限名称：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" data-options="required:true" name="roleName" id="aRoleName" style="width:200px" ></input>
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
        </div>
        <!-- 分配权限 -->
        <div id="DivSet"  style="width:350px;height:450px;padding:10px 20px">
            <ul id="menuTree" >
            </ul>
            <form id="ffAddRole" method="post" novalidate="novalidate">
            	<input type="hidden" name="roleId" id="setRoleId"/>
            	<input type="hidden" name="menuIds" id="setMenuIds" />
            </form>
		</div>
</body>
</html>