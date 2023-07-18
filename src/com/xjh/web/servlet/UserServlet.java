package com.xjh.web.servlet;

import cn.dsna.util.images.ValidateCode;
import com.xjh.domain.Address;
import com.xjh.domain.User;
import com.xjh.service.AddressService;
import com.xjh.service.UserService;
import com.xjh.service.impl.AddressServiceImpl;
import com.xjh.service.impl.UserServiceImpl;
import com.xjh.utils.Base64Utils;
import com.xjh.utils.RandomUtils;
import com.xjh.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userServlet")
public class UserServlet extends BaseServlet {
    //登录功能
    public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("登陆方法");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("用户名"+username);
        String auto = request.getParameter("auto");
        if (StringUtils.isEmpty(username)){
            request.setAttribute("msg","用户名不能为空");
            return "/login.jsp";
        }
        if (StringUtils.isEmpty(password)){
            request.setAttribute("msg","密码不能为空");
            return "/login.jsp";
        }
        //验证用户名和密码是否正确
        UserService userService = new UserServiceImpl();
        User user = userService.login(username, password);
        System.out.println("返回的user"+user);
        if (user==null){
            request.setAttribute("msg","用户名或密码错误");
            return "/login.jsp";
        }else {
            //有用户
            //有没有激活
            if (user.getFlag()!=1){
                request.setAttribute("msg","用户和未激活或被禁用");
                return "/login.jsp";
            }
            //有没有权限
            if (user.getRole()!=1){
                request.setAttribute("msg","用户没有权限");
                return "/login.jsp";
            }

            //登陆成功
            request.getSession().setAttribute("user",user);
            if (auto!=null){
                //勾选了自动登录
                String userinfo=username+"#"+password;
                //加密用户密码 创建 cookie存入
                Cookie cookie = new Cookie("userinfo", Base64Utils.encode(userinfo));
                cookie.setMaxAge(60*60*24*14);//cookie存放两周
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
            //重定向到首页
            return "redirect:/index.jsp";
        }

    }

    //退出功能
    public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.删除session中的用户数据
        request.getSession().removeAttribute("user");
        //2.session失效
        request.getSession().invalidate();
        //3.删除cookie
        Cookie cookie = new Cookie("userinfo", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "redirect:/index.jsp";
    }

    public String checkUserName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        if (username == null || username.trim().length() == 0) {
            return null;
        }

        UserService userService = new UserServiceImpl();
        User user = userService.checUserName(username);
        //用户名不存在 则返回1 用户名不可用
        if (user != null) {
            response.getWriter().write("1");
        } else {
            response.getWriter().write("0");
        }

