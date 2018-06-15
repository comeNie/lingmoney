<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {
		checkHxAccount();

		$(".hx_close").click(function() {
			$("#rz-box-bg").hide();
			$("#hxAccountDialog").hide();
		});
	})
</script>
<input type="hidden" id="accountOpen"
	value="${CURRENT_USER.certification }">
<input type="hidden" id="bindBank" value="${CURRENT_USER.bank }">
<div id="hxAccountDialog" class="TipHuaXing" style="display: none;">
	<div class="th_box">
		<a href="javascript:void(0);" class="hx_close"></a>
		<p></p>
		<a class="ckbtn" href="/myAccount/bindBankCard"></a>
	</div>
</div>


