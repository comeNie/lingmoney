function bankName() {
	var par = /^[\u4e00-\u9fa5]+$/;
	var name = document.getElementById("name").value;
	var ret = par.test(name);
	if (!ret) {
		document.getElementById("tip_name").style.display = "block";
		document.getElementById("tip_name").innerHTML = "银行名称不合法，请重新输入";
		document.getElementById("span_name").style.display = "none";
		return false;
	} else {
		document.getElementById("tip_name").style.display = "none";
		document.getElementById("span_name").style.display = "inline-block";
		return true;
	}
}

function bankNumber() {
	var par = /^(\d{16}|\d{19})$/;
	var number = document.getElementById("number").value;
	var ret = par.test(number);
	if (!ret) {
		document.getElementById("tip_number").style.display = "block";
		document.getElementById("tip_number").innerHTML = "银行账号不合法，请重新输入";
		document.getElementById("span_number").style.display = "none";
		return false;
	} else {
		document.getElementById("tip_number").style.display = "none";
		document.getElementById("span_number").style.display = "inline-block";
		return true;
	}
}

function bankBank() {
	var par = /^[\u4e00-\u9fa5]+$/;
	var bank = document.getElementById("bank").value;
	var ret = par.test(bank);
	if (!ret) {
		document.getElementById("tip_bank").style.display = "block";
		document.getElementById("tip_bank").innerHTML = "开户行名称不合法，请重新输入";
		document.getElementById("span_bank").style.display = "none";
		return false;
	} else {
		document.getElementById("tip_bank").style.display = "none";
		document.getElementById("span_bank").style.display = "inline-block";
		return true;
	}
}

function bank() {
	if (!bankName() || !bankNumber() || !bankBank()) {
		return false;
	} else {
		return true;
	}
}
