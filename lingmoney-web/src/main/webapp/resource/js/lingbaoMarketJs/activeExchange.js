// 加载地址配置
var addressManage = new addressManage({
	'type':'active',
	'success':{
		'del':function(data,id){
			 if(data.code==200) {
				 $("#addressId").val('');
				 addressManage.init('');
			 }else{
				sAlert(data.msg);
			 }
		},
		'setDefault':function(data){
			 if(data.code!=200) {
				 sAlert(data.msg);
			 }
		},
		'showDetail':function(data){
			if(data.code==200) {
				$("#addressId").val(data.obj.id);
				$("#name").val(data.obj.name);
				$("#tel").val(data.obj.telephone);
				$("#addressDetail").val(data.obj.address);
				addressInit('province', 'city', 'town', data.obj.province, data.obj.city, data.obj.town);
			}
		},
		'update':function(data){
			if(data.code==200) {
				$("#isFirstAddress").val(0);
				$("#saveType").val('update');
				$(".editor-addr_btn").show();
				$(".editor-addr").slideUp(400);
			    window.setTimeout(function(){offsetDiv("#exchangeDialog")},400);
			    addressManage.init(data.obj);
			}else{
				sAlert(data.msg);
			}
		},
		'add':function(data){
			if(data.code==200) {
				$("#isFirstAddress").val(0);
				$("#saveType").val('update');
				$(".editor-addr_btn").show();
				$(".editor-addr").slideUp(400);
			    window.setTimeout(function(){offsetDiv("#exchangeDialog")},400);
			    addressManage.init(data.obj);
			}else{
				sAlert(data.msg);
			}
		}
	},
    'editEnd':function(){
    	$(".editor-addr").slideDown(400);
		 window.setTimeout(function(){offsetDiv("#exchangeDialog")},400);
    }

});



//被选中的购物车ID
var checkedCId = '';
//是否全选标识 false全选  true 未全选
var isAllChecked = false;
//已选商品
var countNumber = 0;
//所需领宝
var countIntegral = 0;
//该礼品原始数量
var oldNum = 1;
$(function(){
	  $("#membersUserActiveli").find("dl").show();
	  $("#exchange").addClass("hover");
	  $("#membersUserActive").addClass("hover");
	  $("#nav_member03").addClass("nav-hover");
	  $(".lbmanager .ul-box li").on("click",function(){
		  $(this).addClass("clicked").siblings().removeClass("clicked");
		  var index=$(this).index();
		  $(".lbmanager .ul-box-con>div").eq(index).addClass("active").siblings().removeClass("active");
	  });
	  
	  $(".rz-close").click(function() {
		$("#rz-box-bg").hide();
		$("#exchangeDialog").hide();
		$("#exchangeSuccessDialog").hide();
		$("#exchangeFailDialog").hide();
		$("#confirReceiptDialog").hide;
		$("#confirmAcceptDiv").hide();
	 });
	  
	 $("#exchangeSuccessDialog .btns a").on("click",function(){
		 $("#rz-box-bg").hide();
		 $("#exchangeSuccessDialog").hide();
	 })
	 
	 
	 $(".exchangeDialog_address li").hover(
		function(){
		 $(this).find("span.span-right a").show();
	 	},
	 	function(){
		 $(this).find("span.span-right a").hide()
		}
	 );
	 
	  /*编辑*/
	 $('.exchangeDialog_address li .span-right').each(function(){
		 $(this).find('a:eq(0)').on("click",function(){
			 $(".editor-addr").slideDown(400);
			 window.setTimeout(function(){offsetDiv("#exchangeDialog")},400);
		  })
	 });
	 //首页进入，判断默认显示tab页
	 var type = $("#type").val();
	 if(type==1) {
		 $("#navUl li:eq(0)").click();
	 } else if(type==2) {
		 $("#navUl li:eq(1)").click();
	 }
	 
	 /*编辑完成*/
	/* $(".editor-addr_btn").on("click",function(){
		 updateAddress();
	 });*/
	 
	 initCheckBoxEvent();
	 
	 //城市列表选择框
	 $(".adress-box").click(function(){
	 	$(".dropdown").toggle();
	 });
	 $(".dropdown li a").click(function(){
		$(".dropdown").css("display","none");
	 });
  });

