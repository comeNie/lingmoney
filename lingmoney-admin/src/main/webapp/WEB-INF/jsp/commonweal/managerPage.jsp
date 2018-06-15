<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公益活动维护</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="/resource/js/commonweal.js"></script>
<script type="text/javascript" src="/resource/js/DatePattern.js"></script>

<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
</head>
<body>
    <div class="easyui-layout" style="width: 700px;" fit="true">
        <div class="easyui-panel" data-options="region:'north',title:'设置用户获取爱心值数量',iconCls:'icon-book'"
            style="padding: 5px; height: 110px">
            <span>文本框中显示为当前设置的值，如果要修改，填写要修改的值，点击设置按钮</span>
            <form id="setLoveNumber">
                <input id="loveNumber" name="loveNumber">
            </form>
            <a href="javascript:setLoveNUmber()" class="easyui-linkbutton" id="loveNumberButton" iconcls="icon-edit">设置</a>
        </div>
        <div id="tb" data-options="region:'center'" style="padding: 5px; height: auto">
            <table id="conmonweal_list"></table>
        </div>
    </div>
    <div id="addConmonewalWin" class="easyui-dialog" style="width: 800px; height: 520px; padding: 10px 20px" closed="true"  modal="true"
        data-options="title:'添加公益信息',iconCls: 'icon-add',
            buttons: [{ text:'确定', iconCls:'icon-ok', handler:function(){
                        saveAndUpdate();
                    } },{ text:'关闭', iconCls:'icon-cancel', handler:function(){
                        $('#addConmonewalWin').dialog('close');
                    }
                }]">
         <form id="addConmonewalForm" method="post" novalidate="novalidate" enctype="multipart/form-data">
            <input type="hidden" name="id">
            <table class="view">
                <tr>
                    <th>名称</th><td><input name="pbaName"></td>
                    <th>展示图片</th><td><input class="easyui-filebox" name="path1"></td>
                </tr>
                <tr>
                    <th>礼赞上限</th><td><input name="maxPraise"></td>
                    <th>已礼赞</th><td><input name="sumPraise" value = 0></td></tr>
                <tr>
                    <th>显示开始时间</th><td><input class="easyui-datetimebox" name="stateTime"></td>
                    <th>显示结束时间</th><td><input class="easyui-datetimebox" name="endTime"></td>
                <tr>
                    <th>排序</th><td><input name="sort"></td>
                    <th>简介</th><td><input name="pbaDesc"></td>
               </tr>
            </table>
            <table>
               <tr>
                    <th>详情PC</th>
               </tr>
               <tr>
                    <td>
                    <textarea name="pbaHtmlWeb" id="pbaHtmlWeb"
                            style="width: 700px; height: 400px;"></textarea></td>
               </tr>
               <tr>
                    <th>详情MOBILE</th>
               </tr>
               <tr>
                    <td>
                    <textarea name="pbaHtmlMobile" id="pbaHtmlMobile"
                            style="width: 700px; height: 400px;"></textarea></td>
               </tr>
            </table>
         </form>
    </div>
</body>
</html>