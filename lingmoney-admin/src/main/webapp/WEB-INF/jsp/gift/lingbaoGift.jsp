<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的领地礼品设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="/resource/js/gift/lingbaoGift.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
</head>
<style type="text/css">
.rowOverrideClass {
	background-color: #A8D6F6;
}
</style>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 150px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>礼品名称：</td>
						<td><input class="easyui-textbox" name="name" id="name"
							style="width: 180px"
							data-options="prompt:'请输入礼品名称',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#name').textbox('setValue', '');
											}
										}]" /></td>
						<td>礼品代码：</td>
						<td><input class="easyui-textbox" name="code" id="code"
							style="width: 180px"
							data-options="prompt:'请输入礼品代码',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#code').textbox('setValue', '');
											}
										}]" /></td>
						<td>礼品分类：</td>
						<td>
							<div id="tb" style="padding: 2px 5px;">
								分类名称：<input class="easyui-textbox" style="width: 110px"
									id="searchName"> <a href="javascript:searchName()"
									class="easyui-linkbutton" iconCls="icon-search">查询</a>
							</div> <select class="easyui-combogrid" id="stypeId" name="typeId"
							style="width: 180px"
							data-options="
							            panelWidth: 400,
							            panelHeight: 220,
							            prompt:'请选择礼品分类',
							            toolbar:'#tb',
							            idField: 'id',
							            editable:false,
							            pagination:true,
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/gift/lingbaoGiftType/list?status=1')
									    },
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#stypeId').combogrid('setValue', '');
											}
										}]
							        ">
						</select>
						</td>
					</tr>
					<tr>
						<td>礼品是否上架：</td>
						<td><select class="easyui-combogrid" id="shelfStatus"
							name="shelfStatus" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择礼品是否上架',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#shelfStatus').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td>是否最新上架：</td>
						<td><select class="easyui-combogrid" id="newStatus"
							name="newStatus" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择是否最新上架',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#newStatus').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td>是否推荐：</td>
						<td><select class="easyui-combogrid" id="recommend"
							name="recommend" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择是否推荐',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#recommend').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
					</tr>
					<tr>
						<td>是否达到预警：</td>
						<td><select class="easyui-combogrid" id="isReachWarning"
							name="isReachWarning" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择是否达到预警',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#isReachWarning').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td>礼品领取方式：</td>
						<td><select class="easyui-combogrid" id="receiveWay"
							name="receiveWay" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择礼品领取方式',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#receiveWay').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td>供应商：</td>
						<td><select class="easyui-combogrid" id="supplierId"
							name="supplierId" style="width: 180px"
							data-options="
							            panelWidth: 400,
							            panelHeight: 220,
							            prompt:'请选择供应商',
							            idField: 'id',
							            editable:false,
							            pagination:true,
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/gift/lingbaoSupplier/list?status=1')
									    },
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#supplierId').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
					</tr>
					<tr>
						<td>礼品类别：</td>
						<td><select class="easyui-combogrid" id="types" name="type"
							style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择礼品类别',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#types').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td>应用类型：</td>
						<td><select class="easyui-combogrid" id="applyTypes"
							name="applyType" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择应用类型',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#applyTypes').combogrid('setValue', '');
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
				data-options="sortName:'id',sortOrder:'asc',sortable:true,singleSelect:true,toolbar:toolbar,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/gift/lingbaoGift/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50,sortable:true">礼品id</th>
						<th data-options="field:'name',width:100,sortable:true">礼品名称</th>
						<th data-options="field:'code',width:100,sortable:true">礼品代码</th>
						<th data-options="field:'pictureSmall',width:55"
							formatter="formatPicture">礼品图片</th>
						<th data-options="field:'typeName',width:100">礼品分类</th>
						<th data-options="field:'type',width:100,sortable:true" formatter="formatType">礼品类别</th>
						<th data-options="field:'applyType',width:100"
							formatter="formatApplyType">应用类型</th>
						<th data-options="field:'shelfStatus',width:100"
							formatter="formatShelfStatus">礼品上下架</th>
						<th data-options="field:'newStatus',width:100"
							formatter="formatYesOrNo">是否最新上架</th>
						<th data-options="field:'recommend',width:100"
							formatter="formatYesOrNo">是否推荐</th>
						<th data-options="field:'integral',width:100">积分</th>
						<th data-options="field:'preferentialIntegral',width:100">特惠积分</th>
						<th data-options="field:'hasStore',width:100">入库数量</th>
						<th data-options="field:'store',width:100">当前库存</th>
						<th data-options="field:'exchangeCount',width:100">已兑换次数</th>
						<th data-options="field:'warningStore',width:100">库存预警值</th>
						<th data-options="field:'attribution',width:100">礼品归属地</th>
						<th data-options="field:'supplierName',width:100">供应商</th>
						<th data-options="field:'costPrice',width:100">成本价</th>
						<th data-options="field:'marketPrice',width:100">市场价</th>
						<th data-options="field:'propertyDesc',width:100">规格描述</th>
						<th data-options="field:'exchangeNumber',width:100">单次兑换限制</th>
						<th data-options="field:'level',width:50,sortable:true,sortName:'level',sortvalue:'ASC'">优先级</th>
						<th data-options="field:'receiveWay',width:100"
							formatter="formatReceiveWay">礼品领取方式</th>
						<th data-options="field:'instoreName',width:100">入库人</th>
						<th data-options="field:'instoreTime',width:150"
							formatter="formatTimer">入库时间</th>
						<th data-options="field:'operateName',width:100">操作人</th>
						<th data-options="field:'operateTime',width:150"
							formatter="formatTimer">操作时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 复制的弹出层 -->

	<!-- 添加和修改的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 800px; height: 450px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'我的领地礼品设置',iconCls: 'icon-add',
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
                        removeKindEditor();
                    }
                }]">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" id="giftId"> <input
				type="hidden" name="code"> <input type="hidden"
				name="pictureBig"> <input type="hidden" name="pictureMiddle">
			<input type="hidden" name="pictureSmall"> <input
				type="hidden" name="shelfStatus"> <input type="hidden"
				name="newStatus"> <input type="hidden" name="recommend">
			<input type="hidden" name="changeType" id="changeType"> <input
				type="hidden" name="store" id="store">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">礼品名称：</label></th>
					<td><input class="easyui-textbox" name="name"
						style="width: 200px" data-options="required:true"></input></td>
					<th><label for="name">礼品分类：</label></th>
					<td><select class="easyui-combogrid" id="typeId" name="typeId"
						style="width: 200px"
						data-options="
							            panelWidth: 400,
							            idField: 'id',
							            editable:false,
							            required:true,
							            loadMsg:'正在加载,请稍后...',
							            pagination:true,
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/gift/lingbaoGiftType/list?status=1')
									    },
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="name">积分(个)：</label></th>
					<td><input class="easyui-numberbox" name="integral"
						style="width: 200px" data-options="required:true,min:0,max:999999"></input></td>
					<th><label for="name">特惠积分(个)：</label></th>
					<td><input class="easyui-numberbox"
						name="preferentialIntegral" style="width: 200px"
						data-options="required:true,min:0,max:999999,prompt:'无特惠请填0'"></input></td>
				</tr>
				<tr id="storeTr">
					<th><label for="name">入库数量：</label></th>
					<td><input class="easyui-numberbox" id="hasStore"
						name="hasStore" style="width: 200px"
						data-options="required:true,min:0,max:999999"></input></td>
					<th><label for="name">当前库存：</label></th>
					<td><span id="storeSpan"></span></td>
				</tr>
				<tr>
					<th><label for="name">库存预警值：</label></th>
					<td><input class="easyui-numberbox" name="warningStore"
						id="warningStore" style="width: 200px"
						data-options="required:true,min:1,max:999999,prompt:'最小值为1'"></input></td>
					<th><label for="name">应用类型：</label></th>
					<td><select class="easyui-combogrid" name="applyType"
						id="applyType" style="width: 200px"
						data-options="
							            panelWidth: 200,
							            panelHeight: 120,
							            idField: 'id',
							            editable:false,
							            required:true,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="name">礼品归属地：</label></th>
					<td><input class="easyui-textbox" name="attribution"
						style="width: 200px" data-options="required:false"></input></td>
					<th><label for="name">供应商：</label></th>
					<td><select class="easyui-combogrid" id="supplierId"
						name="supplierId" style="width: 200px"
						data-options="
							            panelWidth: 400,
							            idField: 'id',
							            editable:false,
							            required:true,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            pagination:true,
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/gift/lingbaoSupplier/list?status=1')
									    },
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="name">成本价(元)：</label></th>
					<td><input class="easyui-numberbox" name="costPrice"
						style="width: 200px"
						data-options="required:true,min:0,max:999999,precision:2"></input></td>
					<th><label for="name">市场价(元)：</label></th>
					<td><input class="easyui-numberbox" name="marketPrice"
						style="width: 200px"
						data-options="required:true,min:0,max:999999,precision:2"></input></td>
				</tr>
				<tr>
					<th><label for="name">礼品领取方式：</label></th>
					<td><select class="easyui-combogrid" id="areceiveWay"
						name="receiveWay" style="width: 200px"
						data-options="
							            panelWidth: 200,
							            panelHeight: 120,
							            idField: 'id',
							            required:true,
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
					<th><label for="name">规格描述：</label></th>
					<td><input class="easyui-textbox" name="propertyDesc"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">详情页图片（大）：</label></th>
					<td><input class="easyui-filebox" name="pictureBigImg"
						id="pictureBigImg" style="width: 200px"
						data-options="prompt:'默认不修改'"></input></td>
					<th><label for="name">列表页图片（中）：</label></th>
					<td><input class="easyui-filebox" name="pictureMiddleImg"
						id="pictureMiddleImg" style="width: 200px"
						data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="name">侧栏抽奖图片（小）：</label></th>
					<td><input class="easyui-filebox" name="pictureSmallImg"
						id="pictureSmallImg" style="width: 200px"
						data-options="prompt:'默认不修改'"></input></td>
					<th><label for="name">抽奖图片（手机）：</label></th>
					<td><input class="easyui-filebox" name="pictureMobileImg"
						id="pictureMobileImg" style="width: 200px"
						data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="name">大小：</label></th>
					<td><input class="easyui-textbox" name="size"
						style="width: 200px" data-options="required:false"></input></td>
					<th><label for="name">体积：</label></th>
					<td><input class="easyui-textbox" name="volume"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">颜色：</label></th>
					<td><input class="easyui-textbox" name="color"
						style="width: 200px" data-options="required:false"></input></td>
					<th><label for="name">形状：</label></th>
					<td><input class="easyui-textbox" name="shape"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">面值：</label></th>
					<td><input class="easyui-textbox" name="costValue"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">礼品类别：</label></th>
					<td><select class="easyui-combogrid" name="type" id="type"
						style="width: 200px"
						data-options="
							            panelWidth: 200,
							            panelHeight: 120,
							            idField: 'id',
							            editable:false,
							            required:true,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            onSelect: giftTypePick
							        ">
					</select></td>
					
					<th><label for="name">加息券</label></th>
					<td><select class="easyui-combogrid" name="redpacketId" id="redpacketId"
						style="width: 200px"
						data-options="
										disabled: true,
										url:'/rest/hxRedPacket/list',
										queryParams: {
											hrpType: 1,
											status: 1
										},
							            panelWidth: 200,
							            panelHeight: 120,
							            idField: 'id',
							            editable:false,
							            required:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'hrpNumber',
							            method: 'post',
							            columns: [[
							                {field:'id',title:'id',width:60},
							                {field:'hrpNumber',title:'加息率',width:30}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="name">单次兑换限制：</label></th>
					<td><input class="easyui-numberbox" name="exchangeNumber"
						style="width: 200px" data-options="required:true,min:0,max:999999"></input></td>
					<th><label for="name">优先级：</label></th>
					<td><input class="easyui-numberbox" name="level"
						style="width: 200px"
						data-options="required:true,min:1,max:9999,prompt:'默认值为9999'"></input></td>
				</tr>
			</table>
			<table class="view">
				<tr>
					<th><label for="name">礼品介绍：</label></th>
					<td colspan="3"><textarea name="introduction"
							id="introduction"
							style="width: 400px; height: 400px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
			<table class="view">
				<tr>
					<th><label for="name">规格参数：</label></th>
					<td colspan="3"><textarea name="property" id="property"
							style="width: 400px; height: 400px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!-- excel的弹出层 -->
	<div id="DivimpExcel" class="easyui-dialog"
		style="width: 400px; height: 140px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'批量导入礼品',iconCls: 'icon-excel',buttons: '#dlg-buttons'">
		<form id="ffimpExcel" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table id="tblimpExcel" class="view">
				<tr>
					<th><label for="name">选择excel文件：</label></th>
					<td><input class="easyui-filebox" name="excelFile"
						id="excelFile" style="width: 200px" data-options="required:true"></input>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnimpExcelOK" iconcls="icon-ok" onclick="upload()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivimpExcel').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>