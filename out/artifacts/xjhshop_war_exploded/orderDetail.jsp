<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单详情页</title>
<link rel="stylesheet" type="text/css" href="css/login2.css">
<link href="css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript">
	function payZXB(orderId,money){
		location.href="${pageContext.request.contextPath}/payServlet?method=alipay&oid="+orderId+"&omoney="+money;
	}
	function pay(orderId,money){
		location.href="pay.jsp?oid="+orderId+"&omoney="+money;
	}
</script>
</head>
<%@ include file="header.jsp" %>
<div class="panel panel-default" style="margin: 0 auto;width: 95%;">
	<div class="panel-heading">
	    <h3 class="panel-title"><span class="glyphicon glyphicon-equalizer"></span>&nbsp;&nbsp;订单详情</h3>
	</div>
	<div class="panel-body">
	<table cellpadding="0" cellspacing="0" align="center" width="100%" class="table table-striped table-bordered table-hover">

		<tr>
			<td>订单编号:</td>
			<td>${order.id}</td>
			<td>订单时间:</td>
			<td>${order.time}</td>
		</tr>
		<tr>
			<td>收件人:</td>
			<td>${address.name}</td>
			<td>联系电话:</td>
			<td>${address.phone}</td>
		</tr>
		<tr>
			<td>送货地址:</td>
			<td>${address.detail}</td>
			<td>总价:</td>
			<td>${order.money}</td>
		</tr>
		<tr>
			<td align="center">商品列表:</td>
			<td colspan="3">
				<table align="center" cellpadding="0" cellspacing="0" width="100%"  class="table table-striped table-bordered table-hover">
					<tr align="center"  class="info">
						<th>序号</th>
						<th>商品封面</th>
						<th>商品名称</th>
						<th>商品评分</th>
						<th>商品日期</th>
						<th>商品单价</th>
						<th>购买数量</th>
						<th>小计</th>
					</tr>
					<%--<c:forEach items="${orderDetails}" var="orderDetail" varStatus="j">--%>
						<c:forEach items="${goodsWithNumAndMoneyList}" var="goods" varStatus="i">
							<tr align="center">
								<th>${i.count}</th>
								<th>
									<img src="${pageContext.request.contextPath}/${goods.picture}" width="50px" height="50px">
								</th>
								<th>${goods.name}</th>
								<th>${goods.star}</th>
								<th>${goods.pubdate}</th>
								<th>${goods.price}</th>
								<%--<c:forEach items="${numList}" var="num" varStatus="i">
									<th>${num}</th>
								</c:forEach>
								<c:forEach items="${moneyList}" var="money" varStatus="i">
									<th>${money}</th>
								</c:forEach>--%>
								<th>${goods.num}</th>
								<th>${goods.money}</th>

							</tr>
						</c:forEach>
					<%--</c:forEach>--%>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="4" style="margin-right: 40px;">
				<a href="${pageContext.request.contextPath }/orderServlet?method=getOrderList" class="btn btn-danger btn-sm">返回订单列表</a>
				&nbsp;&nbsp;
				<c:if test="${order.status eq 1 }">&nbsp;&nbsp;
					<button type="button" onclick="payZXB('${order.id}','${order.money}')" class="btn btn-success btn-sm">支付宝支付</button>
				</c:if>
			</td>
		</tr>
	</table>
	</div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>