package com.controller;

import com.entity.Goods;
import com.entity.Order;
import com.entity.Types;
import com.entity.User;
import com.service.CartsService;
import com.service.GoodsService;
import com.service.TypesService;
import com.service.UserService;
import com.utils.SafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 *  @author linxiaobai
 * @date 2020


 * @desc 用户控制层
 */
@Controller
@RequestMapping("/index")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    TypesService typesService;
    @Autowired
    CartsService cartsService;

    @GetMapping("/register")
    public String register() {
        return "/index/register.jsp";
    }

    /**
     * 用户注册 注册成功后跳转到登录页面
     */
    @PostMapping("/register")
    public String register(User user, HttpServletRequest request) {
        if (user.getUsername() == null) {
            request.setAttribute("msg", "用户名不能为空！");
        } else if (Objects.nonNull(userService.queryUserByName(user.getUsername()))) {
            request.setAttribute("msg", "该用户名已经存在！");
        } else {
            userService.add(user);
            request.setAttribute("msg", "注册成功,可以登录啦");
            return "/index/login.jsp";
        }
        return "/index/register.jsp";
    }

    @GetMapping("/login")
    public String login() {
        return "/index/login.jsp";
    }

    /**
     * 登录成功跳转到商城首页
     */
    @PostMapping("/login")
    public String loginSuccess(User user, HttpServletRequest request) {
        boolean type = userService.queryLogin(user);
        if (type == true) {
            HttpSession session = request.getSession();
            user = userService.queryUserByName(user.getUsername());
            session.setAttribute("user", user);
            //购物车右上角的数字(待完善)
            session.setAttribute("cartCount", cartsService.getCartsCount(user.getId()));
//            return "/index/index";
            return "redirect:index";
        } else {
            request.setAttribute("msg", "登录失败，用户名或密码不正确，请重新输入");
            return "/index/login.jsp";
        }
    }

    /**
     * 跳转到修改收货地址界面
     *
     * @return
     */
    @GetMapping("/address")
    public String address() {
        return "/index/address.jsp";
    }

    /**
     * 修改用户收货地址
     */
    @PostMapping("/addressUpdate")
    public String addressUpdate(User user, HttpServletRequest request) {
        //获取当前用户的id
        HttpSession session = request.getSession();
        User user1 = (User) session.getAttribute("user");
        user.setId(user1.getId());
        //更新HttpSession中的用户信息
        user1.setName(user.getName());
        user1.setPhone(user.getPhone());
        user1.setAddress(user.getAddress());
        session.setAttribute("user", user1);
        //执行修改操作
        boolean o = userService.updateAddress(user);
        //判断是否修改成功
        if (o == true) {
            request.setAttribute("msg", "修改成功！");
        } else {
            request.setAttribute("msg", "修改失败！");
        }
        return "/index/address.jsp";
    }

    /**
     * 跳转到修改密码界面
     */
    @GetMapping("/password")
    public String password() {
        return "/index/password.jsp";
    }

    @PostMapping("/passwordUpdate")
    public String updatePassword(User user, HttpServletRequest request, @RequestParam("passwordNew") String passwordNew) {
        //获取当前用户的id
        HttpSession session = request.getSession();
        User user1 = (User) session.getAttribute("user");
        user.setId(user1.getId());
        //原始密码 oldPassword
        String oldPassword = userService.queryPassword(user.getId()).getPassword();
        //判断前端输入的原密码是否跟数据库中的密码一致
        if (oldPassword.equals(SafeUtil.encode(user.getPassword()).trim())) {
            //把前端输入的新密码加密存入数据库
            user.setPassword(SafeUtil.encode(passwordNew.trim()));
            userService.updatePassword(user);
            //如果正确的话就提示修改成功
            request.setAttribute("msg", "修改成功！");
        } else {
            //不正确的话就提示原始密码不正确，请重新输入！
            request.setAttribute("msg", "输入的原密码不对，修改失败，请重新输入！");
        }
        //返回当前页面
        return "/index/password.jsp";
    }

    /**
     * 退出 跳转到登录界面
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //清除用户信息
        session.removeAttribute("user");
        session.removeAttribute("cartCount");
        return "/index/login.jsp";
    }

}
