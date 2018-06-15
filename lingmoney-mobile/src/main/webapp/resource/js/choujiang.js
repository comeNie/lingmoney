//中奖下标变量 
var award;
//禁止连续点击 
var click=false;
//中奖几率展示
var dayLotteryRatio = 50;
//中奖价值
var price = "";
//是否要跳转到购物车
var buycart = false;
$(function(){
	queryChoujiangItem();
	
	luck.init('luck');
	$("#btn").click(function(){
		//验证用户是否登录
		var uid = $("#userId").val();
		if(uid=='') {
			$('#rz-box-bg').show();
			$('#zhc').show(function(){
				$('.reset').on('click',function(){
					$('#zhc,#rz-box-bg').hide();
				});
			});
			return false;
		}
		if(click) {
			return false;
		}
		var data = validateChoujiang();
		if(null==data){
			$("#shadowDiv").show();
			$("#apologyDiv .jifenBox").html("系统错误");
			$("#apologyDiv").show();
			return false;
		}else if(data.code!=200){
			$("#shadowDiv").show();
			$("#apologyDiv .jifenBox").html(data.msg);
			$("#apologyDiv").show();
			return false;
		}
		//标记中奖下标
		award = data.item;
		//获取中奖几率
		dayLotteryRatio = data.dayLotteryRatio;
		//中奖价值
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
	
	$("#luckyDiv .close, #sorryDiv .close, #apologyDiv a").on("click",function(){
		$("#shadowDiv").hide();
		$("#luckyDiv").hide();
		$("#sorryDiv").hide();
		$("#apologyDiv").hide();
	});
});

/*生成抽奖元素*/
function queryChoujiangItem() {
	$.ajax({
		url:'/myPlace/queryChoujiangItem',
		type:'post',
		async:false,
		success:function(data) {
			if(typeof(data)=="string") {
				data = JSON.parse(data);
			}
			if(data.code==200) {
				$.each(data.data,function(i, item){
					var img = '';
					if(undefined==item.pic) {
						//非奖品默认图片
						img = '<div><img src="/resource/images/choujiangImg/sorry.jpg"></img><span>'+item.name+'</span></div>';
					}else {
						img = '<div><img src="'+item.pic+'"><span>'+item.name+'</span></div>';
					}
					$("#luck table td[class$='luck-unit-"+i+"']").html(img);
				});
				$(".hdgz").append(data.rule);
				$("#cost").text(data.cost+"LB/次");
			} else {
				//无数据
				$("#shadowDiv").show();
				$("#apologyDiv .jifenBox").html(data.msg);
				$("#apologyDiv").show();
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
		url:'/myPlace/validateChoujiang',
		type:'post',
		data:{"t":new Date()},
		async:false,
		success:function(data) {
			if(typeof(data)=="string") {
				data = JSON.parse(data);
			}
			if(data.code==200) {
				$("#lingbaoShow").html(data.lingbao);
			}
			json = data;
		}
	});
	return json;
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
		var giftName = $(".luck-unit-"+luck.prize+" img").next().html();
		//延迟提示，抽奖恢复，修改中奖概率
		setTimeout(function(){
			$("#shadowDiv").show();
			if(giftName=="Sorry") {
				$("#sorryDiv").show();
			} else {
				$("#luckyDiv .giftName").html(giftName);
				$("#luckyDiv .price").html(price);
				$("#luckyDiv").show();
				if(buycart==false){//我的领宝
					$("#goCar").hide();
					$("#goMyLingbao").show();
					$(".tip").text("*奖品已充值到你的账户，点击我的领宝查看中奖物品。");
				}else{//购物车
					$("#goCar").show();
					$("#goMyLingbao").hide();
					$(".tip").text("*奖品已加入购物车，点击查看购物车领取中奖物品。");
				}
			}
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