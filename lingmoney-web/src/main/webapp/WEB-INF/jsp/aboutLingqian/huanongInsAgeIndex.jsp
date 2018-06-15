
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<jsp:include page="/common/head.jsp"></jsp:include>


<style type="text/css">
body {
	margin: 0;
	padding: 0;
	width: 100%;
}

#personal_agreement {
	
}
.personal_box{
width: 1024px;
	margin-left: auto;
	margin-right: auto;
	color:#333;
}
 .title_center{
        text-align: center;
        font-weight: bold;
        font-size: 20px;
      
        margin-top:20px;
    }
    
    .general{
        text-align: center;
        font-weight: bold;
        font-size: 16px;
        padding: 30px 0px;
    }
    
    .straight_matter{
      /*   padding: 5px 0 5px 0; */
        font-size: 16px;
        line-height:28px;
        margin-bottom:12px;
        text-indent: 32px;
    }
    .straight_matter span{
      /*   padding: 0px 5px 0 30px; */
        font-weight: bold;
    }
    
    .straight_matter_two{
      /*   padding: 5px 5px 0 30px; */
         font-size: 16px;
        line-height:28px;
        margin-bottom:12px;
        text-indent: 32px;
    }
    
    .straight_matter_three{
     /*    padding: 5px 5px 0 30px; */
        font-size: 16px;
        line-height:28px;
        margin-bottom:12px;
        text-indent: 32px;
    }
    
    .straight_matter_three span{
       /*  padding: 0px 5px 0 5px; */
        font-weight: bold;
    }
</style>
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<input id="pId" type="hidden" value="">
	<div id="personal_agreement" class="personal_box">
		<jsp:include page="huanongInsAgePersonal.jsp"></jsp:include>
	</div>
	<div id="company_agreement" class="personal_box">
		<jsp:include page="huanongInsAgeCompany.jsp"></jsp:include>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
<script type="text/javascript">
	getPrdModel();

	function GetQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}

	function getPrdModel(prdno) {
		var pCode = "";
		$.ajax({
			url : '/insurance/queryProBorType',
			type : 'post',
			async : false,
			data : {
				pId : GetQueryString("pId")
			},
			dataType : 'json',
			success : function(data) {
				console.log(data);
				if (data.code == 200) {
					console.log(data.obj);
					if (data.obj == 1010) {
						showPersionalAgreement();
					} else {
						showCompanyAgreement();
					}
				} else {
					showCompanyAgreement();
				}
			},
			error : function(d) {
				showCompanyAgreement();
			}
		});
	}

	function showPersionalAgreement() {
		console.log("显示个人协议");
		$("#personal_agreement").show();
		$("#company_agreement").hide();
	}

	function showCompanyAgreement() {
		console.log("显示公司协议");
		$("#personal_agreement").hide();
		$("#company_agreement").show();
	}
</script>
</html>
