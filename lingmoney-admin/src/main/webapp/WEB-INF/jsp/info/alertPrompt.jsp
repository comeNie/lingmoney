<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>弹框提示维护</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/info/alertPrompt.js"></script>
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
						<td>名称：</td>
						<td><input class="easyui-textbox" style="width: 180px"
							name="name" id="name"
							data-options="prompt:'请输入名称',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#name').textbox('setValue', '');
											}
										}]" /></td>
						<td>状态：</td>
						<td><select class="easyui-combogrid" id="status"
							name="status" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择状态',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/resource/json/useStatus.json',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#status').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/info/alertPrompt/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100">id</th>
						<th data-options="field:'name',width:100">名称</th>
						<th data-options="field:'buttonTitle',width:100">按钮文字</th>
						<th data-options="field:'buttonType',width:100"
							formatter="formatButtonType">按钮点击类型</th>
						<th data-options="field:'status',width:100"
							formatter="formatStatus">状态</th>
						<th data-options="field:'classIos',width:150">iOS调用类名 小图</th>
						<th data-options="field:'propertyKeyIos',width:150">iOS属性key
							小图</th>
						<th data-options="field:'propertyValueIos',width:150">iOS属性value
							小图</th>
						<th data-options="field:'classAndroid',width:150">android调用类名
							小图</th>
						<th data-options="field:'propertyKeyAndroid',width:150">android属性key
							小图</th>
						<th data-options="field:'propertyValueAndroid',width:150">android属性value
							小图</th>
						<th data-options="field:'classIosBig',width:150">iOS调用类名 大图</th>
						<th data-options="field:'propertyKeyIosBig',width:150">iOS属性key
							大图</th>
						<th data-options="field:'propertyValueIosBig',width:150">iOS属性value
							大图</th>
						<th data-options="field:'classAndroidBig',width:150">android调用类名
							大图</th>
						<th data-options="field:'propertyKeyAndroidBig',width:150">android属性key
							大图</th>
						<th data-options="field:'propertyValueAndroidBig',width:150">android属性value
							大图</th>
						<th data-options="field:'bigImg',width:150">大图路径</th>
						<th data-options="field:'buttonImg',width:150">小图路径</th>
						<th data-options="field:'distance',width:150">图片间距</th>
						<th data-options="field:'centerY',width:150">与y轴中心距离</th>
						<th data-options="field:'androidJumpType',width:150"
							formatter="formatJumpType">安卓跳转类型</th>
						<th data-options="field:'alertType',width:150"
							formatter="formatAlertType">弹框类型</th>
						<th
							data-options="field:'isFullScreen',width:150,formatter:function(value){
																			if(value==0)
																	                return '<font color=red>原图</font>';
																	        else if(value==1)
																	                return '<font color=blue>全屏</font>';
																	        else if(value==2)
																	                return '中';
																		}">大图是否全屏显示</th>
						<th
							data-options="field:'isCloseShow',width:150,formatter:function(value){
																			if(value==0)
																	                return '<font color=red>否</font>';
																	        else if(value==1)
																	                return '<font color=blue>是</font>';
																		}">右上角是否有关闭图标</th>
						<th data-options="field:'priority',width:150">优先级</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加和编辑的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 660px; height: 480px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'弹框提示维护',iconCls: 'icon-add',
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
			<input type="hidden" name="id" />
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">名称：</label></th>
					<td><input class="easyui-textbox" name="name"
						style="width: 400px" data-options=""></input></td>
				</tr>
				<tr>
					<th><label for="name">按钮文字：</label></th>
					<td><input class="easyui-textbox" name="buttonTitle"
						style="width: 400px" data-options=""></input></td>
				</tr>
				<tr>
					<th><label for="name">按钮点击类型：</label></th>
					<td><select class="easyui-combogrid" name="buttonType"
						id="buttonType" style="width: 180px"
						data-options="
							            panelWidth: 180,
							            prompt:'请选择按钮点击类型',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/resource/json/buttonType.json',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#buttonType').combogrid('setValue', '');
											}
										}]
							        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="name">iOS调用类名 小图：</label></th>
					<td><input class="easyui-textbox" name="classIos"
						style="width: 400px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">iOS属性key 小图：</label></th>
					<td><input class="easyui-textbox" name="propertyKeyIos"
						style="width: 400px" data-options="prompt:'多个key以英文逗号分隔'"></input></td>
				</tr>
				<tr>
					<th><label for="name">iOS属性value 小图：</label></th>
					<td><input class="easyui-textbox" name="propertyValueIos"
						style="width: 400px" data-options="prompt:'多个value以英文逗号分隔'"></input></td>
				</tr>
				<tr>
					<th><label for="name">android调用类名 小图：</label></th>
					<td><input class="easyui-textbox" name="classAndroid"
						style="width: 400px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">android属性key 小图：</label></th>
					<td><input class="easyui-textbox" name="propertyKeyAndroid"
						style="width: 400px" data-options="prompt:'多个key以英文逗号分隔'"></input></td>
				</tr>
				<tr>
					<th><label for="name">android属性value 小图：</label></th>
					<td><input class="easyui-textbox" name="propertyValueAndroid"
						style="width: 400px" data-options="prompt:'多个value以英文逗号分隔'"></input></td>
				</tr>
				<tr>
					<th><label for="name">iOS调用类名 大图：</label></th>
					<td><input class="easyui-textbox" name="classIosBig"
						style="width: 400px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">iOS属性key 大图：</label></th>
					<td><input class="easyui-textbox" name="propertyKeyIosBig"
						style="width: 400px" data-options="prompt:'多个key以英文逗号分隔'"></input></td>
				</tr>
				<tr>
					<th><label for="name">iOS属性value 大图：</label></th>
					<td><input class="easyui-textbox" name="propertyValueIosBig"
						style="width: 400px" data-options="prompt:'多个value以英文逗号分隔'"></input></td>
				</tr>
				<tr>
					<th><label for="name">android调用类名 大图：</label></th>
					<td><input class="easyui-textbox" name="classAndroidBig"
						style="width: 400px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">android属性key 大图：</label></th>
					<td><input class="easyui-textbox" name="propertyKeyAndroidBig"
						style="width: 400px" data-options="prompt:'多个key以英文逗号分隔'"></input></td>
				</tr>
				<tr>
					<th><label for="name">android属性value 大图：</label></th>
					<td><input class="easyui-textbox"
						name="propertyValueAndroidBig" style="width: 400px"
						data-options="prompt:'多个value以英文逗号分隔'"></input></td>
				</tr>
				<tr>
					<th><label for="name">大图</label></th>
					<td><input class="easyui-filebox" name="path1"
						style="width: 400px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="name">小图</label></th>
					<td><input class="easyui-filebox" name="path2"
						style="width: 400px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="name">图片间距：</label></th>
					<td><input class="easyui-textbox" name="distance"
						style="width: 400px" data-options="prompt:'请输入大图和小图间距'"></input></td>
				</tr>
				<tr>
					<th><label for="name">与y轴中心距离：</label></th>
					<td><input class="easyui-textbox" name="centerY"
						style="width: 400px" data-options="prompt:'请输入与y轴中心距离'"></input></td>
				</tr>
				<tr>
					<th><label for="name">安卓跳转类型：</label></th>
					<td><select class="easyui-combogrid" name="androidJumpType"
						id="androidJumpType" style="width: 180px"
						data-options="
							            panelWidth: 180,
							            prompt:'请选择安卓跳转类型',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/resource/json/androidJumpType.json',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#androidJumpType').combogrid('setValue', '');
											}
										}]
							        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="name">弹框类型：</label></th>
					<td><select class="easyui-combogrid" name="alertType"
						style="width: 180px"
						data-options="
							            panelWidth: 180,
							            prompt:'请选择弹框类型',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/resource/json/alertType.json',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#alertType').combogrid('setValue', '');
											}
										}]
							        ">
					</select></td>
				</tr>
				<tr>
					<th>大图是否全屏显示：</th>
					<td><select class="easyui-combobox" name="isFullScreen"
						id="isFullScreen" style="width: 200px;"
						data-options="value:'',prompt:'请选择大图是否全屏显示',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#isFullScreen').combobox('setValue', '');
											}
										}]">
							<option value="0">否</option>
							<option value="1">是</option>
							<option value="2">中</option>
					</select></td>
				</tr>
				<tr>
					<th>右上角是否有关闭图标：</th>
					<td><select class="easyui-combobox" name="isCloseShow"
						id="isCloseShow" style="width: 200px;"
						data-options="value:'',prompt:'请选择右上角是否有关闭图标',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#isCloseShow').combobox('setValue', '');
											}
										}]">
							<option value="0">否</option>
							<option value="1">是</option>
					</select></td>
				</tr>
				<tr>
					<th><label for="name">优先级：</label></th>
					<td><input class="easyui-numberbox" name="priority"
						style="width: 400px"
						data-options="prompt:'从低到高',required:true,min:0"></input></td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 更改状态 -->
	<div id="DivStatus" class="easyui-dialog"
		style="width: 440px; height: 140px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'更噶状态',iconCls: 'icon-ok',buttons: '#dlg-buttons'">
		<form id="ffStatus" method="post" novalidate="novalidate">
			<input type="hidden" name="id" />
			<table id="tblOrder" class="view">
				<tr>
					<th><label>状态：</label></th>
					<td><select class="easyui-combogrid" name="status"
						style="width: 200px"
						data-options="
							            panelWidth: 200,
							            idField: 'id',
							            required:false,
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/resource/json/alertStatus.json',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'名称',width:50}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="changeStatus()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivStatus').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>