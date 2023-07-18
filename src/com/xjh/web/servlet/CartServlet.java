package com.xjh.web.servlet;

import com.xjh.domain.Cart;
import com.xjh.domain.Goods;
import com.xjh.domain.User;
import com.xjh.service.CartService;
import com.xjh.service.GoodsService;
import com.xjh.service.impl.CartServiceImpl;
import com.xjh.service.impl.GoodsServiceImpl;
import com.xjh.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/cartServlet")
public class CartServlet extends BaseServlet {
    //加入购物车
    public String addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        response.setContentType("text/html;charset=utf-8");
        //判断有没有登陆
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            response.getWriter().write("<script type='text/javascript'>alert('你还未登录，请先登录');window.location='login.jsp';</scipt>");
        }
        //1.获取参数
        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");
        System.out.println("gid和num"+goodsId+"+"+number);

        if (StringUtils.isEmpty(goodsId)){
            return "redirect:/index.jsp";
        }
        //2.添加到购物车(数据库中存储)
        //2.1根据用户或id和商品id查询有没有商品
        CartService cartService=new CartServiceImpl();//gid pid
        Cart cart=cartService.findByUidAndPid(user.getId(),Integer.parseInt(goodsId));
        System.out.println("cart"+cart);
        //2.2判断cart为空
        GoodsService goodsService = new GoodsServiceImpl();
        Goods goods = goodsService.findById(Integer.parseInt(goodsId));
        System.out.println("goods"+goods);
        int num=Integer.parseInt(number);
        System.out.println("num"+num);
        try {
            if (cart==null){
                //添加
                cart=new Cart(user.getId(),Integer.parseInt(goodsId),num,goods.getPrice().multiply(new BigDecimal(num)));
                cartService.add(cart);
                System.out.println("cart:"+cart);
            }else {
                //更新
                cart.setNum(cart.getNum()+num);
                cart.setMoney(goods.getPrice().multiply(new BigDecimal(cart.getNum())));
                cartService.update(cart);
            }
            return "redirect:/cartSuccess.jsp";

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "redirect:/index.jsp";
        }

    }

    //获取购物车内容
    public String getCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        response.setContentType("text/html;charset=utf-8");
        //判断有没有登陆
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            response.getWriter().write("<script type='text/javascript'>alert('请先登录');window.location='login.jsp';</script>");
        }
        CartService cartService = new CartServiceImpl();
        List<Cart> carts=cartService.findByUid(user.getId());
        request.setAttribute("carts",carts);

        return "/cart.jsp";
    }


    //调整数量
    public String addCartAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        response.setContentType("text/html;charset=utf-8");
        //判断有没有登陆
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            //跳转并提示
            response.getWriter().write("<script type='text/javascript'>alert('请先登录');window.location='login.jsp';</script>");
        }
        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");
        //查询
        CartService cartService = new CartServiceImpl();
        Cart cart = cartService.findByUidAndPid(user.getId(), Integer.parseInt(goodsId));//购买的商品
        if (cart!=null){
            if (number.equals("0")){
                //删除
                cartService.delete(user.getId(),Integer.parseInt(goodsId));
            }else {
                //更新
                int num = Integer.parseInt(number);
                //获取单价
                BigDecimal price = cart.getMoney().divide(new BigDecimal(cart.getNum()));
                //更新数量
                cart.setNum(cart.getNum()+num);
                //更新金额
                cart.setMoney(price.multiply(new BigDecimal(cart.getNum())));
                cartService.update(cart);
            }
        }
        return null;
    }

    //clearCartAjax清空购物车里面所有内容
    public String clearCartAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断有没有登陆
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/login.jsp";
        }
        CartService cartService = new CartServiceImpl();
        cartService.deleteByUid(user.getId());
        return null;

    }



 }