//购物车列表
function exchangeRecordList(page) {
	isAllChecked = false;
	if(undefined==page || null==page || ''==page) {
		page=1;
	}
	var rows = 10;
	$.ajax({
		url:'/lingbaoExchange/exchangeRecordList',
		type:'post',
		data:{"page":page, "rows":10},
		success:function(data) {
			if(data.code==200) {
				$("#buyCarTable").empty();
				var html = '';
				html+='<tbody><tr class="theader">';
				html+='<td></td><td align="center">商品</td><td align="center">商品名称</td>';
				html+='<td align="center">数量</td><td align="right">领宝数量</td>';
				html+='<td align="right" style="padding-right:9px;">操作</td></tr>';
				$.each(data.rows,function(i, item){
					html+='<tr>';
					html+='<td class="first">';
					if(checkedCId.match('(^'+item.cid+',|,'+item.cid+',)')) {
						if(item.oldcost==0){
							html+='<span data-info="'+item.cid+'" data-cost="0" class="J_CheckBoxItem checked" flag="false">';
						} else if(item.preCost!=0) {
							html+='<span data-info="'+item.cid+'" data-cost="'+item.preCost+'" class="J_CheckBoxItem checked" flag="false">';
						} else {
							html+='<span data-info="'+item.cid+'" data-cost="'+item.cost+'" class="J_CheckBoxItem checked" flag="false">';
						}
					} else {
						if(item.oldcost==0){
							html+='<span data-info="'+item.cid+'" data-cost="0" class="J_CheckBoxItem" flag="true">';
						} else if(item.preCost!=0) {
							html+='<span data-info="'+item.cid+'" data-cost="'+item.preCost+'" class="J_CheckBoxItem" flag="true">';
						} else {
							html+='<span data-info="'+item.cid+'" data-cost="'+item.cost+'" class="J_CheckBoxItem" flag="true">';
						}
						isAllChecked = true;
					}
					html+='<input type="checkbox" name="select-all" />';
					html+='<input type="hidden" value="'+item.cid+'" name="cartId">';
					html+='<input type="hidden" value="'+item.id+'" name="giftId">';
					html+='</span></td>';
					var titleName = item.name;
					if(item.name!=null && item.name.length>12) {
						item.name = item.name.substring(0,9)+"...";
					}
					if(item.applyType==1) {//抽奖类型
						html+='<td class="second"><a title="'+titleName+'" href="javascript:void(0);"><img src="'+item.pic+'"/></a></td>';
						html+='<td class="third"><a title="'+item.name+'" href="javascript:void(0);">'+item.name+'</a></td>';
					} else {
						html+='<td class="second"><a title="'+titleName+'" href="/lingbaoExchange/exchange?id='+item.giftId+'"><img src="'+item.pic+'"/></a></td>';
						html+='<td class="third"><a title="'+item.name+'" href="/lingbaoExchange/exchange?id='+item.giftId+'">'+item.name+'</a></td>';
					}
					html+='<td class="forth"><span class="tb-count"  data-info="'+item.store+'" data-limit="'+item.exchangeNumber+'">';
					if(item.oldcost==0) {//中奖所得产品
						html+='<a data-info="'+item.cid+'" href="javascript:void(0)" title="减1" class="limit">-</a>';
					} else if(item.num>1) {
						html+='<a data-info="'+item.cid+'" onclick="subtractNum($(this))" href="javascript:void(0)" title="减1">-</a>';
					}else {
						html+='<a data-info="'+item.cid+'" onclick="subtractNum($(this))" href="javascript:void(0)" title="减1" class="limit">-</a>';
					}
					if(item.oldcost==0) {
						html+= '<input name="number" type="text" value="'+item.num+'" readonly="readonly"/>';
					} else {
						html+='<input name="number" type="text" value="'+item.num+'" onfocus="setOldNum()" onblur="numBlur($(this),'+item.cid+')" onkeyup="value=value.replace(/[^\\d]/g,\'\') " onbeforepaste="clipboardData.setData(\'text\',clipboardData.getData(\'text\').replace(/[^\\d.]/g,\'\'))"   maxlength="8" title="请输入购买量"/>';
					}
					if(item.oldcost==0) {
						html+='<a data-info="'+item.cid+'" href="javascript:void(0)" title="加1" class="limit">+</a>';
					}else if(item.num<item.exchangeNumber && item.num<item.store){
						html+='<a data-info="'+item.cid+'" onclick="addNum($(this))" href="javascript:void(0)" title="加1">+</a>';
					} else {
						html+='<a data-info="'+item.cid+'" onclick="addNum($(this))" href="javascript:void(0)" title="加1" class="limit">+</a>';
					}
					html+='</span></td>';
					if(item.oldcost==0) {
						html+='<td class="fifth">0</td>';
					} else if(item.preCost!=0){
						html+='<td class="fifth">'+(Number(item.preCost)*Number(item.num))+'</td>';
					} else {
						html+='<td class="fifth">'+(Number(item.cost)*Number(item.num))+'</td>';
					}
					if(item.oldcost==0) {
						html+='<td class="sixth"><a href="javascript:void(0)" onclick="deleteFromCart($(this),-1)">删除</a></td>';
					} else {
						html+='<td class="sixth"><a href="javascript:void(0)" onclick="deleteFromCart($(this),'+item.cid+')">删除</a></td>';
					}
					html+='</tr>';
				});
				html+='</tbody>';
				$("#buyCarTable").append(html);
				//全选框元素
				$("#allChexkBox").empty();
				var allChexkBoxHtml = '';
				//设置全选框 
				if(isAllChecked) {
					allChexkBoxHtml += '<span class="J_CheckBoxItem" style="margin-right:29px;" flag="true">';
					allChexkBoxHtml += '<input type="checkbox" name="select-all"/></span>';
					allChexkBoxHtml += '<a href="javascript:void(0)" id="btn_selectAll" flag="true">全选</a>';
				} else {
					allChexkBoxHtml += '<span class="J_CheckBoxItem checked" style="margin-right:29px;" flag="false">';
					allChexkBoxHtml += '<input type="checkbox" name="select-all"/></span>';
					allChexkBoxHtml += '<a href="javascript:void(0)" id="btn_selectAll" flag="false">全选</a>';
				}
				allChexkBoxHtml += '<a href="javascript:void(0)" id="btn_removetAll" onclick="deleteFromCart($(this),\'all\')">删除</a>';
				allChexkBoxHtml += '<span style="margin-right:32px;">已选择商品 <strong id="countNumber" class="countAll" style="padding:0 10px;">'+countNumber+'</strong>件</span>';
				allChexkBoxHtml += '<span>合计： <strong id="countIntegral" class="countAll">'+countIntegral+'</strong>（领宝）</span>';
				allChexkBoxHtml += '<a href="javascript:void(0)" onclick="validExchange($(this))" class="btn_exchange">立即兑换</a>';
				$("#allChexkBox").append(allChexkBoxHtml);
				
				//分页元素
				var currentpage = Number(page);
				var total = Number(data.total);
				var pages = Math.ceil(total/rows);
				
				var pagehtml = '<div id="buyCarPages" class="pagination">';
				pagehtml += '&nbsp;共<strong>'+total+'</strong>项,<strong>'+pages+'</strong>页:&nbsp;';
				if(currentpage==1) {
					pagehtml += '<span class="disabled">«&nbsp;上一页</span>';
				}else {
					pagehtml += '<a href="javascript:exchangeRecordList('+(currentpage-1)+')">«&nbsp;上一页</a>';
				}
				//判断显示的页数 S
				if(pages>8) {
					if(currentpage-4>1) {
						pagehtml += '<span>...</span>';
					}
					for(var i = currentpage-4; i<=currentpage+4; i++) {
						if(i<=0) {
							i=1;
						}
						if(i>pages){
							break;
						} 
						if(i==currentpage) {
							pagehtml += '<span class="current">'+currentpage+'</span>';
						}else {
							pagehtml += '<a href="javascript:exchangeRecordList('+i+')">'+i+'</a>';
						}
					}
					if(currentpage+4<pages) {
						pagehtml += '<span>...</span>';
					}
				} else {
					for(var i = 1; i <= pages; i++) {
						if(i==currentpage) {
							pagehtml += '<span class="current">'+currentpage+'</span>';
						}else {
							pagehtml += '<a href="javascript:exchangeRecordList('+i+')">'+i+'</a>';
						}
					}
				}
				//判断显示的页数 E
				if(currentpage==pages) {
					pagehtml += '<span class="disabled">下一页&nbsp;»</span>';
				} else {
					pagehtml += '<a href="javascript:exchangeRecordList('+(currentpage+1)+')">下一页&nbsp;»</a>';
				}
				pagehtml+='</div>';
				$("#buyCarPages").empty().append(pagehtml);
				
				initCheckBoxEvent();
			}else {
				$("#shopCartDiv").empty();
				$("#shopCartDiv").append("<h3>暂无数据</h3>");
			}
		}
	});
}

