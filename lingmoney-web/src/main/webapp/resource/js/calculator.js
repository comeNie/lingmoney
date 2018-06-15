var flag = false;
function checkMoney() {
	var money = $('#money').val();

	var regex = /^(([1-9]\d*)|0)(\.(\d){1,2})?$/;
	flag = regex.test(money);
	if (money != null && money != "") {
		/*
		 * if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		 * if(!((event.keyCode>=48 && event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
		 * event.returnValue=false;
		 */
		if (flag) {
			if(money<=100000000000){
				$('#moneyDiv').css("display", "inline-block");
				$('#moneyFalse').css("display", "none");
				flag=true;

			}else{
				$('#moneyFalse').css("display", "inline-block");
				$('#moneyDiv').css("display", "none");
				flag=false;
			}
		}else {
			$('#moneyFalse').css("display", "inline-block");
			$('#moneyDiv').css("display", "none");
			flag = false;
		}
	} else {
		$('#moneyFalse').css("display", "inline-block");
		$('#moneyDiv').css("display", "none");
		flag = false;
	}
	return flag;
}

function checkDate() {
	var datetime = $('#datetime').val();
	var regex = /^(([1-9]\d*)|0)$/;
	flag = regex.test(datetime);
	if (datetime != null && datetime != "") {
		if (flag) {
			if(datetime<=1000000){
				$('#dateDiv').css("display", "inline-block");
				$('#dateFalse').css("display", "none");
				flag=true;
			}else{
				$('#dateFalse').css("display", "inline-block");
				$('#dateDiv').css("display", "none");
				flag=false;
			}
			/*$('#dateDiv').css("display", "inline-block");
			$('#dateFalse').css("display", "none");*/
		} else {
			$('#dateDiv').css("display", "none");
			$('#dateFalse').css("display", "inline-block");
			flag = false;
		}
	} else {
		$('#dateDiv').css("display", "none");
		$('#dateFalse').css("display", "inline-block");
		flag = false;
	}
	return flag;
}

function checkAnnual() {
	var annualise = $('#annualise').val();
	var regex = /^(([1-9]\d*)|0)(\.(\d){1,2})?$/;// 标准金额的正则表达式，小数点后允许两位，小数点前不限;
	flag = regex.test(annualise);
	// alert(flag + "3");
	if (annualise != null && annualise != "") {
		if (flag) {
			if(annualise>100){
				$('#annualDiv').css("display", "none");
				$('#annualFalse').css("display", "inline-block");
				flag = false;
			}else{
				$('#annualFalse').css("display", "none");
				$('#annualDiv').css("display", "inline-block");
				flag = true;
			}
		} else {
			$('#annualDiv').css("display", "none");
			$('#annualFalse').css("display", "inline-block");
			flag = false;
		}
	} else {
		$('#annualDiv').css("display", "none");
		$('#annualFalse').css("display", "inline-block");
		flag = false;
	}
	return flag;
}

function resets() {
	$('#money').val("");
	$('#datetime').val("");
	$('#annualise').val("");
	$('#benxi').text("");
	$('#lixi').html("");

	$('#moneyFalse').css("display", "none");
	$('#moneyDiv').css("display", "none");
	$('#dateDiv').css("display", "none");
	$('#dateFalse').css("display", "none");
	$('#annualFalse').css("display", "none");
	$('#annualDiv').css("display", "none");
}
function fmoney(s, n) {
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
	t = "";
	for (i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	return t.split("").reverse().join("") + "." + r;
}
function jisuan() {

	if (checkMoney() && checkDate() && checkAnnual()) {
		var datetime = document.getElementById('datetime').value;

		var annualise = document.getElementById('annualise').value / 100;
		var money = document.getElementById('money').value;
		var lixi = parseFloat(money) * parseFloat(annualise) / 365
				* parseInt(datetime);

		var benxi = parseFloat(lixi) + parseFloat(money);

		$('#benxi').html(fmoney(benxi, 2));
		$('#lixi').html(fmoney(lixi, 2));

	}

}
