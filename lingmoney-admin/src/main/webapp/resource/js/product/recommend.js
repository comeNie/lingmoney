var toolbar = [ {
	text : '添加',
	iconCls : 'icon-add',
	handler : function() {
		add();
	}
}, '-', {
	text : '修改',
	iconCls : 'icon-edit',
	handler : function() {
		edit();
	}
}, '-', {
	text : '删除',
	iconCls : 'icon-remove',
	handler : function() {
		del();
	}
}];
// 添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}
function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}
function formatStatus(value,row){
	if(value == 0){
		return "不显示";
	}
	else if(value == 1){
		return "<font color='green'>显示</font>";
	}
	else{
		return "<font color='red'>未知</font>";
	}
}
function formatRec(value,row){
	if(value == 0){
		return "不推荐";
	}
	else if(value == 1){
		return "<font color='green'>推荐</font>";
	}
	else{
		return "<font color='red'>未知</font>";
	}
}
function formatRecLevel(value,row){
	if(!value||value == 0){
		return "不推荐";
	}else{
		return "<font color='red'>"+value+"</font>";
	}
}
//格式化图片
function formatPicture(value,row,index){
	if('' != value && null != value){
		value = '<img style="width:46px; height:46px; margin-top:5px;" src="' + value + '">';
	  	return  value;
	}
}
// 删除
function del(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认', '您确认想要删除该项吗？', function(r) {
		if (r) {
			$.getJSON("/rest/product/recommend/delete?r=" + Math.random() + "&id=" + rows[0].id,
			    function(info) {
			       if(info.code==200){
			    	   $.messager.alert('提示', '删除成功', 'info');
			    	   $("#adGrid").datagrid('reload');
			       }else{
			    	   $.messager.alert('错误提示', info.msg, 'error');
			       }
			});
		}else{
			return
		}
	});
	closeLoading();
}


// 保存
function saveAndUpdate(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/product/recommend/saveAndUpdate',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivAdd").dialog('close');
				$("#adGrid").datagrid('reload');
			}
			else
			{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
function edit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffAdd").form('clear');
	$("#ffAdd").form('load',rows[0]);
	$("#recCityCodeVal").textbox("setValue",formatCityCode(1,rows[0]));
	$("#DivAdd").dialog('open');
}

// 查询
function search(){
	$('#adGrid').datagrid('load', $.serializeObject($('#searchForm')));
}

function setArea(index, row) {
	$("#recCityCode").val(row.name);
}

var cityInfo = "";

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
}
