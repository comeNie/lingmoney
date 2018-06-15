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
	text : '修改状态',
	iconCls : 'icon-edit',
	handler : function() {
		changeStatu();
	}
}];
// 添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#acontent").val("");
	createKindEditor("#acontent");
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
		return "不可用";
	}
	else if(value == 1){
		return "<font color='green'>可用</font>";
	}
	else{
		return "<font color='red'>未知</font>";
	}
}

function formatContent(value,row){
	if(value!=null && value!=''){
		var dd=value.replace(/<\/?.+?>/g,"");
		var res=dd.replace(/ /g,"");
		return "<a href='javascript:edit();'>"+res.substring(0,15)+"...</a>";
	}
	
}

/*--验证start---*/
// 英文、下划线、数字
function isId(str){
	var reg=/^\w+$/;
	return reg.test(str);
}

// 长度
function isReachLength(str,len){
	if(str.length>len) {
		return false
	}else{
		return true
	}
}

function regIdentification(id){
	var val=$("#"+id).val();
	if(val==null || val==''){
		alert("请输入标识")
		return false
	}
	if(!isId(val)){
		alert("标识请输入英文、数字或下划线")
		return false
	}
	if(!isReachLength(val,40)){
		alert("标识长度不得超过40字符")
		return false
	}
	return true
}
function regContent(id){
	var val=$("#"+id).val();
	if(val==null || val==''){
		alert("请输入内容")
		return false
	}
	if(!isReachLength(val,10000)){
		alert("内容长度不得超过10000字符")
		return false
	}
	return true
}
/*--验证end---*/


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
			$.getJSON("/rest/info/statement/delete?r=" + Math.random() + "&id=" + rows[0].id,
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

// 修改状态
function changeStatu(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认', '您确认想要修改该项的状态吗？', function(r) {
		if (r) {
			var newStatus=1;
			if(rows[0].status=="1"){
				newStatus=0;
			}
			$.getJSON("/rest/info/statement/update?r=" + Math.random() + "&status=" + newStatus +"&id="+rows[0].id+"&identification="+rows[0].identification,
			    function(info) {
			       if(info.code==200){
			    	   $.messager.alert('提示', '修改成功', 'info');
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
function save(){
	if(regIdentification("identification")&&regContent("acontent")){
		showLoading();
		$.post("/rest/info/statement/add",$("#ffAdd").serialize(),function(data){
			// var json = JSON.parse(data);
			if(data.code == 200){
				KindEditor.remove('#acontent');
				$("#DivAdd").dialog('close');
				$("#adGrid").datagrid('reload');
			}
			else
			{
				$.messager.alert('错误提示', data.msg, 'error');
			}
		  }
		)
		closeLoading();
	}
}
function edit(){
	KindEditor.remove('#econtent');
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	var editor = createKindEditor("#econtent");
	$.getJSON("/rest/info/statement/statement?r=" + Math.random() + "&id=" + rows[0].id,
		    function(info) {
		       if(info.code==200){
		    	   editor.html(info.msg)
		       }else{
		    	   $.messager.alert('错误提示', info.msg, 'error');
		       }
		});
	$("#DivEdit").dialog('open');
}
function update(){
	if(regIdentification("identification")&&regContent("econtent")){
		showLoading();
		$.post("/rest/info/statement/update",$("#ffEdit").serialize(),function(data){
			// var json = JSON.parse(data);
			if(data.code == 200){
				$("#DivEdit").dialog('close');
				$("#adGrid").datagrid('reload');
			}
			else
			{
				$.messager.alert('错误提示', data.msg, 'error');
			}
		  })
		 closeLoading();
	}
}

// 查询
function search(){
	if(regIdentification("identification")){
		$('#adGrid').datagrid('load',{
			page:1,
			identification:$("#identification").val()
		});
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

