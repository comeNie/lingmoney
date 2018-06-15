<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>自动还款标的到期确认</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/formatter.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/loading.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/bank/confirmAutoRepayment.js"></script>

</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div data-options="region:'center'" style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="tableDataGrid"
				data-options="rownumbers:false,singleSelect:false,toolbar:toolbar,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/product/listAutoRepaymentProduct',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'code',width:100">code码</th>
						<th data-options="field:'name',width:200">产品名称</th>
						<th data-options="field:'reWay',width:100">还款方式</th>
						<th data-options="field:'rule',width:100,
			            					formatter:function(value){
										        if(value==0)
										                return '金额限制';
										        else if(value==1)
										                return '时间限制';
										        else if(value==2)
										                return '金额时间限制';
								                else if(value==3)
										                return '无限制';
										        }">规则</th>
						<th data-options="field:'fTime',width:100">固定期限</th>
						<th data-options="field:'unitTime',width:100,
			            					formatter:function(value){
										        if(value==0)
										                return '天';
										        else if(value==1)
										                return '周';
										        else if(value ==2 )
										        		return '月';
										        else if(value ==3 )
										        		return '年';
								        		else if(value ==4 )
								        			return '无';		
										        }">单位时间类型</th>
						<th data-options="field:'stDt',width:100" formatter="formatTimer">起始时间</th>
						<th data-options="field:'edDt',width:100" formatter="formatTimer">结束时间</th>
						<th data-options="field:'priorMoney',width:100">筹备金额</th>
						<th data-options="field:'reachMoney',width:100">已筹金额</th>
						<th data-options="field:'status',width:100,
			            					formatter:function(value){
			            						if(value==3)
										                return '<font color=\'red\'>未确认自动还款</font>';
										        else if(value==11)
										                return '<font color=\'green\'>已确认自动还款</font>';
										        }">产品状态</th>
						<th data-options="field:'acNo',width:150">还款账号</th>
						<th data-options="field:'platformUserNo',width:100">借款账户</th>
						<th data-options="field:'costValue',width:100">平台佣金</th>
						<th data-options="field:'pModel',width:100,formatter:function(value){
										        if(value==0)
										                return '产品模式';
										        else if(value==1)
										                return '钱包模式';
										        else if(value==2)
										                return '新手标模式';
										        }">模式</th>
						<th data-options="field:'pType',width:100,formatter:function(value){
										        if(value==0)
										                return '领钱儿';
										        else if(value==1)
										                return '中粮';
										        else if(value==2)
										                return '华兴银行';
										        }">产品所属</th>
						<th data-options="field:'cityCode',width:100" formatter="formatCityCode">所属地区</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>
