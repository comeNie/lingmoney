<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>赠送优惠券</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript">
	$(function(){
		$("#dataGridRedPacket").datagrid({
			type : 'POST',
			url : '/rest/hxRedPacket/redPacketList'
		});
	});

	// 用户购买记录操作工具栏
	var toolbar = [ {
		text : '批量赠送优惠券',
		iconCls : 'icon-add',
		handler : function() {
			present();
		}
	} ];
	var toolbar2 = [ {
		text : '赠送',
		iconCls : 'icon-ok',
		handler : function() {
			give();
		}
	} ];
	
	// 格式化时间
	function formatTimer(value, row) {
		if (value) {
			return new Date(value).format("yyyy-MM-dd hh:mm:ss");
		} else {
			return "";
		}
	}

	// 通过产品id查询产品及购买记录
	var dataGridTrading;
	function search() {
		$("#dataGridProduct").datagrid(
				{
					type : 'POST',
					url : '/rest/product/product/list?id='
							+ $('#pId').combogrid('getValue')
				});
		dataGridTrading = $("#dataGridTrading").datagrid({
			type : 'POST',
			url : '/rest/hxRedPacket/buyer?id='
					+ $('#pId').combogrid('getValue')
		});
	}

	function clear() {
		$('#searchForm').form('clear')
	}

	function give(){
		var rows = $("#dataGridRedPacket").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert("提示", "请选择一条记录", "error");
			return;
		}
		
		var uIds = '';
		var _uNames = '';
		var tradingRows = dataGridTrading.datagrid('getRows');
		for(var i=0;i<tradingRows.length;i++){
			
			if(uIds.indexOf(tradingRows[i].uId) != -1){
				continue;
			}
			// 理财金额要求
			var a = rows[0].requestedAmount;
			// trading表的理财金额
			var b = tradingRows[i].financialMoney;
			// 活动开始时间
			var c = rows[0].startTime;
			// 用户购买时间 
			var d = tradingRows[i].buyTime;
			
			// 过滤不符合的用户
// 			if(b >= a && d >= c){
				uIds += tradingRows[i].uId;
				if(i < tradingRows.length-1){
					uIds += ','
				}
// 			}else{
// 				_uNames += tradingRows[i].userName;
// 				if(i < tradingRows.length-1){
// 					_uNames += ','
// 				}
// 			}
		}
		
		$.post('/rest/hxRedPacket/give',{'uIds':uIds,'rpId':rows[0].id},function(info){
			if (info.code == 200) {
				$("#redPacketWin").dialog('close');
				dataGridTrading.datagrid("reload")
				var returnMsg = "赠送成功";
				if(_uNames != ''){
					returnMsg = "部分用户不符合赠送要求：" + _uNames; 
				}
				$.messager.alert('成功提示', returnMsg, 'info');
			} else {
				$.messager.alert('错误提示', info.msg, 'error');
			}
		})
	}
	
	function searchPro() {
		$('#pId').combogrid('grid').datagrid('load', {
			page : 1,
			id : $('#proId').val(),
			name : $('#proName').val()
		});
	}
	function present(){
		if(dataGridTrading == undefined){
			$.messager.alert('提示', '请选择产品', 'info');
			return;
		}
		
		if(dataGridTrading.datagrid('getRows').length == 0){
			$.messager.alert('提示', '没有需要赠送的用户', 'info');
			return;
		}
		$("#redPacketWin").dialog('open');
	}
