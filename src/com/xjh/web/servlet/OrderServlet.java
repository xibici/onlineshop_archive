package com.xjh.web.servlet;

import com.xjh.domain.*;
import com.xjh.service.AddressService;
import com.xjh.service.CartService;
import com.xjh.service.GoodsService;
import com.xjh.service.OrderService;
import com.xjh.service.impl.AddressServiceImpl;
import com.xjh.service.impl.CartServiceImpl;
import com.xjh.service.impl.GoodsServiceImpl;
import com.xjh.service.impl.OrderServiceImpl;
import com.xjh.utils.RandomUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/orderServlet")
public class OrderServlet extends BaseServlet {
    //获取订单
    public String getOrderView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1判断有没有登陆
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/login.jsp";
        }
        //2查询购物车的商品
        CartService cartService = new CartServiceImpl();
        List<Cart> carts = cartService.findByUid(user.getId());
        request.setAttribute("carts",carts);
        //3获取收货地址
        AddressService addressService=new AddressServiceImpl();
        List<Address> addList=addressService.findByUid(user.getId());
        request.setAttribute("addList",addList);
        System.out.println(addList);
        return "/order.jsp";
    }

    //提交订单  事务相关
    public String addOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断有无登录
        User user =(User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/login.jsp";
        }
        String aid = request.getParameter("aid");
        //1.获取购物车里购买的东西  根据用户id 查看用户买的东西
        CartService cartService = new CartServiceImpl();
        List<Cart> carts = cartService.findByUid(user.getId());
        //1.5判断无商品 或者已经执行事务
        if (carts==null||carts.size()==0){
            request.setAttribute("msg","购物车为空,请选择商品");
            return "/message.jsp";
        }
        //2.先创建订单号 随机数字
        String oid= RandomUtils.createOrderId();
        //2.1遍历cart获得订单详情
        //2.2集合接收单个订单详情
        List<OrderDetail> orderDetails = new ArrayList<>();
        //2.3 算出创建订单所需要的总金额
        BigDecimal sum = new BigDecimal(0);
        for(Cart cart :carts){
            OrderDetail orderDetail = new OrderDetail(null, oid, cart.getPid(), cart.getNum(), cart.getMoney());
            orderDetails.add(orderDetail);
            sum=sum.add(cart.getMoney());//算钱
        }
        //3.创建订单
        Order order = new Order(oid, user.getId(), sum, "1", new Date(), Integer.parseInt(aid));
        OrderService orderService=new OrderServiceImpl();
        try {
            orderService.saveOrder(order,orderDetails);
            request.setAttribute("order",order);//传过去
            return "/orderSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","存在错误，订单提交失败"+e.getMessage());
            return "/message.jsp";
        }

    }

    //查找获取当前用户所有订单        getOrderList
    public String getOrderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断有无登录
        User user =(User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/login.jsp";
        }
        int uid = user.getId();
        System.out.println(uid);
        OrderService orderService = new OrderServiceImpl();
        List<Order> orderList=orderService.getOrderList(uid);
        request.setAttribute("orderList",orderList);
        return "/orderList.jsp";
    }
    //获取订单详情 getOrderDetail  orderDetail表中
    public String getOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数 oid string类型
        String oid = request.getParameter("oid");
        OrderService orderService = new OrderServiceImpl();
        GoodsService goodsService = new GoodsServiceImpl();
        Order order=orderService.findOrderById(oid);
        List<OrderDetail> orderDetails=orderService.findAllorderDetail(oid);
        //1.根据订单号获取订单详情orderDetail
        request.setAttribute("orderDetails",orderDetails);
        request.setAttribute("order",order);
        //2.接收商品 遍历详情
        int size = orderDetails.size();
        System.out.println(size);
        List<GoodsWithNumAndMoney> goodsWithNumAndMoneyList=new ArrayList<>();
        for (OrderDetail orderDetail:orderDetails){
            Goods goods = goodsService.findById(orderDetail.getPid());
            GoodsWithNumAndMoney goodsWithNumAndMoney = new GoodsWithNumAndMoney(
                    goods.getId(), goods.getName(), goods.getPubdate(),goods.getPicture(),
                    goods.getPrice(), goods.getStar(), goods.getIntro(),goods.getTypeid(),
                    goods.getGoodsType(),orderDetail.getNum(),orderDetail.getMoney());
            goodsWithNumAndMoneyList.add(goodsWithNumAndMoney);
        }
        request.setAttribute("goodsWithNumAndMoneyList",goodsWithNumAndMoneyList);
        //通过地址 获取收件人信息
        AddressService addressService = new AddressServiceImpl();
        Address address=addressService.findById(order.getAid());
        request.setAttribute("address",address);
        return "/orderDetail.jsp";
    }


}


    //根据当前用户获取订单order  再