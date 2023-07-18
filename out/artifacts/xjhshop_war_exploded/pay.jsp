<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	    <meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1">
		<title>在线支付</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/login2.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
</head>
<%@ include file="header.jsp" %>
<body>
	<div class="container">
		<div class="row">
			<div class="">
				<div class="panel panel-default"  style="margin: 0 auto;width: 95%;">
				  <div class="panel-heading">
				    <h3 class="panel-title"><span class="glyphicon glyphicon-yen"></span>&nbsp;&nbsp;在线支付
				    	<span class="pull-right"><a href="${pageContext.request.contextPath }/getOrderList">返回订单列表</a>
				    	</span>
				    </h3>
				  </div>
				  <div class="panel-body">	 
				  	<form  action="${pageContext.request.contextPath}/payServlet?method=pay" method="post">
					<table class="table table-bordered table-striped table-hover">
						<tr>
							<td colspan="1">订单号:</td>
							<td colspan="3"><input type="text" class="form-control" name="orderid" value="<%=request.getParameter("oid")%>" readonly="readonly"></td>
						</tr>
						<tr>
							<td colspan="1">支付金额:</td>
							<td colspan="3">
								<div class="input-group" style="width: 200px;">
							      <input type="text" class="form-control"  name="money" value="<%=request.getParameter("omoney")%>">
							      <div class="input-group-addon"><span class="glyphicon glyphicon-yen"></span></div>
						      	</div>
							</td>
						</tr>
						<tr><td colspan="4" class="alert-danger"><strong>请您选择在线支付银行</strong></td></tr>
						<tr>
						  <td><INPUT TYPE="radio" NAME="bankId" value="CMBCHINA-NET-B2C">招商银行</td>
						  <td><INPUT TYPE="radio" NAME="bankId" value="ICBC-NET-B2C">工商银行</td>
						  <td><INPUT TYPE="radio" NAME="bankId" value="ABC-NET-B2C">农业银行</td>
						  <td><INPUT TYPE="radio" NAME="bankId" value="CCB-NET-B2C">建设银行 </td>
						</tr>
						
					</table>
					<div class="pull-right" style="margin-right: 30px;">
						<input type="submit" value="确认提交" class="btn btn-warning btn-lg">
						
					</div>
					</form>
				 </div>
			   </div>
			</div>
			
		</div>
		
	</div>
	<!-- 底部 -->
   <%@ include file="footer.jsp"%>

	
</body>
</html>