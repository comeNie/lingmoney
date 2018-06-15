$(function(){
	$('#bwIdType').combobox({
		onSelect : function (record){
			loadCombogridByIdType(record.value);
		}
	})
});

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
		publish();
	}
}, '-', {
	text : '删除',
	iconCls : 'icon-remove',
	handler : function() {
		del();
	}
}, '-', {
	text : '查看流水',
	iconCls : 'icon-remove',
	handler : function() {
		accountManager();
	}
}];

function accountManager(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认', '切记除了查询不要做其他操作！', function(r) {
		if (r) {
			$.getJSON("/rest/bank/hxBorrower/accountManager?r=" + Math.random() + "&acNo=" + rows[0].bwAcno,
			    function(info) {
					console.log(info);
			       if(info.code==200){
			    	   $.messager.alert('提示', '请在新窗口中查看', 'info');
			    	   console.log(info.obj.bankUrl);
			    	   console.log(info.obj.requestData);
			    	   console.log(info.obj.transCode);
			    	   $("#accountManageForm").attr("action",info.obj.bankUrl);
					   $("#accountManageRequestData").val(info.obj.requestData);
					   $("#accountManageTransCode").val(info.obj.transCode);
					   // 提交
					   $("#accountManageForm").submit();
					   
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

function loadCombogridByIdType(idType) {
	if (idType == 1010 || idType == '1010') {
		$('#enterpriseSerachTab').hide();
		$('#accId').combogrid({
			panelWidth: 400,
            panelHeight: 220,
            prompt:'请选择E账户',
            toolbar:'#tbs',
            idField: 'id',
            editable:false,
            pagination:true,
            url:'/rest/bank/hxBorrower/hxList?status=1',
            onShowPanel:function(){
			    $(this).combogrid('grid').datagrid('reload');
		    },
            loadMsg:'正在加载,请稍后...',
            textField: 'acName',
            method: 'post',
            columns: [[
                {field:'id',title:'id',width:50},
                {field:'acName',title:'name',width:50},
                {field:'mobile',title:'mobile',width:50}
            ]],
            fitColumns: true,
            icons: [{
				iconCls:'icon-remove',
				handler: function(e){
					$('#accId').combogrid('setValue', '');
				}
			}]
		});
		
	} else {
		$('#tbs').hide();
		$('#accId').combogrid({
			panelWidth: 400,
            panelHeight: 220,
            prompt:'请选择企业账户',
            toolbar:'#enterpriseSerachTab',
            idField: 'id',
            editable:false,
            pagination:true,
            url:'/rest/bank/enterpriseAccount/listEnterpriseAccount',
            onShowPanel:function(){
			    $(this).combogrid('grid').datagrid('reload');
		    },
            loadMsg:'正在加载,请稍后...',
            textField: 'enterpriseName',
            method: 'post',
            columns: [[
                {field:'id',title:'id',width:50},
                {field:'enterpriseName',title:'企业名称',width:50},
                {field:'bankNo',title:'银行账号',width:50}
            ]],
            fitColumns: true,
            icons: [{
				iconCls:'icon-remove',
				handler: function(e){
					$('#accId').combogrid('setValue', '');
				}
			}],
			queryParams: {
				status : 1
			}
		});
		
	}
}

function enterpriseSearch() {
	$('#accId').combogrid('grid').datagrid('load',{
		page:1,
		status : 1,
		companyName:$('#companyName').textbox('getValue'),
		bankNo:$('#companyBankNo').textbox('getValue')
	});
}

//添加窗口的显示
function add(){
//	$("#accId").combogrid('readonly', false);
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

function formatStatus(value, row) {
	if (!value || value == 0) {
		return "<font color='red'>不可用</font>";
	} else if (value == 1) {
		return "<font color='blue'>可用</font>";
	}
}

function formatIdtype(value, row) {
	if (value && value == '1010') {
		return "<font color='blue'>身份证</font>";
	} else {
		return "<font color='green'>组织机构代码证</font>";
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
			$.getJSON("/rest/bank/hxBorrower/delete?r=" + Math.random() + "&id=" + rows[0].id,
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

function publish(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.getJSON("/rest/bank/hxBorrower/publish?r=" + Math.random() + "&id=" + rows[0].id,
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

// 保存和修改
function saveAndUpdate(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/bank/hxBorrower/saveAndUpdate',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivAdd").dialog('close');
				$("#adGrid").datagrid('reload');
			}else{
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
	
	loadCombogridByIdType(rows[0].bwIdtype);
	$("#ffAdd").form('load',rows[0]);
	$("#accId").combogrid('setText', rows[0].acName);
	$("#DivAdd").dialog('open');
}

//搜索E账户
function searchName(){
	$('#accId').combogrid('grid').datagrid('load',{
		page:1,
		acName:$('#acName').val(),
		mobile:$('#mobile').val()
	});
}

// 查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		bwAcname:$("#bwAcname").val(),
		mobile:$("#bwMobile").val()
	});
}

