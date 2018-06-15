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
	text : '提交',
	iconCls : 'icon-ok',
	handler : function() {
		ok();
	}
}, '-',{
	text : 'pc项目信息',
	iconCls : 'icon-ok',
	handler : function() {
		changeDetail(0);
	}	
}, '-',{
	text : 'app项目信息',
	iconCls : 'icon-ok',
	handler : function() {
		changeDetail(1);
	}	
}, '-',{
	text : '抵押物信息',
	iconCls : 'icon-ok',
	handler : function() {
		changeDetail(2);
	}	
}, '-',{
	text : '产品详情',
	iconCls : 'icon-ok',
	handler : function() {
		description();
	}	
}, '-',{
	text : '项目详情',
	iconCls : 'icon-ok',
	handler : function() {
		productDetailInfo();
	}	
}, '-', {
	text : '复制',
	iconCls : 'icon-reload',
	handler : function() {
		copyProduct();
	}
}, '-', {
	text : '排序',
	iconCls : 'icon-edit',
	handler : function() {
		sortProduct();
	}
}, '-', {
	text : '项目成立（京东）',
	iconCls : 'icon-ok',
	handler : function() {
		raise();
	}
}];

// 产品排序
var sortProductId = null;
function sortProduct() {
	var row = $('#adGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请选择一条记录');
		return false;
	}
	
	$('#sortProductDialog').dialog('open');
	sortProductId = row.id;
}

function confirmSortProduct() {
	if (sortProductId == null) {
		return false;
	}
	
	$.ajax({
		url : '/rest/product/product/sortProduct',
		type : 'post',
		data : {sort : $('#productSortVal').numberbox('getValue'), id : sortProductId},
		success : function (data) {
			$.messager.alert('提示', data.msg);
			if (data.code == 200) {
				$('#adGrid').datagrid('reload');
				$('#sortProductDialog').dialog('close');
			}
		}
	});
}

function closeSortProductDialog() {
	$('#sortProductDialog').dialog('close');
}

//复制
function copyProduct(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffAdd").form('clear');
	if(rows[0].stDt){
		rows[0].stDt = formatTimer(new Date(rows[0].stDt));
	}
	if(rows[0].edDt){
		rows[0].edDt = formatTimer(new Date(rows[0].edDt));
	}
	$("#ffAdd").form('load',rows[0]);
	$("#isCopy").val(3);//复制
	$("#DivAdd").dialog('open');
}

// 产品详情
function description(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	var description;
	$.ajax({
		url : '/rest/product/product/getBlobContent',
		type : 'post',
		data:{id:rows[0].id},
        async: false,
		success : function(data){
			if(data.code == 200){
				description = data.msg[3];
			} else{
				$.messager.alert('错误提示', data.msg, 'error');
			}
		}
	});
	if(description!=null&&description.startWith("{")){
		var obj = JSON.parse(description);
		$("#title1").textbox("setValue",obj.title1);
		$("#title2").textbox("setValue",obj.title2);
		$("#title3").textbox("setValue",obj.title3);
		$("#title4").textbox("setValue",obj.title4);
		$("#title5").textbox("setValue",obj.title5);
		$("#title6").textbox("setValue",obj.title6);
		$("#title7").textbox("setValue",obj.title7);
		$("#title8").textbox("setValue",obj.title8);
		$("#content1").textbox("setValue",obj.content1);
		$("#content2").textbox("setValue",obj.content2);
		$("#content3").textbox("setValue",obj.content3);
		$("#content4").textbox("setValue",obj.content4);
		$("#content5").textbox("setValue",obj.content5);
		$("#content6").textbox("setValue",obj.content6);
		$("#content7").textbox("setValue",obj.content7);
		$("#content8").textbox("setValue",obj.content8);
	}else{
		$("#title1,#title2,#title3,#title4,#title5,#title6,#title7,#title8,#content1,#content2,#content3,#content4,#content5,#content6,#content7,#content8").textbox("setValue","");
	}
	$("#ffDesc").form('load',rows[0]);
	$("#description").textbox("setValue",description);
	$("#DivDesc").dialog('open');
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
// 分块录入产品详情
function insertDepart(){
	$("#insertType").val(0);
	description();
	$("#insertDepart").show();
	$("#insertAll").hide();
}
// 统一录入产品详情
function insertAll(){
	$("#insertType").val(1);
	description();
	$("#insertAll").show();
	$("#insertDepart").hide();
}

// 修改产品详情
function saveDesc(){
	$.ajax({
		url : '/rest/product/product/saveDesc',
		type : 'post',
		data:$('#ffDesc').serialize(),
        async: false,
		success : function(data){
			if(data.code == 200){
				$("#DivDesc").dialog('close');
				$.messager.alert('提示', '修改成功', 'info');
				$("#adGrid").datagrid('reload');
			} else{
				$.messager.alert('错误提示', data.msg, 'error');
			}
		}
	});
}

function formatImage(value,row,index){
	if(value){
		return '<img src='+value+' style=width:50px; height:50px; margin-top:5px;>';
	}
}

function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}

