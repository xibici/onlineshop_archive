package com.xjh.web.servlet;

import com.xjh.domain.Goods;
import com.xjh.domain.PageBean;
import com.xjh.service.GoodsService;
import com.xjh.service.impl.GoodsServiceImpl;
import com.xjh.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/goodsServlet")
public class GoodsServlet extends BaseServlet {
    //用首页索引 比如点击计算机 传入typeid 然后根据typeid在数据库中查找商品列表
    public String getGoodsListByTypeId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeId = request.getParameter("typeId");
        String _pageNum = request.getParameter("pageNum");
        String _pageSize = request.getParameter("pageSize");
        //判断有无
        int pageNum = 1;
        int pageSize = 8;
        if (!StringUtils.isEmpty(_pageNum)) {
            pageNum = Integer.parseInt(_pageNum);
            if (pageNum < 1) {
                pageNum = 1;
            }
        }
        if (!StringUtils.isEmpty(_pageSize)) {
            pageSize = Integer.parseInt(_pageSize);
            if (pageSize < 1) {
                pageSize = 8;
            }
        }

        GoodsService goodsService = new GoodsServiceImpl();
        String condition = "";
        if (typeId != null && typeId.trim().length() != 0) {
            condition = "typeid=" + typeId;
        }
        //抓一下有问题直接弹回首页
        try {
            //pageNum当前页数 pageSize每页显示数目

            PageBean<Goods> pageBean = goodsService.findPageByWhere(pageNum, pageSize, condition);
            request.setAttribute("pageBean", pageBean);
            request.setAttribute("typeId", typeId);
            System.out.println(request.getParameter("typeId"));
            /*转发给*/
            return "/goodsList.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index.jsp";
        }

    }

    //根据每个商品的id查询商品详细信息
    public String getGoodsById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取id
        String id = request.getParameter("id");
        GoodsService goodsService = new GoodsServiceImpl();
        if (StringUtils.isEmpty(id)) {
            return "redirect:/index.jsp";
        }
        Goods goods = goodsService.findById(Integer.parseInt(id));
        request.setAttribute("goods", goods);

        return "/goodsDetail.jsp";
    }


/*
    //根据搜索查询商品  //findGoodsBySearch
    public String findGoodsBySearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        GoodsService goodsService = new GoodsServiceImpl();
        List<Goods> goodsBySearch = goodsService.findGoodsBySearch(search);

        return null;
    }*/
    //搜素商品 然后分页显示
    public String getGoodsListBySearchLimit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        String _pageNum = request.getParameter("pageNum");
        String _pageSize = request.getParameter("pageSize");
        //判断有无
        int pageNum = 1;
        int pageSize = 8;
        if (!StringUtils.isEmpty(_pageNum)) {
            pageNum = Integer.parseInt(_pageNum);
            if (pageNum < 1) {
                pageNum = 1;
            }
        }
        if (!StringUtils.isEmpty(_pageSize)) {
            pageSize = Integer.parseInt(_pageSize);
            if (pageSize < 1) {
                pageSize = 8;
            }
        }

        GoodsService goodsService = new GoodsServiceImpl();
        String condition = "";
        if (search != null && search.trim().length() != 0) {
            search = "'%" + search + "%'"; //%小明%
            condition = "name like " + search; //name like %小明%
            //search="%"+search+"%";//模糊查询
        }
        //抓一下有问题直接弹回首页
        try {
            //pageNum当前页数 pageSize每页显示数目

            PageBean<Goods> pageBean = goodsService.getGoodsListBySearchLimit(pageNum, pageSize, condition);
            request.setAttribute("pageBean", pageBean);
            request.setAttribute("search", search);
            /*转发给*/
            return "/goodsList.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index.jsp";
        }

    }
}