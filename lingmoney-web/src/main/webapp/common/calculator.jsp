<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function offsetDivC(s) {
		height = $(s).height() + 50;
		width = $(s).width();
		$(s).css("margin-top", -height / 2);
		$(s).css("margin-left", -width / 2);
	}
</script>

<div class="calculator">
	<a class="close" href="javascript:void(0)">
		<img src="/resource/images/img_close.png" />
	</a>
	<div style="height: 75px">
		<img src="/resource/images/img_c.jpg" />
	</div>
	<div style="background: url('/resource/images/bg_c.jpg') no-repeat right top #fff; padding-top: 84px; width: 857px; border: 1px solid #eee; border-top: 0">
		<div class="clearfix">
			<div class="ruit">
				<p class="c_value">收益计算结果</p>
				<div>
					<span class="span_t">预期收益：</span> 
					<span id="lixi" class="span_tt"></span> 
					<span class="span_t">元</span>
				</div>
				<div>
					<span class="span_t">本金+收益：</span> 
					<span id="benxi" class="span_tt"></span>
					<span class="span_t">元</span>
				</div>
			</div>
			<form>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="tip" height="57px" width="70">理财金额</td>
						<td width="110">
							<div class="div_input" style="background: url('/resource/images/bg_input_06.gif') no-repeat right top;">
								<input type="text" name="money" id="money" min="0" onblur="checkMoney()" />
							</div> 
						</td>
						<td width="120">
							<span class="true" style="display: none" id="moneyDiv"></span> 
							<span class="false" style="display: none" id="moneyFalse">格式不正确！</span>
						</td>
					</tr>
					<tr>
						<td class="tip" height="57px" >理财期限</td>
						<td >
							<div class="div_input"
								style="background: url('/resource/images/bg_input_06.gif') no-repeat right -57px;">
								<input type="text" name="datetime" id="datetime" onblur="checkDate()" />
							</div> 
						</td>
						<td >
							<span class="true" style="display: none" id="dateDiv"></span> 
							<span class="false" style="display: none" id="dateFalse">格式不正确！</span>
						</td>
					</tr>
					<tr>
						<td class="tip" height="57px">预期年化<br/>收益率</td>
						<td>
							<div class="div_input"
								style="background: url('/resource/images/bg_input_06.gif') no-repeat right -113px;">
								<input type="text" name="annualise" id="annualise"
									onblur="checkAnnual()" />
							</div> 
						</td>
						<td>
							<span class="true" style="display: none" id="annualDiv"></span>
							<span class="false" style="display: none" id="annualFalse">格式不正确！</span>
						</td>
					</tr>
					<tr>
						<td colspan="3" height="80px">
							<a href="javascript:void(0)" onclick="jisuan()" class="c_cal">计算</a>&nbsp;&nbsp;&nbsp;&nbsp; 
							<a href="javascript:void(0)" onclick="resets()" class="c_reset">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>