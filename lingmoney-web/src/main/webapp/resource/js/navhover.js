$(function(){
	$('.nav-body li').mouseenter(function(){
		$(this).find('.scsq').css({'display':'block','z-index':'1000'});
		$('#wrapper').css('z-index','-1000');
	}).mouseleave(function(){
		$(this).find('.scsq').css({'display':'none','z-index':'-1000'});
		$('#wrapper').css('z-index','0');
	});
});