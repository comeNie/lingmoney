$(function() {
	$('.huaxing_jingdong_tab a').on('click', function() {
		$(this).addClass('active').siblings().removeClass('active');
		var boxindex = $(this).attr('boxindex');
		if (boxindex == 1) {
			$('#boxindex_1').show();
			$('#boxindex_2').hide();
		} else {
			$('#boxindex_2').show();
			$('#boxindex_1').hide();
		}
	});

	$(".rz-close,.reset").click(function() {
		$("#rz-box-bg").hide();
		$("#div_customer").hide();
		$("#div_customer_tip").hide();
	});

	// 判断银行处理结果。
//	ifCard();
});

var content;
window.onload = function() {
	setTimeout(fun, 100);
	// 查询所有快捷支付银行
	$.ajax({
		url : "/myAccount/listQuickPaymentBank",
		type : "get",
		success : function(data) {
			if (data.code == 200) {
				content = "";
				content += '<table width="100%" cellspacing="0" cellpadding="0">';
				content += "<tr style='cursor:pointer;'>";
				$.each(data.obj,
					function(i, item) {
						if (i != 0 && i % 3 == 0) {
							content += "</tr><tr style='cursor:pointer;'>";
						}
						content += "<td style='width:125px;height:50px;' onclick='bankPick(this)'><input data-info='bankCode' type='hidden' value='"
								+ item.bankCode + "'>";
						content += "<input data-info='bankShort' type='hidden' value='" + item.bankShort + "' />";
						content += "<input data-info='bankName' type='hidden' value='" + item.bankName + "' />";
						content += "<img src='" + item.bankLogo + "'></img></td>";
					}
				);

				content += "</tr></table>";
				$("#BankListDiv").append(content);
				$("#BankListDiv2").append(content);
			}
		}
	});

	document.onclick = function() {
		$("#BankListDiv").hide();
	}

	$("#BankListDiv").parent().on("click", function(e) {
		e = e ? e : window.event;
		window.event ? window.event.cancelBubble = true : e.stopPropagation();
	});
};
function fun() {
	if ($("#idCard").val() != "") {
		$(".ssss:eq(0)").find("span").hide();
		$("#idCard").css("color", "#434343");
	}
	if ($("#name").val() != "") {
		$(".ssss:eq(1)").find("span").hide();
		$("#name").css("color", "#434343");
	}
}

// 执行绑卡
function hxTiedCard() {

	// 提交表单
	$.post("/bank/tiedCard",
		function(d) {
			var code = d.code;
			var msg = d.msg;
	
			if (code !== 200) {
				// 弹框提示错误
				cusAlert(msg);
				// 激活成功 刷新页面
				if (code == 3020) {
					setTimeout("window.location.reload()", 1000);
				}
				return;
			} else {
				// 组织表单，提交数据
				$("#requestData").val(d.obj.requestData);
				$("#transCode").val(d.obj.transCode);
				$("#tiedCardForm").attr("action", d.obj.bankUrl);
				$("#tiedCardForm").submit();
	
				$("#div_customer").show();
				$("#rz-box-bg").show();
				offsetDiv("#div_customer");
				$("#div_customer h2").text("请在新页面完成账户激活");
				$("#div_customer p:eq(0)").html('完成账户激活前请<span style="margin: 0 5px; color: #ea5513">不要关闭此窗口</span>，完成账户激活后根据您的情况点击下面按钮<br><a shape="rect" coords="0,75,265,151" href="/hxbankOpertion" style="padding-top:15px;display:block">了解更多，请查看银行存管操作指南>></a>');
				$("#div_customer p:eq(1) a:eq(0)").html('已完成账户激活').attr('target', '_self').attr('href','/bank/queryTiedCardResult/' + d.obj.channelFlow).click(
								function() {
									$('.rz-close').trigger('click')
								});
				$("#div_customer p:eq(1) a:eq(1)").html('账户激活遇到问题').attr('target', '_self').attr('href',
								'/helpCenterGuide').click(
								function() {
									$('.rz-close').trigger('click')
								});
			}
		})
}

