<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<a data-toggle="collapse" data-parent="#accordion" href="#collapse1">发标</a>
	</div>
	<div id="collapse1" class="panel-collapse collapse">
		<div class="panel-body">
			<form action="/rest/bank/hxBidding/bidding" method="post" target="_blank">
				<div class="input-group input-group-sm">
					<span class="input-group-addon">标的ID</span>
					<input type="text" class="form-control" name="biddingId"/>
				</div>
				<input type="submit" class="btn btn-default btn-sm" value="发标" />
			</form>
		</div>
	</div>
</div>
