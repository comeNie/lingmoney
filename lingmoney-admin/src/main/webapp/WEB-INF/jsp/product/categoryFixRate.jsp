<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>固定产品分类收益表</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/product/categoryFixRate.js"></script>
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>产品名称：</td>
	            			<td>
		            			<select class="easyui-combogrid" id="spId" name="pId" style="width:200px" data-options="
							            panelWidth: 200,
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/product/product/listFixArea',
							            method: 'get',
							            onShowPanel:function(){
							            	$(this).combogrid('grid').datagrid('reload');
							            },
							            columns: [[
							                {field:'id',title:'产品id',width:30},
							                {field:'name',title:'产品名称',width:50}
							            ]],
							            fitColumns: true,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#spId').combogrid('setValue', '');
											}
										}]
							        ">
							    </select>
						    </td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果(只支持搜索查询)" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/categoryFixRate/list',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:100">id</th>
			            	<th data-options="field:'rTime',width:100">时间(天)</th>
			            	<th data-options="field:'rate',width:100">汇率</th>
			            	<th data-options="field:'pName',width:100">产品名称</th>
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		<!-- 添加的弹出层 -->
		 <div id="DivAdd" class="easyui-dialog" style="width:350px;height:250px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'添加固定产品分类收益',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 <form id="ffAdd" method="post" novalidate="novalidate">
			    <input type="hidden"  name="pName" id="apName">
			 	<input type="hidden"  name="pId" id="apId">
			 	<table id="tblAdd" class="view">			 	
			 		<tr>			 		
			 			<th>
                           <label for="rTime">汇率的时长：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" name="rTime" id="arTime" style="width:200px" data-options="required:true,min:1"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="rate">时间对应的汇率：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" name="rate" id="arate" style="width:200px" data-options="required:true,min:0,max:1,precision:4"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="pcId">产品名称：</label>
                        </th>
                        <td>         
                        	<select class="easyui-combogrid" name="pcId" id="apcId" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/product/product/listFixArea',
						            method: 'get',
						            onShowPanel:function(){
						            	$(this).combogrid('grid').datagrid('reload');
						            },
						            columns: [[
						                {field:'id',title:'产品id',width:30},
						                {field:'name',title:'产品名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						    </select>
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
		<!-- 编辑的弹出层 -->
		<div id="DivEdit" class="easyui-dialog" style="width:350px;height:250px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'修改固定产品分类收益',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
			<form id="ffEdit" method="post" novalidate="novalidate">
				<input type="hidden" name="id">
			 	<input type="hidden"  name="pName" id="epName">
			 	<input type="hidden"  name="pId" id="epId">
			 	<table id="tblEdit" class="view">
					<tr>
			 			<th>
                           <label for="rTime">汇率的时间：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" name="rTime" id="erTime" style="width:200px" data-options="required:true,min:0"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="rate">时间对应的汇率：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" name="rate" id="erate" style="width:200px" data-options="required:true,min:0,max:1,precision:4"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="pcId">产品名称：</label>
                        </th>
                        <td>
                        	<select class="easyui-combogrid" name="pcId" id="epcId" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/product/product/listFixArea',
						            method: 'get',
						            onShowPanel:function(){
						            	$(this).combogrid('grid').datagrid('reload');
						            },
						            columns: [[
						                {field:'id',title:'产品id',width:30},
						                {field:'name',title:'产品名称',width:50}
						            ]],
						            fitColumns: true
						        ">
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
        </div>
</body>
</html>