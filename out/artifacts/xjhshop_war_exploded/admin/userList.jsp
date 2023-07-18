<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>WI网后台主页-会员信息页面</title>
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		loadUser();
	});
	//连接servlet 获取 数据
	function loadUser(){
		$.get("${pageContext.request.contextPath}/adminServlet?method=getUserList",{},function (list) {
			//解析list user用户数据
			showMsg(list);
		})
	}
	//显示用户信息
	function showMsg(list) {
		//插入首航
		$("#tb_list").html("<tr class='tr_head'><td>编号</td><td>邮箱</td><td>姓名</td><td>密码</td><td>性别</td><td>类别</td><td>激活状态</td><td>操作</td></tr>");
		var i = 1;
		for (var u in list) {//开始遍历
			//声明 tr  td  对象
			var tr = $("<tr></tr>");
			var td1 = $("<td>" + list[u].id + "</td>");
			var td2 = $("<td>" + list[u].email + "</td>");
			var td3 = $("<td>" + list[u].username + "</td>");
            var td4 = $("<td>" + list[u].password + "</td>");/*由于md5加密*/
			var td5 = $("<td>" + list[u].gender + "</td>");
			var td6 = $("<td>" + (list[u].role == 0 ? "管理员" : "会员") + "</td>");
			var td7 = $("<td>" + (list[u].flag == 0 ? "未激活" : "正式会员") + "</td>");//list[u] 激活状态
			var td8 = $("<td><a href='javascript:delUser(" + list[u].id + ")' class='btn btn-primary btn-xs'>删除</a>"
						+
							"<button class=\"btn btn-default btn-sm\" data-toggle=\"modal\" data-target=\"#myModal"+list[u].id+"\">修改</button>&nbsp;&nbsp;\n" +
					"\t\t\t\t\t\t\t\t\t<!-- bootstrap弹出模态框 -->\n" +
					"\n" +
					"\t\t\t\t\t\t\t\t\t<div class=\"modal fade\" tabindex=\"-1\" role=\"dialog\" id=\"myModal"+list[u].id+"\">\n" +
					"\t\t\t\t\t\t\t\t\t\t<div class=\"modal-dialog\" role=\"document\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t<div class=\"modal-content\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"modal-header\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span>&times;</span>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t</button>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"modal-title\">修改用户</h4>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +			/*adminServlet?method=updateGoodsByPid*/
					"\t\t\t\t\t\t\t\t\t\t\t\t<form action=\"\/xjhshop\/adminServlet?method=updateUserById\"  method=\"post\" class=\"form-horizontal\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"motal-body\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<label class=\"col-sm-2 control-label\">邮箱</label>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-sm-10\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"id\" value=\""+list[u].id+"\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" name=\"email\" class=\"form-control\" value=\""+list[u].email+"\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>"+
						"<div class=\"form-group\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<label class=\"col-sm-2 control-label\">姓名</label>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-sm-10\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" name=\"username\" class=\"form-control\" value=\""+list[u].username+"\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "<div class=\"form-group\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<label class=\"col-sm-2 control-label\">密码</label>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-sm-10\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" name=\"password\" class=\"form-control\" value=\""+list[u].password+"\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<label class=\"col-sm-2 control-label\">性别</label>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-sm-10\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"form-group form-inline\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<label>性别:</label>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<select name=\"gender\" id=\"gender\" class=\"form-control\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"男\">男</option>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"女\">女</option>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</select>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<label class=\"col-sm-2 control-label\">帐号类别</label>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-sm-10\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" name=\"role\" class=\"form-control\" value=\""+list[u].role+"\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<label class=\"col-sm-2 control-label\">激活状态</label>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-sm-10\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" name=\"flag\" class=\"form-control\" value=\""+list[u].flag+"\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"motal-footer text-center\" style=\"margin-bottom:10px\">\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-primary\">修改</button>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t\t</form>\n" +
					"\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t\t\t\t\t\t</div>\n" +
					"\t\t\t\t\t\t\t\t\t</div>"


						+"</td>");
			//将td 添加到tr中
			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);//节点流
			tr.append(td7);
            tr.append(td8);
			$("#tb_list").append(tr);
		}
	}
	//删除用户
	function delUser(id){
		if(confirm("确认要删除吗?")){//adminServlet
			$.get("${pageContext.request.contextPath}/adminServlet?method=deleteUserById",{id:id},function (data) {
				location.reload();
			});
		}
	}

	//使用ajax 进行异步交互
	function search() {
		var search=$("#search1").val();
		$.get("${pageContext.request.contextPath}/adminServlet?method=findUserBySearch",{search:search},function(list){
			showMsg(list);
		});
	}

</script>
</head>
<body>
	<div class="row" style="width: 100%;">
			<div class="col-md-12">
				<div class="panel panel-default">
					<div class="panel-heading">会员列表</div>
					<div class="panel-body">
					<!-- 条件查询 -->
					<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div class="form-group form-inline">
									<span>用户姓名</span>
									<input type="text" name="username" id="search1" class="form-control">
								</div>
							</div>

							<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
								<button onclick="search();"  class="btn btn-primary">查找</button>
							</div>
						</div>
						<!-- 列表显示 -->
						<div style="height: 400px;overflow: scroll;">
							<table id="tb_list" class="table table-striped table-hover table-bordered">

							</table>
						</div>

					</div>
				</div>
			</div>
		</div>
</body>
</html>