//查询进行中数据
function queryInfoByUidWait(page){
	var rows = 10;
	if(undefined==page || null==page || ''==page) {
		page=1;
	}
	$.ajax({
		url:'/lingbaoExchange/queryInfoByUidWait',
		type:'post',
		data:{"page":page,"rows":rows},
		success:function(data) {
			if(data.code==200) {
				$("#dataWaitDiv").empty();
				var html = '';
				$.each(data.rows,function(i, item){
					html += '<table cellpadding="0" cellspacing="0" border="0" class="table-default"><tbody>';
					html += '<tr class="theader">';
                    html += '<td rowspan="2" style="padding:0px; height:100%; width:120px; line-height:9px; border-bottom:1px solid #f2f2f2;">';
                    html += '<input value="'+item.serialNumber+'" type="hidden">';
                    html += '<img title="'+item.name+'" style="width: 70%;"  src="'+item.pic+'"></td>';
					html += '<td align="center" style="padding-left:20px;">商品名称</td>';
					html += '<td align="center">兑换时间</td><td align="right">数量</td><td align="right">领宝数量</td>';
					html += '<td align="right" style="padding-right:30px;">状态</td></tr>';
					html += '<tr><td class="first">';
					var titleName = item.name;
					if(item.name!=null && item.name.length>12) {
						item.name = item.name.substring(0,9)+"...";
					}
					if(item.applyType==1) {
						html += '<a title="'+titleName+'" href="javascript:void(0);">'+item.name+'</a>';
					} else {
						html += '<a title="'+titleName+'" href="/lingbaoExchange/exchange.html?id='+item.giftId+'">'+item.name+'</a>';
					}
					html += '<input type="hidden" name="giftId" value="$'+item.giftId+'">';
					html += '<input type="hidden" name="exchangeId" value="$'+item.id+'"></td>';
					html += '<td class="second">'+item.exchangeTime+'</td>';
					html += '<td class="third">'+item.num+'</td>';
					html += '<td class="forth">'+item.integral+'</td>';
					if(item.status==1) {
						html += '<td class="fifth able"><a onclick="confirmReceipt(this,'+item.id+')" href="javascript:void(0)">确认收货</a></td></tr>';
						if(null!=item.expressNumber && item.expressNumber!='') {
							html += '<tr style="background:#fef6f3;">';
							html += '<td colspan="2" style="padding-left:50px; text-align:center; color:#ffffff;font-size:14px; background: url(\'/resource/images/lingbaoMarketImages/carBg.png\') 95px 11px no-repeat">'+item.expressCompany+'</td>';
							html += '<td colspan="4" style=" font-size: 14px; color: #4a4a4a; text-align:center;">快递单号：<em style="color:rgb(244,165,131);">'+item.expressNumber+'</em></td></tr>';
						}
						html += ' </tbody> </table>';
					} else {
						html += '<td class="fifth able"><a href="javascript:void(0)">处理中</a></td>';
						html += '</tr> </tbody> </table>';
					}
				});
				$("#dataWaitDiv").append(html);
				
				var currentpage = Number(page);
				var total = Number(data.total);
				var pages = Math.ceil(total/rows);
				console.info(pages)
				
				var pagehtml = '<div class="page"><div id="dataWaitPages" class="pagination">';
				pagehtml += '&nbsp;共<strong>'+total+'</strong>项,<strong>'+pages+'</strong>页:&nbsp;';
				if(currentpage==1) {
					pagehtml += '<span class="disabled">«&nbsp;上一页</span>';
				}else {
					pagehtml += '<a href="javascript:queryInfoByUidWait('+(Number(currentpage)-1)+')">«&nbsp;上一页</a>';
				}
				//判断显示的页数 S
				if(pages>8) {
					if(currentpage-4>1) {
						pagehtml += '<span>...</span>';
					}
					for(var i = currentpage-4; i<=currentpage+4; i++) {
						if(i<=0) {
							i=1;
						}
						if(i>pages){
							break;
						} 
						if(i==currentpage) {
							pagehtml += '<span class="current">'+currentpage+'</span>';
						}else {
							pagehtml += '<a href="javascript:queryInfoByUidWait('+i+')">'+i+'</a>';
						}
					}
					if(currentpage+4<pages) {
						pagehtml += '<span>...</span>';
					}
				} else {
					for(var i = 1; i <= pages; i++) {
						if(i==currentpage) {
							pagehtml += '<span class="current">'+currentpage+'</span>';
						}else {
							pagehtml += '<a href="javascript:queryInfoByUidWait('+i+')">'+i+'</a>';
						}
					}
				}
				//判断显示的页数 E
				if(currentpage==pages) {
					pagehtml += '<span class="disabled">下一页&nbsp;»</span>';
				} else {
					pagehtml += '<a href="javascript:queryInfoByUidWait('+(Number(currentpage)+1)+')">下一页&nbsp;»</a>';
				}
				pagehtml+='</div></div>';
				$("#dataWaitDiv").append(pagehtml);
				initCheckBoxEvent();
			}else {
				$("#dataWaitDiv").empty().append("<h3>暂无数据</h3>");
			}
		}
	});
}

