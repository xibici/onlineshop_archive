<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./css/bootstrap.min.css" />
<script src="./js/jquery.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/DatePicker.js"></script>
	<script>

		function delselected(pid){
			if(confirm("你确定要删除吗")){
				//ajax请求删除商品
				$.get("adminServlet?method=delGoodsByPid",{pid:pid},function (data) {
					location.reload();
				});
			}
		}

	</script>
	
<title>商品列表</title>

</head>
<body>
<div class="row" style="width:98%;margin-left: 1%;">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				会员列表
			</div>
			<div class="panel-body">
			<form action="adminServlet?method=findGoodsBySearch" name="form1" id="form1" method="post">
				<div class="row">
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<span>商品名称</span>
							<input type="text" name="name" class="form-control">
						</div>
					</div>
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<%--<span>上架时间</span>
							<input type="text" readonly="readonly"  name="pubdate" class="form-control" onclick="setday(this)">
						--%></div>
					</div>
					<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
						<input type="submit" class="btn btn-primary" value="查询">
					</div>
				</div>
			</form>
				<div style="height: 400px;overflow: scroll;">
					<table id="tb_list" class="table table-striped table-hover table-bordered">
						<tr>
							<td>序号</td><td>商品名称</td><td>价格</td><td>上架时间</td><td>类型</td><td>操作</td>
						</tr>
						<%--<% System.out.println("前台request"+request.getAttribute("goodsList"));%>--%>
						<c:forEach items="${goodsList}" var="goods" varStatus="i">
							<tr>
								<td>${goods.id}</td>
								<td id="name">${goods.name}</td>
								<td id="price">${goods.price}</td>
								<td id="pubdate">${goods.pubdate}</td>
								<td id="typeName">${goods.typeid}</td>
								<td>

									<button onclick="delselected(${goods.id});" class="btn btn-danger btn-sm">删除</button>&nbsp;&nbsp;
									<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal${goods.id}">修改</button>&nbsp;&nbsp;
									<!-- bootstrap弹出模态框 -->

									<div class="modal fade" tabindex="-1" role="dialog" id="myModal${goods.id}">
										<div class="modal-dialog" role="document">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal">
														<span>&times;</span>
													</button>
													<h4 class="modal-title">修改商品信息</h4>
												</div>
												<form action="adminServlet?method=updateGoodsByPid" enctype="multipart/form-data" method="post" class="form-horizontal">
													<div class="motal-body">
														<div class="form-group">
															<label class="col-sm-2 control-label">商品名称</label>
															<div class="col-sm-10">
																<input type="hidden" name="id" value="${goods.id}">
																<%--<input type="hidden" name="level" value="${goods.level}">--%>
																<input type="text" name="name" class="form-control" value="${goods.name}">
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">价格</label>
															<div class="col-sm-10">
																<input type="text" name="price" class="form-control" value="${goods.price}">
															</div>
														</div>

														<%--改图片--%>
														<div class="form-group">
															<label class="col-sm-2 control-label">商品封面</label>
															<div class="col-sm-10">
																<%--input type="file" id="file" name="myfile"--%>
																<input type="file" name="myfile" class="form-control" value="${goods.picture}">
															</div>
														</div>

														<div class="form-group">
															<label class="col-sm-2 control-label">商品类型</label>
															<div class="col-sm-10">
																<input type="text" name="typeid" class="form-control" value="${goods.typeid}">
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




									<a tabindex="0" id="example${goods.id}" class="btn btn-primary btn-xs"
									role="button" data-toggle="popover"
									data-trigger="focus"
									data-placement="left"
									data-content="${goods.intro}">描述</a>
									<script type="text/javascript">
										$(function(){
											$("#example${goods.id}").popover();
										})
									</script>
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