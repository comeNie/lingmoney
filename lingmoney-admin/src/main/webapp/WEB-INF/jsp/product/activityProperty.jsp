<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>活动属性表</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/activityProperty.js"></script>
<style type="text/css">
fieldset {
	margin-bottom: 10px;
	color: #333;
	border: #06c dashed 1px;
}

legend {
	color: #06c;
	font-weight: 800;
	background: #fff;
}
</style>

<script type="text/javascript">
	function showAndHideDay(newValue, oldValue) {

		if (newValue == 0) {
			$("#weekfixProp").hide();
			$("#monthfixProp").hide();
			return;
		}

		if (newValue == 1) {
			$("#weekfixProp").show();
			$("#monthfixProp").hide();
			return;
		}
		if (newValue == 2) {
			$("#monthfixProp").show();
			$("#weekfixProp").hide();
		}
	}
</script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/activityProperty/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'actId',width:50">活动id</th>
						<%--<th data-options="field:'actName',width:100">活动名称</th>
						<th data-options="field:'actTitle',width:100">活动标题</th>--%>
						<th data-options="field:'actTitleImage',width:100">标题图片33</th>
						<th data-options="field:'actImage',width:100">活动图片</th>
						<th data-options="field:'actRecommendations',width:100">推荐度</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<%--添加的弹出层  --%>
	<div id="DivAdd" class="easyui-dialog"
		style="width: 880px; height: 620px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'添加活动',iconCls: 'icon-add',
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
		<form id="ffAdd" method="post" novalidate="novalidate" enctype="multipart/form-data">
			<fieldset id="aProductBase">
				<legend>活动属性</legend>
				<div style="overflow:auto;color:red;">
					说明：<br>
						1.标题图片用于在手机端活动中显示。<br/>
						2.活动图片可不上传，活动产品描述等内容可以不添加。
						 
				</div>
				<table id="tblAdd" class="view">
					<tr>
						<th><label for="path1">标题图片</label></th>
						<td><input class="easyui-filebox" name="path1"
							style="width: 350px" ></input></td>
					</tr>
					<tr>
						<th><label for="path2">活动图片</label></th>
						<td><input class="easyui-filebox" name="path2"
							style="width: 350px" ></input></td>						
					</tr>
					<tr>
						<th>推荐度</th>
						<td>						
							<select class="easyui-combobox" name="actRecommendations"
								id="apriority" style="width: 200px" data-options="required:true">
									<option value="3">3</option>
									<option value="3.5">3.5</option>
									<option value="4">4</option>
									<option value="4.5">4.5</option>
									<option value="5">5</option>
							</select>							
						</td>
					</tr>
					<tr>
						<th>活动:</th>
						<td><select multiple="multiple" class="easyui-combogrid"
							id="actId" name="actId" style="width: 200px"   
							data-options="
							            panelWidth: 200,
							            idField:'id',
							            required:true,
							            editable:false,
							            multiple:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'actName',
							            url: '/rest/product/activityProduct/listActivityProduct', 
							            method: 'get',
							            columns: [[
							                {field:'id',title:'活动id',width:30},
							                {field:'actName',title:'活动名称',width:50}
							            ]],
							            fitColumns: true
							        ">
						</select></td>
					</tr>
				</table>
			</fieldset>
			<table class="view">
				<tr>
					<th><label for="content">活动产品描述1：</label></th>
					<td><textarea name="actProDesc1" id="acontent1"
							style="width: 700px; height: 350px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
			<table class="view">
				<tr>
					<th><label for="content">活动产品描述2：</label></th>
					<td><textarea name="actProDesc2" id="acontent2"
							style="width: 700px; height: 350px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
			<table class="view">
				<tr>
					<th><label for="content">活动产品描述3：</label></th>
					<td><textarea name="actProDesc3" id="acontent3"
							style="width: 700px; height: 350px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
			<table class="view">
				<tr>
					<th><label for="content">活动产品描述4：</label></th>
					<td><textarea name="actProDesc4" id="acontent4"
							style="width: 700px; height: 350px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
			<table class="view">
				<tr>
					<th><label for="content">活动产品描述5：</label></th>
					<td><textarea name="actProDesc5" id="acontent5"
							style="width: 700px; height: 350px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
			<input type="hidden" name="id" id="id">
			<!-- <input type="hidden" name="actId" id="actId"> -->
		</form>
	</div>

</body>
</html>