package com.xjh.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/payServlet")
public class PayServlet extends BaseServlet {

    //订单进行支付操作
    public String alipay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进行alipay支付宝付款操作");
        //获取订单号和金额
        String omoney = request.getParameter("omoney");
        String oid = request.getParameter("oid");

        //设置内容进行转发
        request.setAttribute("oid",oid);
        request.setAttribute("omoney",omoney);
        return "/alipay.jsp";

    }


}


