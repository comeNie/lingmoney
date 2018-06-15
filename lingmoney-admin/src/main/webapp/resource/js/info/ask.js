var toolbar = [ {
	text : '回答',
	iconCls : 'icon-edit',
	handler : function() {
		anwser();
	}
}, '-', {
	text : '热门状态',
	iconCls : 'icon-ok',
	handler : function() {
		publish();
	}
}, '-', {
	text : '更改问题状态',
	iconCls : 'icon-ok',
	handler : function() {
		changeStatus();
	}
}, '-', {
	text : '删除',
	iconCls : 'icon-remove',
	handler : function() {
		del();
	}
}];

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
			$.getJSON("/rest/info/ask/delete?r=" + Math.random() + "&id=" + rows[0].id,
			    function(info) {
			       if(info.code==200){
			    	   $.messager.alert('提示', '删除成功', 'info');
			    	   $("#adGrid").datagrid('reload');
			       }else{
			    	   $.messager.alert('错误提示', info.message, 'error');
			       }
			});
	    }else{
	    	return
	    }
	  });
	closeLoading();
}

function formatTimer(value, row) {
	if (value) {
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}
function formatState(value, row) {
	if (!value || value == 0) {
		return "<font color='red'>不可用</font>";
	} else if (value == 1) {
		return "<font color='blue'>可用</font>";
	}
}
function formatStatus(value, row) {
	if (value == 0) {
		return "未回答";
	} else if (value == 1) {
		return "<font color='green'>已回答</font>";
	} else {
		return "<font color='red'>未知</font>";
	}
}
function formatHot(value, row) {
	if (value == 0) {
		return "否";
	} else if (value == 1) {
		return "<font color='green'>是</font>";
	} else {
		return "<font color='red'>未知</font>";
	}
}
// 删除
function del() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.messager.confirm('确认', '您确认想要删除该项吗？', function(r) {
		if (r) {
			$.getJSON("/rest/info/ask/delete?r=" + Math.random() + "&id="
					+ rows[0].id, function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '删除成功', 'info');
					$("#adGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
		} else {
			return		}
	});
	closeLoading();
}

function publish() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return	}
	
	showLoading();
	$.getJSON("/rest/info/ask/publish?r=" + Math.random() + "&id=" + rows[0].id,
			function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '设置成功', 'info');
					$("#adGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
	closeLoading();
}
function changeStatus() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return	}
	
	showLoading();
	$.getJSON("/rest/info/ask/changeStatus?r=" + Math.random() + "&id=" + rows[0].id,
			function(info) {
		if (info.code == 200) {
			$.messager.alert('提示', '修改成功', 'info');
			$("#adGrid").datagrid('reload');
		} else {
			$.messager.alert('错误提示', info.msg, 'error');
		}
	});
	closeLoading();
}

function anwser() {
	KindEditor.remove('#anwser');
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load', rows[0]);
	var editor = createKindEditor("#anwser");
	$.getJSON("/rest/info/ask/listAnwser?r=" + Math.random() + "&askId="
			+ rows[0].id, function(info) {
		if (info.code == 200) {
			editor.html(info.msg)
		} else {
			$.messager.alert('错误提示', info.msg, 'error');
		}
	});

	$("#DivEdit").dialog('open');
}
function toAnwser() {
	var anwser = $("#anwser").val();
	if (anwser == '') {
	    $.messager.alert("提示", "请输入回答内容", "error");
	    return
	}
	if ($('#estatus').val() == 0) {
		save();
	} else if ($('#estatus').val() == 1) {
		update();
	}
	$('#estatus').val(1);
	$('#eanwserMan').val("客服人员1号");
	showLoading();
	$('#ffEdit').form('submit', {
		url : '/rest/info/ask/update',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			if (json.code == 200) {
				KindEditor.remove('#anwser');
				$("#DivEdit").dialog('close');
				$("#adGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
function save() {
	$("#acontent").val($('#anwser').val());
	$("#atime").val(new Date());
	$("#aanwserMan").val("客服人员1号");
	$("#akeyword").val($('#ekeyword').val());
	$("#astatus").val(1);
	$("#ahot").val(0);
	$("#atype").val(1);
	$("#aaskId").val($('#eid').val());

	showLoading();
	$('#ffAdd').form('submit', {
		url : '/rest/info/ask/save',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			if (json.code == 200) {
				KindEditor.remove('#anwser');
				$("#DivAdd").dialog('close');
				$("#adGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
function update() {
	var askId = $('#eid').val();
	// alert("待回答问题的之间id(回答问题的askId)： "+askId);
	var con = sendReq("/rest/info/ask/anwser?askId=" + askId);
	/*$.getJSON("/rest/info/ask/anwser?r=" + Math.random() + "&askId=" + askId,
			function(info) {
				if (info.code == 200) {

					$("#esid").val(info.msg);
					alert("回答问题的主键id： " + $("#esid").val());
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});*/
	//alert(con);
	$("#esid").val(con);
	$("#escontent").val($('#anwser').val());
	$("#estime").val(new Date());
	$("#esanwserMan").val("客服人员2号");
	$("#eskeyword").val($('#ekeyword').val());
	$("#esaskId").val(askId);
	// alert("回答的内容： "+$("#escontent").val());
	showLoading();
	$('#ffEdits').form('submit', {
		url : '/rest/info/ask/update',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			// alert(json);
			if (json.code == 200) {
				KindEditor.remove('#anwser');
				$("#DivEdit").dialog('close');
				$("#adGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
// 查询
function search() {
	$('#adGrid').datagrid('load', {
		page : 1,
		title : $("#stitle").val(),
		state:$("#state").combogrid('getValue')
	});
}
function createKindEditor(contentId) {
	var editor = KindEditor.create(contentId, {
		allowFileManager : false,
		newlineTag : "br",
		uploadJson : '/rest/info/uploadImg',
		afterChange : function() {
			this.sync();
		},
		afterBlur : function() {
			this.sync();
		}
	});
	return editor;
}
