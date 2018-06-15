<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品推荐表</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/recommend.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchForm">
				<table>
					<tr>
						<td>产品：</td>
						<td><select class="easyui-combogrid" name="pId" id="proId"
							style="width: 200px"
							data-options="
							            panelWidth: 400,
							            panelHeight: 'auto',
										editable:false,
							            idField: 'id',
							            loadMsg:'正在加载,请稍后...',
							            prompt:'请选择产品',
							            textField: 'name',
							            pagination:true,
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/product/product/productList')
									    },
							            method: 'get',
							            columns: [[
							                {field:'id',title:'产品id',width:30},
							                {field:'name',title:'产品名称',width:50}
							            ]],
							            
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#proId').combogrid('setValue', '');
											}
										}]
						        ">
						</select></td>
						<td>所属地区 ：</td>
						<td><select class="easyui-combogrid" id="sCityCode"
							name="cityCode" style="width: 200px"
							data-options="
							            panelWidth: 200,
							            panelHeight: 'auto',
							            idField: 'code',
							            editable:false,
							            prompt:'请选择所属地区',
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/areaDomain/queryCodeName')
									    },
							            method: 'get',
							            columns: [[
							                {field:'code',title:'城市编码',width:30},
							                {field:'name',title:'所属地区',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#sCityCode').combogrid('setValue', '');
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/recommend/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:30">id</th>
						<th data-options="field:'pId',width:30">产品id</th>
						<th data-options="field:'pName',width:150">产品名称</th>
						<th
							data-options="field:'pStatus',width:100,
			            					formatter:function(value){
			            						if(value==0)
										                return '产品初始化';
										        else if(value==1)
										                return '项目筹集期';
										        else if(value==2)
										                return '项目运行中/已放款';
										        else if(value==3)
										                return '项目汇款中/项目到期';
								                else if(value==4)
										                return '项目已结束';
								                else if(value==5)
										                return '项目已作废';
								                else if(value==6)
										                return '筹集金额未达标';
								                else if(value==7)
										                return '筹集金额已达标/等待申请放款';
								                else if(value==8)
										                return '流标申请中';
								                else if(value==9)
										                return '已流标';
								                else if(value==10)
										                return '放款申请中';
								                else if(value==11)
										                return '已确认自动还款';
								                else if(value==12)
										                return '还款申请已提交';
								                else if(value==13)
										                return '还款成功';
								                else if(value==14)
										                return '还款收益明细已提交';
								                else if(value==15)
										                return '还款收益明细提交成功';
										        }">产品状态</th>
						<th
							data-options="field:'bStatus',width:100,
			            					formatter:function(value){
										        if(value==-1)
										                return '初始（可发标）';
										        else if(value==0)
										                return '正常（发标成功）';
										        else if(value ==1 )
										        		return '撤销（暂不可用）';
										        else if(value ==5 )
										        		return '发标失败';
										        }">标的状态</th>
						<th data-options="field:'indexX',width:80"
							formatter="formatStatus">横向产品显示</th>
						<th data-options="field:'indexY',width:80"
							formatter="formatStatus">纵向产品显示</th>
						<th data-options="field:'recommend',width:80"
							formatter="formatRec">推荐产品</th>
						<th data-options="field:'cityCode',width:80"
							formatter="formatCityCode">所属地区</th>
						<th data-options="field:'homeShow',width:80"
							formatter="formatRecLevel">活跃用户推荐等级</th>
						<th data-options="field:'newShow',width:80"
							formatter="formatRecLevel">新手专享推荐等级</th>
						<th data-options="field:'actId',width:80">关联活动ID</th>
						<th data-options="field:'actName',width:80">关联活动名称</th>
						<!-- <th data-options="field:'titlePic',width:50"
							formatter="formatPicture">标题图片</th>
						<th data-options="field:'activityPic',width:50"
							formatter="formatPicture">活动图片</th> -->
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 420px; height: 420px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'产品推荐设置',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="pId">产品：</label></th>
					<td><select class="easyui-combogrid" name="pId" id="apid"
						style="width: 200px"
						data-options="
						            panelWidth: 400,
							        panelHeight: 'auto',
							        required: true,
									editable: false,
							        idField: 'id',
							        loadMsg:'正在加载,请稍后...',
							        prompt:'请选择产品',
							        textField: 'name',
							        pagination:true,
							        onShowPanel:function(){
										$(this).combogrid('grid').datagrid('load', '/rest/product/product/productList')
									},
						            method: 'get',
						            columns: [[
						                {field:'id',title:'产品id',width:30},
						                {field:'name',title:'产品名称',width:50}
						            ]],
						            fitColumns: true,
						            onClickRow: function(i,item){
						            	$('#recCityCode').val(item.cityCode);
						            	$('#recCityCodeVal').textbox('setValue',formatCityCode(1,item));
						           }
						        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="indexX">横向产品显示：</label></th>
					<td><select class="easyui-combogrid" name="indexX"
						id="aindexX" style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'recname',
						            url: '/resource/json/recommend.json',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'id',width:30},
						                {field:'recname',title:'是否显示',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select>
				</tr>
				<tr>
					<th><label for="indexY">纵向产品显示：</label></th>
					<td><select class="easyui-combogrid" name="indexY"
						id="aindexY" style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'recname',
						            url: '/resource/json/recommend.json',
						            method: 'get',
						            columns: [[
						                {field:'id',title:'id',width:30},
						                {field:'recname',title:'是否显示',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="recommond">推荐产品：</label></th>
					<td><select class="easyui-combogrid" name="recommend"
						id="arecommend" style="width: 200px"
						data-options="
						            panelWidth: 200,
						            idField: 'recid',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'recname',
						            url: '/resource/json/product_recommend.json',
						            method: 'get',
						            columns: [[
						                {field:'recid',title:'id',width:30},
						                {field:'recname',title:'是否推荐',width:50}
						            ]],
						            fitColumns: true
						        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="cityCode">所属地区 ：</label></th>
					<td><input id="recCityCode" name="cityCode" type="hidden">
						<input id="recCityCodeVal" style="width: 200px"
						readonly="readonly" class="easyui-textbox" /></td>
				</tr>
				<tr>
					<th><label>活跃用户推荐等级：</label></th>
					<td><input class="easyui-numberbox" name="homeShow"
						id="homeShow" style="width: 200px"
						data-options="prompt:'默认0不推荐，为0APP端不展示',required:true"></input></td>
				</tr>
				<tr>
					<th><label>新手专享推荐等级：</label></th>
					<td><input class="easyui-numberbox" name="newShow"
						id="newShow" style="width: 200px"
						data-options="prompt:'默认0不推荐，为0APP端不展示',required:true"></input></td>
				</tr>
				<tr>
					<th><label>关联活动：</label></th>
					<td><select class="easyui-combogrid" name="actId"
						id="actId" style="width: 200px"
						data-options="
						            panelWidth: 480,
						            panelHeight: 'auto',
						            idField: 'id',
						            editable:false,
						            pagination:true,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'actName',
						            url: '/rest/product/activityProduct/list',
						            onShowPanel:function(){
						            	$(this).combogrid('grid').datagrid('reload');
						            },
						            method: 'get',
						            columns: [[
						                {field:'id',title:'活动ID',width:30},
						                {field:'actName',title:'活动名称',width:50}
						            ]],
						            fitColumns: true,
						            icons: [{
										iconCls:'icon-remove',
										handler: function(e){
											$('#actId').combogrid('setValue', '');
										}
									}]
						        ">
					</select></td>
				</tr>
				<!-- <tr>
					<th><label for="name">标题图片：</label></th>
					<td><input class="easyui-filebox" name="titleFile"
						id="titleFile" style="width: 200px"
						data-options="prompt:'请选择图片',required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">活动图片：</label></th>
					<td><input class="easyui-filebox" name="activityFile"
						id="activityFile" style="width: 200px"
						data-options="prompt:'请选择图片',required:false"></input></td>
				</tr> -->
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="saveAndUpdate()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>