function serach() {
	$('#eAccountRechargeGrid').datagrid('load', {
		transdate : $('#transdate').datebox('getValue'),
		bankNo : $('#bankNo').textbox('getValue'), 
		transType : $('#transType').combobox('getValue')
	});
}

function transTypeFormat(value) {
	if (value == "0") {
		return "<font color='green'>收入</font>";
	} else {
		return "<font color='red'>支出</font>";
	}
} 
