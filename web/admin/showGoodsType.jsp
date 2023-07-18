<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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


<title>商品分类</title>
	<script>
		function delselectedType(ptid) {
			if(confirm("你确定要删除吗")){//ajax请求删除商品类型
				$.get("adminServlet?method=delGoodsTypeByPid",{ptid:ptid},function (data) {
					location.reload();
				});
			}
		}

	</script>
</head>
<body>
<div class="row" style="width:98%;margin-left: 1%;">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				商品类型
			</div>
			<div class="panel-body">
				<form action="adminServlet?method=findGoodsTypeByName" name="form1" id="form1" method="post">
				<div class="row">
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<span>商品类型</span>
							<input type="text" name="name" class="form-control">
						</div>
					</div>
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<%--<span>商品名称</span>--%>
							<%--<input type="text" name="pubdate" class="form-control">--%>
						</div>
					</div>
					<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
						<input type="submit" value="查询">
					</div>
				</div>
				</form>
				<div style="height: 400px;overflow: scroll;">
				<table id="tb_list" class="table table-striped table-hover table-bordered">
					<tr>
						<td>序号</td><td>类型</td><td>等级</td><td>所属类型</td><td>操作</td>
					</tr>
					<c:forEach items="${goodsTypeList}" var="gtype" varStatus="i">
					<tr>
						<td>${gtype.id}</td>
						<td>${gtype.name}</td>
						<td>${gtype.level}</td>
						<td>${gtype.parent}</td>
						<td>


							<button onclick="delselectedType(${gtype.id});" class="btn btn-danger btn-sm">删除</button>&nbsp;&nbsp;
							<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal${gtype.id}">修改</button>&nbsp;&nbsp;
							<!-- bootstrap弹出模态框 -->

							<div class="modal fade" tabindex="-1" role="dialog" id="myModal${gtype.id}">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">
												<span>&times;</span>
											</button>
											<h4 class="modal-title">修改商品类型信息</h4>
										</div>
										<form action="adminServlet?method=updateGoodsTypeByPid" method="post" class="form-horizontal">
											<div class="motal-body">
												<div class="form-group">
													<label class="col-sm-2 control-label">商品类型</label>
													<div class="col-sm-10">
														<input type="hidden" name="id" value="${gtype.id}">
															<%--<input type="hidden" name="level" value="${goods.level}">--%>
														<input type="text" name="name" class="form-control" value="${gtype.name}">
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">等级</label>
													<div class="col-sm-10">
														<input type="text" name="level" class="form-control" value="${gtype.level}">
													</div>
												</div>

												<div class="form-group">
													<label class="col-sm-2 control-label">所属类型</label>
													<div class="col-sm-10">
														<input type="text" name="parent" class="form-control" value="${gtype.parent}">
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