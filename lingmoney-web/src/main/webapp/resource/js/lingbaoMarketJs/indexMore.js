$(function(){
	//导航栏选中标识
	$("#nav03").addClass("nav-hover-main");
	//中奖滚动
	jQuery(".mingdan").slide({
		mainCell : ".bd ul",
		autoPlay : true,
		effect : "topMarquee",
		vis : 2,
		interTime : 50,
		trigger : "click"
	});
	
	$(".item-classification img").on("click",function(){
		window.location.href = $(this).parent().find("a:first").attr("href");
	});
	
	//返回时判断当前选中类型
	$("#typePick .clicked").click();
	//默认进来查询全部领宝范围
	$("#rangePcik dd:first").click();
});

function filterSearch(page) {
	if(undefined==page || null==page || ''==page) {
		page=1;
	}
	var typeId = $("#typeFilter").val();
	var range = $("#countFilter").val();
	$.ajax({
		url:'/lbmarket/queryGiftWithCondition',
		type:'post',
		data:{"typeId":typeId,"range":range,"page":page, "rows":16},
		success:function(data) {
			if(data.code==200) {
				$("#giftList").empty();
				var html = '';
				$.each(data.obj.giftList,function(i, item){
					if((i+1)%4==0) {
						html += '<div class="item-classification last">';
					} else {
						html += '<div class="item-classification">';
					}
					html += '<img title="'+item.name+'" src="'+item.pictureMiddle+'" width="220" height="228"/>';
	    			html += '<p title="'+item.name+'">'+item.name+'</p>';
	    			html += '<div class="clearfix">';
	    			if(null!=item.preferentialIntegral && item.preferentialIntegral>0) {
	    				html += '<span class="left" >';
	    				html += '<em style="text-decoration: line-through;font-size: xx-small;margin-right: 5px;">'+item.integral+'  </em>';
	    				html += item.preferentialIntegral+'领宝';
	    				html += '</span>';
	    			} else {
	    				html += '<span class="left">'+item.integral+'领宝</span>';	
	    			}
	    			html += '<a href="/lingbaoExchange/exchange?id='+item.id+'" class="right">立即兑换</a>';	
	    			html += '</div></div>';		
				});
				$("#giftList").append(html);
				
				var pages = data.obj.pages;
				var currentpage = data.obj.currentPage;
				var total = data.obj.total;
				
				var pagehtml = '<div id="pages" class="pagination">';
				pagehtml += '&nbsp;共<strong>'+total+'</strong>项,<strong>'+pages+'</strong>页:&nbsp;';
				if(currentpage==1) {
					pagehtml += '<span class="disabled">«&nbsp;上一页</span>';
				}else {
					pagehtml += '<a href="javascript:filterSearch('+(Number(currentpage)-1)+')">«&nbsp;上一页</a>';
				}
				for(var i = 1; i <= pages; i++) {
					if(i==currentpage) {
						pagehtml += '<span class="current">'+currentpage+'</span>';
					}else {
						pagehtml += '<a href="javascript:filterSearch('+i+')">'+i+'</a>';
					}
				}
				if(currentpage==pages) {
					pagehtml += '<span class="disabled">下一页&nbsp;»</span>';
				} else {
					pagehtml += '<a href="javascript:filterSearch('+(Number(currentpage)+1)+')">下一页&nbsp;»</a>';
				}
				pagehtml+='</div>';
				$("#pages").empty().append(pagehtml);
				
				$(".item-classification img").unbind("click").on("click",function(){
					window.location.href = $(this).parent().find("a:first").attr("href");
				});
			}else {
				$("#pages").empty();
				$("#giftList").empty().append("<h3>暂无数据</h3>");
			}
			return false;
		}
	});
}
/**
 * 设置类型筛选项值
 * @param typeId
 */
function setTypeFileter(typeId,item) {
	$("#typePick dd").removeClass("clicked");
	$("#typeFilter").val(typeId);
	item.addClass("clicked");
	filterSearch();
}
/**
 * 设置领宝个数筛选项值
 * @param num
 */
function setCountFilter(range,item) {
	$("#rangePcik dd").removeClass("clicked");
	$("#countFilter").val(range);
	item.addClass("clicked");
	filterSearch();
}
