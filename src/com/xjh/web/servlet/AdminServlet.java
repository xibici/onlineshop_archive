package com.xjh.web.servlet;

import com.xjh.domain.Goods;
import com.xjh.domain.GoodsType;
import com.xjh.domain.Order;
import com.xjh.domain.User;
import com.xjh.service.GoodsService;
import com.xjh.service.GoodsTypeService;
import com.xjh.service.OrderService;
import com.xjh.service.UserService;
import com.xjh.service.impl.GoodsServiceImpl;
import com.xjh.service.impl.GoodsTypeServiceImpl;
import com.xjh.service.impl.OrderServiceImpl;
import com.xjh.service.impl.UserServiceImpl;
import com.xjh.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet("/adminServlet")
@MultipartConfig
public class AdminServlet extends BaseServlet {
    //显示商品列表
    public String getGoodsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //el
        GoodsService goodsService = new GoodsServiceImpl();
        List<Goods> goodsList = goodsService.findAll();
        request.setAttribute("goodsList",goodsList);

        return "/admin/showGoods.jsp";
    }
    //根据pid删除数据库商品
    public void delGoodsByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GoodsService goodsService = new GoodsServiceImpl();
        String pid = request.getParameter("pid");
        if(pid!=null&&!"".equals(pid)){
            goodsService.delGoodsByPidAdmin(pid);
        }

    }
    //根据pid更新数据库商品信息
    public String updateGoodsByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        response.setContentType("text/html;charset=utf-8");
        //获得参数
        GoodsService goodsService = new GoodsServiceImpl();
        String id = request.getParameter("id");//商品id
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String typeid = request.getParameter("typeid");
        if (StringUtils.isEmpty(name)){
            response.getWriter().write("<script type='text/javascript'>alert('商品名称不能为空');window.location='adminServlet?method=getGoodsList';</script>");
            return null;
        }
        if (StringUtils.isEmpty(price)){
            response.getWriter().write("<script type='text/javascript'>alert('价格不能为空');window.location='adminServlet?method=getGoodsList';</script>");
            return null;
        }
        if (StringUtils.isEmpty(typeid)){
            response.getWriter().write("<script type='text/javascript'>alert('商品类型不能为空');window.location='adminServlet?method=getGoodsList';</script>");
            return null;
        }

        //图片获取
        //上传图片
        Part part = request.getPart("myfile");
        String filename="";
        //没有上传文件 不改动文件

        try {
            System.out.println(part);
            String disposition = part.getHeader("Content-Disposition");
            String suffix = disposition.substring(disposition.lastIndexOf("."),disposition.length()-1);
            //随机的生存一个32的字符串
            filename = UUID.randomUUID()+suffix;
            //获取上传的文件名
            InputStream is = part.getInputStream();
            //动态获取服务器的路径 真实路径
            String serverpath = request.getServletContext().getRealPath("image/goods");
            FileOutputStream fos = new FileOutputStream(serverpath+"/"+filename);
            System.out.println(serverpath+"/"+filename);
            System.out.println(filename);
            byte[] bytes = new byte[1024];
            int length =0;
            while((length=is.read(bytes))!=-1){
                fos.write(bytes,0,length);
            }
            //释放资源
            fos.close();
            is.close();
            filename="image/goods/"+filename;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("没有上传文件");
        }


        //开始更新
        if(id!=null&&!"".equals(id)){//where id=?
            goodsService.updateGoodsByPidAdmin(name,new BigDecimal(price),Integer.parseInt(typeid),Integer.parseInt(id),filename);
        }

        return "/adminServlet?method=getGoodsList";
    }
    //根据查询条件显示商品列表 查找
    public String findGoodsBySearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String search = request.getParameter("name");
        GoodsService goodsService = new GoodsServiceImpl();
        List<Goods> goodsList = goodsService.findGoodsBySearch(search);
        request.setAttribute("goodsList",goodsList);

        return "/admin/showGoods.jsp";
    }
    //增加商品 //addGoods
    public String addGoodsAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        GoodsService goodsService = new GoodsServiceImpl();
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String typeid = request.getParameter("typeid");//分类
        String star = request.getParameter("star");
        String intro = request.getParameter("intro");//简介
        String pubdate = request.getParameter("pubdate");

        //处理照片
        //上传图片
        Part part = request.getPart("myfile");
        System.out.println(part);
        String disposition = part.getHeader("Content-Disposition");
        String suffix = disposition.substring(disposition.lastIndexOf("."),disposition.length()-1);
        //随机的生存一个32的字符串
        String filename = UUID.randomUUID()+suffix;
        //获取上传的文件名
        InputStream is = part.getInputStream();
        //动态获取服务器的路径 真实路径
        String serverpath = request.getServletContext().getRealPath("image/goods");
        FileOutputStream fos = new FileOutputStream(serverpath+"/"+filename);
        System.out.println(serverpath+"/"+filename);
        System.out.println(filename);
        byte[] bytes = new byte[1024];
        int length =0;
        while((length=is.read(bytes))!=-1){
            fos.write(bytes,0,length);
        }
        //释放资源
        fos.close();
        is.close();

        //Date date = new Date();
        //转日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        try {
            Date date = simpleDateFormat.parse(pubdate);
            //处理一波文件名 带个前缀
            filename="image/goods/"+filename;
            goodsService.addGoodsAdmin(name,new BigDecimal(price),Integer.parseInt(typeid),Integer.parseInt(star),date,filename,intro);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Date date1 = new Date();


        return null;
    }
    //获取商品类型列表
    public String getGoodsTypeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //getGoodsTypeList
        GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        List<GoodsType> goodsTypeList = goodsTypeService.findAll();
        request.setAttribute("goodsTypeList",goodsTypeList);

        return "/admin/showGoodsType.jsp";

    }
    //byname name代表商品类型
    public String findGoodsTypeByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("name");
        GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        List<GoodsType> goodsTypeList = goodsTypeService.findGoodsTypeBySearch(search);
        request.setAttribute("goodsTypeList",goodsTypeList);


    return "/admin/showGoodsType.jsp";
    }

    //根据id删除商品类型
    public void delGoodsTypeByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //delGoodsTypeByPid ajax请求删除商品类型
        GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        String ptid = request.getParameter("ptid");
            if(ptid!=null&&!"".equals(ptid)){
                goodsTypeService.delGoodsTypeByPidAdmin(Integer.parseInt(ptid));
            }
    }

    //根据商品类型更新类型信息
    public String updateGoodsTypeByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        String id = request.getParameter("id");//商品id
        String name = request.getParameter("name");
        String level = request.getParameter("level");
        String parent = request.getParameter("parent");
        response.setContentType("text/html;charset=utf-8");  //解决js乱码问题
        //检测空内容
        if (StringUtils.isEmpty(name)){
            response.getWriter().write("<script type='text/javascript'>alert('商品类型不能为空');window.location='adminServlet?method=getGoodsTypeList';</script>");
            return null;
        }
        if (StringUtils.isEmpty(level)){
            response.getWriter().write("<script type='text/javascript'>alert('等级不能为空');window.location='adminServlet?method=getGoodsTypeList';</script>");
            return null;
        }
        if (StringUtils.isEmpty(parent)){
            response.getWriter().write("<script type='text/javascript'>alert('所属类型不能为空');window.location='adminServlet?method=getGoodsTypeList';</script>");
            return null;
        }
        //开始更新
        if(id!=null&&!"".equals(id)){//where id=?
            goodsTypeService.updateGoodsTypeByPid(name, Integer.parseInt(level),Integer.parseInt(parent),Integer.parseInt(id));
        }
        return "/adminServlet?method=getGoodsTypeList";

    }

    //根据用户id更新信息 updateUserByid
    public String updateUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        String id = request.getParameter("id");//用户uid
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String role = request.getParameter("role");//普通还是管理员
        String flag = request.getParameter("flag");

        response.setContentType("text/html;charset=utf-8");  //解决js乱码问题
        //检测空内容
        if (StringUtils.isEmpty(email)){
            response.getWriter().write("<script type='text/javascript'>alert('邮箱不能为空');window.location='adminServlet?method=getUserList';</script>");
            return null;
        }
        if (StringUtils.isEmpty(username)){
            response.getWriter().write("<script type='text/javascript'>alert('姓名不能为空');window.location='adminServlet?method=getUserList';</script>");
            return null;
        }
        if (StringUtils.isEmpty(password)){
            response.getWriter().write("<script type='text/javascript'>alert('密码不能为空');window.location='adminServlet?method=getUserList';</script>");
            return null;
        }
        if (StringUtils.isEmpty(role)){
            response.getWriter().write("<script type='text/javascript'>alert('账号类型不能为空');window.location='adminServlet?method=getUserList';</script>");
            return null;
        }
        if (StringUtils.isEmpty(flag)){
            response.getWriter().write("<script type='text/javascript'>alert('激活状态不能为空');window.location='adminServlet?method=getUserList';</script>");
            return null;
        }

        //开始更新
        if(id!=null&&!"".equals(id)){//where id=?
            //void updateUserByid(String username, String email, String gender, int role, int flag, int id);
            userService.updateUserByidAdmin(username,password,email,gender,Integer.parseInt(role),Integer.parseInt(flag),Integer.parseInt(id));
        }
        response.getWriter().write("<script type='text/javascript'>window.location='/admin/userList.jsp'</script>");


        return null;

    }

        //addGoodsType增加商品类型
    public String addGoodsType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        String typename = request.getParameter("typename");//种类名称
        String level = request.getParameter("level");//种类名称
        String parent = request.getParameter("parent");//种类名称
        goodsTypeService.addGoodsType(typename, Integer.parseInt(level),Integer.parseInt(parent));
        return null;
    }

    //获取所有订单号
    public String getAllOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //getGoodsTypeList
        OrderService orderService = new OrderServiceImpl();
        List<Order> orderList = orderService.findAll();
        request.setAttribute("orderList", orderList);

        return "/admin/showAllOrder.jsp";

    }

    //getUserList获取用户数据
    public void getUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //ajax请求
        UserService userService = new UserServiceImpl();
        //List<GoodsType> goodsTypeList = goodsTypeService.findAll();
        List<User> list=userService.findAll();

        //将user写回客户端,用已封装方法
        writeValue(list,response);

    }

    //删除用户  deleteUser
    public void deleteUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        UserService userService = new UserServiceImpl();
        userService.deleteUserById(Integer.parseInt(id));

    }

    //findGoodsBySearch 根据查询条件显示商品列表 查找
    public void findUserBySearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String search = request.getParameter("search");
        UserService UserService = new UserServiceImpl();
        List<User> list = UserService.findUserBySearch(search);//jsp中遍历的是list
       // request.setAttribute("list",list);
        writeValue(list,response);//ajax
