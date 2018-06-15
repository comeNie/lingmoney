<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<title>Uploadify</title>
<link href="JS/jquery.uploadify-v2.1.0/example/css/default.css"
rel="stylesheet" type="text/css" />
<link href="JS/jquery.uploadify-v2.1.0/uploadify.css"
rel="stylesheet" type="text/css" />

<script type="text/javascript"
src="JS/jquery.uploadify-v2.1.0/jquery-1.3.2.min.js"></script>

<script type="text/javascript"
src="JS/jquery.uploadify-v2.1.0/swfobject.js"></script>

<script type="text/javascript"
src="JS/jquery.uploadify-v2.1.0/jquery.uploadify.v2.1.0.min.js"></script>

<script type="text/javascript">
$(document).ready(function()
{
$("#uploadify").uploadify({
'uploader': 'JS/jquery.uploadify-v2.1.0/uploadify.swf',
'script': 'UploadHandler.ashx',
'cancelImg': 'JS/jquery.uploadify-v2.1.0/cancel.png',
'folder': 'UploadFile',
'queueID': 'fileQueue',
'auto': false,
'multi': true
});
});
</script>

</head>
<body>
<div id="fileQueue"></div>
<input type="file" name="uploadify" id="uploadify" />
<p>
<a href="javascript:$('#uploadify').uploadifyUpload()">上传</a>|
<a href="javascript:$('#uploadify').uploadifyClearQueue()">取消上传</a>
</p>
</body>
</html> 