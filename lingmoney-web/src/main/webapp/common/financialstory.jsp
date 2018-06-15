<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$(".subitem").click(function() {
			$(".mLeft li").find("a").removeClass("hover");
			$(this).addClass("hover");
			$(".mLeft dl").hide();
			$(this).parent("li").find("dl").show(300);
		});
	});
</script>
<!-- 理财小故事开始 -->
<div class="mLeft">
	<h2>理财小故事</h2>
	<ul>
		<li id="financialstoryli"><a id="cartooncategory"
			href="javascript:void(0)" class="subitem">漫画贴</a>
			<dl>
				<c:forEach var="item" items="${listCartoonCategory }">
					<dd>
						<a href="/financialStory/cartoonCategory.html?id=${item.id }" id="cartoon${item.id }">${item.name }</a>
					</dd>
				</c:forEach>
			</dl></li>
		<li><a href="/financialStory/financialManagement.html" id="financialmanagement">理财经</a></li>
	</ul>
</div>
<!-- 理财小故事结束 -->