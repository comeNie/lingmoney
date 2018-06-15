var toolbar = [{
	text : '添加',
	iconCls : 'icon-add',
	handler : function() {
		add();
	}
}, '-',  {
	text : '编辑',
	iconCls : 'icon-edit',
	handler : function() {
		edit();
	}
}];
function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd");
	}else{
		return "";
	}
}
// 添加窗口的显示
function add(){
	KindEditor.remove('#acontent');
	$('#ffAdd').form('clear');
	createKindEditor("#acontent");
	$("#actId").combogrid({readonly:false});
	$("#DivAdd").dialog('open');
}
function edit(){
	$('#ffAdd').form('clear');	
	
	KindEditor.remove('#acontent1');
	KindEditor.remove('#acontent2');
	KindEditor.remove('#acontent3');
	KindEditor.remove('#acontent4');
	KindEditor.remove('#acontent5');
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	var editor1 = createKindEditor("#acontent1");
	var editor2 = createKindEditor("#acontent2");
	var editor3 = createKindEditor("#acontent3");
	var editor4 = createKindEditor("#acontent4");
	var editor5 = createKindEditor("#acontent5");
	$.getJSON("/rest/product/activityProperty/listContent?r=" + Math.random() + "&id=" + rows[0].id,
	function(info) {		     
		 editor1.html(info.actProDesc1);
		 editor2.html(info.actProDesc2);
		 editor3.html(info.actProDesc3);
		 editor4.html(info.actProDesc4);
		 editor5.html(info.actProDesc5);		     
	});	
	$("#actId").combogrid({readonly:true});
	$("#ffAdd").form('load',rows[0]);
	$("#DivAdd").dialog('open');
}

// 保存
function saveAndUpdate() {
	
	showLoading();
	$('#ffAdd').form('submit', {
		url : '/rest/product/activityProperty/saveAndUpdate',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			if (json.code == 200) {
				//$("#DivAdd").dialog('close');
				//$("#adGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		},
		error : function(data) {
		}
	});
	
	setTimeout(function () { 
		$("#DivAdd").dialog('close');
		$("#adGrid").datagrid('reload');
    }, 3000);
	
	closeLoading();
}




/**
 * 显示和隐藏规则输入项目
 * 
 * @param newValue
 * @param oldValue
 */

function createKindEditor(contentId){
	var editor= KindEditor.create(contentId, {
		allowFileManager : false,
		newlineTag:"br",
        uploadJson : '/rest/info/uploadImg',
        afterChange: function () {
            this.sync();
        },
        afterBlur: function () { this.sync(); }
	});
	return editor;
}