//查询已兑换
function queryInfoByUidFinish(page){
	if(undefined==page || null==page || ''==page) {
		page=1;
	}
	var rows = 10;
	$.ajax({
		url:'/lingbaoExchange/queryInfoByUidFinish',
		type:'post',
		data:{"page":page,"rows":rows},
		success:function(data) {
			if(data.code==200) {
				$("#dataFinishDiv").empty();
				var html = '';
				$.each(data.rows,function(i, item){
					html += '<table cellpadding="0" cellspacing="0" border="0" class="table-default">';
					html += '<tbody>';
					html += '<tr class="theader">';
					html += '<td rowspan="2" style="padding:0px; height:100%; width:120px; line-height:9px; border-bottom:1px solid #f2f2f2;">';
					html += '<img title="'+item.name+'" style="width: 70%; height: 135;"  src="'+item.pic+'"> </td>';
 					html += '<td align="center" style="padding-left:20px;">商品名称</td>';
					html += '<td align="center">兑换时间</td>';
					html += '<td align="right">数量</td>';
					html += '<td align="right">领宝数量</td>';
					html += '<td align="right" style="padding-right:30px;">状态</td></tr>';
					html += '<tr><td class="first">';
					var titleName = item.name;
					if(item.name!=null && item.name.length>12) {
						item.name = item.name.substring(0,9)+"...";
					}
					if(item.applyType==1) {
						html += '<a title="'+titleName+'" href="javascript:void(0);">'+item.name+'</a>';
					} else {
						html += '<a title="'+titleName+'" href="/lingbaoExchange/exchange?id='+item.giftId+'">'+item.name+'</a>';
					}
					html += '</td><td class="second">'+item.exchangeTime+'</td><td class="third">'+item.num+'</td>';
					html += '<td class="forth hui">'+item.integral+'</td><td class="fifth unable">已收货</td></tr>';
     				if(null!=item.expressNumber && item.expressNumber.replace(/^\s+|\s+$/g, '')!='') {
     					html += '<tr style="background:#fef6f3;">';
     					html += '<td colspan="2" style="padding-left:50px; text-align:center; color:#ffffff;font-size:14px; background: url(\'/resource/images/lingbaoMarketImages/carBg.png\') 95px 11px no-repeat">'+item.expressCompany+'</td>';
     					html += '<td colspan="4" style=" font-size: 14px; color: #4a4a4a; text-align:center;">快递单号：<em style="color:rgb(244,165,131);">'+item.expressNumber+'</em></td></tr>';
     				}
					html+='</tbody></table>';
				});
				$("#dataFinishDiv").append(html);
				
				var currentpage = Number(page);
				var total = Number(data.total);
				var pages = Math.ceil(total/rows);
				
				var pagehtml = '<div class="page"><div id="dataFinishPages" class="pagination">';
				pagehtml += '&nbsp;共<strong>'+total+'</strong>项,<strong>'+pages+'</strong>页:&nbsp;';
				if(currentpage==1) {
					pagehtml += '<span class="disabled">«&nbsp;上一页</span>';
				}else {
					pagehtml += '<a href="javascript:queryInfoByUidFinish('+(Number(currentpage)-1)+')">«&nbsp;上一页</a>';
				}
				//判断显示的页数 S
				if(pages>8) {
					if(currentpage-4>1) {
						pagehtml += '<span>...</span>';
					}
					for(var i = currentpage-4; i<=currentpage+4; i++) {
						if(i<=0) {
							i=1;
						}
						if(i>pages){
							break;
						} 
						if(i==currentpage) {
							pagehtml += '<span class="current">'+currentpage+'</span>';
						}else {
							pagehtml += '<a href="javascript:queryInfoByUidFinish('+i+')">'+i+'</a>';
						}
					}
					if(currentpage+4<pages) {
						pagehtml += '<span>...</span>';
					}
				} else {
					for(var i = 1; i <= pages; i++) {
						if(i==currentpage) {
							pagehtml += '<span class="current">'+currentpage+'</span>';
						}else {
							pagehtml += '<a href="javascript:queryInfoByUidFinish('+i+')">'+i+'</a>';
						}
					}
				}
				//判断显示的页数 E
				if(currentpage==pages) {
					pagehtml += '<span class="disabled">下一页&nbsp;»</span>';
				} else {
					pagehtml += '<a href="javascript:queryInfoByUidFinish('+(Number(currentpage)+1)+')">下一页&nbsp;»</a>';
				}
				pagehtml+='</div></div>';
				$("#dataFinishDiv").append(pagehtml);
				initCheckBoxEvent();
			}else {
				$("#dataFinishDiv").empty();
				$("#dataFinishDiv").append("<h3>暂无数据</h3>");
			}
		}
	});
}

