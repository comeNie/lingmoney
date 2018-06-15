<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的领地活动设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/gift/lingbaoLotteryType.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>活动名称：</td>
						<td><input class="easyui-textbox" name="name" id="name"
							style="width: 180px"
							data-options="prompt:'请输入活动名称',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#name').textbox('setValue', '');
											}
										}]" /></td>
						<td>活动类型：</td>
						<td><select class="easyui-combogrid" id="type" name="type"
							style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择活动类型',
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
												$('#type').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td>活动状态：</td>
						<td><select class="easyui-combogrid" id="status"
							name="status" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择活动状态',
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/gift/lingbaoLotteryType/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:10">id</th>
						<th data-options="field:'name',width:50">活动名称</th>
						<th data-options="field:'type',width:30" formatter="formatType">活动类型</th>
						<th data-options="field:'picture',width:20"
							formatter="formatPicture">宣传图片</th>
						<th data-options="field:'statement',width:100">文字说明</th>
						<th data-options="field:'status',width:30"
							formatter="formatStatus">活动状态</th>
						<th data-options="field:'priority',width:30">优先级</th>
						<th data-options="field:'integral',width:30"
							formatter="formatIntegral">所需领宝个数</th>
						<th data-options="field:'startTime',width:50"
							formatter="formatTimer">活动开始时间</th>
						<th data-options="field:'endTime',width:50"
							formatter="formatTimer">活动结束时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加和编辑的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 800px; height: 450px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'我的领地活动设置',iconCls: 'icon-add',
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
			<input type="hidden" name="id" />
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">名称：</label></th>
					<td><input class="easyui-textbox" name="name"
						style="width: 200px" data-options="required:true"></input></td>
					<th><label for="name">优先级(由高到低)：</label></th>
					<td><input class="easyui-numberbox" name="priority"
						id="priority" style="width: 200px"
						data-options="required:true,min:1,max:9999"></input></td>
				</tr>
				<tr>
					<th><label for="name">活动类型：</label></th>
					<td><select class="easyui-combogrid" id="atype" name="type"
						style="width: 200px"
						data-options="
							            panelWidth: 200,
							            panelHeight: 100,
							            required:true,
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            onChange:function(newValue,oldValue){
							            	if(newValue==0){
							            		integral(0); 
							            	}else if(newValue==1){
							            		integral(1); 
							            	}
							            },
							            fitColumns: true
							        ">
					</select></td>
					<th id="integralTh"><label for="name">抽奖所需领宝个数：</label></th>
					<td id="integralTd"><input class="easyui-numberbox"
						name="integral" id="integral" style="width: 200px"
						data-options="min:1,max:9999,required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">活动开始时间：</label></th>
					<td><input class="easyui-datetimebox" name="startTime"
						style="width: 200px" data-options="required:true"></input></td>
					<th><label for="name">活动结束时间：</label></th>
					<td><input class="easyui-datetimebox" name="endTime"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">文字说明：</label></th>
					<td><input class="easyui-textbox" name="statement"
						id="statement" style="height: 80px; width: 200px"
						data-options="multiline:true,required:true"></input></td>
					<th><label for="name">宣传图片：</label></th>
					<td><input class="easyui-filebox" name="pictureImg"
						id="pictureImg" style="width: 200px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
			</table>
			<table class="view">
				<tr>
					<th><label for="rule">活动规则：</label></th>
					<td colspan="3"><textarea name="rule" id="rule"
							style="width: 400px; height: 400px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 设置中奖概率-->
	<div id="DivSetting" class="easyui-dialog"
		style="width: 860px; height: 440px; padding: 10px 20px" closed="true" resizable="true" modal="true"
		data-options="title:'设置中奖概率',iconCls: 'icon-setting',buttons: '#dlg-buttons'">
		<p><font color="red">!注意：1、初次抽奖用户默认必中‘展示排序’序号最小礼品，当前为100领宝。2、由于展示图片尺寸不一，请在改变排序前确认展示效果。</font></p>
		<table class="easyui-datagrid" id="settingGrid" style="width:auto;height:90%;"
			data-options="rownumbers:true,singleSelect:true,toolbar:toolbars,fitColumns:true,pagination:true,loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,method:'post'">
			<thead>
				<tr>
					<th data-options="field:'typeId',width:100">活动id</th>
					<th data-options="field:'typeName',width:100">活动名称</th>
					<th data-options="field:'giftId',width:100">礼品id</th>
					<th data-options="field:'giftName',width:100">礼品名称</th>
					<th data-options="field:'word',width:100">文字</th>
					<th data-options="field:'ratio',width:100">中奖概率（%）</th>
					<th data-options="field:'level',width:100">展示排序</th>
					<th data-options="field:'prizeLevel',width:100">奖品级别</th>
					<th data-options="field:'status',width:100"
						formatter="formatStatus">状态</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 添加和修改中奖概率的弹出层 -->
	<div id="DivLottery" class="easyui-dialog"
		style="width: 800px; height: 170px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'设置中奖概率',iconCls: 'icon-add',
			buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        saveAndUpdateLottery();
                    }
                },{
                    text:'关闭',
                     iconCls:'icon-cancel',
                    handler:function(){
                        $('#DivLottery').dialog('close');
                    }
                }]">
		<form id="ffLottery" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id"><input type="hidden"
				name="type" id="typeLottery">
			<table class="view">
				<tr>
					<th>活动id：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="typeId" id="typeId" data-options="required:true"></td>
					<th>展示排序：</th>
					<td><input class="easyui-numberbox" style="width: 200px"
						name="level" id="level"
						data-options="required:true,min:0,max:999,prompt:'由小到大 默认999'"></td>
				</tr>
				<tr>
					<th><input type="radio" name="isGift" value="0" id="isGift">礼品<input
						type="radio" name="isGift" value="1" id="isWord">文字</th>
					<td id="giftIdTd"><div id="tba" style="padding: 2px 5px;">
							礼品ID：<input class="easyui-textbox" style="width: 100px"
								id="searchId"> 礼品名称：<input class="easyui-textbox"
								style="width: 100px" id="searchName"> <a
								href="javascript:searchName(1)" class="easyui-linkbutton"
								iconCls="icon-search">查询</a>
						</div>
						<select class="easyui-combogrid" name="giftId" id="giftId"
						style="width: 200px"
						data-options="
										panelWidth: 430,
										panelHeight: 220,
										toolbar:'#tba',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            pagination:true,
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/gift/lingbaoGift/list?shelfStatus=1&isLottery=1')
									    },
							            method: 'get',
							            pageList: [5,10,50,100],
										pageSize: 5,
							            columns: [[
							                {field:'id',title:'id',width:4},
							                {field:'name',title:'名称',width:4},
							                {field:'code',title:'code',width:4},
							                {field:'integral',title:'积分',width:4}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
					<td id="wordTd"><input class="easyui-textbox"
						style="width: 200px" name="word" id="word"
						data-options="required:true"></td>
					<th>中奖概率（%）：</th>
					<td><input class="easyui-numberbox" style="width: 200px"
						name="ratio"
						data-options="min:0, max:100, required:true, precision:2"></td>
				</tr>
				<tr>
                    <th>奖品级别：</th>
                    <td><input class="easyui-numberbox" style="width: 200px"
                        name="prizeLevel" id="prizeLevel"
                        data-options="required:true,min:1,max:999,prompt:'1：一等奖  2：二等奖 默认10'"></td>
                </tr>
			</table>
		</form>
	</div>

	<!-- 设置限时抢购礼品-->
	<div id="DivLimit" class="easyui-dialog"
		style="width: 860px; height: 300px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'设置限时抢购礼品',iconCls: 'icon-organ',buttons: '#dlg-buttons'">
		<table class="easyui-datagrid" fit='true' id="limitGrid"
			data-options="rownumbers:true,singleSelect:true,toolbar:toolbarss,fitColumns:true,pagination:true,loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,method:'post'">
			<thead>
				<tr>
					<th data-options="field:'typeId',width:100">活动id</th>
					<th data-options="field:'typeName',width:100">活动名称</th>
					<th data-options="field:'giftId',width:100">礼品id</th>
					<th data-options="field:'giftName',width:100">礼品名称</th>
					<th data-options="field:'integral',width:100">原积分</th>
					<th data-options="field:'preferentialIntegral',width:100">限时特惠积分</th>
					<th data-options="field:'level',width:100">展示排序</th>
					<th data-options="field:'status',width:100"
						formatter="formatStatus">状态</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 添加和修改限时抢礼品的弹出层 -->
	<div id="DivLimitGift" class="easyui-dialog"
		style="width: 800px; height: 210px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'设置限时抢礼品',iconCls: 'icon-add',
			buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        saveAndUpdateLimitGift();
                    }
                },{
                    text:'关闭',
                     iconCls:'icon-cancel',
                    handler:function(){
                        $('#DivLimitGift').dialog('close');
                    }
                }]">
		<form id="ffLimitGift" method="post" novalidate="novalidate">
			<input type="hidden" name="id"><input type="hidden"
				name="integral" id="integralLimit"><input type="hidden"
				name="type" id="typeLimit">
			<table class="view">
				<tr>
					<th>活动id：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="typeId" id="typeIdLimit" data-options="required:true"></td>
					<th>展示排序：</th>
					<td><input class="easyui-numberbox" style="width: 200px"
						name="level" id="level"
						data-options="required:true,min:0,max:999,prompt:'由小到大 默认999'"></td>
				</tr>
				<tr>
					<th>选择礼品：</th>
					<td><div id="tbb" style="padding: 2px 5px;">
							礼品ID：<input class="easyui-textbox" style="width: 100px"
								id="searchId2"> 礼品名称：<input class="easyui-textbox"
								style="width: 110px" id="searchName2"> <a
								href="javascript:searchName(2)" class="easyui-linkbutton"
								iconCls="icon-search">查询</a>
						</div>
						<select class="easyui-combogrid" name="giftId" id="giftIdLimit"
						style="width: 200px"
						data-options="
							            panelWidth: 430,
							            panelHeight: 220,
							            toolbar:'#tbb',
							            idField: 'id',
							            editable:false,
							            required:true,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            pagination:true,
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/gift/lingbaoGift/list?shelfStatus=1&isLottery=1')
									    },
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:4},
							                {field:'name',title:'名称',width:4},
							                {field:'code',title:'code',width:4},
							                {field:'integral',title:'积分',width:4}
							            ]],
							            pageList: [5,10,50,100],
										pageSize: 5,
							            onChange:function(newValue,oldValue){
							            	getIntegral(newValue);
							            },
							            fitColumns: true
							        ">
					</select></td>
					<th>原积分：</th>
					<td><span id="organIntegral"></span></td>
				</tr>
				<tr>
					<th>特惠积分：</th>
					<td><input class="easyui-numberbox" style="width: 200px"
						name="preferentialIntegral" id="preferentialIntegral"
						data-options="required:true,min:1,max:9999"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>