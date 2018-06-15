function Trim(str, is_global) {
	var result;
	result = str.replace(/(^\s+)|(\s+$)/g, "");
	if (is_global.toLowerCase() == "g") {
		result = result.replace(/\s/g, "");
	}
	return result;
}
// 查询
function search() {
	var userTel = $("#userTel").val();
	var userName = $("#userName").val();
	/*if(Trim(userTel,'g')==""&&Trim(userName,'g')==""){
		$.messager.alert("提示", "请输入查询条件", "error");
	    return
	}*/
	if($.trim(userTel)==""&&$.trim(userName)==""){
		$.messager.alert("提示", "请输入查询条件", "error");
		return
	}
	$("#adGrid").datagrid({
		type : 'POST',
		url: '/rest/user/bindBank/list',
		queryParams: $.serializeObject($('#searchFrm'))
	});
	
}

function formatBindStatus(value){
	if(value==0){
		return "已解绑";
	}else if(value==1){
		return "<font color=red>已绑定</font>";
	}
}

function formatIsDefault(value){
	if(value==0){
		return "否";
	}else if(value==1){
		return "<font color=red>是</font>";
	}
}

function formatTimer(value){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}
function formatPlatForm(value){
	if(value==1){
		return "pc端";
	}else if(value==2){
		return "手机端";
	}
}
