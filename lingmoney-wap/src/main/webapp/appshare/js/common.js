var locationUrl="http://test.lingmoney.cn"

var lyw=(function(){
/* 时间转换 */
function formatDate(now) { 
	var year=now.getFullYear(); 
	var month=now.getMonth()+1; 
	var date=now.getDate(); 
	var hour=now.getHours(); 
	var minute=now.getMinutes(); 
	var second=now.getSeconds(); 
	/* return year+"-"+month+"-"+date;  */
	 return year+"-"+getzf(month)+"-"+getzf(date)+" "+getzf(hour)+":"+getzf(minute)+":"+getzf(second);  
} 
function getzf(num){  
  if(parseInt(num) < 10){  
	  num = '0'+num;  
  }  
  return num;  
}

//获取RUL参数值
var urlTools = {
	getUrlParam: function(name) {/*?videoId=identification  */
		var params = decodeURI(window.location.search);/* 截取？号后面的部分    index.html?act=doctor,截取后的字符串就是?act=doctor  */
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = params.substr(1).match(reg);
		if (r!=null) return unescape(r[2]); return null;
	}
};
//截取手机号中间4位
function ipHone(t){
    this.t=t.substr(0, 3) + '****' + t.substr(t.length - 4); 
}
//千位符
function toThousands(num) {
    var result = '', counter = 0;
    num = (num || 0).toString();
    for (var i = num.length - 1; i >= 0; i--) {
        counter++;
        result = num.charAt(i) + result;
        if (!(counter % 3) && i != 0) { result = ',' + result; }
    }
    return result;
}
//倒计时
function countDown(starttime){ 
	setInterval(function () {
    var nowtime = new Date();
    var time = starttime - nowtime;
    var day = parseInt(time / 1000 / 60 / 60 / 24);
    var hour = parseInt(time / 1000 / 60 / 60 % 24);
    var minute = parseInt(time / 1000 / 60 % 60);
    var seconds = parseInt(time / 1000 % 60);
    $('.timespan').html(day + "天" + hour + "小时" + minute + "分钟" + seconds + "秒");
  }, 1000);

}

function SetCookie(name, value, expires, path, domain, secure) {
 var today = new Date();
 today.setTime(today.getTime());
 if(expires) { expires *= 86400000; }
 var expires_date = new Date(today.getTime() + (expires));
 document.cookie = name + "=" + escape(value)
  + (expires ? ";expires=" + expires_date.toGMTString() : "")
  + (path ? ";path=" + path : "")
  + (domain ? ";domain=" + domain : "")
  + (secure ? ";secure" : "");
}
function getCookieByString(cookieName){
 var start = document.cookie.indexOf(cookieName+'=');
 if (start == -1) return false;
 start = start+cookieName.length+1;
 var end = document.cookie.indexOf(';', start);
 if (end == -1) end=document.cookie.length;
 return document.cookie.substring(start, end);
 
}



return{
	formatDate:formatDate,
	urlTools:urlTools,
	ipHone:ipHone,
	toThousands:toThousands,
	countDown:countDown,
	getCookieByString:getCookieByString,


}	
	
})()

var bank = lyw.getCookieByString('Bank');
var certification = lyw.getCookieByString('Certification');












































