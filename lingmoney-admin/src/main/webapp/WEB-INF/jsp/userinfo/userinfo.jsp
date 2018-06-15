<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户详情管理</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
<style type="text/css">
fieldset {
	margin-bottom: 10px;
	color: #333;
	border: #06c dashed 1px;
}

legend {
	color: #06c;
	font-weight: 800;
	background: #fff;
}
#dataGrid tr td{height:40px;text-align: center;
   
    border-right: solid 1px #fff;}
#tbody tr td    {white-space:normal; word-break:break-all; }
</style>
<script type="text/javascript">
	var dataGrid;
// 	$(function() {
// 		searchList();		
// 	});
	
	function searchList(){
		$.ajax({
			url : '/rest/userInfo/findUserInfoByParams',
			type : 'POST',
			data : {
				'name' : $("#name").val(),
				'telephone' : $("#telephone").val(),
				'uid' : $("#uid").val()
			},
			success: function(data) {
				if (data.code == 200) {
					var obj = data.obj;
					var html = "";
					html += "<tr>";
					html += "<td>"+obj.name+"</td>";
					html += "<td>"+obj.telephone+"</td>";
					html += "<td>"+obj.last_login_date+"</td>";
					html += "<td>"+obj.register_date+"</td>";
					html += "<td>"+obj.referral_code+"</td>";
					html += "<td>"+obj.referral_id+"</td>";
					html += "<td>"+obj.balance+"</td>";
					html += "<td>"+obj.frozen_money+"</td>";
					html += "<td>"+obj.bank_name+"</td>";
					html += "<td>"+obj.cardNumber+"</td>";
					html += "<td>"+obj.ACCTBAL+"</td>";
					html += "<td><a href='#' onclick='searchResult("+obj.id+");'>查看流水</a></td>";
					html += "</tr>";
					$("#tbody").empty();
					$("#tbody").append(html);
				} else {
					$.messager.alert('错误', data.msg, 'error');
					return false;
				}
			}
		});
		
	}
	
	function searchResult(id) {
		$.ajax({
			url : '/rest/userInfo/findAccountFlowByAId',
			type : 'POST',
			data : {
				'aid' : id
			},
			success: function(data) {
				if (data.code != 200) {
					$.messager.alert('错误', data.msg, 'error');
					return false;
				} else {
					var result = data.rows;
			    	if (result.length > 0) {
			    		var html = "";
			    		for (var i = 0; i < result.length; i++) {
			    			var obj = result[i];
			    			var operateTime = formatDate(new Date(obj.operateTime));
			    			html += "<tr>";
    						html += "<td>"+operateTime+"</td>";
    						html += "<td>"+obj.money+"</td>";
    						var type = "";
   							if (obj.type == 0){
   								type =  "充值";
   							} else if (obj.type == 1) {
   								type =  "提现";
   							} else if (obj.type == 2) {
   								type =  "理财";
   							} else if (obj.type == 3) {
   								type =  "赎回";
   							} else if (obj.type == 4) {
   								type =  "奖励";
   							}
    						html += "<td>"+type+"</td>";
    						var status = "";
   							if (obj.status == 0){
   								status =  "处理中";
   							} else if (obj.status == 1) {
   								status =  "已完成";
   							} else if (obj.status == 2) {
   								status =  "处理失败";
   							} else if (obj.status == 3) {
   								status =  "撤标成功";
   							} else if (obj.status == 4) {
   								status =  "流标";
   							}
    						html += "<td>"+status+"</td>";
    						html += "<td>"+obj.cardPan+"</td>";
    						html += "</tr>";

			    		}
   						$("#tbody1").empty();
   						$("#tbody1").append(html);
			    	}
				}
			}
		});
		
		$("#DivAddInfo").dialog('open');
		$("#DivAddInfo").dialog('setTitle','操作流水记录');
	}
	
	function formatDate(now) {
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		var hour = now.getHours();
		var minute = now.getMinutes();
		var second = now.getSeconds();
		/* return year+"-"+month+"-"+date;  */
		return year + "/" + getzf(month) + "/" + getzf(date) + "/" + getzf(hour) + ":" + getzf(minute) + ":" + getzf(second);
	}
	
	function getzf(num) {
		if (parseInt(num) < 10) {
			num = '0' + num;
		}
		return num;
	}

</script>
 
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false"
		style="height: auto; overflow: hidden; background-color: #fff">
		<form id="searchForm">
			<table>
				<tr>
					<td>姓名：</td>
					<td><input class="easyui-textbox" name="name"
						id="name" style="width: 200px"
						data-options="prompt:'请输入用户姓名',
						            icons: [{
										iconCls:'icon-remove',
										handler: function(e){
											$('#name').textbox('setValue', '');
										}
									}]" />
					</td>
					<td>手机号：</td>
					<td><input class="easyui-textbox" name="telephone"
						id="telephone" style="width: 200px"
						data-options="prompt:'请输入用户手机号',
						            icons: [{
										iconCls:'icon-remove',
										handler: function(e){
											$('#telephone').textbox('setValue', '');
										}
									}]" />
					</td>
					<td>uid：</td>
					<td><input class="easyui-textbox" name="uid"
						id="uid" style="width: 200px"
						data-options="prompt:'请输入用户id',
						            icons: [{
										iconCls:'icon-remove',
										handler: function(e){
											$('#uid').textbox('setValue', '');
										}
									}]" />
					</td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" 
				data-options="iconCls:'icon-search'" onclick="searchList();">查询列表</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false"
		style="width: 550px; height: 350px; overflow: hidden;">
		<table id="dataGrid" class="" border="0" cellspacing="0" cellpadding="0" width="100%" align="center" bordercolor="white"  style="table-layout:fixed;overflow:scroll;">  
            <thead>  
                <tr class="" bgcolor= "#95B8E7">  
                <td>姓名</td>  
                <td>手机号</td>  
                <td>最后登录时间</td>  
                <td>注册时间</td>  
                <td>推荐码</td>  
                <td>推荐人</td>  
                <td>账户余额</td>  
                <td>冻结金额</td>  
                <td>绑卡银行</td>  
                <td>银行卡号</td>  
                <td>华兴余额</td>  
                <td>操作</td>  
            </tr>  
            </thead>
              
            <tbody id="tbody">  
            </tbody>  
        </table>
	</div>
	
	<!-- 添加详情弹出层 -->
	<div id="DivAddInfo" class="easyui-dialog" style="width: 70%; height: 80%; padding: 10px 20px" closed="true" resizable="true" modal="true"
		data-options="buttons: '#dlg-buttons'">
		<form id="ffAddInfo" method="post" novalidate="novalidate" enctype="multipart/form-data">
			<table id="" class="" border="0" cellspacing="0" cellpadding="0" width="100%" align="center" bordercolor="white"  style="table-layout:fixed;overflow:scroll;">  
                <thead>  
                    <tr class="" bgcolor= "#95B8E7">  
                    <td>操作时间</td>  
                    <td>操作金额</td>  
                    <td>类型</td>  
                    <td>状态</td>  
                    <td>兑付卡号</td>  
                </tr>  
                </thead>
                  
                <tbody id="tbody1">  
                </tbody>  
            </table>  
		</form>
	</div>	
</body>

</html>

