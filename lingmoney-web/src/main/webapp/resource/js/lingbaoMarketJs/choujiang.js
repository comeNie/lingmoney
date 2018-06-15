//中奖下标变量 
var award;
//禁止连续点击 
var click=false;
//中奖几率展示
var dayLotteryRatio = 50;
//奖品价值
var price = "";
//是否要跳转到购物车
var buycart = false;
$(function(){
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
	 
	queryChoujiangItem();
	
	luck.init('luck');
	$("#btn").click(function(){
		//验证用户是否登录
		if(click) {
			return false;
		}
		var data = validateChoujiang();
		if(null == data){
			sAlert("系统错误，请联系管理员");
			return false;
		} else if(data.code == 3009){
			$("#rz-box-bg").show();
			$("#zhc").show();
			offsetDiv("#zhc");
			return false;
		} else if(data.code != 200){
			sAlert(data.msg);
			return false;
		}
		//标记中奖下标
		award = data.item;
		//获取中奖几率
		dayLotteryRatio = data.dayLotteryRatio;
		price = data.price;
		buycart = data.buycart;
		//按下弹起效果
		$("#btn").addClass("cjBtnDom");
		setTimeout(function(){	
			$("#btn").removeClass("cjBtnDom");
		},200);
		luck.speed=100;
		roll();
		click=true;
		return false;
	});
});

/*生成抽奖元素*/
function queryChoujiangItem() {
	var typeID = $("#typeID").val();
	$.ajax({
		url:'/lbmarket/queryChoujiangItem',
		type:'get',
		data:{'typeID':typeID},
		async:false,
		success:function(data) {
			if(data.code==200) {
				//加载背景图片
				$(".ld_box").css("background",
						"url('"+data.picture+"') center top no-repeat");
				$.each(data.data,function(i, item){
					var img = '';
					if(undefined==item.pic) {
						//非奖品默认图片
						img = '<img alt="'+item.name+'" src="/resource/images/lingbaoMarketImages/choujiang/220x240-sorry.jpg"></img>';
					}else {
						img = '<img alt="'+item.name+'" src="'+item.pic+'"></img>';
					}
					$("#luck table td[class$='luck-unit-"+i+"']").html(img);
				});
				$("#cost").text(data.cost);
				$(".md_left").append(data.rule);
			} else {
				//无数据，返回主页
				sAlert(data.msg);
				setTimeout(function(){window.location.href = "/lbmarket/index";},2000);
			}	
		}
	});
}

/**
 * (验证)抽奖
 * @returns
 */
function validateChoujiang() {
	var json = null;
	$.ajax({
		url:'/lbmarket/validateChoujiang',
		data:{'t':new Date()},
		type:'post',
		async:false,
		success:function(data) {
			if(data.code==200) {
				$("#lingbaoShow").html(data.lingbao);
			}
			json = data;
		}
	});
	return json;
}

function choujiaoAlert(txt){
	if(txt=="Sorry") {
		showPop("#successQian");
		$("#successQian").find("h2").text("很遗憾");
		$(".successQian_con").find('div').css("background","url('/resource/images/lingbaoMarketImages/bg_failChoujiang.gif') center top no-repeat");
		$("#successContent").text("");
		$("#successQianP").text("很遗憾，您没有中奖哦！");
	} else {
		showPop("#successQian");
		$("#successQian").find("h2").text("恭喜");
		$(".successQian_con").find('div').css("background","url('/resource/images/lingbaoMarketImages/bg_successChoujiang.gif') center top no-repeat");
		$("#successContent").html("恭喜您抽中了 "+txt+"<span style='font-size:16px;'>（"+price+"）</span>");
		if(buycart==false){
			$("#successQianP").html("运气杠杠滴,领宝已充值到 <a style='font-size:16px;text-decoration: underline;color:red;' target=_blank href=/myAccount/lingbaoRecord>你的账户>></a>");
		}else{
			$("#successQianP").html("运气杠杠滴,请前往 <a style='font-size:16px;text-decoration: underline;color:red;' target=_blank href=/lingbaoExchange/exchangeRecord>购物车结算>></a>");
		}
		
	}
}

var luck={
	index:-1,	//当前转动到哪个位置，起点位置
	count:0,	//总共有多少个位置
	timer:0,	//setTimeout的ID，用clearTimeout清除
	speed:20,	//初始转动速度
	times:0,	//转动次数
	cycle:50,	//转动基本次数：即至少需要转动多少次再进入抽奖环节
	prize:-1,	//中奖位置
	init:function(id){
		if ($("#"+id).find(".luck-unit").length>0) {
			$luck = $("#"+id);
			$units = $luck.find(".luck-unit");
			this.obj = $luck;
			this.count = $units.length;
			$luck.find(".luck-unit-"+this.index).addClass("active");
		};
	},
	roll:function(){
		var index = this.index;
		var count = this.count;
		var luck = this.obj;
		$(luck).find(".luck-unit-"+index).removeClass("active");
		index += 1;
		if (index>count-1) {
			index = 0;
		};
		$(luck).find(".luck-unit-"+index).addClass("active");
		this.index=index;
		return false;
	},
	stop:function(index){
		this.prize=index;
		return false;
	}
};


function roll(){
	luck.times += 1;
	luck.roll();
	if (luck.times > luck.cycle+10 && luck.prize==luck.index) {
		var giftName = $(".luck-unit-"+luck.prize+" img").attr("alt");
		//延迟提示，抽奖恢复，修改中奖概率
		setTimeout(function(){
			choujiaoAlert(giftName);
			click=false;
			$("#btnRatio").text(dayLotteryRatio);
		},800);
		clearTimeout(luck.timer);
		luck.prize=-1;
		luck.times=0;
	}else{
		if (luck.times<luck.cycle) {
			luck.speed -= 10;
		}else if(luck.times==luck.cycle) {
			luck.prize = award;		
		}else{
			if (luck.times > luck.cycle+10 && ((luck.prize==0 && luck.index==7) || luck.prize==luck.index+1)) {
				luck.speed += 110;
			}else{
				luck.speed += 20;
			}
		}
		if (luck.speed<40) {
			luck.speed=40;
		};

		luck.timer = setTimeout(roll,luck.speed);
	}
	return false;
}


//闪灯效果
var num = 0;
$(".shanDeng").attr("class",function(){
	setInterval(function(){ 
		num++;
		if(num%2==0){
			$('#deng').addClass("shanDeng2");
		}else{
			$('#deng').addClass("shanDeng");
			$('#deng').removeClass('shanDeng2');
		}
	},500)
});