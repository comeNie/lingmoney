// 地址管理配置
var addressManage = new addressManage({
	'type':'exchange',
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

$(function(){
	$("#nav03").addClass("nav-hover-main");
	
	$("ul.tab li").on("click",function(){
		 $(this).addClass("clicked").siblings().removeClass("clicked");
		 var index=$(this).index();
		 $(".cons>div").eq(index).addClass("show").siblings().removeClass("show");
	 });
	 
	 $(".rz-close").click(function() {
		$("#rz-box-bg").hide();
		$("#exchangeDialog").hide();
		$("#exchangeSuccessDialog").hide();
		$("#exchangeFailDialog").hide();
		$(".editor-addr").hide();
		$("#addToGiftCartAlertDiv").hide();
	 });
	 
	 /*编辑完成*/
	 /*$(".editor-addr_btn").on("click",function(){
		 updateAddress();
	 });*/
	 
	 //城市列表选择框
	 $(".adress-box").click(function(){
	 	$(".dropdown").toggle();
	 });
	 $(".dropdown li a").click(function(){
		$(".dropdown").css("display","none");
	 });
	 
	//取消按钮
	$(".rz-button.reset").click(function(){
		$("#rz-box-bg").hide();
		$("#zhc").hide();
	});
	 
});

//-
function subtractNum(elem) {
	var num = Number($("#num").val());
	var integral = Number($("#integral").val());
	
	var exchangeNumber = Number($("#exchangeNumber").val());
	
	if(num==1) {
		$("#lingbaoSpan").html(num*integral);
		return false
	}else if(num==2){
		num = num-1;
		$("#num").val(num);
		elem.addClass("limit");
		$(".tb-count a:last").removeClass("limit");
	}else if(num<=1){
		num=1;
		$("#num").val(num);
		elem.addClass("limit");
		$(".tb-count a:last").removeClass("limit");
	} else {
		num = num-1;
		$("#num").val(num);
		$(".tb-count a:last").removeClass("limit");
	}
	$("#lingbaoSpan").html(num*integral);
}
//+
function addNum(elem) {
	if(elem.attr("class")=="limit") {
		return false;
	}
	var store = Number($("#store").val());
	var num = Number($("#num").val());
	var integral = Number($("#integral").val());
	var exchangeNumber = Number($("#exchangeNumber").val());
	if(exchangeNumber!=0 && num>=exchangeNumber) {
		num = exchangeNumber;
		$("#num").val(num);
		elem.addClass("limit");
		if(num>1) {
			$(".tb-count a:first").removeClass("limit");
		}
		sAlert("该商品每次仅限兑换"+exchangeNumber+"个");
		return false;
	}
	if(num==store) {
		$("#lingbaoSpan").html(num*integral);
		return false;
	} else if (num==(store-1)) {
		num = num+1;
		$("#num").val(num);
		elem.addClass("limit");
		$(".tb-count a:first").removeClass("limit");
		$("#lingbaoSpan").html(num*integral);
		return false;
	} else if(num>=store){
		num=store;
		$("#num").val(num);
		elem.addClass("limit");
		$(".tb-count a:first").removeClass("limit");
	} else {
		num = num+1;
		$("#num").val(num);
		$(".tb-count a:first").removeClass("limit");
	}
	$("#lingbaoSpan").html(num*integral);
}
//输入数量
function numBlur() {
	var store = Number($("#store").val());
	var num = $("#num").val();
	var zeroStart = /^[1-9]\d*$/;
	if(isNaN(num)) {
		num = 1;
	} else {
		num = Number(num);
	}
	$("#num").val(num);
	var integral = Number($("#integral").val());
	
	var exchangeNumber = Number($("#exchangeNumber").val());
	
	if(exchangeNumber!=0 && num>=exchangeNumber) {
		if(num>exchangeNumber) {
			sAlert("该商品每次仅限兑换"+exchangeNumber+"个");
		}
		num = exchangeNumber;
		$("#num").val(num);
		$(".tb-count a:last").addClass("limit");
		if(num>1) {
			$(".tb-count a:first").removeClass("limit");
		}
	}else if(num>=store) {
		num=store;
		$("#num").val(num);
		$(".tb-count a:first").removeClass("limit");
		$(".tb-count a:last").addClass("limit");
	} else if(num<=1) {
		num=1;
		$("#num").val(num);
		$(".tb-count a:last").removeClass("limit");
		$(".tb-count a:first").addClass("limit");
	} else if(num=='') {
		$("#num").val(1);
	}
	if(num>1) {
		$(".tb-count a:first").removeClass("limit");
	}
	if(num < store && num < exchangeNumber) {
		$(".tb-count a:last").removeClass("limit");
	}
	$("#lingbaoSpan").html(num*integral);
}

//加入购物车
function addToShoppingCart() {
	var giftId = $('#giftId').val();
	var typeId = $('#typeId').val();
	var num = $('#num').val();
	if(num==0) {
		num = 1;
	}
	var integral = $('#integral').val();
	integral = Number(integral)*Number(num);
	
	$.ajax({
		url:'/lingbaoExchange/addToShoppingCart',
		type:'get',
		data:{'giftId':giftId,'typeId':typeId,'num':num,'integral':integral},
		success:function(data){
			if(data.code == 200){
				$("#rz-box-bg").show();
				$("#addToGiftCartAlertDiv").show();
				offsetDiv("#addToGiftCartAlertDiv");
			} else if(data.code == 3009){
				$("#rz-box-bg").show();
				$("#zhc").show();
				offsetDiv("#zhc");
			} else {
				sAlert(data.msg);
			}
		}
	});
}

//兑换
function exchangeGift() {
	var giftId = $("#giftId").val();
	var num = $('#num').val();
	var type = $("#giftType").val();
	var addressId = '';
	if(type==0) {//需要地址
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
	$("#rz-box-bg").hide();
	$("#exchangeDialog").hide();
	$("#exchangeSuccessDialog").hide();
	$("#exchangeFailDialog").hide();
	$.ajax({
		url:'/lingbaoExchange/exchangeGift',
		type:'get',
		data:{'giftId':giftId,'num':num,'addressId':addressId},
		success:function(data){
			if(data.code==200){
				$("#rz-box-bg").show();
			    $("#exchangeSuccessDialog").show();
			    offsetDiv("#exchangeSuccessDialog");
			} else {
				$("#rz-box-bg").show();
			    $("#exchangeFailDialog").show();
			    offsetDiv("#exchangeFailDialog");
			}
		}
	});
}

//兑换验证
function validateExchange() {
	$("#exchangeEditorAddr").hide();
	clearAddressInfo();
	var giftId = $("#giftId").val();
	var number = $('#num').val();
	$.ajax({
		url:'/lingbaoExchange/validateExchange',
		type:'get',
		data:{'giftId':giftId,'number':number},
		success:function(data){
			if(data.code==200){
				$("#spanLingbaoCost").html(data.obj.allCost);
				if(data.obj.type==1) {
					$("#giftType").val(1);
				} else {
					addressInit('province', 'city', 'town');
					createExchangeAddressElem(data.obj.addressList,'');
					$("#giftType").val(0);
				}
				$("#rz-box-bg").show();
			    $("#exchangeDialog").show();
			    offsetDiv("#exchangeDialog");
			} else if(data.code == 3009) {
				$("#rz-box-bg").show();
				$("#zhc").show();
				offsetDiv("#zhc");
			} else {
				sAlert(data.msg);
				return false;
			}
		}
	});
}



