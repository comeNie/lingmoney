$(document).ready(function(){
	$('#symbol').combogrid({
		onLoadSuccess : function() {
			$('#symbol').combogrid('grid').datagrid('selectRecord', 1);
		}
	});
});


function formatTimer(value) {
	if (value) {
		return new Date(value).format("yyyy-MM-dd");
	} else {
		return "";
	}
}

function search() {
	var regDt = $('#regDate').datebox('getValue');
	var platform = $('#platformType').combogrid('getValue');
	var symbol = $('#symbol').combogrid('getValue');
	$('#adGrid').datagrid('load', {
		page : 1,
		regDt : regDt,
		platform : platform,
		symbol : symbol
	});

}

