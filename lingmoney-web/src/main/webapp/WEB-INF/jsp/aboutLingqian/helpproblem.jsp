<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>
<style type="text/css">
.problem{width:744px;margin:0 auto;}
.problem-nav{overflow: hidden;}
.problem-nav li{float: left;width:124px;height:67px;text-align: center;cursor:pointer }
.problem-nav li p{height:55px;line-height:55px;border-bottom: solid #ea5513 2px;font-size:16px;color:#666;}
.problem-nav li div{width:18px;height:12px;font-size:0px;text-align: center; margin-left:53px;}
.kebi{background: url("resource/images/small-1.png")no-repeat;line-height:0px;height:12px;}
.problem-warp{display:none}
.select{display:block}
.problem-tuwen{text-align:right;line-height:30px; padding-top:14px;color:#ea5513}
.problem-tuwen a:link{color:#ea5513}
.problem-tuwen a:visited{color:#ea5513}
.problem-tuwen a:hover{color:#ea5513}
.problem-box-ul{display:block;overflow: hidden; line-height:38px;padding:12px 0px;}
.problem-box-ul-l{float:left;font-size:16px; color:#4d4e4a;width:700px;}
.problem-box-ul-r{float:right;cursor:pointer}
.problem-box{overflow: hidden; border-bottom: solid #eeeeee 1px;}
.problem-none{display:none; font-size:14px; color:#717171;line-height:26px; padding-bottom:24px;padding-left:22px;padding-right:22px;}

.problem-fud{position:relative;    display: block;}
.problem-fud i{position:absolute; top:2px;right:0px;display:block;}
.problem-fud i img{width:20px;}

.problem-box-ul-l span{color:#ea5513;padding:5px;}
.problem-none p{line-height:40px;}
.problem-huangse{color:#ea5513}
.problem-disnono{display:none;}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$("#helpproblem").addClass("hover");

		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");

		/*新手指南效果*/
		$(".ul-guide li span").hover(function() {
			$(this).css("cursor", "pointer");
		})
		$(".ul-guide-dl").hide();
		$(".ul-guide li span").click(function() {
			$(".ul-guide-dl").stop().slideUp();
			$(this).next("dl").stop().slideDown();
		})
		
		$(".problem-nav li").click(function(){
        var index=$(".problem-nav li").index(this)
        $(this).find('div').addClass("kebi")
        $(this).siblings().find('div').removeClass("kebi")
        $(this).find('p').css('color','#ea5513')
          $(this).siblings().find('p').css('color','')
        $(".problem-warp").eq(index).addClass("select").siblings().removeClass("select")
       
    })
    
     $(".problem-box-ul-r").click(function(){
    	   if($(this).attr('data-i')==0){
    		   $(this).closest('.problem-box-ul').closest('.problem-box').find('.problem-none').css('display','block')
    		   $(this).find('img').attr('src','/resource/images/small-02.png')
    		   $(this).attr('data-i','1')
    		   
    	   }else{
    		   $(this).closest('.problem-box-ul').closest('.problem-box').find('.problem-none').css('display','none')
    		     $(this).find('img').attr('src','/resource/images/small-2.png')
    		   $(this).attr('data-i','0')
    	   }
        	
        })

	})
	$(function(){
		
		
		$('.problem-fud >i').click(function(){
			if($(this).attr('data-i')==0){
				$(this).closest('.problem-fud').find('.problem-disnono').show(); 
				$(this).attr('data-i','1');
				$(this).find('img').attr('src','/resource/images/Chevron_Copy2.png')
			}else{
				$(this).closest('.problem-fud').find('.problem-disnono').hide();
				$(this).attr('data-i','0')
				$(this).find('img').attr('src','/resource/images/ChevronCopy.png')
			}
			 
		})
	});
	
</script>




</head>
<body>

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<div class="mainbody">
		<div class="box clearfix">
			<div class="sitemap">
				<a href="/index.html">领钱儿</a>&nbsp;>&nbsp;<span>帮助中心</span>
			</div>
			<!-- 帮助中心开始 -->
			<jsp:include page="/common/helpcenter.jsp"></jsp:include>
			<!-- 帮助中心结束 -->

			<div class="mRight">
				<div class="mRight01">
					<div class="htitle">常见问题</div>

                        <div class="problem">
                        	<div class="problem-nav">
                        		<ul>
                        			<li><p style="color: rgb(234, 85, 19);">开通E账户</p>
                        			<div class="kebi"></div>
                        			</li>
                        			<li><p>激活绑卡</p>
                        			   <div></div>
                        			</li>
                        			<li><p>充值</p>
                        			<div></div>
                        			</li>
                        			<li><p>提现</p>
                        			<div></div>
                        			</li>
                        			<li><p>实名认证</p>
                        			<div></div>
                        			</li>
                        			<li><p>购买</p>
                        			<div></div>
                        			</li>
                        		</ul>
                        	</div>
                       <!--    切换内容 -->
                            
                            <div class="problem-warp select" >
                            <p class="problem-tuwen"><a href="hxbankOpertion#pc1">图文指引></a></p>
                             	<div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">1、什么是华兴E账户？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
									华兴E账户是用户在广东华兴银行开立的银行存管专用户，每人只能开立一个华兴E账户。<br>
                             		</div>
                             	</div>
                             	
                             	<div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">2、开通华兴E账户过程中需要注意什么？对浏览器和操作系统有要求吗？需要下载银行控件吗？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
                             		 ① 由于华兴银行安装环境要求，浏览器建议使用：IE(含IE8、9、10)及IE内核浏览器、Firefox火狐浏览器；<br>
									操作系统支持：Windows XP、Windows Vista、Windows7、Windows8；<br>
									② 开通账户时，您需要下载华兴银行控件；在安装控件过程中，请勿手动刷新或关闭密码设置页面。控件安装完成后，点击“请安装控件后点击此处刷新浏览器”进行刷新操作。<br>
									③ 在开户操作结束时，如果页面提示“对不起，您的开户关联失败！”，建议关闭此页面并重新进行开户操作。<br>
                             		
                             		</div>
                             	</div>
                             	
                             	
                             	<div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">3、开通华兴E账户浏览器跳转不到华兴银行界面，浏览器拦截怎么解决？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
                             		 以下列举了常用浏览器拦截新页面打开的解决方法<br>
									<span class="problem-huangse problem-fud">Internet Explorer浏览器 <i data-i="0"><img src="/resource/images/ChevronCopy.png"/></i>
									 
									<div class="problem-disnono">
									  <p>1、打开浏览器，在浏览器菜单上选择“工具”图标，点击【Internet选项】。</p>
									
                             		<img src="/resource/images/IE1.png"/><br>
                             		<p>2、在窗口中选择“隐私”选项，允许弹出窗口，取消勾选项。</p>
                             		<img src="/resource/images/IE2.png"/>
                             		</div>
                             		
                             		</span>
                             		
                             		
                             		<span class="problem-huangse problem-fud">Chrome谷歌浏览器 <i data-i="0"><img src="/resource/images/ChevronCopy.png"/></i>
									<div class="problem-disnono">
									  <p>1、打开浏览器，在浏览器菜单上选择“打开菜单”，点击【设置】。</p>
									
                             		<img src="/resource/images/chrome1.png"/><br>
                             		<p>2、进入设置，点击【显示高级设置】。</p>
                             		<img src="/resource/images/chrome2.png"/>
                             		<p>3、在隐私设置栏，点击【内容设置】。</p>
									
                             		<img src="/resource/images/chrome3.png"/><br>
                             		<p>4、选择弹出式窗口，点击【允许所有网站显示弹出式窗口】。</p>
                             		<img src="/resource/images/chrome4.png"/>
									
									</div>
									</span>
									  
									
									
									
                             		
                             		
                             		<span class="problem-huangse problem-fud">360Chrome浏览器 <i data-i="0"><img src="/resource/images/ChevronCopy.png"/></i>
									
									 <div class="problem-disnono">
									   <p>1、打开浏览器， 在浏览器菜单上选择“打开菜单”，点击【选项】。</p>
									
	                             		<img src="/resource/images/3601.png"/><br>
	                             		<p>2、进入选项—广告拦截页面，选择【广告拦截】菜单，如右侧内容</p>
	                             		<img src="/resource/images/3602.png"/>
									 </div>
									
                             		</span>
                             		<span class="problem-huangse problem-fud">Firefox火狐浏览器 <i data-i="0"><img src="/resource/images/ChevronCopy.png"/></i>
									
									 <div class="problem-disnono">
									 
									   <p>1、打开浏览器， 在浏览器菜单上点击【选项】。</p>
									
                             		<img src="/resource/images/huohu1.png"/><br>
                             		<p>2、选择“隐私与安全”菜单，取消“拦截弹出式窗口”。</p>
                             		<img src="/resource/images/huohu2.png"/>
									 
									 </div>
									</span>
                             		
                             		
                             		</div>
                             	</div>
                             	
                             </div>
                            
                             <div class="problem-warp">
                              <p class="problem-tuwen"><a href="hxbankOpertion#pc2">图文指引></a></p>
	                             <div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">1、为什么要绑定银行卡？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
									绑定银行卡可以激活华兴银行E账户，做到银行账户一对一，让资金结算更加安全可靠。
                             		</div>
		                          </div>
		                          <div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">2、绑定银行卡需要注意哪些问题？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
									绑定银行卡预留手机号码必须与华兴银行绑定手机号码一致,如不一致，您可登录华兴银行“投融资平台”在个人信息栏目下修改手机号码。
                             		</div>
		                          </div>
		                          <div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">3、一个账户可以绑定多少张银行卡？能绑定其他人的卡吗？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
									目前一个账户只能绑定一张银行卡，且只支持储蓄卡和绑定您自己本人同名同证件的银行卡，不支持绑定信用卡。
                             		</div>
		                          </div>
		                          
		                          <div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">4、支持绑定的银行卡有哪些？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
									<b>银行卡绑定有三种方式：</b>中金支付公司绑卡（推荐）、来账绑卡、华兴银行本行卡绑定。<br>
									<b>中金支付公司绑卡支持的银行：</b>中国银行、中国建设银行、工商银行、交通银行、光大银行、平安银行、广发银行、浦发银行、兴业银行、农业银行、浙商银行、邮储银行、中信银行<br>
									<b>来账绑卡支持的银行：</b>中国银行、农业银行、工商银行、建设银行、交通银行、招商银行、中信银行、平安银行、民生银行、光大银行、广发银行、上海浦东发展银行、深圳前海微众银行。
                             		</div>
		                          </div>
                               </div>
                                
                              <div class="problem-warp">
                              <p class="problem-tuwen"><a href="hxbankOpertion#pc7">图文指引></a></p>
                                <div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">1、如何充值？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
									<!-- 目前支持2种充值方式<br> -->
您可以使用您已绑定的银行卡，通过手机银行、网银方式将资金转账至您的华兴银行存管账户中即可完成充值。
<!-- 2.银行卡充值（T+1个工作日到帐）<br>
具体到账时间银行为准。<br>
请根据自己的需求选择合适的充值方式。<br> -->

                             		</div>
		                          </div>
		                          
		                          
		                         <!--  <div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">2、从华兴银行E账户充值到领钱儿有手续费吗？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
									所有充值手续费由领钱儿平台为客户垫付，客户无需支付任何额外的充值费用。
                             		</div>
		                          </div>
		                           -->
		                          
		                           <div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">2、充值金额有限制吗？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
									充值金额无限制。
                             		</div>
		                          </div>
		                          
		                          <div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">3、转账至E账户，开户行找不到广东华兴银行怎么办？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
									目前有少数银行在转账过程中选取收款银行开户行时找不到“广东华兴银行”的情况，如未找到“广东华兴银行”可选择“城市商业银行—广东华兴银行股份有限公司”。
                             		</div>
		                          </div>
		                          <!-- <div class="problem-box">
                             		<ul class="problem-box-ul">
                             			<li class="problem-box-ul-l">5、从银行卡转账到E账户，平台的账户余额为什么没有增加？</li>
                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
                             		</ul>
                             		<div class="problem-none">
									从银行卡转账到E账户，可在华兴“投融资平台”APP查看余额（投融资平台APP下载二维码见下方）。若在领钱儿“我的账户”中进行“充值”，从E账户充值到领钱儿存管子账户上，才能在账户可用余额里看到。
									<img style="display: block; margin: 0px auto;" alt="" src="/resource/images/hxewm.png">
                             		</div>
		                          </div> -->

                              </div>
                              
                             
                               <div class="problem-warp">
                                <p class="problem-tuwen"><a href="hxbankOpertion#pc6">图文指引></a></p>
                               		 <div class="problem-box">
	                             		<ul class="problem-box-ul">
	                             			<li class="problem-box-ul-l">1、平台提现有手续吗？</li>
	                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
	                             		</ul>
	                             		<div class="problem-none">
										平台提现是免费的
	                             		</div>
		                          </div>
		                          
		                          <div class="problem-box">
	                             		<ul class="problem-box-ul">
	                             			<li class="problem-box-ul-l">2、提现金额有没有限制？</li>
	                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
	                             		</ul>
	                             		<div class="problem-none">
										提现金额无限制
	                             		</div>
		                          </div>
		                          <div class="problem-box">
	                             		<ul class="problem-box-ul">
	                             			<li class="problem-box-ul-l">3、提现失败后，账户可提现金额为什么是0？</li>
	                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
	                             		</ul>
	                             		<div class="problem-none">
										因为资金在广东华兴银行已经冻结，一般30分钟资金会解冻，具体时间以银行为准，之后再重新提现即可。
	                             		</div>
		                          </div>
		                          
		                          <div class="problem-box">
	                             		<ul class="problem-box-ul">
	                             			<li class="problem-box-ul-l">4、提现多久到账？</li>
	                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
	                             		</ul>
	                             		<div class="problem-none">
										<p>提现至已绑定的银行卡，根据提现选择的汇款方式以及金额大小，具体到账时间有所不同。</p>
										<p><b>实时：</b>交易金额小于（含）5万，实时转出（含工作日及节假日）。</p>
										<p><b>加急：</b>交易金额无限制，工作日08:30-16:59内发起提现，实时转出（具体到账时间，视乎收款银行系统处理效率）；非工作日08:30-16:59以外时间，下一个工作日08:30后转出。</p>
										<p><b>普通：</b>交易金额小于（含）5万元，2小时以内转出（含工作日及节假日，具体到账时间，视乎收款银行系统处理效率)。</p>
	                             		</div>
		                          </div>
		                          
		                           <div class="problem-box">
	                             		<ul class="problem-box-ul">
	                             			<li class="problem-box-ul-l">5、冻结金额是什么意思？</li>
	                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
	                             		</ul>
	                             		<div class="problem-none">
										冻结金额是指投标或申请提现时，华兴银行将资金锁定，操作成功后立即解冻。如遇到输错密码、网络不通、操作超时、页面关闭等问题操作不成功时资金将被继续锁定，30分钟内资金解冻回到账户余额中。

	                             		</div>
		                          </div>
		                          
		                          <div class="problem-box">
	                             		<ul class="problem-box-ul">
	                             			<li class="problem-box-ul-l">6、提现为什么需要实名认证？如何认证？</li>
	                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
	                             		</ul>
	                             		<div class="problem-none">
										根据中国人民银行《关于改进个人银行账户服务加强账户管理的通知》的要求，为强化银行账户管理、防范资金风险，电子账户必须完成实名认证才能提现。<br>
认证方式：<br>
1. 下载华兴银行“投融资平台”APP；<br>
2. APP左上角（图标“≡”）-个人中心-实名认证；<br>
3. 进行人脸识别。<br>
认证时间：<br>
1. 工作日周一到周五8:30-17:30，当天审核完17:30以前的申请；<br>
2. 周六周日、法定节假日10:00-18:00，当天审核完18:00以前的申请。长假期审批时间另行通知。<br>


	                             		</div>
		                          </div>

                               </div>
                                <div class="problem-warp">
                                	<p class="problem-tuwen"><a href="hxbankOpertion#pc5">图文指引></a></p>
                                	<div class="problem-box">
	                             		<ul class="problem-box-ul">
	                             			<li class="problem-box-ul-l">1、实名认证审核时间需要多久？</li>
	                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
	                             		</ul>
	                             		<div class="problem-none">
										工作日周一到周五8:30-17:30，当天审核完17:30以前的申请；<br>
                                                                                                       周六周日、法定节假日10:00-18:00，当天审核完18:00以前的申请。长假期审批时间另行通知。  

	                             		</div>
		                          </div>
		                          
		                       <!--    <div class="problem-box">
	                             		<ul class="problem-box-ul">
	                             			<li class="problem-box-ul-l">2、实名认证为什么不通过？</li>
	                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
	                             		</ul>
	                             		<div class="problem-none">
										答：主要由于客户在公安局联网核查系统留存照片不清晰或者是留存照片时间过于久远导致。

	                             		</div>
		                          </div> -->
		                          
		                           <div class="problem-box">
	                             		<ul class="problem-box-ul">
	                             			<li class="problem-box-ul-l">2、人脸识别操作的注意事项？</li>
	                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
	                             		</ul>
	                             		<div class="problem-none">
										答：人脸识别注意事项：<br>
1、请在光线充足的环境下操作，例如：白天室内环境下，请您临近窗户拍摄；<br>
2、请保持头部始终在手机屏幕的头型虚线框内；<br>
3、请在有效的时间内，按照手机屏幕下方的动画指引，以稍快的频率和稍大的幅度分别完成张嘴、抬头、点头等的动作；<br>
4、若由于手机兼容性不支持或其他原因，导致人脸识别失败或无法上传身份证资料的情况，请您更换其他手机进行认证。<br>


	                             		</div>
		                          </div>

                                </div>
                               
                                <div class="problem-warp">
                                 <p class="problem-tuwen"><a href="hxbankOpertion#pc4">图文指引></a></p>
                                 	<div class="problem-box">
	                             		<ul class="problem-box-ul">
	                             			<li class="problem-box-ul-l">1、购买成功后，资金为什么显示支付中？</li>
	                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
	                             		</ul>
	                             		<div class="problem-none">
										由于网络原因或银行系统处理慢，资金会冻结并显示支付中，根据银行系统处理结果，状态会实时刷新。


	                             		</div>
		                          </div>
		                          
		                          
		                          <div class="problem-box">
	                             		<ul class="problem-box-ul">
	                             			<li class="problem-box-ul-l">2、购买成功后是否可以取消？</li>
	                             			<li class="problem-box-ul-r" data-i="0"><img src="/resource/images/small-2.png"/><li>
	                             		</ul>
	                             		<div class="problem-none">
										购买成功后不允许取消，若此项目满标并发放资金后，您账户上的理财冻结金额自动将转入该项目借款人账户。若此项目流标，则您账户上的理财冻结金额将自动转为可用余额。


	                             		</div>
		                          </div>
                                
                                </div>
                        </div> 

				</div>

			</div>
			<div style="clear: both"></div>
		</div>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>