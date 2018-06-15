var toolbar = [{
	text : '手动恢复',
	iconCls : 'icon-edit',
	handler : function() {
		selfRecover();
	}
}];

function search() {
	$('#userAccountRepaymentDatagrid').datagrid('load', {
		page : 1,
		rows : 30,
		name : $("#name").textbox("getText"),
		tel : $("#tel").textbox("getText"),
		initTime : $("#initTime").datebox("getValue"),
		status : $("#status").combobox("getValue")
	});
}

function formatState(value, row, index) {
	if (value == 0) {
		return "<font color='gray'>待回款</font>";
	} else if (value == 1) {
		return "<font color='green'>回款成功</font>";
	}
}

function formatType(value, row, index) {
	if (value == 1) {
		return "流标";
	} else if (value == 2) {
		return "撤标";
	}
}

function selfRecover() {
	var row = $('#userAccountRepaymentDatagrid').datagrid("getSelected");
	
	if (row == null) {
		$.messager.alert('提示', "请先选择一条记录");
		return false;
	}
	
	$.messager.confirm("提示", "确认要手动恢复吗？", function(b) {
		if (b) {
			$.ajax({
				url : '/rest/bank/manualRecoverPayment',
				type : 'post',
				data : {id : row.id},
				success : function(data) {
					$.messager.alert('提示', data.msg);
					if (data.code == 200) {
						$('#userAccountRepaymentDatagrid').datagrid('load', {
							name : $("#name").textbox("getText"),
							tel : $("#tel").textbox("getText"),
							initTime : $("#initTime").datebox("getValue"),
							status : $("#status").combobox("getValue")
						});
					}
				}
			});
		}
	});
}
