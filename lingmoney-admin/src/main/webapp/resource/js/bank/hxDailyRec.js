var formatter = new CommonFormatter();

// 查看文件超链接
function formatFile(value, row, index){
    return  "<a href = "+row.fileUrl+" target='_blank'>查看对账文件</a>";
}

var toolbar = [{
	text : '请求日终对账',
	iconCls : 'icon-edit',
	handler : function() {
		request();
	}
}]

function request(){
	$("#dailyGrid").datagrid("loading", "正在提交，请稍候……");
	$.post("/rest/bank/dailyReconciliate/",function(data){
		if(data.code==200){
			$("#dailyGrid").datagrid("loaded");
			$.messager.alert('提交成功', '日终对账请求成功', 'info');
			$("#dailyGrid").datagrid("reload");
		}else{
			$("#dailyGrid").datagrid("loaded");
			$.messager.alert('查询错误', data.msg, 'error');
		}
	})
}

//搜索标的
function search(){
	var startDate=$("#startDate").datebox('getValue');
	var endDate=$("#endDate").datebox('getValue');
	var today=new Date().toLocaleDateString();
	if(startDate>today||endDate>today){
		$.messager.alert('查询条件错误', "所选时间不得大于今天", 'error');
		return;
	}
	
	if(startDate>endDate){
		$.messager.alert('查询条件错误', "开始时间不得大于结束时间", 'error');
		return;
	}
	
	$('#dailyGrid').datagrid('load',{
		page:1,
		startDate:startDate,
		endDate:startDate
	});
}

function openFile(url){
	// 下载文件
}