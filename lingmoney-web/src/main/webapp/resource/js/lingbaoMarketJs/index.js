$(function(){
	//导航栏选中标识
	$("#nav03").addClass("nav-hover-main");
	//中奖滚动
	jQuery(".mingdan").slide({
		mainCell : ".bd ul",
		autoPlay : true,
		effect : "topMarquee",
		vis : 10,
		interTime : 50,
		trigger : "click"
	});
	
	$(".box-item.clearfix .item img,.item-classification img").on("click",function(){
		window.location.href = $(this).parent().find("a:first").attr("href");
	});
});