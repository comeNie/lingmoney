var browser = navigator.appName
if(browser == 'Microsoft Internet Explorer'){
	var b_version = navigator.appVersion
	var version = b_version.split(";");
	var trim_Version = version[1].replace(/[ ]/g,"");
	alert(trim_Version == "MSIE8.0");
	if (trim_Version == "MSIE6.0") {
		window.top.location.href = "/ie_error.html";
	} else if (trim_Version == "MSIE7.0") {
		window.top.location.href = "/ie_error.html";
	} else if (trim_Version == "MSIE8.0") {
		window.top.location.href = "/ie_error.html";
	}
}
