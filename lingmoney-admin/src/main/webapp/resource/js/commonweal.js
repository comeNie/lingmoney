$(function(){
	getLoveNumber();
	
	$('#conmonweal_list').datagrid({
		toolbar: ["-", 
			{  id: 'add', text: '添加', iconCls: '', handler: function () {addConmonewal()} }, "-", 
			{  id: 'del', text: '删除', iconCls: '', handler: function () {delConmonewal()} }, "-", 
			{  id: 'modify', text: '修改', iconCls: '', handler: function () {editConmonewal()} }, "-", 
			{  id: 'modify', text: '完成', iconCls: '', handler: function () {fulfilConmonewal()} }, "-", 
			{ id: 'publish', text: '发布', iconCls: '', handler: function () {pushConmonewal()} }, "-"],
		fit:true,
	    url:'/rest/commonweal/list',
	    method:'POST',
	    loadMsg : '数据加载中....',
	    pagination:true,
	    singleSelect:true,
	    fitColumns:false,
	    collapsible:true,
	    pageNumber : 1,
	    pageSize : 20,
	    pageList : [20, 40],
	    columns:[[
			{field:'id',title:'数据ID',width:50},
			{field:'pbaName',title:'名称',width:120},
			{field:'pbaDesc',title:'简介',width:200},
			{field:'pbaPicture',title:'展示图片',width:260},
			{field:'maxPraise',title:'礼赞上限',width:60},
			{field:'sumPraise',title:'已礼赞',width:60},
			{field:'status',title:'状态',width:60, formatter: function(value){
				var restr = '初始化';
				if(value == 1){
					restr = '已发布';
				}else if(value == 2){
					restr = '已结束';
				}
				return restr;
			}},
			{field:'stateTime',title:'显示开始时间',width:140, formatter:formatTimer},
			{field:'endTime',title:'显示结束时间',width:140, formatter:formatTimer},
			{field:'sort',title:'排序',width:40},
			{field:'createTime',title:'创建时间',width:140, formatter:formatTimer}
	    ]]
	});
});

/**
 * 获取用户每次获取的爱心数量
 * @returns
 */
function getLoveNumber(){
	$.ajax({
		async:false,
		url:"/rest/commonweal/getLoveNum",
		success:function(data) {
			if(data.code == 200){
				$("#loveNumber").val(data.obj);
			} else{
				$("#loveNumber").val(0);
			}
		}
	});
}

/**
 * 设置用户获取爱心值
 * @returns
 */
function setLoveNUmber(){
	$.messager.confirm('确认','您确认要修改吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/commonweal/setLoveNum?r=" + Math.random() + "&num=" + $("#loveNumber").val(),
    		    function(info) {
    		       if(info.code==200){
    		    	   $.messager.alert('提示', '删除成功', 'info');
    		    	   getLoveNumber();
    		       }else{
    		    	   $.messager.alert('错误提示', info.msg, 'error');
    		       }
    		});    
	    }else{
	    	return
	    }   
	});
}

/**
 * 添加公益活动
 * @returns
 */
function addConmonewal(){
	$('#addConmonewalForm').form('clear');
	$("#addConmonewalWin").dialog('open');
}

/**
 * 编辑公益活动
 * @returns
 */
function editConmonewal(){
	var rows = $("#conmonweal_list").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$('#addConmonewalForm').form('clear');
	if(rows[0].stateTime){
		rows[0].stateTime = formatTimer(new Date(rows[0].stateTime));
	}
	if(rows[0].endTime){
		rows[0].endTime = formatTimer(new Date(rows[0].endTime));
	}
	$("#addConmonewalForm").form('load',rows[0]);
	$("#addConmonewalWin").dialog('open');
}

/**
 * 删除公益活动
 * @returns
 */
function delConmonewal(){
	var rows = $("#conmonweal_list").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/commonweal/del?r=" + Math.random() + "&id=" + rows[0].id,
    		    function(info) {
    		       if(info.code==200){
    		    	   $.messager.alert('提示', '删除成功', 'info');
    		    	   $("#conmonweal_list").datagrid('reload');
    		       }else{
    		    	   $.messager.alert('错误提示', info.msg, 'error');
    		       }
    		});    
	    }else{
	    	return
	    }   
	});
}

/**
 * 发布公益活动
 * @returns
 */
function pushConmonewal(){
	var rows = $("#conmonweal_list").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$.messager.confirm('确认','您确认想要发布数据吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/commonweal/push?r=" + Math.random() + "&id=" + rows[0].id,
    		    function(info) {
    		       if(info.code==200){
    		    	   $.messager.alert('提示', '发布成功', 'info');
    		    	   $("#conmonweal_list").datagrid('reload');
    		       }else{
    		    	   $.messager.alert('错误提示', info.msg, 'error');
    		       }
    		});    
	    }else{
	    	return
	    }   
	});
}

/**
 * 发布公益活动
 * @returns
 */
function fulfilConmonewal(){
	var rows = $("#conmonweal_list").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$.messager.confirm('确认','您确认想要结束公益吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/commonweal/fulfil?r=" + Math.random() + "&id=" + rows[0].id,
    		    function(info) {
    		       if(info.code==200){
    		    	   $.messager.alert('提示', '发布成功', 'info');
    		    	   $("#conmonweal_list").datagrid('reload');
    		       }else{
    		    	   $.messager.alert('错误提示', info.msg, 'error');
    		       }
    		});    
	    }else{
	    	return
	    }   
	});
}
/**
 * 保存与更新
 * @returns
 */
function saveAndUpdate(){
	showLoading();
	$('#addConmonewalForm').form('submit',{
		url: '/rest/commonweal/saveAndUpdate',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#addConmonewalWin").dialog('close');
				$("#conmonweal_list").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

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
