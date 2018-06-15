<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<form action="/rest/pay/eRegister" method="post">
	<table>
		<tr>
			<td>商户编号:</td>
			<td><input type="text" name="platformNo" value="21140011137"></td>
			<td>企业名称:</td>
			<td><input type="text" name="enterpriseName"></td>
		</tr>
		<tr>
			<td>开户银行许可证:</td>
			<td><input type="text" name="bankLicense" ></td>
			<td>组织机构代码:</td>
			<td><input type="text" name="orgNo"></td>
		</tr>
		<tr>
			<td>营业执照编号:</td>
			<td><input type="text" name="businessLicense" ></td>
			<td>税务登记号:</td>
			<td><input type="text" name="taxNo"></td>
		</tr>
		<tr>
			<td>法人姓名:</td>
			<td><input type="text" name="legal" ></td>
			<td>法人身份证号:</td>
			<td><input type="text" name="legalIdNo"></td>
		</tr>
		<tr>
			<td>企业联系人:</td>
			<td><input type="text" name="contact" ></td>
			<td>联系人手机号:</td>
			<td><input type="text" name="contactPhone"></td>
		</tr>
		<tr>
			<td>联系人邮箱:</td>
			<td><input type="text" name="email" ></td>
			<td>借款人类型:</td>
			<td>
				<select name="memberClassType">
					<option value="ENTERPRISE" selected="selected">企业借款人</option>
					<option value="GUARANTEE_CORP">担保公司</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="4"><input type="submit" value="提交"></td>
		</tr>
	</table>
</form>