//确认收货
function confirmReceipt(elem,id){
	$("#rz-box-bg").show();
	$("#confirmAcceptDiv").show();
	offsetDiv("#confirmAcceptDiv");
	var table = $(elem).parents("table");
	var imgUrl = table.find("img:first").attr("src");
	var name = table.find(".first a").attr("title");
	var serialNumber = table.find("input:first").val();
	$("#confirmAcceptDiv .qrshleft img").attr("src",imgUrl);
	$("#confirmAcceptDiv .qrshright p:first").html(name);
	$("#confirmAcceptDiv .qrshright p:last span").html(serialNumber);
	$("#confirmAcceptDiv a:last").unbind("click").on("click",function(){
		$.ajax({
			url:'/lingbaoExchange/confirmReceipt.html',
			type:'get',
			data:{"id":id},
			success:function(data){
				if(data.code==200) {
					$(".rz-close").click();
					queryInfoByUidWait(1);
					//每次确认收货后刷新已收货数据
					queryInfoByUidFinish(1);
				}
			}
		});
	});
}

//删除
function deleteFromCart(elem,type){
	var id = type;
	if(type=='') {
		sAlert("请先选择一条数据");
		return false;
	} else if(type=='all') {
		if(checkedCId=='') {
			sAlert("请先选择一条数据");
			return false;
		}
		//获取所有checkbox数据
		id = checkedCId.substring(0, checkedCId.length-1);
	}
	if(type == -1){
		sAlert("该商品为中奖礼品，无法删除");
		return false;
	}
	var giftName = elem.parent().parent().find(".third a").html();
	var msg = "确认删除?_"+giftName;
	if(type == 'all' && id.split(",").length > 1) {
		msg = "确认全部删除?"
	}
	sConfirm(msg,function(){
		$.ajax({
			url:'/lingbaoExchange/deleteFromCart',
			type:'get',
			data:{"id":id},
			success:function(data){
				sAlert(data.msg);
				if(data.code==200) {
					if(type=='all') {
						countNumber = 0;
						countIntegral = 0;
						checkedCId = '';
					} else {
						if(checkedCId.match('(^'+id+',|,'+id+',)')) {
							var cost = Number(elem.parent().prev().html());
							countNumber -= 1;
							countIntegral -= cost;
							if(checkedCId.indexOf(id+",",0)==0) {
								checkedCId = checkedCId.replace(id+",","");
							} else {
								checkedCId = checkedCId.replace(","+id+",",",");
							}
						}
					}
					exchangeRecordList(1);
				}
			}
		});
	},function(){
		return false;
	},"删除");
		
}

