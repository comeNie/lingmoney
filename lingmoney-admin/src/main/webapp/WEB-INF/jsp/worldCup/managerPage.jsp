<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公益活动维护</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="/resource/js/worldCup.js"></script>
<script type="text/javascript" src="/resource/js/DatePattern.js"></script>

<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
</head>
<body>
    <div class="easyui-layout" style="width: 700px;" fit="true">
        <div id="tb" data-options="region:'center'" style="padding: 5px; height: auto">
            <table id="conmonweal_list"></table>
        </div>
    </div>
    <div id="addConmonewalWin" class="easyui-dialog" style="width: 400px; height: 200px; padding: 10px 20px" closed="true"  modal="true"
        data-options="title:'添加公益信息',iconCls: 'icon-add',
            buttons: [{ text:'确定', iconCls:'icon-ok', handler:function(){
                        saveAndUpdate();
                    } },{ text:'关闭', iconCls:'icon-cancel', handler:function(){
                        $('#addConmonewalWin').dialog('close');
                    }
                }]">
         <form id="addConmonewalForm" method="post" novalidate="novalidate" enctype="multipart/form-data">
            <input type="hidden" name="id">
            <table>
                <tr>
                    <th>队伍LEFT</th><td><input name="leftTeam" style="width:80px;"></td>
                    <th>得分</th><td><input name="leftScore" style="width:80px;"></td>
                </tr>
                <tr>
                    <th>队伍RIGHT</th><td><input name="rightTeam" style="width:80px;"></td>
                    <th>得分</th><td><input name="rightScore" style="width:80px;"></td>
                </tr>
            </table>
         </form>
    </div>
</body>
</html>