// 查询
function search(){
	var pid = $("#productId").combogrid('grid').datagrid('getSelected');
	if (pid == null) {
		$.messager.alert("提示", "请先选择一个产品");
		return false;
	}
	$('#adGrid').datagrid({
		url:'/rest/customQuery/productSellTradingInfo',
		queryParams:{
			page: 1,
			pid: pid.id,
			status : $("#statusCombobox").combobox("getValue")
		}
	});
}


// 统计
function statistics (data) {//计算函数
	// 有数据 才统计
	if (data.total > 0) {
		// 查询统计数据
		$.ajax({
			url : '/rest/customQuery/productSellSumInfo',
			type : 'post',
			data : {pid : $("#productId").combogrid('grid').datagrid('getSelected').id},
			success : function(data) {
				if (data != null) {
					 //新增一行显示统计信息
				    $('#adGrid').datagrid('reloadFooter',[{id: '<span class="subtotal">产品金额统计:</span>', name: '<span class="subtotal">募集金额:'+data.priorMoney+'</span>' , tel: '<span class="subtotal">已筹金额:'+data.reachMoney+'</span>',money: '<span class="subtotal">成功交易金额：'+data.availableMoney+'</span>',employeeName:'<span class="subtotal">占用金额:'+data.waitMoney+'</span>' }]);
				}
			}
		});
	} else {
		$('#adGrid').datagrid('reloadFooter',[{}]);
		$.messager.alert("提示", "暂无数据");
	}
    
}
