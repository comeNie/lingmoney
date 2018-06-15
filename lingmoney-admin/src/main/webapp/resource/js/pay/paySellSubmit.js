var toolbar = [ {
	text : '导出到excel',
	iconCls : 'icon-add',
	handler : function() {
		var rows = $('#adGrid').datagrid('getRows')//获取当前的数据行
		if(rows.length==0){
			$.messager.alert('错误信息','没有可导出的数据','error');
			return ;
		}
		var user_finc=$("#sFincUsers").combogrid('getValue');
		if(!user_finc){
			 $.messager.alert("提示", "请先选择融资用户", "error");
			 return;
		}
		var pType=$("#pType").combogrid('getValue');
		if(!pType){
			$.messager.alert("提示", "请先选择产品所属", "error");
			return;
		}
	    Export(user_finc,pType);
	}
},'-',{
	text : '导入确认excel',
	iconCls : 'icon-ok',
	handler : function() {
		add();
	}
}];
//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}
function Export(user_finc, pType) {  
    var f = $('<form action="/rest/excel/export" method="post" id="fm1"></form>');  
    var i = $('<input type="hidden"  name="platUserNo" />');  
    var j = $('<input type="hidden"  name="pType" />');  
    i.val(user_finc);
    i.appendTo(f);  
    j.val(pType);
    j.appendTo(f);  
    f.appendTo(document.body).submit();  
}  

function search(){
	var user_finc=$("#sFincUsers").combogrid('getValue');
	if(!user_finc){
		 $.messager.alert("提示", "请先选择融资用户", "error");
		 return;
	}
	var pType=$("#pType").combogrid('getValue');
	if(!pType){
		$.messager.alert("提示", "请先选择产品所属", "error");
		return;
	}
	$('#adGrid').datagrid('load',{
		page:1,
		platUserNo:$('#sFincUsers').combogrid('getValue'),
		pType:$('#pType').combogrid('getValue')
	});
	
}
function searchUsers(){
	$('#sFincUsers').combogrid('grid').datagrid('load',{
		page:1,
		name:$('#searchName').val()
	});
}
//统计
function compute(data) {//计算函数
    var rows = $('#adGrid').datagrid('getRows')//获取当前的数据行
    var finaclAll=0
    var interestAll = 0;
    var cost_value_money_All = 0;
    for (var i = 0; i < rows.length; i++) {
    	finaclAll += rows[i]['financial_money'];
    	interestAll += rows[i]['interest'];
    	cost_value_money_All +=rows[i]['cost_value_money'] ;
    }
    var hj = (finaclAll+interestAll+cost_value_money_All).toFixed(2);
    interestAll = interestAll.toFixed(2);
    //新增一行显示统计信息
    $('#adGrid').datagrid('reloadFooter',[{id: '<span class="subtotal">合计:</span>',cost_value_money:'', financial_money: '<span class="subtotal">总的理财金额:'+finaclAll+'</span>' , interest: '<span class="subtotal">总的利息:'+interestAll+'</span>',cost_value_money: '<span class="subtotal">总的平台佣金:'+cost_value_money_All+'</span>',platform_user_no:'<span class="subtotal">合计应付款:'+hj+'</span>' }]);
}

function save(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/excel/impExcel',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivAdd").dialog('close');
				$.messager.alert('信息提示', "上传成功", 'info');
			}
			else
			{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
