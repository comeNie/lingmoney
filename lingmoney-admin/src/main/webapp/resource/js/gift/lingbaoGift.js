var yesOrNoJson;// 是或否json
var applyTypeJson;// 应用类型json
var receiveWayJson;// 礼品领取方式json
var typeJson;// 礼品类别json

$(function() {
	$.ajax({
		type : "GET",
		url : "/resource/json/yesOrNo.json",
		async : false,
		dataType : "json",
		success : function(data) {
			yesOrNoJson = data;
		}
	});
	$("#shelfStatus").combogrid("grid").datagrid("loadData", yesOrNoJson);
	$("#newStatus").combogrid("grid").datagrid("loadData", yesOrNoJson);
	$("#recommend").combogrid("grid").datagrid("loadData", yesOrNoJson);
	$("#isReachWarning").combogrid("grid").datagrid("loadData", yesOrNoJson);
	
	$.ajax({
		type : "GET",
		url : "/resource/json/receiveWay.json",
		async : false,
		dataType : "json",
		success : function(data) {
			receiveWayJson = data;
		}
	});
	$("#receiveWay").combogrid("grid").datagrid("loadData", receiveWayJson);
	$("#areceiveWay").combogrid("grid").datagrid("loadData", receiveWayJson);
	
	$.ajax({
		type : "GET",
		url : "/resource/json/applyType.json",
		async : false,
		dataType : "json",
		success : function(data) {
			applyTypeJson = data;
		}
	});
	$("#applyType").combogrid("grid").datagrid("loadData", applyTypeJson);
	$("#applyTypes").combogrid("grid").datagrid("loadData", applyTypeJson);
	
	$.ajax({
		type : "GET",
		url : "/resource/json/giftType.json",
		async : false,
		dataType : "json",
		success : function(data) {
			typeJson = data;
		}
	});
	$("#type").combogrid("grid").datagrid("loadData", typeJson);
	$("#types").combogrid("grid").datagrid("loadData", typeJson);
	
});

function giftTypePick(index, row) {
	if (row.id == 3) {
		$('#redpacketId').combogrid('enable');
	} else {
		$('#redpacketId').combogrid('disable');
	}
}

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
	text : '上(下)架',
	iconCls : 'icon-ok',
	handler : function() {
		shelf();
	}
}, '-', {
	text : '是否最新',
	iconCls : 'icon-ok',
	handler : function() {
		newStatus();
	}
}, '-', {
	text : '是否推荐',
	iconCls : 'icon-ok',
	handler : function() {
		recommend();
	}
}, '-', {
	text : '导入excel',
	iconCls : 'icon-excel',
	handler : function() {
		impExcel();
	}
}, '-', {
	text : '复制',
	iconCls : 'icon-reload',
	handler : function() {
		copyGift();
	}
}];

// 添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#storeTr").show();
	$("#storeTr2").hide();
	$('#storeSpan').html($("#hasStore").numberbox("getValue"));
	$("#hasStore").numberbox({
		"required":true,
		"onChange":function(newValue,oldValue){  
			$('#store').textbox('setValue',$(this).val());
			$('#storeSpan').html($(this).val());
		}
	});
	removeKindEditor();
	buildKindEditor();
	$("#changeType").val(0);
	$("#DivAdd").dialog('open');
}

// 修改窗口
function edit(){
	removeKindEditor();
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#storeTr").hide();
	$("#storeTr2").show();
	$("#hasStore").numberbox({
		"required":false
	});
	$("#ffAdd").form('clear');
	$("#ffAdd").form('load',rows[0]);
	$("#changeType").val(1);
	$('#typeId').combogrid('grid').datagrid('load', '/rest/gift/lingbaoGiftType/list?status=1&id='+rows[0].typeId);
	$('#supplierId').combogrid('grid').datagrid('load', '/rest/gift/lingbaoSupplier/list?status=1&id='+rows[0].supplierId);
	var editor_1 = createKindEditor('#introduction').html('');
	var editor_2 = createKindEditor('#property').html('');
	$.getJSON("/rest/gift/lingbaoGift/listEditor.html?r=" + Math.random() + "&id=" + rows[0].id, function(info) {
		if (info.code == 200) {
			editor_1.html(info.obj[0]);
			editor_2.html(info.obj[1]);
		} else {
			$.messager.alert('错误提示', info.msg, 'error');
		}
	});
	$("#DivAdd").dialog('open');
}

// 复制
function copyGift(){
	removeKindEditor();
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return;
	}
	$("#ffAdd").form('clear');
	$("#ffAdd").form('load',rows[0]);
	$("#storeTr").show();
	$("#storeTr2").hide();
	$('#storeSpan').html($("#hasStore").numberbox("getValue"));
	$("#hasStore").numberbox({
		"required":true,
		"onChange":function(newValue,oldValue){  
			$('#store').textbox('setValue',$(this).val());
			$('#storeSpan').html($(this).val());
		}
	});
	$("#giftId").val("");
	$("#changeType").val(2);
	$('#typeId').combogrid('grid').datagrid('load', '/rest/gift/lingbaoGiftType/list?status=1&id='+rows[0].typeId);
	$('#supplierId').combogrid('grid').datagrid('load', '/rest/gift/lingbaoSupplier/list?status=1&id='+rows[0].supplierId);
	var editor_1 = createKindEditor('#introduction').html('');
	var editor_2 = createKindEditor('#property').html('');
	$.getJSON("/rest/gift/lingbaoGift/listEditor.html?r=" + Math.random() + "&id=" + rows[0].id, function(info) {
		if (info.code == 200) {
			editor_1.html(info.obj[0]);
			editor_2.html(info.obj[1]);
		} else {
			$.messager.alert('错误提示', info.msg, 'error');
		}
	});
	$("#DivAdd").dialog('open');
}

