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

var toolbar = [ {
	text : '添加',
	iconCls : 'icon-add',
	handler : function() {
		add();
	}
}, '-', {
	text : '编辑',
	iconCls : 'icon-edit',
	handler : function() {
		edit();
	}
}];

//格式化状态信息  
function formatStatus(value, rows){
	if(rows.status=="0") {
		return "<font color='red'>禁用</font>";
	} else {
		return "<font color='green'>启用</font>";
	}
}

//格式化时间信息  
function formatTime(value, rows){
	return new Date(parseInt(value)).toLocaleString().replace(/日/g, " ").replace(/上午|下午/g,"").replace(/\/+/g,'-');   
}

function add(){
	$("#bootImageForm").form("clear");
	$("#bootImageForm table tr:last").hide();
	$("#DivEdit").dialog("setTitle","添加").dialog("open");
}

//编辑按钮点击事件 
function edit(){
	$("#bootImageForm").form("clear");
	var rows = $("#bootImageGrid").datagrid("getSelected");
	rows.showEndTime = formatTime(rows.showEndTime);
	$("#bootImageForm table tr:last").show();
	if (rows==null) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return false;
	}
	$("#bootImageForm").form("load",rows);
	$("#DivEdit").dialog("setTitle","编辑").dialog("open");
}

//保存，点击事件 
function save(){
	$('#bootImageForm').form('submit', {
		url: '/rest/appBootImage/save',
		ajax: true,
		onSubmit: function(){
			var isValid = $(this).form('validate');
			if (!isValid){
				$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
			}
			return isValid;	// 返回false终止表单提交
		},
		success: function(data){
			data = JSON.parse(data);
			$("#DivEdit").dialog('close');
			if(data.code==200) {
				$.messager.alert('提示', '保存成功');
				$('#bootImageGrid').datagrid('reload');
			} else {
				$.messager.alert('提示', data.msg);
			}
		}
	});

}