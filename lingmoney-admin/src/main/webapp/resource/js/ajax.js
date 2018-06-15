function sendReq(url, msg) {

	//返回值
	var ret;
	//1、创建xhr对象
	var xhr = new XMLHttpRequest();
	if (!xhr) {
		alert('创建xhr失败');
		return;
	}
	//alert('hi');
	//2、指定回调函数，并且和事件关联
	xhr.onreadystatechange = function() {

		//和服务器的交互完成
		if (xhr.readyState == 4) {
			//服务器返回ok
			if (xhr.status == 200) {

				//4、接受服务器的响应并且修改页面
				ret = xhr.responseText;				
			}
		}

	};

	//3、向服务器发送请求
	
	xhr.open("post", url, false);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	xhr.send(msg);
	
	return ret;
	
}