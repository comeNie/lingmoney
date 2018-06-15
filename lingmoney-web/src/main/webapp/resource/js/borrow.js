function testName() {

	var par = /^[\u4e00-\u9fa5]+$/;
	var name = document.getElementById("name_per").value;
	var ret = par.test(name);
	if (!ret) {
		document.getElementById("tip_name").style.display = "inline-block";
		document.getElementById("tip_name").innerHTML = "请输入正确的姓名";
		document.getElementById("span_name").style.display = "none";
		return false;
	} else {
		if(name.length<2){
			document.getElementById("tip_name").style.display = "inline-block";
			document.getElementById("tip_name").innerHTML = "请输入正确的姓名";
			document.getElementById("span_name").style.display = "none";
			return false;
		}
		document.getElementById("tip_name").style.display = "none";
		document.getElementById("span_name").style.display = "inline-block";
		return true;
	}
}

function testTelephone() {
	var par = /^[1][34578][0-9]{9}$/;
	var telephone = document.getElementById("phone_per").value;
	var ret = par.test(telephone);
	if (!ret) {
		document.getElementById("tip_telephone").style.display = "inline-block";
		document.getElementById("tip_telephone").innerHTML = "手机号码有误";
		document.getElementById("span_telephone").style.display = "none";
	} else {
		document.getElementById("tip_telephone").style.display = "none";
		document.getElementById("span_telephone").style.display = "inline-block";
	}
	return ret;
}

function testMoney() {
	var par = /^(?!0+(?:\.0+)?$)\d+(?:\.\d{1,2})?$/;
	var money = document.getElementById("money_per").value;
	var ret = par.test(money);
	if (!ret) {
		document.getElementById("tip_money").style.display = "inline-block";
		document.getElementById("tip_money").innerHTML = "借款金额格式不正确";
		document.getElementById("span_money").style.display = "none";
	} else {
		document.getElementById("tip_money").style.display = "none";
		document.getElementById("span_money").style.display = "inline-block";
	}
	return ret;
}

function testDeadline() {
	var ret = false;
	if (document.getElementById("deadline_per").value == "-1") {
		document.getElementById("tip_deadline").style.display = "inline-block";
		document.getElementById("tip_deadline").innerHTML = "请选择您的借款期限";
		document.getElementById("span_deadline").style.display = "none";
	} else {
		document.getElementById("tip_deadline").style.display = "none";
		document.getElementById("span_deadline").style.display = "inline-block";
		ret = true;
	}
	return ret;
}
function testJob() {
	var ret = false;
	if (document.getElementById("job_per").value == "-1") {
		document.getElementById("tip_job").style.display = "inline-block";
		document.getElementById("tip_job").innerHTML = "请选择您的职业";
		document.getElementById("span_job").style.display = "none";
	} else {
		document.getElementById("tip_job").style.display = "none";
		document.getElementById("span_job").style.display = "inline-block";
		ret = true;
	}
	return ret;
}
function testArea() {
	var ret = false;
	if (document.getElementById("area_per").value == "-1") {
		document.getElementById("tip_area").style.display = "inline-block";
		document.getElementById("tip_area").innerHTML = "请选择您所在的地区";
		document.getElementById("span_area").style.display = "none";
	} else {
		document.getElementById("tip_area").style.display = "none";
		document.getElementById("span_area").style.display = "inline-block";
		ret = true;
	}
	return ret;
}
function testHouse() {
	var ret = false;
	if (document.getElementById("house_per").value == "-1") {
		document.getElementById("tip_house").style.display = "inline-block";
		document.getElementById("tip_house").innerHTML = "请选择您的房贷情况";
		document.getElementById("span_house").style.display = "none";
	} else {
		document.getElementById("tip_house").style.display = "none";
		document.getElementById("span_house").style.display = "inline-block";
		ret = true;
	}
	return ret;
}
function testCar() {
	var ret = false;
	if (document.getElementById("car_per").value == "-1") {
		document.getElementById("tip_car").style.display = "inline-block";
		document.getElementById("tip_car").innerHTML = "请选择您的车贷情况";
		document.getElementById("span_car").style.display = "none";
	} else {
		document.getElementById("tip_car").style.display = "none";
		document.getElementById("span_car").style.display = "inline-block";
		ret = true;
	}
	return ret;
}

function testCredit() {
	var ret = false;
	if (document.getElementById("credit_per").value == "-1") {
		document.getElementById("tip_credit").style.display = "inline-block";
		document.getElementById("tip_credit").innerHTML = "请选择您的信用卡情况";
		document.getElementById("span_credit").style.display = "none";
	} else {
		document.getElementById("tip_credit").style.display = "none";
		document.getElementById("span_credit").style.display = "inline-block";
		ret = true;
	}
	return ret;
}
function subForm() {
	if (testName() && testTelephone() && testMoney() && testDeadline()
			&& testJob() && testArea() && testHouse() && testCar()
			&& testCredit() && testYCode()) {
		return true;
	}
	return false;
}

function testNameCom() {

	var par = /^[\u4e00-\u9fa5]+$/;
	var name = document.getElementById("name_com").value;
	var ret = par.test(name);
	if (!ret) {
		document.getElementById("tip_namecom").style.display = "inline-block";
		document.getElementById("tip_namecom").innerHTML = "请输入正确的姓名";
		document.getElementById("span_namecom").style.display = "none";
		return false;
	} else {
		if(name.length<2){
			document.getElementById("tip_namecom").style.display = "inline-block";
			document.getElementById("tip_namecom").innerHTML = "请输入正确的姓名";
			document.getElementById("span_namecom").style.display = "none";
			return false;
		}
		document.getElementById("tip_namecom").style.display = "none";
		document.getElementById("span_namecom").style.display = "inline-block";
		return true;
	}
	
}

