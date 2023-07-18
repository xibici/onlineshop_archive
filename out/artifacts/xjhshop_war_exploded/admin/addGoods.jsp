<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/DatePicker.js"></script>
	<script>
		$(function () {
			//ajax获取商品类型
			$.get("${pageContext.request.contextPath }/goodsTypeServlet?method=goodstypelist",{},function (data) {
					$(data).each(function () {
						var option = "<option value='"+this.id+"'>"+'Typeid:'+this.id+'-'+this.name+"</option>";
							//<option value="2">小米手机</option>
					// var option="<option name='"+this.id+"'>"+this.name+"</option>";
						$("#selectedTypeId").append(option);
					});
					/*$(data).each(function () {
                    //创建<option>
                    var option="<option name='"+this.id+"'>"+this.name+"</option>";
                    //调用select的append
                    province.append(option);
                })*/
			})
		})

	</script>

<title>商品添加页面</title>
</head>
<body>
	<div class="row" style="margin-left: 20px;">
		<form action="${pageContext.request.contextPath }/adminServlet?method=addGoodsAdmin" method="post" enctype="multipart/form-data">
			<div>
				<h3>新增商品</h3>
			</div>
			<hr />
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group form-inline">
						<label>名称:</label>
						<input type="text" name="name" class="form-control" />
					</div>
					
					<div class="form-group form-inline">
					<%--<c:forEach items="${goodstypelist}" var="goodsType" varStatus="i">--%>
						<label>分类:</label>
						<select name="typeid" id="selectedTypeId" class="form-control">
							<%--<option value="0">------</option>
							<option value="1">计算机</option>
							<option value="2">小米手机</option>
							<option value="3">笔记本</option>
							<option value="4">电视盒子</option>
							<option value="5">智能家电</option>--%>
							<%--<option value="${goodsType.id}">${goodsType.name}</option>--%>
						</select>
					<%--</c:forEach>--%>
					</div>
					<div class="form-group form-inline">
						<label>时间:</label>
						<input type="text" name="pubdate" readonly="readonly" class="form-control" onclick="setday(this)" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group form-inline">
						<label>价格:</label>
						<input type="text" name="price" class="form-control" />
					</div>
					<div class="form-group form-inline">
						<label>评分:</label>
						<input type="text" name="star" class="form-control" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-10">
					<div class="form-group form-inline">
						<label>商品图片</label>
						<input type="file" name="myfile" /><%--上传照片--%>
					</div>
					<div class="form-group ">
						<label>商品简介</label>
						<textarea  name="intro" class="form-control" rows="5"></textarea>
					</div>
					<div class="form-group form-inline">
						<input type="submit" value="添加" class="btn btn-primary" />
						<input type="reset" value="重置" class="btn btn-default" />
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>