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
    /**
     * 检测登录状态
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//      return super.preHandle(request, response, handler);
        String uri = request.getRequestURI();
        //contains判断是否包含指定的字符
        if (uri.contains("img") || uri.contains("css") || uri.contains("js") || uri.contains("login")
                || uri.contains("logout")) {
            return true;//不拦截
        }
        Object admin = request.getSession().getAttribute("admin");
        if (Objects.nonNull(admin) && !admin.toString().trim().isEmpty()) {
            return true;//登录验证通过
        }
        response.sendRedirect("login.jsp");
        return false;//其他情况一律拦截
    }
}
