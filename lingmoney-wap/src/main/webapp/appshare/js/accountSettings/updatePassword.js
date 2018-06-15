
$(function(){
	showPass();
});

function showPass(){

		$('.as_up_inputbox .as_up_ib').find('a').click(function(){
			if($(this).hasClass('active')){
				$(this).removeClass('active');
				$(this).parent().find('.see_n').prop('type','password');
			}else{
				$(this).parent().find('.see_n').prop('type','text');
				$(this).addClass('active');
			}
		});

}