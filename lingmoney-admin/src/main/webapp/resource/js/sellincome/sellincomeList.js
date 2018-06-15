
var toolbar = [{
	text : '兑付回签列表',
	iconCls : 'icon-view',
	handler : function() {
	}
}]

function searchFun(){
	var sellDate = $("#sellDate").datebox('getValue');
	var dayCount = $("#days").val();

	$('#adGrid').datagrid('load',{
		sellDate:sellDate,
		dayCount:dayCount
	});
}

function exportFun(){
	var sellDate = $("#sellDate").datebox('getValue');
	var dayCount = $("#days").val();
	
	window.location.href='/rest/sellBatchIncome/exportSellBatchIncomeList?sellDate='+sellDate+'&dayCount='+dayCount;
}

