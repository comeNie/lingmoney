/**
 * 验证身份证号格式
 * @param idCard
 * @returns {Boolean}
 */
function checkIdNo(idCard){
	// 身份证正则表达式(15位)
	var isIDCard1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
	// 身份证正则表达式(18位)
	var isIDCard2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X|x)$/;
	var ret1 = isIDCard1.test(idCard);
	var ret2 = isIDCard2.test(idCard);
	if (!ret1 && !ret2) return false;
	return true;
}

/**
 * 验证姓名格式
 * @param name
 * @returns {Boolean}
 */
function checkName(name){
	if (name.length < 2) return false;
	
	var par = /^[\u4e00-\u9fa5]+$/;
	var ret = par.test(name);
	if (!ret) return false;
	
	return true;
}

/**
 * 验证手机号
 * @param telephone
 * @returns {Boolean}
 */
function checkTelephone(telephone){
	var par=/^[1][34578][0-9]{9}$/;
	if (!par.test(telephone)) return false;
	return true;
}

/**
 * 验证验证码[6位字母或数字]
 * @param vailCode
 * @returns {Boolean}
 */
function checkVailCode(vailCode){
	var par=/^([0-9|A-Za-z]{6})$/;
	if (!par.test(vailCode)) return false;
	return true;
}