/**
 * 通用格式化对象  by yjq
 * 
 **/

//字符串转日期格式，strDate要转为日期格式的字符串
function getDate(strDate){
	strDate = strDate + "";
	
	var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,function (a) { 
		return parseInt(a, 10) - 1; 
		}).match(/\d+/g) + ')');
  return date;
}

//datagrid 通用格式化对象
function CommonFormatter(userConfig){
	var config = {
		dataTimeFormat:"yyyy-MM-dd hh:mm:ss",
		dateFormat:"yyyy-MM-dd",
		titleSub:8,
		longTitleSub:10
	};
	$.extend(config, userConfig);
	
	
	// formatter
	var formatter = {
			// 日期时间格式化
		    DateTimeFormatter: function (value, row, index) {
		        if (value == undefined) {
		            return "";
		        }
		        var dateValue = getDate(value);
		        if (dateValue.getFullYear() < 1900) {
		            return "";
		        }
		        return dateValue.format(config.dataTimeFormat);
		    },

		    //日期格式化
		    DateFormatter: function (value, row, index) {
		        if (value == undefined) {
		            return "";
		        }
		        var dateValue = getDate(value);
		        if (dateValue.getFullYear() < 1900) {
		            return "";
		        }
		        return dateValue.format(config.dateFormat);
		    },
		    // 短标题截取
		    TitleFormatter : function (value, row, index) {
		        if (value.length > 10) value = value.substr(0, config.titleSub) + "...";
		        return value;
		    },
		    
		    // 长标题截取
		    LongTitleFormatter: function (value, row, index) {
		        if (value.length > 15) value = value.substr(0, config.longTitleSub) + "...";
		        return value;
		    }
		};
	
	return formatter;
}

function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}

var cityInfo = "";

$(function(){
	$.ajax({
		async:false,
		url:"/rest/areaDomain/queryCityInfo?r=" + Math.random(),
		success:function(data) {
			cityInfo = data.obj;
		}
	});
});

//城市代码--》城市名
function formatCityCode(value,row) {
	if(row.cityCode!=undefined) {
		return cityInfo[row.cityCode];
	}
}


