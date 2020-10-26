package com.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author linxiaobai
 * @date 2020
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.contains("img") || uri.contains("css") || uri.contains("js") || uri.contains("login")
                || uri.contains("logout")) {
            return true;
        }
        Object admin = request.getSession().getAttribute("admin");
        if (Objects.nonNull(admin) && !admin.toString().trim().isEmpty()) {
            return true;
        }
        response.sendRedirect("login.jsp");
        return false;
    }
}