</script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',iconCls:'icon-book'"
			style="padding: 5px; height: 300px">
			<form id="searchForm">
				<table>
					<tr>
						<td>选择产品：</td>
						<td>
							<div id="tb" style="padding: 2px 5px;">
								产品id：<input class="easyui-textbox" style="width: 80px"
									id="proId"
									data-options="prompt:'产品id',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#proId').textbox('setValue', '');
											}
										}]">
								产品名称： <input class="easyui-textbox" style="width: 80px"
									id="proName"
									data-options="prompt:'产品名称',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#proName').textbox('setValue', '');
											}
										}]">
								<a href="javascript:searchPro()" class="easyui-linkbutton"
									iconCls="icon-search">查询</a>
							</div> <select class="easyui-combogrid" id="pId" name="pId"
							style="width: 180px"
							data-options="
							            panelWidth: 400,
							            panelHeight: 'auto',
							            prompt:'请选择产品',
							            toolbar:'#tb',
							            idField: 'id',
							            editable:false,
							            pagination:true,
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/product/product/list')
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
												$('#pId').combogrid('setValue', '');
											}
										}]
							        ">
						</select>
						</td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							iconcls="icon-search">查询</a></td>
						<td><a href="javascript:clear()" class="easyui-linkbutton"
							iconcls="icon-undo">清空</a></td>
					</tr>
				</table>
			</form>

			<table class="easyui-datagrid" title="产品" id="dataGridProduct"
				data-options="singleSelect:true,fitColumns:false,pagination:false,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,method:'post'">
				<thead>
					<tr>
						<th data-options="field:'name',width:200">产品名称</th>
						<th data-options="field:'fTime',width:100">固定期限</th>
						<th
							data-options="field:'unitTime',width:100,
			            					formatter:function(value){
										        if(value==0)
										                return '天';
										        else if(value==1)
										                return '周';
										        else if(value ==2 )
										        		return '月';
										        else if(value ==3 )
										        		return '年';
								        		else if(value ==4 )
								        			return '无';		
										        }">单位时间类型</th>
						<th data-options="field:'stDt',width:100" formatter="formatTimer">起始时间</th>
						<th data-options="field:'edDt',width:100" formatter="formatTimer">结束时间</th>
						<th data-options="field:'priorMoney',width:100">筹备金额</th>
						<th data-options="field:'reachMoney',width:100">已筹金额</th>
						<th
							data-options="field:'approval',width:100,
			            					formatter:function(value){
										        if(value==0)
										                return '初始状态';
										        else if(value==1)
										                return '提交状态';
										        else if(value==2)
										                return '审核通过状态';
										        }">审批状态</th>
						<th
							data-options="field:'status',width:100,
			            					formatter:function(value){
										        if(value==1)
										                return '项目筹集期';
										        else if(value==2)
										                return '项目运行中';
										        else if(value==3)
										                return '项目汇款中';
								                else if(value==4)
										                return '项目结束';
								                else if(value==5)
										                return '项目作废';
										        }">产品状态</th>
						<th data-options="field:'pcName',width:100">产品分类名称</th>
						<th data-options="field:'platformUserNo',width:100">借款账户</th>
						<th data-options="field:'costValue',width:100">平台佣金</th>
						<th
							data-options="field:'pModel',width:100,formatter:function(value){
										        if(value==0)
										                return '产品模式';
										        else if(value==1)
										                return '钱包模式';
										        else if(value==2)
										                return '新手标模式';
										        }">模式</th>
						<th
							data-options="field:'pType',width:100,formatter:function(value){
										        if(value==0)
										                return '领钱儿';
										        else if(value==1)
										                return '中粮';
										        else if(value==2)
										                return '华兴银行';
										        }">产品所属</th>
					</tr>
				</thead>
			</table>

		</div>
		
		<div class="easyui-panel"
			data-options="region:'center',title:'购买记录',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<table class="easyui-datagrid" title="购买记录" id="dataGridTrading"
				data-options="toolbar:toolbar,singleSelect:true,fitColumns:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,method:'post'">
				<thead>
					<tr>
						<th data-options="field:'userName',width:150,align:'center'">用户姓名</th>
						<th data-options="field:'productName',width:150,align:'center'">产品名称</th>
						<th data-options="field:'financialMoney',width:150,align:'center'">理财金额</th>
						<th data-options="field:'buyTime',width:50,align:'center'" formatter="formatTimer">购买时间</th>
					</tr>
				</thead>
			</table>
		</div>

		<div id="redPacketWin" class="easyui-dialog"
		style="width: 830px; height: 350px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="buttons: '#dlg-buttons','title':'选择红包'">
			<table class="easyui-datagrid" title="红包列表" id="dataGridRedPacket"
				data-options="toolbar:toolbar2,singleSelect:true,fitColumns:true,loadMsg:'请稍后，正在加载数据...',idField:'id',method:'post'">
				<thead>
					<tr>
						<th
							data-options="field:'hrpType',width:30,align:'center',formatter:function(value){
										        if(value==1)
										                return '加息券';
										        else if(value==2)
										                return '红包';
										        }">类型</th>
						<th
							data-options="field:'activityType',width:30,align:'center',formatter:function(value){
										        if(value==1)
										                return '绑卡';
										        else if(value==2)
										                return '理财';
										        }">活动类型</th>				 
										        
						<th data-options="field:'hrpNumber',width:30,align:'center'">赠送金额或加息比例</th>
						<th data-options="field:'requestedAmount',width:30,align:'center'">理财金额要求</th>
						<th data-options="field:'startTime',width:50,align:'center'" formatter="formatTimer">活动开始时间</th>
						<th data-options="field:'endTime',width:50,align:'center'" formatter="formatTimer">活动结束时间</th>
						<th data-options="field:'validityTime',width:50,align:'center'" formatter="formatTimer">有效期</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

</body>
</html>