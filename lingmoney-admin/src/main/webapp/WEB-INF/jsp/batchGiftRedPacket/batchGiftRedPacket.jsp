<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>批量赠送优惠券</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
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
	
	function searchList(){
		var channel = $("#channel").combobox("getValue");
		var type = $("#selectType").combobox("getValue");
		var startDate = $("#startDate").datebox('getValue');
		var endDate = $("#endDate").datebox('getValue');
		var productName = $("#productName").val();
		
		if (null == channel || channel.trim() == "") {
			$.messager.alert('错误', '渠道不能为空', 'error');
			return false;
		}
		
		if (null != type && type.trim() != "") {
			if (null == startDate || startDate.trim() == "") {
				$.messager.alert('错误', '查询开始时间不能为空', 'error');
				return false;
			}			
			if (null == endDate || endDate.trim() == "") {
				$.messager.alert('错误', '查询结束时间不能为空', 'error');
				return false;
			}			
			if (null == productName || productName.trim() == "") {
				$.messager.alert('错误', '产品名称不能为空', 'error');
				return false;
			}			
		}
		
		dataGrid = $('#dataGrid').datagrid({
			url : '/rest/batchGift/findUsersByParams',
			queryParams:{
				channel : channel,
				type : type,
				startDate : startDate,
				endDate : endDate,
				productName : productName
			},
			traditional : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			// fitColumns : true,
			selectOnCheck : false,
			pageSize : 20,
			pageList : [ 10, 20, 30 ],
			onLoadSuccess: function(data) {
				if (data.code != 200) {
					$.messager.alert('错误', data.msg, 'error');
					return false;
				}
			},
			columns : [ [
					{ width : '150', align : 'center', title : 'uid', field : 'uid'},
					{ width : '100', align : 'center', title : '姓名', field : 'name'},
					{ width : '120', align : 'center', title : '手机号', field : 'telephone' },
					{ width : '120', align : 'center', title : '渠道名称', field : 'channelName' },
					{ width : '150', align : 'center', title : '购买产品名称', field : 'productName' },
					{ width : '150', align : 'center', title : '购买时间', field : 'buyDate' },
					{ width : '100', align : 'center', title : '购买金额', field : 'buyMoney' },
					{ width : '150', align : 'center', title : '最小兑付时间', field : 'minSellDt' }
			] ]
		});
	}
	
	// 赠送人ID组，多个用英文逗号分隔
	var uids = "";
	function gift() {
		//初始值
		uids = "";
		var rows = $('#dataGrid').datagrid('getSelections');
		if (rows.length == 0) {
		    $.messager.alert('提示', '请选择一条记录', 'error');
		    return;
		}
		
		$('#giveRedPacketDiv').dialog('open');
		
		var html = "<tr><th>姓名</th><th>手机号</th><th>渠道名称</th></tr>";
		for (var i = 0; i < rows.length; i++) {
			html += "<tr><td>"+ rows[i].name +"</td><td>"+ rows[i].telephone +"</td><td>"+ rows[i].channelName +"</td></tr>";
			if (i == rows.length - 1) {
				uids += rows[i].uid;
			} else {
				uids += rows[i].uid + ",";
			}
		}
		$('#giveRedPacketList').empty().append(html);
	}
	
	function confirmGiveRedPacket() {
		var rId = $('#redPacketId').combogrid('getValue')
		if (rId == null || rId == '') {
			$.messager.alert('提示', '请选择一张加息券');
			return false;
		}
		
		$.messager.confirm('提示', '确认赠送加息券?', function(b) {
			if(b) {
				$.ajax({
					url : '/rest/batchGift/giveRedPacketByUid',
					data : {rId : rId, uids : uids},
					type : 'POST',
					success : function (data) {
						$.messager.alert('提示', data.msg);
						$('#giveRedPacketDiv').dialog('close');
					}
				});
			}
		});
	}
	
	function cancelGiveRedPacket() {
		$('#giveRedPacketDiv').dialog('close');
	}
	
</script>
 