function testTelephoneCom() {
	var par = /^[1][358][0-9]{9}$/;
	var telephone = document.getElementById("phone_com").value;
	var ret = par.test(telephone);
	if (!ret) {
		document.getElementById("tip_telephonecom").style.display = "inline-block";
		document.getElementById("tip_telephonecom").innerHTML = "请输入11位手机号码";
		document.getElementById("span_telephonecom").style.display = "none";
	} else {
		document.getElementById("tip_telephonecom").style.display = "none";
		document.getElementById("span_telephonecom").style.display = "inline-block";
	}
	return ret;
}

function testMoneyCom() {
	var par = /^(?!0+(?:\.0+)?$)\d+(?:\.\d{1,2})?$/;
	var money = document.getElementById("money_com").value;
	var ret = par.test(money);
	if (!ret) {
		document.getElementById("tip_moneycom").style.display = "inline-block";
		document.getElementById("tip_moneycom").innerHTML = "借款金额格式不正确";
		document.getElementById("span_moneycom").style.display = "none";
	} else {
		document.getElementById("tip_moneycom").style.display = "none";
		document.getElementById("span_moneycom").style.display = "inline-block";
	}
	return ret;
}

function testDeadlineCom() {
	var ret = false;
	if (document.getElementById("deadline_com").value == "-1") {
		document.getElementById("tip_deadlinecom").style.display = "inline-block";
		document.getElementById("tip_deadlinecom").innerHTML = "请选择您的借款期限";
		document.getElementById("span_deadlinecom").style.display = "none";
	} else {
		document.getElementById("tip_deadlinecom").style.display = "none";
		document.getElementById("span_deadlinecom").style.display = "inline-block";
		ret = true;
	}
	return ret;
}
function testJobCom() {
	var ret = false;
	if (document.getElementById("job_com").value == "-1") {
		document.getElementById("tip_jobcom").style.display = "inline-block";
		document.getElementById("tip_jobcom").innerHTML = "请选择您的职业";
		document.getElementById("span_jobcom").style.display = "none";
	} else {
		document.getElementById("tip_jobcom").style.display = "none";
		document.getElementById("span_jobcom").style.display = "inline-block";
		ret = true;
	}
	return ret;
}
function testAreaCom() {
	var ret = false;
	if (document.getElementById("area_com").value == "-1") {
		document.getElementById("tip_areacom").style.display = "inline-block";
		document.getElementById("tip_areacom").innerHTML = "请选择您所在的地区";
		document.getElementById("span_areacom").style.display = "none";
	} else {
		document.getElementById("tip_areacom").style.display = "none";
		document.getElementById("span_areacom").style.display = "inline-block";
		ret = true;
	}
	return ret;
}
function testUseCom() {
	var ret = false;
	if (document.getElementById("use_com").value == "") {
		document.getElementById("tip_usecom").style.display = "inline-block";
		document.getElementById("tip_usecom").innerHTML = "请输入借款用途";
		document.getElementById("span_usecom").style.display = "none";
	} else {
		document.getElementById("tip_usecom").style.display = "none";
		document.getElementById("span_usecom").style.display = "inline-block";
		ret = true;
	}
	return ret;
}
function testCompanyCom() {
	var ret = false;
	if (document.getElementById("company_com").value == "") {
		document.getElementById("tip_companycom").style.display = "inline-block";
		document.getElementById("tip_companycom").innerHTML = "请输入公司名称";
		document.getElementById("span_companycom").style.display = "none";
	} else {
		document.getElementById("tip_companycom").style.display = "none";
		document.getElementById("span_companycom").style.display = "inline-block";
		ret = true;
	}
	return ret;
}

function testEstablishCom() {
	//alert(document.getElementById("establish_com").value);
	var ret = false;
	if (document.getElementById("establish_com").value == "") {
		document.getElementById("tip_establishcom").style.display = "inline-block";
		document.getElementById("tip_establishcom").innerHTML = "请选择成立日期";
		document.getElementById("span_establishcom").style.display = "none";
	} else {
		document.getElementById("tip_establishcom").style.display = "none";
		document.getElementById("span_establishcom").style.display = "inline-block";
		ret = true;
	}
	return ret;
}

function testRegistCom() {
	var par = /^(?!0+(?:\.0+)?$)\d+(?:\.\d{1,2})?$/;
	var money = document.getElementById("regist_com").value;
	var ret = par.test(money);
	if (!ret) {
		document.getElementById("tip_registcom").style.display = "inline-block";
		document.getElementById("tip_registcom").innerHTML = "注册资金格式不正确";
		document.getElementById("span_registcom").style.display = "none";
	} else {
		document.getElementById("tip_registcom").style.display = "none";
		document.getElementById("span_registcom").style.display = "inline-block";
	}
	return ret;
}
function testBankCom() {
	var ret = false;
	if (document.getElementById("bank_com").value == "-1") {
		document.getElementById("tip_bankcom").style.display = "inline-block";
		document.getElementById("tip_bankcom").innerHTML = "请选择";
		document.getElementById("span_bankcom").style.display = "none";
	} else {
		document.getElementById("tip_bankcom").style.display = "none";
		document.getElementById("span_bankcom").style.display = "inline-block";
		ret = true;
	}
	return ret;
}
function subFormCom() {
	if (testNameCom() && testTelephoneCom() && testMoneyCom()
			&& testDeadlineCom() && testUseCom() && testCompanyCom()
			&& testJobCom() && testAreaCom() && testEstablishCom()
			&& testRegistCom() && testBankCom() && testCode()) {
		return true;
	}
	return false;
}
