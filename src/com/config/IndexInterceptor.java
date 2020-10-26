package com.config;

import com.entity.Goods;
import com.entity.Tops;
import com.entity.Types;
import com.entity.User;
import com.service.CartsService;
import com.service.GoodsService;
import com.service.TypesService;
import org.apache.ibatis.annotations.Many;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author linxiaobai
 * @date 2020
 */
public class IndexInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    TypesService typesService;
    @Autowired
    CartsService cartsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<Types> typeList = typesService.getAllTypes();
        request.setAttribute("typeList", typeList);
        String uri = request.getRequestURI();
        if (uri.contains("index/cart") || uri.contains("index/my") || uri.contains("index/order")) {
            Object user = request.getSession().getAttribute("user");
            if (Objects.isNull(user)) {
                response.sendRedirect("login");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