//username
    }

    //管理员登录验证
    public String adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (StringUtils.isEmpty(username)){
            request.setAttribute("msg","用户名不能为空");
            return "/admin/login.jsp";
        }
        if (StringUtils.isEmpty(password)){
            request.setAttribute("msg","密码不能为空");
            return "/admin/login.jsp";
        }
        //验证用户名和密码是否正确
        UserService userService = new UserServiceImpl();
        User user = userService.loginAdmin(username, password);
        System.out.println("返回的user"+user);
        if (user==null){
            request.setAttribute("msg","用户名或密码错误");
            return "/admin/login.jsp";
        }else {
            //有没有权限
            if (user.getRole()!=0){
                request.setAttribute("msg","用户不是管理员");
                return "/admin/login.jsp";
            }
            //登陆成功
            request.getSession().setAttribute("admin",true);

            //重定向到首页
            return "redirect:/admin/admin.jsp";
        }

    }

    //退出登陆 logOut
    public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.删除session中的用户数据
        request.getSession().removeAttribute("admin");
        //2.session失效
        request.getSession().invalidate();

        return "redirect:/admin/login.jsp";


    }

    //文件上传

    public String upLoad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //上传图片
        Part part = request.getPart("myfile");
        String disposition = part.getHeader("Content-Disposition");
        String suffix = disposition.substring(disposition.lastIndexOf("."),disposition.length()-1);
        //随机的生存一个32的字符串
        String filename = UUID.randomUUID()+suffix;
        //获取上传的文件名
        InputStream is = part.getInputStream();
        //动态获取服务器的路径 真实路径
        String serverpath = request.getServletContext().getRealPath("IMG");
        FileOutputStream fos = new FileOutputStream(serverpath+"/"+filename);
        System.out.println(filename);
        byte[] bytes = new byte[1024];
        int length =0;
        while((length=is.read(bytes))!=-1){
            fos.write(bytes,0,length);
        }
        //释放资源
        fos.close();
        is.close();
        return filename;

    }

    //findOrderBySearch 按查询条件查找订单
    public String findOrderBySearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           //接收参数
        String userId = request.getParameter("userId");
        String status = request.getParameter("status");

        //让dao判断
        OrderServiceImpl orderService = new OrderServiceImpl();
        List<Order> orderList=orderService.findOrderListBySearch(userId,status);
        request.setAttribute("orderList",orderList);

        //回显信息
        request.setAttribute("userId",userId);
        request.setAttribute("status",status);

        return "/admin/showAllOrder.jsp";



    }

//updateOrderById    更改订单信息
public String updateOrderById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    OrderServiceImpl orderService = new OrderServiceImpl();
    String id = request.getParameter("id");//商品id
    String money = request.getParameter("money");
    String status = request.getParameter("status");
    response.setContentType("text/html;charset=utf-8");  //解决js乱码问题
    //检测空内容
    if (StringUtils.isEmpty(money)){
        response.getWriter().write("<script type='text/javascript'>alert('总金额不能为空');window.location='adminServlet?method=getAllOrder';</script>");
        return null;
    }
    if (StringUtils.isEmpty(status)){
        response.getWriter().write("<script type='text/javascript'>alert('订单状态不能为空');window.location='adminServlet?method=getAllOrder';</script>");
        return null;
    }
    System.out.println(id+money+status);
    //开始更新
    if(id!=null&&!"".equals(id)){//where id=?
        orderService.updateOrderById( new BigDecimal(money),Integer.parseInt(status),id);
    }


    return "/adminServlet?method=getAllOrder";

}



}

