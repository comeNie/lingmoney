<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%@ include file="/common/huaxingDialog.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() { 
		$("#membersUserActiveli_1").find("dl").show();
		$("#mygift").addClass("hover");
		$("#membersUserActive_1").addClass("hover");
		$("#nav_member03").addClass("nav-hover");
		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");
		/*顶部*/
		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		})
		/*顶部*/

		var accountOpen = $("#accountOpen").val();
	    var bindBank = $("#bindBank").val();

	    //显示没有开通华兴E账户弹窗
	    if (accountOpen == 0 || accountOpen == 1) { 
	        showHXDialog(0);
	    } else if (bindBank == 0 || bindBank == 1) {
	        showHXDialog(1);
	    }

	    dongjiemx();
	});

	//去充值
	function goToRecharge(){
		var accountOpen = $("#accountOpen").val();
        var bindBank = $("#bindBank").val();
		
		if (accountOpen == 0 || accountOpen == 1) {
	        showHXDialog(0);
        } else if (bindBank == 0 || bindBank == 1) {
            showHXDialog(1);
        } else {
        	window.location.href="/myFinancial/accountRecharge";
        }
	}

	//去提现
    function goToWithdraw(){
    	var accountOpen = $("#accountOpen").val();
        var bindBank = $("#bindBank").val();
        
        if (accountOpen == 0 || accountOpen == 1) {
            showHXDialog(0);
        } else if (bindBank == 0 || bindBank == 1) {
            showHXDialog(1);
        } else {
        	window.location.href="/myFinancial/accountWithdraw";
        }
    }

	//冻结金额明细
    function dongjiemx(){
        $.ajax({
            url: '/myFinancial/getUserFreeingAmount',
            type: 'post',
            dataType: 'json',
            success: function(d) {
                var _html="";
                _html='<tr>'+
                  /*   '<td>'+d.obj.waitBuyAmount+'元<a href="/myFinancial/finanCialManage">详情</a></td>'+ */
                    '<td>'+d.obj.buyingAmount+'元<a href="/myFinancial/finanCialManage">详情</a></td>'+
                    '<td>'+d.obj.waitCompleteAmount+'元<a href="/myFinancial/finanCialManage">详情</a></td>'+
                    '<td>'+d.obj.withdrawalsAmount+'元<a href="/myFinancial/accountFlow">详情</a></td>'+
                    '</tr>';
                
            $('#dongjieForm').find('tbody').html(_html);
                
            },
            error: function(d) {}
        });
    }
</script>
<style type="text/css">
.mRight01 {
	margin-bottom: 0px;
}

.accountBalance {
	-moz-box-shadow: 0px 0px 0px #ffffff;
	-webkit-box-shadow: 0px 0px 0px #ffffff;
	box-shadow: 0px 0px 0px #ffffff;
	margin: 0;
	border: 1px solid #c2c2c2;
	border-top: 0;
	padding-top: 20px;
	margin-bottom: 15px;
}
.accountBalance .balanceBox{
	border-bottom: 1px solid #c2c2c2;
	padding: 0px 0 20px 0;
}

.accountBalance .balanceBox .b_left {
	width: 380px;
	background: url(/resource/images/accountBalanceImg.png) 53px 44px no-repeat;
	padding-top: 30px;
	padding-left: 20px;
	padding-right: 20px;
	border-right: 1px solid #c2c2c2;
}

.accountBalance .balanceBox .b_left .bl_1 {
	padding-left: 90px;
	padding-bottom: 20px;
}

.accountBalance .balanceBox .b_left .bl_2 {
	padding-left: 90px;
	padding-top: 10px;
	background: url(/resource/images/dongjie.png) 38px 37px no-repeat;
}
.accountBalance .balanceBox .b_left .bl_2 div{
	font-size: 30px;
	font-weight: normal;
}
.accountBalance .balanceBox .b_right {
	width: 290px;
	padding-top: 53px;
}

.accountBalance .balanceBox .b_right p {
	line-height: 40px;
	margin-bottom: 20px;
}

.accountBalance .balanceBox .b_right p span {
	font-size: 30px;
	font-weight: normal;
	color: #000000;
}

