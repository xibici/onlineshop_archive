package com.xjh.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //注意public  判断用户的行为
        String methodName = req.getParameter("method");
        //打印调用的方法
        System.out.println("Base调用了的"+methodName+"方法");
        //3.获取方法对象Method
        try {
            //4.执行方法   //完成方法的分发
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            String url =(String) method.invoke(this, req, resp);
            if(!"upLoad".equals(methodName)){
                if(url!=null&&url.trim().length()!=0){
                    //转发
                    if(url.startsWith("redirect:")){
                        resp.sendRedirect(req.getContextPath()+url.split(":")[1]);
                    }else {
                        req.getRequestDispatcher(url).forward(req, resp);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*将传入的对象序列化为json,写回客户端*/
    public void writeValue(Object obj, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
        System.out.println("obj"+obj);


    }
    /*将传入的对象序列化为json,返回*/
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
