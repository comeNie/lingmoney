<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的领地签到属性</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/gift/lingbaoSign.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>购物车限制个数(当前<span id="cartCountNow"></span>个)：</td>
						<td><input class="easyui-numberbox" style="width: 180px"
							name="cartCount" id="cartCount"
							data-options="min:1,max:100,prompt:'请输入购物车限制个数',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#cartCount').numberbox('setValue', '');
											}
										}]" /></td>
						<td><a href="javascript:setCartCount()"
							class="easyui-linkbutton" id="btnAddOK" iconcls="icon-setting">设置</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/gift/lingbaoSign/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50,hidden:true">id</th>
						<th data-options="field:'startTime',width:50"
							formatter="formatTimer">开始时间</th>
						<th data-options="field:'endTime',width:50"
							formatter="formatTimer">结束时间</th>
						<th data-options="field:'num',width:50">每次签到赠送领宝个数</th>
						<th data-options="field:'type',width:50" formatter="formatType">类型</th>
						<th data-options="field:'operateName',width:50">操作人</th>
						<th data-options="field:'operateTime',width:50"
							formatter="formatTimer">操作时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加和修改的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 420px; height: 250px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'我的领地供应商设置',iconCls: 'icon-add',
			buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        saveAndUpdate();
                    }
                },{
                    text:'关闭',
                     iconCls:'icon-cancel',
                    handler:function(){
                        $('#DivAdd').dialog('close');
                    }
                }]">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">开始时间：</label></th>
					<td><input class="easyui-datetimebox" name="startTime"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">结束时间：</label></th>
					<td><input class="easyui-datetimebox" name="endTime"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">每次签到赠送领宝个数：</label></th>
					<td><input class="easyui-numberbox" name="num"
						style="width: 200px" data-options="required:true,min:1,max:9999"></input></td>
				</tr>
				<tr>
					<th><label for="name">类型：</label></th>
					<td><select class="easyui-combogrid" id="type" name="type"
						style="width: 200px"
						data-options="
							            panelWidth: 200,
							            idField: 'id',
							            required:true,
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/resource/json/signType.json',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
				</tr>

			</table>
		</form>
	</div>

</body>
</html>