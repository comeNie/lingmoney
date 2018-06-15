<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品分类表</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/trading/trading.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>状态：</td>
	            			<td>
	            				 <select class="easyui-combobox" name="status" id="sstatus" style="width:200px;">
	            				   		<option value="-1">全部</option>
								        <option value="0">买入</option>
								        <option value="1">卖出</option>
	            				 </select>
	            			</td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',
		            loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/trading/trading/listFix',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:50">id</th>
			            	<th data-options="field:'pCode',width:200">产品代码</th>
			            	<th data-options="field:'buyMoney',width:200">购入的金额</th>
			            	<th data-options="field:'buyDt',width:200" formatter="formatTimer">购入的时间</th>
			            	<th data-options="field:'valueDt',width:200" formatter="formatDate">计息日期</th>
			            	<th data-options="field:'minSellDt',width:200" formatter="formatDate">可赎回日期</th>
			            	<th data-options="field:'cRate',width:200" formatter="formatCurrentInterest">当前利率</th>
			            	<th data-options="field:'sellMoney',width:200" >卖出的金额</th>
			            	<th data-options="field:'sellDt',width:200" formatter="formatTimer">卖出的时间</th>
			            	<th data-options="field:'expiryDt',width:200" formatter="formatDate">结息的日期</th>
			            	<th data-options="field:'interest',width:200">利息</th>
			            	<th data-options="field:'interestRate',width:200" formatter="formatInterest">利率</th>
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		<!-- 添加的弹出层 -->
		 <div id="DivAdd" class="easyui-dialog" style="width:550px;height:350px;padding:10px 20px" 
			closed="true" resizable="true" modal="true" data-options="title:'添加权限',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 <form id="ffAdd" method="post" novalidate="novalidate">
			 	<input type="hidden" name="buyDt" id="abuyDt"/>
			 	<table id="tblAdd" class="view">
			 		<tr>
			 			<th>
                           <label>购买日期：</label>
                        </th>
                        <td>
                        	 <input class="easyui-datetimebox" id="viewAbuyDt" required style="width:200px">
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label>购买金额：</label>
                        </th>
                        <td>
                        	  <input class="easyui-numberbox" name="buyMoney" precision="2" value="0" required></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>购买的产品:</th>
			 			<td>
			 				<select class="easyui-combogrid" name="pCode"  style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'code',
						            required:true,
						            editable:false,
						            mode:'remote',
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/product/product/listType?type=0',
						            method: 'get',
						            columns: [[
						                 {field:'code',title:'产品码',width:80},
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
		<div id="DivEdit" class="easyui-dialog" style="width:350px;height:150px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'卖出产品',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
			<form id="ffEdit" method="post" novalidate="novalidate">
				<input type="hidden" name="id" />
				<input type="hidden" name="sellDt" id="esellDt"/>
				
				<table id="tblEdit" class="view">
			 		<tr>
			 			<th>
                           <label>卖出日期：</label>
                        </th>
                        <td>
                        	 <input class="easyui-datetimebox" id="viewASellDt" required style="width:200px">
                        </td>
			 		</tr>
			 		<tr>
                        <td colspan="2" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="sell()">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
                        </td>
                    </tr>
                 </table>
            </form>
        </div>
</body>
</html>