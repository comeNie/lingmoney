<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>车辆配置管理</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/vehicle/vehicleConfiguration.js"></script>
</head>
<body>
		<div class="easyui-layout" style="width:700px;" fit="true">
	        <div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding:5px;height:80px">
	            <form id="searchFrm">
	            	<table>
	            		<tr>
	            			<td>车辆价格：</td>
	            			<td><input class="easyui-textbox" name="vehiclePrices" id="svehiclePrices" /></td>
	            			<td><a href="javascript:search()" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-search" >查询</a></td>
	            		</tr>
	            	</table>
	            </form>
	        </div>
	        <div id="tb"  data-options="region:'center'" style="padding:5px;height:auto">       
	            <table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
		            data-options="singleSelect:true,toolbar:toolbar,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/veconfi/confilist',method:'post'">
			        <thead>
			            <tr>
			            	<th data-options="field:'id',width:30">id</th>
			            	<th data-options="field:'vehiclePrices',width:100">车辆价格</th>
			            	<th data-options="field:'level',width:100">车辆级别</th>
			            	<th data-options="field:'mileage',width:60">公里数</th>
			            	<th data-options="field:'carCondition',width:60">车况</th>
			            	<th data-options="field:'structure',width:60">车辆结构</th>
			            	<th data-options="field:'color',width:60">车辆颜色</th>
			            	<th data-options="field:'yearLimit',width:100">车辆年限</th>
			            	<th data-options="field:'displacemint',width:85">发动机排气量</th>
			            	<th data-options="field:'emissionStandard',width:110">汽车尾气排放标准</th>
			            	<th data-options="field:'seating',width:50">座位数</th>
			            	<th data-options="field:'energy',width:100">汽车采用的能源</th>
			            	
			            	<th data-options="field:'intakeType',width:70">进气形式</th>
			            	<th data-options="field:'gearbox',width:70">变速箱</th>
			            	<th data-options="field:'driveMode',width:60">驱动方式</th>
			            	<th data-options="field:'insurance',width:80">保险</th>
			            	<th data-options="field:'seriousAccident',width:100">有无重大事故</th>
			            	<th data-options="field:'waterFireAccident',width:120">有无火烧、水泡事故</th>
			            	<th data-options="field:'majorRepair',width:150">大修记录</th>
			            	<th data-options="field:'untreatedViolations',width:180">未处理的违章</th>
			            	<th data-options="field:'licensePlateLocation',width:80">车牌所在地</th>
			            	<th data-options="field:'licenseNumber',width:100">车牌号码</th>
			            	<th data-options="field:'skylight',width:80">天窗类型</th>
			            	<th data-options="field:'radarImage',width:100">倒车雷达及影像</th>
			            	<th data-options="field:'seatType',width:70">座椅类型</th>
			            	<th data-options="field:'startType',width:90">启动类型</th>
			            </tr>
			        </thead>
		    	</table>
	        </div>
		</div>
		<!-- 添加的弹出层 -->
		 <div id="DivAdd" class="easyui-dialog" style="width:800px;height:500px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'添加车辆配置',iconCls: 'icon-add',buttons: '#dlg-buttons'">
			 <form id="ffAdd" method="post" novalidate="novalidate" enctype="multipart/form-data" >
			 	<table id="tblAdd" class="view">
			 		<tr>
			 			<th>
                           <label for="avehiclePrices">车辆价格：</label></th>
                        <td>
                        	 <input  class="easyui-numberbox" precision="2" name="vehiclePrices" id="avehiclePrices" 
                        	 	style="width:200px" data-options="required:true"></input></td>
                        <th>
                           <label for="alevel">车辆级别，如轿车、SUV等：</label>
                        </th>
                        <td>
                        	<!--   <input  class="easyui-textbox" name="level" id="alevel" style="width:200px" 
                        	 data-options="required:true,panelWidth: 400"></input> -->
                        	 
                        	 <select class="easyui-combogrid" name="level" id="alevel" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'levelId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'levelName',
						            url: '/resource/json/vehicle/level_type.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'levelId',title:'类型id',width:30},
						                {field:'levelName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						    </select>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="amileage">公里数：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" precision="2" name="mileage" id="amileage" style="width:200px" data-options="required:true"></input>
                        </td>
                        
                        <th>
                           <label for="acarCondition">车况(新旧程度)：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" suffix="%" name="carCondition" id="acarCondition" style="width:200px" data-options="required:true"></input>
                        </td>
                    </tr>
                    <tr>
                        <th>
                           <label for="astructure">车辆结构(两厢、敞篷等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="structure" id="astructure" style="width:200px" data-options="required:true"></input> -->
                        	 
                        	  <select class="easyui-combogrid" name="structure" id="astructure" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'structureId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'structureName',
						            url: '/resource/json/vehicle/structure_type.json',
						            method: 'get',
						           
						            columns: [[
						                {field:'structureId',title:'类型id',width:30},
						                {field:'structureName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						    </select>
                        </td>
                        <th>
                           <label for="acolor">车辆颜色：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="color" id="acolor" style="width:200px" data-options="required:true"></input>
                        </td>
                    </tr>
                    <tr> 
                        <th>
                           <label for="ayearLimit">车辆年限：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" name="yearLimit" id="ayearLimit" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="adisplacemint">发动机排气量：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" precision="1" name="displacemint" id="adisplacemint" style="width:200px" data-options="required:true"></input>
                        </td>
                    </tr>   
                    <tr>      
                        <th>
                           <label for="aemissionStandard">汽车尾气排放标准：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="emissionStandard" id="aemissionStandard" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="aseating">座位数：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" name="seating" id="aseating" style="width:200px" data-options="required:true"></input>
                        </td>
                    </tr> 
                    
                    
                    
                    <tr>  
                        <th>
                           <label for="aenergy">汽车采用的能源(如汽油、电动等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="energy" id="aenergy" style="width:200px" data-options="required:true"></input> -->
                        	 
                        	   <select class="easyui-combogrid" name="energy" id="aenergy" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'energyId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'energyName',
						            url: '/resource/json/vehicle/energy_type.json',
						            method: 'get',
						          
						            columns: [[
						                {field:'energyId',title:'类型id',width:30},
						                {field:'energyName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						    </select>
                        </td>
                        <th>
                           <label for="aintakeType">进气形式(如自然吸气、涡轮增压等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="intakeType" id="aintakeType" style="width:200px" data-options="required:true"></input> -->
                        	 
                        	  <select class="easyui-combogrid" name="intakeType" id="aintakeType" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'intakeTypeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'intakeTypeName',
						            url: '/resource/json/vehicle/intake_Type.json',
						            method: 'get',
						           
						            columns: [[
						                {field:'intakeTypeId',title:'类型id',width:30},
						                {field:'intakeTypeName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						    </select>
                        </td>
                    </tr> 
                    <tr> 
                        <th>
                           <label for="agearbox">变速箱(手动、自动等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="gearbox" id="agearbox" style="width:200px" data-options="required:true"></input> -->
                        	 
                        	  <select class="easyui-combogrid" name="gearbox" id="agearbox" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'gearboxId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'gearboxName',
						            url: '/resource/json/vehicle/gearbox_type.json',
						            method: 'get',
						           
						            columns: [[
						                {field:'gearboxId',title:'类型id',width:30},
						                {field:'gearboxName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                        <th>
                           <label for="adriveMode">驱动方式(前驱、后驱等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="driveMode" id="adriveMode" style="width:200px" data-options="required:true"></input> -->
                        	 
                        	 <select class="easyui-combogrid" name="driveMode" id="adriveMode" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'driveModeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'driveModeName',
						            url: '/resource/json/vehicle/driveMode_type.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'driveModeId',title:'类型id',width:30},
						                {field:'driveModeName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                    </tr>
                    <tr>  
                        <th>
                           <label for="ainsurance">保险：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="insurance" id="ainsurance" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	  <select class="easyui-combogrid" name="insurance" id="ainsurance" style="width:200px" data-options="
						            panelWidth: 400,
						            idField: 'insuranceId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'insuranceName',
						            url: '/resource/json/vehicle/insurance.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'insuranceId',title:'类型id',width:30},
						                {field:'insuranceName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                        <th>
                           <label for="aseriousAccident">有无重大事故：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="seriousAccident" id="aseriousAccident" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	 <select class="easyui-combogrid" name="seriousAccidents" id="aseriousAccident" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'seriousAccidentId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'seriousAccidentName',
						            url: '/resource/json/vehicle/seriousAccident.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'seriousAccidentId',title:'类型id',width:30},
						                {field:'seriousAccidentName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                    </tr>  
                    <tr> 
                        <th>
                           <label for="awaterFireAccident">有无火烧、水泡事故：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="waterFireAccident" id="awaterFireAccident" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	 <select class="easyui-combogrid" name="waterFireAccident" id="awaterFireAccident" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'waterFireAccidentId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'waterFireAccidentName',
						            url: '/resource/json/vehicle/waterFireAccident.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'waterFireAccidentId',title:'类型id',width:30},
						                {field:'waterFireAccidentName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                        <th>
                           <label for="amajorRepair">大修记录：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="majorRepair" id="amajorRepair" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	 <select class="easyui-combogrid" name="majorRepair" id="amajorRepair" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'majorRepairId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'majorRepairName',
						            url: '/resource/json/vehicle/majorRepair.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'majorRepairId',title:'类型id',width:30},
						                {field:'majorRepairName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						     </select>
                        </td>
                    </tr>
                    <tr>  
                        <th>
                           <label for="auntreatedViolations">未处理的违章：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="untreatedViolations" id="auntreatedViolations" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	 <select class="easyui-combogrid" name="untreatedViolations" id="auntreatedViolations" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'untreatedViolationsId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'untreatedViolationsName',
						            url: '/resource/json/vehicle/untreatedViolations.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'untreatedViolationsId',title:'类型id',width:30},
						                {field:'untreatedViolationsName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						     </select>
                        </td>
                        <th>
                           <label for="alicensePlateLocation">车牌所在地：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="licensePlateLocation" id="alicensePlateLocation" style="width:200px" data-options="required:true"></input>
                        </td>  
                    </tr>  
                    <tr>                     
                        <th>
                           <label for="alicenseNumber">车牌号码：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="licenseNumber" id="alicenseNumber" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="askylight">天窗类型：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="skylight" id="askylight" style="width:200px" data-options="required:true"></input>
                        </td>
                    </tr>
                    <tr>  
                        <th>
                           <label for="aradarImage">倒车雷达及影像：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="radarImage" id="aradarImage" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="aseatType">座椅类型(如真皮、电动座椅等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="seatType" id="aseatType" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	 <select class="easyui-combogrid" name="seatType" id="aseatType" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'seatTypeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'seatTypeName',
						            url: '/resource/json/vehicle/seatType.json',
						            method: 'get',
						           
						            columns: [[
						                {field:'seatTypeId',title:'类型id',width:30},
						                {field:'seatTypeName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                    </tr>
                    <tr>  
                        <th>
                           <label for="astartType">启动类型(如钥匙启动、无钥匙启动等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="startType" id="astartType" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	 <select class="easyui-combogrid" name="startType" id="astartType" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'startTypeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'startTypeName',
						            url: '/resource/json/vehicle/startType.json',
						            method: 'get',
						           
						            
						            
						            columns: [[
						                {field:'startTypeId',title:'类型id',width:30},
						                {field:'startTypeName',title:'类型名称',width:50}
						            ]],
						            
						            fitColumns: true
						        ">
						      </select>
                        </td>
			 	    </tr>
			 		<tr>
                        <td colspan="4" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
                        </td>
                    </tr>
			 	</table>
			 </form>
		</div>
		<!-- 添加的弹出层 -->
		<!-- 编辑(修改)的弹出层 -->
		<div id="DivEdit" class="easyui-dialog" style="width:800px;height:500px;padding:10px 20px"
			closed="true" resizable="true" modal="true" data-options="title:'修改车辆信息',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
			<form id="ffEdit" method="post" novalidate="novalidate">
				<input type="hidden" name="id" />
				<input type="hidden" name="loginPsw"  id="eloginPsw" value="" />
				<input type="hidden" name="roleName" id="eroleName"/>
				<table id="tblEdit" class="view">
			 	  	<tr>
			 			<th>
                           <label for="avehiclePrices">车辆价格：</label></th>
                        <td>
                        	 <input  class="easyui-numberbox" precision="2" name="vehiclePrices" id="avehiclePrices" 
                        	 	style="width:200px" data-options="required:true"></input></td>
                        <th>
                           <label for="alevel">车辆级别，如轿车、SUV等：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="level" id="alevel" style="width:200px" data-options="required:true,panelWidth: 400"></input>  --> 
                        	 
                        	  <select class="easyui-combogrid" name="level" id="alevel" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'levelId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'levelName',
						            url: '/resource/json/vehicle/level_type.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'levelId',title:'类型id',width:30},
						                {field:'levelName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						    </select>
                        </td>
			 		</tr>
			 		<tr>
			 			<th>
                           <label for="amileage">公里数：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" precision="2" name="mileage" id="amileage" style="width:200px" data-options="required:true"></input>
                        </td>
                        
                        <th>
                           <label for="acarCondition">车况(新旧程度)：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" suffix="%" name="carCondition" id="acarCondition" style="width:200px" data-options="required:true"></input>
                        </td>
                    </tr>
                    <tr>
                        <th>
                           <label for="astructure">车辆结构(两厢、敞篷等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="structure" id="astructure" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	 <select class="easyui-combogrid" name="structure" id="astructure" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'structureId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'structureName',
						            url: '/resource/json/vehicle/structure_type.json',
						            method: 'get',
						           
						            columns: [[
						                {field:'structureId',title:'类型id',width:30},
						                {field:'structureName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						    </select>
                        </td>
                        <th>
                           <label for="acolor">车辆颜色：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="color" id="acolor" style="width:200px" data-options="required:true"></input>
                        </td>
                    </tr>
                    <tr> 
                        <th>
                           <label for="ayearLimit">车辆年限：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" name="yearLimit" id="ayearLimit" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="adisplacemint">发动机排气量：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" precision="1" name="displacemint" id="adisplacemint" style="width:200px" data-options="required:true"></input>
                        </td>
                    </tr>   
                    <tr>      
                        <th>
                           <label for="aemissionStandard">汽车尾气排放标准：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="emissionStandard" id="aemissionStandard" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="aseating">座位数：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-numberbox" name="seating" id="aseating" style="width:200px" data-options="required:true"></input>
                        </td>
                    </tr> 
                    <tr>  
                        <th>
                           <label for="aenergy">汽车采用的能源(如汽油、电动等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="energy" id="aenergy" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	 <select class="easyui-combogrid" name="energy" id="aenergy" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'energyId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'energyName',
						            url: '/resource/json/vehicle/energy_type.json',
						            method: 'get',
						          
						            columns: [[
						                {field:'energyId',title:'类型id',width:30},
						                {field:'energyName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						    </select>
                        </td>
                        <th>
                           <label for="aintakeType">进气形式(如自然吸气、涡轮增压等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="intakeType" id="aintakeType" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	 <select class="easyui-combogrid" name="intakeType" id="aintakeType" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'intakeTypeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'intakeTypeName',
						            url: '/resource/json/vehicle/intake_Type.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'intakeTypeId',title:'类型id',width:30},
						                {field:'intakeTypeName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						    </select>
                        </td>
                    </tr> 
                    <tr> 
                        <th>
                           <label for="agearbox">变速箱(手动、自动等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="gearbox" id="agearbox" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	  <select class="easyui-combogrid" name="gearbox" id="agearbox" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'gearboxId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'gearboxName',
						            url: '/resource/json/vehicle/gearbox_type.json',
						            method: 'get',
						           
						            columns: [[
						                {field:'gearboxId',title:'类型id',width:30},
						                {field:'gearboxName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                        <th>
                           <label for="adriveMode">驱动方式(前驱、后驱等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="driveMode" id="adriveMode" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	 <select class="easyui-combogrid" name="driveMode" id="adriveMode" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'driveModeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'driveModeName',
						            url: '/resource/json/vehicle/driveMode_type.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'driveModeId',title:'类型id',width:30},
						                {field:'driveModeName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                    </tr>
                    <tr>  
                        <th>
                           <label for="ainsurance">保险：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="insurance" id="ainsurance" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	  <select class="easyui-combogrid" name="insurance" id="ainsurance" style="width:200px" data-options="
						            panelWidth: 400,
						            idField: 'insuranceId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'insuranceName',
						            url: '/resource/json/vehicle/insurance.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'insuranceId',title:'类型id',width:30},
						                {field:'insuranceName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                        <th>
                           <label for="aseriousAccident">有无重大事故：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="seriousAccident" id="aseriousAccident" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	  <select class="easyui-combogrid" name="seriousAccidents" id="aseriousAccident" style="width:200px" data-options="
						            panelWidth: 400,
						            idField: 'seriousAccidentId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'seriousAccidentName',
						            url: '/resource/json/vehicle/seriousAccident.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'seriousAccidentId',title:'类型id',width:30},
						                {field:'seriousAccidentName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                    </tr>  
                    <tr> 
                        <th>
                           <label for="awaterFireAccident">有无火烧、水泡事故：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="waterFireAccident" id="awaterFireAccident" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	  <select class="easyui-combogrid" name="waterFireAccident" id="awaterFireAccident" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'waterFireAccidentId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'waterFireAccidentName',
						            url: '/resource/json/vehicle/waterFireAccident.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'waterFireAccidentId',title:'类型id',width:30},
						                {field:'waterFireAccidentName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                        <th>
                           <label for="amajorRepair">大修记录：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="majorRepair" id="amajorRepair" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	  <select class="easyui-combogrid" name="majorRepair" id="amajorRepair" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'majorRepairId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'majorRepairName',
						            url: '/resource/json/vehicle/majorRepair.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'majorRepairId',title:'类型id',width:30},
						                {field:'majorRepairName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						     </select>
                        </td>
                    </tr>
                    <tr>  
                        <th>
                           <label for="auntreatedViolations">未处理的违章：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="untreatedViolations" id="auntreatedViolations" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	  <select class="easyui-combogrid" name="untreatedViolations" id="auntreatedViolations" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'untreatedViolationsId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'untreatedViolationsName',
						            url: '/resource/json/vehicle/untreatedViolations.json',
						            method: 'get',
						            
						            columns: [[
						                {field:'untreatedViolationsId',title:'类型id',width:30},
						                {field:'untreatedViolationsName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						     </select>
                        </td>
                        <th>
                           <label for="alicensePlateLocation">车牌所在地：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="licensePlateLocation" id="alicensePlateLocation" style="width:200px" data-options="required:true"></input>
                        </td>  
                    </tr>  
                    <tr>                     
                        <th>
                           <label for="alicenseNumber">车牌号码：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="licenseNumber" id="alicenseNumber" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="askylight">天窗类型：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="skylight" id="askylight" style="width:200px" data-options="required:true"></input>
                        </td>
                    </tr>
                    <tr>  
                        <th>
                           <label for="aradarImage">倒车雷达及影像：</label>
                        </th>
                        <td>
                        	 <input  class="easyui-textbox" name="radarImage" id="aradarImage" style="width:200px" data-options="required:true"></input>
                        </td>
                        <th>
                           <label for="aseatType">座椅类型(如真皮、电动座椅等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="seatType" id="aseatType" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	  <select class="easyui-combogrid" name="seatType" id="aseatType" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'seatTypeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'seatTypeName',
						            url: '/resource/json/vehicle/seatType.json',
						            method: 'get',
						           
						            columns: [[
						                {field:'seatTypeId',title:'类型id',width:30},
						                {field:'seatTypeName',title:'类型名称',width:50}
						            ]],
						            fitColumns: true
						        ">
						      </select>
                        </td>
                    </tr>
                    <tr>  
                        <th>
                           <label for="astartType">启动类型(如钥匙启动、无钥匙启动等)：</label>
                        </th>
                        <td>
                        	<!-- <input  class="easyui-textbox" name="startType" id="astartType" style="width:200px" data-options="required:true"></input>  --> 
                        	 
                        	  <select class="easyui-combogrid" name="startType" id="astartType" style="width:200px" data-options="
						            panelWidth: 200,
						            idField: 'startTypeId',
						            required:true,
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'startTypeName',
						            url: '/resource/json/vehicle/startType.json',
						            
						            columns: [[
						                {field:'startTypeId',title:'类型id',width:30},
						                {field:'startTypeName',title:'类型名称',width:50}
						            ]],
						            
						            fitColumns: true
						        ">
						      </select>
                        </td>
			 	    </tr>
			 		<tr>
                        <td colspan="4" style="text-align:right; padding-top:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
                        </td>
                    </tr>
                 </table>
            </form>
        </div>
</body>
</html>