// 大字段弹框
function changeDetail(type){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	var editor;
	if(type == 0){
		KindEditor.remove('#projectInfo');
		editor = KindEditor.create('#projectInfo',{
	        designMode : false,
	    });
		$('#ffCdd').form('load',rows[0]);
		$("#DivCdd").dialog('open');
	}else if(type == 1){
		KindEditor.remove('#projectInfoMobile');
		editor = KindEditor.create('#projectInfoMobile',{
	        designMode : false,
	    });
		$('#ffCdd1').form('load',rows[0]);
		$("#DivCdd1").dialog('open');
	}else if(type == 2){
		KindEditor.remove('#introduction');
		editor = createKindEditor("#introduction");
		$('#ffCdd2').form('load',rows[0]);
		$("#DivCdd2").dialog('open');
	}
	$.getJSON("/rest/product/product/getBlobContent?r=" + Math.random() + "&id=" + rows[0].id,
	    function(info) {
	       if(info.code==200){
	    	   editor.html(info.msg[type]);
	    	   $("#projectInfoMobile").val(editor.html());
	       }else{
	    	   $.messager.alert('错误提示', info.msg, 'error');
	       }
	});
}

// 保存pc项目信息、app项目信息和抵押物信息
function saveDetail(type){
	var rows = $("#adGrid").datagrid("getSelections");
	showLoading();
	var formId;
	var divId;
	if(type == 0){
		formId = '#ffCdd';
		divId = '#DivCdd';
		KindEditor.sync("#projectInfo");
	}else if(type == 1){
		formId = '#ffCdd1';
		divId = '#DivCdd1';
		KindEditor.sync("#projectInfoMobile");
	}else if(type == 2){
		formId = '#ffCdd2';
		divId = '#DivCdd2';
		KindEditor.sync("#introduction");
	}
	
	$(formId).form('submit',{
		url: "/rest/product/product/saveDetail?r=" + Math.random()+"&pId="+rows[0].id+"&type="+type,
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$(divId).dialog('close');
				$.messager.alert('提示', '提交成功', 'info');
				$("#adGrid").datagrid('reload');
			}else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

// 添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	init();
	$("#isCopy").val(1);//添加
	$("#DivAdd").dialog('open');
}
// 审批提交
function ok(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	if(rows[0].approval!=0){
		$.messager.alert("提示", "请选择尚未提交的产品", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认','您确认想要提交该产品吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/product/product/updateApproval?r=" + Math.random() + "&id=" + rows[0].id,
    		    function(info) {
    		       if(info.code==200){
    		    	   $.messager.alert('提示', '提交成功', 'info');
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

// 产品手点成立
function raise(){
	var rows = $("#adGrid").datagrid("getSelections");
	
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return;
	}
	
	if (rows[0].pType != 0) {
		$.messager.alert("提示", "只能成立京东产品", "error");
		return;
	}
	
	if (rows[0].code.substring(8,9)==0 && rows[0].rule!=3 && rows[0].status!=2 && rows[0].fixInvest==0) {
		$.messager.confirm('确认','您要成立此项目吗？',function(r){ 
	    if (r){    
	    	$.getJSON("/rest/product/product/raise?r=" + Math.random() + "&id=" + rows[0].id,
    		    function(info) {
    		       if(info.code==200){
    		    	   $.messager.alert('提示', '操作成功', 'info');
    		    	   $("#adGrid").datagrid('reload');
    		       }else{
    		    	   $.messager.alert('错误提示', info.msg, 'error');
    		       }
    		});    
	    }else{
	    	return
	    }   
	});
	
  }else{
	$.messager.alert("提示", "前置产品状态错误", "error");
    return
    }
}



// 删除
function del(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	if(rows[0].approval==2){
		$.messager.alert("提示", "该产品已经审批通过，不允许修改", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/product/product/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
function saveAndUpdate(){
	showLoading();
	$("#apcName").val($('#apcId').combogrid('getText'));
	$('#ffAdd').form('submit',{
		url: '/rest/product/product/saveAndUpdate',
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
function edit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	
	if(rows[0].approval==2){
		$.messager.alert("提示", "该产品已经审批通过，不允许修改", "error");
	    return
	}
	
	if(rows[0].status==1){
		$.messager.alert("提示", "该产品已在筹集期，不允许修改", "error");
	    return
	}
	
	$("#ffAdd").form('clear');
	if(rows[0].stDt){
		rows[0].stDt = formatTimer(new Date(rows[0].stDt));
	}
	if(rows[0].edDt){
		rows[0].edDt = formatTimer(new Date(rows[0].edDt));
	}
	$("#ffAdd").form('load',rows[0]);
	$("#isCopy").val(2);//修改
	$("#DivAdd").dialog('open');
}

// 查询
function search(){
	$('#adGrid').datagrid('load', $.serializeObject($('#searchForm')));
}

/**
 * disable或者enable对象
 * 
 * @param flag
 *            参数，true显示，false不显示
 */
function disapleAndEnableFloatProp(flag){
	if(flag){
		$("#afees").combobox('enable',true);
		$("#afeesRate").numberbox('enable',true);
		
		$("#afees").combobox('enableValidation');
		$("#afeesRate").numberbox('enableValidation');
		$("#afloatProp").show();
	}else{
		$("#afees").combobox('disable',true);
		$("#afeesRate").numberbox('disable',true);
		
		$("#afees").combobox('disableValidation');
		$("#afeesRate").numberbox('disableValidation');
		
		$("#afloatProp").hide();
	}
}
/**
 * 固定类
 * 
 * @param flag
 */
function disapleAndEnableFixProp(flag){
	if(flag){
		$("#aunitTime").combobox('enable',true);
		
		$("#aunitTime").combobox('enableValidation');
		$("#afixProp").show();
	}else{
		$("#aunitTime").combobox('disable',true);
		$("#aunitTime").combobox('disableValidation');
		$("#afixProp").hide();
		disapleAndEnableFixPropFix(false);
		disapleAndEnableFixPropArea(false);
	}
}
/**
 * 固定类中的固定汇率
 * 
 * @param flag
 */
function disapleAndEnableFixPropFix(flag){
	if(flag){
		$("#afTime").numberbox('enable',true);
		$("#afYield").numberbox('enable',true);
		$("#addYield").numberbox('enable',true);
		
		$("#afTime").numberbox('enableValidation');
		$("#afYield").numberbox('enableValidation');
		$("#addYield").numberbox('enableValidation');
		
		$("#afixPropFix").show();
	}else{
		$("#afTime").numberbox('disable',true);
		$("#afYield").numberbox('disable',true);
		$("#addYield").numberbox('disable',true);
		
		$("#afTime").numberbox('disableValidation');
		$("#afYield").numberbox('disableValidation');
		$("#addYield").numberbox('disableValidation');
		
		$("#afixPropFix").hide();
	}
}
/**
 * 固定类中的区间汇率
 * 
 * @param flag
 */
function disapleAndEnableFixPropArea(flag){
	if(flag){
		$("#alTime").numberbox('enable',true);
		$("#ahTime").numberbox('enable',true);
		$("#alYield").numberbox('enable',true);
		$("#ahYield").numberbox('enable',true);
		$("#aallocation").combobox('enable',true);
		
		$("#alTime").numberbox('enableValidation');
		$("#ahTime").numberbox('enableValidation');
		$("#alYield").numberbox('enableValidation');
		$("#ahYield").numberbox('enableValidation');
		$("#aallocation").combobox('enableValidation');
		$("#afixPropArea").show();
		
	}else{
		$("#alTime").numberbox('disable',true);
		$("#ahTime").numberbox('disable',true);
		$("#alYield").numberbox('disable',true);
		$("#ahYield").numberbox('disable',true);
		$("#aallocation").combobox('disable',true);
		
		$("#alTime").numberbox('disableValidation');
		$("#ahTime").numberbox('disableValidation');
		$("#alYield").numberbox('disableValidation');
		$("#ahYield").numberbox('disableValidation');
		$("#aallocation").combobox('disableValidation');
		
		$("#afixPropArea").hide();
	}
}

/**
 * 显示和隐藏固定产品类型或者浮动产品类型信息
 */
function showAndHideProductCategory(gridObjs){
	if(!gridObjs || gridObjs.length==0){
		 $.messager.alert("提示", "请选择产品类型", "error");
		    return
	}
	var obj = gridObjs[0];
	// 固定类型
	if(obj.type==0){
		disapleAndEnableFloatProp(false);
		disapleAndEnableFixProp(true);
		// 固定不变
		if(obj.fixType==1){
			disapleAndEnableFixPropArea(false);
			disapleAndEnableFixPropFix(true);
		}
		// 区间
		else if(obj.fixType==2){
			disapleAndEnableFixPropFix(false);
			disapleAndEnableFixPropArea(true);
		}
	}
	// 浮动类型
	else if(obj.type==1){
		disapleAndEnableFixProp(false);
		disapleAndEnableFloatProp(true);
	}
}


function disapleAndEnableRuleProp(flag){
	if(flag){
		$("#awaitRate").numberbox('enable',true);
		$('#awaitRate').numberbox('enableValidation');
		$("#aruleProp").show();
	}else{
		$("#awaitRate").numberbox('disable',true);
		$('#awaitRate').numberbox('disableValidation');
		disapleAndEnableRulePropMoney(false);
		disapleAndEnableRulePropDate(false);
		$("#aruleProp").hide();
	}
}

function disapleAndEnableRulePropMoney(flag){
	if(flag){
		$("#apriorMoney").numberbox('enable',true);
		
		$("#apriorMoney").numberbox('enableValidation');
		$("#aruleMoney").show();
	}else{
		$("#apriorMoney").numberbox('disable',true);
		
		$("#apriorMoney").numberbox('disableValidation');
		$("#aruleMoney").hide();
	}
}

function disapleAndEnableRulePropDate(flag){
	if(flag){
		$("#astDt").datetimebox('enable',true);
		$("#aedDt").datetimebox('enable',true);
		
		$("#astDt").datetimebox('enableValidation');
		$("#aedDt").datetimebox('enableValidation');
		$("#aruleDate").show();
	}else{
		$("#astDt").datetimebox('disable',true);
		$("#aedDt").datetimebox('disable',true);
		
		$("#astDt").datetimebox('disableValidation');
		$("#aedDt").datetimebox('disableValidation');
		$("#aruleDate").hide();
	}
}

/**
 * 显示和隐藏规则输入项目
 * 
 * @param newValue
 * @param oldValue
 */
function showAndHideRule(newValue,oldValue){
	if(newValue==3){
		disapleAndEnableRuleProp(false);
		return;
	}
	disapleAndEnableRuleProp(true);
	if(newValue==0){
		disapleAndEnableRulePropDate(false)
		disapleAndEnableRulePropMoney(true);
		return;
	}
	if(newValue==1){
		disapleAndEnableRulePropMoney(false);
		disapleAndEnableRulePropMoney(false);
		disapleAndEnableRulePropDate(true)
		return;
	}
	if(newValue==2){
		disapleAndEnableRulePropDate(true)
		disapleAndEnableRulePropMoney(true);
	}
}

function init(){
	disapleAndEnableRulePropMoney(false);
	disapleAndEnableRulePropDate(false);
	disapleAndEnableRuleProp(false);
	disapleAndEnableFixProp(false);
	disapleAndEnableFloatProp(false);
	disapleAndEnableFixPropArea(false);
}

function createKindEditor(contentId){
	var editor= KindEditor.create(contentId, {
		htmlTags : {  
	        font : ['color', 'size', 'face', '.background-color'],  
	        span : [  
	                '.color', '.background-color', '.font-size', '.font-family', '.background',  
	                '.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.line-height'  
	        ],  
	        div : [  
	                '.border-left','.width','.height','.line-height','.padding-bottom','.padding-left','align', '.border', '.margin', '.padding', '.text-align', '.color',  
	                '.background-color', '.font-size', '.font-family', '.font-weight', '.background',  
	                '.font-style', '.text-decoration', '.vertical-align', '.margin-left'  
	        ],  
	        table: [  
	                'border', 'cellspacing', 'cellpadding', 'width', 'height', 'align', 'bordercolor',  
	                '.padding', '.margin', '.border', 'bgcolor', '.text-align', '.color', '.background-color',  
	                '.font-size', '.font-family', '.font-weight', '.font-style', '.text-decoration', '.background',  
	                '.width', '.height', '.border-collapse'  
	        ],  
	        'td,th': [  
	                'align', 'valign', 'width', 'height', 'colspan', 'rowspan', 'bgcolor',  
	                '.text-align', '.color', '.background-color', '.font-size', '.font-family', '.font-weight',  
	                '.font-style', '.text-decoration', '.vertical-align', '.background', '.border'  
	        ],  
	        a : ['href', 'target', 'name'],  
	        embed : ['src', 'width', 'height', 'type', 'loop', 'autostart', 'quality', '.width', '.height', 'align', 'allowscriptaccess'],  
	        img : ['src', 'width', 'height', 'border', 'alt', 'title', 'align', '.width', '.height', '.border'],  
	        'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : [  
	                'align', '.text-align', '.color', '.background-color', '.font-size', '.font-family', '.background',  
	                '.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.text-indent', '.margin-left'  
	        ],  
	        pre : ['class'],  
	        hr : ['class', '.page-break-after'],  
	        'br,tbody,tr,strong,b,sub,sup,em,i,u,strike,s,del' : []  
		},
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
// 钱包模式
function wallet(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	// showLoading();
	if (rows[0].pModel==1) {
	$.messager.confirm('确认','您确认要成立该项目吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/product/product/wallet?r=" + Math.random() + "&id=" + rows[0].id,
    		    function(info) {
    		       if(info.code==200){
    		    	   $.messager.alert('提示', '操作成功', 'info');
    		    	   $("#adGrid").datagrid('reload');
    		       }else{
    		    	   $.messager.alert('错误提示', info.msg, 'error');
    		       }
    		});    
	    }else{
	    	return
	    }   
	});
	
  }else{
	$.messager.alert("提示", "前置产品状态错误", "error");
    return
    }
	// closeLoading();
}


function searchUsers(){
	$('#sFincUsers').combogrid('grid').datagrid('load',{
		page:1,
		name:$('#searchName').val()
	});
}
var cityInfo = "";

$(function(){
	$.ajax({
		async:false,
		url:"/rest/areaDomain/queryCityInfo?r=" + Math.random(),
		success:function(data) {
			cityInfo = data.obj;
		}
	});
});


// 城市代码--》城市名
function formatCityCode(value,row) {
	if(row.cityCode!=undefined) {
		return cityInfo[row.cityCode];
	}
}

// 项目详情
function productDetailInfo() {
	var row = $("#adGrid").datagrid("getSelected");
	if (row != null) {
		$.ajax({
			url : '/rest/product/product/getProductDetailInfo',
			type : 'post',
			data : {pId : row.id},
			success : function(data) {
				KindEditor.remove('#productDetailLonnerInfo');
				KindEditor.remove('#productDetailNormalQues');
				KindEditor.create('#productDetailLonnerInfo',{
			        designMode : false,
			    }).html(data.obj.lonnerInfo);
				KindEditor.create('#productDetailNormalQues',{
					designMode : false,
				}).html(data.obj.normalProblem);
            	$('#productDetailLonnerInfo').val(data.obj.lonnerInfo);
            	$('#productDetailNormalQues').val(data.obj.normalProblem);
			}
		});
		
		$('#productDetailInfoPid').val(row.id);
		$('#productDetailInfo').dialog('open');
	}
}

function saveProductDetailInfo() {
	var id = $('#productDetailInfoPid').val();
	
	$.messager.confirm('确认', '您确认要更新吗？', function(f) {
		if (f) {
			KindEditor.sync('#productDetailLonnerInfo');
			KindEditor.sync('#productDetailNormalQues');
			var productDetailLonnerInfo = $('#productDetailLonnerInfo').val();
			var productDetailNormalQues = $('#productDetailNormalQues').val();
			$.ajax({
				url : '/rest/product/product/updateProductDetailInfo',
				type : 'post',
				data : {id : id, productDetailLonnerInfo : productDetailLonnerInfo, productDetailNormalQues : productDetailNormalQues},
				success : function(data) {
					$('#productDetailInfo').dialog('close');
					$.messager.alert('提示', data.msg);
				}
			});
		}
	});
}

//加载项目信息模板
function lodeProductDetailInfoModel() {
	KindEditor.html('#projectInfoMobile', $('#productDetailInfoModel').html());
	$('#projectInfoMobile').val($('#productDetailInfoModel').html());
}

//加载借款人信息模板
function loadLonnerInfoModel() {
	KindEditor.html("#productDetailLonnerInfo", $('#productLonnerInfoModel').html());
	$('#productDetailLonnerInfo').val($('#productLonnerInfoModel').html());
}

//加载常见问题模板
function loadNormalQuesModel() {
	KindEditor.html("#productDetailNormalQues", $('#productNormalQuesModel').html());
	$('#productDetailNormalQues').val($('#productNormalQuesModel').html());
}