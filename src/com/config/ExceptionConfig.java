package com.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author linxiaobai
 * @date 2020
 */
@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(MyException.class)
    public String exception(MyException exception, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("msg", exception.getMessage());
        return ("/index/error.jsp");
    }
    @ExceptionHandler(Exception.class)
    public String exception(MyException exception, HttpServletRequest request) {
        request.setAttribute("msg", "系统错误");
        return ("/index/error.jsp");
    }

    public static class MyException extends Exception {
        public MyException(String msg) {
            super(msg);
        }
    }
}
