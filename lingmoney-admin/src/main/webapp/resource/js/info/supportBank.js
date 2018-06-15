var toolbar = [{
	text : '添加支付银行',
	iconCls : 'icon-add',
	handler : function() {
		add();
	}
}
 ,
 '-', {
 text : '修改支付银行',
 iconCls : 'icon-edit',
 handler : function() {
 edit();
 }
 }
, '-', {
	text : '删除支付银行',
	iconCls : 'icon-remove',
	handler : function() {
		del();
	}
}, '-', {
	text : '银行排序',
	iconCls : 'icon-edit',
	handler : function() {
		move();
	}
}];

function formatStatus(value,row){
	if(value == 0){
		return "<font color='green'>显示</font>";
	}else if(value == 1){
		return "不显示";
	}
}

// 删除
function del() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.messager.confirm('确认', '您确认想要删除吗？', function(r) {
		if (r) {
			$.getJSON("/rest/info/supportBank/delete?r=" + Math.random() + "&id=" + rows[0].id, function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '删除成功');
					$("#adGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
		} else {
			return
		}
	});
	closeLoading();
}

// 打开添加对话框
function add() {
	$('#ffAdd').form('clear');
	var result = 0;
	$.ajax({  
	     type : "POST",
	     url : '/rest/info/supportBank/getOrder',
	     success : function(data) { 
	    	 var json = JSON.parse(data);
	    	 $("#DivAdd input[name=bankOrder]").val(json);
	     }  
	 });  
	$("#DivAdd").dialog('open');
	
}
 function edit() {
 var rows = $("#adGrid").datagrid("getSelections");
 if (rows.length == 0) {
 $.messager.alert("提示", "请选择一条记录", "error");
 return
 }
 $("#bankLogo").attr("src",rows[0].bankLogo);
 $("#ffEdit").form('clear');
 $("#ffEdit").form('load', rows[0]);
 $("#DivEdit").dialog('open');
 }
