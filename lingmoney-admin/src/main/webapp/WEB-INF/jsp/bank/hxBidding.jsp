<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>华兴银行单笔发标</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/formatter.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/bank/hxBidding.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/loading.js"></script>

<style type="text/css">
.address {
	Font-size: 16px;
	Font-family: 微软雅黑;
}
</style>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>选择产品：</td>
						<td>
							<div id="tb" style="padding: 2px 5px;">
								产品id：<input class="easyui-textbox" style="width: 80px"
									id="proId"
									data-options="prompt:'产品id',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#proId').textbox('setValue', '');
											}
										}]">
								产品名称： <input class="easyui-textbox" style="width: 80px"
									id="proName"
									data-options="prompt:'产品名称',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#proName').textbox('setValue', '');
											}
										}]">
								<a href="javascript:searchName()" class="easyui-linkbutton"
									iconCls="icon-search">查询</a>
							</div> <select class="easyui-combogrid" id="pId" name="pId"
							style="width: 120px"
							data-options="
							            panelWidth: 400,
							            panelHeight: 400,
							            prompt:'请选择产品',
							            toolbar:'#tb',
							            idField: 'id',
							            editable:false,
							            pagination:true,
							            onShowPanel:function(){
										    $(this).combogrid('grid').datagrid('load', '/rest/product/product/list?pType=2')
									    },
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#pId').combogrid('setValue', '');
											}
										}]
							        ">
						</select>
						</td>
						<td>借款编号：</td>
						<td><input class="easyui-textbox" name="loanNo" id="loanNo"
							style="width: 120px"
							data-options="prompt:'请输入借款编号',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#loanNo').textbox('setValue', '');
											}
										}]" /></td>
						<td>标的状态：</td>
						<td><select class="easyui-combobox" name="investObjState"
							id="investObjState" style="width: 120px;"
							data-options="value:'',prompt:'请选择标的状态',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#investObjState').combobox('setValue', '');
											}
										}]">
								<option value="-1">初始（可发标）</option>
								<option value="0">正常（发标成功）</option>
								<option value="1">撤销（暂不可用）</option>
								<option value="5">发标失败</option>
						</select></td>
						<td>产品状态：</td>
                        <td><select class="easyui-combobox" name="pStatus"
                            id="pStatus" style="width: 120px;"
                            data-options="value:'',prompt:'请选产品的状态',panelHeight:'auto',editable:false,icons: [{
                                            iconCls:'icon-remove',
                                            handler: function(e){
                                                $('#pStatus').combobox('setValue', '');
                                            }
                                        }]">
                                <option value="0">产品初始化</option>
                                <option value="1">项目筹集期</option>
                                <option value="2">项目运行中/已放款</option>
                                <option value="3">项目汇款中/项目到期</option>
                                <option value="4">项目已结束</option>
                                <option value="5">项目已作废</option>
                                <option value="6">筹集金额未达标</option>
                                <option value="7">筹集金额已达标/等待申请放款</option>
                                <option value="8">流标申请中</option>
                                <option value="9">已流标</option>
                                <option value="10">放款申请中</option>
                                <option value="11">已确认自动还款</option>
                                <option value="12">还款申请已提交</option>
                                <option value="13">还款成功</option>
                                <option value="14">还款收益明细已提交</option>
                                <option value="15">还款收益明细提交成功</option>
                        </select></td>
						<td>还款日期：<input class="easyui-datebox" style="width: 120px;" id="repaymentDate" ></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="rownumbers:false,singleSelect:true,toolbar:toolbar,fitColumns:false,pagination:true,pageSize:20,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/bank/hxBidding/list',method:'post'">
				<thead>
					<tr>
						<!-- <th data-options="field:'id',width:100,hidden:false">id</th>
						<th data-options="field:'pId',width:60">产品id</th>
						<th data-options="field:'appId',width:60">应用标识</th> -->
						<!-- <th data-options="field:'investId',width:100">标的编号</th> -->
						<th data-options="field:'investObjName',width:200">标的名称</th>
						<th data-options="field:'loanNo',width:120">借款编号</th>
						<th data-options="field:'remark',width:260, formatter:formatCellTooltip">发标流水号</th>
						<th data-options="field:'minInvestAmt',width:100">最低投标金额</th>
						<th data-options="field:'maxInvestAmt',width:100">最高投标金额</th>
						<th data-options="field:'investObjAmt',width:100">总标的金额</th>
						<th data-options="field:'investBeginDate',width:90">招标开始日期</th>
						<th data-options="field:'investEndDate',width:90">招标到期日期</th>
						<th data-options="field:'repayDate',width:70">还款日期</th>
						<th data-options="field:'yearRate',width:50">年利率</th>
						<th data-options="field:'investRange',width:55">期限(天)</th>
						<th data-options="field:'investObjState',width:100,
			            					formatter:function(value){
										        if(value==-1)
										                return '初始（可发标）';
										        else if(value==0)
										                return '正常（发标成功）';
										        else if(value ==1 )
										        		return '撤销（暂不可用）';
										        else if(value == 3)
										                return '标的已结清';
										        else if(value ==5 )
										        		return '发标失败';
										        }">标的状态</th>
						<th data-options="field:'pStatus',width:150">产品状态</th>
						<th data-options="field:'repayStype',width:120">还款方式</th>
						<th data-options="field:'bwTotalNum',width:100">借款人总数</th>
						<th data-options="field:'zrFlag',width:100">是否为债权转让标的</th>
						<th data-options="field:'rateStype',width:100">计息方式</th>
						<th data-options="field:'refLoanNo',width:100">债权转让原标的</th>
						<th data-options="field:'oldReqseq',width:100">原投标第三方交易流水号</th>
						<th data-options="field:'valueDt',width:100,hidden:true">计息日</th>
						<th data-options="field:'investObjInfo',width:200">标的简介</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 修改的弹出层 -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 800px; height: 400px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改标的',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">标的ID：</label></th>
					<td><input class="easyui-textbox" name="id"
						style="width: 200px" data-options="editable:false"></input></td>
					<th><label for="name">标的名称：</label></th>
					<td><input class="easyui-textbox" name="investObjName"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">计息方式：</label></th>
					<td><input class="easyui-textbox" name="rateStype"
						style="width: 200px" data-options="required:false"></input></td>
					<th><label for="name">还款日期(YYYYMMDD)：</label></th>
					<td><input class="easyui-textbox" name="repayDate"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">标的简介：</label></th>
					<td colspan="3"><input class="easyui-textbox"
						name="investObjInfo" style="width: 520px; height: 100px;"
						data-options="multiline:true,required:false"></input></td>
				</tr>
				<tr>
					<th><label for="name">备注：</label></th>
					<td colspan="3"><input class="easyui-textbox" name="remark"
						style="width: 520px; height: 100px;"
						data-options="multiline:true,required:false"></input></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 查看借款人 start-->
	<div id="DivSetting" class="easyui-dialog"
		style="width: 860px; height: 300px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'查看借款人',iconCls: 'icon-organ',buttons: '#dlg-buttons'">
		<table class="easyui-datagrid" fit='true' id="settingGrid"
			data-options="rownumbers:true,singleSelect:true,toolbar:toolbars,fitColumns:false,pagination:true,loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,method:'post'">
			<thead>
				<tr>
					<th data-options="field:'id',width:150,hidden:true">借款信息ID</th>
					<th data-options="field:'bwId',width:150,hidden:true">借款人ID</th>
					<th data-options="field:'bwAcname',width:150">借款人姓名</th>
					<th data-options="field:'mobile',width:150">借款人手机号</th>
					<th data-options="field:'bwIdtype',width:150">借款人证件类型</th>
					<th data-options="field:'bwIdno',width:150">借款人证件号码</th>
					<th data-options="field:'bwAcno',width:150">借款人账号</th>
					<th data-options="field:'bwAcbankid',width:150">借款人账号所属行号</th>
					<th data-options="field:'bwAcbankname',width:150">借款人账号所属行名</th>
					<th data-options="field:'bwAmt',width:150">借款金额</th>
					<th data-options="field:'mortgageId',width:150">借款人抵押品编号</th>
					<th data-options="field:'mortgageInfo',width:150">借款人抵押品简单描述</th>
					<th data-options="field:'checkDate',width:150">借款人审批通过日期</th>
					<th data-options="field:'remark',width:150">备注（其它未尽事宜）</th>

					<!-- <th data-options="field:'bwAge',width:150">借款人年龄</th>
					<th data-options="field:'bwMarriage',width:150">借款人婚姻状态</th>
					<th data-options="field:'bwSex',width:150">借款人性别</th>
					<th data-options="field:'bwCreditRecord',width:150">借款人信用记录</th>
					<th data-options="field:'bwOrginPlace',width:150">借款人籍贯</th>

					<th data-options="field:'autoRepayment',width:150">是否自动还款</th>
					<th data-options="field:'bwUse',width:150">借款用途</th>
					<th data-options="field:'bwInfo',width:150">借款描述</th>
					<th data-options="field:'mortgageOwner',width:150">抵押物所有权人</th>
					<th data-options="field:'mortgageCommon',width:150">抵押物共有情况</th>
					<th data-options="field:'mortgageOwnStyle',width:150">抵押物所有权取得方式</th>
					<th data-options="field:'mortgageRegion',width:150">抵押物所属地域</th>
					<th data-options="field:'mortgageType',width:150">抵押物类型</th>
					<th data-options="field:'mortgageBuyPrice',width:150">抵押物购买价格</th>
					<th data-options="field:'mortgageEvaluatePrice',width:150">抵押物评估价格</th>
					<th data-options="field:'mortgageRate',width:150">抵押物抵押率</th> -->

				</tr>
			</thead>
		</table>
	</div>
	<!-- 查看借款人 end-->

	<!-- 添加和修改借款人的弹出层 start -->
	<div id="DivLottery" class="easyui-dialog"
		style="width: 800px; height: 500px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'维护借款人',iconCls: 'icon-organ',
			buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        saveAndUpdateLottery();
                    }
                },{
                    text:'关闭',
                     iconCls:'icon-cancel',
                    handler:function(){
                        $('#DivLottery').dialog('close');
                    }
                }]">
		<form id="ffLottery" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table class="view">
				<tr>
					<th>标的id：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="biddingId" id="biddingId" data-options="required:true"></td>
					<th>选择借款人：</th>
					<td>
					   <div id="tbf" style="padding: 2px 5px;">
                                                                    借款人名称：<input class="easyui-textbox" style="width: 80px" id="bwAcname"
                                    data-options="prompt:'借款人关键字',
                                        icons: [{
                                            iconCls:'icon-remove',
                                            handler: function(e){
                                                $('#bwAcname').textbox('setValue', '');
                                            }
                                        }]">
                                <a href="javascript:searchBwId()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                            </div>
					   <select class="easyui-combogrid" id="bwId" name="bwId"
						style="width: 200px"
						data-options="
							            panelWidth: 400,
							            panelHeight: 400,
							            prompt:'请选择借款人',
							            toolbar:'#tbf',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            url:'/rest/bank/hxBorrower/list',
							            method: 'post',
							            queryParams:{
							                 status:1
							            },
							            pagination:true,
							            textField: 'bwAcname',
							            required:true,
							            onShowPanel:function(){
						            		$(this).combogrid('grid').datagrid('reload');
						            	},
							            columns: [[
							                {field:'bwAcname',title:'name',width:100},
							                {field:'id',title:'id',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#bwId').combogrid('setValue', '');
											}
										}]
							        ">
					</select></td>
				</tr>
				<tr>
					<th>借款金额：</th>
					<td><input class="easyui-numberbox" style="width: 200px"
						name="bwAmt"
						data-options="min:0, required:true, editable:false, precision:2"></td>
					<th>借款人审批通过日期(YYYYMMDD)：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="checkDate" data-options="required:false"></td>
				</tr>
				<tr>
					<th>借款人抵押品编号：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="mortgageId" data-options="required:false"></td>
					<!-- <th>是否自动还款(Y或N)：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="autoRepayment" data-options="required:true"></td> -->
				</tr>
				<!-- <tr>
					<th>借款用途：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="bwUse" data-options="required:false"></td>
				</tr>
				<tr>
					<th>抵押物所有权人：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="mortgageOwner" data-options="required:false"></td>
					<th>抵押物共有情况：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="mortgageCommon" data-options="required:false"></td>
				</tr>
				<tr>
					<th>抵押物所有权取得方式：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="mortgageOwnStyle" data-options="required:false"></td>
					<th>抵押物所属地域：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="mortgageRegion" data-options="required:false"></td>
				</tr>
				<tr>
					<th>抵押物类型：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="mortgageType" data-options="required:false"></td>
					<th>抵押物购买价格：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="mortgageBuyPrice" data-options="required:false"></td>
				</tr>
				<tr>
					<th>抵押物评估价格：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="mortgageEvaluatePrice" data-options="required:false"></td>
					<th>抵押物抵押率：</th>
					<td><input class="easyui-textbox" style="width: 200px"
						name="mortgageRate" data-options="required:false"></td>
				</tr>
				<tr>
					<th>借款描述：</th>
					<td colspan="3"><input class="easyui-textbox"
						style="width: 520px; height: 200px;" name="bwInfo"
						data-options="required:false,multiline:true"></td>
				</tr> -->
				<tr>
					<th>借款人抵押品简单描述：</th>
					<td colspan="3"><input class="easyui-textbox"
						style="width: 520px; height: 200px;" name="mortgageInfo"
						data-options="required:false,multiline:true"></td>
				</tr>
				<tr>
					<th>备注：</th>
					<td colspan="3"><input class="easyui-textbox" name="remark"
						style="width: 520px; height: 200px;"
						data-options="multiline:true,required:false"></input></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 添加和修改借款人的弹出层 end -->


	<!-- 还款信息和借款人一一对应start -->
	<!-- 还款信息列表 -->
	<div id="paymentListDialog" class="easyui-dialog"
		style="width: 800px; height: 420px; padding: 10px 20px" closed="true"
		resizable="true" modal="true" data-options="title:'还款信息'">
		<div id="tipMsg"></div>
		<table class="easyui-datagrid" title="查询结果" fit='true'
			id="paymentGrid"
			data-options="rownumbers:false,singleSelect:true,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在查询...',idField:'id',collapsible:true,method:'post'">
			<thead>
				<tr>
					<th data-options="field:'id',width:100,hidden:true">id</th>
					<th data-options="field:'channelFlow',width:100">还款流水号</th>
					<th data-options="field:'oldChannelFlow',width:100">原垫付请求流水号</th>
					<th
						data-options="field:'paymentType',width:120,formatter: formatPaymentType">还款类别</th>
					<th
						data-options="field:'status',width:60,formatter: formatPaymentStatus">还款状态</th>
					<th
						data-options="field:'paymentDate',width:100,formatter:formatter.DateTimeFormatter">操作时间</th>
					<th data-options="field:'amount',width:100">还款金额</th>
					<th data-options="field:'feeAmt',width:80">平台手续费</th>
					<th
						data-options="field:'operat',width:150,formatter:formatOperation">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 还款操作 -->
	<div id="paymentDialog" class="easyui-dialog"
		style="width: 800px; height: 400px; padding: 10px 20px" closed="true"
		resizable="true" modal="true" data-options="title:'还款'">
		<form id="paymentForm" method="post"
			action="/rest/bank/singlePayment/">
			<table class="view" style="width: 100%;">
				<tr>
					<th>借款编号：</th>
					<td><input class="easyui-textbox" id="loanNo_payment"
						name="loanNo" style="width: 200px" /></td>
				</tr>
				<tr>
					<th>还款账号户名：</th>
					<td><input class="easyui-textbox" id="bwAcName"
						name="bwAcName" style="width: 200px" /></td>
				</tr>
				<tr>
					<th>还款户名：</th>
					<td><input class="easyui-textbox" id="bwAcNo" name="bwAcNo"
						style="width: 200px" /><span style="color: red">请核对可用余额是否充足</span></td>
				</tr>
				<tr>
					<th>还款类别：</th>
					<td><select id="dfFlag" name="dfFlag"
						style="height: 27px; width: 200px">
							<option value="1">个人普通单笔还款</option>
							<!-- <option value="2">垫付后，借款人还款</option> -->
							<option value="3">公司垫付还款</option>
					</select></td>
				</tr>
				<tr id="oldReqSeqNo_tr" style="display: none;">
					<th>原垫付请求流水号：</th>
					<td><input class="easyui-textbox" name="oldReqSeqNo"
						style="width: 200px" /></td>
				</tr>
				<tr>
					<th>还款金额：</th>
					<td><span id="amount"></span></td>
				</tr>
				<tr>
					<th>平台手续费：</th>
					<td><input class="easyui-numberbox" name="feeAmt"
						style="width: 200px;" data-options="min:0,precision:2" /></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btn_payment" iconcls="icon-ok" onclick="paymentSubmit()">确认还款</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#paymentDialog').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 还款结果查询对话框  -->
	<div id="queryPaymentResultDialog" class="easyui-dialog"
		style="width: 800px; height: 400px; padding: 10px 20px" closed="true"
		resizable="true" modal="true" data-options="title:'还款结果查询'">
		<table class="view">
			<tr>
				<th>银行交易流水号：</th>
				<td name="RESJNLNO"></td>
				<th>还款交易流水号：</th>
				<td name="OLDREQSEQNO"></td>
			</tr>
			<tr>
				<th>交易日期：</th>
				<td name="TRANSDT"></td>
				<th>交易时间：</th>
				<td name="TRANSTM"></td>
			</tr>
			<tr>
				<th>交易状态：</th>
				<td name="RETURN_STATUS"></td>
				<th>失败原因：</th>
				<td name="ERRORMSG"></td>
			</tr>
			<tr>
				<th>借款编号：</th>
				<td name="LOANNO"></td>
				<th>还款账号户名：</th>
				<td name="BWACNAME"></td>
			</tr>
			<tr>
				<th>还款账号：</th>
				<td name="BWACNO"></td>
				<th>还款金额：</th>
				<td name="AMOUNT"></td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: right; padding-top: 10px"><a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconcls="icon-cancel"
					onclick="javascript:$('#queryPaymentResultDialog').dialog('close')">关闭</a>
				</td>
			</tr>
		</table>
	</div>


	<!-- 还款信息end -->


	<!-- 申请放款对话框 Start -->
	<div id="bankLenddingDialog" class="easyui-dialog"
		style="width: 750px; height: 270px; padding: 10px 20px" closed="true"
		resizable="true" modal="true" data-options="title:'申请放款'">
		<form id="bankLenddingForm" method="post">
			<input type="hidden" id="lendBiddingId" name="biddingId">
			<table class="view">
				<tr>
					<th>风险保证金：</th>
					<td><input class="easyui-numberbox" name="guarantAmt"
						style="width: 200px"
						data-options="min:0,max:999999999999999,precision:2"></td>
				</tr>
				<tr>
					<th>备注：</th>
					<td><input class="easyui-textbox easyui-validbox"
						name="remark" style="width: 520px; height: 100px;"
						data-options="multiline:true,validType:'length[0,60]'"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="bankLenddingSubmit()">确定</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#bankLenddingDialog').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 申请放款对话框 End -->
	<!-- 查看投标人 start -->
	<div id="DivBidder" class="easyui-dialog"
		style="width: 860px; height: 300px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'查看投标人',iconCls: 'icon-organ',buttons: '#dlg-buttons'">
		<table class="easyui-datagrid" fit='true' id="bidderGrid"
			data-options="rownumbers:true,singleSelect:true,toolbar:toolbarBidder,fitColumns:false,pagination:true,loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,method:'post'">
			<thead>
				<tr>
					<th data-options="field:'id',width:100">交易ID</th>
					<th data-options="field:'uId',width:100">用户ID</th>
					<th data-options="field:'acNo',width:100">投标人账号</th>
					<th data-options="field:'acName',width:100">投标人姓名</th>
					<th data-options="field:'amount',width:100">投标金额</th>
					<th data-options="field:'bizCode',width:100">投标流水号</th>
					<th data-options="field:'loanNo',width:100">借款编号</th>
					<th data-options="field:'status',width:100"
						formatter="formatTradingStatus">状态</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 查看投标人 end -->
	<form id="sigPaymentForm" name="subBankForm" style="display: none"
		target="_blank" method="post" action=''>
		<input id="sigPaymentRequestData" name="RequestData" value='' /> 
		<input id="sigPaymentTransCode" name="transCode" value='' />
	</form>
	<form id="errorSubForm" name="errorSubForm" style="display: none"
		target="_blank" method="post" action='/rest/bank/singlePayment/errorPage'>
		<input id="errorPageHtml" name="errorPageHtml" value='' />
	</form>

	<!-- 自动还款 -->
	<form id="autoRepaymentForm" style="display: none;" target="_blank"
		method="post" action="">
		<input id="requestData" name="RequestData" value="" /> 
		<input id="transCode" name="transCode" value="" />
	</form>

	<!-- 撤销自动还款授权 -->
	<div id="repealApplyAutoRepaymentDialog" class="easyui-dialog"
		title="自动还款授权撤销" style="width: 260px; height: 180px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,closable:true">
		<form id="repealApplyAutoRepaymentForm" >
			<input type="hidden" id="repealAccountId"> <input
				type="hidden" id="otpSeqNo" name="otpSeqNo">
			<table style="width:100%;">
				<tr>
					<td width="60px;">借款编号</td>
					<td>
						<input id="repealLoanNo" class="easyui-textbox" readonly="readonly">
					</td>
				</tr>

				<tr>
					<td>还款人姓名</td>
					<td>
						<input id="repealLoanner" class="easyui-textbox" readonly="readonly">
					</td>
				</tr>

				<tr>
					<td>短信验证码</td>
					<td colspan="2">
						<input id="otpNo" name="otpNo" class="easyui-textbox" style="width: 60px;" >
						<a id="msgCodeBtn" class="easyui-linkbutton" onclick="getMsgCode();">获取短信验证码</a>
					</td>
				</tr>

				<tr>
					<td>备注</td>
					<td>
						<input id="repealRemark" class="easyui-textbox easyui-validatebox" data-options="validType:length[0,60]">
					</td>
				</tr>

				<tr>
					<td colspan="2" style="padding-left: 113px;">
						<a class="easyui-linkbutton" onclick="confirmRepeal();">确认撤销</a> 
						<a class="easyui-linkbutton" onclick="javascript:$('#repealApplyAutoRepaymentDialog').dialog('close');">取&nbsp;&nbsp;消</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

<script type="text/javascript">
	// 还款表单onchange事件
	$(function() {
		$("#dfFlag").change(function() {
			var sel = $("#dfFlag option:selected").val();
			if (sel == 2) {
				$("#oldReqSeqNo_tr").show();
			} else {
				$("#oldReqSeqNo_tr").hide();
				$("#oldReqSeqNo_tr input").textbox("setValue", "");
			}

		})
	});
	
	 //格式化单元格提示信息  
    function formatCellTooltip(value){  
        return "<span title='" + value + "'>" + value + "</span>";  
    }  
</script>
</body>
</html>
