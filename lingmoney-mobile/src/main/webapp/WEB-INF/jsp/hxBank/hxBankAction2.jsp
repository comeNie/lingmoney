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
	setTimeout(function(){
		$("#ogwForm").submit();
	}, 3000)
})

</script>
</head>
<body>
	<div>
		<span>华兴银行自动跳转...</span>
		<form id="ogwForm" name="ogwForm" method="post" action="${bankUrl }">
			<input type="hidden" id="RequestData" name="RequestData" value="${requestData }" />
			<input type="hidden" id="transCode" name="transCode" value="${transCode }" />
		</form>
	</div>
</body>
</html>