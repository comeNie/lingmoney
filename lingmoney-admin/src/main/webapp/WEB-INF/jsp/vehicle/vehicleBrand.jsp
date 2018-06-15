<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>车辆品牌管理</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/vehicle/vehicleBrand.js"></script>
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>品牌首字母：</td>
	            			<td><input class="easyui-textbox" name="brandInitial" id="sbrandInitial" /></td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            			<!-- <td><a href="javascript:add()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-add" >添加</a></td> -->
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto"> 
	        	<!-- toolbar:toolbar在js中定义，表示菜单 -->      
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/vehicle/brandList',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:30">id</th>
			            	<th data-options="field:'brandInitial',width:100">品牌首字母</th>
			            	<th data-options="field:'tierOneBrand',width:100">一级品牌</th>
			            	<th data-options="field:'tierTwoBrand',width:100">二级品牌</th>
			            	<th data-options="field:'tierThreeBrand',width:100">三级品牌</th>
			            <!-- <th data-options="field:'lastTime',width:200" formatter="formatTimer">最后登陆时间</th> -->	
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		<!-- 添加的弹出层 -->
		 <div id="DivAdd" class="easyui-dialog" style="width:650px;height:200px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'添加车辆品牌',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 <form id="ffAdd" method="post" novalidate="novalidate">
			 	<table id="tblAdd" class="view">
			 		<tr>
			 			<th>
                           <label for="abrandInitial">品牌首字母：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="brandInitial" id="abrandInitial" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="atierOneBrand">一级品牌：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="tierOneBrand" id="atierOneBrand" style="width:200px" data-options="required:true"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="atierTwoBrand">二级品牌：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="tierTwoBrand" id="atierTwoBrand" style="width:200px" data-options="required:true"></input>
                        </td>
                       <th>
                           <label for="atierThreeBrand">三级品牌：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="tierThreeBrand" id="atierThreeBrand" style="width:200px" data-options="required:true"></input>
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
		<!-- 修改的弹出层 -->
		<div id="DivEdit" class="easyui-dialog" style="width:650px;height:200px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'修改车辆品牌信息',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
			<form id="ffEdit" method="post" novalidate="novalidate">
				<input type="hidden" name="id" />
				
				<table id="tblEdit" class="view">
			 		<tr>
			 			<th>
                           <label for="ebrandInitial">品牌首字母：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="brandInitial" id="ebrandInitial" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="etierOneBrand">一级品牌：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="tierOneBrand" id="etierOneBrand" style="width:200px" data-options="required:true"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="etierTwoBrand">二级品牌：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="tierTwoBrand" id="etierTwoBrand" style="width:200px" data-options="required:true"></input>
                        </td>
                       <th>
                           <label for="etierThreeBrand">三级品牌：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="tierThreeBrand" id="etierThreeBrand" style="width:200px" data-options="required:true"></input>
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