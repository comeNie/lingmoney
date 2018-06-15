<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>领钱儿后台管理系统</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
  	<link href="<%=request.getContextPath()%>/resource/css/default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/main.js"></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/resource/js/outlook2.js'> </script>
	<script>
		
	</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		    <img src="/resource/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(/resource/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana,  微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">欢迎光临&nbsp;<span id="bgclock"></span>&nbsp;<a  href="#" id="editpass">修改密码</a> <a  href="javascript:void(0)" id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; "><img src="/resource/images/blocks.gif" width="20" height="20" align="absmiddle" />铭睿博通</span>
    </div>
    
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">By 信息技术部 Email:mrbt_luozhipeng@126.com</div>
    </div>
    <div region="west" split="true" title="导航菜单" style="width:180px;" id="west">
		<div class="easyui-accordion" id="nav_accordion" fit="true" border="false">
			
	    </div>
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false">
			<div title="欢迎使用" style="padding:20px;overflow:hidden;" id="home">
				<h1>Welcome to jQuery UI!</h1>
			</div>
		</div>
    </div>
    
    
     
    <!--修改密码窗口-->
    <div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" closed="true"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="txtNewPass" type="Password" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="txtRePass" type="Password" class="txt01" /></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" > 确定</a> 
                <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="closeLogin()">取消</a>
            </div>
        </div>
    </div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
</body>
</html>