<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		$(".subitem").click(function() {
			$(".mLeft li").find("a").removeClass("hover");
			$(this).addClass("hover");
			$(".mLeft dl").hide();
			$(this).parent("li").find("dl").show(300);
		})
	})
</script>

<!-- 我的账户菜单开始 -->
<div class="mLeft">
	<h2>我的账户</h2>
	<ul>
		<li><a href="/myAccount/bindBankCard" id="membersUserRealname">银行卡</a></li>
		<li id="membersUserActiveli_1"><a id="membersUserActive_1" href="/myAccount/depository" class="subitem">银行存管账户</a>
			<dl>
				<dd>
					<a href="/myFinancial/accountRecharge" id="accountRechargeTab">充值</a>
				</dd>
				<dd>
					<a href="/myFinancial/accountWithdraw" id="accountWithdrawTab">提现</a>
				</dd>
			</dl>
		</li>
		<li><a href="/myAccount/myInfo" id="membersUser">我的资料</a></li>
		<li><a href="/usersMessage/list" id="membersUserNews">消息中心</a></li>
		<li id="membersUserActiveli"><a id="membersUserActive" href="javascript:void(0)" class="subitem">我的活动</a>
			<dl>
				<dd>
					<a href="/myAccount/selectGiftDetail" id="mygift">我的礼品</a>
				</dd>
				<dd>
					<a href="/myAccount/lingbaoRecord" id="lingbao">我的领宝</a>
				</dd>
				<dd>
					<a href="/lingbaoExchange/exchangeRecord" id="exchange">领宝兑换</a>
				</dd>
			</dl>
		</li>
		<li><a href="/myAccount/changePassword" id="membersUserCode">修改密码</a></li>
	</ul>
</div>
<!-- 我的账户菜单结束 -->
