<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
 <!DOCTYPE html>
 <html lang="zh">
 <head>
 	<meta charset="UTF-8">
 	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable = yes,minimum-scale=1.0,maximum-scale=2.0">
 	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
 	<title>领钱儿 专业的互联网金融理财平台</title>
 	<link rel="stylesheet" href="/resource/css/index.css">
 	<link rel="stylesheet" type="text/css" href="/resource/css/others.css" />
	<link rel="stylesheet" type="text/css" href="/resource/css/style.css" /> 
	<link rel="stylesheet" type="text/css" href="/resource/css/qdcss/qingdao.css" /> 
    <link rel="icon" type="image/x-icon" href="/resource/images/ico.ico"/>
    <link rel="shortcut icon" type="/resource/images/image/x-icon" href="ico.ico"/>

   <script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
   <script type="text/javascript" src="/resource/js/yxMobileSlider.js"></script>
   <script type="text/javascript" src="/resource/js/kxbdSuperMarquee.js"></script>
   <script type="text/javascript" src="/resource/js/jquery.SuperSlide.2.1.2.js"></script>
   <script type="text/javascript" src="/resource/js/ajax.js"></script>
   <script type="text/javascript" src="/resource/js/users/login.js"></script>
   
   <jsp:include page="/common/kefu.jsp"></jsp:include>
   
<style type="text/css">
#nav01{
     color:#ea5514;
	background:url(/resource/images/bg_nava.png) no-repeat center bottom; 
 }  
