<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>华兴银行自动跳转</title>

<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>

</head>
<body>
	<%-- <c:choose>
		<c:when test="${errorInfo!=null}">
			<input id="errorInfo" value="${errorInfo}"/>
			<script type="text/javascript">
				window.location.href="errorInfo?"+$("#errorInfo").val();
			</script>
		</c:when>
		<c:otherwise>
			<form name="tiedCardForm" method="post" action="银行的url">
				<input name="requestData" value="${requestData}"/>
				<input name="transCode" value="${transCode}"/>
			</form> 
			<script type="text/javascript">
				$(document).ready(function(){
					console.log($("body").html());
					//$("form").submit();
				})
			</script>
		</c:otherwise>
	</c:choose> --%>
	<input name="token" val=""/>
	
	<script type="text/javascript">
	$.post("/bank/tiedCardPage",{"token":"caf572284b864c1b81c48014830cf7e8cb8a751549bb46f8932ad6fab9da658e"})
	
	</script>
</body>
</html>