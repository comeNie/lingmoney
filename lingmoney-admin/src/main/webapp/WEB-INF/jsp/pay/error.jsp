<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
	<html>
		<body>
			操作失败，原因为：${!empty msg?msg:'未知原因' }
		</body>
	</html>