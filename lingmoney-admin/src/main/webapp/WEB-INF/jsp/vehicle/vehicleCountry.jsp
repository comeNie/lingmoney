<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>车辆国别管理</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/vehicle/vehicleCountry.js"></script>
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>制造国：</td>
	            			<td><input class="easyui-textbox" name="country" id="scountry" /></td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/vecount/countlist',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:30">id</th>
			            	<th data-options="field:'country',width:100">制造国</th>
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		<!-- 添加的弹出层 -->
		 <div id="DivAdd" class="easyui-dialog" style="width:650px;height:200px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'添加国籍',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 <form id="ffAdd" method="post" novalidate="novalidate">
			 	<input type="hidden" name="roleName" id="aroleName">
			 	<table id="tblAdd" class="view">
			 		<tr>
			 			<th>
                           <label for="acountry">制造国(如中国、德国等)：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="country" id="acountry" style="width:200px" data-options="required:true"></input>
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
		<!-- 编辑(修改)的弹出层 -->
		<div id="DivEdit" class="easyui-dialog" style="width:650px;height:200px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'修改车辆国籍',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
			<form id="ffEdit" method="post" novalidate="novalidate">
				<input type="hidden" name="id" />
				<input type="hidden" name="loginPsw"  id="eloginPsw" value="" />
				<input type="hidden" name="roleName" id="eroleName"/>
				<table id="tblEdit" class="view">
			 		<tr>
			 			<th>
                           <label for="ecountry">制造国(如中国、德国等)：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="country" id="ecountry" style="width:200px" data-options="required:true"></input>
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