</head>
<body class="easyui-layout" style="font-size:16px;" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: auto; overflow: hidden; background-color: #fff">
		<form id="searchForm">
			<div>
				<table>
					<tr>
						<td>渠道：</td>
						<td>
							<select name="channel" id="channel" data-options="panelHeight:'auto',editable:false" style="width:152px" class="easyui-combobox" required="true">
	                    		<option value="">请选择</option>
	                    		<option value="5">对外注册H5</option>
	                    		<option value="6">派诺全景-短信群发</option>
	                    		<option value="7">微信引流注册</option>
	                    		<option value="8">今日头条</option>
	                    		<option value="9">应用宝</option>
	                    		<option value="10">百度手机助手</option>
	                    		<option value="11">oppo应用商店</option>
	                    		<option value="12">华为应用市场</option>
	                    		<option value="13">360手机助手</option>
	                    		<option value="14">小米应用商店</option>
	                    		<option value="15">vivo应用商店</option>
	                    		<option value="16">pp助手</option>
	                    		<option value="17">豌豆荚</option>
	                    		<option value="18">酷派应用商店</option>
	                    		<option value="19">三星应用商店</option>
	                    		<option value="20">安卓市场</option>
	                    		<option value="21">91助手</option>
	                    		<option value="22">安智市场</option>
	                    		<option value="23">魅族应用商店</option>
	                    		<option value="25">口粮</option>
	                    		<option value="26">中影票务通</option>
	                    		<option value="27">魔积分</option>
	                    		<option value="28">骆驼管家</option>
	                    		<option value="29">天眼投</option>
	                    		<option value="30">挖福利</option>
	                    	</select>			
						</td>
						<td>查询类型：</td>
						<td>
							<select name="selectType" id="selectType" data-options="panelHeight:'auto',editable:false" style="width:152px" class="easyui-combobox">
	                    		<option value="">请选择</option>
	                    		<option value="1">购买某产品</option>
	                    		<option value="2">兑付某产品</option>
	                    	</select>			
						</td>
					</tr>
					<tr>
						<td>查询开始时间:</td>
						<td>
							<input class="easyui-datetimebox" id="startDate" name="startDate" style="width:152px"/>
						</td>
						<td>查询结束时间:</td>
						<td>
							<input class="easyui-datetimebox" id="endDate" name="endDate" style="width:152px"/>
						</td>
						<td>产品名称：</td>
						<td><input class="easyui-textbox" name="productName"
							id="productName" style="width: 200px"
							data-options="prompt:'请输入产品名称',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#productName').textbox('setValue', '');
											}
										}]" />
						</td>
					</tr>
					
				</table>
				<div>
					<a href="javascript:void(0);" class="easyui-linkbutton" 
						data-options="iconCls:'icon-search'" onclick="searchList();">查询列表</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" 
						data-options="iconCls:'icon-search'" onclick="gift();">赠送优惠券</a>
				</div>
			</div>
		</form>
	</div>
	<div data-options="region:'center',border:false"
		style="width: 550px; height: 350px; overflow: hidden;">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	
	<!-- 赠送加息券弹框 S -->
	<div id="giveRedPacketDiv" class="easyui-dialog"
		style="width: 350px; height: 300px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'赠送加息券',iconCls: 'icon-add', buttons: [{text:'确定', handler: confirmGiveRedPacket}, {text:'取消', handler: cancelGiveRedPacket}]">
		
		请选择加息券:<select id="redPacketId" class="easyui-combogrid" name="dept" style="width:200px;"   
				        data-options="    
				            panelWidth:300,    
				            idField:'id',    
				            textField:'hrpNumber',    
				            url:'/rest/hxRedPacket/manualRedPacketList',    
				            columns:[[    
				                {field:'hrpType',title:'名称',width:60},    
				                {field:'hrpNumber',title:'加息率',width:60}, 
				                {field:'hrpRemarks',title:'描述信息'}   
				            ]]    
				        ">
				    </select>  
				    
		<p>赠送人员列表：</p>
		<table id="giveRedPacketList" style="width:250px;overflow-x: scroll;" cellpadding="5px;">
			
		</table>
	</div>
	
</body>

</html>