//初始化 勾选框点击事件
function initCheckBoxEvent(){ 
	$("table .J_CheckBoxItem").unbind("click");
	$("#btn_selectAll").unbind("click");
	$(".box-heji .J_CheckBoxItem").unbind("click");
	$("table .J_CheckBoxItem").on("click",function(){
		  var cid = $(this).attr("data-info");
		  //所选礼品单个所需领宝数量
		  var cost = $(this).attr("data-cost");
		  //所选礼品个数
		  var num = $(this).parent().parent().find("input[name='number']").val();
		  if($(this).attr("flag")=="true"){
			  $(this).addClass("checked");
			  $(this).attr("flag","false");
			  checkedCId = checkedCId+cid+",";
			  countIntegral += Number(num)*Number(cost);
			  countNumber += 1;
		  }else{
			  $(this).removeClass("checked");
			  $(this).attr("flag","true");
			  if(checkedCId.indexOf(cid+",",0)==0) {
				  checkedCId = checkedCId.replace(cid+",","");
			  } else {
				  checkedCId = checkedCId.replace(","+cid+",",",");
			  }
			  countIntegral -= Number(num)*Number(cost); 
			  countNumber -= 1;
		  }
		  $("#countIntegral").html(countIntegral);
		  $("#countNumber").html(countNumber);
		  checkIsAllChecked();
	  });
	  
	  
	  $(".box-heji .J_CheckBoxItem").on("click",function(){
		  $("#btn_selectAll").click();
	  });
	 
	  $("#btn_selectAll").on("click",function(){
		  var checkElem = $("table .J_CheckBoxItem input[name='cartId']");
		  for(var i = 0 ; i < checkElem.length; i++) {
			  var cid = $(checkElem[i]).val();
			  //减去全选中包含的单选项目数量
			  if(checkedCId.match('(^'+cid+',|,'+cid+',)')) {
				  countNumber -= 1;
				  //所选礼品单个所需领宝数量
				  var cost = $(checkElem[i]).parent().attr("data-cost");
				  //所选礼品个数
				  var num = $(checkElem[i]).parent().parent().parent().find("input[name='number']").val();
				  //所需扣除的领宝数量
				  var integral = Number(cost)*Number(num);
				  countIntegral -= integral;
			  }
			  //删除全选中包含的ID
			  if(checkedCId.indexOf(cid+",",0)==0) {
				  checkedCId = checkedCId.replace(cid+",","");
			  } else {
				  checkedCId = checkedCId.replace(","+cid+",",",");
			  }
		  }
		  if($(this).attr("flag")=="true"){
			  $("table .J_CheckBoxItem,.box-heji .J_CheckBoxItem").addClass("checked");
			  $("table .J_CheckBoxItem,.box-heji .J_CheckBoxItem").attr("flag","false");
			  $(this).attr("flag","false");
			  for(var i = 0 ; i < checkElem.length; i++) {
				  checkedCId += $(checkElem[i]).val()+",";
				  //所选礼品单个所需领宝数量
				  var cost = $(checkElem[i]).parent().attr("data-cost");
				  //所选礼品个数
				  var num = $(checkElem[i]).parent().parent().parent().find("input[name='number']").val();
				  //所需增加的领宝数量
				  var integral = Number(cost)*Number(num);
				  countIntegral += integral;
			  }
			  countNumber += checkElem.length;
		  }else{
			  $("table .J_CheckBoxItem,.box-heji .J_CheckBoxItem").removeClass("checked");
			  $("table .J_CheckBoxItem,.box-heji .J_CheckBoxItem").attr("flag","true");
			  $(this).attr("flag","true");
		  }
		  $("#countNumber").html(countNumber);
		  $("#countIntegral").html(countIntegral);
	  });
}

