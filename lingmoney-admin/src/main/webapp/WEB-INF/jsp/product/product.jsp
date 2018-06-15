<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品表</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/json2.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript"
    src="<%=request.getContextPath()%>/resource/js/product/product.js"></script>

<style type="text/css">
fieldset {
	margin-bottom: 10px;
	color: #333;
	border: #06c dashed 1px;
}

legend {
	color: #06c;
	font-weight: 800;
	background: #fff;
}
</style>
</head>
<body>
<!-- 	<div id="tb" style="padding: 2px 5px; display: none;"> -->
<!-- 		客户名称：<input class="easyui-textbox" style="width: 110px" -->
<!-- 			id="searchName"> <a href="javascript:searchUsers()" -->
<!-- 			class="easyui-linkbutton" iconCls="icon-search">查询</a> -->
<!-- 	</div> -->
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 100px">
			<form id="searchForm">
				<table>
					<tr>
						<td>产品名称：</td>
						<td><input class="easyui-textbox" name="name"
							id="productName" style="width: 200px"
							data-options="prompt:'请输入产品名称',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#productName').textbox('setValue', '');
											}
										}]" /></td>
						<td>审批状态：</td>
						<td><select class="easyui-combobox" name="approval"
							id="approvalStatus" style="width: 200px;"
							data-options="value:'',prompt:'请选择审批状态',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#approvalStatus').combobox('setValue', '');
											}
										}]">
								<option value="0">初始状态</option>
								<option value="1">提交状态</option>
								<option value="2">审核通过状态</option>
						</select></td>
						<td>产品所属：</td>
						<td><select class="easyui-combobox" name="pType" id="pType"
							style="width: 200px;"
							data-options="value:'',prompt:'请选择产品所属',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#pType').combobox('setValue', '');
											}
										}]">
								<option value="0">领钱儿</option>
								<option value="1">中粮</option>
								<option value="2">华兴银行</option>
						</select></td>
					</tr>
					<tr>
						<td>所属地区 ：</td>
						<td><select class="easyui-combogrid" id="sCityCode"
							name="cityCode" style="width: 200px"
							data-options="
							            panelWidth: 200,
							            panelHeight:'auto',
							            idField: 'code',
							            editable:false,
							            prompt:'请选择所属地区',
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/areaDomain/queryCodeName',
							            onShowPanel:function(){
							            	$(this).combogrid('grid').datagrid('reload');
							            },
							            method: 'get',
							            columns: [[
							                {field:'code',title:'城市编码',width:30},
							                {field:'name',title:'所属地区',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#sCityCode').combogrid('setValue', '');
											}
										}]
							        ">
						</select></td>
						<td>产品状态：</td>
						<td><select class="easyui-combobox" name="status"
							id="proStatus" style="width: 200px;"
							data-options="value:'',prompt:'请选择产品状态',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#proStatus').combobox('setValue', '');
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
						<td>产品批次：</td>
						<td><input class="easyui-textbox" name="batch"
							id="productBatch" style="width: 200px" data-options="prompt:'请输入产品批次',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#productBatch').textbox('setValue', '');
											}
										}]"/></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">


			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/product/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'code',width:100">code码</th>
						<th data-options="field:'name',width:200">产品名称</th>
						<th data-options="field:'reWay',width:100">还款方式</th>
						<th
							data-options="field:'rule',width:100,
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
						<th data-options="field:'lTime',width:100">最低期限</th>
						<th data-options="field:'hTime',width:100">最高期限</th>
						<th data-options="field:'fTime',width:100">固定期限</th>
						<th
							data-options="field:'unitTime',width:100,
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
						<th
							data-options="field:'approval',width:100,
			            					formatter:function(value){
										        if(value==0)
										                return '初始状态';
										        else if(value==1)
										                return '提交状态';
										        else if(value==2)
										                return '审核通过状态';
										        }">审批状态</th>
						<th
							data-options="field:'status',width:100,
			            					formatter:function(value){
			            						if(value==0)
										                return '产品初始化';
										        else if(value==1)
										                return '项目筹集期';
										        else if(value==2)
										                return '项目运行中/已放款';
										        else if(value==3)
										                return '项目汇款中/项目到期';
								                else if(value==4)
										                return '项目已结束';
								                else if(value==5)
										                return '项目已作废';
								                else if(value==6)
										                return '筹集金额未达标';
								                else if(value==7)
										                return '筹集金额已达标/等待申请放款';
								                else if(value==8)
										                return '流标申请中';
								                else if(value==9)
										                return '已流标';
								                else if(value==10)
										                return '放款申请中';
								                else if(value==11)
										                return '已确认自动还款';
								                else if(value==12)
										                return '还款申请已提交';
								                else if(value==13)
										                return '还款成功';
								                else if(value==14)
										                return '还款收益明细已提交';
								                else if(value==15)
										                return '还款收益明细提交成功';
										        }">产品状态</th>
						<th data-options="field:'pcName',width:100">产品分类名称</th>
						<th data-options="field:'tags',width:100">标签</th>
						<th
							data-options="field:'activity',width:100,
			            					formatter:function(value){
										        if(value==0)
										                return '否';
										        else if(value==1)
										                return '是';
										        }">活动产品</th>
						<th data-options="field:'costValue',width:100">平台佣金</th>
						<th data-options="field:'waitRate',width:100">筹备期利率</th>
						<th
							data-options="field:'pModel',width:100,formatter:function(value){
										        if(value==0)
										                return '产品模式';
										        else if(value==1)
										                return '钱包模式';
										        else if(value==2)
										                return '新手标模式';
										        }">模式</th>
						<th
							data-options="field:'pType',width:100,formatter:function(value){
										        if(value==0)
										                return '领钱儿';
										        else if(value==1)
										                return '中粮';
										        else if(value==2)
										                return '华兴银行';
										        }">产品所属</th>
						<th data-options="field:'cityCode',width:100"
							formatter="formatCityCode">所属地区</th>
						<th data-options="field:'batch',width:100">产品批次</th>
						<th data-options="field:'sort',width:60">产品排序</th>
						<th
							data-options="field:'insuranceTrust',width:100,formatter:function(value){
										        if(value==0)
										                return '无';
										        else if(value==1)
										                return '有';
										        }">有无保险增信</th>
						<th
							data-options="field:'isRedPacket',width:100,formatter:function(value){
										        if(value==0)
										                return '不可用';
										        else if(value==1)
										                return '可用';
										        }">是否可用优惠券</th>
						<th
							data-options="field:'isDebt',width:100,formatter:function(value){
										        if(value==0)
										                return '不可转让';
										        else if(value==1)
										                return '可转让';
										        }">是否可债权转让</th>
					</tr>

				</thead>
			</table>
		</div>
	</div>
	
	<!-- 排序弹出框S -->
	<div id="sortProductDialog" data-options="title:'排序'" class="easyui-dialog" style="width:235px;height:100px;" modal="true" closed="true">
	   <table>
	       <tr>
	           <td>排序：</td>
	           <td><input class="easyui-numberbox" id="productSortVal" data-options="min:1, max:99"></td>
	       </tr>
	       <tr>
	           <td colspan="2" style="padding-left:120px;">
	               <a class="easyui-linkbutton" href="javascript:void(0)" onclick="confirmSortProduct()">确定</a>
	               <a class="easyui-linkbutton" href="javasrcipt:void(0)" onclick="closeSortProductDialog()">取消</a>
	           </td>
	       </tr>
	   </table>
	</div>
	<!-- 排序弹出框E -->

	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 840px; height: 540px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'添加产品',iconCls: 'icon-add',
			buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        saveAndUpdate();
                    }
                },{
                    text:'重置',
                    iconCls:'icon-clear',
                    handler:function(){
                         KindEditor.remove('#acontent');
                      createKindEditor('#acontent').html('');
                        $('#ffAdd').form('clear');;
                    }
                },{
                    text:'关闭',
                     iconCls:'icon-cancel',
                    handler:function(){
                        $('#DivAdd').dialog('close');
                    }
                }]">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" id="aid"> <input type="hidden"
				name="pcName" id="apcName"> <input type="hidden" name="isCopy" id="isCopy">
			<fieldset id="aProductBase">
				<legend>产品的基本信息</legend>
				<table id="tblAdd" class="view">
					<tr>
						<th><label for="name">产品名称：</label></th>
						<td><input class="easyui-textbox" name="name" id="aname"
							style="width: 200px"
							data-options="prompt:'请输入产品名称',required:true"></input></td>
						<th><label for="reWay">还款方式：</label></th>
						<td><input class="easyui-textbox" name="reWay" id="areWay"
							style="width: 200px"
							data-options="prompt:'请输入还款方式',required:true"></input></td>
					</tr>
					<tr>
						<th>tag标签</th>
						<td><input class="easyui-textbox" name="tags" id="atags"
							style="width: 200px"
							data-options="prompt:'标签以,分隔的方式填写',required:true"></input></td>
						<th><label>产品批次：</label></th>
						<td><input class="easyui-textbox" name="batch" id="batch"
							style="width: 200px"
							data-options="required:false,prompt:'请输入产品批次'"></input></td>
					</tr>
					<tr>
						<th><label for="minMoney">最低金额：</label></th>
						<td><input class="easyui-numberbox" name="minMoney"
							id="aminMoney" style="width: 200px"
							data-options="prompt:'请输入最低金额',required:true,min:0"></input></td>
						<th><label for="increaMoney">递增金额：</label></th>
						<td><input class="easyui-numberbox" name="increaMoney"
							id="aincreaMoney" style="width: 200px"
							data-options="prompt:'请输入递增金额',required:true,min:0"></input></td>
					</tr>
					<tr>
						<th><label for="status">产品分类：</label></th>
						<td><select class="easyui-combogrid" id="apcId" name="pcId"
							style="width: 200px"
							data-options="
							            panelWidth: 200,
							            panelHeight:'auto',
							            prompt:'请选择产品分类',
							            idField: 'id',
							            required:true,
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/product/category/list',
							            method: 'get',
							            remote:'remote',
							            onShowPanel:function(){
							            	$(this).combogrid('grid').datagrid('reload');
							            },
							            onChange:function(newValue,oldValue){
							            	var gridObj = $(this).combogrid('grid').datagrid('getSelections');
							            	showAndHideProductCategory(gridObj);
							            },
							            columns: [[
							                {field:'id',title:'产品分类id',width:30},
							                {field:'name',title:'产品分类名称',width:50},
							                {field:'type',title:'类型',width:50,hidden:true},
							                {field:'fixType',title:'固定子类型',width:50,hidden:true}
							            ]],
							            fitColumns: true">
						</select></td>
						<th><label for="rule">规则：</label></th>
						<td><select class="easyui-combobox" name="rule" id="arule"
							style="width: 200px;"
							data-options="value:'',prompt:'请选择规则',panelHeight:'auto',required:true,editable:false,onChange:function(newValue,oldValue){
							            	showAndHideRule(newValue,oldValue);
							            }">
								<option value="2">金额时间限制</option>
								<option value="3">无限制</option>
						</select></td>
					</tr>
					<tr>
						<th>定期理财方式：</th>
						<td><select class="easyui-combobox" name="fixInvest"
							id="afixInvest" style="width: 200px"
							data-options="prompt:'请选择定期理财方式',panelHeight:'auto',required:true,editable:false,onChange:function(newValue,oldValue){
						            	if(newValue==0){
						            		$('#aexceptionRate').numberbox('disable',true); 
						            	}else{
						            		$('#aexceptionRate').numberbox('enable',true); 
						            		$('#aexceptionRate').numberbox('setValue',0); 
						            	}
						            }">
								<option value="0">非定投</option>
								<!-- <option value="1">周周投</option>
	                        	 	<option value="2">半月投</option> -->
								<option value="3">月月投</option>
						</select></td>
						<th>异常汇率：</th>
						<td><input class="easyui-numberbox" name="exceptionRate"
							id="aexceptionRate" style="width: 200px"
							data-options="disabled:true,prompt:'当用户未按时理财的汇率,只针对固定类产品',min:0,max:1,precision:4"></input>
						</td>
					</tr>
					<tr>
						<th>是否为活动产品：</th>
						<td><select class="easyui-combobox" name="activity"
							id="aactivity" style="width: 200px;"
							data-options="value:'',prompt:'请选择是否为活动产品',panelHeight:'auto',required:true,editable:false">
								<option value="0">否</option>
								<option value="1">是</option>
						</select></td>
						
						<th><label for="name">排序:</label></th>
                        <td><input class="easyui-numberbox" value="99" name="sort" data-options="min:1,max:99,prompt:'1-99顺序展示，默认99'"></td>
					</tr>
					<tr>
						<th>平台佣金:</th>
						<td><input class="easyui-numberbox" name="costValue"
							id="acostValue" style="width: 200px"
							data-options="max:0.1,min:0.001,precision:3,prompt:'最高10%,最低0.1%',required:true"></input>
						</td>
						<th>模式:</th>
						<td><select class="easyui-combobox" name="pModel"
							id="apModel" style="width: 200px;"
							data-options="value:'',prompt:'请选择模式',panelHeight:'auto',required:true,editable:false">
								<option value="0">产品模式</option>
								<option value="1">钱包模式</option>
								<option value="2">新手标模式</option>
						</select></td>
					</tr>
					<tr>
						<th><label for="name">WEB端我的理财产品图片:</label></th>
						<td><select class="easyui-combogrid" name="pictrueUrl"
							id="aimg" style="width: 200px"
							data-options="
						            panelWidth: 200,
						            panelHeight:'auto',
						            prompt:'请选择WEB端我的理财产品图片',
						            idField: 'url',
						            editable:false,
						            required:true,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'url',
						            url: '/rest/product/image/listUrl?type=0',
						            onShowPanel:function(){
						            	$(this).combogrid('grid').datagrid('reload');
						            },
						            method: 'get',
						            columns: [[
						                {field:'name',title:'产品名称',width:30},
						                {field:'url',title:'产品图片',width:40,formatter:formatImage}
						            ]],
						            fitColumns: true
						        ">
						</select>
						<th>产品所属：</th>
                        <td><select class="easyui-combogrid" id="apType" name="pType"
                            style="width: 200px"
                            data-options="
                                        panelWidth: 200,
                                        idField: 'id',
                                        editable:false,
                                        required:true,
                                        loadMsg:'正在加载,请稍后...',
                                        textField: 'name',
                                        url: '/resource/json/product_type.json',
                                        method: 'get',
                                        columns: [[
                                            {field:'id',title:'id',width:30},
                                            {field:'name',title:'名称',width:50}
                                        ]],
                                        fitColumns: true
                                    ">
                        </select></td>
					</tr>
					<tr>
						<th><label for="cityCode">所属地区 ：</label></th>
						<td><select class="easyui-combogrid" name="cityCode"
							id="cityCode" style="width: 200px"
							data-options="
							            panelWidth: 200,
							            panelHeight:'auto',
							            prompt:'请选择所属地区 ',
							            idField: 'code',
							            required:true,
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/areaDomain/queryCodeName',
							            onShowPanel:function(){
							            	$(this).combogrid('grid').datagrid('reload');
							            },
							            method: 'get',
							            columns: [[
							                {field:'code',title:'城市编码',width:30},
							                {field:'name',title:'所属地区',width:50}
							            ]],
							            fitColumns: true
							        ">
						</select></td>
						<th><label for="cityCode">用户购买限额 ：</label></th>
						<td><input class="easyui-numberbox" name="buyLimit"
							id="buyLimit" style="width: 200px"
							data-options="required:true,prompt:'无限额请填0',min:0,precision:2"></input>
						</td>
					</tr>
					<tr>
						<th><label for="name">Android产品背景图:</label></th>
						<td><select class="easyui-combogrid" name="backgroundUrl"
							id="backgroundUrl" style="width: 200px"
							data-options="
						            panelWidth: 200,
						            panelHeight:'auto',
						            prompt:'请选择产品背景图',
						            idField: 'url',
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'url',
						            url: '/rest/product/image/listUrl?type=1',
						            onShowPanel:function(){
						            	$(this).combogrid('grid').datagrid('reload');
						            },
						            method: 'get',
						            columns: [[
						                {field:'name',title:'产品名称',width:30},
						                {field:'url',title:'产品图片',width:40,formatter:formatImage}
						            ]],
						            fitColumns: true
						        ">
						</select></td>
						<th>APP端产品背景图是否磨砂：</th>
						<td><select class="easyui-combobox" name="backgroundFrosted"
							id="backgroundFrosted" style="width: 200px;"
							data-options="value:'',prompt:'请选择APP端产品背景图是否磨砂',panelHeight:'auto',required:true,editable:false">
								<option value="0">是</option>
								<option value="1">否</option>
						</select></td>
					</tr>
					<tr>
						<th><label for="name">Nav产品背景图:</label></th>
						<td><select class="easyui-combogrid" name="backgroundUrlNav"
							id="backgroundUrlNav" style="width: 200px"
							data-options="
						            panelWidth: 200,
						            panelHeight:'auto',
						            prompt:'请选择产品背景图',
						            idField: 'url',
						            editable:false,
						            required:true,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'url',
						            url: '/rest/product/image/listUrl?type=2',
						            onShowPanel:function(){
						            	$(this).combogrid('grid').datagrid('reload');
						            },
						            method: 'get',
						            columns: [[
						                {field:'name',title:'产品名称',width:30},
						                {field:'url',title:'产品图片',width:40,formatter:formatImage}
						            ]],
						            fitColumns: true
						        ">
						</select></td>
						<th><label for="name">iOS产品背景图:</label></th>
						<td><select class="easyui-combogrid" name="backgroundUrlIos"
							id="backgroundUrlIos" style="width: 200px"
							data-options="
						            panelWidth: 200,
						            panelHeight:'auto',
						            prompt:'请选择产品背景图',
						            idField: 'url',
						            editable:false,
						            required:true,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'url',
						            url: '/rest/product/image/listUrl?type=3',
						            onShowPanel:function(){
						            	$(this).combogrid('grid').datagrid('reload');
						            },
						            method: 'get',
						            columns: [[
						                {field:'name',title:'产品名称',width:30},
						                {field:'url',title:'产品图片',width:40,formatter:formatImage}
						            ]],
						            fitColumns: true
						        ">
						</select></td>
					</tr>
					<tr>
					   <th>借款账户：</th>
                       <td>
	                      <select class="easyui-combogrid" id="sFincUsers"
                               name="platformUserNo" style="width: 200px"
                               data-options="
                                           panelWidth: 480,
                                           idField: 'platUserNo',
                                           pagination:true,
                                           editable:false,
                                           loadMsg:'正在加载,请稍后...',
                                           textField: 'name',
                                           url: '/rest/sell_submit/listUsers',
                                           method: 'post',
                                           onShowPanel:function(){
                                               $(this).combogrid('grid').datagrid('reload');
                                           },
                                           columns: [[
                                               {field:'platUserNo',title:'用户id',width:30},
                                               {field:'name',title:'用户名称',width:50}
                                           ]],
                                           fitColumns: true
                                       ">
                           </select>
                       </td>
                       <th>是否有保险增信：</th>
						<td><select class="easyui-combobox" name="insuranceTrust"
							id="insuranceTrust" style="width: 200px;"
							data-options="value:'',prompt:'请选择是否有保险增信',panelHeight:'auto',required:true,editable:false">
								<option value="0">否</option>
								<option value="1">是</option>
						</select></td>
					</tr>
					<tr>
						<th>是否可用优惠券：</th>
						<td><select class="easyui-combobox" name="isRedPacket"
							id="isRedPacket" style="width: 200px;"
							data-options="value:'',prompt:'请选择是否可用优惠券',panelHeight:'auto',required:true,editable:false">
								<option value="0">不可用</option>
								<option value="1">可用</option>
						</select></td>
						<th>是否可债权转让：</th>
						<td><select class="easyui-combobox" name="isDebt"
							id="isDebt" style="width: 200px;"
							data-options="value:'',prompt:'请选择是否可债权转让',panelHeight:'auto',required:true,editable:false">
								<option value="0">不可转让</option>
								<option value="1">可转让</option>
						</select></td>
					</tr>
				</table>
			</fieldset>
			<fieldset id="afixProp" style="display: none">
				<legend>固定类收益产品的属性项</legend>
				<table id="afixPropFix" style="display: none" class="view">
					<tr>
						<th><label for="fTime">固定期限：</label></th>
						<td><input class="easyui-numberbox" name="fTime" id="afTime"
							style="width: 200px"
							data-options="required:true,prompt:'无固定期限填0',min:0"></input></td>
						<th>固定收益率：</th>
						<td><input class="easyui-numberbox" name="fYield"
							id="afYield" style="width: 200px"
							data-options="required:true,prompt:'固定期限产品的收益率',min:0,max:0.15,precision:4"></input>
						</td>
						<th>加息率：</th>
						<td><input class="easyui-numberbox" name="addYield"
							id="addYield" style="width: 200px"
							data-options="required:false,prompt:'加息率',min:0,max:0.15,precision:4"></input>
						</td>
					</tr>
				</table>
				<table id="afixPropArea" style="display: none" class="view">
					<tr>
						<th><label for="lTime">最低期限：</label></th>
						<td><input class="easyui-numberbox" name="lTime" id="alTime"
							style="width: 200px"
							data-options="required:true,prompt:'产品的最低期限',min:0"></input></td>
						<th><label for="hTime">最高期限：</label></th>
						<td><input class="easyui-numberbox" name="hTime" id="ahTime"
							style="width: 200px"
							data-options="required:true,prompt:'产品的最高期限',min:0"></input></td>
					</tr>
					<tr>
						<th>最低收益率：</th>
						<td><input class="easyui-numberbox" name="lYield"
							id="alYield" style="width: 200px"
							data-options="required:true,prompt:'产品的最低收益率',min:0,max:1,precision:4"></input>
						</td>
						<th>最高收益率：</th>
						<td><input class="easyui-numberbox" name="hYield"
							id="ahYield" style="width: 200px"
							data-options="required:true,prompt:'产品的最低收益率',min:0,max:1,precision:4"></input>
						</td>
					</tr>
					<tr>
						<th>是否分息：</th>
						<td><select class="easyui-combobox" name="allocation"
							id="aallocation" style="width: 200px"
							data-options="prompt:'请选择是否分息',panelHeight:'auto',required:true,editable:false">
								<option value="0">否</option>
								<option value="1">是</option>
						</select></td>
					</tr>
				</table>
				<table class="view">
					<tr>
						<th style="width: 140px">产品单位时间：</th>
						<td><select class="easyui-combobox" name="unitTime"
							id="aunitTime" style="width: 200px"
							data-options="prompt:'请选择产品单位时间',panelHeight:'auto',required:true,editable:false">
								<option value="0">天</option>
								<!-- <option value="1">周</option> -->
								<option value="2">月</option>
								<!-- <option value="3">年</option> -->
								<option value="4">无</option>
						</select></td>
					</tr>
				</table>
			</fieldset>
			<fieldset id="afloatProp" style="display: none;">
				<legend>浮动类收益产品的属性项</legend>
				<table class="view">
					<tr>
						<th>手续费收取方式：</th>
						<td><select class="easyui-combobox" name="fees"
							style="width: 200px" id="afees"
							data-options="prompt:'请选择手续费收取方式',required:true,panelHeight:'auto',editable:false">
								<option value="0">不收取</option>
								<option value="1">前收取</option>
								<option value="2">后收取</option>
						</select></td>
						<th>手续费费率：</th>
						<td><input class="easyui-numberbox" name="feesRate"
							id="afeesRate" style="width: 200px"
							data-options="disabled:true,prompt:'手续费费率',min:0,max:1,precision:4"></input>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset id="aruleProp" style="display: none;">
				<legend>产品规则的属性项</legend>
				<table class="view" id="aruleDate" style="display: none;">
					<tr>
						<th><label for="stDt">起始时间：</label></th>
						<td><input class="easyui-datetimebox" name="stDt" id="astDt"
							style="width: 200px"
							data-options="required:true,prompt:'请选择开始时间',editable:false"></input>
						</td>
						<th><label for="edDt">结束时间：</label></th>
						<td><input class="easyui-datetimebox" name="edDt" id="aedDt"
							style="width: 200px"
							data-options="required:true,prompt:'请选择结束时间',editable:false"></input>
						</td>
					</tr>
				</table>
				<table class="view" id="aruleMoney" style="display: none;">
					<tr>
						<th style="width: 140px"><label for="priorMoney">筹备金额：</label>
						</th>
						<td><input class="easyui-numberbox" name="priorMoney"
							id="apriorMoney" style="width: 200px"
							data-options="required:true,prompt:'请填写筹备金额',required:true,min:0,max:10000000"></input>
						</td>
					</tr>
				</table>
				<table class="view">
                    <tr>
                        <th style="width: 150px"><label for="waitRate">产品筹备期利率：</label>
                        </th>
                        <td><input class="easyui-numberbox" name="waitRate"
                            id="awaitRate" style="width: 200px"
                            data-options="required:true,prompt:'请填写产品筹备期汇率',required:true,min:0,max:1,precision:4"></input>
                        </td>
                    </tr>
                </table>
			</fieldset>
		</form>
	</div>

	<!-- 修改pc项目信息 -->
	<div id="DivCdd" class="easyui-dialog"
		style="width: 900px; height: 520px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'修改项目信息',iconCls: 'icon-add',
			buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        saveDetail(0);
                    }
                },{
                    text:'重置',
                    iconCls:'icon-clear',
                    handler:function(){
                        KindEditor.remove('#projectInfo');
                        createKindEditor('#projectInfo').html('');
                        $('#ffCdd').form('clear');;
                    }
                },{
                    text:'关闭',
                     iconCls:'icon-cancel',
                    handler:function(){
                        $('#DivCdd').dialog('close');
                    }
                }]">
		<form id="ffCdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table class="view">
				<tr>
					<th><label for="name">pc项目信息：</label></th>
					<td><textarea name="projectInfo" id="projectInfo"
							style="width: 700px; height: 400px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 修改app项目信息 -->
	<div id="DivCdd1" class="easyui-dialog"
		style="width: 900px; height: 520px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'修改项目信息',iconCls: 'icon-add',
			buttons: [{
			         text:'加载模板',
			         iconCls:'icon-load',
			         handler:function(){
			         lodeProductDetailInfoModel();
			         }
			     },{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        saveDetail(1);
                    }
                },{
                    text:'重置',
                    iconCls:'icon-clear',
                    handler:function(){
                        KindEditor.remove('#projectInfoMobile');
                        createKindEditor('#projectInfoMobile').html('');
                        $('#ffCdd1').form('clear');;
                    }
                },{
                    text:'关闭',
                     iconCls:'icon-cancel',
                    handler:function(){
                        $('#DivCdd1').dialog('close');
                    }
                }]">
		<form id="ffCdd1" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table class="view">
				<tr>
					<th><label for="name">app项目信息：</label></th>
					<td><textarea name="projectInfoMobile" id="projectInfoMobile"
							style="width: 700px; height: 400px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 修改抵押物信息-->
	<div id="DivCdd2" class="easyui-dialog"
		style="width: 900px; height: 520px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'修改抵押物信息',iconCls: 'icon-add',
			buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        saveDetail(2);
                    }
                },{
                    text:'重置',
                    iconCls:'icon-clear',
                    handler:function(){
                         KindEditor.remove('#introduction');
                      createKindEditor('#introduction').html('');
                        $('#ffCdd2').form('clear');;
                    }
                },{
                    text:'关闭',
                     iconCls:'icon-cancel',
                    handler:function(){
                        $('#DivCdd2').dialog('close');
                    }
                }]">
		<form id="ffCdd2" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table class="view">
				<tr>
					<th><label for="name">抵押物信息(315*345)：</label></th>
					<td><textarea name="introduction" id="introduction"
							style="width: 700px; height: 400px; visibility: hidden;"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 修改产品详情-->
	<div id="DivDesc" class="easyui-dialog"
		style="width: 900px; height: 520px; padding: 10px 20px" closed="true"
		modal="true"
		data-options="title:'修改产品详情',iconCls: 'icon-add',
			buttons: [{
                    text:'分块录入',
                    iconCls:'icon-ok',
                    handler:function(){
                        insertDepart();
                    }
                },{
                    text:'统一录入',
                    iconCls:'icon-ok',
                    handler:function(){
                        insertAll();
                    }
                },{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        saveDesc();
                    }
                },{
                    text:'重置',
                    iconCls:'icon-clear',
                    handler:function(){
                        $('#title1,#title2,#title3,#title4,#title5,#title6,#title7,#title8,#content1,#content2,#content3,#content4,#content5,#content6,#content7,#content8,#description').textbox('setValue','');
                    }
                },{
                    text:'关闭',
                     iconCls:'icon-cancel',
                    handler:function(){
                        $('#DivDesc').dialog('close');
                    }
                }]">
		<form id="ffDesc" method="post" novalidate="novalidate">
			<input type="hidden" name="id" id="descId"> <input
				type="hidden" name="insertType" id="insertType" value="0">
			<table class="view" id="insertDepart">
				<tr>
					<th><label>标题1：</label></th>
					<td><input class="easyui-textbox" name="title1" id="title1"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label>内容1：</label></th>
					<td><input class="easyui-textbox" name="content1"
						id="content1" style="width: 700px; height: 200px;"
						data-options="required:false,multiline:true"></input></td>
				</tr>
				<tr>
					<th><label>标题2：</label></th>
					<td><input class="easyui-textbox" name="title2" id="title2"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label>内容2：</label></th>
					<td><input class="easyui-textbox" name="content2"
						id="content2" style="width: 700px; height: 200px;"
						data-options="required:false,multiline:true"></input></td>
				</tr>
				<tr>
					<th><label>标题3：</label></th>
					<td><input class="easyui-textbox" name="title3" id="title3"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label>内容3：</label></th>
					<td><input class="easyui-textbox" name="content3"
						id="content3" style="width: 700px; height: 200px;"
						data-options="required:false,multiline:true"></input></td>
				</tr>
				<tr>
					<th><label>标题4：</label></th>
					<td><input class="easyui-textbox" name="title4" id="title4"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label>内容4：</label></th>
					<td><input class="easyui-textbox" name="content4"
						id="content4" style="width: 700px; height: 200px;"
						data-options="required:false,multiline:true"></input></td>
				</tr>
				<tr>
					<th><label>标题5：</label></th>
					<td><input class="easyui-textbox" name="title5" id="title5"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label>内容5：</label></th>
					<td><input class="easyui-textbox" name="content5"
						id="content5" style="width: 700px; height: 200px;"
						data-options="required:false,multiline:true"></input></td>
				</tr>
				<tr>
					<th><label>标题6：</label></th>
					<td><input class="easyui-textbox" name="title6" id="title6"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label>内容6：</label></th>
					<td><input class="easyui-textbox" name="content6"
						id="content6" style="width: 700px; height: 200px;"
						data-options="required:false,multiline:true"></input></td>
				</tr>
				<tr>
					<th><label>标题7：</label></th>
					<td><input class="easyui-textbox" name="title7" id="title7"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label>内容7：</label></th>
					<td><input class="easyui-textbox" name="content7"
						id="content7" style="width: 700px; height: 200px;"
						data-options="required:false,multiline:true"></input></td>
				</tr>
				<tr>
					<th><label>标题8：</label></th>
					<td><input class="easyui-textbox" name="title8" id="title8"
						style="width: 200px" data-options="required:false"></input></td>
				</tr>
				<tr>
					<th><label>内容8：</label></th>
					<td><input class="easyui-textbox" name="content8"
						id="content8" style="width: 700px; height: 200px;"
						data-options="required:false,multiline:true"></input></td>
				</tr>
			</table>
			<table class="view" id="insertAll" style="display: none;">
				<tr>
					<th><label>产品详情：</label></th>
					<td><input class="easyui-textbox" name="description"
						id="description" style="width: 700px; height: 405px;"
						data-options="required:false,multiline:true"></input></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="productDetailInfo" class="easyui-dialog" title="项目详情" style="width:800px; height:600px;" data-options="closed:true,modal:true">
	    <div id="productDetailInfoTable" class="easyui-tabs" data-options="fit:true,tools:[
	    {
	        text : '加载借款人信息模板',
	        iconCls:'icon-load',
	        handler:function(){
	            loadLonnerInfoModel();
	        }
	    },{
            text : '加载常见问题模板',
            iconCls:'icon-load',
            handler:function(){
                loadNormalQuesModel();
            }
        }, {
            text : '保存',
            iconCls:'icon-save',
            handler:function(){
                saveProductDetailInfo();
            }
        },
	    ]">   
	       <input type="hidden" id="productDetailInfoPid">
		    <div title="借款人信息" style="padding:20px;">   
		        <input id="productDetailLonnerInfo" style="width: 700px; height: 500px; mragin: 30px 20px;" data-options="multiline:true">   
		    </div>   
		    <div title="常见问题"  style="padding:20px;">   
		        <input id="productDetailNormalQues" style="width: 700px; height: 500px; mragin: 30px 20px;" data-options="multiline:true">
		    </div>   
		</div>  
	</div>
	
	<!-- 项目信息模板 -->
	<div id="productDetailInfoModel">
	    <p class="project-box-pro">
		    <span class="project-left">回款方式</span>
		    <span class="project-right">一次性还本付息</span>
		</p>
		<p class="project-box-pro">
		    <span class="project-left">回款说明</span>
		    <span class="project-right">出借本金及收益将于项目到期日后1个工作日内回款至投资人资金存管账户</span>
		</p>
		<p class="project-box-pro">
		    <span class="project-left">资金存管</span>
		    <span class="project-right">广东华兴银行提供银行存管服务，保障用户资金安全</span>
		</p>
		<p class="project-box-pro">
		    <span class="project-left">产品说明</span>
		    <span class="project-right">
		        1.本产品项下借款人与出借人通过平台签署电子借款协议，明确借贷双方的债权债务关系。<br />
		        2.本产品项下借款人为企业。借款企业已通过大数据风控模型及风控人员的严格审核，借款企业信用记录良好，目前企业经营状况良好。
		    </span>
		</p>
		<p class="project-box-pro">
		    <span class="project-left">安全等级</span>
		    <span class="project-right">稳健型（内部评级，仅供参考）</span>
		</p>
		<p class="project-box-pro">
		    <span class="project-left">认购准备</span>
		    <span class="project-right">
		        1.新用户购买需要提前开通资金存管户。<br />
		        2.产品限量抢购，请提前在资金存管账户准备资金。
		    </span>
		</p>
		<p class="project-box-pro">
		    <span class="project-left">费用说明</span>
		    <span class="project-right">认购和到期回款均不收取投资人任何费用</span>
		</p>
		<p class="project-box-pro">
		    <span class="project-left">风险提示</span>
		    <span class="project-right">领钱儿仅为信息发布平台，未以任何明示或暗示的方式对出借人提供担保或承诺保本保息。出借人应根据自身的投资偏好和风险承受能力进行独立判断和作出决策，并自行承担资金出借的风险与责任，包括但不限于可能的本息损失。</span>
		</p>
	</div>
	
	
	<div id="productLonnerInfoModel" style="display:none;">
	   <p class="project-tip">借款人信息</p>
       <div class="">
           <p class="project-box-pro">
               <span class="project-left">企业名称</span>
               <span class="project-right">请填写企业名称</span>
           </p>
           <p class="project-box-pro">
               <span class="project-left">注册地</span>
               <span class="project-right">请填写注册地</span>
           </p>
           <p class="project-box-pro">
               <span class="project-left">注册年限</span>
               <span class="project-right">请填写注册年限</span>
           </p>
           <p class="project-box-pro">
               <span class="project-left">注册资本</span>
               <span class="project-right">请填写注册年限</span>
           </p>
           <p class="project-box-pro">
               <span class="project-left">经营范围</span>
               <span class="project-right">企业征信</span>
           </p>
           <p class="project-box-pro">
               <span class="project-left">企业征信</span>
               <span class="project-right">请填写注册年限</span>
           </p>
           <p class="project-box-pro">
               <span class="project-left">企业经营状况</span>
               <span class="project-right">请填写注册年限</span>
           </p>
           <p class="project-box-pro">
               <span class="project-left">本平台借款次数</span>
               <span class="project-right">请填写注册年限</span>
           </p>
           <p class="project-box-pro">
               <span class="project-left">本平台逾期次数</span>
               <span class="project-right">请填写注册年限</span>
           </p>
           <p class="project-box-pro">
               <span class="project-left">借款用途</span>
               <span class="project-right">请填写注册年限</span>
           </p>
           <p class="project-box-pro">
               <span class="project-left">第一次还款来源</span>
               <span class="project-right">请填写注册年限</span>
           </p>
       </div> 
	</div>
	
	<div id="productNormalQuesModel" style="display:none;">
       <li>
         <div class="tebs-box">
			  <div class="problem-box">
			     <p class="problem-tip" data-tip="0">1.如何计算预期收益？ <img src="/resource/images/uparrow.png"/></p> 
			     <p class="problem-content">
			    预期收益＝投资本金×预期年化利率×实际存续天数÷365
			    情景分析（为方便起见，本计划说明书中所涉及的数字保留到小数点后两位）：
			    某用户投资金额为5万元，当期产品实际存续天数为30天，预期年化收益率为8%。
			    用户预期年化收益为：50000×8%×30÷365＝328.77元。
			    上述收益测算中数据均为模拟数据，仅供参考。
			    </p>
			  </div>
			  
			   <div class="problem-box">
			     <p class="problem-tip" data-tip="0">2.产品到期退出规则是什么？ <img src="/resource/images/uparrow.png"/></p>
			     <p class="problem-content">
			     1.产品正常到期，用户本金及收益自动划转至用户账户。<br>
			     2.如产品因故提前或延迟到期，则到期日以平台发布的公告或电话告知为准。<br>
			     3.到期日当日不计算收益，平台将最迟于到期日后的一个工作日内一次性返还投资本金及收益。
			    </p>
			  </div>
			</div>
      </li>
    </div>
</body>
</html>