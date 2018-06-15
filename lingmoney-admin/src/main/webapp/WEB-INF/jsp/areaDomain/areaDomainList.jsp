<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>地区首页配置</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto;">
			<div style="width:100%;height:40px;">
			城市：	<input id="cityName" name="cityName" class="easyui-textbox" data-options="prompt:'请输入城市名'"/>
			省份：	<input id="provinceName" name="provinceName" class="easyui-textbox" data-options="prompt:'请输入省份'"/>
			<a href="javascript:void(0);" onclick="subQuery()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			<a href="javascript:void(0);" onclick="refresh()" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
			</div>
			<table class="easyui-datagrid" title="查询结果" id="areaGrid" height="95%"
				data-options="toolbar:toolbar,rownumbers:true,singleSelect:true,fitColumns:true,pagination:true,pageSize:20,iconCls:'icon-view',
		            loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/areaDomain/queryAll',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'cityName',width:60">城市</th>
						<th data-options="field:'bdCityCode',width:100">百度城市代码</th>
						<th data-options="field:'icbcCityCode',width:90">保险城市代码</th>
						<th data-options="field:'provinceName',width:140">省份</th>
						<th data-options="field:'domain',width:240">域名</th>
						<th data-options="field:'indexLogo',width:240">首页LOGO</th>
						<th data-options="field:'indexUrl',width:100">首页地址</th>
						<th data-options="field:'footImage',width:240">底部图片</th>
						<th data-options="field:'cityNamePy',width:60">城市拼音</th>
						<th data-options="field:'stauts',width:150,formatter: formatStatus">状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 编辑的弹出层 -->
	<div id="DivEdit" class="easyui-dialog" closed="true" modal="true" title="编辑" style="width:700px;height:350px;padding:10px"
			data-options="
				iconCls: 'icon-edit',
				buttons: [{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						save();
					}
				},{
					text:'取消',
					handler:function(){
						$('#DivEdit').dialog('close');
					}
				}]
			">
		<form id="areaForm" method="post" >
			<input type="hidden" name="id" />
			<table class="view">
				<tr>
					<td>
						百度城市代码
					</td>
					<td>
						<input name="bdCityCode" class="easyui-textbox">
					</td>
					<td>
						保险城市代码
					</td>
					<td>
						<input name="icbcCityCode" class="easyui-textbox">
					</td>
				</tr>
				
				<tr>
					<td>
						城市
					</td>
					<td>
						<input name="cityName" class="easyui-textbox">
					</td>
					
					<td>
						城市拼音
					</td>
					<td>
						<input name="cityNamePy" class="easyui-textbox">
					</td>
				</tr>
				
				<tr>
					<td>
						省份
					</td>
					<td>
						<input name="provinceName" class="easyui-textbox">
					</td>
					<td>
						域名
					</td>
					<td>
						<input name="domain" class="easyui-textbox">
					</td>
				</tr>
				
				<tr>
					<td>
						状态
					</td>
					<td>
						<input name="status" class="easyui-combobox" data-options="
							valueField: 'label',
							textField: 'value',
							editable:false,
							data: [{
								label: '1',
								value: '启用'
							},{
								label: '0',
								value: '禁用'
							}]" >
					</td>
					<td>
						首页地址
					</td>
					<td >
						<input  name="indexUrl" class="easyui-textbox">
					</td>
				</tr>
				
				<tr>
					<td>
						首页LOGO
					</td>
					<td colspan="3">
						<input style="width:350px;" name="indexLogo" class="easyui-textbox">
					</td>
				</tr>
				
				<tr>
					<td>
						底部图片
					</td>
					<td colspan="3">
						<input style="width:350px;" name="footImage" class="easyui-textbox">
					</td>
				</tr>
				
			</table>
		</form>
	</div>
	
	
	<script type="text/javascript">
		var toolbar = [ {
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				add();
			}
		}, '-', {
			text : '编辑',
			iconCls : 'icon-edit',
			handler : function() {
				edit();
			}
		}];
	
		//格式化列表信息 
		function formatStatus(value, rows){
			if(rows.status=="0") {
				return "<font color='red'>禁用</font>";
			} else {
				return "<font color='green'>启用</font>";
			}
		}
		
		//查询按钮 
		function subQuery(){
			var cityName = $('#cityName').textbox('getValue');
			var provinceName = $('#provinceName').textbox('getValue');
			$('#areaGrid').datagrid('reload', {'cityName': cityName,'provinceName': provinceName});
		}
		
		//刷新按钮 
		function refresh(){
			$('#cityName').textbox('setValue','');
			$('#provinceName').textbox('setValue','');
			$('#areaGrid').datagrid('reload',{'t':new Date()});
		}
		
		function add(){
			$("#areaForm").form("clear");
			$("#DivEdit").dialog("setTitle","添加").dialog("open");
		}
		
		//编辑按钮点击事件 
		function edit(){
			var rows = $("#areaGrid").datagrid("getSelected");
			if (rows==null) {
			    $.messager.alert("提示", "请选择一条记录", "error");
			    return false;
			}
			$("#areaForm").form("load",rows);
			$("#DivEdit").dialog("setTitle","编辑").dialog("open");
		}
		
		//保存，点击事件 
		function save(){
			$('#areaForm').form('submit',{
				url: '/rest/areaDomain/save',
				ajax:true,
				success:function(data){
					var json = $.parseJSON(data);
					$.messager.alert("提示",json.msg,"info");
					$("#DivEdit").dialog("close");
					$('#areaGrid').datagrid('reload',{'t':new Date()});
				}
			});
		}
		
	</script>
</body>
</html>