//验证是否全选
function checkIsAllChecked(){
	var check = $("table .J_CheckBoxItem[flag='true']");
	if(check.length==0) {
		isAllChecked = false;
		$("#btn_selectAll").attr("flag","false");
		$("#btn_selectAll").prev().addClass("checked");
		$("#btn_selectAll").prev().attr("flag","false");
	} else {
		isAllChecked = true;
		$("#btn_selectAll").attr("flag","true");
		$("#btn_selectAll").prev().removeClass("checked");
		$("#btn_selectAll").prev().attr("flag","true");
	}
}

//-
function subtractNum(elem) {
	var id = elem.attr("data-info");
	var num = Number(elem.next().val());
	var single = elem.parent().parent().parent().find("span:first").attr("data-cost");
	if(num==1) {
		return false
	}else if(num==2){
		num = num-1;
		elem.addClass("limit");
	}else if(num<=1){
		num=1;
		elem.addClass("limit");
	} else {
		num = num-1;
	}
	//请求后台处理
	var data = changeNum(id,num);
	if(data.code==253) {//253表示原购物车数量大于当前礼品剩余数量
		elem.parent().attr("data-info",data.lastStore);
		num = 1;
		sAlert(data.msg);
	}else if(data.code!=200) {
		sAlert(data.msg);
		return false;
	}
	//成功后移除‘+’禁用，修改数量
	elem.next().val(num);
	elem.next().next().removeClass("limit");
	single = Number(single);
	elem.parent().parent().next().html(num*single);
	if(num == 1) {
		elem.addClass("limit");
	}
	//b==false表示被选中。计算中领宝
	var b = elem.parent().parent().parent().find("span:first").attr("flag");
	if(b=="false") {
		if(data.code == 253) {
			countIntegral += (num - Number(data.oldNum)) * single;
			if(data.obj == 1) {
				elem.next().next().addClass("limit");
			}
		} else {
			countIntegral-=single;
		}
		$("#countIntegral").html(countIntegral);
	}
}
//+
function addNum(elem) {
	var id = elem.attr("data-info");
	var num = Number(elem.prev().val());
	//最高叠堆数量
	var store = Number(elem.parent().attr("data-info"));
	//单次限制数量
	var exchangeNumber = Number(elem.parent().attr("data-limit"));
	if(store==1 || exchangeNumber==1) {
		return false;
	}
	var single = elem.parent().parent().parent().find("span:first").attr("data-cost");
	if(num==store || num==exchangeNumber) {
		return false;
	} else if(num==(store-1) || num==(exchangeNumber-1)) {
		num = num+1;
		elem.addClass("limit");
	} else if(num>=store){
		num=store;
		elem.addClass("limit");
	}else if(num>=exchangeNumber){
		num = exchangeNumber;
		elem.addClass("limit");
		sAlert("该商品每次仅限兑换"+exchangeNumber+"个");
	} else {
		num = num+1;
	}
	//请求后台处理
	var data = changeNum(id,num);
	if(data.code==262) {//库存不足，更新页面库存数量值
		elem.parent().attr("data-info",data.lastStore);
	}
	if(data.code==253) {//253表示原购物车数量大于当前礼品剩余数量
		elem.parent().attr("data-info",data.lastStore);
		num = 1;
		if(store > 1) {
			elem.removeClass("limit");
		}
		elem.prev().prev().addClass("limit");
		sAlert(data.msg);
	}else if(data.code!=200) {
		sAlert(data.msg);
		return false;
	}
	//成功后修改数量，移除‘-’禁用
	elem.prev().val(num);
	if(num != 1) {
		elem.prev().prev().removeClass("limit");
	}
	single = Number(single);
	elem.parent().parent().next().html(num*single);
	//b==false表示被选中。计算中领宝
	var b = elem.parent().parent().parent().find("span:first").attr("flag");
	if(b=="false") {
		if(data.code==253) {
			countIntegral += (num - Number(data.oldNum)) * single;
			elem.prev().prev().addClass("limit");
		} else {
			countIntegral += single;
		}
		$("#countIntegral").html(countIntegral);
	}
}
//输入数量
function numBlur(elem,id) {
	//焦点移到下个SPAN避免操作错误
	elem.next().focus();
	var num = Number(elem.val());
	if(isNaN(num)) {
		num = 1;
	} else {
		num = Number(num);
	}
	//最高叠堆数量
	var store = Number(elem.parent().attr("data-info"));
	//单次兑换限制
	var exchangeNumber = Number(elem.parent().attr("data-limit"));
	if(exchangeNumber==1) {
		sAlert("每次仅限兑换一个该商品!");
		elem.val(1);
		return false;
	}
	if(store<2) {
		sAlert("库存不足,仅剩"+store+"件商品");
		elem.val(1);
		return false;
	}
	var single = elem.parent().parent().parent().find("span:first").attr("data-cost");
	if(num >= store) {
		num = store;
		elem.prev().removeClass("limit");
		elem.next().addClass("limit");
	}else if(num >= exchangeNumber){
		if(num > exchangeNumber) {
			sAlert("该商品每次仅限兑换"+exchangeNumber+"个");
		}
		num = exchangeNumber;
		elem.next().addClass("limit");
		if(num>1) {
			elem.prev().removeClass("limit");
		}
	} else if(num <= 1) {
		num = 1;
		elem.next().removeClass("limit");
		elem.prev().addClass("limit");
	} else if(num=='') {
		elem.val(1);
	}
	var data = changeNum(id,num);
	if(data.code == 262) {//库存不足，更新页面库存数量值
		elem.parent().attr("data-info",data.lastStore);
	}
	
	if(data.code == 253) {//253表示原购物车数量大于当前礼品剩余数量
		elem.parent().attr("data-info",data.lastStore);
		num = 1;
		sAlert(data.msg);
	}else if(data.code != 200) {
		elem.val(data.oldNum);
		if(data.oldNum < store) {
			elem.next().removeClass("limit");
		}
		if(data.oldNum==1) {
			elem.prev().addClass("limit");
		}
		sAlert(data.msg);
		return false;
	}
	elem.val(num);
	if(num > 1) {
		elem.prev().removeClass("limit");
	}
	if(num < store && num < exchangeNumber) {
		elem.next().removeClass("limit");
	}
	single = Number(single);
	elem.parent().parent().next().html(num*single);
	//b==false表示被选中。计算中领宝
	var b = elem.parent().parent().parent().find("span:first").attr("flag");
	if(b=="false") {
		countIntegral += (num - Number(oldNum)) * single;
		$("#countIntegral").html(countIntegral);
	}
}
//修改数量
function changeNum(id,num) {
	var b = false;
	$.ajax({
		url:'/lingbaoExchange/changeNum',
		type:'get',
		async:false,
		data:{'id':id,'num':num},
		success:function(data) {
			b = data;
		}
	});
	return b;
}

