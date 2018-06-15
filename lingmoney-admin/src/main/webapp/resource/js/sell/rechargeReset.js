

// 查询
function reset(){
	
	$.getJSON("/rest/sell/rechargeReset/reset?telephone=" + $("#telephone").val(),
	function(info) {
		
		alert(info.msg);
	        	    	   
	 });
}





