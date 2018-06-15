<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品确认流程表</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/product/submit.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/submit/list',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:100">id</th>
			            	<th data-options="field:'info',width:100">回馈信息</th>
			            	<th data-options="field:'status',width:100,
			            					formatter:function(value){
										        if(value==0)
										                return '待审批';
										        else if(value==1)
										                return '审批退回';
										        else if(value==2)
										                return '审批同意';
										        }">处理状态</th>
			            	<th data-options="field:'opTime',width:100" formatter="formatTimer">处理时间</th>
			            	<th data-options="field:'pId',width:100">产品id</th>
			            	<th data-options="field:'pName',width:100">产品名称</th>
			            	<th data-options="field:'uId',width:100">用户id</th>
			            	<th data-options="field:'ip',width:100">操作ip</th>
			            	
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		
		<!-- 编辑的弹出层 -->
		<div id="DivEdit" class="easyui-dialog" style="width:350px;height:300px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'修改产品确认流程',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
			<form id="ffEdit" method="post" novalidate="novalidate">
			 	<input type="hidden" name="id">
			 	<input type="hidden" name="pId"></input>
				<input type="hidden" name="opTime" id="eop">
				<table id="tblEdit" class="view">
			 		<tr>
			 			<th>
                           <label for="info">回馈信息：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="info" id="einfo" style="width:200px"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="status">处理状态：</label>
                        </th>
                        <td>
                        	<select class="easyui-combogrid" name="status" id="astatus" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'statusId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'statusName',
						            url: '/resource/json/submit_status.json',
						            method: 'get',
						            columns: [[
						                {field:'statusId',title:'状态id',width:30},
						                {field:'statusName',title:'状态名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						    </select>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="opTime">处理时间：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-datetimebox" id="eopTime" style="width:200px" data-options="required:true"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="pName">产品：</label>
                        </th>
                        <td>
                        	<input  class="easyui-textbox" name="pName" id="epName" style="width:200px" data-options="editable:false"></input>
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