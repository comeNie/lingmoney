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
},'-', {
	text : '分配权限',
	iconCls : 'icon-setting',
	handler : function() {
		open_setting();
	}
}];
//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}
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
	$.getJSON("/rest/base/role/changeStatus?r=" + Math.random() + "&id=" + rows[0].id,
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

//保存
function save(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/base/role/save',
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
	$("#DivEdit").dialog('open');
}
function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/base/role/update',
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
		roleName:$("#sroleName").val()
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
/**
 * 获取当前role所对应的menu
 */
function getRoleMenu(id){
	$.getJSON("/rest/base/role_menu/listByRoleId?roleId="+id+"&r=" + Math.random(),
	function(json){
		if(json && json.length>0){
			$.each(json, function(i, n) {
				var node = $('#menuTree').tree('find', n.menuId);
				if($('#menuTree').tree('isLeaf', node.target)){
					$('#menuTree').tree('check', node.target);
				}
			});
		}
	});
	closeLoading();
}
//初始化dialog
function initDialogSetting(title){
	$('#DivSet').dialog({
	    title: title,
	    iconCls:'icon-setting',
	    modal: true,
	    toolbar:[{
			text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#menuTree').tree('reload');}
		},'-',{
			text:'展开',
			iconCls:'icon-expand',
			handler:function(){$('#menuTree').tree('expandAll');}
		},'-',{
			text:'折叠',
			iconCls:'icon-collapse',
			handler:function(){$('#menuTree').tree('collapseAll');}
		}],
		buttons:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){doSetting();}
		}]
	    
	});
	$('#DivSet').dialog('open');
}

//分配权限
function open_setting(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffAddRole").form('clear');
	$("#setRoleId").val(rows[0].id);
	$('#menuTree').tree({
		url:'/rest/base/menu/listTree?r='+Math.random(),
		method:'post',
		animate:true,
		lines:true,
		checkbox:true,
		onBeforeLoad:function(){
         	showLoading();
         },
         onLoadSuccess:function(){
        	 getRoleMenu(rows[0].id);
         }
	});
	initDialogSetting(rows[0].roleName);
	
}
/**
 * 做菜单设置
 */
function doSetting(){
	var ids = getChecked();
	if(!ids || ids == ""){
		$.messager.alert('错误提示', '请选择相应权限，如果没有，直接退出', 'error')
		return;
	}
	$("#setMenuIds").val(ids)
	showLoading();
	$('#ffAddRole').form('submit',{
		url: '/rest/base/role_menu/updateRoleMenu',
		ajax:true,
		success:function(data){
			var json =JSON.parse(data);
			if(json.code == 200){
				$("#DivSet").dialog('close');
			}else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}