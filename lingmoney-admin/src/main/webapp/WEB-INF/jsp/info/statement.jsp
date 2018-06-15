<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>声明规则设置</title>
		<jsp:include page="/common/head.jsp"></jsp:include>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
		</head>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resource/js/info/statement.js"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
		<script charset="utf-8" src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
		<script charset="utf-8" src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
	</head>
	<body>
		<div class="easyui-layout" style="width: 700px;" fit="true">
			<div class="easyui-panel"
				data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
				style="padding: 5px; height: 80px">
				<form id="searchFrm">
					<table>
						<tr>
							<td>标识：</td>
							<td><input class="easyui-textbox" name="identification" id="identification" /></td>
							<td><a href="javascript:search()" class="easyui-linkbutton"
								id="btn_query" iconcls="icon-search">查询</a></td>
						</tr>
					</table>
				</form>
			</div>
			<div id="tb" data-options="region:'center'"
				style="padding: 5px; height: auto">
				<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
					data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/info/statement/list',method:'post'">
					<thead>
						<tr>
							<th data-options="field:'id',width:50">id</th>
							<th data-options="field:'identification',width:100">标识</th>
							<th data-options="field:'content',width:150" formatter="formatContent">内容</th>
							<th data-options="field:'createTime',width:150" formatter="formatTimer">创建时间</th>
							<th data-options="field:'updateTime',width:300" formatter="formatTimer">修改时间</th>
							<th data-options="field:'status',width:50" formatter="formatStatus">状态</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	<!-- 添加的弹出层 -->
		 <div id="DivAdd" class="easyui-dialog" style="width:860px;height:505px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'添加声明规则',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 <form id="ffAdd" method="post" novalidate="novalidate">
			 	<table id="tblAdd" class="view">
			 		<tr>
			 			<th>
                           <label for="name">标识:</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="identification" id="identification" style="width:200px"></input>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="content">内容：</label>
                        </th>
                        <td>
                        	 <textarea name="content" id="acontent" style="width:700px;height:350px;visibility:hidden;"></textarea>
                        </td>
			 		</tr>
			 		<tr>
                        <td colspan="2" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivAdd').dialog('close'),KindEditor.remove('#acontent');">关闭</a>
                        </td>
                    </tr>
			 	</table>
			 </form>
		</div>
		<!-- 编辑的弹出层 -->
		<div id="DivEdit" class="easyui-dialog" style="width:860px;height:505px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'编辑声明规则',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
			<form id="ffEdit" method="post" novalidate="novalidate">
				<input type="hidden" name="id" />
				<table id="tblEdit" class="view">
			 		<tr>
			 			<th>
                           <label for="name">标识:</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="identification" id="identification" style="width:200px"></input>
                        </td>
			 		</tr>
                   <tr>
			 			<th>
                           <label for="content">内容：</label>
                        </th>
                        <td>
                        	 <textarea name="content" id="econtent" style="width:700px;height:350px;visibility:hidden;"></textarea>
                        </td>
			 		</tr>
			 		<tr>
                        <td colspan="2" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivEdit').dialog('close'),KindEditor.remove('#econtent');">关闭</a>
                        </td>
                    </tr>
                 </table>
            </form>
        </div>
	</body>
</html>