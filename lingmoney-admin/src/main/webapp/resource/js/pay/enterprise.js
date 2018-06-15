var toolbar = [{
	text : '添加新账户',
	iconCls : 'icon-add',
	handler : function() {
		add();
	}
},'-',{
	text : '删除',
	iconCls : 'icon-remove',
	handler : function() {
		del();
	}
}, '-',{
	text : '提现',
	iconCls : 'icon-add',
	handler : function() {
		tixianShow();
	}
}, '-',{
	text : '充值',
	iconCls : 'icon-remove',
	handler : function() {
		chongzhiShow();
	}
},'-',{
	text : '查看账户信息',
	iconCls : 'icon-reload',
	handler : function() {
		findInfo();
	}
},'-',{
	text : '绑定银行卡',
	iconCls : 'icon-man',
	handler : function() {
		bindBank();
	}
}];
function add(){
	$('#addForm').form('clear');
	$('#addForm').form('load',{
		platformNo:'10012469957'
	});
	$("#DivAddView").dialog('open');
}
function save(){
	$('#addForm').submit();
	$("#DivAddView").dialog('close');
	$.messager.alert("提示信息",'第三方支付网关操作，关闭后请刷新','info');
}
function del(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return;
	}
	if(rows[0].status!=0){
		 $.messager.alert("提示", "已提交状态，不能删除", "error");
		 return;
	}
	showLoading();
	$.getJSON("/rest/pay/delEnterprise?r=" + Math.random() + "&id=" + rows[0].platformuserno,
	    function(info) {
	       if(info.code==200){
	    	   $.messager.alert('提示', '删除成功', 'info');
	    	   $("#adGrid").datagrid('reload');
	       }else{
	    	   $.messager.alert('错误提示', info.msg, 'error');
	       }
	});
	closeLoading();
}
//查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		enterprisename:$("#senterpriseName").val()
	});
}
function findInfo(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.getJSON("/rest/pay/findInfo?r=" + Math.random() + "&platformUserNo=" + rows[0].platformuserno+"&bankNo="+rows[0].bankNo+"&status="+rows[0].status,
	    function(info) {
	       if(info.code==200){
	    	   showInfo(info.result);
	    	   if(info.reload){
	    		   $("#adGrid").datagrid('reload');
	    	   }
	       }else{
	    	   $.messager.alert('错误提示', info.msg, 'error');
	       }
	});
	closeLoading();
}
/**
 * 显示提现的窗口
 */
function tixianShow(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	if(!rows[0].bankNo || rows[0].bankNo==""){
		$.messager.alert("提示", "请先绑定银行卡，如果已绑定，请先查看账户信息", "error");
	    return
	}
	$('#tixianForm').form('clear');
	$('#tplatformUserNo').val(rows[0].platformuserno);
	$("#DivTixianView").dialog('open');
}
function doTixian(){
	if($("#tmoney").numberbox("isValid")){
		$("#DivTixianView").dialog('close');
		$.messager.alert('提示信息','提现操作提交到第三方支付平台，点击确定关闭窗口','info');
		$('#tixianForm').submit();
	}
}
/**
 * 充值窗口显示
 */
function chongzhiShow(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$('#chongzhiForm').form('clear');
	$('#cplatformUserNo').val(rows[0].platformuserno);
	$("#DivChongzhiView").dialog('open');
}
/**
 * 做充值
 */
function doChongzhi(){
	if($("#cmoney").numberbox("isValid")){
		$("#DivChongzhiView").dialog('close');
		$.messager.alert('提示信息','充值操作提交到第三方支付平台，点击确定关闭窗口','info');
		$('#chongzhiForm').submit();
	}
}
/**
 * 把那规定银行卡
 */
function bindBank(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$.messager.alert('提示信息','绑卡操作提交到第三方支付平台，点击确定关闭窗口','info');
	$("#bplatformUserNo").val(rows[0].platformuserno);
	$("#bindBank").submit();
}
/**
 * 查看账户信息
 * @param info
 */
function showInfo(info){
	$("#vbalance").html(info.balance);
	$("#vavailableAmount").html(info.availableAmount);
	$("#vfreezeAmount").html(info.freezeAmount);
	$("#vcardNo").html(info.cardNo);
	if(info.activeStatus == 'ACTIVATED'){
		$("#vactiveStatus").html("已激活");
	}
	if(info.activeStatus == 'DEACTIVATED'){
		$("#vactiveStatus").html("未激活");
	}
//	$("#adGrid").datagrid('reload');
	$("#DivInfoView").dialog('open');
}