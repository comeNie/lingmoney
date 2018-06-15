/**
 * 生成N位随机数字
 * @param length
 * @returns {String}
 */
function getRandomNum(length){
    var rnd = "";
    for (var i = 0; i < n; i++) {
        rnd += Math.floor(Math.random() * 10);
    }
    return rnd;
}

/**
 * 生成uuid
 * 1.不指定参数则生成正常32位UUID
 * 2.指定长度生成数字字母N位随机串
 * 3.指定基数生成N位内的数字字母随机串
 * @param len 指定uuid的长度
 * @param radix 基数 
 * @returns
 */
function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
    if (len) {
      // 填充
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
    	
      var r;
      // 分割字符串
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      uuid[14] = '4';
 
      // 填充随机字符
      for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random()*16;
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
        }
      }
    }
    return uuid.join('');
}

