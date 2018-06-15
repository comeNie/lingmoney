var toolbar = [ {
	text : '保存',
	iconCls : 'icon-save',
	handler : function() {
		accept();
	}
}];
var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#adGrid').datagrid('validateRow', editIndex)){
		$('#adGrid').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#adGrid').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#adGrid').datagrid('selectRow', editIndex);
		}
	}
}
function accept(){
	$('#adGrid').datagrid('endEdit', editIndex);
	editIndex = undefined;
}
function saveChange(rowIndex, rowData, changes){
	var id;
	var isTransfer;
	
	var rows = $('#adGrid').datagrid('getChanges');
	if(rows.length>0){
		id = rows[0].id;
		isTransfer = rows[0].isTransfer;
	}
	
	var rows2 = $('#adGridRec').datagrid('getChanges');
	if(rows2.length>0){
		id = rows2[0].id;
		isTransfer = rows2[0].isTransfer;
	}
	
	if(id > 0){
		$.ajax({
	        type: "POST",
	        cache: false,
	        url: '/rest/user/transfer/updateUser',
	        data: {
	        	id : id,
	        	isTransfer : isTransfer
	        },
	        success:function(data){
	        	if(data.code != 200){
					$.messager.alert('错误提示', data.msg, 'error');
				}
			}                      
	    }); 
	}
}
var toolbars = [ {
	text : '保存',
	iconCls : 'icon-save',
	handler : function() {
		accepts();
	}
}];
var editIndexs = undefined;
function endEditings(){
	if (editIndexs == undefined){return true}
	if ($('#adGridRec').datagrid('validateRow', editIndexs)){
		$('#adGridRec').datagrid('endEdit', editIndexs);
		editIndexs = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRows(index){
	if (editIndexs != index){
		if (endEditings()){
			$('#adGridRec').datagrid('selectRow', index)
			.datagrid('beginEdit', index);
			editIndexs = index;
		} else {
			$('#adGridRec').datagrid('selectRow', editIndexs);
		}
	}
}
function accepts(){
	$('#adGridRec').datagrid('endEdit', editIndexs);
	editIndexs = undefined;
}
function formatType(value, row) {
	if (!value || value == 0) {
		return "<font color='red'>普通用户</font>";
	} else if (value == 1) {
		return "<font color='blue'>转移用户</font>";
	}
}
function searchUser(){
	var telephone=$('#telephone').val();
	var referralCode=$('#referralCode').val();
	$("#adGrid").datagrid({  
		type : 'POST',
		url: '/rest/user/transfer/searchUserByMap?telephone=' + telephone + '&referralCode='+referralCode
	});
	window.setTimeout(function(){
		var rows = $("#adGrid").datagrid("getRows");
		if(rows.length>0){
			$("#commissionRateNow").numberbox('setValue',rows[0].commissionRate);
			// ajax判断当前用户是否购买过产品
			$.ajax({
		        type: "POST",
		        cache: false,
		        async: false,
		        url: '/rest/user/transfer/searchUserState',
		        data: {
		        	uId : rows[0].uId
		        },
		        success:function(data){
		        	var _html="";
		        	if(rows[0].isTransfer==0){
		        		_html += "当前用户为普通用户,"
		        	}else if(rows[0].isTransfer==1){
		        		_html += "当前用户为转移用户,"
		        	}
		        	if(data.code == 200){
		        		_html += "已购买过产品。"
					}else{
						_html += "未购买过产品。"
					}
		        	$("#stateNow").html(_html);
				}                      
		    }); 
		}else{
			$("#commissionRateNow").numberbox('setValue','');
		}
		
	},500);
	$("#adGridRec").datagrid({  
		type : 'POST',
		url: '/rest/user/transfer/searchRecByMap?telephone=' + telephone + '&referralCode='+referralCode
	});
}
function searchTo(){
	var codeTo=$('#codeTo').val();
	/*
	 * if (!codeTo.startWith("0")) { $.messager.alert("提示", "请输入员工推荐码,0+员工号",
	 * "error"); return }
	 */
	$("#adGridTo").datagrid({  
		type : 'POST',
		url: '/rest/user/transfer/searchUserByReferralCode?referralCode=' + codeTo
	});
}

function transfer(){
	var rows1 = $("#adGrid").datagrid("getRows");
	if (rows1.length == 0) {
	    $.messager.alert("提示", "请选择要修改的用户", "error");
	    return
	}
	var rows2 = $("#adGridRec").datagrid("getRows");
	var rows3 = $("#adGridTo").datagrid("getRows");
	if (rows3.length == 0) {
	    $.messager.alert("提示", "请选择公司内部员工", "error");
	    return
	}
	if ($("#commissionRate").numberbox('getValue')=="") {
		$.messager.alert("提示", "请输入佣金费率", "error");
		return
	}
	var idStr = "";  
    for(var i=0;i<rows2.length;i++){  
    	idStr += rows2[i].uId;  
        if(i < rows2.length-1){   
        	idStr += ',';  
        }else{  
            break;  
        }  
    }
    showLoading();
    $.messager.confirm('确认', '您确认想要修改该项吗？', function(r) {
		if (r) {
			$.getJSON("/rest/user/transfer/transfer?r=" + Math.random() + "&idFrom=" + rows1[0].id+ "&idTo=" + rows3[0].id+ "&idStr=" + idStr+"&commissionRateStr="+$("#commissionRate").numberbox('getValue'),
				    function(info) {
				       if(info.code==200){
							$("#adGrid").datagrid('reload');
							$("#adGridRec").datagrid('reload');
							$("#adGridTo").datagrid('reload');
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
String.prototype.startWith=function(str){
	  if(str==null||str==""||this.length==0||str.length>this.length)
	   return false;
	  if(this.substr(0,str.length)==str)
	     return true;
	  else
	     return false;
	  return true;
	 }