// 自定义alert
function cusAlert(msg) {
	$("#rz-box-bg").show();
	$("#div_customer_tip").show();
	offsetDiv("#div_customer_tip");
	$("#div_customer_tip h2").text("提示");
	$("#div_customer_tip p:eq(0)").html(msg);
}
// 自定义alert
function sAlert(msg) {
	var html = '<div id="sShadow" style="position: fixed;z-index: 9999;left: 0;top: 0;width: 100%;height: 100%;background:#000;opacity:0.6;filter:alpha(opacity=60);"></div>'
	html += '<div style="position: fixed;z-index: 9999;left: 50%;top: 50%;padding-bottom: 30px;width: 700px;background: #fff;" id="sAlert">';
	html += '<div style="height: 52px;line-height:52px;padding-left:22px;border: 0;border-bottom: 1px solid #ea5413;border-left: 8px solid #ea5413;"><h2>提示</h2></div>';
	html += '<p style="padding-top: 30px; font-size: 16px; color: #c54107; text-align:center;padding-left:14px;margin-top:15px;">'
			+ msg + '</p>';
	html += '<p style="padding-top: 10px;text-align:center;padding-left:14px;margin-top:15px;">';
	html += '<a href="javascript:void(0)" onclick="sAlertClose()"  style="text-align:center;padding: 0;display: inline-block;margin-right: 20px;width: 203px;height: 48px;line-height: 48px;font-size: 20px;color: #fff;-webkit-border-radius: 3px;-ms-border-radius: 3px;-o-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px;background: #ea5413;">确定</a></p></div>';
	$("body").append(html);
	offsetDiv("#sAlert");
	$('#sAlert').show();
}

function sAlertClose() {
	$('#sShadow').remove();
	$('#sAlert').remove();
	// window.location.reload();
}
// 请求开户
function accountOpen() {
	if (testName('hxAccountName', 'hxAccountNameTip', 'hxAccountNameTipImg')
			&& testID('hxAccountIdCard', 'hxAccountIdCardTip',
					'hxAccountTipImg')&&testTel('hxAccountMobile','hxAccountTelTip','hxAccountTelTipImg')) {
		var acName = $("#hxAccountName").val();
		var idNo = $("#hxAccountIdCard").val();
		var acTel=$("#hxAccountMobile").val();

		$.ajax({
			type : "post",
			data : {
				acName : acName,
				idNo : idNo,
				mobile:acTel
			},
			url : "/bank/accountOpen",
			dataType : "json",
			success : function(data) {
				if (data.code == 200) {
					$("#accountOpenForm").attr("action",
							data.obj.bankUrl);
					$("#accountOpenRequestData").val(
							data.obj.requestData);
					$("#accountOpenTransCode").val(data.obj.transCode);

					$("#accountOpenForm").submit();
					$("#div_customer").show();
					$("#rz-box-bg").show();
					offsetDiv("#div_customer");
					$("#div_customer h2").text("请在新页面完成账户开立");
					$("#div_customer p:eq(0)").html('完成账户开立前请<span style="margin: 0 5px; color: #ea5513">不要关闭此窗口</span>，完成账户开立后根据您的情况点击下面按钮<br><a shape="rect" coords="0,75,265,151" href="/hxbankOpertion" style="padding-top:15px;display:block">了解更多，请查看银行存管操作指南>></a>');
					$("#div_customer p:eq(1) a:eq(0)").html('已完成账户开立').attr('target', '_self').attr('href','/bank/queryAccountOpen?seqNo=' + data.obj.channelFlow).click(
									function() {
										$('.rz-close').trigger('click')
									});
					$("#div_customer p:eq(1) a:eq(1)").html('账户开立遇到问题')
							.attr('target', '_self').attr('href',
									'/helpCenterGuide').click(
									function() {
										$('.rz-close').trigger('click')
									});
				} else {
					cusAlert(data.msg);
				}
			}
		});
	}
}

function closeVery() {
	$("#rz-box-bg").hide();
	$("#accountOpenVery").hide();
	window.location.reload();
}


//废弃
function ifCard() {
	$.ajax({
		type : "post",
		url : "/bank/accountOpenStatus",
		dataType : "json",
		success : function(data) {
			if (data.code == 6025) {
				$('.hx_box .hxBnakCard').hide();
				$('.hx_box .kt').hide();
				$('.OpenStatus').text(data.msg);
			} else {
				$('.hx_box .hxBnakCard').show();
				$('.hx_box .kt').show();
				$('.OpenStatus').text("");
			}
		}
	});
}
