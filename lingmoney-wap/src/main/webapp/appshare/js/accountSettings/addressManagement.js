$(function(){
});

/*
	地址管理设置默认
*/
function addreddMansetDefault(parameter){
		if(!$(parameter).hasClass('active')){
			$(parameter).addClass('active');
			$(parameter).parent().parent().addClass('default');
			$(parameter).parent().parent().siblings().find('.amb_setdefault').removeClass('active');
			$(parameter).parent().parent().siblings().removeClass('default');
		}
}