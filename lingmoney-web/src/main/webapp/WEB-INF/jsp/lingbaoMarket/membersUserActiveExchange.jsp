<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","No-cache");
response.setDateHeader("Expires", -1);
response.setHeader("Cache-Control","No-store");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
	<title>领钱儿 专业的互联网金融理财平台</title>
	<link rel="stylesheet" href="/resource/css/index.css">
	<link rel="stylesheet" type="text/css" href="/resource/css/style.css" media="screen" />
	<link rel="icon" type="image/x-icon" href="/resource/ico.ico">
	<link rel="shortcut icon" type="image/x-icon" href="/resource/ico.ico">
	<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="/resource/js/lingbaoMarketJs/public.js"></script>
	<script type="text/javascript" src="/resource/js/lingbaoMarketJs/manageAddress.js"></script>
	<script type="text/javascript" src="/resource/js/lingbaoMarketJs/activeExchange.js"></script>
    <link rel="stylesheet" type="text/css" href="/resource/css/lingbaoMarketCss/market.css"/>
	<script type="text/javascript" src="/resource/js/lingbaoMarketJs/public.js"></script>
	<script type="text/javascript" src="/resource/js/jsAddress.js"></script>
	<style type="text/css">
		.pagination {padding: 5px;font-size:12px;text-align:right}
		.pagination a, .pagination a:link, .pagination a:visited {padding:3px 8px;margin:0 5px;border:1px solid #ddd;text-decoration:none;color:#434343;}
		.pagination a:hover, .pagination a:active {background:#204c6f;color: #fff;text-decoration: none;}
		.pagination span.current {padding:3px 8px;margin:0 5px;background:#204c6f;font-weight: bold;color: #fff;}
		.pagination span.disabled {padding: 3px 8px;margin:0 5px;border: 1px solid #eee; color: #ddd;}
	</style>
	<jsp:include page="/common/kefu.jsp"></jsp:include>
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<!-- 用户导航开始 -->
	<jsp:include page="/common/navmember.jsp"></jsp:include>
	<!-- 用户导航结束 -->
	
	<!-- 自定义ALERT弹框 -->
	<div class="rz-box-con" id="alert" style="width:500px;height:200px">
	    <div class="rz-title">
	       <h2>提示</h2>
	       <a class="rz-close" href="javascript:;"><img src="/resource/images/img_rz-close.png"/></a>
	    </div>
	    <div style="padding:20px 0;margin:0 auto;width:400px;text-align:center;font-size:1.5em;"></div>
	    <p style="margin: 40px auto; width: 460px;">
	       <a href="javascript:void(0);" class="rz-button ok" style="width:122px;cursor:pointer">确定</a>
	    </p>
	</div>

	<div class="mainbody_member">
	<input id="type" value="${type }" type="hidden">
		<div class="box clearfix">
			<!-- 我的账户菜单开始 -->
			<jsp:include page="/common/myaccount.jsp"></jsp:include>
			<!-- 我的账户菜单结束 -->
			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">领宝兑换</div>
					<div class="zonglan clearfix">
						<div class="lbmanager">
							<div id="lingbaoShow" class="curlb"><div>${lingbao }</div></div>
							<ul id="navUl" class="ul-box clearfix">
							  <li class="clicked" >购物车</li>
							  <li style="left:200px">进行中</li>
							  <li style="left:400px">已兑换</li>
							</ul>
							<div class="ul-box-con">
							<!-- 购物车 S -->
							   <div id="shopCartDiv" class="active">
							   	<c:if test="${dataCart.code==200 }">
							      <table id="buyCarTable" cellpadding="0" cellspacing="0" border="0" class="buyCarTable">
							         <tbody>
							            <tr class="theader">
							               <td></td>
							               <td align="center">商品</td>
							               <td align="center">商品名称</td>
							               <td align="center">数量</td>
							               <td align="right">领宝数量</td>
							               <td align="right" style="padding-right:9px;">操作</td>
							            </tr>
							            	<c:forEach items="${dataCart.rows }" var="list">
							            		<tr>
									               <td class="first">
									               <c:choose>
								               	   	 	<c:when test="${list.oldcost==0 }">
								               	   	 		<span data-info="${list.cid }" data-cost="0" class="J_CheckBoxItem" flag="true">
								               	   	 	</c:when>
								               	   	 	<c:when test="${list.preCost!=0 }">
								               	   	 		<span data-info="${list.cid }" data-cost="${list.preCost }" class="J_CheckBoxItem" flag="true">
								               	   	 	</c:when>
								               	   	 	<c:otherwise>
								               	   	 		<span data-info="${list.cid }" data-cost="${list.cost }" class="J_CheckBoxItem" flag="true">
								               	   	 	</c:otherwise>
								               	   	</c:choose>
									               		<input type="checkbox" name="select-all" />
									               		<input type="hidden" value="${list.cid }" name="cartId">
									               		<input type="hidden" value="${list.id }" name="giftId">
									               	</span>
									               </td>
									               <c:choose>
									               		<c:when test="${list.applyType==1 }">
									               			<td class="second"><a href="javascript:void(0);"><img title="${list.name}" src="${list.pic }"/></a></td>
									               			<td class="third">
										               			<a title="${list.name}" href="javascript:void(0);">
										               				<c:choose>
																		<c:when test="${fn:length(list.name)>12 }">
																			${fn:substring(list.name, 0, 9)}...
																		</c:when>
																		<c:otherwise>
																			${list.name}
																		</c:otherwise>
																	</c:choose> 
										               			</a>
									               			</td>
									               		</c:when>
									               		<c:otherwise>
									               			<td class="second"><a href="/lingbaoExchange/exchange?id=${list.giftId }"><img src="${list.pic }"/></a></td>
									               			<td class="third"><a href="/lingbaoExchange/exchange?id=${list.giftId }">${list.name }</a></td>
									               		</c:otherwise>
									               </c:choose>
									               <td class="forth">
									               	   <span class="tb-count" data-info="${list.store }" data-limit="${list.exchangeNumber }">
									               	   	 <c:choose>
									               	   	 	<c:when test="${list.oldcost==0 }">
									               	   	 		<a data-info="${list.cid }" href="javascript:void(0)" title="减1" class="limit">-</a>
									               	   	 	</c:when>
									               	   	 	<c:when test="${list.num>1 }">
									               	   	 		<a data-info="${list.cid }" onclick="subtractNum($(this))" href="javascript:void(0)" title="减1">-</a>
									               	   	 	</c:when>
									               	   	 	<c:otherwise>
									               	   	 		<a data-info="${list.cid }" onclick="subtractNum($(this))" href="javascript:void(0)" title="减1" class="limit">-</a>
									               	   	 	</c:otherwise>
									               	   	 </c:choose>
									               	   	 <c:choose>
										                 	<c:when test="${list.oldcost==0 }">
									               	   	 		<input name="number" type="text" value="1" readonly="readonly"/>
									               	   	 	</c:when>
									               	   	 	<c:otherwise>
										                 		<input name="number" type="text" value="${list.num }" onfocus="setOldNum(this.value)" onblur="numBlur($(this),'${list.cid}')" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))"   maxlength="8" title="请输入购买量"/>
									               	   	 	</c:otherwise>
										                 </c:choose>
										                 <c:choose>
										                 	<c:when test="${list.oldcost==0 }">
									               	   	 		<a data-info="${list.cid }" href="javascript:void(0)" title="加1" class="limit">+</a>
									               	   	 	</c:when>
									               	   	 	<c:when test="${list.num>=list.store || list.num>=list.exchangeNumber}">
									               	   	 		<a data-info="${list.cid }" onclick="addNum($(this))" href="javascript:void(0)" title="加1" class="limit">+</a>
									               	   	 	</c:when>
									               	   	 	<c:otherwise>
										                 		<a data-info="${list.cid }" onclick="addNum($(this))" href="javascript:void(0)" title="加1">+</a>
									               	   	 	</c:otherwise>
										                 </c:choose>
										                </span>
										             </td>
										           <c:choose>
											           	<c:when test="${list.oldcost==0 }">
											           		<td class="fifth">0</td>
											           	</c:when>
											           	<c:when test="${list.preCost!=0 }">
											           		<td class="fifth">${list.preCost*list.num }</td>
									               	   	</c:when>
											           	<c:otherwise>
											                <td class="fifth">${list.cost*list.num }</td>
											           	</c:otherwise>
										           </c:choose>
										           <c:choose>
										           		<c:when test="${list.oldcost==0 }">
										           			<td class="sixth"><a href="javascript:void(0)" onclick="deleteFromCart($(this),-1)">删除</a></td>
										           		</c:when>
										           		<c:otherwise>
										           			<td class="sixth"><a href="javascript:void(0)" onclick="deleteFromCart($(this),'${list.cid }')">删除</a></td>
										           		</c:otherwise>
										           </c:choose>
									            </tr>
							            	</c:forEach>
							         </tbody>
							      </table>
							      <div id="allChexkBox" class="box-heji">
							        <span class="J_CheckBoxItem" style="margin-right:29px;" flag="true">
							        	<input type="checkbox" name="select-all"/>
							        </span>
							        <a href="javascript:void(0)" id="btn_selectAll" flag="true">全选</a>
							        <a href="javascript:void(0)" id="btn_removetAll" onclick="deleteFromCart($(this),'all')">删除</a>
							        <span style="margin-right:32px;">已选择商品 <strong id="countNumber" class="countAll" style="padding:0 10px;">0</strong>件</span>
							        <span>合计： <strong id="countIntegral" class="countAll">0</strong>（领宝）</span>
							        <a href="javascript:void(0)" onclick="validExchange($(this))" class="btn_exchange">立即兑换</a>
							      </div>
							      
							      <div class="page">
										<div id="buyCarPages" class="pagination">
											<c:if test="${dataCart.code==200 }">
												&nbsp;共<strong>${dataCart.total }</strong>项,<strong>${dataCart.size }</strong>页:&nbsp;
												<c:if test="${dataCart.size!=null && dataCart.size>1 }">
													<c:choose>
														<c:when test="${dataCart.nowpage==1 }">
															<span class="disabled">«&nbsp;上一页</span>
														</c:when>
														<c:otherwise>
															<a href="javascript:exchangeRecordList(${dataCart.nowpage+1 })">«&nbsp;上一页</a>
														</c:otherwise>
													</c:choose>
													<c:choose>
														<c:when test="${dataCart.size>8 }">
															<c:forEach var="page" begin="1" end="8">
																<c:choose>
																	<c:when test="${page==dataCart.nowpage }">
																		<span class="current">${dataCart.nowpage }</span>
																	</c:when>
																	<c:otherwise>
																		<a href="javascript:exchangeRecordList(${page })">${page }</a>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
															<span>...</span>
														</c:when>
														<c:otherwise>
															<c:forEach var="page" begin="1" end="${dataCart.size }">
																<c:choose>
																	<c:when test="${page==dataCart.nowpage }">
																		<span class="current">${dataCart.nowpage }</span>
																	</c:when>
																	<c:otherwise>
																		<a href="javascript:exchangeRecordList(${page })">${page }</a>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</c:otherwise>
													</c:choose>
													<c:choose>
														<c:when test="${dataCart.nowpage==dataCart.size }">
															<span class="disabled">下一页&nbsp;»</span>
														</c:when>
														<c:otherwise>
															<a href="javascript:exchangeRecordList(${dataCart.nowpage+1 })">下一页&nbsp;»</a>
														</c:otherwise>
													</c:choose>
												</c:if>
												<c:if test="${dataCart.size==1 }">
													<span class="disabled">«&nbsp;上一页</span>
													<span class="current">1</span>
													<span class="disabled">下一页&nbsp;»</span>
												</c:if>
											</c:if>
										</div>
							      </div>
							       </c:if>
							       <c:if test="${dataCart.code!=200 }">
							      	<h3>暂无数据</h3>
							      </c:if>
							   </div>
							   <!-- 购物车 E -->
							   <!-- 进行中 S -->
							   <div style="border:0">
							      <div id="dataWaitDiv" class="songing">
							      	 <c:if test="${dataWait.code==200 }">
						           		<c:forEach items="${dataWait.rows }" var="list">
						           			 <table cellpadding="0" cellspacing="0" border="0" class="table-default">
									           <tbody>
									            <tr class="theader">
									            	<td rowspan="2" style="padding:0px; height:100%; width:120px; line-height:9px; border-bottom:1px solid #f2f2f2;">
									            		<input value="${list.serialNumber }" type="hidden">
									            		<img title="${list.name }" style="width: 70%;"  src="${list.pic }">
									            	</td>
									               	<td align="center" style="padding-left:20px;">商品名称</td>
									               	<td align="center">兑换时间</td>
									               	<td align="right">数量</td>
									               	<td align="right">领宝数量</td>
									               	<td align="right" style="padding-right:30px;">状态</td>
									            </tr>
							           			 <tr>
									               <td class="first">
									               <c:choose>
									               		<c:when test="${list.applyType==1 }">
									               			<a title="${list.name}" href="javascript:void(0);">
																<c:choose>
																	<c:when test="${fn:length(list.name)>12 }">
																		${fn:substring(list.name, 0, 9)}...
																	</c:when>
																	<c:otherwise>
																		${list.name}
																	</c:otherwise>
																</c:choose> 
															</a>
									               		</c:when>
									               		<c:otherwise>
									               			<a title="${list.name}" href="/lingbaoExchange/exchange?id=${list.giftId }">
									               				<c:choose>
																	<c:when test="${fn:length(list.name)>12 }">
																		${fn:substring(list.name, 0, 9)}...
																	</c:when>
																	<c:otherwise>
																		${list.name}
																	</c:otherwise>
																</c:choose> 
									               			</a>
									               		</c:otherwise>
									               </c:choose>
									               	<input type="hidden" name="giftId" value="${list.giftId }">
									               	<input type="hidden" name="exchangeId" value="${list.id }">
									               </td>
									               <td class="second">${list.exchangeTime }</td>
									               <td class="third">${list.num }</td>
									               <td class="forth">${list.integral }</td>
									        <c:choose>
								        		<c:when test="${list.status==1 }">
											               <td class="fifth able"><a href="javascript:void(0)" onclick="confirmReceipt(this,'${list.id }')">确认收货</a></td>
											            </tr>
											            <c:if test="${null!=list.expressNumber && not empty list.expressNumber }">
												            <tr style="background:#fef6f3;">
												            	<td colspan="2" style="padding-left:50px; text-align:center; color:#ffffff;font-size:14px; background: url('/resource/images/lingbaoMarketImages/carBg.png') 95px 11px no-repeat">${list.expressCompany }</td>
										        				<td colspan="4" style=" font-size: 14px; color: #4a4a4a; text-align:center;">快递单号：<em style="color:rgb(244,165,131);">${list.expressNumber }</em></td>
										        			</tr>
											        	</c:if>
											            </tbody>
										        	</table>
								        		</c:when>
								        		<c:otherwise>
								        				<td class="fifth able"><a href="javascript:void(0)">处理中</a></td>
											            </tr>
											            </tbody>
										        	</table>
								        		</c:otherwise>
								        	</c:choose>
						           		</c:forEach>
						           		 <div class="page">
											<div id="dataWaitPages" class="pagination">
												<c:if test="${dataWait.code==200 }">
													&nbsp;共<strong>${dataWait.total }</strong>项,<strong>${dataWait.size }</strong>页:&nbsp;
													<c:if test="${dataWait.size!=null && dataWait.size>1 }">
														<c:choose>
															<c:when test="${dataWait.nowpage==1 }">
																<span class="disabled">«&nbsp;上一页</span>
															</c:when>
															<c:otherwise>
																<a href="javascript:queryInfoByUidWait(${dataWait.nowpage+1 })">«&nbsp;上一页</a>
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${dataWait.size>8 }">
																<c:forEach var="page" begin="1" end="8">
																	<c:choose>
																		<c:when test="${page==dataWait.nowpage }">
																			<span class="current">${dataWait.nowpage }</span>
																		</c:when>
																		<c:otherwise>
																			<a href="javascript:queryInfoByUidWait(${page })">${page }</a>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
																<span>...</span>
															</c:when>
															<c:otherwise>
																<c:forEach var="page" begin="1" end="${dataWait.size }">
																	<c:choose>
																		<c:when test="${page==dataWait.nowpage }">
																			<span class="current">${dataWait.nowpage }</span>
																		</c:when>
																		<c:otherwise>
																			<a href="javascript:queryInfoByUidWait(${page })">${page }</a>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${dataWait.nowpage==dataWait.size }">
																<span class="disabled">下一页&nbsp;»</span>
															</c:when>
															<c:otherwise>
																<a href="javascript:queryInfoByUidWait(${dataWait.nowpage+1 })">下一页&nbsp;»</a>
															</c:otherwise>
														</c:choose>
													</c:if>
													<c:if test="${dataWait.size==1 }">
														<span class="disabled">«&nbsp;上一页</span>
														<span class="current">1</span>
														<span class="disabled">下一页&nbsp;»</span>
													</c:if>
												</c:if>
											</div>
								      </div>
						           	 </c:if>
						           	 <c:if test="${dataWait.code!=200 }">
						           	 	<h3>暂无数据</h3>
						           	 </c:if>
							      </div>
							   </div>
							    <!-- 进行中 E -->
							   <!-- 已兑换 S -->
							   <div id="dataFinishDiv">
							      <c:if test="${dataFinish.code==200 }">
						            	<c:forEach items="${dataFinish.rows }" var="list">
						            		<table cellpadding="0" cellspacing="0" border="0" class="table-default">
									         <tbody>
									            <tr class="theader">
									               <td rowspan="2" style="padding:0px; height:100%; width:120px; line-height:9px; border-bottom:1px solid #f2f2f2;">
									            	   <img title="${list.name }" style="width: 70%; height: 135;"  src="${list.pic }">
									               </td>
									               <td align="center" style="padding-left:20px;">商品名称</td>
									               <td align="center">兑换时间</td>
									               <td align="right">数量</td>
									               <td align="right">领宝数量</td>
									               <td align="right" style="padding-right:30px;">状态</td>
									            </tr>
							            		<tr>
									               <td class="first">
									               		<c:choose>
										               		<c:when test="${list.applyType==1 }">
										               			<a title="${list.name }" href="javascript:void(0);">
										               				<c:choose>
																		<c:when test="${fn:length(list.name)>12 }">
																			${fn:substring(list.name, 0, 9)}...
																		</c:when>
																		<c:otherwise>
																			${list.name}
																		</c:otherwise>
																	</c:choose> 
																</a>
										               		</c:when>
										               		<c:otherwise>
										               			<a title="${list.name}" href="/lingbaoExchange/exchange?id=${list.giftId }">
																	<c:choose>
																		<c:when test="${fn:length(list.name)>12 }">
																			${fn:substring(list.name, 0, 9)}...
																		</c:when>
																		<c:otherwise>
																			${list.name}
																		</c:otherwise>
																	</c:choose> 
																</a>
										               		</c:otherwise>
										               </c:choose>
									               </td>
									               <td class="second">${list.exchangeTime }</td>
									               <td class="third">${list.num }</td>
									               <td class="forth hui">${list.integral }</td>
									               <td class="fifth unable">已收货</td>
									            </tr>
									            <c:if test="${null!=list.expressNumber && not empty list.expressNumber }">
										            <tr style="background:#fef6f3;">
										            	<td colspan="2" style="padding-left:50px; text-align:center; color:#ffffff;font-size:14px; background: url('/resource/images/lingbaoMarketImages/carBg.png') 95px 11px no-repeat">${list.expressCompany }</td>
								        				<td colspan="4" style=" font-size: 14px; color: #4a4a4a; text-align:center;">快递单号：<em style="color:rgb(244,165,131);">${list.expressNumber }</em></td>
								        			</tr>
									        	</c:if>
									        		</tbody>
							     				 </table>
						            	</c:forEach>
							      <div class="page">
										<div id="dataFinishPages" class="pagination">
											<c:if test="${dataFinish.code==200 }">
												&nbsp;共<strong>${dataFinish.total }</strong>项,<strong>${dataFinish.size }</strong>页:&nbsp;
												<c:if test="${dataFinish.size!=null && dataFinish.size>1 }">
													<c:choose>
														<c:when test="${dataFinish.nowpage==1 }">
															<span class="disabled">«&nbsp;上一页</span>
														</c:when>
														<c:otherwise>
															<a href="javascript:queryInfoByUidFinish(${dataFinish.nowpage+1 })">«&nbsp;上一页</a>
														</c:otherwise>
													</c:choose>
													<c:choose>
														<c:when test="${dataFinish.size>8 }">
															<c:forEach var="page" begin="1" end="8">
																<c:choose>
																	<c:when test="${page==dataFinish.nowpage }">
																		<span class="current">${dataFinish.nowpage }</span>
																	</c:when>
																	<c:otherwise>
																		<a href="javascript:queryInfoByUidFinish(${page })">${page }</a>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
															<span>...</span>
														</c:when>
														<c:otherwise>
															<c:forEach var="page" begin="1" end="${dataFinish.size }">
																<c:choose>
																	<c:when test="${page==dataFinish.nowpage }">
																		<span class="current">${dataFinish.nowpage }</span>
																	</c:when>
																	<c:otherwise>
																		<a href="javascript:queryInfoByUidFinish(${page })">${page }</a>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</c:otherwise>
													</c:choose>
													<c:choose>
														<c:when test="${dataFinish.nowpage==dataFinish.size }">
															<span class="disabled">下一页&nbsp;»</span>
														</c:when>
														<c:otherwise>
															<a href="javascript:queryInfoByUidFinish(${dataFinish.nowpage+1 })">下一页&nbsp;»</a>
														</c:otherwise>
													</c:choose>
												</c:if>
												<c:if test="${dataFinish.size==1 }">
													<span class="disabled">«&nbsp;上一页</span>
													<span class="current">1</span>
													<span class="disabled">下一页&nbsp;»</span>
												</c:if>
											</c:if>
										</div>
							      </div>
							      </c:if>
							      <c:if test="${dataFinish.code!=200 }">
							      	<tr><td><h3>暂无数据</h3></td></tr>
							      </c:if>
							   </div>
							   <!-- 已兑换 E -->
							</div>
						</div>

					</div>

				</div>

			</div>
		</div>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	
<!--确认兑换  -->
<div id="rz-box-bg"></div>
<div class="rz-box-con exchangeDialog" id="exchangeDialog">
	<input id="giftType" type="hidden">
	<div class="rz-title">
	    <h2>立即兑换</h2>
		<a href="javascript:void(0)" class="rz-close">
			<img src="/resource/images/img_rz-close.png" />
		</a>
	</div>
	<div class="exchangeDialog_con">
	   <div class="exchangeDialog_lingbao">
	      <span style="margin-left:245px;line-height: 70px;width:122px;font-size:18px;color:#ea5513">所需支付领宝</span>
	      <span id="spanLingbaoCost" style="width:149px;font-size:36px;font-family: arial">3500</span>
	   </div>
	   <div id="exchangeDialogAddress" class="exchangeDialog_address">
	     
	   </div>
	   
	   <div id="exchangeEditorAddr" class="editor-addr">
	       <!-- 用于判断是否初次添加地址 -->
	   	  <input id="isFirstAddress" type="hidden" value="0">
	      <p>
	      	 <input id="saveType" type="hidden" value="update" /> 
	         <span style="float:left">修改收货地址</span>   
	         <a href="javascript:void(0)" id="save" class="editor-addr_btn">编辑完成</a>
	      </p>
	      <div class="form-addAddress">
	        <div>
	        	 <input id="addressId" type="hidden">
	        	 <input id="addressDefault" type="hidden"> 
				 <input id="addressStatus" type="hidden"> 
	             <span class="label">*收货人姓名：</span>
		         <input type="text" id="name" onblur="testName(this.value)"/>
		         <span id="nameTip" class="tip"></span>
		         <span class="label">*收货人电话：</span>
		         <input type="text" id="tel" onblur="testTel(this.value)"/>
		         <span id="telTip" class="tip"></span>
	        </div>
	        <div>
	             <span class="label">*选择地区：</span>
		         <select class="select-box" id="province" name="province"></select> 
		         <select class="select-box" id="city" name="city"></select> 
		         <select class="select-box" id="town" name="town"></select>
	        </div> 
	        <div>
	             <span class="label">*详细地址：</span>
		         <input type="text" id="addressDetail" onblur="testAddress(this.value)" style="width:666px;"/>
		         <span id="addressDetailTip" class="tip" style="margin-left:67px;"></span>
	        </div>
	      </div>
	   </div>
	</div>
	
	<div class="btns">
	  <a href="javascript:void(0)" id="confirm" onclick="confirmExchange()">确认兑换</a>
	</div>
</div>
<!-- 确认兑换结束 -->

<div class="rz-box-con exchangeSuccessDialog" id="exchangeSuccessDialog" style=" width:400px; padding-bottom:0px; height:230px; margin:-125px -200px;border:1px solid #ea5413;">
	<div class="rz-title" style="height: 40px; line-height: 40px;">
	    <h2 style="line-height: 40px;">兑换成功</h2>
		<a href="javascript:void(0)" class="rz-close" style="padding-top: 4px;margin-right: 13px;">
			<img src="/resource/images/img_rz-close.png" />
		</a>
	</div>
	<div class="exchangeSuccess_con" style="width:400px;"></div>  <!--.exchangeFail_con 兑换失败  -->
</div>

<div class="rz-box-con exchangeSuccessDialog" id="exchangeFailDialog" style=" width:400px; padding-bottom:0px; height:230px; margin:-125px -200px;border:1px solid #ea5413;">
	<div class="rz-title" style="height: 40px; line-height: 40px;">
	    <h2 style="line-height: 40px;">兑换失败</h2>
		<a href="javascript:void(0)" class="rz-close" style="padding-top: 4px;margin-right: 13px;">
			<img src="/resource/images/img_rz-close.png" />
		</a>
	</div>
	<div class="exchangeSuccess_con exchangeFail_con"></div>  <!--.exchangeFail_con 兑换失败  -->
</div>

<div class="rz-box-con removeGoods" id="removeGoods">
	<div class="rz-title">
	    <h2>删除兑换礼品</h2>
		<a href="javascript:void(0)" class="rz-close"><img
			src="/resource/images/img_rz-close.png" /></a>
	</div>
	<div style="text-align:center;height:100px;line-height:100px;font-size:18px;">确认要删除这些兑换礼品吗？</div>
	<div class="btns">
	  <a href="javascript:void(0)" class="rz-button ok" style="margin-left:143px;">确认</a>
	  <a href="javascript:void(0)"  class="rz-button reset">取消</a>
	</div>
</div>
<div class="rz-box-con removeGoodsTip" id="removeGoodsTip">
	<div class="rz-title">
		<a href="javascript:void(0)" class="rz-close"><img
			src="/resource/images/img_rz-close.png" /></a>
	</div>
	<div style="text-align:center;height:100px;line-height:100px;font-size:18px;">请选择您要删除的礼品</div>
	<div class="btns">
	  <a href="javascript:void(0)" class="rz-button ok" style="margin-left:143px;">确认</a>
	  <a href="javascript:void(0)"  class="rz-button reset">取消</a>
	</div>
</div>	

<!-- 确认收货 -->
<div class="rz-box-con exchangeSuccessDialog" id="confirmAcceptDiv" style=" display:none; width:400px; padding-bottom:0px; height:280px; margin:-125px -200px; border:1px solid #ea5413">
	<input type="hidden">
	<div class="rz-title" style="border-left:8px solid #ea5413; height: 40px; line-height: 40px;">
	    <h2 style="line-height: 40px; font-size: 14px; color: #4a4a4a;padding-left: 15px;">提示</h2>
		<a href="javascript:void(0)" class="rz-close" style=" padding-top: 4px;  margin-right: 13px;">
			<img src="/resource/images/img_rz-close.png" />
		</a>
	</div>
	<div class="clearfix" style="padding-bottom: 10px; border-bottom: 1px solid #cccccc;">
		<div class="qrshleft" style="margin:15px auto 0;  width: 80px; height: 80px; border-radius:40px; overflow: hidden;">
			<img style="width: 100%; height: 100%;" alt="">
		</div>
		<div class="qrshright" style="text-align:center;">
			<p style="text-align: center;"></p>
			<p style="text-align: center;">订单号：<span style="color:#ea5413;"></span></p>
		</div>
	</div> 
	<a href="javascript:void(0);" style="display: block; width: 168px;height: 30px; border:1px solid #ea5513; border-radius:5px; margin: 18px auto; line-height: 30px; text-align: center; color: #ea5513; font-size: 16px;">确认收货</a>
</div>

</body>
</html>