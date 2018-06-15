<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<%@ include file="/common/top.jsp"%>
<%@ include file="/common/huaxingDialog.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>
<link rel="stylesheet" href="/resource/css/bank/accountRecharge.css" />
<script type="text/javascript" src="/resource/js/bank/accountRecharge.js"></script>
<script type="text/javascript"
	src="/resource/js/clipboard/clipboard.min.js"></script>
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
});
</script>
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<!-- 用户导航开始 -->
	<jsp:include page="/common/navmember.jsp"></jsp:include>
	<!-- 用户导航结束 -->

	<div id="rz-box-bg"></div>

	<div class="mainbody_member">
		<div class="box clearfix">
			<!-- 我的账户菜单开始 -->
			<jsp:include page="/common/myaccount.jsp"></jsp:include>
			<!-- 我的账户菜单结束 -->

			<!-- 自定义弹框 start -->
			<div class="rz-box-con" id="div_customer">
				<div class="rz-title">
					<h2></h2>
					<a href="javascript:void(0)" class="rz-close"> <img
						src="/resource/images/img_rz-close.png" /></a>
				</div>
				<p style="padding-top: 30px; font-size: 16px; text-align: center">

				</p>
				<p style="padding-top: 20px;">
					<a href="javascript:void(0)" class="rz-button "></a> <a
						href="javascript:void(0)" class="rz-button reset"></a>
				</p>
			</div>
			<!-- 自定义弹框 end -->

			<!-- 自定义弹框提示信息 start -->
			<div class="rz-box-con" id="div_customer_tip" style="width: 400px;">
				<div class="rz-title">
					<h2></h2>
					<a href="javascript:void(0)" class="rz-close"> <img
						src="/resource/images/img_rz-close.png" /></a>
				</div>
				<p
					style="padding-top: 30px; font-size: 20px; text-align: center; color: #ea5513">
				</p>
				<p style="padding-top: 20px;">
					<a href="javascript:void(0)" class="rz-button">确定</a>
				</p>
			</div>
			<!-- 自定义弹框提示信息 end -->

			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">充值</div>
					
					<div class="recharge-tabs">
					<span>推荐</span>
						<ul>
							<li class="tabs-border">实时到账</li>
							<li>T+1到账</li>
						</ul>
					</div>
					 
					 <!-- 实时到账 -->
					 <div class="recharge-tabs-box" >
					 
					 
					    <div class="recharge-n-nox">
					    	<div class="recharge-nox-left">
					    		<p class="recharge-nox-left-tip">已绑定银行卡</p>
					    		<ul>
					    			<li><div id="bankname"><img src="" class="banklogo"></div><p >储蓄卡</p></li>
					    			<li class="recharge-nox-leftka cardno" style="text-indent: 12px;"></li>
					    		</ul>
					    	</div>
					    	<div class="recharge-nox-midd">
                                <p>通过<span class="bankname"></span>手机、网银<br>转账至华兴银行存管账户</p>
                                <p style="text-align: center;"><img src="/resource/images/youjiant.jpg"></p>
                            </div>
                            <div class="recharge-nox-right">
                            	<p class="recharge-nox-left-tip">领钱儿|广东华兴银行</p>
					    		<ul>
					    			<li class="recharge-noxlink"><p>收款账户：${CURRENT_USER.acName }</p> <a id="copyCard" href="javascript:void(0);">复制卡号</a></li>
					    			<li class="recharge-nox-leftka">收款卡号：<span id="cz_cardno">${CURRENT_USER.acNo }</span></li>
					    		</ul>
                            </div>
					    </div>
					
					    <div class="sweetTip">
								<p>温馨提示：</p>
								<p>
									1、查看<a href="/hxbankOpertion#pc7" style="color:#5f87f7">充值操作指引>></a><br>
			2、转账时收款银行选择“广东华兴银行”，如无，选择“城市商业银行-广东华兴银行股份有限公司”<br>
								</p>
							</div>

	           
                   
                   
                   </div>
					 <!-- 实时到账 end--> 
					 <!-- T+1到账 -->
					<div class="recharge-tabs-box" style="display:none">
						<form action="" target="_blank" method="post" id="accountRecharge">
						<input type="hidden" id="requestData" name="RequestData" /> <input
							type="hidden" id="transCode" name="transCode" />
							
						<table class="rechargeTab">
							<%-- <tr>
								<th class="td_1">可用余额：</th>
								<td class="td_2"><span> <fmt:formatNumber
											pattern="#,##0.00#" value="${balance==null?0:balance}" />
								</span>元</td>
							</tr> --%>
							
							<tr>
						
								<th class="td_1">充值金额：</th>
								<td class="td_2"><input type="text" step="0.01"
									placeholder="请输入充值金额" id="money"><span id="sp"
									style="display: none"></span></td>
							</tr>
								<tr>
								<td></td>
								<td><p>输入金额，跳转至华兴页面，选择<span style="color:#fa0202">【银行卡】</span>充值</p></td>
								</tr>
							<tr>
								<td></td>
								<td><a href="javascript:void(0)" class="rechargeBtn">充值</a></td>
							</tr>
						</table>
					</form>
					<input type="hidden" id="balance" value="${balance}">
					</div>
                    <!-- T+1到账 end-->
					
			
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(function(){ 
		$('.recharge-tabs li').click(function(){
			  $(this).addClass('tabs-border').siblings().removeClass('tabs-border');
			  $('.recharge-tabs-box').eq($(this).index()).show().siblings('.recharge-tabs-box').hide();
			
		})
		//绑卡信息
		
		setTimeout(function(){
		$.ajax({
			url :'/bank/queryBindCardInfo',
			type : 'post',
			data : {
			
			},
			
			dataType : 'json',
			success : function(data) {
				var html="";
				
				if(data.code==200){
					if(data.obj.statusid=='预绑定'){
						$('.recharge-nox-left').hide();
						$('.recharge-nox-midd').hide();
						
					}else{
						if(data.obj.logo==null){
							$('#bankname').html(data.obj.bankname).css({'color':'#333','font-size':'12px','line-height':'22px'})
						}else{
							$('.banklogo').attr('src',data.obj.logo)
						}
						$('.bankname').html(data.obj.bankname)
						$('.cardno').html(data.obj.cardno)
					}
					
					
				}
				
			},
			error : function(d) {
			}
		});
		
		$.ajax({
			url :'/bank/userHxAccout',
			type : 'post',
			data : {
			
			},
			dataType : 'json',
			success : function(data) {
				if(data.code==200){
					$('.acName').html(data.obj.acName);
					
				}
				
			},
			error : function(d) {
			}
		});
	})	
	},100)
	var c = document.getElementById("cz_cardno");
	var s;
	if (c != null) {
		s = c.innerHTML;
	} else {
		s = "";
	}
	var clipboard = new Clipboard('#copyCard', {
		text : function() {
			return s;
		}
	});

	clipboard.on('success', function(e) {
		var timeout;
		$('#copyCard').text('复制成功');
		timeout = setTimeout(function() {
			$('#copyCard').text('复制卡号');
			clearTimeout(timeout);
		}, 1000)

	});

	clipboard.on('error', function(e) {
		console.log(e);
	});
	
	
	
	
	

	</script>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	
	<!-- 提示开通华兴E账户弹框 -->
    <input type="hidden" id="accountOpen" value="${CURRENT_USER.certification }">
    <input type="hidden" id="bindBank" value="${CURRENT_USER.bank }">
    
</body>
</html>