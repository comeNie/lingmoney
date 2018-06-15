<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>


<script type="text/javascript">
	$(document).ready(function() {
		$("#aboutusBaozheng").addClass("hover");
		$("#aboutusBaozhengli").find("dl").show();
		$("#aboutusLaw").addClass("hover");
		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");

		/*顶部*/
		$(".leftul .weixinLi").hover(function() {
			$(".popweixin").show();
		}, function() {
			$(".popweixin").hide();
		})
		$(".leftul .qqLi").hover(function() {
			$(".popQQ").show();
		}, function() {
			$(".popQQ").hide();
		})

		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		})
		/*顶部*/

	})
</script>
</head>
<body>

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<div class="mainbody">
		<div class="box clearfix">
			<div class="sitemap">
				<a href="/index.html">领钱儿</a>&nbsp;>&nbsp;<span>关于我们</span>
			</div>
			<!-- 关于我们开始 -->
			<jsp:include page="/common/aboutus.jsp"></jsp:include>
			<!-- 关于我们结束 -->


			<div class="mRight">
				<div class="mRight01">
					<div class="htitle">安全保证</div>
					<div class="zonglan hcenter ncenter">
						<div class="section">
							<h2>法律声明</h2>
							<div class="section_safe">
								<h6>免责声明</h6>
								<p style="text-indent:0">1、您将对您提供或发布的信息及其他在本平台上发生的任何行为承担责任，并应使本平台免于对此承担任何责任。</p>
								<p style="text-indent:0">2、 若因用户、银行、支付机构或其他合作机构原因（包括但不限于用户、银行、支付机构、合作机构系统故障、操作失误等），造成操作或交易失败，本平台对此不负任何责任。</p>
								<p style="text-indent:0">3、您确认知悉并同意，基于互联网的特殊性，本平台不担保服务不会中断，也不担保服务的及时性、安全性。系统因相关状况无法正常运作，使个人用户无法使用任何本平台服务或使用任何“领钱儿”网服务时受到任何影响时，本平台对个人用户或第三方不负任何责任，前述状况包括但不限于：</p>
								<p>3.1、本平台进行升级或维护的。</p>
								<p>3.2、电信设备及网络出现不能进行、不能正确进行或不能完整进行数据传输等问题的。</p>
								<p>3.3、由于黑客攻击、网络供应商技术调整或故障、银行方面的问题等原因而造成的本平台服务中断或延迟。</p>
								<p>3.4、因包括但不限于台风、地震、海啸、洪水、停电、战争、恐怖袭击或其他根据互联网惯例所认定之不可抗力之因素，造成本平台不能提供服务的。</p>
								<p>3.5、其他影响本平台提供服务的情形。</p>
								<p style="text-indent:0">4、本平台仅提供投资信息发布及相关增值服务，具体交易信息均以产品发布方所发布内容为准，本平台不对该等产品信息的真实性、充分性、完整性、及时性、可靠性或有效性作出任何明示或暗示的保证，亦不对理财产品收益做出任何明示的或暗示的承诺或担保。您应根据自身的理财偏好和风险承受能力，自行衡量交易相对方、产品信息以及交易的真实性和安全性。</p>
                                <p style="text-indent:0">5、您在选择本平台服务时应直接登录本平台，不应通过邮件或其他平台提供的链接登录，否则由此造成的风险和损失将由您自行承担。</p>
                                <p style="text-indent:0">6、如您发现有他人冒用或盗用您的账户及密码或进行任何其他未经合法授权行为之情形，应立即以书面方式通知本平台并要求本平台暂停服务，否则由此产生一切责任由您本人承担。本平台将积极响应您的要求；但您应理解，对您的要求采取行动需要合理期限，在此之前，本平台对已执行的指令及(或)所导致的您的损失不承担任何责任。</p>
                                <p style="text-indent:0">7、本平台内容可能涉及或链接到由第三方所有、控制或者运营的其他平台（“第三方平台”）。本平台不能保证也没有义务保证第三方平台上信息的真实性和有效性。您确认按照第三方平台的注册协议而非本平台注册协议使用第三方平台，第三方平台的内容、产品、广告和其他任何信息均由您自行判断并承担风险。</p>
								<h6>隐私条款</h6>
								<p style="text-indent:0">
									1、本平台不公开您的注册信息及其他个人信息，将采用行业标准惯例以保护您的用户信息。但您应了解，任何保护措施均可能受限于技术水平限制而不能确保您的信息不会通过本协议中未列明的途径泄露出去，本公司不承担因此而导致的任何损失或责任。</p>
								<p style="text-indent:0">2、虽有第6.1条之约定，但在下列情况下，本公司有权全部或部分披露您的保密信息：</p>
								<p>2.1、根据法律规定，或应行政机关、司法机关要求，向第三人或行政机关、司法机关披露；</p>
								<p>2.2、如您系权利人并针对他人在本平台上侵犯您利益的行为提起投诉，应被投诉人要求，向被投诉人披露；</p>
								<p>2.3、权利人认为您在本平台上的行为侵犯其合法权利并提出投诉的，可向权利人披露；</p>
								<p>2.4、您出现违反本平台规则，需要向第三方披露的；</p>
								<p>2.5、根据法律和本平台规则，本平台因提供服务需要披露或其他本平台认为适合披露的。</p>
								
							</div>

						</div>


					</div>

				</div>

			</div>
			<div style="clear:both"></div>
		</div>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>