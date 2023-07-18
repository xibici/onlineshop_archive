<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台 订单列表</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./css/bootstrap.min.css">
<script src="./js/jquery.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script type="text/javascript">
	function sendOrder(id){
		location.href = "${pageContext.request.contextPath}/sendOrder?oid="+id;
	}
	$(function(){
		$("#search").click(function(){
			var username = $("input[name='username']").val();
			var status = $("select[name='orderStatus'] option:selected").val();
			location.href="${pageContext.request.contextPath}/searchOrder?username="+username+"&status="+status;
		})


	})


</script>
</head>
<body>
<div class="row" style="width:98%;margin-left: 1%;margin-top: 5px;">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				订单列表
			</div>
			<div class="panel-body" >
			<%--查找--%>
			<form action="adminServlet?method=findOrderBySearch" name="form1" id="form1" method="post">

				<div class="row">
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<span>用户id</span>
							<input type="text" name="userId" value="${userId}" class="form-control">
						</div>
					</div>
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<span>订单状态</span>
							<select name="status"  class="form-control">

									<option value="-1">----------</option>
								<c:if test="${status eq 1 }">
									<option value="1" selected="selected">未支付</option>
									<option value="2">已支付,待发货</option>
									<option value="3">已发货,待收货</option>
									<option value="4">完成订单</option>
								</c:if>
								<c:if test="${status eq 2 }">
									<option value="1">未支付</option>
									<option value="2" selected="selected">已支付,待发货</option>
									<option value="3">已发货,待收货</option>
									<option value="4">完成订单</option>
								</c:if>
								<c:if test="${status eq 3 }">
									<option value="1">未支付</option>
									<option value="2">已支付,待发货</option>
									<option value="3" selected="selected">已发货,待收货</option>
									<option value="4">完成订单</option>
								</c:if>
								<c:if test="${status eq 4 }">
									<option value="1">未支付</option>
									<option value="2">已支付,待发货</option>
									<option value="3">已发货,待收货</option>
									<option value="4" selected="selected">完成订单</option>
								</c:if>
								<c:if test="${status eq -1 }">
									<option value="1">未支付</option>
									<option value="2">已支付,待发货</option>
									<option value="3">已发货,待收货</option>
									<option value="4" >完成订单</option>
								</c:if>
								<c:if test="${status eq null }">
									<option value="1">未支付</option>
									<option value="2">已支付,待发货</option>
									<option value="3">已发货,待收货</option>
									<option value="4" >完成订单</option>
								</c:if>

							</select>

						</div>
					</div>
					<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
						<input type="submit" class="btn btn-primary" value="查询">
					</div>
				</div>
			</form>

				<div style="height: 400px;overflow: scroll;">
				<table id="tb_list" class="table table-striped table-hover table-bordered table-condensed">
					<tr>
						<td>序号</td>
						<td>订单编号</td>
						<td>总金额</td>
						<td>订单状态</td>
						<td>订单时间</td>
						<td>用户id</td>
						<td>操作</td>
					</tr>
					<c:forEach items="${orderList}" var="order" varStatus="i">
					<tr>
						<td>${i.count}</td>
						<td>${order.id}</td>
						<td>${order.money}</td>
						<td>
							<c:if test="${order.status eq 1}">
								未支付
							</c:if>
							<c:if test="${order.status eq 2}">
								已支付,待发货
							</c:if>
							<c:if test="${order.status eq 3}">
								已发货,待收货
							</c:if>
							<c:if test="${order.status eq 4}">
								订单完成
							</c:if>
						</td>
						<td>${order.time}</td>
						<td>${order.uid}</td>

						<td>

							<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal${order.id}">修改</button>&nbsp;&nbsp;
							<!-- bootstrap弹出模态框 -->

							<div class="modal fade" tabindex="-1" role="dialog" id="myModal${order.id}">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">
												<span>&times;</span>
											</button>
											<h4 class="modal-title">修改订单信息</h4>
										</div>
										<form action="adminServlet?method=updateOrderById" enctype="multipart/form-data" method="post" class="form-horizontal">
											<div class="motal-body">
									<%--隐藏一个input用来接收id--%>
												<input type="hidden" name="id" value="${order.id}">
												<div class="form-group">
													<label class="col-sm-2 control-label">总金额</label>
													<div class="col-sm-10">
														<input type="text" name="money" class="form-control" value="${order.money}">
													</div>
												</div>

												<div class="form-group">
													<label class="col-sm-2 control-label">订单状态</label>
													<div class="col-sm-10">
														<input type="text" name="status" class="form-control" value="${order.status}">
													</div>
												</div>

											</div>
											<div class="motal-footer text-center" style="margin-bottom:10px">
												<button type="submit" class="btn btn-primary">修改</button>
											</div>
										</form>
									</div>
								</div>
							</div>

						</td>


					</tr>
					</c:forEach>
					
				</table>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>