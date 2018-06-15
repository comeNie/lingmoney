<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
<title>提示信息</title>
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$(function(){
		$.ajax({
			type : "post",
			data : JSON.parse('${params}'),
			url : "${reqUrl}",
			dataType : "json",
			success : function(data) {
				if (data.code == 200) {
					$("#accountOpenForm").attr("action", data.obj.bankUrl);
					$("#accountOpenRequestData").val(data.obj.requestData);
					$("#accountOpenTransCode").val(data.obj.transCode);
					$("#channelFlow").val(data.obj.channelFlow);
					$("#accountOpenForm").submit();
				} else {
					$("#errorDiv").show();
					$("#sorryMsg").html(data.msg);
				}
			}
		});
	})
</script>

</head>
<body>
	<div>
	   <input type="hidden" id="channelFlow" value="">
		<form id="accountOpenForm" method="post" action="">
			<input type="hidden" id="accountOpenRequestData" name="RequestData" value=""/>
			<input type="hidden" id="accountOpenTransCode" name="transCode" value=""/>
			<input type="submit">
		</form>
	</div>
	<div id="errorDiv" style="margin-top: 10%; display: none; text-align: center; padding-top: 28%;">
		<img alt="" src="/resource/images/choujiangImg/sorry0.png" style="width: 50%;">
		<div id="sorryMsg" style="text-align: center; font-size: 16px; padding-top: 12%;color:#666;"></div>
	</div>
</body>
</html>