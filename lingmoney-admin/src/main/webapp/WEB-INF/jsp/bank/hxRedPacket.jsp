<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>华兴红包</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript">
var toolbar = [ {
	text : '添加',
	iconCls : 'icon-add',
	handler : function() {
		add();
	}
}, '-', {
	text : '修改',
	iconCls : 'icon-edit',
	handler : function() {
		edit();
	}
}, '-', {
	text : '发布',
	iconCls : 'icon-ok',
	handler : function() {
		publish();
	}
}, '-', {
	text : '作废',
	iconCls : 'icon-cancel',
	handler : function() {
		del();
	}
}];
var dataGrid;
$(function() {
    dataGrid = $('#dataGrid').datagrid({
        url : '/rest/hxRedPacket/list',
        height : 455,
        title : '华兴红包',
        border : false,
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        pageSize : 10,
        pageList : [ 10, 15, 20 ],
        columns : [ [ 
        	{width : '20', title : '数据ID', field : 'id'},
        	{width : '60', title : '类型', field : 'hrpType',
            	formatter : function(value, row, index) {
            		switch (value) {
					case 1:
						return '加息券';
					case 2:
						return '返现红包';
					}
	            }
            },
			{width : '120', title : '名称', field : 'hrpName'},         
            {width : '80', title : '赠送金额/比例', field : 'hrpNumber'},
            {width : '60', align : 'center', title : '状态', field : 'status', 
	            formatter : function(value, row, index) {
	            	switch (value) {
					case 0:
						return '初始化';
					case 1:
						return '已发布';
					case 2:
						return '已作废';
					}
            }},
            {width : '80', title : '获取方式', field : 'acquisMode',
            	formatter : function(value, row, index) {
            		switch (value) {
					case 0:
						return '手动';
					case 1:
						return '注册';
					case 2:
						return '开通E账户';
					case 3:
						return '激活E账户';
					case 4:
						return '首次理财';
					case 5:
						return '理财';
					}
	            }
            },
            {width : '130', title : '赠送开始时间', field : 'aStartTime',
            	formatter : function(value, row, index) {
            		return formatDate(value)
	            }
            }, 
            {width : '130', title : '赠送结束时间', field : 'aEndTime',
            	formatter : function(value, row, index) {
            		return formatDate(value)
            }},
            {width : '150', title : '赠送产品批次', field : 'aInvestProBatch'},   
            {width : '80', title : '赠送产品类型', field : 'aInvestProType'},   
            {width : '80', title : '赠送产品期限', field : 'aInvestProDay'},   
            {width : '80', title : '赠送理财金额', field : 'aInvestProAmount'},   
            {width : '130', title : '有效期', field : 'validityTime',
            	formatter : function(value, row, index) {
            		return formatDate(value)
            }},
            {width : '60', title : '延后天数', field : 'delayed'},   
            {width : '150', title : '使用产品批次', field : 'uInvestProBatch'},   
            {width : '80', title : '使用产品类型', field : 'uInvestProType'},   
            {width : '80', title : '使用产品期限下限', field : 'uInvestProDay'},
            {width : '80', title : '使用产品期限上限', field : 'uInvestProMixday'},     
            {width : '80', title : '使用理财金额', field : 'uInvestProAmount'},
            {width : '400', title : '使用备注', field : 'hrpRemarks'},
            {width : '400', title : '文档说明', field : 'document'}
        ] ],
        toolbar : toolbar
    });
    $('input[name="aInvestProType"]').combobox({    
        url:'/rest/hxRedPacket/productTypeList', 
        method:'post',
        valueField:'id',
        panelHeight:'auto',
  	    textField:'name',
  	    editable:false,
  	    multiple:true,
  	    onSelect:function(){
  	    	$("#aInvestProBatch").textbox('setValue', '');
	  		$("#aInvestProBatch").textbox('disable');
	  	}
  	});
    $('input[name="uInvestProType"]').combobox({    
        url:'/rest/hxRedPacket/productTypeList', 
        method:'post',
        valueField:'id',
        panelHeight:'auto',
  	    textField:'name',
  	    editable:false,
  	    multiple:true,
  	    onSelect:function(){
  	    	$("#uInvestProBatch").textbox('setValue', '');
	  		$("#uInvestProBatch").textbox('disable');
	  	}
  	});
});

