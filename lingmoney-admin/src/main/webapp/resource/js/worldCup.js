$(function(){
	$('#conmonweal_list').datagrid({
		toolbar: ["-", 
			{  id: 'modify', text: '添加比分', iconCls: '', handler: function () {editConmonewal()} }, "-", 
			{ id: 'publish', text: '发布竞猜', iconCls: '', handler: function () {pushGuess()} }, "-"],
		fit:true,
	    url:'/rest/worldCup/list',
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
			{field:'groupName',title:'小组',width:50},
			{field:'matchDate',title:'竞猜日期',width:140, formatter:formatTimer},
			{field:'matchTime',title:'比赛时间',width:140, formatter:formatTimer},
			{field:'leftTeam',title:'队伍LEFT',width:80},
			{field:'leftImage',title:'队伍logo',width:80, formatter: function(value){
				return '<img alt="" src="' + value + '">';
			}},
			{field:'leftScore',title:'队伍得分',width:60},
			{field:'rightScore',title:'队伍得分',width:60},
			{field:'rightImage',title:'队伍logo',width:80, formatter: function(value){
				return '<img alt="" src="' + value + '">';
			}},
			{field:'rightTeam',title:'队伍RIGHT',width:80},
			{field:'gameResult',title:'比赛结果',width:80, formatter: function(value){
				var restr = '未比赛';
				if(value == 1){
					restr = '平';
				}else if(value == 2){
					restr = 'LEFT获胜';
				}else if(value == 3){
					restr = 'Right获胜';
				}
				return restr;
			}},
			{field:'guessStatus',title:'竞猜状态',width:140}
	    ]]
	});
});

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
 * 发布公益活动
 * @returns
 */
function pushGuess(){
	var rows = $("#conmonweal_list").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$.messager.confirm('确认','您确认想要发布数据吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/worldCup/push?r=" + Math.random() + "&id=" + rows[0].id,
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
		url: '/rest/worldCup/saveAndUpdate',
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
