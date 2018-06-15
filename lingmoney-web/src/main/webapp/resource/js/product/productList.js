
$(document).ready(function() {
	$("#nav02").addClass("nav-hover-main"); 
	
	$(".media-link li:first").css("cursor", "pointer")
	$(".media-link li:first").hover(function() {
		$(this).find("div").show();
	}, function() {
		$(this).find("div").hide();
	});
	
	$(".main-head li:last").css("border", "none");
	$(".main3 li:last").css("margin-right", "0");
	$(".licai li:last").css("margin-right", "0");

	/*认证中心--密码弹框*/
	$("#rz-box-mima .rz-close").click(function() {
		$("#rz-box-bg").hide();
		$("#rz-box-mima").hide();
	});

	$("#rz-box-mima-OK .rz-closeOK").click(function() {
		$("#rz-box-mima-OK").hide();
	});
	
	
	var isRecommend = $("#isRecommend").val();
	if (isRecommend == '0' || isRecommend=='null'||isRecommend=='') {
		$("#isRecommend0").addClass("hover");
	} else if(isRecommend == '1') {
		$("#isRecommend1").addClass("hover");
	}
	
	var cType = $("#cType").val();
	if (cType == '0' || cType=='null' ||cType=='') {
		$("#cType0").addClass("hover");
	} else {
		$("#cType"+cType).addClass("hover");
	}
	
	var cRate = $("#cRate").val();
	if (cRate == '0' || cRate=='null' ||cRate=='') {
		$("#cRate0").addClass("hover");
	} else {
		$("#cRate"+cRate).addClass("hover");
	}
	
	var cCycle = $("#cCycle").val();
	if (cCycle == '0' || cCycle=='null' ||cCycle=='') {
		$("#cCycle0").addClass("hover");
	} else {
		$("#cCycle"+cCycle).addClass("hover");
	}
	
	var minMenoy = $("#minMenoy").val();
	if (minMenoy == '0' || minMenoy=='null' ||minMenoy=='') {
		$("#minMenoy0").addClass("hover");
	} else {
		$("#minMenoy"+minMenoy).addClass("hover");
	}
	
	var pcStatus = $("#pcStatus").val();
	if (pcStatus == '0' || pcStatus == 'null' || pcStatus == '') {
		$("#pcStatus0").addClass("hover");
	} else {
		$("#pcStatus" + pcStatus).addClass("hover");
	}
});

// 回车事件
document.onkeydown = function(e) {
	if (!e)
		e = window.event;
	if ((e.keyCode || e.which) == 13) {
		document.getElementById("bot").click();
	}
}

// 弹框居中 
function offsetDiv(s) {
	height = $(s).height() + 30;
	width = $(s).width();
	$(s).css("margin-top", -height / 2);
	$(s).css("margin-left", -width / 2);
}

jQuery(".slideBox").slide({
	mainCell : ".bd ul",
	effect : "top",
	autoPlay : true
});

function animate() {
	$(".charts").each(function(i, item) {
		var a = parseInt($(item).attr("w"));
		$(item).animate({
			width : a + "%"
		}, 500);
	});
}
animate();

function isRecommendSub(val) {
	$("#isRecommend").val(val);
	sub();
}

function cTypeSub(val) {
	$("#cType").val(val);
	sub();
}

function cRateSub(val) {
	$("#cRate").val(val);
	sub();
}

function cCycleSub(val) {
	$("#cCycle").val(val);
	sub();
}

function minMenoySub(val) {
	$("#minMenoy").val(val);
	sub();
}

function pcStatusSub(val) {
	$("#pcStatus").val(val);
	sub();
}

function keyWordSub() {
	var key = $("#keyword").val();
	$("#key").val(key);
	sub();
}

function sub() {
	var st = $("#isRecommend").val() + "_" + $("#cType").val() + "_"
			+ $("#cRate").val() + "_" + $("#cCycle").val() + "_"
			+ $("#minMenoy").val() + "_" + $("#pcStatus").val();
	var url;
	if ($("#key").val() == '' || $("#key").val() == null) {
		url = "/product/list/" + st + ".html";
	} else {
		url = "/product/list/" + st + ".html?key=" + $("#key").val();
	}

	url = encodeURI(encodeURI(url, "UTF-8"));
	window.location.href = url;
}

function showProduct(val) {
	$("#code").val(val);
	$("#showForm").submit();
}