</style>

 </head>
 <body style="position:relative;z-index:1;background:#fafafa">
 
 	<jsp:include page="/common/calculator.jsp"></jsp:include>

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
<div id="rz-box-bg"></div>	
<input type="hidden" id="session" value="${CURRENT_USER }" />
<div class="welcome-qd">
    <span>x</span>
    <a href="/qdjsp/qdDetail.html"></a>
 	<img src="/resource/images/qdimages/welcome.png" />
 </div>
 	<!--banner -->
        <div class="fullSlide">
    	  <div class="bd">
    	    <ul>
	   	     	<c:forEach items="${listInfoBigBanner }" var="item">
					<li _src="url(${item.path })"
					style="background: #81d5c6 center 0 no-repeat;"><a
					href="${item.url }"></a></li>
				</c:forEach>      
    	    </ul>
    	  </div>
    	  <div class="hd">
    	    <ul>
    	    </ul>
    	  </div>
    	  
    	</div> 

	<!--快速登录-->
	<div class="register_box">
            <div class="register">
			<ul class="clearfix">
				<li><a href="javascript:void(0)">平台收益率</a></li>
				<li><a href="javascript:void(0)">用户登录</a></li>
			</ul>
			<ol>
				<li
					style="padding-top: 25px; *padding-top: 35px; padding-top: 35px; padding-left: 18px;">
					<img src="/resource/images/imgtop.gif" />
				</li>
				<li class="form">
					<p id="p_login_t" style="display: none">账户或密码错误！</p>
					<form onsubmit="return login()">
						<c:if test="${CURRENT_USER==null }">
							<table width="234" style="margin: 0 auto; display: block">
								<tr>
									<td colspan="2">
										<div class="form-div">
											<i
												style="background: url('/resource/images/bg_input.gif') no-repeat left top"></i>
											<input type="text" name="account" id="account" required
												placeholder="手机号码/用户名" onfocus="this.style.color='#434343'" />

										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="form-div">
											<i
												style="background: url('/resource/images/bg_input.gif') no-repeat left bottom"></i>
											<input type="password" id="password" name="password"
												placeholder="登录密码" onfocus="this.style.color='#434343'" />
										</div>
									</td>
								</tr>


								<tr>

									<td align="right"><a href="/users/forgetpassword">忘记密码？</a></td>
								</tr>
								<tr>
									<td colspan="2" height="45px" align="center"><input
										type="image" src="/resource/images/button_zhuce.png"
										style="padding-left: 0; background: none; border: none" /></td>
								</tr>
								<tr>
									<td align="center" height="25px;" colspan="2">还没有领钱儿帐号？<a
										href="/users/regist" style="color: #ed6e31">注册</a></td>
								</tr>
								<tr>
									<td colspan="2"
										style="padding-left: 35px; background: url('/resource/images/bao.png') no-repeat 20px center; font-size: 12px">领钱儿保障您的账户资金流转安全</td>
								</tr>
							</table>
						</c:if>
						<c:if test="${CURRENT_USER!=null }">
							<div class="denglu2" style="display: block">
								<div class="denglu2_div1">
									<p class="Cname_p">您当前登录的账户是：</p>
									<p class="Cname">${CURRENT_USER.nickName==null?CURRENT_USER.loginAccount:CURRENT_USER.nickName }</p>
								</div>
								<div style="padding-top: 10px; text-align: center">
									<a href="/myLingqian/show"
										style="display: block; margin: 0 auto; width: 214px; height: 34px;"><img
										src="/resource/images/enter.png" width="214px" height="34px"></a>
								</div>

							</div>
						</c:if>

					</form>

				</li>
			</ol>
		</div>

	</div>
 <!--快速登录-->
 
    <!--活动-->
    <div class="main2">
        
    </div>
    
    <!--特色-->
    <div class="tese-box">
        <div class="top-body">
        	<ul class="tese clearfix">
		    	<li class="tese-con">
		    		<i class="icon icon-tese1"></i>
		    		<strong style="color:#2ca6e0">真实透明</strong>
		    		<p>项目真实，手续齐全。数据透明，接受查阅。</p>
		    	</li>
		    	<li class="jiange"></li>
		    	<li class="tese-con">
		    		<i class="icon icon-tese2"></i>
		    		<strong style="color:#e93240">严把风控</strong>
		    		<p>构建整套风险识别、风险评估及风险监控体系。</p>
		    	</li>
		    	<li class="jiange"></li>
		    	<li class="tese-con">
		    		<i class="icon icon-tese3"></i>
		    		<strong style="color:#e3007f">高稳收益</strong>
		    		<p>10%预期年华收益率比银行理财翻两倍。</p>
		    	</li>
		    	<li class="jiange"></li>
		    	<li class="tese-con">
		    		<i class="icon icon-tese4"></i>
		    		<strong style="color:#f29600">贴心服务</strong>
		    		<p>快乐工作、贴心服务只为您的点滴满意。</p>
		    	</li>
		    </ul>
		    <div class="tese-bottom"></div>
        </div>  	
    </div>
    
 	<div class="main-body" style="background:#fafafa">
 		<div class="theme clearfix">
 			<div class="left">
				<div class="title-box">
					<i class="icon icon-recommend left"></i>
					<strong>精品推荐</strong>
					<span>市场有风险，投资需谨慎</span>			
				</div>
				<div class="product-recommend">
				  <ul class="clearfix">
					<c:forEach items="${listx}" var="plist" varStatus="product">
						<c:if test="${product.index<=2}">
						<c:choose>
							<c:when test="${product.index == 2 }">
								<li  class="last">
							</c:when>
							<c:otherwise>
								<li>
							</c:otherwise>
						</c:choose>
						
						<c:choose>
							<c:when test="${empty plist.priorMoney || plist.reachMoney<1  || plist.priorMoney<1 || plist.reachMoney/plist.priorMoney<0.01}">
								<input id="recom_ratio${product.index+1 }" value="0" type="hidden">
							</c:when>
							<c:otherwise>
								<input id="recom_ratio${product.index+1 }" value="<fmt:formatNumber value='${plist.reachMoney/plist.priorMoney*100 }' maxFractionDigits='1' ></fmt:formatNumber>" type="hidden">
							</c:otherwise>
						</c:choose>
						
	                       <div class="top">
	                       	  <i class="icon icon-elephant left"></i>
	                       	  <span>${plist.pcName }</span>
	                       </div>
	                       <div class="middle">
	                          <div class="left">
	                          	<strong style="font-size:30px;padding-top:15px;height:44px">
									<!-- 预期年化收益率的判断 -->
									<c:choose>
										<c:when test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">
												无
											</c:if>

											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==1}">
												<fmt:formatNumber type="number" value="${plist.fYield*100 }" maxFractionDigits="1"
													minFractionDigits="1" />%
											</c:if>

											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
												<fmt:formatNumber type="number" value="${plist.lYield*100}" minFractionDigits="1"
													maxFractionDigits="1" />-<fmt:formatNumber type="number" value="${plist.hYield*100}"
													minFractionDigits="1" maxFractionDigits="1" />%
											</c:if>
										</c:when>
										<c:otherwise>
											净值
										</c:otherwise>
									</c:choose>
									<!-- 预期年化收益率的判断 -->
								</strong>
	                          	<p style="padding-bottom:5px;">预期年化收益率</p>
	                          	<c:forEach items="${fn:split(plist.tags, ',')}" var="key" varStatus="status">
									<span
										<c:if test="${status.index==0 }">style="border: 1px solid #ee8241; color: #ee8241"</c:if>
										<c:if test="${status.index==1 }">style="border: 1px solid #7dbbe4; color: #7dbbe4"</c:if>
										<c:if test="${status.index==2 }">style="border: 1px solid #79c064; color: #79c064"</c:if>
										<c:if test="${status.index==3 }">style="border: 1px solid #845299; color: #845299"</c:if>>${key }
									</span>
								</c:forEach>
								<p>投资期限：
									<!-- 期限的判断 -->
									<c:choose>
										<c:when test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">
												非固定(0)&nbsp;
							             	</c:if>
	
											<c:if test="${plist.fTime==0}">
											</c:if>
											
											<c:if test="${fn:substring(plist.code,plist.code.length()-5, plist.code.length()-4)==1}">
												<c:if test="${plist.fTime!=0}">
													${plist.fTime}
													<c:if test="${plist.unitTime==0}">天</c:if>
													<c:if test="${plist.unitTime==1}">周</c:if>
													<c:if test="${plist.unitTime==2}">个月</c:if>
													<c:if test="${plist.unitTime==3}">年</c:if>
												</c:if>
												<c:if test="${plist.fTime==0}">
													无固定期限&nbsp;
							                 	</c:if>
											</c:if>
	
											<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
												<span style="display:inline-block;width:auto;position: relative; z-index: 1; padding: 0; padding-right: 8px; margin-right: 5px">${plist.lTime }-${plist.hTime }<img
													src="/resource/images/jia_small.png"
													style="position: absolute; z-index: 2; right: 1px; top: 0" /></span>&nbsp;<c:if
													test="${plist.unitTime==0}">天</c:if>
												<c:if test="${plist.unitTime==1}">周</c:if>
												<c:if test="${plist.unitTime==2}">个月</c:if>
												<c:if test="${plist.unitTime==3}">年</c:if>
											</c:if>
										</c:when>
										<c:otherwise>
											浮动收益类&nbsp;
							           </c:otherwise>
									</c:choose>
									<!-- 期限的判断 -->
								</p>
	                          </div>
	                          <div class="right" title="项目进度<c:choose><c:when test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">50</c:when><c:when test="${empty plist.priorMoney || plist.reachMoney<1  || plist.priorMoney<1 || plist.reachMoney*1.00000001/plist.priorMoney<0.009999999}">0</c:when><c:otherwise><fmt:formatNumber value="${((plist.reachMoney*1.00000001)/(plist.priorMoney)-0.005)*100 }" maxFractionDigits="0"/></c:otherwise></c:choose>%">
	                          	  <div class="demo">
	                          	  	<c:choose>
	                          	  		<c:when test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
	                          	  			<input id="progress${product.index }" type="hidden" value="50" maxFractionDigits="0"/>">
	                          	  		</c:when>
	                          	  		<c:otherwise>
			                          	  	<input id="progress${product.index }" type="hidden" value="<fmt:formatNumber value="${plist.reachMoney/plist.priorMoney*100 }" maxFractionDigits="0"/>">
	                          	  		</c:otherwise>
	                          	  	</c:choose>
								    <div id="waterHProgress${product.index }" class="div_water"></div>
								    <img src="/resource/images/qdimages/zhezhao.png" class="zhezhao"/>
								    <img id="waterBProgress${product.index }" src="/resource/images/qdimages/wave.gif" class="wave"/>
								  </div>
	                          </div>
	                       </div>
	                       <form action="/product/showProduct.html">
								<input type="hidden" name="code" value="${plist.code }">
								<button type="submit" value="submit">我要理财</button>
							</form>
	                    </li>
	                    </c:if>
					</c:forEach>
					  
                  </ul>
				</div>

				<div class="title-box" style="margin-top:20px">
					<i class="icon icon-products left"></i>
					<strong>产品系列 (${fn:length(listy) })</strong>
					<span>市场有风险，投资需谨慎</span>
					<i class="icon icon-more"></i>
					<a href="/product/list/0_0_0_0_0_1.html" class="more">更多</a>
				</div>
				<div class="tt">
					<c:forEach items="${listy}" var="plist" varStatus="product">
						<c:if test="${product.index<5}">
						<div class="product-item">
							<div class="top">
								<div class="left">
									<i class="icon icon-elephant  left"></i>
									<span>${plist.name}</span>
									<img src="/resource/images/qdimages/new.png" />
								</div>
								<div class="right">
									<c:if test="${plist.tags!=null&&plist.tags!='' }">
										<c:forEach items="${fn:split(plist.tags, ',')}" var="key" varStatus="status">
											<span
												<c:if test="${status.index==0 }">style="border: 1px solid #ee8241; color: #ee8241"</c:if>
												<c:if test="${status.index==1 }">style="border: 1px solid #7dbbe4; color: #7dbbe4"</c:if>
												<c:if test="${status.index==2 }">style="border: 1px solid #79c064; color: #79c064"</c:if>
												<c:if test="${status.index==3 }">style="border: 1px solid #845299; color: #845299"</c:if>>${key }
											</span>
										</c:forEach>
									</c:if>
								</div>
							</div>
							<div class="bottom clearfix">
								<ul class="left clearfix">
									<li style="padding-left:10px;width:130px;">
										<span>预期年化收益率：</span>
										
												<!-- 预期年化收益率的判断 --> 
												<c:choose>
													<c:when test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
														<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">
															<p style="color:#ea560e;"><strong style="font-weight:bold">
																无
															</strong></p>
														</c:if>
														<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==1}">
															<p style="color:#ea560e;"><strong style="float:left;font-weight:bold">
																<fmt:formatNumber type="number" value="${plist.fYield*100 }"
																	minFractionDigits="1" maxFractionDigits="1" />
															</strong> <span class="span-float">%</span></p>
														</c:if>
														<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
															<p style="color:#ea560e;font-size:14px"><strong style="font-weight:bold;font-size:27px">
																<fmt:formatNumber type="number" value="${plist.lYield*100}"
																	minFractionDigits="1" maxFractionDigits="1" /></strong>- <strong style="font-weight:bold;font-size:27px">
																<fmt:formatNumber type="number" value="${plist.hYield*100}"
																	minFractionDigits="1" maxFractionDigits="1" /></strong>%</p>
														</c:if>
													</c:when>
													<c:otherwise>
														<p style="color:#ea560e;"><strong style="font-weight:bold">净值</strong></p>
													</c:otherwise>
												</c:choose>
												<!-- 预期年化收益率的判断 -->
										
									</li>
									<li class="jiange"></li>
									<li  style="padding-left:20px;width:140px;">
										<span>项目期限：</span>
										<p>
											<!-- 期限的判断 -->
											<c:choose>
												<c:when test="${fn:substring(plist.code, plist.code.length()-6, plist.code.length()-5)==0}">
													<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==0}">非固定收益类</c:if>
													<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==1}">
														<c:if test="${plist.fTime==0}">
															无固定期限
														</c:if>
														<c:if test="${plist.fTime!=0}">
															<strong style="float:left">${plist.fTime }</strong>
															<c:if test="${plist.unitTime==0}"><span class="span-float span-float-qi">天</span></c:if>
															<c:if test="${plist.unitTime==1}"><span class="span-float span-float-qi">周</span></c:if>
															<c:if test="${plist.unitTime==2}"><span class="span-float span-float-qi">个月</span></c:if>
															<c:if test="${plist.unitTime==3}"><span class="span-float span-float-qi">年</span></c:if>
														</c:if>
													</c:if>

													<c:if test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
														<span style="position: relative; z-index: 1; padding: 0; padding-right: 8px; margin-right: 5px">
															<strong style="font-size:28px;">${plist.lTime }-${plist.hTime }</strong>
															<img src="/resource/images/jia_small.png" style="position: absolute; z-index: 2; right: -2px; top: 0" />
														</span>
														<c:if test="${plist.unitTime==0}">天</c:if>
														<c:if test="${plist.unitTime==1}">周</c:if>
														<c:if test="${plist.unitTime==2}">个月</c:if>
														<c:if test="${plist.unitTime==3}">年</c:if>
													</c:if>
												</c:when>
												<c:otherwise>浮动收益类</c:otherwise>
											</c:choose>
											<!-- 期限的判断 -->											
										</p>
									</li>
									<li class="jiange"></li>
									<li  style="padding-left:22px;width:180px;">
										<span>项目金额：</span>
										<p>
											<!-- 规模的判断 -->
											<c:if test="${plist.rule==0 || plist.rule==2 }">
												<strong style="float:left">
													<fmt:formatNumber pattern="#,##0.00#" value="${plist.priorMoney/10000 }" />
												</strong><span class="span-float span-float-qi">万</span>
											</c:if> 
											<c:if test="${plist.rule==1 || plist.rule==3 }"><strong style="font-size:25px;font-family:'Microsoft yahei'">无限制</strong></c:if>
											<!-- 规模的判断 -->
										</p>
									</li>
								</ul>
								<div class="right">
									<c:choose>
										<c:when test="${fn:substring(plist.code, plist.code.length()-5, plist.code.length()-4)==2}">
											<div class="progress-box clearfix">
												<span>进度：</span>
												<div class="progress">
													<div class="progress-top"  style="width:65px"></div>
												</div>
												<span style="color:#ea560e">50%</span>
											</div>
										</c:when>
										<c:when test="${empty plist.priorMoney || plist.reachMoney<1  || plist.priorMoney<1 || plist.reachMoney*1.00000001/plist.priorMoney<0.009999999}">
											<div class="progress-box clearfix">
												<span>进度：</span>
												<div class="progress">
													<div class="progress-top" style="width:0px;"></div>
												</div>
												<span style="color:#ea560e">0%</span>
											</div>
										</c:when>
										<c:otherwise>
											<div class="progress-box clearfix">
												<span>进度：</span>
												<div class="progress">
													<div class="progress-top"  style="width:${plist.reachMoney/plist.priorMoney*128 }px"></div>
												</div>
												<span style="color:#ea560e"><fmt:formatNumber value="${((plist.reachMoney*1.00000001)/(plist.priorMoney)-0.005)*100 }" maxFractionDigits="0"/>%</span>
											</div>
										</c:otherwise>
									</c:choose>
									
									<form action="/product/showProduct.html">
										<input type="hidden" name="code" value="${plist.code }">
										<button type="submit" value="submit">我要理财</button>
									</form>
								</div>
	
							</div>
						</div>
						</c:if>
					</c:forEach>
				</div>
 			</div>
 			<div class="right">
				<img src="/resource/images/img_xinshou.jpg"  usemap="#planetmap" />
				<map name="planetmap" id="planetmap">
					<area shape="rect" coords="0,0,265,74" href="freshman.html"/>
					<area shape="rect" coords="0,75,265,151" href="direction.html"/>
				</map>

				<div class="income">
				  <div class="titles clearfix">
				    <strong class="rightTitle">收益对比</strong>
				  </div>
				  <img src="/resource/images/data.gif"/>
				</div>

				<div class="faq">
					  <div class="titles clearfix">
						<strong class="rightTitle">媒体资讯</strong>
						<a href="/infoMedia/list.html" class="moress">
							<i class="icon icon-more"></i> 更多
						</a>
					  </div>
					   <ul>
						   <c:forEach items="${listInfoMedia }" var="item" varStatus="status">
								<c:if test="${status.index<=8 }">
									<li class="clearfix">
										<a target="_blank" href="/infoMedia/details.html?id=${item.id}">${item.title }</a>
										<span>
											<fmt:formatDate value="${item.pDt }" pattern="yyyy-MM-dd" /> 
										</span>
									</li>
								</c:if>
							</c:forEach>
					   </ul>
		         </div>

		         <div class="faq">
					  <div class="titles clearfix">
						<strong class="rightTitle">站内公告</strong>
						  <a href="/infoNotice/list.html" class="moress">
						  	<i class="icon icon-more"></i> 更多
						  </a>
					   </div>
					   <ul>
				   		 <c:forEach var="item" items="${listInfoNotice}" varStatus="status">
							<c:if test="${status.index<=8 }">
								<li class="clearfix">
									<a target="_blank" href="/infoNotice/details.html?id=${item.id}">${item.title }</a>
									<span>
										<fmt:formatDate value="${item.pDt }" pattern="yyyy-MM-dd" /> 
									</span>
								</li>
							</c:if>
						 </c:forEach>
					   </ul>
		         </div>

		        <div class="faq">
				  <div class="titles clearfix">
					<strong class="rightTitle">您问我答</strong>
					  <a href="/usersAsk/list.html" class="moress">
					  	<i class="icon icon-more"></i> 更多
					  </a>
				   </div>
				   <ul>
					   <c:forEach items="${listUsersAsk }" var="item" varStatus="status">
							<c:if test="${status.index<=8 }">
								<li class="clearfix">
									<a target="_blank" href="/usersAsk/details.html?id=${item.id }">${item.title }</a>
									<span>
										<fmt:formatDate value="${item.time }" pattern="yyyy-MM-dd" />
									</span>
								</li>
							</c:if>
						</c:forEach>   	
				   </ul>
				</div>
 	        </div>
        </div>
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
<script type="text/javascript">
$(function(){
	//top
	$(".media-link li:first").css("cursor", "pointer")
	$(".media-link li:first").hover(function() {
		$(this).find("div").show();
	}, function() {
		$(this).find("div").hide();
	});
	
	/*会员登陆*/
	if ($("#session").val() == "") {
		$(".register ul li a:eq(0)")
				.css("color", "#ed6e31");
		$(".register ul li a:eq(0)")
				.hover(
						function() {
							$(".register ul li a:eq(0)")
									.css("color", "#ed6e31")
							$(".register ul li a:eq(1)")
									.css("color", "#fff")
							$(".register")
									.css("background",
											"url(/resource/images/bg_top.png) no-repeat left top");
							$(".register ol li").hide();
							$(".register ol li:eq(0)")
									.show();

						}, function() {
						});

		$(".register ul li a:eq(1)")
				.hover(
						function() {
							$(".register ul li a:eq(1)")
									.css("color", "#ed6e31");
							$(".register ul li a:eq(0)")
									.css("color", "#fff");
							$(".register")
									.css("background",
											"url(/resource/images/bg_top2.png) no-repeat left top");
							$(".register ol li").hide();
							$(".register ol li:eq(1)")
									.show();
							$("li.form input").css(
									"background", "#fff")

						}, function() {
						});
	} else {
		$(".register ul li a:eq(1)")
				.css("color", "#ed6e31");
		$(".register")
				.css("background",
						"url(/resource/images/bg_top2.png) no-repeat left top");
		$(".register ol li:eq(0)").hide();
		$(".register ol li:eq(1)").show();
		$(".register ul li a:eq(0)")
				.hover(
						function() {
							$(".register ul li a:eq(0)")
									.css("color", "#ed6e31")
							$(".register ul li a:eq(1)")
									.css("color", "#fff")
							$(".register")
									.css("background",
											"url(/resource/images/bg_top.png) no-repeat left top");
							$(".register ol li").hide();
							$(".register ol li:eq(0)")
									.show();

						}, function() {
						});

		$(".register ul li a:eq(1)")
				.hover(
						function() {
							$(".register ul li a:eq(1)")
									.css("color", "#ed6e31");
							$(".register ul li a:eq(0)")
									.css("color", "#fff");
							$(".register")
									.css("background",
											"url(/resource/images/bg_top2.png) no-repeat left top");
							$(".register ol li").hide();
							$(".register ol li:eq(1)")
									.show();

						}, function() {
						});
	}
	/*会员登陆*/
	
	/*会员登录*/
    $(".register ul li a:eq(0)").css("color","#ed6e31")
	$(".register ul li a:eq(0)").hover(function(){ 
	    $(".register ul li a:eq(0)").css("color","#ed6e31");
		$(".register ul li a:eq(1)").css("color","#fff");
	    $(".register").css("background","url(/resource/images/qdimages/bg_top_qd.png) no-repeat left top");
		$(".register ol li").hide();
	    $(".register ol li:eq(0)").show();
		
	},function(){
	})
	
	$(".register ul li a:eq(1)").hover(function(){
	    $(".register ul li a:eq(1)").css("color","#ed6e31");
		$(".register ul li a:eq(0)").css("color","#fff");
	    $(".register").css("background","url(/resource/images/qdimages/bg_top_qd2.png) no-repeat left top");
		$(".register ol li").hide();
	    $(".register ol li:eq(1)").show();
		
	},function(){
	})  
	/*会员登录*/
	
	/*banner*/
	 $(".fullSlide").hover(function() {
	    $(this).find(".prev,.next").stop(true, true).fadeTo("show", 0.5)
	},
	function() {
	    $(this).find(".prev,.next").fadeOut()
	});
	$(".fullSlide").slide({
	    titCell: ".hd ul",
	    mainCell: ".bd ul",
	    effect: "fold",
	    autoPlay: true,
	    autoPage: true,
	    trigger: "click",
	    startFun: function(i) {
	        var curLi = $(".fullSlide .bd li").eq(i);
	        if ( !! curLi.attr("_src")) {
	            curLi.css("background-image", curLi.attr("_src")).removeAttr("_src")
	        }
	    }
	});
	/*banner*/
	
	/*city toggle*/
	$(".adress-box").click(function(){
	 $(".dropdown").toggle();
	});
	$(".dropdown li a").click(function(){
		$(".dropdown").css("display","none");
	});
	
	createProgress();
	
	//引导图
	var currentDate=new Date();
	var CloseDate ='2016-12-05 0:0';
	CloseDate = CloseDate.replace(/-/g,"/");
	var CloseDate = new Date(CloseDate);
	if(currentDate<=CloseDate){
		if(document.cookie.match('alert_cookie')==null){
			window.setTimeout(function(){
				$("#rz-box-bg").show();
			   	$(".welcome-qd").show();
			},800)
			
		   	document.cookie="alert_cookie";
		   	$(".welcome-qd span").on("click",function(){
		   		$("#rz-box-bg").hide();
		   	    $(".welcome-qd").hide();
		   	})
		}
		
	}else{
		$("#rz-box-bg").hide();
	   	$(".welcome-qd").hide();
	}

});
function createProgress() {
	var bottonProgress = $(".demo [class=div_water]");
	if(bottonProgress.length>0) {
		for(var i = 0; i < bottonProgress.length; i++) {
			var heigh = $("#progress"+i).val()*1.6;
			//项目进度 
			$("#waterBProgress"+i).stop().animate({bottom:heigh+"px"},2000);
			$("#waterHProgress"+i).stop().animate({height:heigh+"px"},2000);
		    
		}
	}
}

</script>
 </body>
 </html>