<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width" />
    <title>功能菜单</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/base/menu.js"></script>
   
    </head>
<body>
    <div class="easyui-layout" style="width:700px;" fit="true">
        <div data-options="region:'west',split:true,title:'菜单管理',iconCls:'icon-book'" style="width: 250px; padding: 1px;">
            <div style="padding: 1px; border: 1px solid #ddd;">                
                <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" id="A4" onclick="reloadTree()">刷新</a>
                <a id="expandAllBtn" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-expand'" id="A5" onclick="$('#menuTree').tree('expandAll');">展开</a>
                <a id="collapseAllBtn" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-collapse'" id="A6" onclick="$('#menuTree').tree('collapseAll');">折叠</a>
            </div>
             <div class="easyui-panel" style="padding:15px;">
                 <ul class="easyui-tree" id="menuTree" data-options="url:'/rest/base/menu/listTree?r='+Math.random(),method:'get',animate:true,lines:true,
	                 onClick: function(node){
	                   treeClick(node);
	                },
	                onBeforeLoad:function(){
	                	showLoading();
	                },
	                onLoadSuccess:function(){
	                	closeLoading();
	                }">
                </ul>
            </div>
        </div>
        <div id="tb"  data-options="region:'center',title:'',iconCls:'icon-book'" style="padding:5px;height:auto" fit="true">       
              <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/base/menu/listByPid',method:'post'">            
            	 <thead>
			            <tr>
			            	<th data-options="field:'id',width:50">id</th>
			            	<th data-options="field:'text',width:200">菜单名称</th>
			            	<th data-options="field:'status',width:200" formatter="formatStatus">状态</th>
			            </tr>
			        </thead>
            </table>
        </div>
    </div>
     <!--------------------------添加信息的弹出层---------------------------->
    <div id="DivAdd" class="easyui-dialog" style="width:780px;height:230px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="iconCls: 'icon-add',buttons: '#dlg-buttons',title:'添加菜单'">
        <form id="ffAdd" method="post" novalidate="novalidate">
        		<input type="hidden" name="pid" id="apid"/>
                <table id="tblAdd" class="view">
                    <tr>
                        <th>
                            <label for="PID">父菜单：</label>
                        </th>
                        <td>
                        	<input class="easyui-textbox" id="apname"  name="pname"/>
                        </td>
                         <th>
                            <label for="Seq">排序：</label>
                        </th>
                        <td>
                            <input class="easyui-validatebox" type="text" id="aseq" name="seq" />
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <label for="Name">显示名称：</label>
                        </th>
                        <td>
                            <input class="easyui-validatebox" type="text" id="atext" name="text" data-options="required:true,validType:'length[1,50]'" />
                        </td>
                        <th>
                            <label for="Icon">Web界面的菜单图标：</label>
                        </th>
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        <select id="aicon" name="icon"  onchange="javascript:changeIcon(0,this)">
                                            <option>icon-blank</option>
                                            <option>icon-nav</option>
                                            <option>icon-add</option>
                                            <option>icon-edit</option>
                                            <option>icon-remove</option>
                                            <option>icon-save</option>
                                            <option>icon-cut</option>
                                            <option>icon-ok</option>
                                            <option>icon-no</option>
                                            <option>icon-cancel</option>
                                            <option>icon-reload</option>
                                            <option>icon-search</option>
                                            <option>icon-print</option>
                                            <option>icon-help</option>
                                            <option>icon-undo</option>
                                            <option>icon-redo</option>
                                            <option>icon-back</option>
                                            <option>icon-sum</option>
                                            <option>icon-tip</option>
                                            <option>icon-mini-add</option>
                                            <option>icon-mini-edit</option>
                                            <option>icon-mini-refresh</option>
                                            <option>icon-excel</option>
                                            <option>icon-word</option>
                                            <option>icon-organ</option>
                                            <option>icon-lock</option>
                                            <option>icon-alarm</option> 
                                            <option>icon-view</option>
                                            <option>icon-pie</option>
                                            <option>icon-bar</option>
                                            <option>icon-curve</option>
                                            <option>icon-computer</option>
                                            <option>icon-house</option>
                                            <option>icon-key</option>
                                            <option>icon-photo</option>
                                            <option>icon-user</option>
                                            <option>icon-group</option>
                                            <option>icon-group-key</option>
                                            <option>icon-telephone</option>
                                            <option>icon-phone</option>
                                            <option>icon-table</option>
                                            <option>icon-book</option>
                                            <option>icon-comment</option>
                                            <option>icon-date'</option>
                                            <option>icon-email</option>
                                            <option>icon-first</option>
                                            <option>icon-last</option>
                                            <option>icon-next</option>
                                            <option>icon-previous</option>
                                            <option>icon-stop</option>
                                            <option>icon-setting</option>
                                        </select>
                                    </td>
                                    <td><div id="imgIcon0" class="icon-blank" style="width:24px;height:24px"/></td>
                                </tr>
                            </table>  
                        </td>
                    </tr>
                    <tr>
 						<th>
                            <label for="Url">Web界面Url地址：</label>
                        </th>
                        <td>
                            <input class="easyui-validatebox" type="text" id="aurl" name="url" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" onclick="save()" class="easyui-linkbutton"  iconcls="icon-ok" >确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
                        </td>
                    </tr>
                </table>
        </form>
    </div>
    
      <!--------------------------编辑信息的弹出层---------------------------->
    <div id="DivEdit" class="easyui-dialog" style="width:780px;height:230px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="iconCls: 'icon-edit',title:'编辑菜单',buttons: '#dlg-buttons'">
        <form id="ffEdit" method="post" novalidate="novalidate">
        	<input type="hidden" name="id" id="eid"/>
                <table id="tblEdit" class="view">
                    <tr>
                        <th>
                            <label for="PID1">父菜单：</label>
                        </th>
                        <td>
                            <select class="easyui-combotree" style="width:200px" id="epid" name="pid" data-options="url:'/rest/base/menu/listTree?r='+Math.random(),method:'get',animate:true,lines:true">
                        	</select>
                        </td>
                         <th>
                            <label for="Seq1">排序：</label>
                        </th>
                        <td>
                            <input class="easyui-validatebox" type="text" id="eseq" name="seq" />
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <label for="Name1">显示名称：</label>
                        </th>
                        <td>
                            <input class="easyui-validatebox" type="text" id="etext" name="text" data-options="required:true,validType:'length[1,50]'"/>
                        </td>
 						<th>
                            <label for="Icon1">Web界面的菜单图标：</label>
                        </th>
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        <select id="eicon" name="icon" onchange="javascript:changeIcon(1,this)">
                                            <option>icon-blank</option>
                                            <option>icon-nav</option>
                                            <option>icon-add</option>
                                            <option>icon-edit</option>
                                            <option>icon-remove</option>
                                            <option>icon-save</option>
                                            <option>icon-cut</option>
                                            <option>icon-ok</option>
                                            <option>icon-no</option>
                                            <option>icon-cancel</option>
                                            <option>icon-reload</option>
                                            <option>icon-search</option>
                                            <option>icon-print</option>
                                            <option>icon-help</option>
                                            <option>icon-undo</option>
                                            <option>icon-redo</option>
                                            <option>icon-back</option>
                                            <option>icon-sum</option>
                                            <option>icon-tip</option>
                                            <option>icon-mini-add</option>
                                            <option>icon-mini-edit</option>
                                            <option>icon-mini-refresh</option>
                                            <option>icon-excel</option>
                                            <option>icon-word</option>
                                            <option>icon-organ</option>
                                            <option>icon-lock</option>
                                            <option>icon-alarm</option> 
                                            <option>icon-view</option>
                                            <option>icon-pie</option>
                                            <option>icon-bar</option>
                                            <option>icon-curve</option>
                                            <option>icon-computer</option>
                                            <option>icon-house</option>
                                            <option>icon-key</option>
                                            <option>icon-photo</option>
                                            <option>icon-user</option>
                                            <option>icon-group</option>
                                            <option>icon-group-key</option>
                                            <option>icon-telephone</option>
                                            <option>icon-phone</option>
                                            <option>icon-table</option>
                                            <option>icon-book</option>
                                            <option>icon-comment</option>
                                            <option>icon-date'</option>
                                            <option>icon-email</option>
                                            <option>icon-first</option>
                                            <option>icon-last</option>
                                            <option>icon-next</option>
                                            <option>icon-previous</option>
                                            <option>icon-stop</option>
                                            <option>icon-setting</option>
                                        </select>
                                    </td>
                                    <td><div id="imgIcon1" class="icon-blank" style="width:24px;height:24px"/></td>
                                </tr>
                            </table>  
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <label for="Url1">Web界面Url地址：</label>
                        </th>
                        <td>
                            <input class="easyui-validatebox" type="text" id="eurl" name="url" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" style="text-align:right; padding-top:10px">
                            <input type="hidden" id="ID1" name="ID" />
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="update()" iconcls="icon-ok" >确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
                        </td>
                    </tr>
                </table>
        </form>
    </div>
    
</body>
</html>