.accountBalance .balanceBox .b_right a {
	display: inline-block;
	margin-right: 38px;
}
.accountBalance .balanceBox .b_left .tip{
	top:0px;
	left: 105px;
}
.djmoney_mx{
	margin: 20px;
}
.djmoney_mx h3{
	font-style: normal;
	font-weight: normal;
	line-height: 14px;
	font-size: 14px;
	padding-left: 10px;
	border-left: 2px solid #ea5513;
	color: #999999;
	margin-bottom: 35px;
	margin-top: 35px;
}
.djmoney_mx .djbox{
	width: 100%;
	padding-bottom: 36px;
}
.djmoney_mx .djbox table{
	width: 100%;
	font-size: 14px;
	text-align: center;
	border-collapse:collapse;
}
.djmoney_mx .djbox table thead tr td{
	color: #999999;
}
.djmoney_mx .djbox table td{
	height: 40px;
	border: 1px solid #cccccc;
}
.djmoney_mx .djbox table tbody td{
	color: #000000;
}
.djmoney_mx .djbox table tbody td a{
	margin-left: 5px;
	color: #ea5513;
}
.kt_box{
	line-height: normal;
	padding-top: 133px;
	height: 292px;
	margin-bottom: 20px;
	border: 1px solid #cccccc;
	border-top: none;
}
.kt_box a{
	margin-top: 20px;
}
.mtitle, .htitle{
	border-bottom: 0;
}
</style>
</head>
<body>
	<!-- 自定义ALERT弹框 -->
	<div id="rz-box-bg"></div>


	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<!-- 用户导航开始 -->
	<jsp:include page="/common/navmember.jsp"></jsp:include>
	<!-- 用户导航结束 -->

	<div class="mainbody_member">
		<div class="box clearfix">
			<!-- 我的账户菜单开始 -->
			<jsp:include page="/common/myaccount.jsp"></jsp:include>
			<!-- 我的账户菜单结束 -->
			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">银行存管账户</div>
				</div>
				<div class="accountBalance lczj" >
					<div class="balanceBox clearfix">
						<div class="b_left">
							<div class="bl_1">
								<div id="blbtip_2" class="tip">账户总额=可用余额+冻结金额</div>
								<p>
									账户总额（元）<i id="b_Tip" onmouseover="showTip(1);"
										onmouseout="showTip(0);">?</i>
								</p>
								<div class="bl_t">${availableMoney }</div>
							</div>
							<div class="bl_2">
								<p>冻结金额（元）</p>
								<div title="当前投资冻结的资金">${frozenMoney }</div>
							</div>
						</div>
						<div class="b_right">
							<p>
								可用余额（元）<br> <span>${accountBalance }</span>
							</p>
							<a class="operation active" href="javascript:goToRecharge();">充值</a>
							<a class="operation" style="margin-bottom: 0;" href="javascript:goToWithdraw();">提现</a>
						</div>
					</div>
					<div class="djmoney_mx">
						<h3>冻结金额明细</h3>
						<div class="djbox" id="dongjieForm">
							<table>
								<thead>
									<tr>
										<!-- <td>待支付冻结</td> -->
										<td>支付中冻结</td>
										<td>待成立冻结</td>
										<td>提现中冻结</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>0.00<a href="/myFinancial/finanCialManage">详情</a></td>
										<td>0.00<a href="/myFinancial/finanCialManage">详情</a></td>
										<td>0.00<a href="/myFinancial/finanCialManage">详情</a></td>
										<td>0.00<a href="/myFinancial/accountFlow">详情</a></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="sweetTip">
							<p>温馨提示：</p>
							<p>1、冻结金额是指您在购买产品或申请提现时，华兴银行将资金锁定，操作成功后立即解冻。<br>
							2、如遇到输错密码、网络不通、操作超时、页面关闭等问题操作不成功时，华兴银行将资金锁定，预计30分钟内资金回到可用余额中，具体时间以银行为准。
							</p>
						</div>
					</div>
					
				</div>
				<!-- <div class="kt_box" style="display: none;">
						<p>您还未开通华兴E账户，请立即前往开通华兴E账户</p>
						<a class="operation active" href="/myAccount/bindBankCard?kType=1">去开通华兴E账户</a>
				</div> -->
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	
	<!-- 提示开通华兴E账户弹框 -->
    <input type="hidden" id="accountOpen" value="${CURRENT_USER.certification }">
    <input type="hidden" id="bindBank" value="${CURRENT_USER.bank }">

	<script>
		/*获取url参数*/
		function GetQueryString(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return unescape(r[2]);
			return null;
		}
		
		//账户总额提示
		function showTip(types) {
			if (types == 1)
				$("#blbtip_1,#blbtip_2").show();
			else if (types == 0)
				$("#blbtip_1,#blbtip_2").hide();
		}
	</script>


</body>
</html>