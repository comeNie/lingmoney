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
	text : '更改状态',
	iconCls : 'icon-ok',
	handler : function() {
		changeStatus();
	}
} ];

function formatStatus(value, row) {
	if (!value || value == 0) {
		return "<font color='red'>不可用</font>";
	} else if (value == 1) {
		return "<font color='blue'>可用</font>";
	}
}
//修改状态
function changeStatus(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.getJSON("/rest/base/menu/changeStatus?r=" + Math.random() + "&id=" + rows[0].id,
	    function(info) {
	       if(info.code==200){
	    	   $.messager.alert('提示', '修改成功', 'info');
	    	   $("#adGrid").datagrid('reload');
	       }else{
	    	   $.messager.alert('错误提示', info.msg, 'error');
	       }
	});
	closeLoading();
}
function reloadTree() {
	$('#menuTree').tree('reload');
};

function treeClick(node){
	$("#adGrid").datagrid('load',{pid:node.id})
}
function add(){
	var node = $("#menuTree").tree('getSelected');
	if(node){
		$('#ffAdd').form('clear');
		$("#apname").textbox("setValue",node.text)
		$("#apid").val(node.id);
		$("#DivAdd").dialog('open');
	}else{
		 $.messager.alert('信息提示', '请先选择左侧菜单', 'info');
	}
}

function changeIcon(index,obj) {
    $('#imgIcon'+index).attr('class', $(obj).val());
}
//保存
function save(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/base/menu/save',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				refreshTree();
				$("#adGrid").datagrid('reload');
				$("#DivAdd").dialog('close');
			}
			else
			{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
//验证是否就2层
function validTreeLevel(treeObj,node){
	if(node){
		var node1 = $(treeObj).tree('getParent',node.target);
		if(node1 && $(treeObj).tree('getParent',node1.target)){
			return false;
		}
		return true;
	}
}
function edit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	var node = $("#menuTree").tree('getSelected');
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	$('#imgIcon1').attr('class', rows[0].icon);
	$("#epid").combotree("setValue",rows[0].pid);
	$("#DivEdit").dialog('open');
}
function refreshTree(){
	$("#epid").combotree('tree').tree('reload');
	$("#menuTree").tree('reload');
}
//更新
function update(){
	var treeObj = $("#epid").combotree('tree');
	//不能选择当前记录的目录
	if($("#eid").val() == $("#epid").combotree('getValue')){
		$.messager.alert('错误提示', "不能选择当前目录作为父母录", 'error');
		return;
	}
	//判断目录结构是否超过3层
	if(!validTreeLevel(treeObj,$(treeObj).tree('getSelected'))){
		$.messager.alert('错误提示', "此系统只支持到3层深度,请修改父菜单", 'error');
		return;
	}
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/base/menu/update',
		ajax:true,
		success:function(data){
			var json =JSON.parse(data);
			if(json.code == 200){
				$("#adGrid").datagrid('reload');
				refreshTree();
				$("#DivEdit").dialog('close');
			}else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