function del() {
	var rows = $("#dataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return;
	}
	
	showLoading();
	$.messager.confirm('确认', '您确认想要作废吗？', function(r) {
		if (r) {
			$.post('/rest/hxRedPacket/delete',{"id":rows[0].id},function(info){
				if (info.code == 200) {
					$.messager.alert('提示', '操作成功', 'info');
					$("#dataGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
		}
	});
	closeLoading();
}

function search() {
	dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}
//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	
	$("#hrpType").combobox('select',1)
	$("#acquisMode").combobox('select',1)
	$("#aInvestProType").combobox('enable');
	$("#aInvestProDay").textbox('enable');
	$("#aInvestProBatch").textbox('enable');
	$("#uInvestProType").combobox('enable');
	$("#uInvestProDay").textbox('enable');
	$("#uInvestProMixday").textbox('enable');
	$("#uInvestProBatch").textbox('enable');
	$("#DivAdd").dialog('open');
	$("#DivAdd").dialog('setTitle','添加');
}


function edit(){
	var rows = $("#dataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return;
	}
	if(rows[0].status == 1){
		$.messager.alert("提示", "已发布，不能编辑", "error");
		return;
	}
	if(rows[0].status == 2){
		$.messager.alert("提示", "已作废，不能编辑", "error");
		return;
	}
	
	$('#ffAdd').form('clear');
	$("#id").val(rows[0].id)
	$("#hrpType").combobox('select',rows[0].hrpType)
	$("#hrpName").textbox('setValue',rows[0].hrpName)
	$("#acquisMode").combobox('select',rows[0].acquisMode)
	$("#hrpNumber").textbox('setValue',rows[0].hrpNumber)
	$("#aInvestProDay").textbox('setValue',rows[0].aInvestProDay)
	$("#aInvestProType").textbox('setValue',rows[0].aInvestProType)
	$("#aInvestProBatch").textbox('setValue',rows[0].aInvestProBatch)
	
	$("#aInvestProAmount").textbox('setValue',rows[0].aInvestProAmount)
	$("#uInvestProAmount").textbox('setValue',rows[0].uInvestProAmount)
	$("#aStartTime").datetimebox('setValue',formatDate(rows[0].aStartTime))
	$("#aEndTime").datetimebox('setValue',formatDate(rows[0].aEndTime))
	$("#validityTime").datetimebox('setValue',formatDate(rows[0].validityTime))
	$("#delayed").textbox('setValue',rows[0].delayed)
	$("#hrpRemarks").textbox('setValue',rows[0].hrpRemarks)
	$("#uInvestProDay").textbox('setValue',rows[0].uInvestProDay)
	$("#uInvestProMixday").textbox('setValue',rows[0].uInvestProMixday)
	$("#uInvestProType").textbox('setValue',rows[0].uInvestProType)
	$("#uInvestProBatch").textbox('setValue',rows[0].uInvestProBatch)
	$("#document").textbox('setValue',rows[0].document)
	
	$("#DivAdd").dialog('setTitle','编辑');
	$("#DivAdd").dialog('open');
}

function save(){
	showLoading();
	var remark = $("#hrpRemarks").val();
	if(!verifyRemark(remark)){
		closeLoading();
		return;
	}
	
	$('#ffAdd').form('submit',{
		url: '/rest/hxRedPacket/saveOrEdit',
		ajax:true,
		type:"psot",
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivAdd").dialog('close');
				dataGrid.datagrid('reload');
			}else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

function publish(){
	var rows = $("#dataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return;
	}
	if(rows[0].status == 1){
		$.messager.alert("提示", "已发布，不能重复发布", "error");
		return;
	}
	if(rows[0].status == 2){
		$.messager.alert("提示", "已作废，不能发布", "error");
		return;
	}
	showLoading();
	
	$.messager.confirm('确认', '您确认要发布吗？', function(r) {
		if(r){
			$.post('/rest/hxRedPacket/publish',{"id":rows[0].id},function(info){
				if(info.code == 200){
					$.messager.alert("提示", "操作成功", "info");
					dataGrid.datagrid('reload');
				}else{
					$.messager.alert("提示", info.msg, "error");
				}
			},'json');
		}
	});
	closeLoading();
	
}

function formatDate(v){
	if(v != null && v != ''){
		var d = new Date(v)
		return d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds()
	}
}
function clear(){
	$('#searchForm').form('clear')
}
function verifyRemark(str){
     try {
         JSON.parse(str);
         return true;
     } catch(e) {
    	 $.messager.alert("提示", "备注格式错误", "info");
         return false;
     }
}
</script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',iconCls:'icon-book'"
			style="padding: 5px; height: 50px">
			<form id="searchForm">
				<table>
					<tr>
						<td>类型：</td>
						<td>
							<select name="hrpType" data-options="panelHeight:'auto',editable:false" style="width:60px" class="easyui-combobox">
	                    		<option value=""></option>
	                    		<option value="1">加息券</option>
	                    		<option value="2">返现红包</option>
	                    	</select>			
						</td>
						<td>获取方式：</td>
						<td>
							<select name="acquisMode" data-options="panelHeight:'auto',editable:false" style="width:60px" class="easyui-combobox">
	                    		<option value=""></option>
	                    		<option value="0">手动发放</option>
	                    		<option value="1">注册</option>
	                    		<option value="2">开通E账户</option>
	                    		<option value="3">激活E账户</option>
	                    		<option value="4">首次理财</option>
	                    		<option value="5">理财</option>
	                    	</select>			
						</td>
						<td>状态：</td>
						<td>
							<select id="status" class="easyui-combobox" name="status" style="width:70px;" 
								data-options="editable:false, panelHeight:'auto'"> 
							    <option value=""></option>   
							    <option value="0">初始化</option>   
							    <option value="1">已发布</option>   
							    <option value="2">已作废</option> 
							</select>  
						</td>
						<td><a href="javascript:search()" class="easyui-linkbutton" iconcls="icon-search">查询</a></td>
						<td><a href="javascript:clear()" class="easyui-linkbutton" iconcls="icon-undo">清空</a></td>	
					</tr>
				</table>
			</form>
		</div>
		
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table id="dataGrid" data-options="fit:true,border:false"></table>
		</div>
	</div>
	<!-- 添加弹出层 -->
	<div id="DivAdd" class="easyui-dialog" style="width: 700px; height: 385px; padding: 10px 20px" closed="true" resizable="true" modal="true"
		data-options="buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate" enctype="multipart/form-data">
			<input type="hidden" name="id" id="id">
			<table id="tblAdd" class="view">
				<tr>
					<td >类型：</td>
					<td>
						<select id="hrpType" name="hrpType" style="width:152px" class="easyui-combobox" data-options="panelHeight:'auto',editable:false">
                    		<option value="1" >加息券</option>
                    		<option value="2" >返现红包</option>
                    	</select>
					</td>
					<td >名称：</td>
					<td>
						<input class="easyui-textbox" name="hrpName" id="hrpName" style="width:152px" data-options="required:true"/>			
					</td>
				</tr>
				<tr>
					<td >金额/加息比例：</td>
					<td>
						<input class="easyui-textbox" name="hrpNumber" id="hrpNumber"
						style="width: 152px"
						data-options="prompt:'请输入数量',required:true,
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#hrpNumber').textbox('setValue', '');
									}
								}]" />
					</td>
					<td >获取方式：</td>
					<td>
						<select id="acquisMode" name="acquisMode" style="width:152px" data-options="panelHeight:'auto',editable:false" class="easyui-combobox">
								<option value=""></option>
	                    		<option value="0">手动发放</option>
	                    		<option value="1">注册</option>
	                    		<option value="2">开通E账户</option>
	                    		<option value="3">激活E账户</option>
	                    		<option value="4">首次理财</option>
	                    		<option value="5">理财</option>
                    	</select>			
					</td>
				</tr>
				<tr>
					<td>赠送起始时间：</td>
					<td>
						<input id="aStartTime" class="easyui-datetimebox" name="aStartTime" 
						data-options="editable:false,required:true," style="width:152px"/>
					</td>
					<td>赠送结束时间：</td>
					<td>
						<input id="aEndTime" class="easyui-datetimebox" name="aEndTime" 
						data-options="editable:false,required:true," style="width:152px"/>
					</td>
				</tr>
				<tr><td colspan="4" style="color: red;">赠送的开始结束时间，不在期间内不能赠送</td></tr>
				
				<tr>
					<td >赠送产品批次：</td>
					<td>
						<input class="easyui-textbox" name="aInvestProBatch" id="aInvestProBatch"
						style="width: 152px"
						data-options="prompt:'批次1,批次2...',
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#aInvestProBatch').textbox('setValue', '');
									}
								}]" />
					</td>
					<td >赠送产品类型：</td>
					<td><input name="aInvestProType" id="aInvestProType" style="width:152px"/></td>
				</tr>
				<tr>
					<td >赠送产品期限：</td>
					<td>
						<input class="easyui-textbox" name="aInvestProDay" id="aInvestProDay"
						style="width: 152px"
						data-options="prompt:'请输入赠送产品期限',
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#aInvestProDay').textbox('setValue', '');
									}
								}]" />
					</td>
					<td>赠送理财金额：</td>
					<td>
						<input class="easyui-textbox" name="aInvestProAmount" id="aInvestProAmount"
						style="width: 152px" />
					</td>
				</tr>
				<tr><td colspan="4" style="color: red;">指定赠送的条件，先判断产品批次，如果为空，在判断产品类型，产品期限，理财金额，当产品批次不为空，判断期限，理财金额</td></tr>
				
				<tr>				
					<td >使用产品批次：</td>
					<td>
						<input class="easyui-textbox" name="uInvestProBatch" id="uInvestProBatch"
						style="width: 152px"
						data-options="prompt:'批次1,批次2...',
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#uInvestProBatch').textbox('setValue', '');
									}
								}]" />
					</td>
					<td >使用产品类型：</td>
					<td><input name="uInvestProType" id="uInvestProType" style="width:152px"/></td>
				</tr>
				<tr>
					<td >使用产品期限下限：</td>
					<td>
						<input class="easyui-textbox" name="uInvestProMixday" id="uInvestProMixday"
						style="width: 152px"
						data-options="prompt:'请输入使用产品期限',
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#uInvestProMixday').textbox('setValue', '');
									}
								}]" />
						<!-- ,onChange:function(){$('#productId').combobox('disable')} -->
					</td>
				
					<td >使用产品期限上限：</td>
					<td>
						<input class="easyui-textbox" name="uInvestProDay" id="uInvestProDay"
						style="width: 152px"
						data-options="prompt:'请输入使用产品期限',
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#uInvestProDay').textbox('setValue', '');
									}
								}]" />
						<!-- ,onChange:function(){$('#productId').combobox('disable')} -->
					</td>
				</tr>
				<tr>
					<td>使用理财金额：</td>
					<td>
						<input class="easyui-textbox" name="uInvestProAmount" id="uInvestProAmount"
						style="width: 152px" />
					</td>	
					<td></td>
					<td></td>	
				</tr>
				<tr><td colspan="4" style="color: red;">指定使用的条件，1、指定过期时间。2、指定使用的产品批次号，赠送的时候要计划好，要如何使用，3、使用产品的期限必须大于等于“使用产品期限上限”，4、使用产品的期限必须小于等于“使用产品期限下限”，5、使用产品类型限制，6、使用理财金额限制</td></tr>
				<tr>
					<td>有效期截至：</td>
					<td >
						<input class="easyui-datetimebox" id="validityTime" name="validityTime" data-options="onShowPanel:function(){
						        $(this).datetimebox('spinner').timespinner('setValue','23:59:59');
						    }" style="width:152px"/>
					</td>
					<td>延后天数</td>
					<td>
						<input class="easyui-textbox" name="delayed" id="delayed" style="width: 152px" />
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="3">
						<input class="easyui-textbox" name="hrpRemarks" id="hrpRemarks"
						style="width: 430px" 
						data-options="required:true,icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#hrpRemarks').textbox('setValue', '');
									}
								}],onChange:verifyRemark" />
					</td>
				<tr/>
				<tr>
				<td colspan="4" style="color: red;">备注示例，要显示到前端的使用规则，要想清楚：
					[{&quot;key&quot;:&quot;value&quot;},{&quot;key&quot;:&quot;value&quot;},{&quot;key&quot;:&quot;value&quot;}]</td>
				</tr>
				<tr>
					<td>文档说明：</td>
					<td colspan="3">
						<input class="easyui-textbox" name="document" id="document" style="width: 430px" data-options="prompt:'请输入详细赠送使用说明',required:true" />
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right;">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-ok" onclick="save()">确定</a> <a
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