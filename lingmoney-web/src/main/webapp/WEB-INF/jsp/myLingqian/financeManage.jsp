<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
    content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="/resource/laydate/laydate.js"></script>
<script type="text/javascript"
    src="/resource/js/lingbaoMarketJs/public.js"></script>
<link rel="stylesheet" type="text/css" href="/resource/css/suiXinQu.css" />
<script type="text/javascript"
    src="/resource/js/myLingqian/financeManage.js"></script>

<style type="text/css">
#deep td {
    height: 40px;
}

.contents td {
    border: none;
}

.contents input {
    padding: 5px 10px;
    width: 200px;
    height: 20px;
    line-height: 20px;
}

.table02 {
    margin-top: 0;
}

.table02 td {
    line-height: 37px;
    height: 37px;
}
</style>

</head>
<body>
    <input type="hidden" id="isBindBank" value="${isBindBank }">

    <div id="rz-box-bg"></div>
    <!-- 交易流水弹框 -->
    <div class="rz-box-con protocol" id="trade">
        <div class="rz-title">
            <h2>交易流水</h2>
            <a href="javascript:void(0)" class="rz-close"
                style="padding-top: 15px;"> <img
                src="/resource/images/img_rz-close1.png" />
            </a>
        </div>
        <div style="margin: 0 auto; width: 660px; margin-top: 10px;">
            <dl class="dl02 clearfix">
                <dd>
                    <a href="javascript:void(0)" class="hover" id="latestTrade">最近30笔</a>
                </dd>
                <dd>
                    <a href="javascript:void(0)" id="allTrade">全部</a>
                </dd>
            </dl>
            <table class="table02" width="100%" cellpadding="0" cellspacing="0"
                border="0">
                <thead>
                    <tr>
                        <th width="110">操作时间</th>
                        <th width="110">支付完成时间</th>
                        <th width="50">类型</th>
                        <th width="50">状态</th>
                        <th width="70">金额（元）</th>
                        <th width="70">兑付银行账户</th>
                    </tr>
                </thead>
            </table>
        </div>
        <div class="pro_txt" style="margin-top: 5px; height: 343px;">
            <table class="table02" width="100%" cellpadding="0" cellspacing="0"
                border="0">
                <tbody id="takeHeartFlow"></tbody>
            </table>
        </div>


    </div>
    <!-- 零钱宝赎回弹框 -->
    <div class="rz-box-con " id="wallet">
        <div class="rz-title">
            <h2>我的随心取 - 赎回</h2>
            <a href="javascript:void(0)" class="rz-close"> <img
                src="/resource/images/img_rz-close.png" />
            </a>
        </div>

        <div style="margin: 0 auto; width: 600px;">
            <p style="padding-top: 30px; text-align: left">用户持有本理财计划实时金额最低为1000元，赎回时须为0.01元的整数倍。用户可选择全部赎回或部分赎回，部分赎回后用户持有本理财产品的实时金额不得低于1000元，否则将只接受用户的全部赎回申请。T日16：00前申请赎回，T+1到帐；T日16：00后赎回，T+2日到帐（如遇节假日顺延）。</p>

            <form style="width: 100%">
                <div id="divMainTab4">
                    <ul
                        style="list-style: none; margin: 0px; padding: 0px; border-collapse: collapse;"
                        class="clearfix">
                        <li><a href="javascript:void(0)" id="fun_1" class="selected"
                            onclick="changeTab4('1')">部分赎回</a></li>
                        <li style="border: none"><a href="javascript:void(0)"
                            id="fun_2" class="normal" onclick="changeTab4('2')">全部赎回</a></li>
                    </ul>
                </div>
                <div class="contents">
                    <div id="dd1" style="display: block" class="divContent">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0"
                            style="height: 86px;">
                            <tr>
                                <td style="height: 36px; width: 106px;">当前可提：</td>
                                <td style="height: 36px;" id="AllMoney"></td>
                            </tr>
                            <tr>
                                <td style="height: 50px; width: 106px;">赎回份额：</td>
                                <td style="height: 50px;"><input type="text"
                                    id="money_input" value="请输入赎回份额"
                                    onblur="if(this.value==''){this.value='请输入赎回份额';this.style.color='#434343'}"
                                    onfocus="if(this.value=='请输入赎回份额'){this.value=''}" />&nbsp;&nbsp;份（1份=1元）
                                    <span id="tianshi"></span></td>
                            </tr>
                        </table>
                    </div>
                    <div id="dd2" style="display: none" class="divContent">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0"
                            style="margin-bottom: 24px;">
                            <tr>
                                <td style="height: 50px; width: 106px;">当前可提：</td>
                                <td><input type="text" id="AllMoneyT"
                                    value="215,788,540.99" readonly="readonly" />&nbsp;&nbsp;元</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <p style="padding-top: 20px;">
                    <a href="javascript:void(0)" onclick="AllSellDiv()"
                        class="rz-button button_success">确认赎回</a> <a
                        href="javascript:void(0)" class="rz-button reset">取消</a>
                </p>
            </form>
        </div>
    </div>

    <div class="rz-box-con" id="AllSellDiv">
        <div class="rz-title">
            <h2>全部赎回确认</h2>
            <a href="javascript:void(0)" class="rz-close"> <img
                src="/resource/images/img_rz-close.png" />
            </a>
        </div>
        <p class="color" style="padding-top: 20px; font-size: 20px;">
                      您确定要执行此操作吗？
        </p>
        <p style="padding-top: 10px;">
            <a href="javascript:void(0)" class="rz-button" onclick="queding()">确定</a>
            <a href="javascript:void(0)" class="rz-button reset">取消</a>
        </p>
    </div>
    <div class="rz-box-con" id="portionSellDiv">
        <div class="rz-title">
            <h2>部分赎回确认</h2>
            <a href="javascript:void(0)" class="rz-close" id="closePortionSell">
                <img src="/resource/images/img_rz-close.png" />
            </a>
        </div>
        <p class="color" style="padding-top: 20px; font-size: 20px;">
            您确认赎回活钱宝-随心取产品吗？</p>
        <p class="color" style="padding-top: 20px; font-size: 20px;">
            赎回金额:<span id="sellMoneySure"></span>
        </p>
        <p style="padding-top: 10px;">
            <a href="javascript:void(0)" class="rz-button" onclick="queding()">确定</a>
            <a href="javascript:void(0)" class="rz-button reset"
                id="cancelPortionSell">取消</a>
        </p>
    </div>

    <!-- 申请赎回细则弹框 -->
    <div class="rz-box-con" id="ask">
        <div class="rz-title">
            <h2>赎回确认</h2>
            <a href="javascript:void(0)" class="rz-close"> <img
                src="/resource/images/img_rz-close.png" />
            </a>
        </div>
        <p class="color" style="padding-top: 20px; font-size: 20px;">您确定要赎回此产品吗？</p>
        <p style="padding-top: 10px;">
            <a href="javascript:void(0)" class="rz-button" onclick="queding()">确定</a>
            <a href="javascript:void(0)" class="rz-button reset">取消</a>
        </p>
    </div>

    <!-- 申请赎回处理中 -->
    <div class="rz-box-con" id="opration">
        <div class="rz-title">
            <h2>赎回处理中</h2>
        </div>
        <p class="color" style="padding-top: 20px; font-size: 24px;">您要赎回的产品正在处理中。。。</p>
    </div>

    <div class="rz-box-con " id="fail">
        <div class="rz-title">
            <h2>赎回失败</h2>
            <a href="javascript:void(0)" class="rz-close"><img
                src="/resource/images/img_rz-close.png" /></a>
        </div>
        <p class="color" style="padding-top: 20px; font-size: 20px;">
            <span id="error_content">您赎回此产品失败</span>
        </p>
        <p style="padding-top: 10px;">
            <a href="/myFinancial/finanCialManage" class="rz-button">确定</a>
        </p>
    </div>

    <div class="rz-box-con " id="fails">
        <div class="rz-title">
            <h2>退回失败</h2>
            <a href="javascript:void(0)" class="rz-close"> <img
                src="/resource/images/img_rz-close.png" /></a>
        </div>
        <p class="color" style="padding-top: 20px; font-size: 20px;">您退回此产品失败</p>
        <p style="padding-top: 10px;">
            <a href="/myFinancial/finanCialManage" class="rz-button button_ok">确定</a>
        </p>
    </div>

    <div class="rz-box-con " id="failss">
        <div class="rz-title">
            <h2>赎回失败</h2>
            <a href="javascript:void(0)" class="rz-close"> <img
                src="/resource/images/img_rz-close.png" />
            </a>
        </div>
        <p class="color" style="padding-top: 20px; font-size: 20px;">你的这款产品有未完成的处理申请，请勿重复提交</p>
        <p style="padding-top: 10px;">
            <a href="/myFinancial/finanCialManage" class="rz-button button_ok">确定</a>
        </p>
    </div>


    <div class="rz-box-con " id="walletSucc">
        <div class="rz-title">
            <h2>赎回成功</h2>
            <a href="javascript:void(0)" class="rz-close"> <img
                src="/resource/images/img_rz-close.png" /></a>
        </div>
        <p class="color" style="padding-top: 20px; font-size: 20px;">
            赎回成功,请继续理财！</p>
        <p style="padding-top: 10px;">
            <a href="/myFinancial/finanCialManage" class="rz-button button_ok">确定</a>
        </p>
    </div>
    <div class="rz-box-con " id="nobindbank">
        <div class="rz-title">
            <h2>赎回失败</h2>
            <a href="javascript:void(0)" class="rz-close"> <img
                src="/resource/images/img_rz-close.png" />
            </a>
        </div>
        <p class="color" style="padding-top: 20px; font-size: 20px;">
            请您先绑定银行卡再进行赎回操作！</p>
        <p style="padding-top: 10px;">
            <a href="/myFinancial/finanCialManage" class="rz-button button_ok">确定</a>
        </p>
    </div>

    <input type="hidden" id="tid" />
    <input type="hidden" id="checkMoney" />
    <input type="hidden" id="productmodel" />
    <input type="hidden" id="redeemType" />
    <!-- 赎回确认页面-->
    <div class="rz-box-con" id="tou_xqd" style="width: 750px">
        <div class="rz-title">
            <h2>赎回详情</h2>
            <a href="/myFinancial/finanCialManage" class="rz-close"> <img
                src="/resource/images/img_rz-close.png" /></a>
        </div>
        <h1>领钱儿项目赎回详情</h1>
        <div class="xqd_top clearfix">
            <div class="xqd_left">
                <p>
                    项目名称：<span class="color" id="pname"></span>
                </p>
                <p>
                    生效时间：<span class="color" id="buyDate"></span>
                </p>
                <p>
                    赎回时间：<span class="color" id="sellDate"></span>
                </p>
                <p>
                    理财额度：<span class="color" id="financialMoney"></span>元
                </p>
                <p>
                    手续费：<span class="color" id="sp"></span>元
                </p>
                <p id="zhuy"></p>
            </div>
            <div class="xqd_right">
                <p>&nbsp;</p>
                <p>
                    预期年化收益率：<span class="color" id="yield"
                        style="padding: 0; float: none"></span>
                </p>
                <p>
                    理财期限：<span class="color" id="financialTime"
                        style="padding: 0; float: none"></span>天
                </p>
                <p>
                    收益金额：<span class="color" id="income"
                        style="padding: 0; float: none"></span>元
                </p>
            </div>
        </div>

        <div class="xqd_bottom">
            <p>
                赎回金额：<span class="color" style="font-size: 28px;" id="sellMoney"></span>元
            </p>
            <p id="lingbaoNum"></p>
        </div>

        <p style="margin: 10px auto; width: 460px;">
            <a href="/myFinancial/finanCialManage" class="rz-button button-ok">确定</a>
        </p>
    </div>
    <!-- 赎回确认页面-->

    <!-- 头部开始 -->
    <jsp:include page="/common/welcome.jsp"></jsp:include>
    <!-- 头部结束 -->

    <!-- 用户导航开始 -->
    <jsp:include page="/common/navmember.jsp"></jsp:include>
    <!-- 用户导航结束 -->
    <div class="mainbody_member">
        <div class="box clearfix">
            <!-- 我的理财菜单开始 -->
            <jsp:include page="/common/mymanage.jsp"></jsp:include>
            <!-- 我的理财菜单结束 -->

            <form action="/myFinancial/finanCialManage" id="queryForm"
                method="post">
                <input type="hidden" name="status" id="status" value="${status}">
            </form>

            <div class="mRight" style="min-height: 600px">
                <div class="mRight01">
                    <div class="mtitle">理财管理</div>
                    <dl class="dl02 clearfix" style="padding: 20px 0 0 25px;">
                        <dt>理财状态:</dt>
                        <dd>
                            <a href="javascript:void(0)" id="quanb1" onclick="copeStatus(1)">持有中</a>
                        </dd>
                        <dd>
                            <a href="javascript:void(0)" id="quanb2" onclick="copeStatus(2)">待回款</a>
                        </dd>
                        <dd>
                            <a href="javascript:void(0)" id="quanb3" onclick="copeStatus(3)">已回款</a>
                        </dd>
                        <dd>
                            <a href="javascript:void(0)" id="quanb0" onclick="copeStatus(0)">待支付</a>
                        </dd>
                        <dd>
                            <a href="javascript:void(0)" id="quanb12"
                                onclick="copeStatus(12)">支付中</a>
                        </dd>
                        <dd>
                            <a href="javascript:void(0)" id="quanb4" onclick="copeStatus(4)">待成立</a>
                        </dd>
                    </dl>
                    <c:if test="${status==0}">
                        <table class="licaitip">
                            <thead>
                                <tr>
                                    <td>待支付总金额</td>
                                    <td>
                                        <!-- 预期总收益： -->
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${amount}<span>元</span></td>
                                    <td>
                                        <!-- ${profit}<span>元</span> -->
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${status==1}">
                        <table class="licaitip">
                            <thead>
                                <tr>
                                    <td>持有中总金额</td>
                                    <td>
                                        预期总收益
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${amount}<span>元</span></td>
                                    <td>
                                        ${profit}<span>元</span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${status==2}">
                        <table class="licaitip">
                            <thead>
                                <tr>
                                    <td>待回款总金额</td>
                                    <td>
                                        预期总收益
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${amount}<span>元</span></td>
                                    <td>
                                        ${profit}<span>元</span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${status==3}">
                        <table class="licaitip">
                            <thead>
                                <tr>
                                    <td>已回款总金额</td>
                                    <td>
                                        已赚总收益
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${amount}<span>元</span></td>
                                    <td>
                                        ${profit}<span>元</span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${status==4}">
                        <table class="licaitip">
                            <thead>
                                <tr>
                                    <td>待成立总金额</td>
                                    <td>
                                        <!--预期总收益-->
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${amount}<span>元</span></td>
                                    <td>
                                    <!--    ${profit}<span>元</span>-->
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${status==12}">
                        <table class="licaitip">
                            <thead>
                                <tr>
                                    <td>支付中总金额</td>
                                    <td>
                                        <!-- 预期总收益 -->
                                    </td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${amount}<span>元</span></td>
                                    <td>
                                        <!--${profit}<span>元</span>-->
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </c:if>

                    <!-- 核心内容 S -->
                    <div class="divContent">
                        <!-- 遍历我的理财 S -->
                        <c:forEach items="${gridPage.rows }" var="item">
                            <!-- 区分稳盈宝(车贷宝)或者随心取 S -->
                            <c:choose>
                                <c:when test="${item.pudType==0}">
                                    <!-- 稳盈宝(车贷宝) S -->
                                    <c:choose>
                                        <c:when test="${item.status==0}">
                                            <!-- 待支付 S -->
                                            <div class="licaiBox">
                                                <div class="licaiDivContent toBePaid">
                                                    <div class="ldc_1 clearfix">
                                                        <span class="ldc_prdName">${item.product.name }</span>
                                                        <c:if test="${item.product.pType == 2}">
                                                            <img class="hxbank_logo"
                                                                src="/resource/images/hxbank_btn_logo.png">
                                                        </c:if>
                                                        <div class="tobeTiem clearfix">
                                                            <span>支付剩余时间：</span> <input id="theRestTimeOfPayment"
                                                                value="${item.theRestTimeOfPayment }" type="hidden">
                                                            <a href="javascript:void(0);">00:15:00</a>
                                                        </div>
                                                    </div>
                                                    <div class="ldc_2 clearfix">
                                                        <div class="tobeLeft">
                                                            <p>
                                                                项目名称：<span>${item.product.name }</span>
                                                            </p>
                                                            <p>
                                                                项目期限：<span>${item.product.fTime }天</span>
                                                            </p>
                                                            <p>
                                                                购买时间：<span><fmt:formatDate value="${item.buyDt}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss" /></span>
                                                            </p>
                                                        </div>
                                                        <div class="tobeRight">
                                                            <p>
                                                                理财额度：<span><fmt:formatNumber pattern="#,##0.00#"
                                                                        value="${item.financialMoney}" />元</span>
                                                            </p>
                                                            <p>
                                                               预期年化收益率：<span><fmt:formatNumber pattern="##.0"
                                                                        value="${item.product.fYield * 100}" />%</span>
                                                            </p>
                                                            <p>
                                                                到期时间：<span>到日期以产品成立计算后${item.product.fTime }天</span>
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <div class="ldc_3 clearfix">
                                                        <p class="payMoney">
                                                            支付金额：<span><fmt:formatNumber pattern="#,##0.00#"
                                                                    value="${item.financialMoney}" /></span>元
                                                        </p>
                                                        <div class="xieyi">
                                                            <p>
                                                                <input type="checkbox" class="input_agree"
                                                                    checked="checked" />同意并阅读领钱儿 <a
                                                                    href="javascript:void(0);" id="assignmentOfCredit">《借款协议》</a>
                                                            </p>
                                                        </div>
                                                        <div class="payBox">
                                                            <a href="javascript:void(0)"
                                                                onclick="gotoPay(${item.id}, ${item.product.pType})"
                                                                class="toPay">立即支付</a> <a href="javascript:void(0)"
                                                                onclick="cancelPayment(${item.id})" class="unPay">取消支付</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- 待支付 E -->
                                        </c:when>
                                        <c:otherwise>
                                            <!-- 根据不同状态设置样式 S -->
                                            <!-- 持有中 S -->
                                            <c:if test="${item.status==1}">
                                                <c:set var="classDivContent" value="licaiDivContent hold" />
                                                <c:set var="financialDaysVal" value="${item.financialDays }" />
                                                <c:set var="interestVal" value="预期收益（元）" />
                                            </c:if>
                                            <!-- 持有中 E -->
                                            <!-- 待回款 S -->
                                            <c:if test="${item.status==2}">
                                                <c:set var="classDivContent"
                                                    value="licaiDivContent receivable" />
                                                <c:set var="interestVal" value="预期收益（元）" />
                                            </c:if>
                                            <!-- 待回款 E -->
                                            <!-- 已回款 S -->
                                            <c:if test="${item.status==3}">
                                                <c:set var="classDivContent"
                                                    value="licaiDivContent backReceivable" />
                                                <c:set var="interestVal" value="已赚收益（元）" />
                                            </c:if>
                                            <!-- 已回款 E -->
                                            <!-- 冻结中 S -->
                                            <c:if test="${item.status==4}">
                                                <c:set var="classDivContent" value="licaiDivContent freeze" />
                                                <c:set var="interestVal" value="预期收益（元）" />
                                            </c:if>
                                            <!-- 冻结中 E -->
                                            <!-- 支付中 S -->
                                            <c:if test="${item.status==12}">
                                                <c:set var="classDivContent" value="licaiDivContent freeze" />
                                                <c:set var="interestVal" value="预期收益（元）" />
                                            </c:if>

                                            <!-- 支付中 E -->
                                            <!-- 根据不同状态设置样式 E -->

                                            <div class="licaiBox">
                                                <div class="${classDivContent }">
                                                    <div class="ldc_1 clearfix">
                                                        <span class="ldc_prdName">${item.product.name}</span>
                                                        <c:if test="${item.product.pType == 2}">
                                                            <img class="hxbank_logo"
                                                                src="/resource/images/hxbank_btn_logo.png">
                                                        </c:if>
                                                        <c:if test="${item.showContract == 1}">
                                                            <a target="_black" href="/pdf/PdfPath?tId=${item.id }">查看《借款协议》 </a>
                                                        </c:if>
                                                     <c:if test="${item.product.insuranceTrust == 1}">
                                                        <span class="style-insurance">履约险承保</span>
                                                       </c:if> 
                                                        
                                                        
                                                    </div>
                                                    <div class="ldc_2 clearfix">
                                                        <div class="myPrincipal">
                                                            <span>理财金额（元）</span>
                                                            <p>
                                                                <fmt:formatNumber pattern="#,##0.00#"
                                                                    value="${item.financialMoney}" />
                                                            </p>
                                                        </div>
                                                        <div class="annualEarnings">
                                                            <span>预期年化收益率（%）</span>
                                                            <p>
                                                            <c:if
														        test="${item.product.addYield>0}">	
														        <fmt:formatNumber pattern="##0.0"
                                                                    value="${item.product.fYield * 100-item.product.addYield * 100}" />
                                                                    +<fmt:formatNumber pattern="##0.0"
                                                                    value="${item.product.addYield * 100}" />    
                                                                                      
                                                                  
                                                                     </c:if>
                                                                     <c:if
														            test="${item.product.addYield==0 || item.product.addYield==null}">	
                                                                         <fmt:formatNumber pattern="##0.0"
                                                                            value="${item.product.fYield * 100}" /> 
                                                                                                            
                                                                     </c:if>
                                                                     
                                                                <!-- 判断有无加息券或红包 S -->
                                                                <c:if test="${not empty item.hxRedPacket.hrpType}">
                                                                    <c:if test="${item.hxRedPacket.hrpType == 1}">+<fmt:formatNumber
                                                                            pattern="#0.0"
                                                                            value="${item.hxRedPacket.hrpNumber * 100}" />
                                                                    </c:if>
                                                                </c:if>
                                                                <!-- 判断有无加息券或红包 S -->
                                                            </p>
                                                        </div>
                                                        <div class="limit">
                                                           <c:choose>
                                                              <c:when test="${item.product.id == 53}">
                                                                    <span>已持有天数（天）</span>
                                                                    <p>
                                                                        <c:if test="${item.financialDays != null }">
                                                                            <span>${item.financialDays }</span>
                                                                        </c:if>
                                                                    </p>
                                                              </c:when>
                                                              <c:otherwise>
                                                                    <span>项目期限（天）</span>
                                                                    <p>
																<c:if test="${financialDaysVal != null }">
																	<span>${financialDaysVal }</span>/</c:if>${item.product.fTime }</p>
                                                              </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <div class="borderRightImg" style="width:36px;"></div>
                                                        <div class="earnings" style="width:130px;">
                                                            <c:if test="${item.status != 12 && item.status != 4}">
                                                                <span>${interestVal }</span>
                                                                <c:choose>
                                                                    <c:when test="${item.product.id == 53}">
                                                                        <fmt:parseNumber value="${item.principal}" var="principal"/>
                                                                        <fmt:parseNumber value="${item.financialMoney}" var="finalMoney"/>
                                                                        <p><fmt:formatNumber value="${principal - finalMoney}" pattern="##0.00"/></p>
                                                                        
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <p>
                                                                            <span><fmt:formatNumber pattern="#,##0.00#"
                                                                                    value="${item.interest}" /> <!-- 判断有无加息券或红包 S --> <c:if
                                                                                    test="${not empty item.hxRedPacket.hrpType}">
                                                                                    <c:if test="${item.hxRedPacket.hrpType == 1}">+<fmt:formatNumber
                                                                                            pattern="#,##0.00#"
                                                                                            value="${item.usersRedPacket.actualAmount}" />
                                                                                    </c:if>
                                                                                </c:if> <!-- 判断有无加息券或红包 E --> </span>
                                                                        </p>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:if>
                                                        </div>
                                                        <c:if test="${item.status==1 && item.product.id == 53}">
                                                            <a onclick="planARedeem(${item.product.fYield * 100}, ${item.id })" style="display:block; width:75px;height:30px; text-align:center; line-height:30px;font-size:14px; color:#ffffff;background:#ea5513; float:left;margin-left:0px;margin-top:30px;" href="javascript:void(0)" >赎回</a>
                                                        </c:if>
                                                                        
                                                    </div>
                                                    <div class="ldc_3 clearfix">
                                                        <span>购买时间： <c:if
                                                                test="${item.buyDt != null && item.buyDt != 'null'}">
                                                                <fmt:formatDate value="${item.buyDt}"
                                                                    pattern="yyyy-MM-dd HH:mm:ss" />
                                                            </c:if>
                                                        </span> <a href="javascript:void(0);">到期时间： <c:if
                                                                test="${item.minSellDt != null && item.minSellDt != 'null'}">
                                                                <fmt:formatDate value="${item.minSellDt}"
                                                                    pattern="yyyy-MM-dd " />
                                                            </c:if>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <!-- 稳盈宝(车贷宝) E -->
                                </c:when>
                                <c:when test="${item.pudType==1}">
                                    <!-- 随心取 S -->
                                    <input type="hidden" id="allTradeFlow"
                                        onclick="btn_trade(${item.product.id},${item.id})">
                                    <c:choose>
                                        <c:when test="${item.status==1}">
                                            <!-- 持有中 S -->
                                            <div class="sDivItem">
                                                <!-- 顶部 S -->
                                                <div class="sDivItemTop clearfix">
                                                    <span class="sleft">${item.product.name}</span> <a
                                                        class="btn_trade"
                                                        onclick="btn_trade(${item.product.id},${item.id},30)">交易流水</a>
                                                </div>
                                                <!-- 顶部 E -->
                                                <!-- 中部 S -->
                                                <!-- 根据不同等级设置样式 S -->
                                                <c:choose>
                                                    <c:when test="${item.stalls=='A'}">
                                                        <c:set var="classCenter"
                                                            value="sDivItemCenter CenterCon_A" />
                                                        <c:set var="classProgress" value="s_progress s_progress_A" />
                                                    </c:when>
                                                    <c:when test="${item.stalls=='B'}">
                                                        <c:set var="classCenter"
                                                            value="sDivItemCenter CenterCon_B" />
                                                        <c:set var="classProgress" value="s_progress s_progress_B" />
                                                    </c:when>
                                                    <c:when test="${item.stalls=='C'}">
                                                        <c:set var="classCenter" value="sDivItemCenter" />
                                                        <c:set var="classProgress" value="s_progress" />
                                                    </c:when>
                                                </c:choose>
                                                <!-- 根据不同等级设置样式 E -->
                                                <div class="${classCenter }">
                                                    <div class="CenterCon  clearfix">
                                                        <div class="CenterConL">
                                                            <p class="p_sy">本金+预期收益（元）</p>
                                                            <p class="sy">${item.principal}</p>
                                                        </div>
                                                        <div class="CenterConR">
                                                            <p>
                                                                首次购买时间：
                                                                <c:if
                                                                    test="${item.buyDt != null && item.buyDt != 'null'}">
                                                                    <fmt:formatDate value="${item.buyDt}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss" />
                                                                </c:if>
                                                            </p>
                                                            <p>
                                                                累计收益：
                                                                <c:if test="${item.allInterest != null}">
                                                                    <fmt:formatNumber pattern="#,##0.00#"
                                                                        value="${item.allInterest}" />元
                                                                </c:if>
                                                            </p>
                                                            <p>
                                                                预期收益：
                                                                <c:if test="${item.hreatInterest != null}">
                                                                    <fmt:formatNumber pattern="#,##0.00#"
                                                                        value="${item.hreatInterest}" />元
                                                                </c:if>
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <div class="${classProgress }">
                                                        <div class="ani">
                                                            <p></p>
                                                        </div>
                                                    </div>
                                                    <p class="CenterBot">
                                                        <c:choose>
                                                            <c:when
                                                                test="${empty item.nextStalls||item.nextStalls==''}">
                                                                 您当前所持的“随心取”理财产品为 “<span>${item.stalls}档</span>”
                                                            </c:when>
                                                            <c:otherwise>
                                                                您当前所持的“随心取”理财产品为 “<span>${item.stalls}档 </span>”距升级为“${item.nextStalls}档”还差<span>
                                                                    <c:if test="${item.difference != null}">
                                                                        <fmt:formatNumber pattern="#,##0.00#"
                                                                            value="${item.difference}" />
                                                                    </c:if>
                                                                </span>元。
                                                             </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </div>
                                                <!-- 中部 E -->
                                                <!-- 底部 S -->
                                                <div class="sDivItemBot clearfix">
                                                    <ul class="clearfix">
                                                        <li style="padding-left: 22px; width: 200px">
                                                            <p>我的本金（元）</p> <span> <c:if
                                                                    test="${item.financialMoney != null}">
                                                                    <fmt:formatNumber pattern="#,##0.00#"
                                                                        value="${item.financialMoney}" />
                                                                </c:if>
                                                        </span>
                                                        </li>
                                                        <li style="width: 174px">
                                                            <p>预期年化收益率</p> <span> <c:if
                                                                    test="${item.product.fYield != null}">
                                                                    <c:if
                                                                    test="${item.product.addYield > 0}">
                                                                      
                                                                        <fmt:formatNumber type="percent"
                                                                        value="${item.product.fYield-item.product.addYield}" maxFractionDigits="3"
                                                                        minFractionDigits="1" />+
                                                                         <fmt:formatNumber type="percent"
                                                                        value="${item.product.addYield}" maxFractionDigits="3"
                                                                        minFractionDigits="1" />
                                                                     </c:if>
                                                                 <c:if
                                                                    test="${item.product.addYield == 0 || item.product.addYield == null}">
                                                                       <fmt:formatNumber type="percent"
                                                                        value="${item.product.fYield }"   maxFractionDigits="3"
                                                                        minFractionDigits="1" />                  
                                                                        
                                                                </c:if>
                                                                </c:if>
                                                        </span>
                                                        </li>
                                                        <li style="width: 198px">
                                                            <p>已持有天数（天）</p> <span>${item.financialDays}</span>
                                                        </li>
                                                    </ul>
                                                    <div class="botBtn">
                                                        <input type="hidden" id="runDate"
                                                            value="${item.financialDays}"> <a
                                                            href="/product/showProduct?changTab=&amp;code=12001704020391">申购</a>
                                                        <c:choose>
                                                            <c:when test="${item.redeemFlag==0}">
                                                                <input type="hidden" id="takeRate"
                                                                    value="${item.product.fYield}">
                                                                <button type="button"
                                                                    
                                                                    onclick="shuhui(${item.id},${item.pudType},${item.financialMoney})">赎回</button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button type="button" disabled="disabled"
                                                                    
                                                                    onclick="shuhui(${item.id},${item.pudType},${item.financialMoney})">赎回</button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                                <!-- 底部 E -->
                                            </div>
                                            <!-- 持有中 E -->
                                        </c:when>
                                        <c:when test="${item.status==2}">
                                            <!-- 待回款 S -->
                                            <div class="sDivItem">
                                                <!-- 顶部 S -->
                                                <div class="sDivItemTop clearfix">
                                                    <span class="sleft">${item.product.name}</span> <a
                                                        class="btn_trade"
                                                        onclick="btn_trade(${item.product.id},${item.id},30)">交易流水</a>
                                                </div>
                                                <!-- 顶部 E -->
                                                <!-- 中部 S -->
                                                <div class="sDivItemCenter shu_center">
                                                    <div class="CenterCon clearfix">
                                                        <div class="CenterConL">
                                                            <p class="p_sy">本金+预期收益（元）</p>
                                                            <p class="sy">${item.principal}</p>
                                                        </div>
                                                        <div class="CenterConR">
                                                            <p>
                                                                首次购买时间：
                                                                <c:if
                                                                    test="${item.buyDt != null && item.buyDt != 'null'}">
                                                                    <fmt:formatDate value="${item.buyDt}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss" />
                                                                </c:if>
                                                            </p>
                                                            <p>
                                                                累计收益：
                                                                <c:if test="${item.allInterest != null}">
                                                                    <fmt:formatNumber pattern="#,##0.00#"
                                                                        value="${item.allInterest}" /> 元
                                                                    </c:if>
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- 中部 E -->
                                                <!-- 底部 S -->
                                                <div class="sDivItemBot sDivItemBots clearfix">
                                                    <ol class="clearfix">
                                                        <li>理财本金： <span> <c:if
                                                                    test="${item.financialMoney != null}">
                                                                    <fmt:formatNumber pattern="#,##0.00#"
                                                                        value="${item.financialMoney}" /> 元
                                                                    </c:if>
                                                        </span>
                                                        </li>
                                                        <li>预期年化收益率： <c:if
                                                                test="${item.product.fYield != null}">
                                                                
                                                                <c:if
                                                                    test="${item.product.addYield > 0}">
                                                                    <fmt:formatNumber type="percent"
                                                                        value="${item.product.fYield-item.product.addYield}" maxFractionDigits="3"
                                                                        minFractionDigits="1" />+
                                                                         <fmt:formatNumber type="percent"
                                                                        value="${item.product.addYield}" maxFractionDigits="3"
                                                                        minFractionDigits="1" />
                                                                     </c:if>
                                                                 <c:if
                                                                    test="${item.product.addYield == 0 || item.product.addYield == null}">
                                                                    <fmt:formatNumber type="percent"
                                                                        value="${item.product.fYield }"   maxFractionDigits="3"
                                                                        minFractionDigits="1" />
                                                                </c:if>
                                                                                      
                                                            </c:if>
                                                        </li>
                                                        <li>持有天数：${item.financialDays}天</li>
                                                    </ol>
                                                </div>
                                                <!-- 底部 E -->
                                            </div>
                                            <!-- 待回款 E -->
                                        </c:when>
                                        <c:when test="${item.status==3}">
                                            <!-- 已回款 S -->
                                            <div class="sDivItem" style="border-color: #c2c2c2;">
                                                <!-- 顶部 S -->
                                                <div class="sDivItemTop clearfix">
                                                    <span class="sleft">${item.product.name}</span> <a
                                                        class="btn_trade"
                                                        onclick="btn_trade(${item.product.id},${item.id},30)">交易流水</a>
                                                </div>
                                                <!-- 顶部 E -->
                                                <!-- 中部 S -->
                                                <div class="sDivItemCenter yi_center" style="background-color: #e4e4e4;" >
                                                    <div class="CenterCon clearfix">
                                                        <div class="CenterConL">
                                                            <p class="p_sy">本金+预期收益（元）</p>
                                                            <p class="sy">${item.principal}</p>
                                                        </div>
                                                        <div class="CenterConR">
                                                            <p>
                                                                首次购买时间：
                                                                <c:if
                                                                    test="${item.buyDt != null && item.buyDt != 'null'}">
                                                                    <fmt:formatDate value="${item.buyDt}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss" />
                                                                </c:if>
                                                            </p>
                                                            <p>
                                                                累计收益：
                                                                <c:if test="${item.allInterest != null}">
                                                                    <fmt:formatNumber pattern="#,##0.00#"
                                                                        value="${item.allInterest}" />元
                                                                </c:if>
                                                            </p>
                                                            <p>
                                                                预期收益：
                                                                <c:if test="${item.hreatInterest != null}">
                                                                    <fmt:formatNumber pattern="#,##0.00#"
                                                                        value="${item.hreatInterest}" />元
                                                                </c:if>
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- 中部 E -->
                                                <!-- 底部 S -->
                                                <div class="sDivItemBot sDivItemBots clearfix">
                                                    <ol class="clearfix">
                                                        <li>理财本金： <c:if test="${item.financialMoney != null}">
                                                                <fmt:formatNumber pattern="#,##0.00#"
                                                                    value="${item.financialMoney}" /> 元
                                                                </c:if>
                                                        </li>
                                                        <li>预期年化收益率： <c:if
                                                                test="${item.product.fYield != null}">
                                                                <c:if
                                                                    test="${item.product.addYield > 0}">
                                                                    <fmt:formatNumber type="percent"
                                                                        value="${item.product.fYield-item.product.addYield}" maxFractionDigits="3"
                                                                        minFractionDigits="1" />+
                                                                         <fmt:formatNumber type="percent"
                                                                        value="${item.product.addYield}" maxFractionDigits="3"
                                                                        minFractionDigits="1" />
                                                                     </c:if>
                                                                 <c:if
                                                                    test="${item.product.addYield == 0 || item.product.addYield == null}">
                                                                    <fmt:formatNumber type="percent"
                                                                        value="${item.product.fYield }"   maxFractionDigits="3"
                                                                        minFractionDigits="1" />
                                                                </c:if>
                                                                
                                                            </c:if>
                                                        </li>
                                                        <li>持有天数：${item.financialDays}天</li>
                                                    </ol>
                                                </div>
                                                <!-- 底部 E -->
                                            </div>
                                            <!-- 已回款 E -->
                                        </c:when>
                                    </c:choose>
                                    <!-- 随心取 E -->
                                </c:when>
                            </c:choose>
                            <!-- 区分稳盈宝（车贷宝）或者随心取 E -->
                        </c:forEach>
                        <!-- 遍历我的理财 S -->
                    </div>
                    <!-- 核心内容 E -->
                    <!-- 分页 S -->
                    <div class="pages">
                        <w:pager pageSize="${pageSize}" pageNo="${pageNo}" url=""
                            recordCount="${gridPage.total}" />
                    </div>
                    <!-- 分页 E -->
                </div>
            </div>
        </div>
    </div>
    <!-- 确认取消支付弹框 -->
    <div id="cancelPaymentAlert" class="cancelPaymentAlert"
        style="display: none; z-index: 9999;">
        <div class="contentBox">您确定取消支付吗？</div>
        <div class="buttonBox clearfix">
            <a class="determine" href="javascript:void(0);">确定</a> <a
                class="cancel" href="javascript:void(0);">取消</a>
        </div>
    </div>

    <!-- 表单提交华兴银行 Start -->
    <form action="" target="_blank" method="post" id="hxBankForm">
        <input type="hidden" id="requestData" name="RequestData" /> <input
            type="hidden" id="transCode" name="transCode" />
    </form>
    <!-- 表单提交华兴银行 End -->

    <!-- 表单提交京东 Start -->
    <form action="" target="_blank" method="post" id="jdActiveForm">
        <input type="hidden" id="jdTradingId" name="jdTradingId" value=''>
    </form>
    <!-- 表单提交京东银行 End -->

    <!-- 立即支付form S -->
    <form id="paymentForm" action="" method="post" target="_blank">
        <input type="hidden" name="tId" id="paymentTid">
    </form>
    <!-- 立即支付form E -->

    <!-- 自定义弹框 start -->
    <div class="rz-box-con" id="div_customer">
        <div class="rz-title">
            <h2></h2>
            <a href="javascript:void(0)" class="rz-close"> <img
                src="/resource/images/img_rz-close.png" /></a>
        </div>
        <p style="padding-top: 30px; font-size: 16px; text-align: center">

        </p>
        <p style="padding-top: 20px;">
            <a href="javascript:void(0)" class="rz-button"></a> <a
                href="javascript:void(0)" class="rz-button reset"></a>
        </p>
    </div>
    <!-- 自定义弹框 end -->

    <!-- 自定义弹框提示信息 start -->
    <div class="rz-box-con" id="div_customer_tip" style="width: 400px;">
        <div class="rz-title">
            <h2></h2>
            <a href="javascript:void(0)" class="rz-close"> <img
                src="/resource/images/img_rz-close.png" /></a>
        </div>
        <p
            style="padding-top: 30px; font-size: 20px; text-align: center; color: #ea5513">
        </p>
        <p style="padding-top: 20px;">
            <a href="javascript:void(0)" class="rz-button" id="ok_button">确定</a>
        </p>
    </div>
    <!-- 自定义弹框提示信息 end -->

    <!-- 含有确定取消按钮的小弹框 start -->
    <div class="rz-box-con" id="bombBox" style="width: 500px">
        <div class="rz-title">
            <h2>提示</h2>
        </div>
        <div
            style="padding: 20px 0; margin: 0 auto; width: 400px; text-align: center; font-size: 1.5em;"
            id="bombBoxContent"></div>
        <p style="padding-top: 10px;" id="ensureCancel">
            <a href="javascript:void(0)" class="rz-button">确定</a><a
                href="javascript:void(0)" class="rz-button"
                style="background: #eee; color: #535353;">取消</a>
        </p>
    </div>
    <!-- 含有确定取消按钮的小弹框 end -->

    <!-- 立即支付弹窗 S-->
    <div class="rz-box-con" id="paymentDiv">
        <div class="rz-title">
            <h2>请在新页面完成支付</h2>
            <a href="javascript:void(0)" class="rz-close"> <img
                src="/resource/images/img_rz-close.png" /></a>
        </div>
        <p style="padding-top: 30px; font-size: 16px; text-align: center">
            完成支付前请<span style="margin: 0 5px; color: #ea5513">不要关闭此窗口</span>，完成提现后根据您的情况点击下面按钮
        </p>
        <p style="padding-top: 20px;">
            <a href="/bank/singleBiddingResult" class="rz-button ">已完成支付</a> <a
                href="/bank/singleBiddingResult" class="rz-button reset">支付遇到问题</a>
        </p>
    </div>
    <!-- 立即支付弹窗 E-->

    <!-- 债权转让协议 -->
    <div class="rz-box-con protocol" id="assignmentOfCreditDialog">
        <jsp:include page="/common/loanAgreement.jsp"></jsp:include>
        <div
            style="margin: 0 auto; width: 640px; padding-top: 20px; text-align: center">
            <a href="javascript:void(0)" class="a_go1">同意并继续</a>
        </div>
    </div>
    
    <!-- 底部开始 -->
    <jsp:include page="/common/bottom.jsp"></jsp:include>
    <!-- 底部结束 -->
    <script type="text/javascript">
        function paddingzhifu(){
            if($('#quanb12').hasClass('hover')){
                $('.myPrincipal').css('padding-left','110px');
                $('.borderRightImg').css('background','none');
            }
            if($('#quanb4').hasClass('hover')){
                $('.myPrincipal').css('padding-left','110px');
                $('.borderRightImg').css('background','none');
            }
        }
        $(function(){
            paddingzhifu();
            $('.dl02 dd').click(function(){
                paddingzhifu(); 
            });
        });
        
        function planARedeem(yeild, tid) {
        	$("#tid").val(tid);
        	$("#productmodel").val(1); // 设置类型为1，直接提示处理结果。 
        	$("#redeemType").val(2);//赎回类型为全部赎回
        	$("#takeValueID").text(yeild + "%");
            offsetDiv("#AllSellDiv");
            $("#rz-box-bg").hide();
            $("#wallet").hide();
            $("#rz-box-bg").show();
            $("#AllSellDiv").show();           
       }
        
    </script>
</body>
</html>