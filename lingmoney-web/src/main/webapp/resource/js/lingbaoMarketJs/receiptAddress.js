var addressMag = new addressManage({
	'type':'address',
	'success':{
		'del':function(data,id){
			sAlert(data.msg);
			if(data.code==200){
				addressMag.init();
				//如果删除的地址信息为编辑框中的信息，清空编辑框
				var eid = $("#addressId").val();
				if(eid==id) {
					$(".addAddress p").html("新增收货地址");
					clearAddressInfo();
				}
			}
			
		},
		'setDefault':function(data){
			 if(data.code==200) {
				 addressMag.init();
			 } else {
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
				addressMag.init();
				$(".addAddress p").html("新增收货地址");
				$("#saveType").val('add');
				clearAddressInfo();
			 } else {
				 sAlert(data.msg);
			 }
		},
		'add':function(data){
			if(data.code==200) {
				addressMag.init();
				$(".addAddress p").html("新增收货地址");
				$("#saveType").val('add');
				clearAddressInfo();
			} else {
				sAlert(data.msg);
			}
		}
	},
	'editStart':function(){
		$(".addAddress p").html("修改收货地址");
	    $("#saveType").val('update');
	    $("#name-addAddress").val();
	}
});

/*领宝收货地址js*/
 $(function(){
	 var isFirstSetAddress=0;
	  /*弹框*/
	  $("#btnChangeAddr").on("click",function(){
		  addressMag.init();
		  $("#rz-box-bg").show();
		  $("#changeAddr").show();
		  offsetDiv("#changeAddr");
		  clearAddressInfo();
	  });
	 $(".rz-close,.btns-addAddress a:eq(1)").click(function() {
		$("#rz-box-bg").hide();
		$("#changeAddr").hide();
		$(".addAddress p").html("新增收货地址");
		$(".addAddress p").attr("class","addAdress");
	 });
  });
 
 /**
  * 格式化手机号
  * @param number
  * @returns {String}
  */
 function formatMobile(number) {
	 return number.substring(0,3)+"****"+number.substring(7,11);
 }
 
 

 

 
 