// 保存
function save() {
	/*
	 * alert("后台维护,不支持添加"); return;
	 */
	var bankName = $("#DivAdd input[name=bankName]").val();
	bankName=bankName.replace(/\s+/g,""); 
	if(bankName == null || bankName == ""|| bankName.length==0){
		 $(".bankCodeSpan").html("<font color='red'>银行名称必填项</font>");
		 return;
	}
	else{
		 $(".bankCodeSpan").html("");
	}
	var bankCode = $("#DivAdd input[name=bankCode]").val();
	bankCode=bankCode.replace(/\s+/g,""); 
	if(bankCode == null || bankCode == "" || bankCode.length==0 ||isNaN($("#DivOrder input[name=bankCode]").val())){
		 $(".bankCodeSpan").html("<font color='red'>银行代码必填项</font>");
		 return;
	}
	else{
		 $(".bankCodeSpan").html("");
	}
	var bankShort = $("#DivAdd input[name=bankShort]").val();
	bankShort=bankShort.replace(/\s+/g,""); 
	if(bankShort == null || bankShort == "" || bankCode.length==0){
		 $(".bankCodeSpan").html("<font color='red'>银行简称必填项</font>");
		 return;
	}
	else{
		 $(".bankCodeSpan").html("");
	}
	 var f=$("#DivAdd input[name=bankImg]").val().substring($("#DivAdd input[name=bankImg]").val().lastIndexOf('.'));
     if(f==""){ 
    	 $.messager.alert('错误提示',"请选择上传文件", 'error');
    	 return;
     }
     else
     {
    	 if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG|GIF|JPEG)$/.test(f)){
    		 $.messager.alert('错误提示',"请选择正确的图片格式", 'error');
    		 return;
    	 }
     }
    showLoading();
	var type = $('#supportBankTypeId').combogrid('getText');
	var order = $("#DivAdd input[name=bankOrder").val();
	if(type=="快捷支付"){
		$("#DivAdd input[name=bankType]").val(0);
	}
	else if(type=="网银支付"){
		$("#DivAdd input[name=bankType]").val(1);
	}
	else if(type=="快捷支付和网银支付"){
		$("#DivAdd input[name=bankType]").val(2);
	}
	$("#DivAdd input[name=bankOrder]").val(order);
	$('#ffAdd').form('submit', {
		url : '/rest/info/supportBank/save',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			if (json.code == 200) {
				 $.messager.alert("提示","添加成功");
				$("#DivAdd").dialog('close');
				$("#adGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
// 更新
 function update(){ 
	 // 上传图片
	 /*
		 * var f=$("#DivEdit input[name=bankImg]").val().substring($("#DivEdit
		 * input[name=bankImg]").val().lastIndexOf('.')); if(f!="") {
		 * if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG|GIF|JPEG)$/.test(f)){
		 * $.messager.alert('错误提示',"请选择正确的图片格式", 'error'); return; } }
		 */ 
	 /*
		 * alert("后台维护,不支持编辑"); return;
		 */
	 showLoading();
	 var type = $('#supportBankTypeUpdateId').combogrid('getText');
	 var order = $("#DivEdit input[name=bankOrder").val();
	 if(type=="快捷支付"){ $("#DivEdit input[name=bankType]").val(0); } else
		 if(type=="网银支付"){ $("#DivEdit input[name=bankType").val(1); } else
			 if(type=="快捷支付和网银支付"){ $("#DivEdit input[name=bankType").val(2); }
	 $("#DivEdit input[name=bankOrder]").val(order);
	 $('#ffEdit').form('submit',{ url: '/rest/info/supportBank/update', ajax:true,
		 success:function(data){ 
			 var json = JSON.parse(data); 
			 if(json.code == 200){
				 $.messager.alert("提示","修改成功");
				 $("#DivEdit").dialog('close');
				 $("#adGrid").datagrid('reload');
			 }
			 else{
				 $.messager.alert('错误提示', json.msg, 'error');
			 } 
		} });
	 closeLoading(); 
}
 
// 查询
function search() {
	$('#adGrid').datagrid('load', {
		page : 1,
		bankName : $("#bankName").val()
	});
}
// function move(target,isUp){
function move() {
	var row = $("#adGrid").datagrid("getSelected");
	if(row == null){
		row = $("#adGrid").datagrid("getSelected");
	}
	if (row == null || row.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	$("#DivOrder input[name=bankOrder]").val("");
	$("#DivOrder input[name=id]").val(row.id);
	$("#DivOrder input[name=bankName]").val(row.bankName);
	$("#DivOrder input[name=bankCode]").val(row.bankCode);
	$("#DivOrder input[name=bankShort]").val(row.bankShort);
	$("#DivOrder input[name=bankLogo]").val(row.bankLogo);
	$("#bankOrderShow").val(row.bankOrder);
	if(row.bankType=="0"){
		$("#DivOrder input[name=bankType]").val("快捷支付");
	}
	else if(row.bankType=="1"){
		$("#DivOrder input[name=bankType").val("网银支付");
	}
	else if(row.bankType=="2"){
		$("#DivOrder input[name=bankType").val("快捷支付和网银支付");
	}
	$("#DivOrder").dialog('open');
    /*
	 * var $view = $(target).closest('div.datagrid-view'); var index =
	 * $(target).closest('tr.datagrid-row').attr('datagrid-row-index'); var $row =
	 * $view.find('tr[datagrid-row-index=' + index + ']'); if (isUp) {
	 * $row.each(function() { $(this).prev().before($(this)); }); } else {
	 * $row.each(function() { $(this).before($(this).next()); }); }
	 * $row.removeClass('datagrid-row-over'); e.stopPropagation();
	 */
}
// function formatOper(){
// return '<button onclick="move(this,true)">设置排序</button>';
// }
function updateOrder(){
 	if($("#DivOrder input[name=bankOrder]").val() < 0 || $("#DivOrder input[name=bankOrder]").val()==0||
 			isNaN($("#DivOrder input[name=bankOrder]").val())){
 		$.messager.alert('错误提示', "order需要大于0的数字", 'error');
 		return;
 	}
 	if($("#DivOrder input[name=bankCode]").val()==""){
 		 $(".bankCodeSpan").html("<font color='red'>银行代码必填</font>");
 		return;
 	}
 	$(".bankCodeSpan").html("<font color='red'>银行代码已经存在</font>");
 	showLoading();
	$('#ffOrder').form('submit', {
	url : '/rest/info/supportBank/saveOrder',
	ajax : true,
	success : function(data) {
		var json = JSON.parse(data);
		if (json.code == 200) {
			$("#DivOrder").dialog('close');
			$("#adGrid").datagrid('reload');
		} else {
			$.messager.alert('错误提示', json.msg, 'error');
		}
	}
	});
	closeLoading();
}

function checkBankCode() {
	var bankCode = $("#DivAdd input[name=bankCode]").val();
	bankCode=bankCode.replace(/\s+/g,""); 
	if(bankCode == null || bankCode == "" || bankCode.length==0){
		$("#DivAdd input[name=bankCode]").val("")
		 $(".bankCodeSpan").html("<font color='red'>银行代码为必填项</font>");
		 return;
	}
	$.ajax({  
	     type : "POST",
	     url : '/rest/info/supportBank/checkBankCode',
	     data: {bankcode:bankCode}, 
	     success : function(data) { 
	    	 var json = JSON.parse(data);
	    	 if(json != -1){
	    		 $("#DivAdd input[name=bankCode]").val("")
	    		 $(".bankCodeSpan").html("<font color='red'>银行代码已经存在</font>");
	    		 return;
	    	 }
	    	 else{
	    		 $(".bankCodeSpan").html("");
	    	 }
	    	 
	     }  
	 });  
	
	function checkBankName() {
		var bankName = $("#DivAdd input[name=bankName]").val();
		bankName=bankName.replace(/\s+/g,""); 
		if(bankName == null || bankName == "" || bankName.length==0){
			 $(".bankCodeSpan").html("<font color='red'>银行名称必填项</font>");
    		 return;
		}
		else{
			 $(".bankCodeSpan").html("");
		}
	}
	function checkBankShort() {
		var bankShort = $("#DivAdd input[name=bankShort]").val();
		bankShort=bankShort.replace(/\s+/g,"");
		if(bankShort == null || bankShort == "" || bankShort.length==0){
			 $(".bankCodeSpan").html("<font color='red'>银行简称必填项</font>");
    		 return;
		}
		else{
			 $(".bankCodeSpan").html("");
		}
	}
}
function checkBankCodeEdit() {
	var bankCode = $("#DivEdit input[name=bankCode]").val();
	var id = $("#DivEdit input[name=id]").val();
	bankCode=bankCode.replace(/\s+/g,""); 
	if(bankCode == null || bankCode == "" || bankCode.length==0){
		$("#DivEdit input[name=bankCode]").val("")
		 $(".bankCodeSpanEdit").html("<font color='red'>银行代码为必填项</font>");
		 return;
	}
	$.ajax({  
	     type : "POST",
	     url : '/rest/info/supportBank/checkBankCodeEdit',
	     data: {bankcode:bankCode,id:id}, 
	     success : function(data) { 
	    	 var json = JSON.parse(data);
	    	 if(json != -1){
	    		 $("#DivEdit input[name=bankCode]").val("")
	    		 $(".bankCodeSpanEdit").html("<font color='red'>银行代码已经存在</font>");
	    		 return;
	    	 }
	    	 else{
	    		 $(".bankCodeSpanEdit").html("");
	    	 }
	    	 
	     }  
	 });  
}
	
function checkBankNameEdit() {
	var bankName = $("#DivEdit input[name=bankName]").val();
	bankName=bankName.replace(/\s+/g,""); 
	if(bankName == null || bankName == "" || bankName.length==0){
		 $(".bankCodeSpanEdit").html("<font color='red'>银行名称必填项</font>");
		 return;
	}
	else{
		 $(".bankCodeSpanEdit").html("");
	}
	$(".bankCodeSpanEdit").html("<font color='red'>银行名称必填项</font>");
}
function checkBankShortEdit() {
	var bankShort = $("#DivEdit input[name=bankShort]").val();
	bankShort=bankShort.replace(/\s+/g,"");
	if(bankShort == null || bankShort == "" || bankShort.length==0){
		 $(".bankCodeSpanEdit").html("<font color='red'>银行简称必填项</font>");
		 return;
	}
	else{
		 $(".bankCodeSpanEdit").html("");
	}
}


function addbankMaintain(){
	var row = $('#adGrid').datagrid('getSelected');
	if(row != null){
		$.messager.confirm('确认', "确定要将【" + row.bankName + "】设置成维护吗?", function(r) {
			if (r) {
				$.ajax({  
				     type : "POST",
				     url : '/rest/info/supportBank/addbankMaintain',
				     data: {
				    	 bankId:row.id
				     }, 
				     success : function(data) { 
				    	 if(data.code == 200){
				    		 $.messager.alert("提示", data.msg, "info");
				    	 }else{
				    		 $.messager.alert("提示", data.msg, "error");
				    	 }
				     }  
				 });
		}});
	}else{
		$.messager.alert("提示", "请选择一条记录", "info");
	}
}

function delbankMaintain(){
	var row = $('#adGrid').datagrid('getSelected');
	if(row != null){
		$.messager.confirm('确认', "确定要将【" + row.bankName + "】删除维护吗?", function(r) {
			if (r) {
				$.ajax({  
				     type : "POST",
				     url : '/rest/info/supportBank/delbankMaintain',
				     data: {
				    	 bankId:row.id
				     }, 
				     success : function(data) { 
				    	 if(data.code == 200){
				    		 $.messager.alert("提示", data.msg, "info");
				    	 }else{
				    		 $.messager.alert("提示", data.msg, "error");
				    	 }
				     }  
				 });
			}});
	}else{
		$.messager.alert("提示", "请选择一条记录", "info");
	}
}

function takeHeartAllowBuy(x){
	if(x == 1){
		var msg = $('#takeHeartMsg').val();
		if(msg == ''){
			$.messager.alert("提示", "禁止购买提示信息不能为空", "info");
			return;
		}
		$.messager.confirm('确认', "确定要将禁止购买随心取吗?", function(r) {
			if (r) {
				$.ajax({  
				     type : "POST",
				     url : '/rest/info/supportBank/takeHeartAllowBuy',
				     data: {
				    	 status:1,
				    	 msg : msg
				     }, 
				     success : function(data) { 
				    	 if(data.code == 200){
				    		 $.messager.alert("提示", data.msg, "info");
				    	 }else{
				    		 $.messager.alert("提示", data.msg, "error");
				    	 }
				     }  
				 });
			}});
	}else{
		$.messager.confirm('确认', "确定要将禁止购买随心取吗?", function(r) {
			if (r) {
				$.ajax({  
				     type : "POST",
				     url : '/rest/info/supportBank/takeHeartAllowBuy',
				     data: {
				    	 status:0
				     }, 
				     success : function(data) { 
				    	 if(data.code == 200){
				    		 $.messager.alert("提示", data.msg, "info");
				    	 }else{
				    		 $.messager.alert("提示", data.msg, "error");
				    	 }
				     }  
				 });
		}});
	}
}

	 
