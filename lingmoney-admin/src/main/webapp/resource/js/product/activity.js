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
	text : '修改发布状态',
	iconCls : 'icon-ok',
	handler : function() {
		publish();
	}
}];
/**
 * 格式化时间
 * 
 * @param value
 * @param row
 * @returns
 */
function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}
/**
 * 格式化状态
 * 
 * @param value
 * @param row
 * @returns {String}
 */
function formatStatus(value, row) {
	if (value == 0) {
		return "<font color='red'>未发布</font>";
	} else if (value == 1) {
		return "<font color='blue'>已发布</font>";
	}
}
/**
 * 格式化执行方式
 * 
 * @param value
 * @param row
 * @returns {String}
 */
function formatExeType(value, row) {
	if (value == 0) {
		return "<font color='blue'>按日执行</font>";
	} else if (value == 1) {
		return "<font color='red'>按周执行</font>";
	} else if (value == 2) {
		return "<font color='black'>按月执行</font>";
	}
}
/**
 * 格式化优先级
 * 
 * @param value
 * @param row
 * @returns {String}
 */
function formatPriority(value, row) {
	if (value == 0) {
		return "<font color='blue'>普通活动</font>";
	} else if (value == 1) {
		return "<font color='red'>新手特供活动（首页）</font>";
	} else if (value == 2) {
		return "<font color='gray'>新手特供活动（理财列表页）</font>";
	} else if (value == 3) {
		return "<font color='brown'>新手特供活动（购买页）</font>";
	} 
}
/**
 * 格式化是否在活动中心展示
 * 
 * @param value
 * @param row
 * @returns {String}
 */
function formatCenter(value, row) {
	if (value == 0) {
		return "<font color='blue'>是</font>";
	} else if (value == 1) {
		return "<font color='red'>否</font>";
	} 
}
/**
 * 格式化活动类型
 * 
 * @param value
 * @param row
 * @returns {String}
 */
function formatType(value, row) {
	if (value == 0) {
		return "<font color='blue'>产品活动</font>";
	} else if (value == 1) {
		return "<font color='red'>非产品活动</font>";
	} 
}
/**
 * 格式化是否可预热
 * 
 * @param value
 * @param row
 * @returns {String}
 */
function formatPreheat(value, row) {
	if (value == 0) {
		return "<font color='blue'>否</font>";
	} else if (value == 1) {
		return "<font color='red'>是</font>";
	} 
}
// 添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}
function edit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$('#ffAdd').form('clear');
	if(rows[0].startDate){
		rows[0].startDate = formatTimer(new Date(rows[0].startDate));
	}
	if(rows[0].endDate){
		rows[0].endDate = formatTimer(new Date(rows[0].endDate));
	}
	if(rows[0].pId==null){
		rows[0].pId="";
	}
	showAndHideType(rows[0].actType,rows[0].actType);
	showAndHidePriority(rows[0].priority,rows[0].priority);
	$.getJSON("/rest/product/activityProduct/listContent?r=" + Math.random() + "&id=" + rows[0].id,
		    function(info) {
		       if(info.code==200){
		    	   $("#actDesc").textbox("setValue",info.obj[0]);
		    	   $("#actDescMobile").textbox("setValue",info.obj[1]);
		       }else{
		    	   $.messager.alert('错误提示', info.msg, 'error');
		       }
		});
	$("#ffAdd").form('load',rows[0]);
	$("#DivAdd").dialog('open');
}

// 删除
function del(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/product/activityProduct/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
function saveAndUpdate() {
	showLoading();
	$('#ffAdd').form('submit', {
		url : '/rest/product/activityProduct/saveAndUpdate',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			if (json.code == 200) {
				$("#DivAdd").dialog('close');
				$("#adGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		},
		error : function(data) {
		}
	});
	closeLoading();
}

// 查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		actName:$("#pactName").val(),		
		actUrl:$("#actUrl").val()		
	});
}


function publish(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.getJSON("/rest/product/activityProduct/publish?r=" + Math.random() + "&id=" + rows[0].id,
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

function showAndHidePriority(newValue,oldValue){
	if(newValue>=1){
		disapleAndEnablePriorityProp(true);
		return;
	}
	if(newValue==0){
		disapleAndEnablePriorityProp(false);
		return;
	}
}

function disapleAndEnablePriorityProp(flag){
	if(flag){
		$("#linkUrlPc").textbox('enable');
		$("#linkUrlApp").textbox('enable');
		$("#path1").filebox('enable');
		$("#path2").filebox('enable');
		$("#path3").filebox('enable');
		$("#actPriorityProp").show();
	}else{
		$("#linkUrlPc").textbox('disable');
		$("#linkUrlPc").textbox('disable');
		$("#path1").filebox('disable');
		$("#path2").filebox('disable');
		$("#path3").filebox('disable');
		$("#actPriorityProp").hide();
	}
}

/**
 * 显示和隐藏活动类型输入项目
 * 
 * @param newValue 0产品活动 1非产品活动  
 * @param oldValue
 */
function showAndHideType(newValue,oldValue){
	if(newValue==0){
		disapleAndEnableTypeProp(true);
		return;
	}
	if(newValue==1){
		disapleAndEnableTypeProp(false);
		return;
	}
}

function disapleAndEnableTypeProp(flag){
	if(flag){
		$("#pId").combogrid('enable');
		$("#actTypeProp").show();
	}else{
		$("#pId").combogrid('disable');
		$("#actTypeProp").hide();
	}
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

