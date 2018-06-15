var toolbar = [ {
	text : '重置密码',
	iconCls : 'icon-reload',
	handler : function() {
		resetpwd();
	}
}, '-', {
	text : '账户信息',
	iconCls : 'icon-view',
	handler : function() {
		account();
	}
}, '-', {
	text : '用户属性',
	iconCls : 'icon-book',
	handler : function() {
		properties();
	}
}, '-', {
	text : '批量注册',
	iconCls : 'icon-batch-add',
	handler : function() {
		regist();
	}
}, '-', {
	text : '用户作废',
	iconCls : 'icon-remove',
	handler : function() {
		deleteUser();
	}
}, '-', {
	text : '导出excel',
	iconCls : 'icon-ok',
	handler : function() {
		exportExcel();
	}
}, '-', {
	text : '查看购买记录',
	iconCls : 'icon-tip',
	handler : function() {
		seeBuyRecords();
	}
}, '-', {
	text : '赠送加息券',
	iconCls : 'icon-add',
	handler : function() {
		giveRedPacket();
	}
}];

// 赠送人ID组，多个用英文逗号分隔
var uids = "";
function giveRedPacket() {
	//初始值
	uids = "";
	var rows = $('#adGrid').datagrid('getSelections');
	if (rows.length == 0) {
	    $.messager.alert('提示', '请选择一条记录', 'error');
	    return;
	}
	
	$('#giveRedPacketDiv').dialog('open');
	
	var html = "<tr><th>姓名</th><th>手机号</th></tr>";
	for (var i = 0; i < rows.length; i++) {
		html += "<tr><td>"+ rows[i].name +"</td><td>"+ rows[i].telephone +"</td></tr>";
		if (i == rows.length - 1) {
			uids += rows[i].id;
		} else {
			uids += rows[i].id + ",";
		}
	}
	$('#giveRedPacketList').empty().append(html);
}

function hrpTypeFormat(value) {
	if (value == 1) {
		return "加息券";
	} else if (value == 2) {
		return "返现红包";
	}
}

function confirmGiveRedPacket() {
	var rId = $('#redPacketId').combogrid('getValue')
	if (rId == null || rId == '') {
		$.messager.alert('提示', '请选择一张加息券');
		return false;
	}
	
	$.messager.confirm('提示', '确认赠送加息券?', function(b) {
		if(b) {
			$.ajax({
				url : '/rest/hxRedPacket/giveRedPacketByUid',
				data : {rId : rId, uids : uids},
				type : 'post',
				success : function (data) {
					$.messager.alert('提示', data.msg);
					$('#giveRedPacketDiv').dialog('close');
				}
			});
		}
	});
}

function cancelGiveRedPacket() {
	$('#giveRedPacketDiv').dialog('close');
}



function formatTimer(value){
	if(value){
		return new Date(value).format("yyyy-MM-dd");
	}else{
		return "";
	}
}
function formatTimers(value){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}

function formatType(value){
    if(value==0){
        return '移动用户';
      }else if(value==1){
        return '<font color="green">互联网用户</font>';
      } else if(value==9){
    	  return '<font color="green">废弃用户</font>';  
      }
      else{
    	return '<font color="red">未知</font>';
      }
}
// 添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}
// 弹出批量注册窗口
function regist(){
	$("#ffRegist").form('clear');
	$("#DivRegist").dialog('open');
}
function edit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	if(rows[0].type==0){
		$("#efixTypeTr").show();
	}
	$("#DivEdit").dialog('open');
}

