<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>
<h3>债权转让申请（异步）</h3>
<form action="/bank/transferDebtPage" method="get">
	原投标流水：<input type="text" name="oldreqseqno"><br>
	原标的编号：<input type="text" name="oldreqnumber"><br>
	原标的名称：<input type="text" name="oldreqname"><br>
	转让人名称：<input type="text" name="custname"><br>
	剩余金额：<input type="text" name="amount"><br>
	预计剩余收益：<input type="text" name="preincome"><br>
	备注：<input type="text" name="remark"><br>
	<input type="submit" value="提交">
</form>

<hr>
<h3>债权转让申请结果查询（错误信息：无此交易流水）</h3>
<form action="/bank/transferDebtAssignmentSearch" method="post">
	原投标流水：<input type="text" name="oldreqseqno"><br>
	<input type="submit" value="提交">
</form>

<hr>
<h3>自动投标授权（异步）</h3>
<form action="/bank/autoTenderAuthorizePage" method="get">
	备注：<input type="text" name="remark"><br>
	<input type="submit" value="提交">
</form>

<hr>
<h3>自动投标授权结果查询（错误信息：无此交易流水）</h3>
<form action="/bank/autoTenderAuthorizeSearch" method="post">
	原投标流水：<input type="text" name="oldreqseqno"><br>
	<input type="submit" value="提交">
</form>

<hr>
<h3>自动投标授权撤销（错误信息：动态密码不存在）</h3>
<form action="/bank/autoTenderAuthorizeCancel" method="post">
	动态密码唯一标识：<input type="text" name="otpseqno"><br>
	动态密码：<input type="text" name="otpno"><br>
	备注：<input type="text" name="remark"><br>
	<input type="submit" value="提交">
</form>

<hr>
<h3>投标优惠返回</h3>
<form action="/bank/tenderReturn" method="post">
	借款编号：<input type="text" name="loanno"><br>
	还款账户户名：<input type="text" name="bwacname"><br>
	还款账号：<input type="text" name="bwacno"><br>
	优惠总金额：<input type="text" name="amount"><br>
	优惠总笔数：<input type="text" name="totalnum"><br>
	<input type="submit" value="提交">
</form>

</body>
</html>