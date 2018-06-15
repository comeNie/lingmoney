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
}, '-', {
	text : '发布',
	iconCls : 'icon-ok',
	handler : function() {
		publish();
	}
}];
function formatTimer(value,row){
	return new Date(value).format("yyyy-MM-dd");
}
//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	var rows = $('#product').combogrid('grid').datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请先查询处一种浮动产品", "error");
	    return
	}
	$("#apName").val(rows[0].name);
	$("#apNameLabel").text(rows[0].name);
	$("#apId").val(rows[0].id);
	$("#DivAdd").dialog('open');
}
//删除
function del(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/product/floatNetValue/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
function formatStatus(value,row){
	if(value == 0){
		return "未发布";
	}
	else if(value == 1){
		return "<font color='green'>已发布</font>";
	}
	else{
		return "<font color='red'>未知</font>";
	}
}
/**
 * 发布
 */
function publish(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	if(rows[0].status==1){
		 $.messager.alert("提示", "已经发布", "error");
		 return
	}
	showLoading();
	$.getJSON("/rest/product/floatNetValue/publish?r=" + Math.random() + "&id=" + rows[0].id,
	    function(info) {
	       if(info.code==200){
	    	   $.messager.alert('提示', '发布成功', 'info');
	    	   $("#adGrid").datagrid('reload');
	       }else{
	    	   $.messager.alert('错误提示', info.msg, 'error');
	       }
	});
	closeLoading();
}
//保存
function save(){
	showLoading();
	var netValueDt = $('#anetValueDt').datebox('getValue');
	$('#anet').val(new Date(formatTimer(netValueDt)));
	//alert($('#anet').val());
	$('#ffAdd').form('submit',{
		url: '/rest/product/floatNetValue/save',
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
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	$("#epNameLabel").text(rows[0].pName);
	$('#enetValueDt').datebox('setValue',formatTimer(new Date(rows[0].netValueDt)));
	$("#DivEdit").dialog('open');
}
function update(){
	showLoading();
	$("#epName").val($('#epId').combogrid('getText'));
	var netValueDt = $('#enetValueDt').datebox('getValue');
	$('#enet').val(new Date(formatTimer(netValueDt)));
	$('#ffEdit').form('submit',{
		url: '/rest/product/floatNetValue/update',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivEdit").dialog('close');
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

//查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		pId:$('#product').combogrid('getValue')
	});
}
function searchFloat(){
	$('#product').combogrid('grid').datagrid('load',{
		page:1,
		name:$('#searchName').val(),
		approval:$('#searchApproval').combobox('getValue')
	});
}

function getChecked(){
    var nodes = $('#menuTree').tree('getChecked',['checked','indeterminate']);
    var s = '';
    for(var i=0; i<nodes.length; i++){
        if (s != '') s += ',';
        s += nodes[i].id;
    }
    return s;
}


