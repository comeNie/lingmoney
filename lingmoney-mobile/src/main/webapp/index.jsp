<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<h2>Hello World!</h2>
	<form action="/bank/accountOpenPage" method="post" target="_blank">
		<input type="text" name="params" value="" />
		
		<input type="submit" value="开通账户" />
	</form>
	
	<form action="/bank/accountRechargePage" method="post" target="_blank">
		<input type="text" name="params" value="" />
		
		<input type="submit" value="充值" />
	</form>
	
	<form action="/bank/autoTenderAuthorizePage" method="post" target="_blank">
		<input type="text" name="params" value="" />
		
		<input type="submit" value="自动投标授权" />
	</form>
	<form action="/bank/tiedCardPage" method="post" target="_blank">
		<input type="text" name="params" value="" />
		
		<input type="submit" value="绑卡" />
	</form>
</body>
</html>
