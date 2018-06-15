<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#nav04").addClass("nav-hover-main");
		$(".subitem").click(function() {
			$(".mLeft li").find("a").removeClass("hover");
			$(this).addClass("hover");
			$(".mLeft dl").hide(500);
			$(this).parent("li").find("dl").stop(false, true).show(500);
		});
	});
</script>

<!-- 关于我们开始 -->
<div class="mLeft">
	<h2>关于领钱儿</h2>
	<ul>
		<li id="aboutusWeli">
			<a href="javascript:void(0)" id="aboutusWe" class="subitem">关于我们</a>
			<dl>
				<dd>
					<a href="/aboutus.html" id="aboutus">公司简介</a>
				</dd>
				<dd>
					<a href="/aboutusTeam.html" id="aboutusTeam">团队精英</a>
				</dd>
				<dd>
					<a href="/aboutusQualification.html" id="aboutusQualification">资质证明</a>
				</dd>
			</dl>
		</li>

		<li id="aboutusBaozhengli"><a href="javascript:void(0)"
			id="aboutusBaozheng" class="subitem">安全保证</a>
			<dl>
				<dd>
					<a href="/aboutusSafe.html" id="aboutusSafe">安全保障</a>
				</dd>
				<dd>
					<a href="/aboutusLaw.html" id="aboutusLaw">法律声明</a>
				</dd>
			</dl></li>

		<li id="aboutusZixunli"><a href="javascript:void(0)"
			id="aboutusZixun" class="subitem">资讯中心</a>
			<dl>
				<dd>
					<a href="/infoNotice/list.html" id="aboutusNotice">站内公告</a>
				</dd>
				<dd>
					<a href="/infoNews/list.html" id="aboutusNews">公司动态</a>
				</dd>
				<dd>
					<a href="/infoMedia/list.html" id="aboutusMedia">媒体资讯</a>
				</dd>
				<dd>
					<a href="/aboutusIdea.html" id="aboutusIdea">理财理念</a>
				</dd>
			</dl></li>

		<li id="aboutusHuobanli"><a href="javascript:void(0)"
			id="aboutusHuoban" class="subitem">合作伙伴</a>
			<dl>
				<!-- <dd>
					<a href="/aboutusCooperation.html" id="aboutusCooperation">合作机构</a>
				</dd> -->
				<dd>
					<a href="/aboutusJoin.html" id="aboutusJoin">加入我们</a>
				</dd>
			</dl></li>

		<li><a href="/aboutusMap.html" id="aboutusMap">联系我们</a></li>
	</ul>
</div>
<!-- 关于我们结束 -->