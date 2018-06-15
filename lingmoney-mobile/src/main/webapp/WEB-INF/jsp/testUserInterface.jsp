<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="../../static/jQuery/jquery-1.8.0.js" charset="utf-8"></script>

</head>
<body>
	<div>此页用于测试喵~</div>
	
	<form action="/users/modifyAvatar" method="post" enctype="multipart/form-data">
		图片：<input name="imgFile" id="imgFile" type="file"></input><br/>
		
		<input  name="token" value="0a48e78f994a45b6b6b0c058ee813b37838fc476b1d643e8ba21aede48e3a8b7"></input>
		<input type="submit" value="提交"></input><br/>
	</form>
	
	<a href="/user/personalCenter">click it</a>
	 <textarea  rows="100" cols="800" id="display"></textarea>  
	<script type="text/javascript">
	function bianli(obj){
		var props="";
		// 开始遍历
  	  for(var p in obj){ // 方法
      	  if (typeof (obj[p]) == "object"&&obj[p]!=null){ 
      		 props +=p+"="+ bianli(obj[p]) +" \n ";
      	  } else { 
      		 if(obj[p]!=null){
      			props +=p+"="+ obj[p] +" \n "
      		 }
      	  }
  	  }
  	  return props;
	}
	</script>
	
	
		
</body>
</html>