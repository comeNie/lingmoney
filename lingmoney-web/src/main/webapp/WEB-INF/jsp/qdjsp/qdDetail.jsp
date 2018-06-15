
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" href="/resource/css/newYear.css">
<link rel="icon" type="image/x-icon" href="/resource/images/ico.ico">
<link type="text/css" rel="stylesheet" href="/resource/css/basic.css">
<jsp:include page="/common/head.jsp"></jsp:include>
<style type="text/css">
.detail {
	width: 100%;
	text-align: center;
	height: 930px;
	background: url('/resource/images/qdimages/detail.jpg') no-repeat center
		top;
	overflow:hidden
}
.detail .wrap{
    position:relative;
    z-index:1;
    padding-top:0;
    margin:0 auto;
    width:1024px;
    height:930px;
}
.detail .wrap a{
  display:block;
  position: absolute;
  z-index: 2;
}
.zhucea{
  top:539px;
  left:238px;
  width:257px;
  height:73px;
}
.kankan{
  top:539px;
  left:518px;
  width:257px;
  height:73px;
}
.freshman{
  top:634px;
  left:50%;
  margin-left:-74px;
  width:148px;
  height:20px;
}
.jd{
  top:824px;
  left:151px;
  width:108px;
  height:20px;
}
.app{
  top:767px;
  left:612px;
  width:98px;
  height:20px;
}
</style>
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<div class="detail">
		<div class="wrap">
		    <a href="/regist.html" class="zhucea"></a> 
			<a href="/index.html" class="kankan"></a>
			<a href="/direction.html" class="freshman"></a> 
			<a href="/jingdongzhifu.html" class="jd"></a>
			<a href="/phone.html" class="app"></a>
		</div>
	    
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>
