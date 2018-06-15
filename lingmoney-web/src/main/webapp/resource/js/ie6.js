$(document).ready(function() {
	if ($.browser.version == "6.0") {
		$(".has_sub").hover(function() {
			$(this).find(".s_nav").show();
		}, function() {
			$(this).find(".s_nav").hide();
		});
	}
});
