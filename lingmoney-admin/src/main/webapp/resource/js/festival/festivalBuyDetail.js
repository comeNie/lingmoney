
var toolbar = [{
	text : '活动产品认购列表',
	iconCls : 'icon-view',
	handler : function() {
	}
}]

function searchFun(){
	var startDate = $("#startDate").datebox('getValue');
	var endDate = $("#endDate").datebox('getValue');
	var pactName = $("#pactName").val();
	var activityStartDate = $("#activityStartDate").datebox('getValue');
	var activityEndDate = $("#activityEndDate").datebox('getValue');

	$('#adGrid').datagrid('load',{
		startDate:startDate,
		endDate:endDate,
		pactName:pactName,
		activityStartDate:activityStartDate,
		activityEndDate:activityEndDate
	});
}

function exportFun(){
	var startDate = $("#startDate").datebox('getValue');
	var endDate = $("#endDate").datebox('getValue');
	var pactName = $("#pactName").val();
	var activityStartDate = $("#activityStartDate").datebox('getValue');
	var activityEndDate = $("#activityEndDate").datebox('getValue');
	
	console.log(encodeURI(pactName));

	window.location.href=encodeURI('/rest/festival/festivalBuy/exportSellBatchIncomeList?startDate='+startDate+'&endDate='+endDate+'&pactName='+encodeURI(pactName)+'&activityStartDate='+activityStartDate+'&activityEndDate='+activityEndDate);
}