// 注销账户
function deleteUser(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}	
	if (rows[0].Type == 9) {
	    $.messager.alert("提示", "用户已作废", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认','您确定要注销此用户吗？',function(r){    
	    if (r){    
		     $.getJSON("/rest/user/users/deleteUser?id=" + rows[0].id,
			 function(info) {
				if(info.code==200){
			    	   $.messager.alert('提示', '注销成功', 'info');
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



// 重置密码
function resetpwd(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}	
	showLoading();
	$.messager.confirm('确认','您确认想要重置密码吗？',function(r){    
	    if (r){    
		     $.getJSON("/rest/user/users/updateUsersPswByInit?id=" + rows[0].id,
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
	    	$.getJSON("/rest/user/users/delete?r=" + Math.random() + "&id=" + rows[0].id,
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

/*
 * function userInfo(){ //$('#easyui-layout') var rows =
 * $("#adGrid").datagrid("getSelections"); if (rows.length == 0) {
 * $.messager.alert("提示", "请选择一条记录", "error"); return } showLoading();
 * $("#adGrid").datagrid({ url: '/rest/user/usermessage/list' });
 * $("#message").dialog('open'); //
 * $.getJSON("/rest/user/users/selectUsersAccount?uId=" + rows[0].id, //
 * function(info) { // $("#addAccount").form('load',info.data); //
 * $("#message").dialog('open'); // }); closeLoading(); }
 */

// 保存
function account(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.getJSON("/rest/user/users/selectUsersAccount?uId=" + rows[0].id,
		    function(info) {
	    	   $("#addAccount").form('load',info.data);
	    	   $("#account").dialog('open');
		});
	closeLoading();
}


function properties(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	
	
	$.getJSON("/rest/user/users/selectUsersProperties?uId=" + rows[0].id,
	    function(info) {
				if(info.data!=undefined) {
					if(info.data.regDate){
						info.data.regDate = formatTimer(new Date(info.data.regDate));
					}
					if(info.data.certification==0){
						info.data.certification='否';
					}else if(info.data.certification==1){
						info.data.certification='是';
					}else{
						info.data.certification='未知';
					}
					
					if(info.data.bank==0){
						info.data.bank='否';
					}else if(info.data.bank==1){
						info.data.bank='是';
					}else{
						info.data.bank='未知';
					}
					
					if(info.data.autoPay==0){
						info.data.autoPay='否';
					}else if(info.data.autoPay==1){
						info.data.autoPay='是';
					}else{
						info.data.autoPay='未知';
					}
					
					if(info.data.firstBuy==0){
						info.data.firstBuy='否';
					}else if(info.data.firstBuy==1){
						info.data.firstBuy='是';
					}else{
						info.data.firstBuy='未知';
					}
					
					$("#propertiesAdd").form('load',info.data);
					$("#properties").dialog('open');
				} else {
					$.messager.alert("提示","没有该用户信息","info");
				}
	}); 
	closeLoading();
}

function bank(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.getJSON("/rest/user/users/selectUsersBank?r=" + Math.random() + "&uId=" + rows[0].id,
		    function(info) {
			   if(info.data!=null) {
				   $("#Addbank").form('load',info.data);
				   $("#bank").dialog('open');
			   }else {
				   $.messager.alert("提示","该用户未绑卡","info");
			   }
		}); 
	
	closeLoading();
}
function formatStatus(value){
    if(value==0)
        return '未生效';
    else if(value==1)
        return '<font color="green">已生效</font>';
}

function formatBank(value){
    if(value==0)
        return '否';
    else if(value==1)
        return '<font color="green">是</font>';
}


function edit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	$("#DivEdit").dialog('open');
}
function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/user/users/update',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivEdit").dialog('close');
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
function upload(){
	showLoading();
	$('#ffRegist').form('submit',{
		url: '/rest/user/users/upload',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			// alert(json);
			if(json.code == 200){
				$("#DivRegist").dialog('close');
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

//查询
function search() {
	var columns = [];
	var pageSize = 10;
    var pageList = [];
	if(showMgr){//显示理财经理
		pageSize = 100;
		pageList = [ 100, 1000, 10000 ];
		columns = [ [ 
		             {width : '100', title : '用户id', field : 'id', sortable : true},
		             {width : '100', title : '用户姓名', field : 'name', sortable : true},
		             {width : '100', title : '用户手机号', field : 'telephone', sortable : true},
		             {width : '100', title : '用户推荐码', field : 'referralCode', sortable : true},
		             {width : '100', title : '是否有理财经理', field : 'isHaveManager', sortable : true,
		             	formatter : function(value, row, index) {
		             		switch (value) {
		 					case 0:
		 						return '<font color="blue">否</font>';
		 					case 1:
		 						return '是';
		 					}
		 	            }
		             },
		             {width : '100', title : '是否绑卡激活', field : 'isBindCard', sortable : true,
		            	 formatter : function(value, row, index) {
		            		 switch (value) {
		            		 case 0:
		            			 return '<font color="blue">否</font>';
		            		 case 1:
		            			 return '是';
		            		 }
		            	 }
		             },
		             {width : '100', title : '是否买过产品', field : 'isFinancialProduct', sortable : true,
		             	formatter : function(value, row, index) {
		             		switch (value) {
		             		case 0:
		             			return '<font color="blue">否</font>';
		             		case 1:
		             			return '是';
		             		}
		             	}
		             },
		             {width : '100', title : '是否持有产品', field : 'isHoldProduct', sortable : true,
		             	formatter : function(value, row, index) {
		             		switch (value) {
		             		case 0:
		             			return '<font color="blue">否</font>';
		             		case 1:
		             			return '是';
		             		}
		             	}
		             },
		             {width : '100', title : '理财金额', field : 'financialMoney', sortable : true},
		             {width : '100', title : '持有金额', field : 'holdMoney', sortable : true},
		             {width : '100', title : '理财经理编号', field : 'managerId', sortable : true},
		             {width : '100', title : '理财经理姓名', field : 'managerName', sortable : true},
		             {width : '100', title : '理财经理公司', field : 'managerCom', sortable : true},
		             {width : '100', title : '理财经理部门', field : 'managerDept', sortable : true},
		             {width : '100', title : '注册日期', field : 'regDate', sortable : true,
		             	formatter : formatTimer
		             },
		             {width : '100', title : '年龄', field : 'age', sortable : false},
		             {width : '100', title : '性别', field : 'sex', sortable : false},
		             {width : '100', title : '平台类型', field : 'platformType', sortable : true,
		             	formatter : function(value, row, index) {
		             		switch (value) {
		 					case 0:
		 						return '<font color="blue">注册用户</font>';
		 					case 1:
		 						return '导入用户';
		 					}
		 	            }
		             },
		             {width : '100', title : '注册渠道', field : 'registChannel', sortable : true}
		         ] ];
	}else{//不显示理财经理
		pageSize = 10;
		pageList = [ 10, 50, 100 ];
		columns = [ [ 
		             {width : '100', title : '用户id', field : 'id', sortable : true},
		             {width : '100', title : '用户姓名', field : 'name', sortable : true},
		             {width : '100', title : '用户手机号', field : 'telephone', sortable : true},
		             {width : '100', title : '用户推荐码', field : 'referralCode', sortable : true},
		             {width : '100', title : '是否有理财经理', field : 'isHaveManager', sortable : true,
			             	formatter : function(value, row, index) {
			             		switch (value) {
			 					case 0:
			 						return '<font color="blue">否</font>';
			 					case 1:
			 						return '是';
			 					}
			 	            }
			             },
		             {width : '100', title : '是否绑卡激活', field : 'isBindCard', sortable : true,
		             	formatter : function(value, row, index) {
		             		switch (value) {
		 					case 0:
		 						return '<font color="blue">否</font>';
		 					case 1:
		 						return '是';
		 					}
		 	            }
		             },
		             {width : '100', title : '是否买过产品', field : 'isFinancialProduct', sortable : true,
		             	formatter : function(value, row, index) {
		             		switch (value) {
		             		case 0:
		             			return '<font color="blue">否</font>';
		             		case 1:
		             			return '是';
		             		}
		             	}
		             },
		             {width : '100', title : '是否持有产品', field : 'isHoldProduct', sortable : true,
		             	formatter : function(value, row, index) {
		             		switch (value) {
		             		case 0:
		             			return '<font color="blue">否</font>';
		             		case 1:
		             			return '是';
		             		}
		             	}
		             },
		             {width : '100', title : '理财金额', field : 'financialMoney', sortable : true},
		             {width : '100', title : '持有金额', field : 'holdMoney', sortable : true},
		             {width : '100', title : '理财经理编号', field : 'managerId', sortable : false},
		             {width : '100', title : '理财经理姓名', field : 'managerName', sortable : false},
		             {width : '100', title : '理财经理公司', field : 'managerCom', sortable : false},
		             {width : '100', title : '理财经理部门', field : 'managerDept', sortable : false},
		             {width : '100', title : '注册日期', field : 'regDate', sortable : true,
		             	formatter : formatTimer
		             },
		             {width : '100', title : '年龄', field : 'age', sortable : false},
		             {width : '100', title : '性别', field : 'sex', sortable : false},
		             {width : '100', title : '平台类型', field : 'platformType', sortable : true,
		             	formatter : function(value, row, index) {
		             		switch (value) {
		 					case 0:
		 						return '<font color="blue">注册用户</font>';
		 					case 1:
		 						return '导入用户';
		 					}
		 	            }
		             },
		             {width : '100', title : '注册渠道', field : 'registChannel', sortable : true}
		             /*,
		             {width : '100', title : '操作', field : '_operate', sortable : false,
		            	 formatter : formatOperate
		             }*/
		         ] ];
	}
	$('#adGrid').datagrid({
		url:'/rest/user/users/usersList',
		queryParams: $.serializeObject($('#searchForm')),
		height : 'auto',
        title : '查询结果',
        iconCls:'icon-book',
        border : false,
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : false,
        sortOrder: 'asc',
        sortable: true,
        fitColumns: true,
        loadMsg:'请稍后，正在加载数据...',
        collapsible:true,
        pageSize : pageSize,
        pageList : pageList,
        columns : columns,
        toolbar : toolbar
	});
}

// 格式化操作按钮
function formatOperate(val,row,index){  
    return '<a href="javascript:void(0);" onclick="seeBuyRecords('+index+')">查看购买记录</a>';  
}  

// 查看购买记录
function seeBuyRecords(tStatus){
	if(tStatus==null){
		$('#tStatus').combobox('setValue', '');
	}
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
    $('#DivSetting').dialog({ 
		title:'查看购买记录：'+'('+rows[0].name+')'
	});
	$("#settingGrid").datagrid({ 
		url:'/rest/user/users/tradingList',
		queryParams: {
			uId:rows[0].id,
			tStatus:tStatus
		},
		height : 'auto',
        border : false,
        striped : true,
        rownumbers : true,
        pagination : false,
        singleSelect : true,
        sortOrder: 'asc',
        sortable: true,
        fitColumns: false,
        loadMsg:'请稍后，正在加载数据...',
        collapsible:true,
        columns : [ [ 
		             {width : '50', title : '交易id', field : 'tId', sortable : true},
		             {width : '50', title : '用户id', field : 'uId', sortable : true},
		             {width : '50', title : '产品id', field : 'pId', sortable : true},
		             {width : '170', title : '产品名称', field : 'pName', sortable : true},
		             {width : '50', title : '收益率', field : 'fYield', sortable : true},
		             {width : '70', title : '期限（天）', field : 'fTime', sortable : true},
		             {width : '70', title : '产品状态', field : 'pStatus', sortable : true,
		             	formatter : function(value, row, index) {
		             		switch (value) {
			             		case 0:
					                return '初始'+'('+value+')';
			             		case 1:
					                return '募集中'+'('+value+')';
			             		case 2:
					                return '运行中'+'('+value+')';
			             		case 3:
					                return '回款中'+'('+value+')';
			             		case 4:
					                return '已结束'+'('+value+')';
			             		case 5:
					                return '已作废'+'('+value+')';
		             		}
		 	            }
		             },
		             {width : '70', title : '理财金额', field : 'financialMoney', sortable : true},
		             {width : '130', title : '购买时间', field : 'buyDt', sortable : true,
		            	 formatter : formatTimers
		             },
		             {width : '130', title : '到期时间', field : 'minSellDt', sortable : false,
		            	 formatter : formatTimers
		             },
		             {width : '70', title : '预期收益', field : 'interest', sortable : false},
		             {width : '80', title : '订单状态', field : 'tStatus', sortable : true,
			             	formatter : function(value, row, index) {
			             		switch (value) {
				             		case 0:
						                return '待支付'+'('+value+')';
				             		case 1:
						                return '持有中'+'('+value+')';
				             		case 2:
						                return '回款中'+'('+value+')';
				             		case 3:
						                return '已回款'+'('+value+')';
				             		case 5:
						                return '支付失败'+'('+value+')';
				             		case 12:
						                return '支付中'+'('+value+')';
				             		case 18:
						                return '订单失效'+'('+value+')';
			             		}
			 	            }
			             }
		         ] ],
		 toolbar : '#toolb'
	});
	$("#DivSetting").dialog('open');
}


// 导出excel
function exportExcel(){
	showLoading();
	//search();
	// console.log($.serializeObject($('#searchForm')));
	window.setTimeout(function(){
		// 组织表单，提交数据
		$('#searchForm').attr('action','/rest/user/users/exportExcel').submit();
		closeLoading();
	},1000)
	
}

var showMgr = $("#showMgr").val();
// 切换是否显示理财经理
function swithMgr(){
	if(showMgr){
		showMgr = false;
	}else{
		showMgr = true;
	}
	if(showMgr){
		$("#showMgr").val(true);
		$(".showMgrTd").show();
	}else{
		$("#showMgr").val(false);
		$(".showMgrTd").hide();
	}
}

document.onkeydown = function (event) {
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if (e && e.keyCode == 13) {
    	search();
    }
};

$(function(){
	//search();
});
