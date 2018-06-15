function showLoading(){
	$.messager.progress({ 
		title:'请稍后', 
		msg:'页面加载中...' 
	}); 
}
function closeLoading(){
	$.messager.progress('close');
}

function showProgress() {
	$.messager.progress({ 
		title:'请稍后', 
		msg:'请求处理中...' 
	}); 
}

function closeProgress() {
	$.messager.progress('close');
}


/*var cityInfo = "";

$(function(){
	$.ajax({
		async:false,
		url:"/rest/areaDomain/queryCityInfo?r=" + Math.random(),
		success:function(data) {
			cityInfo = data.obj;
		}
	});
});


//城市代码--》城市名
function formatCityCode(value,row) {
	if(row.cityCode!=undefined) {
		return cityInfo[row.cityCode];
	}
}*/
