package com.mrbt.lingmoney.bank.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 华兴银行错误码提示
 * 
 * 当错误码为OGWERR999999，请各自外围系统统一显示为：“系统处理异常”给客户，实际返回的错误信息为：系统处理异常+(服务流水号)，
 * 例如：系统处理异常(OGW01201603010702585420), 方便外联系统快速定位错误信息。
 * 当错误码为OGWERR999998，表示系统超时。
 * @author ruochen.yu
 *
 */
public class HxReturndErrorCode {
	static Map<String, String> errorMsg = new HashMap<String, String>();
	
	static{
		errorMsg.put("OGWERR000001","验签失败");
		errorMsg.put("OGWERR000002","签名失败");
		errorMsg.put("OGWERR000003","系统逻辑处理异常");
		errorMsg.put("OGWERR000004","请勿重复提交");
		errorMsg.put("OGWERR000005","md5摘要提取失败");
		errorMsg.put("OGWERR000006","系统交易处理异常");
		errorMsg.put("OGWERR100001","{key}为必输项!");
		errorMsg.put("OGWERR100002","{key}不能为空!");
		errorMsg.put("OGWERR100003","{key}超过合法长度!");
		errorMsg.put("OGWERR100004","{key}校验失败!");
		errorMsg.put("OGWERR100005","商户号不能为空");
		errorMsg.put("OGWERR100006","获取商户信息失败");
		errorMsg.put("OGWERR100007","商户不存在");
		errorMsg.put("OGWERR100008","商户ip不合法");
		errorMsg.put("OGWERR200001","获取数据库sequence失败");
		errorMsg.put("OGWERR200002","商户交易信息记录失败");
		errorMsg.put("OGWERR200003","外联网关交易记录失败");
		errorMsg.put("OGWERR200004","通信组件交易信息记录失败");
		errorMsg.put("OGWERR200005","通讯组件错误信息记录失败");
		errorMsg.put("EAS020010001","操作数据库失败");
		errorMsg.put("EAS020120010","输入待冲正前台流水号已冲正");
		errorMsg.put("EAS020120133","输入户名不正确");
		errorMsg.put("EAS020120134","输入交易金额格式有误");
		errorMsg.put("EAS020120138","当前账户已经注销");
		errorMsg.put("EAS020120139","不支持对冲正交易再次冲正");
		errorMsg.put("EAS020120140","不可以隔日沖正");
		errorMsg.put("EAS020200025","开始时间不能大于结束日期");
		errorMsg.put("EAS020200010","只能查询两年内的数据");
		errorMsg.put("EAS020200008","查询时间区间不能大于三个月");
		errorMsg.put("EAS020700006","录入解冻金额与冻结记录冻结金额不一致");
		errorMsg.put("EAS020700007","录入E账号与冻结记录E账号不相符");
		errorMsg.put("EAS020700008","录入冻结编号与冻结记录冻结编号不相符");
		errorMsg.put("EAS020700009","查询E账户金额冻结记录,至少输入E账号、订单号、冻结编号三者其中之一");
		errorMsg.put("EAS020700010","E账户冻结记录与订单记录不相符");
		errorMsg.put("EAS020700012","推送的项目已经存在");
		errorMsg.put("EAS020700013","项目不存在");
		errorMsg.put("EAS020700014","根据项目ID和项目编号查询到多个项目");
		errorMsg.put("EAS020700015","获取批量解冻文件路径时，操作数据库失败");
		errorMsg.put("EAS020700016","获取的批量解冻文件路径为空");
		errorMsg.put("EAS020700017","下载的批量解冻文件不符合要求");
		errorMsg.put("EAS020700018","调用服务失败");
		errorMsg.put("EAS020700019","根据流水号查询批次信息失败");
		errorMsg.put("EAS020700020","批量解冻结果查询时,查询流水号和批次号两者必输其一");
		errorMsg.put("EAS020700021","批量资金划转时录入批量订单类型不合法");
		errorMsg.put("EAS020700022","调用服务-统一支付执行标准订单失败");
		errorMsg.put("EAS020700024","查询资产交易平台资金归集内部账户信息失败");
		errorMsg.put("EAS020700025","获取批量资金划转文件路径时操作数据库失败");
		errorMsg.put("EAS020700026","获取的批量资金划转文件路径为空");
		errorMsg.put("EAS020700027","资产交易平台放款时调用统一支付服务-执行标准订单失败");
		errorMsg.put("EAS020700029","资产交易平台放款时查询出放款金额为合法");
		errorMsg.put("EAS020700030","资产交易平台登记还款文件明细信息时调用服务-创建支付订单失败");
		errorMsg.put("EAS020700032","项目已经申请还款且还未处理,不允许重复申请");
		errorMsg.put("EAS020700034","资产交易平台还款时根据订单号查询不到订单信息");
		errorMsg.put("EAS020400001","后台系统没收到交易请求，交易失败");
		errorMsg.put("OGW100200009","接口访问过于频繁，请5分钟后再访问");
		errorMsg.put("EAS020010001","操作数据库失败");
		errorMsg.put("EAS020120010","输入待冲正前台流水号已冲正");
		errorMsg.put("EAS020120133","输入户名不正确");
		errorMsg.put("EAS020120134","输入交易金额格式有误");
		errorMsg.put("EAS020120138","当前账户已经注销");
		errorMsg.put("EAS020120139","不支持对冲正交易再次冲正");
		errorMsg.put("EAS020120140","不可以隔日沖正");
		errorMsg.put("EAS020200025","开始时间不能大于结束日期");
		errorMsg.put("EAS020200010","只能查询两年内的数据");
		errorMsg.put("EAS020200008","查询时间区间不能大于三个月");
		errorMsg.put("SP00001","创建订单时录入产品号不存在");
		errorMsg.put("SP00002","创建订单时录入订单角色当事人不存在");
		errorMsg.put("SP00003","创建订单时录入订单角色类型不存在");
		errorMsg.put("SP00004","创建订单时录入付款方信息为空");
		errorMsg.put("SP00005","订单总金额与付款金额不相等");
		errorMsg.put("SP00006","根据订单号查询订单信息不存在");
		errorMsg.put("SP00007","订单状态与修改后状态一致");
		errorMsg.put("SP00008","根据订单号查询订单状态不存在");
		errorMsg.put("EAS020700035","资产交易平台还款时修改订单状态为处理中时，操作数据库失败");
		errorMsg.put("EAS020700036","资产交易平台还款时根据订单号查询不到还款明细信息");
		errorMsg.put("EAS020700037","资产交易平台还款查询还款申请批次信息失败");
		errorMsg.put("EAS020700038","资产交易平台还款信息查询还款状态不合法");
		errorMsg.put("EAS020700039","资产交易平台还款审核录入操作类型不合法");
		errorMsg.put("EAS020700040","不支持的币种");
		errorMsg.put("BIP019000006","映射输入参数异常");
		errorMsg.put("BIP019000007","映射输出参数异常");
		errorMsg.put("BIP019000008","不存在的活动定义");
		errorMsg.put("BIP019000009","流程超时");
		errorMsg.put("BIP019000010","不可识别的活动定义类型");
		errorMsg.put("BIP019000011","不存在的Beanshell脚本生成的java类");
		errorMsg.put("BIP019000012","记录流程开始流水异常");
		errorMsg.put("BIP019000013","记录流程结束流水异常");
		errorMsg.put("BIP019000014","记录活动开始流水异常");
		errorMsg.put("BIP019000015","记录活动结束流水异常");
		errorMsg.put("BIP019000016","不存在的Beanshell类执行方法");
		errorMsg.put("BIP019000017","Beanshell类方法执行异常");
		errorMsg.put("BIP019000018","当前活动在运行中，无法产生下一步活动");
		errorMsg.put("BIP019000019","符合条件的迁移定义超过1条");
		errorMsg.put("BIP019000020","控制类活动不能被重试");
		errorMsg.put("BIP019000021","系统错误");
		errorMsg.put("BIP019000022","服务拒绝");
		errorMsg.put("BIP019000023","服务超时");
		errorMsg.put("BIP019000024","没有符合条件的迁移定义");
		errorMsg.put("BIP019000025","活动异常时没有可执行的异常策略");
		errorMsg.put("BIP019000148","执行远程活动异常");
		errorMsg.put("BIP019000027","服务ID为空");
		errorMsg.put("BIP019000028","服务定义不存在");
		errorMsg.put("BIP019000029","从数据库流水中创建流程实例异常");
		errorMsg.put("BIP019000032","冲正达最大次数");
		errorMsg.put("BIP019000033","创建事后冲正任务异常");
		errorMsg.put("BIP019000034","本地组件调用异常");
		errorMsg.put("BIP019000035","本地组件执行失败");
		errorMsg.put("BIP019000037","调用后台系统失败");
		errorMsg.put("OGWERR999997","无此交易流水");
		errorMsg.put("OGWERR999999","系统处理异常");
		errorMsg.put("OGWERR999998","系统超时");
	}
	
	/**
	 * 通过华兴银行返回码，查找说明
	 * @param errorCode
	 * @return
	 */
	public static String giveErrorMsg(String errorCode){
		if(errorMsg.containsKey(errorCode)){
			return errorMsg.get(errorCode);
		}else{
			return "错误码不存在";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(giveErrorMsg("OGWERR000001"));
	}
}
