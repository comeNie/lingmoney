/**
 * 地址管理工具类
 * @description 在使用位置实例化addressManage对象，传入json类型配置
 * @param config 通用配置
 * @returns {___anonymous4097_4163}
 */
 function addressManage(config){
 	var privateConfig={
 	'debug':true,
    'type':null,
 	  'url':{
      'del':'/users/deleteAddress',
      'setDefault':'/users/setDefaultAddress',
      'showDetail':'/users/queryAddressDetail',
      'update':'/users/modifyAddress',
      'add':'/users/addAddress'
    },
    'success':{
      'del':function(){},
      'setDefault':function(){},
      'showDetail':function(){},
      'update':function(){},
      'add':function(){}
    },
    'editStart':function(){},
    'editEnd':function(){},
 	}
 	$.extend(privateConfig,config);
 	
 	// 初始化数据
 	privateConfig.init = function (id){
 		// 查询地址列表
 		ajax('/users/queryAddress',null,function(data){
 			if(data.code==200&&data.rows.length!=0){
		        
		        // 加载html代码 和鼠标滑过事件
		        // id在修改后变成新的了 所以要重新传一下 让展示内容和之前修改内容一致
		        createExchangeAddressElem(data.rows, id, privateConfig.type);
		        
		    } else if(data.code==3011||data.rows.length==0){
		        $("#isFirstAddress").val(1);
		        dealForNullData(privateConfig.type)
		    }	
	 		else{
	 			sAlert(data.msg);
	 		}
 		})
 	}
 	privateConfig.update=function(){
 		// 判断是增加还是修改
		 if ($('#saveType').val()=='add') {
			ajax(privateConfig.url.add,loadParam(['name','telephone','province','city','town','address','isFirst']),function(data){
				$("#isFirstAddress").val(0);
				privateConfig.success.add(data);
			})
	     }else{
	    	ajax(privateConfig.url.update,loadParam(['id','name','telephone','province','city','town','address']),function(data){
	    		$("#isFirstAddress").val(0);
	    		privateConfig.success.update(data);
	        })
	    }
 	}
 	bindEvent();
 	
 	
 	function bindEvent(){
 		$(document).on("click",'.del',function(){
 			var id = $(this).attr("data-info");
			 sConfirm("确定要删除?",function(){
				 privateConfig.debug&&console.log({'id':$(this).attr("data-info")});
				 
				 ajax(privateConfig.url.del,{'id':id},function(data){
		 		      privateConfig.success.del(data,$(this).attr("data-info"));
		 		  })
			 },function(){
				 return false;
			 },"删除");
			
	  });

 		$(document).on("click",'.setDefault',function(){
		  ajax(privateConfig.url.setDefault,{'id':$(this).attr("data-info")},function(data){
			  privateConfig.success.setDefault(data);
		  })
	  });

 		$(document).on("click",'.showDetail',function(){
		 privateConfig.editStart();
	      ajax(privateConfig.url.showDetail,{'id':$(this).attr("data-info")},function(data){
 		     privateConfig.success.showDetail(data);
 		    privateConfig.editEnd();
 		  })
 		  
	  });
 	 $(document).on("click",'#save',function(){
 		verifyAll();
	    // 判断是增加还是修改
	    if ($('#saveType').val()=='add') {
	      ajax(privateConfig.url.add,loadParam(['name','telephone','province','city','town','address','isFirst']),function(data){
	    	  $("#isFirstAddress").val(0);
	    	  privateConfig.success.add(data);
	      })
	    }else{
	      ajax(privateConfig.url.update,loadParam(['id','name','telephone','province','city','town','address']),function(data){
	    	  $("#isFirstAddress").val(0);
	    	  privateConfig.success.update(data);
	      })
	    }
	  });
 	}
  

	function loadParam(arr){
 		var data={};
 		if(arr.length>0){
 			var param={
 				id:$("#addressId").val(),
 				name: $("#name").val().replace(/^\s+|\s+$/g, ''),
 				telephone:$("#tel").val().replace(/^\s+|\s+$/g, ''),
 				province:$("#province").val(),
 				city:$("#city").val(),
 				town:$("#town").val(),
 				address:$("#addressDetail").val().replace(/^\s+|\s+$/g, ''),
 				isFirst:$("#isFirstAddress").val()
 			}
 	 		
 			for(var i in arr){
 				data[arr[i]] = param[arr[i]];
 			}
 		}
 		
 		return data;
 	}
 	
 	function ajax(url,data,successFun){
 		 $.ajax({
 			 url:url,
 			 async: false,
 			 type:'post',
 			 data:data,
 			 success:function(data) {
 				successFun(data);
 			 }
 		 });
 	}
 	
 	return{
 		init:privateConfig.init,
 		update:privateConfig.update
 	}
 }

 /**
  * 列表为空时的操作
  * @param type
  */
 function dealForNullData(type){
	$("#isFirstAddress").val(1);
	if(type=='address'){
		//无数据
		$("#receiptAddressDiv").empty();
		initAddressEventForAddr();
		$(".addAddress p").html("新增收货地址");
		$("#saveType").val('add');
	}else{
		$("#exchangeDialogAddress").empty();
		$(".editor-addr").show();
		$("#exchangeEditorAddr p:first span").html("新增收货地址");
		$("#saveType").val('add');
		$(".editor-addr_btn").hide();
	}
	clearAddressInfo();
}


 /**
  * 创建地址列表元素
  * @param addressList
  * @param id
  * @param type
  */
 function createExchangeAddressElem(addressList, id, type){
	if(addressList.length==0||addressList==null){
		dealForNullData(type);
		return;
	}
 	if((typeof id=="undefined"||id==null) && type=="address"){
    for(var i in addressList){
      addressList[i].classs=addressList[i].isDefault==1?'default':'';
      addressList[i].msg=addressList[i].isDefault==1?'默认地址':'设为默认';
    }

 		// 此时创建弹框元素
 		isFirstSetAddress = addressList.length;
 		
 		var htmlStr='<div class="item clearfix {classs}"><span data-info="{id}" class="span-left setDefault">{msg}<em></em></span><span class="span-center">{name}&nbsp;&nbsp;{province} {city} {town} {address}&nbsp;&nbsp;&nbsp;&nbsp;{telephone} </span><span class="span-right"><a data-info={id} href="javascript:void(0)" class="showDetail">编辑</a><a data-info="{id}" href="javascript:void(0)" class="del">删除</a></span></div>';
 		var res = dataTemplate(htmlStr,addressList);
 	  $("#receiptAddressDiv").empty().append(res);
 		
 	}else{
 		// 此时创建select元素
 		
 		$("#exchangeDialogAddress").empty();
 		var html = '';
 		var rowNum=0;
 		
 		if(id != ''){
 			// 如果id不为空，则查到它是第几条数据
 			for(var i = 0 ; i < addressList.length; i++){
 				if(addressList[i].id==id){
 					rowNum = i;
 				}
 			}
 		}
 		var c=type=='exchange'?'class="pop"':'';
 		html+='<ul class="checked"> <em '+c+' flag="true"></em>';
 		html+='<li><span data-info="'+addressList[rowNum].id+'" class="span-left">'+addressList[rowNum].name+'&nbsp;&nbsp;'+addressList[rowNum].province+' ';
 		html+= addressList[rowNum].city+' '+addressList[rowNum].town+' '+addressList[rowNum].address;
 		html+='&nbsp;&nbsp;&nbsp;&nbsp;'+addressList[rowNum].telephone+' </span>';
 		html+='<span class="span-right">';
 		html+='<a data-info="'+addressList[rowNum].id+'" href="javascript:void(0)" class="showDetail">编辑</a>';
 		html+='<a data-info="'+addressList[rowNum].id+'" href="javascript:void(0)" class="del">删除</a>';
 		html+='</span></li> </ul>';
 		
 		if(addressList.length > 1) {
 			html+='<ul class="exchangeDialog_address_drop">';
 			for(var i = 0 ; i < addressList.length; i++) {
 				html+='<li><span data-info="'+addressList[i].id+'" class="span-left">'+addressList[i].name+'&nbsp;&nbsp;'+addressList[i].province+' ';
 				html+=addressList[i].city+' '+addressList[i].town+' '+addressList[i].address;
 				html+='&nbsp;&nbsp;&nbsp;&nbsp;'+addressList[i].telephone+' </span></li>';
 			}
 			html+='</ul>';
 		}
 		$("#exchangeDialogAddress").append(html);
 		
 	}
 	initAddressHoverEvent(type);
 }

 /**
  * 事件初始化
  * @param type  exchange address
  */
 function initAddressHoverEvent(type){
 	if(type=="address"){
 		initAddressEventForAddr()
 	}else{
 		initAddressEventForEx(type)
 	}
 }

 function initAddressEventForEx(type){
 	$(".exchangeDialog_address li").hover(function(){
 		 $(this).find("span.span-right a").show();
 	 },function(){$(this).find("span.span-right a").hide()});
 	
 	// select效果
 	 $(".exchangeDialog_address ul.checked em").on("click",function(){
 		 if($(this).attr("flag")=="true"){
 			 $(".exchangeDialog_address_drop").stop().slideDown();
 			 $(this).attr("flag","false");
 			 $(".exchangeDialog_address_drop li").unbind();
 			 $(".exchangeDialog_address_drop li").on("click",function(){
 				 $(".exchangeDialog_address ul.checked .span-left").html($(this).find("span").html());
 				 $(".exchangeDialog_address ul.checked .span-left").attr("data-info",$(this).find("span").attr("data-info"));
 				 $(".exchangeDialog_address ul.checked a").attr("data-info",$(this).find("span").attr("data-info"));
 				 $(".exchangeDialog_address_drop").slideUp();
 				 $(".exchangeDialog_address ul.checked em").attr("flag","true");
 			 }) 
 		 }else if($(this).attr("flag")=="false"){
 			 $(".exchangeDialog_address_drop").stop().slideUp();
 			 $(this).attr("flag","true");
 		 }
 		 
 	 });
 	 if(type=='exchange'){
 		 $(document).on("click",function(e){
 			 e.target=e.target||e.srcElement;
 			 if($(e.target).prop("class")!="pop"){
 				 $(".exchangeDialog_address_drop").stop().slideUp();
 				 $(".pop").attr("flag","true");
 			 }
 		 });
 	 }
 }

 function initAddressEventForAddr(){
 	 /*地址-hover*/
 	 $(".has_addr .item").hover(function(){
 		 $(this).find("span.span-right a").show();
 	 },function(){
 		 $(this).find("span.span-right a").hide()
 	 });
 }

 /**
  * 清空已填表单信息
  */
 function clearAddressInfo(){
 	 $("#addressId").val("");
 	 $("#name").val("");
 	 $("#tel").val("");
 	 $("#addressDetail").val("");
 	 $("#addressDefault").val("");
 	 $("#nameTip").html("");
 	 $("#telTip").html("");
 	 $("#addressDetailTip").html("");
 	 addressInit('province', 'city', 'town', '北京', '市辖区', '东城区');
 }

 /**
  * 将数据载入html模板
  */
 var dataTemplate = function(template,data){  
     var outPrint="";  
     for(var i = 0 ; i < data.length ; i++){  
         var matchs = template.match(/\{[a-zA-Z]+\}/gi);  
         var temp="";  
         for(var j = 0 ; j < matchs.length ;j++){  
             if(temp == "")  
                 temp = template;  
             var re_match = matchs[j].replace(/[\{\}]/gi,"");  
             temp = temp.replace(matchs[j],data[i][re_match]);  
         }  
         outPrint += temp;  
     }  
     return outPrint;  
 } 
 
 
//验证详细地址
 function testAddress(address) {
 	var b= false;
 	if(address=='') {
 		 $("#addressDetailTip").html("请填写详细地址");
 		 b = false;
 	 } else {
 		 $("#addressDetailTip").html("");
 		 b = true;
 	 }
 	return b;
 }

 //验证姓名
 function testName(name) {
 	var b= false;
 	if(name==''){
 		 $("#nameTip").html("请填写姓名");
 		 b = false;
 	 }else {
 		 $("#nameTip").html("");
 		 b = true;
 	 }
 	return b;
 }

 //验证手机号
 function testTel(telephone){
 	 var b= false;
 	 var myreg = /^1[3|4|5|7|8]\d{9}$/;
 	 if (myreg.test(telephone)) {
 		 $("#telTip").html("");
 		 b = true;
 	 } else {
 		 $("#telTip").html("手机号格式有误");
 		 b = false;
 	 }
 	 return b;
 }
 
 function verifyAll(){
	 if(!testName($("#name").val().replace(/^\s+|\s+$/g, '')) || !testTel($("#tel").val().replace(/^\s+|\s+$/g, '')) || !testAddress($("#addressDetail").val().replace(/^\s+|\s+$/g, ''))) {
		 return false;
	 }
	 return true;
 }
 
