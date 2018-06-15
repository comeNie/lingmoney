var activityTypeJson;//活动类型json
var versionStatusJson;//活动状态json
$(function() {
	$.ajax({
		type : "GET",
		url : "/resource/json/activity_type.json",
		async : false,
		dataType : "json",
		success : function(data) {
			activityTypeJson = data;
		}
	});
	$("#type").combogrid("grid").datagrid("loadData", activityTypeJson);
	$("#atype").combogrid("grid").datagrid("loadData", activityTypeJson);
	
	$.ajax({
		type : "GET",
		url : "/resource/json/useStatus.json",
		async : false,
		dataType : "json",
		success : function(data) {
			versionStatusJson = data;
		}
	});
	$("#status").combogrid("grid").datagrid("loadData", versionStatusJson);
});
// 领宝活动类型工具栏
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
	text : '设置中奖概率',
	iconCls : 'icon-setting',
	handler : function() {
		setting();
	}
}, '-', {
	text : '设置限时抢购礼品',
	iconCls : 'icon-organ',
	handler : function() {
		limit();
	}
}];

// 中奖活动工具栏
var toolbars = [{
	text : '添加',
	iconCls : 'icon-add',
	handler : function() {
		adds();
	}
}, '-', {
	text : '修改',
	iconCls : 'icon-edit',
	handler : function() {
		edits();
	}
}, '-', {
	text : '更改状态',
	iconCls : 'icon-ok',
	handler : function() {
		publishs();
	}
}];

// 限时抢活动工具栏
var toolbarss = [{
	text : '添加',
	iconCls : 'icon-add',
	handler : function() {
		addss();
	}
}, '-', {
	text : '修改',
	iconCls : 'icon-edit',
	handler : function() {
		editss();
	}
}, '-', {
	text : '更改状态',
	iconCls : 'icon-ok',
	handler : function() {
		publishss();
	}
}];


// 格式化状态
function formatStatus(value, row) {
	if (!value || value == 0) {
		return "<font color='red'>不可用</font>";
	} else if (value == 1) {
		return "<font color='blue'>可用</font>";
	}
}

// 格式化活动类型
function formatType(value, row) {
	if (value == 0) {
		return "<font color='green'>抽奖</font>";
	} else if (value == 1) {
		return "<font color='blue'>限时抢</font>";
	}
}

// 格式化时间
function formatTimer(value, row) {
	if (value) {
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}

// 格式化积分
function formatIntegral(value, row){
	if (value == null) {
		return "<font color='red'>无</font>";
	}else {
		return value;
	}
}

// 格式化图片
function formatPicture(value,row,index){
	if('' != value && null != value){
		value = '<img style="width:46px; height:46px; margin-top:5px;" src="' + value + '">';
	  	return  value;
	}
}

// 删除编辑器
function removeKindEditor(){
	KindEditor.remove('#rule');
}

// 创建编辑器
function buildKindEditor(){
	var editor_1 = createKindEditor('#rule').html('');
}

// 类型是否为抽奖，抽奖则有领宝个数，限时抢则没有
function integral(flag){
	if(flag==0){// 抽奖类型，领宝个数显示
		$('#integralTh').show();
		$('#integralTd').show();
		$('#integral').numberbox({ 
			required:true,
			editable:true
		});
	}else if(flag==1){// 限时抢，领宝个数隐藏
		$('#integralTh').hide();
		$('#integralTd').hide();
		$('#integral').numberbox({ 
			required:false,
			editable:false
		});
	}
}

// 判断为礼品还是鼓励语，0礼品，1鼓励语
function word(flag){
	if(flag==0){// 礼品，选择礼品下拉框显示，鼓励语输入框隐藏
		$("#giftIdTd").show();
		$("#wordTd").hide();
		$('#giftId').combogrid({ 
			required:true
		});
		$('#word').textbox({ 
			required:false
		});
	}else if(flag==1){// 鼓励语，鼓励语输入框显示，选择礼品下拉框隐藏
		$("#giftIdTd").hide();
		$("#wordTd").show();
		$('#giftId').combogrid({ 
			required:false
		});
		$('#word').textbox({ 
			required:true
		});
	}
}

// 根据选择不同礼品给原积分赋值
function getIntegral(id){
	$.getJSON("/rest/gift/lingbaoGift/findById.html?r=" + Math.random() + "&id=" + id, function(info) {
		if (info.code == 200) {
			$("#organIntegral").text(info.obj.integral);
			$("#integralLimit").val(info.obj.integral);
		} 
	});
}
// 查询活动类型
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		name:$("#name").val(),
		status:$("#status").combogrid('getValue'),
		type:$("#type").combogrid('getValue')
	});
}

