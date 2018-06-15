function search() {
	$('#footPrintDatagrid').datagrid('load', {
		page : 1,
		rows : 30,
		account : $("#account").val(),
		tel : $("#tel").val(),
		date : $("#date").datebox("getValue")
	});
}
