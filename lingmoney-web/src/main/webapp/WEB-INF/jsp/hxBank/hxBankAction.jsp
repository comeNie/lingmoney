<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$(function(){
		$.ajax({
			type : "post",
			data : {
				params : "${params}"
			},
			url : "${reqUrl}",
			dataType : "json",
			success : function(data) {
				alert(data);
				if (data.code == 200) {
					
					console.log(data.obj.bankUrl);
					$("#accountOpenForm").attr("action", data.obj.bankUrl);
					$("#accountOpenRequestData").val(data.obj.requestData);
					$("#accountOpenTransCode").val(data.obj.transCode);
					alert("aaaaaaa==="+$("#accountOpenForm"));
					$("#accountOpenForm").submit();
				} else {
					window.location.href="errorInfo?"+data.msg;
				}
			}
		});
	})
</script>
</head>
<body>
	<div>
		<form id="accountOpenForm" method="post" action="" target="_blank">
			<input type="hidden" id="accountOpenRequestData" name="RequestData" value=""/>
			<input type="hidden" id="accountOpenTransCode" name="transCode" value=""/>
		</form>
	</div>
</body>
</html>