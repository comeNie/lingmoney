$(function(){
	$('#tableDataGrid').datagrid('load');
});

var toolbar = [ {
	text : '自动还款确认',
	iconCls : 'icon-ok',
	handler : function() {
		confirmAutoRepayment(1);
	}
}, '-', {
	text : '自动还款取消',
	iconCls : 'icon-remove',
	handler : function() {
		confirmAutoRepayment(0);
	}
}];

function search() {
	$('#tableDataGrid').datagrid('load',{
		page : 1,
		date : $('#date').val()
	});
}

function confirmAutoRepayment(type) {
	var rows = $("#tableDataGrid").datagrid("getSelections");

	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	
	$.messager.confirm('提示', '确认执行此操作?', function(ok) {
		if(ok) {
			var productIds = "";
			$.each(rows, function(i, item){
				if (i == 0) {
					productIds = item.id;
				} else {
					productIds = productIds + "," + item.id;
				}
			});
			showProgress();
			$.ajax({
				url : '/rest/product/product/confirmAutoRepayment',
				type : 'post',
				data :{
					productIds : productIds,
					type : type
				},
				success : function(data){
					closeProgress();
					if(data.code == 200){
						$.messager.alert('提示', data.msg, 'info');
						$("#tableDataGrid").datagrid('reload');
					} else{
						$.messager.alert('错误提示', data.msg, 'error');
					}
				}
			});
		}
	});
}
