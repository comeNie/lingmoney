var toolbar = [ {
	text : '解冻',
	iconCls : 'icon-add',
	handler : function() {
		add();
	}
}];
// 添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	createKindEditor("#acontent");
	$("#DivAdd").dialog('open');
}


function formatTimer(value){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}

function formatStatus(value,row){
	if(value == 0){
		return "处理中";
	}
	else if(value == 1){
		return "<font color='green'>解冻成功</font>";
	}
	else{
		return "<font color='red'>解冻失败</font>";
	}
}

// 保存
function save(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/transactionInquiry/thawObject',
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
				$("#DivAdd").dialog('close');
				$("#adGrid").datagrid('reload');
			}
		}
	});
	closeLoading();
}

// 查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		requestNo:$("#requestNo").val()
	});
}

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
