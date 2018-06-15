<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>用户注册协议</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<link rel="icon" type="image/x-icon" href="/resource/images/ico.ico">
<link rel="stylesheet" href="/resource/css/index.css">
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<!--移动端版本兼容 -->
<script type="text/javascript">
	var phoneWidth = parseInt(window.screen.width);
	var phoneScale = phoneWidth / 640;
	var ua = navigator.userAgent;
	if (/Android (\d+\.\d+)/.test(ua)) {
		var version = parseFloat(RegExp.$1);
		if (version > 2.3) {
			console.log('Android version = ' + version + '> 2.3');
			document
					.write('<meta name="viewport" content="width=640,initial-scale=1, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
		} else {
			console.log('Android version = ' + version + '<= 2.3');
			document
					.write('<meta name="viewport" content="width=640,initial-scale=1, target-densitydpi=device-dpi">');
		}
	} else {
		console.log('iOS');
		document
				.write('<meta name="viewport" content="width=640,initial-scale=1, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
	};
</script>
<!--移动端版本兼容 end-->

</head>
<body>
	<!-- 协议弹框 -->
	<div class="pro_txt" style="font-size: 26px;">
		<p class="MsoNormal" align="center" style="text-align: center;font-size: 30px;font-weight: bold;margin: 5px 0;">
			领钱儿平台用户服务协议<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			北京睿博盈通网络科技有限公司（下称“本公司”）通过其合法运营的领钱儿（下称“本平台”，网址是<span>http://www.lingmoney.cn</span>）及其已经开发<span>APP</span>依据《领钱儿平台用户服务协议》（以下简称“本协议”）为领钱儿注册用户（以下简称“领钱儿用户”、“用户”或“您”）提供服务。在成为领钱儿用户前，您务必仔细阅读以下条款，充分理解各条款内容后再选择是否接受本协议。除非您接受本协议所有条款，否则无权使用领钱儿于本协议下所提供的服务。一旦您在领钱儿平台或领钱儿<span>APP</span>提交用户注册申请，即意味着您已阅读本协议所有条款，并对本协议条款的含义及相应的法律后果已全部通晓并充分理解，同意受本协议约束。若您不接受以下条款，请您停止注册。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			一、声明与承诺<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			1.1
			本协议的内容包括以下全部条款以及本平台已经发布的及将来可能发布的与用户有关的各项规则，该规则均为本协议不可分割的一部分，与以下所列条款具有同等法律效力。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			1.2
			本公司有权根据需要修改本协议的内容。如以下条款或本平台各项规则有任何变更，本平台将在平台上刊载公告，无需另行单独通知您。经修订的相关条款和规则一经公告，即于公告之日或公告规定的特定生效日期自动生效，请您适时关注本平台关于相关条款和规则的公告。如不同意该变更，请您在该变更的公告刊载之日起<span>72</span>小时内以您在本平台注册时提供的邮箱向本平台的服务邮箱（服务邮箱详见页面展示）发送邮件明确表明希望终止本协议（“注册终止申请”），本平台确认收到您的邮件后将与您协商本协议的终止以及终止后双方义务的履行。如您在本款所述时限内未发送注册终止申请，则视为您已经同意接受该变更，经修订的相关条款和规则一经公告，即于公告之日或公告规定的特定生效日期自动生效并对您产生法律约束力。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			1.3 您同意，基于运行、交易安全的需要或根据相关法律法规规定或监管机构的要求，本公司可以暂时停止提供或者限制本服务部分功能<span>,</span>或提供新的功能，在任何功能减少、增加或者变化时，只要您仍然使用本服务，表示您仍然同意本协议或者变更后的协议。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			1.4
			您应在仔细阅读、充分理解并完全接受本协议的全部条款及各类规则后方能勾选位于注册页面下方的“我已阅读并同意《领钱儿平台用户服务协议》”选项，并按照本平台的流程注册，注册时应使用本人的用户账号，本协议在您注册成功时即产生法律效力。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			1.5 本协议产生法律效力后，适用于您在本平台的全部活动。如有违反而导致任何法律后果的发生，您将以自己的名义独立承担所有相应的法律责任。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			1.6 在本平台交易需订立的合同采用电子合同方式。注册用户使用用户账户登录本平台后，根据本平台的相关规则，以用户账户<span>ID</span>在本平台通过点击确认或类似方式签署的电子合同即视为注册用户本人真实意愿并以注册用户本人名义签署的合同，具有法律效力。注册用户应妥善保管自己的账户密码等账户信息，注册用户通过前述方式订立的电子合同对合同各方具有法律约束力，注册用户不得以其账户密码等账户信息被盗用或其他理由否认已订立的合同的效力或不按照该等合同履行相关义务。我们在此特别提醒您应避免选择过于简单、明显的单词或日期（包括但不限于姓名、昵称、生日等）作为账户密码。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			1.7 本协议不涉及您与本平台的其他用户之间的法律关系及法律纠纷，本公司不承担因您与本平台的其他用户之间的纠纷而产生的任何法律责任。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			1.8 您明确并认可，任何通过本平台进行的交易并不能避免以下风险的产生，本平台不能也没有义务为如下风险负责：<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>1</span>）宏观经济风险：因宏观经济形势变化，可能引起价格等方面的异常波动，用户有可能遭受损失；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>2</span>）政策风险：有关法律、法规及相关政策、规则发生变化，可能引起价格等方面异常波动，用户有可能遭受损失；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>3</span>）违约风险：因其他交易方无力或无意愿按时足额履约，用户有可能遭受损失；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>4</span>）利率风险：市场利率变化可能对购买或持有产品的实际收益产生影响；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>5</span>）不可抗力因素导致的风险；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>6</span>）因用户的过错导致的任何损失
			，该过错包括但不限于：决策失误、操作不当、遗忘或泄露密码、密码被他人破解、用户使用的计算机系统被第三方侵入、用户委托他人代理交易时他人恶意或不当操作而造成的损失。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			二、用户的身份<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			2.1 作为本平台企业<span>/</span>个人用户，您必须是在中华人民共和国（暂不包括香港特别行政区、澳门特别行政区及台湾地区）大陆注册的具有独立法人资格的有限责任公司或股份有限公司。
		</p>
		<p class="MsoNormal" style="text-indent: 22.2pt;">
			&nbsp;2.2
			在注册时和使用本平台服务的所有期间，您应准确提供并及时更新真实资料和信息，并保证自您注册之时起至您使用本平台服务的所有期间，所提交的所有资料和信息（包括但不限于公司名称、住址、组织机构代码、征信信息、银行账户、法定代表人的身份信息、电子邮件地址、联系电话<span>/</span>手机、联系地址、邮政编码、个人身份信息等）真实、准确、完整、有效，且是最新的。如您提交的任何资料或信息出现变更，您应在变更当日向本平台提交变更后的资料和信息，由于资料或信息变更影响您使用本平台服务或因未能按时提交变更后的资料或信息影响您使用本平台服务的，本平台不承担任何责任。您应完全独自承担因信息提供不实或未及时提交变更信息而导致的您在使用本平台服务过程中遭受的任何损失或增加任何费用等不利后果。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			2.3 本平台仅对已注册用户这一特定范围提供服务，其他人员、组织本平台并不提供服务。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			三、服务内容<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			3.1 本平台向您提供以下服务中的一项或多项：<span>(1) </span>信息交互；<span> (2)</span>为交易完成提供技术服务与支持；<span>
				(3) </span>其他相关中介服务。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			3.2本平台不对任何用户、任何交易提供任何担保或条件，无论是明示、默示或法定的。本平台不能也不试图对个人用户发布的信息进行控制，对该等信息，本平台不承担任何形式的证明、鉴定服务。本平台不能完全保证平台内容的真实性、充分性、可靠性、准确性、完整性和有效性，并且无需承担任何由此引起的法律责任。用户依赖于个人用户的独立判断进行交易，用户应对其作出的判断承担全部责任。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			3.3本<a name="_GoBack"></a>平台对用户账户仅提供如下技术服务：<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>1</span>）银行卡认证：为使用领钱儿或领钱儿委托的第三方机构提供的充值、取现、代扣等服务，个人用户应按照领钱儿平台规定的流程提交以个人用户本人名义登记的有效银行借记卡等信息，经由领钱儿审核通过后，领钱儿会将个人用户的账户与前述银行账户进行绑定。如个人用户未按照领钱儿规定提交相关信息或提交的信息错误、虚假、过时或不完整，或者领钱儿有合理的理由怀疑个人用户提交的信息为错误、虚假、过时或不完整，领钱儿有权拒绝为个人用户提供银行卡认证服务，个人用户因此未能使用充值、取现、代扣等服务而产生的损失自行承担。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>2</span>）充值：个人用户可以使用领钱儿指定的方式向用户账户充入资金，用于通过领钱儿平台进行交易。个人用户账户内的资金不计收利息。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>3</span>）代收<span>/</span>代付：领钱儿按照其平台当时向个人用户开放的功能提供代收<span>/</span>代付服务，自行或委托第三方机构代为收取其他用户或领钱儿合作的担保公司等第三方向个人用户的账户支付的本息、代偿金等各类款项，或者，将个人用户账户里的款项支付给个人用户指定的其他方。领钱儿不保证提供的前述服务符合个人用户的期望。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>4</span>）取现：个人用户可以通过领钱儿平台当时开放的取现功能将个人用户账户中的资金转入经过认证的银行卡账户中。领钱儿将于收到个人用户的前述指示后，尽快通过第三方机构将相应的款项汇入个人用户经过认证的银行卡账户（根据个人用户提供的银行不同，会产生汇入时间上的差异）。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>5</span>）查询：领钱儿将对个人用户在领钱儿平台的所有操作进行记录，不论该操作之目的最终是否实现。个人用户可以通过用户账户实时查询用户账户名下的交易记录。个人用户理解并同意个人用户最终收到款项的服务是由个人用户经过认证的银行卡对应的银行提供的，需向该银行请求查证。个人用户理解并同意通过领钱儿平台查询的任何信息仅作为参考，不作为相关操作或交易的证据或依据；如该等信息与领钱儿记录存在任何不一致，应以领钱儿提供的书面记录为准。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			个人用户了解，上述充值、代收<span>/</span>代付及取现服务涉及领钱儿与银行、担保公司、第三方支付机构等第三方的合作。个人用户同意：<span>(a)
			</span>受银行、担保公司、第三方支付机构等第三方仅在工作日进行资金代扣及划转的现状等各种原因所限，领钱儿不对前述服务的资金到账时间做任何承诺，也不承担与此相关的责任，包括但不限于由此产生的利息、货币贬值等损失；<span>(b)
			</span>一经个人用户使用前述服务，即表示个人用户不可撤销地授权领钱儿进行相关操作，且该等操作是不可逆转的，个人用户不能以任何理由拒绝付款或要求取消交易。就前述服务，领钱儿暂不会向个人用户收取费用，但个人用户应按照第三方的规定向第三方支付费用，具体请见第三方平台的相关信息。与第三方之间就费用支付事项产生的争议或纠纷，与领钱儿无关。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			3.4 本公司向您提供的服务的具体内容、是否收取服务费及服务费的具体标准和规则，由本公司与您另行签署的具体协议以及本平台公布的规则确定。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			3.5
			除本公司与您签署的具体协议另有约定外，本平台向您发出的任何通知、资料、文件等基于本协议或本平台服务产生的信息，您同意本平台可通过平台公告、电子邮件、短信或其他有效方式发送前述信息，信息自发送之日即视为已送达您。如因信息传输、您未能在发送之前提交最新的电子邮件或手机号码等原因导致您未能在发送当日收到该等信息，本公司及本平台无需承担任何责任。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			四、用户使用限制<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			4.1
			您不得利用本平台或本平台服务从事任何不符合中国法律法规、规章、规范性文件、政府政策、互联网之国际惯例的行为或侵犯他人合法权益的活动。本平台在发现您从事该活动时，有权不经通知而立即停止您对本平台的全部或部分功能的使用，且您应承担所有相关法律责任。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			4.2
			在使用本平台提供的任何服务（包括但不限于站内信服务、群组服务、论坛服务或其他电子邮件转发服务）的过程中，您不得发送、公布或展示任何垃圾邮件、信息或其他可能违反中国法律法规、规章、规范性文件、政府政策、互联网之国际惯例及本协议的内容。本平台在发现您从事该等活动或发布该等内容时，有权不经您同意而删除该等内容，并有权不经通知而立即暂停或停止您对本平台的全部或部分功能的使用。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			4.3未经本公司或本平台明示或者书面准许，您不得从事以下任何行为：<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>1</span>）使用任何机器人、蜘蛛软件、刷屏软件或其他自动方式进入本平台<span>;</span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>2</span>）进行任何对本平台造成或可能造成重大负荷的行为；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>3</span>）干扰或试图干扰本平台的正常工作或本平台进行的任何活动；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>4</span>）越过本平台可能用于防止或限制进入本平台的机器人排除探头或其他形式。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			4.4
			您在注册时向本平台提交的电子邮箱、用户名、密码及安全问题答案是您在本平台的唯一识别信息。您注册成功后，不得将注册的电子邮箱、用户名、密码及安全问题答案转让或授权给第三方使用。您确认，使用您的用户名和密码登录本平台后在本平台的一切行为均代表您本人并由您承担相应的法律后果。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			4.5
			本平台的所有内容，包括但不限于文本、数据、图片、音频、视频、源代码和其他所有信息，均由本公司享有知识产权。未经本公司事先书面同意，您或其他任何人不得复制、改编、传播、公布、展示或以任何其他方式侵犯本公司的知识产权。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			4.6
			您不得对本平台或其内容进行转售或商业利用、衍生利用；不得为其他商业利益而下载或拷贝账户信息或使用任何数据采集和摘录工具。未经本公司书面许可，严禁对本平台的内容进行系统获取以直接或间接创建或编辑文集、汇编、数据库或人名地址录。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			4.7 未经本公司明确书面同意，您不得以任何方式使用本平台、本公司或其关联方的字号、商标。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			4.8 本公司保留对您违反上述规定的行为追究法律责任的权利。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			五、用户信息的保护及披露<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			5.1
			您在使用浏览器或者移动客户端访问本平台时，本平台或本平台授权的第三方插件会自动接收并记录您在在浏览器端或者移动客户端的数据，包括但不限于<span>IP</span>地址、平台<span>cookie</span>、网卡地址、手机设备号等信息。本平台收集和储存您的用户信息的主要目的在于提高为您提供服务的效率和质量。同时，本平台会积极采取先进的技术手段和有效的管理机制来保障用户的账户安全，保障用户的隐私信息不被任何不合理的使用或者泄露。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			5.2 您同意本平台在业务运营中使用您的用户信息，您授予本公司永久的、免费的及完整的许可使用权利<span>(</span>并且有权对该权利进行再授权<span>)</span>，使本公司有权在不涉及和泄露用户隐私情况下使用您的数据参与制作其派生产品，包括但不限于市场调研、消费分析等，以已知或日后开发的任何形式的媒体、技术或纳入其他作品里。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			5.3 您同意本平台可通过人工或自动程序对您的个人资料进行管理和必要审查，以防止利用本平台进行欺诈等非法行为的发生。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			5.4 您同意因使用需要，本平台及相关服务软件、应用程序将会记录您的设备号。该记录仅用于设备识别及本平台消息推送，不会用于其它任何用途。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			六、隐私权保护<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			6.1
			本平台不公开您的注册信息及其他个人信息，将采用行业标准惯例以保护您的用户信息。但您应了解，任何保护措施均可能受限于技术水平限制而不能确保您的信息不会通过本协议中未列明的途径泄露出去，本公司不承担因此而导致的任何损失或责任。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			6.2 虽有第<span>6.1</span>条之约定，但在下列情况下，本公司有权全部或部分披露您的保密信息：<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>1</span>）根据法律规定，或应行政机关、司法机关要求，向第三人或行政机关、司法机关披露；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>2</span>）如您系权利人并针对他人在本平台上侵犯您利益的行为提起投诉，应被投诉人要求，向被投诉人披露；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>3</span>）权利人认为您在本平台上的行为侵犯其合法权利并提出投诉的，可向权利人披露；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>4</span>）您出现违反本平台规则，需要向第三方披露的；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>5</span>）根据法律和本平台规则，本平台因提供服务需要披露或其他本平台认为适合披露的。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			七、免责声明<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			7.1 您将对您提供或发布的信息及其他在本平台上发生的任何行为承担责任，并应使本平台免于对此承担任何责任。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			7.2
			若因用户、银行、支付机构或其他合作机构原因（包括但不限于用户、银行、支付机构、合作机构系统故障、操作失误等），造成操作或交易失败，本平台对此不负任何责任。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			7.3
			您确认知悉并同意，基于互联网的特殊性，本平台不担保服务不会中断，也不担保服务的及时性、安全性。系统因相关状况无法正常运作，使个人用户无法使用任何本平台服务或使用任何领钱儿服务时受到任何影响时，本平台对个人用户或第三方不负任何责任，前述状况包括但不限于：
			<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>1</span>）本平台进行升级或维护的。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>2</span>）电信设备及网络出现不能进行、不能正确进行或不能完整进行数据传输等问题的。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>3</span>）由于黑客攻击、网络供应商技术调整或故障、银行方面的问题等原因而造成的本平台服务中断或延迟。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>4</span>）因包括但不限于台风、地震、海啸、洪水、停电、战争、恐怖袭击或其他根据互联网惯例所认定之不可抗力之因素，造成本平台不能提供服务的。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>5</span>）其他影响本平台提供服务的情形。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			7.4
			本平台仅提供理财信息发布及相关增值服务，具体交易信息均以产品发布方所发布内容为准，本平台不对该等产品信息的真实性、充分性、完整性、及时性、可靠性或有效性作出任何明示或暗示的保证，亦不对理财产品收益做出任何明示的或暗示的承诺或担保。您应根据自身的理财偏好和风险承受能力，自行衡量交易相对方、产品信息以及交易的真实性和安全性。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			7.5 您在选择本平台服务时应直接登录本平台，不应通过邮件或其他平台提供的链接登录，否则由此造成的风险和损失将由您自行承担。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			7.6
			如您发现有他人冒用或盗用您的账户及密码或进行任何其他未经合法授权行为之情形，应立即以书面方式通知本平台并要求本平台暂停服务，否则由此产生一切责任由您本人承担。本平台将积极响应您的要求；但您应理解，对您的要求采取行动需要合理期限，在此之前，本平台对已执行的指令及<span>(</span>或<span>)</span>所导致的您的损失不承担任何责任。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			7.7
			本平台内容可能涉及或链接到由第三方所有、控制或者运营的其他平台（“第三方平台”）。本平台不能保证也没有义务保证第三方平台上的信息的真实性和有效性。您确认按照第三方平台的注册协议而非本注册协议使用第三方平台，第三方平台的内容、产品、广告和其他任何信息均由您自行判断并承担风险。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			八、税费承担<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			8.1 您自行承担使用本平台提供服务时发生的任何费用，该费用包括但不限于网络使用费、通信费、资料快递费。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			8.2
			针对使用本平台提供服务完成的交易而取得的收入，您自行承担相应的个人所得税及其他税费。您应自行按照中国法律向税务机关申报并缴纳个人所得税及其他税费。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			九、违约责任<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			9.1
			如您违反本协议项下任何陈述、保证、承诺或任何其他义务时，本公司或本平台有权不经通知而立即停止您使用本平台的全部或部分功能，并有权要求您承担您给本平台带来的任何损失。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			9.2
			如您违反了本协议中所作的陈述、保证、承诺或任何其他义务，致使本平台或本公司及其股东、实际控制人、董事、员工、代理人及其他关联方承受任何损失，您应向受损失的一方做出全额赔偿（包括受损失的一方因向您追偿而发生的诉讼费、律师费、差旅费、误工费等费用）。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			十、法律适用与管辖<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			10.1因本平台提供服务所产生的争议均适用中华人民共和国法律，并由北京睿博盈通网络科技有限公司住所地的人民法院管辖。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			十一、注销与终止<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			11.1
			如您决定不再使用用户账户，应向本公司提出申请并清偿所有应付款项，经本公司审核无误后可正式注销用户账户。本公司有权保留注销账户前的一切使用数据，并可在法律法规许可或本协议约定的范围内使用。本条款不因注册协议终止而失效。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			11.2
			您确认，您应自行对您的领钱儿账户负责，只有您方可使用该账户。该账户不可转让、不可赠与。在您决定不再使用用户账户时，您应按本公司规定流程向本公司申请注销该账户。如出现公司合并、分立、注销等情形，本公司或其授权的主体有权根据有效法律文书（包括但不限于生效的法院判决等）处置与用户账户相关的款项。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			11.3
			除非本公司终止本协议或者您申请终止本协议及注销相应用户账户且经本平台同意，否则本协议始终有效。在您发生如下情形的情况下，本公司有权通过本协议第<span>3.3</span>条约定的通知方式告知您终止本协议、关闭您的账户或者限制您使用本平台：<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>1</span>）提供的企业<span>/</span>个人资料、个人资料不真实、不完整；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>2</span>）利用本平台从事任何非法活动；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>3</span>）用户账户涉嫌洗钱、套现、传销、被冒用或存在其他风险；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>4</span>）未按照与本公司签署的具体协议以及本平台公布的规则确定支付相应的服务费用；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>5</span>）账户连续三年内未实际使用；<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			（<span>6</span>）其他违反本协议、相关规则或法律法规规定的行为。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			11.4
			当您不再符合本协议第二条约定的注册用户身份条件致使本协议及（或）相关规则无法继续履行时，本协议即行终止。您或您的合法授权代表在提交相关证明文件后，可向本平台申请查询您在本平台注册的用户信息及其他交易信息，并办理资产的转移。同时，本平台有权关闭您的账户。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			11.5用户账户的暂停、中断或终止不代表您责任的终止，您仍应对使用领钱儿服务期间的行为承担可能的违约或损害赔偿责任。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			十二、其他<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			12.1本协议各标题仅为参考之用，在任何方面均不界定、限制、解释或描述该条的范围或限度。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			12.2若本协议的部分条款被认定为无效或者无法实施时，本协议中的其他条款仍然有效。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			12.3本公司可将本协议项下的权利义务转让给本公司的关联方。<span></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			12.4 本协议的最终解释权归本公司享有。<span></span>
		</p>
		<p class="MsoNormal">
			<span>&nbsp;</span>
		</p>
	</div>
	<!-- 协议弹框 -->
</body>
</html>