// 保存
function saveAndUpdate(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/gift/lingbaoGift/saveAndUpdate',
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
function removeKindEditor(){
	KindEditor.remove('#introduction');
	KindEditor.remove('#property');
}
function buildKindEditor(){
	var editor_1 = createKindEditor('#introduction').html('');
	var editor_2 = createKindEditor('#property').html('');
}

// 格式化tip
function formatTip(value,row,index){
	if('' != value && null != value){
		 var content = '<li title="' + value + '" class="tip">' + value + '</li>';  
	     return content;  
	}
}

// 格式化图片
function formatPicture(value,row,index){
	if('' != value && null != value){
		value = '<img style="width:46px; height:46px; margin-top:5px;" src="' + value + '">';
	  	return  value;
	}
}

function formatTimer(value, row) {
	if (value) {
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}
function formatDate(value, row) {
	if (value) {
		return new Date(value).format("yyyy-MM-dd");
	}
	return "";
}

function formatYesOrNo(value, row) {
	if (!value || value == 0) {
		return "<font color='black'>否</font>";
	} else if (value == 1) {
		return "<font color='blue'>是</font>";
	} 
}

function formatShelfStatus(value, row) {
	if (!value || value == 0) {
		return "<font color='black'>下架</font>";
	} else if (value == 1) {
		return "<font color='blue'>上架</font>";
	} 
}

function formatReceiveWay(value, row) {
	if (!value || value == 0) {
		return "<font color='black'>寄送</font>";
	} else if (value == 1) {
		return "<font color='blue'>自取</font>";
	} else if (value == 2) {
		return "<font color='green'>无需领取</font>";
	} 
}

function formatType(value, row) {
	if (!value || value == 0) {
		return "<font color='black'>实物礼品</font>";
	} else if (value == 1) {
		return "<font color='blue'>虚拟礼品</font>";
	} else if (value == 2) {
		return "<font color='red'>领宝</font>";
	} else if (value == 3) {
		return "<font color='green'>加息券</font>";
	}
}

function formatApplyType(value, row) {
	if (!value || value == 0) {
		return "<font color='black'>兑换</font>";
	} else if (value == 1) {
		return "<font color='blue'>抽奖</font>";
	} else if (value == 2) {
		return "<font color='red'>两者皆可</font>";
	}
}

// 礼品上下架
function shelf() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.getJSON(
			"/rest/gift/lingbaoGift/shelf?r=" + Math.random() + "&id=" + rows[0].id,
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

// 是否最新
function newStatus() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.getJSON(
			"/rest/gift/lingbaoGift/newStatus?r=" + Math.random() + "&id=" + rows[0].id,
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
// 是否推荐
function recommend() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.getJSON(
			"/rest/gift/lingbaoGift/recommend?r=" + Math.random() + "&id=" + rows[0].id,
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


// 添加窗口的显示
function impExcel(){
	$('#ffimpExcel').form('clear');
	$("#DivimpExcel").dialog('open');
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
			$.getJSON("/rest/gift/lingbaoGift/delete?r=" + Math.random() + "&id=" + rows[0].id,
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

// 上传excel
function upload(){
	var excelFile = $("#ffimpExcel :file")[0].files;
	if(!/\.(xls|xlsx|XLS|XLSX)$/.test(excelFile[0].name)){
		$.messager.alert('提示','请上传有效的excel文件'); 
		return false;
	}
	showLoading();
	$('#ffimpExcel').form('submit',{
		url: '/rest/gift/lingbaoGift/importExcel',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivimpExcel").dialog('close');
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

// 保存
function addInStore(){
	showLoading();
	$('#ffAddStore').form('submit',{
		url: '/rest/gift/lingbaoGift/addInStore',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivAddStore").dialog('close');
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

// 查询库存预警值大于库存的礼品
function lingbaoStoreWarning(){
	$("#adGrid").datagrid({  
		type : 'POST',
		url: '/rest/gift/lingbaoGift/lingbaoStoreWarning'
	});
	window.setTimeout(function(){
		var rows = $("#adGrid").datagrid("getRows");
		if(rows.length>0){
			$.newmessager.show('库存告急', '部分礼品库存告急！！！', 1);
		}
	},100)
	
}

function searchName(){
	$('#stypeId').combogrid('grid').datagrid('load',{
		page:1,
		name:$('#searchName').val()
	});
}


// 查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		name:$("#name").val(),
		code:$("#code").val(),
		typeId:$("#stypeId").combogrid('getValue'),
		shelfStatus:$("#shelfStatus").combogrid('getValue'),
		newStatus:$("#newStatus").combogrid('getValue'),
		recommend:$("#recommend").combogrid('getValue'),
		receiveWay:$("#receiveWay").combogrid('getValue'),
		isReachWarning:$("#isReachWarning").combogrid('getValue'),
		supplierId:$("#supplierId").combogrid('getValue'),
		type:$("#types").combogrid('getValue'),
		applyType:$("#applyTypes").combogrid('getValue')
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


$.event.special.valuechange = {
  teardown: function (namespaces) {
    $(this).unbind('.valuechange');
  },

  handler: function (e) {
    $.event.special.valuechange.triggerChanged($(this));
  },

  add: function (obj) {
    $(this).on('keyup.valuechange cut.valuechange paste.valuechange input.valuechange', obj.selector, $.event.special.valuechange.handler)
  },

  triggerChanged: function (element) {
    var current = element[0].contentEditable === 'true' ? element.html() : element.val()
      , previous = typeof element.data('previous') === 'undefined' ? element[0].defaultValue : element.data('previous')
    if (current !== previous) {
      element.trigger('valuechange', [element.data('previous')])
      element.data('previous', current)
    }
  }
}