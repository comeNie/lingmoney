<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>浮动类产品净值表</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/product/floatNetValue.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	        	 <div id="tb" style="padding:2px 5px;">
			                       产品名称：<input class="easyui-textbox" style="width:110px" id="searchName">
			       	审批状态： <select class="easyui-combobox" id="searchApproval" panelHeight="auto" style="width:100px">
			       		<option value="">全部</option>
			            <option value="0">初始化</option>
			            <option value="1">提交</option>
			            <option value="2">审核通过</option>
			        </select>
			        <a href="javascript:searchFloat()" class="easyui-linkbutton" iconCls="icon-search" >查询</a>
			    </div>
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>浮动类产品：</td>
	            			<td><select class="easyui-combogrid" id="product" name="product" style="width:200px" data-options="
							            panelWidth: 480,
							            toolbar:'#tb',
							            idField: 'id',
							            pagination:true,
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/product/product/listFloat',
							            method: 'post',
							           	onShowPanel:function(){
						            		$(this).combogrid('grid').datagrid('reload');
						            	},
							            columns: [[
							                {field:'id',title:'产品id',width:30},
							                {field:'name',title:'产品名称',width:50}
							            ]],
							            onChange:function(){
							            	search();
							            },
							            fitColumns: true
							        ">
							    </select></td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果(只支持搜索查询)" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/floatNetValue/list',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:100">id</th>
			            	<th data-options="field:'netValueDt',width:100" formatter="formatTimer">日期</th>
			            	<th data-options="field:'netValue',width:100">净值</th>
			            	<th data-options="field:'pId',width:100">产品id</th>
			            	<th data-options="field:'pName',width:100">产品名称</th>
			            	<th data-options="field:'status',width:100" formatter="formatStatus">状态</th>
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		<!-- 添加的弹出层 -->
		 <div id="DivAdd" class="easyui-dialog" style="width:350px;height:210px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'添加浮动类产品净值',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 <form id="ffAdd" method="post" novalidate="novalidate">
			 	<input type="hidden" name="pName" id="apName">
			 	<input type="hidden" name="pId" id="apId">
			 	<input type="hidden" name="netValueDt" id="anet">
			 	<table id="tblAdd" class="view">
			 		<tr>
			 			<th>
                           <label for="netValueDt">日期：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-datebox" id="anetValueDt" style="width:200px" data-options="required:true,editable:false"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="netValue">净值：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" name="netValue" id="anetValue" style="width:200px" data-options="required:true,min:0,max:2,precision:4"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="pId">产品：</label>
                        </th>
                        <td>
                        	<label id="apNameLabel"></label>
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
		<div id="DivEdit" class="easyui-dialog" style="width:350px;height:210px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'修改产品分类',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
			<form id="ffEdit" method="post" novalidate="novalidate">
			 	<input type="hidden" name="id">
				<input type="hidden" name="netValueDt" id="enet">
				<table id="tblEdit" class="view">
			 		<tr>
			 			<th>
                           <label for="netValueDt">日期：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-datebox" id="enetValueDt" style="width:200px" data-options="required:true,editable:false"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="netValue">净值：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" name="netValue" id="enetValue" style="width:200px" data-options="required:true,min:0,max:2,precision:4"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="pId">产品：</label>
                        </th>
                        <td>
                        	<label id="epNameLabel"></label>
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