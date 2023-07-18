package com.xjh.web.servlet;

import com.alibaba.fastjson.JSON;
import com.xjh.domain.GoodsType;
import com.xjh.service.GoodsTypeService;
import com.xjh.service.impl.GoodsTypeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/goodsTypeServlet")
public class GoodsTypeServlet extends BaseServlet {
    public String goodstypelist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码
        response.setContentType("application/json;charset=utf-8");
        //获取商品类型
        GoodsTypeService goodsTypeService =new GoodsTypeServiceImpl();
        List<GoodsType> goodstypelist=goodsTypeService.findTypeByLevel(1);
        //使用fastjson转换成json字符串

        writeValue(goodstypelist,response);
        return null;
    }

   /* public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }*/
}
