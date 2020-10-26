package com.controller;

import com.entity.*;
import com.service.*;
import com.utils.PageHtmlUtils;
import com.utils.SafeUtil;

import com.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @author linxiaobai
 * @date 2020
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    TypesService typesService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    TopsService topsService;

    /***
     * 登录验证
     * @param admin
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String login(Admin admin, HttpSession session, HttpServletRequest request) {
        List<Admin> adminList = adminService.queryAll();
        for (Admin admin1 : adminList) {
            if (admin1.getUsername().equals(admin.getUsername())) {//当存在这个用户
                if (admin1.getPassword().equals(SafeUtil.encode(admin.getPassword()))) {
                    session.setAttribute("admin", admin);
//                    String msg = "欢迎进入后台管理，" + admin.getUsername();
                    request.setAttribute("msg", "恭喜你登录成功！");
                    return "/admin/index.jsp";
                }
            }
        }
        request.setAttribute("msg", "你输入的账号或者密码不正确");
        return "/admin/login.jsp";
    }

    /**
     * 后台首页
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        request.setAttribute("msg", "恭喜你登录成功！");
        return "/admin/index.jsp";
    }

    /**
     * 退出
     *
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("admin");
        session.removeAttribute("msg");
        return "/admin/login.jsp";
    }

    /**
     * header.jsp中类目管理
     *
     * @return
     */
    @RequestMapping("/typeList")
    public String typeList(HttpServletRequest request) {
        List<Types> typeList = typesService.getAllTypes();
        request.setAttribute("typeList", typeList);
        return "/admin/type_list.jsp";
    }

    /**
     * 类目管理中添加类目
     */
    @GetMapping("/typeAdd")
    public String typeAdd() {
        return "/admin/type_add.jsp";
    }

    @PostMapping("/typeSave")
    public String typeSave(Types types) {
        typesService.insertTypes(types);
        return "/admin/typeList";
    }

    /**
     * 修改类目
     */
    @GetMapping("/typeEdit")
    public String typeEdit(int id, HttpServletRequest request) {
        Types type = typesService.getByIdTypes(id);
        request.setAttribute("type", type);
        return "/admin/type_edit.jsp";
    }

    @PostMapping("/typeUpdate")
    public String typeUpdate(Types type, HttpServletRequest request) {
        System.out.println(type.toString());
        boolean status = typesService.updateType(type);
        if (status) {
            request.setAttribute("msg", "修改成功！");
        } else {
            request.setAttribute("msg", "修改失败！");
        }
        return "/admin/typeList";
    }

    /**
     * 删除类目
     */
    @GetMapping("/typeDelete")
    public String typeDelete(int id) {
        typesService.deleteType(id);
        return "/admin/typeList";
    }

    /**
     * 商品管理
     */
    @GetMapping("/goodList")
    public String goodList(HttpServletRequest request,
                           @RequestParam(required = false, defaultValue = "0") int type,
                           @RequestParam(required = false, defaultValue = "1") int page,
                           @RequestParam(required = false, defaultValue = "6") int size
    ) {
        List<Goods> goodList;
        if (type == 0) {//查找所有
            goodList = goodsService.getAllGoods(page, size);
            request.setAttribute("goodList", goodList);
            request.setAttribute("pageTool", PageHtmlUtils.getPageTool(request, goodsService.getListCount(), page, size));
        } else {//查看今日推荐商品
            goodList = goodsService.getTypesToTopsGoods(type, page, size);
            request.setAttribute("goodList", goodList);
            request.setAttribute("pageTool", PageHtmlUtils.getPageTool(request, goodsService.getCountTops(type), page, size));
        }
        return "/admin/good_list.jsp";
    }

    /**
     * 添加商品
     */
    @GetMapping("/goodAdd")
    public String goodAdd(HttpServletRequest request) {
        //加载类目
        List<Types> types = typesService.getAllTypes();
        request.setAttribute("typeList", types);
        return "/admin/good_add.jsp";
    }

    /**
     * 商品添加功能
     */
    @PostMapping("/goodSave")
    public String goodSave(Goods goods, MultipartFile file) throws Exception {
//        System.out.println("前台传来的goods:" + goods.toString());
        goods.setCover(UploadUtil.upload(file));
        System.out.println(goods.getCover());
//        System.out.println("修改后的值：" + goods.toString());
        goodsService.addGood(goods);
        return "redirect:goodList";
    }

    /**
     * 添加到今日推荐
     */
    @PostMapping("/topSave.action")
    public @ResponseBody
    String topSave(int goodId, int type) {
        boolean status = topsService.addTops(type, goodId);
        if (status) {
            return "ok";
        } else {
            return "no";
        }
    }

    /**
     * 移除今日推荐
     */
    @PostMapping("/topDelete.action")
    public @ResponseBody
    String topDelete(int goodId, int type) {
        boolean status = topsService.deleteTops(type, goodId);
        if (status) {
            return "ok";
        } else {
            return "no";
        }
    }

    /**
     * 修改商品（界面）
     */
    @GetMapping("/goodEdit")
    public String goodEdit(int id, HttpServletRequest request) {
        Goods goods = goodsService.getGoodsById(id);
        request.setAttribute("good", goods);
        //加载类目
        List<Types> types = typesService.getAllTypes();
        request.setAttribute("typeList", types);
        return "/admin/good_edit.jsp";
    }

    /**
     * 修改商品功能
     */
    @PostMapping("/goodUpdate")
    public String goodUpdate(Goods goods, MultipartFile file) throws Exception {
        if (file != null) {
            goods.setCover(UploadUtil.upload(file));
        }
        goodsService.updateGood(goods);
        return "redirect:goodList";
    }

    /**
     * 删除商品
     */
    @GetMapping("/goodDelete")
    public String goodDelete(int id) {
        goodsService.deleteGood(id);
        return "redirect:goodList";
    }

    /**
     * 订单管理
     */
    @RequestMapping("/orderList")
    public String orderList(HttpServletRequest request,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "6") int size) {
        List<Order> orderList;
        long count;
        String status = request.getParameter("status");
        if (status != null && Objects.nonNull(status)) {
            orderList = orderService.getAllOrder1(Integer.parseInt(status), page, size);
            count = orderService.getOrderCount(Integer.parseInt(status));
        } else {
            orderList = orderService.getAllOrder(page, size);
            count = orderService.getOrderCount();
        }
        request.setAttribute("orderList", orderList);
        request.setAttribute("pageTool", PageHtmlUtils.getPageTool(request, count, page, size));
        return "/admin/order_list.jsp";
    }

    /**
     * 发货
     */
    @GetMapping("/orderSend")
    public String orderSend(int status, int id) {
        orderService.updateStatus(status, id);
        return "/admin/orderList";
    }

    /**
     * 订单点击完成
     *
     * @param status
     * @param id
     * @return
     */
    @GetMapping("/orderFinish")
    public String orderFinish(int status, int id) {
        orderService.updateStatus(status, id);
        return "/admin/orderList";
    }

    /**
     * 删除
     */
    @GetMapping("/orderDelete")
    public String orderDelete(int status, int id) {
        orderService.deleteOrder(id, status);
        return "/admin/orderList";
    }

    /**
     * 用户管理
     */
    @RequestMapping("/userList")
    public String userList(HttpServletRequest request,
                           @RequestParam(required = false, defaultValue = "1") int page,
                           @RequestParam(required = false, defaultValue = "10") int size) {
        List<User> userList = userService.queryAllUser(page, size);
        request.setAttribute("userList", userList);
        request.setAttribute("pageTool", PageHtmlUtils.getPageTool(request, userService.queryAllCount(), page, size));
        return "/admin/user_list.jsp";
    }

    /**
     * 添加客户
     */
    @RequestMapping("/userAdd")
    public String userAdd() {
        return "/admin/user_add.jsp";//添加客户界面
    }

    @PostMapping("/userSave")
    public String userSave(User user, HttpServletRequest request) {
        User user1 = userService.queryUserByName(user.getUsername());
        //先判断是否存在此用户
        if (user1 == null && Objects.isNull(user1)) {
            //注册该用户并让密码加密
            String pwd = SafeUtil.encode(user.getPassword());
            user.setPassword(pwd);
            userService.add(user);
            return "/admin/userList";
        } else {
            request.setAttribute("msg", "该用户已经被注册，请重新输入！");
            return "/admin/userAdd";
        }
    }

    /**
     * 重置密码
     */
    @RequestMapping("/userRe")
    public String userRe(int id, HttpServletRequest request) {
        //加载当前用户信息
        User user = userService.queryPassword(id);
        request.setAttribute("user", user);
        return "/admin/user_reset.jsp";
    }

    @PostMapping("/userReset")
    public String userReset(User user, HttpServletRequest request) {
        String pwd = SafeUtil.encode(user.getPassword());
        user.setPassword(pwd);
        if (userService.updatePassword(user)) {
            request.setAttribute("msg", "修改成功！");
        } else {
            request.setAttribute("msg", "修改失败！请重新输入！");
        }
        return "/admin/userRe";
    }

    /**
     * 修改用户
     */
    @RequestMapping("/userEdit")
    public String userEdit(int id, HttpServletRequest request) {
        //加载当前用户信息
        User user = userService.queryPassword(id);
        request.setAttribute("user", user);
        return "/admin/user_edit.jsp";
    }

    @PostMapping("/userUpdate")
    public String userUpdate(User user, HttpServletRequest request) {
        if (user.getName().trim().equals("") || user.getAddress().trim().equals("") || user.getPhone().trim().equals("")) {
            request.setAttribute("msg", "修改失败,请正确填写姓名、电话、地址！");
            return "/admin/userEdit";
        } else {
            userService.updateAddress(user);
            return "/admin/userList";
        }
    }

    /**
     * 删除用户
     */
    @GetMapping("/userDelete")
    public String userDelete(int id) {
        userService.deleteUser(id);
        return "/admin/userList";
    }

    /**
     * 管理员
     */
    @RequestMapping("/adminList")
    public String adminList(HttpServletRequest request,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "10") int size) {
        List<Admin> adminList = adminService.queryAllAdmin(page, size);
        request.setAttribute("adminList", adminList);
        request.setAttribute("pageTool", PageHtmlUtils.getPageTool(request, adminService.getAllAdminCount(), page, size));
        return "/admin/admin_list.jsp";
    }

    /**
     * 添加管理员
     */
    @GetMapping("/adminAdd")
    public String adminAdd() {
        return "/admin/admin_add.jsp";
    }

    @PostMapping("/adminSave")
    public String adminSave(Admin admin) {
        String pwd = SafeUtil.encode(admin.getPassword());
        admin.setPassword(pwd);
        adminService.addAdmin(admin);
        return "/admin/adminList";
    }

    /**
     * 重置密码
     */
    @RequestMapping("/adminRe")
    public String adminRe(int id, HttpServletRequest request) {
        Admin admin = adminService.getAdminById(id);
        request.setAttribute("admin", admin);
        return "/admin/admin_reset.jsp";
    }

    @PostMapping("/adminReset")
    public String adminReset(Admin admin, HttpServletRequest request) {
        String pwd = SafeUtil.encode(admin.getPassword());
        admin.setPassword(pwd);
        boolean type = adminService.updatePwd(admin);
        if (type) {
            request.setAttribute("msg", "修改成功！");
        } else {
            request.setAttribute("msg", "修改失败！请重新输入！");
        }
        return "/admin/adminRe";
    }

    /**
     * 删除管理员
     */
    @GetMapping("/adminDelete")
    public String adminDelete(int id) {
        adminService.deleteAdmin(id);
        return "/admin/adminList";
    }
}