        return null;
    }

    /*注册功能*/
    public String register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.用户注册
        //1.1获取数据
        try {
            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());
            String repassword = request.getParameter("repassword");
            //检验数据是否为空
            if (StringUtils.isEmpty(user.getUsername())) {
                //用户名为空 不走
                request.setAttribute("registerMsg", "用户名不能为空");
                return "/register.jsp";
            }
            if (StringUtils.isEmpty(user.getPassword())) {
                //密码为空 不走
                request.setAttribute("registerMsg", "密码不能为空");
                return "/register.jsp";
            }
            if (!user.getPassword().equals(repassword)) {
                request.setAttribute("registerMsg", "两次密码不一致");
                return "/register.jsp";
            }
            if (StringUtils.isEmpty(user.getEmail())) {
                //邮箱为空 不走
                request.setAttribute("registerMsg", "邮箱不能为空");
                return "/register.jsp";
            }

            //调用业务Service
            UserService userService = new UserServiceImpl();
            //flag激活 role角色 code激活码  没有
            user.setFlag(0);//0没激活 1激活
            user.setRole(1);
            user.setCode(RandomUtils.createActive());//给用户设置随机激活码
            System.out.println(user.toString());
            userService.register(user);
            System.out.println("用户注册");
            return "redirect:/registerSuccess.jsp";

        } catch (Exception e) {
            //注册失败
            request.setAttribute("registerMsg", "注册失败");
            e.printStackTrace();
        }

        return "/register.jsp";
    }

    /*显示验证码功能*/
    public String code(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //新建验证码
        ValidateCode validateCode = new ValidateCode(100, 40, 4, 20);
        String code = validateCode.getCode();
        request.getSession().setAttribute("vcode", code);
        validateCode.write(response.getOutputStream());

        return null;

    }

    //验证登陆输入的验证码是否正确
    public String checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientcode = request.getParameter("code");
        String servercode = (String) request.getSession().getAttribute("vcode");
        if (StringUtils.isEmpty(clientcode)) {
            return null;
        }
        if (clientcode.equalsIgnoreCase(servercode)) {
            response.getWriter().write("0");
        } else{
            response.getWriter().write("1");
        }

        return null;

    }

    //获取收货地址 getAddress
    public String getAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断是否登陆
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/login.jsp";
        }
        AddressService addressService = new AddressServiceImpl();
        List<Address> addList = addressService.findByUid(user.getId());//获取地址列表 数据库中
        request.setAttribute("addList",addList);
        System.out.println(addList);
        return "/self_info.jsp";
    }
        //addAddress
    public String addAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断是否登陆
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/login.jsp";
        }
        //获取所有信息
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
        if (StringUtils.isEmpty(name)){
            request.setAttribute("msg","收件人不能为空");
            return getAddress(request,response);//为空再取一次 访问之后再查询一次
        }
        if (StringUtils.isEmpty(phone)){
            request.setAttribute("msg","电话不能为空");
            return getAddress(request,response);
        }
        if (StringUtils.isEmpty(detail)){
            request.setAttribute("msg","地址不能为空");
            return getAddress(request,response);
        }
        Address address = new Address(null, detail, name, phone, user.getId(), 0);//默认地址1 不是默认0
        AddressService addressService = new AddressServiceImpl();
        addressService.add(address);

        return getAddress(request,response);
    }

    /*user/defaultAddress?id=设为默认地址*/
    public String defaultAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断是否登陆
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        AddressService addressService = new AddressServiceImpl();
        addressService.updateDefault(Integer.parseInt(id),user.getId());/*地址表中的地址id和用户id*/
            //修改完成后转发回去
        return getAddress(request,response);
    }

    //deleteAddress
    public String deleteAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断是否登陆
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        AddressService addressService = new AddressServiceImpl();
        addressService.delete(Integer.parseInt(id));

        return getAddress(request,response);
    }

    //user/updateAddress
    public String updateAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断是否登陆
        User user = (User) request.getSession().getAttribute("user");
        response.setContentType("text/html;charset=utf-8");
        if (user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        String level = request.getParameter("level");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");

        if (StringUtils.isEmpty(name)){
            response.getWriter().write("<script type='text/javascript'>alert('收件人不能为空');window.location='userServlet?method=getAddress'</script>");
            return null;
        }
        if (StringUtils.isEmpty(phone)){
            response.getWriter().write("<script type='text/javascript'>alert('电话不能为空');window.location='userServlet?method=getAddress'</script>");
            return null;
        }
        if (StringUtils.isEmpty(detail)){
            response.getWriter().write("<script type='text/javascript'>alert('详细地址不能为空');window.location='userServlet?method=getAddress'</script>");
            return null;
        }

        //更新地址
        Address address = new Address(Integer.parseInt(id), detail, name, phone, user.getId(), Integer.parseInt(level));
        AddressService addressService = new AddressServiceImpl();
        addressService.update(address);

        return getAddress(request,response);

    }
        //激活用户
    public String activate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        //设置编码
        response.setContentType("text/html;charset=utf-8");
        //获取邮箱和激活码 并与他们的base加密后的值对比
        String e = request.getParameter("e");
        String c = request.getParameter("c");
        //解密一波
        String email = null;
        String code = null;
        try {
            email = Base64Utils.decode(e);
            code = Base64Utils.decode(c);

        } catch (Exception ex) {
            ex.printStackTrace();
            response.getWriter().write("激活码错误");
            throw new RuntimeException("激活码错误",ex);
            //出现 编码异常 idea报错
        }
        //通过邮箱查询用户
        UserService userService = new UserServiceImpl();
        User user = userService.findUserByEmail(email);
        System.out.println(user);
        if (user != null&&code.equals(user.getCode())) {
            //激活码对应的上  靠邮箱查询用户修改激活状态
            userService.activateUser(email);
            response.getWriter().write("激活成功!");
        } else {
            response.getWriter().write("激活码错误");
        }

        return null;
    }


    //微信支付wexinpay
    public String wexinpay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String money = request.getParameter("money");
        String oid = request.getParameter("oid");
        request.setAttribute("oid",oid);
        request.setAttribute("money",money);

        return "payment.jsp";

    }

    }