// 添加活动类型
function add(){
	$('#ffAdd').form('clear');
	integral(1);
	removeKindEditor();
	buildKindEditor();
	$("#DivAdd").dialog('open');
}

// 修改活动类型
function edit(){
	removeKindEditor();
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffAdd").form('clear');
	if(rows[0].startTime){
		rows[0].startTime = formatTimer(new Date(rows[0].startTime));
	}
	if(rows[0].endTime){
		rows[0].endTime = formatTimer(new Date(rows[0].endTime));
	}
	if(rows[0].type==0){// 抽奖
		integral(0);
	}else if(rows[0].type==1){// 限时抢
		integral(1);
	}
	var editor_1 = createKindEditor('#rule').html('');
	$.getJSON("/rest/gift/lingbaoLotteryType/listEditor.html?r=" + Math.random() + "&id=" + rows[0].id, function(info) {
		if (info.code == 200) {
			editor_1.html(info.obj[0]);
		} else {
			$.messager.alert('错误提示', info.msg, 'error');
		}
	});
	$("#ffAdd").form('load',rows[0]);
	$("#DivAdd").dialog('open');
}

// 删除活动类型
function del(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){
			$.getJSON("/rest/gift/lingbaoLotteryType/delete?r=" + Math.random() + "&id=" + rows[0].id,
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

// 保存或修改活动类型
function saveAndUpdate(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/gift/lingbaoLotteryType/saveAndUpdate',
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

// 更改活动状态
function publish() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.getJSON(
			"/rest/gift/lingbaoLotteryType/publish?r=" + Math.random() + "&id=" + rows[0].id,
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

// 设置中奖概率
function setting(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	if (rows[0].type != 0) {
		$.messager.alert("提示", "请选择抽奖活动", "error");
		return
	}
	$('#DivSetting').dialog({ 
		title:'设置中奖概率'+'('+rows[0].name+')'
	});
	$("#settingGrid").datagrid({ 
		url: '/rest/gift/lingbaoLotteryRatio/list?typeId='+rows[0].id
	});
	$("#DivSetting").dialog('open');
}

// 添加中奖概率
function adds() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	$('#ffLottery').form('clear');
	$("#ffLottery").form('load', {
		typeId: rows[0].id
    });
	$('#typeId').textbox({ 
		editable:false 
	});
	$('#DivLottery').dialog({ 
		title:'添加中奖概率'+'('+rows[0].name+')'
	});
	word(0);
	$("#isGift").trigger("click");
	$("#DivLottery").dialog('open');
}

// 修改中奖概率
function edits() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	var rowss = $("#settingGrid").datagrid("getSelections");
	if (rowss.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	$('#ffLottery').form('clear');
	$('#typeId').textbox({ 
		editable:false 
	});
	$('#DivLottery').dialog({ 
		title:'修改中奖概率'+'('+rows[0].name+')',
		iconCls: 'icon-edit'
	});
	if(rowss[0].giftId == null){// 文字
		$("#isWord").trigger("click");
	}else{// 礼品
		$("#isGift").trigger("click");
		$('#giftId').combogrid('grid').datagrid('load', '/rest/gift/lingbaoGift/list?shelfStatus=1&id='+rowss[0].giftId);
	}
	$("#ffLottery").form('load', rowss[0]);
	$("#ffLottery").form('load', {
		typeId: rows[0].id
    });
	$("#DivLottery").dialog('open');
}
$(function(){
	$("#isGift").click(function(){
		word(0);
	});
	$("#isWord").click(function(){
		word(1);
	});
})
// 删除中奖概率
function dels() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	var rowss = $("#settingGrid").datagrid("getSelections");
	if (rowss.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.messager.confirm('确认', '您确认想要删除该项吗？', function(r) {
		if (r) {
			$.getJSON("/rest/gift/lingbaoLotteryRatio/delete?r=" + Math.random() + "&id=" + rowss[0].id, function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '删除成功', 'info');
					$("#settingGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
		} else {
			return
		}
	});
	closeLoading();
}

// 添加或修改中奖概率
function saveAndUpdateLottery() {
	$("#typeLottery").val(0);
	showLoading();
	$('#ffLottery').form('submit', {
		url : '/rest/gift/lingbaoLotteryRatio/saveAndUpdate',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			if (json.code == 200) {
				$("#DivLottery").dialog('close');
				$("#settingGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

// 更改中奖概率状态
function publishs() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	var rowss = $("#settingGrid").datagrid("getSelections");
	if (rowss.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.getJSON(
			"/rest/gift/lingbaoLotteryRatio/publish?r=" + Math.random() + "&id=" + rowss[0].id,
			function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '设置成功', 'info');
					$("#settingGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
	closeLoading();
}

// 设置限时抢购礼品
function limit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	if (rows[0].type != 1) {
		$.messager.alert("提示", "请选择限时抢活动", "error");
		return
	}
	$('#DivLimit').dialog({ 
		title:'设置限时抢购礼品'+'('+rows[0].name+')'
	});
	$("#limitGrid").datagrid({ 
		url: '/rest/gift/lingbaoLotteryRatio/list?typeId='+rows[0].id,
		pageList: [5,10],
		pageSize: 5
	});
	$("#DivLimit").dialog('open');
}

// 添加限时抢礼品
function addss() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	$('#ffLimitGift').form('clear');
	$('#typeId').textbox({ 
		editable:false 
	});
	$('#DivLimitGift').dialog({ 
		title:'添加限时抢礼品'+'('+rows[0].name+')'
	});
	$("#ffLimitGift").form('load', {
		typeId: rows[0].id
    });
	$("#DivLimitGift").dialog('open');
}

// 修改限时抢礼品
function editss() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	var rowss = $("#limitGrid").datagrid("getSelections");
	if (rowss.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	$('#ffLimitGift').form('clear');
	$('#typeIdLimit').textbox({ 
		editable:false 
	});
	$('#DivLimitGift').dialog({ 
		title:'修改限时抢礼品'+'('+rows[0].name+')',
		iconCls: 'icon-edit'
	});
	$("#ffLimitGift").form('load', rowss[0]);
	$('#giftIdLimit').combogrid('grid').datagrid('load', '/rest/gift/lingbaoGift/list?shelfStatus=1&id='+rowss[0].giftId);
	$("#DivLimitGift").dialog('open');
}

// 删除限时抢礼品
function delss() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	var rowss = $("#limitGrid").datagrid("getSelections");
	if (rowss.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.messager.confirm('确认', '您确认想要删除该项吗？', function(r) {
		if (r) {
			$.getJSON("/rest/gift/lingbaoLotteryRatio/delete?r=" + Math.random() + "&id=" + rowss[0].id, function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '删除成功', 'info');
					$("#limitGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
		} else {
			return
		}
	});
	closeLoading();
}

// 修改限时抢礼品
function saveAndUpdateLimitGift() {
	$("#typeLimit").val(1);
	showLoading();
	$('#ffLimitGift').form('submit', {
		url : '/rest/gift/lingbaoLotteryRatio/saveAndUpdate',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			if (json.code == 200) {
				$("#DivLimitGift").dialog('close');
				$("#limitGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

// 更改限时抢礼品状态
function publishss() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	var rowss = $("#limitGrid").datagrid("getSelections");
	if (rowss.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.getJSON(
			"/rest/gift/lingbaoLotteryRatio/publish?r=" + Math.random() + "&id=" + rowss[0].id,
			function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '设置成功', 'info');
					$("#limitGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
	closeLoading();
}

// 创建富文本编辑器
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

// 通过名称模糊搜索礼品
function searchName(i){
	if(i==1){
		$('#giftId').combogrid('grid').datagrid('load',{
			page:1,
			id:$('#searchId').val(),
			name:$('#searchName').val()
		});
	}else if(i==2){
		$('#giftIdLimit').combogrid('grid').datagrid('load',{
			page:1,
			id:$('#searchId2').val(),
			name:$('#searchName2').val()
		});
	}
}