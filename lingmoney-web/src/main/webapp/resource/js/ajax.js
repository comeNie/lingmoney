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
	//ajax请求标识
	xhr.setRequestHeader("x-requested-with", "XMLHttpRequest");
	xhr.send(msg);
	return ret;
}

/**
 * ajax封装
 * url 发送请求的地址
 * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
 * async 默认值: true。默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。
 *       注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
 * type 请求方式("POST" 或 "GET")， 默认为 "GET"
 * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
 * successfn 成功回调函数
 * errorfn 失败回调函数
 */
jQuery.ax=function(url, data, async, type, dataType, successfn, errorfn) {
    async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
    type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
    dataType = (dataType==null || dataType=="" || typeof(dataType)=="undefined")? "json" : dataType;
    data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
    $.ajax({
        type: type,
        async: async,
        data: data,
        url: url,
        dataType: dataType,
        success: function(d){
            successfn(d);
        },
        error: function(e){
            errorfn(e);
        }
    });
};

/**
 * ajax封装
 * url 发送请求的地址
 * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
 * successfn 成功回调函数
 */
jQuery.axs=function(url, data, successfn) {
//	console.log(data);
    data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
    $.ajax({
        type: "post",
        data: data,
        url: url,
        dataType: "json",
        success: function(d){
            successfn(d);
        }
    });
};

/**
 * ajax封装
 * url 发送请求的地址
 * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
 * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
 * successfn 成功回调函数
 * errorfn 失败回调函数
 */
jQuery.axse=function(url, data, successfn, errorfn) {
    data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
    $.ajax({
        type: "post",
        data: data,
        url: url,
        dataType: "json",
        success: function(d){
            successfn(d);
        },
        error: function(e){
            errorfn(e);
        }
    });
};