function setOldNum(val) {
	oldNum = val;
}

//立即兑换验证
function validExchange(elem){
	$("#exchangeEditorAddr").hide();
	$("#exchangeDialogAddress").empty();
	clearAddressInfo();
	if(checkedCId=='') {
		sAlert("请先选择一条数据");
		return false;
	} else {
		//用户领宝数量
		var lingbaoCount = Number($("#lingbaoShow").text());
		//兑换所需领宝数量
		var cost = Number(elem.prev().children("strong").html());
		if(lingbaoCount < cost) {
			sAlert("领宝余额不足");
			return false;
		} else {
			$.ajax({
				url:'/lingbaoExchange/validateExchange',
				type:'get',
				data:{'cids':checkedCId},
				success:function(data){
					if(data.code==200){
						$("#spanLingbaoCost").html(data.obj.allCost);
						//是否需要地址 0 需要
						$("#giftType").val(data.obj.type);
						if(data.obj.type==0) {
							addressInit('province', 'city', 'town');
							createExchangeAddressElem(data.obj.addressList,'');
						}
						$("#rz-box-bg").show();
					    $("#exchangeDialog").show();
					    offsetDiv("#exchangeDialog");
					} else {
						sAlert(data.msg);
						return false;
					}
				}
			});
		}
		
	}
}


//确认兑换
function confirmExchange() {
	var cids = checkedCId.substring(0, checkedCId.length-1);
	var type = $("#giftType").val();
	var addressId = '';
	if(type == 0) {
		if($("#isFirstAddress").val()==1) {
			 var name = $("#name").val().replace(/^\s+|\s+$/g, '');
			 var telephone = $("#tel").val().replace(/^\s+|\s+$/g, '');
			 var address = $("#addressDetail").val().replace(/^\s+|\s+$/g, '');
			 if(!testName(name) || !testTel(telephone) || !testAddress(address)) {
				 return false;
			 }
			 addressManage.update();
		}
		addressId = $("#exchangeDialogAddress .checked li span:first").attr("data-info");
		if(undefined==addressId || addressId=='') {
			sAlert("请先选择一个收货地址");
			return false;
		}
	}
	$.ajax({
		url:'/lingbaoExchange/exchangeGift.html',
		type:'get',
		data:{'cids':cids,'addressId':addressId},
		success:function(data){
			$("#exchangeDialog").hide();
			if(data.code==200){
				//修改展示用户领宝数量
				$("#lingbaoShow div").html(data.obj);
				$("#rz-box-bg").show();
			    $("#exchangeSuccessDialog").show();
			    offsetDiv("#exchangeSuccessDialog");
			    countNumber = 0;
				countIntegral = 0;
				checkedCId = '';
			    exchangeRecordList(1);
			    //确认兑换后重新加载 进行中 数据
			    queryInfoByUidWait(1);
			} else {
				$("#rz-box-bg").show();
			    $("#exchangeFailDialog").show();
			    offsetDiv("#exchangeFailDialog");
			}
		}
	});
}


